
package com.gallup.gethip;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.gallup.gethip.model.Profile;
import com.gallup.gethip.model.Shareable;
import com.gallup.gethip.resources.ProfileResource;
import com.gallup.gethip.resources.ShareableResource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Main {

	private static ConnectionSource connectionSource;

	private static void createDatabaseConnection() {
		String databaseUrl = "jdbc:mysql://127.0.0.1:3306/shareapizza";
		try {
			connectionSource = new JdbcConnectionSource(databaseUrl);
			((JdbcConnectionSource) connectionSource).setUsername("root");
			((JdbcConnectionSource) connectionSource).setPassword("MyPassword");
			DataSourceManager.setConnectionSource(connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void buildDaos() {
		try {
			DataSourceManager.addDao(Profile.class);
			DataSourceManager.addDao(Shareable.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			TableUtils.createTableIfNotExists(connectionSource, Shareable.class);
			TableUtils.createTableIfNotExists(connectionSource, Profile.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ProfileResource profileResource = new ProfileResource();
		profileResource.createProfile(new Profile(1L, "kevin", "Kevin Lin", "123-456-7890"));
		profileResource.createProfile(new Profile(2L, "kevin2", "Kevin Lin2", "123-456-7890"));

		ShareableResource shareableResource = new ShareableResource();
		shareableResource.createShareable(new Shareable(1L, "Kevin", "Pizza", "Valentinos", 8.59d), null);
		shareableResource.createShareable(new Shareable(2L, "Kevin2", "Ice cream", "Dairy Queen", 4.36d), null);
	}

	private static int getPort(int defaultPort) {
		// grab port from environment, otherwise fall back to default port 9998
		String httpPort = System.getProperty("jersey.test.port");
		if (null != httpPort) {
			try {
				return Integer.parseInt(httpPort);
			} catch (NumberFormatException e) {
			}
		}
		return defaultPort;
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(getPort(9998)).build();
	}

	public static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException {
		ResourceConfig resourceConfig = new PackagesResourceConfig("com.gallup.gethip.resources");

		System.out.println("Starting grizzly2...");
		return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
	}

	public static void main(String[] args) throws IOException {
		// Grizzly 2 initialization
		HttpServer httpServer = startServer();
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
		createDatabaseConnection();
		buildDaos();
		System.in.read();
		httpServer.stop();
	}
}
