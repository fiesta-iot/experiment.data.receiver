/*
===========================================================
This file is part of the FIESTA-IoT platform. It is to be used as a sample
Version v0.0.1, October 2016.
Authors: Rachit Agarwal.
Copyright (C) 2016, Inria.
===========================================================
*/

//code is partly inspired from http://stackoverflow.com/questions/35122747/cant-get-utf-8-upload-file-name-in-servlet-on-server-wildfly-9-0-2

package eu.fiesta_iot.experimentServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class ExperimentServerService {
	public static String realPath;
	static Logger log = LoggerFactory.getLogger(ExperimentServerService.class);

	@GET
	@Produces("text/plain")
	@Path("/test")
	public String welcomeMessage() {
		String welcomeText = "Welcome to Experiment Services\n" + "=============================\n\n";
		log.info(welcomeText);
		return welcomeText;
	}
	
	/**
	 * Store the file in the desired repository
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@POST
	@Path("/store")
	public Response store(@Context HttpServletRequest request) throws IOException {

		log.info("realPath: " + realPath);

		try {
			for (Part part : request.getParts()) {

				String fileName = extractFileName(part);

				File file = new File("<FILEPATH>", fileName);

				FileWriter out;
				try {
					out = new FileWriter(file);
					try {
						out.write(getContent(part.getInputStream()));
					} finally {
						try {
							out.close();
						} catch (IOException closeException) {
							closeException.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				log.info("file created");
			}

			return Response.ok().build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity(e.getMessage())
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity(e.getMessage())
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	private static String getContent(InputStream input) {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1024];
		int readBytes = 0;
		try {
			while ((readBytes = input.read(b)) >= 0) {
				sb.append(new String(b, 0, readBytes, "UTF-8"));
			}
			input.close();
			return sb.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
			if (input != null)
				try {
					input.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return null;
	}
}
