package dataType;

public class Gram2 {
	

	public boolean isPrep=false;
	node node1,node2;	
	
    /**
     * 二元组的构造函数
     * @param node1
     * @param node2
     */
	public Gram2(node node1,node node2)
	{
		this.node1=node1;
		this.node2=node2;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode1()
	{
		return node1;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode2()
	{
		return node2;
	}

	/**
	 * 二元组合并成三元组
	 * @param gram2
	 * @return
	 */
	public Gram3 mergeGram2(Gram2 gram2)
	{
		if(this.node2.word.equals(gram2.node1.word))
		{
			if(this.node1.word.equals(gram2.node2.word))
			{
				return null;
			}
			else
			{
				Gram3 gram3=new Gram3(this.node1,this.node2,gram2.node2);
				return gram3;
			}
		}
		else
		{
			return null;
		}			
	}
	
	/**
	 * 三元组合并成四元组
	 * @param gram3
	 * @return
	 */
	public Gram4 mergeGram3(Gram3 gram3)
	{
		if(this.node2.word.equals(gram3.node1.word))
		{
			Gram4 gram4=new Gram4(this.node1,this.node2,gram3.node2,gram3.node3);
			return gram4;			
		}
		else
		{
			return null;
		}		
	}
	
	/**
	 * 四元组合并成五元组
	 * @param gram4
	 * @return
	 */
	public Gram5 mergeGram4(Gram4 gram4)
	{
		if(this.node2.word.equals(gram4.node1.word))
		{
			Gram5 gram5=new Gram5(this.node1,this.node2,gram4.node2,gram4.node3,gram4.node4);
			return gram5;
		}
		else
		{
			return null;
		}				
	}
	
	/**
	 * 判断元组是否是介词词组
	 */
	public void Ispre()
	{
		if(this.node1.property.equals("IN")||this.node2.property.equals("IN"))
		{
			this.isPrep=true;
		}
	}

}
