/*
===========================================================
This file is part of the FIESTA-IoT platform. It is to be used as a sample
Version v0.0.1, October 2016.
Authors: Rachit Agarwal.
Copyright (C) 2016, Inria.
===========================================================
*/

package eu.fiesta_iot.experimentServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent context) {
		String realPath = context.getServletContext().getRealPath("/");
		ExperimentServerService.realPath=realPath;
		System.setProperty("contextPath", context.getServletContext().getContextPath());
	}
	public void contextDestroyed(ServletContextEvent context) {
	}
	
}
