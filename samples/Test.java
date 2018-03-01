package samples;

import java.math.*;
import java.util.*;

import com.sun.javafx.runtime.SystemProperties;

class Test{
	public static double lanescr(int[] note,double base){
		return note[0]*Math.floor(base)+note[1]*Math.floor(1.25*base)+note[2]*Math.floor(1.10*base)+note[3]*Math.floor(1.10*1.25*base)+note[4]*Math.floor(1.15*base)+note[5]*Math.floor(1.15*1.25*base)+note[6]*Math.floor(1.20*base)+note[7]*Math.floor(1.20*1.25*base)+note[8]*Math.floor(1.25*base)+note[9]*Math.floor(1.25*1.25*base);
	}
	public static String setUnitsf(Card_datas[] unit, Card_datas frend){
		//スコア計算におけるBaseを計算するメソッドとする。
		String subcentersklnm = unit[4].getacskn();
		String centersklnm = unit[4].getcskin();
		int[] su = new int[9];//Su Status of Unit カードステータスでスクールアイドルスキル増加分も含む。(これを9人分加算するとセンタースキルを省いた場合のユニット数値になる)
		int[] sa = new int[9];//Sa Status of one Card カードステータスでスクールアイドルスキル増加分を含まない。
		int veil = 0;
		int aura = 0;
		double cenup = 0;
		double subcenup = 0;
		int tmpsu = 0;

		for(int len = 0; len < 9; len++){
			sa[len] = unit[len].gcpr();
			veil += unit[len].gpveil();
			aura += unit[len].gpaura();
			sa[len] += unit[len].gpkiss()*200.0+ unit[len].gppfm()*450.0;
		}
		for(int len = 0;len < 9;len++){
			if(unit[len].gpcross() != 0 || unit[len].gpring() != 0){
				sa[len] += Math.ceil(sa[len]*0.018)*aura + Math.ceil(sa[len]*0.024)*veil + Math.ceil(sa[len]*0.16)*unit[len].gpcross()+ Math.ceil(sa[len]*0.10)*unit[len].gpring();
			}else{
				sa[len] += Math.ceil(sa[len]*0.018)*aura + Math.ceil(sa[len]*0.024)*veil;
			}
		}

		//自ユニットのセンターサブセンター処理 SSRセンターとSRセンター処理できてませんねぇ？！
		if(subcentersklnm.equals("μ's") || subcentersklnm.equals("Aqours")){
			subcenup = 0.03;
		}else{
			subcenup = 0.06;
		}

		if(centersklnm.equals("ピュアエンジェル") || centersklnm.equals("クールエンプレス") || centersklnm.equals("スマイルプリンセス")){
			cenup = 0.09;
			for(int len = 0;len < unit.length;len++){
				if(subcentersklnm.equals(unit[len].getgrade())||subcentersklnm.equals(unit[len].getsubuntnm())){
					tmpsu += sa[len];
					su[len] = (int)Math.ceil(sa[len]*cenup) + (int)Math.ceil(sa[len]*subcenup);
				}else{
					tmpsu += sa[len];
					su[len] = (int)Math.ceil(sa[len]*cenup);
				}
			}
		}else if(centersklnm.indexOf("エンジェル") != -1 || centersklnm.indexOf("エンプレス") != -1 || centersklnm.indexOf("プリンセス") != -1){
			//わからないーわからないーままでーなんとかなるさ と Ah　はじめよう　君のこころは　かｇ（ry
			if(centersklnm.indexOf("エンジェル") != -1){
				for(int len = 0;len < unit.length;len++){
					if(subcentersklnm.equals(unit[len].getgrade())||subcentersklnm.equals(unit[len].getsubuntnm())){
						tmpsu += sa[len];
						su[len] = (int)Math.ceil(sa[len]+0.12*unit[len].gcpr())+(int)Math.ceil(sa[len]*subcenup);
					}else{
						tmpsu += sa[len];
						su[len] = (int)Math.ceil(sa[len]+0.12*unit[len].gcpr());
					}
						//round up してほしい。
				}
			}else if(centersklnm.indexOf("エンプレス") != -1){
					for(int len = 0;len < unit.length;len++){
						if(subcentersklnm.equals(unit[len].getgrade())||subcentersklnm.equals(unit[len].getsubuntnm())){
							tmpsu += sa[len];
							su[len] = (int)Math.ceil(sa[len]+0.12* unit[len].gccl())+(int)Math.ceil(sa[len]*subcenup);
						}else{
							tmpsu += sa[len];
							su[len] = (int)Math.ceil(sa[len]+0.12* unit[len].gccl());
						}
							//round up してほしい。
					}
			}else{
				for(int len = 0;len < unit.length;len++){
					if(subcentersklnm.equals(unit[len].getgrade())||subcentersklnm.equals(unit[len].getsubuntnm())){
						tmpsu += sa[len];
						su[len] = (int)Math.ceil(sa[len]+0.12*unit[len].gcsm()) + (int)Math.ceil(sa[len]*subcenup);
					}else{
						tmpsu += sa[len];
						su[len] = (int)Math.ceil(sa[len]+0.12* unit[len].gcsm());
					}
						//round up してほしい。
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
			//わからないーわからないーままでーなんとかなるさ と Ah　はじめよう　君のこころは　かｇ（ry
			if (centersklnm.indexOf("エンジェル") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcpr());
					}
					//round up してほしい。
				}
			} else if (centersklnm.indexOf("エンプレス") != -1) {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gccl());
					}
					//round up してほしい。
				}
			} else {
				for (int len = 0; len < unit.length; len++) {
					if (subcentersklnm.equals(unit[len].getgrade()) || subcentersklnm.equals(unit[len].getsubuntnm())) {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm())
								+ (int) Math.ceil(sa[len] * subcenup);
					} else {
						su[len] += (int) Math.ceil(sa[len] + 0.12 * unit[len].gcsm());
					}
					//round up してほしい。
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
		for(int len = 0;len < 9;len++){
			allsu += su[len];
		}
		tmpsu += allsu;
		String rtnstr = tmpsu + "," +allsu;

		return rtnstr;
	}

	//https://teratail.com/questions/9363
	//上記のpythonプログラムを参考にした。
	public static double nCr(int n, int r){
		r = Math.min(r, n-r);
		if(r == 0)return 1;
		if(r == 1)return n;
		double[] numerator = new double[n-r];
		for(int i = 0;i<r;i++){
			numerator[i] = n - r + 1 + i;
		}
		double[] denominator = new double[r];
		for(int i = 0;i < r; i++){
			denominator[i] = i + 1;
		}
		for(int p = 2;p < r + 1;p++){
			double pivot = denominator[p-1];
			if(pivot > 1){
				int offset = (n - r) % p;
				for(int k = p - 1; k < r;k += p){
					numerator[k - offset] /= pivot;
					denominator[k] /= pivot;
				}
			}
		}
		double result = 1;
		for(int k = 0;k < r;k++){
			if(numerator[k] > 1){
				result *= numerator[k];
			}
		}
		return result;
	}

	public static double[] prob(int n, double prob){
		double[] bf_p = new double[n];
		for(int r = 0; r < n; r++){
			if(r == 0){
				bf_p[r] = 1;
			}else{
				/*if(bf_p[r-1] - (nCr(n,r))*Math.pow(prob,r)*Math.pow((1-prob),(n-r)) < 0){
					bf_p[r] = bf_p[r-1];
				}else{*/
					bf_p[r] = bf_p[r-1] - (nCr(n,r))*Math.pow(prob,r)*Math.pow((1-prob),(n-r));
				//}
			}

		}
		return bf_p;
	}

	public static int setMaxactcnt(Card_datas ondt, int maxcombo, double perper, int musictm, String sf, int star_icon/*, Card_datas[] unit*/){
		int mactct = 0;
		int base = Integer.parseInt(sf.split(",",0)[0]);
		String skitp = ondt.gskitp();
		//check Card_datas line 521
		if(ondt.gskitp().equals("タイマー")){
			mactct = (int)Math.floor(musictm/ondt.gfactv());
		}else if(ondt.gskitp().equals("リズムアイコン")){
			mactct = (int)Math.floor(maxcombo/ondt.gfactv());
		}else if(ondt.gskitp().equals("パーフェクト")){
			mactct = (int)Math.floor(maxcombo * perper/ondt.gfactv());
		}else if(ondt.gskitp().equals("コンボ")){
			mactct = (int)Math.floor(maxcombo / ondt.gfactv());
		}else if(ondt.gskitp().equals("スコア")){
			mactct = (int)Math.floor(base * maxcombo * 1.3 / (80 * ondt.gfactv()));
		}else if(ondt.gskitp().equals("スターアイコン")){
			mactct = (int)Math.floor(star_icon / ondt.gfactv());
		}else if(ondt.gskitp().equals("チェイン")){
			//処理が思いつかない
			/*for(int len = 0;len < unit.length;len++){
				if(ondt.getgrade().equals(unit[len].getgrade())){

				}
			}*/
			//仕方ないので未実装
		}
		return mactct;
	}

	public static void main(String[] args){
		long start = System.currentTimeMillis();
		int musictm = 98;//楽曲時間
		Card_datas[] unit = new Card_datas[9];
		unit[0] = new Card_datas(1, "桜内梨子", "スマイル", "UR", "私の声聞こえますか", true, 4, 4, "スコア", "リズムアイコン", 23, 47, 0.0, 1580,
				6320, 4100, 4490, "スマイルプリンセス", "Guilty Kiss");
		unit[1] = new Card_datas(2, "松浦果南", "クール", "UR", "わんわん巡回", true, 4, 4, "スコア", "コンボ", 22, 43, 0.0, 1670, 4100,
				4460, 6360, "クールエンプレス", "AZELEA");
		unit[2] = new Card_datas(3, "渡辺曜", "ピュア", "SSR", "アイドルも水泳も", true, 6, 1, "回復", "リズムアイコン", 15, 23, 0.0, 3, 3440,
				5920, 4340, "ピュアパワー", "Aqours");
		unit[3] = new Card_datas(4, "西木野真姫", "ピュア", "SSR", "暑い日に2人で", true, 6, 3, "スコア", "コンボ", 19, 37, 0.0, 780, 3520,
				5850, 4240, "ピュアパワー", "BiBi");
		unit[4] = new Card_datas(5, "南ことり", "ピュア", "UR", "プールで２人きり", true, 4, 3, "回復", "リズムアイコン", 21, 44, 0.0, 5, 4120,
				6370, 4420, "ピュアエンジェル", "2年生");
		unit[5] = new Card_datas(6, "園田海未", "ピュア", "UR", "クラシックメイド", true, 4, 4, "回復", "リズムアイコン", 23, 49, 0.0, 5, 4170,
				6320, 4370, "ピュアエンジェル", "μ's");
		unit[6] = new Card_datas(7, "津島善子", "ピュア", "UR", "たゆたうクラゲ", true, 4, 3, "スコア", "パーフェクト", 26, 44, 0.0, 1415,
				4040, 6310, 4540, "ピュアエンジェル", "1年生");
		unit[7] = new Card_datas(8, "小泉花陽", "ピュア", "UR", "花陽のにぎりめし", true, 4, 3, "スコア", "パーフェクト", 20, 43, 0.0, 1160,
				4530, 6300, 4040, "ピュアエンジェル", "Printemps");
		unit[8] = new Card_datas(9, "園田海未", "ピュア", "SR", "敏腕マネージャー", true, 4, 5, "スコア", "スコア", 13500, 17, 0.0, 1290,
				3650, 5260, 3820, "ピュアエンジェル", "2年生");
		Card_datas frend = new Card_datas(5, "南ことり", "ピュア", "UR", "プールで２人きり", true, 4, 3, "回復", "リズムアイコン", 21, 44, 0.0,
				5, 4120, 6370, 4420, "ピュアエンジェル", "2年生");
		//double[] probs = new double[unit.length];
		//int maxcombo = 496;
		//double perper = 0.93;
		/*for(int len = 0;len < unit.length;len++){
			probs[len] = unit[len].gprob/100.0;
		}
		int[] effects = new int[9];
		for(int len = 0;len < unit.length;len++){
			effects[len] = unit[len].gefsz();
		}
		int star_icon = 63;
		double perper = 0.93;
		int[] runskl = new int[9];
		for(int len = 0;len < unit.length;len++){
			runskl[len] = setMaxactcnt(unit[len], maxcombo, perper, musictm, setUnitsf(unit,frend), star_icon);
		}
		*/
		double[] probs = {0.47,0.43,0.37,0.23,0.44,0.44,0.49,0.43,0.17};//ここの即値をCard_datasだけで表現できたらこのjavaファイルの役目は終わり
		int[] runskl = {21,22,26,33,23,17,21,23,57};
		int[] effects = {1580,1670,780,0,5,1415,5,1160,1290};
		//int[] unitpivots = {1,0,8,7,5,6,4,2,3};
		effects[0] *= 2.5;
		effects[1] *= 2.5;
		effects[5] *= 2.5;
		effects[7] *= 2.5;
		effects[8] *= 2.5;

		effects[4] *= 480;
		effects[6] *= 480;
		ArrayList<double[]> ar_rprobs = new ArrayList<double[]>();
		double k = 1/120.0;//分母の最小値は78.12
		for(int len = 0;len < 9;len++){
			double[] temp = prob(runskl[len]+1,probs[len]);
			ar_rprobs.add(temp);
		}
		double[][] rprobs = new double[ar_rprobs.size()][];
		for(int len = 0;len < ar_rprobs.size();len++){
			rprobs[len] = ar_rprobs.get(len);
		}
		int[] rpvt = new int[9];
		int[] pivot = new int[9];
		for(int len = 0;len < 9;len++){
			rpvt[len] = 0;
			pivot[len] = 0;
		}
		//int sumeff = 0;
		int tempscr = 0;
		long steps = 0;
		//ArrayList<Double> al_cprbs = new ArrayList<Double>();
		//kを超えるまでの確率分布を作成する(probでやってるような処理。※逆からなので0+=probとなる。);
		double sumprob = 0.0;
		double ansprob = 0.0;
		//label:for(int a = rprobs[1].length-1;a > Math.floor((rprobs[1].length-1)*probs[1]);a--){
			/*  gのコメント参照 ループを回さずに確率分布を加算し、次のループへ。 */
			//for(int b = rprobs[0].length-1;b > Math.floor((rprobs[0].length-1)*probs[0]);b--){
				/*  gのコメント参照 ループを回さずに確率分布を加算し、次のループへ。 */
				/*temp = sumprob;
				temp += rprobs[1][a] * ps[0] * ps[8] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
				if(temp < k || a < Math.floor((rprobs[1].length-1)*probs[1])){
					sumprob += rprobs[1][a] * ps[0] * ps[8] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
					System.out.println("b is break");
					System.out.println("sumprob = "+sumprob);
					a--;
					break;
				}*/
				//for(int c = rprobs[8].length-1;c > Math.floor((rprobs[8].length-1)*probs[8]);c--){
					/*  gのコメント参照 ループを回さずに確率分布を加算し、次のループへ。
					 */
					/*temp = sumprob;
					temp += rprobs[1][a] * rprobs[0][b] * ps[8] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
					if(temp < k || b < Math.floor((rprobs[0].length-1)*probs[0])){
						sumprob += rprobs[1][a] * rprobs[0][b] * ps[8] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
						System.out.println("c is break");
						System.out.println("sumprob = "+sumprob);
						b--;
						break;
					}*/
					//for(int d = rprobs[7].length-1;d > Math.floor((rprobs[7].length-1)*probs[7]);d--){
						/* gのコメント参照 ループを回さずに確率分布を加算し、次のループへ。
						 */
						/*temp = sumprob;
						temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
						if(temp < k || c < Math.floor((rprobs[8].length-1)*probs[8])){
							sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * ps[7] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
							System.out.println("d is break");
							System.out.println("sumprob = " + sumprob);
							c--;
							break;
						}*/
						//for(int e = rprobs[5].length-1;e > Math.floor((rprobs[5].length-1)*probs[5]);e--){
							/* gのコメント参照ループを回さずに確率分布を加算し、次のループへ。
							 */
							/*temp = sumprob;
							temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
							if(temp < k || d < Math.floor((rprobs[7].length-1)*probs[7])){
								sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * ps[5] * ps[6] * ps[4] * ps[2] * ps[3];
								System.out.println("e is break");
								System.out.println("sumprob = " + sumprob);
								d--;
								break;
							}*/
							//for(int f = rprobs[6].length-1;f > Math.floor((rprobs[6].length-1)*probs[6]);f--){
								/* gのコメント参照 ループを回さずに確率分布を加算し、次のループへ。
								 */
								/*temp = sumprob;
								temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * ps[6] * ps[4] * ps[2] * ps[3];
								if(temp < k || e < Math.floor((rprobs[5].length-1)*probs[5])){
									sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * ps[6] * ps[4] * ps[2] * ps[3];
									System.out.println("f is break");
									System.out.println("sumprob = " + sumprob);
									e--;
									break;
								}*/
								//for(int g = rprobs[4].length-1;g > Math.floor((rprobs[4].length-1)*probs[4]);g--){
									/* if(sumprob += 1ループして得られる確率分布の全体 < k){
										sumprobをgの1ループ分だけ足し算して、
										fのループを1つ進める。//このように処理するとfor文を回す回数そのものが減り、計算回数も減り、計算時間も減る。
									}
									 */
									/*temp = sumprob;
									temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * ps[4] * ps[2] * ps[3];
									if(temp < k || f < Math.floor((rprobs[6].length-1)*probs[6])){
										sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * ps[4] * ps[2] * ps[3];
										System.out.println("g is break");
										System.out.println("sumprob = " + sumprob);
										f--;
										break;
									}*/
									//for(int h = rprobs[2].length-1;h > Math.floor((rprobs[2].length-1)*probs[2]);h--){
										/*if(sumprob += rprobs[1][a] * ... * rprobs[2]の確率分布全体 * rprobs[3][9] < k ){
											sumprobをhの1ループ分だけ足し算して、
											hのループを1つ進める．
										}
										*/
										/*temp = sumprob;
										temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * ps[2] * ps[3];
										if(temp < k || g < Math.floor((rprobs[4].length-1)*probs[4])){
											sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * ps[2] * ps[3];
											System.out.println("h is break");
											System.out.println("sumprob = " + sumprob);
											g--;
											break;
										}*/
										//for(int i = rprobs[3].length-1;i > Math.floor((rprobs[3].length-1)*probs[3]);i--){
											/*temp = sumprob;
											temp += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * rprobs[2][h] * ps[3];
											if(temp < k){
												sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * rprobs[2][h] * ps[3];
												break;
											}*/
											//sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d]
											//		* rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * rprobs[2][h]
											//		* rprobs[3][i];
											//steps++;
											//System.out.println("sumprob = "+sumprob);
											/*if(sumprob += rprobs[1][a] * ... * rprobs[3]の確率分布全体 < k){
												sumprobをiの1ループ文だけ足し算して、
												hのループを一つ進める.
											}*/
											/*if(sumprob > k && rprobs[1][a] > k && rprobs[0][b] > k && rprobs[8][c] > k && rprobs[7][d] > k && rprobs[5][e] > k && rprobs[6][f] > k && rprobs[4][g] > k && rprobs[2][h] > k && rprobs[3][i] > k){
												rpvt[1] = a;
												rpvt[0] = b;
												rpvt[8] = c;
												rpvt[7] = d;
												rpvt[5] = e;
												rpvt[6] = f;
												rpvt[4] = g;
												rpvt[2] = h;
												rpvt[3] = i;
												tempscr = 0;
												for (int elem = 0; elem < 9; elem++) {
													tempscr += effects[elem] * rpvt[elem];
												}
												break label;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
		double dk = k/10.0;
		double tempprob = 0.0;
		int tempef = 0;
		/*for(double d:rprobs[8]){
			System.out.println("d is "+d);
		}*/
		for(int a = rprobs[1].length -1;a >= 0;a--){
		  if(rprobs[1][a] > dk && a >= (int)Math.floor(probs[1] * runskl[1])-4 && rprobs[1][a] > dk){
			tempprob = rprobs[1][a];
			for(int b = rprobs[0].length -1 ; b >= 0;b--){
			  if(tempprob * rprobs[0][b] > dk && b >= (int)Math.floor(probs[0]*runskl[0])-4 && rprobs[0][b] > dk){
				tempprob *= rprobs[0][b];
				for(int c = rprobs[8].length-1;c >= 0;c--){
				  //System.out.println("sumprob = "+sumprob);
				  if(tempprob * rprobs[8][c] > dk && c >= (int)Math.floor(probs[8]*runskl[8])-4 && rprobs[8][c] > dk){
					tempprob *= rprobs[8][c];
					for(int d = rprobs[7].length-1;d >= 0;d--){
					  if(tempprob * rprobs[7][d] > dk && d >= (int)Math.floor(probs[7]*runskl[7])-4 && rprobs[7][d] > dk){
						tempprob *= rprobs[7][d];
						for(int e = rprobs[5].length-1;e >= 0;e--){
						  //System.out.println("sumprob ="+sumprob);
						  if(tempprob * rprobs[5][e] > dk && e >= (int)Math.floor(probs[5]*runskl[5])-4 && rprobs[5][e] > dk){
							tempprob *= rprobs[5][e];
							for(int f = rprobs[6].length-1;f >= 0;f--){
							  if(tempprob * rprobs[6][f] > dk && f >= (int)Math.floor(probs[6]*runskl[6])-4 && rprobs[6][f] > dk){
								tempprob *= rprobs[6][f];
								for(int g = rprobs[4].length-1;g >= 0;g--){
								  if(tempprob * rprobs[4][g] > dk && g >= (int)Math.floor(probs[4]*runskl[4])-4 && rprobs[4][g] > dk){
									tempprob *= rprobs[4][g];
									for(int h = rprobs[2].length-1;h >= 0;h--){
									  if(tempprob * rprobs[2][h] > dk && h >= (int)Math.floor(probs[2]*runskl[2])-4 && rprobs[2][h] > dk){
										tempprob *= rprobs[2][h];
										for(int i = rprobs[3].length-1;i >= 0;i--){
										  tempprob *= rprobs[3][i];
										  sumprob += tempprob;
										  steps++;
										  tempprob /= rprobs[3][i];
										  //System.out.println("step i");
										  //System.out.println("sumprob = "+sumprob);
										  if(sumprob > k && rprobs[3][i] > k){
											//System.out.println("sumprob is "+ sumprob);
											pivot[1] = a;
											pivot[0] = b;
											pivot[8] = c;
											pivot[7] = d;
											pivot[5] = e;
											pivot[6] = f;
											pivot[4] = g;
											pivot[2] = h;
											pivot[3] = i;
											//tempscr = 0;
											for(int j = 0;j < 9;j++){
											  tempef += effects[j] * pivot[j];
											}
											//System.out.println("tempscr = "+ tempscr);
											//System.out.println("tempef = "+tempef);
											if(tempef > tempscr){
											  //System.out.println("get answer.");
											  ansprob = sumprob;
											  tempscr = tempef;
											  rpvt[1] = a;  rpvt[0] = b;  rpvt[8] = c;
											  rpvt[7] = d;  rpvt[5] = e;  rpvt[6] = f;
											  rpvt[4] = g;  rpvt[2] = h;  rpvt[3] = i;
											}
											tempef = 0;
										  }
										}
									  }else{
									  steps += rprobs[3].length;
									  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c]*rprobs[7][d]*rprobs[5][e]*rprobs[6][f]*rprobs[4][g]*rprobs[2][h];
									  tempprob = rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f] * rprobs[4][g];
									  }
									}
								  }else{
								  steps += rprobs[2].length * rprobs[3].length;
								  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c]*rprobs[7][d]*rprobs[5][e]*rprobs[6][f]*rprobs[4][g];
								  tempprob = rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e] * rprobs[6][f];
								  }
								}
							  }else{
							  steps += rprobs[4].length * rprobs[2].length * rprobs[3].length;
							  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c]*rprobs[7][d]*rprobs[5][e]*rprobs[6][f];
							  tempprob = rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d] * rprobs[5][e];
							  }
							}
						  }else{
						  steps += rprobs[6].length * rprobs[4].length * rprobs[2].length * rprobs[3].length;
						  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c]*rprobs[7][d]*rprobs[5][e];
						  tempprob = rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d];
						  }
						}
					  }else{
					  steps += rprobs[5].length * rprobs[6].length * rprobs[4].length * rprobs[2].length * rprobs[3].length;
					  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c]*rprobs[7][d];
					  tempprob = rprobs[1][a] * rprobs[0][b] * rprobs[8][c];
					  }
					}
				  }else{
				  steps += rprobs[7].length * rprobs[5].length * rprobs[6].length * rprobs[4].length * rprobs[2].length * rprobs[3].length;
				  sumprob = rprobs[1][a]*rprobs[0][b]*rprobs[8][c];
				  tempprob = rprobs[1][a] * rprobs[0][b];
				  }
				}
			  }else{
			  steps += rprobs[8].length * rprobs[7].length * rprobs[5].length * rprobs[6].length * rprobs[4].length * rprobs[2].length * rprobs[3].length;
			  sumprob = rprobs[1][a]*rprobs[0][b];
			  tempprob = rprobs[1][a];
			  }
			}
		  }else{
		  steps += rprobs[0].length * rprobs[8].length * rprobs[7].length * rprobs[5].length * rprobs[6].length * rprobs[4].length * rprobs[2].length * rprobs[3].length;
		  sumprob = rprobs[1][a];
		  tempprob = 0.0;
		  }
		}
		for(int len = 0;len < 9;len++){
			System.out.println(unit[len].grrity()+":"+unit[len].getname()+":"+unit[len].getskinm()+":スキル発動回数:"+rpvt[len] +":発生確率("+(rprobs[len][rpvt[len]]*100)+"%)");
		}
		System.out.println("楽曲名:海岸通りで待ってるよ");
		System.out.println("特技スコアアップ期待値:"+tempscr+"("+ansprob*100+"%)");
		System.out.println("計算した数値が条件を満たすまでに計算がループした回数:"+steps);
		System.out.println("ベーススコア計算");
		unit[2].spcross(1); unit[2].spaura(1);
		unit[3].spcross(1); unit[3].spaura(1);
		String sfstr = setUnitsf(unit,frend);
		int unitsf = Integer.parseInt(sfstr.split(",",0)[0]);
		sfstr = sfstr.replaceAll(",","+");
		System.out.println("ユニット値:" + sfstr);
		double base = unitsf / 80.0;
		int sumscr = 0;
		double a = 0.0;
		a = 5*base+0*1.25*base + 5*1.10*base+1*1.10*1.25*base + 7*1.15*base+1*1.15*1.25*base + 15*1.20*base+1*1.20*1.25*base + 9*1.25*base+1*1.25*1.25*base;
		a = a*1.10;
		double b = 0.0;
		int[] blane = {6,0,8,0,11,2,20,2,9,0};
		b = lanescr(blane, base);
		b = b*1.10;
		double c = 0.0;
		int[] clane = {8,0,6,1,15,1,23,2,15,0};
		c = lanescr(clane, base);
		c = c*1.10*1.10;
		double d = 0.0;
		int[] dlane = {4,1,3,1,10,1,25,1,11,0};
		d = lanescr(dlane, base);
		d = d*1.10;
		double e = 0.0;
		int[] elane = {3,0,1,0,3,0,19,0,8,0};
		e = lanescr(elane, base);
		e = e*1.10;
		int[] flane = {2,1,4,1,12,1,27,1,10,0};
		double f = lanescr(flane, base);
		f = f*1.10;
		int[] glane ={7,0,5,1,16,0,24,2,15,0};
		double g = lanescr(glane, base);
		g = g*1.10*1.10;
		int[] hlane = {7,0,9,0,9,1,21,1,9,0};
		double h = lanescr(hlane, base);
		h = h*1.10;
		int[] ilane = {6,0,4,0,8,2,15,1,8,1};
		double i = lanescr(ilane, base);
		i = i*1.10;
		sumscr = (int)Math.floor(a+b+c+d+e+f+g+h+i);
		System.out.println("ベーススコア:"+sumscr);
		sumscr += tempscr;
		System.out.println("スコア期待値："+sumscr+"：このスコアを超えるスコアが出る確率は"+ansprob*100+"%");
		System.out.println("sumprob is "+sumprob*100+"%");
		long end = System.currentTimeMillis();
		System.out.println("計算終了。計算に掛かった時間は"+(end-start)+"msです。");
	}
}
