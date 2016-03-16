import java.util.ArrayList;


public class Generation extends Settings
{
	public Generation() {}
	public Generation(Generation gen) 
	{
		this.listGen = new ArrayList<Individual>( gen.listGen );

	}

	ArrayList<Individual> listGen = new ArrayList<Individual>();
	


}
