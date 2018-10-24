package util;

public class HashUtil {
	
	
	public static String hash(String password){
		String result = "";
		
		result = password.hashCode() + "";
		
		return result;
	}
}
