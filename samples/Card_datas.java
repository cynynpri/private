package samples;

import java.io.*;
import java.util.*;
import javafx.scene.control.*;

class Card_datas{
	private int cnum;//card nums
	private int sislt;//SIS's slot number
	private int skilv;//skill level
	private int factv;//If activate on skill
	private int prob;//skill prob
	private int efsz;//effect size
	private int csm;// Card's Smile size
	private int ccl;// Card's Cool size
	private int cpr;// Card's Pure size

	private double accut;//accracy time

	private String name;//Chara name
	private String pprty;//property
	private String rrity;//rarity
	private String skinm;//skill name
	private String sksha;//skill score or heal or accuracy time
	private String skitp;//skill type
	private String cskin;//center skill name
	private String acskn;//Add center skill name

	private String unitnm; //���j�b�g�� ex)��'s Aqours
	private String subuntnm; //�T�u���j�b�g�� ex) Printemps,lily white, BiBi, CYaRon!, Guilty Kiss, AZALEA
	private String grade;//�w�N 1�N, 2�N, 3�N
	private int expefsz;//�X�R�A�A�b�v���Ғl
	private int pkiss;//SIS�̃L�b�X private kiss
	private int ppfm;//SIS�̃p�t���[�� private perfume
	private int pring;//SIS�̃����O private ring
	private int pcross;//SIS�̃N���X private cross
	private int paura;//SIS�̃I�[�� private aura
	private int pveil;//SIS�̃��F�[�� private veil
	private int actcnt;//�X�L��������
	private int upactcnt;//�X�L��������(�������A�b�v���̂���)
	private int pcharm;//SIS�̃`���[�� private charm
	private int pheal;//SIS�̃q�[�� private heal
	private int ptrick;//SIS�̃g���b�N private trick
	private int pwink;//SIS�̃E�C���N private wink
	private int pimage;//�L��������SIS private character image item
	private int pbloom;//SIS�̃u���[�� private bloom
	private int ptrill;//SIS�̃g���� private trill
	private int pnnet;//SIS�̃m�l�b�g private nonette

	private Skill_data skillleveleffectsize;//���Z�̓��Z���x���u�[�X�g�ɑΉ����邽�߂̒ǉ��B
	/*private enum pprte{
		Smile("�X�}�C��"), Cool("�N�[��"), Pure("�s���A");
		private String pname;

		private pprte(String pname){
			this.pname = pname;
		}
	};*/

	private boolean awake;//awaked:True, don't awake:False


	public Card_datas(){
		super();
	}

	public Card_datas(int cnum, String name, String pprty, String rrity, String skinm, boolean awake, int sislt, int skilv, String sksha,String skitp, int factv, int prob, double accut, int efsz, int csm, int cpr, int ccl, String cskin, String acskn){
		this.cnum = cnum;
		this.name = name;
		this.pprty = pprty;
		this.rrity = rrity;
		this.skinm = skinm;
		this.awake = awake;
		this.sislt = sislt;
		this.skilv = skilv;
		this.sksha = sksha;
		this.skitp = skitp;
		this.factv = factv;
		this.prob = prob;
		this.accut = accut;
		this.efsz = efsz;
		this.csm = csm;
		this.ccl = ccl;
		this.cpr = cpr;
		this.cskin = cskin;
		this.acskn = acskn;
		//set grade
		if(name.equals("����ԗz")||name.equals("���ؖ�^�P")||name.equals("����z")||name.equals("�Ó��P�q")||name.equals("���V���r�B")||name.equals("���ؓc�Ԋ�")){
			grade = "1�N��";
		}else if(name.equals("�삱�Ƃ�")||name.equals("�����T��")||name.equals("���c�C��")||name.equals("�n�ӗj")||name.equals("���C���")||name.equals("�������q")){
			grade = "2�N��";
		}else if(name.equals("������")||name.equals("���V�ɂ�")||name.equals("�����G��")||name.equals("���V�_�C��")||name.equals("�����f�")||name.equals("���Y�ʓ�")){
			grade = "3�N��";
		}else{
			grade = "empty";
		}
		//set unitnm
		if(name.equals("����ԗz")||name.equals("���ؖ�^�P")||name.equals("����z")||name.equals("�삱�Ƃ�")||name.equals("�����T��")||name.equals("���c�C��")||name.equals("������")||name.equals("�����G��")||name.equals("���V�ɂ�")){
			unitnm = "��'s";
		}else if(name.equals("���V���r�B")||name.equals("���ؓc�Ԋ�")||name.equals("�Ó��P�q")||name.equals("�n�ӗj")||name.equals("���C���")||name.equals("�������q")||name.equals("�����f�")||name.equals("���V�_�C��")||name.equals("���Y�ʓ�")){
			unitnm = "Aqours";
		}else{
			unitnm ="empty";
		}
		//set subuntnm
		if(name.equals("����ԗz")||name.equals("�����T��")||name.equals("�삱�Ƃ�")){
			subuntnm = "Printemps";
		}else if(name.equals("����z")||name.equals("���c�C��")||name.equals("������")){
			subuntnm = "lily white";
		}else if(name.equals("���ؖ�^�P")||name.equals("���V�ɂ�")||name.equals("�����G��")){
			subuntnm = "BiBi";
		}else if(name.equals("���V���r�B")||name.equals("���C���")||name.equals("�n�ӗj")){
			subuntnm = "CYaRon�I";
		}else if(name.equals("�Ó��P�q")||name.equals("�������q")||name.equals("�����f�")){
			subuntnm = "Guilty Kiss";
		}else if(name.equals("���ؓc�Ԋ�")||name.equals("���Y�ʓ�")||name.equals("���V�_�C��")){
			subuntnm = "AZALEA";
		}else{
			subuntnm = "empty";
		}
	}

//=============================================================

	final public void scnum(int cnum){
		this.cnum = cnum;
	}

	final public int gcnum(){
		return cnum;
	}

//=============================================================
	final public void setname(String name){
		this.name = name;
	}

	final public String getname(){
		return name;
	}
//=============================================================
	final public void spprty(String pprty){
		this.pprty = pprty;
	}

	final public String gpprty(){
		return pprty;
	}
//=============================================================
	final public void srrity(String rrity){
		this.rrity = rrity;
	}

	final public String grrity(){
		return rrity;
	}
//=============================================================
	final public void setskinm(String skinm){
		this.skinm = skinm;
	}

	final public String getskinm(){
		return skinm;
	}
//=============================================================
	final public void sawake(boolean awake){
		this.awake = awake;
	}

	final public boolean gawake(){
		return awake;
	}

	final public String gsawk(){
		//get string awake
		String rtnawk = "";

		if(awake){
			rtnawk = "�o��";
		}else{
			rtnawk = "���o��";
		}

		return rtnawk;
	}

	final public void sstrawake(String strawake){
		Boolean setawake = true;
		setawake = chkrawake(strawake);
		awake = setawake;
	}

	//if you set string awake, you check ctrl+f "chkrawake"; plz.
//=============================================================
	final public void ssislt(int sislt){
		this.sislt = sislt;
	}

	final public int gsislt(){
		return sislt;
	}
//=============================================================
	final public void sskilv(int skilv){
		this.skilv = skilv;
	}

	final public int gskilv(){
		return skilv;
	}
//=============================================================
	final public void ssksha(String sksha){
		this.sksha = sksha;
	}

	final public String gsksha(){
		return sksha;
	}
//=============================================================
	final public void sskitp(String skitp){
		this.skitp = skitp;
	}

	final public String gskitp(){
		return skitp;
	}
//=============================================================
	final public void sfactv(int factv){
		this.factv = factv;
	}

	final public int gfactv(){
		return factv;
	}
//=============================================================
	final public void sprob(int prob){
		this.prob = prob;
	}

	final public int gprob(){
		return prob;
	}
//=============================================================
	final public void saccut(double accut){
		this.accut = accut;
	}

	final public double gaccut(){
		return accut;
	}
//=============================================================
	final public void sefsz(int efsz){
		this.efsz = efsz;
	}

	final public int gefsz(){
		return efsz;
	}
//=============================================================
	final public void scsm(int csm){
		this.csm = csm;
	}

	final public int gcsm(){
		return csm;
	}
//=============================================================
	final public void sccl(int ccl){
		this.ccl = ccl;
	}

	final public int gccl(){
		return ccl;
	}
//=============================================================
	final public void scpr(int cpr){
		this.cpr = cpr;
	}

	final public int gcpr(){
		return cpr;
	}
//=============================================================
	final public void setcskin(String cskin){
		this.cskin = cskin;
	}

	final public String getcskin(){
		return cskin;
	}
//=============================================================
	final public void setacskn(String acskn){
		this.acskn = acskn;
	}

	final public String getacskn(){
		return acskn;
	}
//=============================================================
	final public void setunitnm(String unitnm){
		this.unitnm = unitnm;
	}

	final public String getunitnm(){
		return unitnm;
	}
//=============================================================
	final public void setsubuntnm(String subuntnm){
		this.subuntnm = subuntnm;
	}

	final public String getsubuntnm(){
		return subuntnm;
	}
//=============================================================
	final public void setgrade(String grade){
		this.grade = grade;
	}

	final public String getgrade(){
		return grade;
	}

	final public int getparseGrade(){
		if(grade.indexOf("1") != -1){
			return 1;
		}else if(grade.indexOf("2") != -1){
			return 2;
		}else if(grade.indexOf("3") != -1){
			return 3;
		}else{
			return -1;
		}
	}
//=============================================================
	final public void setexpefsz(int expefsz){
		this.expefsz = expefsz;
	}

	final public int getexpefsz(){
		return expefsz;
	}
//=============================================================
	final public void spkiss(int pkiss){
		this.pkiss = pkiss;
	}

	final public int gpkiss(){
		if(pkiss <= 1 && pkiss >= 0){
			return pkiss;
		}
		return -1;
	}
//=============================================================
	final public void sppfm(int ppfm){
		this.ppfm = ppfm;
	}

	final public int gppfm(){
		if(ppfm <= 1 && ppfm >= 0){
			return ppfm;
		}
		return -1;
	}
//=============================================================
	final public void spring(int pring){
		this.pring = pring;
	}

	final public int gpring(){
		if(pring <= 1 && pring >= 0){
			return pring;
		}
		return -1;
	}
//=============================================================
	final public void spaura(int paura){
		this.paura = paura;
	}

	final public int gpaura(){
		if(paura <= 1 && paura >= 0){
			return paura;
		}
		return -1;
	}
//=============================================================
	final public void spcross(int pcross){
		this.pcross = pcross;
	}

	final public int gpcross(){
		if(pcross <= 1 && pcross >= 0){
			return pcross;
		}
		return -1;
	}
//=============================================================
	final public void spveil(int pveil){
		this.pveil = pveil;
	}

	final public int gpveil(){
		if(pveil <= 1 && pveil >= 0){
			return pveil;
		}
		return -1;
	}
//=============================================================
	final public void sactcnt(int actcnt){
		this.actcnt = actcnt;
	}

	final public int gactcnt(){
		return actcnt;
	}
//=============================================================
	final public void supactcnt(int upactcnt){
		this.upactcnt = upactcnt;
	}

	final public int gupactcnt(){
		return upactcnt;
	}
//=============================================================
	final public void spcharm(int pcharm){
		this.pcharm = pcharm;
	}

	final public int gpcharm(){
		if (pcharm <= 1 && pcharm >= 0) {
			return pcharm;
		}
		return -1;
	}
//=============================================================
	final public void spheal(int pheal){
		this.pheal = pheal;
	}

	final public int gpheal(){
		if (pheal <= 1 && pheal >= 0) {
			return pheal;
		}
		return -1;
	}
//=============================================================
	final public void sptrick(int ptrick){
		this.ptrick = ptrick;
	}

	final public int gptrick(){
		if(ptrick <= 1 && ptrick >= 0){
			return ptrick;
		}
		return -1;
	}
//=============================================================
	final public void spwink(int pwink){
		this.pwink = pwink;
	}

	final public int gpwink(){
		if(pwink <= 1 && pwink >= 0){
			return pwink;
		}
		return -1;
	}
//=============================================================
	final public void spimage(int pimage){
		this.pimage = pimage;
	}

	final public int gpimage(){
		if(pimage <= 1 && pimage >= 0){
			return pimage;
		}
		return -1;
	}
//=============================================================
	final public void spbloom(int pbloom){
		this.pbloom = pbloom;
	}

	final public int gpbloom(){
		if(pbloom <= 1 && pbloom >= 0){
			return pbloom;
		}
		return -1;
	}
//=============================================================
	final public void sptrill(int ptrill){
		this.ptrill = ptrill;
	}

	final public int gptrill(){
		if(ptrill >= 0 && ptrill <= 1){
			return ptrill;
		}
		return -1;
	}
//=============================================================
	final public void spnnet(int pnnet){
		this.pnnet = pnnet;
	}

	final public int gpnnet(){
		if(pnnet <= 1 && pnnet >= 0){
			return pnnet;
		}
		return -1;
	}
//=============================================================
	final public void setskillleveleffectsize(Skill_data slef){
		skillleveleffectsize = slef;
	}

	final public Skill_data getskillleveleffectsize(){
		return skillleveleffectsize;
	}
//--------------------------------------------------------------
//�ȏオ�P����getter��setter.�ȉ��͎��ۂɎg�p����getter��setter.
	final public void setlisttoskilldata(Skill_data[] sdata){
		boolean matchbl = false;
		for(Skill_data temp : sdata){
			if(temp.getskinm().equals(skinm)){
				setskillleveleffectsize(temp);
				matchbl = true;
			}
		}
		if(!matchbl){
			System.err.println("�X�L���f�[�^�̃Z�b�g�Ɏ��s���܂����B");
			System.err.print("�X�L���f�[�^�ɂ�null�����܂��B(NullPointerException�ŗ������ꍇ�́Z�Zsd.csv�̃f�[�^�����������̂��m�F���Ă��������B)");
			setskillleveleffectsize(null);
		}
	}
	//���Z���x���u�[�X�g�ɑΉ����邽�߂̃��\�b�h
	final public double[] getskilldatatoefsz(Card_datas boostcard){
		double[] leveluptoefsz = new double[2];
		int boosted_skill_level = skilv + boostcard.gefsz();
		String arraystr = Skill_read.getefsz(boosted_skill_level, skillleveleffectsize);
		if(arraystr.indexOf(",") != -1){
			String[] intdoubles = arraystr.split(",",0);
			leveluptoefsz[0] = Double.parseDouble(intdoubles[0]);
			leveluptoefsz[1] = Double.parseDouble(intdoubles[1]);
		}else{
			try{
				leveluptoefsz[0] = Integer.parseInt(arraystr);
				leveluptoefsz[1] = 0.0;//This is error_code. accut is not less than zero.
			}catch(NumberFormatException e){
				leveluptoefsz[0] = 0.0;//This is error code. efsz is not less than zero.
				leveluptoefsz[1] = Double.parseDouble(arraystr);
			}
		}
		return leveluptoefsz;//leveluptoefsz[0]��int efsz. leveluptoefsz[1]��double accut.�ɑΉ�����B
	}
	//���Z���x���u�[�X�g��̃��j�b�g��Ԃ����\�b�h�B
	final public Card_datas[] skilllevelboostedtoUnits(Card_datas[] unit, Card_datas boostcard){
		int len = 0;
		for(Card_datas temp : unit){
			if(!(temp.gsksha().equals(boostcard.gsksha()))){
				double[] tempdouble = temp.getskilldatatoefsz(boostcard);
				temp.sefsz((int)tempdouble[0]);
				temp.saccut(tempdouble[1]);
				unit[len] = temp;
			}
			len++;
		}
		return unit;
	}
//=============================================================
	public static String setchknms(int len){
		String rtnstr = new String();
		String[] checknms = new String[18];
		checknms[0] = "\"1.��T��";
		checknms[1] = "\"2.�G��";
		checknms[2] = "\"3.���Ƃ�";
		checknms[3] = "\"4.�C��";
		checknms[4] = "\"5.�z";
		checknms[5] = "\"6.�^�P";
		checknms[6] = "\"7.��";
		checknms[7] = "\"8.�ԗz";
		checknms[8] = "\"9.�ɂ�";
		checknms[9] = "\"11.���";
		checknms[10] = "\"12.���q";
		checknms[11] = "\"13.�ʓ�";
		checknms[12] = "\"14.�_�C��";
		checknms[13] = "\"15.�j";
		checknms[14] = "\"16.�P�q";
		checknms[15] = "\"17.�Ԋ�";
		checknms[16] = "\"18.�f�";
		checknms[17] = "\"19.���r�B";


		rtnstr = checknms[len];

		return rtnstr;
	}
	public static String setrnms(int len){
		String rtnstr = new String();
		String[] checknms = new String[18];

		checknms[0] = "�����T��";
		checknms[1] = "�����G��";
		checknms[2] = "�삱�Ƃ�";
		checknms[3] = "���c�C��";
		checknms[4] = "����z";
		checknms[5] = "���ؖ�^�P";
		checknms[6] = "������";
		checknms[7] = "����ԗz";
		checknms[8] = "���V�ɂ�";
		checknms[9] = "���C���";
		checknms[10] = "�������q";
		checknms[11] = "���Y�ʓ�";
		checknms[12] = "���V�_�C��";
		checknms[13] = "�n�ӗj";
		checknms[14] = "�Ó��P�q";
		checknms[15] = "���ؓc�Ԋ�";
		checknms[16] = "�����f�";
		checknms[17] = "���V���r�B";

		rtnstr = checknms[len];

		return rtnstr;
	}

	public static String setchkpty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = ",\"1.�X�}�C��";
		checkpty[1] = ",\"2.�s���A";
		checkpty[2] = ",\"3.�N�[��";

		rtnstr = checkpty[len];

		return rtnstr;
	}

	public static String setrpty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = "�X�}�C��";
		checkpty[1] = "�s���A";
		checkpty[2] = "�N�[��";

		rtnstr = checkpty[len];

		return rtnstr;
	}

	public static String setchkrty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = ",\"1.SR\"";
		checkpty[1] = ",\"2.SSR\"";
		checkpty[2] = ",\"3.UR\"";

		rtnstr = checkpty[len];

		return rtnstr;
	}

	public static String setrrty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = "SR";
		checkpty[1] = "SSR";
		checkpty[2] = "UR";

		rtnstr = checkpty[len];

		return rtnstr;
	}

	public static String setchksha(int len){
		String rtnstr = new String();
		String[] checksktp = new String[8];

		checksktp[0] = ",\"1.�X�R�A\"";
		checksktp[1] = ",\"2.����\"";
		checksktp[2] = ",\"3.��\"";
		checksktp[3] = ",\"4.�p�[�t�F�N�g\"";
		checksktp[4] = ",\"5.������\"";
		checksktp[5] = ",\"6.�p�����[�^�[\"";
		checksktp[6] = ",\"7.�V���N��\"";
		checksktp[7] = ",\"8.���s�[�g\"";

		rtnstr = checksktp[len];

		return rtnstr;
	}

	public static String setrsha(int len){
		String rtnstr = new String();
		String[] checkrsktp = new String[8];

		checkrsktp[0] = "�X�R�A";
		checkrsktp[1] = "����";
		checkrsktp[2] = "��";
		checkrsktp[3] = "�p�[�t�F�N�g";
		checkrsktp[4] = "������";
		checkrsktp[5] = "�p�����[�^�[";
		checkrsktp[6] = "�V���N��";
		checkrsktp[7] = "���s�[�g";


		rtnstr = checkrsktp[len];

		return rtnstr;
	}

	public static String setchksktp(int len){
		String rtnstr = new String();
		String[] checksktp = new String[7];

		checksktp[0] = ",\"1.�^�C�}�[\",";
		checksktp[1] = ",\"2.���Y���A�C�R��\",";
		checksktp[2] = ",\"3.�p�[�t�F�N�g\",";
		checksktp[3] = ",\"4.�R���{\",";
		checksktp[4] = ",\"5.�X�R�A\",";
		checksktp[5] = ",\"6.�X�^�[�A�C�R��\",";
		checksktp[6] = ",\"7.�`�F�C��\",";

		rtnstr = checksktp[len];

		return rtnstr;
	}

	public static String setrsktp(int len){
		String rtnstr = new String();
		String[] checksktp = new String[7];

		checksktp[0] = "�^�C�}�[";
		checksktp[1] = "���Y���A�C�R��";
		checksktp[2] = "�p�[�t�F�N�g";
		checksktp[3] = "�R���{";
		checksktp[4] = "�X�R�A";
		checksktp[5] = "�X�^�[�A�C�R��";
		checksktp[6] = "�`�F�C��";

		rtnstr = checksktp[len];

		return rtnstr;
	}

	public static String setchkcskn(String pprty,int len) {
		String rtnstr = new String();
		String[] chkcskn_s = new String[6];
		String[] chkcskn_p = new String[6];
		String[] chkcskn_c = new String[6];
		//chk ... CHecK
		//cskn ... Center SKill Name
		//_s or _p or _c ... Smile , Pure , Cool

		//chkcskn_s
		chkcskn_s[0] = ",\"1.�X�}�C���p���[\"";
		chkcskn_s[1] = ",\"2.�X�}�C���n�[�g\"";
		chkcskn_s[2] = ",\"3.�X�}�C���X�^�[\"";
		chkcskn_s[3] = ",\"4.�X�}�C���v�����Z�X\"";
		chkcskn_s[4] = ",\"5.�X�}�C���G���W�F��\"";
		chkcskn_s[5] = ",\"6.�X�}�C���G���v���X\"";

		//chkcskn_p
		chkcskn_p[0] = ",\"1.�s���A�p���[\"";
		chkcskn_p[1] = ",\"2.�s���A�n�[�g\"";
		chkcskn_p[2] = ",\"3.�s���A�X�^�[\"";
		chkcskn_p[3] = ",\"4.�s���A�v�����Z�X\"";
		chkcskn_p[4] = ",\"5.�s���A�G���W�F��\"";
		chkcskn_p[5] = ",\"6.�s���A�G���v���X\"";

		//chkcskn_c
		chkcskn_c[0] = ",\"1.�N�[���p���[\"";
		chkcskn_c[1] = ",\"2.�N�[���n�[�g\"";
		chkcskn_c[2] = ",\"3.�N�[���X�^�[\"";
		chkcskn_c[3] = ",\"4.�N�[���v�����Z�X\"";
		chkcskn_c[4] = ",\"5.�N�[���G���W�F��\"";
		chkcskn_c[5] = ",\"6.�N�[���G���v���X\"";

		if(pprty.equals("�X�}�C��")){
			rtnstr = chkcskn_s[len];
		} else if (pprty.equals("�s���A")){
			rtnstr = chkcskn_p[len];
		} else if (pprty.equals("�N�[��")){
			rtnstr = chkcskn_c[len];
		} else{
			rtnstr = null;
		}

		return rtnstr;
	}

	public static String setrcskin(String liner){
		String rtnrcskn = new String();
		String checkcskn = new String();
		String[] rcskn = new String[18];
		String[] chkcskn = new String[18];
		int len_s = 0;
		int lgh = 0;
		for(len_s = 0; len_s < 18; len_s++){
			if(len_s < 6){
				String bfs = setchkcskn("�X�}�C��", len_s);
				chkcskn[len_s] = bfs;
				lgh = bfs.length();
				if(lgh < 5){
					return rtnrcskn;
				}else{
					rcskn[len_s] = bfs.substring(4,lgh - 1);
				}
			}else if(6 <= len_s && len_s  < 12){
				String bfp = setchkcskn("�s���A", len_s - 6);
				chkcskn[len_s] = bfp;
				lgh = bfp.length();
				if(lgh < 5){
					return rtnrcskn;
				}else{
					rcskn[len_s] = bfp.substring(4, lgh - 1);
				}
			}else if(12 <= len_s && len_s < 18){
				String bfc = setchkcskn("�N�[��", len_s - 12);
				chkcskn[len_s] = bfc;
				lgh = bfc.length();
				if(lgh < 5){
					return rtnrcskn;
				}else{
					rcskn[len_s] = bfc.substring(4, lgh - 1);
				}
			}
			lgh = 0;
		}
		for(len_s = 0; len_s < 18; len_s++){
			checkcskn = chkcskn[len_s];
			 if(liner.indexOf(checkcskn) != -1){
				rtnrcskn = rcskn[len_s];
			 }
		}

		return rtnrcskn;
	}


	public static String setrname(String name){
		String rname = new String();
		String[] checknms = new String[18];
		String[] srnm = new String[18];
		for(int len = 0; len < 18; len++){
			checknms[len] = setchknms(len);
			srnm[len] = setrnms(len);
		}
		for(int len = 0; len < 18; len++){
			if(name.equals(checknms[len])){
				rname = srnm[len];
				return rname;
			} /* rname means regular name. */
		}
		return null;
	}
	public static String setrpprty(String pty){
		String rpty = new String();
		String[] checkpty = new String[3];
		String[] srpty = new String[3];
		for(int len = 0; len < 3; len++){
			checkpty[len] = setchkpty(len);
			srpty[len] = setrpty(len);
		}
		for(int len = 0; len < 3; len++){
			if(pty.equals(checkpty[len])){
				rpty = srpty[len];
				return rpty;
			} /* rpty means regular property. */
		}
		return pty;
	}

	public static String setrrrity(String rty){
		String rrty = new String();
		String[] checkrty = new String[3];
		String[] srrty = new String[3];
		for(int len = 0; len < 3; len++){
			checkrty[len] = setchkrty(len);
			srrty[len] = setrrty(len);
		}
		for(int len = 0; len < 3; len++){
			if(rty.equals(checkrty[len])){
				rrty = srrty[len];
				return rrty;
			} /* rrrty means regular rarity. */
		}
		return null;
	}

	public static String setrsksha(String sksha){
		String rsksha = new String();
		String[] checksha = new String[8];
		String[] srsha = new String[8];
		for(int len = 0; len < 8; len++){
			checksha[len] = setchksha(len);
			srsha[len] = setrsha(len);
		}
		for(int len = 0; len < 8; len++){
			if(sksha.equals(checksha[len])){
				rsksha = srsha[len];
				return rsksha;
			} /* rsksha means regular skill of score or heal or accuracy time. */
		}
		return null;
	}

	public static String setrskitp(String skitp){
		String rskitp = new String();
		String[] checksktp = new String[7];
		String[] srsktp = new String[7];
		for(int len = 0; len < 7; len++){
			checksktp[len] = setchksktp(len);
			srsktp[len] = setrsktp(len);
		}
		for(int len = 0; len < 7; len++){
			if(skitp.equals(checksktp[len])){
				rskitp = srsktp[len];
				return rskitp;
			} /* rskitp means regular skill type. */
		}
		return null;
	}

	public static String chksklnm(String sklnm){
		String beginstr = "R\",\"";
		String endstr = "\",\"1.��";
		String rtnstr = new String();
		int b = 0;
		int e = 0;

		b = sklnm.indexOf(beginstr);
		e = sklnm.indexOf(endstr);
		if(e == - 1 || e == 0){
			endstr = "\",\"2.�o";
			e = sklnm.indexOf(endstr);
			if(e == -1 || e == 0){
				return null;
			}
		}

		rtnstr = sklnm.substring(b, e);
		int leng = rtnstr.length();
		rtnstr = rtnstr.substring(4,leng);

		//System.out.println("rtnstr = " + rtnstr);

		return rtnstr;
	}

	public static boolean chkawake(String liner){
		String chkawk = "\",\"1.���o";
		boolean rtnb = true;
		if(liner.indexOf(chkawk) != -1){
			rtnb = false;
		}

		return rtnb;
	}

	public static boolean chkrawake(String liner){
		String chkawk = "���o";
		boolean rtnb = true;
		if(liner.indexOf(chkawk) != -1){
			rtnb = false;
		}

		return rtnb;
	}

	public static String setracskn(String liner, String rrity){
		String rtnacsn = new String();
		String bfs = new String();
		String[] chkacsn = new String[11];
		String[] racsn = new String[11];
		int bs = 0;//begin string
		int lgh = 0;
		int es = 0;//end of string
		if(rrity.equals("SR")){
			rtnacsn = "empty"; //This null is test. plz, deciding Strings setted.
			return rtnacsn;
		}
		chkacsn[0] = ",\"1.��'s\"";
		chkacsn[1] = ",\"2.Aqours\"";
		chkacsn[2] = ",\"3.1�N��\"";
		chkacsn[3] = ",\"4.2�N��\"";
		chkacsn[4] = ",\"5.3�N��\"";
		chkacsn[5] = ",\"6.BiBi\"";
		chkacsn[6] = ",\"7.lily white\"";
		chkacsn[7] = ",\"8.Printemps\"";
		chkacsn[8] = ",\"9.Guilty Kiss\"";
		chkacsn[9] = ",\"10.AZALEA\"";
		chkacsn[10] = ",\"11.CYaRon�I\"";
		for(int len = 0; len < 11; len++){
			rtnacsn = chkacsn[len];
			bfs = String.valueOf(len+1) + ".";
			lgh = bfs.length();
			bs = lgh + 2;
			es = rtnacsn.length() - 1;
			if(bs < es){
				racsn[len] = rtnacsn.substring(bs,es);
			}else {
				return "empty";
			}
			rtnacsn = "";
		}
		for(int len = 0; len < 11; len++){
			bfs = chkacsn[len];
			if(liner.indexOf(bfs) != -1){
				rtnacsn = racsn[len];
			}
		}
		return rtnacsn;
	}

	public static int chksklslot(String liner){
		String chkawk = "�o��\",";
		String chkskltype = ",\"1.�X�R�A\"";
		String cutl = new String();
		int b = liner.indexOf(chkawk);
		int e = liner.indexOf(chkskltype);
		int rtnskslt = 0;

		b = b + 4;
		int len = 0;
		while(e == -1){
			chkskltype = setchksha(len);
			e = liner.indexOf(chkskltype);
			len++;
			if(len > 7){
				//System.err.println("liner is "+liner);
				System.err.println("�f�[�^���擾�ł��܂���ł����B");
				return 0;
			}
		}
		cutl = liner.substring(b,e-2);
		rtnskslt = Integer.parseInt(cutl);

		return rtnskslt;
	}

	public static int chkskllv(String liner){
		String chkawk = "�o��\",";
		String chkskltype = ",\"1.�X�R�A\"";
		String cutl = new String();
		int b = liner.indexOf(chkawk);
		int e = liner.indexOf(chkskltype);
		int rtnsklv = 0;
		b = b + 4;
		int len = 0;
		while (e == -1) {
			chkskltype = setchksha(len);
			e = liner.indexOf(chkskltype);
			len++;
			if (len > 7) {
				//System.err.println("liner is " + liner);
				System.err.println("�f�[�^���擾�ł��܂���ł����B");
				return 0;
			}
		}
		cutl = liner.substring(b+2,e);
		rtnsklv = Integer.parseInt(cutl);

		return rtnsklv;
	}

	public static int[] chkintegers(String liner){
		String[] buffer_a = new String[7];
		//String chkliner = new String();
		int[] rtn_array = new int[7];
		int len = 0;
		int bs = 0;//Began String
		int es = 0;//End of String
		boolean bs_setted = false;
		if(liner.indexOf(",null,null,null") != -1){
			return rtn_array;
		}else if(liner.indexOf("{\"smile:") != -1){
			return rtn_array;
		}
		for(len = 0;len < 18;len++){
			if(len < 5){
				if(!bs_setted){
					bs = liner.indexOf(setchksktp(len));
					if (bs != -1 ) {
						bs += setchksktp(len).length();
						bs_setted = true;
					} else if (bs == -1 && len == 5) {
						return rtn_array;
					}
				}
			}
			if(0 <= len && len < 6 && es <= 0 ){
				//chkliner = setchkcskn("�X�}�C��", len);
				es = liner.indexOf(setchkcskn("�X�}�C��", len));
			}else if(6 <= len && len < 12 && es <= 0){
				//chkliner = setchkcskn("�N�[��", len-6);
				es = liner.indexOf(setchkcskn("�N�[��", len-6));
			}else if(12 <= len && len < 18 && es <= 0){
				//chkliner = setchkcskn("�s���A", len-12);
				es = liner.indexOf(setchkcskn("�s���A", len-12));
				if(len == 18 && es < bs){
					return rtn_array;
				}
			}
		}

		//�f�o�b�N�p
		//System.out.println(liner.substring(bs,es));
		/*try{
			chkliner = liner.substring(bs,es);
		}catch(StringIndexOutOfBoundsException e){
			return rtn_array;
		}
		buffer_a = chkliner.split(",", 7);*/
		try{
			buffer_a = liner.substring(bs,es).split(",",7);
		}catch(StringIndexOutOfBoundsException e){
			return rtn_array;
		}
		bs = 0;
		for(len = 0; len < 7; len++){
			//chkliner = buffer_a[len];
			if(len == 2){
				bs = 0;
			}else{
				//bs = Integer.parseInt(chkliner);
				try{
					bs = Integer.parseInt(buffer_a[len]);
				}catch(NumberFormatException e){
					double tmpdbl = Double.parseDouble(buffer_a[len]);
					bs = (int)(tmpdbl*100.0 - 100);
				}
			}
			rtn_array[len] = bs;
		}
		return rtn_array;
	}

	public static double chkdouble(String liner) {
		String[] buffer_a = new String[7];
		String chkliner = new String();
		int len = 0;
		int bs = 0;//Began String
		int es = 0;//End of String
		boolean bs_setted = false;
		double rtn_double = 0.0;
		if (liner.indexOf(",null,null,null") != -1) {
			return rtn_double;
		} else if (liner.indexOf("{\"smile:") != -1) {
			return rtn_double;
		}
		for (len = 0; len < 18; len++) {
			if (len < 5) {
				if (!bs_setted) {
					chkliner = setchksktp(len);
					bs = liner.indexOf(chkliner);
					if (bs != -1) {
						bs += chkliner.length();
						bs_setted = true;
					} else if (bs == -1 && len == 5) {
						return rtn_double;
					}
				}
			}
			if (0 <= len && len < 6 && es <= 0) {
				chkliner = setchkcskn("�X�}�C��", len);
				es = liner.indexOf(chkliner);
			} else if (6 <= len && len < 12 && es <= 0) {
				chkliner = setchkcskn("�N�[��", len - 6);
				es = liner.indexOf(chkliner);
			} else if (12 <= len && len < 18 && es <= 0) {
				chkliner = setchkcskn("�s���A", len - 12);
				es = liner.indexOf(chkliner);
				if (len == 18 && es < bs) {
					return rtn_double;
				}
			}
		}

		//�f�o�b�N�p
		//System.out.println(liner.substring(bs, es));

		try {
			chkliner = liner.substring(bs, es);
		} catch (StringIndexOutOfBoundsException e) {
			return rtn_double;
		}
		buffer_a = chkliner.split(",", 7);
		rtn_double = Double.parseDouble(buffer_a[2].toString());
		return rtn_double;
	}
//=============================================================
	public static Integer[] fixedSislt(String cskin, String rrity){
		//�o����Ԃ��ۂ�����r�h�r�̃X���b�g������͂��郁�\�b�h
		int iniSislt = 1;
		int maxSislt = 1;
		if(rrity.equals("UR")){
			iniSislt = 4;
			maxSislt = 8;
		}else if(rrity.equals("SSR")){
			iniSislt = 3;
			maxSislt = 6;
		}else if(rrity.equals("SR")){
			iniSislt = 2;
			maxSislt = 4;
		}else if(rrity.equals("UR") && cskin.equals("�N�[���p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("�X�}�C���p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("�s���A�p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}
		Integer[] rSislt = new Integer[maxSislt - iniSislt + 1];
		for(int i = 0; i < rSislt.length; i++){
			rSislt[i] = iniSislt + i;
		}
		return rSislt;
	}

	public static int[] fixedSislt(Card_datas cdata){
		//�o����Ԃ��ۂ�����r�h�r�̃X���b�g������͂��郁�\�b�h
		String cskin = cdata.getcskin();
		String rrity = cdata.grrity();
		int iniSislt = 1;
		int maxSislt = 1;
		if(rrity.equals("UR")){
			iniSislt = 4;
			maxSislt = 8;
		}else if(rrity.equals("SSR")){
			iniSislt = 3;
			maxSislt = 6;
		}else if(rrity.equals("SR")){
			iniSislt = 2;
			maxSislt = 4;
		}else if(rrity.equals("UR") && cskin.equals("�N�[���p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("�X�}�C���p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("�s���A�p���[")){
			iniSislt = 2;
			maxSislt = 2;
		}
		int[] rSislt = {iniSislt,maxSislt};
		return rSislt;
	}

//=============================================================
	public static String[] skill_list(String chara_name, String rrity)throws IOException{
		if(chara_name.equals("���c�C��")){
			String[] skill_allList = new String[Skill_data.mAx(chara_name)];
			skill_allList = Skill_data.sKillName(chara_name);
			if(rrity.equals("UR")){
				int[] ur_skillcard = {1,5,10,13,19,26,31,39,47,53,58};
				String[] skill_List = new String[ur_skillcard.length];
				for(int len = 0; len < ur_skillcard.length; len++){
					for(int i = 0; i < skill_allList.length; i++){
						if(i == ur_skillcard[len]){
							skill_List[len] = skill_allList[i];
						}
					}
				}
				return skill_List;
			}else if(rrity.equals("SSR")){
				int[] ssr_skillcard = {46,52,57};
				String[] skill_List = new String[ssr_skillcard.length];
				for(int len = 0; len < ssr_skillcard.length; len++){
					for(int i = 0; i < skill_allList.length; i++){
						if(i == ssr_skillcard[len]){
							skill_List[len] = skill_allList[i];
						}
					}
				}
				return skill_List;
			}else{
				int[] urssr_skillcard = {1,5,10,13,19,26,31,39,46,47,52,53,57,58};
				String[] skill_List = new String[skill_allList.length - urssr_skillcard.length];
				int sklistc = 0;
				for(int len = 0;len < urssr_skillcard.length;len++){
					for(int i = 0; i < skill_allList.length; i++){
						//������if���̎������Â������Ń��A���e�B����X�L�����Z�b�g�ł��Ȃ�
						if(i == urssr_skillcard[len]){
							skill_allList[i] = "Empty";
							//System.out.println(skill_allList[i]);
						}
					}
				}
				for(int i = 0; i < skill_allList.length; i++){
					if(!skill_allList[i].equals("Empty")){
						skill_List[sklistc] = skill_allList[i];
						sklistc++;
					}
				}
				return skill_List;
			}
		}

		return null;
	}
	public static String[] skill_Pprtylist(String pprty, String chara_name, String rrity)throws IOException{
		String[] skill_list = skill_list(chara_name,rrity);
		if(chara_name.equals("���c�C��")){
			if(pprty.equals("�X�}�C��")){
				if(rrity.equals("UR")){
					String[] rtn_skils = new String[4];
					rtn_skils[0] = skill_list[1];
					rtn_skils[1] = skill_list[5];
					rtn_skils[2] = skill_list[7];
					rtn_skils[3] = skill_list[10];

					return rtn_skils;
				}else if(rrity.equals("SSR")){
					String[] rtn_skils = new String[1];
					rtn_skils[0] = skill_list[2];

					return rtn_skils;
				}else if(rrity.equals("SR")){
					int[] sm_skillList = {5,6,8,10,13,18,20,23,25,29,33,34,36,39,42,45};//smile
					String[] rtn_skils = new String[sm_skillList.length];
					for(int len = 0; len < rtn_skils.length; len++){
						for(int i = 0; i <skill_list.length; i++){
							if(i == sm_skillList[len]){
								rtn_skils[len] = skill_list[i];
							}
						}
					}
					return rtn_skils;
				}
			}else if(pprty.equals("�s���A")){
				if(rrity.equals("UR")){
					String[] rtn_skils = new String[3];
					rtn_skils[0] = skill_list[2];
					rtn_skils[1] = skill_list[4];
					rtn_skils[2] = skill_list[8];

					return rtn_skils;
				}else if(rrity.equals("SSR")){
					String[] rtn_skils = new String[1];
					rtn_skils[0] = skill_list[1];

					return rtn_skils;
				}else if(rrity.equals("SR")){
					int[] pr_skillList = {3,4,7,11,12,16,19,21,26,28,31,32,37,38,40,43};//pure
					String[] rtn_skils = new String[pr_skillList.length];
					for(int len = 0; len < rtn_skils.length; len++){
						for(int i = 0; i <skill_list.length; i++){
							if(i == pr_skillList[len]){
								rtn_skils[len] = skill_list[i];
							}
						}
					}
					return rtn_skils;
				}
			}else if(pprty.equals("�N�[��")){
				if(rrity.equals("UR")){
					String[] rtn_skils = new String[4];
					rtn_skils[0] = skill_list[0];
					rtn_skils[1] = skill_list[3];
					rtn_skils[2] = skill_list[6];
					rtn_skils[3] = skill_list[9];

					return rtn_skils;
				}else if(rrity.equals("SSR")){
					String[] rtn_skils = new String[1];
					rtn_skils[0] = skill_list[0];

					return rtn_skils;
				}else if(rrity.equals("SR")){
					int[] cl_skillList = {0,1,2,9,14,15,17,22,24,27,30,35,41,44};//cool
					String[] rtn_skils = new String[cl_skillList.length];
					for(int len = 0; len < rtn_skils.length; len++){
						for(int i = 0; i <skill_list.length; i++){
							if(i == cl_skillList[len]){
								rtn_skils[len] = skill_list[i];
							}
						}
					}
					return rtn_skils;
				}
			}
		}
		return null;
	}

	public static int sfloor(double basescore){
		int rtn = 0;
		if(basescore > 0){
			rtn = (int)basescore;
		}
		if(rtn <= 0){
			System.err.println("�X�R�A�̌v�Z�l�����ɂȂ�܂���");
		}
		return rtn;
	}

//=============================================================================
//���Z���x���e�[�u�� ->Start 1203 to 1310
//=============================================================================
	public static Integer[] healTable(int iniheal, int finheal){
		Integer[] rtnTable = new Integer[8];

		if((finheal - iniheal) <= 2){
			for(int i = 0; i < 8;i++){
				rtnTable[i] = iniheal + (i/3);
			}
		}else{
			for(int i = 0; i < 8;i++){
				rtnTable[i] = iniheal + (i/2);
			}
		}

		return rtnTable;
	}

	public static Integer[] scrupTable(int iniupscr, int difupscr, int finupscr){
		Integer[] rtnTable = new Integer[8];
		for(int len = 0; len < 7; len++){
			rtnTable[len] = iniupscr + (difupscr*len);
		}
		rtnTable[7] = finupscr;

		return rtnTable;
	}

	public static Integer[] pprtyupTable(int iniupp, int finupp){
		//������������%UP������Ă����X�L���̃e�[�u����Ԃ����\�b�h
		Integer[] rtnTable = new Integer[8];
		if((finupp - iniupp) == 14){
			rtnTable[0] = iniupp;
			Integer[] diffT = {2,2,2,2,2,2,2};
			for(int i = 1;i<8;i++){
				rtnTable[i] = rtnTable[i-1] + diffT[i-1];
			}
		}/*else if((finupp - iniupp) == 14){//#1415 card.
			rtnTable[0] = iniupp;
			Integer[] diffT = {2,2,2,2,2,2,2};
			for(int i = 1;i<8;i++){
				rtnTable[i] = rtnTable[i-1] + diffT[i-1];
			}
		}*/else if((finupp - iniupp) == 21){//#1416 card.
			rtnTable[0] = iniupp;
			Integer[] diffT = {3,3,3,3,3,3,3};
			for(int i = 1;i<8;i++){
				rtnTable[i] = rtnTable[i-1] + diffT[i-1];
			}
		}else if((finupp - iniupp) == 22){
			rtnTable[0] = iniupp;
			Integer[] diffT = {4,3,3,3,3,3,3};
			for(int i = 1;i<8;i++){
				rtnTable[i] = rtnTable[i-1] + diffT[i-1];
			}
		}else if((finupp - iniupp) == 28){
			rtnTable[0] = iniupp;
			Integer[] diffT = {3,5,4,4,4,4,4};
			for(int i = 1;i<8;i++){
				rtnTable[i] = rtnTable[i-1] + diffT[i-1];
			}
		}else{
			rtnTable = null;
		}
		return rtnTable;
	}

	public static double[] accracyTable(double iniact, int intfin){
		double finact =  intfin/10.0;
		double[] rtnTable = new double[8];
		if(finact == 0){
			for(int len = 0; len < rtnTable.length; len++){
				rtnTable[len] = iniact;
			}
		}else if((finact - iniact) == 3.5){
			for(int len = 0; len < rtnTable.length; len++) {
				rtnTable[len] = iniact + 0.5 * len;
			}
		}else if((finact - iniact) == 4.0){
			for(int len = 0; len < rtnTable.length; len++){
				if((len%2) == 1 ){
					rtnTable[len] = iniact + 1.0*(int)((len+1)/2);
				}else if(len == 0){
					rtnTable[len] = iniact;
				}else{
					rtnTable[len] = rtnTable[len - 1];
				}
			}
		}else if((finact - iniact) == 2.0){
			for(int len = 0; len < rtnTable.length; len++){
				if((len%2) == 1 ){
					rtnTable[len] = iniact + 0.5*(int)((len+1)/2);
				}else if(len == 0){
					rtnTable[len] = iniact;
				}else{
					rtnTable[len] = rtnTable[len - 1];
				}
			}
		}else{
			for(int len = 0; len < rtnTable.length; len++){
				rtnTable[len] = iniact + 1.0*len;
			}
		}
		return rtnTable;
	}
//=============================================================================
//���Z���x���e�[�u�� ->End 1203 to 1310
//=============================================================================
	public static void toStringCard_datas(List<Card_datas> cdata, ComboBox<String> cb)throws DataNotFoundException{
		if(cb.getItems().size() != 0){
			cb.getItems().clear();
		}

		try{
			for(int len = 0;len < cdata.size()-1;len++){
				cb.getItems().add(cdata.get(len).gcnum() + ":" +cdata.get(len).getname()+ ":" + cdata.get(len).grrity()+ ":" +cdata.get(len).gpprty() + ":" + cdata.get(len).getskinm());
			}
		}catch(NullPointerException e){
			cb.getItems().clear();
			String err = new String();
			throw new DataNotFoundException(err);
		}
	}

	public static Card_datas setGuitoUnit(Card_datas unit, List<Card_datas> cdata ,ComboBox<String> cb){
		String[] bfcb = cb.getValue().split(":",0);
		int cnum = Integer.parseInt(bfcb[0]);
		unit = cdata.get(cnum-1);
		return unit;
	}

	public static String setskilltext(Card_datas card){
		String rtn_skilltext = new String();
		String tprchain = new String();
		String name = card.getname();

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
		//���X�g�r���[�ɕ\��������Z�̃e�L�X�g���o�͂���B
		if(card.gskitp().equals("���Y���A�C�R��")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "���Ƃ�";
		}else if(card.gskitp().equals("�^�C�}�[")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "�b���Ƃ�";
		}else if(card.gskitp().equals("�p�[�t�F�N�g")){
			rtn_skilltext = "PERFECT��" + card.gfactv() + "��B�����邲�Ƃ�";
		}else if(card.gskitp().equals("�R���{")){
			rtn_skilltext = card.gskitp() + card.gfactv() +"��B�����邲�Ƃ�";
		}else if(card.gskitp().equals("�X�R�A")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "�B�����Ƃ�";
		}else if(card.gskitp().equals("�X�^�[�A�C�R��")){
			rtn_skilltext = card.gskitp() + "PERFECT" + card.gfactv() + "�񂲂Ƃ�";
		}else if(card.gskitp().equals("�`�F�C��")){
			rtn_skilltext = "���g������" + tprchain + "�̓��Z�����ׂĔ��������";
		}

		rtn_skilltext = rtn_skilltext + card.gprob() + "%�̊m����";
		if(card.gsksha().equals("�X�R�A")){
			rtn_skilltext = rtn_skilltext + "�X�R�A��" + card.gefsz() + "������";
		}else if(card.gsksha().equals("��")){
			rtn_skilltext = rtn_skilltext + "�̗͂�" + card.gefsz() + "�񕜂���";
		}else if(card.gsksha().equals("����")){
			rtn_skilltext = rtn_skilltext + "���肪" + card.gaccut() + "�b���������";
		}else if(card.gsksha().equals("������")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "�b�ԑ��̓��Z�̔����m����1." + card.gefsz() + "�{�ɂȂ�";
		}else if(card.gsksha().equals("�p�����[�^�[")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "�b��" + tprchain + "�̑���P��" + card.gefsz() + "%UP����";
		}else if(card.gsksha().equals("�p�[�t�F�N�g")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "�b��PERFECT���̃^�b�vSCORE��" + card.gefsz() + "������";
		}else if(card.gsksha().equals("�V���N��")){
			rtn_skilltext = rtn_skilltext + card.gefsz() + "�b��" + tprchain + "�̂����ꂩ�Ɠ�������P�ɂȂ�";
		}else if(card.gsksha().equals("���s�[�g")){
			rtn_skilltext = rtn_skilltext + "���O�ɔ����������Z���s�[�g�ȊO�̓��Z���ʂ𔭓�";
		}
		return rtn_skilltext;
	}

	public static Card_datas[] csmsort(Card_datas[] fldt) {
		//GUITest������cdata���󂯎���cdata[0] = null;
		//cdata[1]����f�[�^�������Ă���B
		//���̌`���ɍ��킹�邽�߁A�\�[�g��sorted[0]�̃f�[�^��rtncdata[1]�ɏ��������K�v������B
		int max = fldt.length;
		Card_datas[] rfldt = new Card_datas[max - 1];
		for (int len = 0; len < max - 1; len++) {
			rfldt[len] = fldt[len + 1];
		}
		//�X�}�C���l�ł̃\�[�g��(�Ō�̔z�񂪍ő�)
		for (int len = 0; len < max - 1; ++len) {
			for (int j = len + 1; j < max - 1; ++j) {
				Card_datas temp = new Card_datas();
				temp = rfldt[len];
				if (temp.gcsm() <= rfldt[j].gcsm()) {
					rfldt[len] = rfldt[j];
					rfldt[j] = temp;
				}
			}
		}
		for (int len = 0; len < max - 1; len++) {
			fldt[len + 1] = rfldt[len];
		}
		//Card_read.print_cdata(fldt, fldt.length);

		return fldt;
	}
	//public static void main(String[] args)throws IOException{
		/*String[] skilist = skill_list("���c�C��","UR");
		for(int i = 0;i < skilist.length;i++){
			System.out.println(skilist[i]);
		}
		String[] ssrskilist = skill_list("���c�C��","SSR");
		for(int i = 0;i < ssrskilist.length;i++){
			System.out.println(ssrskilist[i]);
		}
		String[] srskilist = skill_list("���c�C��","SR");
		for(int i = 0;i < srskilist.length;i++){
			System.out.println(srskilist[i]);
		}
		String[] skilist = skill_Pprtylist("�s���A","���c�C��","SSR");*/
		//Integer[] sislt = fixedSislt("test","SR");
		/*Integer[] scrupTable = new Integer[8];
		scrupTable = scrupTable(600,280,2560);
		for(Integer elem : scrupTable){
			System.out.println(elem);
		}*/
		/*Integer[] healTable = new Integer[8];
		healTable = healTable(4,6);
		for(Integer prnt : healTable){
			System.out.println(prnt);
		}*/
		/*double[] accracyTable = accracyTable(5.5,9.5);
		for(double prnt : accracyTable){
			System.out.println(prnt);
		}*/
	//}

}
