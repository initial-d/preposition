package parse;

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataType.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class Test {

	static int tp=0,fn=0,fp=0,tn=0;
	static int even=0;
	
	/**
	 * 测试函数
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\yangxuan\\workspace\\parse\\500.txt");	
		BufferedReader reader = new BufferedReader(new FileReader(file));
		LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz");
		Run run=new Run();
		//run.CreateTireTree();
		run.loadTree();
		try {
			String temp=null;
			String first=null;
			String second=null;
			while((temp = reader.readLine())!=null)
			{
				Pattern p = Pattern.compile("[.,\"\\?!:;']");// 增加对应的标点  
			    Matcher m = p.matcher(temp);  
			    String s = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
				even++;
				System.out.println(even);
				if(even%2==1){ first=s;System.out.println(s);}
				second=first;
				GramBag  newBag=StanfordParser.getGram(s,lp);
				if(Find(s,newBag,run)==true)
				{
					if(even%2==1) tp++;
					else fp++;
				}
				else
				{
					if(even%2==1) fn++;
					else tn++;
					Revise(s,second,lp,run,newBag);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("500个测试句子，其中250个是为正确的，250个是错误的！");
		System.out.println("TP= "+tp+"  FN= "+fn+" FP= "+fp+"  TN= "+tn);
		double accuracy = (tp+tn)*1.0/(tp+tn+fn+fp);
		double recall = tp*1.0/(tp+fn);
		double pecision=tp*1.0/(tp+fp);
		System.out.println("召回率是： "+ recall);
		System.out.println("正确率是： "+ accuracy);
	
	}
	
	/**
	 * 判断字符串中是否还有数词
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
	
	/**
	 * 查询待检测的句子
	 * @param temp
	 * @param gBag
	 * @param run
	 * @return
	 * @throws IOException
	 */
	public static boolean Find(String temp,GramBag gBag,Run run) throws IOException
	{
		boolean flag=false;
		int [] count=new int[6];
		int sum;
		HashMap <String ,String> hashmap=new HashMap<String,String>();
		sum=0;
		for(int i=0;i<gBag.Ls5.size();i++)
		{
			Gram5 gram5= gBag.Ls5.get(i);
			String str="";
			if(isNumeric(gram5.getNode1().word)==false)
			{
			    str+=gram5.getNode1().word;
			}
			if(isNumeric(gram5.getNode2().word)==false)
			{
			    str+=gram5.getNode2().word;
		    }
			if(isNumeric(gram5.getNode3().word)==false)
			{
			    str+=gram5.getNode3().word;
			}
			if(isNumeric(gram5.getNode4().word)==false)
			{
			    str+=gram5.getNode4().word;
			}
		   if(isNumeric(gram5.getNode5().word)==false)
			{
			    str+=gram5.getNode5().word;
			}
		   if(hashmap.get(str)==null){  
			   sum++;
			   if(run.tree5.find(run.tree5, str)!=-1)
		       {
		    	   count[5]++;
		       } 
			   hashmap.put(str,"1");
			}
		    
		}	
	    if(count[5]>0)
	    {
	    	flag=true; 
	    	System.out.println("句子正确没有语法错去！");
	    	return flag;
	    }
	    
	    sum=0;
		for(int i=0;i<gBag.Ls4.size();i++)
		{
			Gram4 gram4= gBag.Ls4.get(i);
			String str="";
			if(isNumeric(gram4.getNode1().word)==false)
			{
			    str+=gram4.getNode1().word;
			}
			if(isNumeric(gram4.getNode2().word)==false)
			{
			    str+=gram4.getNode2().word;
		    }
			if(isNumeric(gram4.getNode3().word)==false)
			{
			    str+=gram4.getNode3().word;
			}
			if(isNumeric(gram4.getNode4().word)==false)
			{
			    str+=gram4.getNode4().word;
			}
		
			if(hashmap.get(str)==null){  
				   sum++;
				   if(run.tree5.find(run.tree5, str)!=-1)
			       {
			    	   count[4]++;
			       } 
				   hashmap.put(str,"1");
			}
		}			
		if(count[4]>0)
	    {
	    	flag=true; 
	    	System.out.println("句子正确没有语法错去！");
	    	return flag;
	    }
		sum=0;
		for(int i=0;i<gBag.Ls3.size();i++)
		{
			Gram3 gram3= gBag.Ls3.get(i);
			String str="";
			if(isNumeric(gram3.getNode1().word)==false)
			{
			    str+=gram3.getNode1().word;
			}
			if(isNumeric(gram3.getNode2().word)==false)
			{
			    str+=gram3.getNode2().word;
		    }
			if(isNumeric(gram3.getNode3().word)==false)
			{
			    str+=gram3.getNode3().word;
			}
			if(hashmap.get(str)==null){  
				   sum++;
				   if(run.tree5.find(run.tree5, str)!=-1)
			       {
			    	   count[3]++;
			       } 
				   hashmap.put(str,"1");
				}
		}			
		if(count[3]>1 || count[3]>=sum)
	    {
	    	flag=true; 
	    	System.out.println("句子正确没有语法错去！");
	    	return flag;
	    }
		sum=0;
		for(int i=0;i<gBag.Ls2.size();i++)
		{
			Gram2 gram2= gBag.Ls2.get(i);
			String str="";
			if(isNumeric(gram2.getNode1().word)==false)
			{
			    str+=gram2.getNode1().word;
			}
			if(isNumeric(gram2.getNode2().word)==false)
			{
			    str+=gram2.getNode2().word;
		    }
			if(hashmap.get(str)==null){
				  sum++;
				   if(run.tree5.find(run.tree5, str)!=-1)
			       {
			    	   count[5]++;
			       } 
				   hashmap.put(str, "1");
			}
		}	
		if(count[2]>1 || count[2]>=sum)
	    {
	    	flag=true; 
	    	System.out.println("句子正确没有语法错去！");
	    	return flag;
	    }
		return flag;
		
	}
	
	
	/**
	 * 替换字符串中的介词
	 * @param temp
	 * @param second
	 * @param lp
	 * @param run
	 * @param gBag
	 * @throws IOException
	 */
	public static void  Revise(String temp,String second,LexicalizedParser lp,Run run,GramBag gBag) throws IOException
	{
		int count,sum,max=0,size;
		boolean success=false;
		File file = new File("E:\\工程实践\\Parse\\prep");	
		BufferedReader reader = new BufferedReader(new FileReader(file));
		HashMap<String,Integer> string=StanfordParser.Replace(temp, lp);
		if(string==null){ 
			if(temp.equals(second)==true){tp++;}
			else tp++;
			System.out.println("句子正确不需要修改！"); 
			return;
		}
		String [] array = temp.split(" "); 
		String [] strrep={"above","about","across","after","against","along","amidst","among","around","as","at","before","behind","beneath","below","beside","besides","between","beyond","but","buy","concerning","despite","down","during","except","excepting","for","from","in","inside","into","like","near","of","off","on","onto","outof","outside","over","past","round","since","than","through","till","to","toward","under","until","up","upon","with","within","without"};
		
		try
		{
			String test=null;
			String rep=null;
			String last=null;
			String index=null;
			
			for(int j=0;j<array.length;j++)
			{
				if(string.get(array[j])==null)  continue;
				rep=array[j];
				
				for(int k=0;k<56;k++)
				{
					test=strrep[k];
					size=sum=0;
					index=test;
					
					HashMap <String ,String> hashmap=new HashMap<String,String>();
					for(int i=0;i<gBag.Ls5.size();i++)
					{
						Gram5 gram5= gBag.Ls5.get(i);
						String str="";
						if(isNumeric(gram5.getNode1().word)==false)
						{
							if(string.get(gram5.getNode1().word)==null)
							{
								str+=gram5.getNode1().word;
							}
							else str+=index;
						}
						if(isNumeric(gram5.getNode2().word)==false)
						{
							if(string.get(gram5.getNode2().word)==null)
							{
								str+=gram5.getNode2().word;
							}
							else str+=index;
					    }
						if(isNumeric(gram5.getNode3().word)==false)
						{
							if(string.get(gram5.getNode3().word)==null)
							{
								str+=gram5.getNode3().word;
							}
							else str+=index;
						}
						if(isNumeric(gram5.getNode4().word)==false)
						{
							if(string.get(gram5.getNode4().word)==null)
							{
								str+=gram5.getNode4().word;
							}
							else str+=index;
						}
						if(isNumeric(gram5.getNode5().word)==false)
						{
							if(string.get(gram5.getNode5().word)==null)
							{
								str+=gram5.getNode5().word;
							}
							else str+=index;
						}
						if(hashmap.get(str)==null)
						{
							if((count=run.tree5.find(run.tree5, str))!=-1)
							{
								sum+=count;
							}
							hashmap.put(str, "1");
							size++;
						}
					}	
					if(sum>=size && size>0 && rep.equals(index)==false && success==false) 
					{
						System.out.println(temp);
						System.out.println("句子修改之后正确的是： ");
						System.out.println(temp.replaceAll(rep, index));
						success=true;
						break;
					}
					size=sum=0;
					for(int i=0;i<gBag.Ls4.size();i++)
					{
						Gram4 gram4= gBag.Ls4.get(i);
						String str="";
						if(isNumeric(gram4.getNode1().word)==false)
						{
						    str+=gram4.getNode1().word;
						}
						if(isNumeric(gram4.getNode2().word)==false)
						{
						    str+=gram4.getNode2().word;
					    }
						if(isNumeric(gram4.getNode3().word)==false)
						{
						    str+=gram4.getNode3().word;
						}
						if(isNumeric(gram4.getNode4().word)==false)
						{
						    str+=gram4.getNode4().word;
						}
						str=str.replaceAll(rep,test);
						if(hashmap.get(str)==null)
						{
							if((count=run.tree5.find(run.tree5, str))!=-1)
							{
								sum+=count;
							}
							hashmap.put(str, "1");
							size++;
						}
					}	
					if(sum>=size && size>0 && rep.equals(index)==false&& success==false) 
					{
						System.out.println(temp);
						System.out.println("句子修改之后正确的是： ");
						System.out.println(temp.replaceAll(rep, index));
						success=true;
						break;
					}
					size=sum=0;
					for(int i=0;i<gBag.Ls3.size();i++)
					{
						Gram3 gram3= gBag.Ls3.get(i);
						String str="";
						if(isNumeric(gram3.getNode1().word)==false)
						{
						    str+=gram3.getNode1().word;
						}
						if(isNumeric(gram3.getNode2().word)==false)
						{
						    str+=gram3.getNode2().word;
					    }
						if(isNumeric(gram3.getNode3().word)==false)
						{
						    str+=gram3.getNode3().word;
						}
						str=str.replaceAll(rep,test);
						if(hashmap.get(str)==null)
						{
							if((count=run.tree5.find(run.tree5, str))!=-1)
							{
								sum+=count;
							}
							hashmap.put(str, "1");
							size++;
						}
					}	
					if(sum>=size && size>0 && rep.equals(index)==false && success==false ) 
					{
						System.out.println(temp);
						System.out.println("句子修改之后正确的是： ");
						System.out.println(temp.replaceAll(rep, index));
						success=true;
						break;
					}
					size=sum=0;
					for(int i=0;i<gBag.Ls2.size();i++)
					{
						Gram2 gram2= gBag.Ls2.get(i);
						String str="";
						if(isNumeric(gram2.getNode1().word)==false)
						{
						    str+=gram2.getNode1().word;
						}
						if(isNumeric(gram2.getNode2().word)==false)
						{
						    str+=gram2.getNode2().word;
					    }
						str=str.replaceAll(rep,test);
						if(hashmap.get(str)==null)
						{
							if((count=run.tree5.find(run.tree5, str))!=-1)
							{
								sum+=count;
							}
							hashmap.put(str, "1");
							size++;
						}
					}	
					if(sum>max && size>0) 
					{
						max=sum;
						last=test;
					}	
				}
				if(last==null) continue;
				if(success==false)
				{
					System.out.println(temp);
					System.out.println("句子修改之后正确的是： ");
					System.out.println(temp.replaceAll(rep, index));
					success=true;
					break;
				}
			}
		}
		finally{
			reader.close();
		}
			
    }
}
