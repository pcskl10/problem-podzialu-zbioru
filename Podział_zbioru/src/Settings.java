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
	 * Iloœæ generacji
	 */
    static int MAX_GEN;;
    
    /**
     * Liczba osobników (individuals) w generacji
     */
    static int IND;
    
    /**
     * Liczba zbiorów na jakie ma zostaæ podzielony g³ówny zbiór
     */
    static int SUBSETS;
    
    /**
     * Akceptowalny b³¹d
     */
    static BigDecimal ACCEPT_ERR;
    
    /**
     * Procent osobników prze¿ywaj¹cych ka¿d¹ generacjê
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
