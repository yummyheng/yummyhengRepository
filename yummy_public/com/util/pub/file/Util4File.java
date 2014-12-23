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
 * �ļ�������ع�����
 * @author hengzai 2013-7-16
 */

public class Util4File {
	static int FileSize = 5 * 1024;
	
	private static HashMap _lockedFiles = new HashMap();
	
	/**
	 * ·������ 
	 */
	
	//���һ��Ŀ¼�Ƿ���ڣ��������򴴽� dirΪĿ¼�� ������Ŀ¼������ļ�
	public static void creatDir (String dir) {
		//dirָ������Ŀ¼
		File file = new File(dir);	
		
		//�ж��Ƿ���� 
		if(!file.exists()) {
			//����Ŀ¼
			file.mkdirs();
		}
		
		return;
	}
	
	//һ���ļ����������·������Ŀ¼���ܲ����ڣ��÷����Զ�����Ŀ¼
	public static void creatFileDir(String in_fileAddr) {
		//�滻ת���
		String fileAddr = ContentDealing.replaceChar(in_fileAddr, '\\', '/');
		
		//��β��Ѱ�ҷָ��
		int p = fileAddr.lastIndexOf('/');
		//���ļ�����Ŀ¼������
		String dir = fileAddr.substring(0, p);
		
		creatDir(dir);
	}
	
	//�ж��Ƿ���ڴ��ļ�
	public static String defineFileDir(String in_fileAddr) {
		//�滻ת���
		String fileAddr = ContentDealing.replaceChar(in_fileAddr, '\\', '/');
		
		//dirָ������Ŀ¼
		File file = new File(fileAddr);	
		
		//�ж�Ŀ¼�Ƿ���� 
		if(file.exists()) {
			return fileAddr;
		} else {
			return null;
		}
	}
	
	/**
	 * �ı�����
	 */
	
	//���ж��ı��ļ� �����ַ���
	public static int readFileByLine(String fileAddr, ChangableString cString) throws IOException{
		int i;
		File in_file = new File(fileAddr);
		i = 0;
		
		//����ļ�������
		if(!in_file.exists()) {
			//����Ϊ0
			cString.l = 0;
			return -1;	
		}
		
		//�ַ���ȡ�� �����׳�IOException
		BufferedReader in = new BufferedReader(new FileReader(in_file));
		
		//����ȡһ�еĴ������ɱ��ַ��������� cString��sa�ܹ����ݶ���Ĵ�������Զ�����
		while((cString.sa[i] = in.readLine()) != null) {
			//�ж��Ƿ���������������������ȣ��������������
			i++;
			if( i > cString.cp) {
				cString.extend();
			}
		}
		
		//�����յ�����д��l��
		cString.l = i;
		//�ر��ַ���ȡ��
		in.close();
		//������������
		return i;
	}
	
	//����д�ı��ļ� �����ַ���
	public static int writeFileByLine(String fileAddr, ArrayList<String> sList) throws IOException {
		int i;
		//�����ֽ����� ASCII����� 13�����У� 10����س� 
		byte[] ln = {13, 10};
		//�����һ���ַ���
		String LN = new String(ln);
		//ͨ���ļ�·����ȡ�ļ�
		File out_file = new File(fileAddr);
		//�ַ������ �׳�IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file));
		//����д�붯��
		for(i = 0; i < sList.size(); i++) {
			out.write(sList.get(i) + LN);
		}
		//�ر��ַ������
		out.close();
		//��������ֵ
		return i;
	}
	
	//��һ���ַ���(����)д���ı� �����ַ��� ����ķ����������Ϊ����
	public static void writeString2File(String fileAddr, String s) throws IOException {
		//ͨ���ļ�·����ȡ�ļ�
		File out_file = new File(fileAddr);
		//�����ַ������ �׳�IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file));
		//����д�붯��
		out.write(s);
		//�ر��ַ������
		out.close();
		
		return;
	}
	
	//��һ���ַ���(����)д���ı� �����ַ��� ����ķ���Ϊɾ��֮ǰ������д �˷���Ϊ��ԭ�л�������д
	public static void addString2File(String fileAddr, String s) throws IOException {
		//ͨ���ļ�·����ȡ�ļ�
		File out_file = new File(fileAddr);
		//�����ַ������ ������д���� �׳�IOException
		BufferedWriter out = new BufferedWriter(new FileWriter(out_file, true));
		//����д�붯��
		out.write(s);
		//�ر��ַ������
		out.close();
		
		return;
	}
	
	//���ļ����뵽һ���ַ����� �����ַ���
	public static String readFile2string(String fileAddr) throws IOException {
		//ͨ���ļ�·����ȡ�ļ�
		File in_file = new File(fileAddr);
		//�����ַ������� �׳�IOException�쳣
		BufferedReader in = new BufferedReader(new FileReader(in_file));
		//��StringBuffer���쵫����ȫ
		StringBuilder sBuilder = new StringBuilder();
		String s;
		
		//���ж�ȡ��������ӵ�sBuilder��
		while ((s = in.readLine()) != null) {
			sBuilder.append(s);
		}
		
		s = sBuilder.substring(0);
		//�ر���
		in.close();
		//��������
		return s;
		
	}
	
	//��byte[]д���ı���  ���ڱ����ļ� ����1
	public static int writeBytes2File(String fileAddr, byte[] b) {
		if(fileAddr == null) {
			return -2;
		}
		
		File out_file = new File(fileAddr);
		
		return writeBytes2File(out_file, b);
	}
	
	//��byte[]д���ı���  �����ֽ��� ���ڱ����ļ� ����2
	public static int writeBytes2File(File out_file, byte[] b) {
		//�ֽ�����������
		ByteArrayInputStream bArrayInStm = null;
		FileOutputStream fileOutStm = null;
		//����ÿ�ζ�ȡ�Ĺ̶����ȵ��ֽ����� ���˹��� ����Ӱ�������ٶ�
		byte[] buf = new byte[1024];
		
		int n = 0, sz = 0;
		
		try {
			
			bArrayInStm = new ByteArrayInputStream(b);
			fileOutStm = new FileOutputStream(out_file);
			
			//��ȡ��һ�����ֽ� ����Ϊ�ֽ�buf������������С��buf������read()�������ص�nֵ ��ĩβʱ����-1
			while((n = bArrayInStm.read(buf)) > 0) {
				sz = sz + n;
				//��byte����ĵ�0������n���ֽ�д��������� ��󳤶�Ϊbuf�����鳤��
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
	
	//��byte[]��ʽ��ȡ�ı� ���ط���1
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
	
	//��byte[]��ʽ��ȡ�ı� �����ֽ��� ���ط���2
	public static byte[] readFile2Bytes(File file) {
		int c;
		int iLen;
		
		//�ֽ���
		FileInputStream fileInStm = null;
		ByteArrayOutputStream bArrayOutStm = null;
		
		//����ÿ�ζ�ȡ�Ĺ̶����ȵ��ֽ����� ���˹��� ����Ӱ�������ٶ�
		byte[] buf = new byte[FileSize];
		
		int n = 0, sz = 0;
		
		try {
			fileInStm = new FileInputStream(file);
			bArrayOutStm = new ByteArrayOutputStream();
			iLen = 0;
			
			//��ȡ��һ�����ֽ� ����Ϊ�ֽ�buf������������С��buf������read()�������ص�nֵ ��ĩβʱ����-1
			while ((n = fileInStm.read(buf)) > 0) {
				sz = sz + n;
				bArrayOutStm.write(buf, 0, n);
			}
			
			//�ر���
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
		
		//�齨������byte[]
		byte[] bts = null;
		bts = bArrayOutStm.toByteArray();
		return bts;
	}
	
	/**
	 * �������
	 */
	
	//��Http�����������ļ��������浽�����ļ���
	public static int dlHttpFile(String urlAddr, String localName) {
		//����ÿ�ζ�ȡ�Ĺ̶����ȵ��ֽ����� ���˹��� ����Ӱ�������ٶ�
		byte[] buf = new byte[1024];
		URL url = null;
		
		//����DataInputStream��ȡ�ļ��е����� ������BufferedInputStream ���������ȡ�����������͵ķ���
		DataInputStream inStm = null;
		ByteArrayOutputStream bArrayOutStm = new ByteArrayOutputStream();
		
		try {
			//����url��ַ
			url = new URL(urlAddr);
			
			//��������������
			inStm = new DataInputStream(url.openStream());
			int n = 0;
			
			//��������ȡ����������
			while ((n = inStm.read(buf)) > 0) {
				//��������������д��byte[]������� 
				bArrayOutStm.write(buf, 0, n);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		//������������ݷ���byte[]��
		byte[] fileData = bArrayOutStm.toByteArray();
		//��byte[]�еĽ��д���ı�
		int result = writeBytes2File(localName, fileData);
		if(result < 0) {
			//���ظ��� ��������
			return result;
		}
		
		//���ؽ������
		return fileData.length;
	}
	
	//����exe�ļ� 
	public static int executeEXE(String in_fileAddr) {
		String fileAddr = defineFileDir(in_fileAddr);
		if(fileAddr != null) {
			//�����ļ�
			Runtime rt;
			rt = Runtime.getRuntime();
			try {
				rt.exec(in_fileAddr);
			} catch (IOException e) {
				e.printStackTrace();
				//����ʧ��
				return -1;
			}
			return 0;
		} else {
			//����ʧ��
			return -1;
		}
	}
	
	//��̬�� FLP
	static	class FLP {
		FileChannel channel;
		FileLock lock;
	}
	
	//���ļ����� ��ʹ�û��Զ�ռ��ʽ����ĳ���ļ�
	public static int fileLock(String fileAddr) {
		
		RandomAccessFile visit;
		FLP flp = null;
		//���HashMao���Ƿ��д˶����ж��Ƿ��ѱ����� ����null���� 
		flp = (FLP)_lockedFiles.get(fileAddr);
		if(flp != null) {
			//���ļ��ѱ�����
			return -5;
		}
		flp = new FLP();
		
		//RandomAccessFile���Է����ļ����κεط� rw��ʾ�ļ������ڿɴ������ɶ�д�ļ�
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
					//�ر�ͨ��
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
		
		//�ж������Ƿ���Ч ����Ч�͹ر�ͨ���˳� ��Ч���¼֮��ر�ͨ��
		if(flp.lock == null) {
			if(flp.channel != null) {
				//�ر�ͨ��
				try {
					flp.channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return -2;
			}
		} else if(flp.lock.isValid()) {
			//���������HashMap�м�¼״̬
			_lockedFiles.put(fileAddr, flp);
			return 0;
		}
		try {
			if(flp.channel != null) {
				//�ر�ͨ��
				flp.channel.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int fileUnLock(String fileAddr) {
		FLP flp = null;
		//�ж��Ƿ����
		flp = (FLP) _lockedFiles.get(fileAddr);
		if(flp == null) {
			//û����
			return -5;
		}
		//�Ѿ�����
		try {
			if(flp != null) {
				//�ͷ�����
				flp.lock.release();
			}
			if(flp.channel != null) {
				//�ر�ͨ��
				flp.channel.close();
			}
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			//�Ѹ������ļ��ļ�¼ɾȥ
			_lockedFiles.remove(fileAddr);
		}
	}
	
	public static boolean defineFileIsLock(String fileAddr) {
		FLP flp = null;
		flp = (FLP) _lockedFiles.get(fileAddr);
		if(flp == null) {
			return false;
		}
		
		//�ж�flp��������̬���Ƿ���ֵ
		if(flp.lock.isValid()) {
			return true;
		}
		return false;
	}
	
	public static int fileUnLockAndDel(String fileAddr) {
		FLP flp = null;
		//���HashMap�Ƿ��м�¼
		flp = (FLP) _lockedFiles.get(fileAddr);
		//�����򷵻�
		if(flp == null) {
			return -5;
		}
		
		try {
			//����Ƿ�����
			if(flp.lock != null) {
				//�ļ�����
				flp.lock.release();
			}
			if(flp.channel != null) {
				//�ر�ͨ��
				flp.channel.close();
			}
			
			//ɾ���ļ�
			File file = new File(fileAddr);
			file.delete();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			//��HashMap��ɾ����Ӧ��¼
			_lockedFiles.remove(fileAddr);
		}
	}
	
	//ɾ���ļ���
	public static int deleteDirectory(String fileAddr) {
		File file = new File(fileAddr);
		if(file.exists()) {
			File[] children = file.listFiles();
			//��������
			for(int i = 0; i < children.length; i++) {
				//��������Ƿ���Ŀ¼ ����ɾ��Ŀ¼������ļ���Ŀ¼ ������ɾ���ļ�
				if(children[i].isDirectory()) {
					//�����÷���
					deleteDirectory(fileAddr + "/" + children[i].getName());
				} else {
					children[i].delete();
				}
			} 
			file.delete();
			return 0;
		} else {
			//�ļ��в�����
			return -1;
		}
	}
	
	//�õ�һ���ı��ļ������� ���ط���1
	public static long getFileLines(String fileAddr) {
		if(fileAddr == null) {
			return -1;
		}
		
		File file = new File(fileAddr);
		return getFileLines(file);
	}
	
	//�õ�һ���ı��ļ������� ���ط���2
	public static long getFileLines(File file) {
		long i;
		i = 0;
		//�ж��ļ��Ƿ����
		if(!file.exists()) {
			i = 0;
			return -1;
		}
		//���û����ַ���
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String d = null;
			//��������
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
	
	//ͬʱ�����ļ���С���ļ������������� �������������һ�������� ���ط���1
	public static TwoLong getFileSizeAndLineNo(String fileAddr) {
		if(fileAddr == null) {
			return null;
		}
		
		File file = new File(fileAddr);
		
		return getFileSizeAndLineNo(file);
	}
	
	//ͬʱ�����ļ���С���ļ������������� �������������һ�������� ���ط���2
	public static TwoLong getFileSizeAndLineNo(File file) {
		if(file == null || !file.exists()) {
			return null;
		}
		
		TwoLong tLong = new TwoLong();
		//�ļ���С
		tLong.setL1(file.length());
		//�ļ�������
		tLong.setL2(getFileLines(file));
		
		return tLong;
	}
	
	//�������ļ�
}
	