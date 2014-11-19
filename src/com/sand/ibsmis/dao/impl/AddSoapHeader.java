package com.sand.ibsmis.dao.impl;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sand.ibsmis.util.IBSMisCommUtils;

public class AddSoapHeader extends AbstractSoapInterceptor {
	public static final String xml_namespaceUR_att = "http://service.com";  
	public static final String xml_header_el = "soap:header";  
	public static final String xml_authentication_el = "auth:authentication";  
	public static final String xml_eid_el = "eid";  
	public static final String xml_sk_el = "sk";  
  
    public AddSoapHeader() {  
        super(Phase.WRITE);  
    }  
  
    public void handleMessage(SoapMessage message) throws Fault {  
        String eid =IBSMisCommUtils.getProperties("eid");  
        String sk = IBSMisCommUtils.getProperties("sk");
//    	String eid = "0001";
//    	String sk = "c23b39b6dc2941f8ab320ec56d31d7f9";
  
        QName qname = new QName("RequestSOAPHeader");
  
        Document doc = (Document) DOMUtils.createDocument();  
        Element root = doc.createElement(xml_header_el);  
        Element eEid = doc.createElement(xml_eid_el);  
        eEid.setTextContent(eid);  
        Element eSk = doc.createElement(xml_sk_el);  
        eSk.setTextContent(sk);  
        Element child = doc.createElementNS(xml_namespaceUR_att,  xml_authentication_el);  
        child.appendChild(eEid);  
        child.appendChild(eSk);  
        root.appendChild(child);  
        XMLUtils.printDOM(root);
        SoapHeader head = new SoapHeader(qname, root);  
        List<Header> headers = message.getHeaders();  
        headers.add(head);  
    }  
}