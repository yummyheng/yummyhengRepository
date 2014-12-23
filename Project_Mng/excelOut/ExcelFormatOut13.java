package excelOut;


	import java.util.ArrayList;
	import java.util.HashMap;

import AF.ut.AFUtility;

	/**
	 * 质押结果查询打印
	 * 
	 * 该类用于将原PDF打印的数据按照原格式输出到Excle中
	 * @comments	:
	 * @author   	:顾文科 2012-8-29
	 *
	 */
	public class ExcelFormatOut13 implements ExcelFormatOut_I{
		private final String prpGUID="cdc#PRP1008100150";
		/**
		 * @param fileName		输出Excel文件名(全路径)
		 * @param sheetName		输出sheet名
		 * @throws Exception
		 */
		public void export(String fileName,String sheetName) throws Exception{
			
			int colOffSet=1;															//写入数据偏移量,用于整体右移
			int listfirstRow=6;															//列表数据写入的首行号
			
			HashMap valMap=ExcelOperPlus.gainParsedData(prpGUID);						//通过打印实参获取数据
			if(valMap==null){
				throw new Exception("未能获取输出数据!");
			}
			
			
			//主信息取值组 标题信息
			String[] summary=(String[])valMap.get("2");									//从实参中取值,参数为实参组节点的sc								
			if(summary==null){
				throw new Exception("未能取得标题组信息!");
			}
			int summaryCellCount=21;														//主信息独立单元格数量
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

			
			
			//表尾取值组 标题信息
			String[] summ=(String[])valMap.get("5");									//从实参中取值,参数为实参组节点的sc								
			if(summ==null){
				throw new Exception("未能取得Map组信息!");
			}
			int summCellCount=5;														//表尾信息独立单元格数量
			CellDes[] summCellDes=ExcelOperPlus.genCellDes(summCellCount,true,false,30,null);//表尾信息单元格属性数组
			String[] summWriteVal=new String[summCellCount];						//真正写入的表尾信息值数组			
			
			
			
			//开始写入数据
			ExcelOperPlus rExcel=new ExcelOperPlus();									//定义Excle操作器
			rExcel.openExcle(fileName,sheetName);										//以指定文件名,sheet名创建Excle
			
			for (int i = 0; i < summaryCellDes.length; i++) {							//依次写入主信息
				rExcel.writeToCell(sheetName, summaryWriteVal[i], summaryCellDes[i], colOffSet);
			}																			//写入列表信息,并根据数据合并单元格
			CellDes orderColDes=new CellDes(true,false,5,0);
			rExcel.writeExcelAndMer(writeList, tableListLable,sheetName,listfirstRow,mergeDes,colOffSet,null,null);
			
			int sumRow=rExcel.currentRow;											
			initSummCell(summCellDes,summWriteVal,summ,sumRow);					//初始化表尾信息单元格格式及数据
			
			for (int j = 0; j < summCellDes.length; j++) {							//依次写入尾信息
				rExcel.writeToCell(sheetName, summWriteVal[j], summCellDes[j], colOffSet);
			}
			
//			rExcel.writeLine(sheetName, sumWrite, sumRow, sumCellDes, colOffSet);		//写入合计行数据
			
//			CellDes hjLableCell=new CellDes(-1,sumRow,-1,sumRow,"C",true,1);			//合计标签单元格
//			rExcel.writeToCell(sheetName,"合计", hjLableCell, colOffSet);
			
			
			rExcel.closeExcle();														//保存并关闭Excle
			
		}
	
		/**
		 * 质押结果查询打印主信息初始化
		 * @comments	:
		 * @author   	:顾文科 2012-8-29
		 * @param summaryCellDes		单元格描述数组
		 * @param summaryWriteVal		写入单元格的值
		 * @param summary				通过实参描述取得的值
		 */
		private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
			int id=0;
			
			summaryWriteVal[id]="质押结果查询列表";	//报表标题
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=8;							//合并4到8单元格
			summaryCellDes[id].rowStart=0;
			summaryCellDes[id].rowEnd=0;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;					//使用粗体字
			summaryCellDes[id].fontSize=20;							//使用20号字
			
			id=id+1;
			summaryWriteVal[id]="出押方简称：";				//出押方简称 标签
			summaryCellDes[id].colStart=0;
			summaryCellDes[id].colEnd=0;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[0];							//出押方简称
			summaryCellDes[id].colStart=1;
			summaryCellDes[id].colEnd=1;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]="出押方债券账号：";					//出押方债券账号 标签
			summaryCellDes[id].colStart=2;
			summaryCellDes[id].colEnd=2;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[1];							//出押方债券账号
			summaryCellDes[id].colStart=3;
			summaryCellDes[id].colEnd=3;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]="受押方简称：";					//受押方简称 标签
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=4;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[2];							//受押方简称
			summaryCellDes[id].colStart=5;
			summaryCellDes[id].colEnd=5;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="受押方债券账号：";					//受押方债券账号 标签
			summaryCellDes[id].colStart=6;
			summaryCellDes[id].colEnd=6;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[3];							//受押方债券账号
			summaryCellDes[id].colStart=7;
			summaryCellDes[id].colEnd=7;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;


			id=id+1;
			summaryWriteVal[id]="业务类别：";					//业务类别 标签
			summaryCellDes[id].colStart=8;
			summaryCellDes[id].colEnd=8;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[4];							//业务类别 标签
			summaryCellDes[id].colStart=9;
			summaryCellDes[id].colEnd=9;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;

			
			id=id+1;
			summaryWriteVal[id]="敞口金额(元)：";						//敞口金额 标签
			summaryCellDes[id].colStart=0;
			summaryCellDes[id].colEnd=0;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[6];							//敞口金额
			summaryCellDes[id].colStart=1;
			summaryCellDes[id].colEnd=1;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="实际敞口金额(元)：";						//实际敞口金额 标签
			summaryCellDes[id].colStart=2;
			summaryCellDes[id].colEnd=2;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;

			id=id+1;
			summaryWriteVal[id]=summary[7];							//实际敞口金额
			summaryCellDes[id].colStart=3;
			summaryCellDes[id].colEnd=3;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="质押券价值(元)：";						//质押券价值 标签
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=4;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;

			id=id+1;
			summaryWriteVal[id]=summary[8];							//质押券价值
			summaryCellDes[id].colStart=5;
			summaryCellDes[id].colEnd=5;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
		

		id=id+1;
		summaryWriteVal[id]="敞口角色：";						//敞口角色 标签
		summaryCellDes[id].colStart=6;
		summaryCellDes[id].colEnd=6;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;

		id=id+1;
		summaryWriteVal[id]=summary[9];							//敞口角色
		summaryCellDes[id].colStart=7;
		summaryCellDes[id].colEnd=7;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		
		
		id=id+1;
		summaryWriteVal[id]="到期日：";						//到期日 标签
		summaryCellDes[id].colStart=8;
		summaryCellDes[id].colEnd=8;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;
		summaryCellDes[id].textFormat=TextF.date;
		summaryCellDes[id].dateFormat=TextF.yyyyNMMYddR;
		
		id=id+1;
		summaryWriteVal[id]=summary[5];							//到期日
		summaryCellDes[id].colStart=9;
		summaryCellDes[id].colEnd=9;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		summaryCellDes[id].textFormat=TextF.date;
		summaryCellDes[id].dateFormat=TextF.yyyyMMdd;
	}
		
		
		//初始化列表每一列的单元格描述
		private CellDes[] initColCellDes(int colCount){
			
			if(colCount<=0){
				return null;
			}
			
			//根据列数创建生成每一列单元格描述 
			//默认为 需要合并单元格,非数字,列宽20,使用内容格式化,左对齐,不使用粗体字,有边框,10号字,参照合同号进行合并
			CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,false,false,20,true,"L",false,1,10,3);
			
			colCellDes[9].textFormat=TextF.date;			//生成日期  需要转换格式
			colCellDes[9].dateFormat=TextF.yyyy_MM_dd;		//日期转换格式
			
			colCellDes[10].textFormat=TextF.date;			//取消日期  需要转换格式
			colCellDes[10].dateFormat=TextF.yyyy_MM_dd;		//日期转换格式
			
			colCellDes[11].textFormat=TextF.date;			//更新日期  需要转换格式
			colCellDes[11].dateFormat=TextF.yyyy_MM_dd_hsm;		//日期转换格式
			
			
			for (int i = 3; i <= 6; i++) {				//8到15列都为数字
				colCellDes[i].align=TextF.RIGHT;		//右对齐
				colCellDes[i].textFormat=TextF.millSpt;	//千分位显示
			}
			
			colCellDes[3].decDigits=2;					//债券面额（万元）  2位小数
			colCellDes[4].decDigits=2;					//质押券价值（元） 2位小数
			colCellDes[5].decDigits=6;					//质押券价格(元/百元） 6位小数
			colCellDes[6].decDigits=6;					//质押率（%） 6位小数
			
			return colCellDes;
			
		}
		
		/**
		 * 生成列表表头信息,并将通过打印实参取得的列表数据转换为可以写入Excle的数据
		 * 通过实参取得的列表数据,每一条数据是按照实参描述各个节点的顺序生成的String[]
		 * 但写入Excle时不是这个顺序,所以需要对应转换
		 * 
		 * 
		 * @comments	:
		 * @author   	:顾文科 2012-8-29
		 *
		 * @param tableList				通过打印实参取得的列表数据
		 * @param tableListLable		写入时的列表表头数据
		 * @param summary				打印实参的标题取值组,列表头的部分数据从这里取得
		 * @param writeList				真正写入Excle的列表数组
		 */
		private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
			tableListLable[0]="对应合同号";					
			tableListLable[1]="债券代码";
			tableListLable[2]="债券简称";				
			tableListLable[3]="债券面额（万元）";
			tableListLable[4]="质押券价值(元)";
			tableListLable[5]="质押券价格（元/百元）";
			tableListLable[6]="质押率(%)";
			tableListLable[7]="交易类型";
			tableListLable[8]="记录状态";
			tableListLable[9]="生成日期";
			tableListLable[10]="取消日期";
			tableListLable[11]="更新时间";
			
			//将通过打印实参取得的列表数据与写入Excle的列表数据进行对应
			//对应的依据是打印模板中的顺序
			for (int i = 0; i < tableList.size(); i++) {
				String[] aLine=tableList.get(i);
				int colCount=aLine.length;
				String[] writeLine=new String[colCount];
				
				writeLine[0]=aLine[0];				//对应合同号
				writeLine[1]=aLine[1];				//债券代码
				writeLine[2]=aLine[2];				//债券简称
				writeLine[3]=aLine[3];				//债券面额（万元）
				writeLine[4]=aLine[4];				//质押券价值(元)
				writeLine[5]=aLine[5];				//质押券价格（元/百元）
				writeLine[6]=aLine[6];				//质押率(%)
				writeLine[7]=aLine[7];				//交易类型
				writeLine[8]=aLine[8];				//记录状态
				writeLine[9]=aLine[9];				//生成日期
				writeLine[10]=aLine[10];			//取消日期
				writeLine[11]=aLine[11];			//更新时间	
				writeList.add(writeLine);
			}
		}
		
		
		
		/**
		 * 质押结果查询打印表尾信息初始化
		 * @comments	:
		 * @author   	:顾文科 2012-8-29
		 * @param sumRow 
		 * @param summaryCellDes		单元格描述数组
		 * @param summaryWriteVal		写入单元格的值
		 * @param summary				通过实参描述取得的值
		 */
		private void initSummCell(CellDes[] summCellDes, String[] summWriteVal,String[] summ, int sumRow) {
			int tail=0;
			summWriteVal[tail]="中央国债登记结算有限责任公司";				//中央国债登记结算有限责任公司 标签
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+1;
			summCellDes[tail].rowEnd=sumRow+1;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]="打印时间：";				//打印时间 标签
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+2;
			summCellDes[tail].rowEnd=sumRow+2;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]=summ[0];							//打印时间
			summCellDes[tail].colStart=11;
			summCellDes[tail].colEnd=11;
			summCellDes[tail].rowStart=sumRow+2;
			summCellDes[tail].rowEnd=sumRow+2;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=false;
			summCellDes[tail].fontSize=10;
			summCellDes[tail].textFormat=TextF.date;
			summCellDes[tail].dateFormat=TextF.yyyyNMMYddR;
			
			tail=tail+1;
			summWriteVal[tail]="打印人：";					//打印人 标签
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+3;
			summCellDes[tail].rowEnd=sumRow+3;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]=summ[1];							//打印人
			summCellDes[tail].colStart=11;
			summCellDes[tail].colEnd=11;
			summCellDes[tail].rowStart=sumRow+3;
			summCellDes[tail].rowEnd=sumRow+3;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=false;
			summCellDes[tail].fontSize=10;
			

			
		}
		
	}

