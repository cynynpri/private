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

	private String unitnm; //ユニット名 ex)μ's Aqours
	private String subuntnm; //サブユニット名 ex) Printemps,lily white, BiBi, CYaRon!, Guilty Kiss, AZALEA
	private String grade;//学年 1年, 2年, 3年
	private int expefsz;//スコアアップ期待値
	private int pkiss;//SISのキッス private kiss
	private int ppfm;//SISのパフューム private perfume
	private int pring;//SISのリング private ring
	private int pcross;//SISのクロス private cross
	private int paura;//SISのオーラ private aura
	private int pveil;//SISのヴェール private veil
	private int actcnt;//スキル発動回数
	private int upactcnt;//スキル発動回数(発動率アップ時のもの)
	private int pcharm;//SISのチャーム private charm
	private int pheal;//SISのヒール private heal
	private int ptrick;//SISのトリック private trick
	private int pwink;//SISのウィンク private wink
	private int pimage;//キャラ限定SIS private character image item
	private int pbloom;//SISのブルーム private bloom
	private int ptrill;//SISのトリル private trill
	private int pnnet;//SISのノネット private nonette

	private Skill_data skillleveleffectsize;//特技の特技レベルブーストに対応するための追加。
	/*private enum pprte{
		Smile("スマイル"), Cool("クール"), Pure("ピュア");
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
		if(name.equals("小泉花陽")||name.equals("西木野真姫")||name.equals("星空凛")||name.equals("津島善子")||name.equals("黒澤ルビィ")||name.equals("国木田花丸")){
			grade = "1年生";
		}else if(name.equals("南ことり")||name.equals("高坂穂乃果")||name.equals("園田海未")||name.equals("渡辺曜")||name.equals("高海千歌")||name.equals("桜内梨子")){
			grade = "2年生";
		}else if(name.equals("東條希")||name.equals("矢澤にこ")||name.equals("絢瀬絵里")||name.equals("黒澤ダイヤ")||name.equals("小原鞠莉")||name.equals("松浦果南")){
			grade = "3年生";
		}else{
			grade = "empty";
		}
		//set unitnm
		if(name.equals("小泉花陽")||name.equals("西木野真姫")||name.equals("星空凛")||name.equals("南ことり")||name.equals("高坂穂乃果")||name.equals("園田海未")||name.equals("東條希")||name.equals("絢瀬絵里")||name.equals("矢澤にこ")){
			unitnm = "μ's";
		}else if(name.equals("黒澤ルビィ")||name.equals("国木田花丸")||name.equals("津島善子")||name.equals("渡辺曜")||name.equals("高海千歌")||name.equals("桜内梨子")||name.equals("小原鞠莉")||name.equals("黒澤ダイヤ")||name.equals("松浦果南")){
			unitnm = "Aqours";
		}else{
			unitnm ="empty";
		}
		//set subuntnm
		if(name.equals("小泉花陽")||name.equals("高坂穂乃果")||name.equals("南ことり")){
			subuntnm = "Printemps";
		}else if(name.equals("星空凛")||name.equals("園田海未")||name.equals("東條希")){
			subuntnm = "lily white";
		}else if(name.equals("西木野真姫")||name.equals("矢澤にこ")||name.equals("絢瀬絵里")){
			subuntnm = "BiBi";
		}else if(name.equals("黒澤ルビィ")||name.equals("高海千歌")||name.equals("渡辺曜")){
			subuntnm = "CYaRon！";
		}else if(name.equals("津島善子")||name.equals("桜内梨子")||name.equals("小原鞠莉")){
			subuntnm = "Guilty Kiss";
		}else if(name.equals("国木田花丸")||name.equals("松浦果南")||name.equals("黒澤ダイヤ")){
			subuntnm = "AZALEA";
		}else{
			subuntnm = "empty";
		}
	}

	public Card_datas(int cnum, String name, String pprty, String rrity, String skinm, boolean awake, int sislt,
			int skilv, String sksha, String skitp, int factv, int prob, double accut, int efsz, int csm, int cpr,
			int ccl, String cskin, String acskn, int pkiss, int ppfm, int pring, int pcross, int paura, int pveil,
			int pcharm, int pheal, int ptrick, int pwink, int pimage, int pbloom, int ptrill, int pnnet) {
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
		this.pkiss = pkiss;
		this.ppfm = ppfm;
		this.pring = pring;
		this.pcross = pcross;
		this.paura = paura;
		this.pveil = pveil;
		this.pcharm = pcharm;
		this.pheal = pheal;
		this.ptrick = ptrick;
		this.pwink = pwink;
		this.pimage = pimage;
		this.pbloom = pbloom;
		this.ptrill = ptrill;
		this.pnnet = pnnet;
		//set grade
		if (name.equals("小泉花陽") || name.equals("西木野真姫") || name.equals("星空凛") || name.equals("津島善子")
				|| name.equals("黒澤ルビィ") || name.equals("国木田花丸")) {
			grade = "1年生";
		} else if (name.equals("南ことり") || name.equals("高坂穂乃果") || name.equals("園田海未") || name.equals("渡辺曜")
				|| name.equals("高海千歌") || name.equals("桜内梨子")) {
			grade = "2年生";
		} else if (name.equals("東條希") || name.equals("矢澤にこ") || name.equals("絢瀬絵里") || name.equals("黒澤ダイヤ")
				|| name.equals("小原鞠莉") || name.equals("松浦果南")) {
			grade = "3年生";
		} else {
			grade = "empty";
		}
		//set unitnm
		if (name.equals("小泉花陽") || name.equals("西木野真姫") || name.equals("星空凛") || name.equals("南ことり")
				|| name.equals("高坂穂乃果") || name.equals("園田海未") || name.equals("東條希") || name.equals("絢瀬絵里")
				|| name.equals("矢澤にこ")) {
			unitnm = "μ's";
		} else if (name.equals("黒澤ルビィ") || name.equals("国木田花丸") || name.equals("津島善子") || name.equals("渡辺曜")
				|| name.equals("高海千歌") || name.equals("桜内梨子") || name.equals("小原鞠莉") || name.equals("黒澤ダイヤ")
				|| name.equals("松浦果南")) {
			unitnm = "Aqours";
		} else {
			unitnm = "empty";
		}
		//set subuntnm
		if (name.equals("小泉花陽") || name.equals("高坂穂乃果") || name.equals("南ことり")) {
			subuntnm = "Printemps";
		} else if (name.equals("星空凛") || name.equals("園田海未") || name.equals("東條希")) {
			subuntnm = "lily white";
		} else if (name.equals("西木野真姫") || name.equals("矢澤にこ") || name.equals("絢瀬絵里")) {
			subuntnm = "BiBi";
		} else if (name.equals("黒澤ルビィ") || name.equals("高海千歌") || name.equals("渡辺曜")) {
			subuntnm = "CYaRon！";
		} else if (name.equals("津島善子") || name.equals("桜内梨子") || name.equals("小原鞠莉")) {
			subuntnm = "Guilty Kiss";
		} else if (name.equals("国木田花丸") || name.equals("松浦果南") || name.equals("黒澤ダイヤ")) {
			subuntnm = "AZALEA";
		} else {
			subuntnm = "empty";
		}
	}

//=============================================================

	public final void scnum(int cnum){
		this.cnum = cnum;
	}

	public final int gcnum(){
		return cnum;
	}

//=============================================================
	public final void setname(String name){
		this.name = name;
	}

	public final String getname(){
		return name;
	}
//=============================================================
	public final void spprty(String pprty){
		this.pprty = pprty;
	}

	public final String gpprty(){
		return pprty;
	}
//=============================================================
	public final void srrity(String rrity){
		this.rrity = rrity;
	}

	public final String grrity(){
		return rrity;
	}
//=============================================================
	public final void setskinm(String skinm){
		this.skinm = skinm;
	}

	public final String getskinm(){
		return skinm;
	}
//=============================================================
	public final void sawake(boolean awake){
		this.awake = awake;
	}

	public final boolean gawake(){
		return awake;
	}

	public final String gsawk(){
		//get string awake
		String rtnawk = "";

		if(awake){
			rtnawk = "覚醒";
		}else{
			rtnawk = "未覚醒";
		}

		return rtnawk;
	}

	public final void sstrawake(String strawake){
		Boolean setawake = true;
		setawake = chkrawake(strawake);
		awake = setawake;
	}

	//if you set string awake, you check ctrl+f "chkrawake"; plz.
//=============================================================
	public final void ssislt(int sislt){
		this.sislt = sislt;
	}

	public final int gsislt(){
		return sislt;
	}
//=============================================================
	public final void sskilv(int skilv){
		this.skilv = skilv;
	}

	public final int gskilv(){
		return skilv;
	}
//=============================================================
	public final void ssksha(String sksha){
		this.sksha = sksha;
	}

	public final String gsksha(){
		return sksha;
	}
//=============================================================
	public final void sskitp(String skitp){
		this.skitp = skitp;
	}

	public final String gskitp(){
		return skitp;
	}
//=============================================================
	public final void sfactv(int factv){
		this.factv = factv;
	}

	public final int gfactv(){
		return factv;
	}
//=============================================================
	public final void sprob(int prob){
		this.prob = prob;
	}

	public final int gprob(){
		return prob;
	}
//=============================================================
	public final void saccut(double accut){
		this.accut = accut;
	}

	public final double gaccut(){
		return accut;
	}
//=============================================================
	public final void sefsz(int efsz){
		this.efsz = efsz;
	}

	public final int gefsz(){
		return efsz;
	}
//=============================================================
	public final void scsm(int csm){
		this.csm = csm;
	}

	public final int gcsm(){
		return csm;
	}
//=============================================================
	public final void sccl(int ccl){
		this.ccl = ccl;
	}

	public final int gccl(){
		return ccl;
	}
//=============================================================
	public final void scpr(int cpr){
		this.cpr = cpr;
	}

	public final int gcpr(){
		return cpr;
	}
//=============================================================
	public final void setcskin(String cskin){
		this.cskin = cskin;
	}

	public final String getcskin(){
		return cskin;
	}
//=============================================================
	public final void setacskn(String acskn){
		this.acskn = acskn;
	}

	public final String getacskn(){
		return acskn;
	}
//=============================================================
	public final void setunitnm(String unitnm){
		this.unitnm = unitnm;
	}

	public final String getunitnm(){
		return unitnm;
	}
//=============================================================
	public final void setsubuntnm(String subuntnm){
		this.subuntnm = subuntnm;
	}

	public final String getsubuntnm(){
		return subuntnm;
	}
//=============================================================
	public final void setgrade(String grade){
		this.grade = grade;
	}

	public final String getgrade(){
		return grade;
	}

	public final int getparseGrade(){
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
	public final void setexpefsz(int expefsz){
		this.expefsz = expefsz;
	}

	public final int getexpefsz(){
		return expefsz;
	}
//=============================================================
	public final void spkiss(int pkiss){
		this.pkiss = pkiss;
	}

	public final int gpkiss(){
		if(pkiss <= 1 && pkiss >= 0){
			return pkiss;
		}
		return -1;
	}
//=============================================================
	public final void sppfm(int ppfm){
		this.ppfm = ppfm;
	}

	public final int gppfm(){
		if(ppfm <= 1 && ppfm >= 0){
			return ppfm;
		}
		return -1;
	}
//=============================================================
	public final void spring(int pring){
		this.pring = pring;
	}

	public final int gpring(){
		if(pring <= 1 && pring >= 0){
			return pring;
		}
		return -1;
	}
//=============================================================
	public final void spaura(int paura){
		this.paura = paura;
	}

	public final int gpaura(){
		if(paura <= 1 && paura >= 0){
			return paura;
		}
		return -1;
	}
//=============================================================
	public final void spcross(int pcross){
		this.pcross = pcross;
	}

	public final int gpcross(){
		if(pcross <= 1 && pcross >= 0){
			return pcross;
		}
		return -1;
	}
//=============================================================
	public final void spveil(int pveil){
		this.pveil = pveil;
	}

	public final int gpveil(){
		if(pveil <= 1 && pveil >= 0){
			return pveil;
		}
		return -1;
	}
//=============================================================
	public final void sactcnt(int actcnt){
		this.actcnt = actcnt;
	}

	public final int gactcnt(){
		return actcnt;
	}
//=============================================================
	public final void supactcnt(int upactcnt){
		this.upactcnt = upactcnt;
	}

	public final int gupactcnt(){
		return upactcnt;
	}
//=============================================================
	public final void spcharm(int pcharm){
		this.pcharm = pcharm;
	}

	public final int gpcharm(){
		if (pcharm <= 1 && pcharm >= 0) {
			return pcharm;
		}
		return -1;
	}
//=============================================================
	public final void spheal(int pheal){
		this.pheal = pheal;
	}

	public final int gpheal(){
		if (pheal <= 1 && pheal >= 0) {
			return pheal;
		}
		return -1;
	}
//=============================================================
	public final void sptrick(int ptrick){
		this.ptrick = ptrick;
	}

	public final int gptrick(){
		if(ptrick <= 1 && ptrick >= 0){
			return ptrick;
		}
		return -1;
	}
//=============================================================
	public final void spwink(int pwink){
		this.pwink = pwink;
	}

	public final int gpwink(){
		if(pwink <= 1 && pwink >= 0){
			return pwink;
		}
		return -1;
	}
//=============================================================
	public final void spimage(int pimage){
		this.pimage = pimage;
	}

	public final int gpimage(){
		if(pimage <= 1 && pimage >= 0){
			return pimage;
		}
		return -1;
	}
//=============================================================
	public final void spbloom(int pbloom){
		this.pbloom = pbloom;
	}

	public final int gpbloom(){
		if(pbloom <= 1 && pbloom >= 0){
			return pbloom;
		}
		return -1;
	}
//=============================================================
	public final void sptrill(int ptrill){
		this.ptrill = ptrill;
	}

	public final int gptrill(){
		if(ptrill >= 0 && ptrill <= 1){
			return ptrill;
		}
		return -1;
	}
//=============================================================
	public final void spnnet(int pnnet){
		this.pnnet = pnnet;
	}

	public final int gpnnet(){
		if(pnnet <= 1 && pnnet >= 0){
			return pnnet;
		}
		return -1;
	}
//=============================================================
	public final void setskillleveleffectsize(Skill_data slef){
		skillleveleffectsize = slef;
	}

	public final Skill_data getskillleveleffectsize(){
		return skillleveleffectsize;
	}
//--------------------------------------------------------------
//以上が単純なgetterとsetter.以下は実際に使用するgetterとsetter.
	public final void setlisttoskilldata(Skill_data[] sdata){
		boolean matchbl = false;
		for(Skill_data temp : sdata){
			if(temp.getskinm().equals(skinm)){
				setskillleveleffectsize(temp);
				matchbl = true;
			}
		}
		if(!matchbl){
			System.err.println("スキルデータのセットに失敗しました。");
			System.err.print("スキルデータにはnullを入れます。(NullPointerExceptionで落ちた場合は〇〇sd.csvのデータが正しいものか確認してください。)");
			setskillleveleffectsize(null);
		}
	}
	//特技レベルブーストに対応するためのメソッド
	public final double[] getskilldatatoefsz(Card_datas boostcard){
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
		return leveluptoefsz;//leveluptoefsz[0]がint efsz. leveluptoefsz[1]がdouble accut.に対応する。
	}
	//特技レベルブースト後のユニットを返すメソッド。
	public final Card_datas[] skilllevelboostedtoUnits(Card_datas[] unit, Card_datas boostcard){
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
		checknms[0] = "\"1.穂乃果";
		checknms[1] = "\"2.絵里";
		checknms[2] = "\"3.ことり";
		checknms[3] = "\"4.海未";
		checknms[4] = "\"5.凛";
		checknms[5] = "\"6.真姫";
		checknms[6] = "\"7.希";
		checknms[7] = "\"8.花陽";
		checknms[8] = "\"9.にこ";
		checknms[9] = "\"11.千歌";
		checknms[10] = "\"12.梨子";
		checknms[11] = "\"13.果南";
		checknms[12] = "\"14.ダイヤ";
		checknms[13] = "\"15.曜";
		checknms[14] = "\"16.善子";
		checknms[15] = "\"17.花丸";
		checknms[16] = "\"18.鞠莉";
		checknms[17] = "\"19.ルビィ";


		rtnstr = checknms[len];

		return rtnstr;
	}
	public static String setrnms(int len){
		String rtnstr = new String();
		String[] checknms = new String[18];

		checknms[0] = "高坂穂乃果";
		checknms[1] = "絢瀬絵里";
		checknms[2] = "南ことり";
		checknms[3] = "園田海未";
		checknms[4] = "星空凛";
		checknms[5] = "西木野真姫";
		checknms[6] = "東條希";
		checknms[7] = "小泉花陽";
		checknms[8] = "矢澤にこ";
		checknms[9] = "高海千歌";
		checknms[10] = "桜内梨子";
		checknms[11] = "松浦果南";
		checknms[12] = "黒澤ダイヤ";
		checknms[13] = "渡辺曜";
		checknms[14] = "津島善子";
		checknms[15] = "国木田花丸";
		checknms[16] = "小原鞠莉";
		checknms[17] = "黒澤ルビィ";

		rtnstr = checknms[len];

		return rtnstr;
	}

	public static String setchkpty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = ",\"1.スマイル";
		checkpty[1] = ",\"2.ピュア";
		checkpty[2] = ",\"3.クール";

		rtnstr = checkpty[len];

		return rtnstr;
	}

	public static String setrpty(int len){
		String rtnstr = new String();
		String[] checkpty = new String[3];

		checkpty[0] = "スマイル";
		checkpty[1] = "ピュア";
		checkpty[2] = "クール";

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

		checksktp[0] = ",\"1.スコア\"";
		checksktp[1] = ",\"2.判定\"";
		checksktp[2] = ",\"3.回復\"";
		checksktp[3] = ",\"4.パーフェクト\"";
		checksktp[4] = ",\"5.発動率\"";
		checksktp[5] = ",\"6.パラメーター\"";
		checksktp[6] = ",\"7.シンクロ\"";
		checksktp[7] = ",\"8.リピート\"";

		rtnstr = checksktp[len];

		return rtnstr;
	}

	public static String setrsha(int len){
		String rtnstr = new String();
		String[] checkrsktp = new String[8];

		checkrsktp[0] = "スコア";
		checkrsktp[1] = "判定";
		checkrsktp[2] = "回復";
		checkrsktp[3] = "パーフェクト";
		checkrsktp[4] = "発動率";
		checkrsktp[5] = "パラメーター";
		checkrsktp[6] = "シンクロ";
		checkrsktp[7] = "リピート";


		rtnstr = checkrsktp[len];

		return rtnstr;
	}

	public static String setchksktp(int len){
		String rtnstr = new String();
		String[] checksktp = new String[7];

		checksktp[0] = ",\"1.タイマー\",";
		checksktp[1] = ",\"2.リズムアイコン\",";
		checksktp[2] = ",\"3.パーフェクト\",";
		checksktp[3] = ",\"4.コンボ\",";
		checksktp[4] = ",\"5.スコア\",";
		checksktp[5] = ",\"6.スターアイコン\",";
		checksktp[6] = ",\"7.チェイン\",";

		rtnstr = checksktp[len];

		return rtnstr;
	}

	public static String setrsktp(int len){
		String rtnstr = new String();
		String[] checksktp = new String[7];

		checksktp[0] = "タイマー";
		checksktp[1] = "リズムアイコン";
		checksktp[2] = "パーフェクト";
		checksktp[3] = "コンボ";
		checksktp[4] = "スコア";
		checksktp[5] = "スターアイコン";
		checksktp[6] = "チェイン";

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
		chkcskn_s[0] = ",\"1.スマイルパワー\"";
		chkcskn_s[1] = ",\"2.スマイルハート\"";
		chkcskn_s[2] = ",\"3.スマイルスター\"";
		chkcskn_s[3] = ",\"4.スマイルプリンセス\"";
		chkcskn_s[4] = ",\"5.スマイルエンジェル\"";
		chkcskn_s[5] = ",\"6.スマイルエンプレス\"";

		//chkcskn_p
		chkcskn_p[0] = ",\"1.ピュアパワー\"";
		chkcskn_p[1] = ",\"2.ピュアハート\"";
		chkcskn_p[2] = ",\"3.ピュアスター\"";
		chkcskn_p[3] = ",\"4.ピュアプリンセス\"";
		chkcskn_p[4] = ",\"5.ピュアエンジェル\"";
		chkcskn_p[5] = ",\"6.ピュアエンプレス\"";

		//chkcskn_c
		chkcskn_c[0] = ",\"1.クールパワー\"";
		chkcskn_c[1] = ",\"2.クールハート\"";
		chkcskn_c[2] = ",\"3.クールスター\"";
		chkcskn_c[3] = ",\"4.クールプリンセス\"";
		chkcskn_c[4] = ",\"5.クールエンジェル\"";
		chkcskn_c[5] = ",\"6.クールエンプレス\"";

		if(pprty.equals("スマイル")){
			rtnstr = chkcskn_s[len];
		} else if (pprty.equals("ピュア")){
			rtnstr = chkcskn_p[len];
		} else if (pprty.equals("クール")){
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
				String bfs = setchkcskn("スマイル", len_s);
				chkcskn[len_s] = bfs;
				lgh = bfs.length();
				if(lgh < 5){
					return rtnrcskn;
				}else{
					rcskn[len_s] = bfs.substring(4,lgh - 1);
				}
			}else if(6 <= len_s && len_s  < 12){
				String bfp = setchkcskn("ピュア", len_s - 6);
				chkcskn[len_s] = bfp;
				lgh = bfp.length();
				if(lgh < 5){
					return rtnrcskn;
				}else{
					rcskn[len_s] = bfp.substring(4, lgh - 1);
				}
			}else if(12 <= len_s && len_s < 18){
				String bfc = setchkcskn("クール", len_s - 12);
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
		String endstr = "\",\"1.未";
		String rtnstr = new String();
		int b = 0;
		int e = 0;

		b = sklnm.indexOf(beginstr);
		e = sklnm.indexOf(endstr);
		if(e == - 1 || e == 0){
			endstr = "\",\"2.覚";
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
		String chkawk = "\",\"1.未覚";
		boolean rtnb = true;
		if(liner.indexOf(chkawk) != -1){
			rtnb = false;
		}

		return rtnb;
	}

	public static boolean chkrawake(String liner){
		String chkawk = "未覚";
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
		chkacsn[0] = ",\"1.μ's\"";
		chkacsn[1] = ",\"2.Aqours\"";
		chkacsn[2] = ",\"3.1年生\"";
		chkacsn[3] = ",\"4.2年生\"";
		chkacsn[4] = ",\"5.3年生\"";
		chkacsn[5] = ",\"6.BiBi\"";
		chkacsn[6] = ",\"7.lily white\"";
		chkacsn[7] = ",\"8.Printemps\"";
		chkacsn[8] = ",\"9.Guilty Kiss\"";
		chkacsn[9] = ",\"10.AZALEA\"";
		chkacsn[10] = ",\"11.CYaRon！\"";
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
		String chkawk = "覚醒\",";
		String chkskltype = ",\"1.スコア\"";
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
				System.err.println("データが取得できませんでした。");
				return 0;
			}
		}
		cutl = liner.substring(b,e-2);
		rtnskslt = Integer.parseInt(cutl);

		return rtnskslt;
	}

	public static int chkskllv(String liner){
		String chkawk = "覚醒\",";
		String chkskltype = ",\"1.スコア\"";
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
				System.err.println("データが取得できませんでした。");
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
				//chkliner = setchkcskn("スマイル", len);
				es = liner.indexOf(setchkcskn("スマイル", len));
			}else if(6 <= len && len < 12 && es <= 0){
				//chkliner = setchkcskn("クール", len-6);
				es = liner.indexOf(setchkcskn("クール", len-6));
			}else if(12 <= len && len < 18 && es <= 0){
				//chkliner = setchkcskn("ピュア", len-12);
				es = liner.indexOf(setchkcskn("ピュア", len-12));
				if(len == 18 && es < bs){
					return rtn_array;
				}
			}
		}

		//デバック用
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
				if(buffer_a[len].indexOf(".") != -1){
					double tmpdbl = Double.parseDouble(buffer_a[len]);
					bs = (int) (tmpdbl * 100.0 - 100);
				}else{
					bs = Integer.parseInt(buffer_a[len]);
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
				chkliner = setchkcskn("スマイル", len);
				es = liner.indexOf(chkliner);
			} else if (6 <= len && len < 12 && es <= 0) {
				chkliner = setchkcskn("クール", len - 6);
				es = liner.indexOf(chkliner);
			} else if (12 <= len && len < 18 && es <= 0) {
				chkliner = setchkcskn("ピュア", len - 12);
				es = liner.indexOf(chkliner);
				if (len == 18 && es < bs) {
					return rtn_double;
				}
			}
		}

		//デバック用
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
		//覚醒状態か否かからＳＩＳのスロット数を入力するメソッド
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
		}else if(rrity.equals("UR") && cskin.equals("クールパワー")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("スマイルパワー")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("ピュアパワー")){
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
		//覚醒状態か否かからＳＩＳのスロット数を入力するメソッド
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
		}else if(rrity.equals("UR") && cskin.equals("クールパワー")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("スマイルパワー")){
			iniSislt = 2;
			maxSislt = 2;
		}else if(rrity.equals("UR") && cskin.equals("ピュアパワー")){
			iniSislt = 2;
			maxSislt = 2;
		}
		int[] rSislt = {iniSislt,maxSislt};
		return rSislt;
	}

//=============================================================
	public static int sfloor(double basescore){
		int rtn = 0;
		if(basescore > 0){
			rtn = (int)basescore;
		}
		if(rtn <= 0){
			System.err.println("スコアの計算値が負になりました");
		}
		return rtn;
	}

//=============================================================================
//特技レベルテーブル ->Start 1347 to 1453
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
		//属性ｐが○○%UPするっていうスキルのテーブルを返すメソッド
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
//特技レベルテーブル ->End 1347 to 1453
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

		if(name.equals("小泉花陽")||name.equals("西木野真姫")||name.equals("星空凛")||name.equals("南ことり")||name.equals("高坂穂乃果")||name.equals("園田海未")||name.equals("矢澤にこ")||name.equals("絢瀬絵里")||name.equals("東條希")){
			tprchain = "μ's";
		}else if(name.equals("黒澤ルビィ")||name.equals("国木田花丸")||name.equals("津島善子")||name.equals("渡辺曜")||name.equals("高海千歌")||name.equals("桜内梨子")||name.equals("松浦果南")||name.equals("黒澤ダイヤ")||name.equals("小原鞠莉")){
			tprchain = "Aqours";
		}
		if(name.equals("小泉花陽")||name.equals("西木野真姫")||name.equals("星空凛")||name.equals("黒澤ルビィ")||name.equals("国木田花丸")||name.equals("津島善子")){
			tprchain = tprchain + "1年生";
		}else if(name.equals("南ことり")||name.equals("高坂穂乃果")||name.equals("園田海未")||name.equals("渡辺曜")||name.equals("高海千歌")||name.equals("桜内梨子")){
			tprchain = tprchain + "2年生";
		}else{
			tprchain = tprchain + "3年生";
		}
		//リストビューに表示する特技のテキストを出力する。
		if(card.gskitp().equals("リズムアイコン")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "個ごとに";
		}else if(card.gskitp().equals("タイマー")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "秒ごとに";
		}else if(card.gskitp().equals("パーフェクト")){
			rtn_skilltext = "PERFECTを" + card.gfactv() + "回達成するごとに";
		}else if(card.gskitp().equals("コンボ")){
			rtn_skilltext = card.gskitp() + card.gfactv() +"を達成するごとに";
		}else if(card.gskitp().equals("スコア")){
			rtn_skilltext = card.gskitp() + card.gfactv() + "達成ごとに";
		}else if(card.gskitp().equals("スターアイコン")){
			rtn_skilltext = card.gskitp() + "PERFECT" + card.gfactv() + "回ごとに";
		}else if(card.gskitp().equals("チェイン")){
			rtn_skilltext = "自身を除く" + tprchain + "の特技がすべて発動すると";
		}

		rtn_skilltext = rtn_skilltext + card.gprob() + "%の確率で";
		if(card.gsksha().equals("スコア")){
			rtn_skilltext = rtn_skilltext + "スコアが" + card.gefsz() + "増える";
		}else if(card.gsksha().equals("回復")){
			rtn_skilltext = rtn_skilltext + "体力が" + card.gefsz() + "回復する";
		}else if(card.gsksha().equals("判定")){
			rtn_skilltext = rtn_skilltext + "判定が" + card.gaccut() + "秒強化される";
		}else if(card.gsksha().equals("発動率")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "秒間他の特技の発動確率が1." + card.gefsz() + "倍になる";
		}else if(card.gsksha().equals("パラメーター")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "秒間" + tprchain + "の属性Pが" + card.gefsz() + "%UPする";
		}else if(card.gsksha().equals("パーフェクト")){
			rtn_skilltext = rtn_skilltext + card.gaccut() + "秒間PERFECT時のタップSCOREが" + card.gefsz() + "増える";
		}else if(card.gsksha().equals("シンクロ")){
			rtn_skilltext = rtn_skilltext + card.gefsz() + "秒間" + tprchain + "のいずれかと同じ属性Pになる";
		}else if(card.gsksha().equals("リピート")){
			rtn_skilltext = rtn_skilltext + "直前に発動した特技リピート以外の特技効果を発動";
		}
		return rtn_skilltext;
	}

	public static String getSISimagename(Card_datas card){
		if(card.getname().equals("高坂穂乃果")){
			return "スマイルパワフル";
		}else if(card.getname().equals("絢瀬絵里")){
			return "クールプリマ";
		}else if(card.getname().equals("南ことり")){
			return "ピュアチャープ";
		}else if(card.getname().equals("園田海未")){
			return "クールシューター";
		}else if(card.getname().equals("星空凛")){
			return "スマイルキティ";
		}else if(card.getname().equals("西木野真姫")){
			return "クールディーバ";
		}else if(card.getname().equals("東條希")){
			return "ピュアフォーチュン";
		}else if(card.getname().equals("小泉花陽")){
			return "ピュアフラワー";
		}else if(card.getname().equals("矢澤にこ")){
			return "スマイルギャラクシー";
		}else if(card.getname().equals("高海千歌")){
			return "スマイルオレンジ";
		}else if(card.getname().equals("桜内梨子")){
			return "クールブロッサム";
		}else if(card.getname().equals("松浦果南")){
			return "ピュアドルフィン";
		}else if(card.getname().equals("黒澤ダイヤ")){
			return "クールプラム";
		}else if(card.getname().equals("渡辺曜")){
			return "ピュアボヤージュ";
		}else if(card.getname().equals("津島善子")){
			return "クールリトルデーモン";
		}else if(card.getname().equals("国木田花丸")){
			return "スマイルフューチャー";
		}else if(card.getname().equals("小原鞠莉")){
			return "スマイルシャイニー";
		}else if(card.getname().equals("黒澤ルビィ")){
			return "ピュアロリポップ";
		}
		return "Error!!";
	}

	public boolean equals(Card_datas target){
		if(cnum == target.gcnum() && skinm.equals(target.getskinm())){
			return true;
		}else{
			return false;
		}
	}

	public static Card_datas[] csmsort(Card_datas[] fldt) {
		//GUITest側からcdataを受け取るとcdata[0] = null;
		//cdata[1]からデータが入っている。
		//この形式に合わせるため、ソートしsorted[0]のデータをrtncdata[1]に書き直す必要がある。
		int max = fldt.length;
		Card_datas[] rfldt = new Card_datas[max - 1];
		for (int len = 0; len < max - 1; len++) {
			rfldt[len] = fldt[len + 1];
		}
		//スマイル値でのソート例(最後の配列が最大)
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
		return fldt;
	}

	public static Card_datas[] cprsort(Card_datas[] fldt) {
		//GUITest側からcdataを受け取るとcdata[0] = null;
		//cdata[1]からデータが入っている。
		//この形式に合わせるため、ソートしsorted[0]のデータをrtncdata[1]に書き直す必要がある。
		int max = fldt.length;
		Card_datas[] rfldt = new Card_datas[max - 1];
		for (int len = 0; len < max - 1; len++) {
			rfldt[len] = fldt[len + 1];
		}
		//ピュア値でのソート例(最後の配列が最大)
		for (int len = 0; len < max - 1; ++len) {
			for (int j = len + 1; j < max - 1; ++j) {
				Card_datas temp = new Card_datas();
				temp = rfldt[len];
				if (temp.gcpr() <= rfldt[j].gcpr()) {
					rfldt[len] = rfldt[j];
					rfldt[j] = temp;
				}
			}
		}
		for (int len = 0; len < max - 1; len++) {
			fldt[len + 1] = rfldt[len];
		}
		return fldt;
	}

	public static Card_datas[] cclsort(Card_datas[] fldt) {
		//GUITest側からcdataを受け取るとcdata[0] = null;
		//cdata[1]からデータが入っている。
		//この形式に合わせるため、ソートしsorted[0]のデータをrtncdata[1]に書き直す必要がある。
		int max = fldt.length;
		Card_datas[] rfldt = new Card_datas[max - 1];
		for (int len = 0; len < max - 1; len++) {
			rfldt[len] = fldt[len + 1];
		}
		//クール値でのソート例(最後の配列が最大)
		for (int len = 0; len < max - 1; ++len) {
			for (int j = len + 1; j < max - 1; ++j) {
				Card_datas temp = new Card_datas();
				temp = rfldt[len];
				if (temp.gccl() <= rfldt[j].gccl()) {
					rfldt[len] = rfldt[j];
					rfldt[j] = temp;
				}
			}
		}
		for (int len = 0; len < max - 1; len++) {
			fldt[len + 1] = rfldt[len];
		}
		return fldt;
	}

	//public static void main(String[] args)throws IOException{
		/*String[] skilist = skill_list("園田海未","UR");
		for(int i = 0;i < skilist.length;i++){
			System.out.println(skilist[i]);
		}
		String[] ssrskilist = skill_list("園田海未","SSR");
		for(int i = 0;i < ssrskilist.length;i++){
			System.out.println(ssrskilist[i]);
		}
		String[] srskilist = skill_list("園田海未","SR");
		for(int i = 0;i < srskilist.length;i++){
			System.out.println(srskilist[i]);
		}
		String[] skilist = skill_Pprtylist("ピュア","園田海未","SSR");*/
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
