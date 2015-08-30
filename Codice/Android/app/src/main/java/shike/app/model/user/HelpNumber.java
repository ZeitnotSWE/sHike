package shike.app.model.user;

/**
 * Classe che modella un numero di soccorso dell'utente
 */
public class HelpNumber {

	/**
	 * Numero di telefono
	 */
	private String number;

	/**
	 * Nome associato al numero
	 */
	private String name;

	public HelpNumber(String number, String name) {
		this.number = number;
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HelpNumber)) {
			return false;
		}

		HelpNumber that = (HelpNumber) o;

		if (number != null ? !number.equals(that.number) : that.number != null) {
			return false;
		}
		return !(name != null ? !name.equals(that.name) : that.name != null);

	}

	@Override
	public int hashCode() {
		int result = number.hashCode();
		result = 31 * result + name.hashCode();
		return result;
	}
}
