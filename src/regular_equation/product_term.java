package regular_equation;

public class product_term {
	public int coe = 1;
	public int variable_list[] = new int[26] ;
	product_term next;
	
	public boolean isConst()
	{
		for(int i = 0 ; i < 26 ; i++)
		{
			if(variable_list[i] != 0)
				return false;
		}
		return true;
	}
}


qwfwqfweq
