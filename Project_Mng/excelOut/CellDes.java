package excelOut;

public class CellDes {

		public int colStart;					//��Ԫ����ʼ�к�
		public int rowStart;					//��Ԫ����ʼ�к�
		public int colEnd;						//��Ԫ����ֹ�к�
		public int rowEnd;						//��Ԫ����ֹ�к�
		
		public String lastData="";				//��һ�иõ�Ԫ���ֵ,���ںϹ���Ԫ��
		public boolean isMerge=false;			//�õ�Ԫ���Ƿ�������ݺϲ�
		
		public int[] refMergeCol;				//���ݺϲ�ʱ,���յ��к����� isMerge==trueʱ��Ч
		public int[] refMergeRow;				//���ݺϲ�ʱ,���յ��к����� isMerge==trueʱ��Ч
		
		public boolean doSubtotal=false;		//�Ƿ���Ҫ����С��
		public int[] sumCol;					//��Ҫ����С�Ƶ��к�
		public boolean sumRowWTV=false;		//С�����Ƿ�д����ϼ����������е�ֵ(��������Ҫ���ڶ���С��ʱʹ��,д�������е�ֵ���������ϲ�)
		
		public boolean isNumber=false;		//�õ�Ԫ���Ƿ�Ϊ����
		
		public int cellLen=0;					//��Ԫ���п��,
		
		public boolean useFormat=false;		//�Ƿ�ʹ�õ�Ԫ�����ݸ�ʽ��
		
		//�������Ե�useFormat==trueʱ��Ч
		public int decDigits=0;				//С��λ��
		public String textFormat="N";			//ת����ʽ,Ŀǰ֧�� millSptǧ��λ��ʾ,date���ڸ�ʽ
		public String dateFormat="yyyyMMdd";	//���ڸ�ʽ,��textFormat=dateʱ��Ч
		public String align="L";				//���뷽ʽ
		public boolean useBoldFont=false;		//�Ƿ�ʹ�ô�����
		public int borderSzie=-1;				//�߿��ϸ.-1��ʾ������߿�.0:����,1ϸ�߿�,>=2�ֱ߿�
		public int fontSize=10;				//�����С
		
	public CellDes(){
		
	}
	/**
	 * ���캯��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param isMerge			�Ƿ����ϲ�
	 * @param refMergeCol		�ϲ�ʱ���յ��к�,�����趨����
	 */
	public CellDes(boolean isMerge,int... refMergeCol){
		this.isMerge=isMerge;
		this.refMergeCol=refMergeCol;
	}
	
	/**
	 * ���캯��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param isMerge			�Ƿ����ϲ�
	 * @param isNumber			��Ԫ���Ƿ������ָ�ʽ
	 * @param cellLen			�п��
	 * @param refMergeCol		�ϲ�ʱ���յ��к�,�����趨����
	 */
	public CellDes(boolean isMerge,boolean isNumber,int cellLen,int... refMergeCol){
		this.isMerge=isMerge;
		this.refMergeCol=refMergeCol;
		this.isNumber=isNumber;
		this.cellLen=cellLen;
	}
	
	/**
	 * ���캯��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param colStart			��Ԫ����ʼ�к�
	 * @param rowStart			��Ԫ����ʼ�к�
	 * @param colEnd			��Ԫ����ֹ�к�
	 * @param rowEnd			��Ԫ����ֹ�к�
	 */
	public CellDes(int colStart,int rowStart,int colEnd,int rowEnd){
		this.colStart=colStart;
		this.rowStart=rowStart;
		this.colEnd=colEnd;
		this.rowEnd=rowEnd;
		
	}	
	
	/**
	 * ���캯��
	 * @comments	:
	 * @author   	:�ο� 2012-8-7
	 *
	 * @param colStart			��Ԫ����ʼ�к�
	 * @param rowStart			��Ԫ����ʼ�к�
	 * @param colEnd			��Ԫ����ֹ�к�
	 * @param rowEnd			��Ԫ����ֹ�к�
	 * @param align				���ݶ��뷽ʽ "L"�����;"C"����;"R"�Ҷ���
	 * @param useBoldFont		��Ԫ���Ƿ�ʹ�ô�����
	 * @param borderSzie		��Ԫ��߿� 0:����;1:ϸ�߿�;2:�ϴֱ߿�
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
