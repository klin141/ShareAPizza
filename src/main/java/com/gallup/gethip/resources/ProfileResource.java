package com.gallup.gethip.resources;

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
import javax.ws.rs.core.MediaType;

import com.gallup.gethip.DataSourceManager;
import com.gallup.gethip.model.Profile;
import com.j256.ormlite.dao.Dao;

@Path("/profiles")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class ProfileResource {

	@GET
	public List<Profile> readAllProfiles() {
		try {
			return getDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{profileName}")
	public Profile readProfile(@PathParam("profileName") String profileName) {
		try {
			return getDao().queryForEq("profile_name", profileName).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	public Profile createProfile(Profile profile) {
		try {
			profile.setId(getDao().queryForAll().size() + 1);
			getDao().createIfNotExists(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		try {
			getDao().update(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}

	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName) {
		try {
			getDao().delete(getDao().queryForEq("profile_name", profileName).get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Dao<Profile, String> getDao() {
		Dao<Profile, String> dao = DataSourceManager.getInstance().getDao(Profile.class);
		return dao;
	}
}
