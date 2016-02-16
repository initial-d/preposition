package parse;

public class Tire implements java.io.Serializable {
	
	Tire [] child;  //字符孩子节点
	char ch;  //当前节点字符
	int count; //节点计数器
	
	/**
	 * 字典树构造函数
	 */
	public Tire()
	{
	  child=new Tire[26];
	  count=0;
	}
	
	/**
	 * 字符串插入字典树
	 * @param root
	 * @param str
	 */
	public static void insert(final Tire root,String str)
	{
		Tire cur=root;
		//String s=str.replace(str.charAt(0), (char)(str.charAt(0)+32));
		//第一个单词大写变成小写
		for(char ch: str.toCharArray())
		{
			int idx=ch-'a';
			if(idx<0 || idx>25) continue;
			if(cur.child[idx]==null)
			{
				cur.child[idx]=new Tire();
			}
			cur=cur.child[idx];
			cur.ch=ch;
		}
		cur.count++;
	}
	
	/**
	 * 在字典树查询字符串
	 * @param root
	 * @param str
	 * @return
	 */
	public static int find(final Tire root, String str)
	{
		Tire cur=root;
		for(char ch:str.toCharArray())
		{
			int idx=ch-'a';
			if(idx<0 || idx>25) continue;
			if(cur.child[idx]==null)
			{
				return -1;
			}
			cur=cur.child[idx];
		}
		return cur.count;
	}

}












