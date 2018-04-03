package es.experis.arqueopterix.policyserver.util;

import java.util.Random;

public class IDGenerator {

	private static int LENGTH = 7;
	private static final int LIMIT_MEDIUMINT_MYSQL = 8388607;
	
	public static int generateRandom() {
		Random random;
	    char[] digits;
		do
	    {
			random = new Random();
			digits = new char[LENGTH];
		    digits[0] = (char) (random.nextInt(9) + '1');
		    for (int i = 1; i < LENGTH; i++) {
		        digits[i] = (char) (random.nextInt(10) + '0');
		    }
	    }while(Integer.parseInt(new String(digits)) >= LIMIT_MEDIUMINT_MYSQL);
	    
	    return Integer.parseInt(new String(digits));
	}
}
