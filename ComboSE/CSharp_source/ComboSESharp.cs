using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

//ComboSESharp.csファイル
//コンボSEを鳴らす処理、映像を表示する処理、画像をファイル出力する処理を行う
//詳しい処理は Show_capture_devicies(); メソッドを参照のこと。

namespace WindowsFormsApplication3
{
	public partial class ComboSESharp : Form
	{
		public ComboSESharp()
		{
			InitializeComponent();
            capture_device_index_numud.Value = Properties.Settings.Default.device_index;
            Combo_img_x.Value = Properties.Settings.Default.x_position;
            Combo_img_y.Value = Properties.Settings.Default.y_position;
            Combo_digits_width_numud.Value = Properties.Settings.Default.width;
            Combo_digits_height_numud.Value = Properties.Settings.Default.height;
            face_x_numud.Value = Properties.Settings.Default.f_left;
            face_y_numud.Value = Properties.Settings.Default.f_top;
            face_width_numud.Value = Properties.Settings.Default.f_width;
            face_height_numud.Value = Properties.Settings.Default.f_height;
		}

		private void button1_Click(object sender, EventArgs e)
		{
            Show_capture_devicies();
		}

        private void Show_capture_devicies()
        {
            string sendargs = "";
            sendargs += capture_device_index_numud.Value + " ";
            sendargs += Combo_img_x.Value + " ";
            sendargs += Combo_img_y.Value + " ";
            sendargs += Combo_digits_width_numud.Value + " ";
            sendargs += Combo_digits_height_numud.Value + " ";
            sendargs += face_x_numud.Value + " ";
            sendargs += face_y_numud.Value + " ";
            sendargs += face_width_numud.Value + " ";
            sendargs += face_height_numud.Value + " ";
            if (device_detecting.Checked)
            {
                sendargs += "1 ";
            }
            else
            {
                sendargs += "0 ";
            }
            if (picture_Takable.Checked)
            {
                sendargs += "1 ";
            }
            else
            {
                sendargs += "0 ";
            }
            if (cutting_showable.Checked)
            {
                sendargs += "1 ";
            }
            else
            {
                sendargs += "0 ";
            }
            sendargs += div.Value + " ";
            if (chara_choosable.Checked)
            {
                sendargs += "1";
            }
            else
            {
                sendargs += "0";
            }
            string cd = System.IO.Directory.GetCurrentDirectory();
            string path = cd + "\\ComboSE_bin.exe";
            var p = System.Diagnostics.Process.Start(path, sendargs);
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

        private void setting_loader_Click(object sender, EventArgs e)
        {
            Properties.Settings.Default.Reload();
        }
    }
}
