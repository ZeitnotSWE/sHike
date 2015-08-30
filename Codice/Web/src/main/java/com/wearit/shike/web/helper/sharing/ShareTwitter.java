package com.wearit.shike.web.helper.sharing;

public class ShareTwitter implements Share {

	private final String SHARE_TW = "https://twitter.com/home?status=";
	private String text;

	public ShareTwitter() {
		this.text = "Ho appena condiviso il mio tracciato.";
	}

	/**
	 * @param text
	 *            da impostare sulla condivisione
	 */
	public ShareTwitter(String text) {
		this.text = text;
	}

	@Override
	public String share(String url) {
		return this.SHARE_TW + this.text + " - Via " + url;

	}

	/**
	 * @return sHARE_TW ritorna il link parziale del tweet
	 */
	public String getSHARE_TW() {
		return SHARE_TW;
	}

	/**
	 * @return text ritorna la descrizione del tweet
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            il nuovo text da impostare
	 */
	public void setText(String text) {
		this.text = text;
	}

}
