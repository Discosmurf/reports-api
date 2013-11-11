package no.hib.tod141.reports.model;

import java.util.List;

import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;

@MongoCollection(name="reports")
public class Report {

	private String _id;
	private double latitude;
	private double longitude;
	private String reportedBy;
	private long reportedTime;
	private String description;
	private List<ImageInfo> images;
	
	@ObjectId
	@JsonProperty("_id")
	public String getId() {
		return _id;
	}
	
	@ObjectId
	@JsonProperty("_id")
	public void setId(String _id) {
		this._id = _id;
	}
	
	@JsonProperty("latitude")
	public double getLatitude() {
		return latitude;
	}
	@JsonProperty("latitude")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@JsonProperty("longitude")
	public double getLongitude() {
		return longitude;
	}
	@JsonProperty("longitude")
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@JsonProperty("reportedBy")
	public String getReportedBy() {
		return reportedBy;
	}
	@JsonProperty("reportedBy")
	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}
	@JsonProperty("reportedTime")
	public long getReportedTime() {
		return reportedTime;
	}
	@JsonProperty("reportedTime")
	public void setReportedTime(long reportedTime) {
		this.reportedTime = reportedTime;
	}
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty("images")
	public List<ImageInfo> getImages() {
		return images;
	}
	@JsonProperty("images")
	public void setImages(List<ImageInfo> images) {
		this.images = images;
	}
	
	
	
}
