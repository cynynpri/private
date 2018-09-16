package cynynpri.java.sif.combose.imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.scene.image.Image;

public final class CaptureImg {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private Mat mat;
	private Image img;

	public CaptureImg() {
		mat = new Mat();
		img = null;
	}

	public CaptureImg(Mat mat, Image img) {
		this.mat = mat;
		this.img = img;
	}

	public final Mat getMat() {
		return mat;
	}

	public final Image getImg() {
		return img;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((mat == null) ? 0 : mat.hashCode());
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
		CaptureImg other = (CaptureImg) obj;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (mat == null) {
			if (other.mat != null)
				return false;
		} else if (!mat.equals(other.mat))
			return false;
		return true;
	}
}
