package no.hib.tod141.reports.dao;


import java.util.List;
import no.hib.tod141.reports.model.Image;
import no.hib.tod141.reports.model.Report;



public interface ReportsDAO {
	
	// GET - /reports
	//BasicDBList getAllReports();
	List<Report> getAllReports();
	List<Report> getListOfReportsFromParams(int count, String orderBy, boolean asc);
	// POST - /reports
	String newReport(Report report);
	// GET - /reports/{rId}
	Report getReport(String rId);
	// PUT - /reports/{rId}
	void updateReport(String rId, Report report);
	// DELETE - /reports/{rId}
	void deleteReport(String rId);
	// GET - /reports/{rId}/images
	List<Image> getAllImageInfoFromReport(String rId);
	// POST - /reports/{rId}/images
	String appendNewImageToReport(String rId, Image image);
	// GET - /reports/{rId}/images/{iId}
	String getImageFromReport(String rId, String iId);
	// PUT - /reports/{rId}/images/{iId}
	void updateImageFromReport(String rId, String iId, Image image);
	// DELETE - /reports/{rId}/images/{iId}
	void deleteImageFromReport(String rId, String iId);
	
}
