package samples;

@SuppressWarnings("serial")//serialVersionUID��`�����Ȃ���΂Ȃ�Ȃ����A������������B
class FileFormatNotMatchException extends Exception{
	public FileFormatNotMatchException(String e){
		super(e + "(���̓t�@�C���`�����قȂ�܂��B.csv�`����.txt�`���̃t�@�C����ǂݍ���ł��������B)");
	}
}
