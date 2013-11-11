package no.hib.tod141.reports.model;


import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;

@MongoCollection(name="images")
public class Image {
	
	private String _id;
	private String rId;
	private String dataURI;
	
	@ObjectId
	@JsonProperty("_id")
	public String get_id() {
		return _id;
	}
	@ObjectId
	@JsonProperty("_id")
	public void set_id(String _id) {
		this._id = _id;
	}
	@JsonProperty("rId")
	public String getrId() {
		return rId;
	}
	@JsonProperty("rId")
	public void setrId(String rId) {
		this.rId = rId;
	}
	@JsonProperty("dataURI")
	public String getDataURI() {
		return dataURI;
	}
	@JsonProperty("dataURI")
	public void setDataURI(String dataURI) {
		this.dataURI = dataURI;
	}
	
}
