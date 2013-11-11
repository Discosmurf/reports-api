package no.hib.tod141.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageInfo {

	private String iId;
	private String url;
	
	@JsonProperty("iId")
	public String getiId() {
		return iId;
	}
	@JsonProperty("iId")
	public void setiId(String iId) {
		this.iId = iId;
	}
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}
	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}
	
}
