package dataType;

public class node {
	
	public String word,property;
	
	/**
	 * 节点构造函数
	 */
	public node()
	{
		this.word=null;
		this.property=null;
	}
	
	/**
	 * 重载构造函数
	 * @param word
	 * @param property
	 */
	public node(String word,String property)
	{
		this.word=word;
		this.property=property;
	}

}
