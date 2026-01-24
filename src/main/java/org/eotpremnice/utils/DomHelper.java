package org.eotpremnice.utils;

import lombok.NoArgsConstructor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

@NoArgsConstructor
public final class DomHelper {

    public static Document newDocument() throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        return f.newDocumentBuilder().newDocument();
    }

    public static Element el(Document doc, String ns, String prefix, String localName) {
        Element e = doc.createElementNS(ns, localName);
        e.setPrefix(prefix);
        return e;
    }

    public static Element elText(Document doc, String ns, String prefix, String localName, String text) {
        Element e = el(doc, ns, prefix, localName);
        if (text != null) e.setTextContent(text);
        return e;
    }

}
