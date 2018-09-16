/**
 *
 */
package cynynpri.java.sif.combose.imgproc;

import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author game
 *
 */
public class ModelImgProc extends AbstImgProc {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private final Boolean debug = false;
	/* (非 Javadoc)
	 * @see cynynpri.java.sif.combose.imgproc.AbstImgProc#cvtFullColor2Bin(org.opencv.core.Mat)
	 */
	@Override
	public final Mat cvtFullColor2Bin(Mat src_img) {
		//this method is convert src_img to bin(28x28 pixel)_img.
		Mat img = new Mat();
		Size size = new Size(3,3);
		Imgproc.cvtColor(src_img, img, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(img, img, size, 0);
		Size res = new Size(28,28);
		Imgproc.resize(img, img, res);
		double thres = 144.0;
		Imgproc.threshold(img, img, thres, 255, Imgproc.THRESH_BINARY);
		Core.bitwise_not(img, img);
		return img;
	}

	/* (非 Javadoc)
	 * @see cynynpri.java.sif.combose.imgproc.AbstImgProc#getbinImgList(org.opencv.core.Mat)
	 */
	@Override
	public final List<LabeledImg> getbinImgList(String path){
		List<LabeledImg> binImgs = new LinkedList<LabeledImg>();
		// TODO : write get binImg list process
		return binImgs;
	}

	/* (非 Javadoc)
	 * @see cynynpri.java.sif.combose.imgproc.AbstImgProc#writeMat(org.opencv.core.Mat)
	 */
	@Override
	public final void writeMat(String img_name, Mat src_img, String path) {
		Imgcodecs.imwrite(img_name+".png", src_img);
	}

	public final void writeMat(List<LabeledImg> imgs, String img_name, Mat src_img, String path) {
		Imgcodecs.imwrite(img_name+".png", src_img);
		imgs.add(new LabeledImg(img_name, src_img));
	}

	public final String tmpDetect(Mat src_img, LabeledImg lbltemp, double threshold) {
		Mat temp = lbltemp.getMat();
		Mat res = new Mat(src_img.rows() - temp.rows() + 1, src_img.cols() - temp.cols() + 1, CvType.CV_32FC1);
		Imgproc.matchTemplate(src_img, temp, res, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult detectresult = Core.minMaxLoc(res);
		double min_val = detectresult.minVal;
		Point min_loc = detectresult.minLoc;
		double max_val = detectresult.maxVal;
		Point max_loc = detectresult.maxLoc;
		if(debug) {
			System.out.println("min_val : "+min_val);
			System.out.println("min_loc(x, y) : ("+min_loc.x+", "+min_loc.y+" )");
			System.out.println("max_val : "+max_val);
			System.out.println("max_loc(x, y) : ("+max_loc.x+", "+max_loc.y+" )");
		}
		if(max_val >= threshold) {
			return lbltemp.getfileName();
		}
		return "";
	}
}
