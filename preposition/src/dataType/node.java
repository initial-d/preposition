package dataType;

public class node {
	
	public String word,property;
	
	/**
	 * �ڵ㹹�캯��
	 */
	public node()
	{
		this.word=null;
		this.property=null;
	}
	
	/**
	 * ���ع��캯��
	 * @param word
	 * @param property
	 */
	public node(String word,String property)
	{
		this.word=word;
		this.property=property;
	}

}
