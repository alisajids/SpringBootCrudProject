package util;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRestClient {
	
	protected String pwd;
	protected RestTemplate restTemplate;
	
	// Timeouts
	@Value("${http.connection-timeout:30000}")
    private int connectionTimeout;// 30 sec, the time for waiting until a connection is established
	
	@Value("${http.request-timeout:30000}")
    private int requestTimeout; // 30 sec, the time for waiting for a connection from connection pool
	
	@Value("${http.socket-timeout:60000}")
    private int socketTimeout;  // 60 sec, the time for waiting for data
	
	@Value("${http.retry-count:2}")
	private int retryCount; // No of times to retry in case of broken connection
	
    protected static final String FW_SL = "/";
	
	@Autowired
    private ProxySettings proxySettings;
	
	@PostConstruct
	protected void init() {
		final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		restTemplate = new RestTemplate(requestFactory);
		restTemplate.setErrorHandler(new RestClientResponseErrorHandler());
		restTemplate.setInterceptors(
				Arrays.asList(new BasicAuthenticationInterceptor(username, pwd, StandardCharsets.UTF_8)));
	}
	
	protected final ClientHttpRequestFactory getClientHttpRequestFactory() {
        log.debug("proxy: {}:{}", proxySettings.getHost(), proxySettings.getPort());
        HttpHost proxy = proxySettings.getHttpHost();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setProxy(proxy)
                .build();

        SSLContext sslContext = null;
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (KeyManagementException | NoSuchAlgorithmException
                | KeyStoreException e) {
            log.error("Error encountered in ServiceNow Client: {}", e.getMessage());
            throw new AppException("Error occured in creating custom ssl context");
        }

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom()
        		.setRetryHandler((exception, executionCount, context) -> {
                    if (executionCount > retryCount) {
                        log.warn("Maximum retries {} reached for connecting to Service Now", retryCount);
                        return false;
                    }
                    return true;
                })
                .setSSLSocketFactory(sslSocketFactory)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
	
	protected HttpEntity<String> setReqEntity(String requestBody) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(APPLICATION_JSON));
		if (StringUtils.isNotBlank(requestBody))
			return new HttpEntity<>(requestBody, requestHeaders); 
		return new HttpEntity<>(requestHeaders);
	}
	
	 protected String exchange(final String path, final Map<String, ?> pathVars, final HttpMethod method,
            final String jsonRequestBody, final MultiValueMap<String, String> params, final MediaType accept,
            final MediaType contentType) {

        String endpt = (pathVars != null && !pathVars.isEmpty()) ? new UriTemplate(path).expand(pathVars).toString()
                : path;
        URI uri = UriComponentsBuilder.fromHttpUrl(endpt).queryParams(params).build().encode().toUri();
        HttpHeaders requestHeaders = new HttpHeaders();
        if (accept == null) {
            requestHeaders.setAccept(Arrays.asList(APPLICATION_JSON));
        } else {
            requestHeaders.setAccept(Arrays.asList(accept));
        }
        if (contentType == null) {
            requestHeaders.setContentType(APPLICATION_JSON);
        } else {
            requestHeaders.setContentType(contentType);
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, method, requestEntity, String.class);
        // if error is encountered, error handler should have thrown an exception
        return responseEntity.getBody();
    }

    /**
     * @param path        The path to use when making a Service REST API. This can
     *                    contain path variables which will be expanded using the
     *                    supplied pathVars parameter.
     * @param pathVars    The values to use to substitute variables specified in the
     *                    path if any
     * @param method      The HTTPMethod to use
     * @param payloadBody The map containing the request payload
     * @param params      The map containing all the query parameters for the
     *                    request if any
     * @param accept      The format of the response that's acceptable/expected
     * @param contentType The format for the request data to be sent
     * @return
     */
    protected String exchange(final String path, final Map<String, ?> pathVars, final HttpMethod method,
            final MultiValueMap<String, Object> payloadBody, final MultiValueMap<String, String> params,
            final MediaType accept, final MediaType contentType) {

        String endpt = (pathVars != null && !pathVars.isEmpty()) ? new UriTemplate(path).expand(pathVars).toString()
                : path;
        URI uri = UriComponentsBuilder.fromHttpUrl(endpt).queryParams(params).build().encode().toUri();
        HttpHeaders requestHeaders = new HttpHeaders();
        if (accept == null) {
            requestHeaders.setAccept(Arrays.asList(APPLICATION_JSON));
        } else {
            requestHeaders.setAccept(Arrays.asList(accept));
        }
        if (contentType == null) {
            requestHeaders.setContentType(APPLICATION_JSON);
        } else {
            requestHeaders.setContentType(contentType);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(payloadBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, method, requestEntity, String.class);
        // if error is encountered, error handler should have thrown an exception
        return responseEntity.getBody();
    }
}
