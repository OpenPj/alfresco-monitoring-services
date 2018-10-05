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

package org.alfresco.hackaton.monitoring.behaviors;

import org.alfresco.hackaton.monitoring.model.MonitoringServiceModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class NodeServiceMonitoringBehaviors implements 

	NodeServicePolicies.BeforeCreateNodePolicy, 
	NodeServicePolicies.OnCreateNodePolicy,
	NodeServicePolicies.BeforeUpdateNodePolicy {

    protected static Log logger = LogFactory.getLog(NodeServiceMonitoringBehaviors.class);
	
	/** The policy component */
    private PolicyComponent policyComponent;


	/** The DropWizard metric registry **/
    private MetricRegistry metricRegistry;
    
    /** The request metric for services **/
    private Meter requests; 
    private static final String METRIC_NAME = "node-service-requests";
    
    // Behaviours
    private Behaviour beforeCreateNode;
    private Behaviour onCreateNode;
    private Behaviour beforeUpdateNode;
    
    public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}
	
	public void setMetricRegistry(MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}

	@Override
	public void beforeCreateNode(NodeRef parentRef, QName assocTypeQName, QName assocQName, QName nodeTypeQName) {
		requests.mark();
		
		if(logger.isDebugEnabled()) {
			logger.debug("NodeService - Monitoring - Before Create Node - Marked");
		}

	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		requests.mark();
		
		if(logger.isDebugEnabled()) {
			logger.debug("NodeService - Monitoring - On Create Node - Marked");
		}
	}
	
	@Override
	public void beforeUpdateNode(NodeRef nodeRef) {
		requests.mark();
		
		if(logger.isDebugEnabled()) {
			logger.debug("NodeService - Monitoring - Before Update Node - Marked");
		}
		
	}

	
    public void init() {
    	requests = metricRegistry.meter(METRIC_NAME);
    	
    	//Creating the behaviors
        this.beforeCreateNode = new JavaBehaviour(
        		this, 
        		NodeServicePolicies.BeforeCreateNodePolicy.QNAME.getLocalName(), 
        		Behaviour.NotificationFrequency.EVERY_EVENT);
        
        this.onCreateNode = new JavaBehaviour(
        		this, 
        		NodeServicePolicies.OnCreateNodePolicy.QNAME.getLocalName(), 
        		Behaviour.NotificationFrequency.EVERY_EVENT);
        
        this.beforeUpdateNode = new JavaBehaviour(
        		this, 
        		NodeServicePolicies.BeforeUpdateNodePolicy.QNAME.getLocalName(), 
        		Behaviour.NotificationFrequency.EVERY_EVENT);

    	
        //Binding the behaviors
        this.policyComponent.bindClassBehaviour(
                NodeServicePolicies.BeforeCreateNodePolicy.QNAME,
                MonitoringServiceModel.ASPECT_MONITORING,
                beforeCreateNode);
        
        this.policyComponent.bindClassBehaviour(
                NodeServicePolicies.OnCreateNodePolicy.QNAME,
                MonitoringServiceModel.ASPECT_MONITORING,
                this.onCreateNode);
        
        this.policyComponent.bindClassBehaviour(
                NodeServicePolicies.BeforeUpdateNodePolicy.QNAME,
                MonitoringServiceModel.ASPECT_MONITORING,
                this.beforeUpdateNode);
    }

	

}
