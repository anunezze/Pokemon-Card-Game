package util;

public class IdGenerator {
	
	private static long idCounter = 0;
	
	public static synchronized long createID(){
		return idCounter++;
	}

}
