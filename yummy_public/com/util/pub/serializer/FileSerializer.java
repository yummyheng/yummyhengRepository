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
 * 序列化与反序列化xml文件的相关类
 * @author hengzai 时间：2013年7月25日
 */

final public class FileSerializer {
	private static XStream Xstream = null;
	
	/**
	 * 序列化相关 
	 */
	
	//将对象序列化为文件类型
	public static void serializerToFileByX(Object o, String fileAddr) {
		byte[] b = serializerToFileByX(o);
		
	}
	
	//将对象序列化为byte[]类型
	public static byte[] serializerToFileByX(Object o) {
		String s = serializerToStringByX(o);
		return s.getBytes();
		
	}
	
	//将对象序列化为String类型
	public static String serializerToStringByX(Object o) {
		if(Xstream == null) {
			Xstream = new XStream();
		}
		Byte[] b = null;
		String s = null;
		
		s = Xstream.toXML(o);
		
		return s;
	}
	
	//序列化保存某对象到指定文件
	public static void serializerAndSave(Object o, String fileAddr) {
		
		try {
			//初始化参数
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
	 * 反序列化相关 
	 */
	
	//通过文件路径反序列化为对象
	public static Object unSerializerFileByX(String fileAddr) {
		byte[] b = Util4File.readFile2Bytes(fileAddr);
		Object o = unSerializerByX(b);
		return o;
	}
	
	//通过读进的字节反序列化为对象
	public static Object unSerializerByX(byte[] b) {
		String content = new String(b);
		return unSerializerByX(content);
	}
	
	//将传进来的字符串反序列化为对象
	public static Object unSerializerByX(String content) {
		//初始化参数
		if(Xstream == null) {
			Xstream = new XStream();
		}
		Object o = null;
		
		//将xml上的内容反序列化为对象
		o = Xstream.fromXML(content);
		
		 return o;
	}
	
	//将指定文件反序列化为对象
	public static Object unSerializerFromFile(String fileAddr) throws Exception {
		//初始化参数
		Object o = null;
		ObjectInputStream in = null;
		
		try {
			//从ObjectInputStream中读取对象
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
	 * 其它
	 */
	
	//通过序列化-反序列化的方法，深度克隆任意可序列化的对象
	public static <T> T clonseObj(T o) {
		T t = null;
		
		if(Xstream == null) {
			Xstream = new XStream();
		}
		
		//序列化为字符串
		String s = Xstream.toXML(o);
		//反序列化为对象
		t = (T) Xstream.fromXML(s);
		
		return t; 
	}
}
