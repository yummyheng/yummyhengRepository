package com.util.pub.serializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import com.util.pub.file.Util4File;

/**
 * ���л��뷴���л�xml�ļ��������
 * @author hengzai ʱ�䣺2013��7��25��
 */

final public class FileSerializer {
	private static XStream Xstream = null;
	
	/**
	 * ���л���� 
	 */
	
	//���������л�Ϊ�ļ�����
	public static void serializerToFileByX(Object o, String fileAddr) {
		byte[] b = serializerToFileByX(o);
		
	}
	
	//���������л�Ϊbyte[]����
	public static byte[] serializerToFileByX(Object o) {
		String s = serializerToStringByX(o);
		return s.getBytes();
		
	}
	
	//���������л�ΪString����
	public static String serializerToStringByX(Object o) {
		if(Xstream == null) {
			Xstream = new XStream();
		}
		Byte[] b = null;
		String s = null;
		
		s = Xstream.toXML(o);
		
		return s;
	}
	
	//���л�����ĳ����ָ���ļ�
	public static void serializerAndSave(Object o, String fileAddr) {
		
		try {
			//��ʼ������
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileAddr));
			
			out.writeObject(o);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �����л���� 
	 */
	
	//ͨ���ļ�·�������л�Ϊ����
	public static Object unSerializerFileByX(String fileAddr) {
		byte[] b = Util4File.readFile2Bytes(fileAddr);
		Object o = unSerializerByX(b);
		return o;
	}
	
	//ͨ���������ֽڷ����л�Ϊ����
	public static Object unSerializerByX(byte[] b) {
		String content = new String(b);
		return unSerializerByX(content);
	}
	
	//�����������ַ��������л�Ϊ����
	public static Object unSerializerByX(String content) {
		//��ʼ������
		if(Xstream == null) {
			Xstream = new XStream();
		}
		Object o = null;
		
		//��xml�ϵ����ݷ����л�Ϊ����
		o = Xstream.fromXML(content);
		
		 return o;
	}
	
	//��ָ���ļ������л�Ϊ����
	public static Object unSerializerFromFile(String fileAddr) throws Exception {
		//��ʼ������
		Object o = null;
		ObjectInputStream in = null;
		
		try {
			//��ObjectInputStream�ж�ȡ����
			in = new ObjectInputStream(new FileInputStream(fileAddr));
			o = in.readObject();
		} catch (Exception e) {
			if(in != null) {
				in.close();
			}
			throw e;
		} 
		
		in.close();
		
		return o;
	}
	
	/**
	 * ����
	 */
	
	//ͨ�����л�-�����л��ķ�������ȿ�¡��������л��Ķ���
	public static <T> T clonseObj(T o) {
		T t = null;
		
		if(Xstream == null) {
			Xstream = new XStream();
		}
		
		//���л�Ϊ�ַ���
		String s = Xstream.toXML(o);
		//�����л�Ϊ����
		t = (T) Xstream.fromXML(s);
		
		return t; 
	}
}
