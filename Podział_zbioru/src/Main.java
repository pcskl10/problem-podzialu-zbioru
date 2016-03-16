import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;


public class Main extends Settings
{

	static BigInteger[] bigElements;
	static Settings settings;
	Main(Settings s, BigInteger[] bigElementss)
	{
		settings = s;
		bigElements = bigElementss;
		
	}
	public static void main(String[] args) 
	{
		
		ArrayList<BigInteger> list = new ArrayList<BigInteger>(bigElements.length);
		Collections.addAll(list, bigElements);

		Evolution e = new Evolution();
		
		Generation gen = new Generation();
		gen = e.randomGeneration(list);  
        for(int i = 0; i <MAX_GEN; i++)
        {
            gen = e.evolve(gen); 
            Chart.iteration = i+1;
            if( gen == null )
            {
            	
                break;
            }

        }
       
        System.out.println("koniec");
	}


}
