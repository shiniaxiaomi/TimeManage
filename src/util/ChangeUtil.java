package util;

public class ChangeUtil {

	
	
	public static String changeTime(int time){
		
		String timeString = "";
		int shi;
		int fen;
		int miao;
		
		shi=time/3600;
		fen=(time%3600)/60;
		miao=time%60;
		
		if(shi<=9){
			timeString=timeString+"0"+shi;
		}else {
			timeString=timeString+shi;
		}
		timeString=timeString+":";
		if(fen<=9){
			timeString=timeString+"0"+fen;
		}else {
			timeString=timeString+fen;
		}
		timeString=timeString+":";
		if(miao<=9){
			timeString=timeString+"0"+miao;
		}else {
			timeString=timeString+miao;
		}
		
		return timeString;
	}
	
	public static int changeString(String time){
		
		if(StringUtil.isNumber(time) && !time.equals("")){
			return Integer.valueOf(time).intValue();	
		}else {
			return 0;
		}
		
			
		
	}
	
	
}
