package samples;

@SuppressWarnings("serial")
class DataNotFoundException extends Exception{
	public DataNotFoundException(String e){
		super(e + "(読み込むデータが入力されていないかNullPointerになっています。)");
	}
}
