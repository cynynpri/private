package samples;

@SuppressWarnings("serial")
class DataNotFoundException extends Exception{
	public DataNotFoundException(String e){
		super(e + "(�ǂݍ��ރf�[�^�����͂���Ă��Ȃ���NullPointer�ɂȂ��Ă��܂��B)");
	}
}
