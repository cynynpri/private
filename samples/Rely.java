import java.util.*;
import java.io.*;

class Rely {
	//Music_REsult_anaLyzerクラス　略名:Rely(梨子のギルキス内での呼び方リリーとかけてる).
	private String musicnm;
	private boolean fullcombobl;
	private int perfects;
	private int greats;
	private int goods;
	private int bads;
	private int missies;

	public Rely() {
		super();
	}

	public Rely(String musicnm, boolean fullcombobl, int perfects, int greats, int goods, int bads, int missies) {
		this.musicnm = musicnm;
		this.fullcombobl = fullcombobl;
		this.perfects = perfects;
		this.greats = greats;
		this.goods = goods;
		this.bads = bads;
		this.missies = missies;
	}

	public Rely(String musicnm, String fullcombostr, int perfects, int greats, int goods, int bads, int missies) {
		this.musicnm = musicnm;
		fullcombobl = fullcombostr.equals("fullcombo!");
		this.perfects = perfects;
		this.greats = greats;
		this.goods = goods;
		this.bads = bads;
		this.missies = missies;
	}

	final public void setmusicnm(String musicnm) {
		this.musicnm = musicnm;
	}

	final public String getmusicnm() {
		return musicnm;
	}

	final public void setfullcombobl(boolean fullcombobl) {
		this.fullcombobl = fullcombobl;
	}

	final public boolean getfullcombobl() {
		return fullcombobl;
	}

	final public void setstrfcbl(String fullcombostr) {
		fullcombobl = fullcombostr.equals("fullcombo!");
	}

	final public String getstrfcbl() {
		String fcstr = new String();
		fcstr = "miss!";
		if (fullcombobl) {
			fcstr = "fullcombo!";
		}
		return fcstr;
	}

	final public void setperfects(int perfects) {
		this.perfects = perfects;
	}

	final public int getperfects() {
		return perfects;
	}

	final public void setgreats(int greats) {
		this.greats = greats;
	}

	final public int getgreats() {
		return greats;
	}

	final public void setgoods(int goods) {
		this.goods = goods;
	}

	final public int getgoods() {
		return goods;
	}

	final public void setbads(int bads) {
		this.bads = bads;
	}

	final public int getbads() {
		return bads;
	}

	final public void setmissies(int missies) {
		this.missies = missies;
	}

	final public int getmissies() {
		return missies;
	}

	final public static List<Rely> setfrtolistrely(String rely_datapath){
		List<Rely> rely_list = new ArrayList<Rely>();
		try{
			BufferedReader bfrely = new BufferedReader(new FileReader(rely_datapath));
			int counter = 0;
			String liner = new String();
			while((liner = bfrely.readLine()) != null){
				String[] liners = liner.split("\t",0);
				if(counter < 100){
					rely_list.add(new Rely(liners[0], liners[1], Integer.parseInt(liners[2]),
							Integer.parseInt(liners[3]), Integer.parseInt(liners[4]), Integer.parseInt(liners[5]),
							Integer.parseInt(liners[6])));
					counter++;
				}else{
					counter -= 100;
					rely_list.set(counter,new Rely(liners[0], liners[1], Integer.parseInt(liners[2]),
							Integer.parseInt(liners[3]), Integer.parseInt(liners[4]), Integer.parseInt(liners[5]),
							Integer.parseInt(liners[6])));
					counter++;
				}
			}
			bfrely.close();
		}catch(FileNotFoundException e){
			System.err.println(e);
			System.err.println("Relyクラスのsetfrtolistrelyメソッドエラー:ファイルが見つかりません");
		}catch(IOException e){
			System.err.println(e);
			System.err.println("Relyクラスのsetfrtolistrelyメソッドエラー:ファイルが読み込めません");
		}
		return rely_list;
	}

	public static String analysed_data(List<Rely> rely_list){
		int maxcbs = 0;
		int maxpers = 0;
		int maxgres = 0;
		int maxgds = 0;
		int maxbds = 0;
		int maxmss = 0;
		int fccnt = 0;
		for(Rely temp : rely_list){
			maxcbs += temp.getperfects() + temp.getgreats() + temp.getgoods() + temp.getbads() + temp.getmissies();
			maxpers += temp.getperfects();
			maxgres += temp.getgreats();
			maxgds += temp.getgoods();
			maxbds += temp.getbads();
			maxmss += temp.getmissies();
			if(temp.getfullcombobl()){
				fccnt++;
			}
		}
		if(maxcbs == 0){
			maxcbs = 1;
		}
		int size = rely_list.size();
		if(size == 0){
			size = 1;
		}
		String rtn_str = new String();
		rtn_str = "フルコン率"+ (int)((double)fccnt/(double)size*100.0)+"%"+
				  ":パフェ率"+ (int)((double)maxpers/ (double)maxcbs*100.0)+"%"+":グレ率"+ (int)((double)maxgres/ (double)maxcbs*100.0)+"%"+
				  ":グド率"+ (int)((double)maxgds/ (double)maxcbs*100.0)+"%:バド率"+ (int)((double)maxbds/ (double)maxcbs*100.0)+
				  "%:ミス率"+(int)((double)maxmss/ (double)maxcbs*100.0)+"%";
		return rtn_str;
	}
}
