import java.math.BigInteger;
import java.util.ArrayList;


public class Individual 
{
	
	ArrayList<ArrayList<BigInteger>> listInd = new ArrayList<ArrayList<BigInteger>>();
	BigInteger rank = new BigInteger("0");
	
	Individual() {}
	
	Individual(ArrayList<ArrayList<BigInteger>> list, byte[] r)
	{
		listInd = new ArrayList<ArrayList<BigInteger>>(list);
		rank = new BigInteger(r);
	}
	
	Individual(BigInteger elements[], int size, int setNumber)
	{
		for(int i=0; i<size; i++)
	        listInd.get(setNumber).add(elements[i]);
	}
	
	
	/**
	 * Zwraca ocenê osobnika
	 * @return
	 */
	public BigInteger getRank()
	{
		return rank;
	}
	
	/**
	 * Sumuje liczby w jednym ze zbiorów osobnika
	 * @param setNumber - index zbioru
	 * @return suma wartoœci liczb w zbiorze
	 */
	public BigInteger sumSet(int setNumber) 
	{
	     BigInteger sum = new BigInteger("0");
	     for(BigInteger i : listInd.get(setNumber))
	         sum = sum.add(i);
	     return sum;
	}
	
	

}
