package util;

/**
 * 字符串工具类
 * @author lyj80
 *
 */
public class StringUtil {

	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str==null || "".equals(str.trim())) {//str为空或者str是空串
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if(str!=null && !"".equals(str.trim())) {//str为不为空并且str不是空串
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isNumber(String s) {
		
		String str=s.trim();//去掉前导空白和后导空白
		
		for(int i=0;i<str.length();i++){
			if(i==0){
				if(str.charAt(i)=='0'){
					str=str.substring(1, str.length());
					i--;
				}
			}
			
			if(i>=0){
				if(!(str.charAt(i)>='0' && str.charAt(i)<='9'))	return false;
			}
			
		}
		
		return true;
	}
	
	public static String transNumber(String s){
		
		String str=s.trim();//去掉前导空白和后导空白
		
		for(int i=0;i<str.length();i++){
			
			if(i==0 && str.length()>1){
				if(str.charAt(i)=='0'){
					str=str.substring(1, str.length());
					i--;
				}
			}
			
			if(i>=0){
				if(!(str.charAt(i)>='0' && str.charAt(i)<='9'))	return "0";
			}
			
		}
		
		return str; 
		
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(transNumber("0"));
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
