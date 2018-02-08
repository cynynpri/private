package samples;

import java.io.*;
import java.util.*;

/*
	Chara_SkillsdataはリネームされSkill_dataにクラス名を変更する。
	Skill_data sdata[] = new Skill_data();
	あるいは
	Skill_data sdata[] = setdata(character_name);
	と宣言して、スキルの名前、効果、カードのデータすべてを出力するクラスとする。
	Card_datasとの違いはSkillの部分がCardとなっている通り、個人所有の"カード"なのか、
	個人所有しているカードを登録するために"特技"から登録するためのデータを出力させるのかを
	区別するためである。
 */


class Skill_data{
	private String rrity; //レアリティ rarity
	private String skinm; //特技名 skill name
	private String sklef; //発動効果 skill effect
	private String tpskr; //発動条件型  type of skill running
	private int fsz; //発動条件数値 if run size
	private int sprob; //初期発動確率(Lv1) skill probabry
	private int dfprb; //1レベルごとの確率増加分 different probabry
	private int fprob; //最終地発動確率 final skill probabry
	private double accut; //判定強化時間 accuracy time
	private int iefsz; //lv1効果量 initial effect size
	private int difez; //1レベルごとの効果量増加分 different effect size
	private int fefsz; //lv8*** final effect size
	private int csm; //card smile
	private int cpr; //card pure
	private int ccl; //card cool
	private String csknm; //センタースキル center skill name
	private String scsnm; //サブセンタースキル sub center skill name
	private double pfupt; //パーフェクトアップ時間の最終値 perfect score up time.

	/**
	 * スキルデータのコンストラクタ.
	 * <p>
	 * スキルデータを扱うため、honosd.csvなどに代表されるcsvファイルから読み取られたデータを格納する、コンストラクタ。<br>
	 * setdataメソッドによりそれぞれのパラメータにデータが入力されるので、基本的にnullは入らない<br>
	 * </p>
	 *
	 * @param rrity レアリティ rarity
	 * @param skinm 特技名 skill name
	 * @param sklef 発動効果 skill effect
	 * @param tpskr 発動条件型  type of skill running
	 * @param fsz 発動条件数値 if run size
	 * @param sprob 初期発動確率(Lv1) skill probabry
	 * @param dfprb 1レベルごとの確率増加分 different probabry
	 * @param fprob 最終値発動確率 final skill probabry
	 * @param accut 判定強化時間 accuracy time
	 * @param iefsz lv1効果量 initial effect size
	 * @param difez 1レベルごとの効果量増加分 different effect size
	 * @param fefsz lv8*** final effect size
	 * @param csm card smile amount
	 * @param cpr card pure amount
	 * @param ccl card cool amount
	 * @param csknm センタースキル center skill name
	 * @param scsnm サブセンタースキル sub center skill name
	 * @param pfupt パーフェクトアップ時間の最終値 perfect score up time.
	 */
	public Skill_data(String rrity, String skinm, String sklef, String tpskr, int fsz, int sprob, int dfprb, int fprob, double accut, int iefsz, int difez, int fefsz, int csm, int cpr, int ccl, String csknm, String scsnm, Double pfupt){
		this.rrity = rrity;
		this.skinm = skinm;
		this.sklef = sklef;
		this.tpskr = tpskr;
		this.fsz = fsz;
		this.sprob = sprob;
		this.dfprb = dfprb;
		this.fprob = fprob;
		this.accut = accut;
		this.iefsz = iefsz;
		this.difez = difez;
		this.fefsz = fefsz;
		this.csm = csm;
		this.cpr = cpr;
		this.ccl = ccl;
		this.csknm = csknm;
		this.scsnm = scsnm;
		this.pfupt = pfupt;
	}
	//=========================================================================
	// set,getメソッド定義 ->Start 65 to 184.
	//=========================================================================
	/**
	 * セットレアリティ
	 *
	 * <p>
	 * --レアリティをセットする。ファイル読み込み側でnullははじくので直呼び出しでのnull入れは入る.
	 * </p>
	 * @param rrity レアリティ
	 */
	public void srrity(String rrity){
		this.rrity = rrity;
	}

	/**
	 * ゲットレアリティ
	 *
	 * <p>
	 * --レアリティをゲットする。
	 * </p>
	 * @param rrity レアリティ
	 * @return Skill_data型の配列に格納されているレアリティ(String)を返す
	 */
	public String grrity(){
		return rrity;
	}
	//-----------------------------------
	public void setskinm(String skinm){
		this.skinm = skinm;
	}
	public String getskinm(){
		return skinm;
	}
	//-----------------------------------
	public void ssklef(String sklef){
		this.sklef = sklef;
	}
	public String gsklef(){
		return sklef;
	}
	//-----------------------------------
	public void stpskr(String tpskr){
		this.tpskr = tpskr;
	}
	public String gtpskr(){
		return tpskr;
	}
	//-----------------------------------
	public void sfsz(int fsz){
		this.fsz = fsz;
	}
	public int gfsz(){
		return fsz;
	}
	//-----------------------------------
	public void ssprob(int sprob){
		this.sprob = sprob;
	}
	public int gsprob(){
		return sprob;
	}
	public double gdblprb(){
		double dblprb = 0.0;
		dblprb = (double)sprob / 100.0;
		return dblprb;
	}
	//-----------------------------------
	public void sdfprb(int dfprb){
		this.dfprb = dfprb;
	}
	public int gdfprb(){
		return dfprb;
	}
	//-----------------------------------
	public void sfprob(int fprob){
		this.fprob = fprob;
	}
	public int gfprob(){
		return fprob;
	}
	//-----------------------------------
	public void saccut(double accut){
		this.accut = accut;
	}
	public double gaccut(){
		return accut;
	}
	//-----------------------------------
	public void siefsz(int iefsz){
		this.iefsz = iefsz;
	}
	public int giefsz(){
		return iefsz;
	}
	//-----------------------------------
	public void sdifez(int difez){
		this.difez = difez;
	}
	public int gdifez(){
		return difez;
	}
	//-----------------------------------
	public void sfefsz(int fefsz){
		this.fefsz = fefsz;
	}
	public int gfefsz(){
		return fefsz;
	}
	//-----------------------------------
	public void scsm(int csm){
		this.csm = csm;
	}
	public int gcsm(){
		return csm;
	}
	//-----------------------------------
	public void scpr(int cpr){
		this.cpr = cpr;
	}
	public int gcpr(){
		return cpr;
	}
	//-----------------------------------
	public void sccl(int ccl){
		this.ccl = ccl;
	}
	public int gccl(){
		return ccl;
	}
	//-----------------------------------
	public void setcsknm(String csknm){
		this.csknm = csknm;
	}
	public String getcsknm(){
		return csknm;
	}
	//-----------------------------------
	public void setscsnm(String scsnm){
		this.scsnm = scsnm;
	}
	public String getscsnm(){
		if(scsnm.equals("empty")){
			return "";
		}else{
			return scsnm;
		}
	}
	//-----------------------------------
	public void spfupt(double pfupt){
		this.pfupt = pfupt;
	}
	public double gpfupt(){
		return pfupt;
	}
	//=========================================================================
	// set,getメソッド定義 ->End 55 to 184.
	//=========================================================================
	public static Skill_data[] setdata(String chara_name)throws FileNotFoundException, IOException{
		//String型のキャラ名からsdataにデータをセットするメソッド.
		ArrayList<Skill_data> setdata = new ArrayList<Skill_data>();
		String sdfpath = rtn_charasd(chara_name);
		FileReader fr = new FileReader(sdfpath);
		BufferedReader br = new BufferedReader(fr);
		int index = 0;

		String[] br_sdata = new String[18];
		String liner;
		while((liner = br.readLine()) != null){
			br_sdata = liner.split(",",18);
			Skill_data onedt = new Skill_data(
				br_sdata[0],
				br_sdata[1],
				br_sdata[2],
				br_sdata[3],
				Integer.parseInt(br_sdata[4]),
				Integer.parseInt(br_sdata[5]),
				Integer.parseInt(br_sdata[6]),
				Integer.parseInt(br_sdata[7]),
				Double.parseDouble(br_sdata[8]),
				Integer.parseInt(br_sdata[9]),
				Integer.parseInt(br_sdata[10]),
				Integer.parseInt(br_sdata[11]),
				Integer.parseInt(br_sdata[12]),
				Integer.parseInt(br_sdata[13]),
				Integer.parseInt(br_sdata[14]),
				br_sdata[15],
				br_sdata[16],
				Double.parseDouble(br_sdata[17])
			);
			setdata.add(index, onedt);
			index++;
		}
		br.close();

		Skill_data[] sdata = new Skill_data[setdata.size()];
		for(int len = 0; len < setdata.size(); len++){
			sdata[len] = setdata.get(len);
		}

		return sdata;
	}

	public static String rtn_charasd(String chara_name){
		String dPath = System.getProperty("user.dir");

		if(chara_name.equals("高坂穂乃果")){
			dPath = dPath + "/Datalist/honosd.csv";
		}else if(chara_name.equals("絢瀬絵里")){
			dPath = dPath + "/Datalist/elisd.csv";
		}else if(chara_name.equals("南ことり")){
			dPath = dPath + "/Datalist/kotosd.csv";
		}else if(chara_name.equals("園田海未")){
			dPath = dPath + "/Datalist/umisd.csv";
		}else if(chara_name.equals("星空凛")){
			dPath = dPath + "/Datalist/rinsd.csv";
		}else if(chara_name.equals("西木野真姫")){
			dPath = dPath + "/Datalist/makisd.csv";
		}else if(chara_name.equals("東条希")){
			dPath = dPath + "/Datalist/nonsd.csv";
		}else if(chara_name.equals("小泉花陽")){
			dPath = dPath + "/Datalist/kayosd.csv";
		}else if(chara_name.equals("矢澤にこ")){
			dPath = dPath + "/Datalist/yazsd.csv";
		}else if(chara_name.equals("高海千歌")){
			dPath = dPath + "/Datalist/chiksd.csv";
		}else if(chara_name.equals("桜内梨子")){
			dPath = dPath + "/Datalist/rikosd.csv";
		}else if(chara_name.equals("松浦果南")){
			dPath = dPath + "/Datalist/knnsd.csv";
		}else if(chara_name.equals("黒澤ダイヤ")){
			dPath = dPath + "/Datalist/diasd.csv";
		}else if(chara_name.equals("渡辺曜")){
			dPath = dPath + "/Datalist/yousd.csv";
		}else if(chara_name.equals("津島善子")){
			dPath = dPath + "/Datalist/johasd.csv";
		}else if(chara_name.equals("国木田花丸")){
			dPath = dPath + "/Datalist/marusd.csv";
		}else if(chara_name.equals("小原鞠莉")){
			dPath = dPath + "/Datalist/marisd.csv";
		}else if(chara_name.equals("黒澤ルビィ")){
			dPath = dPath + "/Datalist/rubysd.csv";
		}else {
			System.out.println("データ読み込みエラー：キャラクター名が読み込めません");
			System.exit(1);
		}

		return dPath;
	}

	public static String[] sKillName(String chara_name)throws IOException{
		//String filep = System.getProperty("user.dir");
		/*
		Scanner scanner = new Scanner(Chara_Sds);
		scanner.useDelimiter(",");

		while(scanner.hasNext()){
			int mAx = 0;
			mAx++;
		}
		*/
		String[] chara_sKillName = new String[1];
		String dPath = rtn_charasd(chara_name);

		FileReader chara_Sds = new FileReader(dPath);
		BufferedReader br_CSds = new BufferedReader(chara_Sds);

		String line;
		while((line = br_CSds.readLine()) != null){
			chara_sKillName = line.split(",", 0);
		}
		br_CSds.close();
		return chara_sKillName;
	}

	public static int mAx(String chara_name){
		int mAx = 1;
		return mAx;
	}

	/*public static void main(String[] args)throws IOException{
		Skill_data[] sdata = setdata("園田海未");
		//Skill_data[] rdata = Skill_read.setSkill("SR", sdata);
		String[] skilT = new String[sdata.length];
		for(int len = 0; len < skilT.length; len++){
			skilT[len] = sdata[len].grrity();
		}

		int counter = 1;
		for(String elem : skilT){
			System.out.println(counter + " cards is " + elem);
			counter++;
		}
	 //上デバック用のメイン文（読み取れたので終了）

	}*/
}
