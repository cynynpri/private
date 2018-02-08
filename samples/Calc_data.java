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
		if(pprty.equals("スマイル")){
			//
			fldt = Card_datas.csmsort(fldt);
		}else if(pprty.equals("ピュア")){
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
	//上記のpythonプログラムを参考にした。

	//スキル発動最大回数を計算するメソッド
	public static int setMaxactcnt(Card_datas ondt, Music_data calcMd, double perper, String sf/*, Card_datas[] unit*/) {
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
			//処理が思いつかない
			/*for(int len = 0;len < unit.length;len++){
				if(ondt.getgrade().equals(unit[len].getgrade())){

				}
			}*/
			//仕方ないので未実装
		}
		return mactct;
	}

	//スキル発動期待値を計算するメソッド
	public static int setsklexp(Card_datas calcCd, double perper, String sf, Card_datas[] unit, Music_data calcMd){
		//引数のperperはパフェ率のこと。
		//sfはユニットの値
		//unitは一枚のカード
		//calcMdは計算したい楽曲
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
		//(1/プレイ回数)の確率で出せる最大スコア計算アルゴリズムを以下に示す.
		// 1.スコア期待値順にソートする。
		// 2.スコア期待値のもっとも高い子が最も発動し、他のカードとの発動確率の積が(1/プレイ回数)を満たせばよい
		// 3.以上のものを計算する。しかし、計算には条件が存在し、
		// まず１より小の積であるゆえ、1/120*1*1...*1みたいなことはできない(各カードの確率分布的にもありえない)
		// それ故各カード1/120以上の確率で発生しうる事象を積に使わざるを得ない。
		// ここまでくると高々有限個さらには１桁回数の計算にもなりうるが、方程式を解く必要がある。


	}*/

	public static String setUnitsf(Card_datas[] unit,Card_datas frend, Music_data cmdt){
		//ユニット値Sfを求めるメソッド ユニット値,センタースキル増加分 という文字列を出力する。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		String rrity = unit[4].grrity();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;
		Music_data cmscdt = cmdt;//もしかしたら渡す楽曲データは配列じゃなくて1つだけになるかも…

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
			sa[len] += unit[len].gpkiss() * 200.0 + unit[len].gppfm() * 450.0;
		}

		//SIS処理
		for (int len = 0; len < 9; len++) {
			if (unit[len].gpcross() != 0 || unit[len].gpring() != 0) {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil
						+ Math.ceil(sa[len] * 0.16) * unit[len].gpcross()
						+ Math.ceil(sa[len] * 0.10) * unit[len].gpring();
			} else {
				sa[len] += Math.ceil(sa[len] * 0.018) * aura + Math.ceil(sa[len] * 0.024) * veil;
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
			if (centersklnm.indexOf("エンジェル") != -1) {
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
			} else if (centersklnm.indexOf("エンプレス") != -1) {
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
			} else if (centersklnm.indexOf("プリンセス") != -1) {
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

		//フレンド処理
		subcentersklnm = frend.getacskn();
		centersklnm = frend.getcskin();
		if (subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")) {
			subcenup = 0.03;
		} else {
			subcenup = 0.06;
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
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr());
					}
				}
			} else if (centersklnm.indexOf("エンプレス") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl());
					}
				}
			} else if (centersklnm.indexOf("プリンセス") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm());
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
		// ある特定の確率で出る最大スコアを計算するメソッド
		// 派生版として、 1/プレイ回数 の確率を1ビットとして、グラフを表示させるメソッドも作成可能。
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

	public static double lanescr(int[] note, double base) {//calcBSで使うメソッド **計算式的にはBaseは不必要である。
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
