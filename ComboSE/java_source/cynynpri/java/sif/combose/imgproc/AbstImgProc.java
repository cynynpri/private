/**
 *
 */
package cynynpri.java.sif.combose.imgproc;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * @author game
 *
 */
public abstract class AbstImgProc {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public abstract Mat cvtFullColor2Bin(Mat src_img);
	public abstract List<LabeledImg> getbinImgList(String path);
	public abstract void writeMat(String img_name, Mat src_img, String path);
}
