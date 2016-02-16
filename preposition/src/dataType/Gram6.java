package dataType;

public class Gram6 {
	public boolean isPrep=false;
	node node1,node2,node3,node4,node5,node6;	
	
	/**
	 * 六元组构造函数
	 * @param node1
	 * @param node2
	 * @param node3
	 * @param node4
	 * @param node5
	 * @param node6
	 */
	public Gram6(node node1,node node2,node node3,node node4,node node5,node node6)
	{
		this.node1=node1;
		this.node2=node2;	
		this.node3=node3;
		this.node4=node4;
		this.node5=node5;
		this.node6=node6;
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
	 * get函数
	 * @return
	 */
	public node getNode4()
	{
		return this.node4;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode5()
	{
		return this.node5;
	}
	
	/**
	 * get函数
	 * @return
	 */
	public node getNode6()
	{
		return this.node6;
	}
	
	/**
	 * 判断元组是否是介词元祖
	 */
	public void Ispre()
	{
		if(this.node1.property.equals("IN")||this.node2.property.equals("IN")||this.node3.property.equals("IN")||this.node4.property.equals("IN")||this.node4.property.equals("IN"))
		{
			this.isPrep=true;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
