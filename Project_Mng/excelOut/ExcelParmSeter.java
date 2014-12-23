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
 * 打印实参解析器
 * 
 * 实参支持表达式和 条件赋值表达式 如： x01>2 || p0==null ? substring(p02,0,I01):p99 支持如下常数： 1234
 * 123.01 0x1f 16进制数 null 支持如下函数：
 *   1）substring(String inStr,int s,int e)， 得到某字符串inStr从 s 到 e 的子串， s 从0开始；
 *   2）substring(String inStr,int s)         得到某字符串inStr从 s位置开始一直到结尾的子串；
 *   3）lengthof(String)                      得到某字符串的长度
 *   
 *   例如： substring(S01,lengthof(S02))
 *   支持字符串加法 S08+substring(S01,2,11)
 * 
 * 方法说明： 使用realRarmParse（）方法，对一个打印实参描述PRD对象进行解析处理，该方法将根据PRD中实参表达式计算出具体的值，
 * 并将值赋到该PRP对应的打印描述PD中；
 * 该方法首先根据实参描述对各组实参进行解析并未形参赋值，处理完成后，调用parsePD（）对形参内定义的表达式形参进行计算， 获取具体的值；
 * 
 * 注意，PD中的列累计组没有处理，列累计计算在打印过程中由打印处理程序完成，
 * 累计列中的表达式需要调用parseOneGroup（group）方法中进行计算，但必须在该组各累计列计算完成后调用。
 * 
 * 
 * @author 任凯 2012/08/01
 * 
 */
public class ExcelParmSeter implements PMExpParserI {
	private char DT;					//数据来源类型 P S(DataSet) B(BEO) M(SvcBus 中的map)
	private AFComboObj BEO;
	private String DataSet;
	private PMPrintDesRoot PD;
	private IABParmFactory Factory = ABParmFactory.Factory; // 通用参数工厂,用于为使用代码的字段提供具体值
	private Object BeoIns; // BEO实例
	private String instance;//BEO实例名
	private List ListBeo = null; // 如果BeoIns是List则将BeoIns转换为 ListBeo
	private Object Cbeo; // 当BEO为List时，当前行的beo对象
	private UBUtility Utl = new UBUtility();
	private List<PMPrintItemsValue> PIVs; // 某组的打印值列表
	private PMPrintItemsValue PIV; // 某组打印值列表的当前值对象
	private ESBResponse RSP=(ESBResponse) UBViewDesCG.ObjFct.getAObj(
			"com.cdc.esb.api.ESBResponseImpl", "ESBReponseInFct");//ESB返回值	//任凯 20100313 加
	private ESBDataSet EsbDataSet;											//任凯 20100313 加
	
	private HashMap RMP=(HashMap)UBViewDesCG.ObjFct.getAObj("java.util.HashMap", "SvcMap");//任凯 20100810 加,为了处理UAP的打印时间等数据
	private int prtFlag=0;					//打印测试数据开关 0:不打印 1:打印
//	private int prtFlag=1;					//打印测试数据开关 0:不打印 1:打印
	
	private HashMap parseData=new HashMap();//保存根据实参描述解析出的值,返回给调用者
	AFutPEgetValueI valGeter = null;
	
	AFutPEgetValueI valGeterForPD = new APValGetForPD();// 通过表达式获取值
	List groups=null;

	public ExcelParmSeter() {

	}

	public int realRarmParse(PMprpRoot prp) throws Exception {
		if (prp == null)
			return -1; // 打印实参描述为空
		if (!prp.hasChildren())
			return -2; // 打印实参描述不包含任何组
		groups = prp.gainChildren();
		if (groups == null || groups.size() < 1)
			return -3; // 打印实参描述不包含任何组
		
		try { // 拿到该实参描述对应的打印描述
			PMPrintDesRoot pd = (PMPrintDesRoot) Factory.getItemByGUID(prp.getPD().getKey());
			PD = AFUtility.cloneObjByXstream(pd);
			PD.setExpParser(this); // 为打印描述设置解析器
		} catch (Exception e) {
			e.printStackTrace();
		}

		syn(PD,prp);//实参，形参进行同步  刘铮加
		
		// 为表达式解析器设置取值器
		valGeter = new APValGet();
		
		/////////////////////////////////////////////////////////////
		printESB(RSP);				//测试时使用.将EsbD中的key和值打印出来
		////////////////////////////////////////////////////////////
		return realRarmParsePage(1);
	}
	
	public int realRarmParsePage(int page) throws Exception {
		PD.clear();//把动态组包含的值给清除
		AFutParseExpression.setGetValHandler(valGeter);
		for (int i = 0; i < groups.size(); i++) {
			PMprpGroup group = (PMprpGroup) groups.get(i);
			if (group == null)
				return -5;
			List items = group.gainChildren();
			if (items == null || items.size() < 1)
				return -10;
			DT = group.getDT(); // 设置该组的数据来源类型 P S(DataSet) B(BEO)
			BEO = group.getBEO();
			DataSet = group.getDataSet();
			instance=group.getInstance();
			if (DT == 'S') {
				if(RSP!=null){		//得到Esb中的dataSet  //任凯 20100313 加
					if(DataSet==null||"".equals(DataSet)){//如果DataSet值为空,则从默认的ESB_Dataset中取值,否则从指定的DataSet中取值
						EsbDataSet=RSP.getDataSet();
					}else{
						EsbDataSet=RSP.getDataSet(DataSet);
					}
					
					//////////////////////以下是临时打印,测试使用//////////////////////////////////////////////////////////////
					print("从ESBDataSet:"+(DataSet==null?"ESB_Dataset":DataSet)+"中取值!");
					printESBDataSet(EsbDataSet);//测试时使用.将EsbDataSet中的key和值打印出来
					
					//////////////////////////////////////////////////////////////////////////////////
				}
			}
			if (DT == 'B') {
				// BeoIns = 。。。 //或去BEO实例
				//如果从BEO中取值,则DataSet表示BEO的类名
				//这里没有使用BEO,因为BEO不能接受输入值,只能从类描述中选择类.这样如果实例为java.util.ArrayList时无法处理
				//所以暂时使用DataSet数据来表示BEO的类名,以后完善.
				BeoIns=UBViewDesCG.ObjFct.getAObj(DataSet,instance);
				if (BeoIns instanceof List) {
					ListBeo = (List) BeoIns;
				} else {
					ListBeo = null;
				}
				
			}
			if (group.getMaxL() == 1) { // 如果最大行数=1，按单行处理，否则按多行处理
				Cbeo = BeoIns;
				setSingleLine(items,group);
			} else {
				setMultiLine(items,group);//多行取值
			}
		}
		return 0;
	}

	/**
	 * 解析表描述内部的表达式（表达式形参）
	 * 
	 * @param PD
	 * @throws Exception
	 */
	private int parsePD(PMPrintDesRoot PD) throws Exception {

		List groups = PD.gainChildren();
		if (groups == null || groups.size() < 1)
			return -3; // 打印实参描述不包含任何组
		// 为表达式解析器设置取值器 -- 针对打印描述的内部表达式，
		// 对各参数组进行处理
		for (int i = 0; i < groups.size(); i++) {
			if(groups.get(i) instanceof PMPrintImageDes)
				continue;
			PMPrintItemsDes group = (PMPrintItemsDes) groups.get(i);
			if (group == null)
				return -5;
			String tp = group.getTp(); // 拿到该组的类型
			if (tp.endsWith("A") || tp.endsWith("C"))
				continue; // 常数或列累计的组暂不处理
			parseOneGroup(group);
		}
		return 0;
	}

	/**
	 * 当打印过程中需要计算列累计组的内的表达式值时，可以独立地调用此方法， 注意在调用前必须将列累计值先计算出来。
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
		if (group.getMaxRows() == 1) { // 如果最大行数=1，按单行处理，否则按多行处理
			PIV = PIVs.get(0);
			setSingleLinePD(items);
		} else {
			setMultiLinePD(items);
		}
		return 0;
	}

	
	
	/**
	 * 为单行参数组付值
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setSingleLine(List items,PMprpGroup group) throws Exception {
		if(EsbDataSet!=null) EsbDataSet.next();//单条时也可能从DataSet中取值,这时仅取第一条数据.所以next一次就行了.  任凯加 20100315
		
		String[] vals=new String[items.size()];
		
		for (int i = 0; i < items.size(); i++) {
			PMprpItem item = (PMprpItem) items.get(i);
			if(!item.isUsing()){
				continue;
			}
			String rpe = item.getRPE(); // 拿到该项的实参表达式
			String val = prsOnePRE(rpe);
			vals[i]=val;
			PD.putVal(((PMprpGroup) item.gainParent()).getCode(), item.getCode(), val); // 调用打印描述对象的setVal方法，为之赋值
		}
		
//		String key=group.getSC()+"_SingleLine";
		String key=group.getSC();
		parseData.put(key, vals);
		
		
		String[] header=new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			PMprpItem item = (PMprpItem) items.get(i);
			String head = item.getText(); // 拿到该项的显示名称
			header[i]=head;
		}
		
		parseData.put(key+"_lable", header);
		
	}

	/**
	 * 为多行参数组赋值
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setMultiLine(List items,PMprpGroup group) throws Exception {
		int N = 0; // 当前的行号
//		if (next(N)) { // 如果当前组包含后续数据，则进行如下处理
		
		ArrayList<String[]> valList=new ArrayList<String[]>();
		
		while (next(N)) { // 如果当前组包含后续数据，则进行如下处理  任凯改 将if改为while,才可进行循环 20100315
			String[] aLine=new String[items.size()];
			for (int i = 0; i < items.size(); i++) {
				PMprpItem item = (PMprpItem) items.get(i);
				String rpe = item.getRPE(); // 拿到该项的实参表达式
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
			String head = item.getText(); // 拿到该项的显示名称
			header[i]=head;
		}
		
		parseData.put(key+"_lable", header);
	}
	
	private  void setMultiLinePage(List items,int page,PMPrintDesRoot pd) throws Exception {
		AF2int se = getPageSE(page,pd);
		int s = se.getI();
		int e = se.getJ();
		int N = s; // 当前的行号
//		if (next(N)) { // 如果当前组包含后续数据，则进行如下处理
		while (next(N)) { // 如果当前组包含后续数据，则进行如下处理  任凯改 将if改为while,才可进行循环 20100315
			for (int i = 0; i < items.size(); i++) {
				PMprpItem item = (PMprpItem) items.get(i);
				String rpe = item.getRPE(); // 拿到该项的实参表达式
				String val = prsOnePRE(rpe);
				pd.putVal(((PMprpGroup) item.gainParent()).getCode(), item.getCode(), N-s, val); // 调用打印描述对象的setVal方法，为之赋值
			}
			N++;
			if (N==e+1) {
				return;
			}
		}
	}

	
	private AF2int getPageSE(int page ,PMPrintDesRoot pd) {
		int s=0,e=0;
		int fl = pd.getFPLine(); //首页动态行数
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
	 * 解析 条件赋值表达式 1）逻辑表达式?表达式1：表达式2 2）逻辑表达式?表达式1
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
			rpe = AFUtility.getBracketsInside(rpe, '(', ')'); // 扒皮
			returnVal=parse(rpe);
		}catch (Exception e) {
			System.out.println("解析表达式"+rpe+"出错!"+"\n"+e.getMessage());
//			e.printStackTrace();
			return rpe;
		}
		return returnVal;



	}

	/**
	 * 对PD内部表达式进行处理 为单行参数组付值
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
				continue; // 不是表达式的不处理
			String val = prsOnePRE(exp);
			 PD.putVal(((PMPrintItemsDes)item.gainParent()).getCode(),item.getCode(),val);
			// //调用打印描述对象的setVal方法，为之赋值
		}
	}

	/**
	 * 对PD内部表达式进行处理 为多行参数组赋值
	 * 
	 * @param group
	 * @throws Exception
	 */
	private void setMultiLinePD(List items) throws Exception {
		int N = 0; // 当前的行号
//		if (nextPD(N)) { // 如果当前组包含后续数据，则进行如下处理
		while (nextPD(N)) { // 如果当前组包含后续数据，则进行如下处理 任凯改 将if改为while,才可进行循环 20100315
			for (int i = 0; i < items.size(); i++) {
				PMPrintItemDes item = (PMPrintItemDes) items.get(i);
				if(item.getExpression()==null||item.getExpression().equals("")){
					continue; // 不是表达式的不处理
				}
				String exp = item.getExpression().trim();
				if (exp == null || exp.equals(""))
					continue; // 不是表达式的不处理
				String val = prsOnePRE(exp);
				
				
				
				PD.putVal(((PMPrintItemsDes)item.gainParent()).getCode(),item.getCode(),N,val);
				
			}
			N++;
		}
	}

	/**
	 * 解析并返还表达式的值，均以字符串形式
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
	private String parse(String exp) throws Exception {
		// 调用实际的表达式解析方法，注意它会回调 getValue（）方法获取具体项的值
		AFutPEResule r = AFutParseExpression.parse(exp, '*');
		if (r.type == 'S') {
			return r.str;
		} else {
			return String.valueOf(r.r);
		}
	}
	/**
	 * 返回当前参数组对应的数据来源是否包含下一行数据
	 * 
	 * @return
	 */
	private boolean next(int n) {
		switch (DT) {
		case 'P':
			return false; // 参数不支持next()
		case 'M':
			return false; // 参数不支持next()
		case 'S': // DataSet ，调用相应DataSet的next()方法  
			if(EsbDataSet==null)return false;				//任凯 20100313 加
			print("下一条数据------------->");
			return EsbDataSet.next();
		case 'B': // 对于BEO
			if (ListBeo == null) {
				return false;
			}
			if (n < ListBeo.size()) {
				Cbeo = ListBeo.get(n);
				print("下一条数据------------->");
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 判断PD内部某组是否包含下一行数据
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
			PIV = PIVs.get(n); // 移动当前值对象
			return true;
		}
	}

	/**
	 * 实参解析的取值接口，它将被表达式解析器调用
	 * 
	 * 使用表达式解析器的获取参数接口适配器AFutPEgetValueAdapter后，通常仅需覆盖getValue()方法即可。
	 */
	private class APValGet extends AFutPEgetValueAdapter {
		public AFPEValObj getValue(String e) throws Exception {
			// TODO Auto-generated method stub
			//需要加入对特殊字符的判断
			//如果以DT$_开头,表示要进行日期格式转换
			//如果以SC@scid$_开头,表示要根据@scid标识的标准代码进行转换
			//如果以CD@scid$_开头,表示要根据@scid标识的标准代码进行转换
			AFPEValObj returnObj=new AFPEValObj();
			String prefix="$_";
			String val="";
			String returnVal="";
			String format="";										//转换格式标签.如果为"DT",表示要进行日期格式转换,如果是"SC",表示要进行标准代码转换
			val=e;
			if(val==null){
				return null;
			}
			
			if(val.startsWith("DT$_")){
				val=val.substring(val.indexOf(prefix)+2);			//取得实际的key值
				format="DT";										//转换日期格式
			}else if(val.startsWith("SC@")){
				val=val.substring(val.indexOf(prefix)+2);			//标准代码格式转换
				format="SC";										//通过sc取得text
			}else if(val.startsWith("CD@")){
				val=val.substring(val.indexOf(prefix)+2);			//标准代码格式转换
				format="CD";										//通过code取得text
			}else if(val.startsWith("NU$_")){
				val=val.substring(val.indexOf(prefix)+2);			//取得实际的key值
				format="NU";	
			}
			
			//以下是取值的核心步骤
			switch (DT) {
			case 'P':
				returnVal= (String)RSP.getParameter(val);
				print("打印实参取值----从ESB中取值:"+val+"=="+returnVal);
				break;
			case 'S': // DataSet ，调用相应DataSet的get()方法
				try{
				returnVal= EsbDataSet.getString(val);
				print("打印实参取值----从ESB_DataSet中取值:"+val+"=="+returnVal);
				}catch (Exception e1) {
//					returnVal="Key "+val+"不存在!";
					returnVal=null;
					System.out.println("打印实参取值----从ESB_DataSet中取值:"+val+"=="+"Key "+val+"不存在!");
				}
//				returnVal= EsbDataSet.getString(val);
				break;
			case 'B': // 对于BEO
				if(Cbeo==null){
					returnVal="BEO为空!";
				}else{
					returnVal= Utl.getFieldStrVal(Cbeo, val);
					print("打印实参取值----从BEO中取值:"+val+"=="+returnVal);
				}
				break;
			case 'M': // 对于Map
				if(RMP==null){
					returnVal="RMP为空!";
				}else{
					returnVal=(String) RMP.get(val);
					print("打印实参取值----从Map中取值:"+val+"=="+returnVal);
				}
				break;
			}
			if(returnVal==null){
				returnObj.setVal("");
			}else{
				returnObj.setVal(returnVal.trim());
			} 
			//下面对需要进行格式转换的值进行相应的转换
			//目前只转换两种格式.1,将日期转换成需要的格式.2,将标准代码转换成显示的Text
			if(format.equals("DT")){
				returnVal=AFutDate.getStrDataByForm(returnVal,"yyyy-MM-dd hh:mm:ss");
				returnObj.setVal(returnVal);
				return returnObj;
				
			}else if(format.equals("SC")){
				String scVal="";
				String scGUID="";
				//取得SC的GUID. 解析的格式为 SC@scid$_keyxx ,下面将scid部分解析出来
				scGUID=e.substring(0, e.indexOf(prefix)); 
				scGUID=scGUID.substring(scGUID.indexOf("SC@")+3);
				
				returnVal=D2C.gainTextBySC(scGUID, returnVal);			//通过sc得到要显示的值
				returnObj.setVal(returnVal);
				return returnObj;
			}else if(format.equals("CD")){
				String scVal="";
				String scGUID="";
				//取得SC的GUID. 解析的格式为 CD@scid$_keyxx ,下面将scid部分解析出来
				scGUID=e.substring(0, e.indexOf(prefix)); 
				scGUID=scGUID.substring(scGUID.indexOf("CD@")+3);
				
				returnVal=D2C.gainTextByCode(scGUID, returnVal);		//通过code得到要显示的值
				returnObj.setVal(returnVal);
				return returnObj;
			}else if(format.equals("NU")){								//该值是数值型,需要转换
				returnObj.setVal(returnVal);
				returnObj.setType('N');
				return returnObj;
			}
			
			return returnObj;											//不需要进行转换的值,直接返回
		}

		
	}

	/**
	 * 打印描述（PD）内部表达式解析的取值接口，它将被表达式解析器调用， 
	 * 需要打印描述对象更具
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

		 
		//循环遍历每一个模板的变量组（目的：实参加上必须项）
		for(int i=0;i<pd.gainChildren().size();i++){        
			if(pd.gainChildren().get(i) instanceof PMPrintItemsDes){
			PMPrintItemsDes items = (PMPrintItemsDes)pd.gainChildren().get(i);
			if(items.getTp().endsWith("sd#SC2692,B")){//只有变量组才进行同步判断
				  boolean existG = false;//该组是否存在
				  PMprpGroup group = null;
				   for(int x=0;x<prp.gainChildren().size();x++){//对实参组进行遍历
					    group = (PMprpGroup)prp.gainChildren().get(x);
					   if(items.getSC().equals(group.getSC())){//存在对应的实参，把瞬态的变量附上值
						  existG = true;
						  group.setOAnn(items.getAnn());
						  group.setOText(items.getText());
						  group.setSNo(items.getSNo());
						  group.setLoP(items.getRPP());
						  group.setMaxL(items.getMaxRows());
						  group.setCode(items.getCode());//用于同步
						  break;
						  
					  }
				  }
				   if(existG){//该组存在,进一步检测组下节点是否都存在
					   for(int j=0;j<items.gainChildren().size();j++){//循环组中每一个节点
							PMPrintItemDes pditem =(PMPrintItemDes)items.gainChildren().get(j);
							boolean existI = false;//该节点是否存在
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
						    	if(!existI){//不存在时
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
					   
				   }else{//该组不存在
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
		
		//遍历每一个打印实参（目的：设置实参的某些项置为不可用）做减法
		for(int i=0;i<prp.gainChildren().size();i++){
			PMprpGroup group = (PMprpGroup)prp.gainChildren().get(i);
			boolean existG = false;
			for(int x=0;x<pd.gainChildren().size();x++){
				if(pd.gainChildren().get(x) instanceof PMPrintItemsDes){
				PMPrintItemsDes items =(PMPrintItemsDes)pd.gainChildren().get(x);			
				if(group.getSC().equals(items.getSC())&&items.getTp().equals("sd#SC2692,B")){
					existG =true;
					for(int y=0;y<group.gainChildren().size();y++){//对组内节点进行遍历
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
						if(!existI){//不存在时
							prpitem.setUsing(false);
						}
					}
				}
			  }
			}		
			if(!existG){//PD不存在该组
                  for(int m=0;m<group.gainChildren().size();m++){
                	  ((PMprpItem)group.gainChildren().get(m)).setUsing(false);
                  }
			}
			
		}
	
	}
	//该方法可将ESBDataSet中的key和值打印出来,便于测试时使用
	public  void printESBDataSet(ESBDataSet ds) throws Exception{
		if(prtFlag==0){
			return;
		}
		if(ds!=null){
			System.out.println("ESBDataSet记录数量:"+ds.getRowCount());
			String[] col = ds.getColumn();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>ESBDataSet中的数据");
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
			ds.setCurrentRow(-1);	//打印数据后,需要将记录集的当前行回到初始位置,否则记录集会定位到最后一条记录,取值会出错
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<");
		}else{
			System.out.println("未能取得所需的ESBDataSet!");
		}
	}
	//该方法可将ESB中的key和值打印出来,便于测试时使用
	public  void printESB(ESBResponse esb) throws Exception{
		if(prtFlag==0){
			return;
		}
		if(esb!=null){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>ESB中的数据");
			System.out.println(esb.getParameterMap());
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<");
		}else{
			System.out.println("未能取得所需的ESB!");
		}
	}
	
	public void print(String prtString){
		if(prtFlag==0){
			return;
		}
		System.out.println(prtString);
	}
}
