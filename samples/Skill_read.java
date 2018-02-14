package samples;

import java.io.*;
import java.util.*;

class Skill_read{
	public static Skill_data[] setSkill(String rrity, Skill_data[] sdata){
		//レアリティから特技検索しやすくするためのメソッド
		ArrayList<Skill_data> rtndata = new ArrayList<Skill_data>();
		for(int len = 0; len < sdata.length; len++){
			if(rrity.equals(sdata[len].grrity())){
				rtndata.add(sdata[len]);
			}
		}
		Skill_data[] rdata = new Skill_data[rtndata.size()];
		for(int len = 0; len < rtndata.size(); len++){
			rdata[len] = rtndata.get(len);
		}
		return rdata;
	}

	public static String[] setSkillnameT(String pprty,Skill_data[] sdata){
		//属性からスキルテーブルをString[]で返すメソッド
		ArrayList<Skill_data>bfprdata = new ArrayList<Skill_data>();
		for(int i = 0; i < sdata.length;i++){
			if((sdata[i].getcsknm().indexOf(pprty) != -1)){
				bfprdata.add(sdata[i]);
			}
		}
		String[] rtnStr = new String[bfprdata.size()];
		for(int len = 0;len < bfprdata.size();len++){
			rtnStr[len] = bfprdata.get(len).getskinm();
		}
		return rtnStr;
	}

	public static Skill_data onerdata(String skinm, Skill_data[] sdata){
		for(int i = 0; i < sdata.length; i++){
			if(skinm.equals(sdata[i].getskinm())){
				return sdata[i];
			}
		}
		return null;
	}//特定スキルのsdataを返すメソッド(色々な情報はここからgetすればいい)

	public static int getprob(int skilv, Skill_data rdata){
		int iniprb = rdata.gsprob();
		int difprb = rdata.gdfprb();
		int fprob = rdata.gfprob();
		if(skilv != 8){
			return (iniprb + difprb*(skilv-1));
		}else{
			return fprob;
		}
	}//特技レベルから確率を返すメソッド

	public static String getefsz(int skilv, Skill_data rdata){
		//Card_datasの342行目を参考にして追加してください
		if(rdata.gsklef().equals("スコア")){
			int iniscr = rdata.giefsz();
			int difscr = rdata.gdifez();
			int finscr = rdata.gfefsz();
			Integer[] scrT = Card_datas.scrupTable(iniscr, difscr, finscr);
			int rtnefsz = scrT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("回復")){
			int inihe = rdata.giefsz();
			int finhe = rdata.gfefsz();
			Integer[] helT = Card_datas.healTable(inihe, finhe);
			int rtnefsz = helT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("判定")){
			double accut = rdata.gaccut();
			int finint = rdata.gfefsz();
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			return String.valueOf(rtnefsz);
		}else if(rdata.gsklef().equals("パーフェクト")){//#1413などに対応してください
			double accut = rdata.gaccut();//初期のパーフェクトアップ時間
			int finscrupt = (int)rdata.gpfupt()*10;
			//System.out.println("finscrupt :" + finscrupt);
			double[]pfupT = Card_datas.accracyTable(accut, finscrupt);
			double pfctscrupT = pfupT[skilv-1];//perfect score up time

			int iniscr = rdata.giefsz();
			int difscr = rdata.gdifez();
			int finscr = rdata.gfefsz();
			Integer[] scrT = Card_datas.scrupTable(iniscr, difscr, finscr);
			int rtnscrupTb = scrT[skilv-1];//score up Table
			String rtnStr = String.valueOf(rtnscrupTb) + "," + String.valueOf(pfctscrupT);

			return rtnStr;//split必須 int+double
		}else if(rdata.gsklef().equals("パラメーター")){
			int iniprup = rdata.giefsz();//初期パラメタアップ値（%）
			int finprup = rdata.gfefsz();//最終パラメタアップ値(%)
			Integer[] pupT = Card_datas.pprtyupTable(iniprup, finprup);
			int rtnprupP = pupT[skilv-1];
			String rtnStr = String.valueOf(rtnprupP) + ",";

			double accut = rdata.gaccut();
			int finint = (int)rdata.gpfupt()*10;//#1416対応済み。
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			rtnStr = rtnStr + String.valueOf(rtnefsz);
			return rtnStr;//split必須 int+double
		}else if(rdata.gsklef().equals("発動率")){
			int iniprup = rdata.giefsz();//初期特技アップ値（%）
			int finprup = rdata.gdifez();//最終特技アップ値(%)
			Integer[] pupT = Card_datas.pprtyupTable(iniprup, finprup);
			int rtnprupP = pupT[skilv-1];
			String rtnStr = String.valueOf(rtnprupP) + ",";//秒数

			double accut = rdata.gaccut();
			int finint = rdata.gfefsz();
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];//発動x倍

			rtnStr = rtnStr + String.valueOf(rtnefsz);
			return rtnStr;//split必須 int+double
		}else if(rdata.gsklef().equals("リピート")){
			return String.valueOf(getprob(skilv,rdata));
		}else if(rdata.gsklef().equals("シンクロ")){
			double accut = rdata.gaccut();//初期コピー時間
			int finint = rdata.gfefsz();//最終コピー時間
			double[] accT = Card_datas.accracyTable(accut, finint);
			double rtnefsz = accT[skilv-1];

			return String.valueOf(rtnefsz);
		}
		return null;
	}//特技レベルから判定強化などのスキル型の効果量を返すメソッド(計算しないのでStringでおｋ)

	public static String getSklefText(int skilv ,Skill_data srdata, String name){
		String rtntext = new String();
		String gefsz = getefsz(skilv, srdata);//スコア回復などの効果量
		String gtpsk = srdata.gtpskr();
		if(gtpsk.equals("パーフェクト")){
			gtpsk = "PERFECT";
		}
		int fsz = srdata.gfsz();//発動条件数値
		int prob = getprob(skilv,srdata);//発動確率
		String chkstr = srdata.gsklef();//スキルの発動効果型
		String tprchain = new String();
		String[] bfefsz = new String[2];
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

		if(chkstr.equals("パーフェクト") || chkstr.equals("パラメーター") ||chkstr.equals("発動率")){
			bfefsz = gefsz.split(",",0);//int + double
		}
		if(gtpsk.equals("リズムアイコン")){
			rtntext = gtpsk + fsz + "個ごとに";
		}else if(gtpsk.equals("タイマー")){
			rtntext = fsz + "秒ごとに";
		}else if(gtpsk.equals("PERFECT")){
			rtntext = gtpsk +"を"+ fsz + "回達成するごとに";
		}else if(gtpsk.equals("コンボ")){
			rtntext = gtpsk + fsz + "を達成するごとに";
		}else if(gtpsk.equals("スコア")){
			rtntext = gtpsk + fsz + "達成ごとに";
		}else if(gtpsk.equals("スターアイコン")){
			rtntext = gtpsk + "PERFECT" + fsz +"回ごとに";
		}else if(gtpsk.equals("チェイン")){
			rtntext = "自身を除く" + tprchain + "の特技がすべて発動すると";
			//factv = 0;扱い
			//fsz = 0扱い
		}


		rtntext = rtntext + prob + "%の確率で";
		if(chkstr.equals("回復")){
			rtntext = rtntext + "体力が" + gefsz + "回復する";
		}else if(chkstr.equals("判定")){
			rtntext = rtntext + "判定が" + gefsz + "秒強化される";
		}else if(chkstr.equals("スコア")){
			rtntext = rtntext + "スコアが" + gefsz +"増える";
		}else if(chkstr.equals("パーフェクト")){//高坂穂乃果さんのSR #1413に対応できてないのでコメントアウト
			String scrupTb = bfefsz[0];//int
			String pfctscrupT = bfefsz[1]; //double
			rtntext = rtntext + pfctscrupT + "秒間PERFECT時のタップSCOREが" + scrupTb + "増える";
		}else if(chkstr.equals("発動率")){
			rtntext = rtntext + bfefsz[1] + "秒間他の特技の発動確率が1." + bfefsz[0] +"倍になる";
		}else if(chkstr.equals("パラメーター")){
			rtntext = rtntext + bfefsz[1] + "秒間" + tprchain + "の属性Pが" + bfefsz[0] +"%UPする";
		}else if(chkstr.equals("シンクロ")){
			rtntext = rtntext + gefsz + "秒間" + tprchain + "のいずれかと同じ属性Pになる";
		}else if(chkstr.equals("リピート")){
			rtntext = rtntext + "直前に発動した特技リピート以外の特技効果を発動";
		}

		return rtntext;
	}

	public static String[] setcpprty(Skill_data srdata, String pprty, String rrity, String awake){
		int bfcsm = srdata.gcsm();
		int bfcpr = srdata.gcpr();
		int bfccl = srdata.gccl();
		int kizuna = 0;
		if(awake.equals("覚醒")){
			if(rrity.equals("SR")){
				kizuna = 500;
			}else if(rrity.equals("SSR")){
				kizuna = 750;
			}else{
				kizuna = 1000;
			}
			if(pprty.equals("スマイル")){
				bfcsm +=  kizuna;
			}else if(pprty.equals("ピュア")){
				bfcpr += kizuna;
			}else{
				bfccl += kizuna;
			}
		}else if(awake.equals("未覚醒")){
			if(rrity.equals("SR")){
				kizuna = 250;
				bfcsm -= 280;
				bfcpr -= 280;
				bfccl -=280;
			}else if(rrity.equals("SSR")){
				kizuna = 375;
				bfcsm -= 300;
				bfcpr -= 300;
				bfccl -= 300;
			}else{
				kizuna = 500;
				bfcsm -= 300;
				bfcpr -= 300;
				bfccl -= 300;
			}
			if(pprty.equals("スマイル")){
				bfcsm +=  kizuna;
			}else if(pprty.equals("ピュア")){
				bfcpr += kizuna;
			}else{
				bfccl += kizuna;
			}
		}
		String rcsm = "スマイル値:"+bfcsm;
		String rcpr = "ピュア値:"+bfcpr;
		String rccl = "クール値:"+bfccl;
		String[] rtnStrs = {rcsm, rcpr, rccl, String.valueOf(bfcsm), String.valueOf(bfcpr), String.valueOf(bfccl)};

		return rtnStrs;
	}

	/*public static void main(String[] args)throws IOException, FileNotFoundException{
		Skill_data[] sdata = Skill_data.setdata("園田海未");
		Skill_data[] rdata = setSkill("SR",sdata);
		String[] elem = new String[rdata.length];
		for(int len = 0; len < rdata.length; len++){
			elem[len] = rdata[len].getskinm();
			System.out.println((len+1) + " card is " + elem[len]);
		}
	}*/
}
