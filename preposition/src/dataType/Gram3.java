package dataType;

public class Gram3 {
	
	public boolean isPrep=false;
	node node1,node2,node3;
	
	/**
	 * ��Ԫ�鹹�캯��
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
	 * get����
	 * @return
	 */
	public node getNode1()
	{
		return this.node1;
	}
	
	/**
	 * get����
	 * @return
	 */
	public node getNode2()
	{
		return this.node2;
	}
	
	/**
	 * get����
	 * @return
	 */
	public node getNode3()
	{
		return this.node3;
	}
	
	/**
	 * ��Ԫ��ϲ�����Ԫ��
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
	 * ��Ԫ��ϲ�����Ԫ��
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
	 * �ж���Ԫ���Ƿ��ǽ�ʴ���
	 */
	public void  Ispre()
	{
		if(this.node1.property.equals("IN") ||this.node2.property.equals("IN")||this.node3.property.equals("IN") )
		{
			this.isPrep=true;
		}
	}

}
