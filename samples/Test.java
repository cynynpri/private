import java.math.*;
import java.util.*;

class Test{
	public static double lanescr(int[] note,double base){
		return note[0]*base+note[1]*1.25*base+note[2]*1.10*base+note[3]*1.10*1.25*base+note[4]*1.15*base+note[5]*1.15*1.25*base+note[6]*1.20*base+note[7]*1.20*1.25*base+note[8]*1.25*base+note[9]*1.25*1.25*base; 
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
				if(bf_p[r-1] - (nCr(n,r))*Math.pow(prob,r)*Math.pow((1-prob),(n-r)) < 0){
					bf_p[r] = bf_p[r-1];
				}else{
					bf_p[r] = bf_p[r-1] - (nCr(n,r))*Math.pow(prob,r)*Math.pow((1-prob),(n-r));
				}
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
			runskl[len] = setMaxactcnt(unit[len], maxcombo, perper, musictm, setUnitsf(unit,frend), star_icon)
		}
		*/
		double[] probs = {0.47,0.43,0.37,0.23,0.44,0.44,0.49,0.43,0.17};//ここの即値をCard_datasだけで表現できたらこのjavaファイルの役目は終わり
		int[] runskl = {21,22,26,33,23,17,21,23,57};
		int[] effects = {1580,1670,780,0,5,1415,5,1160,1290};
		effects[0] *= 2.5;
		effects[1] *= 2.5;
		effects[5] *= 2.5;
		effects[7] *= 2.5;
		effects[8] *= 2.5;

		effects[4] *= 480;
		effects[6] *= 480;
		ArrayList<double[]> ar_rprobs = new ArrayList<double[]>();
		double k = 5/100.0;//分母の最小値は78.12
		for(int len = 0;len < 9;len++){
			double[] temp = prob(runskl[len]+1,probs[len]);
			ar_rprobs.add(temp);
		}
		double[][] rprobs = new double[ar_rprobs.size()][];
		for(int len = 0;len < ar_rprobs.size();len++){
			rprobs[len] = ar_rprobs.get(len);
		}
		int[] rpvt = new int[9];
		//int[] pivot = new int[9];
		//int sumeff = 0;
		int tempscr = 0;
		long steps = 0;
		//ArrayList<Double> al_cprbs = new ArrayList<Double>();
		//kを超えるまでの確率分布を作成する(probでやってるような処理。※逆からなので0+=probとなる。);
		double sumprob = 0.0;
		label:for(int a = rprobs[1].length-1;a > Math.floor((rprobs[1].length-1)*probs[1]);a--){
			for(int b = rprobs[0].length-1;b > Math.floor((rprobs[0].length-1)*probs[0]);b--){
				for(int c = rprobs[8].length-1;c > Math.floor((rprobs[8].length-1)*probs[8]);c--){
					for(int d = rprobs[7].length-1;d > Math.floor((rprobs[7].length-1)*probs[7]);d--){
						for(int e = rprobs[5].length-1;e > Math.floor((rprobs[5].length-1)*probs[5]);e--){
							for(int f = rprobs[6].length-1;f > Math.floor((rprobs[6].length-1)*probs[6]);f--){
								for(int g = rprobs[4].length-1;g > Math.floor((rprobs[4].length-1)*probs[4]);g--){
									for(int h = rprobs[2].length-1;h > Math.floor((rprobs[2].length-1)*probs[2]);h--){
										for(int i = rprobs[3].length-1;i > Math.floor((rprobs[3].length-1)*probs[3]);i--){
											sumprob += rprobs[1][a] * rprobs[0][b] * rprobs[8][c] * rprobs[7][d]
													* rprobs[5][e] * rprobs[6][f] * rprobs[4][g] * rprobs[2][h]
													* rprobs[3][i];
											steps++;
											if(sumprob > k && rprobs[1][a] > k && rprobs[0][b] > k && rprobs[8][c] > k && rprobs[7][d] > k && rprobs[5][e] > k && rprobs[6][f] > k && rprobs[4][g] > k && rprobs[2][h] > k && rprobs[3][i] > k){
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
		}
		for(int len = 0;len < 9;len++){
			System.out.println(rpvt[len]);
		}
		System.out.println("特技スコアアップ期待値:"+tempscr+"("+sumprob*100+"%)");
		System.out.println("計算した数値が条件を満たすまでに計算がループした回数:"+steps);
		System.out.println("ベーススコア計算");
		unit[2].spcross(1); unit[2].spaura(1);
		unit[3].spcross(1); unit[3].spaura(1);
		String sfstr = setUnitsf(unit,frend);
		System.out.println("ユニット値:"+sfstr);
		int unitsf = Integer.parseInt(sfstr.split(",",0)[0]);
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
		System.out.println("スコア期待値："+sumscr+"：このスコアを超えるスコアが出る確率は"+sumprob*100+"%");
		System.out.println("計算終了。");
	}
}
