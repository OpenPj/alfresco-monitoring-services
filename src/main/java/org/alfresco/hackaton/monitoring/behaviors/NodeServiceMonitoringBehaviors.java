package org.alfresco.hackaton.monitoring.behaviors;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

public class NodeServiceMonitoringBehaviors implements NodeServicePolicies.BeforeCreateNodePolicy, NodeServicePolicies.OnCreateNodePolicy {

	@Override
	public void beforeCreateNode(NodeRef parentRef, QName assocTypeQName, QName assocQName, QName nodeTypeQName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		// TODO Auto-generated method stub
		
	}

}
