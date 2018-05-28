using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using OpenCvSharp;
using Microsoft.SmallBasic.Library;

//ComboSESharp.csファイル
//コンボSEを鳴らす処理、映像を表示する処理、画像をファイル出力する処理を行う
//詳しい処理は Show_capture_devicies(); メソッドを参照のこと。

namespace WindowsFormsApplication3
{
	public partial class ComboSESharp : Form
	{
		int device_index = 0;
		VideoCapture capture;
		int top = 0;//キャプチャー範囲のy座標(最上部)
		int bottom = 0;//キャプチャー範囲のy座標(最下部) never used.
		int width = 0;//一文字の画像の幅
		int height = 0;//一文字の画像の高さ
		int left = 0;//キャプチャー範囲のx座標(左端)
		bool se_playable = true;//SEを一定時間間隔で再生するためのbool.
		int se_playing_timer = 0;//上記のboolをTrueに直すためのfpsカウンタ.
		string process_path = System.IO.Directory.GetCurrentDirectory();
		int combo = 0;//画像から読み取ったコンボ数そのもの.
		string chara_name = "_";//キャラクターネーム 例)高坂穂乃果, 高海千歌, etc...
		List<string> chara_file_names = new List<string>();//キャラクターの顔画像のファイル名を保存したList
		List<string> u_file_names = new List<string>();//一の位の画像のファイル名を保存したList
		List<string> t_file_names = new List<string>();//十の位の画像のファイル名を保存したList
		List<string> h_file_names = new List<string>();//百の位の画像のファイル名を保存したList
		public ComboSESharp()
		{
			InitializeComponent();
		}

		private void button1_Click(object sender, EventArgs e)
		{
			Thread t = new Thread(new ThreadStart(Show_capture_devicies));
			t.Start();
		}

		private void Show_capture_devicies()
		{
			try {
				bool detected = false;//キャラクターを認識できたかどうかを判別する bool.
				bool analyzable = false;//数値認識するかどうか
				List<Mat> units_comparable_imgs = new List<Mat>();
				List<Mat> tens_comparable_imgs = new List<Mat>();
				List<Mat> hundreds_comparable_imgs = new List<Mat>();
				List<Mat> faces_comparable_imgs = new List<Mat>();
				int imgs_separator = 0;
				foreach (List<Mat> t in comparable_imgs())
				{
					if (imgs_separator == 0)
					{
						units_comparable_imgs = t;
					}
					else if (imgs_separator == 1)
					{
						tens_comparable_imgs = t;
					}
					else if (imgs_separator == 2)
					{
						hundreds_comparable_imgs = t;
					}
					else if (imgs_separator == 3)
					{
						faces_comparable_imgs = t;
					}
					imgs_separator++;
				}
				imgs_separator = 0;
				device_index = (int)capture_device_index_numud.Value;
				capture = new VideoCapture();//ビデオキャプチャーのインスタンス
				capture.Open(device_index);//gui側で入力した数値のデバイスを開く
				if (!capture.IsOpened())
				{
					//開けない場合の処理
					throw new Exception("キャプチャーデバイスの初期化に失敗しました。");
				}

				left = (int)Combo_img_x.Value;
				top = (int)Combo_img_y.Value;
				width = (int)Combo_digits_width_numud.Value;
				height = (int)Combo_digits_height_numud.Value;

				int f_left = (int)face_x_numud.Value;
				int f_top = (int)face_y_numud.Value;
				int f_width = (int)face_width_numud.Value;
				int f_height = (int)face_height_numud.Value;

				FrameSource fs = FrameSource.CreateCameraSource(device_index);
				using (Window normalWindow = new Window("SIF_ComboSE_display.exe"))
				{
					if (Resizable.Checked)
					{
						normalWindow.OnMouseCallback += position_detect;
					}
					Mat frame = new Mat();//キャプチャー全体
					bool closable = false;
					bool[] combos = { true, true, true, true, true, true, true };//10, 50, 100, 200, 300, 400, 500のそれぞれのコンボ数の時SEを再生するかのbool値の配列.
					while (!closable)
					{
						if (!se_playable)
						{
							se_playing_timer++;
							if (se_playing_timer >= 60)
							{
								se_playable = true;
								se_playing_timer = 0;
							}
						}
                        //capture.Read(frame);
                        frame = capture.RetrieveMat();
						if (frame.Empty())
						{
							break;
						}

						if (!(cutting_showable.Checked))
						{
							normalWindow.ShowImage(frame);//映像を表示する処理(実際は画像を表示するメソッドであるので毎フレーム更新している。)
						}
						int k = Cv2.WaitKey(1);

						//以下出力映像から画像を作成する処理.
						Mat h_digit = new Mat();//いわゆる百の位の画像を処理するMat
						Mat t_digit = new Mat();//いわゆる十の位の画像を処理するMat
						Mat u_digit = new Mat();//いわゆる一の位の画像を処理するMat
						Mat face_img = new Mat();//センターの子の画像を処理するMat

						if (width <= 0)
						{
							throw new Exception("コンボ数値の座標が不正です。:幅が0あるいは負の数の設定になっています。");
						}
						if (height <= 0)
						{
							throw new Exception("コンボ数値の座標が不正です。:高さが0あるいは負の数の設定になっています。");
						}
						Rect h_rect = new Rect(0, 0, 0, 0);
						Rect t_rect = new Rect(0, 0, 0, 0);
						Rect u_rect = new Rect(0, 0, 0, 0);
						Rect face_rect = new Rect(0, 0, 0, 0);
						if (!(device_detecting.Checked))
						{
							h_rect = new Rect(left, top, width, height);// 引数はx,y,width,height
							t_rect = new Rect(left + width, top, width, height);
							u_rect = new Rect(left + (width - 1) * 2, top, width, height);
							face_rect = new Rect(f_left, f_top, f_width, f_height);
                            Mat f = capture.RetrieveMat();
							h_digit = frame[h_rect];//コンボ数値部分を切り取る処理(百の位)
							t_digit = frame[t_rect];//同上(十の位)
							u_digit = frame[u_rect];//同上(一の位)
							face_img = frame[face_rect];//キャラクターの顔を切り取る部分
							u_digit = img_preprocessing(u_digit);
							t_digit = img_preprocessing(t_digit);
							h_digit = img_preprocessing(h_digit);
							face_img = img_preprocessing(face_img);
						}
						if (chara_choosable.Checked && !(detected) && k == 97)
						{
							//キャラクターの顔の認識の処理
							int file_index = 0;
							foreach(Mat t in faces_comparable_imgs) {
								if (compare_img(face_img, t))
								{
									string name = chara_file_names[file_index];
									if (name.Contains("honoka"))
									{
										chara_name = "honoka";
                                        detected = true;
									}
									else if (name.Contains("eli"))
									{
										chara_name = "eli";
                                        detected = true;
                                    }
									else if (name.Contains("kotori"))
									{
										chara_name = "kotori";
                                        detected = true;
                                    }
									else if (name.Contains("umi"))
									{
										chara_name = "umi";
                                        detected = true;
                                    }
									else if (name.Contains("rin"))
									{
										chara_name = "rin";
                                        detected = true;
                                    }
									else if (name.Contains("maki"))
									{
										chara_name = "maki";
                                        detected = true;
                                    }
									else if (name.Contains("nozomi"))
									{
										chara_name = "nozomi";
                                        detected = true;
                                    }
									else if (name.Contains("hanayo"))
									{
										chara_name = "hanayo";
                                        detected = true;
                                    }
									else if (name.Contains("nico"))
									{
										chara_name = "nico";
                                        detected = true;
                                    }
									else if (name.Contains("chika"))
									{
										chara_name = "chika";
                                        detected = true;
                                    }
									else if (name.Contains("riko"))
									{
										chara_name = "riko";
                                        detected = true;
                                    }
									else if (name.Contains("kanan"))
									{
										chara_name = "kanan";
                                        detected = true;
                                    }
									else if (name.Contains("dia"))
									{
										chara_name = "dia";
                                        detected = true;
                                    }
									else if (name.Contains("you"))
									{
										chara_name = "you";
                                        detected = true;
                                    }
									else if (name.Contains("yoshiko"))
									{
										chara_name = "yoshiko";
                                        detected = true;
                                    }
									else if (name.Contains("hanamaru"))
									{
										chara_name = "hanamaru";
                                        detected = true;
                                    }
									else if (name.Contains("mari"))
									{
										chara_name = "mari";
                                        detected = true;
                                    }
									else if (name.Contains("ruby"))
									{
										chara_name = "ruby";
                                        detected = true;
                                    }
								}
								file_index++;
							}
						}
						if (k == 27 || k == 113)// ascii code 'ESC' and ascii code 'q' if q key is pushed.
						{
							/*qキーあるいはESCキーが押されたら、ウインドウを閉じる.*/
							if (picture_Takable.Checked && !(device_detecting.Checked))
							{
								Cv2.ImWrite("u_digit.png", u_digit);
								Cv2.ImWrite("t_digit.png", t_digit);
								Cv2.ImWrite("h_digit.png", h_digit);
                                Cv2.ImWrite("face.png", face_img);
                                Cv2.ImWrite("SIF_window.png", frame);
                                h_digit = frame[h_rect];//コンボ数値部分を切り取る処理(百の位)
                                t_digit = frame[t_rect];//同上(十の位)
                                u_digit = frame[u_rect];//同上(一の位)
                                face_img = frame[face_rect];//キャラクターの顔を切り取る部分
                                Cv2.ImWrite("u_digit_raw.png", u_digit);
                                Cv2.ImWrite("t_digit_raw.png", t_digit);
                                Cv2.ImWrite("h_digit_raw.png", h_digit);
                                Cv2.ImWrite("face_raw.png", face_img);
                            }
							closable = true;
							break;
						}

						if (cutting_showable.Checked && !(device_detecting.Checked))
						{
							Cv2.Rectangle(frame, h_rect, new Scalar(0, 255, 0), 3);//切り取る部分を表示する処理
							Cv2.Rectangle(frame, t_rect, new Scalar(0, 255, 0), 3);
							Cv2.Rectangle(frame, u_rect, new Scalar(0, 255, 0), 3);
							normalWindow.ShowImage(frame);//映像を表示する処理(実際は画像を表示するメソッドであるので毎フレーム更新している。)
						}
						if (analyzable)
						{

                            if (combos[0] && compare_img(u_digit, units_comparable_imgs[0]) && compare_img(t_digit, tens_comparable_imgs[0]))
                            {
                                combo = 10;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp);
                                }
                                combos[0] = false;
                            }
                            else if (combos[1] && compare_img(u_digit, units_comparable_imgs[1]) && compare_img(t_digit, tens_comparable_imgs[1]))
                            {
                                combo = 50;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp, false, true);
                                }
                                combos[1] = false;
                            }
                            else if (combos[2] && compare_img(h_digit, hundreds_comparable_imgs[0]) && compare_img(t_digit, tens_comparable_imgs[2]))
                            {
                                combo = 100;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp);
                                }
                                combos[2] = false;
                            }
                            else if (combos[3] && compare_img(h_digit, hundreds_comparable_imgs[1]) && compare_img(t_digit, tens_comparable_imgs[3]))
                            {
                                combo = 200;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp, false, true);
                                }
                                combos[3] = false;
                            }
                            else if (combos[4] && compare_img(h_digit, hundreds_comparable_imgs[2]) && compare_img(t_digit, tens_comparable_imgs[4]))
                            {
                                combo = 300;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp);
                                }
                                combos[4] = false;
                            }
                            else if (combos[5] && compare_img(h_digit, hundreds_comparable_imgs[3]) && compare_img(t_digit, tens_comparable_imgs[5]))
                            {
                                combo = 400;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp, false, true);
                                }
                                combos[5] = false;
                            }
                            else if (combos[6] && compare_img(h_digit, hundreds_comparable_imgs[4]) && compare_img(t_digit, tens_comparable_imgs[6]))
                            {
                                combo = 500;
                                object[] tmp = new object[2];
                                tmp[0] = combo;
                                tmp[1] = chara_name;
                                if (detected)
                                {
                                    se_play(tmp);
                                }
                                else if (!(chara_choosable.Checked))
                                {
                                    se_play(tmp, true, true);
                                }
                                else
                                {
                                    se_play(tmp);
                                }
                                combos[6] = false;

                            }

							if(k == 98)//ascii code 'b' if b key is pushed.
							{
								analyzable = false;
								for(int i = 0;i < combos.Length; i++)
								{
									combos[i] = true;
								}
                                detected = false;
                                chara_name = "_";
							}
						}
						if (k == 97)//ascii code 'a' if a key is pushed.
						{
							//aキーが押されたら、コンボ数を認識開始.
							analyzable = true;
							if (units_comparable_imgs.Count == 0)
							{
								throw new Exception("比較画像の読みとりに失敗しました。:一の位の画像");
							}
							if (tens_comparable_imgs.Count == 0)
							{
								throw new Exception("比較画像の読みとりに失敗しました。:十の位の画像");
							}
							if (hundreds_comparable_imgs.Count == 0)
							{
								throw new Exception("比較画像の読みとりに失敗しました。:百の位の画像");
							}
							if (faces_comparable_imgs.Count == 0)
							{
								throw new Exception("比較画像の読みとりに失敗しました。:顔画像");
							}
						}
					}
					capture.Release();
					Cv2.DestroyWindow("SIF_ComboSE_display.exe");//DestroyAllWindowだと多分親ウィンドウも消えるのでこの処理。
				}
			}catch(Exception err)
			{
				status_lbl.Text = "ステータス表示:" + err;
				capture = null;
			}
		}

		private List<List<Mat>> comparable_imgs()
		{
			List<Mat> u_digits_imgs = new List<Mat>();// units 1 imgs
			List<Mat> t_digits_imgs = new List<Mat>();// ten 10 imgs
			List<Mat> h_digits_imgs = new List<Mat>();// hundred 100 imgs
			List<Mat> chara_faces_imgs = new List<Mat>();// character_faces imgs
			List<List<Mat>> all_imgs = new List<List<Mat>>();//return value.
			string[] paths = { "units_nums", "tens", "hundreds", "faces" };
            int c = 0;
            foreach (string p in paths)
			{
				//path参照処理.
				string path = process_path + "\\" + p + "\\";
				string[] files = System.IO.Directory.GetFiles(path);
				foreach (string file in files)
				{
					Mat img = new Mat(file, ImreadModes.GrayScale);
                    Cv2.Resize(img, img, new OpenCvSharp.Size(28, 28));
                    img.ConvertTo(img, MatType.CV_32F);
                    if (c == 0)
					{
                        u_digits_imgs.Add(img); //10
                        u_file_names.Add(file);
					}
                    else if(c == 1)
                    { 
                        t_digits_imgs.Add(img);//10
                        t_file_names.Add(file);
                    }
                    else if(c == 2)
					{
                        h_digits_imgs.Add(img);//100
                        h_file_names.Add(file);
					}
                    else if(c == 3)
					{
                        //Console.WriteLine("fc adding");
						chara_faces_imgs.Add(img);
						chara_file_names.Add(file);
					}
				}
                c++;
            }

            for(int i = 0;i < u_file_names.Count; i++)
            {
                Mat t = u_digits_imgs[i];
                string p = u_file_names[i]; 
                if(p.Contains("10") && !(p.Contains("100")) && i != 0)
                {
                    Mat m = u_digits_imgs[0];
                    u_digits_imgs.Insert(0, t);
                    u_digits_imgs.Insert(i, m);
                }
                if(p.Contains("50") && !(p.Contains("500")) && i != 1)
                {
                    Mat m = u_digits_imgs[1];
                    u_digits_imgs.Insert(1, t);
                    u_digits_imgs.Insert(i, m);
                }
            }

            for (int i = 0; i < t_file_names.Count; i++) {
                Mat t = t_digits_imgs[i];
                string p = t_file_names[i];
                if (p.Contains("10") && !(p.Contains("100")) && i != 0)
                {
                    Mat m = t_digits_imgs[0];
                    t_digits_imgs.Insert(0, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if (p.Contains("50") && !(p.Contains("500")) && i != 1)
                {
                    Mat m = t_digits_imgs[1];
                    t_digits_imgs.Insert(1, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if (p.Contains("100") && i != 2)
                {
                    Mat m = t_digits_imgs[2];
                    t_digits_imgs.Insert(2, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if (p.Contains("200") && i != 3)
                {
                    Mat m = t_digits_imgs[3];
                    t_digits_imgs.Insert(3, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if (p.Contains("300") && i != 4)
                {
                    Mat m = t_digits_imgs[4];
                    t_digits_imgs.Insert(4, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if (p.Contains("400") && i != 5) {
                    Mat m = t_digits_imgs[5];
                    t_digits_imgs.Insert(5, t);
                    t_digits_imgs.Insert(i, m);
                }
                else if(p.Contains("500") && i != 6)
                {
                    Mat m = t_digits_imgs[6];
                    t_digits_imgs.Insert(6, t);
                    t_digits_imgs.Insert(i, m);
                }

            }

            for(int i = 0; i < h_file_names.Count; i++)
            {
                Mat t = h_digits_imgs[i];
                string p = h_file_names[i];
                if(i != 0 && p.Contains("100"))
                {
                    Mat m = h_digits_imgs[0];
                    h_digits_imgs.Insert(0, t);
                    h_digits_imgs.Insert(i, m);
                }else if (i != 1 && p.Contains("200"))
                {
                    Mat m = h_digits_imgs[1];
                    h_digits_imgs.Insert(1, t);
                    h_digits_imgs.Insert(i, m);
                }
                else if (i != 2 && p.Contains("300"))
                {
                    Mat m = h_digits_imgs[2];
                    h_digits_imgs.Insert(2, t);
                    h_digits_imgs.Insert(i, m);
                }
                else if (i != 3 && p.Contains("400"))
                {
                    Mat m = h_digits_imgs[3];
                    h_digits_imgs.Insert(3, t);
                    h_digits_imgs.Insert(i, m);
                }
                else if (i != 4 && p.Contains("500"))
                {
                    Mat m = h_digits_imgs[4];
                    h_digits_imgs.Insert(4, t);
                    h_digits_imgs.Insert(i, m);
                }
            }


			all_imgs.Add(u_digits_imgs);
			all_imgs.Add(t_digits_imgs);
			all_imgs.Add(h_digits_imgs);
			all_imgs.Add(chara_faces_imgs);
			return all_imgs;
		}

		private void position_detect(MouseEvent @event, int x, int y, MouseEvent flags) 
		{
			//参考：
			//http://sourcechord.hatenablog.com/entry/2016/08/28/120639

			//コンボの画像の左上を左クリックして、右下を右クリックしてもらうようにして
			//画像の高さと幅、座標を取得する
			status_lbl.Text = "コンボ数値切り取り範囲指定モード:百の位のコンボ数値の左上で左クリック、百の位のコンボ数値の右下で右クリックしてください.";
			int pd_top = 0;
			int pd_left = 0;

			if (flags.Equals(MouseEvent.FlagLButton))
			{
				pd_top = y;
				pd_left = x;
				Combo_img_x.Value = x;
				Combo_img_y.Value = y;
				label5.Text = "x座標:" + x + " y座標:" + y;
			}
			if (flags.Equals(MouseEvent.FlagRButton))
			{
				int pd_bottom = y;
				int pd_right = x;//キャプチャー範囲のx座標(右端)
				
				Combo_digits_width_numud.Value = pd_right - Combo_img_x.Value;
				Combo_digits_height_numud.Value = pd_bottom - Combo_img_y.Value;

				if ((int)Combo_digits_height_numud.Value > 0 && (int)Combo_digits_width_numud.Value > 0)
				{
					Resizable.Checked = false;
					status_lbl.Text = "ステータス表示:正常:コンボ数値切り取り範囲読み込み成功.";
				}
				label5.Text = "x座標:" + Combo_img_x.Value + " y座標:" + Combo_img_y.Value + " width :" + (pd_right - Combo_img_x.Value) + "height :" + (pd_bottom - Combo_img_y.Value);
			}
		}

		private Mat img_preprocessing(Mat img)
		{
			/*
			 * 画像の前処理　元画像→28x28の白黒画像に変換.
			 * 元は人工知能で使われている画像素材Mnistの
			 * 数値トレーニング用の画像に合わせる処理だったので、背景黒で、文字が白になる。
			 */
			Cv2.CvtColor(img, img, ColorConversionCodes.RGB2GRAY);
			Cv2.GaussianBlur(img, img, new OpenCvSharp.Size(3, 3), 0);
			Cv2.Resize(img, img, new OpenCvSharp.Size(28, 28));
			Cv2.Threshold(img, img, 144, 255, ThresholdTypes.Binary);
			img = 255 - img;
			Cv2.BitwiseNot(img, img);
            img.ConvertTo(img, MatType.CV_32F);
            
			return img;
		}

		public void play_sound(object args)
		{
			try {
				int combo = 0;
				string name = "_";
				int counter = 0;
				foreach (object t in (object[])args)
				{
					if (counter == 0)
					{
						combo = (int)t;
					}
					else
					{
						name = (string)t;
					}
					counter++;
				}
				if (name.Equals("_")) {
					throw new Exception("Error!!キャラクター名が指定されていません！");
				}
				if (combo == 0)
				{
					throw new Exception("Error!!コンボ数が読み取れませんでした");
				}
                if (combo <= 300)
                {
                    string se_path = process_path + "\\comboSEs\\" + name + "\\" + combo + ".mp3";
                    Sound.Play(se_path);
                }
                else
                {
                    string se_path = process_path + "\\comboSEs\\" + name + "\\eternal.mp3";
                    Sound.Play(se_path);
                }
			} catch(Exception e)
			{
				status_lbl.Text = "ステータス表示:" + e;
			}
		}

		private void se_play(object t, bool muse=true, bool aqours=false)
		{
			int combo = 0;
			string name = "_";
			int counter = 0;
			foreach (object o in (object[])t)
			{
				if (counter == 0)
				{
					combo = (int)o;
				}
				else
				{
					name = (string)o;
				}
				counter++;
			}
			if (!name.Equals("_"))
			{
				Thread t_sp = new Thread(new ParameterizedThreadStart(play_sound));
				t_sp.Start(t);
				se_playable = false;
			}
			else if(muse && aqours)
			{
				string[] all_names = {"honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico",
					"chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"};
				Random r = new Random();
				int p = r.Next(18);
				name = all_names[p];
				object[] o = new object[2];
				o[0] = combo;
				o[1] = name;
				Thread t_sp = new Thread(new ParameterizedThreadStart(play_sound));
				t_sp.Start(o);
				se_playable = false;
			}
			else if (muse)
			{
				string[] muse_names = { "honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico" };
				Random r = new Random();
				int p = r.Next(9);
				name = muse_names[p];
				object[] o = new object[2];
				o[0] = combo;
				o[1] = name;
				Thread t_sp = new Thread(new ParameterizedThreadStart(play_sound));
				t_sp.Start(o);
				se_playable = false;
			}
			else if (aqours)
			{
				string[] aqours_names = { "chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby" };
				Random r = new Random();
				int p = r.Next(9);
				name = aqours_names[p];
				object[] o = new object[2];
				o[0] = combo;
				o[1] = name;
				Thread t_sp = new Thread(new ParameterizedThreadStart(play_sound));
				t_sp.Start(o);
				se_playable = false;
			}
		}

		private bool compare_img(Mat frame_img, Mat comparable_img)
		{
			//frame_img は映像出力している部分から切り取った画像.
			//comparable_img は用意した比較用の画像.
			bool playable = false;//comparable_img//frame_img
            var res_img = new Mat(frame_img.Rows - comparable_img.Rows, frame_img.Cols - comparable_img.Cols, MatType.CV_32FC1);
            Cv2.MatchTemplate(frame_img, comparable_img, res_img, TemplateMatchModes.CCoeffNormed);
			double min_val = 0.0;
			double max_val = 0.0;
			Cv2.MinMaxLoc(res_img, out min_val, out max_val);
			double d = (int)div.Value/100.0;
			if(max_val >= d)
			{
				playable = true;
			}
			return playable;
		}

		private void div_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.div = (int)div.Value;
		}

		private void Combo_digits_width_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.width = (int)Combo_digits_width_numud.Value;
		}

		private void Combo_digits_height_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.height = (int)Combo_digits_height_numud.Value;
		}

		private void Combo_img_x_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.x_position = (int)Combo_img_x.Value;
		}

		private void Combo_img_y_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.y_position = (int)Combo_img_y.Value;
		}

		private void saver_Click(object sender, EventArgs e)
		{
			Properties.Settings.Default.Save();
		}

		private void capture_device_index_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.device_index = (int)capture_device_index_numud.Value;
		}

		private void face_width_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.f_width = (int)face_width_numud.Value;
		}

		private void face_height_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.f_height = (int)face_height_numud.Value;
		}

		private void face_x_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.f_left = (int)face_x_numud.Value;
		}

		private void face_y_numud_ValueChanged(object sender, EventArgs e)
		{
			Properties.Settings.Default.f_top = (int)face_y_numud.Value;
		}
	}
}
