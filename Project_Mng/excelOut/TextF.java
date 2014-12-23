package excelOut;

/**
 * 定义Excel单元格的各种格式以及数据的转换形式
 * @comments	:
 * @author   	:任凯 2012-8-9
 *
 */
public class TextF {
	/**
	 * 内容左对齐
	 */
	public static final String Left="Left";
	/**
	 * 内容居中
	 */
	public static final String CENTRE="Center";
	/**
	 * 内容右对齐
	 */
	public static final String RIGHT="Right";
	
	
	
	/**
	 * 虚线边框
	 */
	public static final int HAIR=0;
	/**
	 * 细线边框
	 */
	public static final int THIN=1;
	/**
	 * 粗线边框
	 */
	public static final int MEDIUM=2;
		
	
	/**
	 * 数字千分位显示
	 */
	public static final String millSpt="millSpt";
	
	/**
	 * 内容为日期
	 */
	public static final String date="date";
	/**
	 * 日期格式为yyyyMMdd
	 */
	public static final String yyyyMMdd="yyyyMMdd";
	/**
	 * 日期格式为yyyyMMdd  hh:mm:ss
	 */
	public static final String yyyyMMdd_hsm="yyyyMMdd hh:mm:ss";
	/**
	 * 日期格式为yyyy-MM-dd
	 */
	public static final String yyyy_MM_dd="yyyy-MM-dd";
	/**
	 * 日期格式为yyyy-M-Mdd hh:mm:ss
	 */
	public static final String yyyy_MM_dd_hsm="yyyy-MM-dd hh:mm:ss";
	
	/**
	 * 日期格式为yyyy年MM月dd日
	 */
	public static final String yyyyNMMYddR="yyyy年MM月dd日";
	/**
	 * 日期格式为yyyy年MM月dd日 hh:mm:ss
	 */
	public static final String yyyyNMMYddR_hsm="yyyy年MM月dd日 hh:mm:ss";
	
	/**
	 * 转换为人民币大写
	 */
	public static final String numToRMB="numToRMB";
	
	
	
	public TextF(){
		
	}
	
}
