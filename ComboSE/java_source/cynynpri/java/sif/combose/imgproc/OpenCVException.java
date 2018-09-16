/**
 *
 */
package cynynpri.java.sif.combose.imgproc;

/**
 * @author game
 *
 */
@SuppressWarnings("serial")
public class OpenCVException extends RuntimeException {
	private int code;

	public OpenCVException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
