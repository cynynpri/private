package samples;

import java.io.*;
import java.util.*;
import java.math.*;

class Calc_data{
	//計算用ユニットデータ群
	/*static ArrayList<Card_datas[]> unitdata = new ArrayList<Card_datas[]>();
	static Card_datas[] oneunit = new Card_datas[9];
	private String unitnm; //ユニット名 ex)μ's Aqours
	private String subuntnm; //サブユニット名 ex) Printemps,lily white, BiBi, CYaRon!, Guilty Kiss, AZALEA
	private String grade;//学年 1年, 2年, 3年
	private int usm;//ユニットスマイル値
	private int upr;//ユニットピュア値
	private int ucl;//ユニットクール値*/
	private double regular_probably;
	private double up_regular_probably;
	private int skill_up_score;
	private int probably_up_skillupscore;
	private int base_score;
	private long calculation_time;
	private int unit_pprty;//ユニット値
	private int centerskill_up_unit_pprty;//センタースキルによる上昇分ユニット値

	/*
	public static List<Card_datas[]> setUnitlist(List<Card_datas> cdata, Music_data calcMd){
		//ユニット提案メソッド
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
		if(pprty.equals("スマイル")){
			//
			fldt = Card_datas.csmsort(fldt);
		}else if(pprty.equals("ピュア")){
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
		//上記のpythonプログラムを参考にした。
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

	//スキル発動最大回数を計算するメソッド
	public static int setMaxactcnt(Card_datas ondt, Music_data calcMd, double perper, String sf, Card_datas[] unit)throws DataNotFoundException{
		int maxcombo = calcMd.gmaxcb();
		int star_icon = calcMd.gstar_icon();
		int musictm = calcMd.gmusictm();
		int mactct = 0;
		int base = Integer.parseInt(sf.split(",", 0)[0]);
		//check Card_datas line 521
		if (ondt.gskitp().equals("タイマー")) {
			mactct = (int)Math.floor(musictm / ondt.gfactv());
		} else if (ondt.gskitp().equals("リズムアイコン")) {
			mactct = (int)Math.floor(maxcombo / ondt.gfactv());
		} else if (ondt.gskitp().equals("パーフェクト")) {
			mactct = (int)Math.floor(maxcombo * perper / ondt.gfactv());
		} else if (ondt.gskitp().equals("コンボ")) {
			mactct = (int)Math.floor(maxcombo / ondt.gfactv());
		} else if (ondt.gskitp().equals("スコア")) {
			mactct = (int)Math.floor(base * maxcombo * 1.3 / (80 * ondt.gfactv()));
		} else if (ondt.gskitp().equals("スターアイコン")) {
			mactct = (int)Math.floor(star_icon / ondt.gfactv());
		} else if (ondt.gskitp().equals("チェイン")) {
			//対象カードの最大スキル発動回数の最低値がチェインの最大発動回数．(e.g. {19,28,17,22} -> maxactcnt of Chain is 17)
			List<Card_datas> targetCd = new ArrayList<Card_datas>();
			for(int len = 0;len < 9;len++){
				/*
				 * 無限ループ回避に失敗した場合
				 * 下記if文内のunit[len].gskitp().equals(ondt.gskitp()) == falseを確認のこと。plz check ctrl + f.
				 */
				if(unit[len].getgrade().equals(ondt.getgrade()) && unit[len].getunitnm().equals(ondt.getunitnm()) && unit[len].gskitp().equals(ondt.gskitp()) == false){
					targetCd.add(unit[len]);
				}
			}
			//再帰させるので無限ループ回避の確認
			for(int len = 0;len < targetCd.size();len++){
				if(targetCd.get(len).gskitp().equals("チェイン")){
					System.err.println(ondt.grrity()+":"+ondt.getname()+":"+ondt.getskinm()+"のカードに置いて予期せぬ処理が発生しました。");
					System.out.println("メソッドエラーにより無限ループの回避に失敗しました。");
					String err = new String();
					throw new DataNotFoundException(err);
				}
			}
			int[] tmpac = new int[targetCd.size()];
			for(int len = 0;len < targetCd.size();len++){
				tmpac[len] = setMaxactcnt(targetCd.get(len), calcMd, perper, sf, unit);
				if (tmpac[len] > mactct) {// <- 0から最大値を入れるようにする。
					mactct = tmpac[len];
				}
			}
			if(mactct == 0){
				System.out.println("チェイン対象となるカードが存在しないようです。");
				System.out.println(ondt.grrity()+":"+ondt.getname()+":"+ondt.getskinm()+"の最大発動回数を0として計算します。");
				return 0;
			}
			for (int len = 0; len < tmpac.length; len++){
				if (tmpac[len] < mactct) {// <- mactctには最大値が入っているので0との比較にはならない。
					mactct = tmpac[len];
				}
			}

		}
		return mactct;
	}

	//スキル発動によるスコアアップ期待値を計算するメソッド
	public static int setsklexp(Card_datas calcCd, Music_data calcMd, double perper, String sf, Card_datas[] unit, Card_datas frend, double tapscoreup)throws NullPointerException{
		/*
		 * 引数のperperはパフェ率のこと。
		 * sfはユニットの値
		 * unitは一枚のカード
		 * calcMdは計算したい楽曲
		 */
		int efsz = calcCd.gefsz();
		double prob = calcCd.gprob()/100.0;
		int maxactcnt = 0;
		try{
			maxactcnt = setMaxactcnt(calcCd, calcMd, perper, sf, unit);
		}catch(DataNotFoundException e){
			System.out.println(e);
			System.out.println("メソッドエラーによりスキルによるスコアアップ期待値の計算に無限ループが発生しました。");
			System.out.println("このエラーを作者にスクリーンショットを撮ってどのような操作を行ったのか明記の上伝えてください。");
			return 0;
		}
		if(calcCd.gsksha().equals("スコア")){
			if(calcCd.gpcharm() == 0){
				return (int)Math.floor(maxactcnt*prob*efsz);
			}else{
				efsz = (int)(efsz*2.5);
				return (int) Math.floor(maxactcnt * prob * efsz);
			}
		}else if(calcCd.gsksha().equals("回復")){
			if(calcCd.gpheal() == 0){
				efsz = 0;
				return 0;
			}else{
				efsz *= 480;
				return (int) Math.floor(maxactcnt * prob * efsz);
			}
		}else if(calcCd.gsksha().equals("判定")){
			if(calcCd.gptrick() == 1){
				/*
				 * トリックが付いている場合
				 * sfを再定義し、判定強化時間その再定義されたrsfで
				 * rsf - sfを計算し、1タップ上昇分を計算する。
				 * そして、楽曲の1秒当たりのノーツ数から、
				 * accut分の時間を掛け算して、rsf-sfの数値と掛け算し、
				 * トリック上昇分を出力する。
				 */
				int counter = 0;
				boolean prupbl = false;
				for(int len = 0;len < unit.length;len++){
					if(unit[len].gsksha().equals("パラメーター")){
						prupbl = true;
						counter++;
					}else if(unit[len].gsksha().equals("判定")){
						counter++;
					}else if(unit[len].gsksha().equals("シンクロ")){
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
					dfBs = dfBs - tmpBS;//相乗効果分だけを出す。
					dfBs /= counter;//相乗効果分を割る。
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
		}else if(calcCd.gsksha().equals("パーフェクト")){
			double spnotes = calcMd.gmaxcb()/calcMd.gmusictm();
			double oefsz = spnotes*calcCd.gaccut()*efsz;
			return (int)Math.floor(maxactcnt * prob *oefsz);
		}else if(calcCd.gsksha().equals("発動率")){
			//確率分布を再計算して上昇したスキル期待値分を元のスキル期待値分から引いて、最大発動回数で割る。
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
				System.err.println(calcCd.grrity() + ":" +calcCd.getname()+":"+calcCd.getskinm()+"のカードの期待値計算において異常処理が発生しました。");
				throw new NullPointerException(errs);
			}
			for(int len = 0;len < lunt.size();len++){
				probs[len] = lunt.get(len).gprob();
				uprobs[len] = efsz/100.0 + 1;//発動アップ確率を格納。
				uprobs[len] *= lunt.get(len).gprob();//発動確率をアップして格納。
				lunt.get(len).sprob((int)Math.floor(uprobs[len]));//発動確率をアップしたものへ再定義。
				tmpefsz[len] = lunt.get(len).gefsz();//仮期待値を作成するためのefszとmaxactcntを格納。
				try {
					maxactcnts[len] = setMaxactcnt(lunt.get(len), calcMd, perper, sf, unit);//同上。
				} catch (DataNotFoundException e) {
					System.out.println(e);
					System.out.println("メソッドエラーによりスキルによるスコアアップ期待値の計算に無限ループが発生しました。");
					System.out.println("このエラーを作者にスクリーンショットを撮ってどのような操作を行ったのか明記の上伝えてください。");
					return 0;
				}
				oefsz += uprobs[len] * tmpefsz[len] * maxactcnts[len];//発動アップによるスキルのスコアアップ仮期待値を格納。
				lunt.get(len).sprob((int)probs[len]);//元の発動確率へ戻し、再定義。
				try {
					maxactcnts[len] = setMaxactcnt(lunt.get(len), calcMd, perper, sf, unit);//元の最大発動回数を格納。
				} catch (DataNotFoundException e) {
					System.out.println(e);
					System.out.println("メソッドエラーによりスキルによるスコアアップ期待値の計算に無限ループが発生しました。");
					System.out.println("このエラーを作者にスクリーンショットを撮ってどのような操作を行ったのか明記の上伝えてください。");
					return 0;
				}
				tefsz += probs[len] * tmpefsz[len] * maxactcnts[len];//元の発動率でのスキルによるスコアアップ期待値を格納。
			}
			oefsz -= tefsz;//発動アップ発動済みのスコアアップ期待値から元の発動率でのスコアアップ期待値を引く。
			double accut = calcCd.gaccut()/calcMd.gmusictm();//発動時間割合を格納。
			return (int)Math.floor(maxactcnt * prob * oefsz * accut);//最大発動回数×確率で発動回数期待値を算出し、oefszは全時間発動時の時間による関数値なのでaccutという時間を代入する。

		}else if(calcCd.gsksha().equals("パラメーター")){
			double accut = calcCd.gaccut();
			String prsf = setprupsf(unit, frend, calcMd);
			/*
			 * 対象となるカードの属性値をefsz%分アップさせる
			 * そしてsfを計算する-> prsf
			 * 後は判定強化と同じ処理。
			 * sfの計算メソッドの再定義が必要。
			 * テストメソッドにて計算式確認済み
			 */
			int counter = 0;
			boolean trunt = false;
			for(int len = 0;len < unit.length;len++){
				if(unit[len].gsksha().equals("判定")){
					trunt = true;
					counter++;
				}else if(unit[len].gsksha().equals("パラメーター")){
					counter++;
				}else if(unit[len].gsksha().equals("シンクロ")){
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
				dfBS = dfBS - tmpBS;//トリックと判定の相乗効果分が残る。
				dfBS /= counter;//相乗効果分を判定とパラメーターのカードの数だけ割る(判定の方も同様にすれば相乗効果分を余計に足すことがなくなる。)
				dfBS += tmpBS;//カードがパラメーターアップなのでパラメーターアップ分だけ戻す。
				double oefsz = dfBS *(calcCd.gaccut()/calcMd.gmusictm());
				return (int)Math.floor(maxactcnt * prob * oefsz);
			}else{
				int prupBS = calcBS(unit, calcMd, prsf, tapscoreup);
				int iBS = calcBS(unit, calcMd, sf, tapscoreup);
				int dfBS = prupBS - iBS;
				double oefsz = dfBS *(calcCd.gaccut()/calcMd.gmusictm());
				return (int)Math.floor(maxactcnt * prob * oefsz);
			}
		}else if(calcCd.gsksha().equals("シンクロ")){
			double accut = calcCd.gaccut();
			double oefsz = 0.0;
			/*
			 * 対象のカードの中から1枚取得し、ユニットを作成しsfを再計算する。-> sysf
			 * sysfから元のsfを引きあとは判定強化の処理と同一。
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
						+ "のカードの期待値計算において異常処理が発生しました。");
				String err = new String();
				throw new NullPointerException(err);
			}
			String[] tempsysf = new String[calcs.size()];
			for (int len = 0; len < calcs.size(); len++) {
				for (int i = 0; i < calcunit.size(); i++) {
					syunt[i] = calcunit.get(i);
				}
				//SIS引き継ぎ
				calcs.get(len).spkiss(calcCd.gpkiss());
				calcs.get(len).sppfm(calcCd.gppfm());
				calcs.get(len).spring(calcCd.gpring());
				calcs.get(len).spcross(calcCd.gpcross());
				calcs.get(len).spaura(calcCd.gpaura());
				calcs.get(len).spveil(calcCd.gpveil());
				calcs.get(len).sptrick(calcCd.gptrick());
				//引き継ぎここまで。
				if(calcunit.size() != 8){
					for(int j = calcunit.size();j < 9;j++){
						syunt[j] = calcs.get(len);
					}
				}else{
					syunt[calcunit.size()] = calcs.get(len);
				}
				//判定、パラメーターがあると相乗効果分があるのでその処理のための下準備。
				int counter = 0;
				for(int j = 0;j < syunt.length;j++){
					if(syunt[j].gsksha().equals("判定")){
						clctrprupbl = true;
						counter++;
					}else if(syunt[j].gsksha().equals("パラメーター")){
						clctrprupbl = true;
						counter++;
					}else if(syunt[j].gsksha().equals("シンクロ")){
						counter++;
					}
				}
				if(clctrprupbl){
					//判定、パラメーターとの相乗効果分も含んだ計算処理。
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
					double dfBS = exitmp - extrtmp - exprtmp;//これが相乗効果分。
					dfBS /= counter;
					oefsz += dfBS *(calcCd.gaccut()/calcMd.gmusictm());//シンクロなので特に数値を戻す必要なし。
				}else{

					/*
					if(calcCd.gptrick() == 1){
						tempsysf[len] = settrsf(syunt, frend, calcMd);
					}else{
						tempsysf[len] = setUnitsf(syunt, frend, calcMd);//シンクロ時のsfを格納させる。
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
				tmp += calcBS(unit, calcMd, tempsysf[len], tapscoreup);//シンクロ発動時のBSを計算させtmpに合算する。
			}
			//合算した分で割り、平均を出す。
			tmp -= calcBS(unit, calcMd, sf, tapscoreup)*calcs.size();//元の数値を引く。
			return (int)Math.floor(maxactcnt * prob * tmp*(accut/calcMd.gmusictm())*calcMd.gmaxcb());
		}else if(calcCd.gsksha().equals("リピート")){
			List<Card_datas> hits = new ArrayList<Card_datas>();
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(calcCd.getgrade()) && unit[len].getunitnm().equals(calcCd.getunitnm()) && !(unit[len].equals(calcCd))){
					hits.add(unit[len]);
				}
			}
			for(Card_datas tmp:hits){
				if(tmp.equals(calcCd)){
					System.out.println("特技リピートのスコアアップ期待値計算において、予期せぬエラーが発生しました。");
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
		//ユニット値Sfを求めるメソッド ユニット値,センタースキル増加分 という文字列を出力する。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;//ノネットが発動する条件を満たせば1そうでなければ0
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//もしかしたら渡す楽曲データは配列じゃなくて1つだけになるかも…

		//ノネットの処理．
		int muse = 0;
		int aqua = 0;
		for(Card_datas tmp: unit){
			if(tmp.getunitnm().equals("μ's")){
				muse++;
			}else if(tmp.getunitnm().equals("Aqours")){
				aqua++;
			}
		}
		if(muse == unit.length || aqua == unit.length){
			unitnnt = 1;
		}

		//カード値処理(掛け算における切り上げ無し)
		for (int len = 0; len < 9; len++) {
			if(cmscdt.gpprty().equals("スマイル")){
				sa[len] = unit[len].gcsm();
			}else if(cmscdt.gpprty().equals("ピュア")){
				sa[len] = unit[len].gcpr();
			}else{
				sa[len] = unit[len].gccl();
			}
			veil += unit[len].gpveil();
			aura += unit[len].gpaura();
			nonette += unit[len].gpnnet();
			bloom += unit[len].gpbloom();
		}

		//SIS処理
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

		//自ユニットのセンターサブセンター処理 SSRセンターとSRセンター処理できてませんねぇ？！
		if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
		if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
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
		} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
				|| centersklnm.indexOf("プリンセス") != -1) {
			cenup = 0.12;
			if (centersklnm.indexOf("エンジェル") != -1) {
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
			} else if (centersklnm.indexOf("エンプレス") != -1) {
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
			} else if (centersklnm.indexOf("プリンセス") != -1) {
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
		}else if (centersklnm.indexOf("スター") != -1){
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

		//フレンド処理 -無しならnullを入れることにする。
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
			if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
				cenup = 0.09;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
					|| centersklnm.indexOf("プリンセス") != -1) {
				if (centersklnm.indexOf("エンジェル") != -1) {
					cenup = 0.12;
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("エンプレス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("プリンセス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
						}
					}
				}
			} else if (centersklnm.indexOf("スター") != -1) {
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
		//トリック発動時のユニット値rSfを求めるメソッド ユニット値,センタースキル増加分 という文字列を出力する。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;//ユニットが統一されていれば1そうでなければ0.
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//もしかしたら渡す楽曲データは配列じゃなくて1つだけになるかも…

		//ノネットの処理．
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("μ's")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

		//キッスやパフュームを含めたカード値処理(掛け算における切り上げ無し)
		for (int len = 0; len < 9; len++) {
			if (cmscdt.gpprty().equals("スマイル")) {
				sa[len] = unit[len].gcsm();
			} else if (cmscdt.gpprty().equals("ピュア")) {
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

		//SIS処理
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
							//ringやcrossの処理考えてなかった…＼(^o^)／ｵﾜﾀ
				} else {
					sa[len] +=  Math.ceil(sa[len]*0.33 * (1+0.018*aura) * (1+0.024*veil) * (1+0.04*bloom) *(1+0.042*unitnnt*nonette))  + unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			}
		}

		//自ユニットのセンターサブセンター処理 SSRセンターとSRセンター処理できてませんねぇ？！
		if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
		if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
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
		} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
				|| centersklnm.indexOf("プリンセス") != -1) {
			if (centersklnm.indexOf("エンジェル") != -1) {
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
			} else if (centersklnm.indexOf("エンプレス") != -1) {
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
			} else if (centersklnm.indexOf("プリンセス") != -1) {
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
		} else if (centersklnm.indexOf("スター") != -1) {
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

		//フレンド処理 Empty is null.
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
			if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
				cenup = 0.09;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
					|| centersklnm.indexOf("プリンセス") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("エンジェル") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("エンプレス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("プリンセス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
						}
					}
				}
			} else if (centersklnm.indexOf("スター") != -1) {
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
		//ユニット値Sfを求めるメソッド ユニット値,センタースキル増加分 という文字列を出力する。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//もしかしたら渡す楽曲データは配列じゃなくて1つだけになるかも…

		//ノネットの処理。
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("μ's")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

		//キッスやパフュームを含めたカード値処理(掛け算における切り上げ無し)
		for (int len = 0; len < 9; len++) {
			if(cmscdt.gpprty().equals("スマイル")){
				sa[len] = unit[len].gcsm();
			}else if(cmscdt.gpprty().equals("ピュア")){
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

		//SIS処理
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
			if(unit[len].gsksha().equals("パラメーター")){
				upp = (unit[len].gefsz()/100.0)+1.0;
				tmp = unit[len];
			}
		}
		if(tmp != null){
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(tmp.getgrade()) && unit[len].getunitnm().equals(tmp.getunitnm())){
					//学年とメインユニット名が等しければ。
					sa[len] *= upp;
				}
			}
		}

		//自ユニットのセンターサブセンター処理 SSRセンターとSRセンター処理できてませんねぇ？！
		if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
		if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
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
		} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
				|| centersklnm.indexOf("プリンセス") != -1) {
			cenup = 0.12;
			if (centersklnm.indexOf("エンジェル") != -1) {
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
			} else if (centersklnm.indexOf("エンプレス") != -1) {
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
			} else if (centersklnm.indexOf("プリンセス") != -1) {
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
		}else if (centersklnm.indexOf("スター") != -1){
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

		//フレンド処理 Empty is null.
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
			if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
				cenup = 0.09;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
					|| centersklnm.indexOf("プリンセス") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("エンジェル") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("エンプレス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("プリンセス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
						}
					}
				}
			} else if (centersklnm.indexOf("スター") != -1) {
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
		//トリック発動時のユニット値rSfを求めるメソッド ユニット値,センタースキル増加分 という文字列を出力する。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		int bloom = 0;
		int nonette = 0;
		int unitnnt = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		int allsu = 0;
		Music_data cmscdt = cmdt;//もしかしたら渡す楽曲データは配列じゃなくて1つだけになるかも…

		//ノネットの処理。
		int muse = 0;
		int aqua = 0;
		for (Card_datas tmp : unit) {
			if (tmp.getunitnm().equals("μ's")) {
				muse++;
			} else if (tmp.getunitnm().equals("Aqours")) {
				aqua++;
			}
		}
		if (muse == unit.length || aqua == unit.length) {
			unitnnt = 1;
		}

		//キッスやパフュームを含めたカード値処理(掛け算における切り上げ無し)
		for (int len = 0; len < 9; len++) {
			if (cmscdt.gpprty().equals("スマイル")) {
				sa[len] = unit[len].gcsm();
			} else if (cmscdt.gpprty().equals("ピュア")) {
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

		//SIS処理
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
					//ringやcrossの処理考えてなかった…＼(^o^)／ｵﾜﾀ
				} else {
					sa[len] += Math
							.ceil(sa[len] * 0.33 * (1 + 0.018 * aura) * (1 + 0.024 * veil) * (1 + 0.04 * bloom)
									* (1 + 0.042 * unitnnt * nonette))
							+ unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0 + unit[len].gpwink() * 1400.0;
				}
			}
		}

		//パラメーターアップの処理．
		double upp = 0.0;
		Card_datas tmp = new Card_datas();
		for(int len = 0;len < unit.length;len++){
			if(unit[len].gsksha().equals("パラメーター")){
				upp = (unit[len].gefsz()/100.0)+1.0;
				tmp = unit[len];
			}
		}
		if(tmp != null){
			for(int len = 0;len < unit.length;len++){
				if(unit[len].getgrade().equals(tmp.getgrade()) && unit[len].getunitnm().equals(tmp.getunitnm())){
				//学年とメインユニット名が等しければ。
					sa[len] *= upp;
				}
			}
		}

		//自ユニットのセンターサブセンター処理 SSRセンターとSRセンター処理できてませんねぇ？！
		if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
		if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
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
		} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
				|| centersklnm.indexOf("プリンセス") != -1) {
			cenup = 0.12;
			if (centersklnm.indexOf("エンジェル") != -1) {
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
			} else if (centersklnm.indexOf("エンプレス") != -1) {
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
			} else if (centersklnm.indexOf("プリンセス") != -1) {
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
		} else if (centersklnm.indexOf("スター") != -1) {
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

		//フレンド処理 Empty -> null
		if(frend != null){
			subcentersklnm = frend.getacskn();
			centersklnm = frend.getcskin();
			if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
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
			if (centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")) {
				cenup = 0.09;
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] * cenup) + (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] * cenup);
					}
				}
			} else if (centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1
					|| centersklnm.indexOf("プリンセス") != -1) {
				cenup = 0.12;
				if (centersklnm.indexOf("エンジェル") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcpr());
						}
					}
				} else if (centersklnm.indexOf("エンプレス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gccl());
						}
					}
				} else if (centersklnm.indexOf("プリンセス") != -1) {
					for (int len = 0; len < unit.length; len++) {
						if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm())
									+ (int) Math.ceil(sa[len] * subcenup);
						} else {
							su[len] += (int) Math.ceil(cenup * unit[len].gcsm());
						}
					}
				}
			} else if (centersklnm.indexOf("スター") != -1) {
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
		 * スキル発動期待値が高い順にソートするメソッド
		 * unit[0]が一番期待値が高く、unit[8]が一番期待値が小さい。
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
		//積分済みの二項分布データを入力するメソッド。
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
		//実際の一回一回のスキル発動回数に対応する確率を入力するメソッド。
		double[] bf_p = new double[n];
		for(int r = 0; r < n;r++){
			bf_p[r] = nCr(n,r) * Math.pow(prob, r) * Math.pow((1 - prob), (n - r));
		}
		return bf_p;
	}

	public static Calc_data calcscrmain(Card_datas[] unit, Music_data calcMd, Card_datas frend, double perper, int playcount, double tapscoreup, int depth)throws DataNotFoundException{
		/*
		 *  ある特定の確率で出る最大スコアを計算するメソッド
		 *  メインのスコア計算メソッドとする。
		 *  派生版として、 1/プレイ回数 の確率を1ビットとして、グラフを表示させるメソッドも作成可能。
		 */
		double discriminant = (double)(1.0/playcount);
		String sf = setUnitsf(unit, frend, calcMd);
		int base_scr = calcBS(unit, calcMd, sf, tapscoreup);
		String actsf = new String();
		double[][] unitprbs = new double[9][];//実際の積分済みの確率分布を格納する配列
		double[][] unitsprbs = new double[9][];//積分する前の実際の確率を格納する配列。
		double[][] untupprbs = new double[9][];//発動確率アップの積分済みの確率分布を格納する配列
		double[][] untupsprbs = new double[9][];//積分する前の発動確率アップの確率を格納する配列。
		boolean upprbsbl = false;//発動率アップによる再計算をするか否かのbool値。通常は再計算しないのfalse.
		int[] maxactcnts = new int[9];
		int[] expactcnts = new int[9];
		int[] scrupT = new int[9];
		for(int len = 0; len < unit.length;len++){
			try{
				maxactcnts[len] = setMaxactcnt(unit[len], calcMd, perper, sf, unit);
			}catch(DataNotFoundException e){
				System.out.println(e);
				System.out.println("メソッドcalcscrmainにてsetMaxactcntメソッドが原因のエラー");
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
			if(unit[len].gsksha().equals("スコア")){
				scrupT[len] = unit[len].gefsz();
				if(unit[len].gpcharm() != 0 && unit[len].gpcharm() != -1){
					scrupT[len] *= (int)(unit[len].gpcharm()*2.5);
				}
			}else if(unit[len].gsksha().equals("回復")){
				scrupT[len] = unit[len].gefsz();
				scrupT[len] *= unit[len].gpheal()*480;//heal = 0 => 0;
			}else if(unit[len].gsksha().equals("判定")){
				double accut = unit[len].gaccut();
				//トリックが付いている場合
				if(unit[len].gptrick()==1){
					int counter = 0;
					boolean chkprsy = false;
					for(Card_datas tmp: unit){
						if(tmp.gsksha().equals("パラメーター")){
							chkprsy = true;
							counter++;
						}else if(tmp.gsksha().equals("シンクロ")){
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
						//常に判定強化が発動し、トリックが発動してるときのスコア(実際には起こりえない)
						int iBs = calcBS(unit, calcMd, sf, tapscoreup);
						//全く判定強化が発動せず、トリックが発動しないときのスコア
						int dfBs = trBs - iBs;
						//上記のスコア差(すなわちこれがトリックによる1曲中のすべてのスコア上昇分。)
						//※近似。最初の数秒は確実に判定強化が発動しない部分があるがそこが加味できていない。
						//つまり実際のトリックによる上昇分よりも高めに出力される。
						double oefsz = dfBs * (accut/calcMd.gmusictm());
						//accutは判定強化時間,musictmは楽曲時間なので1回判定強化が発動した時の楽曲中における割合をトリックによる1曲中のすべてのスコア上昇分に掛け算。
						scrupT[len] = (int)Math.floor(oefsz);
						//上記の数値をfloorすなわち小数点以下切り捨てでscrupT[]に格納。
						//scrupTに格納された値はこの後計算する、スキル発動回数との積を出すのに使われる。
					}
				}else{
					//そうでない場合は通常の1タップスコアでスコアを計算。
					scrupT[len] = 0;//スキルが何回発動しようがスコア上昇分は0.
				}
			}else if(unit[len].gsksha().equals("パーフェクト")){
				/*
				 * SISの処理無し
				 * 楽曲中に一定時間発動する。
				 * その一定時間中に飛んでくるノーツ数は未知数である。
				 * 正確な数値が計算できない。
				 * 多分だけれども判定強化の上位互換。
				 */
				double pfupt = unit[len].gaccut();
				int musictm = calcMd.gmusictm();
				int maxcombo = calcMd.gmaxcb();
				double nps = musictm / maxcombo;
				scrupT[len] = (int)Math.floor(nps*pfupt*unit[len].gefsz());
			}else if(unit[len].gsksha().equals("発動率")){
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
			}else if(unit[len].gsksha().equals("パラメーター")){
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
						if(tmp.gsksha().equals("判定")){
							chktrsy = true;
							counter++;
						}else if(tmp.gsksha().equals("シンクロ")){
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
			}else if(unit[len].gsksha().equals("シンクロ")){
				Card_datas calcCd = unit[len];
				double accut = calcCd.gaccut();
				double oefsz = 0.0;
				/*
				 * 対象のカードの中から1枚取得し、ユニットを作成しsfを再計算する。-> sysf
				 * sysfから元のsfを引きあとは判定強化の処理と同一。
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
							+ "のカードの期待値計算において異常処理が発生しました。");
					String err = new String();
					throw new NullPointerException(err);
				}
				String[] tempsysf = new String[calcs.size()];
				for (int k = 0; k < calcs.size(); k++) {
					for (int i = 0; i < calcunit.size(); i++) {
						syunt[i] = calcunit.get(i);
					}
					//SIS引き継ぎ
					calcs.get(k).spkiss(calcCd.gpkiss());
					calcs.get(k).sppfm(calcCd.gppfm());
					calcs.get(k).spring(calcCd.gpring());
					calcs.get(k).spcross(calcCd.gpcross());
					calcs.get(k).spaura(calcCd.gpaura());
					calcs.get(k).spveil(calcCd.gpveil());
					calcs.get(k).sptrick(calcCd.gptrick());
					//引き継ぎここまで。
					if(calcunit.size() != 8){
						for(int j = calcunit.size();j < 9;j++){
							syunt[j] = calcs.get(k);
						}
					}else{
						syunt[calcunit.size()] = calcs.get(k);
					}
					//判定、パラメーターがあると相乗効果分があるのでその処理のための下準備。
					int counter = 0;
					for(Card_datas tmp:syunt){
						clctrprupbl = true;
					}
					for(int j = 0;j < syunt.length;j++){
						if(clctrprupbl){
							if(syunt[j].gsksha().equals("判定")){
								clctrprupbl = true;
								counter++;
							}else if(syunt[j].gsksha().equals("パラメーター")){
								clctrprupbl = true;
								counter++;
							}else if(syunt[j].gsksha().equals("シンクロ")){
								counter++;
							}
						}
					}
					if(clctrprupbl){
						//判定、パラメーターとの相乗効果分も含んだ計算処理。
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
						double dfBS = exitmp - extrtmp - exprtmp;//これが相乗効果分。
						dfBS /= counter;
						oefsz += dfBS *(calcCd.gaccut()/calcMd.gmusictm());//シンクロなので特に数値を戻す必要なし。
					}else{

					/*
					if(calcCd.gptrick() == 1){
						tempsysf[k] = settrsf(syunt, frend, calcMd);
					}else{
						tempsysf[k] = setUnitsf(syunt, frend, calcMd);//シンクロ時のsfを格納させる。
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
						tmp += calcBS(unit, calcMd, tempsysf[k], tapscoreup);//シンクロ発動時のBSを計算させtmpに合算する。
					}
					//合算した分で割り、平均を出す。
					tmp -= calcBS(unit, calcMd, sf, tapscoreup)*calcs.size();//元の数値を引く。
					scrupT[len] = (int)Math.floor(tmp*(accut/calcMd.gmusictm())* calcMd.gmaxcb());
				}
			}else if(unit[len].gsksha().equals("リピート")){
				Card_datas calcCd = unit[len];
				List<Card_datas> hits = new ArrayList<Card_datas>();
				for(Card_datas tmp : unit){
					if(tmp.getgrade().equals(calcCd.getgrade()) && tmp.getunitnm().equals(calcCd.getunitnm()) && !(tmp.equals(calcCd))){
						hits.add(tmp);
					}
				}
				for(Card_datas tmp:hits){
					if(tmp.equals(calcCd)){
						System.out.println("特技リピートのスコアアップ期待値計算において、予期せぬエラーが発生しました。");
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
		double sumprob = 0.0;//積分済みの二項分布から検索する。
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
		 * 確率でソート。0番目が一番発動確率が高くなるようにユニットをソートする。
		 * pivotを制御する必要があるのでpointerを追加。
		 * その他確率分布を格納してる配列なども場合によってはnullpo落ちしかねないのでソートする。
		 */
		for(int i = 0;i < unit.length;i++){
			for(int k = unit.length-1;k > i;k--){
				//コピーを作成。
				Card_datas temp = unit[i];
				int pointer = regurarpivot_pointer[i];
				double[] prbs = unitprbs[i];
				double[] sprbs = unitsprbs[i];
				double[] upprbs = untupprbs[i];
				double[] upsprbs = untupsprbs[i];
				int actcnts = expactcnts[i];
				if(temp.gprob() < unit[k].gprob()){
					/*
					 * 大きい方の入力処理部分
					 * unit[i] = unit[k];
					 */
					regurarpivot_pointer[i] = k;
					unitprbs[i] = unitprbs[k];
					unitsprbs[i] = unitsprbs[k];
					expactcnts[i] = expactcnts[k];
					untupprbs[i] = untupprbs[k];
					untupsprbs[i] = untupsprbs[k];
					/*
					 * 小さい方の入力処理部分 コピーを代入。
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
																			//pivotの処理して、スコア比較。
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
																				//pivotの処理して、スコア比較。
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
			//発動率アップがすべての時間において発動してるときのスコアなのでmusictmでaccut(発動率アップ時間)を割る必要がある。

		}

		for(Card_datas sklup:unit){
			if(sklup.getskinm().equals("発動率")){
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
		//楽曲のタップスコアから最大ノーツ数を掛け算して全タップスコアを出力するメソッド
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

	public static double lanescr(int[] note, double base, Card_datas card, Music_data music, double tapscoreup) {//calcBSで使うメソッド **計算式的にはBaseは不必要である。
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
		if(note.length == 10){// <- Easy ～ Expertまでの譜面データの処理．
			return note[0]*Math.floor(base) + note[1]*Math.floor(ln*base)+note[2]*Math.floor(1.10 * base) + note[3]*Math.floor(1.10*ln*base)
					+ note[4]*Math.floor(1.15*base) + note[5]*Math.floor( 1.15 * ln * base) + note[6]*Math.floor(1.20 * base)
					+ note[7]*Math.floor(1.20 * ln * base) + note[8]*Math.floor(1.25 * base) + note[9]*Math.floor(1.25 * ln * base);
		}else if(note.length == 14){// <- Masterの処理の準備．メドフェスにも対応可能？
			return note[0]*Math.floor(base) + note[1]*Math.floor(ln*base)+note[2]*Math.floor(1.10 * base) + note[3]*Math.floor(1.10*ln*base)
					+ note[4]*Math.floor(1.15*base) + note[5]*Math.floor( 1.15 * ln * base) + note[6]*Math.floor(1.20 * base)
					+ note[7]*Math.floor(1.20 * ln * base) + note[8]*Math.floor(1.25 * base) + note[9]*Math.floor(1.25 * ln * base)
					+ note[10]*Math.floor(1.30 * base) + note[11]*Math.floor(1.30 * ln * base) + note[12]*Math.floor(1.35 * base) + note[13]*Math.floor(1.35 * base * ln);
		}else{
			System.err.println("楽曲の譜面データが読み込めませんでした。");
			return 0;
		}
	}
}
