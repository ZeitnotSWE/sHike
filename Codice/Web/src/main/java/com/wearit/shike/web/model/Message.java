package com.wearit.shike.web.model;

public class Message {
	private String title, content;
	private Alert alert;
	private boolean backToList;

	public Message(String title, String content) {
		this.title = title;
		this.content = content;
		this.alert = Alert.SUCCESS;
		this.backToList = false;
	}

	public Message(String title, String content, int alert_id) {
		this.title = title;
		this.content = content;
		this.alert = Alert.getByValue(alert_id);
		this.backToList = false;
	}

	public enum Alert {
		SUCCESS(0, "alert-success"), DANGER(1, "alert-danger"), WARNING(2, "alert-warning"), INFO(
				3, "alert-info");

		private final String name;
		private final int value;

		Alert(final int value, final String name) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		public static Alert getByName(final String name) {
			for(final Alert gen : values()) {
				if(gen.getName().equals(name)) {
					return gen;
				}
			}
			return null;
		}

		public static Alert getByValue(final int value) {
			for(final Alert gen : values()) {
				if(gen.getValue() == value) {
					return gen;
				}
			}
			return null;
		}

	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            il nuovo title da impostare
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            il nuovo content da impostare
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return alert
	 */
	public Alert getAlert() {
		return this.alert;
	}

	/**
	 * @param alert
	 *            il nuovo alert da impostare
	 */
	public void setAlert(int alert_id) {
		this.alert = Alert.getByValue(alert_id);
	}

	/**
	 * @return backToList
	 */
	public boolean isBackToList() {
		return backToList;
	}

	/**
	 * @param backToList
	 *            il nuovo backToList da impostare
	 */
	public void setBackToList(boolean backToList) {
		this.backToList = backToList;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Message)) {
			return false;
		}
		Message other = (Message) obj;
		if(alert.getValue() != other.alert.getValue()) {
			return false;
		}
		if(backToList != other.backToList) {
			return false;
		}
		if(content == null) {
			if(other.content != null) {
				return false;
			}
		} else if(!content.equals(other.content)) {
			return false;
		}
		if(title == null) {
			if(other.title != null) {
				return false;
			}
		} else if(!title.equals(other.title)) {
			return false;
		}
		return true;
	}

}