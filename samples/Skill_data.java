package samples;

import java.io.*;
import java.util.*;

/*
	Chara_Skillsdata�̓��l�[������Skill_data�ɃN���X����ύX����B
	Skill_data sdata[] = new Skill_data();
	���邢��
	Skill_data sdata[] = setdata(character_name);
	�Ɛ錾���āA�X�L���̖��O�A���ʁA�J�[�h�̃f�[�^���ׂĂ��o�͂���N���X�Ƃ���B
	Card_datas�Ƃ̈Ⴂ��Skill�̕�����Card�ƂȂ��Ă���ʂ�A�l���L��"�J�[�h"�Ȃ̂��A
	�l���L���Ă���J�[�h��o�^���邽�߂�"���Z"����o�^���邽�߂̃f�[�^���o�͂�����̂���
	��ʂ��邽�߂ł���B
 */


class Skill_data{
	private String rrity; //���A���e�B rarity
	private String skinm; //���Z�� skill name
	private String sklef; //�������� skill effect
	private String tpskr; //���������^  type of skill running
	private int fsz; //�����������l if run size
	private int sprob; //���������m��(Lv1) skill probabry
	private int dfprb; //1���x�����Ƃ̊m�������� different probabry
	private int fprob; //�ŏI�n�����m�� final skill probabry
	private double accut; //���苭������ accuracy time
	private int iefsz; //lv1���ʗ� initial effect size
	private int difez; //1���x�����Ƃ̌��ʗʑ����� different effect size
	private int fefsz; //lv8*** final effect size
	private int csm; //card smile
	private int cpr; //card pure
	private int ccl; //card cool
	private String csknm; //�Z���^�[�X�L�� center skill name
	private String scsnm; //�T�u�Z���^�[�X�L�� sub center skill name
	private double pfupt; //�p�[�t�F�N�g�A�b�v���Ԃ̍ŏI�l perfect score up time.

	/**
	 * �X�L���f�[�^�̃R���X�g���N�^.
	 * <p>
	 * �X�L���f�[�^���������߁Ahonosd.csv�Ȃǂɑ�\�����csv�t�@�C������ǂݎ��ꂽ�f�[�^���i�[����A�R���X�g���N�^�B<br>
	 * setdata���\�b�h�ɂ�肻�ꂼ��̃p�����[�^�Ƀf�[�^�����͂����̂ŁA��{�I��null�͓���Ȃ�<br>
	 * </p>
	 *
	 * @param rrity ���A���e�B rarity
	 * @param skinm ���Z�� skill name
	 * @param sklef �������� skill effect
	 * @param tpskr ���������^  type of skill running
	 * @param fsz �����������l if run size
	 * @param sprob ���������m��(Lv1) skill probabry
	 * @param dfprb 1���x�����Ƃ̊m�������� different probabry
	 * @param fprob �ŏI�l�����m�� final skill probabry
	 * @param accut ���苭������ accuracy time
	 * @param iefsz lv1���ʗ� initial effect size
	 * @param difez 1���x�����Ƃ̌��ʗʑ����� different effect size
	 * @param fefsz lv8*** final effect size
	 * @param csm card smile amount
	 * @param cpr card pure amount
	 * @param ccl card cool amount
	 * @param csknm �Z���^�[�X�L�� center skill name
	 * @param scsnm �T�u�Z���^�[�X�L�� sub center skill name
	 * @param pfupt �p�[�t�F�N�g�A�b�v���Ԃ̍ŏI�l perfect score up time.
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
	// set,get���\�b�h��` ->Start 65 to 184.
	//=========================================================================
	/**
	 * �Z�b�g���A���e�B
	 *
	 * <p>
	 * --���A���e�B���Z�b�g����B�t�@�C���ǂݍ��ݑ���null�͂͂����̂Œ��Ăяo���ł�null����͓���.
	 * </p>
	 * @param rrity ���A���e�B
	 */
	public void srrity(String rrity){
		this.rrity = rrity;
	}

	/**
	 * �Q�b�g���A���e�B
	 *
	 * <p>
	 * --���A���e�B���Q�b�g����B
	 * </p>
	 * @param rrity ���A���e�B
	 * @return Skill_data�^�̔z��Ɋi�[����Ă��郌�A���e�B(String)��Ԃ�
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
	// set,get���\�b�h��` ->End 55 to 184.
	//=========================================================================
	public static Skill_data[] setdata(String chara_name)throws FileNotFoundException, IOException{
		//String�^�̃L����������sdata�Ƀf�[�^���Z�b�g���郁�\�b�h.
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

		if(chara_name.equals("�����T��")){
			dPath = dPath + "/Datalist/honosd.csv";
		}else if(chara_name.equals("�����G��")){
			dPath = dPath + "/Datalist/elisd.csv";
		}else if(chara_name.equals("�삱�Ƃ�")){
			dPath = dPath + "/Datalist/kotosd.csv";
		}else if(chara_name.equals("���c�C��")){
			dPath = dPath + "/Datalist/umisd.csv";
		}else if(chara_name.equals("����z")){
			dPath = dPath + "/Datalist/rinsd.csv";
		}else if(chara_name.equals("���ؖ�^�P")){
			dPath = dPath + "/Datalist/makisd.csv";
		}else if(chara_name.equals("������")){
			dPath = dPath + "/Datalist/nonsd.csv";
		}else if(chara_name.equals("����ԗz")){
			dPath = dPath + "/Datalist/kayosd.csv";
		}else if(chara_name.equals("���V�ɂ�")){
			dPath = dPath + "/Datalist/yazsd.csv";
		}else if(chara_name.equals("���C���")){
			dPath = dPath + "/Datalist/chiksd.csv";
		}else if(chara_name.equals("�������q")){
			dPath = dPath + "/Datalist/rikosd.csv";
		}else if(chara_name.equals("���Y�ʓ�")){
			dPath = dPath + "/Datalist/knnsd.csv";
		}else if(chara_name.equals("���V�_�C��")){
			dPath = dPath + "/Datalist/diasd.csv";
		}else if(chara_name.equals("�n�ӗj")){
			dPath = dPath + "/Datalist/yousd.csv";
		}else if(chara_name.equals("�Ó��P�q")){
			dPath = dPath + "/Datalist/johasd.csv";
		}else if(chara_name.equals("���ؓc�Ԋ�")){
			dPath = dPath + "/Datalist/marusd.csv";
		}else if(chara_name.equals("�����f�")){
			dPath = dPath + "/Datalist/marisd.csv";
		}else if(chara_name.equals("���V���r�B")){
			dPath = dPath + "/Datalist/rubysd.csv";
		}else {
			System.out.println("�f�[�^�ǂݍ��݃G���[�F�L�����N�^�[�����ǂݍ��߂܂���");
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
		Skill_data[] sdata = setdata("���c�C��");
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
	 //��f�o�b�N�p�̃��C�����i�ǂݎ�ꂽ�̂ŏI���j

	}*/
}
