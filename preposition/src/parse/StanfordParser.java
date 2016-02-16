package parse;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
//import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import dataType.*;


public class StanfordParser {
	

/**Just for testing
 * @param args
 * @throws IOException 
 * @throws InvalidFormatException 
 */
	public static void main(String[] args)throws IOException {
	   //相对路径即可
		LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz");
		String sent2 = "tom and his brother like to deal with problem";
		Pattern p = Pattern.compile("[.,\"\\?!:';]");// 增加对应的标点  
	    Matcher m = p.matcher(sent2);  
	    String s = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
	    demoAPI(lp,s);
	    getGram(s,lp);
	  }
	

	/**
	 * I design the method to get gram2,gram3,gram4,gram5
	 * @param str
	 * @param lp
	 * @return
	 * @throws IOException
	 */
	public static GramBag getGram(String str,LexicalizedParser lp)throws IOException
	{
		TokenizerFactory tokenizerFactory =
				PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		List rawWords2 =
			      tokenizerFactory.getTokenizer(new StringReader(str)).tokenize();
		Tree parse = lp.apply(rawWords2);
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	    List<TypedDependency> tdl =  (List) gs.typedDependenciesCCprocessed();
	    GramBag gBag=new GramBag();
	    HashMap<String,String> hashmap = new HashMap<String,String>();
	    ShallowParser swParser = ShallowParser.getInstance();
		hashmap=swParser.getMap(str);
		
		/**
		 * 从依赖关系提取介词二元组
		 */
	    for(TypedDependency tdl1:tdl){
		    
		       String reln=tdl1.reln().toString();
		       
		       if("root"==reln)
		       {
		    	   continue;
		       }
		       Gram2 temp=GramBag.getGram2((tdl1.gov().toString()),(tdl1.dep().toString()));
		       Pattern p = Pattern.compile("[.,\"\\?!:';]");// 增加对应的标点  
			   Matcher m1 = p.matcher(temp.getNode1().word);  
			   temp.getNode1().word = m1.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
			   Matcher m2 = p.matcher(temp.getNode2().word);  
			   temp.getNode2().word = m2.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
			   if(isPrep(temp.getNode1().word, hashmap)==true || isPrep( temp.getNode2().word,hashmap)==true)
			   {
				   temp.isPrep=true;
			   }
		       if(reln.contains("_"))
		       {
		    	   
		    	   int index=reln.indexOf((int)'_');
		    	   String str1=reln.substring(0, index);
		    	   String str2=reln.substring(index+1);
		    	   node node_temp=new node(str2,str1);
		    	   if(str1.equals("prep")==true || str1.equals("prepc")==true) 
		    	   {
		    		   String ss=node_temp.word; 
			    	   int idx= ss.indexOf((int)'_');
			    	   String s1="";
				       if(idx!=-1){ s1+=ss.substring(0,idx);  s1+=ss.substring(idx+1); node_temp.word=s1;}
				       idx= s1.indexOf((int)'_');
				       String s2="";
				       if(idx!=-1){ s2+=s1.substring(0,idx);  s2+=s1.substring(idx+1); node_temp.word=s2;}
		    	   }
		    	   Gram2 gm1=new Gram2(temp.getNode1(),node_temp);
		    	   if(isPrep(gm1.getNode1().word, hashmap)==true || isPrep( node_temp.word,hashmap)==true)
				   {
					   gm1.isPrep=true;
				   }
		    	   Gram2 gm2=new Gram2(node_temp,temp.getNode2());
		    	   if(isPrep(gm2.getNode2().word, hashmap)==true || isPrep( node_temp.word,hashmap)==true)
				   {
					  gm2.isPrep=true;
				   }   	   
		    	   gBag.Ls2.add(gm1);
		    	   gBag.Ls2.add(gm2);
		       }
		       else
		       {
		    	   if(temp.getNode1().word.equals("")==true || temp.getNode2().word.equals("")==true) continue;
		    	   gBag.Ls2.add(temp); 
		       }		   
		      }
	    

        /**
         * 二元组合并成三元组
         */
	    for(int i=0;i<gBag.Ls2.size();i++)
	    {
	    	for(int j=0;j<gBag.Ls2.size();j++)
	    	{
	    		
	    		Gram3 temp=null;
	    		if(i!=j && null!=(temp=gBag.Ls2.get(i).mergeGram2(gBag.Ls2.get(j))) )
	    		{
	    			if(gBag.Ls2.get(i).isPrep==true || gBag.Ls2.get(j).isPrep==true) temp.isPrep=true;
	    			gBag.Ls3.add(temp);
	    		}
	    	}	    	
	    }
	    

        /**
         * 二三元组合并成四元组
         */
	    for(int i=0;i<gBag.Ls2.size();i++)
	    {
	    	for(int j=0;j<gBag.Ls3.size();j++)
	    	{
	    		Gram4 temp=null;
	    		if(null!=(temp=gBag.Ls2.get(i).mergeGram3(gBag.Ls3.get(j))) )
	    		{
	    			if(gBag.Ls2.get(i).isPrep==true || gBag.Ls3.get(j).isPrep==true) temp.isPrep=true;
	    			gBag.Ls4.add(temp);
	    		}
	    		if(null!=(temp=gBag.Ls3.get(j).mergeGram2(gBag.Ls2.get(i))))
	    		{
	    			if(gBag.Ls3.get(j).isPrep==true || gBag.Ls2.get(i).isPrep==true) temp.isPrep=true;
	    			gBag.Ls4.add(temp);
	    		}
	    	 }
	     }
	    
	    

        /**
         * 二四元组合并成五元组
         */
	    for(int i=0;i<gBag.Ls2.size();i++)
	    {
	    	for(int j=0;j<gBag.Ls4.size();j++)
	    	{
	    		Gram5 temp=null;
	    		if(null!=(temp=gBag.Ls2.get(i).mergeGram4(gBag.Ls4.get(j))))
	    		{
	    			if(gBag.Ls2.get(i).isPrep==true || gBag.Ls4.get(j).isPrep==true) temp.isPrep=true;
	    			gBag.Ls5.add(temp);
	    		}
	    		if(null!=(temp=gBag.Ls4.get(j).mergeGram2(gBag.Ls2.get(i))))
	    		{
	    			if(gBag.Ls4.get(j).isPrep==true || gBag.Ls2.get(i).isPrep==true) temp.isPrep=true;
	    			gBag.Ls5.add(temp);
	    		}
	    	}
	    }
	    
	    
	    /**
	     * 三元组合并成五元组
	     */
	    for(int i=0;i<gBag.Ls3.size();i++)
	    {
	    	for(int j=0;j<gBag.Ls3.size();j++)
	    	{
	    		Gram5 temp=null;
	    		if(i!=j && null!=(temp=gBag.Ls3.get(i).mergeGram3(gBag.Ls3.get(j))))
	    		{
	    			if(gBag.Ls3.get(i).isPrep==true || gBag.Ls3.get(j).isPrep==true) temp.isPrep=true;
	    			gBag.Ls5.add(temp);
	    		}
	    	}
	    }
	    
        /**
         * 筛选二元组中的介词元祖
         */
	    GramBag newBag=new GramBag();
	    for(int i=0;i<gBag.Ls2.size();i++)
	    {
	       if(gBag.Ls2.get(i).isPrep==true)
	       {
	          newBag.Ls2.add(gBag.Ls2.get(i));
	       }
	    }
	   
	    /**
         * 筛选三元组中的介词元祖
         */
	    for(int i=0;i<gBag.Ls3.size();i++)
	    {
	       if(gBag.Ls3.get(i).isPrep==true)
	       {
	          newBag.Ls3.add(gBag.Ls3.get(i));
	       }
	    }
	  
	    /**
         * 筛选四元组中的介词元祖
         */
	    for(int i=0;i<gBag.Ls4.size();i++)
	    {
	       if(gBag.Ls4.get(i).isPrep==true)
	       {
	          newBag.Ls4.add(gBag.Ls4.get(i));
	       }
	    }
	   
	    /**
         * 筛选五元组中的介词元祖
         */
	    for(int i=0;i<gBag.Ls5.size();i++)
	    {
	       if(gBag.Ls5.get(i).isPrep==true)
	       {
	          newBag.Ls5.add(gBag.Ls5.get(i));
	       }
	    }
	   
	   /*
	    System.out.println("二元组");
	    for(int i=0;i<newBag.Ls2.size();i++)
	    {
	    	Gram2 temp=newBag.Ls2.get(i);
	    	System.out.println("("+temp.getNode1().word+temp.getNode2().word+")");
	    }		
	    System.out.println();
	    
	    System.out.println("三元组");
	    for(int i=0;i<newBag.Ls3.size();i++)
	    {
	    	Gram3 temp=newBag.Ls3.get(i);
	    	System.out.println("("+temp.getNode1().word+temp.getNode2().word+temp.getNode3().word+")");
	    }		
	   
	    System.out.println();
	    System.out.println("四元组");
	    for(int i=0;i<newBag.Ls4.size();i++)
	    {
	    	Gram4 temp=newBag.Ls4.get(i);
	    	System.out.println("("+temp.getNode1().word+temp.getNode2().word+temp.getNode3().word+temp.getNode4().word+")");
	    }		
	    System.out.println();
	    
	    System.out.println("五元组");
	    for(int i=0;i<newBag.Ls5.size();i++)
	    {
	    	Gram5 temp=newBag.Ls5.get(i);
	    	System.out.println("("+temp.getNode1().word+temp.getNode2().word+temp.getNode3().word+temp.getNode4().word+temp.getNode5().word+")");
	    }	
	    System.out.println();*/
	    return newBag;	
	   
	}
	
	  /**
	   * 调用API的demo
	   * @param lp
	   * @param str
	   */
	  public static void demoAPI(LexicalizedParser lp,String str) {
	    
	    String sent2 = str;
	    TokenizerFactory tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
	    List rawWords2 =tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
	    Tree parse = lp.apply(rawWords2);
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	    List tdl =  (List) gs.typedDependenciesCCprocessed();
	    System.out.println(tdl);
	    System.out.println("依赖关系");
	    for(TypedDependency tdl1:(List<TypedDependency>)tdl){
	       System.out.println(tdl1);       //例如输出完整的：nsubj(sentence-4, This-1)
	       System.out.print(tdl1.gov()+"   "); //例如输出支配地位的：sentence-4
	       System.out.println(tdl1.dep()); //例如输出从属地位的：This-1
	       System.out.println(tdl1.reln());//例如输出关系：nsubj
	       System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	       
	      }
	    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    ////////////输出第三块
	    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
	    tp.printTree(parse);
	  }
	  
	  /**
	   * 介词存储在hashmap中
	   * @param str
	   * @param lp
	   * @return
	   * @throws IOException
	   */
	  public static HashMap<String,Integer> Replace(String str,LexicalizedParser lp)  throws IOException
	  {
		  HashMap<String,Integer> replace = new HashMap<String,Integer>();
		  HashMap<String,String> hashmap = new HashMap<String,String>();
		  ShallowParser swParser = ShallowParser.getInstance();
	      hashmap=swParser.getMap(str);
	      String [] string = str.split(" "); 
          for(int i=0;i<string.length;i++)
          {
        	  if("IN".equals(hashmap.get(string[i])) || "TO".equals(hashmap.get(string[i]))){
        		  replace.put(string[i], i);
        	  }
        	  
          }
		  return replace;
      }
	  
	  /**
	   * 判断
	   * @param str
	   * @param hashmap
	   * @return
	   */
	  public static boolean isPrep(String str,HashMap hashmap)
	  {
		  if("IN".equals(hashmap.get(str))==true) return true;
		  if("TO".equals(hashmap.get(str))==true) return true;
		  else return false;
	  }
}