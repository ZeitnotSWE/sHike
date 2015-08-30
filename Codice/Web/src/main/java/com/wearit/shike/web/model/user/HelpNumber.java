package com.wearit.shike.web.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelpNumber {
	private int account_id;
	private String number;
	
	@JsonProperty("name")
	private String description;

	/**
	 * @return account_id
	 */
	public int getAccount_id() {
		return account_id;
	}

	/**
	 * @param account_id
	 *            il nuovo account_id da impostare
	 */
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	/**
	 * @return number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            il nuovo number da impostare
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            il nuovo description da impostare
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + account_id;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof HelpNumber)) {
			return false;
		}
		HelpNumber other = (HelpNumber) obj;
		if(account_id != other.account_id) {
			return false;
		}
		if(description == null) {
			if(other.description != null) {
				return false;
			}
		} else if(!description.equals(other.description)) {
			return false;
		}
		if(number == null) {
			if(other.number != null) {
				return false;
			}
		} else if(!number.equals(other.number)) {
			return false;
		}
		return true;
	}

}
