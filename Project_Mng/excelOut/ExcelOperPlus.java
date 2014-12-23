package excelOut;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import ADM.Factory.ABParmFactory;
import ADM.prt.PMprpRoot;
import AF.ut.AFUtility;
import AF.ut.AFutDate;
import AF.ut.AFutTransform;
import Dp.util.D2C;
/***
 * 
 * @author �ο�   	2012-07-31 
 * @comments: 		������������ݿ�����UBExcelOper��
 * 					�����ṩ�˲���Excel�ĸ��ַ���,����ʹ��,��鿴����˵��
 * 					ע��:����Excel���ʱ,����Ҫ���ɸ����ʵ��,������openExcleByMode����
 * 					������Ϻ�,�Լ������쳣ʱ,����Ҫ����closeExcle��������Excel�ļ�,�����ļ�����
 * 
 * 					����׷���˷���������ض���ʽ��Excle�ļ���д�롣��ϲ���Ԫ�񣬺ϼƵȡ�
 * 
 */
public class ExcelOperPlus {
	

	private WritableWorkbook writeWorkbook;
	private Workbook readWorkbook;
	
	char sheetMode='N';	//sheetҳ�ı༭ģʽ,Ĭ��Ϊ�½�ģʽ 
						//N:�½�ģʽ;U:׷������ģʽ,���sheetҳ������,���ͬNģʽ;F:ǿ��ģʽ,���sheetҳ������,����ʾ����,����׷������
	
	
	private int order=1;
	public int currentRow=0;
	
	private WritableCellFormat numCellFmt = new WritableCellFormat(NumberFormats.DEFAULT);			//���ֵ�Ԫ��
	
	private WritableFont cellFont = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false); //��Ԫ������
	private WritableCellFormat cellFmt = new WritableCellFormat(cellFont); 					 	//��Ԫ���ʽ
	
	
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public ExcelOperPlus(){
		initCell();//��ʼ����Ԫ���ʽ
	}
	
	/**
	 * ���캯��
	 * @param fileMod		N:��ʾ�������ļ�;
	 * 						U:��ȡ�����ļ�.���û��,�򴴽����ļ�;
	 * 						F:��ȡ�����ļ�.���û��,�򷵻ش���-1;
	 * @param sheetMod		N:��ʾ������sheetҳ;
	 * 						U:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ������sheetҳ
	 * 						F:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ���ش���-1
	 * @param path			Excel�ļ���,ȫ·��
	 * @throws Exception 
	 */
	
	public ExcelOperPlus(char fileMod,char sheetMod,String path) throws Exception{
		openExcleByMode(fileMod,sheetMod,path);
		initCell();
	}
	
	//��ʼ����Ԫ���ʽ
	private void initCell(){
		try {
			cellFmt.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @throws IOException 
	 * @throws WriteException 
	 * @comments:	�رղ�����excel
	 *				��ɾ�Ĳ������,���߳�������,��Ҫ�ر�,�����ļ�����.
	 */
	public  void closeExcle() throws Exception{
		if(writeWorkbook!=null){
			//��sheetҳʱ����д,��������
			WritableSheet[] sheets=writeWorkbook.getSheets();
			if(sheets!=null&&sheets.length>0){
				writeWorkbook.write();
			}
			writeWorkbook.close();
			writeWorkbook=null;
		}
		if(readWorkbook!=null){
			readWorkbook.close();
			readWorkbook=null;
		}
	}
	/**
	 * @author: �ο� 2011-1-17
	 * @comments:		��excel
	 * @param path		Excel�ļ���,ȫ·��
	 * @param sheetName ��Ҫָ��������sheet��,�����Ƕ��,������Ŀ����
	 * @return
	 * @throws Exception 
	 */
	public void openExcle(String path,String... sheetName) throws Exception{
		File file = new File(path);
		writeWorkbook=Workbook.createWorkbook(file);
		
		if(sheetName.length>0){
			for(int i =0;i<sheetName.length;i++){
				writeWorkbook.createSheet(sheetName[i], i);
			}
		}else{
			writeWorkbook.createSheet("sheet1", 0);
		}
				
	}
	public void openAExcel(String path)throws Exception{
		openExcleByMode('F','F',path);
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @comments:			ͨ��ָ��ģʽ�򿪻򴴽�Excel�ļ�
	 * @param fileMod		N:��ʾ�������ļ�;
	 * 						U:��ȡ�����ļ�.���û��,�򴴽����ļ�;
	 * 						F:��ȡ�����ļ�.���û��,���׳��쳣;
	 * 
	 * @param sheetMod		N:��ʾ������sheetҳ;
	 * 						U:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ������sheetҳ
	 * 						F:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ�׳��쳣;
	 * 
	 * @param path			Excel�ļ���,ȫ·��
	 */
	public  void openExcleByMode(char fileMod,char sheetMod,String path)throws Exception{
		try {
			if(path==null||path.equals("")){
				throw new Exception("����Excel����ʧ�ܣ��ļ���Ϊ��!");
			}
			
			if(!path.endsWith(".xls")){
				path=path+".xls";
			}
			
			sheetMode=sheetMod;
			
			File file = new File(path);
			if(fileMod=='N'){//�������ļ�
				writeWorkbook=Workbook.createWorkbook(file);
				
			}else if(fileMod=='U'){//��ȡ�����ļ�,���û��,�򴴽�
				if(!file.exists()){//���������,�򴴽����ļ�
					writeWorkbook=Workbook.createWorkbook(file);
				}else{				//�������,�����
					Workbook readWorkbook=Workbook.getWorkbook(file);			//����Excel
					writeWorkbook=Workbook.createWorkbook(file,readWorkbook);	//��Excelת��Ϊ�ɶ�д����ʽ
				}
				
			}else if(fileMod=='F'){//��ȡ�����ļ�,���������,����ʾ����
				if(!file.exists()){//���������,�򷵻ش���-2
					throw new Exception("�ļ�������! path="+path);
				}else{				//�������,�����
					Workbook readWorkbook=Workbook.getWorkbook(file);			//����Excel
					writeWorkbook=Workbook.createWorkbook(file,readWorkbook);	//��Excelת��Ϊ�ɶ�д����ʽ
				}
			}else{//ģʽ����ʶ��,��Ĭ���½�
				writeWorkbook=Workbook.createWorkbook(file);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//��List�е��������ݰ�˳��д��ָ��sheet�е�ָ����
	public  void writeLine(Object[] valueList,int rowNum,String sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				label = new Label(i,rowNum,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	
	//��List�е��������ݰ�˳��д��ָ��sheet�е�ָ����
	//д������ʱ,�����ַ��ͺ�������
	public  void writeLine(Object[] valueList,String[] dataType,int rowNum,String sheetName) throws Exception{
		
		String writeVal="";
		Label label;	
		jxl.write.Number number;
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				if(dataType == null){
					label = new Label(i,rowNum,writeVal);
					wSheet.addCell(label); 		
				}else{
					String t = dataType[i];
					if(isNumber(t)){//�������ֵ�͵Ļ���������ֵ���ݴ洢
						try {
							double dValue = Double.parseDouble(writeVal);
							number = new jxl.write.Number(i,rowNum,dValue,numCellFmt);
							wSheet.addCell(number);
						} catch (Exception e) {
							label = new Label(i,rowNum,writeVal);
							wSheet.addCell(label); 
						}					
					}else{
						label = new Label(i,rowNum,writeVal);
						wSheet.addCell(label); 						
					}	
				}
			}
			label = null;
			number = null;
		}catch (Exception e) {
			closeExcle();
			throw e;
			
		}
		
	}
	
	//��List�е��������ݰ�˳��д��ָ��sheet�е�ָ����
	public void writeLine(List<String> valueList,int rowNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String)valueList.get(i);
				label = new Label(i,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	/**
	 * 
	 * @author: �ο� 2011-3-3
	 * @comments:
	 * @param valueList
	 * @param rowNum  д�����ʼ��
	 * @param colNum  д�����ʼ��
	 * @param sheetName
	 * @return
	 * @throws Exception 
	 */
	public void writeLine(List<String> valueList,int rowNum,int colNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String)valueList.get(i);
				label = new Label(i+colNum-1,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	public  void writeLine(String[] valueList,int rowNum,int colNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				label = new Label(i+colNum-1,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	//��List�е��������ݰ�˳��д��ָ��sheet�е�ָ����
	public  void writeLine(List<String> valueList,int rowNum,String sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String)valueList.get(i);
				label = new Label(i,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	
	//�������е��������ݰ�˳��д��ָ��sheet�е�ָ����
	public  void writeLine(String[] valueList,int rowNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				label = new Label(i,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	public  void writeLine(String[] valueList,int rowNum,int colNum,String sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				label = new Label(i+colNum-1,rowNum-1,writeVal);
				wSheet.addCell(label); 
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	/**
	 * ��sheet�е�һ��Cell��д������
	 * @comments	:
	 * @author   	:�ο� 2011-11-3
	 *
	 * @param valueList
	 * @param rowNum
	 * @param colNum
	 * @param sheetName
	 * @return
	 * @throws Exception 
	 */
	private void writeCell(String writeVal,int rowNum,int colNum,String sheetName) throws Exception{
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		try{
			label = new Label(colNum-1,rowNum-1,writeVal);
			wSheet.addCell(label); 
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	/**
	 * ��sheet�е�һ��Cell��д������ ����ָ��д�����ݵ����� 
	 * dataType=String,��ʾ���ַ���ʽд��,dataType=num,��ʾ��������ʽд��
	 * @comments	:
	 * @author   	:�ο� 2011-11-3
	 *
	 * @param writeVal
	 * @param rowNum
	 * @param colNum
	 * @param dataType
	 * @param sheetName
	 * @return
	 * @throws Exception 
	 */
	private void writeCell(String writeVal,int rowNum,int colNum,String dataType,String sheetName) throws Exception{
		
		Label label;	
		jxl.write.Number number;
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		try{
				
			if(dataType == null){
				label = new Label(colNum-1,rowNum-1,writeVal);
				wSheet.addCell(label); 		
			}else{
				String t = dataType;
				if(isNumber(t)){//�������ֵ�͵Ļ���������ֵ���ݴ洢
					try {
						double dValue = Double.parseDouble(writeVal);
						number = new jxl.write.Number(colNum-1,rowNum-1,dValue,numCellFmt);
						wSheet.addCell(number);
					} catch (Exception e) {
						label = new Label(colNum-1,rowNum-1,writeVal);
						wSheet.addCell(label); 
					}					
				}else{
					label = new Label(colNum-1,rowNum-1,writeVal);
					wSheet.addCell(label); 						
				}	
			}
			
			label = null;
			number = null;
		}catch (Exception e) {
			closeExcle();
			throw e;
			
		}
		
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-15
	 * @comments:			����άListд��ָ��Excel��ָ��sheet��
	 * @param valueList 	������String[][]
	 * @param sheetName		Ҫд���sheet��
	 * @return				0:����;-1:�쳣
	 * @throws Exception 
	 */
	public void writeExcel(String[][] valueArray,String sheetName) throws Exception{
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		int writeNum=0;
		switch(sheetMode){
			case 'N'://�½�ģʽ
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueArray,sheetName,0);
				
			case 'U'://׷��ģʽ
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueArray,sheetName,writeNum);
				
			case 'F'://ǿ��ģʽ
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcel(valueArray,sheetName,writeNum);
				}
				
			default://ģʽ���ô���,��'N'����
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueArray,sheetName,0);
		}
		
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-15
	 * @comments:			����άListд��ָ��Excel��ָ��sheet��
	 * @param valueList 	������List<String[]>
	 * @param sheetName		Ҫд���sheet��
	 * @return				0:����;-1:�쳣
	 * @throws Exception 
	 */
	public void writeExcel(List<String[]> valueList,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
			case 'N'://�½�ģʽ
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueList,sheetName,0);
			case 'U'://׷��ģʽ
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueList,sheetName,writeNum);
			case 'F'://ǿ��ģʽ
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcel(valueList,sheetName,writeNum);
				}
			default://ģʽ���ô���,��'N'����
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueList,sheetName,0);
		}
	}
	/**
	 * 
	 * @author: �ο� 2011-1-15
	 * @comments:			����άListд��ָ��Excel��ָ��sheet��
	 * @param dataType 		������String[],��ʾÿһ�е�����
	 * @param valueList 	������List<String[]>
	 * @param sheetName		Ҫд���sheet��
	 * @return				0:����;-1:�쳣
	 * @throws Exception 
	 */
	public void writeExcel(List<String[]> valueList,String[] dataType,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
		case 'N'://�½�ģʽ
			removeSheet(sheetName);
			creatNewSheet(sheetName);
			writeExcel(valueList,dataType,sheetName,0);
		case 'U'://׷��ģʽ
			writeNum=getSheetWriteRow(sheetName);
			writeExcel(valueList,dataType,sheetName,writeNum);
		case 'F'://ǿ��ģʽ
			if(writeWorkbook.getSheet(sheetName)==null){
				throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
			}else{
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueList,dataType,sheetName,writeNum);
			}
		default://ģʽ���ô���,��'N'����
			removeSheet(sheetName);
			creatNewSheet(sheetName);
			writeExcel(valueList,dataType,sheetName,0);
		}
	}
	 
	/**
	 * 
	 * @author: �ο� 2011-1-15
	 * @comments:			����άListд��ָ��Excel��ָ��sheet��
	 * @param valueList 	������List<List<String>>
	 * @param sheetName		Ҫд���sheet��
	 * @return				0:����;-1:�쳣
	 * @throws Exception 
	 */
	public void writeExcelLL(List<List<String>> valueList,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
			case 'N'://�½�ģʽ
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcelLL(valueList,sheetName,0);
				
			case 'U'://׷��ģʽ
				writeNum=getSheetWriteRow(sheetName);
				writeExcelLL(valueList,sheetName,writeNum);
				
			case 'F'://ǿ��ģʽ
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcelLL(valueList,sheetName,writeNum);
				}
				
			default://ģʽ���ô���,��'N'����
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcelLL(valueList,sheetName,0);
		}
		
	}
	
	/**
	 * ��ָ��sheet��ָ����Ԫ��Cell��д������
	 * @comments	:
	 * @author   	:�ο� 2011-11-3
	 *
	 * @param writeVal
	 * @param rowNum
	 * @param colNum
	 * @param sheetName
	 * @return
	 * @throws Exception 
	 */
	public void writeToCell(String writeVal,int rowNum,int colNum,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		writeCell(writeVal,rowNum,colNum,sheetName);
	}
	/**
	 * ��ָ��sheet��ָ����Ԫ��Cell��д������,����ָ��д�������
	 * @comments	:
	 * @author   	:�ο� 2011-11-3
	 *
	 * @param writeVal
	 * @param rowNum
	 * @param colNum
	 * @param dataType
	 * @param sheetName
	 * @return
	 * @throws Exception 
	 */
	public void writeToCell(String writeVal,int rowNum,int colNum,String dataType,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		writeCell(writeVal,rowNum,colNum,sheetName,dataType);
		
	}
	
	/**
	 * @author �ο� 2010-01-15
	 * @comments			�ı�sheetҳ�Ĳ���ģʽ
	 * @param sheetM		N:��ʾ������sheetҳ;
	 * 						U:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ������sheetҳ
	 * 						F:��ȡ����sheetҳ,���û��,�ڵ���д������ʱ���ش���-1
	 */
	public void changeSheetMode(char sheetM){
		sheetMode=sheetM;
	}
	
	
	/**
	 * @author �ο� 2010-01-15
	 * @comments 		��ָ��sheetҳ�е��������б���ʽ����
	 * @param sheetName
	 * @return
	 */
	public List<String[]> readExcel(String sheetName){
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			return null;
		}
		
		int rowNum = wSheet.getRows();		//��������
		int columnNum = wSheet.getColumns();//��������
		
		List returnList= new ArrayList();
		String[] currentRow;
		String value="";
		for (int i = 0; i < rowNum; i++) {
			
			currentRow=new String[columnNum];
			for(int j=0;j<columnNum;j++){
				value=wSheet.getCell(j,i).getContents();
				currentRow[j]=value;
			}
			returnList.add(currentRow);
		}
		return returnList;
	}
	/*
	 * ����ά����д��ָ��Excel��ָ��sheet��,��ָ����firstRow��ʼд��
	 * ���������String����
	 */
	private void writeExcel(String[][] valueArray,String sheetName,int firstRow) throws Exception{
		if(valueArray==null){
			throw new Exception("ָ����д������Ϊ��!");
		}
		String[] writeVal=null;
		try{
			for (int i = 0; i < valueArray.length; i++) {
				writeVal=valueArray[i];
				writeLine(writeVal,i+firstRow,sheetName);
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	
	/*
	 * ��Listд��ָ��Excel��ָ��sheet��,��ָ����firstRow��ʼд��
	 * List�е�ÿ��Ԫ�ر�����String[]����
	 */
	public void writeExcel(List<String[]> valueList,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("ָ����д������Ϊ��!");
		}
		String[] writeVal =null;
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String[])valueList.get(i);
				writeLine(writeVal,i+firstRow,sheetName);
			}
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}

	/*
	 * ��Listд��ָ��Excel��ָ��sheet��,��ָ����firstRow��ʼд��
	 * д������ʱ,�����ַ��ͺ�������
	 * List�е�ÿ��Ԫ�ر�����String[]����
	 */
	public void writeExcel(List<String[]> valueList,String[] dataType,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("ָ����д������Ϊ��!");
		}
		String[] writeVal =null;
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String[])valueList.get(i);
				writeLine(writeVal,dataType,i+firstRow,sheetName);
			}
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	
	/*
	 * ����άListд��ָ��Excel��ָ��sheet��,��ָ����firstRow��ʼд��
	 * List������String����
	 */
	private void writeExcelLL(List<List<String>> valueList,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("ָ����д������Ϊ��!");
		}
		List<String> writeVal=null;
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(List<String>)valueList.get(i);
				writeLine(writeVal,i+firstRow,sheetName);
			}
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	/**
	 * ��ָ���������ӡ2ά�б�
	 * @author: �ο� 2011-3-3
	 * @comments:
	 * @param valueList
	 * @param sheetName
	 * @param firstRow
	 * @return
	 * @throws Exception 
	 */
	public void writeArea(List<String[]> valueList,String sheetName,int firstRow,int firstCol) throws Exception{
		if(valueList==null){
			throw new Exception("ָ����д������Ϊ��!");
		}
		String[] writeVal =null;
		try{
			for (int i = 0; i < valueList.size(); i++) {
				writeVal=(String[])valueList.get(i);
				writeLine(writeVal,i+firstRow,firstCol,sheetName);
			}
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @comments:�����µ�sheetҳ ���Դ������
	 * @param sheetName	
	 */
	public void creatNewSheets(String...sheetName){
		if(sheetName==null){//���sheetNameΪ��,�����½��ļ�,����봴��һ��Ĭ��sheetҳ
			writeWorkbook.createSheet("newSheet1", 0);
		}else{				//���sheetName����,��ָ�����ƴ���sheetҳ
			for(int i =0;i<sheetName.length;i++){
				if(sheetName[i]==null||sheetName[i].equals("")){
					creatNewSheet("newSheet"+i);
				}else{
					creatNewSheet(sheetName[i]);
				}
				
			}
		}
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @comments:�����µ�sheetҳ
	 * @param sheetName
	 */
	public void creatNewSheet(String sheetName){
		int sheetCount=writeWorkbook.getNumberOfSheets();
		
		if(sheetName==null||sheetName.equals("")){
			writeWorkbook.createSheet("newSheet1",sheetCount);
		}
		if(writeWorkbook.getSheet(sheetName)==null){//���sheetҳ������,�򴴽�
			writeWorkbook.createSheet(sheetName,sheetCount);
		}
		
	}
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @comments:���sheet
	 * @param sheetName
	 */
	public void clearSheet(String sheetName){
		if(sheetName==null||sheetName.equals("")){
			return;
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(wSheet==null){//���sheetҳ������,�����
			return;
		}
		int rowNum = wSheet.getRows();
		//�������
		for (int i=rowNum-1;i>=0;i--){
			wSheet.removeRow(i);
		}
		
	}
	
	
	/**
	 * 
	 * @author: �ο� 2011-1-17
	 * @comments:ɾ��sheet
	 * @param sheetName
	 */
	public void removeSheet(String sheetName){
		if(sheetName==null||sheetName.equals("")){
			return;
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(wSheet==null){//���sheetҳ������,�����
			return;
		}
		
		String[] sheets=writeWorkbook.getSheetNames();
		
		for(int i=0;i<=sheets.length;i++){
			if(sheetName.equalsIgnoreCase(sheets[i])){
				writeWorkbook.removeSheet(i);
				return;
			}
		}
		
		
	}
	
	//�õ�ָ��sheet��д���е�λ��,�����һ������λ��
	private int getSheetWriteRow(String sheetName){
		WritableSheet wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){//���sheetҳ������,�򴴽�
			writeWorkbook.createSheet(sheetName,0);
			return 0;
		}
		int rowNum = wSheet.getRows();	//�ҵ����һ�����ݵ�λ��
		
		return rowNum;
		
	}
	
	//�ж�Ҫд��������Ƿ���������
	private boolean isNumber(String dataType){
		
		if(dataType==null||dataType.equals("")){
			return false;
		}
		
		if(dataType.equalsIgnoreCase("String")){
			return false;
		}
		
		if(dataType.equalsIgnoreCase("java.lang.String")){
			return false;
		}
		if(dataType.equalsIgnoreCase("java.sql.Timestamp")){
			return false;
		}
		
		return true;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//���´�����ҪΪ���ݵ�Excel�ض���ʽ���ʹ��
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * ���б�������ָ����ʽд��Excle,ͬʱ�����趨�ϲ���Ԫ��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param valueList			д����б�����
	 * @param header			�б���б���
	 * @param sheetName			д���sheet��
	 * @param firstRow			д�����ʼ�к�
	 * @param mergeColDes		ÿһ�еĵ�Ԫ������,�����ϲ�������Ϣ,��Ԫ���ʽ��
	 * @param colOffset			��������ƫ����,�����������ƵĹ���
	 * @param orderColDes		����е�Ԫ������,���Ϊ��,���ʾ����Ҫ�����
	 * @param subTotalDes		С�Ƶ�Ԫ������,���Ϊ��,���ʾ����ҪС��
	 * @throws Exception
	 */
	public void writeExcelAndMer(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,
									int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		
		if(colOffset<0){
			throw new Exception("��ƫ����colOffset����С��0!");
		}
		
		//���б�����д��Excle,ͬʱ�����Ҫ�ϲ��ĵ�Ԫ������
		ArrayList<CellDes> mergeList=writeExcel(valueList,header,sheetName,firstRow,mergeColDes,colOffset,orderColDes,subTotalDes);
		
		if(mergeList==null||mergeList.size()==0){
			return;
		}
		//�ϲ���Ԫ��
		for (int i = 0; i < mergeList.size(); i++) {
			CellDes aMergeDes=mergeList.get(i);
			mergeCell(aMergeDes.colStart,aMergeDes.rowStart,aMergeDes.colEnd,aMergeDes.rowEnd,sheetName,colOffset);
		}
		
	}
	/**
	 * ���б�������ָ����ʽд��Excle,ͬʱ�����趨�ϲ���Ԫ��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param valueList			д����б�����
	 * @param header			�б���б���
	 * @param sheetName			д���sheet��
	 * @param firstRow			д�����ʼ�к�
	 * @param mergeColDes		ÿһ�еĵ�Ԫ������,�����ϲ�������Ϣ,��Ԫ���ʽ��
	 * @param colOffset			��������ƫ����,�����������ƵĹ���
	 * @param orderColDes		����е�Ԫ������,���Ϊ��,���ʾ����Ҫ�����
	 * @throws Exception
	 */
	public void writeExcelAndMer(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,
			int colOffset,CellDes orderColDes) throws Exception{
		
		writeExcelAndMer(valueList,header,sheetName,firstRow,mergeColDes,colOffset,orderColDes,null);
		
	}
	
	
	/**
	 * ���б�������ָ����ʽд��Excle
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param valueList			д����б�����
	 * @param header			�б���б���
	 * @param sheetName			д���sheet��
	 * @param firstRow			д�����ʼ�к�
	 * @param mergeColDes		ÿһ�еĵ�Ԫ������,�����ϲ�������Ϣ,��Ԫ���ʽ��
	 * @param colOffset			��������ƫ����,�����������ƵĹ���
	 * @param orderColDes		����е�Ԫ������,���Ϊ��,���ʾ����Ҫ�����
	 * @return					��Ҫ�ϲ��ĵ�Ԫ�������б�
	 * @throws Exception
	 */
	private ArrayList<CellDes> writeExcel(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		if(valueList==null){
			throw new Exception("д�������Ϊ�գ�");
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		boolean addOrder=false;														//�Ƿ��������б�ʶ
		
		if(orderColDes!=null){															//�����������,����б�ʶΪ��
			addOrder=true;
			if(colOffset<1){															//��������ʱ,��ƫ��������Ϊ1.
				colOffset=1;
			}
		}
		
		try{
			setColLenth(wSheet,mergeColDes,colOffset);									//�����п�
			
			if(header!=null){															//����б��ͷ���鲻��,��д���ͷ��
				
				CellDes[] headerColDes=genHeaderColDes(mergeColDes);
				
				if(addOrder){
					writeALine(wSheet,header,firstRow,headerColDes,colOffset,"���");	//����б�ʶΪ��,��д�����
				}else{
					writeALine(wSheet,header,firstRow,headerColDes,colOffset,null);	//����б�ʶΪ��,��д�����
				}
				firstRow=firstRow+1;													//д���ͷ�к�,��������ʼ����Ҫ+1
			}
			
			
			//д����ʱ,����Ҫ����1������д��,���������������ݳ�ʼ��
			//����,�����ÿһ�����ݻ���ǰһ����Ӧ�м������е����ݱȽ�,�����Ƿ�ϲ�
			String[] writeVal =null;
			writeVal=(String[])valueList.get(0);
			
			if(addOrder){
				writeALine(wSheet,writeVal,firstRow,mergeColDes,colOffset,"1");		//����� д��1������,�����бȽ�
				
				int[] refCol=orderColDes.refMergeCol;									//���ȼ�¼����еĲ�����,��Ϊ����Ҫ���п�¡
				
				orderColDes=AFUtility.cloneObjByXstream(mergeColDes[0]);				//�����¡��һ�е�����,Ϊ��ʹ��ӵ���������һ������ͬ�ĸ�ʽ
																						//����û�в�ȡ���ֵ�ķ���,��Ϊ�˱����������ı��,������˸�ʽ����,���ﻹ��Ҫ�Ĵ���
				orderColDes.refMergeCol=refCol;
				orderColDes.colStart=-1;												//����������-1�����λ��,���ʵ�����ݵ�1��(��1�����Ϊ0)
				orderColDes.colEnd=-1;										
				orderColDes.rowStart=firstRow;
				orderColDes.rowEnd=firstRow;
				
			}else{
				writeALine(wSheet,writeVal,firstRow,mergeColDes,colOffset,null);	//������� д��1������,���������ݴ���
			}
			
			
			for (int i = 0; i < mergeColDes.length; i++) {								//���ݵ�1������,�������������ݳ�ʼ��
				if(mergeColDes[i]==null){
					mergeColDes[i]=new CellDes();
				}
				
				mergeColDes[i].lastData=writeVal[i]+"";									//ʹ�õ�Ԫ�������е�lastData���Լ�¼��һ�����ݸ��е�ֵ,���ںϲ�ʱ�ıȽ�.��ʱ��1�������Ѿ�д��
				if(mergeColDes[i].isMerge){
					mergeColDes[i].colStart=i;
					mergeColDes[i].colEnd=i;
					mergeColDes[i].rowStart=firstRow;
					mergeColDes[i].rowEnd=firstRow;
				}
			}
			
			boolean addSubTotla=false;
			if(subTotalDes!=null&&subTotalDes.doSubtotal){								//���С���������ղ���subTotalDes.doSubtotal==trueʱ,��ʾͨ���������С��
				addSubTotla=true;
				subTotalDes.rowStart=0;
				subTotalDes.rowEnd=0;
			}
			
			ArrayList<CellDes> mergeQue=new ArrayList<CellDes>();						//���ڱ���ϲ���Ԫ������
			
			boolean isWriteSubTotal=false;
			int writeRow=firstRow+1;
			
			for (int i = 1; i < valueList.size(); i++) {								//�����Ѿ�д���˵�һ������,����ѭ��ʱ�ӵ�2�����ݿ�ʼ
				writeVal=(String[])valueList.get(i);
				
				if(addSubTotla){//�����Ҫ����С��,�����С�Ʋ�д��
					isWriteSubTotal=writeSubTotal(wSheet,writeRow,valueList,writeVal,mergeColDes,subTotalDes,colOffset);//�ж��Ƿ�д��С����
					if(isWriteSubTotal){												//���д����С����,��ǰ�к�+1
						writeRow=writeRow+1;
					}
				}
				
				boolean[] isMerge=writeLine(wSheet,writeVal,writeRow,mergeColDes,colOffset,orderColDes,subTotalDes);//дÿһ������,������һ�����ݽ��бȽ�,�����Ƿ�ϲ�
				genMergeQue(isMerge,mergeColDes,mergeQue,isWriteSubTotal);								//���ɺϲ���Ԫ���б�
				
				if(addOrder){															//�������������,�����������Ҫ�ϲ��Ķ���
					genMergeQue(isMerge,orderColDes,mergeQue,isWriteSubTotal);
				}
				writeRow=writeRow+1;
			}
			
			if(addSubTotla){															//�����Ҫ����С��,��д�����һ��ֵ��С��
				writeLastSubTotal(wSheet,writeRow,valueList,mergeColDes,subTotalDes,colOffset);
				writeRow=writeRow+1;
			}
			
			addLastMerge(mergeColDes,mergeQue);											//д�����һ�����ݺ�,�����кϲ�����
			
			if(addOrder){
				addLastMerge(new CellDes[]{orderColDes},mergeQue);						//�����Ҫ�����,��д�����һ�����ݺ�,��������кϲ�����
			}
			
			currentRow=writeRow;														//Excle�ĵ�ǰ�к�
			
			return mergeQue;
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	
	//��ͷ��������,��������������¡����
	private CellDes[] genHeaderColDes(CellDes[] colDes) throws IOException{
		
		CellDes[] headerColDes=AFUtility.cloneObjByXstream(colDes);
		
		for (int i = 0; i < headerColDes.length; i++) {
			headerColDes[i].useFormat=true;					//ʹ�����ݸ�ʽ��
			headerColDes[i].useBoldFont=true;				//ʹ�ô�����
		}
		
		return headerColDes;
	}
	
	/**
	 * д��һ������
	 * @comments	:
	 * @author   	:�ο� 2012-8-8
	 *
	 * @param sheetName				Excel��sheet��
	 * @param values				Ҫд�����������
	 * @param rowNum				д����к�
	 * @param colDes				д����ÿһ�еĵ�Ԫ������
	 * @param colOffset				д����ƫ����,�������������ƵĹ���
	 * @throws Exception
	 */
	public void writeLine(String sheetName,String[] values,int rowNum,CellDes[] colDes,int colOffset) throws Exception{
		
		try{
			
			if(values==null){
				throw new Exception("д�������Ϊ�գ�");
			}
			
			WritableSheet wSheet;
			wSheet=writeWorkbook.getSheet(sheetName);
			if(wSheet==null){
				throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
			}
			
			for (int i = 0; i < values.length; i++) {
				String writeVal=values[i];
				writeCell(wSheet,writeVal,i,rowNum,colDes[i],colOffset);
			}
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	//����д��Excle��Ԫ����п�
	private void setColLenth(WritableSheet wSheet,CellDes[] mergeColDes,int colOffset){
		for (int i = 0; i < mergeColDes.length; i++) {
			int lenth=mergeColDes[i].cellLen;
			if(lenth<=0){
				continue;
			}
			wSheet.setColumnView(i+colOffset, lenth);
			
		}
		
	}
	
	/**
	 * ����ÿһ�еĵ�Ԫ������,����Ҫ�ϲ��ĵ�Ԫ�����ϲ���Ԫ�����
	 * ��Ԫ������mergeColDesͨ��rowStart��rowEnd��������¼���дӵڼ��кϲ����ڼ���
	 * 
	 * ���ĳ����Ҫ�ϲ�,�򽫸��е�������rowEnd+1,ֱ������Ҫ�ϲ�ʱ,���һ���ϲ���Ԫ��ļ���
	 * ��ʱ��rowStart��rowEnd����ΪrowEnd+1,���¿�ʼ������һ����Ҫ�ϲ��ĵ�Ԫ���rowStart��rowEnd
	 * @comments	:
	 * @author   	:�ο� 2012-8-8
	 *
	 * @param isMerge					��¼ÿһ���Ƿ�ϲ�������
	 * @param mergeColDes				ÿһ�еĵ�Ԫ������
	 * @param mergeQue					�ϲ���Ԫ�����
	 * @throws Exception	
	 */
	private void genMergeQue(boolean[] isMerge,CellDes[] mergeColDes,ArrayList<CellDes> mergeQue,boolean addSubTotal) throws Exception{
		
		CellDes aNewMerge=null;
		CellDes mergeQueElm=null;
		
		for (int j = 0; j < isMerge.length; j++) {						//���δ���ÿ����Ԫ��
			aNewMerge=mergeColDes[j];
			if(aNewMerge.isMerge==false){								//����õ�Ԫ����Ҫ�ϲ�����,������
				continue;
			}
			
			if(isMerge[j]==true){										//���ĳ����Ҫ�ϲ�,�򽫸��е�������rowEnd+1,ֱ���жϲ���Ҫ�ϲ�ʱ,���һ���ϲ���Ԫ��ļ���
				aNewMerge.rowEnd=aNewMerge.rowEnd+1;
			}else{														//���ĳ�в���Ҫ�ϲ�,����Ҫ����һ���ϲ���Ԫ������
				
				if(aNewMerge.rowStart!=aNewMerge.rowEnd){				//��rowStart!=rowEndʱ,����Ҫ��������һ���ϲ���Ԫ������
					mergeQueElm=AFUtility.cloneObjByXstream(aNewMerge);	//����cloneһ����Ԫ������,Ϊ�˱������ݱ���д
					mergeQue.add(mergeQueElm);							//���ϲ���Ԫ����������ϲ���Ԫ�����
				}
				int newRowStart;						
				if(addSubTotal){										//��Ҫ���С����ʱ
					newRowStart=aNewMerge.rowEnd+2;						//����ǰ�еĺϲ���Ԫ��rowStart��rowEnd����ΪrowEnd+1,���¿�ʼ������һ����Ҫ�ϲ��ĵ�Ԫ��
				}else{													//����Ҫ���С����ʱ
					newRowStart=aNewMerge.rowEnd+1;						//����ǰ�еĺϲ���Ԫ��rowStart��rowEnd����ΪrowEnd+1,���¿�ʼ������һ����Ҫ�ϲ��ĵ�Ԫ��
				}
				
				aNewMerge.rowStart=newRowStart;
				aNewMerge.rowEnd=newRowStart;
				
			}
		}
	}
	
	/**
	 * ��������еĺϲ���Ԫ������,������ϲ���Ԫ�����
	 * @comments	:
	 * @author   	:�ο� 2012-8-8
	 *
	 * @param isMerge					��¼ÿһ���Ƿ�ϲ�������
	 * @param orderColDes				����еĵ�Ԫ������
	 * @param mergeQue					�ϲ���Ԫ�����
	 * @param addSubTotal				�Ƿ���Ҫ���С�Ƶı�ʶ
	 * @throws Exception
	 */
	private void genMergeQue(boolean[] isMerge,CellDes orderColDes,ArrayList<CellDes> mergeQue,boolean addSubTotal) throws Exception{
		
		CellDes mergeQueElm=null;
		
		if(orderColDes!=null){											//���������Ϊ��
			int[] refCols=orderColDes.refMergeCol;
			int refCol=0;												//�����Ĭ�ϵĲ�����Ϊ0;
			
			if(refCols==null){											//��������˶��������,��ֻȡ��1������ֵ
				refCol=refCols[0];										//����еĲ�����ֻȡ��1������ֵ
			}
			
			if(isMerge[refCol]==true){									//�����������Ҫ�ϲ�,������е�������rowEnd+1,ֱ���жϲ���Ҫ�ϲ�ʱ,���һ���ϲ���Ԫ��ļ���
				orderColDes.rowEnd=orderColDes.rowEnd+1;
			}else{														//��������в���Ҫ�ϲ�,����Ҫ����һ����źϲ���Ԫ������
				
				if(orderColDes.rowStart!=orderColDes.rowEnd){			//��rowStart!=rowEndʱ,����Ҫ��������һ���ϲ���Ԫ������
					mergeQueElm=AFUtility.cloneObjByXstream(orderColDes);//����cloneһ����Ԫ������,Ϊ�˱������ݱ���д
					mergeQue.add(mergeQueElm);							//���ϲ���Ԫ����������ϲ���Ԫ�����
				}
				
				int newRowStart;						
				if(addSubTotal){										//��Ҫ���С����ʱ
					newRowStart=orderColDes.rowEnd+2;					//����ǰ�еĺϲ���Ԫ��rowStart��rowEnd����ΪrowEnd+2(��Ϊ�����С��һ��),���¿�ʼ������һ����Ҫ�ϲ��ĵ�Ԫ��
				}else{													//����Ҫ���С����ʱ
					newRowStart=orderColDes.rowEnd+1;					//����ǰ�еĺϲ���Ԫ��rowStart��rowEnd����ΪrowEnd+1,���¿�ʼ������һ����Ҫ�ϲ��ĵ�Ԫ��
				}
				orderColDes.rowStart=newRowStart;
				orderColDes.rowEnd=newRowStart;
				
			}
			
		}
		
	}
	
	/**
	 * ����һ�����ݵ�С�Ʋ�д��
	 * @comments	:
	 * @author   	:�ο� 2012-8-16
	 *
	 * @param wSheet			Excle��sheet����
	 * @param writeRowNum		��ǰд���к�
	 * @param valueList			д�������б�.(��������,���ڼ���С��)
	 * @param currentVal		��ǰ׼��д���һ������(�����ж�����һ�������Ƿ�Ϊһ��)
	 * @param mergeColDes		�е�Ԫ������(ʹ�����е�����ȡ����һ��ÿһ�е�ֵ)
	 * @param sumTotalDes		�ϲ���Ԫ������(��Ҫָ���ϲ��Ĳ�����,��С�Ʒ��������,ͬʱָ����Щ����Ҫ����С��)
	 * @param colOffset			������ƫ����
	 * @return
	 * @throws Exception
	 */
	private boolean writeSubTotal(WritableSheet wSheet,int writeRowNum,List<String[]> valueList,String[] currentVal,CellDes[] mergeColDes,CellDes sumTotalDes,int colOffset) throws Exception{
		
		if(sumTotalDes==null){
			return false;
		}
		
		if(sumTotalDes.sumCol==null){
			return false;
		}
		
		boolean doSum=checkSumVal(currentVal,mergeColDes,sumTotalDes);//��鵱ǰһ�е�ֵ�Ƿ����һ�����ڼ���С��ʱ��һ��
		
		boolean writeSubTotal=false;
		if(doSum){														
			sumTotalDes.rowEnd=sumTotalDes.rowEnd+1;					//�������һ��,�򽫺ϼƵ�Ԫ������������ֹ��+1(һ��С�ƻ�û��ͳ�����)
			writeSubTotal=false;										//����false,��ʾ��û��д��С����
		}else{															//���������һ��,��ʼ����С��,��д��
			
			int[] refCol=sumTotalDes.refMergeCol;						//ͳ��С�ƵĲ�����,��С�Ƶķ�������
			int subTotalCol=0;
			if(refCol!=null||refCol.length>0){
				subTotalCol=refCol[0];									//��������˶��������,��ֻȡ��1��������
			}
			String[] lastSubVal=valueList.get(sumTotalDes.rowEnd);		//ȡ��һ��С�Ƶ����һ�е�ֵ(���ͬʱд��С����,�����𵽶��μ���С�ƵĹ���)
			String[] sumTotalLine= genSubTotalLine(valueList,lastSubVal,sumTotalDes);//����С��ֵ
			sumTotalLine[subTotalCol]="С�� "+lastSubVal[subTotalCol];	//����С�Ʊ�ǩ
			writeALine(wSheet,sumTotalLine,writeRowNum,mergeColDes,colOffset,null);	//д��С����
			
			int newRowStart=sumTotalDes.rowEnd+1;
			sumTotalDes.rowStart=newRowStart;							//С��������+1���¿�ʼ����
			sumTotalDes.rowEnd=newRowStart;
			writeSubTotal=true;											//����true,��ʾд����С����
		}
		
		return writeSubTotal;
	}
	
	/**
	 * ���һ������д���,д��С��
	 * @comments	:
	 * @author   	:�ο� 2012-8-16
	 *
	 * @param wSheet
	 * @param writeRowNum
	 * @param valueList
	 * @param mergeColDes
	 * @param sumTotalDes
	 * @param colOffset
	 * @throws Exception
	 */
	private void writeLastSubTotal(WritableSheet wSheet,int writeRowNum,List<String[]> valueList,CellDes[] mergeColDes,CellDes sumTotalDes,int colOffset) throws Exception{
		
		if(sumTotalDes==null){
			return;
		}
		
		if(sumTotalDes.sumCol==null){
			return;
		}
		
		int[] refCol=sumTotalDes.refMergeCol;
		
		int subTotalCol=0;
		if(refCol!=null||refCol.length>0){
			subTotalCol=refCol[0];
		}
		String[] lastSubVal=valueList.get(sumTotalDes.rowEnd);
		String[] sumTotalLine= genSubTotalLine(valueList,lastSubVal,sumTotalDes);
		sumTotalLine[subTotalCol]="С�� "+lastSubVal[subTotalCol];
		writeALine(wSheet,sumTotalLine,writeRowNum,mergeColDes,colOffset,null);	
	}
	
	/**
	 * ����С��
	 * @comments	:
	 * @author   	:�ο� 2012-8-16
	 *
	 * @param valueList
	 * @param lastSubVal
	 * @param sumTotalDes
	 * @return
	 * @throws Exception
	 */
	private String[] genSubTotalLine(List<String[]> valueList,String[] lastSubVal,CellDes sumTotalDes) throws Exception{
		
		String[] sumTotalLine=new String[lastSubVal.length];
		if(sumTotalDes.sumRowWTV){//�Ƿ�д��С����������е�ֵ(���ͬʱд��С���������е�ֵ,�����𵽶��μ���С�ƵĹ���)
			for (int i = 0; i < lastSubVal.length; i++) {
				sumTotalLine[i]=lastSubVal[i];
			}
		}
		
		List<String[]> sumList=new ArrayList<String[]>();
		
		int startRow=sumTotalDes.rowStart;			//һ��С�Ƶ���ʼ��
		int endRow=sumTotalDes.rowEnd;				//һ��С�Ƶ���ֹ��
		
		
		int[] sumCol=sumTotalDes.sumCol;
		for (int i = startRow; i <=endRow; i++) {
			sumList.add(valueList.get(i));
		}
		
		String[] sumVal=genSumS(sumList,sumCol);	//����С��ֵ
		
		for (int i = 0; i < sumCol.length; i++) {
			sumTotalLine[sumCol[i]]=sumVal[i];
		}
		
		return sumTotalLine;
	}
	
	/**
	 * ���һ�����ݴ�����ɺ�,��Ҫ���ø÷���,���ϲ�������ӵ��ϲ�������
	 * ����ᶪʧ���ָúϲ��ĵ�Ԫ��
	 * @comments	:
	 * @author   	:�ο� 2012-7-31
	 *
	 * @param mergeColDes
	 * @param mergeQue
	 */
	private void addLastMerge(CellDes[] mergeColDes,ArrayList<CellDes> mergeQue){
		
		if(mergeColDes==null){
			return;
		}
		
		for (int i = 0; i < mergeColDes.length; i++) {
			if(mergeColDes[i].isMerge==false){
				continue;
			}
			if(mergeColDes[i].rowStart!=mergeColDes[i].rowEnd){
				mergeQue.add(mergeColDes[i]);
			}
		}
		
	}
	
	/**
	 * д��һ������ �����ϲ���ȴ���
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param wSheet
	 * @param valueList
	 * @param rowNum
	 * @param mergeColDes
	 * @param colOffset
	 * @param orderVal
	 * @throws Exception
	 */
	private void writeALine(WritableSheet wSheet,Object[] valueList,int rowNum,CellDes[] mergeColDes,int colOffset,String orderVal) throws Exception{
		String writeVal="";
		
		int valCount=valueList.length;
		int colCount=mergeColDes.length;
		
		if(valCount!=colCount){
			throw new Exception("д����������������������������! ��������= "+valCount+" ����������="+colCount);
		}
		
		for (int i = 0; i < valueList.length; i++) {
			writeVal=(String)valueList[i];
			writeCell(wSheet,writeVal,i,rowNum,mergeColDes[i],colOffset);
		}
		
		if(orderVal!=null){//����в���,��д�����
			CellDes orderCellDes=AFUtility.cloneObjByXstream(mergeColDes[0]);//����������Ƕ�̬������,����������еĵ�Ԫ�������������ݵĵ�һ��������ͬ
			writeCell(wSheet,orderVal,0,rowNum,orderCellDes,colOffset-1);
		}
		
	}
	
	/**
	 * дһ������,����ϲ���Ԫ����������,����ÿһ���Ƿ�Ҫ����һ�е����ݺϲ�����Ϣ��������ʽ����
	 * @comments	:
	 * @author   	:�ο� 2012-8-8
	 *
	 * @param wSheet			Ҫд���sheet����
	 * @param values			Ҫд���һ������
	 * @param rowNum			д����к�
	 * @param mergeColDes		�����б�ÿһ�еĵ�Ԫ������
	 * @param colOffset			д��ʱ������ƫ����	
	 * @param orderColDes		����е�Ԫ������
	 * @param subTotalDes		С���е�Ԫ������
	 * @return					doMerge[]�����¼��ǰ�е�ÿһ���Ƿ�Ҫ����һ�����ݺϲ�
	 * @throws Exception
	 */
	private  boolean[] writeLine(WritableSheet wSheet,String[] values,int rowNum,CellDes[] mergeColDes,int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		String writeVal="";
		
		/*�����߼�����
		 * doMerge[]�����¼��ǰ�е�ÿһ���Ƿ�Ҫ����һ�����ݺϲ�
		 * 
		 * mergeColDes������ÿ��Ԫ�ص�:
		 * lastData��¼���м�¼����һ�����ݶ�Ӧ�е�ֵ
		 * refMergeCol[]��¼�˸������ݺϲ�ʱ��Ҫ���յ����,�����Ƕ��.
		 * ��mergeColDes[2].refMergeCol==1,3,���ʾ���ݺϲ�ʱ,����Ҫ�ƽ�mergeColDes[2].lastData��ֵ�Ƿ�͵�ǰ�е�ֵһ��
		 * ͬʱҪ�Ƚ�mergeColDes[1],mergeColDes[3]��lastData�Ƿ��뵱ǰ�еĶ�Ӧ��һ��.
		 * ���һ��,���Ӧ��doMerge[2]��ֵΪtrue,��ʾ��3��(���λ��)��Ҫ����һ�еĶ�Ӧ�кϲ�
		 * 
		 */
		
		boolean[] doMerge=new boolean[mergeColDes.length];							//doMerge[]�����¼��ǰ�е�ÿһ���Ƿ�Ҫ����һ�����ݺϲ�,��ʼֵ��Ϊfalse
		try{
			
			for (int i = 0; i < values.length; i++) {									//����д�뵱ǰ��ÿһ�е�ֵ
				writeVal=values[i];
				writeCell(wSheet,writeVal,i,rowNum,mergeColDes[i],colOffset);			//ִ��д��
				
				if(writeVal==null){
					continue;
				}
				
				if(mergeColDes[i]==null){
					continue;
				}
				if(checkRefVal(writeVal,values,mergeColDes,mergeColDes[i])){			//��鵱ǰд��ֵ�Ƿ�Ҫ����һ�����ݺϲ�
					doMerge[i]=true;
				}
			}
			
			
			if(orderColDes!=null){														//���������Ϊ��,��д�����
				int[] refCols=orderColDes.refMergeCol;
				int refCol=0;															//�����Ĭ�ϵĲ�����Ϊ0;
				
				if(refCols==null){														//��������˶��������,��ֻȡ��1������ֵ
					refCol=refCols[0];													//����еĲ�����ֻȡ��1������ֵ
				}
				
				//����������Ƕ�̬�������,����������Ҫ�����⴦��
				//�������еĲ�������Ҫ�ϲ�,����Ų��ı�д��
				//�������еĲ����в���Ҫ�ϲ�,�����+1д��
				//����,��Ҫ�ϲ�����ŵ�Ԫ���ֵ����ͬ
				//����еĺϲ��ڸ÷����⴦��
				if(doMerge[refCol]==true){												//�������еĲ�������Ҫ�ϲ�,����Ų��ı�д��
					writeCell(wSheet,order+"",0,rowNum,orderColDes,colOffset-1);
				}else{																	//�������еĲ����в���Ҫ�ϲ�,�����+1д��
					
					if(!isSubTotalLine(values,subTotalDes)){							//�������С����,�����+1д��,����д�����,�����кϲ�
						order=order+1;
						writeCell(wSheet,order+"",0,rowNum,orderColDes,colOffset-1);
						
					}
					
				}
				
			}
			
			//��mergeColDes��ÿ��Ԫ�ص�lastDataֵ��Ϊ��ǰֵ,
			//��������ıȽϷ���checkRefVal��������mergeColDes�е�ֵ
			//����������ѭ�����֮����ܸı��ж�������mergeColDes�е�ֵ.
			for (int i = 0; i < values.length; i++) {
				writeVal=values[i];
				mergeColDes[i].lastData=writeVal;
			}
			
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		return doMerge;
	}
	
	/**
	 * �ж��Ƿ���С����
	 * 
	 * �������̨Լ��,���ָ���е�ֵ��"С��"��ͷ,���ʾ��������ΪС��
	 * ���������,С���е������ɺ�̨����,����ͨ���ݵ���ʽд��,��С������Ϊ������һ��,�����кϲ�,��д�����
	 * @comments	:
	 * @author   	:�ο� 2012-8-16
	 *
	 * @param values
	 * @param subTotalDes
	 * @return
	 */
	private boolean isSubTotalLine(String[] values,CellDes subTotalDes){
		
		if(subTotalDes==null){
			return false;
		}
		
		int[] ref=subTotalDes.refMergeCol;
		
		if(ref==null||ref.length==0){
			return false;
		}
		
		int refCol=ref[0];
		
		String refVal=values[refCol];
		
		if(refVal==null){
			return false;
		}
		
		if(refVal.startsWith("С��")){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * �жϵ�ǰ��Ԫ���Ƿ�Ҫ����һ�еĶ�Ӧ��Ԫ��ϲ�
	 * @comments	:
	 * @author   	:�ο� 2012-8-8
	 *
	 * @param writeVal					��ǰ��Ԫ���д��ֵ
	 * @param valueList					��ǰд��������
	 * @param mergeColDes				д���б����ݵ��е�Ԫ����������
	 * @param currentMergeDes			��ǰ��Ԫ������
	 * @return
	 */
	private boolean checkRefVal(String writeVal,String[] valueList,CellDes[] mergeColDes,CellDes currentMergeDes){
		
		if(currentMergeDes.isMerge==false){
			return false;
		}
		
		int[] ref=currentMergeDes.refMergeCol;						//��ǰ��Ԫ��Ĳ�����
		if(ref==null||ref.length==0){								//û����������
			if(writeVal.equals(currentMergeDes.lastData)){			//û����������ʱ�����Ƚϵ�ǰд��ֵ����һ�ж�Ӧ�е�ֵ
				return true;
			}else{
				return false;
			}
		}
		
		
		if(!writeVal.equals(currentMergeDes.lastData)){				//����������ʱ���ȱȽϵ�ǰ�е�����
			return false;
		}
		
		boolean rt=true;
		for (int i = 0; i < ref.length; i++) {						//���αȽϲ����е�ֵ
			String val=(String)valueList[ref[i]];					//�����е�ǰ�е�ֵ
			String lastDate=mergeColDes[ref[i]].lastData;			//��������һ�е�ֵ
			
			if(val==null){
				continue;
			}
			if(!val.equals(lastDate)){								//ֻҪ��һ�в����,�򷵻�false,��ʾ���ϲ�
				rt=false;
				break;
			}
		}
		return rt;
	}
	
	/**
	 * �ж�һ�������Ƿ�����һ��Ϊһ�����С��
	 * @comments	:
	 * @author   	:�ο� 2012-8-16
	 *
	 * @param currentVal
	 * @param listColDes
	 * @param sumTotalDes
	 * @return
	 */
	private boolean checkSumVal(String[] currentVal,CellDes[] listColDes,CellDes sumTotalDes){
		
		if(sumTotalDes==null){
			return false;
		}
		
		int[] ref=sumTotalDes.refMergeCol;						//С�ƵĲ�����
		if(ref==null||ref.length==0){							//û����������
			return false;
		}
		
		boolean rt=true;
		for (int i = 0; i < ref.length; i++) {						//���αȽϲ����е�ֵ
			String val=(String)currentVal[ref[i]];					//�����е�ǰ�е�ֵ
			String lastDate=listColDes[ref[i]].lastData;			//��������һ�е�ֵ
			
			if(val==null){
				continue;
			}
			if(!val.equals(lastDate)){								//ֻҪ��һ�в����,�򷵻�false,��ʾ������һ�м���ϼ�
				rt=false;
				break;
			}
		}
		return rt;
	}
	
	/**
	 * �ϲ���Ԫ��
	 * �����ʼ�к�����ֹ�к����,������ʼ�кź���ֹ�к����,˵����ͬһ����Ԫ��,���ý��кϲ�
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param colStart				��ʼ�к�
	 * @param rowStart				��ʼ�к�
	 * @param colEnd				��ֹ�к�
	 * @param rowEnd				��ֹ�к�
	 * @param sheetName				Excel��sheet��
	 * @param colOffset				������ƫ����
	 * @throws Exception
	 */
	public void mergeCell(int colStart,int rowStart,int colEnd,int rowEnd,String sheetName,int colOffset) throws Exception{
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
		}
		
		if(colOffset<=0){
			colOffset=1;
		}
		
		if(colStart==colEnd&&rowStart==rowEnd){											//�����ͬһ����Ԫ��,�򲻽��кϲ�
			return;
		}
		
		try{
			wSheet.mergeCells(colStart+colOffset, rowStart, colEnd+colOffset, rowEnd);	//�ϲ���Ԫ��
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	/**
	 * ����ָ���ĸ�ʽ,дһ����Ԫ��.
	 * �����Ԫ��������colStart!=colEnd����rowStart!=rowEnd,����е�Ԫ��ϲ�
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param sheetName				Excel��sheet��
	 * @param writeVal				д�뵥Ԫ���ֵ
	 * @param currentMergeDes		��Ԫ������,��colStart!=colEnd����rowStart!=rowEnd,����е�Ԫ��ϲ�
	 * @param colOffset				д��ʱ����ƫ����
	 * @throws Exception
	 */
	public void writeToCell(String sheetName,String writeVal,CellDes currentMergeDes,int colOffset) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("δָ��sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("δ��ȡ��ָ��sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(currentMergeDes==null){
			throw new Exception("д�뵥Ԫ������Ϊ��!");
		}
		
		int colStart=currentMergeDes.colStart;
		int rowStart=currentMergeDes.rowStart;
		
		int colEnd=currentMergeDes.colEnd;
		int rowEnd=currentMergeDes.rowEnd;
		
		writeCell(wSheet,writeVal,colStart,rowStart,currentMergeDes,colOffset);
		
		if(colStart!=colEnd||rowStart!=rowEnd){
			mergeCell(colStart,rowStart,colEnd,rowEnd,sheetName,colOffset);
		}
		
	}
	
	/**
	 * ����ָ����ʽ,д��һ����Ԫ��
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param wSheet					Excel��sheet����
	 * @param writeVal					д��ֵ
	 * @param colNum					д����к�
	 * @param rowNum					д����к�
	 * @param cellDes					��Ԫ������
	 * @param colOffset					��ƫ����
	 * @throws Exception
	 */
	private void writeCell(WritableSheet wSheet,String writeVal,int colNum,int rowNum,CellDes cellDes,int colOffset) throws Exception{
		
		Label label;	
		jxl.write.Number number;
		colNum=colNum+colOffset;
		
		if(colNum<0){																//�ݴ���
			colNum=0;
		}
		if(rowNum<0){																//�ݴ���
			rowNum=0;
		}
		
		if(cellDes==null){
			cellDes=new CellDes();													//�����Ԫ������Ϊ��,����Ĭ�ϸ�ʽд��
		}
		
		if(writeVal==null||writeVal.equalsIgnoreCase("null")){
			writeVal="";
		}
		
		try{
				
			if(cellDes.isNumber == false){											//�������������,������ͨ�ı���ʽд��
				label = genLable(colNum,rowNum,writeVal,cellDes);					//���ɵ�Ԫ��
				wSheet.addCell(label); 		
			}else{																	//�����������,�������ֵ�Ԫ��д��
				try {
					double dValue = Double.parseDouble(writeVal);
					number = new jxl.write.Number(colNum,rowNum,dValue,numCellFmt);
					wSheet.addCell(number);
				} catch (Exception e) {												//�������ת�ʹ���,������ͨ�ı���ʽд��
					label = genLable(colNum,rowNum,writeVal,cellDes);
					wSheet.addCell(label); 
				}					
			}
			
			label = null;
			number = null;
		}catch (Exception e) {
			closeExcle();
			throw e;
			
		}
		
	}
	
	/**
	 * ����ָ����ʽ����Ҫд��ĵ�Ԫ��
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param colNum
	 * @param rowNum
	 * @param writeVal
	 * @param cellDes
	 * @return
	 */
	private Label genLable(int colNum,int rowNum,String writeVal,CellDes cellDes){
		
		Label label =null;
		
		try {
		
			if(cellDes==null||cellDes.useFormat==false){														//�����Ԫ������Ϊ��,��ʹ�ø�ʽ,������ͨ��ʽ���ɵ�Ԫ��
				label = new Label(colNum,rowNum,writeVal);
				return label;
			}
			
			if(cellDes.useBoldFont){																			
				cellFont = new WritableFont(WritableFont.ARIAL,cellDes.fontSize,WritableFont.BOLD,false);	 	//ʹ�ô�������
				cellFmt = new WritableCellFormat(cellFont); 					 								//��Ԫ���ʽ
				
			}else{
				cellFont = new WritableFont(WritableFont.ARIAL,cellDes.fontSize,WritableFont.NO_BOLD,false);	//��ʹ�ô�������
				cellFmt = new WritableCellFormat(cellFont); 					 								//��Ԫ���ʽ
				
			}
			
			if(cellDes.align!=null){																			//���ֶ��뷽ʽ
				if(cellDes.align.equalsIgnoreCase("L")||cellDes.align.equalsIgnoreCase("Left")){				//�����
					cellFmt.setAlignment(Alignment.LEFT);
				}else if(cellDes.align.equalsIgnoreCase("C")||cellDes.align.equalsIgnoreCase("Center")){		//����
					cellFmt.setAlignment(Alignment.CENTRE);
				}else if(cellDes.align.equalsIgnoreCase("R")||cellDes.align.equalsIgnoreCase("Right")){			//�Ҷ���
					cellFmt.setAlignment(Alignment.RIGHT);
				}
			}
			
			if(cellDes.borderSzie==0){																			//��Ԫ��߿�
				cellFmt.setBorder(Border.ALL, BorderLineStyle.HAIR);											//���߱߿�
			}else if(cellDes.borderSzie==1){
				cellFmt.setBorder(Border.ALL, BorderLineStyle.THIN);											//ϸ�߿�
			}else if(cellDes.borderSzie>=2){
				cellFmt.setBorder(Border.ALL, BorderLineStyle.MEDIUM);											//�ϴֱ߿�
			}
			
			writeVal=textFormat(writeVal,cellDes);																//���ݸ�ʽת��
			
			label = new Label(colNum,rowNum,writeVal,cellFmt);													//���ɵ�Ԫ��
		
		} catch (WriteException e) {
			label = new Label(colNum,rowNum,writeVal);
		}
		
		return label;
	}
	
	/**
	 * ���ݸ�ʽת��
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param content					Ҫת��������
	 * @param cellDes					��Ԫ������		
	 * @return
	 */
	private String textFormat(String content,CellDes cellDes){
		if(content==null||cellDes==null){
			return "";
		}
		
		String rt=content;
		try {
			if(cellDes.textFormat==null||cellDes.textFormat.equalsIgnoreCase("N")){				//������������ݸ�ʽ��,�򷵻�ԭֵ
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("millSpt")){									//ת��Ϊǧ��λ��ʾ
				rt=AFutTransform.NumFormate(content,-1,cellDes.decDigits,false,true,'R');
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("date")){									//ʹ�����ڸ�ʽ
				rt=AFutDate.getStrDataByForm(content,cellDes.dateFormat);
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("numToRMB")){								//ת��Ϊ����Ҵ�д
				rt=numberToRMB(content);
				return rt;
			}
		
		} catch (Exception e) {
			rt=content+"��ʽת������"+e.getMessage();
			
		}
		return rt;
	}
	
	/**
	 * ������װ��Ϊ����Ҵ�д
	 * ���ת��ʧ��,������ԭֵ
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param content
	 * @return
	 */
	private String numberToRMB(String content){
		try {
			double val=Double.parseDouble(content);
			return D2C.numberToRMB(val, true);
			
		} catch (Exception e) {
			return content;
		}
		
	}
	
	/**
	 * ����ָ������������������,���ɵ�Ԫ������
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param count					��������
	 * @param isMerge				��Ԫ���Ƿ����ϲ�
	 * @param isNumber				��Ԫ�������Ƿ�������
	 * @param cellLen				��Ԫ����
	 * @param refMergeCol			�ϲ�ʱ�Ĳ��������,����ָ�����
	 * @return
	 */
	public static CellDes[] genCellDes(int count,boolean isMerge,boolean isNumber,int cellLen,int... refMergeCol){
		CellDes[] mergeDes=new CellDes[count];
		
		for (int i = 0; i < count; i++) {
			mergeDes[i]=new CellDes(isMerge,isNumber,cellLen,refMergeCol);
		}
		
		return mergeDes;
	}
	
	/**
	 * ����ָ������������������,���ɵ�Ԫ������
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param count					��������
	 * @param isMerge				��Ԫ���Ƿ����ϲ�
	 * @param isNumber				��Ԫ�������Ƿ�������
	 * @param cellLen				��Ԫ����
	 * @param useFormat				�Ƿ�ʹ�����ݸ�ʽ��
	 * @param align					���ֶ��뷽ʽ
	 * @param useBoldFont			�Ƿ�ʹ�ô�����
	 * @param borderSzie			��Ԫ��߿�
	 * @param fontSize				�����С
	 * @param refMergeCol			�ϲ�ʱ�Ĳ��������,����ָ�����
	 * @return
	 */
	public static CellDes[] genMergeDes(int count,boolean isMerge,boolean isNumber,int cellLen,boolean useFormat,
			String align,boolean useBoldFont,int borderSzie,int fontSize,int... refMergeCol){
		
		CellDes[] mergeDes=new CellDes[count];
		
		for (int i = 0; i < count; i++) {
			mergeDes[i]=new CellDes();
			mergeDes[i].isMerge=isMerge;
			mergeDes[i].isNumber=isNumber;
			mergeDes[i].cellLen=cellLen;
			mergeDes[i].useFormat=useFormat;
			mergeDes[i].align=align;
			mergeDes[i].useBoldFont=useBoldFont;
			mergeDes[i].borderSzie=borderSzie;
			mergeDes[i].fontSize=fontSize;
			mergeDes[i].refMergeCol=refMergeCol;
			
		}
		return mergeDes;
	}
	
	/**
	 * ����ʵ������ȡ������
	 * ȡ�õ�������ʵ��������ڵ��scΪkey����HashMap
	 * 
	 * ���ʵ�����ǵ�������,��������ʽΪString[],˳��Ϊʵ�����и��������˳��
	 * ���ʵ�����Ƕ�������,��������ʽΪList<String[]>,һ����¼String[]��˳��Ϊʵ�����и��������˳��
	 * 
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param prpRootId
	 * @return
	 * @throws Exception
	 */
	public static HashMap gainParsedData(String prpRootId) throws Exception{
		
		if(prpRootId==null||prpRootId.equals("")){
			throw new Exception("ָ���Ĵ�ӡʵ������GUIDΪ��!");
		}
		
		ExcelParmSeter parmSeter =new ExcelParmSeter();										
		PMprpRoot prpRoot=(PMprpRoot)ABParmFactory.Factory.getItemByGUID(prpRootId);		//��ȡʵ������
		if(prpRoot==null){
			throw new Exception("δ��ȡ�ô�ӡʵ������! ��ӡʵ������GUID="+prpRootId);
		}
		
		HashMap parsedData=parmSeter.gainParsedData(prpRoot);								//����ʵ��������ȡ����
		if(parsedData==null){
			throw new Exception("δ�ܽ������ɴ�ӡĿ�����!");
		}
		
		return parsedData;
	}
	
	/**
	 * �����б�ָ���еĺϼ�
	 * ָ���еĺϼ���double[]��ʽ����
	 * 
	 * ���sourceList��һ����¼ΪString[5],sumIndex=0,1,3
	 * ���ʾ����String[0],String[1]��String[3]�ĺϼ�
	 * ����double[3]
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param sourceList			Ҫ���кϲ�������б�
	 * @param sumIndex				ָ����Щ����Ҫ����ϼ�.
	 * @return						ָ���еĺϼ���double[]��ʽ����
	 * @throws Exception
	 */
	public static double[] genSumD(List<String[]> sourceList,int...sumIndex) throws Exception{
		
		if(sourceList==null||sourceList.size()==0){
			throw new Exception("Ҫ��͵������б�Ϊ��!");
		}
		
		if(sumIndex==null||sumIndex.length==0){
			throw new Exception("δָ��Ҫ��͵�������!");
		}
		
		double[] sumResult=new double[sumIndex.length];
		
		for (int i = 0; i < sourceList.size(); i++) {
			String[] aLine=sourceList.get(i);
			
			for (int j = 0; j < sumResult.length; j++) {
				try {
					double aData=Double.parseDouble(aLine[sumIndex[j]]);
					sumResult[j]=sumResult[j]+aData;
				} catch (Exception e) {
					continue;//�������ת�ʹ���,������������,����������һ��
				}
			}
		}
		
		return sumResult;
	}
	
	/**
	 * �����б�ָ���еĺϼ�
	 * ָ���еĺϼ���String[]��ʽ����
	 * 
	 * ���sourceList��һ����¼ΪString[5],sumIndex=0,1,3
	 * ���ʾ����String[0],String[1]��String[3]�ĺϼ�
	 * ����String[3]
	 * @comments	:
	 * @author   	:�ο� 2012-8-9
	 *
	 * @param sourceList			Ҫ���кϲ�������б�
	 * @param sumIndex				ָ����Щ����Ҫ����ϼ�.
	 * @return						ָ���еĺϼ���String[]��ʽ����
	 * @throws Exception
	 */
	public static String[] genSumS(List<String[]> sourceList,int...sumIndex) throws Exception{
		
		double[] sumD=genSumD(sourceList,sumIndex);
		
		int size=sumD.length;
		String[] sumS=new String[size];
		
		
		for (int i = 0; i < size; i++) {
			sumS[i]=sumD[i]+"";
		}
		
		return sumS;
	}
	
	
	
	
}
