/**
 *
 */
package cynynpri.java.sif.combose.imgproc;

import java.io.ByteArrayInputStream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javafx.scene.image.Image;

/**
 * @author game
 *
 */
public final class OpenCVCapture {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private Mat img;
	private int deviceIndex;
	private VideoCapture capture;

	public OpenCVCapture() {
		this.img = new Mat();
		this.deviceIndex = 0;
		this.capture = new VideoCapture(deviceIndex);
	}

	public OpenCVCapture(int deviceIndex) {
		this.img = new Mat();
		this.deviceIndex = deviceIndex;
		this.capture = new VideoCapture(deviceIndex);
	}

	/**
	 * @return img
	 */
	private final Mat getMat() {
		return img;
	}

	/**
	 * @param img セットする img
	 */
	private final void setMat(Mat img) {
		this.img = img;
	}

	/**
	 * @return deviceIndex
	 */
	public final int getDeviceIndex() {
		return deviceIndex;
	}

	/**
	 * @param deviceIndex セットする deviceIndex
	 */
	private final void setDeviceIndex(int deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

	/**
	 * @return capture
	 */
	private final VideoCapture getCapture() {
		return capture;
	}

	/**
	 * @param capture セットする capture
	 */
	private final void setCapture(VideoCapture capture) {
		this.capture = capture;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capture == null) ? 0 : capture.hashCode());
		result = prime * result + deviceIndex;
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		return result;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenCVCapture other = (OpenCVCapture) obj;
		if (capture == null) {
			if (other.capture != null)
				return false;
		} else if (!capture.equals(other.capture))
			return false;
		if (deviceIndex != other.deviceIndex)
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		return true;
	}

	public final double getFps2double() {
		//this method is native getFps method.
		if(null == capture) {
			return 0.0;
		}
		return capture.get(Videoio.CAP_PROP_FPS);
	}

	public final int getFps() {
		//this method is getFps to int-val.(the fps is floored.)
		if(null == capture) {
			return 0;
		}
		return (int)Math.floor(capture.get(Videoio.CAP_PROP_FPS));
	}

	public final Mat setFrame() throws OpenCVException {
		//capture error process
		if(null == capture) {
			String errmsg = new String();
			errmsg = "OpenCV:VideoCaptureConstructError!:OpenCVCapture.java:140:Mat_setFrame:if(null == capture )\n deviceIndex : "+deviceIndex+" .";
			int errcode = -1;// -1 is fatal error.
			throw new OpenCVException(errcode, errmsg);
		}
		if(!capture.isOpened()) {
			String errmsg = new String();
			errmsg = "OpenCV:VideoCaptureOpenError!:OpenCVCapture.java:148:Mat_setFrame:capture.isOpened().\n deviceIndex : "+deviceIndex+" .";
			int errcode = -5;// -5 is VideoCapture open error.
			if(null != capture) {
				capture.release();
			}
			throw new OpenCVException(errcode, errmsg);
		}else
		//capture read process.
		{
			capture.read(img);
			return getMat();
		}
	}

	public final Image getImg() {
		MatOfByte bytemat = new MatOfByte();
		Imgcodecs.imencode(".bmp", img, bytemat);
		ByteArrayInputStream byteimg = new ByteArrayInputStream(bytemat.toArray());
		Image src_img = new Image(byteimg);
		return src_img;
	}

	public final CaptureImg captureRun() {
		try {
			Mat src_mat = setFrame();
			Image src_img = getImg();
			return new CaptureImg(src_mat, src_img);
		}catch(OpenCVException e) {
			e.printStackTrace();
			return null;
		}
	}

	public final void stop() {
		if(null != this.capture) {
			if(this.capture.isOpened()) {
				capture.release();
			}
		}
	}
}
