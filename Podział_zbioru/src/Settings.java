import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;


public class Settings {
	
	static protected ArrayList<BigInteger> input;
	static protected BigInteger IDEAL_SET;
	static BigInteger xsize;
	static Generation BEST_INDS = new Generation();
	
	/**
	 * Ilo�� generacji
	 */
    static int MAX_GEN;;
    
    /**
     * Liczba osobnik�w (individuals) w generacji
     */
    static int IND;
    
    /**
     * Liczba zbior�w na jakie ma zosta� podzielony g��wny zbi�r
     */
    static int SUBSETS;
    
    /**
     * Akceptowalny b��d
     */
    static BigDecimal ACCEPT_ERR;
    
    /**
     * Procent osobnik�w prze�ywaj�cych ka�d� generacj�
     */
    static int SURVIVORS;
    
    Settings() {}
    
    Settings(int gen, int ind, int subs, int err, int surv, BigInteger el[]) 
    {
    	MAX_GEN = gen;
    	SUBSETS = subs;
    	IND = ind;
    	ACCEPT_ERR = new BigDecimal(err);
    	SURVIVORS = surv;
    	
    	input = new ArrayList<BigInteger>(el.length);
		Collections.addAll(input, el);
		BigInteger sum = new BigInteger("0"); 
	    for(BigInteger i : input)
	         sum = sum.add(i);
	    IDEAL_SET = sum.divide(BigInteger.valueOf(SUBSETS));
	    xsize = new BigInteger(String.valueOf(el.length));
	    Collections.sort(input);
    }
    
    Settings(BigInteger el[])
    {
    	input = new ArrayList<BigInteger>(el.length);
		Collections.addAll(input, el);
		BigInteger sum = new BigInteger("0"); 
	    for(BigInteger i : input)
	         sum = sum.add(i);
	    IDEAL_SET = sum.divide(BigInteger.valueOf(SUBSETS));
	    xsize = new BigInteger(String.valueOf(el.length));
	    Collections.sort(input);
    }

}
