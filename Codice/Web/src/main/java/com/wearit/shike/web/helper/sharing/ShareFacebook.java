package com.wearit.shike.web.helper.sharing;

public class ShareFacebook implements Share {

	private final String SHARE_FB = "https://www.facebook.com/sharer/sharer.php?u=";

	@Override
	public String share(String url) {
		return this.SHARE_FB + url;

	}

	/**
	 * @return sHARE_FB
	 */
	public String getSHARE_FB() {
		return SHARE_FB;
	}

}
