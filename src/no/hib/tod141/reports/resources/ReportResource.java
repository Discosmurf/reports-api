package no.hib.tod141.reports.resources;


import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;
import no.hib.tod141.reports.dao.ReportsDAO;
import no.hib.tod141.reports.dao.ReportsDAOImpl;
import no.hib.tod141.reports.model.Image;
import no.hib.tod141.reports.model.ImageInfo;
import no.hib.tod141.reports.model.Report;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportResource {

	@Context
	private UriInfo uriInfo;
	private final ReportsDAO dao = ReportsDAOImpl.getInstance();
	
	// GET - /reports
	@GET
	public List<Report> getAllReports(@QueryParam("count") @DefaultValue("10") final int count, 
			@QueryParam("orderBy") @DefaultValue("reportedTime") final String order,
			@QueryParam("desc") @DefaultValue("true") final boolean asc) {
		
		// A count <= 0 returns all reports, else reports are returned according to the parameter query. 
		if(count <= 0) {
			return dao.getAllReports();
		} else {
			return dao.getListOfReportsFromParams(count, order, asc);
		}
	}
	
	
	// POST - /reports
	@POST
	public Response newReport(Report report) {
		
		Response response = null;
		URI uri = uriInfo.getAbsolutePath();
		
		String newID = dao.newReport(report);
		String location = String.format("%s/%s", uri.toString(), newID);
		response = Response.status(201).header("Location", location).entity(location).build();
		
		return response;
	}

	// GET - /reports/{rId}
	@GET
	@Path("{rId}")
	public Report getReport(@PathParam("rId")final String rId) {
		
		System.out.println(rId);
		
		return dao.getReport(rId);
		
	}

	// PUT - /reports/{rId}
	@PUT
	@Path("{rId}")
	public void updateReport(@PathParam("rId") final String rId, Report report) {
		
		System.out.println("PUT p� " + rId);
			
		dao.updateReport(rId, report);
		
	}

	// DELETE - /reports/{rId}
	@DELETE
	@Path("{rId}")
	public void deleteReport(@PathParam("rId") final String rId) {
	
		dao.deleteReport(rId);
		
	}

	// GET - /reports/{rId}/images
	
	@GET @Path("{rId}/images")
	public List<Image> getImageInfoFromReport(@PathParam("rId") final String rId) {  // Returnerer JSON
		
		return dao.getAllImageInfoFromReport(rId);
			
	}

	// POST - /reports/{rId}/images
	@POST @Path("{rId}/images")
	public Response appendNewImagesToReport(@PathParam("rId") final String rId, Image image) {
		
		// TODO: Add support for a collection of images, instead of just single images. 
		
		Response response;
		Report editMe = dao.getReport(rId);
		
		String newImageId = dao.appendNewImageToReport(rId, image);
		
		// It's important that the AJAX-calls to this method is done without a / at the end of the URL,
		// or else an extra / will be appended to the returned resource URI. 
		String newLocation = uriInfo.getAbsolutePath().toString() + "/" + newImageId;
		
		ImageInfo newImageInfo = new ImageInfo();
		newImageInfo.setiId(newImageId);
		newImageInfo.setUrl(newLocation);
		
		editMe.getImages().add(newImageInfo);
		dao.updateReport(rId, editMe);
		
		response = Response.status(201).entity(newLocation).header("Content-type", "text/plain").build();
		
		return response;
	}

	
	// GET - /reports/{rId}/images/{iId}
	
	@GET @Path("{rId}/images/{iId}")
	public Response getImageFromReport(@PathParam("rId") final String rId, @PathParam("iId") final String iId,
			@QueryParam("base64") @DefaultValue("false") final boolean encoded) {
		
		Response response;
		
		String dataURI = dao.getImageFromReport(rId, iId);
		String mimeTypeImage = "image/jpeg";
		String mimeTypeText = "text/plain";
		
		if(dataURI == null) {
			response = Response.status(404).entity("You accidentally 404'ed the whole thing!!").header("Content-type", mimeTypeText).build();
		} else if(!encoded) {
			byte[] imageData = DatatypeConverter.parseBase64Binary(dataURI);
			response = Response.ok(imageData, mimeTypeImage).header("Content-type", mimeTypeImage).build();
		} else {
			// Bullshit..
			response = Response.ok("data:" + mimeTypeImage + ";base64," + dataURI, mimeTypeImage).header("Content-type", mimeTypeImage).build();
		}
		
		
		return response;
	}
	
	// PUT - /reports/{rId}/images/{iId}
	@PUT @Path("{rId}/images/{iId}")
	public void updateImageFromReport(@PathParam("rId") final String rId, @PathParam("iId") final String iId, Image image) {
				
		dao.updateImageFromReport(rId, iId, image);
		
	}

	// DELETE - /reports/{rId}/images/{iId}
	@DELETE @Path("{rId}/images/{iId}")
	public void deleteImageFromReport(@PathParam("rId") final String rId, @PathParam("iId") final String iId) {
		
		dao.deleteImageFromReport(rId, iId);
		
	}
	
	

}
