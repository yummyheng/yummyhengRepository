package excelOut;

public class CellDes {

		public int colStart;					//单元格起始列号
		public int rowStart;					//单元格起始行号
		public int colEnd;						//单元格终止列号
		public int rowEnd;						//单元格终止行号
		
		public String lastData="";				//上一行该单元格的值,用于合共单元格
		public boolean isMerge=false;			//该单元格是否参与数据合并
		
		public int[] refMergeCol;				//数据合并时,参照的列号数组 isMerge==true时有效
		public int[] refMergeRow;				//数据合并时,参照的行号数组 isMerge==true时有效
		
		public boolean doSubtotal=false;		//是否需要计算小计
		public int[] sumCol;					//需要计算小计的列号
		public boolean sumRowWTV=false;		//小计行是否写入除合计列外其他列的值(该属性主要用于多层次小计时使用,写入其他列的值可以用来合并)
		
		public boolean isNumber=false;		//该单元格是否为数字
		
		public int cellLen=0;					//单元格列宽度,
		
		public boolean useFormat=false;		//是否使用单元格内容格式化
		
		//以下属性当useFormat==true时有效
		public int decDigits=0;				//小数位数
		public String textFormat="N";			//转换格式,目前支持 millSpt千分位显示,date日期格式
		public String dateFormat="yyyyMMdd";	//日期格式,当textFormat=date时有效
		public String align="L";				//对齐方式
		public boolean useBoldFont=false;		//是否使用粗体字
		public int borderSzie=-1;				//边框粗细.-1表示不加入边框.0:虚线,1细边框,>=2粗边框
		public int fontSize=10;				//字体大小
		
	public CellDes(){
		
	}
	/**
	 * 构造函数
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param isMerge			是否参与合并
	 * @param refMergeCol		合并时参照的列号,可以设定多列
	 */
	public CellDes(boolean isMerge,int... refMergeCol){
		this.isMerge=isMerge;
		this.refMergeCol=refMergeCol;
	}
	
	/**
	 * 构造函数
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param isMerge			是否参与合并
	 * @param isNumber			单元格是否是数字格式
	 * @param cellLen			列宽度
	 * @param refMergeCol		合并时参照的列号,可以设定多列
	 */
	public CellDes(boolean isMerge,boolean isNumber,int cellLen,int... refMergeCol){
		this.isMerge=isMerge;
		this.refMergeCol=refMergeCol;
		this.isNumber=isNumber;
		this.cellLen=cellLen;
	}
	
	/**
	 * 构造函数
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param colStart			单元格起始列号
	 * @param rowStart			单元格起始行号
	 * @param colEnd			单元格终止列号
	 * @param rowEnd			单元格终止行号
	 */
	public CellDes(int colStart,int rowStart,int colEnd,int rowEnd){
		this.colStart=colStart;
		this.rowStart=rowStart;
		this.colEnd=colEnd;
		this.rowEnd=rowEnd;
		
	}	
	
	/**
	 * 构造函数
	 * @comments	:
	 * @author   	:任凯 2012-8-7
	 *
	 * @param colStart			单元格起始列号
	 * @param rowStart			单元格起始行号
	 * @param colEnd			单元格终止列号
	 * @param rowEnd			单元格终止行号
	 * @param align				内容对齐方式 "L"左对齐;"C"居中;"R"右对齐
	 * @param useBoldFont		单元格是否使用粗体字
	 * @param borderSzie		单元格边框 0:虚线;1:细边框;2:较粗边框
	 */
	public CellDes(int colStart,int rowStart,int colEnd,int rowEnd,String align,boolean useBoldFont,int borderSzie){
		
		this.colStart=colStart;
		this.rowStart=rowStart;
		this.colEnd=colEnd;
		this.rowEnd=rowEnd;
		
		this.useFormat=true;
		this.align=align;
		this.useBoldFont=useBoldFont;
		this.borderSzie=borderSzie;
		
	}	
}
