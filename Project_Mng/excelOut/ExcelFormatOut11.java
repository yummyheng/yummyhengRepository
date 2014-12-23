package excelOut;

import java.util.ArrayList;
import java.util.HashMap;

import AF.ut.AFUtility;

/**
 * 输出 已完成受押分类台账（按业务类别）
 * 
 * 该类用于将原PDF打印的数据按照原格式输出到Excle中
 * @comments	:
 * @author   	:吴月 2012-8-16
 *
 */
public class ExcelFormatOut11 implements ExcelFormatOut_I{
	private final String prpGUID="cdc#PRP100810020111";
	
	/**
	 * 输出 已完成受押分类台账（按业务类别）
	 * @comments	:
	 * @author   	:吴月 2012-8-16
	 *
	 * @param fileName		输出Excel文件名(全路径)
	 * @param sheetName		输出sheet名
	 * @throws Exception
	 */
	public void export(String fileName,String sheetName) throws Exception{
		
		int colOffSet=1;															//写入数据偏移量,用于整体右移
		int listfirstRow=5;															//列表数据写入的首行号
		
		
		HashMap valMap=ExcelOperPlus.gainParsedData(prpGUID);						//通过打印实参获取数据
		if(valMap==null){
			throw new Exception("未能获取输出数据!");
		}
		
		
		//主信息取值组 标题信息
		String[] summary=(String[])valMap.get("2");									//从实参中取值,参数为实参组节点的sc								
		if(summary==null){
			throw new Exception("未能取得标题组信息!");
		}
		int summaryCellCount=10;														//主信息独立单元格数量
		CellDes[] summaryCellDes=ExcelOperPlus.genCellDes(summaryCellCount,true,false,30,null);//主信息单元格属性数组
		String[] summaryWriteVal=new String[summaryCellCount];						//真正写入的主信息值数组			
		initSummaryCell(summaryCellDes,summaryWriteVal,summary);					//初始化主信息单元格格式及数据
		
		
		//列表信息取值组
		ArrayList<String[]> tableList=(ArrayList<String[]>)valMap.get("4");			//从实参中取值, 表格取值组
		if(tableList==null||tableList.size()==0){
			throw new Exception("未能取得表格取值组信息!");
		}
		int colCount=tableList.get(0).length;										//表格列数量
		String[] tableListLable=new String[colCount];								//表格取值组标签
		ArrayList<String[]> writeList=new ArrayList<String[]>();					//真正写入的列表数据
		initTableList(tableList,tableListLable,summary,writeList);					//初始化列表数据
		String[] realcol=writeList.get(0);											
		CellDes[] mergeDes=initColCellDes(realcol.length);							//定义列表每一列的单元格描述信息
		
		CellDes subTotalDes=new CellDes();											//小计描述
		initSubTotalDes(subTotalDes);												//初始化小计描述	
		
		//合计取值组
		ArrayList<String[]> sumList=(ArrayList<String[]>)valMap.get("5");			//合计取值组
		if(sumList==null||sumList.size()==0){
			throw new Exception("未能取得合计取值组信息!");
		}
		
		String[] sumVal=ExcelOperPlus.genSumS(sumList,0,1);						//生成合计数据,分别对第0,1,2列进行合计
		CellDes[] sumCellDes=AFUtility.cloneObjByXstream(mergeDes);					//合计行数据的单元格描述,克隆数据列表列单元格描述
		String[] sumWrite=new String[realcol.length];								//合计行写入数据数组							
		initSumCellDes(sumCellDes,sumWrite,sumVal);									//初始化合计写入行数据,并初始化合计数据单元格描述
		
		
		//开始写入数据
		ExcelOperPlus rExcel=new ExcelOperPlus();									//定义Excle操作器
		rExcel.openExcle(fileName,sheetName);										//以指定文件名,sheet名创建Excle
		
		for (int i = 0; i < summaryCellDes.length; i++) {							//依次写入主信息
			rExcel.writeToCell(sheetName, summaryWriteVal[i], summaryCellDes[i], colOffSet);
		}
																					//写入列表信息,并根据数据合并单元格
		CellDes orderColDes=new CellDes(true,false,5,0);
		rExcel.writeExcelAndMer(writeList, tableListLable,sheetName,listfirstRow,mergeDes,colOffSet,orderColDes,subTotalDes);
		int sumRow=rExcel.currentRow;												//合计行写入位置,为Excle当前位置
		rExcel.writeLine(sheetName, sumWrite, sumRow, sumCellDes, colOffSet);		//写入合计行数据
		
		CellDes hjLableCell=new CellDes(-1,sumRow,-1,sumRow,"C",true,1);			//合计标签单元格
		rExcel.writeToCell(sheetName,"合计", hjLableCell, colOffSet);
		
		rExcel.closeExcle();														//保存并关闭Excle
		
	}
	/**
	 * 已完成受押分类台账（按业务类别）主信息初始化
	 * @comments	:
	 * @author   	:吴月 2012-8-16
	 *
	 * @param summaryCellDes		单元格描述数组
	 * @param summaryWriteVal		写入单元格的值
	 * @param summary				通过实参描述取得的值
	 */
	private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
		
		summaryWriteVal[0]="已完成受押分类台账（按业务类别）";//报表标题
		summaryCellDes[0].colStart=2;
		summaryCellDes[0].colEnd=6;							//合并4到8单元格
		summaryCellDes[0].rowStart=0;
		summaryCellDes[0].rowEnd=0;
		summaryCellDes[0].useFormat=true;
		summaryCellDes[0].align=TextF.CENTRE;
		summaryCellDes[0].useBoldFont=true;					//使用粗体字
		summaryCellDes[0].fontSize=20;						//使用20号字
		
		summaryWriteVal[1]="受押方债券账户简称：";			//标签
		summaryCellDes[1].colStart=0;
		summaryCellDes[1].colEnd=0;
		summaryCellDes[1].rowStart=2;
		summaryCellDes[1].rowEnd=2;
		summaryCellDes[1].useFormat=true;
		summaryCellDes[1].align=TextF.Left;
		summaryCellDes[1].useBoldFont=true;
		summaryCellDes[1].fontSize=10;
		
		
		summaryWriteVal[2]=summary[0];						//单位名称
		summaryCellDes[2].colStart=1;
		summaryCellDes[2].colEnd=1;
		summaryCellDes[2].rowStart=2;
		summaryCellDes[2].rowEnd=2;
		summaryCellDes[2].useFormat=true;
		summaryCellDes[2].align=TextF.Left;
		summaryCellDes[2].useBoldFont=false;
		summaryCellDes[2].fontSize=10;
		
		
		summaryWriteVal[3]="受押方债券账号：";				//标签
		summaryCellDes[3].colStart=6;
		summaryCellDes[3].colEnd=6;
		summaryCellDes[3].rowStart=2;
		summaryCellDes[3].rowEnd=2;
		summaryCellDes[3].useFormat=true;
		summaryCellDes[3].align=TextF.Left;
		summaryCellDes[3].useBoldFont=true;
		summaryCellDes[3].fontSize=10;
		
		summaryWriteVal[4]=summary[2];						//托管账号
		summaryCellDes[4].colStart=7;
		summaryCellDes[4].colEnd=7;
		summaryCellDes[4].rowStart=2;
		summaryCellDes[4].rowEnd=2;
		summaryCellDes[4].useFormat=true;
		summaryCellDes[4].align=TextF.Left;
		summaryCellDes[4].useBoldFont=false;
		summaryCellDes[4].fontSize=10;
		
		summaryWriteVal[5]="业务名称：";						//标签
		summaryCellDes[5].colStart=0;
		summaryCellDes[5].colEnd=0;
		summaryCellDes[5].rowStart=3;
		summaryCellDes[5].rowEnd=3;
		summaryCellDes[5].useFormat=true;
		summaryCellDes[5].align=TextF.Left;
		summaryCellDes[5].useBoldFont=true;
		summaryCellDes[5].fontSize=10;
		
		summaryWriteVal[6]=summary[4];						//业务名称
		summaryCellDes[6].colStart=1;
		summaryCellDes[6].colEnd=1;
		summaryCellDes[6].rowStart=3;
		summaryCellDes[6].rowEnd=3;
		summaryCellDes[6].useFormat=true;
		summaryCellDes[6].align=TextF.Left;
		summaryCellDes[6].useBoldFont=false;
		summaryCellDes[6].fontSize=10;
		
	    summaryWriteVal[7]="查询时段：";						//标签
		summaryCellDes[7].colStart=0;
		summaryCellDes[7].colEnd=0;
		summaryCellDes[7].rowStart=4;
		summaryCellDes[7].rowEnd=4;
		summaryCellDes[7].useFormat=true;
		summaryCellDes[7].align=TextF.Left;
		summaryCellDes[7].useBoldFont=true;
		summaryCellDes[7].fontSize=10;
		summaryCellDes[7].textFormat=TextF.date;
		summaryCellDes[7].dateFormat=TextF.yyyyNMMYddR;
		
		
	    summaryWriteVal[8]=summary[3];						//开始查询时间段
		summaryCellDes[8].colStart=1;
		summaryCellDes[8].colEnd=1;
		summaryCellDes[8].rowStart=4;
		summaryCellDes[8].rowEnd=4;
		summaryCellDes[8].useFormat=true;
		summaryCellDes[8].align=TextF.Left;
		summaryCellDes[8].useBoldFont=false;
		summaryCellDes[8].fontSize=10;
		summaryCellDes[8].textFormat=TextF.date;
		summaryCellDes[8].dateFormat=TextF.yyyyNMMYddR;
		
	    summaryWriteVal[9]=summary[1];						//结束查询时间段
		summaryCellDes[9].colStart=2;
		summaryCellDes[9].colEnd=2;
		summaryCellDes[9].rowStart=4;
		summaryCellDes[9].rowEnd=4;
		summaryCellDes[9].useFormat=true;
		summaryCellDes[9].align=TextF.Left;
		summaryCellDes[9].useBoldFont=false;
		summaryCellDes[9].fontSize=10;
		summaryCellDes[9].textFormat=TextF.date;
		summaryCellDes[9].dateFormat=TextF.yyyyNMMYddR;
		
	}
	
	
	//初始化列表每一列的单元格描述
	private CellDes[] initColCellDes(int colCount){
		
		if(colCount<=0){
			return null;
		}
		
		//根据列数创建生成每一列单元格描述 
		//默认为 需要合并单元格,非数字,列宽20,使用内容格式化,左对齐,不使用粗体字,有边框,10号字,参照第4列(合同号)进行合并
		CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,true,false,20,true,"L",false,1,10,3);
		
		colCellDes[2].textFormat=TextF.date;		//业务发生日期  需要转换格式
		colCellDes[2].dateFormat=TextF.yyyyNMMYddR;	//日期转换格式
		
		colCellDes[9].textFormat=TextF.date;		//到期日期  需要转换格式
		colCellDes[9].dateFormat=TextF.yyyyNMMYddR;	//日期转换格式
		
		colCellDes[5].isMerge=false;				//受押债券  不进行合并
		colCellDes[6].isMerge=false;				//质押券面额 不进行合并
		
		//右对齐、千分位显示
		colCellDes[6].align=TextF.RIGHT;		
		colCellDes[6].textFormat=TextF.millSpt;	
		colCellDes[7].align=TextF.RIGHT;		
		colCellDes[7].textFormat=TextF.millSpt;	
		
		colCellDes[6].decDigits=4;					//质押券面额（万元）  4位小数
		colCellDes[7].decDigits=6;					//敞口金额（万元） 6位小数
		
//		colCellDes[16].useFormat=false;				//打印实参cdc#PRP100810020100中比形参多了"调整方向"一项,有问题,这里将多的这一项设为不显示内容
		
		return colCellDes;
		
	}
	
	/**
	 * 生成列表表头信息,并将通过打印实参取得的列表数据转换为可以写入Excle的数据
	 * 通过实参取得的列表数据,每一条数据是按照实参描述各个节点的顺序生成的String[]
	 * 但写入Excle时不是这个顺序,所以需要对应转换
	 * 
	 * 
	 * @comments	:
	 * @author   	:吴月 2012-8-16
	 *
	 * @param tableList				通过打印实参取得的列表数据
	 * @param tableListLable		写入时的列表表头数据
	 * @param summary				打印实参的标题取值组,列表头的部分数据从这里取得
	 * @param writeList				真正写入Excle的列表数组
	 */
	private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
		
		tableListLable[0]="业务类别";
		tableListLable[1]="业务名称";			                                                                
		tableListLable[2]=summary[5];           //列名 业务发生日期,需要从实参的标题取值组中获得          
		tableListLable[3]="合同号";                                                                       
		tableListLable[4]="出押方";                                                                       
		tableListLable[5]="受押债券";                                                                     
		tableListLable[6]="质押券面额（万元）";                                                           
		tableListLable[7]="敞口金额（万元）";                                                             
		tableListLable[8]="到期完成结果";                                                                 
		tableListLable[9]=summary[6];			//列名 到期日期 ,需要从实参的标题取值组中获得
		
		//将通过打印实参取得的列表数据与写入Excle的列表数据进行对应
		//对应的依据是打印模板中的顺序
		for (int i = 0; i < tableList.size(); i++) {
			String[] aLine=tableList.get(i);
			int colCount=aLine.length;
			String[] writeLine=new String[colCount];
			
			writeLine[0]=aLine[7];				//业务类别   
			writeLine[1]=aLine[9];				//业务名称                                   
			writeLine[2]=aLine[0];				//业务发生日期          	              
			writeLine[3]=aLine[1];				//合同号                	              
			writeLine[4]=aLine[2];				//出押方                	              
			writeLine[5]=aLine[3];				//受押债券            	              
			writeLine[6]=aLine[4];				//质押券面额（万元）  	              
			writeLine[7]=aLine[5];			  //敞口金额（万元）                  
			writeLine[8]=aLine[6];			  //到期完成结果      	
			writeLine[9]=aLine[8];			  //到期日期
			
			writeList.add(writeLine);
		}
	}
	
	/**
	 * 初始化合计行的列单元格描述及数据
	 * @comments	:
	 * @author   	:吴月 2012-8-16
	 *
	 * @param sumCellDes	合计行每列的单元格描述,初始值和列数据一致
	 * @param sumWrite		写入的合计值
	 * @param sumVal		通过打印实参计算出的合计值
	 */
	private void initSumCellDes(CellDes[] sumCellDes,String[] sumWrite,String[] sumVal){
		
		sumWrite[6]=sumVal[0];//质押券面额（万元） 
		sumWrite[7]=sumVal[1];//敞口金额合计
		
		sumCellDes[6].decDigits=4;//质押券面额（万元） 	4位小数 
		sumCellDes[7].decDigits=6;//敞口金额合计	6位小数 
		
	}
    private void initSubTotalDes(CellDes sumTotalDes){
		
		sumTotalDes.sumCol=new int[]{6,7};			//参与小计的列
		sumTotalDes.refMergeCol=new int[]{0};			//计算小计的参照列(小计分组参照列),业务类别
		
	}
		
}
