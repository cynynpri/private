package samples;

@SuppressWarnings("serial")//serialVersionUID定義をしなければならないが、それを回避する。
class FileTypeNotMatchException extends Exception{
	public FileTypeNotMatchException(String e){
		super(e + "(入力ファイル形式が異なります。.csv形式か.txt形式のファイルを読み込んでください。)");
	}
}
