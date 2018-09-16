/**
 *
 */
package cynynpri.java.sif.combose.detects.Card;

/**
 * @author game
 *
 */
public class CardAttribute {
	protected enum Attribute{
		Smile("スマイル"),
		Pure("ピュア"),
		Cool("クール"),
		All("すべて"),
		Empty("");
		private String attrName;
		private Attribute(String attrName) {
			this.attrName = attrName;
		}

		public String getName() {
			return this.attrName;
		}
	}
	private Attribute attr;
	private Integer smile;
	private Integer pure;
	private Integer cool;

	public CardAttribute(String attr, Integer smile, Integer pure, Integer cool) {
		this.attr = Attribute.valueOf(attr);
		this.smile = smile;
		this.pure = pure;
		this.cool = cool;
	}

	public CardAttribute() {
		this.attr = Attribute.valueOf(new String());
		this.smile = new Integer(0);
		this.pure = new Integer(0);
		this.cool = new Integer(0);
	}

	/**
	 * @return attr
	 */
	public final Attribute getAttr() {
		return attr;
	}

	/**
	 * @param attr セットする attr
	 */
	private final void setAttr(Attribute attr) {
		this.attr = attr;
	}

	protected final void setAttr(String attr) {
		this.attr = Attribute.valueOf(attr);
	}

	/**
	 * @return smile
	 */
	public final Integer getSmile() {
		return smile;
	}

	/**
	 * @param smile セットする smile
	 */
	protected final void setSmile(Integer smile) {
		this.smile = smile;
	}

	/**
	 * @return pure
	 */
	public final Integer getPure() {
		return pure;
	}

	/**
	 * @param pure セットする pure
	 */
	protected final void setPure(Integer pure) {
		this.pure = pure;
	}

	/**
	 * @return cool
	 */
	public final Integer getCool() {
		return cool;
	}

	/**
	 * @param cool セットする cool
	 */
	protected final void setCool(Integer cool) {
		this.cool = cool;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attr == null) ? 0 : attr.hashCode());
		result = prime * result + ((cool == null) ? 0 : cool.hashCode());
		result = prime * result + ((pure == null) ? 0 : pure.hashCode());
		result = prime * result + ((smile == null) ? 0 : smile.hashCode());
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
		CardAttribute other = (CardAttribute) obj;
		if (attr != other.attr)
			return false;
		if (cool == null) {
			if (other.cool != null)
				return false;
		} else if (!cool.equals(other.cool))
			return false;
		if (pure == null) {
			if (other.pure != null)
				return false;
		} else if (!pure.equals(other.pure))
			return false;
		if (smile == null) {
			if (other.smile != null)
				return false;
		} else if (!smile.equals(other.smile))
			return false;
		return true;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardAttribute [attr=" + attr + ", smile=" + smile + ", pure=" + pure + ", cool=" + cool + "]";
	}
}
