package org.mbet.android;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelper {
	
	private static class ChannelHandler extends DefaultHandler{
		
		Boolean currentElement = false;
		
		String currentValue;
		VideoChannel videoChannel;
		VideoItem videoItem;
		
		@Override
		public void startElement(String uri, String localName,
				String qName, Attributes attributes) {
			currentElement = true;
			
			if (localName.equals("channel")) {
				videoChannel = new VideoChannel();
			} else if (localName.equals("item")) {
				videoItem = videoChannel.addItem();
			}
		}
		
		@Override
		public void endElement(String uri, String localName,
				String qName) {
			currentElement = false;
			
			if (videoItem != null){
				if (localName.equals("title")) {
					videoItem.setTitle(currentValue);
				} else if (localName.equals("link")) {
					videoItem.setLink(currentValue);
				} else if (localName.equals("thumbnail")) {
					videoItem.setThumbnail(currentValue);
				} else if (localName.equals("item")){
					videoItem = null;
				}
			}
			
			if (localName.equals("channel")){
				videoChannel.save();
			}
			
		}
		
		@Override
		public void characters(char[] characters, int start, int length){
			if (currentElement) {
				currentValue = new String(characters, start, length);
				currentElement = false;
			}
		}
		
	};
	
	
	public static void parseXML(InputStream xmlStream){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			xmlReader.setContentHandler(new ChannelHandler());
			xmlReader.parse(new InputSource(xmlStream));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void parseXML(URL xmlUrl){
		try{
			parseXML(xmlUrl.openStream());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void parseXML(String xmlUrl){
		try {
			parseXML(new URL(xmlUrl));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
