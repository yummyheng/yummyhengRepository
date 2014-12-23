package excelOut;

import AF.rf.AFfrObjFactory;
import UB.pub.UBViewDesCG;

public interface ExcelFormatOut_I {

	public AFfrObjFactory objFct = UBViewDesCG.ObjFct;
	
	public void export(String fileName,String sheetName) throws Exception;
	
}
