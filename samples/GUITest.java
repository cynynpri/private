package samples;

import java.io.*;
import java.io.File.*;
import java.util.*;

import javafx.application.Application;
import javafx.beans.binding.SetExpression;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.chart.*;
//import java.lang.Object;
//import javafx.scene.Node;


public class GUITest extends Application{
	static int printlogc = 1;
	static int maxNum = 0;
	static Card_datas[] unitdt = new Card_datas[9];
	static ArrayList<Skill_data> sdata = new ArrayList<Skill_data>();
	static Skill_data srdata;
	static ArrayList<Card_datas> cdatas = new ArrayList<Card_datas>();
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

	public void start(Stage stage){
		//*�\�������p�l���̑傫��
		/*stage.setWidth(800);
		stage.setHeight(600);*/
		//�^�u�p�l���̍쐬
		StackPane spane = new StackPane();
		TabPane tpane = new TabPane();
		tpane.setPrefSize(800, 600);

		Tab chara_DataSet = new Tab();
		chara_DataSet.setClosable(false);
		chara_DataSet.setText("�L�����N�^�[�f�[�^�o�^");
		StackPane spane_c_d = new StackPane();
		chara_DataSet.setContent(spane_c_d);
//=============================================================================
//�^�u�P chara_DataSet START
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

			//*�`���C�X�{�b�N�X
			//ChoiceBox<String> chara_Sel = new ChoiceBox<>(FXCollections.observableArrayList("�����T��","�����G��","�삱�Ƃ�","���c�C��","����z","���ؖ�^�P","������","����ԗz","���V�ɂ�","���C���","�������q","���Y�ʓ�","���V�_�C��","�n�ӗj","�Ó��P�q","���ؓc�Ԋ�","�����f�","���V���r�B"));
			//* ->1 �F�X�s�s�������Ȃ������̂ŁA�R���{�{�b�N�X��
			ComboBox<String> chara_Sel = new ComboBox<>(FXCollections.observableArrayList("�����T��","�����G��","�삱�Ƃ�","���c�C��","����z","���ؖ�^�P","������","����ԗz","���V�ɂ�","���C���","�������q","���Y�ʓ�","���V�_�C��","�n�ӗj","�Ó��P�q","���ؓc�Ԋ�","�����f�","���V���r�B"));
			chara_Sel.setVisibleRowCount(9);
			chara_Sel.setValue("�����T��");
			//chara_Sel.getItems().addAll("�����T��","�����G��","�삱�Ƃ�","���c�C��","����z","���ؖ�^�P","������","����ԗz","���V�ɂ�","���C���","�������q","���Y�ʓ�","���V�_�C��","�n�ӗj","�Ó��P�q","���ؓc�Ԋ�","�����f�","���V���r�B");

			//skills cb
			//ChoiceBox<String> chara_SKills = new ChoiceBox<>();
			//*���l�ɃR���{�{�b�N�X�� ->1
			ComboBox<String> chara_SKills = new ComboBox<>();

			//set rrity
			ComboBox<String> setRrity = new ComboBox<>();
			//set pprty
			ComboBox<String> setPprty = new ComboBox<>();

			//set awake
			RadioButton unawake = new RadioButton("���o��");
			RadioButton awake = new RadioButton("�o��");
			ToggleGroup setawake = new ToggleGroup();

			//set sislt
			Label lblsislt = new Label("SIS�̃X���b�g��");
			ComboBox<Integer> setSislt = new ComboBox<>();

			//set skilv
			Label lblskilv = new Label("���Z���x���I��");
			ComboBox<Integer> setSkilv = new ComboBox<>();
			setSkilv.getItems().addAll(1,2,3,4,5,6,7,8);
			setSkilv.setValue(1);

			//setSksha score heal acc
			ComboBox<String> setSksha = new ComboBox<>();

			//running type of skill
			ComboBox<String> setRtpsk = new ComboBox<String>();
			//setRtpsk.setEditable(true);

			//���������^���Z�b�g����cb.���Y���A�C�R����^�C�}�[�Ȃ�...

			Label card_sm = new Label("0");
			Label card_pr = new Label("0");
			Label card_cl = new Label("0");


			//dataconverter�p��FileChooser
			FileChooser fcer = new FileChooser();

			//dataconverter Btn
			Button dataconverter = new Button("�ϊ�����");

			//*�ۑ��p�̃{�^��
			Button saver = new Button("�ۑ�����");

			//�X�L���������x��
			Label detailsk = new Label("���Z����I�����Ă�������");

			try{
				chSelector = "�����T��";
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
				String[] skilT = Skill_read.setSkillnameT("�X�}�C��" ,Skill_read.setSkill("SR", table));
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
				System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�����X�L�����Z�b�g�ł��܂���ł���");
				printlogc++;
				System.err.println(e);
				tpane.getSelectionModel().select(3);
			}

			setSksha.setValue(srdata.gsklef());
			Label skshalbl = new Label("���Z�����w�肵�Ă�������");
			Label rtpsk = new Label("���Z�����w�肵�Ă�������");

			//settings chara_SKills
			//chara_SKills.getItems().addAll("��ɁA�L�����N�^�[��I�����Ă�������");
			//chara_SKills.setValue("��ɁA�L�����N�^�[��I�����Ă�������");


			//settings setRrity
			setRrity.getItems().addAll("UR","SSR","SR");
			setRrity.setValue("SR");

			//settings setPprty.
			setPprty.getItems().addAll("�X�}�C��","�s���A","�N�[��");
			setPprty.setValue("�X�}�C��");

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
					if(chara_Sel.getValue().equals("���c�C��")){
						try{
							//String[] buffer_CSds = new String[Chara_Skillsdata.mAx("���c�C��")];
							/*String[] buffer_CSds = Card_datas.skill_Pprtylist(setPprty.getValue(),chara_Sel.getValue(), setRrity.getValue());*/ //20180108�ɃR�����g�A�E�g
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
							//buffer_CSds = Chara_Skillsdata.sKillName("���c�C��");
							/* ���̏���buffer_CSds�ɃL�������̖��O����Ɠǂݍ��݂�
							�P��ōςނ��A�y���Ȃ� */
							/*chara_SKills.getItems().clear(); //20180108�ɃR�����g�A�E�g
							chara_SKills.getItems().addAll(buffer_CSds);
							chara_SKills.setValue(buffer_CSds[0].toString());
							if(buffer_CSds.length >= 15){
								chara_SKills.setVisibleRowCount(15);
							}else{
								chara_SKills.setVisibleRowCount(buffer_CSds.length);
							}*/
						}catch(IOException e){
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}/*catch(FileNotFoundException e){
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L����������܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}*/
					}else if(tmpchnm.equals("�������q")){
						chara_SKills.getItems().clear();
						chara_SKills.setValue("���̐��A�������܂���");
						chara_SKills.getItems().addAll("���̐��A�������܂���");
					}else if(tmpchnm.equals("�����T��")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("�삱�Ƃ�")) {
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(tmpchnm.equals("����z")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
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
					if(chara_Sel.getValue().equals("���c�C��")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(chara_Sel.getValue().equals("�����T��")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("�삱�Ƃ�")) {
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("����z")) {
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
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
					if(chara_Sel.getValue().equals("���c�C��")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					}else if(chara_Sel.getValue().equals("�����T��")){
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("�삱�Ƃ�")) {
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
							printlogc++;
							System.err.println(e);
							tpane.getSelectionModel().select(3);
						}
					} else if (tmpchnm.equals("����z")) {
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
							System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u���̃L�����N�^�[�I���{�b�N�X:�X�L�����Z�b�g�ł��܂���ł���");
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
						//����璷����Ȃ���...?
						//�Ȃ��boolean��ݒ肵����...?
						//�ق�Ƃ�boolean�K�v...?
						//�����Ƃ��������Ȃ���...?
					}
				}
			});

			//setSkilv settings
			setSkilv.setOnAction(event -> detailsk.setText(Skill_read.getSklefText(setSkilv.getValue(), srdata, chara_Sel.getValue())));

			//*�ۑ��p�̃{�^���������ꂽ���̃C�x���g
			saver.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					try{
						//if(//*�`�F�b�N�{�b�N�X�Ń`�F�b�N����ꂽ�Ƃ��ɁA���e���㏑�������A����������){
						//FlieWriter fw = new FileWriter(Path() + "Character_data.txt");
						//}
						//*�t�@�C���ǂݍ���
						FileWriter fw = new FileWriter(System.getProperty("user.dir")+"\\Character_data.ini", true);
						//BufferedWriter bw = new BufferedWriter(fw);
						//PrintWriter pw = new PrintWriter(bw);
						PrintWriter pw = new PrintWriter(new BufferedWriter(fw));


						//*�t�@�C����������
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
							if(srdata.gsklef().equals("����")){
								bfod.saccut(Double.parseDouble(Skill_read.getefsz(setSkilv.getValue(), srdata)));
								bfod.sefsz(0);
							}else if(srdata.gsklef().equals("������")){
								String[] bfstr = Skill_read.getefsz(setSkilv.getValue(), srdata).split(",", 2);
								bfod.saccut(Double.parseDouble(bfstr[1]));
								bfod.sefsz(Integer.parseInt(bfstr[0]));
							}else if(srdata.gsklef().equals("�p�����[�^�[")){
								String[] bfstr = Skill_read.getefsz(setSkilv.getValue(), srdata).split(",", 2);
								bfod.saccut(Double.parseDouble(bfstr[1]));
								bfod.sefsz(Integer.parseInt(bfstr[0]));
							}else if(srdata.gsklef().equals("�p�[�t�F�N�g")){
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
							System.err.println(printlogc +":�G���[����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ۑ�����{�^��:����~�X");
							printlogc++;
							tpane.getSelectionModel().select(3);
						}
						//*�t�@�C�������
						pw.close();
					//* ��O����
					}catch(FileNotFoundException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ۑ�����{�^��:�t�@�C����������Ȃ����ߏo�͂ł��܂���");
						printlogc++;
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(IOException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ۑ�����{�^��:�t�@�C���o�͂ł��܂���");
						printlogc++;
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}
				}
			});
			//fcer ��dataconverter�Ŏg�p����܂�
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
								cdatas.clear();//add����̂ŁA����܂łɃf�[�^�������Ă�Ƃ܂���
							}
							for(int len = 0; len < bfcdata.length; len++){
								cdatas.add(Card_read.one_carddata(bfcdata, len));
							}
							FileWriter fwSIFSCsettings = new FileWriter(iniDataPath);
							BufferedWriter bwSIFSCsettings = new BufferedWriter(fwSIFSCsettings);
							PrintWriter pwSIFSCsettings = new PrintWriter(bwSIFSCsettings);
							pwSIFSCsettings.println("Character_cdata.tsv");
							pwSIFSCsettings.close();
							System.out.println(printlogc + ":�V�X�e���ʏ탍�O:");
							printlogc++;
							System.out.println("�J�[�h�f�[�^��ǂݍ��݂܂���:");
							System.out.println("�J�[�h��: "+ maxNum);
							Card_read.print_cdata(bfcdata,maxNum);
							card_num = maxNum+1;
							tpane.getSelectionModel().select(3);
						}
					}catch(NullPointerException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ϊ�����{�^��:���\�b�h�G���[");
						printlogc++;
						if(debuglevel >= 1)
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(FileNotFoundException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ϊ�����{�^��:�t�@�C����������܂���");
						printlogc++;
						if(debuglevel >= 1)
						System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(IOException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ϊ�����{�^��:�o�͂ł��܂���");
						printlogc++;
						if(debuglevel >= 1)
							System.err.println(e);
						tpane.getSelectionModel().select(3);
					}catch(FileTypeNotMatchException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ϊ�����{�^��:���̓t�@�C���`�����قȂ�܂�");
						printlogc++;
						if(debuglevel >= 1){
							System.err.println(e);
						}
						tpane.getSelectionModel().select(3);
					}catch(DataNotFoundException e){
						System.err.println(printlogc + ":��O����:�ꏊ:�L�����N�^�[�f�[�^�o�^�^�u:�ϊ�����{�^��:���͂��ꂽ�t�@�C������f�[�^��������܂���ł����B");
						printlogc++;
						if (debuglevel >= 1) {
							System.err.println(e);
						}
						tpane.getSelectionModel().select(3);
					}
				}
			});
//=============================================================================
//�^�u�P chara_DataSet END
//=============================================================================

//=============================================================================
//�^�u2 calc_SCore START
//=============================================================================
			Tab calc_SCore = new Tab();
			calc_SCore.setClosable(false);
			calc_SCore.setText("�y�ȃX�R�A�v�Z");
			StackPane spane_c_s = new StackPane();
			calc_SCore.setContent(spane_c_s);



			//*�`���C�X�{�b�N�X
			ChoiceBox<String> music_SEl = new ChoiceBox<>(FXCollections.observableArrayList("�l���LIVE �N�Ƃ�LIFE"));

			Button calc_start = new Button("�v�Z����");
			//*�v�Z�p�̃{�^���������ꂽ���̃C�x���g
			/*calc_start.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
				}
			});*/
//=============================================================================
//�^�u2 calc_SCore END
//=============================================================================

//=============================================================================
//�^�u3 plot_SCoreGraph START
//=============================================================================
			Tab plot_SCoreGraph = new Tab();
			plot_SCoreGraph.setClosable(false);
			plot_SCoreGraph.setText("�y�ȃX�R�A�m�����z�\��");
			StackPane spane_p_sg = new StackPane();
			plot_SCoreGraph.setContent(spane_p_sg);
			/*tpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
				public void changed(ObservableValue<? extends Tab>ov, Tab t, Tab t1){

				}
			});*/
			//�O���b�h�p�l�ݒ�
			//�E�����O���t�\���A�������{�^����R���{�{�b�N�X���̑����ʂƂ���B
			GridPane groot = new GridPane();
			GridPane gguis = new GridPane();

			//�O���t�\����
			NumberAxis xAxis = new NumberAxis();
			NumberAxis yAxis = new NumberAxis();
			LineChart<Number,Number> plot = new LineChart<Number,Number>(xAxis, yAxis);

			//�����ʕ�
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
			Label aLane = new Label("a���[��:");
			Label bLane = new Label("b���[��:");
			Label cLane = new Label("c���[��:");
			Label dLane = new Label("d���[��:");
			Label eLane = new Label("e���[��(�Z���^�[):");//center
			Label fLane = new Label("f���[��:");
			Label gLane = new Label("g���[��:");
			Label hLane = new Label("h���[��:");
			Label iLane = new Label("i���[��:");
			//Boolean
			Boolean unitbl = false;

			Button readBtn = new Button("�J�[�h�f�[�^�ǂݍ���");
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
						System.err.println(printlogc + ":�ǂݍ��ރf�[�^������܂���B");
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
			/*Button setUnitBtn = new Button("���j�b�g�Ґ�");
			setUnitBtn.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					unitbl = true;
					aLcb.getValue();
				}
			});*/
			Button plotBtn = new Button("�O���t�\��");


			BorderPane root_p_sg = new BorderPane();
			root_p_sg.setCenter(aLcb);
			root_p_sg.setBottom(readBtn);
			spane_p_sg.getChildren().addAll(root_p_sg);
//=============================================================================
//�^�u3 plot_SCoreGraph END
//=============================================================================

//=============================================================================
//�^�u4 system_log START
//=============================================================================
			Tab system_log = new Tab();
			system_log.setClosable(false);
			String slTitle = "�V�X�e�����O";
			system_log.setText(slTitle);
			StackPane syslogsp = new StackPane();
			system_log.setContent(syslogsp);

			TextArea txa_Log = new TextArea();
			txa_Log.setEditable(false);
			Button resetBtn = new Button("���Z�b�g�{�^��");
			redirectConsole(txa_Log, resetBtn);//->Event settings
//=============================================================================
//�^�u4 system_log END
//=============================================================================
		BorderPane root_c_d = new BorderPane();
		setAnyThings.getChildren().addAll(chara_Sel, setRrity, setPprty, chara_SKills, setAwakes, lblsislt, setSislt, lblskilv, setSkilv, detailsk, setcpprty,/*setSksha,*/ /*skshalbl, rtpsk,*/ /*setRtpsk,*/ dataconverter);
		root_c_d.setCenter(setAnyThings);
		root_c_d.setBottom(saver);

		BorderPane root_c_s = new BorderPane();
		root_c_s.setCenter(music_SEl);
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
			System.out.println(printlogc + ":�V�X�e���ʏ탍�O:");
			printlogc++;
			System.out.println("�ǂݍ��݃f�[�^����(�������)");
			tpane.getSelectionModel().select(3);
			chknulls = "first";
		}else if(printlogc == -1){
			printlogc = 1;
			System.err.println(printlogc + ":�G���[����:�N�����G���[:�f�[�^�ǂݍ���:�A�v������G���[:NullPointerException");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -2){
			printlogc = 1;
			System.err.println(printlogc + ":�G���[����:�N�����G���[:�f�[�^�ǂݍ���:�t�@�C���ǂݍ��݃G���[:�t�@�C����������܂���");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -3){
			printlogc = 1;
			System.err.println(printlogc + ":�G���[����:�N�����G���[:�f�[�^�ǂݍ���:�A�v������G���[:IOException");
			System.err.println(errstr);
			printlogc++;
			tpane.getSelectionModel().select(3);
		}else if(printlogc == -4){
			printlogc = 1;
			System.out.println(printlogc + ":*�f�o�b�O���[�h:�����ǂݍ��ݍς݃X�L���f�[�^��:" + sdata.size());
			System.out.println("sdata.size() = " + sdata.size());
			printlogc++;
			tpane.getSelectionModel().select(3);
		}
	}

	private static void redirectConsole(TextArea textarea, Button resetButton){
		//https://qiita.com/snipsnipsnip/items/281bd6ad20417b10fa04
		//��L�̃T�C�g���Q�l��javafx�d�l�ɏ����������B
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream(){
			@Override
			public synchronized void flush() throws IOException{
				textarea.setText(toString());
			}
		};

		resetButton.setOnAction(new EventHandler<ActionEvent>(){
			//*���Z�b�g�{�^���������ꂽ���̃C�x���g
				public void handle(ActionEvent e){
				synchronized(bytes){
					bytes.reset();
					printlogc = 1;
				}
				textarea.setText("");
			}
		});

		// true������Ƃ����^�C�~���O��flush�����
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

	//* ���C�����\�b�h
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
				}
			}
			brinifile.close();
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
