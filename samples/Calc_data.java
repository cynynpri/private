package samples;

import java.io.*;
import java.util.*;
import java.math.*;

class Calc_data{
	//�v�Z�p���j�b�g�f�[�^�Q
	/*static ArrayList<Card_datas[]> unitdata = new ArrayList<Card_datas[]>();
	static Card_datas[] oneunit = new Card_datas[9];
	private String unitnm; //���j�b�g�� ex)��'s Aqours
	private String subuntnm; //�T�u���j�b�g�� ex) Printemps,lily white, BiBi, CYaRon!, Guilty Kiss, AZALEA
	private String grade;//�w�N 1�N, 2�N, 3�N
	private int usm;//���j�b�g�X�}�C���l
	private int upr;//���j�b�g�s���A�l
	private int ucl;//���j�b�g�N�[���l*/

	/*public static ArrayList<Card_datas[]> setUnitlist(ArrayList<Card_datas> cdata,String pprty,int maxnotes){
		int max = cdata.size();
		Card_datas[] untdt = new Card_datas[9];
		Card_datas[] fldt = new Card_datas[cdata.size()];
		for(int len = 0;len<max;len++){
			fldt[len] = cdata.get(len);
		}
		Card_datas[] rfldt = new Card_datas[max-1];
		for(int len = 0;len<max-1;len++){
			rfldt[len] = fldt[len+1];
		}
		Card_datas[] bfcdata = new Card_datas[20];
		if(pprty.equals("�X�}�C��")){
			//
			fldt = Card_datas.csmsort(fldt);
		}else if(pprty.equals("�s���A")){
			//
		}else{
			//
		}

		for (int len = 0; len < 20; len++) {
			bfcdata[len] = fldt[fldt.length - len];
		}
		if (bfcdata[0].getgrade().equals(bfcdata[1].getgrade())) {
			//
		}

		return null;
	}*/

	public static double nCr(int n, int r){
		r = Math.min(r, n - r);
		if (r == 0) return 1;
		if (r == 1) return n;
		double[] numerator = new double[n - r];
		for(int i = 0; i < r; i++){
			numerator[i] = n - r + 1 + i;
		}
		double[] denominator = new double[r];
		for(int i = 0; i < r; i++){
			denominator[i] = i + 1;
		}
		for(int p = 2; p < r + 1; p++){
			double pivot = denominator[p - 1];
			if(pivot > 1){
				int offset = (n - r) % p;
				for(int k = p - 1; k < r; k += p){
					numerator[k - offset] /= pivot;
					denominator[k] /= pivot;
				}
			}
		}
		double result = 1;
		for(int k = 0; k < r; k++){
			if(numerator[k] > 1){
				result *= numerator[k];
			}
		}
		return result;
	}
	//https://teratail.com/questions/9363
	//��L��python�v���O�������Q�l�ɂ����B

	//�X�L�������ő�񐔂��v�Z���郁�\�b�h
	public static int setMaxactcnt(Card_datas ondt, Music_data calcMd, double perper, String sf/*, Card_datas[] unit*/) {
		int maxcombo = calcMd.gmaxcb();
		int star_icon = calcMd.gstar_icon();
		int musictm = calcMd.gmusictm();
		int mactct = 0;
		int base = Integer.parseInt(sf.split(",", 0)[0]);
		//check Card_datas line 521
		if (ondt.gskitp().equals("�^�C�}�[")) {
			mactct = (int)Math.floor(musictm / ondt.gfactv());
		} else if (ondt.gskitp().equals("���Y���A�C�R��")) {
			mactct = (int)Math.floor(maxcombo / ondt.gfactv());
		} else if (ondt.gskitp().equals("�p�[�t�F�N�g")) {
			mactct = (int)Math.floor(maxcombo * perper / ondt.gfactv());
		} else if (ondt.gskitp().equals("�R���{")) {
			mactct = (int)Math.floor(maxcombo / ondt.gfactv());
		} else if (ondt.gskitp().equals("�X�R�A")) {
			mactct = (int)Math.floor(base * maxcombo * 1.3 / (80 * ondt.gfactv()));
		} else if (ondt.gskitp().equals("�X�^�[�A�C�R��")) {
			mactct = (int)Math.floor(star_icon / ondt.gfactv());
		} else if (ondt.gskitp().equals("�`�F�C��")) {
			//�������v�����Ȃ�
			/*for(int len = 0;len < unit.length;len++){
				if(ondt.getgrade().equals(unit[len].getgrade())){

				}
			}*/
			//�d���Ȃ��̂Ŗ�����
		}
		return mactct;
	}

	//�X�L���������Ғl���v�Z���郁�\�b�h
	public static int setsklexp(Card_datas calcCd, double perper, String sf, Card_datas[] unit, Music_data calcMd){
		//������perper�̓p�t�F���̂��ƁB
		//sf�̓��j�b�g�̒l
		//unit�͈ꖇ�̃J�[�h
		//calcMd�͌v�Z�������y��
		int efsz = calcCd.gefsz();
		double prob = calcCd.gprob()/100.0;
		int maxactcnt = 0;
		maxactcnt = setMaxactcnt(calcCd, calcMd, perper, sf);
		return (int)Math.floor(maxactcnt*prob*efsz);
	}
	/*public void realsklef(Card_datas[] unit, Music_data calcMd, int playcount){
		ArrayList<float[]> bfrtnscrT = new ArrayList<float[]>();
		for(int len = 0;len < 9;len++){
			//unit[len].sactcnt(setsklcntT(unit[len],calcMd,playcount));
			//unit[len].gactcnt()[0] = min.
			//unit[len].gactcnt()[unit[len].gactcnt().length -1] = max.
		}
		//(1/�v���C��)�̊m���ŏo����ő�X�R�A�v�Z�A���S���Y�����ȉ��Ɏ���.
		// 1.�X�R�A���Ғl���Ƀ\�[�g����B
		// 2.�X�R�A���Ғl�̂����Ƃ������q���ł��������A���̃J�[�h�Ƃ̔����m���̐ς�(1/�v���C��)�𖞂����΂悢
		// 3.�ȏ�̂��̂��v�Z����B�������A�v�Z�ɂ͏��������݂��A
		// �܂��P��菬�̐ςł���䂦�A1/120*1*1...*1�݂����Ȃ��Ƃ͂ł��Ȃ�(�e�J�[�h�̊m�����z�I�ɂ����肦�Ȃ�)
		// ����̊e�J�[�h1/120�ȏ�̊m���Ŕ��������鎖�ۂ�ςɎg�킴��𓾂Ȃ��B
		// �����܂ł���ƍ��X�L������ɂ͂P���񐔂̌v�Z�ɂ��Ȃ肤�邪�A�������������K�v������B


	}*/

	public static String setUnitsf(Card_datas[] unit,Card_datas frend, Music_data cmdt){
		//���j�b�g�lSf�����߂郁�\�b�h ���j�b�g�l,�Z���^�[�X�L�������� �Ƃ�����������o�͂���B
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂ށB(�����9�l�����Z����ƃZ���^�[�X�L�����Ȃ����ꍇ�̃��j�b�g���l�ɂȂ�)
		int[] sa = new int[9];//Sa Status of one Card �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂܂Ȃ��B
		int veil = 0;
		int aura = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		Music_data cmscdt = cmdt;//������������n���y�ȃf�[�^�͔z�񂶂�Ȃ���1�����ɂȂ邩���c

		//�L�b�X��p�t���[�����܂߂��J�[�h�l����(�|���Z�ɂ�����؂�グ����)
		for (int len = 0; len < 9; len++) {
			if(cmscdt.gpprty().equals("�X�}�C��")){
				sa[len] = unit[len].gcsm();
			}else if(cmscdt.gpprty().equals("�s���A")){
				sa[len] = unit[len].gcpr();
			}else{
				sa[len] = unit[len].gccl();
			}
			veil += unit[len].gpveil();
			aura += unit[len].gpaura();
			sa[len] += unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0;
		}

		//SIS����
		for (int len = 0; len < 9; len++) {
			if (unit[len].gpcross() != 0 || unit[len].gpring() != 0) {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
						+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross()
						+ Math.ceil(sa[len] * 0.10) * unit[len].gpring();
			} else {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil;
			}
		}
		//�����j�b�g�̃Z���^�[�T�u�Z���^�[���� SSR�Z���^�[��SR�Z���^�[�����ł��Ă܂���˂��H�I
		if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
			if(rrity.equals("SSR")){
				subcenup = 0.01;
			}else{
				subcenup = 0.03;
			}
		} else {
			if (rrity.equals("SSR")) {
				subcenup = 0.02;
			} else {
				subcenup = 0.06;
			}
		}
		if (centersklnm.equals("�s���A�G���W�F��") || centersklnm.equals("�N�[���G���v���X") || centersklnm.equals("�X�}�C���v�����Z�X")) {
			cenup = 0.09;
			for (int len = 0; len < unit.length; len++) {
				if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
					tmpsu += sa[len];
					su[len] = (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
				} else {
					tmpsu += sa[len];
					su[len] = (int) Math.ceil(sa[len] * cenup);
				}
			}
		} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
				|| centersklnm.indexOf("�v�����Z�X") != -1) {
			if (centersklnm.indexOf("�G���W�F��") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm());
					}
				}
			}
		}else if (centersklnm.indexOf("�X�^�[") != -1){
				cenup = 0.07;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(sa[len] * cenup);
					}
				}
		} else {
			cenup = 0.06;
			for(int len = 0; len < unit.length; len++){
				tmpsu += sa[len];
				su[len] = (int) Math.ceil(sa[len] * cenup);
			}
		}

		//�t�����h����
		subcentersklnm = frend.getacskn();
		centersklnm = frend.getcskin();
		if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
			subcenup = 0.03;
		} else {
			subcenup = 0.06;
		}
		if (centersklnm.equals("�s���A�G���W�F��") || centersklnm.equals("�N�[���G���v���X") || centersklnm.equals("�X�}�C���v�����Z�X")) {
			cenup = 0.09;
			for (int len = 0; len < unit.length; len++) {
				if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
					su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
				} else {
					su[len] += (int) Math.ceil(sa[len] * cenup);
				}
			}
		} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
				|| centersklnm.indexOf("�v�����Z�X") != -1) {
			if (centersklnm.indexOf("�G���W�F��") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm());
					}
				}
			}
		} else if (centersklnm.indexOf("�X�^�[") != -1) {
			cenup = 0.07;
			for (int len = 0; len < unit.length; len++) {
				if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
					su[len] = (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
				} else {
					su[len] = (int) Math.ceil(sa[len] * cenup);
				}
			}
		} else {
			cenup = 0.06;
			for (int len = 0; len < unit.length; len++) {
				su[len] = (int) Math.ceil(sa[len] * cenup);
			}
		}

		int allsu = 0;
		for (int len = 0; len < 9; len++) {
			allsu += su[len];
		}
		tmpsu += allsu;
		String rtnstr = tmpsu + "," + allsu;

		return rtnstr;
	}

	public static Card_datas[] setsortsklUnit(Card_datas[] unit, Music_data cmdt, String sf, double perper){
		int[] sklexpT = new int[unit.length];
		for(int len = 0;len < sklexpT.length;len++){
			sklexpT[len] = setsklexp(unit[len], perper, sf, unit, cmdt);
		}
		for(int len = 0;len < unit.length;len++){
			for(int i = unit.length;i > len;i--){
				if(sklexpT[len] < sklexpT[i]){
					Card_datas temp = unit[i];
					int skltmp = sklexpT[i];
					sklexpT[i] = sklexpT[len];
					unit[i] = unit[len];
					sklexpT[len] = skltmp;
					unit[len] = temp;
				}
			}
		}
		return unit;
	}

	public static double[] setprob(int n, double prob) {
		double[] bf_p = new double[n];
		for (int r = 0; r < n; r++) {
			if (r == 0) {
				bf_p[r] = 1;
			} else {
				if (bf_p[r - 1] - (nCr(n, r)) * Math.pow(prob, r) * Math.pow((1 - prob), (n - r)) < 0) {
					bf_p[r] = bf_p[r - 1];
				} else {
					bf_p[r] = bf_p[r - 1] - (nCr(n, r)) * Math.pow(prob, r) * Math.pow((1 - prob), (n - r));
				}
			}

		}
		return bf_p;
	}

	public static int showsklupscr(double perper, Card_datas[] unit, Music_data calcMd, Card_datas frend){
		// �������̊m���ŏo��ő�X�R�A���v�Z���郁�\�b�h
		// �h���łƂ��āA 1/�v���C�� �̊m����1�r�b�g�Ƃ��āA�O���t��\�������郁�\�b�h���쐬�\�B
		int rtn_scr = 0;
		String sf = setUnitsf(unit, frend, calcMd);
		Card_datas[] sklcalc = setsortsklUnit(unit, calcMd, sf, perper);
		double[][] unitprbs = new double[9][];
		for(int len = 0; len < unit.length;len++){
			unitprbs[len] = setprob(setMaxactcnt(sklcalc[len], calcMd, perper, sf), sklcalc[len].gprob()/100.0);
		}

		return rtn_scr;
	}

	public static int calcBS(Card_datas[] unit, Music_data cmdt, String sf){
		double rtnBS = 0;
		int intbase = Integer.parseInt(sf.split(",",0)[0]);
		double dBase = intbase/80.0;
		int[][] all_lanes = cmdt.glanes();
		for(int len = 0;len < unit.length;len++){
			double tempscr = lanescr(all_lanes[len], dBase);
			if(cmdt.gpprty().equals(unit[len].gpprty())){
				tempscr *= 1.10;
			}else if(cmdt.gunttp().equals(unit[len].getunitnm())){
				tempscr *= 1.10;
			}
			rtnBS += tempscr;
		}

		return (int)Math.floor(rtnBS);
	}

	public static double lanescr(int[] note, double base) {//calcBS�Ŏg�����\�b�h **�v�Z���I�ɂ�Base�͕s�K�v�ł���B
		double ln = 1.25;
		return note[0] * base + note[1] * ln * base + note[2] * 1.10 * base + note[3] * 1.10 * ln * base
				+ note[4] * 1.15 * base + note[5] * 1.15 * ln * base + note[6] * 1.20 * base
				+ note[7] * 1.20 * ln * base + note[8] * 1.25 * base + note[9] * 1.25 * ln * base;
	}

	/*public static void main(String[] args)throws Exception{
		String str = System.getProperty("user.dir")+"/Character_cdata.ini";
		File dPath = new File(str);
		Card_datas[] bfcdata = new Card_datas[1];
		bfcdata = Card_read.reading_rdata(dPath);
		ArrayList<Card_datas> cdatas = new ArrayList<Card_datas>();
		for(int len = 1; len < bfcdata.length; len++){
			cdatas.add(Card_read.one_carddata(bfcdata, len));
		}

		ArrayList<Card_datas[]> a = setUnitlist(cdatas);
	}*/
}
