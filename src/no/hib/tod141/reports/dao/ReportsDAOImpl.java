package no.hib.tod141.reports.dao;


import java.net.UnknownHostException;
import java.util.List;
import org.mongojack.DBQuery;
import org.mongojack.DBSort;
import org.mongojack.WriteResult;
import no.hib.tod141.reports.model.Image;
import no.hib.tod141.reports.model.ImageInfo;
import no.hib.tod141.reports.model.Report;
import no.hib.tod141.reports.persistence.MongoDatabase;

public class ReportsDAOImpl implements ReportsDAO {

	private static ReportsDAOImpl instance = null;
	private MongoDatabase db;

	
	public static synchronized ReportsDAOImpl getInstance() {
		
		if(instance == null) {
			instance = new ReportsDAOImpl();
			System.out.println("Ny DAO");
		} 
		
		return instance;
		
	}
	
	private ReportsDAOImpl() {

		try {
			db = new MongoDatabase();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Report> getAllReports() {

		List<Report> list = db.getReportCollection().find().toArray();

		return list;
	}
	
	@Override
	public List<Report> getListOfReportsFromParams(int count, String orderBy, boolean desc) {
		
		List<Report> list = null;
		
		if(desc) {
		
			list = db.getReportCollection().find().sort(DBSort.desc(orderBy)).limit(count).toArray();
		
		} else {
			list = db.getReportCollection().find().sort(DBSort.asc(orderBy)).limit(count).toArray();
		}
		
		
		return list;
	}

	@Override
	public String newReport(Report report) {

		WriteResult<Report, String> result = db.getReportCollection().insert(
				report);
		
		return result.getSavedId();
	}

	@Override
	public Report getReport(String rId){

		Report report = db.getReportCollection().findOneById(rId);
	
		return report;
	}

	@Override
	public void updateReport(String rId, Report report) {
		
		db.getReportCollection().updateById(rId, report);

	}

	@Override
	public void deleteReport(String rId) {
		
		db.getReportCollection().removeById(rId);

	}

	@Override
	public List<Image> getAllImageInfoFromReport(String rId) {
		
		List<Image> list = db.getImageCollection().find(DBQuery.is("rId", rId)).toArray();
		
		return list;
	}

	@Override
	public String appendNewImageToReport(String rId, Image image) {
		
		if(image.getrId() == null) {
			image.setrId(rId);
		}
		
		String imageID = db.getImageCollection().insert(image).getSavedId();
		
		return imageID;
	}

	@Override
	public String getImageFromReport(String rId, String iId) {
		
		Image image = db.getImageCollection().findOne(DBQuery.is("_id", iId).and(DBQuery.is("rId", rId)));
		
		if(image == null) {
			return null;
		} else {
			return image.getDataURI();
		}
		
	}

	@Override
	public void updateImageFromReport(String rId, String iId, Image image) {
		
		db.getImageCollection().update(DBQuery.is("_id", iId).and(DBQuery.is("rId", rId)), image);

	}

	@Override
	public void deleteImageFromReport(String rId, String iId) {
		
		db.getImageCollection().removeById(iId);
		
		Report editMe = this.getReport(rId);
	
		List<ImageInfo> imageInfos = editMe.getImages();
		ImageInfo found = null;
		
		for(ImageInfo imageInfo : imageInfos) {
			
			if(imageInfo.getiId().equals(iId)) {
				found = imageInfo;
			}
			
		}
		
		if(found != null) {
			imageInfos.remove(found);
		}
		
		db.getReportCollection().updateById(rId, editMe);
		
	}

	public void closeDAO() {
		db.close();
	}

}
