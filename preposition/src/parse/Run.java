package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataType.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;

public class Run {
	
	Tire tree5;
  
	public Run()
	{
		tree5=new Tire();
	}
	
	/**
	 * 构造字典树
	 * @throws IOException
	 */
	public void CreateTireTree() throws IOException
	{
		// TODO Auto-generated method stub
		FileReader fr=new FileReader("E:\\工程实践\\Parser\\lala");
	    BufferedReader reader=new BufferedReader(fr);
	    LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz");
		int count=0;
		try {
			String temp=null;
			while((temp = reader.readLine())!=null)
			{
			    Pattern p = Pattern.compile("[.,\"\\?!:';]");// 增加对应的标点  
	            Matcher m = p.matcher(temp);  
	            String s = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
				GramBag  gBag=StanfordParser.getGram(s,lp);
				count++;
				HashMap<String,String> hashmap = new HashMap<String,String>();
				 for(int i=0;i<gBag.Ls2.size();i++)
				 {
					 String str="";
					 if(isNumeric(gBag.Ls2.get(i).getNode1().word)==false)
					 {
				        str+=gBag.Ls2.get(i).getNode1().word;
					 }
					 if(isNumeric(gBag.Ls2.get(i).getNode2().word)==false)
					 {
						 str+=gBag.Ls2.get(i).getNode2().word;
					 }
					 if(hashmap.get(str)==null){  Tire.insert(tree5, str);  hashmap.put(str, "1");
					 }
				     
				 }
				    
				    for(int i=0;i<gBag.Ls3.size();i++)
				    {
				    	 String str="";
				    	 if(isNumeric(gBag.Ls3.get(i).getNode1().word)==false)
						 {
				    		 str+=gBag.Ls3.get(i).getNode1().word;
						 }
				    	 if(isNumeric(gBag.Ls3.get(i).getNode2().word)==false)
						 {
				    		 str+=gBag.Ls3.get(i).getNode2().word;
						 }
				    	 if(isNumeric(gBag.Ls3.get(i).getNode3().word)==false)
						 {
				    		 str+=gBag.Ls3.get(i).getNode3().word;
						 }
				    	 if(hashmap.get(str)==null){  Tire.insert(tree5, str);  hashmap.put(str, "1");
				    	 }
				    }
				    for(int i=0;i<gBag.Ls4.size();i++)
				    {
				    	 String str="";
				    	 if(isNumeric(gBag.Ls4.get(i).getNode1().word)==false)
						 {
				    		 str+=gBag.Ls4.get(i).getNode1().word;
						 }
				    	 if(isNumeric(gBag.Ls4.get(i).getNode2().word)==false)
						 {
				    		 str+=gBag.Ls4.get(i).getNode2().word;
						 }
				    	 if(isNumeric(gBag.Ls4.get(i).getNode3().word)==false)
				         {
				    		 str+=gBag.Ls4.get(i).getNode3().word;
						 }
				    	 if(isNumeric(gBag.Ls4.get(i).getNode4().word)==false)
						 {
				    		 str+=gBag.Ls4.get(i).getNode4().word;
						 }
				    	 if(hashmap.get(str)==null){  Tire.insert(tree5, str);  hashmap.put(str,"1");
				    	 }
				    }
				    for(int i=0;i<gBag.Ls5.size();i++)
				    {
				    	 String str="";
				    	 if(isNumeric(gBag.Ls5.get(i).getNode1().word)==false)
						 {
				    		 str+=gBag.Ls5.get(i).getNode1().word;
						 }
				    	 if(isNumeric(gBag.Ls5.get(i).getNode2().word)==false)
						 {
				    		 str+=gBag.Ls5.get(i).getNode2().word;
						 }
				    	 if(isNumeric(gBag.Ls5.get(i).getNode3().word)==false)
						 {
				    		 str+=gBag.Ls5.get(i).getNode3().word;
						 }
				    	 if(isNumeric(gBag.Ls5.get(i).getNode4().word)==false)
						 {
				    		 str+=gBag.Ls5.get(i).getNode4().word;
						 }
				    	 if(isNumeric(gBag.Ls5.get(i).getNode5().word)==false)
						 {
				    		 str+=gBag.Ls5.get(i).getNode5().word;
						 }
				    	 if(hashmap.get(str)==null){  Tire.insert(tree5, str);  hashmap.put(str, "1");
				    
				    	 }
					     
				    }
				   System.out.println(count);
			}
			reader.close();
			System.out.println("字典树创建成功");
			
			//对象组序列化
			try(
					ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("gram5.txt"));)
					{
					    oos.writeObject(tree5);
					}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	
	}
	
	/**
	 * 字典树加载
	 */
	public void loadTree()
	{	
		Tire tree=null;
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("gram5.txt")))
		{
			tree5=(Tire)ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz");
		String str="japan won around argentina 1 to 0 .";
		//StanfordParser.getGram(str,lp);
		Run run=new Run();
		run.CreateTireTree();
		//run.loadTree();
	}
	
	/**
	 * 判断字符串是否是数词
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		  for (int i = str.length();--i>=0;)
		  {   
			  if (Character.isDigit(str.charAt(i)))
			  { 
				  return true; 
			  } 
		  } 
		  return false; 
	} 
}