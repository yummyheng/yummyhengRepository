package excelOut;

import java.util.ArrayList;
import java.util.HashMap;

import AF.ut.AFUtility;

/**
 * ��� �������Ѻ����̨�ˣ���ҵ�����
 * 
 * �������ڽ�ԭPDF��ӡ�����ݰ���ԭ��ʽ�����Excle��
 * @comments	:
 * @author   	:���� 2012-8-16
 *
 */
public class ExcelFormatOut11 implements ExcelFormatOut_I{
	private final String prpGUID="cdc#PRP100810020111";
	
	/**
	 * ��� �������Ѻ����̨�ˣ���ҵ�����
	 * @comments	:
	 * @author   	:���� 2012-8-16
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
		
		String[] sumVal=ExcelOperPlus.genSumS(sumList,0,1);						//���ɺϼ�����,�ֱ�Ե�0,1,2�н��кϼ�
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
	 * �������Ѻ����̨�ˣ���ҵ���������Ϣ��ʼ��
	 * @comments	:
	 * @author   	:���� 2012-8-16
	 *
	 * @param summaryCellDes		��Ԫ����������
	 * @param summaryWriteVal		д�뵥Ԫ���ֵ
	 * @param summary				ͨ��ʵ������ȡ�õ�ֵ
	 */
	private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
		
		summaryWriteVal[0]="�������Ѻ����̨�ˣ���ҵ�����";//�������
		summaryCellDes[0].colStart=2;
		summaryCellDes[0].colEnd=6;							//�ϲ�4��8��Ԫ��
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
		
		
		summaryWriteVal[2]=summary[0];						//��λ����
		summaryCellDes[2].colStart=1;
		summaryCellDes[2].colEnd=1;
		summaryCellDes[2].rowStart=2;
		summaryCellDes[2].rowEnd=2;
		summaryCellDes[2].useFormat=true;
		summaryCellDes[2].align=TextF.Left;
		summaryCellDes[2].useBoldFont=false;
		summaryCellDes[2].fontSize=10;
		
		
		summaryWriteVal[3]="��Ѻ��ծȯ�˺ţ�";				//��ǩ
		summaryCellDes[3].colStart=6;
		summaryCellDes[3].colEnd=6;
		summaryCellDes[3].rowStart=2;
		summaryCellDes[3].rowEnd=2;
		summaryCellDes[3].useFormat=true;
		summaryCellDes[3].align=TextF.Left;
		summaryCellDes[3].useBoldFont=true;
		summaryCellDes[3].fontSize=10;
		
		summaryWriteVal[4]=summary[2];						//�й��˺�
		summaryCellDes[4].colStart=7;
		summaryCellDes[4].colEnd=7;
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
		
		
	    summaryWriteVal[8]=summary[3];						//��ʼ��ѯʱ���
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
		
	    summaryWriteVal[9]=summary[1];						//������ѯʱ���
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
		CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,true,false,20,true,"L",false,1,10,3);
		
		colCellDes[2].textFormat=TextF.date;		//ҵ��������  ��Ҫת����ʽ
		colCellDes[2].dateFormat=TextF.yyyyNMMYddR;	//����ת����ʽ
		
		colCellDes[9].textFormat=TextF.date;		//��������  ��Ҫת����ʽ
		colCellDes[9].dateFormat=TextF.yyyyNMMYddR;	//����ת����ʽ
		
		colCellDes[5].isMerge=false;				//��Ѻծȯ  �����кϲ�
		colCellDes[6].isMerge=false;				//��Ѻȯ��� �����кϲ�
		
		//�Ҷ��롢ǧ��λ��ʾ
		colCellDes[6].align=TextF.RIGHT;		
		colCellDes[6].textFormat=TextF.millSpt;	
		colCellDes[7].align=TextF.RIGHT;		
		colCellDes[7].textFormat=TextF.millSpt;	
		
		colCellDes[6].decDigits=4;					//��Ѻȯ����Ԫ��  4λС��
		colCellDes[7].decDigits=6;					//���ڽ���Ԫ�� 6λС��
		
//		colCellDes[16].useFormat=false;				//��ӡʵ��cdc#PRP100810020100�б��βζ���"��������"һ��,������,���ｫ�����һ����Ϊ����ʾ����
		
		return colCellDes;
		
	}
	
	/**
	 * �����б��ͷ��Ϣ,����ͨ����ӡʵ��ȡ�õ��б�����ת��Ϊ����д��Excle������
	 * ͨ��ʵ��ȡ�õ��б�����,ÿһ�������ǰ���ʵ�����������ڵ��˳�����ɵ�String[]
	 * ��д��Excleʱ�������˳��,������Ҫ��Ӧת��
	 * 
	 * 
	 * @comments	:
	 * @author   	:���� 2012-8-16
	 *
	 * @param tableList				ͨ����ӡʵ��ȡ�õ��б�����
	 * @param tableListLable		д��ʱ���б��ͷ����
	 * @param summary				��ӡʵ�εı���ȡֵ��,�б�ͷ�Ĳ������ݴ�����ȡ��
	 * @param writeList				����д��Excle���б�����
	 */
	private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
		
		tableListLable[0]="ҵ�����";
		tableListLable[1]="ҵ������";			                                                                
		tableListLable[2]=summary[5];           //���� ҵ��������,��Ҫ��ʵ�εı���ȡֵ���л��          
		tableListLable[3]="��ͬ��";                                                                       
		tableListLable[4]="��Ѻ��";                                                                       
		tableListLable[5]="��Ѻծȯ";                                                                     
		tableListLable[6]="��Ѻȯ����Ԫ��";                                                           
		tableListLable[7]="���ڽ���Ԫ��";                                                             
		tableListLable[8]="������ɽ��";                                                                 
		tableListLable[9]=summary[6];			//���� �������� ,��Ҫ��ʵ�εı���ȡֵ���л��
		
		//��ͨ����ӡʵ��ȡ�õ��б�������д��Excle���б����ݽ��ж�Ӧ
		//��Ӧ�������Ǵ�ӡģ���е�˳��
		for (int i = 0; i < tableList.size(); i++) {
			String[] aLine=tableList.get(i);
			int colCount=aLine.length;
			String[] writeLine=new String[colCount];
			
			writeLine[0]=aLine[7];				//ҵ�����   
			writeLine[1]=aLine[9];				//ҵ������                                   
			writeLine[2]=aLine[0];				//ҵ��������          	              
			writeLine[3]=aLine[1];				//��ͬ��                	              
			writeLine[4]=aLine[2];				//��Ѻ��                	              
			writeLine[5]=aLine[3];				//��Ѻծȯ            	              
			writeLine[6]=aLine[4];				//��Ѻȯ����Ԫ��  	              
			writeLine[7]=aLine[5];			  //���ڽ���Ԫ��                  
			writeLine[8]=aLine[6];			  //������ɽ��      	
			writeLine[9]=aLine[8];			  //��������
			
			writeList.add(writeLine);
		}
	}
	
	/**
	 * ��ʼ���ϼ��е��е�Ԫ������������
	 * @comments	:
	 * @author   	:���� 2012-8-16
	 *
	 * @param sumCellDes	�ϼ���ÿ�еĵ�Ԫ������,��ʼֵ��������һ��
	 * @param sumWrite		д��ĺϼ�ֵ
	 * @param sumVal		ͨ����ӡʵ�μ�����ĺϼ�ֵ
	 */
	private void initSumCellDes(CellDes[] sumCellDes,String[] sumWrite,String[] sumVal){
		
		sumWrite[6]=sumVal[0];//��Ѻȯ����Ԫ�� 
		sumWrite[7]=sumVal[1];//���ڽ��ϼ�
		
		sumCellDes[6].decDigits=4;//��Ѻȯ����Ԫ�� 	4λС�� 
		sumCellDes[7].decDigits=6;//���ڽ��ϼ�	6λС�� 
		
	}
    private void initSubTotalDes(CellDes sumTotalDes){
		
		sumTotalDes.sumCol=new int[]{6,7};			//����С�Ƶ���
		sumTotalDes.refMergeCol=new int[]{0};			//����С�ƵĲ�����(С�Ʒ��������),ҵ�����
		
	}
		
}
