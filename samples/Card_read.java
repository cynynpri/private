package samples;

import java.io.*;

class Card_read{
	static int card_Max_num;

	public static int getMaxnum(File dPath)throws FileNotFoundException, IOException{
		setfRMaxnum(dPath);
		return card_Max_num;
	}

	public static void setMaxnum(int num){
		card_Max_num = num;
	}

	public static void setfRMaxnum(File dPath)throws FileNotFoundException, IOException{
		//String dPath = System.getProperty("user.dir");
		//dPath = dPath + "\\Character_cdata.ini";
		FileReader fr = new FileReader(dPath.getPath());
		BufferedReader br = new BufferedReader(fr);
		String liner = new String();
		int dMax = 0;
		while(liner != null && liner.indexOf(",null,") == -1){
			liner = br.readLine();
			dMax++;
			//System.out.println("dMax = " + dMax);
		}
		setMaxnum(dMax);
		br.close();
	}
	public static Card_datas[] reading_rdata(File dPath)throws FileNotFoundException, IOException{
		//本来のデータリーダー


		//String dPath = System.getProperty("user.dir");
		/*
		if(boofilepath){
		int lth = dPath.length();
		lth = lth - 12;
		dPath = dPath.substring(0,lth);
		dPath = dPath + "\\Samples\\Character_cdata.tsv";
		}else{*/
		//dPath = dPath + "\\Character_cdata.ini";
		setfRMaxnum(dPath);
		int mAx = getMaxnum(dPath);
		Card_datas[] cdata = new Card_datas[mAx];
		FileReader rrdt = new FileReader(dPath);
		BufferedReader bfrd = new BufferedReader(rrdt);
		String liner;
		//int c_Max = 1;
		String[] buffer_str = new String[19];
		while((liner = bfrd.readLine()) != null){
			buffer_str = liner.split("\t", 19);
			cdata[Integer.parseInt(buffer_str[0])] = new Card_datas(
				Integer.parseInt(buffer_str[0]),
				buffer_str[1],
				buffer_str[2].toString(),
				buffer_str[3].toString(),
				buffer_str[4].toString(),
				Card_datas.chkrawake(buffer_str[5].toString()),
				Integer.parseInt(buffer_str[6].toString()),
				Integer.parseInt(buffer_str[7].toString()),
				buffer_str[8].toString(),
				buffer_str[9].toString(),
				Integer.parseInt(buffer_str[10].toString()),
				Integer.parseInt(buffer_str[11].toString()),
				Double.parseDouble(buffer_str[12].toString()),
				Integer.parseInt(buffer_str[13].toString()),
				Integer.parseInt(buffer_str[14].toString()),
				Integer.parseInt(buffer_str[15].toString()),
				Integer.parseInt(buffer_str[16].toString()),
				buffer_str[17].toString(),
				buffer_str[18].toString()
			);
			//c_Max++;
		}
		//print_cdata(cdata,c_Max);
		// ↑の文でcdataにデータ読み込みできたかどうか確認しました

		bfrd.close();
		//System.out.println(cdata[1].getname());
		return cdata;
	}
	public static void print_cdata(Card_datas[] cdata, int card_number){
		for(int len = 1; len < card_number; len++){
			System.out.println(
				String.valueOf(cdata[len].gcnum()) + ","
				+ cdata[len].getname() + ","
				+ cdata[len].gpprty() + ","
				+ cdata[len].grrity() + ","
				+ cdata[len].getskinm() + ","
				+ cdata[len].gsawk() + ","
				+ String.valueOf(cdata[len].gsislt()) + ","
				+ String.valueOf(cdata[len].gskilv()) + ","
				+ cdata[len].gsksha() + ","
				+ cdata[len].gskitp() + ","
				+ String.valueOf(cdata[len].gfactv()) + ","
				+ String.valueOf(cdata[len].gprob()) + ","
				+ String.valueOf(cdata[len].gaccut()) + ","
				+ String.valueOf(cdata[len].gefsz()) + ","
				+ String.valueOf(cdata[len].gcsm()) + ","
				+ String.valueOf(cdata[len].gcpr()) + ","
				+ String.valueOf(cdata[len].gccl()) + ","
				+ cdata[len].getcskin() + ","
				+ cdata[len].getacskn()
			);
		}
	}
	public static Card_datas one_carddata(Card_datas[] cdatas, int len){
		Card_datas one_data;
		if(cdatas[len] != null){
			one_data = cdatas[len];
		}else{
			return null;
		}
		return one_data;
	}

	public static Card_datas[] dc_cdatas(File dPath) throws FileNotFoundException, NullPointerException, IOException, FileFormatNotMatchException{
		//見習い師様のスコア計算機からエクスポートしたデータをコンバートするメソッド
		//本来、このメソッドはmainメソッドではなくdataconvertメソッドである。
		/*String filepath = System.getProperty("user.dir")+"\\Sample_new.csv";
		File f = new File(filepath);*/
		if(dPath.getPath().indexOf(".csv") == -1 && dPath.getPath().indexOf(".txt") == -1){
			String e = new String();
			throw new FileFormatNotMatchException(e);
		}
		FileReader fr = new FileReader(dPath.getPath());
		BufferedReader br = new BufferedReader(fr);
		String liner = br.readLine();
		//System.out.println(liner);

		String filePath = System.getProperty("user.dir");
		//int lth = filePath.length();
		//lth = lth - 12;
		//filePath = filePath.substring(0,lth);
		//filePath = filePath + "\\Samples\\Character_cdata.tsv";*/
		filePath = filePath + "\\Character_cdata.ini";
		//*ファイル読み込み
		FileWriter fw = new FileWriter(filePath);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);

		String[] checknms = new String[18];
		String[] chkpty = new String[3];
		String[] chkrty = new String[3];
		String[] chksksha = new String[8];
		String[] chksktp = new String[7];
		int[] d_array = new int[7];
		int len = 0;
		for(len = 0; len < 18; len++){
			checknms[len] = Card_datas.setchknms(len);
			if(len < 8){
				if(len < 7){
					if(len < 3){
						chkpty[len] = Card_datas.setchkpty(len);
						chkrty[len] = Card_datas.setchkrty(len);
					}
				chksktp[len] = Card_datas.setchksktp(len);
				}
			chksksha[len] = Card_datas.setchksha(len);//8
			}
		}
		String chkp = chkpty[0];
		String chkr = chkrty[0];
		String check_nm = checknms[0];
		String chksha = chksksha[0];
		String chkstp = chksktp[0];

		String sname = new String();
		String spty = new String();
		String srty = new String();
		String sklnm = new String();
		String rsksha = new String();
		String rskitp = new String();
		String rcskin = new String();
		String racskn = new String();
		boolean rawkb = false;
		int lc = 0;
		int card_num = 1;
		int rskslt = 0;
		int rsklv = 0;
		int dMax = 1000;
		double accut = 0.0;
		Card_datas[] cdata = new Card_datas[dMax];

//=========================================================
//starting loop at while line 77
//=========================================================
		while(liner != null && liner.indexOf(",null,") == -1){
			sname = "";
			spty = "";
			srty = "";
			rsksha = "";
			rskitp = "";
			rawkb = false;
			for(len = 0; len < 18 ; len++){
				check_nm = checknms[len];
				if(liner.indexOf(check_nm) != -1){
					sname = Card_datas.setrname(check_nm);
				}
				if(len < 8){
					chksha = chksksha[len];
					if(len < 7){
						chkstp = chksktp[len];
						if(len < 3){
							chkp = chkpty[len];
							chkr = chkrty[len];
							if(liner.indexOf(chkp) != -1){
								spty = Card_datas.setrpprty(chkp);
							}
							if(liner.indexOf(chkr) != -1){
								srty = Card_datas.setrrrity(chkr);
							}
						}
						if(liner.indexOf(chkstp) != -1){
							rskitp = Card_datas.setrskitp(chkstp);
						}
					}
					if(liner.indexOf(chksha) != -1){
						rsksha = Card_datas.setrsksha(chksha);
					}
				}
			}
			sklnm = Card_datas.chksklnm(liner);
			rawkb = Card_datas.chkawake(liner);
			rskslt = Card_datas.chksklslot(liner);
			rsklv = Card_datas.chkskllv(liner);
			rcskin = Card_datas.setrcskin(liner);
			d_array = Card_datas.chkintegers(liner);
			accut = Card_datas.chkdouble(liner);
			racskn = Card_datas.setracskn(liner,srty);
			if(sname.equals("") && lc >= 1|| spty.equals("") && lc >= 1 || srty.equals("") && lc >= 1){
				break;
			}//else if(lc == 0){}
			else{
				cdata[card_num] = new Card_datas(
					card_num,
					sname,
					spty,
					srty,
					sklnm,
					rawkb,
					rskslt,
					rsklv,
					rsksha,
					rskitp,
					d_array[0],
					d_array[1],
					accut,
					d_array[3],
					d_array[4],
					d_array[5],
					d_array[6],
					rcskin,
					racskn
				);
				card_num++;
			}
			setMaxnum(card_num);
			liner = br.readLine();
			lc++;
		}
//=========================================================
//End of loop at while line 77
//=========================================================
		if(card_num == 1){
			if(cdata[1].getname().equals("")){
				fr.close();
				pw.close();

				System.out.println("データが見つかりませんでした");
				return null;
				/*String err = new String();
				throw new DataNotFoundException(err);*/
			}
		}
		//print_cdata(cdata, card_num);
		for(len = 1; len < card_num; len++){
			//*ファイル書き込み
			pw.println(
				String.valueOf(cdata[len].gcnum()) + "\t"
				+ cdata[len].getname() + "\t"
				+ cdata[len].gpprty() + "\t"
				+ cdata[len].grrity() + "\t"
				+ cdata[len].getskinm() + "\t"
				+ cdata[len].gsawk() + "\t"
				+ String.valueOf(cdata[len].gsislt()) + "\t"
				+ String.valueOf(cdata[len].gskilv()) + "\t"
				+ cdata[len].gsksha() + "\t"
				+ cdata[len].gskitp() + "\t"
				+ String.valueOf(cdata[len].gfactv()) + "\t"
				+ String.valueOf(cdata[len].gprob()) + "\t"
				+ String.valueOf(cdata[len].gaccut()) + "\t"
				+ String.valueOf(cdata[len].gefsz()) + "\t"
				+ String.valueOf(cdata[len].gcsm()) + "\t"
				+ String.valueOf(cdata[len].gcpr()) + "\t"
				+ String.valueOf(cdata[len].gccl()) + "\t"
				+ cdata[len].getcskin() + "\t"
				+ cdata[len].getacskn()
			);
		}
		fr.close();
		//*ファイルを閉じる
		pw.close();
		//cdata = reading_rdata();
		//print_cdata(cdata, card_num);
		return cdata;
	}
	public static void setonecarddata(PrintWriter pw, Card_datas onerdata){
			pw.println(String.valueOf(onerdata.gcnum()) + "\t" + onerdata.getname() + "\t" + onerdata.gpprty() + "\t"
					+ onerdata.grrity() + "\t" + onerdata.getskinm() + "\t" + onerdata.gsawk() + "\t"
					+ String.valueOf(onerdata.gsislt()) + "\t" + String.valueOf(onerdata.gskilv()) + "\t"
					+ onerdata.gsksha() + "\t" + onerdata.gskitp() + "\t" + String.valueOf(onerdata.gfactv()) + "\t"
					+ String.valueOf(onerdata.gprob()) + "\t" + String.valueOf(onerdata.gaccut()) + "\t"
					+ String.valueOf(onerdata.gefsz()) + "\t" + String.valueOf(onerdata.gcsm()) + "\t"
					+ String.valueOf(onerdata.gcpr()) + "\t" + String.valueOf(onerdata.gccl()) + "\t"
					+ onerdata.getcskin() + "\t" + onerdata.getacskn());
	}
	/*public static void main(String[] args){
		try{
			String dpath = System.getProperty("user.dir");
			dpath = dpath + "\\__sample_new.csv";
			String dth = System.getProperty("user.dir");
			dth = dth + "\\Character_cdata.ini";
			File fth = new File(dth);
			File fpath = new File(dpath);
			setfRMaxnum(fth);
			Card_datas[] cdata = new Card_datas[getMaxnum(fth)];
			cdata = dc_cdatas(fpath);
			print_cdata(cdata, getMaxnum(fth));
			//System.out.println(cdata[19].gcnum());
		}catch(FileNotFoundException e){
			System.out.println(e);
			String dpath = System.getProperty("user.dir");
			dpath = dpath + "\\__sample_new.csv";
			File fpath = new File(dpath);
			Card_datas[] cdata = new Card_datas[1000];
			try{
				cdata = dc_cdatas(fpath);
			}catch(FileNotFoundException err){
				System.exit(1);
			}catch(IOException err){
				System.exit(1);
			}catch(NullPointerException err){
				System.exit(1);
			}
		}catch(IOException e){
			System.out.println(e);
		}catch(NullPointerException e){
			System.out.println(e);
		}
	}*/
	//↑デバック用のmain.確認できたのでmainをコメントアウト.
}
