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
 * @author 任凯   	2012-07-31 
 * @comments: 		该类的主体内容拷贝了UBExcelOper类
 * 					该类提供了操作Excel的各种方法,具体使用,请查看方法说明
 * 					注意:操作Excel表格时,首先要生成该类的实例,并调用openExcleByMode方法
 * 					操作完毕后,以及程序异常时,都需要调用closeExcle方法保存Excel文件,否则文件会损坏
 * 
 * 					该类追加了方法，完成特定格式的Excle文件的写入。如合并单元格，合计等。
 * 
 */
public class ExcelOperPlus {
	

	private WritableWorkbook writeWorkbook;
	private Workbook readWorkbook;
	
	char sheetMode='N';	//sheet页的编辑模式,默认为新建模式 
						//N:新建模式;U:追加数据模式,如果sheet页不存在,则等同N模式;F:强制模式,如果sheet页不存在,则提示错误,否则追加数据
	
	
	private int order=1;
	public int currentRow=0;
	
	private WritableCellFormat numCellFmt = new WritableCellFormat(NumberFormats.DEFAULT);			//数字单元格
	
	private WritableFont cellFont = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false); //单元格字体
	private WritableCellFormat cellFmt = new WritableCellFormat(cellFont); 					 	//单元格格式
	
	
	
	/**
	 * 默认构造函数
	 */
	public ExcelOperPlus(){
		initCell();//初始化单元格格式
	}
	
	/**
	 * 构造函数
	 * @param fileMod		N:表示创建新文件;
	 * 						U:读取现有文件.如果没有,则创建新文件;
	 * 						F:读取现有文件.如果没有,则返回错误-1;
	 * @param sheetMod		N:表示创建新sheet页;
	 * 						U:读取现有sheet页,如果没有,在调用写法方法时创建新sheet页
	 * 						F:读取现有sheet页,如果没有,在调用写法方法时返回错误-1
	 * @param path			Excel文件名,全路径
	 * @throws Exception 
	 */
	
	public ExcelOperPlus(char fileMod,char sheetMod,String path) throws Exception{
		openExcleByMode(fileMod,sheetMod,path);
		initCell();
	}
	
	//初始化单元格格式
	private void initCell(){
		try {
			cellFmt.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author: 任凯 2011-1-17
	 * @throws IOException 
	 * @throws WriteException 
	 * @comments:	关闭并保存excel
	 *				增删改查操作后,或者程序出错后,都要关闭,否则文件会损坏.
	 */
	public  void closeExcle() throws Exception{
		if(writeWorkbook!=null){
			//有sheet页时才能写,否则会出错
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
	 * @author: 任凯 2011-1-17
	 * @comments:		打开excel
	 * @param path		Excel文件名,全路径
	 * @param sheetName 需要指定创建的sheet名,可以是多个,并且数目任意
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
	 * @author: 任凯 2011-1-17
	 * @comments:			通过指定模式打开或创建Excel文件
	 * @param fileMod		N:表示创建新文件;
	 * 						U:读取现有文件.如果没有,则创建新文件;
	 * 						F:读取现有文件.如果没有,则抛出异常;
	 * 
	 * @param sheetMod		N:表示创建新sheet页;
	 * 						U:读取现有sheet页,如果没有,在调用写法方法时创建新sheet页
	 * 						F:读取现有sheet页,如果没有,在调用写法方法时抛出异常;
	 * 
	 * @param path			Excel文件名,全路径
	 */
	public  void openExcleByMode(char fileMod,char sheetMod,String path)throws Exception{
		try {
			if(path==null||path.equals("")){
				throw new Exception("创建Excel对象失败，文件名为空!");
			}
			
			if(!path.endsWith(".xls")){
				path=path+".xls";
			}
			
			sheetMode=sheetMod;
			
			File file = new File(path);
			if(fileMod=='N'){//创建新文件
				writeWorkbook=Workbook.createWorkbook(file);
				
			}else if(fileMod=='U'){//读取已有文件,如果没有,则创建
				if(!file.exists()){//如果不存在,则创建新文件
					writeWorkbook=Workbook.createWorkbook(file);
				}else{				//如果存在,则读出
					Workbook readWorkbook=Workbook.getWorkbook(file);			//读出Excel
					writeWorkbook=Workbook.createWorkbook(file,readWorkbook);	//将Excel转换为可读写的形式
				}
				
			}else if(fileMod=='F'){//读取已有文件,如果不存在,则提示错误
				if(!file.exists()){//如果不存在,则返回错误-2
					throw new Exception("文件不存在! path="+path);
				}else{				//如果存在,则读出
					Workbook readWorkbook=Workbook.getWorkbook(file);			//读出Excel
					writeWorkbook=Workbook.createWorkbook(file,readWorkbook);	//将Excel转换为可读写的形式
				}
			}else{//模式不能识别,则默认新建
				writeWorkbook=Workbook.createWorkbook(file);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//将List中的所有数据按顺序写入指定sheet中的指定行
	public  void writeLine(Object[] valueList,int rowNum,String sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	
	
	//将List中的所有数据按顺序写入指定sheet中的指定行
	//写入数据时,区分字符型和数字型
	public  void writeLine(Object[] valueList,String[] dataType,int rowNum,String sheetName) throws Exception{
		
		String writeVal="";
		Label label;	
		jxl.write.Number number;
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
		}
		
		try{
			for (int i = 0; i < valueList.length; i++) {
				writeVal=(String)valueList[i];
				if(dataType == null){
					label = new Label(i,rowNum,writeVal);
					wSheet.addCell(label); 		
				}else{
					String t = dataType[i];
					if(isNumber(t)){//如果是数值型的话，按照数值数据存储
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
	
	//将List中的所有数据按顺序写入指定sheet中的指定行
	public void writeLine(List<String> valueList,int rowNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	 * @author: 任凯 2011-3-3
	 * @comments:
	 * @param valueList
	 * @param rowNum  写入的起始行
	 * @param colNum  写入的起始列
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
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	
	//将List中的所有数据按顺序写入指定sheet中的指定行
	public  void writeLine(List<String> valueList,int rowNum,String sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	
	
	//将数组中的所有数据按顺序写入指定sheet中的指定行
	public  void writeLine(String[] valueList,int rowNum,int sheetName) throws Exception{
		String writeVal="";
		Label label;	
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	 * 向sheet中的一个Cell中写入数据
	 * @comments	:
	 * @author   	:任凯 2011-11-3
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
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	 * 向sheet中的一个Cell中写入数据 可以指定写入数据的类型 
	 * dataType=String,表示以字符形式写入,dataType=num,表示以数字形式写入
	 * @comments	:
	 * @author   	:任凯 2011-11-3
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
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
		}
		
		try{
				
			if(dataType == null){
				label = new Label(colNum-1,rowNum-1,writeVal);
				wSheet.addCell(label); 		
			}else{
				String t = dataType;
				if(isNumber(t)){//如果是数值型的话，按照数值数据存储
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
	 * @author: 任凯 2011-1-15
	 * @comments:			将二维List写入指定Excel的指定sheet中
	 * @param valueList 	必须是String[][]
	 * @param sheetName		要写入的sheet名
	 * @return				0:正常;-1:异常
	 * @throws Exception 
	 */
	public void writeExcel(String[][] valueArray,String sheetName) throws Exception{
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("未指定sheetName!");
		}
		int writeNum=0;
		switch(sheetMode){
			case 'N'://新建模式
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueArray,sheetName,0);
				
			case 'U'://追加模式
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueArray,sheetName,writeNum);
				
			case 'F'://强制模式
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("未能取得指定sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcel(valueArray,sheetName,writeNum);
				}
				
			default://模式设置错误,则按'N'处理
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueArray,sheetName,0);
		}
		
	}
	
	/**
	 * 
	 * @author: 任凯 2011-1-15
	 * @comments:			将二维List写入指定Excel的指定sheet中
	 * @param valueList 	必须是List<String[]>
	 * @param sheetName		要写入的sheet名
	 * @return				0:正常;-1:异常
	 * @throws Exception 
	 */
	public void writeExcel(List<String[]> valueList,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("未指定sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
			case 'N'://新建模式
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueList,sheetName,0);
			case 'U'://追加模式
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueList,sheetName,writeNum);
			case 'F'://强制模式
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("未能取得指定sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcel(valueList,sheetName,writeNum);
				}
			default://模式设置错误,则按'N'处理
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcel(valueList,sheetName,0);
		}
	}
	/**
	 * 
	 * @author: 任凯 2011-1-15
	 * @comments:			将二维List写入指定Excel的指定sheet中
	 * @param dataType 		必须是String[],表示每一列的类型
	 * @param valueList 	必须是List<String[]>
	 * @param sheetName		要写入的sheet名
	 * @return				0:正常;-1:异常
	 * @throws Exception 
	 */
	public void writeExcel(List<String[]> valueList,String[] dataType,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("未指定sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
		case 'N'://新建模式
			removeSheet(sheetName);
			creatNewSheet(sheetName);
			writeExcel(valueList,dataType,sheetName,0);
		case 'U'://追加模式
			writeNum=getSheetWriteRow(sheetName);
			writeExcel(valueList,dataType,sheetName,writeNum);
		case 'F'://强制模式
			if(writeWorkbook.getSheet(sheetName)==null){
				throw new Exception("未能取得指定sheet! sheetName="+sheetName);
			}else{
				writeNum=getSheetWriteRow(sheetName);
				writeExcel(valueList,dataType,sheetName,writeNum);
			}
		default://模式设置错误,则按'N'处理
			removeSheet(sheetName);
			creatNewSheet(sheetName);
			writeExcel(valueList,dataType,sheetName,0);
		}
	}
	 
	/**
	 * 
	 * @author: 任凯 2011-1-15
	 * @comments:			将二维List写入指定Excel的指定sheet中
	 * @param valueList 	必须是List<List<String>>
	 * @param sheetName		要写入的sheet名
	 * @return				0:正常;-1:异常
	 * @throws Exception 
	 */
	public void writeExcelLL(List<List<String>> valueList,String sheetName) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("未指定sheetName!");
		}
		
		int writeNum=0;
		switch(sheetMode){
			case 'N'://新建模式
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcelLL(valueList,sheetName,0);
				
			case 'U'://追加模式
				writeNum=getSheetWriteRow(sheetName);
				writeExcelLL(valueList,sheetName,writeNum);
				
			case 'F'://强制模式
				if(writeWorkbook.getSheet(sheetName)==null){
					throw new Exception("未能取得指定sheet! sheetName="+sheetName);
				}else{
					writeNum=getSheetWriteRow(sheetName);
					writeExcelLL(valueList,sheetName,writeNum);
				}
				
			default://模式设置错误,则按'N'处理
				removeSheet(sheetName);
				creatNewSheet(sheetName);
				writeExcelLL(valueList,sheetName,0);
		}
		
	}
	
	/**
	 * 向指定sheet的指定单元格Cell中写入数据
	 * @comments	:
	 * @author   	:任凯 2011-11-3
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
			throw new Exception("未指定sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("未能取得指定sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		writeCell(writeVal,rowNum,colNum,sheetName);
	}
	/**
	 * 向指定sheet的指定单元格Cell中写入数据,可以指定写入的类型
	 * @comments	:
	 * @author   	:任凯 2011-11-3
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
			throw new Exception("未指定sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("未能取得指定sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		writeCell(writeVal,rowNum,colNum,sheetName,dataType);
		
	}
	
	/**
	 * @author 任凯 2010-01-15
	 * @comments			改变sheet页的操作模式
	 * @param sheetM		N:表示创建新sheet页;
	 * 						U:读取现有sheet页,如果没有,在调用写法方法时创建新sheet页
	 * 						F:读取现有sheet页,如果没有,在调用写法方法时返回错误-1
	 */
	public void changeSheetMode(char sheetM){
		sheetMode=sheetM;
	}
	
	
	/**
	 * @author 任凯 2010-01-15
	 * @comments 		将指定sheet页中的数据以列表形式读出
	 * @param sheetName
	 * @return
	 */
	public List<String[]> readExcel(String sheetName){
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			return null;
		}
		
		int rowNum = wSheet.getRows();		//数据行数
		int columnNum = wSheet.getColumns();//数据列数
		
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
	 * 将二维数组写入指定Excel的指定sheet中,从指定的firstRow开始写入
	 * 数组必须是String类型
	 */
	private void writeExcel(String[][] valueArray,String sheetName,int firstRow) throws Exception{
		if(valueArray==null){
			throw new Exception("指定的写入数据为空!");
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
	 * 将List写入指定Excel的指定sheet中,从指定的firstRow开始写入
	 * List中的每个元素必须是String[]类型
	 */
	public void writeExcel(List<String[]> valueList,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("指定的写入数据为空!");
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
	 * 将List写入指定Excel的指定sheet中,从指定的firstRow开始写入
	 * 写入数据时,区分字符型和数字型
	 * List中的每个元素必须是String[]类型
	 */
	public void writeExcel(List<String[]> valueList,String[] dataType,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("指定的写入数据为空!");
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
	 * 将二维List写入指定Excel的指定sheet中,从指定的firstRow开始写入
	 * List必须是String类型
	 */
	private void writeExcelLL(List<List<String>> valueList,String sheetName,int firstRow) throws Exception{
		if(valueList==null){
			throw new Exception("指定的写入数据为空!");
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
	 * 向指定的区域打印2维列表
	 * @author: 任凯 2011-3-3
	 * @comments:
	 * @param valueList
	 * @param sheetName
	 * @param firstRow
	 * @return
	 * @throws Exception 
	 */
	public void writeArea(List<String[]> valueList,String sheetName,int firstRow,int firstCol) throws Exception{
		if(valueList==null){
			throw new Exception("指定的写入数据为空!");
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
	 * @author: 任凯 2011-1-17
	 * @comments:创建新的sheet页 可以创建多个
	 * @param sheetName	
	 */
	public void creatNewSheets(String...sheetName){
		if(sheetName==null){//如果sheetName为空,又是新建文件,则必须创建一个默认sheet页
			writeWorkbook.createSheet("newSheet1", 0);
		}else{				//如果sheetName不空,则按指定名称创建sheet页
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
	 * @author: 任凯 2011-1-17
	 * @comments:创建新的sheet页
	 * @param sheetName
	 */
	public void creatNewSheet(String sheetName){
		int sheetCount=writeWorkbook.getNumberOfSheets();
		
		if(sheetName==null||sheetName.equals("")){
			writeWorkbook.createSheet("newSheet1",sheetCount);
		}
		if(writeWorkbook.getSheet(sheetName)==null){//如果sheet页不存在,则创建
			writeWorkbook.createSheet(sheetName,sheetCount);
		}
		
	}
	
	/**
	 * 
	 * @author: 任凯 2011-1-17
	 * @comments:清空sheet
	 * @param sheetName
	 */
	public void clearSheet(String sheetName){
		if(sheetName==null||sheetName.equals("")){
			return;
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(wSheet==null){//如果sheet页不存在,则忽略
			return;
		}
		int rowNum = wSheet.getRows();
		//清空数据
		for (int i=rowNum-1;i>=0;i--){
			wSheet.removeRow(i);
		}
		
	}
	
	
	/**
	 * 
	 * @author: 任凯 2011-1-17
	 * @comments:删除sheet
	 * @param sheetName
	 */
	public void removeSheet(String sheetName){
		if(sheetName==null||sheetName.equals("")){
			return;
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(wSheet==null){//如果sheet页不存在,则忽略
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
	
	//得到指定sheet的写入行的位置,即最后一行数据位置
	private int getSheetWriteRow(String sheetName){
		WritableSheet wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){//如果sheet页不存在,则创建
			writeWorkbook.createSheet(sheetName,0);
			return 0;
		}
		int rowNum = wSheet.getRows();	//找到最后一行数据的位置
		
		return rowNum;
		
	}
	
	//判断要写入的类型是否是数字型
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
	//以下代码主要为数据的Excel特定格式输出使用
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 将列表数据以指定格式写入Excle,同时按照设定合并单元格
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param valueList			写入的列表数据
	 * @param header			列表的列标题
	 * @param sheetName			写入的sheet名
	 * @param firstRow			写入的起始行号
	 * @param mergeColDes		每一列的单元格描述,包括合并参照信息,单元格格式等
	 * @param colOffset			数据整体偏移量,可起到整体右移的功能
	 * @param orderColDes		序号列单元格描述,如果为空,则表示不需要序号列
	 * @param subTotalDes		小计单元格描述,如果为空,则表示不需要小计
	 * @throws Exception
	 */
	public void writeExcelAndMer(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,
									int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		
		if(colOffset<0){
			throw new Exception("列偏移量colOffset不能小于0!");
		}
		
		//将列表数据写入Excle,同时获得需要合并的单元格序列
		ArrayList<CellDes> mergeList=writeExcel(valueList,header,sheetName,firstRow,mergeColDes,colOffset,orderColDes,subTotalDes);
		
		if(mergeList==null||mergeList.size()==0){
			return;
		}
		//合并单元格
		for (int i = 0; i < mergeList.size(); i++) {
			CellDes aMergeDes=mergeList.get(i);
			mergeCell(aMergeDes.colStart,aMergeDes.rowStart,aMergeDes.colEnd,aMergeDes.rowEnd,sheetName,colOffset);
		}
		
	}
	/**
	 * 将列表数据以指定格式写入Excle,同时按照设定合并单元格
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param valueList			写入的列表数据
	 * @param header			列表的列标题
	 * @param sheetName			写入的sheet名
	 * @param firstRow			写入的起始行号
	 * @param mergeColDes		每一列的单元格描述,包括合并参照信息,单元格格式等
	 * @param colOffset			数据整体偏移量,可起到整体右移的功能
	 * @param orderColDes		序号列单元格描述,如果为空,则表示不需要序号列
	 * @throws Exception
	 */
	public void writeExcelAndMer(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,
			int colOffset,CellDes orderColDes) throws Exception{
		
		writeExcelAndMer(valueList,header,sheetName,firstRow,mergeColDes,colOffset,orderColDes,null);
		
	}
	
	
	/**
	 * 将列表数据以指定格式写入Excle
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param valueList			写入的列表数据
	 * @param header			列表的列标题
	 * @param sheetName			写入的sheet名
	 * @param firstRow			写入的起始行号
	 * @param mergeColDes		每一列的单元格描述,包括合并参照信息,单元格格式等
	 * @param colOffset			数据整体偏移量,可起到整体右移的功能
	 * @param orderColDes		序号列单元格描述,如果为空,则表示不需要序号列
	 * @return					需要合并的单元格描述列表
	 * @throws Exception
	 */
	private ArrayList<CellDes> writeExcel(List<String[]> valueList,String[] header,String sheetName,int firstRow,CellDes[] mergeColDes,int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		if(valueList==null){
			throw new Exception("写入的数据为空！");
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
		}
		
		boolean addOrder=false;														//是否添加序号列标识
		
		if(orderColDes!=null){															//序号描述不空,序号列标识为真
			addOrder=true;
			if(colOffset<1){															//添加序号列时,列偏移量至少为1.
				colOffset=1;
			}
		}
		
		try{
			setColLenth(wSheet,mergeColDes,colOffset);									//设置列宽
			
			if(header!=null){															//如果列表表头数组不空,则写入表头行
				
				CellDes[] headerColDes=genHeaderColDes(mergeColDes);
				
				if(addOrder){
					writeALine(wSheet,header,firstRow,headerColDes,colOffset,"序号");	//序号列标识为真,则写序号列
				}else{
					writeALine(wSheet,header,firstRow,headerColDes,colOffset,null);	//序号列标识为假,则不写序号列
				}
				firstRow=firstRow+1;													//写入表头行后,数据行起始行需要+1
			}
			
			
			//写数据时,首先要将第1行数据写入,并进行列描述数据初始化
			//这样,后面的每一行数据会与前一条相应列及参照列的数据比较,决定是否合并
			String[] writeVal =null;
			writeVal=(String[])valueList.get(0);
			
			if(addOrder){
				writeALine(wSheet,writeVal,firstRow,mergeColDes,colOffset,"1");		//带序号 写第1行数据,不进行比较
				
				int[] refCol=orderColDes.refMergeCol;									//首先记录序号列的参照列,因为下面要进行克隆
				
				orderColDes=AFUtility.cloneObjByXstream(mergeColDes[0]);				//这里克隆第一列的描述,为了使添加的序号列与第一列有相同的格式
																						//这里没有采取逐项赋值的方法,是为了避免列描述改变后,如添加了格式属性,这里还需要改代码
				orderColDes.refMergeCol=refCol;
				orderColDes.colStart=-1;												//序号列列序号-1是相对位置,相对实际数据第1列(第1列序号为0)
				orderColDes.colEnd=-1;										
				orderColDes.rowStart=firstRow;
				orderColDes.rowEnd=firstRow;
				
			}else{
				writeALine(wSheet,writeVal,firstRow,mergeColDes,colOffset,null);	//不带序号 写第1行数据,不进行数据处理
			}
			
			
			for (int i = 0; i < mergeColDes.length; i++) {								//根据第1行数据,进行列描述数据初始化
				if(mergeColDes[i]==null){
					mergeColDes[i]=new CellDes();
				}
				
				mergeColDes[i].lastData=writeVal[i]+"";									//使用单元格描述中的lastData属性记录上一行数据该列的值,用于合并时的比较.此时第1行数据已经写入
				if(mergeColDes[i].isMerge){
					mergeColDes[i].colStart=i;
					mergeColDes[i].colEnd=i;
					mergeColDes[i].rowStart=firstRow;
					mergeColDes[i].rowEnd=firstRow;
				}
			}
			
			boolean addSubTotla=false;
			if(subTotalDes!=null&&subTotalDes.doSubtotal){								//如果小计描述不空并且subTotalDes.doSubtotal==true时,表示通过程序计算小计
				addSubTotla=true;
				subTotalDes.rowStart=0;
				subTotalDes.rowEnd=0;
			}
			
			ArrayList<CellDes> mergeQue=new ArrayList<CellDes>();						//用于保存合并单元格序列
			
			boolean isWriteSubTotal=false;
			int writeRow=firstRow+1;
			
			for (int i = 1; i < valueList.size(); i++) {								//由于已经写入了第一行数据,所以循环时从第2行数据开始
				writeVal=(String[])valueList.get(i);
				
				if(addSubTotla){//如果需要计算小计,则计算小计并写入
					isWriteSubTotal=writeSubTotal(wSheet,writeRow,valueList,writeVal,mergeColDes,subTotalDes,colOffset);//判断是否写入小计行
					if(isWriteSubTotal){												//如果写入了小计行,则当前行号+1
						writeRow=writeRow+1;
					}
				}
				
				boolean[] isMerge=writeLine(wSheet,writeVal,writeRow,mergeColDes,colOffset,orderColDes,subTotalDes);//写每一行数据,并于上一条数据进行比较,决定是否合并
				genMergeQue(isMerge,mergeColDes,mergeQue,isWriteSubTotal);								//生成合并单元格列表
				
				if(addOrder){															//如果添加了序号列,则生成序号需要合并的队列
					genMergeQue(isMerge,orderColDes,mergeQue,isWriteSubTotal);
				}
				writeRow=writeRow+1;
			}
			
			if(addSubTotla){															//如果需要计算小计,则写入最后一组值的小计
				writeLastSubTotal(wSheet,writeRow,valueList,mergeColDes,subTotalDes,colOffset);
				writeRow=writeRow+1;
			}
			
			addLastMerge(mergeColDes,mergeQue);											//写完最后一行数据后,处理列合并描述
			
			if(addOrder){
				addLastMerge(new CellDes[]{orderColDes},mergeQue);						//如果需要序号列,则写完最后一行数据后,处理序号列合并描述
			}
			
			currentRow=writeRow;														//Excle的当前行号
			
			return mergeQue;
			
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
		
	}
	
	//表头行列描述,由数据列描述克隆而来
	private CellDes[] genHeaderColDes(CellDes[] colDes) throws IOException{
		
		CellDes[] headerColDes=AFUtility.cloneObjByXstream(colDes);
		
		for (int i = 0; i < headerColDes.length; i++) {
			headerColDes[i].useFormat=true;					//使用内容格式化
			headerColDes[i].useBoldFont=true;				//使用粗体字
		}
		
		return headerColDes;
	}
	
	/**
	 * 写入一行数据
	 * @comments	:
	 * @author   	:任凯 2012-8-8
	 *
	 * @param sheetName				Excel的sheet名
	 * @param values				要写入的数据数组
	 * @param rowNum				写入的行号
	 * @param colDes				写入行每一列的单元格描述
	 * @param colOffset				写入列偏移量,可以起到整体右移的功能
	 * @throws Exception
	 */
	public void writeLine(String sheetName,String[] values,int rowNum,CellDes[] colDes,int colOffset) throws Exception{
		
		try{
			
			if(values==null){
				throw new Exception("写入的数据为空！");
			}
			
			WritableSheet wSheet;
			wSheet=writeWorkbook.getSheet(sheetName);
			if(wSheet==null){
				throw new Exception("未能取得指定sheet! sheetName="+sheetName);
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
	
	//设置写入Excle单元格的列宽
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
	 * 根据每一列的单元格描述,将需要合并的单元格加入合并单元格队列
	 * 单元格描述mergeColDes通过rowStart和rowEnd属性来记录该列从第几行合并到第几行
	 * 
	 * 如果某列需要合并,则将该列的描述的rowEnd+1,直到不需要合并时,完成一个合并单元格的计算
	 * 这时将rowStart和rowEnd都设为rowEnd+1,重新开始计算下一个需要合并的单元格的rowStart和rowEnd
	 * @comments	:
	 * @author   	:任凯 2012-8-8
	 *
	 * @param isMerge					记录每一列是否合并的数组
	 * @param mergeColDes				每一列的单元格描述
	 * @param mergeQue					合并单元格队列
	 * @throws Exception	
	 */
	private void genMergeQue(boolean[] isMerge,CellDes[] mergeColDes,ArrayList<CellDes> mergeQue,boolean addSubTotal) throws Exception{
		
		CellDes aNewMerge=null;
		CellDes mergeQueElm=null;
		
		for (int j = 0; j < isMerge.length; j++) {						//依次处理每个单元格
			aNewMerge=mergeColDes[j];
			if(aNewMerge.isMerge==false){								//如果该单元格不需要合并处理,则跳过
				continue;
			}
			
			if(isMerge[j]==true){										//如果某列需要合并,则将该列的描述的rowEnd+1,直到判断不需要合并时,完成一个合并单元格的计算
				aNewMerge.rowEnd=aNewMerge.rowEnd+1;
			}else{														//如果某列不需要合并,则需要生成一个合并单元格描述
				
				if(aNewMerge.rowStart!=aNewMerge.rowEnd){				//当rowStart!=rowEnd时,才需要真正生成一个合并单元格描述
					mergeQueElm=AFUtility.cloneObjByXstream(aNewMerge);	//这里clone一个单元格描述,为了避免数据被改写
					mergeQue.add(mergeQueElm);							//将合并单元格描述加入合并单元格队列
				}
				int newRowStart;						
				if(addSubTotal){										//需要添加小计行时
					newRowStart=aNewMerge.rowEnd+2;						//将当前列的合并单元的rowStart和rowEnd都设为rowEnd+1,重新开始计算下一个需要合并的单元格
				}else{													//不需要添加小计行时
					newRowStart=aNewMerge.rowEnd+1;						//将当前列的合并单元的rowStart和rowEnd都设为rowEnd+1,重新开始计算下一个需要合并的单元格
				}
				
				aNewMerge.rowStart=newRowStart;
				aNewMerge.rowEnd=newRowStart;
				
			}
		}
	}
	
	/**
	 * 生成序号列的合并单元格描述,并加入合并单元格队列
	 * @comments	:
	 * @author   	:任凯 2012-8-8
	 *
	 * @param isMerge					记录每一列是否合并的数组
	 * @param orderColDes				序号列的单元格描述
	 * @param mergeQue					合并单元格队列
	 * @param addSubTotal				是否需要添加小计的标识
	 * @throws Exception
	 */
	private void genMergeQue(boolean[] isMerge,CellDes orderColDes,ArrayList<CellDes> mergeQue,boolean addSubTotal) throws Exception{
		
		CellDes mergeQueElm=null;
		
		if(orderColDes!=null){											//序号描述不为空
			int[] refCols=orderColDes.refMergeCol;
			int refCol=0;												//序号列默认的参照列为0;
			
			if(refCols==null){											//如果设置了多个参照列,则只取第1个设置值
				refCol=refCols[0];										//序号列的参照列只取第1个设置值
			}
			
			if(isMerge[refCol]==true){									//如果参照列需要合并,则将序号列的描述的rowEnd+1,直到判断不需要合并时,完成一个合并单元格的计算
				orderColDes.rowEnd=orderColDes.rowEnd+1;
			}else{														//如果参照列不需要合并,则需要生成一个序号合并单元格描述
				
				if(orderColDes.rowStart!=orderColDes.rowEnd){			//当rowStart!=rowEnd时,才需要真正生成一个合并单元格描述
					mergeQueElm=AFUtility.cloneObjByXstream(orderColDes);//这里clone一个单元格描述,为了避免数据被改写
					mergeQue.add(mergeQueElm);							//将合并单元格描述加入合并单元格队列
				}
				
				int newRowStart;						
				if(addSubTotal){										//需要添加小计行时
					newRowStart=orderColDes.rowEnd+2;					//将当前列的合并单元的rowStart和rowEnd都设为rowEnd+2(因为多加了小计一行),重新开始计算下一个需要合并的单元格
				}else{													//不需要添加小计行时
					newRowStart=orderColDes.rowEnd+1;					//将当前列的合并单元的rowStart和rowEnd都设为rowEnd+1,重新开始计算下一个需要合并的单元格
				}
				orderColDes.rowStart=newRowStart;
				orderColDes.rowEnd=newRowStart;
				
			}
			
		}
		
	}
	
	/**
	 * 计算一组数据的小计并写入
	 * @comments	:
	 * @author   	:任凯 2012-8-16
	 *
	 * @param wSheet			Excle的sheet对象
	 * @param writeRowNum		当前写入行号
	 * @param valueList			写入数据列表.(所有数据,用于计算小计)
	 * @param currentVal		当前准备写入的一行数据(用于判断与上一行数据是否为一组)
	 * @param mergeColDes		列单元格描述(使用其中的属性取得上一行每一列的值)
	 * @param sumTotalDes		合并单元格描述(主要指定合并的参照列,及小计分组的依据,同时指定哪些列需要计算小计)
	 * @param colOffset			列整体偏移量
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
		
		boolean doSum=checkSumVal(currentVal,mergeColDes,sumTotalDes);//检查当前一行的值是否和上一行属于计算小计时的一组
		
		boolean writeSubTotal=false;
		if(doSum){														
			sumTotalDes.rowEnd=sumTotalDes.rowEnd+1;					//如果属于一组,则将合计单元格描述的行终止号+1(一组小计还没有统计完成)
			writeSubTotal=false;										//返回false,表示还没有写入小计行
		}else{															//如果不属于一组,则开始计算小计,并写入
			
			int[] refCol=sumTotalDes.refMergeCol;						//统计小计的参照列,即小计的分组依据
			int subTotalCol=0;
			if(refCol!=null||refCol.length>0){
				subTotalCol=refCol[0];									//如果设置了多个参照了,则只取第1个参照列
			}
			String[] lastSubVal=valueList.get(sumTotalDes.rowEnd);		//取得一组小计的最后一行的值(如果同时写入小计行,可以起到多层次计算小计的功能)
			String[] sumTotalLine= genSubTotalLine(valueList,lastSubVal,sumTotalDes);//计算小计值
			sumTotalLine[subTotalCol]="小计 "+lastSubVal[subTotalCol];	//定义小计标签
			writeALine(wSheet,sumTotalLine,writeRowNum,mergeColDes,colOffset,null);	//写入小计行
			
			int newRowStart=sumTotalDes.rowEnd+1;
			sumTotalDes.rowStart=newRowStart;							//小计列描述+1重新开始计算
			sumTotalDes.rowEnd=newRowStart;
			writeSubTotal=true;											//返回true,表示写入了小计行
		}
		
		return writeSubTotal;
	}
	
	/**
	 * 最后一行数据写完后,写入小计
	 * @comments	:
	 * @author   	:任凯 2012-8-16
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
		sumTotalLine[subTotalCol]="小计 "+lastSubVal[subTotalCol];
		writeALine(wSheet,sumTotalLine,writeRowNum,mergeColDes,colOffset,null);	
	}
	
	/**
	 * 计算小计
	 * @comments	:
	 * @author   	:任凯 2012-8-16
	 *
	 * @param valueList
	 * @param lastSubVal
	 * @param sumTotalDes
	 * @return
	 * @throws Exception
	 */
	private String[] genSubTotalLine(List<String[]> valueList,String[] lastSubVal,CellDes sumTotalDes) throws Exception{
		
		String[] sumTotalLine=new String[lastSubVal.length];
		if(sumTotalDes.sumRowWTV){//是否写入小计外的其他列的值(如果同时写入小计行其他列的值,可以起到多层次计算小计的功能)
			for (int i = 0; i < lastSubVal.length; i++) {
				sumTotalLine[i]=lastSubVal[i];
			}
		}
		
		List<String[]> sumList=new ArrayList<String[]>();
		
		int startRow=sumTotalDes.rowStart;			//一组小计的起始行
		int endRow=sumTotalDes.rowEnd;				//一组小计的终止行
		
		
		int[] sumCol=sumTotalDes.sumCol;
		for (int i = startRow; i <=endRow; i++) {
			sumList.add(valueList.get(i));
		}
		
		String[] sumVal=genSumS(sumList,sumCol);	//计算小计值
		
		for (int i = 0; i < sumCol.length; i++) {
			sumTotalLine[sumCol[i]]=sumVal[i];
		}
		
		return sumTotalLine;
	}
	
	/**
	 * 最后一条数据处理完成后,需要调用该方法,将合并描述添加到合并队列中
	 * 否则会丢失部分该合并的单元格
	 * @comments	:
	 * @author   	:任凯 2012-7-31
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
	 * 写入一行数据 不做合并项等处理
	 * @comments	:
	 * @author   	:任凯 2012-8-9
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
			throw new Exception("写入数据列数量与列描述数量不等! 数据列数= "+valCount+" 列描述数量="+colCount);
		}
		
		for (int i = 0; i < valueList.length; i++) {
			writeVal=(String)valueList[i];
			writeCell(wSheet,writeVal,i,rowNum,mergeColDes[i],colOffset);
		}
		
		if(orderVal!=null){//序号列不空,则写序号列
			CellDes orderCellDes=AFUtility.cloneObjByXstream(mergeColDes[0]);//由于序号列是动态生成列,这里让序号列的单元格描述与表格数据的第一列设置相同
			writeCell(wSheet,orderVal,0,rowNum,orderCellDes,colOffset-1);
		}
		
	}
	
	/**
	 * 写一行数据,处理合并单元格描述内容,并将每一列是否要和上一行的数据合并的信息已数组形式返回
	 * @comments	:
	 * @author   	:任凯 2012-8-8
	 *
	 * @param wSheet			要写入的sheet对象
	 * @param values			要写入的一行数据
	 * @param rowNum			写入的行号
	 * @param mergeColDes		数据列表每一列的单元格描述
	 * @param colOffset			写入时的整体偏移量	
	 * @param orderColDes		序号列单元格描述
	 * @param subTotalDes		小计列单元格描述
	 * @return					doMerge[]数组记录当前行的每一列是否要和上一行数据合并
	 * @throws Exception
	 */
	private  boolean[] writeLine(WritableSheet wSheet,String[] values,int rowNum,CellDes[] mergeColDes,int colOffset,CellDes orderColDes,CellDes subTotalDes) throws Exception{
		String writeVal="";
		
		/*处理逻辑简述
		 * doMerge[]数组记录当前行的每一列是否要和上一行数据合并
		 * 
		 * mergeColDes数组中每个元素的:
		 * lastData记录了中记录了上一行数据对应列的值
		 * refMergeCol[]记录了该列数据合并时需要参照的序号,可以是多个.
		 * 如mergeColDes[2].refMergeCol==1,3,则表示数据合并时,不仅要计较mergeColDes[2].lastData的值是否和当前行的值一致
		 * 同时要比较mergeColDes[1],mergeColDes[3]的lastData是否与当前行的对应列一致.
		 * 如果一致,则对应的doMerge[2]赋值为true,表示第3列(相对位置)需要和上一行的对应列合并
		 * 
		 */
		
		boolean[] doMerge=new boolean[mergeColDes.length];							//doMerge[]数组记录当前行的每一列是否要和上一行数据合并,初始值都为false
		try{
			
			for (int i = 0; i < values.length; i++) {									//依次写入当前行每一列的值
				writeVal=values[i];
				writeCell(wSheet,writeVal,i,rowNum,mergeColDes[i],colOffset);			//执行写入
				
				if(writeVal==null){
					continue;
				}
				
				if(mergeColDes[i]==null){
					continue;
				}
				if(checkRefVal(writeVal,values,mergeColDes,mergeColDes[i])){			//检查当前写入值是否要与上一行数据合并
					doMerge[i]=true;
				}
			}
			
			
			if(orderColDes!=null){														//序号描述不为空,则写序号列
				int[] refCols=orderColDes.refMergeCol;
				int refCol=0;															//序号列默认的参照列为0;
				
				if(refCols==null){														//如果设置了多个参照列,则只取第1个设置值
					refCol=refCols[0];													//序号列的参照列只取第1个设置值
				}
				
				//由于序号列是动态加入的列,所以这里需要做特殊处理
				//如果序号列的参照列需要合并,则序号不改变写入
				//如果序号列的参照列不需要合并,则序号+1写入
				//这样,需要合并的序号单元格的值会相同
				//序号列的合并在该方法外处理
				if(doMerge[refCol]==true){												//如果序号列的参照列需要合并,则序号不改变写入
					writeCell(wSheet,order+"",0,rowNum,orderColDes,colOffset-1);
				}else{																	//如果序号列的参照列不需要合并,则序号+1写入
					
					if(!isSubTotalLine(values,subTotalDes)){							//如果不是小计行,则序号+1写入,否则不写入序号,不进行合并
						order=order+1;
						writeCell(wSheet,order+"",0,rowNum,orderColDes,colOffset-1);
						
					}
					
				}
				
			}
			
			//将mergeColDes中每个元素的lastData值改为当前值,
			//由于上面的比较方法checkRefVal会依赖于mergeColDes中的值
			//所以在上面循环完成之后才能改变列定义数组mergeColDes中的值.
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
	 * 判断是否是小计行
	 * 
	 * 这里与后台约定,如果指定列的值以"小计"开头,则表示该行数据为小计
	 * 这种清空下,小计行的数据由后台传回,以普通数据的形式写入,但小计行做为单独的一行,不进行合并,不写入序号
	 * @comments	:
	 * @author   	:任凯 2012-8-16
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
		
		if(refVal.startsWith("小计")){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 判断当前单元格是否要与上一行的对应单元格合并
	 * @comments	:
	 * @author   	:任凯 2012-8-8
	 *
	 * @param writeVal					当前单元格的写入值
	 * @param valueList					当前写入行数据
	 * @param mergeColDes				写入列表数据的列单元格描述数组
	 * @param currentMergeDes			当前单元格描述
	 * @return
	 */
	private boolean checkRefVal(String writeVal,String[] valueList,CellDes[] mergeColDes,CellDes currentMergeDes){
		
		if(currentMergeDes.isMerge==false){
			return false;
		}
		
		int[] ref=currentMergeDes.refMergeCol;						//当前单元格的参照列
		if(ref==null||ref.length==0){								//没有依赖的列
			if(writeVal.equals(currentMergeDes.lastData)){			//没有依赖的列时，仅比较当前写入值与上一行对应列的值
				return true;
			}else{
				return false;
			}
		}
		
		
		if(!writeVal.equals(currentMergeDes.lastData)){				//有依赖的列时，先比较当前列的内容
			return false;
		}
		
		boolean rt=true;
		for (int i = 0; i < ref.length; i++) {						//依次比较参照列的值
			String val=(String)valueList[ref[i]];					//参照列当前行的值
			String lastDate=mergeColDes[ref[i]].lastData;			//参照列上一行的值
			
			if(val==null){
				continue;
			}
			if(!val.equals(lastDate)){								//只要有一列不相等,则返回false,表示不合并
				rt=false;
				break;
			}
		}
		return rt;
	}
	
	/**
	 * 判断一行数据是否与上一行为一组计算小计
	 * @comments	:
	 * @author   	:任凯 2012-8-16
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
		
		int[] ref=sumTotalDes.refMergeCol;						//小计的参照列
		if(ref==null||ref.length==0){							//没有依赖的列
			return false;
		}
		
		boolean rt=true;
		for (int i = 0; i < ref.length; i++) {						//依次比较参照列的值
			String val=(String)currentVal[ref[i]];					//参照列当前行的值
			String lastDate=listColDes[ref[i]].lastData;			//参照列上一行的值
			
			if(val==null){
				continue;
			}
			if(!val.equals(lastDate)){								//只要有一列不相等,则返回false,表示不与上一行计算合计
				rt=false;
				break;
			}
		}
		return rt;
	}
	
	/**
	 * 合并单元格
	 * 如果起始列号与终止列号相等,并且起始行号和终止行号相等,说明是同一个单元格,则不用进行合并
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param colStart				起始列号
	 * @param rowStart				起始行号
	 * @param colEnd				终止列号
	 * @param rowEnd				终止行号
	 * @param sheetName				Excel的sheet名
	 * @param colOffset				列整体偏移量
	 * @throws Exception
	 */
	public void mergeCell(int colStart,int rowStart,int colEnd,int rowEnd,String sheetName,int colOffset) throws Exception{
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		if(wSheet==null){
			throw new Exception("未能取得指定sheet! sheetName="+sheetName);
		}
		
		if(colOffset<=0){
			colOffset=1;
		}
		
		if(colStart==colEnd&&rowStart==rowEnd){											//如果是同一个单元格,则不进行合并
			return;
		}
		
		try{
			wSheet.mergeCells(colStart+colOffset, rowStart, colEnd+colOffset, rowEnd);	//合并单元格
		}catch (Exception e) {
			closeExcle();
			throw e;
		}
	}
	
	/**
	 * 按照指定的格式,写一个单元格.
	 * 如果单元格描述中colStart!=colEnd或者rowStart!=rowEnd,会进行单元格合并
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param sheetName				Excel的sheet名
	 * @param writeVal				写入单元格的值
	 * @param currentMergeDes		单元格描述,当colStart!=colEnd或者rowStart!=rowEnd,会进行单元格合并
	 * @param colOffset				写入时的列偏移量
	 * @throws Exception
	 */
	public void writeToCell(String sheetName,String writeVal,CellDes currentMergeDes,int colOffset) throws Exception{
		
		if(sheetName==null||sheetName.trim().equals("")){
			throw new Exception("未指定sheetName!");
		}
		
		if(writeWorkbook.getSheet(sheetName)==null){
			if(sheetMode=='F'){
				throw new Exception("未能取得指定sheet! sheetName="+sheetName);
			}else{
				creatNewSheet(sheetName);
			}
		}
		
		WritableSheet wSheet;
		wSheet=writeWorkbook.getSheet(sheetName);
		
		if(currentMergeDes==null){
			throw new Exception("写入单元格描述为空!");
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
	 * 按照指定格式,写入一个单元格
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param wSheet					Excel的sheet对象
	 * @param writeVal					写入值
	 * @param colNum					写入的列号
	 * @param rowNum					写入的行号
	 * @param cellDes					单元格描述
	 * @param colOffset					列偏移量
	 * @throws Exception
	 */
	private void writeCell(WritableSheet wSheet,String writeVal,int colNum,int rowNum,CellDes cellDes,int colOffset) throws Exception{
		
		Label label;	
		jxl.write.Number number;
		colNum=colNum+colOffset;
		
		if(colNum<0){																//容错处理
			colNum=0;
		}
		if(rowNum<0){																//容错处理
			rowNum=0;
		}
		
		if(cellDes==null){
			cellDes=new CellDes();													//如果单元格描述为空,则以默认格式写入
		}
		
		if(writeVal==null||writeVal.equalsIgnoreCase("null")){
			writeVal="";
		}
		
		try{
				
			if(cellDes.isNumber == false){											//如果不是数字型,则以普通文本方式写入
				label = genLable(colNum,rowNum,writeVal,cellDes);					//生成单元格
				wSheet.addCell(label); 		
			}else{																	//如果是数字型,则以数字单元格写入
				try {
					double dValue = Double.parseDouble(writeVal);
					number = new jxl.write.Number(colNum,rowNum,dValue,numCellFmt);
					wSheet.addCell(number);
				} catch (Exception e) {												//如果发生转型错误,则以普通文本方式写入
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
	 * 按照指定格式生成要写入的单元格
	 * @comments	:
	 * @author   	:任凯 2012-8-9
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
		
			if(cellDes==null||cellDes.useFormat==false){														//如果单元格描述为空,或不使用格式,则以普通方式生成单元格
				label = new Label(colNum,rowNum,writeVal);
				return label;
			}
			
			if(cellDes.useBoldFont){																			
				cellFont = new WritableFont(WritableFont.ARIAL,cellDes.fontSize,WritableFont.BOLD,false);	 	//使用粗体字体
				cellFmt = new WritableCellFormat(cellFont); 					 								//单元格格式
				
			}else{
				cellFont = new WritableFont(WritableFont.ARIAL,cellDes.fontSize,WritableFont.NO_BOLD,false);	//不使用粗体字体
				cellFmt = new WritableCellFormat(cellFont); 					 								//单元格格式
				
			}
			
			if(cellDes.align!=null){																			//文字对齐方式
				if(cellDes.align.equalsIgnoreCase("L")||cellDes.align.equalsIgnoreCase("Left")){				//左对齐
					cellFmt.setAlignment(Alignment.LEFT);
				}else if(cellDes.align.equalsIgnoreCase("C")||cellDes.align.equalsIgnoreCase("Center")){		//居中
					cellFmt.setAlignment(Alignment.CENTRE);
				}else if(cellDes.align.equalsIgnoreCase("R")||cellDes.align.equalsIgnoreCase("Right")){			//右对齐
					cellFmt.setAlignment(Alignment.RIGHT);
				}
			}
			
			if(cellDes.borderSzie==0){																			//单元格边框
				cellFmt.setBorder(Border.ALL, BorderLineStyle.HAIR);											//虚线边框
			}else if(cellDes.borderSzie==1){
				cellFmt.setBorder(Border.ALL, BorderLineStyle.THIN);											//细边框
			}else if(cellDes.borderSzie>=2){
				cellFmt.setBorder(Border.ALL, BorderLineStyle.MEDIUM);											//较粗边框
			}
			
			writeVal=textFormat(writeVal,cellDes);																//数据格式转换
			
			label = new Label(colNum,rowNum,writeVal,cellFmt);													//生成单元格
		
		} catch (WriteException e) {
			label = new Label(colNum,rowNum,writeVal);
		}
		
		return label;
	}
	
	/**
	 * 数据格式转换
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param content					要转换的数据
	 * @param cellDes					单元格描述		
	 * @return
	 */
	private String textFormat(String content,CellDes cellDes){
		if(content==null||cellDes==null){
			return "";
		}
		
		String rt=content;
		try {
			if(cellDes.textFormat==null||cellDes.textFormat.equalsIgnoreCase("N")){				//如果不是用内容格式化,则返回原值
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("millSpt")){									//转换为千分位显示
				rt=AFutTransform.NumFormate(content,-1,cellDes.decDigits,false,true,'R');
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("date")){									//使用日期格式
				rt=AFutDate.getStrDataByForm(content,cellDes.dateFormat);
				return rt;
			}
			
			if(cellDes.textFormat.equalsIgnoreCase("numToRMB")){								//转换为人民币大写
				rt=numberToRMB(content);
				return rt;
			}
		
		} catch (Exception e) {
			rt=content+"格式转换错误"+e.getMessage();
			
		}
		return rt;
	}
	
	/**
	 * 将数字装换为人民币大写
	 * 如果转换失败,将返回原值
	 * @comments	:
	 * @author   	:任凯 2012-8-9
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
	 * 根据指定的数量及属性设置,生成单元格描述
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param count					生成数量
	 * @param isMerge				单元格是否参与合并
	 * @param isNumber				单元格内容是否是数字
	 * @param cellLen				单元格宽度
	 * @param refMergeCol			合并时的参照列序号,可以指定多个
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
	 * 根据指定的数量及属性设置,生成单元格描述
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param count					生成数量
	 * @param isMerge				单元格是否参与合并
	 * @param isNumber				单元格内容是否是数字
	 * @param cellLen				单元格宽度
	 * @param useFormat				是否使用内容格式化
	 * @param align					文字对齐方式
	 * @param useBoldFont			是否使用粗体字
	 * @param borderSzie			单元格边框
	 * @param fontSize				字体大小
	 * @param refMergeCol			合并时的参照列序号,可以指定多个
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
	 * 根据实参描述取得数据
	 * 取得的数据以实参描述组节点的sc为key放入HashMap
	 * 
	 * 如果实参组是单条数据,则数据形式为String[],顺序为实参组中各数据项的顺序
	 * 如果实参组是多条数据,则数据形式为List<String[]>,一条记录String[]的顺序为实参组中各数据项的顺序
	 * 
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param prpRootId
	 * @return
	 * @throws Exception
	 */
	public static HashMap gainParsedData(String prpRootId) throws Exception{
		
		if(prpRootId==null||prpRootId.equals("")){
			throw new Exception("指定的打印实参描述GUID为空!");
		}
		
		ExcelParmSeter parmSeter =new ExcelParmSeter();										
		PMprpRoot prpRoot=(PMprpRoot)ABParmFactory.Factory.getItemByGUID(prpRootId);		//获取实参描述
		if(prpRoot==null){
			throw new Exception("未能取得打印实参描述! 打印实参描述GUID="+prpRootId);
		}
		
		HashMap parsedData=parmSeter.gainParsedData(prpRoot);								//根据实参描述获取数据
		if(parsedData==null){
			throw new Exception("未能解析生成打印目标对象!");
		}
		
		return parsedData;
	}
	
	/**
	 * 计算列表指定列的合计
	 * 指定列的合计以double[]形式返回
	 * 
	 * 如果sourceList中一条记录为String[5],sumIndex=0,1,3
	 * 则表示计算String[0],String[1]和String[3]的合计
	 * 返回double[3]
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param sourceList			要进行合并计算的列表
	 * @param sumIndex				指定哪些列需要计算合计.
	 * @return						指定列的合计以double[]形式返回
	 * @throws Exception
	 */
	public static double[] genSumD(List<String[]> sourceList,int...sumIndex) throws Exception{
		
		if(sourceList==null||sourceList.size()==0){
			throw new Exception("要求和的数据列表为空!");
		}
		
		if(sumIndex==null||sumIndex.length==0){
			throw new Exception("未指定要求和的数据列!");
		}
		
		double[] sumResult=new double[sumIndex.length];
		
		for (int i = 0; i < sourceList.size(); i++) {
			String[] aLine=sourceList.get(i);
			
			for (int j = 0; j < sumResult.length; j++) {
				try {
					double aData=Double.parseDouble(aLine[sumIndex[j]]);
					sumResult[j]=sumResult[j]+aData;
				} catch (Exception e) {
					continue;//如果出现转型错误,则跳过该数据,继续计算下一条
				}
			}
		}
		
		return sumResult;
	}
	
	/**
	 * 计算列表指定列的合计
	 * 指定列的合计以String[]形式返回
	 * 
	 * 如果sourceList中一条记录为String[5],sumIndex=0,1,3
	 * 则表示计算String[0],String[1]和String[3]的合计
	 * 返回String[3]
	 * @comments	:
	 * @author   	:任凯 2012-8-9
	 *
	 * @param sourceList			要进行合并计算的列表
	 * @param sumIndex				指定哪些列需要计算合计.
	 * @return						指定列的合计以String[]形式返回
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
