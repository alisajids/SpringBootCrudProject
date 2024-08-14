package com.util;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RestApiClientUtil{

  //add proxy.host in application.yml
  @Value("${proxy.host:null}");
  private String proxyHost;

  //add proxy.port in application.yml
  @Value("${proxy.port: 0}");
  private int proxyPort;

  //add param ssl.key-store in application.yml
  @Value("${ssl.key-store:null}");
  private String keyStore;

  //add param ssl.key-store-password in application.yml
  @Value("${ssl.key-store-password: 0}");
  private int keyStorePassword;

  private RestTemplate restTemplate;

  @PostConstruct
	public void afterConsutruct() throws Exception {
		restTemplate();
		restTemplate.getMessageConverters().add(0,
				new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}

	private HttpHeaders getPCFClientHeaders() {
		HttpHeaders headers = new HttpHeaders();
		return headers;
	}


	public void restTemplate() throws Exception {
		
		if (!StringUtils.isBlank(proxyHost) && proxyPort != 0) {
			logger.info("Found proxy configuration.");
			
			KeyStore trustStore = KeyStore.getInstance("JKS");
			try (InputStream trustStoreStream = getClass().getClassLoader().getResourceAsStream(keystore)) {
				trustStore.load(trustStoreStream, keystorepassword.toCharArray());
			}
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(trustStore, null).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					NoopHostnameVerifier.INSTANCE);

			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			logger.info("Setting up proxy host [{}] and port [{}]", proxyHost, proxyPort);
			
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.setProxy(proxy)
					.build();
			logger.info("Setting up JKS for SSL Handshaking.");
//			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			factory.setConnectTimeout(10000); // connection timeout
			factory.setReadTimeout(10000);

			restTemplate = new RestTemplate(factory);
		} else {
			logger.info("No proxy host configured.");
			restTemplate = new RestTemplate();
		}
	}
  public <K, V> V callPostService( String serviceUrl, HttpEntity<MultiValueMap<String, String>> httpEntity, Class<V> returnClassType)  {

		V responseObj = null;
		ResponseEntity<V> response = null;
		try {
		 response = restTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, returnClassType);	
		 responseObj = (V) response.getBody();
		 return responseObj;
		}catch(ResourceAccessException e) {
			e.printStackTrace(); 
			logger.error(e.getMessage()+ "\n Stack Trace"+ExceptionUtils.getStackTrace(e));
		}catch (Exception e) {
			e.printStackTrace(); 
			logger.error(e.getMessage()+ "\n Stack Trace"+ExceptionUtils.getStackTrace(e));
		}
		return null;
	}
	
	public <K, V> V callGetService( String serviceUrl, HttpEntity<MultiValueMap<String, String>> httpEntity, Class<V> returnClassType)  {

		V responseObj = null;
		ResponseEntity<V> response = null;
		response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, returnClassType);	
		responseObj = (V) response.getBody();
		return responseObj;
	}
  }
