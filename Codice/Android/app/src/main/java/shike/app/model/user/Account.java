package shike.app.model.user;

import java.util.Date;
import java.util.UUID;

/**
 * Classe che modella l'account del sito web che è stato associato all'applicazione
 */
public class Account {

	/**
	 * Identificatore univoco dell'account (corrisponde all'attributo _id nel database)
	 */
	private int _id;
	/**
	 * Token di connessione al sito web
	 */
	private UUID connectionToken;
	private String firstName;
	private String lastName;
	private int height;
	private double weight;
	private Date birthDate;
	private Gender gender;

	/**
	 * Costruttore di Account
	 *
	 * @param _id id univoco dell'account
	 */
	public Account(int _id) {
		this._id = _id;
	}

	/**
	 * Crea e ritorna un account temporaneo contenente solamente il token temporaneo ricevuto
	 * dalla parte web
	 *
	 * @param connectionToken token temporaneo
	 * @return account temporaneo creato
	 */
	public static Account createTempAccount(UUID connectionToken) {
		Account tempAccount = new Account(0);
		tempAccount.connectionToken = connectionToken;
		tempAccount.gender = Gender.UNKNOWN;
		tempAccount.firstName = "";
		tempAccount.lastName = "";
		tempAccount.birthDate = new Date(0);
		return tempAccount;
	}

	public int get_id() {
		return _id;
	}

	public UUID getConnectionToken() {
		return connectionToken;
	}

	public void setConnectionToken(UUID connectionToken) {
		this.connectionToken = connectionToken;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Account)) {
			return false;
		}

		Account account = (Account) o;

		if (_id != account._id) {
			return false;
		}
		if (height != account.height) {
			return false;
		}
		if (Double.compare(account.weight, weight) != 0) {
			return false;
		}
		if (connectionToken != null ? !connectionToken.equals(account.connectionToken) :
			account.connectionToken != null) {
			return false;
		}
		if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) {
			return false;
		}
		if (lastName != null ? !lastName.equals(account.lastName) : account.lastName != null) {
			return false;
		}
		if (birthDate != null ? !birthDate.equals(account.birthDate) : account.birthDate != null) {
			return false;
		}
		return gender == account.gender;

	}

	/**
	 * Enum che indica i possibili sessi indcati dallo standard ISO 5218
	 */
	public enum Gender {

		UNKNOWN(0, "sconosciuto"),
		MALE(1, "maschio"),
		FEMALE(2, "femmina"),
		UNSPECIFIED(9, "non specificato");

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
			for (final Gender gen : values()) {
				if (gen.getId() == id) {
					return gen;
				}
			}
			return UNKNOWN;
		}

		public int getId() {
			return id;
		}

		public static Gender getByName(final String name) {
			for (final Gender gen : values()) {
				if (gen.getName().equals(name)) {
					return gen;
				}
			}
			return UNKNOWN;
		}

		public String getName() {
			return name;
		}

	}

}
