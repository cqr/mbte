package org.mbet.android;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelper {
	
	private static DefaultHandler myXMLHandler = new DefaultHandler(){
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
				} else if (localName.equals("channel")){
					videoChannel.save();
				}
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
	
	
	public static void parseXML(){
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
