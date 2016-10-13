package regular_eqation;

import java.util.*;
//
public class regular_eqation {
    int  vnumber;
	public String equ;
	int const_term ;
	product_term head , tail ;
	boolean[] alphabet = new boolean[26];
	
	
	
	
	public boolean expression()
	{
		char ch ;
		//------------------------Ѱ�ҷǷ��ַ����������г��ֵı������ĸ���--------------------------------//
		for(int i = 0; i < equ.length();i++)
		{
			ch = equ.charAt(i);
			if( (ch  <= '9'&&ch >= '0')|| ch =='*' || ch == '+' )
				continue;
			else if(ch <='z'&& ch >='a')
			{
				alphabet[ch-'a'] = true;
			}
			else
				return false;
		}
		//--------------------ͨ�������ַ����������ʽ��Ϣ�������ݽṹ��---------------------//
		for(int i = 0; i < equ.length();i++)
		{
			for(int j= i  ; ;j++  )
			{
				if(j == equ.length() - 1)
				{
					if( store( equ.substring( i , j+1 ) ) == false )
						return false;
					i = j;
					break;
				}
				if(equ.charAt(j) == '+')
				{
					if( store( equ.substring( i , j ) ) == false )
						return false;
					i = j;
					break;
				}
			}			
		}
		return true;
		
	}
	private boolean store(String str)
	{
		if(str.length()==0)
			return false;
		char chh =str.charAt(str.length()-1);
		if(  !( (chh <='9'&& chh >='0') || (chh <= 'z' && chh >= 'a') )  )
			return false;
		product_term temp = new product_term();
		
		for(int i = 0 ; i < str.length();i++)
		{
			char ch =str.charAt(i);
			if(ch <= '9'&& ch >= '0')
			{
				for(int j=i+1;true;j++)
				{
					if( str.length() == j || !(str.charAt(j) <= '9'&& str.charAt(j) >= '0') )
					{
						temp.coe *= Integer.parseInt( str.substring(i, j) );
						i = j-1;
						break;
					}
				}               
			}
			else if( ch <= 'z' && ch >= 'a')
			{
				if( i == str.length() - 1 || (str.charAt(i+1) <='z'&&str.charAt(i+1)>='a')|| str.charAt(i+1) == '*' )
					temp.variable_list[ch-'a']++;
				else if (str.charAt(i+1) <='9'&&str.charAt(i+1)>='0')
					return false;
			}
			else if( ch =='*')
			{
				if(str.charAt(i+1) =='*' )
					return false;
			}
		}
		
		if(temp.coe ==0 )
			return true;
		if( temp.isConst() )
			const_term += temp.coe;
		else
		{
			
			if(head == null)
			{
				head = temp;
				tail = head;
			}
			else 
			{
				tail.next = temp;
				tail = tail.next;
			}
			
		}
		
		return true;
	}
	
	public void simplify( String ins)
	{
		char variable[] = new char[26];
		   vnumber=0;
		for(int i=10;i<ins.length();i++)
		{
			if(ins.charAt(i)<='z'&&ins.charAt(i)>='a')
			{
				
				variable[vnumber]=ins.charAt(i);
				vnumber++;
			}
		}
		for(int k=0;k<vnumber;k++)
		{
			for(int i=0;i<26;i++)
			{
				if(alphabet[ variable[k]  -'a'] == false)
				{
					System.out.println("���ʽ��û����Ҫ����ı�����");
					return ;
				}
			}
		}
		int a ,b;
		a=12;
		int num[] = new int [26] ;
		for(int i=0;i<vnumber;i++)
		{
			b=a;
			for(;(b<ins.length()&&ins.charAt(b)>='0'&&ins.charAt(b)<='9');b++)
			{
				;
			}
			num[i]=Integer.parseInt( ins.substring(a, b ));
			a=b+3;
			
		}
		regular_eqation copy=new regular_eqation();
		copy.const_term = const_term;
		product_term temp ;
		product_term p = head;
		while(p != null)
		{
			temp = new product_term();
			temp.coe=p.coe;
			for(int k=0;k<vnumber;k++)
			{
				for( int i = 0 ; i < 26;i++)
				{
					if('a' + i  == variable[k] )
					{
						temp.coe=temp.coe*math.pow(num[k], p.variable_list[i]);
					}
					temp.variable_list[i]=p.variable_list[i];
				}
			}
			for(int k=0;k<vnumber;k++)
			{
				for( int i = 0 ; i < 26;i++)
				{
					if('a' + i  == variable[k] )
					{
						temp.variable_list[i]=0;
					}
				}
			}
			if(temp.coe==0)
			{
				p=p.next;
				continue;
			}
			if(temp.isConst())
			{
				copy.const_term+= temp.coe;
			}
			else
			{
				if(copy.head==null)
				{
					copy.head=temp;
					copy.tail=temp;
					copy.tail.next=null;
				}
				else
				{
					copy.tail.next=temp;
					copy.tail=temp;
					temp.next=null;
				}		
			}
			p=p.next;
		}
	
		p=copy.head;
		String output_string = new String();
		while(p !=null)
		{
			int flag=0;
			if(p.coe ==1)
			{
				flag=1;
			}
			else 
				output_string +=  p.coe;
			for(int k=0;k<26;k++)
			{
				for(int m=0 ; m < p.variable_list[k];m++)
				{
					if(flag==1)
					{
						output_string += ((char)('a'+k));
						flag =0;
					}
					else 
						output_string += ("*"+(char)('a'+k));
					
				}
			}
			output_string+=("+");
			p = p.next;
		}
		if(copy.const_term != 0)
			System.out.println(output_string+copy.const_term);
		else
		{
			if(output_string.length()==0)
				System.out.println("0");
			else
				System.out.println(output_string.substring(0, output_string.length()-1));
		}
			
	}
	
	public void derivative(String ins)
	{
		char variable = ins.charAt(4);
		if(!(variable <= 'z'&&variable>='a'))
		{
			System.out.println("���벻�Ϸ���");
			return ;
		}	
		if(alphabet[ variable  -'a'] == false)
		{
			System.out.println("Error,  no variable");
			return ;
		}
		regular_eqation copy=new regular_eqation();
		product_term temp ;
		product_term p = head;
		while(p != null)
		{
			temp = new product_term();
			temp.coe=p.coe;
			for( int i = 0 ; i < 26;i++)
			{
				if('a' + i  == variable )
				{
					temp.coe=temp.coe* p.variable_list[i];
					temp.variable_list[i] = p.variable_list[i] - 1;
				}
				else
				{
					temp.variable_list[i]=p.variable_list[i];
				}
			}
			if(temp.coe==0)
			{
				p=p.next;
				continue;
			}
			if(temp.isConst())
			{
				copy.const_term+= temp.coe;
			}
			else
			{
				if(copy.head==null)
				{
					copy.head=temp;
					copy.tail=temp;
					copy.tail.next=null;
				}
				else
				{
					copy.tail.next=temp;
					copy.tail=temp;
					temp.next=null;
				}		
			}
			p=p.next;
		}
		p=copy.head;
		String output_string = new String();
		while(p !=null)
		{
			int flag=0;
			if(p.coe ==1)
			{
				flag=1;
			}
			else 
				output_string +=  p.coe;
			for(int k=0;k<26;k++)
			{
				for(int m=0 ; m < p.variable_list[k];m++)
				{
					if(flag==1)
					{
						output_string += ((char)('a'+k));
						flag =0;
					}
					else 
						output_string += ("*"+(char)('a'+k));
					
				}
			}
			output_string+=("+");
			p = p.next;
		}
		if(copy.const_term != 0)
			System.out.println(output_string+copy.const_term);
		else
		{
			if(output_string.length()==0)
				System.out.println("0");
			else
				System.out.println(output_string.substring(0, output_string.length()-1));
		}
			
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		regular_eqation equation ;
		Scanner in = new Scanner(System.in);
		String input;
//----------------------������ʽ�׶�------------------------------//
		while(true)
		{
			System.out.println("��������ʽ��");
			input =in.nextLine();
			equation = new regular_eqation();
		    equation.equ= new String(input);	 
		    if(equation.expression() == true)
		    {
		    	System.out.println("������ʽ�Ϸ���");
		    	break;
		    }
		    else 
		    {
		    	System.out.println("������ʽ���Ϸ���");    
		    	equation.equ = null;
		    }
		    	
		}
//-----------------   ��������---------------------------//
		while(true)
		{
			System.out.println("���������������!exit�˳���");
		    input =in.nextLine();
			if(  input.length() > 9 && input.substring(0, 9).equals("!simplify") )
				equation.simplify(input);
			else if( input.length() > 4 &&  input.substring(0, 4).equals("!d/d") )
				equation.derivative(input);
			else if( input.equals("!simplify"))
			{
				System.out.println(equation.equ);   
			}
			else if (input.equals( "!exit" )  )
			{
				System.out.println("���������");
				break;
			}
			else 
				System.out.println("����ָ��Ϸ���");
		}
	    
	}

}