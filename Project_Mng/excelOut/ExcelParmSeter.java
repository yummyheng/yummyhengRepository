package excelOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ADM.Factory.ABParmFactory;
import ADM.Factory.IABParmFactory;
import ADM.po.AFComboObj;
import ADM.prt.PMExpParserI;
import ADM.prt.PMPrintDesRoot;
import ADM.prt.PMPrintImageDes;
import ADM.prt.PMPrintItemDes;
import ADM.prt.PMPrintItemsDes;
import ADM.prt.PMPrintItemsValue;
import ADM.prt.PMprpGroup;
import ADM.prt.PMprpItem;
import ADM.prt.PMprpRoot;
import AF.BaseType.AF2int;
import AF.po.AFPEValObj;
import AF.ut.AFUtility;
import AF.ut.AFutDate;
import AF.ut.AFutPEResule;
import AF.ut.AFutPEgetValueAdapter;
import AF.ut.AFutPEgetValueI;
import AF.ut.AFutParseExpression;
import Dp.util.D2C;
import UB.pub.UBUtility;
import UB.pub.UBViewDesCG;

import com.cdc.esb.api.ESBDataSet;
import com.cdc.esb.api.ESBResponse;

/**
 * ��ӡʵ�ν�����
 * 
 * ʵ��֧�ֱ��ʽ�� ������ֵ���ʽ �磺 x01>2 || p0==null ? substring(p02,0,I01):p99 ֧�����³����� 1234
 * 123.01 0x1f 16������ null ֧�����º�����
 *   1��substring(String inStr,int s,int e)�� �õ�ĳ�ַ���inStr�� s �� e ���Ӵ��� s ��0��ʼ��
 *   2��substring(String inStr,int s)         �õ�ĳ�ַ���inStr�� sλ�ÿ�ʼһֱ����β���Ӵ���
 *   3��lengthof(String)                      �õ�ĳ�ַ����ĳ���
 *   
 *   ���磺 substring(S01,lengthof(S02))
 *   ֧���ַ����ӷ� S08+substring(S01,2,11)
 * 
 * ����˵���� ʹ��realRarmParse������������һ����ӡʵ������PRD������н��������÷���������PRD��ʵ�α��ʽ����������ֵ��
 * ����ֵ������PRP��Ӧ�Ĵ�ӡ����PD�У�
 * �÷������ȸ���ʵ�������Ը���ʵ�ν��н�����δ�βθ�ֵ��������ɺ󣬵���parsePD�������β��ڶ���ı��ʽ�βν��м��㣬 ��ȡ�����ֵ��
 * 
 * ע�⣬PD�е����ۼ���û�д������ۼƼ����ڴ�ӡ�������ɴ�ӡ���������ɣ�
 * �ۼ����еı��ʽ��Ҫ����parseOneGroup��group�������н��м��㣬�������ڸ�����ۼ��м�����ɺ���á�
 * 
 * 
 * @author �ο� 2012/08/01
 * 
 */
public class ExcelParmSeter implements PMExpParserI {
	private char DT;					//������Դ���� P S(DataSet) B(BEO) M(SvcBus �е�map)
	private AFComboObj BEO;
	private String DataSet;
	private PMPrintDesRoot PD;
	private IABParmFactory Factory = ABParmFactory.Factory; // ͨ�ò�������,����Ϊʹ�ô�����ֶ��ṩ����ֵ
	private Object BeoIns; // BEOʵ��
	private String instance;//BEOʵ����
	private List ListBeo = null; // ���BeoIns��List��BeoInsת��Ϊ ListBeo
	private Object Cbeo; // ��BEOΪListʱ����ǰ�е�beo����
	private UBUtility Utl = new UBUtility();
	private List<PMPrintItemsValue> PIVs; // ĳ��Ĵ�ӡֵ�б�
	private PMPrintItemsValue PIV; // ĳ���ӡֵ�б�ĵ�ǰֵ����
	private ESBResponse RSP=(ESBResponse) UBViewDesCG.ObjFct.getAObj(
			"com.cdc.esb.api.ESBResponseImpl", "ESBReponseInFct");//ESB����ֵ	//�ο� 20100313 ��
	private ESBDataSet EsbDataSet;											//�ο� 20100313 ��
	
	private HashMap RMP=(HashMap)UBViewDesCG.ObjFct.getAObj("java.util.HashMap", "SvcMap");//�ο� 20100810 ��,Ϊ�˴���UAP�Ĵ�ӡʱ�������
	private int prtFlag=0;					//��ӡ�������ݿ��� 0:����ӡ 1:��ӡ
//	private int prtFlag=1;					//��ӡ�������ݿ��� 0:����ӡ 1:��ӡ
	
	private HashMap parseData=new HashMap();//�������ʵ��������������ֵ,���ظ�������
	AFutPEgetValueI valGeter = null;
	
	AFutPEgetValueI valGeterForPD = new APValGetForPD();// ͨ�����ʽ��ȡֵ
	List groups=null;

	public ExcelParmSeter() {

	}

	public int realRarmParse(PMprpRoot prp) throws Exception {
		if (prp == null)
			return -1; // ��ӡʵ������Ϊ��
		if (!prp.hasChildren())
			return -2; // ��ӡʵ�������������κ���
		groups = prp.gainChildren();
		if (groups == null || groups.size() < 1)
			return -3; // ��ӡʵ�������������κ���
		
		try { // �õ���ʵ��������Ӧ�Ĵ�ӡ����
			PMPrintDesRoot pd = (PMPrintDesRoot) Factory.getItemByGUID(prp.getPD().getKey());
			PD = AFUtility.cloneObjByXstream(pd);
			PD.setExpParser(this); // Ϊ��ӡ�������ý�����
		} catch (Exception e) {
			e.printStackTrace();
		}

		syn(PD,prp);//ʵ�Σ��βν���ͬ��  ���
		
		// Ϊ���ʽ����������ȡֵ��
		valGeter = new APValGet();
		
		/////////////////////////////////////////////////////////////
		printESB(RSP);				//����ʱʹ��.��EsbD�е�key��ֵ��ӡ����
		////////////////////////////////////////////////////////////
		return realRarmParsePage(1);
	}
	
	public int realRarmParsePage(int page) throws Exception {
		PD.clear();//�Ѷ�̬�������ֵ�����
		AFutParseExpression.setGetValHandler(valGeter);
		for (int i = 0; i < groups.size(); i++) {
			PMprpGroup group = (PMprpGroup) groups.get(i);
			if (group == null)
				return -5;
			List items = group.gainChildren();
			if (items == null || items.size() < 1)
				return -10;
			DT = group.getDT(); // ���ø����������Դ���� P S(DataSet) B(BEO)
			BEO = group.getBEO();
			DataSet = group.getDataSet();
			instance=group.getInstance();
			if (DT == 'S') {
				if(RSP!=null){		//�õ�Esb�е�dataSet  //�ο� 20100313 ��
					if(DataSet==null||"".equals(DataSet)){//���DataSetֵΪ��,���Ĭ�ϵ�ESB_Dataset��ȡֵ,�����ָ����DataSet��ȡֵ
						EsbDataSet=RSP.getDataSet();
					}else{
						EsbDataSet=RSP.getDataSet(DataSet);
					}
					
					//////////////////////��������ʱ��ӡ,����ʹ��//////////////////////////////////////////////////////////////
					print("��ESBDataSet:"+(DataSet==null?"ESB_Dataset":DataSet)+"��ȡֵ!");
					printESBDataSet(EsbDataSet);//����ʱʹ��.��EsbDataSet�е�key��ֵ��ӡ����
					
					//////////////////////////////////////////////////////////////////////////////////
				}
			}
			if (DT == 'B') {
				// BeoIns = ������ //��ȥBEOʵ��
				//�����BEO��ȡֵ,��DataSet��ʾBEO������
				//����û��ʹ��BEO,��ΪBEO���ܽ�������ֵ,ֻ�ܴ���������ѡ����.�������ʵ��Ϊjava.util.ArrayListʱ�޷�����
				//������ʱʹ��DataSet��������ʾBEO������,�Ժ�����.
				BeoIns=UBViewDesCG.ObjFct.getAObj(DataSet,instance);
				if (BeoIns instanceof List) {
					ListBeo = (List) BeoIns;
				} else {
					ListBeo = null;
				}
				
			}
			if (group.getMaxL() == 1) { // ����������=1�������д������򰴶��д���
				Cbeo = BeoIns;
				setSingleLine(items,group);
			} else {
				setMultiLine(items,group);//����ȡֵ
			}
		}
		return 0;
	}

	/**
	 * �����������ڲ��ı��ʽ�����ʽ�βΣ�
	 * 
	 * @param PD
	 * @throws Exception
	 */
	private int parsePD(PMPrintDesRoot PD) throws Exception {

		List groups = PD.gainChildren();
		if (groups == null || groups.size() < 1)
			return -3; // ��ӡʵ�������������κ���
		// Ϊ���ʽ����������ȡֵ�� -- ��Դ�ӡ�������ڲ����ʽ��
		// �Ը���������д���
		for (int i = 0; i < groups.size(); i++) {
			if(groups.get(i) instanceof PMPrintImageDes)
				continue;
			PMPrintItemsDes group = (PMPrintItemsDes) groups.get(i);
			if (group == null)
				return -5;
			String tp = group.getTp(); // �õ����������
			if (tp.endsWith("A") || tp.endsWith("C"))
				continue; // ���������ۼƵ����ݲ�����
			parseOneGroup(group);
		}
		return 0;
	}

	/**
	 * ����ӡ��������Ҫ�������ۼ�����ڵı��ʽֵʱ�����Զ����ص��ô˷����� ע���ڵ���ǰ���뽫���ۼ�ֵ�ȼ��������
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public int parseOneGroup(PMPrintItemsDes group) throws Exception {
		AFutParseExpression.setGetValHandler(valGeterForPD);
		List items = group.gainChildren();
		if (items == null || items.size() < 1)
			return -10;
		PIVs = group.getPrtVal();
		if (group.getMaxRows() == 1) { // ����������=1�������д������򰴶��д���
			PIV = PIVs.get(0);
			setSingleLinePD(items);
		} else {
			setMultiLinePD(items);
		}
		return 0;
	}

	
	
	/**
	 * Ϊ���в����鸶ֵ
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setSingleLine(List items,PMprpGroup group) throws Exception {
		if(EsbDataSet!=null) EsbDataSet.next();//����ʱҲ���ܴ�DataSet��ȡֵ,��ʱ��ȡ��һ������.����nextһ�ξ�����.  �ο��� 20100315
		
		String[] vals=new String[items.size()];
		
		for (int i = 0; i < items.size(); i++) {
			PMprpItem item = (PMprpItem) items.get(i);
			if(!item.isUsing()){
				continue;
			}
			String rpe = item.getRPE(); // �õ������ʵ�α��ʽ
			String val = prsOnePRE(rpe);
			vals[i]=val;
			PD.putVal(((PMprpGroup) item.gainParent()).getCode(), item.getCode(), val); // ���ô�ӡ���������setVal������Ϊ֮��ֵ
		}
		
//		String key=group.getSC()+"_SingleLine";
		String key=group.getSC();
		parseData.put(key, vals);
		
		
		String[] header=new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			PMprpItem item = (PMprpItem) items.get(i);
			String head = item.getText(); // �õ��������ʾ����
			header[i]=head;
		}
		
		parseData.put(key+"_lable", header);
		
	}

	/**
	 * Ϊ���в����鸳ֵ
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setMultiLine(List items,PMprpGroup group) throws Exception {
		int N = 0; // ��ǰ���к�
//		if (next(N)) { // �����ǰ������������ݣ���������´���
		
		ArrayList<String[]> valList=new ArrayList<String[]>();
		
		while (next(N)) { // �����ǰ������������ݣ���������´���  �ο��� ��if��Ϊwhile,�ſɽ���ѭ�� 20100315
			String[] aLine=new String[items.size()];
			for (int i = 0; i < items.size(); i++) {
				PMprpItem item = (PMprpItem) items.get(i);
				String rpe = item.getRPE(); // �õ������ʵ�α��ʽ
				String val = prsOnePRE(rpe);
				
				aLine[i]=val;
			}
			N++;
			valList.add(aLine);
		}
		
//		String key=group.getSC()+"_MultiLine";
		String key=group.getSC();
		parseData.put(key, valList);
		
		String[] header=new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			PMprpItem item = (PMprpItem) items.get(i);
			String head = item.getText(); // �õ��������ʾ����
			header[i]=head;
		}
		
		parseData.put(key+"_lable", header);
	}
	
	private  void setMultiLinePage(List items,int page,PMPrintDesRoot pd) throws Exception {
		AF2int se = getPageSE(page,pd);
		int s = se.getI();
		int e = se.getJ();
		int N = s; // ��ǰ���к�
//		if (next(N)) { // �����ǰ������������ݣ���������´���
		while (next(N)) { // �����ǰ������������ݣ���������´���  �ο��� ��if��Ϊwhile,�ſɽ���ѭ�� 20100315
			for (int i = 0; i < items.size(); i++) {
				PMprpItem item = (PMprpItem) items.get(i);
				String rpe = item.getRPE(); // �õ������ʵ�α��ʽ
				String val = prsOnePRE(rpe);
				pd.putVal(((PMprpGroup) item.gainParent()).getCode(), item.getCode(), N-s, val); // ���ô�ӡ���������setVal������Ϊ֮��ֵ
			}
			N++;
			if (N==e+1) {
				return;
			}
		}
	}

	
	private AF2int getPageSE(int page ,PMPrintDesRoot pd) {
		int s=0,e=0;
		int fl = pd.getFPLine(); //��ҳ��̬����
		if (page == 1) {
			s =0;
			e = fl-1;
		}else
		{
			s = fl + (page-2) * pd.getMaxLine();
			e = s + pd.getMaxLine()-1;
		}
		AF2int se = new AF2int(s,e);
		return se;
	}
	
	/**
	 * ���� ������ֵ���ʽ 1���߼����ʽ?���ʽ1�����ʽ2 2���߼����ʽ?���ʽ1
	 * 
	 * @param rpe
	 * @return
	 * @throws Exception
	 */
	public String prsOnePRE(String rpe){
		if (rpe == null)
			return null;
		
		String returnVal="";
		try{
			rpe = rpe.trim();
			rpe = AFUtility.getBracketsInside(rpe, '(', ')'); // ��Ƥ
			returnVal=parse(rpe);
		}catch (Exception e) {
			System.out.println("�������ʽ"+rpe+"����!"+"\n"+e.getMessage());
//			e.printStackTrace();
			return rpe;
		}
		return returnVal;



	}

	/**
	 * ��PD�ڲ����ʽ���д��� Ϊ���в����鸶ֵ
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setSingleLinePD(List items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			PMPrintItemDes item = (PMPrintItemDes) items.get(i);
			if(item.getExpression()==null||item.getExpression().equals("")){
				continue;
			}
			String exp = item.getExpression().trim();
			if (exp == null || exp.equals(""))
				continue; // ���Ǳ��ʽ�Ĳ�����
			String val = prsOnePRE(exp);
			 PD.putVal(((PMPrintItemsDes)item.gainParent()).getCode(),item.getCode(),val);
			// //���ô�ӡ���������setVal������Ϊ֮��ֵ
		}
	}

	/**
	 * ��PD�ڲ����ʽ���д��� Ϊ���в����鸳ֵ
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setMultiLinePD(List items) throws Exception {
		int N = 0; // ��ǰ���к�
//		if (nextPD(N)) { // �����ǰ������������ݣ���������´���
		while (nextPD(N)) { // �����ǰ������������ݣ���������´��� �ο��� ��if��Ϊwhile,�ſɽ���ѭ�� 20100315
			for (int i = 0; i < items.size(); i++) {
				PMPrintItemDes item = (PMPrintItemDes) items.get(i);
				if(item.getExpression()==null||item.getExpression().equals("")){
					continue; // ���Ǳ��ʽ�Ĳ�����
				}
				String exp = item.getExpression().trim();
				if (exp == null || exp.equals(""))
					continue; // ���Ǳ��ʽ�Ĳ�����
				String val = prsOnePRE(exp);
				
				
				
				PD.putVal(((PMPrintItemsDes)item.gainParent()).getCode(),item.getCode(),N,val);
				
			}
			N++;
		}
	}

	/**
	 * �������������ʽ��ֵ�������ַ�����ʽ
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
	private String parse(String exp) throws Exception {
		// ����ʵ�ʵı��ʽ����������ע������ص� getValue����������ȡ�������ֵ
		AFutPEResule r = AFutParseExpression.parse(exp, '*');
		if (r.type == 'S') {
			return r.str;
		} else {
			return String.valueOf(r.r);
		}
	}
	/**
	 * ���ص�ǰ�������Ӧ��������Դ�Ƿ������һ������
	 * 
	 * @return
	 */
	private boolean next(int n) {
		switch (DT) {
		case 'P':
			return false; // ������֧��next()
		case 'M':
			return false; // ������֧��next()
		case 'S': // DataSet ��������ӦDataSet��next()����  
			if(EsbDataSet==null)return false;				//�ο� 20100313 ��
			print("��һ������------------->");
			return EsbDataSet.next();
		case 'B': // ����BEO
			if (ListBeo == null) {
				return false;
			}
			if (n < ListBeo.size()) {
				Cbeo = ListBeo.get(n);
				print("��һ������------------->");
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * �ж�PD�ڲ�ĳ���Ƿ������һ������
	 * 
	 * @return
	 */
	private boolean nextPD(int n) {
		if(PIVs==null){
			return false;
		}
		if (n >= PIVs.size()) {
			return false;
		} else {
			PIV = PIVs.get(n); // �ƶ���ǰֵ����
			return true;
		}
	}

	/**
	 * ʵ�ν�����ȡֵ�ӿڣ����������ʽ����������
	 * 
	 * ʹ�ñ��ʽ�������Ļ�ȡ�����ӿ�������AFutPEgetValueAdapter��ͨ�����踲��getValue()�������ɡ�
	 */
	private class APValGet extends AFutPEgetValueAdapter {
		public AFPEValObj getValue(String e) throws Exception {
			// TODO Auto-generated method stub
			//��Ҫ����������ַ����ж�
			//�����DT$_��ͷ,��ʾҪ�������ڸ�ʽת��
			//�����SC@scid$_��ͷ,��ʾҪ����@scid��ʶ�ı�׼�������ת��
			//�����CD@scid$_��ͷ,��ʾҪ����@scid��ʶ�ı�׼�������ת��
			AFPEValObj returnObj=new AFPEValObj();
			String prefix="$_";
			String val="";
			String returnVal="";
			String format="";										//ת����ʽ��ǩ.���Ϊ"DT",��ʾҪ�������ڸ�ʽת��,�����"SC",��ʾҪ���б�׼����ת��
			val=e;
			if(val==null){
				return null;
			}
			
			if(val.startsWith("DT$_")){
				val=val.substring(val.indexOf(prefix)+2);			//ȡ��ʵ�ʵ�keyֵ
				format="DT";										//ת�����ڸ�ʽ
			}else if(val.startsWith("SC@")){
				val=val.substring(val.indexOf(prefix)+2);			//��׼�����ʽת��
				format="SC";										//ͨ��scȡ��text
			}else if(val.startsWith("CD@")){
				val=val.substring(val.indexOf(prefix)+2);			//��׼�����ʽת��
				format="CD";										//ͨ��codeȡ��text
			}else if(val.startsWith("NU$_")){
				val=val.substring(val.indexOf(prefix)+2);			//ȡ��ʵ�ʵ�keyֵ
				format="NU";	
			}
			
			//������ȡֵ�ĺ��Ĳ���
			switch (DT) {
			case 'P':
				returnVal= (String)RSP.getParameter(val);
				print("��ӡʵ��ȡֵ----��ESB��ȡֵ:"+val+"=="+returnVal);
				break;
			case 'S': // DataSet ��������ӦDataSet��get()����
				try{
				returnVal= EsbDataSet.getString(val);
				print("��ӡʵ��ȡֵ----��ESB_DataSet��ȡֵ:"+val+"=="+returnVal);
				}catch (Exception e1) {
//					returnVal="Key "+val+"������!";
					returnVal=null;
					System.out.println("��ӡʵ��ȡֵ----��ESB_DataSet��ȡֵ:"+val+"=="+"Key "+val+"������!");
				}
//				returnVal= EsbDataSet.getString(val);
				break;
			case 'B': // ����BEO
				if(Cbeo==null){
					returnVal="BEOΪ��!";
				}else{
					returnVal= Utl.getFieldStrVal(Cbeo, val);
					print("��ӡʵ��ȡֵ----��BEO��ȡֵ:"+val+"=="+returnVal);
				}
				break;
			case 'M': // ����Map
				if(RMP==null){
					returnVal="RMPΪ��!";
				}else{
					returnVal=(String) RMP.get(val);
					print("��ӡʵ��ȡֵ----��Map��ȡֵ:"+val+"=="+returnVal);
				}
				break;
			}
			if(returnVal==null){
				returnObj.setVal("");
			}else{
				returnObj.setVal(returnVal.trim());
			} 
			//�������Ҫ���и�ʽת����ֵ������Ӧ��ת��
			//Ŀǰֻת�����ָ�ʽ.1,������ת������Ҫ�ĸ�ʽ.2,����׼����ת������ʾ��Text
			if(format.equals("DT")){
				returnVal=AFutDate.getStrDataByForm(returnVal,"yyyy-MM-dd hh:mm:ss");
				returnObj.setVal(returnVal);
				return returnObj;
				
			}else if(format.equals("SC")){
				String scVal="";
				String scGUID="";
				//ȡ��SC��GUID. �����ĸ�ʽΪ SC@scid$_keyxx ,���潫scid���ֽ�������
				scGUID=e.substring(0, e.indexOf(prefix)); 
				scGUID=scGUID.substring(scGUID.indexOf("SC@")+3);
				
				returnVal=D2C.gainTextBySC(scGUID, returnVal);			//ͨ��sc�õ�Ҫ��ʾ��ֵ
				returnObj.setVal(returnVal);
				return returnObj;
			}else if(format.equals("CD")){
				String scVal="";
				String scGUID="";
				//ȡ��SC��GUID. �����ĸ�ʽΪ CD@scid$_keyxx ,���潫scid���ֽ�������
				scGUID=e.substring(0, e.indexOf(prefix)); 
				scGUID=scGUID.substring(scGUID.indexOf("CD@")+3);
				
				returnVal=D2C.gainTextByCode(scGUID, returnVal);		//ͨ��code�õ�Ҫ��ʾ��ֵ
				returnObj.setVal(returnVal);
				return returnObj;
			}else if(format.equals("NU")){								//��ֵ����ֵ��,��Ҫת��
				returnObj.setVal(returnVal);
				returnObj.setType('N');
				return returnObj;
			}
			
			return returnObj;											//����Ҫ����ת����ֵ,ֱ�ӷ���
		}

		
	}

	/**
	 * ��ӡ������PD���ڲ����ʽ������ȡֵ�ӿڣ����������ʽ���������ã� 
	 * ��Ҫ��ӡ�����������
	 */
	private class APValGetForPD extends AFutPEgetValueAdapter {

		public AFPEValObj getValue(String e) throws Exception {
			AFPEValObj 	returnVal = PIV.getVal(e);
			return returnVal;
		}
	}
	public PMPrintDesRoot gainPMPrintDesRoot(PMprpRoot prp){
		try {
			if(realRarmParse(prp)==0){
				return PD;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public HashMap gainParsedData(PMprpRoot prp){
		try {
			if(realRarmParse(prp)==0){
				return parseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private void syn(PMPrintDesRoot pd,PMprpRoot prp){

		 
		//ѭ������ÿһ��ģ��ı����飨Ŀ�ģ�ʵ�μ��ϱ����
		for(int i=0;i<pd.gainChildren().size();i++){        
			if(pd.gainChildren().get(i) instanceof PMPrintItemsDes){
			PMPrintItemsDes items = (PMPrintItemsDes)pd.gainChildren().get(i);
			if(items.getTp().endsWith("sd#SC2692,B")){//ֻ�б�����Ž���ͬ���ж�
				  boolean existG = false;//�����Ƿ����
				  PMprpGroup group = null;
				   for(int x=0;x<prp.gainChildren().size();x++){//��ʵ������б���
					    group = (PMprpGroup)prp.gainChildren().get(x);
					   if(items.getSC().equals(group.getSC())){//���ڶ�Ӧ��ʵ�Σ���˲̬�ı�������ֵ
						  existG = true;
						  group.setOAnn(items.getAnn());
						  group.setOText(items.getText());
						  group.setSNo(items.getSNo());
						  group.setLoP(items.getRPP());
						  group.setMaxL(items.getMaxRows());
						  group.setCode(items.getCode());//����ͬ��
						  break;
						  
					  }
				  }
				   if(existG){//�������,��һ��������½ڵ��Ƿ񶼴���
					   for(int j=0;j<items.gainChildren().size();j++){//ѭ������ÿһ���ڵ�
							PMPrintItemDes pditem =(PMPrintItemDes)items.gainChildren().get(j);
							boolean existI = false;//�ýڵ��Ƿ����
						    if(pditem.getExpression()==null||pditem.getExpression().equals("")){
						    	for(int y=0;y<group.gainChildren().size();y++){
						    		PMprpItem prpitem =(PMprpItem)group.gainChildren().get(y);
						    		if(pditem.getSC().equals(prpitem.getSC())){
                                     existI= true;
                                     prpitem.setOAnn(pditem.getAnn());
                                     prpitem.setOText(pditem.getText());
                                     prpitem.setSNo(pditem.getSNo());
                                     prpitem.setCode(pditem.getCode());
                                     prpitem.setUsing(true);
                                     break;
						    		}
						    	}
						    	if(!existI){//������ʱ
						    		PMprpItem  currentItem  = new PMprpItem();
						    		currentItem.setSC(pditem.getSC());
						    		currentItem.setCode(pditem.getCode());
						    		currentItem.setSNo(pditem.getSNo());
						    		currentItem.setOText(pditem.getText());
						    		currentItem.setText(pditem.getText());
						    		currentItem.setOAnn(pditem.getAnn());
						    		currentItem.setAnn(pditem.getAnn());
						    		currentItem.setParent(group);
						    	}
						    }
					   }
					   
				   }else{//���鲻����
    				   PMprpGroup  currentGroup  = new PMprpGroup();
    				   currentGroup.setSC(items.getSC());
    				   currentGroup.setCode(items.getCode());
    				   currentGroup.setSNo(items.getSNo());
    				   currentGroup.setOAnn(items.getAnn());
    				   currentGroup.setAnn(items.getAnn());
    				   currentGroup.setLoP(items.getRPP());
    				   currentGroup.setMaxL(items.getMaxRows());
    				   currentGroup.setOText(items.getText());
    				   currentGroup.setText(items.getText());
    				   currentGroup.setParent(prp);
    				   for(int j=0;j<items.gainChildren().size();j++){
    					   PMPrintItemDes item =(PMPrintItemDes)items.gainChildren().get(j);  
    					   if(item.getExpression()==null||item.getExpression().equals("")){
    						   PMprpItem  currentItem  = new PMprpItem();
    						   currentItem.setSC(item.getSC());
    						   currentItem.setCode(item.getCode());
    						   currentItem.setSNo(item.getSNo());
    						   currentItem.setOText(item.getText());
    						   currentItem.setText(item.getText());
    						   currentItem.setOAnn(item.getAnn());
    						   currentItem.setAnn(item.getAnn());
    						   currentItem.setParent(currentGroup);
    					   }
    				   }
				   }
			   }
		    }
		}
		
		//����ÿһ����ӡʵ�Σ�Ŀ�ģ�����ʵ�ε�ĳЩ����Ϊ�����ã�������
		for(int i=0;i<prp.gainChildren().size();i++){
			PMprpGroup group = (PMprpGroup)prp.gainChildren().get(i);
			boolean existG = false;
			for(int x=0;x<pd.gainChildren().size();x++){
				if(pd.gainChildren().get(x) instanceof PMPrintItemsDes){
				PMPrintItemsDes items =(PMPrintItemsDes)pd.gainChildren().get(x);			
				if(group.getSC().equals(items.getSC())&&items.getTp().equals("sd#SC2692,B")){
					existG =true;
					for(int y=0;y<group.gainChildren().size();y++){//�����ڽڵ���б���
						PMprpItem  prpitem= (PMprpItem)group.gainChildren().get(y);
						boolean existI = false;
						for(int j=0;j<items.gainChildren().size();j++){
							PMPrintItemDes pditem =(PMPrintItemDes)items.gainChildren().get(j);
							if(prpitem.getSC().equals(pditem.getSC())&&(pditem.getExpression()==null||pditem.equals(""))){
                             existI = true;				
                             prpitem.setUsing(true);
                             break;
							}
						}
						if(!existI){//������ʱ
							prpitem.setUsing(false);
						}
					}
				}
			  }
			}		
			if(!existG){//PD�����ڸ���
                  for(int m=0;m<group.gainChildren().size();m++){
                	  ((PMprpItem)group.gainChildren().get(m)).setUsing(false);
                  }
			}
			
		}
	
	}
	//�÷����ɽ�ESBDataSet�е�key��ֵ��ӡ����,���ڲ���ʱʹ��
	public  void printESBDataSet(ESBDataSet ds) throws Exception{
		if(prtFlag==0){
			return;
		}
		if(ds!=null){
			System.out.println("ESBDataSet��¼����:"+ds.getRowCount());
			String[] col = ds.getColumn();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>ESBDataSet�е�����");
			while(ds.next()){
				StringBuilder row = new StringBuilder();
				for (int i = 0; i < col.length; i++) {
					String colName = col[i];
					String value = ds.getString(colName);
					if(value!=null)
						value = value.trim();
					row.append("["+i+"]"+colName+"="+value+" ");
				}
				System.out.println(row);
			}
			ds.setCurrentRow(-1);	//��ӡ���ݺ�,��Ҫ����¼���ĵ�ǰ�лص���ʼλ��,�����¼���ᶨλ�����һ����¼,ȡֵ�����
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<");
		}else{
			System.out.println("δ��ȡ�������ESBDataSet!");
		}
	}
	//�÷����ɽ�ESB�е�key��ֵ��ӡ����,���ڲ���ʱʹ��
	public  void printESB(ESBResponse esb) throws Exception{
		if(prtFlag==0){
			return;
		}
		if(esb!=null){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>ESB�е�����");
			System.out.println(esb.getParameterMap());
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<");
		}else{
			System.out.println("δ��ȡ�������ESB!");
		}
	}
	
	public void print(String prtString){
		if(prtFlag==0){
			return;
		}
		System.out.println(prtString);
	}
}
