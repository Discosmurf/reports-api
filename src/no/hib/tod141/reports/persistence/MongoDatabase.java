package no.hib.tod141.reports.persistence;

import java.net.UnknownHostException;

import no.hib.tod141.reports.model.Image;
import no.hib.tod141.reports.model.Report;
import org.mongojack.JacksonDBCollection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/*****
 * 
 * This project has been using MongoLab for hosting through Heroku.com
 * In addition, the third party POJO-mapper	/deserialization tool used is MongoJack,
 * which works rather conveniently on top of the MongoDB Java driver.
 *
 */


public class MongoDatabase {

	private MongoClient mongo;
	private DB db;
	private DBCollection reportCollection;
	private DBCollection imageCollection;
	private JacksonDBCollection<Report, String> reportColl;
	private JacksonDBCollection<Image, String> imageColl;
	
	public MongoDatabase() throws UnknownHostException{
		
		try {
		mongo = new MongoClient(new MongoClientURI(DBDetails.MONGO_URI));
		} catch(UnknownHostException e) {
			throw new UnknownHostException("Error while establishing connection with MongoLab");
		}
		
		db = mongo.getDB(DBDetails.DB_NAME);
		reportCollection = db.getCollection(DBDetails.REPORT_COLLECTION_NAME);
		imageCollection = db.getCollection(DBDetails.IMAGE_COLLECTION_NAME);
		reportColl = JacksonDBCollection.wrap(reportCollection, Report.class, String.class);
		imageColl = JacksonDBCollection.wrap(imageCollection, Image.class, String.class);
		
	}
	
	public DB getDB() {
		return db;
	}
	
	public JacksonDBCollection<Report, String> getReportCollection() {
		return reportColl;
	}
	
	public JacksonDBCollection<Image, String> getImageCollection() {
		return imageColl;
	}
	
	public void close() {
		mongo.close();
	}
	
}
