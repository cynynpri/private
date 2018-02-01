package samples;

import java.io.*;
import java.util.*;

class Card_datas{
	private int cnum;
	private int sislt;
	private int skilv;
	private int factv;
	private int prob;
	private int efsz;
	private int csm;
	private int ccl;
	private int cpr;

	private double accut;

	private String name;
	private String pprty;
	private String rrity;
	private String skinm;
	private String sksha;
	private String skitp;
	private String cskin;
	private String acskn;

	private String unitnm;
	private String subuntnm;
	private String grade;
	private int expefsz;
	private int pkiss;//�r�h�r�̃L�b�X
	private int ppfm;//SIS�̃p�t���[��
	private int pring;//SIS�̃����O
	private int pcross;//SIS�̃N���X
	private int paura;//SIS�̃I�[��
	private int pveil;//SIS�̃��F�[��
	private int[] actcnt;
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

	public void scnum(int cnum){
		this.cnum = cnum;
	}

	public int gcnum(){
		return cnum;
	}

//=============================================================
	public void setname(String name){
		this.name = name;
	}

	public String getname(){
		return name;
	}
//=============================================================
	public void spprty(String pprty){
		this.pprty = pprty;
	}

	public String gpprty(){
		return pprty;
	}
//=============================================================
	public void srrity(String rrity){
		this.rrity = rrity;
	}
	public String grrity(){
		return rrity;
	}
//=============================================================
	public void setskinm(String skinm){
		this.skinm = skinm;
	}
	public String getskinm(){
		return skinm;
	}
//=============================================================
	public void sawake(boolean awake){
		this.awake = awake;
	}
	public boolean gawake(){
		return awake;
	}

	public String gsawk(){
		//get string awake
		String rtnawk = "";

		if(awake){
			rtnawk = "�o��";
		}else{
			rtnawk = "���o��";
		}

		return rtnawk;
	}

	public void sstrawake(String strawake){
		/*String chkawk = "���o��";
		Boolean setawake = true;
		if(chkawk.equals(strawake)){
			setawake = false;
		}*/
		Boolean setawake = true;

		setawake = chkrawake(strawake);
		awake = setawake;
	}

	//if you set string awake, you check ctrl+f "chkrawake"; plz.
//=============================================================
	public void ssislt(int sislt){
		this.sislt = sislt;
	}
	public int gsislt(){
		return sislt;
	}
//=============================================================
	public void sskilv(int skilv){
		this.skilv = skilv;
	}
	public int gskilv(){
		return skilv;
	}
//=============================================================
	public void ssksha(String sksha){
		this.sksha = sksha;
	}
	public String gsksha(){
		return sksha;
	}
//=============================================================
	public void sskitp(String skitp){
		this.skitp = skitp;
	}
	public String gskitp(){
		return skitp;
	}
//=============================================================
	public void sfactv(int factv){
		this.factv = factv;
	}
	public int gfactv(){
		return factv;
	}
//=============================================================
	public void sprob(int prob){
		this.prob = prob;
	}
	public int gprob(){
		return prob;
	}
//=============================================================
	public void saccut(double accut){
		this.accut = accut;
	}
	public double gaccut(){
		return accut;
	}
//=============================================================
	public void sefsz(int efsz){
		this.efsz = efsz;
	}
	public int gefsz(){
		return efsz;
	}
//=============================================================
	public void scsm(int csm){
		this.csm = csm;
	}
	public int gcsm(){
		return csm;
	}
//=============================================================
	public void sccl(int ccl){
		this.ccl = ccl;
	}
	public int gccl(){
		return ccl;
	}
//=============================================================
	public void scpr(int cpr){
		this.cpr = cpr;
	}
	public int gcpr(){
		return cpr;
	}
//=============================================================
	public void setcskin(String cskin){
		this.cskin = cskin;
	}
	public String getcskin(){
		return cskin;
	}
//=============================================================
	public void setacskn(String acskn){
		this.acskn = acskn;
	}
	public String getacskn(){
		return acskn;
	}
//=============================================================
	public void setunitnm(String unitnm){
		this.unitnm = unitnm;
	}
	public String getunitnm(){
		return unitnm;
	}
//=============================================================
	public void setsubuntnm(String subuntnm){
		this.subuntnm = subuntnm;
	}
	public String getsubuntnm(){
		return subuntnm;
	}
//=============================================================
	public void setgrade(String grade){
		this.grade = grade;
	}
	public String getgrade(){
		return grade;
	}
	public int getparseGrade(){
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
	public void setexpefsz(int expefsz){
		this.expefsz = expefsz;
	}
	public int getexpefsz(){
		return expefsz;
	}
//=============================================================
	public void spkiss(int pkiss){
		this.pkiss = pkiss;
	}
	public int gpkiss(){
		if(pkiss <= 1 && pkiss >= 0){
			return pkiss;
		}
		return -1;
	}
//=============================================================
	public void sppfm(int ppfm){
		this.ppfm = ppfm;
	}
	public int gppfm(){
		if(ppfm <= 1 && ppfm >= 0){
			return ppfm;
		}
		return -1;
	}
//=============================================================
	public void spring(int pring){
		this.pring = pring;
	}
	public int gpring(){
		if(pring <= 1 && pring >= 0){
			return pring;
		}
		return -1;
	}
//=============================================================
	public void spaura(int paura){
		this.paura = paura;
	}
	public int gpaura(){
		if(paura <= 1 && paura >= 0){
			return paura;
		}
		return -1;
	}
//=============================================================
	public void spcross(int pcross){
		this.pcross = pcross;
	}
	public int gpcross(){
		if(pcross <= 1 && pcross >= 0){
			return pcross;
		}
		return -1;
	}
//=============================================================
	public void spveil(int pveil){
		this.pveil = pveil;
	}
	public int gpveil(){
		if(pveil <= 1 && pveil >= 0){
			return pveil;
		}
		return -1;
	}
//=============================================================
	public void sactcnt(int[] actcnt){
		this.actcnt = actcnt;
	}
	public int[] gactcnt(){
		return actcnt;
	}
//=============================================================
	public static boolean chkrawake(String liner){
		String chkawk = "���o";
		boolean rtnb = true;
		if(liner.indexOf(chkawk) != -1){
			rtnb = false;
		}

		return rtnb;
	}
}
