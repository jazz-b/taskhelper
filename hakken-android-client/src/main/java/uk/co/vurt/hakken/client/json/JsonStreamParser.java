package uk.co.vurt.hakken.client.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import uk.co.vurt.hakken.domain.job.JobDefinition;

public interface JsonStreamParser {

	public List<JobDefinition> parseJobDefinitionStream(InputStream in) throws IOException;
}
