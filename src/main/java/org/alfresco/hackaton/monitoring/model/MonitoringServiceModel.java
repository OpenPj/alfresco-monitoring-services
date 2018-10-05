package org.alfresco.hackaton.monitoring.model;

import org.alfresco.service.namespace.QName;

public interface MonitoringServiceModel {

	static final String NAMESPACE_URI = "http://www.alfresco.com/hackaton/model/monitoring/content/1.0";
	static final QName ASPECT_MONITORING = QName.createQName(NAMESPACE_URI, "monitoringAspect");
	
}
