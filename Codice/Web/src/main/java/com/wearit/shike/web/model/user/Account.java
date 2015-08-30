package com.wearit.shike.web.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account {
	private int _id;
	private String emailAddress;

	@JsonIgnore
	private String passwordHash, passwordHashRepeat, role;

	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            il nuovo role da impostare
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            il nuovo _id da impostare
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            il nuovo emailAddress da impostare
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 *            il nuovo passwordHash da impostare
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return passwordHashRepeat
	 */
	public String getPasswordHashRepeat() {
		return passwordHashRepeat;
	}

	/**
	 * @param passwordHashRepeat
	 *            il nuovo passwordHashRepeat da impostare
	 */
	public void setPasswordHashRepeat(String passwordHashRepeat) {
		this.passwordHashRepeat = passwordHashRepeat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		result = prime * result
				+ ((passwordHashRepeat == null) ? 0 : passwordHashRepeat.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}

		if(!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		if(_id != other._id) {
			return false;
		}
		if(emailAddress == null) {
			if(other.emailAddress != null) {
				return false;
			}
		} else if(!emailAddress.equals(other.emailAddress)) {
			return false;
		}

		if(role == null) {
			if(other.role != null) {
				return false;
			}
		} else if(!role.equals(other.role)) {
			return false;
		}
		return true;
	}

}
