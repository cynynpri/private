package samples;

import java.io.*;
import java.util.*;

class Skill_read{
	public static Skill_data[] setSkill(String rrity, Skill_data[] sdata){
		//���A���e�B������Z�������₷�����邽�߂̃��\�b�h
		ArrayList<Skill_data> rtndata = new ArrayList<Skill_data>();
		for(int len = 0; len < sdata.length; len++){
			if(rrity.equals(sdata[len].grrity())){
				rtndata.add(sdata[len]);
			}
		}
		Skill_data[] rdata = new Skill_data[rtndata.size()];
		for(int len = 0; len < rtndata.size(); len++){
			rdata[len] = rtndata.get(len);
		}
		return rdata;
	}

	public static String[] setSkillnameT(String pprty,Skill_data[] sdata){
		//��������X�L���e�[�u����String[]�ŕԂ����\�b�h
		ArrayList<Skill_data>bfprdata = new ArrayList<Skill_data>();
		for(int i = 0; i < sdata.length;i++){
			if((sdata[i].getcsknm().indexOf(pprty) != -1)){
				bfprdata.add(sdata[i]);
			}
		}
		String[] rtnStr = new String[bfprdata.size()];
		for(int len = 0;len < bfprdata.size();len++){
			rtnStr[len] = bfprdata.get(len).getskinm();
		}
		return rtnStr;
	}

	public static Skill_data onerdata(String skinm, Skill_data[] sdata){
		for(int i = 0; i < sdata.length; i++){
			if(skinm.equals(sdata[i].getskinm())){
				return sdata[i];
			}
		}
		return null;
	}//����X�L����sdata��Ԃ����\�b�h(�F�X�ȏ��͂�������get����΂���)

	public static int getprob(int skilv, Skill_data rdata){
		int iniprb = rdata.gsprob();
		int difprb = rdata.gdfprb();
		int fprob = rdata.gfprob();
		if(skilv != 8){
			return (iniprb + difprb*(skilv-1));
		}else{
			return fprob;
		}
	}//���Z���x������m����Ԃ����\�b�h

	public static String getefsz(int skilv, Skill_data rdata){
		//Card_datas��342�s�ڂ��Q�l�ɂ��Ēǉ����Ă�������
		if(rdata.gsklef().equals("�X�R�A")){
			int iniscr = rdata.giefsz();
			int difscr = rdata.gdifez();
			int finscr = rdata.gfefsz();
			Integer[] scrT = Card_datas.scrupTable(iniscr, difscr, finscr);
			int rtnefsz = scrT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("��")){
			int inihe = rdata.giefsz();
			int finhe = rdata.gfefsz();
			Integer[] helT = Card_datas.healTable(inihe, finhe);
			int rtnefsz = helT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("����")){
			double accut = rdata.gaccut();
			int finint = rdata.gfefsz();
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("�p�[�t�F�N�g")){//#1413�ȂǂɑΉ����Ă�������
			double accut = rdata.gaccut();//�����̃p�[�t�F�N�g�A�b�v����
			int finscrupt = (int)rdata.gpfupt()*10;
			//System.out.println("finscrupt :" + finscrupt);
			double[]pfupT = Card_datas.accracyTable(accut, finscrupt);
			double pfctscrupT = pfupT[skilv-1];//perfect score up time

			int iniscr = rdata.giefsz();
			int difscr = rdata.gdifez();
			int finscr = rdata.gfefsz();
			Integer[] scrT = Card_datas.scrupTable(iniscr, difscr, finscr);
			int rtnscrupTb = scrT[skilv-1];//score up Table
			String rtnStr = String.valueOf(rtnscrupTb) + "," + String.valueOf(pfctscrupT);

			return rtnStr;//split�K�{ int+double
		}else if(rdata.gsklef().equals("�p�����[�^�[")){
			int iniprup = rdata.giefsz();//�����p�����^�A�b�v�l�i%�j
			int finprup = rdata.gfefsz();//�ŏI�p�����^�A�b�v�l(%)
			Integer[] pupT = Card_datas.pprtyupTable(iniprup, finprup);
			int rtnprupP = pupT[skilv-1];
			String rtnStr = String.valueOf(rtnprupP) + ",";

			double accut = rdata.gaccut();
			int finint = (int)rdata.gpfupt()*10;//#1416�Ή��ς݁B
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			rtnStr = rtnStr + String.valueOf(rtnefsz);
			return rtnStr;//split�K�{ int+double
		}else if(rdata.gsklef().equals("������")){
			int iniprup = rdata.giefsz();//�������Z�A�b�v�l�i%�j
			int finprup = rdata.gdifez();//�ŏI���Z�A�b�v�l(%)
			Integer[] pupT = Card_datas.pprtyupTable(iniprup, finprup);
			int rtnprupP = pupT[skilv-1];
			String rtnStr = String.valueOf(rtnprupP) + ",";//�b��

			double accut = rdata.gaccut();
			int finint = rdata.gfefsz();
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];//����x�{

			rtnStr = rtnStr + String.valueOf(rtnefsz);
			return rtnStr;//split�K�{ int+double
		}else if(rdata.gsklef().equals("���s�[�g")){
			return String.valueOf(getprob(skilv,rdata));
		}else if(rdata.gsklef().equals("�V���N��")){
			double accut = rdata.gaccut();//�����R�s�[����
			int finint = rdata.gfefsz();//�ŏI�R�s�[����
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			return String.valueOf(rtnefsz);
		}
		return null;
	}//���Z���x�����画�苭���Ȃǂ̃X�L���^�̌��ʗʂ�Ԃ����\�b�h(�v�Z���Ȃ��̂�String�ł���)

	public static String getSklefText(int skilv ,Skill_data srdata, String name){
		String rtntext = new String();
		String gefsz = getefsz(skilv, srdata);//�X�R�A�񕜂Ȃǂ̌��ʗ�
		String gtpsk = srdata.gtpskr();
		if(gtpsk.equals("�p�[�t�F�N�g")){
			gtpsk = "PERFECT";
		}
		int fsz = srdata.gfsz();//�����������l
		int prob = getprob(skilv,srdata);//�����m��
		String chkstr = srdata.gsklef();//�X�L���̔������ʌ^
		String tprchain = new String();
		String[] bfefsz = new String[2];
		if(name.equals("����ԗz")||name.equals("���ؖ�^�P")||name.equals("����z")||name.equals("�삱�Ƃ�")||name.equals("�����T��")||name.equals("���c�C��")||name.equals("���V�ɂ�")||name.equals("�����G��")||name.equals("������")){
			tprchain = "��'s";
		}else if(name.equals("���V���r�B")||name.equals("���ؓc�Ԋ�")||name.equals("�Ó��P�q")||name.equals("�n�ӗj")||name.equals("���C���")||name.equals("�������q")||name.equals("���Y�ʓ�")||name.equals("���V�_�C��")||name.equals("�����f�")){
			tprchain = "Aqours";
		}

		if(name.equals("����ԗz")||name.equals("���ؖ�^�P")||name.equals("����z")||name.equals("���V���r�B")||name.equals("���ؓc�Ԋ�")||name.equals("�Ó��P�q")){
			tprchain = tprchain + "1�N��";
		}else if(name.equals("�삱�Ƃ�")||name.equals("�����T��")||name.equals("���c�C��")||name.equals("�n�ӗj")||name.equals("���C���")||name.equals("�������q")){
			tprchain = tprchain + "2�N��";
		}else{
			tprchain = tprchain + "3�N��";
		}

		if(chkstr.equals("�p�[�t�F�N�g") || chkstr.equals("�p�����[�^�[") ||chkstr.equals("������")){
			bfefsz = gefsz.split(",",0);//int + double
		}
		if(gtpsk.equals("���Y���A�C�R��")){
			rtntext = gtpsk + fsz + "���Ƃ�";
		}else if(gtpsk.equals("�^�C�}�[")){
			rtntext = fsz + "�b���Ƃ�";
		}else if(gtpsk.equals("PERFECT")){
			rtntext = gtpsk +"��"+ fsz + "��B�����邲�Ƃ�";
		}else if(gtpsk.equals("�R���{")){
			rtntext = gtpsk + fsz + "��B�����邲�Ƃ�";
		}else if(gtpsk.equals("�X�R�A")){
			rtntext = gtpsk + fsz + "�B�����Ƃ�";
		}else if(gtpsk.equals("�X�^�[�A�C�R��")){
			rtntext = gtpsk + "PERFECT" + fsz +"�񂲂Ƃ�";
		}else if(gtpsk.equals("�`�F�C��")){
			rtntext = "���g������" + tprchain + "�̓��Z�����ׂĔ��������";
			//factv = 0;����
			//fsz = 0����
		}


		rtntext = rtntext + prob + "%�̊m����";
		if(chkstr.equals("��")){
			rtntext = rtntext + "�̗͂�" + gefsz + "�񕜂���";
		}else if(chkstr.equals("����")){
			rtntext = rtntext + "���肪" + gefsz + "�b���������";
		}else if(chkstr.equals("�X�R�A")){
			rtntext = rtntext + "�X�R�A��" + gefsz +"������";
		}else if(chkstr.equals("�p�[�t�F�N�g")){//�����T�ʂ����SR #1413�ɑΉ��ł��ĂȂ��̂ŃR�����g�A�E�g
			String scrupTb = bfefsz[0];//int
			String pfctscrupT = bfefsz[1]; //double
			rtntext = rtntext + pfctscrupT + "�b��PERFECT���̃^�b�vSCORE��" + scrupTb + "������";
		}else if(chkstr.equals("������")){
			rtntext = rtntext + bfefsz[1] + "�b�ԑ��̓��Z�̔����m����1." + bfefsz[0] +"�{�ɂȂ�";
		}else if(chkstr.equals("�p�����[�^�[")){
			rtntext = rtntext + bfefsz[1] + "�b��" + tprchain + "�̑���P��" + bfefsz[0] +"%UP����";
		}else if(chkstr.equals("�V���N��")){
			rtntext = rtntext + gefsz + "�b��" + tprchain + "�̂����ꂩ�Ɠ�������P�ɂȂ�";
		}else if(chkstr.equals("���s�[�g")){
			rtntext = rtntext + "���O�ɔ����������Z���s�[�g�ȊO�̓��Z���ʂ𔭓�";
		}

		return rtntext;
	}

	public static String[] setcpprty(Skill_data srdata, String pprty, String rrity, String awake){
		int bfcsm = srdata.gcsm();
		int bfcpr = srdata.gcpr();
		int bfccl = srdata.gccl();
		int kizuna = 0;
		if(awake.equals("�o��")){
			if(rrity.equals("SR")){
				kizuna = 500;
			}else if(rrity.equals("SSR")){
				kizuna = 750;
			}else{
				kizuna = 1000;
			}
			if(pprty.equals("�X�}�C��")){
				bfcsm +=  kizuna;
			}else if(pprty.equals("�s���A")){
				bfcpr += kizuna;
			}else{
				bfccl += kizuna;
			}
		}else if(awake.equals("���o��")){
			if(rrity.equals("SR")){
				kizuna = 250;
				bfcsm -= 280;
				bfcpr -= 280;
				bfccl -=280;
			}else if(rrity.equals("SSR")){
				kizuna = 375;
				bfcsm -= 300;
				bfcpr -= 300;
				bfccl -= 300;
			}else{
				kizuna = 500;
				bfcsm -= 300;
				bfcpr -= 300;
				bfccl -= 300;
			}
			if(pprty.equals("�X�}�C��")){
				bfcsm +=  kizuna;
			}else if(pprty.equals("�s���A")){
				bfcpr += kizuna;
			}else{
				bfccl += kizuna;
			}
		}
		String rcsm = "�X�}�C���l:"+bfcsm;
		String rcpr = "�s���A�l:"+bfcpr;
		String rccl = "�N�[���l:"+bfccl;
		String[] rtnStrs = {rcsm, rcpr, rccl, String.valueOf(bfcsm), String.valueOf(bfcpr), String.valueOf(bfccl)};

		return rtnStrs;
	}

	/*public static void main(String[] args)throws IOException, FileNotFoundException{
		Skill_data[] sdata = Skill_data.setdata("���c�C��");
		Skill_data[] rdata = setSkill("SR",sdata);
		String[] elem = new String[rdata.length];
		for(int len = 0; len < rdata.length; len++){
			elem[len] = rdata[len].getskinm();
			System.out.println((len+1) + " card is " + elem[len]);
		}
	}*/
}
