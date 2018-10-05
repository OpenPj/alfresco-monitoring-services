package org.alfresco.hackaton.monitoring.reporters;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.metrics.ElasticsearchReporter;
import org.junit.Test;

import com.codahale.metrics.MetricRegistry;

public class ElasticSearchReporterTest {
	
	final MetricRegistry registry = new MetricRegistry();

	@Test
	public void testElasticReporter() throws IOException {
		ElasticsearchReporter reporter = ElasticsearchReporter.forRegistry(registry)
		    .hosts("localhost:9200", "localhost:9201")
		    .build();
		reporter.start(60, TimeUnit.SECONDS);
	}
	
}
