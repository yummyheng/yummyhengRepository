package excelOut;

import java.util.ArrayList;
import java.util.HashMap;

import AF.ut.AFUtility;

/**
 * ��� ��������Ѻ����̨�ˣ���ҵ�����ƣ�
 * 
 * �������ڽ�ԭPDF��ӡ�����ݰ���ԭ��ʽ�����Excle��
 * @comments	:
 * @author   	:���� 2012-8-15
 *
 */
public class ExcelFormatOut10 implements ExcelFormatOut_I{
	private final String prpGUID="cdc#PRP100810020108";
	
	/**
	 * ��� ��������Ѻ����̨�ˣ���ҵ�����ƣ�
	 * @comments	:
	 * @author   	:���� 2012-8-15
	 *
	 * @param fileName		���Excel�ļ���(ȫ·��)
	 * @param sheetName		���sheet��
	 * @throws Exception
	 */
	public void export(String fileName,String sheetName) throws Exception{
		
		int colOffSet=1;															//д������ƫ����,������������
		int listfirstRow=5;															//�б�����д������к�
		
		
		HashMap valMap=ExcelOperPlus.gainParsedData(prpGUID);						//ͨ����ӡʵ�λ�ȡ����
		if(valMap==null){
			throw new Exception("δ�ܻ�ȡ�������!");
		}
		
		
		//����Ϣȡֵ�� ������Ϣ
		String[] summary=(String[])valMap.get("2");									//��ʵ����ȡֵ,����Ϊʵ����ڵ��sc								
		if(summary==null){
			throw new Exception("δ��ȡ�ñ�������Ϣ!");
		}
		int summaryCellCount=10;														//����Ϣ������Ԫ������
		CellDes[] summaryCellDes=ExcelOperPlus.genCellDes(summaryCellCount,true,false,30,null);//����Ϣ��Ԫ����������
		String[] summaryWriteVal=new String[summaryCellCount];						//����д�������Ϣֵ����			
		initSummaryCell(summaryCellDes,summaryWriteVal,summary);					//��ʼ������Ϣ��Ԫ���ʽ������
		
		
		//�б���Ϣȡֵ��
		ArrayList<String[]> tableList=(ArrayList<String[]>)valMap.get("4");			//��ʵ����ȡֵ, ���ȡֵ��
		if(tableList==null||tableList.size()==0){
			throw new Exception("δ��ȡ�ñ��ȡֵ����Ϣ!");
		}
		int colCount=tableList.get(0).length;										//���������
		String[] tableListLable=new String[colCount];								//���ȡֵ���ǩ
		ArrayList<String[]> writeList=new ArrayList<String[]>();					//����д����б�����
		initTableList(tableList,tableListLable,summary,writeList);					//��ʼ���б�����
		String[] realcol=writeList.get(0);											
		CellDes[] mergeDes=initColCellDes(realcol.length);							//�����б�ÿһ�еĵ�Ԫ��������Ϣ
		
		CellDes subTotalDes=new CellDes();											//С������
		initSubTotalDes(subTotalDes);												//��ʼ��С������	
		
		//�ϼ�ȡֵ��
		ArrayList<String[]> sumList=(ArrayList<String[]>)valMap.get("5");			//�ϼ�ȡֵ��
		if(sumList==null||sumList.size()==0){
			throw new Exception("δ��ȡ�úϼ�ȡֵ����Ϣ!");
		}
		
		String[] sumVal=ExcelOperPlus.genSumS(sumList,0,1,2);						//���ɺϼ�����,�ֱ�Ե�0,1,2�н��кϼ�
		CellDes[] sumCellDes=AFUtility.cloneObjByXstream(mergeDes);					//�ϼ������ݵĵ�Ԫ������,��¡�����б��е�Ԫ������
		String[] sumWrite=new String[realcol.length];								//�ϼ���д����������							
		initSumCellDes(sumCellDes,sumWrite,sumVal);									//��ʼ���ϼ�д��������,����ʼ���ϼ����ݵ�Ԫ������
		
		
		//��ʼд������
		ExcelOperPlus rExcel=new ExcelOperPlus();									//����Excle������
		rExcel.openExcle(fileName,sheetName);										//��ָ���ļ���,sheet������Excle
		
		for (int i = 0; i < summaryCellDes.length; i++) {							//����д������Ϣ
			rExcel.writeToCell(sheetName, summaryWriteVal[i], summaryCellDes[i], colOffSet);
		}
																					//д���б���Ϣ,���������ݺϲ���Ԫ��
		CellDes orderColDes=new CellDes(true,false,5,0);
		rExcel.writeExcelAndMer(writeList, tableListLable,sheetName,listfirstRow,mergeDes,colOffSet,orderColDes,subTotalDes);
		int sumRow=rExcel.currentRow;												//�ϼ���д��λ��,ΪExcle��ǰλ��
		rExcel.writeLine(sheetName, sumWrite, sumRow, sumCellDes, colOffSet);		//д��ϼ�������
		
		CellDes hjLableCell=new CellDes(-1,sumRow,-1,sumRow,"C",true,1);			//�ϼƱ�ǩ��Ԫ��
		rExcel.writeToCell(sheetName,"�ϼ�", hjLableCell, colOffSet);
		
		rExcel.closeExcle();														//���沢�ر�Excle
		
	}
	/**
	 * ��������Ѻ����̨�ˣ���ҵ�����ƣ�����Ϣ��ʼ��
	 * @comments	:
	 * @author   	:���� 2012-8-15
	 *
	 * @param summaryCellDes		��Ԫ����������
	 * @param summaryWriteVal		д�뵥Ԫ���ֵ
	 * @param summary				ͨ��ʵ������ȡ�õ�ֵ
	 */
	private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
		
		summaryWriteVal[0]="��������Ѻ����̨�ˣ���ҵ�����ƣ�";//�������
		summaryCellDes[0].colStart=4;
		summaryCellDes[0].colEnd=8;							//�ϲ�4��8��Ԫ��
		summaryCellDes[0].rowStart=0;
		summaryCellDes[0].rowEnd=0;
		summaryCellDes[0].useFormat=true;
		summaryCellDes[0].align=TextF.CENTRE;
		summaryCellDes[0].useBoldFont=true;					//ʹ�ô�����
		summaryCellDes[0].fontSize=20;						//ʹ��20����
		
		summaryWriteVal[1]="��Ѻ��ծȯ�˻���ƣ�";			//��ǩ
		summaryCellDes[1].colStart=0;
		summaryCellDes[1].colEnd=0;
		summaryCellDes[1].rowStart=2;
		summaryCellDes[1].rowEnd=2;
		summaryCellDes[1].useFormat=true;
		summaryCellDes[1].align=TextF.Left;
		summaryCellDes[1].useBoldFont=true;
		summaryCellDes[1].fontSize=10;
		
		
		summaryWriteVal[2]=summary[1];						//��λ����
		summaryCellDes[2].colStart=1;
		summaryCellDes[2].colEnd=1;
		summaryCellDes[2].rowStart=2;
		summaryCellDes[2].rowEnd=2;
		summaryCellDes[2].useFormat=true;
		summaryCellDes[2].align=TextF.Left;
		summaryCellDes[2].useBoldFont=false;
		summaryCellDes[2].fontSize=10;
		
		
		summaryWriteVal[3]="��Ѻ��ծȯ�˺ţ�";				//��ǩ
		summaryCellDes[3].colStart=8;
		summaryCellDes[3].colEnd=8;
		summaryCellDes[3].rowStart=2;
		summaryCellDes[3].rowEnd=2;
		summaryCellDes[3].useFormat=true;
		summaryCellDes[3].align=TextF.Left;
		summaryCellDes[3].useBoldFont=true;
		summaryCellDes[3].fontSize=10;
		
		summaryWriteVal[4]=summary[3];						//�й��˺�
		summaryCellDes[4].colStart=9;
		summaryCellDes[4].colEnd=9;
		summaryCellDes[4].rowStart=2;
		summaryCellDes[4].rowEnd=2;
		summaryCellDes[4].useFormat=true;
		summaryCellDes[4].align=TextF.Left;
		summaryCellDes[4].useBoldFont=false;
		summaryCellDes[4].fontSize=10;
		
		summaryWriteVal[5]="ҵ�����ƣ�";						//��ǩ
		summaryCellDes[5].colStart=0;
		summaryCellDes[5].colEnd=0;
		summaryCellDes[5].rowStart=3;
		summaryCellDes[5].rowEnd=3;
		summaryCellDes[5].useFormat=true;
		summaryCellDes[5].align=TextF.Left;
		summaryCellDes[5].useBoldFont=true;
		summaryCellDes[5].fontSize=10;
		
		summaryWriteVal[6]=summary[4];						//ҵ������
		summaryCellDes[6].colStart=1;
		summaryCellDes[6].colEnd=1;
		summaryCellDes[6].rowStart=3;
		summaryCellDes[6].rowEnd=3;
		summaryCellDes[6].useFormat=true;
		summaryCellDes[6].align=TextF.Left;
		summaryCellDes[6].useBoldFont=false;
		summaryCellDes[6].fontSize=10;
		
	    summaryWriteVal[7]="��ѯʱ�Σ�";						//��ǩ
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
		
		
	    summaryWriteVal[8]=summary[0];						//��ʼ��ѯʱ���
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
		
	    summaryWriteVal[9]=summary[2];						//������ѯʱ���
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
	
	
	//��ʼ���б�ÿһ�еĵ�Ԫ������
	private CellDes[] initColCellDes(int colCount){
		
		if(colCount<=0){
			return null;
		}
		
		//����������������ÿһ�е�Ԫ������ 
		//Ĭ��Ϊ ��Ҫ�ϲ���Ԫ��,������,�п�20,ʹ�����ݸ�ʽ��,�����,��ʹ�ô�����,�б߿�,10����,���յ�4��(��ͬ��)���кϲ�
		CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,true,false,20,true,"L",false,1,10,2);
		
		colCellDes[1].textFormat=TextF.date;		//ҵ��������  ��Ҫת����ʽ
		colCellDes[1].dateFormat=TextF.yyyyNMMYddR;	//����ת����ʽ
		
		colCellDes[15].textFormat=TextF.date;		//��������  ��Ҫת����ʽ
		colCellDes[15].dateFormat=TextF.yyyyNMMYddR;	//����ת����ʽ
		
		colCellDes[4].isMerge=false;				//��Ѻծȯ  �����кϲ�
		colCellDes[5].isMerge=false;				//�������ֵ �����кϲ�
		colCellDes[6].isMerge=false;				//��Ѻȯ��� �����кϲ�
		colCellDes[7].isMerge=false;				//ծȯ��ֵ �����кϲ�
		colCellDes[8].isMerge=false;				//ծȯ��Ѻ�� �����кϲ�
		
		//�Ҷ��롢ǧ��λ��ʾ
		colCellDes[6].align=TextF.RIGHT;		
		colCellDes[6].textFormat=TextF.millSpt;	
		colCellDes[7].align=TextF.RIGHT;		
		colCellDes[7].textFormat=TextF.millSpt;	
		colCellDes[9].align=TextF.RIGHT;		
		colCellDes[9].textFormat=TextF.millSpt;
		colCellDes[10].align=TextF.RIGHT;		
		colCellDes[10].textFormat=TextF.millSpt;	
		colCellDes[11].align=TextF.RIGHT;		
		colCellDes[11].textFormat=TextF.millSpt;
		colCellDes[12].align=TextF.RIGHT;		
		colCellDes[12].textFormat=TextF.millSpt;	
		colCellDes[13].align=TextF.RIGHT;		
		colCellDes[13].textFormat=TextF.millSpt;	
		
		colCellDes[6].decDigits=4;					//��Ѻȯ����Ԫ��  4λС��
		colCellDes[7].decDigits=6;					//ծȯ��ֵ(Ԫ/��Ԫ��ֵ�� 6λС��
		colCellDes[8].decDigits=2;					//ծȯ��Ѻ�ʣ�%�� 6λС��
		colCellDes[10].decDigits=6;					//��Ѻ��ֵ����Ԫ�� 2λС��
		colCellDes[11].decDigits=6;					//���ڽ���Ԫ�� 6λС��
		colCellDes[12].decDigits=4;					//��ֵ����Ԫ�� 4λС��
		colCellDes[13].decDigits=4;					//��ֵ������%�� 4λС��
		colCellDes[14].decDigits=4;					//��ֵ�ٽ������%�� 4λС��
		
		colCellDes[16].useFormat=false;				//��ӡʵ��cdc#PRP100810020100�б��βζ���"��������"һ��,������,���ｫ�����һ����Ϊ����ʾ����
		
		return colCellDes;
		
	}
	
	/**
	 * �����б��ͷ��Ϣ,����ͨ����ӡʵ��ȡ�õ��б�����ת��Ϊ����д��Excle������
	 * ͨ��ʵ��ȡ�õ��б�����,ÿһ�������ǰ���ʵ�����������ڵ��˳�����ɵ�String[]
	 * ��д��Excleʱ�������˳��,������Ҫ��Ӧת��
	 * 
	 * 
	 * @comments	:
	 * @author   	:���� 2012-8-15
	 *
	 * @param tableList				ͨ����ӡʵ��ȡ�õ��б�����
	 * @param tableListLable		д��ʱ���б��ͷ����
	 * @param summary				��ӡʵ�εı���ȡֵ��,�б�ͷ�Ĳ������ݴ�����ȡ��
	 * @param writeList				����д��Excle���б�����
	 */
	private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
		
		tableListLable[0]="ҵ������";			
		tableListLable[1]=summary[5];           //���� ҵ��������,��Ҫ��ʵ�εı���ȡֵ���л�� 
		tableListLable[2]="��ͬ��";
		tableListLable[3]="��Ѻ��";
		tableListLable[4]="��Ѻծȯ";
		tableListLable[5]="�������ֵ";
		tableListLable[6]="��Ѻȯ����Ԫ��";
		tableListLable[7]="ծȯ��ֵ(Ԫ/��Ԫ��ֵ��";
		tableListLable[8]="ծȯ��Ѻ�ʣ�%��";
		tableListLable[9]="��Ѻ��ֵ����Ԫ��";
		tableListLable[10]="���ڽ���Ԫ��";
		tableListLable[11]="��ֵ����Ԫ��";
		tableListLable[12]="��ֵ������%��";
		tableListLable[13]="��ֵ�ٽ������%��";
		tableListLable[14]="��Ѻȯ��ӯ/����";
		tableListLable[15]=summary[6];			//���� �������� ,��Ҫ��ʵ�εı���ȡֵ���л��
		
		//��ͨ����ӡʵ��ȡ�õ��б�������д��Excle���б����ݽ��ж�Ӧ
		//��Ӧ�������Ǵ�ӡģ���е�˳��
		for (int i = 0; i < tableList.size(); i++) {
			String[] aLine=tableList.get(i);
			int colCount=aLine.length;
			String[] writeLine=new String[colCount];
			
			writeLine[0]=aLine[3];				//ҵ������         
			writeLine[1]=aLine[0];				//ҵ��������                              
			writeLine[2]=aLine[1];				//��ͬ��               	              
			writeLine[3]=aLine[2];				//��Ѻ��               	              
			writeLine[4]=aLine[4];				//��Ѻծȯ             	              
			writeLine[5]=aLine[5];				//�������ֵ           	              
			writeLine[6]=aLine[6];				//��Ѻȯ����Ԫ��   	              
			writeLine[7]=aLine[8];				//ծȯ��ֵ(Ԫ/��Ԫ��ֵ��              
			writeLine[8]=aLine[9];				//ծȯ��Ѻ�ʣ�%��      	              
			writeLine[9]=aLine[10];			    //��Ѻ��ֵ����Ԫ��     	              
			writeLine[10]=aLine[7];				//���ڽ���Ԫ��     	              
			writeLine[11]=aLine[11];			//��ֵ����Ԫ��         	              
			writeLine[12]=aLine[12];			//��ֵ������%��        	              
			writeLine[13]=aLine[13];			//��ֵ�ٽ������%��    	              
			writeLine[14]=aLine[14];			//��Ѻȯ��ӯ/����      	              
			writeLine[15]=aLine[16];			//��������
			
			writeList.add(writeLine);
		}
	}
	
	/**
	 * ��ʼ���ϼ��е��е�Ԫ������������
	 * @comments	:
	 * @author   	:���� 2012-8-15
	 *
	 * @param sumCellDes	�ϼ���ÿ�еĵ�Ԫ������,��ʼֵ��������һ��
	 * @param sumWrite		д��ĺϼ�ֵ
	 * @param sumVal		ͨ����ӡʵ�μ�����ĺϼ�ֵ
	 */
	private void initSumCellDes(CellDes[] sumCellDes,String[] sumWrite,String[] sumVal){
		
		sumWrite[9]=sumVal[0];//��Ѻ��ֵ�ϼ� 	��10�� 
		sumWrite[10]=sumVal[1];//���ڽ��ϼ�	��11��
		sumWrite[11]=sumVal[2];//��ֵ�ϼ�		��12��
		
		sumCellDes[10].decDigits=6;//��Ѻ��ֵ�ϼ� 	6λС�� 
		sumCellDes[9].decDigits=6;//���ڽ��ϼ�	6λС�� 
		sumCellDes[11].decDigits=6;//��ֵ�ϼ�		6λС�� 
		
	}
    private void initSubTotalDes(CellDes sumTotalDes){
		
		sumTotalDes.sumCol=new int[]{9,10,11};			//����С�Ƶ���
		sumTotalDes.refMergeCol=new int[]{0};			//����С�ƵĲ�����(С�Ʒ��������),ҵ�����
		
	}
		
}
