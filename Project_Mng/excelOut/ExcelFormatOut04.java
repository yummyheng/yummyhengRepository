package excelOut;

import java.util.ArrayList;
import java.util.HashMap;

import AF.ut.AFUtility;

/**
 * �����г�Ѻ����̨�ˣ���ҵ�����ƣ�
 * 
 * �������ڽ�ԭPDF��ӡ�����ݰ���ԭ��ʽ�����Excle��
 * @comments	:
 * @author   	:�ο� 2012-8-14
 *
 */
public class ExcelFormatOut04 implements ExcelFormatOut_I{
	private final String prpGUID="cdc#PRP100810020102";
	/**
	 * ���ݵ�Excle�ض���ʽ���
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param fileName		���Excel�ļ���(ȫ·��)
	 * @param sheetName		���sheet��
	 * @throws Exception
	 */
	public void export(String fileName,String sheetName) throws Exception{
		
		int colOffSet=1;															//д������ƫ����,������������
		int listfirstRow=6;															//�б�����д������к�
		
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
	 * �����г�Ѻ����̨�ˣ���ҵ���������Ϣ��ʼ��
	 * @comments	:
	 * @author   	:�ο� 2012-8-14
	 *
	 * @param summaryCellDes		��Ԫ����������
	 * @param summaryWriteVal		д�뵥Ԫ���ֵ
	 * @param summary				ͨ��ʵ������ȡ�õ�ֵ
	 */
	private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
		int id=0;
		
		summaryWriteVal[id]="�����г�Ѻ����̨�ˣ���ҵ�����ƣ�";	//�������
		summaryCellDes[id].colStart=4;
		summaryCellDes[id].colEnd=8;							//�ϲ�4��8��Ԫ��
		summaryCellDes[id].rowStart=0;
		summaryCellDes[id].rowEnd=0;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;					//ʹ�ô�����
		summaryCellDes[id].fontSize=20;							//ʹ��20����
		
		id=id+1;
		summaryWriteVal[id]="��Ѻ��ծȯ�˻���ƣ�";				//��Ѻ��ծȯ�˻���� ��ǩ
		summaryCellDes[id].colStart=2;
		summaryCellDes[id].colEnd=2;
		summaryCellDes[id].rowStart=2;
		summaryCellDes[id].rowEnd=2;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]=summary[1];							//��λ����
		summaryCellDes[id].colStart=3;
		summaryCellDes[id].colEnd=3;
		summaryCellDes[id].rowStart=2;
		summaryCellDes[id].rowEnd=2;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]="��Ѻ��ծȯ�˺ţ�";					//��Ѻ��ծȯ�˺� ��ǩ
		summaryCellDes[id].colStart=5;
		summaryCellDes[id].colEnd=5;
		summaryCellDes[id].rowStart=2;
		summaryCellDes[id].rowEnd=2;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]=summary[3];							//�й��˺�
		summaryCellDes[id].colStart=6;
		summaryCellDes[id].colEnd=6;
		summaryCellDes[id].rowStart=2;
		summaryCellDes[id].rowEnd=2;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]="ҵ�����ƣ�";						//ҵ������ ��ǩ
		summaryCellDes[id].colStart=2;
		summaryCellDes[id].colEnd=2;
		summaryCellDes[id].rowStart=3;
		summaryCellDes[id].rowEnd=3;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]=summary[4];							//ҵ������
		summaryCellDes[id].colStart=3;
		summaryCellDes[id].colEnd=3;
		summaryCellDes[id].rowStart=3;
		summaryCellDes[id].rowEnd=3;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		
		id=id+1;
		summaryWriteVal[id]="��ѯʱ�Σ�";						//��ѯʱ�� ��ǩ
		summaryCellDes[id].colStart=2;
		summaryCellDes[id].colEnd=2;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;
		summaryCellDes[id].textFormat=TextF.date;
		summaryCellDes[id].dateFormat=TextF.yyyyNMMYddR;
		
		id=id+1;
		summaryWriteVal[id]=summary[0];							//��ʼ��ѯʱ���
		summaryCellDes[id].colStart=3;
		summaryCellDes[id].colEnd=3;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		summaryCellDes[id].textFormat=TextF.date;
		summaryCellDes[id].dateFormat=TextF.yyyyNMMYddR;
		
		id=id+1;
		summaryWriteVal[id]=summary[2];							//������ѯʱ���
		summaryCellDes[id].colStart=4;
		summaryCellDes[id].colEnd=4;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		summaryCellDes[id].textFormat=TextF.date;
		summaryCellDes[id].dateFormat=TextF.yyyyNMMYddR;
		
	}
	
	
	//��ʼ���б�ÿһ�еĵ�Ԫ������
	private CellDes[] initColCellDes(int colCount){
		
		if(colCount<=0){
			return null;
		}
		
		//����������������ÿһ�е�Ԫ������ 
		//Ĭ��Ϊ ��Ҫ�ϲ���Ԫ��,������,�п�20,ʹ�����ݸ�ʽ��,�����,��ʹ�ô�����,�б߿�,10����,���պ�ͬ�Ž��кϲ�
		CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,true,false,20,true,"L",false,1,10,2);
		
		colCellDes[1].textFormat=TextF.date;			//ҵ��������  ��Ҫת����ʽ
		colCellDes[1].dateFormat=TextF.yyyyNMMYddR;		//����ת����ʽ
		
		colCellDes[4].isMerge=false;				//��Ѻծȯ  �����кϲ�
		colCellDes[5].isMerge=false;				//�������ֵ �����кϲ�
		colCellDes[6].isMerge=false;				//��Ѻȯ��� �����кϲ�
		colCellDes[7].isMerge=false;				//ծȯ��ֵ �����кϲ�
		colCellDes[8].isMerge=false;				//ծȯ��Ѻ�� �����кϲ�
		
		for (int i = 6; i <= 13; i++) {				//8��15�ж�Ϊ����
			colCellDes[i].align=TextF.RIGHT;		//�Ҷ���
			colCellDes[i].textFormat=TextF.millSpt;	//ǧ��λ��ʾ
		}
		
		colCellDes[6].decDigits=4;					//��Ѻȯ����Ԫ��  4λС��
		colCellDes[7].decDigits=6;					//ծȯ��ֵ(Ԫ/��Ԫ��ֵ�� 6λС��
		colCellDes[8].decDigits=6;					//ծȯ��Ѻ�ʣ�%�� 6λС��
		colCellDes[9].decDigits=6;					//��Ѻ��ֵ����Ԫ��6λС��
		colCellDes[10].decDigits=6;					//���ڽ���Ԫ�� 6λС��
		colCellDes[11].decDigits=6;					//��ֵ����Ԫ�� 6λС��
		colCellDes[12].decDigits=4;					//��ֵ������%�� 4λС��
		colCellDes[13].decDigits=4;					//��ֵ�ٽ������%�� 4λС��
		
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
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param tableList				ͨ����ӡʵ��ȡ�õ��б�����
	 * @param tableListLable		д��ʱ���б��ͷ����
	 * @param summary				��ӡʵ�εı���ȡֵ��,�б�ͷ�Ĳ������ݴ�����ȡ��
	 * @param writeList				����д��Excle���б�����
	 */
	private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
		tableListLable[0]="ҵ������";
		tableListLable[1]=summary[5];				// ҵ��������,��Ҫ��ʵ�εı���ȡֵ���л��
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
		tableListLable[15]=summary[6];				// �������� ,��Ҫ��ʵ�εı���ȡֵ���л��
		
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
			writeLine[9]=aLine[10];				//��Ѻ��ֵ����Ԫ��
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
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param sumCellDes	�ϼ���ÿ�еĵ�Ԫ������,��ʼֵ��������һ��
	 * @param sumWrite		д��ĺϼ�ֵ
	 * @param sumVal		ͨ����ӡʵ�μ�����ĺϼ�ֵ
	 */
	private void initSumCellDes(CellDes[] sumCellDes,String[] sumWrite,String[] sumVal){
		
		sumWrite[9]=sumVal[0];//��Ѻ��ֵ�ϼ� 	��10�� 
		sumWrite[10]=sumVal[1];//���ڽ��ϼ�	��11��
		sumWrite[11]=sumVal[2];//��ֵ�ϼ�		��12��
		
		sumCellDes[9].decDigits=6;//��Ѻ��ֵ�ϼ� 	6λС�� 
		sumCellDes[10].decDigits=6;//���ڽ��ϼ�	6λС�� 
		sumCellDes[11].decDigits=6;//��ֵ�ϼ�		6λС�� 
		
	}
	private void initSubTotalDes(CellDes sumTotalDes){
		
		sumTotalDes.sumCol=new int[]{9,10,11};			//����С�Ƶ���
		sumTotalDes.refMergeCol=new int[]{0};			//����С�ƵĲ�����(С�Ʒ��������),ҵ�����
		
	}
		
}
