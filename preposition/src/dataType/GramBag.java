package dataType;

import java.util.ArrayList;

public class GramBag implements java.io.Serializable{
	
	public ArrayList<Gram2> Ls2=new ArrayList<Gram2>();
	public ArrayList<Gram3> Ls3=new ArrayList<Gram3>();
	public ArrayList<Gram4> Ls4=new ArrayList<Gram4>();
	public ArrayList<Gram5> Ls5=new ArrayList<Gram5>();
	
	/**
	 * 提取二元组
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static Gram2 getGram2(String str1,String str2)
	{
		Gram2 gram2=null;
		node node1=null,node2=null;
		int index;
		index=str1.indexOf((int)'/');
		String temp1=null,temp2=null;
		if(index<0)
		{
			temp1=str1;
			temp2=null;
		}	
		else
		{
			temp1=str1.substring(0, index);	
			temp2=str1.substring(index+1);
		}		
		node1=new node(temp1,temp2);		
		index=str2.indexOf((int)'/');
		temp1=str2.substring(0, index);
		temp2=str2.substring(index+1);
		node2=new node(temp1,temp2);
		
		gram2=new Gram2(node1,node2);		
		return gram2;		
	}
}
