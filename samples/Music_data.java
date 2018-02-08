package samples;

import java.io.*;
import java.util.*;

class Music_data {
	//楽曲データ群
	private int msnum;//楽曲番号
	private String mscnm; //楽曲名
	private String difficulty;//難易度
	private String unttp; //楽曲ユニットタイプ ex)μ's Aqours and so on.
	private String pprty; //楽曲属性
	private int maxcombo;//最大ノーツ数
	private int star_icon;//スターノーツの数
	private int musictm;//楽曲時間(秒)

	//スコア計算用楽曲データ群
	private int[] alane = new int[10];// 1~50 LN1~50 51~100 LN51~100 101~200 LN101~200 201~400 LN201~400 401~599 LN401~599の数値を格納。aから左上bはその隣eがセンターiが右上
	private int[] blane = new int[10];
	private int[] clane = new int[10];
	private int[] dlane = new int[10];
	private int[] elane = new int[10];
	private int[] flane = new int[10];
	private int[] glane = new int[10];
	private int[] hlane = new int[10];
	private int[] ilane = new int[10];


	public Music_data(){
		super();
	}

	public Music_data(int msnum, String mscnm, String difficulty, String unttp, String pprty, int maxcombo,
			int star_icon, int musictm, int[] alane, int[] blane, int[] clane, int[] dlane, int[] elane, int[] flane,
			int[] glane, int[] hlane, int[] ilane) {
		this.msnum = msnum;
		this.mscnm = mscnm;
		this.difficulty = difficulty;
		this.unttp = unttp;
		this.pprty = pprty;
		this.maxcombo = maxcombo;
		this.star_icon = star_icon;
		this.musictm = musictm;
		this.alane = alane;
		this.blane = blane;
		this.clane = clane;
		this.dlane = dlane;
		this.elane = elane;
		this.flane = flane;
		this.glane = glane;
		this.hlane = hlane;
		this.ilane = ilane;
	}

	public int gmaxcb(){
		return maxcombo;
	}
	public String getmscnm() {
		return mscnm;
	}

	public String gpprty(){
		return pprty;
	}

	public int gstar_icon(){
		return star_icon;
	}

	public int gmusictm(){
		return musictm;
	}

	public String gunttp(){
		return unttp;
	}

	public int[][] glanes(){
		int[][] lanes = new int[9][];
		lanes[0] = alane;
		lanes[1] = blane;
		lanes[2] = clane;
		lanes[3] = dlane;
		lanes[4] = elane;
		lanes[5] = flane;
		lanes[6] = glane;
		lanes[7] = hlane;
		lanes[8] = ilane;
		return lanes;
	}

	public static Music_data getMusicdt(Music_data[] music_list, String musicnm) throws DataNotFoundException {
		for (int len = 0; len < music_list.length; len++) {
			if (music_list[len].getmscnm().equals(musicnm)) {
				return music_list[len];
			}
		}
		String err = new String();
		throw new DataNotFoundException(err);
	}

	public static Music_data[] r_Rdata()throws FileNotFoundException, IOException{
		ArrayList<Music_data> aL_mscdts = new ArrayList<Music_data>();
		String dPath = System.getProperty("user.dir") + "\\Datalist\\FullMusicData.csv";
		BufferedReader br = new BufferedReader(new FileReader(dPath));
		String liner = new String();
		liner = br.readLine();
		String[] arbf_str = new String[17];
		int[] alane = new int[10];
		int[] blane = new int[10];
		int[] clane = new int[10];
		int[] dlane = new int[10];
		int[] elane = new int[10];
		int[] flane = new int[10];
		int[] glane = new int[10];
		int[] hlane = new int[10];
		int[] ilane = new int[10];
		while(liner != null){
			arbf_str = liner.split(",",0);
			for(int len = 0;len < 10;len++){
				alane[len] = Integer.parseInt(arbf_str[8].split(" ",0)[len]);
				blane[len] = Integer.parseInt(arbf_str[9].split(" ",0)[len]);
				clane[len] = Integer.parseInt(arbf_str[10].split(" ",0)[len]);
				dlane[len] = Integer.parseInt(arbf_str[11].split(" ",0)[len]);
				elane[len] = Integer.parseInt(arbf_str[12].split(" ",0)[len]);
				flane[len] = Integer.parseInt(arbf_str[13].split(" ",0)[len]);
				glane[len] = Integer.parseInt(arbf_str[14].split(" ",0)[len]);
				hlane[len] = Integer.parseInt(arbf_str[15].split(" ",0)[len]);
				ilane[len] = Integer.parseInt(arbf_str[16].split(" ",0)[len]);
			}
			aL_mscdts.add(new Music_data(
				Integer.parseInt(arbf_str[0]),
				arbf_str[1],
				arbf_str[2],
				arbf_str[3],
				arbf_str[4],
				Integer.parseInt(arbf_str[5]),
				Integer.parseInt(arbf_str[6]),
				Integer.parseInt(arbf_str[7]),
				alane,
				blane,
				clane,
				dlane,
				elane,
				flane,
				glane,
				hlane,
				ilane
			));

			liner = br.readLine();
		}
		Music_data[] rtn_fldt = new Music_data[aL_mscdts.size()];
		for(int len = 0;len < rtn_fldt.length;len++){
			rtn_fldt[len] = aL_mscdts.get(len);
		}
		return rtn_fldt;
	}
}
