package com.coe.wms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	public static <T> Object toObject(String xml, Class<T> c) {
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
	public static <T> String toXml(T object) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
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
}
