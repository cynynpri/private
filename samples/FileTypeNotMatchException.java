package samples;

@SuppressWarnings("serial")//serialVersionUID��`�����Ȃ���΂Ȃ�Ȃ����A������������B
class FileTypeNotMatchException extends Exception{
	public FileTypeNotMatchException(String e){
		super(e + "(���̓t�@�C���`�����قȂ�܂��B.csv�`����.txt�`���̃t�@�C����ǂݍ���ł��������B)");
	}
}
