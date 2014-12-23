package excelOut;


	import java.util.ArrayList;
	import java.util.HashMap;

import AF.ut.AFUtility;

	/**
	 * ��Ѻ�����ѯ��ӡ
	 * 
	 * �������ڽ�ԭPDF��ӡ�����ݰ���ԭ��ʽ�����Excle��
	 * @comments	:
	 * @author   	:���Ŀ� 2012-8-29
	 *
	 */
	public class ExcelFormatOut13 implements ExcelFormatOut_I{
		private final String prpGUID="cdc#PRP1008100150";
		/**
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
			int summaryCellCount=21;														//����Ϣ������Ԫ������
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

			
			
			//��βȡֵ�� ������Ϣ
			String[] summ=(String[])valMap.get("5");									//��ʵ����ȡֵ,����Ϊʵ����ڵ��sc								
			if(summ==null){
				throw new Exception("δ��ȡ��Map����Ϣ!");
			}
			int summCellCount=5;														//��β��Ϣ������Ԫ������
			CellDes[] summCellDes=ExcelOperPlus.genCellDes(summCellCount,true,false,30,null);//��β��Ϣ��Ԫ����������
			String[] summWriteVal=new String[summCellCount];						//����д��ı�β��Ϣֵ����			
			
			
			
			//��ʼд������
			ExcelOperPlus rExcel=new ExcelOperPlus();									//����Excle������
			rExcel.openExcle(fileName,sheetName);										//��ָ���ļ���,sheet������Excle
			
			for (int i = 0; i < summaryCellDes.length; i++) {							//����д������Ϣ
				rExcel.writeToCell(sheetName, summaryWriteVal[i], summaryCellDes[i], colOffSet);
			}																			//д���б���Ϣ,���������ݺϲ���Ԫ��
			CellDes orderColDes=new CellDes(true,false,5,0);
			rExcel.writeExcelAndMer(writeList, tableListLable,sheetName,listfirstRow,mergeDes,colOffSet,null,null);
			
			int sumRow=rExcel.currentRow;											
			initSummCell(summCellDes,summWriteVal,summ,sumRow);					//��ʼ����β��Ϣ��Ԫ���ʽ������
			
			for (int j = 0; j < summCellDes.length; j++) {							//����д��β��Ϣ
				rExcel.writeToCell(sheetName, summWriteVal[j], summCellDes[j], colOffSet);
			}
			
//			rExcel.writeLine(sheetName, sumWrite, sumRow, sumCellDes, colOffSet);		//д��ϼ�������
			
//			CellDes hjLableCell=new CellDes(-1,sumRow,-1,sumRow,"C",true,1);			//�ϼƱ�ǩ��Ԫ��
//			rExcel.writeToCell(sheetName,"�ϼ�", hjLableCell, colOffSet);
			
			
			rExcel.closeExcle();														//���沢�ر�Excle
			
		}
	
		/**
		 * ��Ѻ�����ѯ��ӡ����Ϣ��ʼ��
		 * @comments	:
		 * @author   	:���Ŀ� 2012-8-29
		 * @param summaryCellDes		��Ԫ����������
		 * @param summaryWriteVal		д�뵥Ԫ���ֵ
		 * @param summary				ͨ��ʵ������ȡ�õ�ֵ
		 */
		private void initSummaryCell(CellDes[] summaryCellDes,String[] summaryWriteVal,String[] summary){
			int id=0;
			
			summaryWriteVal[id]="��Ѻ�����ѯ�б�";	//�������
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=8;							//�ϲ�4��8��Ԫ��
			summaryCellDes[id].rowStart=0;
			summaryCellDes[id].rowEnd=0;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;					//ʹ�ô�����
			summaryCellDes[id].fontSize=20;							//ʹ��20����
			
			id=id+1;
			summaryWriteVal[id]="��Ѻ����ƣ�";				//��Ѻ����� ��ǩ
			summaryCellDes[id].colStart=0;
			summaryCellDes[id].colEnd=0;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[0];							//��Ѻ�����
			summaryCellDes[id].colStart=1;
			summaryCellDes[id].colEnd=1;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]="��Ѻ��ծȯ�˺ţ�";					//��Ѻ��ծȯ�˺� ��ǩ
			summaryCellDes[id].colStart=2;
			summaryCellDes[id].colEnd=2;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[1];							//��Ѻ��ծȯ�˺�
			summaryCellDes[id].colStart=3;
			summaryCellDes[id].colEnd=3;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]="��Ѻ����ƣ�";					//��Ѻ����� ��ǩ
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=4;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[2];							//��Ѻ�����
			summaryCellDes[id].colStart=5;
			summaryCellDes[id].colEnd=5;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="��Ѻ��ծȯ�˺ţ�";					//��Ѻ��ծȯ�˺� ��ǩ
			summaryCellDes[id].colStart=6;
			summaryCellDes[id].colEnd=6;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[3];							//��Ѻ��ծȯ�˺�
			summaryCellDes[id].colStart=7;
			summaryCellDes[id].colEnd=7;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;


			id=id+1;
			summaryWriteVal[id]="ҵ�����";					//ҵ����� ��ǩ
			summaryCellDes[id].colStart=8;
			summaryCellDes[id].colEnd=8;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[4];							//ҵ����� ��ǩ
			summaryCellDes[id].colStart=9;
			summaryCellDes[id].colEnd=9;
			summaryCellDes[id].rowStart=2;
			summaryCellDes[id].rowEnd=2;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;

			
			id=id+1;
			summaryWriteVal[id]="���ڽ��(Ԫ)��";						//���ڽ�� ��ǩ
			summaryCellDes[id].colStart=0;
			summaryCellDes[id].colEnd=0;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;
			
			id=id+1;
			summaryWriteVal[id]=summary[6];							//���ڽ��
			summaryCellDes[id].colStart=1;
			summaryCellDes[id].colEnd=1;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="ʵ�ʳ��ڽ��(Ԫ)��";						//ʵ�ʳ��ڽ�� ��ǩ
			summaryCellDes[id].colStart=2;
			summaryCellDes[id].colEnd=2;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;

			id=id+1;
			summaryWriteVal[id]=summary[7];							//ʵ�ʳ��ڽ��
			summaryCellDes[id].colStart=3;
			summaryCellDes[id].colEnd=3;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
			

			id=id+1;
			summaryWriteVal[id]="��Ѻȯ��ֵ(Ԫ)��";						//��Ѻȯ��ֵ ��ǩ
			summaryCellDes[id].colStart=4;
			summaryCellDes[id].colEnd=4;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=true;
			summaryCellDes[id].fontSize=10;

			id=id+1;
			summaryWriteVal[id]=summary[8];							//��Ѻȯ��ֵ
			summaryCellDes[id].colStart=5;
			summaryCellDes[id].colEnd=5;
			summaryCellDes[id].rowStart=4;
			summaryCellDes[id].rowEnd=4;
			summaryCellDes[id].useFormat=true;
			summaryCellDes[id].align=TextF.CENTRE;
			summaryCellDes[id].useBoldFont=false;
			summaryCellDes[id].fontSize=10;
		

		id=id+1;
		summaryWriteVal[id]="���ڽ�ɫ��";						//���ڽ�ɫ ��ǩ
		summaryCellDes[id].colStart=6;
		summaryCellDes[id].colEnd=6;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=true;
		summaryCellDes[id].fontSize=10;

		id=id+1;
		summaryWriteVal[id]=summary[9];							//���ڽ�ɫ
		summaryCellDes[id].colStart=7;
		summaryCellDes[id].colEnd=7;
		summaryCellDes[id].rowStart=4;
		summaryCellDes[id].rowEnd=4;
		summaryCellDes[id].useFormat=true;
		summaryCellDes[id].align=TextF.CENTRE;
		summaryCellDes[id].useBoldFont=false;
		summaryCellDes[id].fontSize=10;
		
		
		id=id+1;
		summaryWriteVal[id]="�����գ�";						//������ ��ǩ
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
		summaryWriteVal[id]=summary[5];							//������
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
		
		
		//��ʼ���б�ÿһ�еĵ�Ԫ������
		private CellDes[] initColCellDes(int colCount){
			
			if(colCount<=0){
				return null;
			}
			
			//����������������ÿһ�е�Ԫ������ 
			//Ĭ��Ϊ ��Ҫ�ϲ���Ԫ��,������,�п�20,ʹ�����ݸ�ʽ��,�����,��ʹ�ô�����,�б߿�,10����,���պ�ͬ�Ž��кϲ�
			CellDes[] colCellDes=ExcelOperPlus.genMergeDes(colCount,false,false,20,true,"L",false,1,10,3);
			
			colCellDes[9].textFormat=TextF.date;			//��������  ��Ҫת����ʽ
			colCellDes[9].dateFormat=TextF.yyyy_MM_dd;		//����ת����ʽ
			
			colCellDes[10].textFormat=TextF.date;			//ȡ������  ��Ҫת����ʽ
			colCellDes[10].dateFormat=TextF.yyyy_MM_dd;		//����ת����ʽ
			
			colCellDes[11].textFormat=TextF.date;			//��������  ��Ҫת����ʽ
			colCellDes[11].dateFormat=TextF.yyyy_MM_dd_hsm;		//����ת����ʽ
			
			
			for (int i = 3; i <= 6; i++) {				//8��15�ж�Ϊ����
				colCellDes[i].align=TextF.RIGHT;		//�Ҷ���
				colCellDes[i].textFormat=TextF.millSpt;	//ǧ��λ��ʾ
			}
			
			colCellDes[3].decDigits=2;					//ծȯ����Ԫ��  2λС��
			colCellDes[4].decDigits=2;					//��Ѻȯ��ֵ��Ԫ�� 2λС��
			colCellDes[5].decDigits=6;					//��Ѻȯ�۸�(Ԫ/��Ԫ�� 6λС��
			colCellDes[6].decDigits=6;					//��Ѻ�ʣ�%�� 6λС��
			
			return colCellDes;
			
		}
		
		/**
		 * �����б��ͷ��Ϣ,����ͨ����ӡʵ��ȡ�õ��б�����ת��Ϊ����д��Excle������
		 * ͨ��ʵ��ȡ�õ��б�����,ÿһ�������ǰ���ʵ�����������ڵ��˳�����ɵ�String[]
		 * ��д��Excleʱ�������˳��,������Ҫ��Ӧת��
		 * 
		 * 
		 * @comments	:
		 * @author   	:���Ŀ� 2012-8-29
		 *
		 * @param tableList				ͨ����ӡʵ��ȡ�õ��б�����
		 * @param tableListLable		д��ʱ���б��ͷ����
		 * @param summary				��ӡʵ�εı���ȡֵ��,�б�ͷ�Ĳ������ݴ�����ȡ��
		 * @param writeList				����д��Excle���б�����
		 */
		private void initTableList(ArrayList<String[]> tableList,String[] tableListLable,String[] summary,ArrayList<String[]> writeList){
			tableListLable[0]="��Ӧ��ͬ��";					
			tableListLable[1]="ծȯ����";
			tableListLable[2]="ծȯ���";				
			tableListLable[3]="ծȯ����Ԫ��";
			tableListLable[4]="��Ѻȯ��ֵ(Ԫ)";
			tableListLable[5]="��Ѻȯ�۸�Ԫ/��Ԫ��";
			tableListLable[6]="��Ѻ��(%)";
			tableListLable[7]="��������";
			tableListLable[8]="��¼״̬";
			tableListLable[9]="��������";
			tableListLable[10]="ȡ������";
			tableListLable[11]="����ʱ��";
			
			//��ͨ����ӡʵ��ȡ�õ��б�������д��Excle���б����ݽ��ж�Ӧ
			//��Ӧ�������Ǵ�ӡģ���е�˳��
			for (int i = 0; i < tableList.size(); i++) {
				String[] aLine=tableList.get(i);
				int colCount=aLine.length;
				String[] writeLine=new String[colCount];
				
				writeLine[0]=aLine[0];				//��Ӧ��ͬ��
				writeLine[1]=aLine[1];				//ծȯ����
				writeLine[2]=aLine[2];				//ծȯ���
				writeLine[3]=aLine[3];				//ծȯ����Ԫ��
				writeLine[4]=aLine[4];				//��Ѻȯ��ֵ(Ԫ)
				writeLine[5]=aLine[5];				//��Ѻȯ�۸�Ԫ/��Ԫ��
				writeLine[6]=aLine[6];				//��Ѻ��(%)
				writeLine[7]=aLine[7];				//��������
				writeLine[8]=aLine[8];				//��¼״̬
				writeLine[9]=aLine[9];				//��������
				writeLine[10]=aLine[10];			//ȡ������
				writeLine[11]=aLine[11];			//����ʱ��	
				writeList.add(writeLine);
			}
		}
		
		
		
		/**
		 * ��Ѻ�����ѯ��ӡ��β��Ϣ��ʼ��
		 * @comments	:
		 * @author   	:���Ŀ� 2012-8-29
		 * @param sumRow 
		 * @param summaryCellDes		��Ԫ����������
		 * @param summaryWriteVal		д�뵥Ԫ���ֵ
		 * @param summary				ͨ��ʵ������ȡ�õ�ֵ
		 */
		private void initSummCell(CellDes[] summCellDes, String[] summWriteVal,String[] summ, int sumRow) {
			int tail=0;
			summWriteVal[tail]="�����ծ�Ǽǽ����������ι�˾";				//�����ծ�Ǽǽ����������ι�˾ ��ǩ
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+1;
			summCellDes[tail].rowEnd=sumRow+1;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]="��ӡʱ�䣺";				//��ӡʱ�� ��ǩ
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+2;
			summCellDes[tail].rowEnd=sumRow+2;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]=summ[0];							//��ӡʱ��
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
			summWriteVal[tail]="��ӡ�ˣ�";					//��ӡ�� ��ǩ
			summCellDes[tail].colStart=10;
			summCellDes[tail].colEnd=10;
			summCellDes[tail].rowStart=sumRow+3;
			summCellDes[tail].rowEnd=sumRow+3;
			summCellDes[tail].useFormat=true;
			summCellDes[tail].align=TextF.CENTRE;
			summCellDes[tail].useBoldFont=true;
			summCellDes[tail].fontSize=10;
			
			tail=tail+1;
			summWriteVal[tail]=summ[1];							//��ӡ��
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

