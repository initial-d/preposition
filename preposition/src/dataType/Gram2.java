package dataType;

public class Gram2 {
	

	public boolean isPrep=false;
	node node1,node2;	
	
    /**
     * ��Ԫ��Ĺ��캯��
     * @param node1
     * @param node2
     */
	public Gram2(node node1,node node2)
	{
		this.node1=node1;
		this.node2=node2;
	}
	
	/**
	 * get����
	 * @return
	 */
	public node getNode1()
	{
		return node1;
	}
	
	/**
	 * get����
	 * @return
	 */
	public node getNode2()
	{
		return node2;
	}

	/**
	 * ��Ԫ��ϲ�����Ԫ��
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
	 * ��Ԫ��ϲ�����Ԫ��
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
	 * ��Ԫ��ϲ�����Ԫ��
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
	 * �ж�Ԫ���Ƿ��ǽ�ʴ���
	 */
	public void Ispre()
	{
		if(this.node1.property.equals("IN")||this.node2.property.equals("IN"))
		{
			this.isPrep=true;
		}
	}

}
