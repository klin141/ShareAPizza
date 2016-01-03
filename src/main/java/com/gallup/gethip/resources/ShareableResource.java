package com.gallup.gethip.resources;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.gallup.gethip.DataSourceManager;
import com.gallup.gethip.model.Shareable;
import com.j256.ormlite.dao.Dao;

@Path("/shareables")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class ShareableResource {

	@GET
	public List<Shareable> readAllShareables() {
		try {
			return getDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{shareableID}")
	public Shareable readShareable(@PathParam("shareableID") long shareableID) {
		try {
			return getDao().queryForEq("id", shareableID).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	public Response createShareable(Shareable shareable, @Context UriInfo uriInfo) {
		try {
			shareable.setId(getDao().queryForAll().size() + 1);
			getDao().createIfNotExists(shareable);
			String newID = String.valueOf(shareable.getId());
			if (uriInfo != null) {
				URI uri = uriInfo.getAbsolutePathBuilder().path(newID).build();
				return Response.created(uri).entity(shareable).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PUT
	@Path("/{shareableID}")
	public Shareable updateShareable(@PathParam("shareableID") long id, Shareable shareable) {
		shareable.setId(id);
		try {
			getDao().update(shareable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shareable;
	}

	@DELETE
	@Path("/{shareableID}")
	public void deleteShareable(@PathParam("shareableID") long id) {
		try {
			getDao().delete(getDao().queryForEq("id", id).get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Dao<Shareable, String> getDao() {
		Dao<Shareable, String> dao = DataSourceManager.getInstance().getDao(Shareable.class);
		return dao;
	}

}
