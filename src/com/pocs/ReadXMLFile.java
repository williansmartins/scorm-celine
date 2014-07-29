package com.pocs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {

	static List<String> titles = new ArrayList();
	static List<String> identifierrefs = new ArrayList();
	static String schemaversionString = "";
	static ScormBase scormbase = new ScormBase();

	public static void main(String argv[]) {
		readXML();
	}

	public static ScormBase readXML() {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean schemaversion = false;
				boolean title = false;
				boolean item = false;
				boolean bsalary = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("schemaversion")) {
						schemaversion = true;
					}

					if (qName.equalsIgnoreCase("title")) {
						title = true;
					}

					if (qName.equalsIgnoreCase("item")
							&& attributes.getIndex("identifierref") >= 0) {
						// System.out.println("===========" +
						// attributes.getValue( attributes.getIndex(
						// "identifierref" )) );
						identifierrefs.add(attributes.getValue(attributes
								.getIndex("identifierref")));
						item = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					// System.out.println("End Element :" + qName);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (schemaversion) {
						// System.out.println(">>>>>>>>>>>>>>>>> schemaversion : "
						// + new String(ch, start, length));
						schemaversionString = new String(ch, start, length);
						schemaversion = false;
					}

					if (title) {
						System.out.println("<<<<<<<<<<<<<<<<<<< title : "
								+ new String(ch, start, length));
						titles.add(new String(ch, start, length));
						title = false;
					}

					if (item) {
						// System.out.println("Nick Name : " + new String(ch,
						// start, length));
						item = false;
					}

				}
				

			};

			saxParser.parse("c:\\outputzip\\imsmanifest.xml", handler);

			titles.remove(0);
			System.out.println("Titles: " + titles.size());
			System.out.println("Identifierrefs: " + identifierrefs.size());
			System.out.println("Version: " + schemaversionString);
			
			scormbase.identifierrefs = identifierrefs;
			scormbase.titles = titles;
			scormbase.version = schemaversionString;
			return scormbase;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scormbase;
	}

}