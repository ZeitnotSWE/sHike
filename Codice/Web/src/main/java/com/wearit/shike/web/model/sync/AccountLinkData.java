package com.wearit.shike.web.model.sync;

import java.util.Random;
import java.util.UUID;

/**
 * Classe che modella i dati di collegamento account dal web
 */
public class AccountLinkData {
	/**
	 * Codice usa e getta da utilizzare per la prima connessione
	 */
	private String oneTimeCode;
	/**
	 * Token di connessione da usare per le sincronizzazioni
	 */
	private UUID connectionToken;

	public String getOneTimeCode() {
		return oneTimeCode;
	}

	public UUID getConnectionToken() {
		return connectionToken;
	}

	public void setOneTimeCode(String s) {
		oneTimeCode = s;
	}

	public void setConnectionToken(UUID u) {
		connectionToken = u;
	}

	public static AccountLinkData generateRandAcc() {
		AccountLinkData ad = new AccountLinkData();
		ad.connectionToken = UUID.randomUUID();
		ad.oneTimeCode = AccountLinkData.generateRandString();
		return ad;
	}

	private static String generateRandString() {
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i < 5; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

}
