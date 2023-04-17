
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class PatternSearcher {
	private static final Logger logger = LoggerFactory.getLogger(PatternSearcher.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
    private static final String QUERY_TEMPLATE = "{\"query\" : {\"query_string\" : {\"query\" : \"%s\"}}}";
//	private static final String PAGE_QUERY_TEMPLATE = "{\"query\" : {\"query_string\" : {\"query\" : \"%s\"}}, \"from\" : %d, \"size\" : %d}";
	
	private static final String PAGE_QUERY_TEMPLATE = 
	"{\"from\" : \"%d\", \"size\" : \"%d\",\"query\" : {\"query_string\" : {\"query\" : \"%s\"}}, \"sort\" : [{ \"event_ts\" : {\"order\" : \"%s\"}}]}";

	
	private String hostname = "localhost";
	private int port = 9200;
	private String protocol = "http";
	
	public PatternSearcher(String hostname, int port, String protocol) {
		this.hostname = hostname;
		this.port = port;
		this.protocol = protocol;
	}
	
	public JsonNode search(String index, String query) {
		// setting 1000 records if not size specified.
		return search(index, query, 0, 1000, "desc");
	}
	
	public JsonNode search(String index, String query, int from, int size, String sortOrder) {		
		JsonNode resultNode = null;
		try (RestClient restClient = RestClient.builder(new HttpHost(hostname, port, protocol)).build()) {
			
			HttpEntity entity = new NStringEntity((from < 0 || size < 0) ? String.format(QUERY_TEMPLATE, query) : String.format(PAGE_QUERY_TEMPLATE, from,size,query,
					     sortOrder), 
					ContentType.APPLICATION_JSON);
			Response response = restClient.performRequest("GET",
					(index == null ? "/_search" : ("/" + index + "/_search")),
					Collections.singletonMap("pretty", "true"), entity);
			resultNode = mapper.readTree(EntityUtils.toString(response.getEntity()));
			
		} catch (ResponseException e) {
			try {
				resultNode = mapper.readTree(EntityUtils.toString(e.getResponse().getEntity()));
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultNode;
	}
}

