package com.wearit.shike.web.model.session.track;

public class RecordedTrack extends Track {
	private int syncNumber, virtual_id;

	/**
	 * @return syncNumber
	 */
	public int getSyncNumber() {
		return syncNumber;
	}

	/**
	 * @param syncNumber
	 *            il nuovo syncNumber da impostare
	 */
	public void setSyncNumber(int syncNumber) {
		this.syncNumber = syncNumber;
	}

	/**
	 * @return virtualId
	 */
	public int getVirtualId() {
		return virtual_id;
	}

	/**
	 * @param virtualId
	 *            il nuovo virtualId da impostare
	 */
	public void setVirtualId(int virtualId) {
		this.virtual_id = virtualId;
	}

	/**
	 * Calcola la lunghezza del percorso tramite i suoi punti
	 * 
	 * @return dst lunghezza calcolata del percorso
	 */
	public double getLengthCalc() {
		double dst = 0.0;
		Location prvl = null;
		for(Location p : this.getPoints()) {
			if(prvl != null) {
				dst += prvl.getDistance(p);
			}
			prvl = p;
		}

		return dst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + syncNumber;
		result = prime * result + virtual_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof RecordedTrack)) {
			return false;
		}
		RecordedTrack other = (RecordedTrack) obj;
		if(syncNumber != other.syncNumber) {
			return false;
		}
		if(virtual_id != other.virtual_id) {
			return false;
		}
		return true;
	}

}
