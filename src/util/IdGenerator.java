package util;

public class IdGenerator {
	
	private static IdGenerator instance = null;
	
	private long idCounter;
	
	private IdGenerator(){
		idCounter = 0;
	}
	
	public static synchronized IdGenerator getInstance(){
		if(instance == null){
			instance = new IdGenerator();
		}
		return instance;
	}
	
	public long createID(){
		return idCounter++;
	}

}
