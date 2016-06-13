package com.puxtech.weipan.parser;

import org.w3c.dom.Element;

/**
 * <b>Description:</b>
 * <p>
 * TODO
 * </p>

 */
public class BaseParser {

	protected String getString(Element e, String tag) {
		try{
			if (((Element) (e.getElementsByTagName(tag).item(0))).getFirstChild() != null) {
				return ((Element) (e.getElementsByTagName(tag).item(0)))
						.getFirstChild().getNodeValue();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
		
	}
	
	protected Element getElement(Element parent, String elementTag){
		return (Element) parent.getElementsByTagName(elementTag).item(0);
	}

}
