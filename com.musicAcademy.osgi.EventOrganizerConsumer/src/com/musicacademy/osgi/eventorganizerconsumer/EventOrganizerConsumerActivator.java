package com.musicacademy.osgi.eventorganizerconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.musicacademy.osgi.middleservice.IMiddleService;

public class EventOrganizerConsumerActivator implements BundleActivator {

	ServiceReference serviceReference;
	
	@Override
	public void start(BundleContext context) throws Exception {
		
		System.out.println("Event consumer started...\n");

		serviceReference = context.getServiceReference(IMiddleService.class
				.getName());
		IMiddleService middleService = (IMiddleService) context.getService(serviceReference);
		
		middleService.selectEventOrganizerOpt();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
		System.out.println("Event consumer stopped...\n");
		context.ungetService(serviceReference);
	}

}
