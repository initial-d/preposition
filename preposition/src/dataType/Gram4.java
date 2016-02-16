package dataType;

public class Gram4 {
	
	public boolean isPrep=false;
	node node1,node2,node3,node4;
	
	/**
	 * 四元组构造函数
	 * @param node1
	 * @param node2
	 * @param node3
	 * @param node4
	 */
	public Gram4(node node1,node node2,node node3,node node4)
	{
		this.node1=node1;
		this.node2=node2;	
		this.node3=node3;
		this.node4=node4;
		Ispre();
	}
	public node getNode1()
	{
		return this.node1;
	}
	public node getNode2()
	{
		return this.node2;
	}
	public node getNode3()
	{
		return this.node3;
	}
	public node getNode4()
	{
		return this.node4;
	}

	/**
	 * 四元组合并成五元组
	 * @param gram2
	 * @return
	 */
	public Gram5 mergeGram2(Gram2 gram2)
	{
		if(this.node4.word.equals(gram2.node1.word))
		{
			Gram5 gram5=new Gram5(this.node1,this.node2,this.node3,this.node4,gram2.node2);
			return gram5;
		}
		else
		{
			return null;
		}			
	}
	
	/**
	 *判断四元组是否是介词元组
	 */
	public void Ispre()
	{
		if(this.node1.property.equals("IN")||this.node2.property.equals("IN")||this.node3.property.equals("IN")||this.node4.property.equals("IN"))
		{
			this.isPrep=true;
		}
	}
}
