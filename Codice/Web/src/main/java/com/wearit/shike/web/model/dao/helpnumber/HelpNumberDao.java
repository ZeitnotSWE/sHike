package com.wearit.shike.web.model.dao.helpnumber;

import java.util.List;
import com.wearit.shike.web.model.user.HelpNumber;

/**
 * Interfaccia che rappresenta il DAO per la tabella HelpNumbers. Tale interfaccia
 * definisce tutte le operazioni di CRUD per tale tabella.
 */
public interface HelpNumberDao {

	/**
	 * Ritornata i numeri associati all'utente
	 * 
	 * @param idUser
	 *            id utente
	 * @return numeri associati all'utente
	 */
	public List<HelpNumber> getAll(int idUser);

	/**
	 * Ritorna il singolo HelpNumber con i parametri forniti
	 * 
	 * @param idUser
	 *            utente del numero
	 * @param number
	 *            numero richiesto
	 * @return numer dell'utente idUser
	 */
	public HelpNumber get(int idUser, String number);

	/**
	 * Metodo che viene usato per aggiungere un numero alla tabella HelpNumbers
	 * 
	 * @param number
	 *            numero da aggiungere
	 * @param description
	 *            descrizione associata
	 */
	public void add(int idUser, HelpNumber number);

	/**
	 * Metodo che viene usato per togliere un numero dalla tabella HelpNumbers
	 * 
	 * @param number
	 *            numero da rimuovere
	 */
	public void delete(int id, String number);

	/**
	 * Metodo che viene usato per aggiornare un numero di soccorso
	 * 
	 * @param id
	 * @param number
	 * @param newNumber
	 *            nuovo numero che verra' inserito
	 */
	public void update(int id, String number, HelpNumber newNumber);

}
