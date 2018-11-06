package util;

import java.util.HashMap;
import java.util.Map;

public enum ChallengeStatus {
	OPEN(0),
	REFUSED(1),
	WITHDRAW(2),
	ACCEPTED(3);
	
	private int value;
	private static Map<Integer, ChallengeStatus> map = new HashMap<Integer, ChallengeStatus>();
	
	static {
        for (ChallengeStatus csEnum : ChallengeStatus.values()) {
            map.put(csEnum.value, csEnum);
        }
    }
	
	private ChallengeStatus(final int leg) { value = leg; }
	public static ChallengeStatus valueOf(int legNo) {
        return map.get(legNo);
    }
}
