package org.ranther.log;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;

public class XMLOps {

	private Element node;

	public XMLOps(Element node) {
		this.node = node;
	}

	public List<Element> getAllNodes() {
		return getAllNodes(this.node);
	}

	public List<Element> getAllNodes(Element node) {
		List<Element> elements = new ArrayList<>();
		List<Element> listelements = node.elements();
		elements.addAll(listelements);
		for (Element element : listelements) {
			elements.addAll(getAllNodes(element));
		}
		return elements;
	}

}
