package cynynpri.java.sif.combose.imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class LabeledImg {
	private String name;
	private Mat img;
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public LabeledImg(String name, Mat img) {
		this.name = name;
		this.img = img;
	}

	public final String getfileName() {
		return name;
	}

	public final Mat getMat() {
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		LabeledImg other = (LabeledImg) obj;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
