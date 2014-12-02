package com.coe.wms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

public class XmlUtil {
	private static final Logger logger = Logger.getLogger(XmlUtil.class);

	/**
	 * xml 转对象
	 * 
	 * @param xml
	 * @param c
	 * @return
	 */
	public static Object toObject(String xml, Class c) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller um = context.createUnmarshaller();
			return um.unmarshal(inputStream);
		} catch (JAXBException e) {
			logger.error("xml :" + xml + "  转对象 异常:" + e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 对象转xml
	 * 
	 * @param c
	 * @param object
	 * @return
	 */
	public static String toXml(Class c, Object object) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Marshaller m = context.createMarshaller();
			m.marshal(object, output);
			return output.toString();
		} catch (JAXBException e) {
			logger.error("对象转xml 异常:" + e.getMessage());
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> String objToXmlString(T obj) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(obj.getClass());
		Marshaller m = jc.createMarshaller();
		m.setProperty("jaxb.formatted.output", true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Result result = new StreamResult(out);
		m.marshal(obj, result);
		// 转码
		byte[] bystr = out.toByteArray();
		String str = "";
		try {
			str = new String(bystr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer(str.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", ""));
		return sb.toString();
	}
}
