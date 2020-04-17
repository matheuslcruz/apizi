package com.getapizi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ApiXmlResource {
	private Document document;

	public static <T> T instantiate(String url, Class<T> klazz) throws MalformedURLException, JAXBException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		return instantiate(connection.getInputStream(), klazz);
	}

	public static <T> T instantiate(InputStream inputStream, Class<T> klazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(klazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Object obj = jaxbUnmarshaller.unmarshal(inputStream);
		T response = (T) klazz.cast(obj);
		return response;
	}
	
	public ApiXmlResource(String url) throws MalformedURLException, SAXException, IOException, ParserConfigurationException {
		this(new URL(url).openConnection().getInputStream());
	}

	public ApiXmlResource(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    document = builder.parse(inputStream);
	}

	public String one(String xpath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		NodeList nodes = getNodeList(xpath);
		
		switch (nodes.getLength()) {
		case 0:
			return null;
		case 1:
			return nodes.item(0).getTextContent();
		default:
			throw new XPathExpressionException("xPath returned more then one node.");
		}
	}

	public String[] many(String xpath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		NodeList nodes = getNodeList(xpath);
		String checkIds[] = new String[nodes.getLength()];
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			checkIds[i] = node.getTextContent();
		}
		
		return checkIds;
	}

	private NodeList getNodeList(String xpath) throws XPathExpressionException {
	    XPathFactory pathFactory = XPathFactory.newInstance();
	    XPath path = pathFactory.newXPath();
	    XPathExpression expression;
	    expression = path.compile(xpath);
		NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
		return nodeList;
	}

}
