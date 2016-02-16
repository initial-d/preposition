package parse;

public class Tire implements java.io.Serializable {
	
	Tire [] child;  //�ַ����ӽڵ�
	char ch;  //��ǰ�ڵ��ַ�
	int count; //�ڵ������
	
	/**
	 * �ֵ������캯��
	 */
	public Tire()
	{
	  child=new Tire[26];
	  count=0;
	}
	
	/**
	 * �ַ��������ֵ���
	 * @param root
	 * @param str
	 */
	public static void insert(final Tire root,String str)
	{
		Tire cur=root;
		//String s=str.replace(str.charAt(0), (char)(str.charAt(0)+32));
		//��һ�����ʴ�д���Сд
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
	 * ���ֵ�����ѯ�ַ���
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












