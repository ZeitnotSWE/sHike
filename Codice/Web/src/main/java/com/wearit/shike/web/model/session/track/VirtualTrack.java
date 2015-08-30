/**
 * 
 */
package com.wearit.shike.web.model.session.track;

public class VirtualTrack extends Track {
	private String name, authorName;
	private boolean isToSync;

	public VirtualTrack() {
		super();
	}

	/**
	 * @param name
	 */
	public VirtualTrack(String name) {
		super();
		this.name = name;
	}

	/**
	 * Enum che modella i vari livelli di difficoltà
	 */
	public enum Level {

		EASY(0, "Facile"), MEDIUM(1, "Medio"), HARD(2, "Difficile");

		/**
		 * Identificatore univoco della difficoltà
		 */
		private final int id;

		/**
		 * Stringa identificativa della difficoltà
		 */
		private final String name;

		Level(int id, String name) {
			this.id = id;
			this.name = name;
		}

		/**
		 * @return id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return name
		 */
		public String getName() {
			return name;
		}

		public static Level getById(final int id) {
			for(final Level dType : values()) {
				if(dType.id == id) {
					return dType;
				}
			}
			return null;
		}

		public static Level getByName(final String name) {
			for(final Level dType : values()) {
				if(dType.name.equals(name)) {
					return dType;
				}
			}
			return null;
		}

	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            il nuovo name da impostare
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            il nuovo authorName da impostare
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return isToSync
	 */
	public boolean isToSync() {
		return isToSync;
	}

	/**
	 * @param isToSync
	 *            il nuovo isToSync da impostare
	 */
	public void setToSync(boolean isToSync) {
		this.isToSync = isToSync;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!super.equals(obj)) {
			return false;
		}
		if(!(obj instanceof VirtualTrack)) {
			return false;
		}
		VirtualTrack other = (VirtualTrack) obj;
		if(name == null) {
			if(other.name != null) {
				return false;
			}
		} else if(!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public Level getLevel() {
		Level dt = Level.EASY;
		if((this.getHeightDiff() > 1000.0) || (this.getLength() > 40.0)) {
			dt = Level.HARD;
		} else if((this.getHeightDiff() > 500.0) || (this.getLength() > 15.0)) {
			dt = Level.MEDIUM;
		}
		return dt;
	}

}
