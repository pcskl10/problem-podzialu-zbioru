import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;



public class Evolution extends Settings
{
	private BigDecimal LOWEST_ERR =  new BigDecimal(Double.MAX_VALUE);
	static Individual BEST_IND = new Individual();
	static int ilosc = 1;

	/**
	 * Dzia³a, ale trzeba potestowaæ
	 * @param mainSet
	 * @return
	 */
	Generation randomGeneration(ArrayList<BigInteger> mainSet)
	{
		Generation gen = new Generation();;
		for(int i = 0; i < IND; ++i)
		{
			gen.listGen.add(new Individual());
		}
			
		ArrayList<BigInteger> copy = new ArrayList<BigInteger>(mainSet);
	    int size = 0;
	    MyRandom rand = new MyRandom();
	    
	    for(int i=0; i < IND; i++, mainSet.clear(), mainSet.addAll(copy))
	    { 
	    	
	        for(int sub_n=0; sub_n < SUBSETS; ++sub_n)
	        { 
	        	gen.listGen.get(i).listInd.add(new ArrayList<BigInteger>());
	            if(sub_n == SUBSETS-1)   //dla ostatniego zbioru dodaje pozosta³e elementy
	                size = mainSet.size();
	            else
	                size = (rand.nextNonNegative() % (mainSet.size() - SUBSETS + sub_n)) +1;
	            
	            for(int counter = 0; counter < size; counter++)
	            {
	                gen.listGen.get(i).listInd.get(sub_n).add( anyItem(mainSet) );
	            }
	        }
	    }
	    return gen;
	}
	
	int[] chooseParents(double fitnesses[], double total_fitness, int pairs_needed)
	{
		double map[] = new double[IND];
	    double total = 0;
	    int parents[] = new int[ pairs_needed*2 ];
	    Random rand = new Random();
	    
	    for(int i = 0; i < IND; i++)// liczy pradopodobieñstow dla ka¿dego osobnika na zostanie rodzicem
	    {                 
	        total += fitnesses[i]/total_fitness;
	        map[i] = total;
	    }


	    for(int i = 0; i < pairs_needed*2; i += 2)
	    {                
	        double prob_p1 = rand.nextDouble();     
	        int pind1 = 1, pind2;
	        while( prob_p1 > map[ pind1 ] ) pind1++;
	        parents[i] = pind1-1;
	        
	        do
	        {
	        	pind2 = 1;
	        	double prob_p2 = rand.nextDouble();
	        	while( prob_p2 > map[ pind2 ] ) pind2++;
	        	parents[i+1] = pind2-1;
	        } while ( pind1 == pind2 ); //reject Px + Px
	        
	    }

	    return parents;
	}
	
	
	Generation evolve(Generation old_gen)
	{
		
		Generation new_gen = new Generation();
	    double fitnesses[] = new double[IND];
	    double total_fitness = 0; 
	    int parents[];
	    int survived = (SURVIVORS * IND)/100; // iloœæ osobników, która prze¿ywa generacjê
	    
	    scoreIndividuals(old_gen);
	    Collections.sort(old_gen.listGen, new RankComparator()); //najlepsze osobniki na pocz¹tku (tu ju¿ sie liczy rank dla wszystkich osobników)
	    BigDecimal highest_error = new BigDecimal(Rate.rateIndividual( old_gen.listGen.get(IND-1) )); // najwiêkszy b³¹d (ostatni osobnik)
	    BigDecimal lowest_error  = new BigDecimal(Rate.rateIndividual( old_gen.listGen.get(0) )); // najmniejszy b³ad (pierwszy osobnik)
	    //LOWEST_ERR = lowest_error.compareTo(LOWEST_ERR) == -1  ? lowest_error : LOWEST_ERR;
	    
	   
	    if( lowest_error.compareTo(LOWEST_ERR) == -1 ) 
	    {
	    	LOWEST_ERR = lowest_error;
	    	Generation allBestInds = new Generation();
	    	allBestInds.listGen.add(0, old_gen.listGen.get(0));
	    	
	    	BEST_IND.listInd.clear();
	        BEST_IND = copy_ind( old_gen.listGen.get(0) );
	        Collections.sort(BEST_IND.listInd.get(0));
	        for(int i = 0; i < SUBSETS; i++)
	        {
	        	Collections.sort(BEST_IND.listInd.get(i));
	        	System.out.println(BEST_IND.listInd.get(i) + "    " + BEST_IND.listInd.get(i).size() + "     sum= " + BEST_IND.sumSet(i));
	        }
	        System.out.println();
	        Settings.BEST_INDS.listGen.add(0, copy_ind(BEST_IND));
	        

	    	
	    }
	    
	    if(LOWEST_ERR.compareTo(ACCEPT_ERR) == 0 || LOWEST_ERR.compareTo(ACCEPT_ERR) == -1)
	    {
	    	System.out.println("LOWEST_ERR <= ACCEPT_ERR");
	        return null;
	    }
	    
	    
	    for(int i = 0; i < IND; i++) // ocena starej generacji (wszystkich osobników)
	    { 
	        fitnesses[i] = i+1;//highest_error + 1 - (old_gen.listGen.get(i).rank );
	        total_fitness += fitnesses[i];
	    }
	    
	    int i;
	    if(IND > 1 )
	    {
	    parents = chooseParents(fitnesses,  total_fitness, IND-survived+2 ); 
	     
	    int parent = 0;
	    for(i = 0; i < survived; i++)
	    { //zachowuje najlepsze osobniki
	        new_gen.listGen.add( copy_ind( old_gen.listGen.get(i) ) );
	    }
	    
	    
	    
	    
	     //CROSSOVER  
	    for( ; i < IND; i++ ) // tworzy pozosta³e osobniki poprzez crossover wykorzystuj¹c najlepszych rodziców
	    {
	    	new_gen.listGen.add( crossover( sortSetsByRank(old_gen.listGen.get(parents[parent])) , sortSetsByRank(old_gen.listGen.get( parents[parent + 1] ) )) ); // dodaje skrzy¿owane osobniki do new_gen
	        parent += 2; 
	    } 
	    }
	    
	    
	     //MUTATION 
	    Collections.sort( old_gen.listGen, new RankComparator() ); // sortowanie
	   
	    Random rand = new Random();
	    for( i = 0; i < IND; i++ )
	    {
	     
	        if ( i < survived ) 
	        {//10%
	            for(int nmut = 1; nmut > 0; nmut-- )  
	            	if((rand.nextInt(100) % 10) == 0 ) mutation( new_gen.listGen.get(i) );
	        } 
	        else if ( i < (int)IND*0.50 )
	        { //10-50%
	            for(int nmut = 4; nmut > 0; nmut-- )  
	            	if( ( rand.nextInt(100) % 2 ) != 0 )  mutation( new_gen.listGen.get(i) );
	        } 
	        else if( i<(int)IND*0.70 )
	        {//50-70%
	            for(int nmut = 10; nmut > 0; nmut--) 
	            	if( ( rand.nextInt(100) % 2 ) != 0 )  mutation( new_gen.listGen.get(i) );
	        } 
	        else  //70-100%
	            for(int nmut = 20; nmut > 0; nmut-- )
	            {
	            	if ( ( rand.nextInt(100) % 2 ) != 0 )   mutation( new_gen.listGen.get(i) );
	            }
	    }
	    return new Generation(new_gen);       
	}
	
	Individual crossover( Individual par1, Individual par2 )
	{
		Individual newind = new Individual();
		ArrayList<BigInteger> tmp = new ArrayList<BigInteger>();
	    ArrayList<BigInteger> copy = new ArrayList<BigInteger>(input);
	    BigInteger p1rate;
		BigInteger p2rate;
	    Map<BigInteger, Integer> el_map = new HashMap<BigInteger, Integer>();
	    

	    for( int i=0, p1=0, p2=0; i < SUBSETS; i++ )
	    {
	        p1rate = ( Rate.rateSet(par1.listInd.get(p1), IDEAL_SET) ).abs();
	        p2rate = ( Rate.rateSet(par2.listInd.get(p2), IDEAL_SET) ).abs();

	        if( p1rate.compareTo(p2rate) == 1 ) 
	        {
	            newind.listInd.add(new ArrayList<BigInteger>(p2 < SUBSETS ? par2.listInd.get(p2++) : par1.listInd.get(p1++)));
	        } 
	        else if( p1rate.compareTo(p2rate) == -1) 
	        { 
	        	newind.listInd.add(new ArrayList<BigInteger>(p1 < SUBSETS ? par1.listInd.get(p1++) : par2.listInd.get(p2++)));
	        } 
	        else 
	        {
	            if( p1 != SUBSETS && p2 != SUBSETS ) 
	            {
	            	Random rand = new Random();
	                if( ( rand.nextInt(100) % 2 ) != 0 )
	                    newind.listInd.add( new ArrayList<BigInteger>(par2.listInd.get(p2++)) );
	                else
	                	newind.listInd.add( new ArrayList<BigInteger>(par1.listInd.get(p1++)) );
	            }
	            else
	            	newind.listInd.add( new ArrayList<BigInteger>(p1 < SUBSETS ? par1.listInd.get(p1++) : par2.listInd.get(p2++)) );
	        }

	        
	        // sprawdzamy czy w nie powtarzaj¹ siê wartoœci w setach (jedna wartoœæ mo¿e wystêpowaæ tylko raz)
	        tmp = new ArrayList<BigInteger>( newind.listInd.get(i) ); 
	        ListIterator<BigInteger> it = tmp.listIterator();
	        Integer sizeOfSet = Integer.valueOf(newind.listInd.get(i).size());
	        for( int el = 0; el < sizeOfSet; el++ )
	        {
	        	//System.out.println(sizeOfSet);
	        	BigInteger val = new BigInteger("0");
				if( it.hasNext() ) 
	        		val = it.next();

				copy.remove( (Object)val );
	            if( !el_map.containsKey( val ) )
	            {
	                el_map.put(val, i);
	                
	            }
	            else
	            {
	                p1rate = ((IDEAL_SET.subtract(newind.sumSet(el_map.get(val)))).add(val)).abs();// tu chcemy wsadziæ 
	                p2rate = ((IDEAL_SET.subtract(newind.sumSet(i))).add(val) ).abs(); // tu jest wsadzony
	                //usuwamy powtórzon¹ wartoœæ z tego zbioru, tóry ma wiêkszy b³¹d
	                if((p1rate.compareTo(p2rate) == 1))
	                    newind.listInd.get(el_map.get(val)).remove((Object) val);
	                else if(p1rate.compareTo(p2rate) == -1 ) 
	                {
	                    newind.listInd.get(i).remove((Object) val);
	                }
	                else
	                {
	                	Random rand = new Random();
	                    if( (rand.nextInt() % 2) != 0)
	                    	newind.listInd.get(el_map.get(val)).remove((Object)val);
	                    else
	                    	newind.listInd.get(i).remove((Object)val);
	                }

	            }
	        }
	    }
	    
	    
	    tmp = new ArrayList<BigInteger>(copy);
	    ListIterator<BigInteger> it2 = tmp.listIterator();
	    for(int i = copy.size(); i > 0; i--) // rozdaje pozosta³e elementy po zbiorach
	    {
	    	
	        int  min_ind = -1;
	        BigDecimal min_err = new BigDecimal(Double.MAX_VALUE);
	        BigDecimal  err = new BigDecimal("0");
	        BigInteger val = new BigInteger("0");
			if( it2.hasNext() )
	        	val  = it2.next();
	        for(int s=0; s < SUBSETS; s++)
	        {
	        	err = new BigDecimal(IDEAL_SET.subtract(newind.sumSet(s))).subtract(new BigDecimal(val)).abs();
	            if(min_err.compareTo(err) == 1);
	            {
	                min_err = err;
	                min_ind = s;
	            }
	        }

	        newind.listInd.get(min_ind).add(val); 
	        copy.remove( (Object)val );
	    }
	    
	    
	    return newind;
	}
	
	
	/**
	 * Dzia³a, ale mo¿e wylosowaæ set nr 1 i dodaæ do set nr 1 (czyli dodaæ do tego samego)
	 * @param ind - osobnik poddany mutacji
	 */
	void mutation(Individual ind)
	{
		
	    BigInteger el = new BigInteger("0");
	    MyRandom rand = new MyRandom();
	    ArrayList<BigInteger> set = new ArrayList<BigInteger>();
	    while(el.compareTo(BigInteger.valueOf(0)) == 0)
	    {
	        set = ind.listInd.get(rand.nextNonNegative() % SUBSETS);
	        if(set.size() != 0)
	        	el = anyItem(set);
	    }
	    set = ind.listInd.get(rand.nextNonNegative() % SUBSETS);
	    if(!set.contains(el))
	    	set.add(el);

	}
	
	
	/**
	 * Funkcja losuje elementy z podanej listy i go kasuje w liœcie 
	 * @param set - lista
	 * @return wylosowany element
	 */
	BigInteger anyItem(ArrayList<BigInteger> set)
    {
		MyRandom randomGenerator = new MyRandom();
		/*if(s.size() == 0)
			return -1;*/
		//System.out.println(s.size());
        int index = randomGenerator.nextInt(set.size());
        BigInteger item = set.get(index);
        set.remove(item);
        return item;
    }
	
	/**
	 * Ocenia osobniki
	 * @param gen - generacja poddana ocenie
	 */
	void scoreIndividuals(Generation gen)
	{
		for(int i = 0; i < gen.listGen.size(); ++i)
		{
			Rate.rateIndividual(gen.listGen.get(i));
		}
	}
	
	
	/**
	 * Kopiuje osobnika
	 * @param ind - osobnik którego trzeba zrobiæ kopiê
	 * @return
	 */
	Individual copy_ind(Individual ind)
	{
		Individual copy = new Individual();
		for(int i = 0; i < ind.listInd.size(); i++)
		{
			copy.listInd.add(new ArrayList<BigInteger>(ind.listInd.get(i)));
		}
		//copy.listInd = new ArrayList<ArrayList<BigInteger>>(ind.listInd);
		//copy.rank = new BigInteger(new String((ind.rank).toByteArray()));
		return copy;
		
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) 
	    {
	        if (Objects.equals(value, entry.getValue())) 
	        {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public boolean check(Individual ind)
	{
		ArrayList<BigInteger> tmp;
		ArrayList<BigInteger> copy = new ArrayList<BigInteger>(input);
	    Map<BigInteger, Integer> el_map = new HashMap<BigInteger, Integer>();
		for(int i = 0; i < SUBSETS; i++)
		{
			tmp = new ArrayList<BigInteger>( ind.listInd.get(i) ); 
			ListIterator<BigInteger> it = tmp.listIterator();
			for( int el = 0; el < ind.listInd.get(i).size(); el++ )
			{
				BigInteger val = new BigInteger("0");
				if( it.hasNext() ) 
					val = it.next();

				copy.remove( (Object)val );
				if( !el_map.containsKey( val ) )
				{
					el_map.put(val, i);
				}
				else 
					return false;
			}
		}
		return true;
	}
	
	public Individual sortSetsByRank(Individual a)
	{
		ArrayList<BigInteger> temp;
    	int zmiana = 1;
    	while(zmiana > 0)
    	{
    		zmiana = 0;
    		for(int i=0; i < a.listInd.size()-1; i++)
    		{
    			if((Rate.rateSet(a.listInd.get(i),IDEAL_SET).abs()).compareTo(Rate.rateSet(a.listInd.get(i+1),IDEAL_SET).abs()) == 1 )
    			{
    				temp = new ArrayList<BigInteger>(a.listInd.get(i+1));
    				a.listInd.get(i+1).clear();
    				a.listInd.get(i+1).addAll(a.listInd.get(i));
    				a.listInd.get(i).clear();
    				a.listInd.get(i).addAll(temp);
    				zmiana++;
    			}
    		}
    	}
        return a;
	}
	
}

class RankComparator extends Settings implements Comparator<Individual>
{
    public int compare(Individual a, Individual b) 
    {
        return (a.rank).compareTo(b.rank) == -1 ? -1 : (a.rank).compareTo(b.rank) == 0 ? 0 : 1;
    }
    
}

class MyRandom extends Random 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRandom() {}
    public MyRandom(int seed) { super(seed); }

    public int nextNonNegative() 
    {
        return next(31);
    }
}
