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
	private double regular_probably;
	private double up_regular_probably;
	private int skill_up_score;
	private int probably_up_skillupscore;
	private int base_score;
	private long calculation_time;
	private int unit_pprty;//���j�b�g�l
	private int centerskill_up_unit_pprty;//�Z���^�[�X�L���ɂ��㏸�����j�b�g�l

	/*
	public static List<Card_datas[]> setUnitlist(List<Card_datas> cdata, Music_data calcMd){
		//���j�b�g��ă��\�b�h
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
			fldt = Card_datas.cprsort(fldt);
		}else{
			//
			fldt = Card_datas.cclsort(fldt);
		}

		for (int len = 0; len < 20; len++) {
			bfcdata[len] = fldt[fldt.length - len];
		}
		if (bfcdata[0].getgrade().equals(bfcdata[1].getgrade())) {
			//
		}

		return;
	}
	*/
	public Calc_data(){
		super();
	}
	//---------------------------------------------------------
	final public void setregular_probably(double regular_probably){
		this.regular_probably = regular_probably;
	}
	final public double getregular_probably(){
		return regular_probably;
	}
	//---------------------------------------------------------
	final public void set_up_regular_probably(double up_regular_probably){
		this.up_regular_probably = up_regular_probably;
	}
	final public double get_up_regular_probably(){
		return up_regular_probably;
	}
	//---------------------------------------------------------
	final public void setskill_up_score(int skill_up_score){
		this.skill_up_score = skill_up_score;
	}
	final public int getskill_up_score(){
		return skill_up_score;
	}
	//---------------------------------------------------------
	final public void set_probably_up_skillupscore(int probably_up_skillupscore){
		this.probably_up_skillupscore = probably_up_skillupscore;
	}
	final public int get_probably_up_skillupscore(){
		return probably_up_skillupscore;
	}
	//---------------------------------------------------------
	final public void setbase_score(int base_score){
		this.base_score = base_score;
	}
	final public int getbase_score(){
		return base_score;
	}
	//---------------------------------------------------------
	final public void setcalculation_time(long calculation_time){
		this.calculation_time = calculation_time;
	}
	final public long getcalculation_time(){
		return calculation_time;
	}
	//---------------------------------------------------------
	final public String toString(){
		return regular_probably+","+up_regular_probably+","+skill_up_score+","+probably_up_skillupscore+","+base_score+","+calculation_time;
	}
	//---------------------------------------------------------
	final public void setunit_pprty(int unit_pprty){
		this.unit_pprty = unit_pprty;
	}
	final public int getunit_pprty(){
		return unit_pprty;
	}
	//---------------------------------------------------------
	final public void setcenterskill_up_unit_pprty(int centerskill_up_unit_pprty){
		this.centerskill_up_unit_pprty = centerskill_up_unit_pprty;
	}
	final public int getcenterskill_up_unit_pprty(){
		return centerskill_up_unit_pprty;
	}
	//---------------------------------------------------------


	public static double nCr(int n, int r){
		//https://teratail.com/questions/9363
		//��L��python�v���O�������Q�l�ɂ����B
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

	//�X�L�������ő�񐔂��v�Z���郁�\�b�h
	public static int setMaxactcnt(Card_datas ondt, Music_data calcMd, double perper, String sf, Card_datas[] unit)throws DataNotFoundException{
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
			//�ΏۃJ�[�h�̍ő�X�L�������񐔂̍Œ�l���`�F�C���̍ő唭���񐔁D(e.g. {19,28,17,22} -> maxactcnt of Chain is 17)
			List<Card_datas> targetCd = new ArrayList<Card_datas>();
			for(int len = 0;len < 9;len++){
				/*
				 * �������[�v����Ɏ��s�����ꍇ
				 * ���Lif������unit[len].gskitp().equals(ondt.gskitp()) == false���m�F�̂��ƁBplz check ctrl + f.
				 */
				if(unit[len].getgrade().equals(ondt.getgrade()) && unit[len].getunitnm().equals(ondt.getunitnm()) && unit[len].gskitp().equals(ondt.gskitp()) == false){
					targetCd.add(unit[len]);
				}
			}
			//�ċA������̂Ŗ������[�v����̊m�F
			for(int len = 0;len < targetCd.size();len++){
				if(targetCd.get(len).gskitp().equals("�`�F�C��")){
					System.err.println(ondt.grrity()+":"+ondt.getname()+":"+ondt.getskinm()+"�̃J�[�h�ɒu���ė\�����ʏ������������܂����B");
					System.out.println("���\�b�h�G���[�ɂ�薳�����[�v�̉���Ɏ��s���܂����B");
					String err = new String();
					throw new DataNotFoundException(err);
				}
			}
			int[] tmpac = new int[targetCd.size()];
			for(int len = 0;len < targetCd.size();len++){
				tmpac[len] = setMaxactcnt(targetCd.get(len), calcMd, perper, sf, unit);
				if (tmpac[len] > mactct) {// <- 0����ő�l������悤�ɂ���B
					mactct = tmpac[len];
				}
			}
			if(mactct == 0){
				System.out.println("�`�F�C���ΏۂƂȂ�J�[�h�����݂��Ȃ��悤�ł��B");
				System.out.println(ondt.grrity()+":"+ondt.getname()+":"+ondt.getskinm()+"�̍ő唭���񐔂�0�Ƃ��Čv�Z���܂��B");
				return 0;
			}
			for (int len = 0; len < tmpac.length; len++){
				if (tmpac[len] < mactct) {// <- mactct�ɂ͍ő�l�������Ă���̂�0�Ƃ̔�r�ɂ͂Ȃ�Ȃ��B
					mactct = tmpac[len];
				}
			}

		}
		return mactct;
	}

	//�X�L�������ɂ��X�R�A�A�b�v���Ғl���v�Z���郁�\�b�h
	public static int setsklexp(Card_datas calcCd, Music_data calcMd, double perper, String sf, Card_datas[] unit, Card_datas frend, double tapscoreup)throws NullPointerException{
		/*
		 * ������perper�̓p�t�F���̂��ƁB
		 * sf�̓��j�b�g�̒l
		 * unit�͈ꖇ�̃J�[�h
		 * calcMd�͌v�Z�������y��
		 */
		int efsz = calcCd.gefsz();
		double prob = calcCd.gprob()/100.0;
		int maxactcnt = 0;
		try{
			maxactcnt = setMaxactcnt(calcCd, calcMd, perper, sf, unit);
		}catch(DataNotFoundException e){
			System.out.println(e);
			System.out.println("���\�b�h�G���[�ɂ��X�L���ɂ��X�R�A�A�b�v���Ғl�̌v�Z�ɖ������[�v���������܂����B");
			System.out.println("���̃G���[����҂ɃX�N���[���V���b�g���B���Ăǂ̂悤�ȑ�����s�����̂����L�̏�`���Ă��������B");
			return 0;
		}
		if(calcCd.gsksha().equals("�X�R�A")){
			if(calcCd.gpcharm() == 0){
				return (int)Math.floor(maxactcnt*prob*efsz);
			}else{
				efsz = (int)(efsz*2.5);
				return (int) Math.floor(maxactcnt * prob * efsz);
			}
		}else if(calcCd.gsksha().equals("��")){
			if(calcCd.gpheal() == 0){
				efsz = 0;
				return 0;
			}else{
				efsz *= 480;
				return (int) Math.floor(maxactcnt * prob * efsz);
			}
		}else if(calcCd.gsksha().equals("����")){
			if(calcCd.gptrick() == 1){
				/*
				 * �g���b�N���t���Ă���ꍇ
				 * sf���Ē�`���A���苭�����Ԃ��̍Ē�`���ꂽrsf��
				 * rsf - sf���v�Z���A1�^�b�v�㏸�����v�Z����B
				 * �����āA�y�Ȃ�1�b������̃m�[�c������A
				 * accut���̎��Ԃ��|���Z���āArsf-sf�̐��l�Ɗ|���Z���A
				 * �g���b�N�㏸�����o�͂���B
				 */
				int counter = 0;
				boolean prupbl = false;
				for(int len = 0;len < unit.length;len++){
					if(unit[len].gsksha().equals("�p�����[�^�[")){
						prupbl = true;
						counter++;
					}else if(unit[len].gsksha().equals("����")){
						counter++;
					}else if(unit[len].gsksha().equals("�V���N��")){
						prupbl = true;
						counter++;
					}
				}
				if(prupbl){
					int pruptrBS = calcBS(unit, calcMd, setpruptrsf(unit, frend, calcMd), tapscoreup);
					int prupBS = calcBS(unit, calcMd, setprupsf(unit, frend, calcMd), tapscoreup);
					int trBS = calcBS(unit, calcMd, settrsf(unit, frend, calcMd), tapscoreup);
					int iBs = calcBS(unit, calcMd, sf, tapscoreup);
					int tmpBS = trBS - iBs;
					int tmpdfBs = prupBS - iBs;
					double dfBs = pruptrBS - iBs;
					dfBs = dfBs - tmpdfBs;
					dfBs = dfBs - tmpBS;//������ʕ��������o���B
					dfBs /= counter;//������ʕ�������B
					dfBs += tmpBS;
					double oefsz = dfBs * (calcCd.gaccut()/calcMd.gmusictm());
					return (int)Math.floor(maxactcnt * prob * oefsz);
				}else{
					int trBs = calcBS(unit, calcMd, settrsf(unit, frend, calcMd), tapscoreup);
					int iBs = calcBS(unit, calcMd, sf, tapscoreup);
					int dfBs = iBs - trBs;
					double oefsz = dfBs * (calcCd.gaccut()/calcMd.gmusictm());
					return (int)Math.floor(maxactcnt * prob * oefsz);
				}
			}else{
				return 0;
			}
		}else if(calcCd.gsksha().equals("�p�[�t�F�N�g")){
			double spnotes = calcMd.gmaxcb()/calcMd.gmusictm();
			double oefsz = spnotes*calcCd.gaccut()*efsz;
			return (int)Math.floor(maxactcnt * prob *oefsz);
		}else if(calcCd.gsksha().equals("������")){
			//�m�����z���Čv�Z���ď㏸�����X�L�����Ғl�������̃X�L�����Ғl����������āA�ő唭���񐔂Ŋ���B
			double[] uprobs = new double[8];
			double[] probs = new double[8];
			double[] tmpefsz = new double[8];
			double[] maxactcnts = new double[8];
			double tefsz = 0.0;
			double oefsz = 0.0;
			List<Card_datas> lunt = new ArrayList<Card_datas>();
			for(int len = 0;len < unit.length;len++){
				if(!(calcCd.equals(unit[len]))){
					lunt.add(unit[len]);
				}
			}
			if(lunt.size() == unit.length){
				String errs = new String();
				System.err.println(calcCd.grrity() + ":" +calcCd.getname()+":"+calcCd.getskinm()+"�̃J�[�h�̊��Ғl�v�Z�ɂ����Ĉُ폈�����������܂����B");
				throw new NullPointerException(errs);
			}
			for(int len = 0;len < lunt.size();len++){
				probs[len] = lunt.get(len).gprob();
				uprobs[len] = efsz/100.0 + 1;//�����A�b�v�m�����i�[�B
				uprobs[len] *= lunt.get(len).gprob();//�����m�����A�b�v���Ċi�[�B
				lunt.get(len).sprob((int)Math.floor(uprobs[len]));//�����m�����A�b�v�������̂֍Ē�`�B
				tmpefsz[len] = lunt.get(len).gefsz();//�����Ғl���쐬���邽�߂�efsz��maxactcnt���i�[�B
				try {
					maxactcnts[len] = setMaxactcnt(lunt.get(len), calcMd, perper, sf, unit);//����B
				} catch (DataNotFoundException e) {
					System.out.println(e);
					System.out.println("���\�b�h�G���[�ɂ��X�L���ɂ��X�R�A�A�b�v���Ғl�̌v�Z�ɖ������[�v���������܂����B");
					System.out.println("���̃G���[����҂ɃX�N���[���V���b�g���B���Ăǂ̂悤�ȑ�����s�����̂����L�̏�`���Ă��������B");
					return 0;
				}
				oefsz += uprobs[len] * tmpefsz[len] * maxactcnts[len];//�����A�b�v�ɂ��X�L���̃X�R�A�A�b�v�����Ғl���i�[�B
				lunt.get(len).sprob((int)probs[len]);//���̔����m���֖߂��A�Ē�`�B
				try {
					maxactcnts[len] = setMaxactcnt(lunt.get(len), calcMd, perper, sf, unit);//���̍ő唭���񐔂��i�[�B
				} catch (DataNotFoundException e) {
					System.out.println(e);
					System.out.println("���\�b�h�G���[�ɂ��X�L���ɂ��X�R�A�A�b�v���Ғl�̌v�Z�ɖ������[�v���������܂����B");
					System.out.println("���̃G���[����҂ɃX�N���[���V���b�g���B���Ăǂ̂悤�ȑ�����s�����̂����L�̏�`���Ă��������B");
					return 0;
				}
				tefsz += probs[len] * tmpefsz[len] * maxactcnts[len];//���̔������ł̃X�L���ɂ��X�R�A�A�b�v���Ғl���i�[�B
			}
			oefsz -= tefsz;//�����A�b�v�����ς݂̃X�R�A�A�b�v���Ғl���猳�̔������ł̃X�R�A�A�b�v���Ғl�������B
			double accut = calcCd.gaccut()/calcMd.gmusictm();//�������Ԋ������i�[�B
			return (int)Math.floor(maxactcnt * prob * oefsz * accut);//�ő唭���񐔁~�m���Ŕ����񐔊��Ғl���Z�o���Aoefsz�͑S���Ԕ������̎��Ԃɂ��֐��l�Ȃ̂�accut�Ƃ������Ԃ�������B

		}else if(calcCd.gsksha().equals("�p�����[�^�[")){
			double accut = calcCd.gaccut();
			String prsf = setprupsf(unit, frend, calcMd);
			/*
			 * �ΏۂƂȂ�J�[�h�̑����l��efsz%���A�b�v������
			 * ������sf���v�Z����-> prsf
			 * ��͔��苭���Ɠ��������B
			 * sf�̌v�Z���\�b�h�̍Ē�`���K�v�B
			 * �e�X�g���\�b�h�ɂČv�Z���m�F�ς�
			 */
			int counter = 0;
			boolean trunt = false;
			for(int len = 0;len < unit.length;len++){
				if(unit[len].gsksha().equals("����")){
					trunt = true;
					counter++;
				}else if(unit[len].gsksha().equals("�p�����[�^�[")){
					counter++;
				}else if(unit[len].gsksha().equals("�V���N��")){
					trunt = true;
					counter++;
				}
			}
			if(trunt){
				prsf = settrsf(unit, frend, calcMd);
				String prupsf = setprupsf(unit, frend, calcMd);
				String pruptrsf = setpruptrsf(unit, frend, calcMd);
				int pruptrBS = calcBS(unit, calcMd, pruptrsf, tapscoreup);
				int prBS = calcBS(unit, calcMd, prupsf, tapscoreup);
				int trBS = calcBS(unit, calcMd, prsf, tapscoreup);
				int iBS = calcBS(unit, calcMd, sf, tapscoreup);
				int tmpBS = prBS - iBS;
				int tmpdfBS = trBS - iBS;
				double dfBS = pruptrBS - iBS;
				dfBS = dfBS - tmpdfBS;
				dfBS = dfBS - tmpBS;//�g���b�N�Ɣ���̑�����ʕ����c��B
				dfBS /= counter;//������ʕ��𔻒�ƃp�����[�^�[�̃J�[�h�̐���������(����̕������l�ɂ���Α�����ʕ���]�v�ɑ������Ƃ��Ȃ��Ȃ�B)
				dfBS += tmpBS;//�J�[�h���p�����[�^�[�A�b�v�Ȃ̂Ńp�����[�^�[�A�b�v�������߂��B
				double oefsz = dfBS *(calcCd.gaccut()/calcMd.gmusictm());
				return (int)Math.floor(maxactcnt * prob * oefsz);
			}else{
				int prupBS = calcBS(unit, calcMd, prsf, tapscoreup);
				int iBS = calcBS(unit, calcMd, sf, tapscoreup);
				int dfBS = prupBS - iBS;
				double oefsz = dfBS *(calcCd.gaccut()/calcMd.gmusictm());
				return (int)Math.floor(maxactcnt * prob * oefsz);
			}
		}else if(calcCd.gsksha().equals("�V���N��")){
			double accut = calcCd.gaccut();
			double oefsz = 0.0;
			/*
			 * �Ώۂ̃J�[�h�̒�����1���擾���A���j�b�g���쐬��sf���Čv�Z����B-> sysf
			 * sysf���猳��sf���������Ƃ͔��苭���̏����Ɠ���B
			 */
			boolean clctrprupbl = false;
			List<Card_datas> calcs = new ArrayList<Card_datas>();
			Card_datas[] syunt = new Card_datas[9];
			List<Card_datas> calcunit = new ArrayList<Card_datas>();
			for (int len = 0; len < unit.length; len++) {
				if (unit[len].getgrade().equals(calcCd.getgrade()) && !(unit[len].equals(calcCd))) {
					calcs.add(unit[len]);
				}
				if (!(unit[len].equals(calcCd))) {
					calcunit.add(unit[len]);
				}
			}
			if (calcunit.size() == unit.length) {
				System.err.println(calcCd.grrity() + ":" + calcCd.getname() + ":" + calcCd.getskinm()
						+ "�̃J�[�h�̊��Ғl�v�Z�ɂ����Ĉُ폈�����������܂����B");
				String err = new String();
				throw new NullPointerException(err);
			}
			String[] tempsysf = new String[calcs.size()];
			for (int len = 0; len < calcs.size(); len++) {
				for (int i = 0; i < calcunit.size(); i++) {
					syunt[i] = calcunit.get(i);
				}
				//SIS�����p��
				calcs.get(len).spkiss(calcCd.gpkiss());
				calcs.get(len).sppfm(calcCd.gppfm());
				calcs.get(len).spring(calcCd.gpring());
				calcs.get(len).spcross(calcCd.gpcross());
				calcs.get(len).spaura(calcCd.gpaura());
				calcs.get(len).spveil(calcCd.gpveil());
				calcs.get(len).sptrick(calcCd.gptrick());
				//�����p�������܂ŁB
				if(calcunit.size() != 8){
					for(int j = calcunit.size();j < 9;j++){
						syunt[j] = calcs.get(len);
					}
				}else{
					syunt[calcunit.size()] = calcs.get(len);
				}
				//����A�p�����[�^�[������Ƒ�����ʕ�������̂ł��̏����̂��߂̉������B
				int counter = 0;
				for(int j = 0;j < syunt.length;j++){
					if(syunt[j].gsksha().equals("����")){
						clctrprupbl = true;
						counter++;
					}else if(syunt[j].gsksha().equals("�p�����[�^�[")){
						clctrprupbl = true;
						counter++;
					}else if(syunt[j].gsksha().equals("�V���N��")){
						counter++;
					}
				}
				if(clctrprupbl){
					//����A�p�����[�^�[�Ƃ̑�����ʕ����܂񂾌v�Z�����B
					String tmpsf = setpruptrsf(syunt, frend, calcMd);
					String trsf = settrsf(syunt, frend, calcMd);
					String prsf = setprupsf(syunt, frend, calcMd);
					sf = setUnitsf(syunt, frend, calcMd);
					int iBS = calcBS(unit, calcMd, sf ,tapscoreup);
					int trBS = calcBS(unit, calcMd, trsf ,tapscoreup);
					int prBS = calcBS(unit, calcMd, prsf, tapscoreup);
					int allBS = calcBS(unit, calcMd, tmpsf, tapscoreup);
					int exprtmp = allBS - prBS;
					int extrtmp = allBS - trBS;
					int exitmp = allBS -iBS;
					double dfBS = exitmp - extrtmp - exprtmp;//���ꂪ������ʕ��B
					dfBS /= counter;
					oefsz += dfBS *(calcCd.gaccut()/calcMd.gmusictm());//�V���N���Ȃ̂œ��ɐ��l��߂��K�v�Ȃ��B
				}else{

					/*
					if(calcCd.gptrick() == 1){
						tempsysf[len] = settrsf(syunt, frend, calcMd);
					}else{
						tempsysf[len] = setUnitsf(syunt, frend, calcMd);//�V���N������sf���i�[������B
					}
					 */
					tempsysf[len] = setUnitsf(syunt, frend, calcMd);
				}
			}
			if(clctrprupbl){
				oefsz /= calcs.size();
				return (int)Math.floor(maxactcnt * prob * oefsz*(accut/calcMd.gmusictm())* calcMd.gmaxcb());
			}
			double tmp = 0.0;
			for (int len = 0; len < calcs.size(); len++) {
				tmp += calcBS(unit, calcMd, tempsysf[len], tapscoreup);//�V���N����������BS���v�Z����tmp�ɍ��Z����B
			}
			//���Z�������Ŋ���A���ς��o���B
			tmp -= calcBS(unit, calcMd, sf, tapscoreup)*calcs.size();//���̐��l�������B
			return (int)Math.floor(maxactcnt * prob * tmp*(accut/calcMd.gmusictm())*calcMd.gmaxcb());
		}else if(calcCd.gsksha().equals("���s�[�g")){
			List<Card_datas> hits = new ArrayList<Card_datas>();
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(calcCd.getgrade()) && unit[len].getunitnm().equals(calcCd.getunitnm()) && !(unit[len].equals(calcCd))){
					hits.add(unit[len]);
				}
			}
			for(Card_datas tmp:hits){
				if(tmp.equals(calcCd)){
					System.out.println("���Z���s�[�g�̃X�R�A�A�b�v���Ғl�v�Z�ɂ����āA�\�����ʃG���[���������܂����B");
					String err = new String();
					throw new NullPointerException(err);
				}
			}
			double tmpexps = 0.0;
			for(int len = 0;len < hits.size();len++){
				tmpexps += setsklexp(hits.get(len), calcMd, perper, sf, unit, frend, tapscoreup);
			}
			tmpexps /= hits.size();
			double oefsz = tmpexps;
			return (int)Math.floor(maxactcnt * prob * oefsz);
		}
		return -1;
	}

	public static String setUnitsf(Card_datas[] unit,Card_datas frend, Music_data cmdt/*, Boolean trbl*/){
		//���j�b�g�lSf�����߂郁�\�b�h ���j�b�g�l,�Z���^�[�X�L�������� �Ƃ�����������o�͂���B
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂ށB(�����9�l�����Z����ƃZ���^�[�X�L�����Ȃ����ꍇ�̃��j�b�g���l�ɂȂ�)
		int[] sa = new int[9];//Sa Status of one Card �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂܂Ȃ��B
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;//�m�l�b�g��������������𖞂�����1�����łȂ����0
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//������������n���y�ȃf�[�^�͔z�񂶂�Ȃ���1�����ɂȂ邩���c

		//�m�l�b�g�̏����D
		int muse = 0;
		int aqua = 0;
		for(Card_datas tmp: unit){
			if(tmp.getunitnm().equals("��'s")){
				muse++;
			}else if(tmp.getunitnm().equals("Aqours")){
				aqua++;
			}
		}
		if(muse == unit.length || aqua == unit.length){
			unitnnt = 1;
		}

		//�J�[�h�l����(�|���Z�ɂ�����؂�グ����)
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
			nonette += unit[len].gpnnet();
			bloom += unit[len].gpbloom();
		}

		//SIS����
		for (int len = 0; len < 9; len++) {
			if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0 || unit[len].gpimage() > 0) {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil + Math.ceil(sa[len] * 0.042) * nonette * unitnnt
						+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross() + Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.28) * unit[len].gptrill()
						+ Math.ceil(sa[len] * 0.29) * unit[len].gpimage()
						+ Math.ceil(sa[len] * 0.10) * unit[len].gpring() + unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
			} else {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil + Math.ceil(sa[len] * 0.042) * nonette * unitnnt + unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
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
			cenup = 0.12;
			if (centersklnm.indexOf("�G���W�F��") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm());
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

		//�t�����h���� -�����Ȃ�null�����邱�Ƃɂ���B
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
				if (rrity.equals("SSR")) {
					subcenup = 0.01;
				} else {
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
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
					|| centersklnm.indexOf("�v�����Z�X") != -1) {
				if (centersklnm.indexOf("�G���W�F��") != -1) {
					cenup = 0.12;
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("�G���v���X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
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
			for (int len = 0; len < 9; len++) {
				allsu += su[len];
			}
			tmpsu += allsu;
		}
		String rtnstr = tmpsu + "," + allsu;
		return rtnstr;
	}

	public static String settrsf(Card_datas[] unit, Card_datas frend, Music_data cmdt/*, Boolean trbl*/) {
		//�g���b�N�������̃��j�b�g�lrSf�����߂郁�\�b�h ���j�b�g�l,�Z���^�[�X�L�������� �Ƃ�����������o�͂���B
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂ށB(�����9�l�����Z����ƃZ���^�[�X�L�����Ȃ����ꍇ�̃��j�b�g���l�ɂȂ�)
		int[] sa = new int[9];//Sa Status of one Card �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂܂Ȃ��B
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;//���j�b�g�����ꂳ��Ă����1�����łȂ����0.
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//������������n���y�ȃf�[�^�͔z�񂶂�Ȃ���1�����ɂȂ邩���c

		//�m�l�b�g�̏����D
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("��'s")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

		//�L�b�X��p�t���[�����܂߂��J�[�h�l����(�|���Z�ɂ�����؂�グ����)
		for (int len = 0; len < 9; len++) {
			if (cmscdt.gpprty().equals("�X�}�C��")) {
				sa[len] = unit[len].gcsm();
			} else if (cmscdt.gpprty().equals("�s���A")) {
				sa[len] = unit[len].gcpr();
			} else {
				sa[len] = unit[len].gccl();
			}
			veil += unit[len].gpveil();
			aura += unit[len].gpaura();
			bloom += unit[len].gpbloom();
			if(unitnnt == 1){
				nonette += unit[len].gpnnet();
			}
		}

		//SIS����
		for (int len = 0; len < 9; len++) {
			if(unit[len].gptrick() == 0){
				if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0 || unit[len].gpimage() > 0) {
					sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
							+ Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * unitnnt * nonette
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0
							+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross()
							+ Math.ceil(sa[len] * 0.10) * unit[len].gpring()
							+ Math.ceil(sa[len] * 0.28) * unit[len].gptrill()
							+ Math.ceil(sa[len] * 0.29) * unit[len].gpimage();
				} else {
					sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil + Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * unitnnt * nonette
					+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			}else{
				if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0 || unit[len].gpimage() > 0) {
					sa[len] += Math.ceil(sa[len] * 0.16 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) * (1+0.042*unitnnt*nonette)) * unit[len].gpcross()
							+ Math.ceil(sa[len] * 0.10 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) * (1+0.042*unitnnt*nonette)) * unit[len].gpring()
							+ Math.ceil(sa[len] * 0.33 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) * (1+0.042*unitnnt*nonette)) + unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0
							+ Math.ceil(sa[len] * 0.28 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) * (1+0.042*unitnnt*nonette)) * unit[len].gptrill()
							+ Math.ceil(sa[len] * 0.29 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) * (1+0.042*unitnnt*nonette)) * unit[len].gpimage();
							//ring��cross�̏����l���ĂȂ������c�_(^o^)�^���
				} else {
					sa[len] +=  Math.ceil(sa[len]*0.33 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) *(1+0.042*unitnnt*nonette))  + unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			}
		}

		//�����j�b�g�̃Z���^�[�T�u�Z���^�[���� SSR�Z���^�[��SR�Z���^�[�����ł��Ă܂���˂��H�I
		if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
			if (rrity.equals("SSR")) {
				subcenup = 0.01;
			} else {
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
				cenup = 0.12;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm());
					}
				}
			}
		} else if (centersklnm.indexOf("�X�^�[") != -1) {
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
			for (int len = 0; len < unit.length; len++) {
				tmpsu += sa[len];
				su[len] = (int) Math.ceil(sa[len] * cenup);
			}
		}

		//�t�����h���� Empty is null.
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
				if (rrity.equals("SSR")) {
					subcenup = 0.01;
				} else {
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
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
					|| centersklnm.indexOf("�v�����Z�X") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("�G���W�F��") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("�G���v���X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
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
			for (int len = 0; len < 9; len++) {
				allsu += su[len];
			}
			tmpsu += allsu;
		}
		String rtnstr = tmpsu + "," + allsu;
		return rtnstr;
	}

	public static String setprupsf(Card_datas[] unit,Card_datas frend, Music_data cmdt/*, Boolean trbl*/){
		//���j�b�g�lSf�����߂郁�\�b�h ���j�b�g�l,�Z���^�[�X�L�������� �Ƃ�����������o�͂���B
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂ށB(�����9�l�����Z����ƃZ���^�[�X�L�����Ȃ����ꍇ�̃��j�b�g���l�ɂȂ�)
		int[] sa = new int[9];//Sa Status of one Card �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂܂Ȃ��B
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//������������n���y�ȃf�[�^�͔z�񂶂�Ȃ���1�����ɂȂ邩���c

		//�m�l�b�g�̏����B
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("��'s")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

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
			bloom += unit[len].gpbloom();
			if(unitnnt != 0){
				nonette += unit[len].gpnnet();
			}
		}

		//SIS����
		for (int len = 0; len < 9; len++) {
			if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0 || unit[len].gpimage() > 0) {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil + Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * nonette * unitnnt
						+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross()
						+ Math.ceil(sa[len] * 0.10) * unit[len].gpring()
						+ Math.ceil(sa[len] * 0.28) * unit[len].gptrill()
						+ Math.ceil(sa[len] * 0.29) * unit[len].gpimage()
						+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
			} else {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
						+ Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * nonette * unitnnt
						+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
			}
		}

		double upp = 0.0;
		Card_datas tmp = new Card_datas();
		for(int len = 0;len < unit.length;len++){
			if(unit[len].gsksha().equals("�p�����[�^�[")){
				upp = (unit[len].gefsz()/100.0)+1.0;
				tmp = unit[len];
			}
		}
		if(tmp != null){
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(tmp.getgrade()) && unit[len].getunitnm().equals(tmp.getunitnm())){
					//�w�N�ƃ��C�����j�b�g������������΁B
					sa[len] *= upp;
				}
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
			cenup = 0.12;
			if (centersklnm.indexOf("�G���W�F��") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm());
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

		//�t�����h���� Empty is null.
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
				if (rrity.equals("SSR")) {
					subcenup = 0.01;
				} else {
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
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
					|| centersklnm.indexOf("�v�����Z�X") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("�G���W�F��") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("�G���v���X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
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
			for (int len = 0; len < 9; len++) {
				allsu += su[len];
			}
			tmpsu += allsu;
		}
		String rtnstr = tmpsu + "," + allsu;
		return rtnstr;
	}

	public static String setpruptrsf(Card_datas[] unit, Card_datas frend, Music_data cmdt/*, Boolean trbl*/) {
		//�g���b�N�������̃��j�b�g�lrSf�����߂郁�\�b�h ���j�b�g�l,�Z���^�[�X�L�������� �Ƃ�����������o�͂���B
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂ށB(�����9�l�����Z����ƃZ���^�[�X�L�����Ȃ����ꍇ�̃��j�b�g���l�ɂȂ�)
		int[] sa = new int[9];//Sa Status of one Card �J�[�h�X�e�[�^�X�ŃX�N�[���A�C�h���X�L�����������܂܂Ȃ��B
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//������������n���y�ȃf�[�^�͔z�񂶂�Ȃ���1�����ɂȂ邩���c

		//�m�l�b�g�̏����B
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("��'s")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

		//�L�b�X��p�t���[�����܂߂��J�[�h�l����(�|���Z�ɂ�����؂�グ����)
		for (int len = 0; len < 9; len++) {
			if (cmscdt.gpprty().equals("�X�}�C��")) {
				sa[len] = unit[len].gcsm();
			} else if (cmscdt.gpprty().equals("�s���A")) {
				sa[len] = unit[len].gcpr();
			} else {
				sa[len] = unit[len].gccl();
			}
			veil += unit[len].gpveil();
			aura += unit[len].gpaura();
			bloom += unit[len].gpbloom();
			if(unitnnt != 0){
				nonette += unit[len].gpnnet();
			}
		}

		//SIS����
		for (int len = 0; len < 9; len++) {
			if (unit[len].gptrick() == 0) {
				if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0
						|| unit[len].gpimage() > 0) {
					sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
							+ Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * unitnnt * nonette
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0
							+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross()
							+ Math.ceil(sa[len] * 0.10) * unit[len].gpring()
							+ Math.ceil(sa[len] * 0.28) * unit[len].gptrill()
							+ Math.ceil(sa[len] * 0.29) * unit[len].gpimage();
				} else {
					sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
							+ Math.ceil(sa[len] * 0.04) * bloom + Math.ceil(sa[len] * 0.042) * unitnnt * nonette
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			} else {
				if (unit[len].gpcross() > 0 || unit[len].gpring() > 0 || unit[len].gptrill() > 0
						|| unit[len].gpimage() > 0) {
					sa[len] += Math
							.ceil(sa[len] * 0.16 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette))
							* unit[len].gpcross()
							+ Math.ceil(sa[len] * 0.10 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette)) * unit[len].gpring()
							+ Math.ceil(sa[len] * 0.33 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette))
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0
							+ Math.ceil(sa[len] * 0.28 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette)) * unit[len].gptrill()
							+ Math.ceil(sa[len] * 0.29 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette)) * unit[len].gpimage();
					//ring��cross�̏����l���ĂȂ������c�_(^o^)�^���
				} else {
					sa[len] += Math
							.ceil(sa[len] * 0.33 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette))
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			}
		}

		//�p�����[�^�[�A�b�v�̏����D
		double upp = 0.0;
		Card_datas tmp = new Card_datas();
		for(int len = 0;len < unit.length;len++){
			if(unit[len].gsksha().equals("�p�����[�^�[")){
				upp = (unit[len].gefsz()/100.0)+1.0;
				tmp = unit[len];
			}
		}
		if(tmp != null){
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(tmp.getgrade()) && unit[len].getunitnm().equals(tmp.getunitnm())){
				//�w�N�ƃ��C�����j�b�g������������΁B
					sa[len] *= upp;
				}
			}
		}

		//�����j�b�g�̃Z���^�[�T�u�Z���^�[���� SSR�Z���^�[��SR�Z���^�[�����ł��Ă܂���˂��H�I
		if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
			if (rrity.equals("SSR")) {
				subcenup = 0.01;
			} else {
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
			cenup = 0.12;
			if (centersklnm.indexOf("�G���W�F��") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("�G���v���X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						tmpsu += sa[len];
						su[len] = (int) Math.ceil(cenup * unit[len].gcsm());
					}
				}
			}
		} else if (centersklnm.indexOf("�X�^�[") != -1) {
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
			for (int len = 0; len < unit.length; len++) {
				tmpsu += sa[len];
				su[len] = (int) Math.ceil(sa[len] * cenup);
			}
		}

		//�t�����h���� Empty -> null
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("��'s") || subcentersklnm.equals("Aqours")) {
				if (rrity.equals("SSR")) {
					subcenup = 0.01;
				} else {
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
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("�G���W�F��") != -1 || centersklnm.indexOf("�G���v���X") != -1
					|| centersklnm.indexOf("�v�����Z�X") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("�G���W�F��") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("�G���v���X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("�v�����Z�X") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
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
			for (int len = 0; len < 9; len++) {
				allsu += su[len];
			}
			tmpsu += allsu;
		}
		String rtnstr = tmpsu + "," + allsu;
		return rtnstr;
	}

	public static Card_datas[] setsortsklUnit(Card_datas[] unit, Music_data cmdt, double perper, String sf, Card_datas frend, double tapscoreup){
		/*
		 * �X�L���������Ғl���������Ƀ\�[�g���郁�\�b�h
		 * unit[0]����Ԋ��Ғl�������Aunit[8]����Ԋ��Ғl���������B
		 */
		int[] sklexpT = new int[unit.length];
		for(int len = 0;len < sklexpT.length;len++){
			sklexpT[len] = setsklexp(unit[len], cmdt, perper, sf, unit, frend, tapscoreup);
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
		//�ϕ��ς݂̓񍀕��z�f�[�^����͂��郁�\�b�h�B
		double[] bf_p = new double[n];
		for (int r = 0; r < n; r++) {
			if (r == 0) {
				bf_p[r] = 1;
			} else {
				bf_p[r] = bf_p[r - 1] - (nCr(n, r)) * Math.pow(prob, r) * Math.pow((1 - prob), (n - r));
			}
		}
		return bf_p;
	}

	public static double[] setrealprob(int n, double prob){
		//���ۂ̈����̃X�L�������񐔂ɑΉ�����m������͂��郁�\�b�h�B
		double[] bf_p = new double[n];
		for(int r = 0; r < n;r++){
			bf_p[r] = nCr(n,r) * Math.pow(prob, r) * Math.pow((1 - prob), (n - r));
		}
		return bf_p;
	}

	public static Calc_data calcscrmain(Card_datas[] unit, Music_data calcMd, Card_datas frend, double perper, int playcount, double tapscoreup, int depth)throws DataNotFoundException{
		/*
		 *  �������̊m���ŏo��ő�X�R�A���v�Z���郁�\�b�h
		 *  ���C���̃X�R�A�v�Z���\�b�h�Ƃ���B
		 *  �h���łƂ��āA 1/�v���C�� �̊m����1�r�b�g�Ƃ��āA�O���t��\�������郁�\�b�h���쐬�\�B
		 */
		double discriminant = (double)(1.0/playcount);
		String sf = setUnitsf(unit, frend, calcMd);
		int base_scr = calcBS(unit, calcMd, sf, tapscoreup);
		String actsf = new String();
		double[][] unitprbs = new double[9][];//���ۂ̐ϕ��ς݂̊m�����z���i�[����z��
		double[][] unitsprbs = new double[9][];//�ϕ�����O�̎��ۂ̊m�����i�[����z��B
		double[][] untupprbs = new double[9][];//�����m���A�b�v�̐ϕ��ς݂̊m�����z���i�[����z��
		double[][] untupsprbs = new double[9][];//�ϕ�����O�̔����m���A�b�v�̊m�����i�[����z��B
		boolean upprbsbl = false;//�������A�b�v�ɂ��Čv�Z�����邩�ۂ���bool�l�B�ʏ�͍Čv�Z���Ȃ���false.
		int[] maxactcnts = new int[9];
		int[] expactcnts = new int[9];
		int[] scrupT = new int[9];
		for(int len = 0; len < unit.length;len++){
			try{
				maxactcnts[len] = setMaxactcnt(unit[len], calcMd, perper, sf, unit);
			}catch(DataNotFoundException e){
				System.out.println(e);
				System.out.println("���\�b�hcalcscrmain�ɂ�setMaxactcnt���\�b�h�������̃G���[");
				return null;
			}
		}
		for(int len = 0; len < unit.length;len++){
			expactcnts[len] = (int)Math.floor(maxactcnts[len]*unit[len].gprob()/100.0);
			//System.out.println("expactcnts is "+ expactcnts[len]);
		}
		for(int len = 0; len < unit.length;len++){
			unitprbs[len] = setrealprob(maxactcnts[len], unit[len].gprob()/100.0);
			unitsprbs[len] = setprob(maxactcnts[len], unit[len].gprob()/100.0);
			/*
			for(double temp : setprob(maxactcnts[len], unit[len].gprob()/100.0)){
				System.out.println("setprob["+len+"] is"+temp);
			}
			*/
		}

		for(int len = 0;len < unit.length;len++){
			//System.out.println("unitprbs[len].length is "+unitprbs[len].length);
			//System.out.println("unitsprbs[len].length is " + unitsprbs[len].length);
			if(unit[len].gsksha().equals("�X�R�A")){
				scrupT[len] = unit[len].gefsz();
				if(unit[len].gpcharm() != 0 && unit[len].gpcharm() != -1){
					scrupT[len] *= (int)(unit[len].gpcharm()*2.5);
				}
			}else if(unit[len].gsksha().equals("��")){
				scrupT[len] = unit[len].gefsz();
				scrupT[len] *= unit[len].gpheal()*480;//heal = 0 => 0;
			}else if(unit[len].gsksha().equals("����")){
				double accut = unit[len].gaccut();
				//�g���b�N���t���Ă���ꍇ
				if(unit[len].gptrick()==1){
					int counter = 0;
					boolean chkprsy = false;
					for(Card_datas tmp: unit){
						if(tmp.gsksha().equals("�p�����[�^�[")){
							chkprsy = true;
							counter++;
						}else if(tmp.gsksha().equals("�V���N��")){
							chkprsy = true;
							counter++;
						}
					}
					if(chkprsy){
						int prtrBS = calcBS(unit, calcMd, setpruptrsf(unit, frend, calcMd), tapscoreup);
						int prBS = calcBS(unit, calcMd, setprupsf(unit, frend, calcMd), tapscoreup);
						int trBS = calcBS(unit, calcMd, settrsf(unit, frend, calcMd), tapscoreup);
						int iBS = calcBS(unit, calcMd, sf, tapscoreup);
						int t1BS = prBS - iBS;
						int t2BS = trBS - iBS;
						int t3BS = prtrBS - iBS;
						double dfBS = t3BS - t2BS;
						dfBS -= t1BS;
						dfBS /= counter;
						dfBS += t2BS;
						double oefsz = dfBS * (accut / calcMd.gmusictm());
						scrupT[len] = (int)Math.floor(oefsz);
					}else{
						int trBs = calcBS(unit, calcMd, settrsf(unit, frend, calcMd), tapscoreup);
						//��ɔ��苭�����������A�g���b�N���������Ă�Ƃ��̃X�R�A(���ۂɂ͋N���肦�Ȃ�)
						int iBs = calcBS(unit, calcMd, sf, tapscoreup);
						//�S�����苭�������������A�g���b�N���������Ȃ��Ƃ��̃X�R�A
						int dfBs = trBs - iBs;
						//��L�̃X�R�A��(���Ȃ킿���ꂪ�g���b�N�ɂ��1�Ȓ��̂��ׂẴX�R�A�㏸���B)
						//���ߎ��B�ŏ��̐��b�͊m���ɔ��苭�����������Ȃ����������邪�����������ł��Ă��Ȃ��B
						//�܂���ۂ̃g���b�N�ɂ��㏸���������߂ɏo�͂����B
						double oefsz = dfBs * (accut/calcMd.gmusictm());
						//accut�͔��苭������,musictm�͊y�Ȏ��ԂȂ̂�1�񔻒苭���������������̊y�Ȓ��ɂ����銄�����g���b�N�ɂ��1�Ȓ��̂��ׂẴX�R�A�㏸���Ɋ|���Z�B
						scrupT[len] = (int)Math.floor(oefsz);
						//��L�̐��l��floor���Ȃ킿�����_�ȉ��؂�̂Ă�scrupT[]�Ɋi�[�B
						//scrupT�Ɋi�[���ꂽ�l�͂��̌�v�Z����A�X�L�������񐔂Ƃ̐ς��o���̂Ɏg����B
					}
				}else{
					//�����łȂ��ꍇ�͒ʏ��1�^�b�v�X�R�A�ŃX�R�A���v�Z�B
					scrupT[len] = 0;//�X�L�������񔭓����悤���X�R�A�㏸����0.
				}
			}else if(unit[len].gsksha().equals("�p�[�t�F�N�g")){
				/*
				 * SIS�̏�������
				 * �y�Ȓ��Ɉ�莞�Ԕ�������B
				 * ���̈�莞�Ԓ��ɔ��ł���m�[�c���͖��m���ł���B
				 * ���m�Ȑ��l���v�Z�ł��Ȃ��B
				 * ����������ǂ����苭���̏�ʌ݊��B
				 */
				double pfupt = unit[len].gaccut();
				int musictm = calcMd.gmusictm();
				int maxcombo = calcMd.gmaxcb();
				double nps = musictm / maxcombo;
				scrupT[len] = (int)Math.floor(nps*pfupt*unit[len].gefsz());
			}else if(unit[len].gsksha().equals("������")){
				for(int k = 0;k < unit.length;k++){
					try{
						untupprbs[k] = setrealprob(setMaxactcnt(unit[k],calcMd,perper,sf,unit), (unit[len].gefsz()/100.0 + 1) * (unit[k].gprob()/100.0));
						untupsprbs[k] = setprob(setMaxactcnt(unit[k],calcMd,perper,sf,unit), (unit[len].gefsz()/100.0 + 1) * (unit[k].gprob()/100.0));
					}catch(DataNotFoundException e){
						String err = new String();
						System.out.println(e);
						throw new DataNotFoundException(err);
					}
				}
				upprbsbl = true;
			}else if(unit[len].gsksha().equals("�p�����[�^�[")){
				double accut = unit[len].gaccut();
				boolean chktrsy = false;
				int counter = 0;
				for(Card_datas tmp:unit){
					if(tmp.gptrick() == 1){
						chktrsy = true;
					}
				}
				for(Card_datas tmp:unit){
					if(chktrsy){
						if(tmp.gsksha().equals("����")){
							chktrsy = true;
							counter++;
						}else if(tmp.gsksha().equals("�V���N��")){
							chktrsy = true;
							counter++;
						}
					}
				}
				if(chktrsy){
					int prtrBS = calcBS(unit, calcMd, setpruptrsf(unit, frend, calcMd), tapscoreup);
					int trBS = calcBS(unit, calcMd, settrsf(unit, frend, calcMd), tapscoreup);
					int prBS = calcBS(unit, calcMd, setprupsf(unit, frend, calcMd), tapscoreup);
					int iBS = calcBS(unit, calcMd, sf, tapscoreup);
					int t1BS = prtrBS - iBS;
					int t2BS = trBS -iBS;
					int t3BS = prBS - iBS;
					double dfBS = t1BS - t3BS;
					dfBS -= t2BS;
					dfBS /= counter;
					dfBS += t3BS;
					double oefsz = dfBS * (accut / calcMd.gmusictm());
					scrupT[len] = (int)Math.floor(oefsz);
				}else{
					int prBS = calcBS(unit, calcMd, setprupsf(unit, frend, calcMd), tapscoreup);
					int iBS = calcBS(unit, calcMd, sf, tapscoreup);
					int dfBS = prBS - iBS;
					double oefsz = dfBS * (accut/calcMd.gmusictm());
					scrupT[len] = (int)Math.floor(oefsz);
				}
			}else if(unit[len].gsksha().equals("�V���N��")){
				Card_datas calcCd = unit[len];
				double accut = calcCd.gaccut();
				double oefsz = 0.0;
				/*
				 * �Ώۂ̃J�[�h�̒�����1���擾���A���j�b�g���쐬��sf���Čv�Z����B-> sysf
				 * sysf���猳��sf���������Ƃ͔��苭���̏����Ɠ���B
				 */
				boolean clctrprupbl = false;
				List<Card_datas> calcs = new ArrayList<Card_datas>();
				Card_datas[] syunt = new Card_datas[9];
				List<Card_datas> calcunit = new ArrayList<Card_datas>();
				for (Card_datas tmp : unit) {
					if (tmp.getgrade().equals(calcCd.getgrade()) && !(tmp.equals(calcCd))) {
						calcs.add(tmp);
					}
					if (!(tmp.equals(calcCd))) {
						calcunit.add(tmp);
					}
				}
				if (calcunit.size() == unit.length) {
					System.err.println(calcCd.grrity() + ":" + calcCd.getname() + ":" + calcCd.getskinm()
							+ "�̃J�[�h�̊��Ғl�v�Z�ɂ����Ĉُ폈�����������܂����B");
					String err = new String();
					throw new NullPointerException(err);
				}
				String[] tempsysf = new String[calcs.size()];
				for (int k = 0; k < calcs.size(); k++) {
					for (int i = 0; i < calcunit.size(); i++) {
						syunt[i] = calcunit.get(i);
					}
					//SIS�����p��
					calcs.get(k).spkiss(calcCd.gpkiss());
					calcs.get(k).sppfm(calcCd.gppfm());
					calcs.get(k).spring(calcCd.gpring());
					calcs.get(k).spcross(calcCd.gpcross());
					calcs.get(k).spaura(calcCd.gpaura());
					calcs.get(k).spveil(calcCd.gpveil());
					calcs.get(k).sptrick(calcCd.gptrick());
					//�����p�������܂ŁB
					if(calcunit.size() != 8){
						for(int j = calcunit.size();j < 9;j++){
							syunt[j] = calcs.get(k);
						}
					}else{
						syunt[calcunit.size()] = calcs.get(k);
					}
					//����A�p�����[�^�[������Ƒ�����ʕ�������̂ł��̏����̂��߂̉������B
					int counter = 0;
					for(Card_datas tmp:syunt){
						clctrprupbl = true;
					}
					for(int j = 0;j < syunt.length;j++){
						if(clctrprupbl){
							if(syunt[j].gsksha().equals("����")){
								clctrprupbl = true;
								counter++;
							}else if(syunt[j].gsksha().equals("�p�����[�^�[")){
								clctrprupbl = true;
								counter++;
							}else if(syunt[j].gsksha().equals("�V���N��")){
								counter++;
							}
						}
					}
					if(clctrprupbl){
						//����A�p�����[�^�[�Ƃ̑�����ʕ����܂񂾌v�Z�����B
						String tmpsf = setpruptrsf(syunt, frend, calcMd);
						String trsf = settrsf(syunt, frend, calcMd);
						String prsf = setprupsf(syunt, frend, calcMd);
						sf = setUnitsf(syunt, frend, calcMd);
						int iBS = calcBS(unit, calcMd, sf, tapscoreup);
						int trBS = calcBS(unit, calcMd, trsf, tapscoreup);
						int prBS = calcBS(unit, calcMd, prsf, tapscoreup);
						int allBS = calcBS(unit, calcMd, tmpsf, tapscoreup);
						int exprtmp = allBS - prBS;
						int extrtmp = allBS - trBS;
						int exitmp = allBS -iBS;
						double dfBS = exitmp - extrtmp - exprtmp;//���ꂪ������ʕ��B
						dfBS /= counter;
						oefsz += dfBS *(calcCd.gaccut()/calcMd.gmusictm());//�V���N���Ȃ̂œ��ɐ��l��߂��K�v�Ȃ��B
					}else{

					/*
					if(calcCd.gptrick() == 1){
						tempsysf[k] = settrsf(syunt, frend, calcMd);
					}else{
						tempsysf[k] = setUnitsf(syunt, frend, calcMd);//�V���N������sf���i�[������B
					}
					*/
						tempsysf[k] = setUnitsf(syunt, frend, calcMd);
					}
				}
				if(clctrprupbl){
					oefsz /= calcs.size();
					scrupT[len] = (int)Math.floor(oefsz*(accut/calcMd.gmusictm())* calcMd.gmaxcb());
				}else{
					double tmp = 0.0;
					for (int k = 0; k < calcs.size(); k++) {
						tmp += calcBS(unit, calcMd, tempsysf[k], tapscoreup);//�V���N����������BS���v�Z����tmp�ɍ��Z����B
					}
					//���Z�������Ŋ���A���ς��o���B
					tmp -= calcBS(unit, calcMd, sf, tapscoreup)*calcs.size();//���̐��l�������B
					scrupT[len] = (int)Math.floor(tmp*(accut/calcMd.gmusictm())* calcMd.gmaxcb());
				}
			}else if(unit[len].gsksha().equals("���s�[�g")){
				Card_datas calcCd = unit[len];
				List<Card_datas> hits = new ArrayList<Card_datas>();
				for(Card_datas tmp : unit){
					if(tmp.getgrade().equals(calcCd.getgrade()) && tmp.getunitnm().equals(calcCd.getunitnm()) && !(tmp.equals(calcCd))){
						hits.add(tmp);
					}
				}
				for(Card_datas tmp:hits){
					if(tmp.equals(calcCd)){
						System.out.println("���Z���s�[�g�̃X�R�A�A�b�v���Ғl�v�Z�ɂ����āA�\�����ʃG���[���������܂����B");
						String err = new String();
						throw new NullPointerException(err);
					}
				}
				double tmpexps = 0.0;
				for(int k = 0;k < hits.size();k++){
					tmpexps += setsklexp(hits.get(k), calcMd, perper, sf, unit, frend, tapscoreup);
				}
				tmpexps /= hits.size();
				double oefsz = tmpexps;
				scrupT[len] = (int)Math.floor(oefsz);
			}
		}
		double tempprob = 0.0;
		double tempsprob = 0.0;
		double sumprob = 0.0;//�ϕ��ς݂̓񍀕��z���猟������B
		long steps = 0;
		int[] tmppivot = new int[9];
		int[] regularpivot = new int[9];
		int[] regurarpivot_pointer = new int[9];
		int tempscr = 0;
		int regularscr = 0;
		double regularprob = 0.0;
		/*
		int count = 0;
		for(int temp : scrupT){
			System.out.println("scrupT["+count+"]:"+unit[count].getname()+":"+scrupT[count]);
			count++;
		}
		*/
		for(int len = 0;len < unit.length;len++){
			regurarpivot_pointer[len] = len;
		}

		/*
		 * �m���Ń\�[�g�B0�Ԗڂ���Ԕ����m���������Ȃ�悤�Ƀ��j�b�g���\�[�g����B
		 * pivot�𐧌䂷��K�v������̂�pointer��ǉ��B
		 * ���̑��m�����z���i�[���Ă�z��Ȃǂ��ꍇ�ɂ���Ă�nullpo���������˂Ȃ��̂Ń\�[�g����B
		 */
		for(int i = 0;i < unit.length;i++){
			for(int k = unit.length-1;k > i;k--){
				//�R�s�[���쐬�B
				Card_datas temp = unit[i];
				int pointer = regurarpivot_pointer[i];
				double[] prbs = unitprbs[i];
				double[] sprbs = unitsprbs[i];
				double[] upprbs = untupprbs[i];
				double[] upsprbs = untupsprbs[i];
				int actcnts = expactcnts[i];
				if(temp.gprob() < unit[k].gprob()){
					/*
					 * �傫�����̓��͏�������
					 * unit[i] = unit[k];
					 */
					regurarpivot_pointer[i] = k;
					unitprbs[i] = unitprbs[k];
					unitsprbs[i] = unitsprbs[k];
					expactcnts[i] = expactcnts[k];
					untupprbs[i] = untupprbs[k];
					untupsprbs[i] = untupsprbs[k];
					/*
					 * ���������̓��͏������� �R�s�[�����B
					 * unit[k] = temp;
					 */
					regurarpivot_pointer[k] = pointer;
					unitprbs[k] = prbs;
					unitsprbs[k] = sprbs;
					expactcnts[k] = actcnts;
					untupprbs[k] = upprbs;
					untupsprbs[k] = upsprbs;
				}
			}
		}
		int[] lanelength = new int[9];
		for (int len = 0; len < unit.length; len++) {
			lanelength[len] = unitprbs[len].length - 1;
			if (scrupT[len] == 0) {
				for (int k = 0; k < unitsprbs[len].length; k++) {
					unitsprbs[len][k] = 1;
					unitprbs[len][k] = 1;
				}
				lanelength[len] = 1;
				expactcnts[len] = depth;
			}
		}

		for(int alane = lanelength[0];alane >= 0;alane--){
			if(unitsprbs[0][alane] > discriminant && alane >= expactcnts[0] - depth){
				tempprob = unitprbs[0][alane];
				tempsprob = unitsprbs[0][alane];
				for(int blane = lanelength[1];blane >= 0;blane--){
					if(tempsprob * unitsprbs[1][blane] > discriminant && blane >= expactcnts[1] - depth && unitsprbs[1][blane] > discriminant){
						tempprob *= unitprbs[1][blane];
						tempsprob *= unitsprbs[1][blane];
						for(int clane = lanelength[2];clane >= 0;clane--){
							if(tempsprob * unitsprbs[2][clane] > discriminant && clane >= expactcnts[2] - depth && unitsprbs[2][clane] > discriminant){
								tempprob *= unitprbs[2][clane];
								tempsprob *= unitsprbs[2][clane];
								for(int dlane = lanelength[3];dlane >= 0;dlane--){
									if(tempsprob * unitsprbs[3][dlane] > discriminant && dlane >= expactcnts[3] - depth && unitsprbs[3][dlane] > discriminant){
										tempprob *= unitprbs[3][dlane];
										tempsprob *= unitsprbs[3][dlane];
										for(int elane = lanelength[4];elane >= 0;elane--){
											if(tempsprob * unitsprbs[4][elane] > discriminant && elane >= expactcnts[4] - depth && unitsprbs[4][elane] > discriminant){
												tempprob *= unitprbs[4][elane];
												tempsprob *= unitsprbs[4][elane];
												for(int flane = lanelength[5];flane >= 0;flane--){
													if(tempsprob * unitsprbs[5][flane] > discriminant && flane >= expactcnts[5] - depth && unitsprbs[5][flane] > discriminant){
														tempprob *= unitprbs[5][flane];
														tempsprob *= unitsprbs[5][flane];
														for(int glane = lanelength[6];glane >= 0;glane--){
															if(tempsprob * unitsprbs[6][glane] > discriminant && glane >= expactcnts[6] - depth && unitsprbs[6][glane] > discriminant){
																tempprob *= unitprbs[6][glane];
																tempsprob *= unitsprbs[6][glane];
																for(int hlane = lanelength[7];hlane >= 0;hlane--){
																	if(tempsprob * unitsprbs[7][hlane] > discriminant && hlane >= expactcnts[7] - depth && unitsprbs[7][hlane] > discriminant){
																		tempprob *= unitprbs[7][hlane];
																		tempsprob *= unitsprbs[7][hlane];
																		for(int ilane = lanelength[8];ilane >= 0;ilane--){
																			//pivot�̏������āA�X�R�A��r�B
																			tempprob *= unitprbs[8][ilane];
																			sumprob += tempprob;
																			steps++;
																			tempprob /= unitprbs[8][ilane];
																			if(sumprob > discriminant && unitsprbs[8][ilane] > discriminant && ilane >= expactcnts[8] - depth){
																				tmppivot[regurarpivot_pointer[0]] = alane;
																				tmppivot[regurarpivot_pointer[1]] = blane;
																				tmppivot[regurarpivot_pointer[2]] = clane;
																				tmppivot[regurarpivot_pointer[3]] = dlane;
																				tmppivot[regurarpivot_pointer[4]] = elane;
																				tmppivot[regurarpivot_pointer[5]] = flane;
																				tmppivot[regurarpivot_pointer[6]] = glane;
																				tmppivot[regurarpivot_pointer[7]] = hlane;
																				tmppivot[regurarpivot_pointer[8]] = ilane;
																				for(int len = 0;len < unit.length;len++){
																					tempscr += scrupT[len] * tmppivot[len];
																				}
																				//System.out.println("tempscr is "+tempscr);
																				if(tempscr > regularscr){
																					regularscr = tempscr;
																					regularpivot[regurarpivot_pointer[0]] = alane;
																					regularpivot[regurarpivot_pointer[1]] = blane;
																					regularpivot[regurarpivot_pointer[2]] = clane;
																					regularpivot[regurarpivot_pointer[3]] = dlane;
																					regularpivot[regurarpivot_pointer[4]] = elane;
																					regularpivot[regurarpivot_pointer[5]] = flane;
																					regularpivot[regurarpivot_pointer[6]] = glane;
																					regularpivot[regurarpivot_pointer[7]] = hlane;
																					regularpivot[regurarpivot_pointer[8]] = ilane;
																					regularprob = sumprob;
																				}
																				tempscr = 0;
																			}
																		}
																	}else{
																		steps += unitprbs[8].length;
																		sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane] * unitsprbs[5][flane] * unitsprbs[6][glane] * unitsprbs[7][hlane];
																		tempprob = unitprbs[0][alane] * unitprbs[1][blane] * unitprbs[2][clane] * unitprbs[3][dlane] * unitprbs[4][elane] * unitprbs[5][flane] * unitprbs[6][glane];
																		tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane] * unitsprbs[5][flane] * unitsprbs[6][glane];
																	}
																}
															}else{
																steps += unitprbs[7].length * unitprbs[8].length;
																sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane] * unitsprbs[5][flane] * unitsprbs[6][glane];
																tempprob = unitprbs[0][alane] * unitprbs[1][blane] * unitprbs[2][clane] * unitprbs[3][dlane] * unitprbs[4][elane] * unitprbs[5][flane];
																tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane] * unitsprbs[5][flane];
															}
														}
													}else{
														steps += unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
														sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane] * unitsprbs[5][flane];
														tempprob = unitprbs[0][alane] * unitprbs[1][blane] * unitprbs[2][clane] * unitprbs[3][dlane] * unitprbs[4][elane];
														tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane];
													}
												}
											}else{
												steps += unitprbs[5].length * unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
												sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane] * unitsprbs[4][elane];
												tempprob = unitprbs[0][alane] * unitprbs[1][blane] * unitprbs[2][clane] * unitprbs[3][dlane];
												tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane];
											}
										}
									}else{
										steps += unitprbs[4].length * unitprbs[5].length * unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
										sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane] * unitsprbs[3][dlane];
										tempprob = unitprbs[0][alane] * unitprbs[1][blane] * unitprbs[2][clane];
										tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane];
									}
								}
							}else{
								steps += unitprbs[3].length * unitprbs[4].length * unitprbs[5].length * unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
								sumprob = unitsprbs[0][alane] * unitsprbs[1][blane] * unitsprbs[2][clane];
								tempprob = unitprbs[0][alane] * unitprbs[1][blane];
								tempsprob = unitsprbs[0][alane] * unitsprbs[1][blane];
							}
						}
					}else{
						steps += unitprbs[2].length * unitprbs[3].length * unitprbs[4].length * unitprbs[5].length * unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
						sumprob = unitsprbs[0][alane] * unitsprbs[1][blane];
						tempprob = unitprbs[0][alane];
						tempsprob = unitsprbs[0][alane];
					}
				}
			}else{
				steps += unitprbs[1].length * unitprbs[2].length * unitprbs[3].length * unitprbs[4].length * unitprbs[5].length * unitprbs[6].length * unitprbs[7].length * unitprbs[8].length;
				sumprob = unitsprbs[0][alane];
				tempsprob = 0.0;
				tempprob = 0.0;
			}
		}

		for(int len = 0;len < unit.length;len++){
			unit[len].sactcnt(regularpivot[len]);
		}
		if(upprbsbl){
			for(int len = 0;len < unit.length;len++){
				lanelength[len] = untupprbs[len].length - 1;
				if(scrupT[len] == 0){
					lanelength[len] = 1;
					expactcnts[len] = depth;
					for(int k = 0;k < untupsprbs[len].length;k++){
						untupprbs[len][k] = 1;
						untupsprbs[len][k] = 1;
					}
				}
			}
		}

		int upregularscr = 0;
		double upedsumprob = 0.0;
		if(upprbsbl){
			for (int alane = lanelength[0]; alane >= 0; alane--) {
				if (untupprbs[0][alane] > discriminant && alane >= expactcnts[0] - depth) {
					tempprob = untupprbs[0][alane];
					tempsprob = untupsprbs[0][alane];
					for (int blane = lanelength[1]; blane >= 0; blane--) {
						if (tempsprob * untupsprbs[1][blane] > discriminant && blane >= expactcnts[1] - depth
								&& untupsprbs[1][blane] > discriminant) {
							tempprob *= untupprbs[1][blane];
							tempsprob *= untupsprbs[1][blane];
							for (int clane = lanelength[2]; clane >= 0; clane--) {
								if (tempsprob * untupsprbs[2][clane] > discriminant && clane >= expactcnts[2] - depth
										&& untupsprbs[2][clane] > discriminant) {
									tempprob *= untupprbs[2][clane];
									tempsprob *= untupsprbs[2][clane];
									for (int dlane = lanelength[3]; dlane >= 0; dlane--) {
										if (tempsprob * untupsprbs[3][dlane] > discriminant && dlane >= expactcnts[3] - depth
												&& untupsprbs[3][dlane] > discriminant) {
											tempprob *= untupprbs[3][dlane];
											tempsprob *= untupsprbs[3][dlane];
											for (int elane = lanelength[4]; elane >= 0; elane--) {
												if (tempsprob * untupsprbs[4][elane] > discriminant
														&& elane >= expactcnts[4] - depth
														&& untupsprbs[4][elane] > discriminant) {
													tempprob *= untupprbs[4][elane];
													tempsprob *= untupsprbs[4][elane];
													for (int flane = lanelength[5]; flane >= 0; flane--) {
														if (tempsprob * untupsprbs[5][flane] > discriminant
																&& flane >= expactcnts[5] - depth
																&& untupsprbs[5][flane] > discriminant) {
															tempprob *= untupprbs[5][flane];
															tempsprob *= untupsprbs[5][flane];
															for (int glane = lanelength[6]; glane >= 0; glane--) {
																if (tempsprob * untupsprbs[6][glane] > discriminant
																		&& glane >= expactcnts[6] - depth
																		&& untupsprbs[6][glane] > discriminant) {
																	tempprob *= untupprbs[6][glane];
																	tempsprob *= untupsprbs[6][glane];
																	for (int hlane = lanelength[7]; hlane >= 0; hlane--) {
																		if (tempsprob * untupsprbs[7][hlane] > discriminant
																				&& hlane >= expactcnts[7] - depth
																				&& untupsprbs[7][hlane] > discriminant) {
																			tempprob *= untupprbs[7][hlane];
																			tempsprob *= untupsprbs[7][hlane];
																			for (int ilane = lanelength[8]; ilane >= 0; ilane--) {
																				//pivot�̏������āA�X�R�A��r�B
																				tempprob *= untupprbs[8][ilane];
																				sumprob += tempprob;
																				steps++;
																				tempprob /= untupprbs[8][ilane];
																				if (sumprob > discriminant
																						&& untupsprbs[8][ilane] > discriminant
																						&& ilane >= expactcnts[8] - depth) {
																					tmppivot[regurarpivot_pointer[0]] = alane;
																					tmppivot[regurarpivot_pointer[1]] = blane;
																					tmppivot[regurarpivot_pointer[2]] = clane;
																					tmppivot[regurarpivot_pointer[3]] = dlane;
																					tmppivot[regurarpivot_pointer[4]] = elane;
																					tmppivot[regurarpivot_pointer[5]] = flane;
																					tmppivot[regurarpivot_pointer[6]] = glane;
																					tmppivot[regurarpivot_pointer[7]] = hlane;
																					tmppivot[regurarpivot_pointer[8]] = ilane;
																					for (int len = 0; len < unit.length; len++) {
																						tempscr += scrupT[len]
																								* tmppivot[len];
																					}
																					if (tempscr > upregularscr) {
																						upregularscr = tempscr;
																						regularpivot[regurarpivot_pointer[0]] = alane;
																						regularpivot[regurarpivot_pointer[1]] = blane;
																						regularpivot[regurarpivot_pointer[2]] = clane;
																						regularpivot[regurarpivot_pointer[3]] = dlane;
																						regularpivot[regurarpivot_pointer[4]] = elane;
																						regularpivot[regurarpivot_pointer[5]] = flane;
																						regularpivot[regurarpivot_pointer[6]] = glane;
																						regularpivot[regurarpivot_pointer[7]] = hlane;
																						regularpivot[regurarpivot_pointer[8]] = ilane;
																						upedsumprob = sumprob;
																					}
																					tempscr = 0;
																				}
																			}
																		} else {
																			steps += untupprbs[8].length;
																			sumprob = untupsprbs[0][alane]
																					* untupsprbs[1][blane]
																					* untupsprbs[2][clane]
																					* untupsprbs[3][dlane]
																					* untupsprbs[4][elane]
																					* untupsprbs[5][flane]
																					* untupsprbs[6][glane]
																					* untupsprbs[7][hlane];
																			tempprob = untupprbs[0][alane]
																					* untupprbs[1][blane]
																					* untupprbs[2][clane]
																					* untupprbs[3][dlane]
																					* untupprbs[4][elane]
																					* untupprbs[5][flane]
																					* untupprbs[6][glane];
																			tempsprob = untupsprbs[0][alane]
																					* untupsprbs[1][blane]
																					* untupsprbs[2][clane]
																					* untupsprbs[3][dlane]
																					* untupsprbs[4][elane]
																					* untupsprbs[5][flane]
																					* untupsprbs[6][glane];
																		}
																	}
																} else {
																	steps += untupprbs[7].length * untupprbs[8].length;
																	sumprob = untupsprbs[0][alane] * untupsprbs[1][blane]
																			* untupsprbs[2][clane] * untupsprbs[3][dlane]
																			* untupsprbs[4][elane] * untupsprbs[5][flane]
																			* untupsprbs[6][glane];
																	tempprob = untupprbs[0][alane] * untupprbs[1][blane]
																			* untupprbs[2][clane] * untupprbs[3][dlane]
																			* untupprbs[4][elane] * untupprbs[5][flane];
																	tempsprob = untupsprbs[0][alane] * untupsprbs[1][blane]
																			* untupsprbs[2][clane] * untupsprbs[3][dlane]
																			* untupsprbs[4][elane] * untupsprbs[5][flane];
																}
															}

														} else {
															steps += untupprbs[6].length * untupprbs[7].length
																	* untupprbs[8].length;
															sumprob = untupsprbs[0][alane] * untupsprbs[1][blane]
																	* untupsprbs[2][clane] * untupsprbs[3][dlane]
																	* untupsprbs[4][elane] * untupsprbs[5][flane];
															tempprob = untupprbs[0][alane] * untupprbs[1][blane]
																	* untupprbs[2][clane] * untupprbs[3][dlane]
																	* untupprbs[4][elane];
															tempsprob = untupsprbs[0][alane] * untupsprbs[1][blane]
																	* untupsprbs[2][clane] * untupsprbs[3][dlane]
																	* untupsprbs[4][elane];
														}
													}
												} else {
													steps += untupprbs[5].length * untupprbs[6].length
															* untupprbs[7].length * untupprbs[8].length;
													sumprob = untupsprbs[0][alane] * untupsprbs[1][blane]
															* untupsprbs[2][clane] * untupsprbs[3][dlane]
															* untupsprbs[4][elane];
													tempprob = untupprbs[0][alane] * untupprbs[1][blane]
															* untupprbs[2][clane] * untupprbs[3][dlane];
													tempsprob = untupsprbs[0][alane] * untupsprbs[1][blane]
															* untupsprbs[2][clane] * untupsprbs[3][dlane];
												}
											}
										} else {
											steps += untupprbs[4].length * untupprbs[5].length * untupprbs[6].length
													* untupprbs[7].length * untupprbs[8].length;
											sumprob = untupsprbs[0][alane] * untupsprbs[1][blane] * untupsprbs[2][clane]
													* untupsprbs[3][dlane];
											tempprob = untupprbs[0][alane] * untupprbs[1][blane] * untupprbs[2][clane];
											tempsprob = untupsprbs[0][alane] * untupsprbs[1][blane] * untupsprbs[2][clane];
										}
									}
								} else {
									steps += untupprbs[3].length * untupprbs[4].length * untupprbs[5].length
											* untupprbs[6].length * untupprbs[7].length * untupprbs[8].length;
									sumprob = untupsprbs[0][alane] * untupsprbs[1][blane] * untupsprbs[2][clane];
									tempprob = untupprbs[0][alane] * untupprbs[1][blane];
									tempsprob = untupsprbs[0][alane] * untupsprbs[1][blane];
								}
							}
						} else {
							steps += untupprbs[2].length * untupprbs[3].length * untupprbs[4].length * untupprbs[5].length
									* untupprbs[6].length * untupprbs[7].length * untupprbs[8].length;
							sumprob = untupsprbs[0][alane] * untupsprbs[1][blane];
							tempprob = untupprbs[0][alane];
							tempsprob = untupsprbs[0][alane];
						}
					}
				} else {
					steps += untupprbs[1].length * untupprbs[2].length * untupprbs[3].length * untupprbs[4].length
							* untupprbs[5].length * untupprbs[6].length * untupprbs[7].length * untupprbs[8].length;
					sumprob = untupsprbs[0][alane];
					tempprob = 0.0;
					tempsprob = 0.0;
				}
			}


			for (int len = 0; len < unit.length; len++) {
				unit[len].supactcnt(regularpivot[len]);
			}
			//�������A�b�v�����ׂĂ̎��Ԃɂ����Ĕ������Ă�Ƃ��̃X�R�A�Ȃ̂�musictm��accut(�������A�b�v����)������K�v������B

		}

		for(Card_datas sklup:unit){
			if(sklup.getskinm().equals("������")){
				double permusic = sklup.gaccut()/calcMd.gmusictm();
				int defscr = upregularscr - regularscr;
				upregularscr = (int)Math.floor(sklup.gactcnt()*defscr*permusic);
			}
		}
		Calc_data rtn_data = new Calc_data();
		rtn_data.setregular_probably(regularprob);
		rtn_data.setskill_up_score(regularscr);
		rtn_data.setbase_score(base_scr);
		rtn_data.set_up_regular_probably(upedsumprob);
		rtn_data.set_probably_up_skillupscore(upregularscr);
		rtn_data.setunit_pprty(Integer.parseInt(sf.split(",",0)[0]));
		rtn_data.setcenterskill_up_unit_pprty(Integer.parseInt(sf.split(",",0)[1]));

		return rtn_data;
	}

	public static int calcBS(Card_datas[] unit, Music_data cmdt, String sf, double tapscoreup){
		//�y�Ȃ̃^�b�v�X�R�A����ő�m�[�c�����|���Z���đS�^�b�v�X�R�A���o�͂��郁�\�b�h
		double rtnBS = 0;
		int intbase = Integer.parseInt(sf.split(",",0)[0]);
		double dBase = intbase/80.0;
		int[][] all_lanes = cmdt.glanes();
		for(int len = 0;len < unit.length;len++){
			double tempscr = lanescr(all_lanes[len], dBase, unit[len], cmdt, tapscoreup);
			rtnBS += tempscr;
		}

		return (int)Math.floor(rtnBS);
	}

	public static double lanescr(int[] note, double base, Card_datas card, Music_data music, double tapscoreup) {//calcBS�Ŏg�����\�b�h **�v�Z���I�ɂ�Base�͕s�K�v�ł���B
		double ln = 1.25;
		double pprty = 1.0;
		double unit = 1.0;
		if(music.gunttp().equals(card.getunitnm())){
			unit = 1.1;
		}
		if(music.gpprty().equals(card.gpprty())){
			pprty = 1.1;
		}
		if(tapscoreup == 0.0){
			tapscoreup = 1.0;
		}
		base = base*unit*pprty*tapscoreup;
		if(note.length == 10){// <- Easy �` Expert�܂ł̕��ʃf�[�^�̏����D
			return note[0]*Math.floor(base) + note[1]*Math.floor(ln*base)+note[2]*Math.floor(1.10 * base) + note[3]*Math.floor(1.10*ln*base)
					+ note[4]*Math.floor(1.15*base) + note[5]*Math.floor( 1.15 * ln * base) + note[6]*Math.floor(1.20 * base)
					+ note[7]*Math.floor(1.20 * ln * base) + note[8]*Math.floor(1.25 * base) + note[9]*Math.floor(1.25 * ln * base);
		}else if(note.length == 14){// <- Master�̏����̏����D���h�t�F�X�ɂ��Ή��\�H
			return note[0]*Math.floor(base) + note[1]*Math.floor(ln*base)+note[2]*Math.floor(1.10 * base) + note[3]*Math.floor(1.10*ln*base)
					+ note[4]*Math.floor(1.15*base) + note[5]*Math.floor( 1.15 * ln * base) + note[6]*Math.floor(1.20 * base)
					+ note[7]*Math.floor(1.20 * ln * base) + note[8]*Math.floor(1.25 * base) + note[9]*Math.floor(1.25 * ln * base)
					+ note[10]*Math.floor(1.30 * base) + note[11]*Math.floor(1.30 * ln * base) + note[12]*Math.floor(1.35 * base) + note[13]*Math.floor(1.35 * base * ln);
		}else{
			System.err.println("�y�Ȃ̕��ʃf�[�^���ǂݍ��߂܂���ł����B");
			return 0;
		}
	}
}
