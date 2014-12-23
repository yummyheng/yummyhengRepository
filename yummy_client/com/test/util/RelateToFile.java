package com.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

public class RelateToFile {
	public static <T> void WritingFile(T clz, String file_path) {
		XStream xstream = new XStream();
		try {
			String out = xstream.toXML(clz);
			String path = file_path + "/" + clz.getClass().getSimpleName();
			writeStringToFile(path, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> Object ReadingFile(String file_path) {
		try {
			XStream xstream = new XStream();
			String file;
			file = readFileToString(file_path);
			Object obj = xstream.fromXML(file);
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将一个字符串写入文本文件
	 * @param pFileName
	 * @param S
	 * @return
	 * @throws IOException
	 */
	public static void writeStringToFile(String FileName, String S) throws IOException{
		File outFile = new File(FileName); 
		BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		out.write(S);
		out.close();
		return ;
	}
	
	/**
	 * 将文件读入到一个字符串中
	 * @param pFileName
	 * @param S
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String FileName) throws IOException{
		File inFile = new File(FileName); 
		BufferedReader in = new BufferedReader(new FileReader(inFile));
		StringBuilder sb = new StringBuilder();
		String s;
		while (( s = in.readLine()) != null){
			sb.append(s);
		}
		s = sb.substring(0);
		in.close();
		return s;
	}
}
