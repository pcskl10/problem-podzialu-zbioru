import java.math.BigInteger;
import java.util.ArrayList;


final class Rate extends Settings
{
	/**
	 * Ocenia osobnik
	 * @param in - osobnik
	 * @return ocena osobnik
	 */
	static BigInteger rateIndividual( Individual in )
	{
	    BigInteger diff_total = new BigInteger("0");
	    for(int i = 0; i < in.listInd.size(); i++)
	    {
	        diff_total = diff_total.add(pow(rateSet(in.listInd.get(i), IDEAL_SET), BigInteger.valueOf(2)));
	    }
	    in.rank = diff_total;
		return diff_total;
	}
	
	/**
	 * Ocenia zbiór osobnika
	 * @param arrayList - zbiór do oceny
	 * @param goal - idealny zbiór
	 * @return
	 */
	static BigInteger rateSet(ArrayList<BigInteger> arrayList, BigInteger goal )
	{
		BigInteger sum = new BigInteger("0"); 
	     for(BigInteger i : arrayList)
	         sum = sum.add(i);
	    return sum.subtract(goal);
	}
	
	static BigInteger pow(BigInteger base, BigInteger exponent) 
	{
		  BigInteger result = BigInteger.ONE;
		  while (exponent.signum() > 0) 
		  {
		    if (exponent.testBit(0)) result = result.multiply(base);
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		  }
		  return result;
		}
}
