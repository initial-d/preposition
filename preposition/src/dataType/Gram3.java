package dataType;

public class Gram3 {
	
	public boolean isPrep=false;
	node node1,node2,node3;
	
	/**
	 * 三元组构造函数
	 * @param node1
	 * @param node2
	 * @param node3
	 */
	public Gram3(node node1,node node2,node node3)
	{
		this.node1=node1;
		this.node2=node2;
		this.node3=node3;
		Ispre();
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode1()
	{
		return this.node1;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode2()
	{
		return this.node2;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode3()
	{
		return this.node3;
	}
	
	/**
	 * 三元组合并成四元组
	 * @param gram2
	 * @return
	 */
	public Gram4 mergeGram2(Gram2 gram2)
	{
		if(this.node3.word.equals(gram2.node1.word))
		{
			Gram4 gram4=new Gram4(this.node1,this.node2,this.node3,gram2.node2);
			return gram4;
		}
		else
		{
			return null;
		}		
	}
	
	/**
	 * 三元组合并成五元组
	 * @param gram3
	 * @return
	 */
	public Gram5 mergeGram3(Gram3 gram3)
	{ 
		if(this.node3.word.equals(gram3.node1.word))
		{
			Gram5 gram5=new Gram5(this.node1,this.node2,this.node3,gram3.node2,gram3.node3);
			if( (true==this.isPrep) || (true==gram3.isPrep) )
			{
				gram5.isPrep=true;
			}
			else
			{
				gram5.isPrep=false;
			}
			return gram5;
		}
		else
		{
			return null;
		}		
	}
	
	/**
	 * 判断三元组是否是介词词组
	 */
	public void  Ispre()
	{
		if(this.node1.property.equals("IN") ||this.node2.property.equals("IN")||this.node3.property.equals("IN") )
		{
			this.isPrep=true;
		}
	}

}
