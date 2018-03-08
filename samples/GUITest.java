package samples;

import java.io.*;
import java.io.File.*;
import java.util.*;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.SetExpression;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.chart.*;
import javafx.util.Callback;
import javafx.application.Platform;
//import java.lang.Object;
//import javafx.scene.Node;

public class GUITest extends Application{
	static int printlogc = 1;
	static int maxNum = 0;
	static List<Card_datas> unit = new ArrayList<Card_datas>();
	static Card_datas[] unitdt = new Card_datas[9];
	static List<Skill_data> sdata = new ArrayList<Skill_data>();
	static Skill_data srdata;
	static List<Card_datas> cdatas = new ArrayList<Card_datas>();
	static int card_num = 0;
	static String iniDataPath;//SIF_SCsettings.ini
	static String chknulls;
	static String errstr;
	static int debuglevel = 0;
	static String chSelector = new String();
	static boolean chkclc = false;// use chara_SKills.setOnAction fire boolean.
	static int orcsm = 0;
	static int orcpr = 0;
	static int orccl = 0;
	static List<Music_data>music_list = new ArrayList<Music_data>();
	static Stage tmpstage = new Stage();
	static TabPane tpane = new TabPane();
	static ObservableList<Card_datas> listtounit = FXCollections.observableArrayList();
	static ListView<Card_datas> card_list = new ListView<Card_datas>();
	static Music_data calcMd = new Music_data();
	static Card_datas frend = new Card_datas();
	static double perper = 0.0;//パフェ率
	static int playcount = 0;//プレイ回数
	static double tapscrup = 0.0;//タップアップ値
	static int depth = 0;//探索幅
	static Calc_data calc_result = new Calc_data();
	static String result_score_str = new String();
	static Label result_score_label = new Label();
	static boolean ctou = false;
	static boolean utoc = false;
	final private static CalcScoreService cSS = new CalcScoreService();
	final private static ListView<Card_datas> unitListview = new ListView<Card_datas>();//ユニットのリストビュー
	final private static ObservableList<Card_datas> unitList = FXCollections.observableArrayList();

	public void start(Stage stage){
		tmpstage = stage;
		initializeComponents();
		initializeListeners();
		//*表示されるパネルの大きさ
		/*stage.setWidth(800);
		stage.setHeight(600);*/
		//タブパネルの作成
		StackPane spane = new StackPane();
		tpane.setPrefSize(800, 600);

		Tab chara_DataSet = new Tab();
		chara_DataSet.setClosable(false);
		chara_DataSet.setText("キャラクターデータ登録");
		StackPane spane_c_d = new StackPane();
		chara_DataSet.setContent(spane_c_d);
//=============================================================================
//タブ１ chara_DataSet START
//=============================================================================
			VBox setAnyThings = new VBox(14);
			setAnyThings.setPadding(new Insets(20, 25, 25, 25));
			setAnyThings.setAlignment(Pos.CENTER);
			HBox setAwakes = new HBox(2);
			setAwakes.setPadding(new Insets(10, 12, 12, 12));
			setAwakes.setAlignment(Pos.CENTER);
			HBox setcpprty = new HBox(3);
			setcpprty.setPadding(new Insets(10, 12, 12, 12));
			setcpprty.setAlignment(Pos.CENTER);

			/* GridPane */
			/*
			GridPane mainPane = new GridPane();
			GridPane subPane = new GridPane();
			*/

			//*チョイスボックス
			//ChoiceBox<String> chara_Sel = new ChoiceBox<>(FXCollections.observableArrayList("高坂穂乃果","絢瀬絵里","南ことり","園田海未","星空凛","西木野真姫","東条希","小泉花陽","矢澤にこ","高海千歌","桜内梨子","松浦果南","黒澤ダイヤ","渡辺曜","津島善子","国木田花丸","小原鞠莉","黒澤ルビィ"));
			//* ->1 色々不都合しかなかったので、コンボボックス化
			ComboBox<String> chara_Sel = new ComboBox<>(FXCollections.observableArrayList("高坂穂乃果","絢瀬絵里","南ことり","園田海未","星空凛","西木野真姫","東条希","小泉花陽","矢澤にこ","高海千歌","桜内梨子","松浦果南","黒澤ダイヤ","渡辺曜","津島善子","国木田花丸","小原鞠莉","黒澤ルビィ"));
			chara_Sel.setVisibleRowCount(9);
			chara_Sel.setValue("高坂穂乃果");
			//chara_Sel.getItems().addAll("高坂穂乃果","絢瀬絵里","南ことり","園田海未","星空凛","西木野真姫","東条希","小泉花陽","矢澤にこ","高海千歌","桜内梨子","松浦果南","黒澤ダイヤ","渡辺曜","津島善子","国木田花丸","小原鞠莉","黒澤ルビィ");

			//skills cb
			//ChoiceBox<String> chara_SKills = new ChoiceBox<>();
			//*同様にコンボボックス化 ->1
			ComboBox<String> chara_SKills = new ComboBox<>();

			//set rrity
			ComboBox<String> setRrity = new ComboBox<>();
			//set pprty
			ComboBox<String> setPprty = new ComboBox<>();

			//set awake
			RadioButton unawake = new RadioButton("未覚醒");
			RadioButton awake = new RadioButton("覚醒");
			ToggleGroup setawake = new ToggleGroup();

			//set sislt
			Label lblsislt = new Label("SISのスロット数");
			ComboBox<Integer> setSislt = new ComboBox<>();

			//set skilv
			Label lblskilv = new Label("特技レベル選択");
			ComboBox<Integer> setSkilv = new ComboBox<>();
			setSkilv.getItems().addAll(1,2,3,4,5,6,7,8);
			setSkilv.setValue(1);

			//setSksha score heal acc
			ComboBox<String> setSksha = new ComboBox<>();

			//running type of skill
			ComboBox<String> setRtpsk = new ComboBox<String>();
			//setRtpsk.setEditable(true);

			//発動条件型をセットするcb.リズムアイコンやタイマーなど...

			Label card_sm = new Label("0");
			Label card_pr = new Label("0");
			Label card_cl = new Label("0");


			//dataconverter用のFileChooser
			FileChooser fcer = new FileChooser();

			//dataconverter Btn
			Button dataconverter = new Button("変換する");

			//*保存用のボタン
			Button saver = new Button("保存する");

			//スキル説明ラベル
			Label detailsk = new Label("特技名を選択してください");

			try{
				chSelector = "高坂穂乃果";
				Skill_data[] bfdata = Skill_data.setdata(chSelector);
				if(sdata.size() >= 0){
					sdata.clear();
				}
				if(debuglevel == 1){
					//System.out.println("sdata.size() = "+ sdata.size());
					printlogc = -4;
				}
				for(int len = 0; len < bfdata.length; len++){
					sdata.add(len, bfdata[len]);
				}
				Skill_data[] table = new Skill_data[sdata.size()];
				for(int len = 0; len < sdata.size(); len++){
					table[len] = sdata.get(len);
				}
				String[] skilT = Skill_read.setSkillnameT("スマイル" ,Skill_read.setSkill("SR", table));
				chara_SKills.getItems().clear();
				chara_SKills.getItems().addAll(skilT);
				chara_SKills.setValue(String.valueOf(skilT[0].toString()));
				if(skilT.length >= 15){
					chara_SKills.setVisibleRowCount(15);
				}else{
					chara_SKills.setVisibleRowCount(skilT.length);
				}
				srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
			}catch(IOException e){
				System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:初期スキルがセットできませんでした");
				printlogc++;
				System.err.println(e);
				tpane.getSelectionModel().select(3);
			}

			setSksha.setValue(srdata.gsklef());
			Label skshalbl = new Label("特技名を指定してください");
			Label rtpsk = new Label("特技名を指定してください");

			//settings chara_SKills
			//chara_SKills.getItems().addAll("先に、キャラクターを選択してください");
			//chara_SKills.setValue("先に、キャラクターを選択してください");


			//settings setRrity
			setRrity.getItems().addAll("UR","SSR","SR");
			setRrity.setValue("SR");

			//settings setPprty.
			setPprty.getItems().addAll("スマイル","ピュア","クール");
			setPprty.setValue("スマイル");

			//settings setawake
			unawake.setToggleGroup(setawake);
			awake.setToggleGroup(setawake);
			setAwakes.getChildren().addAll(unawake, awake);
			unawake.setSelected(true);

			//settings setSislt
			setSislt.getItems().addAll(1,2,3,4,5,6,7,8);
			setSislt.setValue(1);

			//setting of setcpprty.
			setcpprty.getChildren().addAll(card_sm, card_pr, card_cl);

			//chara_Sel Event settings
			chara_Sel.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					chkclc = false;
					String tmpchnm = chara_Sel.getValue();//temporary character name
					if(chara_Sel.getValue().equals("園田海未")){
						try{
							//String[] buffer_CSds = new String[Chara_Skillsdata.mAx("園田海未")];
							/*String[] buffer_CSds = Card_datas.skill_Pprtylist(setPprty.getValue(),chara_Sel.getValue(), setRrity.getValue());*/ //20180108にコメントアウト
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
							//if(!chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() > 0){
									//for(int len = 0; len < sdata.size(); len++){
										//sdata.remove(len);
									//}
									sdata.clear();
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							//buffer_CSds = Chara_Skillsdata.sKillName("園田海未");
							/* この処理buffer_CSdsにキャラ毎の名前つけると読み込みが
							１回で済むし、軽くなる */
							/*chara_SKills.getItems().clear(); //20180108にコメントアウト
							chara_SKills.getItems().addAll(buffer_CSds);
							chara_SKills.setValue(buffer_CSds[0].toString());
							if(buffer_CSds.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(buffer_CSds.length);
							}*/
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}/*catch(FileNotFoundException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルが見つかりませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}*/
					}else if(tmpchnm.equals("桜内梨子")){
						chara_SKills.getItems().clear();
						chara_SKills.setValue("私の声、聞こえますか");
						chara_SKills.getItems().addAll("私の声、聞こえますか");
					}else if(tmpchnm.equals("高坂穂乃果")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								if(debuglevel == 1){
									System.out.println("sdata.size() = "+ sdata.size());
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if(skilT.length == 1){
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("南ことり")) {
						try {
							boolean chSelbl = false;
							if (chSelbl == chSelector.equals(tmpchnm)) {
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if (sdata.size() >= 0) {
									sdata.clear();
								}
								if (debuglevel == 1) {
									System.out.println("sdata.size() = " + sdata.size());
								}
								for (int len = 0; len < bfdata.length; len++) {
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for (int len = 0; len < sdata.size(); len++) {
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue(),
									Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if (skilT.length >= 15) {
								chara_SKills.setVisibleRowCount(15);
							} else {
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if (skilT.length == 1) {
								detailsk.setText(
									Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
								}
						} catch (IOException e) {
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(tmpchnm.equals("星空凛")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								if(debuglevel == 1){
									System.out.println("sdata.size() = "+ sdata.size());
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if(skilT.length == 1){
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}
				}
			});

			//setRrity Event settings
			setRrity.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					chkclc = false;
					String tmpchnm = chara_Sel.getValue();
					if(chara_Sel.getValue().equals("園田海未")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if(skilT.length == 1){
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
								//setcpprty settings.
								String[] rcpprty = Skill_read.setcpprty(srdata, setPprty.getValue(), setRrity.getValue(), ((RadioButton)setawake.getSelectedToggle()).getText());
								orcsm = Integer.parseInt(rcpprty[3]);
								orcpr = Integer.parseInt(rcpprty[4]);
								orccl = Integer.parseInt(rcpprty[5]);
								card_sm.setText(rcpprty[0]);
								card_pr.setText(rcpprty[1]);
								card_cl.setText(rcpprty[2]);
							}
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(chara_Sel.getValue().equals("高坂穂乃果")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if(skilT.length == 1){
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
								//setcpprty settings.
								String[] rcpprty = Skill_read.setcpprty(srdata, setPprty.getValue(), setRrity.getValue(), ((RadioButton)setawake.getSelectedToggle()).getText());
								orcsm = Integer.parseInt(rcpprty[3]);
								orcpr = Integer.parseInt(rcpprty[4]);
								orccl = Integer.parseInt(rcpprty[5]);
								card_sm.setText(rcpprty[0]);
								card_pr.setText(rcpprty[1]);
								card_cl.setText(rcpprty[2]);
							}
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("南ことり")) {
						try {
							boolean chSelbl = false;
							if (chSelbl == chSelector.equals(tmpchnm)) {
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if (sdata.size() >= 0) {
									sdata.clear();
								}
								if (debuglevel == 1) {
									System.out.println("sdata.size() = " + sdata.size());
								}
								for (int len = 0; len < bfdata.length; len++) {
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for (int len = 0; len < sdata.size(); len++) {
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue(),Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if (skilT.length >= 15) {
								chara_SKills.setVisibleRowCount(15);
							} else {
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if (skilT.length == 1) {
							detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						} catch (IOException e) {
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("星空凛")) {
						try {
							boolean chSelbl = false;
							if (chSelbl == chSelector.equals(tmpchnm)) {
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if (sdata.size() >= 0) {
									sdata.clear();
								}
								if (debuglevel == 1) {
									System.out.println("sdata.size() = " + sdata.size());
								}
								for (int len = 0; len < bfdata.length; len++) {
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for (int len = 0; len < sdata.size(); len++) {
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue(),
							Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if (skilT.length >= 15) {
								chara_SKills.setVisibleRowCount(15);
							} else {
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if (skilT.length == 1) {
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						} catch (IOException e) {
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}
				}
			});
			//setPprty Event settings
			setPprty.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					chkclc = false;
					String tmpchnm = chara_Sel.getValue();
					if(chara_Sel.getValue().equals("園田海未")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								if(debuglevel == 1){
									System.out.println("sdata.size() = "+ sdata.size());
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if(skilT.length == 1){
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(chara_Sel.getValue().equals("高坂穂乃果")){
						try{
							boolean chSelbl = false;
							if(chSelbl == chSelector.equals(tmpchnm)){
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if(sdata.size() >= 0){
									sdata.clear();
								}
								if(debuglevel == 1){
									System.out.println("sdata.size() = "+ sdata.size());
								}
								for(int len = 0; len < bfdata.length; len++){
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for(int len = 0; len < sdata.size(); len++){
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue() ,Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if(skilT.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
						}catch(IOException e){
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("南ことり")) {
						try {
							boolean chSelbl = false;
							if (chSelbl == chSelector.equals(tmpchnm)) {
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if (sdata.size() >= 0) {
									sdata.clear();
								}
								if (debuglevel == 1) {
									System.out.println("sdata.size() = " + sdata.size());
								}
								for (int len = 0; len < bfdata.length; len++) {
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for (int len = 0; len < sdata.size(); len++) {
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue(),Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if (skilT.length >= 15) {
								chara_SKills.setVisibleRowCount(15);
							} else {
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if (skilT.length == 1) {
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						} catch (IOException e) {
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("星空凛")) {
						try {
							boolean chSelbl = false;
							if (chSelbl == chSelector.equals(tmpchnm)) {
								Skill_data[] bfdata = Skill_data.setdata(tmpchnm);
								if (sdata.size() >= 0) {
									sdata.clear();
								}
								if (debuglevel == 1) {
									System.out.println("sdata.size() = " + sdata.size());
								}
								for (int len = 0; len < bfdata.length; len++) {
									sdata.add(len, bfdata[len]);
								}
								chSelector = tmpchnm;
							}
							Skill_data[] table = new Skill_data[sdata.size()];
							for (int len = 0; len < sdata.size(); len++) {
								table[len] = sdata.get(len);
							}
							String[] skilT = Skill_read.setSkillnameT(setPprty.getValue(),Skill_read.setSkill(setRrity.getValue(), table));
							chara_SKills.getItems().clear();
							chara_SKills.getItems().addAll(skilT);
							chara_SKills.setValue(String.valueOf(skilT[0].toString()));
							if (skilT.length >= 15) {
								chara_SKills.setVisibleRowCount(15);
							} else {
								chara_SKills.setVisibleRowCount(skilT.length);
							}
							srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
							if (skilT.length == 1) {
								detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
							}
						} catch (IOException e) {
							System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ内のキャラクター選択ボックス:スキルがセットできませんでした");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}
				}
			});
			//chara_SKills settings.
			chara_SKills.setOnMouseClicked(event -> chkclc = true);
			chara_SKills.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					if(chkclc == true){
						Skill_data[] table = new Skill_data[sdata.size()];
						for(int len = 0; len < sdata.size(); len++){
							table[len] = sdata.get(len);
						}
						srdata = Skill_read.onerdata(chara_SKills.getValue(), table);
						setSksha.setValue(srdata.gsklef());
						skshalbl.setText(srdata.gsklef());
						rtpsk.setText(srdata.gtpskr());
						setRtpsk.setValue(srdata.gtpskr());
						detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue()));
						if(debuglevel >= 1){
							//System.out.println(printlogc +"srdata.csm = " + srdata.gcsm());
							String srgcsm = "srdata.csm = " + srdata.gcsm();
							printoutTb(srgcsm);
						}
						//setcpprty settings.
						String[] rcpprty = Skill_read.setcpprty(srdata, setPprty.getValue(), setRrity.getValue(), ((RadioButton)setawake.getSelectedToggle()).getText());
						orcsm = Integer.parseInt(rcpprty[3]);
						orcpr = Integer.parseInt(rcpprty[4]);
						orccl = Integer.parseInt(rcpprty[5]);
						card_sm.setText(rcpprty[0]);
						card_pr.setText(rcpprty[1]);
						card_cl.setText(rcpprty[2]);

						chkclc = false;
						//これ冗長じゃないか...?
						//なんでbooleanを設定したの...?
						//ほんとにboolean必要...?
						//もっといい処理ないの...?
					}
				}
			});

			//setSkilv settings
			setSkilv.setOnAction(event -> detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue())));

			//*保存用のボタンが押された時のイベント
			saver.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					try{
						//if(//*チェックボックスでチェックを入れたときに、内容を上書きせず、初期化する){
						//FlieWriter fw = new FileWriter(Path() + "Character_data.txt");
						//}
						//*ファイル読み込み
						FileWriter fw = new FileWriter(System.getProperty("user.dir")+"\\Character_data.ini", true);
						//BufferedWriter bw = new BufferedWriter(fw);
						//PrintWriter pw = new PrintWriter(bw);
						PrintWriter pw = new PrintWriter(new BufferedWriter(fw));


						//*ファイル書き込み
						if(debuglevel == 1){
							System.out.println("saver is Pushed.");
						}
						if(card_num == 0 && cdatas.size() != 0){
							card_num = cdatas.get(cdatas.size() - 1).gcnum()+1;
						}else if(card_num == 0 && cdatas.size() == 0){
							card_num = 1;
						}
						if(chara_Sel.getValue() != null && pw != null){
							Card_datas bfod = new Card_datas();
							bfod.scnum(card_num);
							bfod.setname(chara_Sel.getValue());
							bfod.spprty(setPprty.getValue());
							bfod.srrity(setRrity.getValue());
							bfod.setskinm(chara_SKills.getValue());
							bfod.sstrawake(((RadioButton)setawake.getSelectedToggle()).getText());
							bfod.ssislt(setSislt.getValue());
							bfod.sskilv(setSkilv.getValue());
							bfod.ssksha(srdata.gsklef());
							bfod.sskitp(srdata.gtpskr());
							bfod.sfactv(srdata.gfsz());
							bfod.sprob(Skill_read.getprob(setSkilv.getValue(), srdata));
							if(srdata.gsklef().equals("判定")){
								bfod.saccut(Double.parseDouble(Skill_read.getefsz(setSkilv.getValue(), srdata)));
								bfod.sefsz(0);
							}else if(srdata.gsklef().equals("発動率")){
								String[] bfstr = Skill_read.getefsz(setSkilv.getValue(), srdata).split(",", 2);
								bfod.saccut(Double.parseDouble(bfstr[1]));
								bfod.sefsz(Integer.parseInt(bfstr[0]));
							}else if(srdata.gsklef().equals("パラメーター")){
								String[] bfstr = Skill_read.getefsz(setSkilv.getValue(), srdata).split(",", 2);
								bfod.saccut(Double.parseDouble(bfstr[1]));
								bfod.sefsz(Integer.parseInt(bfstr[0]));
							}else if(srdata.gsklef().equals("パーフェクト")){
								String[] bfstr = Skill_read.getefsz(setSkilv.getValue(), srdata).split(",", 2);
								bfod.saccut(Double.parseDouble(bfstr[1]));
								bfod.sefsz(Integer.parseInt(bfstr[0]));
							}else{
								bfod.saccut(srdata.gaccut());
								bfod.sefsz(Integer.parseInt(Skill_read.getefsz(setSkilv.getValue(),srdata)));
							}
							bfod.scsm(orcsm);
							bfod.scpr(orcpr);
							bfod.sccl(orccl);
							bfod.setcskin(srdata.getcsknm());
							bfod.setacskn(srdata.getscsnm());
							cdatas.add(bfod);
							listtounit.add(bfod);
							card_list.setItems(listtounit);
							Card_read.setonecarddata(pw, bfod);
							if(debuglevel >= 1){
								Card_datas[] printd = new Card_datas[cdatas.size()];
								for(int len = 0;len < cdatas.size();len++){
									printd[len] = cdatas.get(len);
								}
								Card_read.print_cdata(printd, printd.length);
							}
							if(card_num == 1 && chknulls.equals("first")){
								FileWriter fwSIFSCsettings = new FileWriter(iniDataPath);
								BufferedWriter bwSIFSCsettings = new BufferedWriter(fwSIFSCsettings);
								PrintWriter pwSIFSCsettings = new PrintWriter(bwSIFSCsettings);
								pwSIFSCsettings.println("Character_cdata.tsv");
								pwSIFSCsettings.close();
								chknulls = "";
							}
							card_num++;
						}else{
							System.err.println(printlogc +":エラー発生:場所:キャラクターデータ登録タブ:保存するボタン:操作ミス");
							printlogc++;
							tpane.getSelectionModel().select(3);
						}
						//*ファイルを閉じる
						pw.close();
					//* 例外処理
					}catch(FileNotFoundException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:保存するボタン:ファイルが見つからないため出力できません");
						printlogc++;
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(IOException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:保存するボタン:ファイル出力できません");
						printlogc++;
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}
				}
			});
			//fcer はdataconverterで使用されます
			fcer.setInitialDirectory(new File(System.getProperty("user.dir")));
			//datacvtBtn Event settings
			dataconverter.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					try{
						File fcopen = fcer.showOpenDialog(stage);
						if(debuglevel == 1){
							System.out.println("filepath = " + fcopen.getPath());
						}
						if(fcopen != null){
							//maxNum = Card_read.getMaxnum(fcopen);
							Card_datas[] bfcdata = Card_read.dc_cdatas(fcopen);
							maxNum = bfcdata.length;
							if(cdatas.size() != 0){
								cdatas.clear();//addするので、これまでにデータが入ってるとまずい
							}
							if(listtounit.size() != 0){
								listtounit.clear();
							}
							for(int len = 0; len < bfcdata.length; len++){
								cdatas.add(Card_read.one_carddata(bfcdata, len));
								listtounit.add(Card_read.one_carddata(bfcdata, len));
							}
							card_list.setItems(listtounit);
							FileWriter fwSIFSCsettings = new FileWriter(iniDataPath);
							BufferedWriter bwSIFSCsettings = new BufferedWriter(fwSIFSCsettings);
							PrintWriter pwSIFSCsettings = new PrintWriter(bwSIFSCsettings);
							pwSIFSCsettings.println("Character_cdata.tsv");
							pwSIFSCsettings.close();
							System.out.println(printlogc + ":システム通常ログ:");
							printlogc++;
							System.out.println("カードデータを読み込みました:");
							System.out.println("カード数: "+ maxNum);
							Card_read.print_cdata(bfcdata,maxNum);
							card_num = maxNum+1;
							tpane.getSelectionModel().select(3);
						}
					}catch(NullPointerException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:変換するボタン:メソッドエラー");
						printlogc++;
						if(debuglevel >= 1)
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(FileNotFoundException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:変換するボタン:ファイルが見つかりません");
						printlogc++;
						if(debuglevel >= 1)
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(IOException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:変換するボタン:出力できません");
						printlogc++;
						if(debuglevel >= 1)
							System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(FileFormatNotMatchException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:変換するボタン:入力ファイル形式が異なります");
						printlogc++;
						if(debuglevel >= 1){
							System.err.println(e);
						}
						tpane.getSelectionModel().select(3);
					}catch(DataNotFoundException e){
						System.err.println(printlogc + ":例外発生:場所:キャラクターデータ登録タブ:変換するボタン:入力されたファイルからデータが見つかりませんでした。");
						printlogc++;
						if (debuglevel >= 1) {
							System.err.println(e);
						}
						tpane.getSelectionModel().select(3);
					}
				}
			});
//=============================================================================
//タブ１ chara_DataSet END
//=============================================================================

//=============================================================================
//タブ2 calc_SCore START
//=============================================================================
			Tab calc_SCore = new Tab();
			calc_SCore.setClosable(false);
			calc_SCore.setText("楽曲スコア計算");
			StackPane spane_c_s = new StackPane();
			calc_SCore.setContent(spane_c_s);

			GridPane c_SCgroot = new GridPane();//メインのグリッドペイン
			GridPane c_SCguis = new GridPane();//guiのボタンなどを格納するペイン

			c_SCgroot.add(c_SCguis, 0, 1);


			//*チョイスボックス
			/*ChoiceBox<String> music_SEl = new ChoiceBox<>(FXCollections.observableArrayList("僕らのLIVE 君とのLIFE"));*/
			//*楽曲選択コンボボックス
			String[] music_titles = new String[music_list.size()];//これ配列になってるんでListにしようかと思います//元のMusic_data型はListなので
			//ここでfor文回す意味を教えてほしい。
			for(int len = 0;len < music_list.size();len++){
				music_titles[len] = music_list.get(len).getmscnm();
			}
			Label music_SEl_lbl = new Label("楽曲選択:");
			HBox music_SElhb = new HBox();
			ComboBox<String> music_SEl = new ComboBox<String>();
			for(String adder : music_titles){
				music_SEl.getItems().add(adder);
			}
			music_SElhb.getChildren().addAll(music_SEl_lbl,music_SEl);

			Button calc_start = new Button("スコアを計算する");
			//パフェ率の入力ボックスと、探索幅を入力するボックスが必要。

			//パフェ率入力ボックス。intを%に直す形式。
			Label perperlbl = new Label("パーフェクト率:");
			Spinner<Integer> setperper = new Spinner<>(0,100,80);
			setperper.setEditable(true);

			//探索幅入力ボックス。
			Label depthlbl = new Label("発動回数探索の最小値幅:");
			Spinner<Integer> setdepth = new Spinner<>(0,8,4);
			setdepth.setEditable(true);

			//タップアップ値入力ボックス。
			Label tapuplbl = new Label("タップアップ値:");
			Spinner<Double> settapup = new Spinner<Double>(1.00, 1.40, 1.00, 0.01);
			Label tapupendlbl = new Label("倍");
			HBox tapuphbox = new HBox();
			tapuphbox.getChildren().addAll(settapup, tapupendlbl);
			settapup.setEditable(true);

			//プレイ回数を入力するボックス。
			Label playcountlbl = new Label("プレイ回数(最大100000まで):");
			Spinner<Integer> setplaycount = new Spinner<>(1,100000,20);
			setplaycount.setEditable(true);

			//リストビュー(所持カード)
			Label card_listlbl = new Label("全カードリスト");
			Label unitListlbl = new Label("ユニットカードリスト");

			//ユニット解除ボタン
			Button unitdelete = new Button("ユニットクリア");
			unitdelete.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					if(unitList.size() != 0){
						for(Card_datas temp:unitList){
							listtounit.add(temp.gcnum()-1,temp);
						}
						unitList.clear();
						unitListview.setItems(unitList);
						initializeComponents();
					}
				}
			});
			HBox unitguishb = new HBox();
			unitguishb.getChildren().addAll(unitListlbl, unitdelete);
			//ListView<Card_datas> card_list = card_list_view;
			//fillCard_datas(cdatas);
			listtounit = FXCollections.observableArrayList();
			for(int len = 0;len < cdatas.size();len++){
				listtounit.add(cdatas.get(len));
			}
			card_list.setItems(listtounit);
			//リストビュー(ユニット)

			//フレンドの有無チェックボックス
			CheckBox frendcb = new CheckBox();
			Label frcblbl = new Label("フレンドの有無");
			HBox frendguishb = new HBox();
			frendguishb.getChildren().addAll(frcblbl, frendcb);

			//フレンドコンボボックス-> レアリティ,メインセンター,サブセンタースキルを入力
			ComboBox<String> frendrrityselecter = new ComboBox<>();
			Label frrtsellbl = new Label("フレンドのレアリティ");
			String[] frendrrityies = {"SR","SSR","UR"};//Rは未実装
			frendrrityselecter.getItems().addAll(frendrrityies);
			frendrrityselecter.getSelectionModel().select(0);
			HBox frrtselhb = new HBox();
			frrtselhb.getChildren().addAll(frrtsellbl, frendrrityselecter);
			frrtselhb.setVisible(frendcb.isSelected());

			//フレンドのセンタースキル。
			ComboBox<String> frendcenterselecter = new ComboBox<>();
			Label frecenlbl = new Label("フレンドのセンタースキル");
			String[] frendcenterskills = {"ハート","スター","プリンセス","エンジェル","エンプレス"};//スマイルなどの属性は楽曲データから取得できるので不問にできるかと。
			frendcenterselecter.getItems().addAll(frendcenterskills);
			HBox frcenselhb = new HBox();
			frcenselhb.getChildren().addAll(frecenlbl, frendcenterselecter);
			frcenselhb.setVisible(frendcb.isSelected());

			//フレンドのサブセンタースキル
			ComboBox<String> frendsubcenterselecter = new ComboBox<>();
			String[] subcenterlist = {"μ's", "Aqours","1年生","2年生","3年生", "Printemps", "lily white", "BiBi", "CYaRon！", "Guilty Kiss", "AZALEA"};
			frendsubcenterselecter.getItems().addAll(subcenterlist);
			frendsubcenterselecter.getSelectionModel().select(0);
			Label frscselbl = new Label("フレンドのサブセンター");
			HBox frscselhb = new HBox();
			frscselhb.getChildren().addAll(frscselbl, frendsubcenterselecter);
			frscselhb.setVisible(frendcb.isSelected());
			frendcb.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					frrtselhb.setVisible(frendcb.isSelected());
					frcenselhb.setVisible(frendcb.isSelected());
					if (frendrrityselecter.getValue().equals("SR")) {
						frscselhb.setVisible(false);
					} else {
						frscselhb.setVisible(frendcb.isSelected());
					}
				}
			});
			frendrrityselecter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (frendrrityselecter.getValue().equals("SR")) {
						frscselhb.setVisible(false);
					}else{
						frscselhb.setVisible(frendcb.isSelected());
					}
				}
			});

			//*計算用のボタンが押された時のイベント
			calc_start.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					if(unitList.size() == 9){
						try{
							calcMd = Music_data.getMusicdt(music_list, music_SEl.getValue());
							if(debuglevel >= 1){
								System.out.println("calcMd is setted. calcMd value is "+ calcMd.getmscnm());
							}
						}catch(DataNotFoundException e){
							System.err.println(e);
							tpane.getSelectionModel().select(3);
							return;
						}
						for(int len = 0;len < 9;len++){
							unitdt[len] = unitList.get(len);
						}
						if(frendcb.isSelected()){
							frend.srrity(frendrrityselecter.getValue());
							if(frendrrityselecter.getValue().equals("SSR") || frendrrityselecter.getValue().equals("UR")){
								frend.setacskn(frendsubcenterselecter.getValue());
							}else{
								frend.setacskn("Empty");
							}
							frend.setcskin(calcMd.gpprty()+frendcenterselecter.getValue());
						}else{
							frend = null;
						}
						perper = setperper.getValue()/100.0;
						playcount = (int)setplaycount.getValue();
						//System.out.println("playcount is "+playcount);
						tapscrup = (double)settapup.getValue();
						depth = (int)setdepth.getValue();
						cSS.restart();
					}else{
						//エラー処理
						System.err.println(printlogc+":エラー:ユニットが正しくセットできていません。");
						int len = 1;
						if(unitList.size() != 0){
							for(Card_datas err: unitList){
								System.out.println("ユニット"+len+"番目:"+err.getname()+":"+err.grrity()+":"+err.gpprty()+":"+err.getskinm());
								len++;
							}
						}else{
							System.err.println("ユニットが空です。");
						}
						printlogc++;
						tpane.getSelectionModel().select(3);
					}
				}
			});

			c_SCguis.add(music_SElhb, 0, 0);
			c_SCguis.add(perperlbl, 0, 1);
			c_SCguis.add(setperper, 1, 1);
			c_SCguis.add(depthlbl, 0, 2);
			c_SCguis.add(setdepth, 1, 2);
			c_SCguis.add(tapuplbl, 0, 3);
			c_SCguis.add(tapuphbox, 1, 3);
			c_SCguis.add(playcountlbl, 0, 4);
			c_SCguis.add(setplaycount, 1, 4);
			c_SCguis.add(card_listlbl, 0, 5);
			c_SCguis.add(unitguishb, 1, 5);
			c_SCguis.add(card_list, 0, 6);
			c_SCguis.add(unitListview, 1, 6);
			c_SCguis.add(frendguishb, 2, 0);
			c_SCguis.add(frrtselhb, 2, 1);
			c_SCguis.add(frcenselhb, 2, 2);
			c_SCguis.add(frscselhb, 2, 3);
			c_SCguis.add(result_score_label, 2, 4);

//=============================================================================
//タブ2 calc_SCore END
//=============================================================================

//=============================================================================
//タブ3 plot_SCoreGraph START
//=============================================================================
			Tab plot_SCoreGraph = new Tab();
			plot_SCoreGraph.setClosable(false);
			plot_SCoreGraph.setText("楽曲スコア確率分布表示");
			StackPane spane_p_sg = new StackPane();
			plot_SCoreGraph.setContent(spane_p_sg);
			/*tpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
				public void changed(ObservableValue<? extends Tab>ov, Tab t, Tab t1){

				}
			});*/
			//グリッドパネ設定
			//右側をグラフ表示、左側をボタンやコンボボックス等の操作画面とする。
			GridPane groot = new GridPane();
			GridPane gguis = new GridPane();

			//グラフ表示部
			NumberAxis xAxis = new NumberAxis();
			NumberAxis yAxis = new NumberAxis();
			LineChart<Number,Number> plot = new LineChart<Number,Number>(xAxis, yAxis);

			//操作画面部
			//ComboBox settings
			ComboBox<String> aLcb = new ComboBox<>();
			ComboBox<String> bLcb = new ComboBox<>();
			ComboBox<String> cLcb = new ComboBox<>();
			ComboBox<String> dLcb = new ComboBox<>();
			ComboBox<String> eLcb = new ComboBox<>();
			ComboBox<String> fLcb = new ComboBox<>();
			ComboBox<String> gLcb = new ComboBox<>();
			ComboBox<String> hLcb = new ComboBox<>();
			ComboBox<String> iLcb = new ComboBox<>();
			//Label settings
			Label aLane = new Label("aレーン:");
			Label bLane = new Label("bレーン:");
			Label cLane = new Label("cレーン:");
			Label dLane = new Label("dレーン:");
			Label eLane = new Label("eレーン(センター):");//center
			Label fLane = new Label("fレーン:");
			Label gLane = new Label("gレーン:");
			Label hLane = new Label("hレーン:");
			Label iLane = new Label("iレーン:");
			//Boolean
			Boolean unitbl = false;

			Button readBtn = new Button("カードデータ読み込み");
			readBtn.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					try{
						Card_datas.toStringCard_datas(cdatas, aLcb);
						Card_datas.toStringCard_datas(cdatas, bLcb);
						Card_datas.toStringCard_datas(cdatas, cLcb);
						Card_datas.toStringCard_datas(cdatas, dLcb);
						Card_datas.toStringCard_datas(cdatas, eLcb);
						Card_datas.toStringCard_datas(cdatas, fLcb);
						Card_datas.toStringCard_datas(cdatas, gLcb);
						Card_datas.toStringCard_datas(cdatas, hLcb);
						Card_datas.toStringCard_datas(cdatas, iLcb);
					}catch(DataNotFoundException e){
						System.err.println(printlogc + ":読み込むデータがありません。");
						if(debuglevel >= 1)System.err.println(e);
						printlogc++;
						tpane.getSelectionModel().select(3);
					}
				}
			});
			aLcb.setOnMouseClicked(event -> chkclc = true);
			aLcb.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					if(chkclc == true){
						Card_datas.setGuitoUnit(unitdt[0], cdatas, aLcb);
						chkclc = false;
					}
				}
			});
			/*Button setUnitBtn = new Button("ユニット編成");
			setUnitBtn.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					unitbl = true;
					aLcb.getValue();
				}
			});*/
			Button plotBtn = new Button("グラフ表示");


			BorderPane root_p_sg = new BorderPane();
			root_p_sg.setCenter(aLcb);
			root_p_sg.setBottom(readBtn);
			spane_p_sg.getChildren().addAll(root_p_sg);
//=============================================================================
//タブ3 plot_SCoreGraph END
//=============================================================================

//=============================================================================
//タブ4 system_log START
//=============================================================================
			Tab system_log = new Tab();
			system_log.setClosable(false);
			String slTitle = "システムログ";
			system_log.setText(slTitle);
			StackPane syslogsp = new StackPane();
			system_log.setContent(syslogsp);

			TextArea txa_Log = new TextArea();
			txa_Log.setEditable(false);
			Button resetBtn = new Button("リセットボタン");
			redirectConsole(txa_Log, resetBtn);//->Event settings
//=============================================================================
//タブ4 system_log END
//=============================================================================
		BorderPane root_c_d = new BorderPane();
		setAnyThings.getChildren().addAll(chara_Sel, setRrity, setPprty, chara_SKills, setAwakes, lblsislt, setSislt, lblskilv, setSkilv, detailsk, setcpprty,/*setSksha,*/ /*skshalbl, rtpsk,*/ /*setRtpsk,*/ dataconverter);
		root_c_d.setCenter(setAnyThings);
		root_c_d.setBottom(saver);

		BorderPane root_c_s = new BorderPane();
		root_c_s.setCenter(c_SCgroot);
		root_c_s.setBottom(calc_start);

		BorderPane root_s_l = new BorderPane();
		root_s_l.setCenter(txa_Log);
		root_s_l.setBottom(resetBtn);

		spane_c_d.getChildren().addAll(root_c_d);
		spane_c_s.getChildren().addAll(root_c_s);
		syslogsp.getChildren().addAll(root_s_l);

		tpane.getSelectionModel().select(0);
		tpane.getTabs().addAll(chara_DataSet, calc_SCore, plot_SCoreGraph, system_log);

		spane.getChildren().add(tpane);
		Scene sroot = new Scene(spane);

		stage.setTitle("SIF_Score_Calc");
		stage.setScene(sroot);
		stage.show();

		txa_Log.lookup(".content").setStyle("-fx-background-color:#eeeeee");
		if(cdatas.size() == 0 && chknulls == null){
			System.out.println(printlogc + ":システム通常ログ:");
			printlogc++;
			System.out.println("読み込みデータ無し(初期状態)");
			tpane.getSelectionModel().select(3);
			chknulls = "first";
		}else if(printlogc == -1){
			printlogc = 1;
			System.err.println(printlogc + ":エラー発生:起動時エラー:データ読み込み:アプリ動作エラー:NullPointerException");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -2){
			printlogc = 1;
			System.err.println(printlogc + ":エラー発生:起動時エラー:データ読み込み:ファイル読み込みエラー:ファイルが見つかりません");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -3){
			printlogc = 1;
			System.err.println(printlogc + ":エラー発生:起動時エラー:データ読み込み:アプリ動作エラー:IOException");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -4){
			printlogc = 1;
			System.out.println(printlogc + ":*デバッグモード:初期読み込み済みスキルデータ数:" + sdata.size());
			System.out.println("sdata.size() = " + sdata.size());
			printlogc++;
			tpane.getSelectionModel().select(3);
		}
	}

	private static class CalcScoreService extends Service<Boolean>{
		//スコア計算するスレッド.
		@Override
			protected Task<Boolean> createTask(){
				return new calcTask();
			}

	};
	private static class calcTask extends Task<Boolean>{
		@Override
		protected Boolean call() throws Exception{
			//スコア計算スレッドでの実際の処理.
			//Platform.runLater(() ->System.out.println("Thread getted playcount is"+playcount));
			calc_result = Calc_data.calcscrmain(unitdt, calcMd, frend, perper, playcount, tapscrup, depth);
			int resultint = calc_result.getskill_up_score() + calc_result.getbase_score();
			/*Platform.runLater(() -> System.out.println("スキルアップスコア:"+calc_result.getskill_up_score()));
			Platform.runLater(()-> System.out.println("ベーススコア:"+ calc_result.getbase_score()));
			Platform.runLater(() -> System.out.println("発生確率:"+calc_result.getregular_probably()));
			for(Card_datas temp:unit){
				Platform.runLater(() -> System.out.println(temp.getname()+":"+temp.gactcnt()));
			}*/
			result_score_str = String.valueOf(resultint);
			result_score_str = "スコア期待値:"+result_score_str;
			Platform.runLater(() -> result_score_label.setText(result_score_str));

			return true;
		}
	}

	private void initializeListeners() {
		//http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm
		//上記のサイトを参考にした。

		//card_list -> unitListview.
		card_list.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (card_list.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				Dragboard dragBoard = card_list.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				Card_datas temp = card_list.getSelectionModel().getSelectedItem();
				content.putString(temp.gcnum() + "," + temp.getname() + "," + temp.gpprty() + "," + temp.grrity() + ","
						+ temp.getskinm() + "," + temp.gsawk() + "," + temp.gsislt() + "," + temp.gskilv() + ","
						+ temp.gsksha() + "," + temp.gskitp() + "," + temp.gfactv() + "," + temp.gprob() + ","
						+ temp.gaccut() + "," + temp.gefsz() + "," + temp.gcsm() + "," + temp.gcpr() + "," + temp.gccl()
						+ "," + temp.getcskin() + "," + temp.getacskn() + "," + temp.gpkiss() + "," + temp.gppfm() + ","
						+ temp.gpring() + "," + temp.gpcross() + "," + temp.gpaura() + "," + temp.gpveil() + ","
						+ temp.gpcharm() + "," + temp.gpheal() + "," + temp.gptrick() + "," + temp.gpwink() + ","
						+ temp.gpimage() + "," + temp.gpbloom() + "," + temp.gptrill() + "," + temp.gpnnet());
				dragBoard.setContent(content);
				ctou = true;
			}
		});
		unitListview.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (ctou == true) {
					event.acceptTransferModes(TransferMode.MOVE);
				}
			}
		});
		unitListview.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (ctou == true) {
					String getcard_data = event.getDragboard().getString();
					//System.out.println(getcard_data);
					String[] temp = getcard_data.split(",", 0);
					int cnum = Integer.parseInt(temp[0]);
					String name = temp[1];
					String pprty = temp[2];
					String rrity = temp[3];
					String skinm = temp[4];
					boolean awake = Card_datas.chkrawake(temp[5]);
					int sislt = Integer.parseInt(temp[6]);
					int skilv = Integer.parseInt(temp[7]);
					String sksha = temp[8];
					String skitp = temp[9];
					int factv = Integer.parseInt(temp[10]);
					int prob = Integer.parseInt(temp[11]);
					double accut = Double.parseDouble(temp[12]);
					int efsz = Integer.parseInt(temp[13]);
					int csm = Integer.parseInt(temp[14]);
					int cpr = Integer.parseInt(temp[15]);
					int ccl = Integer.parseInt(temp[16]);
					String cskin = temp[17];
					String acskn = temp[18];
					int pkiss = Integer.parseInt(temp[19]);
					int ppfm = Integer.parseInt(temp[20]);
					int pring = Integer.parseInt(temp[21]);
					int pcross = Integer.parseInt(temp[22]);
					int paura = Integer.parseInt(temp[23]);
					int pveil = Integer.parseInt(temp[24]);
					int pcharm = Integer.parseInt(temp[25]);
					int pheal = Integer.parseInt(temp[26]);
					int ptrick = Integer.parseInt(temp[27]);
					int pwink = Integer.parseInt(temp[28]);
					int pimage = Integer.parseInt(temp[29]);
					int pbloom = Integer.parseInt(temp[30]);
					int ptrill = Integer.parseInt(temp[31]);
					int pnnet = Integer.parseInt(temp[32]);
					unitList.addAll(new Card_datas(cnum, name, pprty, rrity, skinm, awake, sislt, skilv, sksha, skitp,
							factv, prob, accut, efsz, csm, cpr, ccl, cskin, acskn, pkiss, ppfm, pring, pcross, paura,
							pveil, pcharm, pheal, ptrick, pwink, pimage, pbloom, ptrill, pnnet));
					unitListview.setItems(unitList);
					listtounit.remove(card_list.getSelectionModel().getSelectedItem());
					card_list.setItems(listtounit);
					event.setDropCompleted(true);
					ctou = false;
					initializeComponents();
				}
			}
		});

		//unitListview -> card_list
		unitListview.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Dragboard dragBoard = unitListview.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				if (unitListview.getSelectionModel().getSelectedItem() != null) {
					Card_datas temp = unitListview.getSelectionModel().getSelectedItem();
					content.putString(temp.gcnum() + "," + temp.getname() + "," + temp.gpprty() + "," + temp.grrity()
							+ "," + temp.getskinm() + "," + temp.gsawk() + "," + temp.gsislt() + "," + temp.gskilv()
							+ "," + temp.gsksha() + "," + temp.gskitp() + "," + temp.gfactv() + "," + temp.gprob() + ","
							+ temp.gaccut() + "," + temp.gefsz() + "," + temp.gcsm() + "," + temp.gcpr() + ","
							+ temp.gccl() + "," + temp.getcskin() + "," + temp.getacskn() + "," + temp.gpkiss() + ","
							+ temp.gppfm() + "," + temp.gpring() + "," + temp.gpcross() + "," + temp.gpaura() + ","
							+ temp.gpveil() + "," + temp.gpcharm() + "," + temp.gpheal() + "," + temp.gptrick() + ","
							+ temp.gpwink() + "," + temp.gpimage() + "," + temp.gpbloom() + "," + temp.gptrill() + ","
							+ temp.gpnnet());
					dragBoard.setContent(content);
					utoc = true;
				} else {
					System.err.println("表示バグによりNullPointerExceptionが発生しました。");
					unitListview.getSelectionModel()
							.clearSelection(unitListview.getSelectionModel().getSelectedIndex());
					initializeComponents();
				}
			}
		});

		card_list.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (utoc == true) {
					event.acceptTransferModes(TransferMode.MOVE);
				}
			}
		});

		card_list.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (utoc == true) {
					int index = unitListview.getSelectionModel().getSelectedIndex();
					String getcard_data = event.getDragboard().getString();
					//System.out.println(getcard_data);
					String[] temp = getcard_data.split(",", 0);
					int cnum = Integer.parseInt(temp[0]);
					String name = temp[1];
					String pprty = temp[2];
					String rrity = temp[3];
					String skinm = temp[4];
					boolean awake = Card_datas.chkrawake(temp[5]);
					int sislt = Integer.parseInt(temp[6]);
					int skilv = Integer.parseInt(temp[7]);
					String sksha = temp[8];
					String skitp = temp[9];
					int factv = Integer.parseInt(temp[10]);
					int prob = Integer.parseInt(temp[11]);
					double accut = Double.parseDouble(temp[12]);
					int efsz = Integer.parseInt(temp[13]);
					int csm = Integer.parseInt(temp[14]);
					int cpr = Integer.parseInt(temp[15]);
					int ccl = Integer.parseInt(temp[16]);
					String cskin = temp[17];
					String acskn = temp[18];
					int pkiss = Integer.parseInt(temp[19]);
					int ppfm = Integer.parseInt(temp[20]);
					int pring = Integer.parseInt(temp[21]);
					int pcross = Integer.parseInt(temp[22]);
					int paura = Integer.parseInt(temp[23]);
					int pveil = Integer.parseInt(temp[24]);
					int pcharm = Integer.parseInt(temp[25]);
					int pheal = Integer.parseInt(temp[26]);
					int ptrick = Integer.parseInt(temp[27]);
					int pwink = Integer.parseInt(temp[28]);
					int pimage = Integer.parseInt(temp[29]);
					int pbloom = Integer.parseInt(temp[30]);
					int ptrill = Integer.parseInt(temp[31]);
					int pnnet = Integer.parseInt(temp[32]);
					card_list.getItems().add(cnum - 1,
							new Card_datas(cnum, name, pprty, rrity, skinm, awake, sislt, skilv, sksha, skitp, factv,
									prob, accut, efsz, csm, cpr, ccl, cskin, acskn, pkiss, ppfm, pring, pcross, paura,
									pveil, pcharm, pheal, ptrick, pwink, pimage, pbloom, ptrill, pnnet));
					unitList.remove(index);
					unitListview.refresh();
					unitListview.setItems(unitList);
					event.setDropCompleted(true);
					utoc = false;
					initializeComponents();
				}
			}
		});
	}

	private void initializeComponents() {
		initializeListView(card_list);
		initializeunitListView(unitListview);
	}

	private void initializeListView(ListView<Card_datas> listView) {
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView.setCellFactory(new Callback<ListView<Card_datas>, ListCell<Card_datas>>() {
			@Override
			public ListCell<Card_datas> call(ListView<Card_datas> temp) {
				return new carddatatoCell();
			}
		});
	}

	private void initializeunitListView(ListView<Card_datas> listView) {
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView.setCellFactory(new Callback<ListView<Card_datas>, ListCell<Card_datas>>() {
			@Override
			public ListCell<Card_datas> call(ListView<Card_datas> temp) {
				return new unitdatatoCell();
			}
		});
	}

	static class unitdatatoCell extends ListCell<Card_datas> {
		@Override
		protected void updateItem(Card_datas card, boolean empty) {
			super.updateItem(card, empty);
			if (!empty) {
				if (card == null || empty || card.getunitnm().equals("empty") || card.getgrade().equals("empty")
						|| card.getsubuntnm().equals("empty")) {
					setText("");
					return;
				}
				setText(card.grrity() + ":" + card.gpprty() + ":" + card.getname() + ":" + card.getskinm());
				if (card.gpprty().equals("スマイル")) {
					setStyle("-fx-background-color:#ffecec");
				} else if (card.gpprty().equals("ピュア")) {
					setStyle("-fx-background-color:#ecffec");
				} else if (card.gpprty().equals("クール")) {
					setStyle("-fx-background-color:#ececff");
				} else {
					setStyle("-fx-background-color:#eeeeee");
				}
				setTooltip(new Tooltip("効果:" + Card_datas.setskilltext(card) + "\n\n" + "装備済みキッス:" + card.gpkiss()
						+ "個\t" + "装備済みパフューム:" + card.gppfm() + "個\n" + "装備済みリング:" + card.gpring() + "個\t" + "装備済みクロス:"
						+ card.gpcross() + "個\n" + "装備済みオーラ:" + card.gpaura() + "個\t" + "装備済みヴェール:" + card.gpveil()
						+ "個\n" + "装備済みチャーム:" + card.gpcharm() + "個\t" + "装備済みヒール:" + card.gpheal() + "個\n"
						+ "装備済みトリック:" + card.gptrick() + "個\n" + "装備済み" + Card_datas.getSISimagename(card) + ":"
						+ card.gpimage() + "個\n" + "装備済みノネット:" + card.gpnnet() + "個\t" + "装備済みウィンク:" + card.gpwink()
						+ "個\n" + "装備済みトリル:" + card.gptrill() + "個\t" + "装備済みブルーム:" + card.gpbloom() + "個"));
				/*setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
							Stage setSIS = new Stage();
							setSIS.initModality(Modality.APPLICATION_MODAL);
							setSIS.initOwner(tmpstage);
							setSIS.setMaxWidth(500);
							GridPane setSISgroot = new GridPane();
							Label setSISlbl = new Label("SIS設定画面");
							Label explainlbl = new Label("装備しているSISにチェックを入れてください。");
							VBox lbls = new VBox();
							lbls.getChildren().addAll(setSISlbl, explainlbl);

							CheckBox setkiss = new CheckBox();
							if (card.gpkiss() != 0) {
								setkiss.setSelected(true);
							}
							Label setkisslbl = new Label("キッス:");
							HBox setkisshb = new HBox();
							setkisshb.getChildren().addAll(setkisslbl, setkiss);

							CheckBox setppfm = new CheckBox();
							if (card.gppfm() != 0) {
								setppfm.setSelected(true);
							}
							Label setppfmlbl = new Label("パフューム:");
							HBox setppfmhb = new HBox();
							setppfmhb.getChildren().addAll(setppfmlbl, setppfm);

							CheckBox setring = new CheckBox();
							if (card.gpring() != 0) {
								setring.setSelected(true);
							}
							Label setringlbl = new Label("リング:");
							HBox setringhb = new HBox();
							setringhb.getChildren().addAll(setringlbl, setring);

							CheckBox setcross = new CheckBox();
							if (card.gpcross() != 0) {
								setcross.setSelected(true);
							}
							Label setcrosslbl = new Label("クロス:");
							HBox setcrosshb = new HBox();
							setcrosshb.getChildren().addAll(setcrosslbl, setcross);

							CheckBox setaura = new CheckBox();
							if (card.gpaura() != 0) {
								setaura.setSelected(true);
							}
							Label setauralbl = new Label("オーラ:");
							HBox setaurahb = new HBox();
							setaurahb.getChildren().addAll(setauralbl, setaura);

							CheckBox setveil = new CheckBox();
							if (card.gpveil() != 0) {
								setveil.setSelected(true);
							}
							Label setveillbl = new Label("ヴェール:");
							HBox setveilhb = new HBox();
							setveilhb.getChildren().addAll(setveillbl, setveil);

							CheckBox setcharm = new CheckBox();
							if (card.gpcharm() != 0) {
								setcharm.setSelected(true);
							}
							Label setcharmlbl = new Label("チャーム:");
							HBox setcharmhb = new HBox();
							setcharmhb.getChildren().addAll(setcharmlbl, setcharm);

							CheckBox setheal = new CheckBox();
							if (card.gpheal() != 0) {
								setheal.setSelected(true);
							}
							Label setheallbl = new Label("ヒール:");
							HBox sethealhb = new HBox();
							sethealhb.getChildren().addAll(setheallbl, setheal);

							CheckBox settrick = new CheckBox();
							if (card.gptrick() != 0) {
								settrick.setSelected(true);
							}
							Label settricklbl = new Label("トリック:");
							HBox settrickhb = new HBox();
							settrickhb.getChildren().addAll(settricklbl, settrick);

							CheckBox setimage = new CheckBox();
							if (card.gpimage() != 0) {
								setimage.setSelected(true);
							}
							Label setimagelbl = new Label(Card_datas.getSISimagename(card) + ":");
							HBox setimagehb = new HBox();
							setimagehb.getChildren().addAll(setimagelbl, setimage);

							CheckBox setnonette = new CheckBox();
							if (card.gpnnet() != 0) {
								setnonette.setSelected(true);
							}
							Label setnonettelbl = new Label("ノネット:");
							HBox setnonettehb = new HBox();
							setnonettehb.getChildren().addAll(setnonettelbl, setnonette);

							CheckBox setwink = new CheckBox();
							if (card.gpwink() != 0) {
								setwink.setSelected(true);
							}
							Label setwinklbl = new Label("ウインク:");
							HBox setwinkhb = new HBox();
							setwinkhb.getChildren().addAll(setwinklbl, setwink);

							CheckBox settrill = new CheckBox();
							if (card.gptrill() != 0) {
								settrill.setSelected(true);
							}
							Label settrilllbl = new Label("トリル:");
							HBox settrillhb = new HBox();
							settrillhb.getChildren().addAll(settrilllbl, settrill);

							CheckBox setbloom = new CheckBox();
							if (card.gpbloom() != 0) {
								setbloom.setSelected(true);
							}
							Label setbloomlbl = new Label("ブルーム:");
							HBox setbloomhb = new HBox();
							setbloomhb.getChildren().addAll(setbloomlbl, setbloom);

							Button sissaver = new Button("SIS設定を保存して閉じる");
							sissaver.setOnAction(new EventHandler<ActionEvent>() {
								public void handle(ActionEvent event) {
									if (setkiss.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gppfm();
												temp.spkiss(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpkiss()) {
													System.out.println("キッスをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":キッス装備数:"
															+ cdatas.get(len).gpkiss());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpkiss();
												temp.spkiss(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpkiss()) {
													System.out.println("キッスのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":キッス装備数:"
															+ cdatas.get(len).gpkiss());
												}
											}
										}
									}
									if (setppfm.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gppfm();
												temp.sppfm(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (temp.gppfm() != getNowdata) {
													System.out.println("パフュームをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":パフューム装備数:"
															+ cdatas.get(len).gppfm());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gppfm();
												temp.sppfm(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gppfm()) {
													System.out.println("パフュームのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":パフューム装備数:"
															+ cdatas.get(len).gppfm());
												}
											}
										}
									}
									if (setring.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpring();
												temp.spring(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpring()) {
													System.out.println("リングをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":リング装備数:"
															+ cdatas.get(len).gpring());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpring();
												temp.spring(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpring()) {
													System.out.println("リングのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":リング装備数:"
															+ cdatas.get(len).gpring());
												}
											}
										}
									}
									if (setcross.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcross();
												temp.spcross(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpcross()) {
													System.out.println("クロスをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":クロス装備数:"
															+ cdatas.get(len).gpcross());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcross();
												temp.spcross(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpcross()) {
													System.out.println("クロスのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":クロス装備数:"
															+ cdatas.get(len).gpcross());
												}
											}
										}
									}
									if (setaura.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpaura();
												temp.spaura(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpaura()) {
													System.out.println("オーラをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":オーラ装備数:"
															+ cdatas.get(len).gpaura());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpaura();
												temp.spaura(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpaura()) {
													System.out.println("オーラのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":オーラ装備数:"
															+ cdatas.get(len).gpaura());
												}
											}
										}
									}
									if (setveil.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpveil();
												temp.spveil(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpveil()) {
													System.out.println("ヴェールをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ヴェール装備数:"
															+ cdatas.get(len).gpveil());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpveil();
												temp.spveil(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpveil()) {
													System.out.println("ヴェールのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ヴェール装備数:"
															+ cdatas.get(len).gpveil());
												}
											}
										}
									}
									if (setcharm.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcharm();
												temp.spcharm(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpcharm()) {
													System.out.println("チャームをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":チャーム装備数:"
															+ cdatas.get(len).gpcharm());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcharm();
												temp.spcharm(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpcharm()) {
													System.out.println("チャームのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":チャーム装備数:"
															+ cdatas.get(len).gpcharm());
												}
											}
										}
									}
									if (setheal.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpheal();
												temp.spheal(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpheal()) {
													System.out.println("ヒールをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ヒール装備数:"
															+ cdatas.get(len).gpheal());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpheal();
												temp.spheal(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpheal()) {
													System.out.println("ヒールのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ヒール装備数:"
															+ cdatas.get(len).gpheal());
												}
											}
										}
									}
									if (settrick.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrick();
												temp.sptrick(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gptrick()) {
													System.out.println("トリックをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":トリック装備数:"
															+ cdatas.get(len).gptrick());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrick();
												temp.sptrick(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gptrick()) {
													System.out.println("トリックのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":トリック装備数:"
															+ cdatas.get(len).gptrick());
												}
											}
										}
									}
									if (setimage.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpimage();
												temp.spimage(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpimage()) {
													System.out.println(Card_datas.getSISimagename(card) + "をセットしました。");
													System.out.println(cdatas.get(len).getname() + ":"
															+ Card_datas.getSISimagename(card) + "装備数:"
															+ cdatas.get(len).gpimage());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpimage();
												temp.spimage(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpimage()) {
													System.out
															.println(Card_datas.getSISimagename(card) + "のセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":"
															+ Card_datas.getSISimagename(card) + "装備数:"
															+ cdatas.get(len).gpimage());
												}
											}
										}
									}
									if (setnonette.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpnnet();
												temp.spnnet(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpnnet()) {
													System.out.println("ノネットをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ノネット装備数:"
															+ cdatas.get(len).gpnnet());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpnnet();
												temp.spnnet(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpnnet()) {
													System.out.println("ノネットのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ノネット装備数:"
															+ cdatas.get(len).gpnnet());
												}
											}
										}
									}
									if (setwink.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpwink();
												temp.spwink(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpwink()) {
													System.out.println("ウインクをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ウインク装備数:"
															+ cdatas.get(len).gpwink());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpwink();
												temp.spwink(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpwink()) {
													System.out.println("ウインクのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ウインク装備数:"
															+ cdatas.get(len).gpwink());
												}
											}
										}
									}
									if (settrill.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrill();
												temp.sptrill(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gptrill()) {
													System.out.println("トリルをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":トリル装備数:"
															+ cdatas.get(len).gptrill());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrill();
												temp.sptrill(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gptrill()) {
													System.out.println("トリルのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":トリル装備数:"
															+ cdatas.get(len).gptrill());
												}
											}
										}
									}
									if (setbloom.isSelected()) {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpbloom();
												temp.spbloom(1);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpbloom()) {
													System.out.println("ブルームをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ブルーム装備数:"
															+ cdatas.get(len).gpbloom());
												}
											}
										}
									} else {
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpbloom();
												temp.spbloom(0);
												cdatas.set(len, temp);
												unitList.set(len, temp);
												unitListview.setItems(unitList);
												if (getNowdata != temp.gpbloom()) {
													System.out.println("ブルームのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ブルーム装備数:"
															+ cdatas.get(len).gpbloom());
												}
											}
										}
									}
									//System.out.println("リストをスライドして、一度更新するとリストに反映されます。");
									//tpane.getSelectionModel().select(3);
									setSIS.close();
								}
							});
							setSISgroot.add(sissaver, 0, 0);
							setSISgroot.add(lbls, 0, 1);
							setSISgroot.add(setkisshb, 0, 2);
							setSISgroot.add(setppfmhb, 1, 2);
							setSISgroot.add(setringhb, 0, 3);
							setSISgroot.add(setcrosshb, 1, 3);
							setSISgroot.add(setaurahb, 0, 4);
							setSISgroot.add(setveilhb, 1, 4);
							setSISgroot.add(setcharmhb, 0, 5);
							setSISgroot.add(sethealhb, 1, 5);
							setSISgroot.add(settrickhb, 0, 6);
							setSISgroot.add(setimagehb, 1, 6);
							setSISgroot.add(setnonettehb, 0, 7);
							setSISgroot.add(setwinkhb, 1, 7);
							setSISgroot.add(settrillhb, 0, 8);
							setSISgroot.add(setbloomhb, 1, 8);
							Scene setSISscene = new Scene(setSISgroot);
							setSIS.setScene(setSISscene);
							setSIS.setTitle(card.grrity() + card.getname() + "SIS設定");
							setSIS.show();
						}
					}
				});*/
			}
		}
	}

	static class carddatatoCell extends ListCell<Card_datas> {
		@Override
		protected void updateItem(Card_datas card, boolean empty) {
			super.updateItem(card, empty);
			if (!empty) {
				if(card == null || empty || card.getunitnm().equals("empty") || card.getgrade().equals("empty") || card.getsubuntnm().equals("empty")){
					setText("");
					return;
				}
				setText(card.grrity() + ":" + card.gpprty() + ":" + card.getname() + ":" + card.getskinm());
				if(card.gpprty().equals("スマイル")){
					setStyle("-fx-background-color:#ffecec");
				}else if(card.gpprty().equals("ピュア")){
					setStyle("-fx-background-color:#ecffec");
				}else if(card.gpprty().equals("クール")){
					setStyle("-fx-background-color:#ececff");
				}else{
					setStyle("-fx-background-color:#eeeeee");
				}

				setTooltip(new Tooltip("効果:" + Card_datas.setskilltext(card) + "\n\n"
						+ "装備済みキッス:" + card.gpkiss() + "個\t"
						+ "装備済みパフューム:" + card.gppfm() + "個\n"
						+ "装備済みリング:" + card.gpring() + "個\t"
						+ "装備済みクロス:" + card.gpcross() + "個\n"
						+ "装備済みオーラ:" + card.gpaura() + "個\t"
						+ "装備済みヴェール:" + card.gpveil() + "個\n"
						+ "装備済みチャーム:" + card.gpcharm() + "個\t"
						+ "装備済みヒール:" + card.gpheal() + "個\n"
						+ "装備済みトリック:" + card.gptrick() + "個\n"
						+ "装備済み"+ Card_datas.getSISimagename(card)+ ":" + card.gpimage() + "個\n"
						+ "装備済みノネット:" + card.gpnnet() + "個\t"
						+ "装備済みウィンク:" + card.gpwink() + "個\n"
						+ "装備済みトリル:" + card.gptrill() + "個\t"
						+ "装備済みブルーム:" + card.gpbloom() + "個\n\n"
						+ "ダブルクリックでSISを修正"));
				setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
							Stage setSIS = new Stage(/*StageStyle.TRANSPARENT*/);
							setSIS.initModality(Modality.APPLICATION_MODAL);
							setSIS.initOwner(tmpstage);
							setSIS.setMaxWidth(500);
							GridPane setSISgroot = new GridPane();
							//setSISgroot.setStyle("-fx-background-color: rgba(0,0,0,0.5);-fx-text-background-color:white");
							Label setSISlbl = new Label("SIS設定画面");
							Label explainlbl = new Label("装備しているSISにチェックを入れてください。");
							VBox lbls = new VBox();
							lbls.getChildren().addAll(setSISlbl, explainlbl);

							CheckBox setkiss = new CheckBox();
							if(card.gpkiss() != 0){
								setkiss.setSelected(true);
							}
							Label setkisslbl = new Label("キッス:");
							HBox setkisshb = new HBox();
							setkisshb.getChildren().addAll(setkisslbl,setkiss);

							CheckBox setppfm = new CheckBox();
							if(card.gppfm() != 0){
								setppfm.setSelected(true);
							}
							Label setppfmlbl = new Label("パフューム:");
							HBox setppfmhb = new HBox();
							setppfmhb.getChildren().addAll(setppfmlbl, setppfm);

							CheckBox setring = new CheckBox();
							if(card.gpring() != 0){
								setring.setSelected(true);
							}
							Label setringlbl = new Label("リング:");
							HBox setringhb = new HBox();
							setringhb.getChildren().addAll(setringlbl, setring);

							CheckBox setcross = new CheckBox();
							if(card.gpcross() != 0){
								setcross.setSelected(true);
							}
							Label setcrosslbl = new Label("クロス:");
							HBox setcrosshb = new HBox();
							setcrosshb.getChildren().addAll(setcrosslbl, setcross);

							CheckBox setaura = new CheckBox();
							if(card.gpaura() != 0){
								setaura.setSelected(true);
							}
							Label setauralbl = new Label("オーラ:");
							HBox setaurahb = new HBox();
							setaurahb.getChildren().addAll(setauralbl, setaura);

							CheckBox setveil = new CheckBox();
							if(card.gpveil() != 0){
								setveil.setSelected(true);
							}
							Label setveillbl = new Label("ヴェール:");
							HBox setveilhb = new HBox();
							setveilhb.getChildren().addAll(setveillbl, setveil);

							CheckBox setcharm = new CheckBox();
							if(card.gpcharm() != 0){
								setcharm.setSelected(true);
							}
							Label setcharmlbl = new Label("チャーム:");
							HBox setcharmhb = new HBox();
							setcharmhb.getChildren().addAll(setcharmlbl, setcharm);
							setcharmhb.setVisible(card.gsksha().equals("スコア"));


							CheckBox setheal = new CheckBox();
							if(card.gpheal() != 0){
								setheal.setSelected(true);
							}
							Label setheallbl = new Label("ヒール:");
							HBox sethealhb = new HBox();
							sethealhb.getChildren().addAll(setheallbl, setheal);
							sethealhb.setVisible(card.gsksha().equals("回復"));

							CheckBox settrick = new CheckBox();
							if(card.gptrick() != 0){
								settrick.setSelected(true);
							}
							Label settricklbl = new Label("トリック:");
							HBox settrickhb = new HBox();
							settrickhb.getChildren().addAll(settricklbl, settrick);
							settrickhb.setVisible(card.gsksha().equals("判定"));

							CheckBox setimage = new CheckBox();
							if(card.gpimage() != 0){
								setimage.setSelected(true);
							}
							Label setimagelbl = new Label(Card_datas.getSISimagename(card)+":");
							HBox setimagehb = new HBox();
							setimagehb.getChildren().addAll(setimagelbl, setimage);

							CheckBox setnonette = new CheckBox();
							if(card.gpnnet() != 0){
								setnonette.setSelected(true);
							}
							Label setnonettelbl = new Label("ノネット:");
							HBox setnonettehb = new HBox();
							setnonettehb.getChildren().addAll(setnonettelbl, setnonette);

							CheckBox setwink = new CheckBox();
							if(card.gpwink() != 0){
								setwink.setSelected(true);
							}
							Label setwinklbl = new Label("ウィンク:");
							HBox setwinkhb = new HBox();
							setwinkhb.getChildren().addAll(setwinklbl, setwink);

							CheckBox settrill = new CheckBox();
							if(card.gptrill() != 0){
								settrill.setSelected(true);
							}
							Label settrilllbl = new Label("トリル:");
							HBox settrillhb = new HBox();
							settrillhb.getChildren().addAll(settrilllbl, settrill);

							CheckBox setbloom = new CheckBox();
							if(card.gpbloom() != 0){
								setbloom.setSelected(true);
							}
							Label setbloomlbl = new Label("ブルーム:");
							HBox setbloomhb = new HBox();
							setbloomhb.getChildren().addAll(setbloomlbl, setbloom);

							Button sissaver = new Button("SIS設定を保存して閉じる");
							sissaver.setOnAction(new EventHandler<ActionEvent>() {
								public void handle(ActionEvent event) {
									if(setkiss.isSelected()){
										for(int len = 0;len < cdatas.size();len++){
											if(card.equals(cdatas.get(len))){
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpkiss();
												temp.spkiss(1);
												cdatas.get(len).spkiss(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spkiss(1);
													}
												}
												card_list.setItems(listtounit);
												if(getNowdata != temp.gpkiss()){
													System.out.println("キッスをセットしました。");
													System.out.println(cdatas.get(len).getname() +":キッス装備数:"+ cdatas.get(len).gpkiss());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpkiss();
												temp.spkiss(0);
												cdatas.get(len).spkiss(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spkiss(0);
													}
												}
												card_list.setItems(listtounit);
												if(getNowdata != temp.gpkiss()){
													System.out.println("キッスのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":キッス装備数:" + cdatas.get(len).gpkiss());
												}
											}
										}
									}
									if(setppfm.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gppfm();
												temp.sppfm(1);
												cdatas.get(len).sppfm(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).sppfm(1);
													}
												}
												card_list.setItems(listtounit);
												if(temp.gppfm() != getNowdata){
													System.out.println("パフュームをセットしました。");
													System.out.println(
															cdatas.get(len).getname() + ":パフューム装備数:" + cdatas.get(len).gppfm());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gppfm();
												temp.sppfm(0);
												cdatas.get(len).sppfm(0);
												for(int k = 0;k < listtounit.size(); k++){
													listtounit.get(k).sppfm(0);
												}
												card_list.setItems(listtounit);
												if(getNowdata != temp.gppfm()){
													System.out.println("パフュームのセットを外しました。");
													System.out.println(
															cdatas.get(len).getname() + ":パフューム装備数:" + cdatas.get(len).gppfm());
												}
											}
										}
									}
									if(setring.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpring();
												temp.spring(1);
												cdatas.get(len).spring(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spring(1);
													}
												}
												card_list.setItems(listtounit);
												if(getNowdata != temp.gpring()){
													System.out.println("リングをセットしました。");
													System.out.println(
															cdatas.get(len).getname() + ":リング装備数:" + cdatas.get(len).gpring());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpring();
												temp.spring(0);
												cdatas.get(len).spring(0);
												for(int k = 0; k < listtounit.size();k++){
													if(card.equals(listtounit.get(k))){
														listtounit.get(k).spring(0);
													}
												}
												card_list.setItems(listtounit);
												if(getNowdata != temp.gpring()){
													System.out.println("リングのセットを外しました。");
													System.out.println(
															cdatas.get(len).getname() + ":リング装備数:" + cdatas.get(len).gpring());
												}
											}
										}
									}
									if(setcross.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcross();
												temp.spcross(1);
												cdatas.get(len).spcross(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spcross(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpcross()) {
													System.out.println("クロスをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":クロス装備数:"
															+ cdatas.get(len).gpcross());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcross();
												temp.spcross(0);
												cdatas.get(len).spcross(0);
												for(int k = 0; k < listtounit.size();k++){
													if(card.equals(listtounit.get(k))){
														listtounit.get(k).spcross(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpcross()) {
													System.out.println("クロスのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":クロス装備数:"
															+ cdatas.get(len).gpcross());
												}
											}
										}
									}
									if(setaura.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpaura();
												temp.spaura(1);
												cdatas.get(len).spaura(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spaura(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpaura()) {
													System.out.println("オーラをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":オーラ装備数:"
															+ cdatas.get(len).gpaura());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpaura();
												temp.spaura(0);
												cdatas.get(len).spaura(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spaura(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpaura()) {
													System.out.println("オーラのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":オーラ装備数:"
															+ cdatas.get(len).gpaura());
												}
											}
										}
									}
									if(setveil.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpveil();
												temp.spveil(1);
												cdatas.get(len).spveil(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spveil(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpveil()) {
													System.out.println("ヴェールをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ヴェール装備数:"
															+ cdatas.get(len).gpveil());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpveil();
												temp.spveil(0);
												cdatas.get(len).spveil(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spveil(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpveil()) {
													System.out.println("ヴェールのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ヴェール装備数:"
															+ cdatas.get(len).gpveil());
												}
											}
										}
									}
									if(setcharm.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcharm();
												temp.spcharm(1);
												cdatas.get(len).spcharm(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spcharm(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpcharm()) {
													System.out.println("チャームをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":チャーム装備数:"
															+ cdatas.get(len).gpcharm());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpcharm();
												temp.spcharm(0);
												cdatas.get(len).spcharm(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spcharm(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpcharm()) {
													System.out.println("チャームのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":チャーム装備数:"
															+ cdatas.get(len).gpcharm());
												}
											}
										}
									}
									if(setheal.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpheal();
												temp.spheal(1);
												cdatas.get(len).spheal(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spheal(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpheal()) {
													System.out.println("ヒールをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ヒール装備数:"
															+ cdatas.get(len).gpheal());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpheal();
												temp.spheal(0);
												cdatas.get(len).spheal(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spheal(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpheal()) {
													System.out.println("ヒールのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ヒール装備数:"
															+ cdatas.get(len).gpheal());
												}
											}
										}
									}
									if(settrick.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrick();
												temp.sptrick(1);
												cdatas.get(len).sptrick(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).sptrick(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gptrick()) {
													System.out.println("トリックをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":トリック装備数:"
															+ cdatas.get(len).gptrick());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrick();
												temp.sptrick(0);
												cdatas.get(len).sptrick(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).sptrick(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gptrick()) {
													System.out.println("トリックのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":トリック装備数:"
															+ cdatas.get(len).gptrick());
												}
											}
										}
									}
									if(setimage.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpimage();
												temp.spimage(1);
												cdatas.get(len).spimage(1);
												for(int k = 0; k < listtounit.size();k++){
													if(card.equals(listtounit.get(k))){
														listtounit.get(k).spimage(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpimage()) {
													System.out.println(Card_datas.getSISimagename(card)+"をセットしました。");
													System.out.println(cdatas.get(len).getname() + ":"+Card_datas.getSISimagename(card)+"装備数:"
															+ cdatas.get(len).gpimage());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpimage();
												temp.spimage(0);
												cdatas.get(len).spimage(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spimage(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpimage()) {
													System.out.println(Card_datas.getSISimagename(card) + "のセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":"
															+ Card_datas.getSISimagename(card) + "装備数:"
															+ cdatas.get(len).gpimage());
												}
											}
										}
									}
									if(setnonette.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpnnet();
												temp.spnnet(1);
												cdatas.get(len).spnnet(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spnnet(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpnnet()) {
													System.out.println("ノネットをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ノネット装備数:"
															+ cdatas.get(len).gpnnet());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpnnet();
												temp.spnnet(0);
												cdatas.get(len).spnnet(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spnnet(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpnnet()) {
													System.out.println("ノネットのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ノネット装備数:"
															+ cdatas.get(len).gpnnet());
												}
											}
										}
									}
									if(setwink.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpwink();
												temp.spwink(1);
												cdatas.get(len).spwink(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spwink(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpwink()) {
													System.out.println("ウィンクをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ウィンク装備数:"
															+ cdatas.get(len).gpwink());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpwink();
												temp.spwink(0);
												cdatas.get(len).spwink(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spwink(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpwink()) {
													System.out.println("ウィンクのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ウィンク装備数:"
															+ cdatas.get(len).gpwink());
												}
											}
										}
									}
									if(settrill.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrill();
												temp.sptrill(1);
												cdatas.get(len).sptrill(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).sptrill(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gptrill()) {
													System.out.println("トリルをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":トリル装備数:"
															+ cdatas.get(len).gptrill());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gptrill();
												temp.sptrill(0);
												cdatas.get(len).sptrill(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).sptrill(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gptrill()) {
													System.out.println("トリルのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":トリル装備数:"
															+ cdatas.get(len).gptrill());
												}
											}
										}
									}
									if(setbloom.isSelected()){
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpbloom();
												temp.spbloom(1);
												cdatas.get(len).spbloom(1);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spbloom(1);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpbloom()) {
													System.out.println("ブルームをセットしました。");
													System.out.println(cdatas.get(len).getname() + ":ブルーム装備数:"
															+ cdatas.get(len).gpbloom());
												}
											}
										}
									}else{
										for (int len = 0; len < cdatas.size(); len++) {
											if (card.equals(cdatas.get(len))) {
												Card_datas temp = cdatas.get(len);
												int getNowdata = temp.gpbloom();
												temp.spbloom(0);
												cdatas.get(len).spbloom(0);
												for (int k = 0; k < listtounit.size(); k++) {
													if (card.equals(listtounit.get(k))) {
														listtounit.get(k).spbloom(0);
													}
												}
												card_list.setItems(listtounit);
												if (getNowdata != temp.gpbloom()) {
													System.out.println("ブルームのセットを外しました。");
													System.out.println(cdatas.get(len).getname() + ":ブルーム装備数:"
															+ cdatas.get(len).gpbloom());
												}
											}
										}
									}
									//System.out.println("リストをスライドして、一度更新するとリストに反映されます。");
									//tpane.getSelectionModel().select(3);
									setSIS.close();
								}
							});
							setSISgroot.add(sissaver, 0, 0);
							setSISgroot.add(lbls, 0, 1);
							setSISgroot.add(setkisshb, 0, 2);
							setSISgroot.add(setppfmhb, 1, 2);
							setSISgroot.add(setringhb, 0, 3);
							setSISgroot.add(setcrosshb, 1, 3);
							setSISgroot.add(setaurahb, 0, 4);
							setSISgroot.add(setveilhb, 1, 4);
							setSISgroot.add(setcharmhb, 0, 5);
							setSISgroot.add(sethealhb, 1, 5);
							setSISgroot.add(settrickhb, 0, 6);
							setSISgroot.add(setimagehb, 1, 6);
							setSISgroot.add(setnonettehb, 0, 7);
							setSISgroot.add(setwinkhb, 1, 7);
							setSISgroot.add(settrillhb, 0, 8);
							setSISgroot.add(setbloomhb, 1, 8);
							Scene setSISscene = new Scene(setSISgroot);
							//setSISscene.setFill(null);
							setSIS.setScene(setSISscene);
							setSIS.setTitle(card.grrity()+card.getname()+"SIS設定");
							setSIS.show();
						}
					}
				});
			}
		}
	}

	private static void redirectConsole(TextArea textarea, Button resetButton){
		//https://qiita.com/snipsnipsnip/items/281bd6ad20417b10fa04
		//上記のサイトを参考にjavafx仕様に書き直した。
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream(){
			@Override
			public synchronized void flush() throws IOException{
				textarea.setText(toString());
			}
		};

		resetButton.setOnAction(new EventHandler<ActionEvent>(){
			//*リセットボタンが押された時のイベント
				public void handle(ActionEvent e){
				synchronized(bytes){
					bytes.reset();
					printlogc = 1;
				}
				textarea.setText("");
			}
		});

		// trueをつけるといいタイミングでflushされる
		PrintStream out = new PrintStream(bytes, true);

		System.setErr(out);
		System.setOut(out);
	}

	public static void printoutTb(String str){
		System.out.println(printlogc + ":" + str);
		printlogc++;
	}
	public static void printerrTb(String str){
		System.err.println(printlogc + ":" + str);
		printlogc++;
	}

	//* メインメソッド
	public static void main(String[] args){
		int argc = args.length;
		if(argc >= 1){
			debuglevel = Integer.parseInt(args[0]);
		}
		try{
			String inipath = System.getProperty("user.dir");
			inipath = inipath + "\\SIF_SCsettings.ini";
			iniDataPath = inipath;//SIF_SCsettings.ini
			FileReader frinifile = new FileReader(inipath);
			BufferedReader brinifile = new BufferedReader(frinifile);
			String dPath = brinifile.readLine();
			chknulls = dPath;
			if(dPath != null){
				File dfilePath = new File(dPath);
				maxNum = Card_read.getMaxnum(dfilePath);
				for(int len = 1; len < maxNum; len++){
					cdatas.add(Card_read.one_carddata(Card_read.reading_rdata(dfilePath), len));
					listtounit.add(Card_read.one_carddata(Card_read.reading_rdata(dfilePath), len));
				}
			}
			card_list.setItems(listtounit);
			brinifile.close();
			Music_data[] ar_mlist = Music_data.r_Rdata();
			for(Music_data tmp:ar_mlist){
				music_list.add(tmp);
			}

		}catch(NullPointerException e){
				System.err.println(e);
				printlogc = -1;
				errstr = e.toString();
		}catch(FileNotFoundException e){
				System.err.println(e);
				printlogc = -2;
				errstr = e.toString();
		}catch(IOException e){
				System.err.println(e);
				printlogc = -3;
				errstr = e.toString();
		}
		launch(args);
	}
}
