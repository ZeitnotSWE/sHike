package com.wearit.shike.web.model.user;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonAccount extends Account {

	private String firstName, lastName;
	private int height;
	private double weight;
	private Gender gender;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;

	@JsonIgnore
	private int syncCount;

	/**
	 * Costruttore di default di CommonAccount
	 */
	public CommonAccount() {
		super();
	}

	public int getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(int syncCount) {
		this.syncCount = syncCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@JsonIgnore
	public int getGender() {
		return gender.getId();
	}

	public void setGender(int gender) {
		this.gender = Gender.getById(gender);
	}

	@JsonProperty("gender")
	public Gender getGendeType() {
		return gender;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof CommonAccount)) {
			return false;
		}
		CommonAccount other = (CommonAccount) obj;
		if(birthDate == null) {
			if(other.birthDate != null) {
				return false;
			}
		} else if(!birthDate.equals(other.birthDate)) {
			return false;
		}

		if(firstName == null) {
			if(other.firstName != null) {
				return false;
			}
		} else if(!firstName.equals(other.firstName)) {
			return false;
		}
		if(gender.getId() != other.gender.getId()) {
			return false;
		}
		if(height != other.height) {
			return false;
		}
		if(lastName == null) {
			if(other.lastName != null) {
				return false;
			}
		} else if(!lastName.equals(other.lastName)) {
			return false;
		}
		if(syncCount != other.syncCount) {
			return false;
		}
		if(Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) {
			return false;
		}
		return true;
	}

	/**
	 * Enum che indica i possibili sessi indcati dallo standard ISO 5218
	 */
	public enum Gender {

		UNKNOWN(0, "sconosciuto"), MALE(1, "maschio"), FEMALE(2, "femmina"), UNSPECIFIED(9,
				"non specificato");

		/**
		 * Nome del sesso
		 */
		private final String name;

		/**
		 * Identificatore del sesso (in accordo con ISO 5218)
		 */
		private final int id;

		Gender(final int id, final String name) {
			this.name = name;
			this.id = id;
		}

		public static Gender getById(final int id) {
			for(final Gender gen : values()) {
				if(gen.getId() == id) {
					return gen;
				}
			}
			return null;
		}

		public int getId() {
			return id;
		}

		public static Gender getByName(final String name) {
			for(final Gender gen : values()) {
				if(gen.getName().equals(name)) {
					return gen;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

	}

}
