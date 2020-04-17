package com.getapizi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ApiToken {
	private String encoded;
	
	public ApiToken(String token) {
		String auth = token + ':';
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		this.encoded = new String(encodedAuth);
	}

	public ApiToken(String user, String password) {
		String auth = user + ':' + password;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		this.encoded = new String(encodedAuth);
	}

	public ApiXmlResource xml(String url) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
		return new ApiXmlResource(getInputStream(url));
	}

	public <T> T xml(String url, Class<T> klazz) throws JAXBException, MalformedURLException, IOException {
		return ApiXmlResource.instantiate(getInputStream(url), klazz);
	}

	public <T> T json(String url, Class<T> klazz) throws IOException {
		return ApiJsonResource.instantiate(getInputStream(url), klazz);
	}

	private InputStream getInputStream(String url) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("Authorization", "Basic " + encoded);
		return connection.getInputStream();
	}
}
