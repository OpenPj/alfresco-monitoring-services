/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.alfresco.hackaton.monitoring.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class MonitoringServiceMethodInterceptor implements MethodInterceptor {
	
	/** The DropWizard metric registry **/
	private MetricRegistry metricRegistry;

	/** The request metric for services **/
	private Meter requests;

	/** 
	 * Service Name policy for tracking different requests for services: node-service-method-requests, content-service-method-requests
	 * **/
	private static final String SERVICE_REPLACER = "<?SERVICE_NAME?>";
	private static final String METRIC_NAME_PATTERN = SERVICE_REPLACER + "-method-requests";

	private String serviceName;

	private Timer timer = new Timer();

	public void setMetricRegistry(MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = null;
		requests.mark();
		Timer.Context timingContext = timer.time();
		object = invocation.proceed();
		timingContext.stop();
		return object;
	}

	public void init() {
		String serviceMetric = StringUtils.replace(METRIC_NAME_PATTERN, SERVICE_REPLACER, serviceName);
		requests = metricRegistry.meter(serviceMetric);
	}

}
