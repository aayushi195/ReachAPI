package edu.asu.heal.reachv3.api.models;

public class Options {

	private int optionId;
	private String title;

	public Options() {}

	public Options(int optionId, String title) {
		this.optionId = optionId;
		this.title = title;
	}

	public int getOptionId() {

		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Options : {" +
				", optionId : '" + optionId + '\'' +
				", title : '" + title + '\'' +
				'}';
	}
}
