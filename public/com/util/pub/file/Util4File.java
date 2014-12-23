package com.util.pub.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;

import com.util.pub.container.ChangableString;
import com.util.pub.container.TwoLong;
import com.util.pub.content.ContentDealing;


/**
 * 文件操作相关工具类
 * @author hengzai 2013-7-16
 */

public class Util4File {
	static int FileSize = 5 * 1024;
	
	private static HashMap _lockedFiles = new HashMap();
	
	/**
	 * 路径处理 
	 */
	
	//检查一个目录是否存在，不存在则创建 dir为目录名 不包含目录后面的文件
	public static void creatDir (String dir) {
		//dir指传进的目录
		File file = new File(dir);	
		
		//判断是否存在 
		if(!file.exists()) {
			//创建目录
			file.mkdirs();
		}
		
		return;
	}
	
	//一个文件名如果包含路径，但目录可能不存在，该方法自动创建目录
	public static void creatFileDir(String in_fileAddr) {
		//替换转义符
		String fileAddr = ContentDealing.replaceChar(in_fileAddr, '\\', '/');
		
		//从尾部寻找分割符
		int p = fileAddr.lastIndexOf('/');
		//把文件名和目录名分离
		String dir = fileAddr.substring(0, p);
		
		creatDir(dir);
	}
	
	//判断是否存在此文件
	public static String defineFileDir(String in_fileAddr) {
		//替换转义符
		String fileAddr = ContentDealing.replaceChar(in_fileAddr, '\\', '/');
		
		//dir指传进的目录
		File file = new File(fileAddr);	
		
		//判断目录是否存在 
		if(file.exists()) {
			return fileAddr;
		} else {
			return null;
		}
	}
	
	/**
	 * 文本处理
	 */
	
	//按行读文本文件 采用字符流
	public static int readFileByLine(String fileAddr, ChangableString cString) throws IOException{
		int i;
		File in_file = new File(fileAddr);
		i = 0;
		
		//如果文件不存在
		if(!in_file.exists()) {
			//行数为0
			cString.l = 0;
			return -1;	
		}
		
		//字符读取流 向上抛出IOException
		BufferedReader in = new BufferedReader(new FileReader(in_file));
		
		//将读取一行的代码放入可变字符串容器中 cString的sa能够根据读入的代码进行自动扩充
		while((cString.sa[i] = in.readLine()) != null) {
			//判断是否递增的行数超出容器长度，超出则进行扩充
			i++;
			if( i > cString.cp) {
				cString.extend();
			}
		}
		
		//将最终的行数写入l中
		cString.l = i;
		//关闭字符读取流
		in.close();
		//返回最终行数
		return i;
	}
	
	//按行写文本文件 采用字符流
	public static int writeFileByLine(String fileAddr, ArrayList<String> sList) throws IOException {
		int i;
		//生成字节数组 ASCII码表中 13代表换行， 10代表回车 
		byte[] ln = {13, 10};
		//组成下一行字符串
		String LN = new String(ln);
		//通过文件路径读取文件
		File out_file = new File(fileAddr);
		//字符输出流 抛出IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file));
		//进行写入动作
		for(i = 0; i < sList.size(); i++) {
			out.write(sList.get(i) + LN);
		}
		//关闭字符输出流
		out.close();
		//返回总行值
		return i;
	}
	
	//将一个字符串(单行)写入文本 采用字符流 上面的方法输入参数为多行
	public static void writeString2File(String fileAddr, String s) throws IOException {
		//通过文件路径读取文件
		File out_file = new File(fileAddr);
		//设置字符输出流 抛出IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file));
		//进行写入动作
		out.write(s);
		//关闭字符输出流
		out.close();
		
		return;
	}
	
	//将一个字符串(单行)写入文本 采用字符流 上面的方法为删除之前内容重写 此方法为在原有基础上续写
	public static void addString2File(String fileAddr, String s) throws IOException {
		//通过文件路径读取文件
		File out_file = new File(fileAddr);
		//设置字符输出流 设置续写属性 抛出IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file, true));
		//进行写入动作
		out.write(s);
		//关闭字符输出流
		out.close();
		
		return;
	}
	
	//将文件读入到一个字符串中 采用字符流
	public static String readFile2string(String fileAddr) throws IOException {
		//通过文件路径读取文件
		File in_file = new File(fileAddr);
		//设置字符输入流 抛出IOException异常
		BufferedReader in = new BufferedReader(new FileReader(in_file));
		//比StringBuffer更快但不安全
		StringBuilder sBuilder = new StringBuilder();
		String s;
		
		//按行读取并逐行添加到sBuilder中
		while ((s = in.readLine()) != null) {
			sBuilder.append(s);
		}
		
		s = sBuilder.substring(0);
		//关闭流
		in.close();
		//返回数组
		return s;
		
	}
	
	//将byte[]写入文本中  用于保存文件 重载1
	public static int writeBytes2File(String fileAddr, byte[] b) {
		if(fileAddr == null) {
			return -2;
		}
		
		File out_file = new File(fileAddr);
		
		return writeBytes2File(out_file, b);
	}
	
	//将byte[]写入文本中  采用字节流 用于保存文件 重载2
	public static int writeBytes2File(File out_file, byte[] b) {
		//字节数组输入流
		ByteArrayInputStream bArrayInStm = null;
		FileOutputStream fileOutStm = null;
		//设置每次读取的固定长度的字节数组 不宜过大 过大影响运行速度
		byte[] buf = new byte[1024];
		
		int n = 0, sz = 0;
		
		try {
			
			bArrayInStm = new ByteArrayInputStream(b);
			fileOutStm = new FileOutputStream(out_file);
			
			//读取下一部分字节 容量为字节buf的容量或者是小于buf的容量read()方法返回的n值 到末尾时返回-1
			while((n = bArrayInStm.read(buf)) > 0) {
				sz = sz + n;
				//将byte数组的第0个到第n个字节写入输出流中 最大长度为buf的数组长度
				fileOutStm.write(buf, 0, n);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	//以byte[]形式读取文本 重载方法1
	public static byte[] readFile2Bytes(String fileAddr) {
		if(fileAddr == null) {
			return null;
		}
		
		File file = new File(fileAddr);
		
		if(!file.exists()) {
			return null;
		}
		
		return readFile2Bytes(file);
	}
	
	//以byte[]形式读取文本 采用字节流 重载方法2
	public static byte[] readFile2Bytes(File file) {
		int c;
		int iLen;
		
		//字节流
		FileInputStream fileInStm = null;
		ByteArrayOutputStream bArrayOutStm = null;
		
		//设置每次读取的固定长度的字节数组 不宜过大 过大影响运行速度
		byte[] buf = new byte[FileSize];
		
		int n = 0, sz = 0;
		
		try {
			fileInStm = new FileInputStream(file);
			bArrayOutStm = new ByteArrayOutputStream();
			iLen = 0;
			
			//读取下一部分字节 容量为字节buf的容量或者是小于buf的容量read()方法返回的n值 到末尾时返回-1
			while ((n = fileInStm.read(buf)) > 0) {
				sz = sz + n;
				bArrayOutStm.write(buf, 0, n);
			}
			
			//关闭流
			if(fileInStm != null) {
				fileInStm.close();
			}
			
			if(bArrayOutStm != null) {
				bArrayOutStm.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		//组建并返回byte[]
		byte[] bts = null;
		bts = bArrayOutStm.toByteArray();
		return bts;
	}
	
	/**
	 * 下载相关
	 */
	
	//从Http服务器下载文件，并保存到本地文件中
	public static int dlHttpFile(String urlAddr, String localName) {
		//设置每次读取的固定长度的字节数组 不宜过大 过大影响运行速度
		byte[] buf = new byte[1024];
		URL url = null;
		
		//利用DataInputStream读取文件中的内容 区别于BufferedInputStream 此流多出读取各种数据类型的方法
		DataInputStream inStm = null;
		ByteArrayOutputStream bArrayOutStm = new ByteArrayOutputStream();
		
		try {
			//放入url地址
			url = new URL(urlAddr);
			
			//设置输入流并打开
			inStm = new DataInputStream(url.openStream());
			int n = 0;
			
			//遍历并读取所有流数据
			while ((n = inStm.read(buf)) > 0) {
				//将输入流的数据写入byte[]输出流中 
				bArrayOutStm.write(buf, 0, n);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		//将输出流的数据放入byte[]中
		byte[] fileData = bArrayOutStm.toByteArray();
		//将byte[]中的结果写入文本
		int result = writeBytes2File(localName, fileData);
		if(result < 0) {
			//返回负数 即错误结果
			return result;
		}
		
		//返回结果长度
		return fileData.length;
	}
	
	//运行exe文件 
	public static int executeEXE(String in_fileAddr) {
		String fileAddr = defineFileDir(in_fileAddr);
		if(fileAddr != null) {
			//运行文件
			Runtime rt;
			rt = Runtime.getRuntime();
			try {
				rt.exec(in_fileAddr);
			} catch (IOException e) {
				e.printStackTrace();
				//运行失败
				return -1;
			}
			return 0;
		} else {
			//运行失败
			return -1;
		}
	}
	
	//静态类 FLP
	static	class FLP {
		FileChannel channel;
		FileLock lock;
	}
	
	//对文件加锁 能使用户以独占方式访问某个文件
	public static int fileLock(String fileAddr) {
		
		RandomAccessFile visit;
		FLP flp = null;
		//检查HashMao中是否有此对象判断是否已被加锁 返回null则无 
		flp = (FLP)_lockedFiles.get(fileAddr);
		if(flp != null) {
			//该文件已被加锁
			return -5;
		}
		flp = new FLP();
		
		//RandomAccessFile可以访问文件的任何地方 rw表示文件不存在可创建并可读写文件
		try {
			visit = new RandomAccessFile(fileAddr, "rw");
			if(visit == null) {
				return -3;
			}
			//
			flp.channel = visit.getChannel();
			flp.lock = flp.channel.tryLock();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				if(flp.channel != null) {
					//关闭通道
					flp.channel.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		//判断锁定是否生效 不生效就关闭通道退出 生效则记录之后关闭通道
		if(flp.lock == null) {
			if(flp.channel != null) {
				//关闭通道
				try {
					flp.channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return -2;
			}
		} else if(flp.lock.isValid()) {
			//将对象放入HashMap中记录状态
			_lockedFiles.put(fileAddr, flp);
			return 0;
		}
		try {
			if(flp.channel != null) {
				//关闭通道
				flp.channel.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int fileUnLock(String fileAddr) {
		FLP flp = null;
		//判断是否加锁
		flp = (FLP) _lockedFiles.get(fileAddr);
		if(flp == null) {
			//没加锁
			return -5;
		}
		//已经加锁
		try {
			if(flp != null) {
				//释放锁定
				flp.lock.release();
			}
			if(flp.channel != null) {
				//关闭通道
				flp.channel.close();
			}
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			//把该锁定文件的记录删去
			_lockedFiles.remove(fileAddr);
		}
	}
	
	public static boolean defineFileIsLock(String fileAddr) {
		FLP flp = null;
		flp = (FLP) _lockedFiles.get(fileAddr);
		if(flp == null) {
			return false;
		}
		
		//判断flp的锁定静态类是否有值
		if(flp.lock.isValid()) {
			return true;
		}
		return false;
	}
	
	public static int fileUnLockAndDel(String fileAddr) {
		FLP flp = null;
		//检查HashMap是否有记录
		flp = (FLP) _lockedFiles.get(fileAddr);
		//无锁则返回
		if(flp == null) {
			return -5;
		}
		
		try {
			//检查是否有锁
			if(flp.lock != null) {
				//文件解锁
				flp.lock.release();
			}
			if(flp.channel != null) {
				//关闭通道
				flp.channel.close();
			}
			
			//删除文件
			File file = new File(fileAddr);
			file.delete();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			//从HashMap中删除对应记录
			_lockedFiles.remove(fileAddr);
		}
	}
	
	//删除文件夹
	public static int deleteDirectory(String fileAddr) {
		File file = new File(fileAddr);
		if(file.exists()) {
			File[] children = file.listFiles();
			//遍历子项
			for(int i = 0; i < children.length; i++) {
				//如果子项是否是目录 是则删除目录里面的文件和目录 不是则删除文件
				if(children[i].isDirectory()) {
					//迭代该方法
					deleteDirectory(fileAddr + "/" + children[i].getName());
				} else {
					children[i].delete();
				}
			} 
			file.delete();
			return 0;
		} else {
			//文件夹不存在
			return -1;
		}
	}
	
	//得到一个文本文件的行数 重载方法1
	public static long getFileLines(String fileAddr) {
		if(fileAddr == null) {
			return -1;
		}
		
		File file = new File(fileAddr);
		return getFileLines(file);
	}
	
	//得到一个文本文件的行数 重载方法2
	public static long getFileLines(File file) {
		long i;
		i = 0;
		//判断文件是否存在
		if(!file.exists()) {
			i = 0;
			return -1;
		}
		//设置缓冲字符流
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String d = null;
			//计算行数
			while((d = bf.readLine()) != null) {
				i++;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
	}
	
	//同时返回文件大小和文件长度两个对象 将两个对象放在一个对象中 重载方法1
	public static TwoLong getFileSizeAndLineNo(String fileAddr) {
		if(fileAddr == null) {
			return null;
		}
		
		File file = new File(fileAddr);
		
		return getFileSizeAndLineNo(file);
	}
	
	//同时返回文件大小和文件长度两个对象 将两个对象放在一个对象中 重载方法2
	public static TwoLong getFileSizeAndLineNo(File file) {
		if(file == null || !file.exists()) {
			return null;
		}
		
		TwoLong tLong = new TwoLong();
		//文件大小
		tLong.setL1(file.length());
		//文件总行数
		tLong.setL2(getFileLines(file));
		
		return tLong;
	}
	
	//打开所有文件
}
	