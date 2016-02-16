package parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**a Shallow Parser based on opennlp
 * @author yangliu
 * @blog http://blog.csdn.net/yangliuy
 * @mail yang.liu@pku.edu.cn
 */

public class ShallowParser {
	
	private static ShallowParser instance = null ;
	private static POSModel model;
	private static ChunkerModel cModel ;
	
	//Singleton pattern
	public static ShallowParser getInstance() throws InvalidFormatException, IOException{
		if(ShallowParser.instance == null){
			POSModel model = new POSModelLoader().load(new File("E:\\工程实践\\Parser\\src\\en-pos-maxent.bin"));
			InputStream is = new FileInputStream("E:\\工程实践\\Parser\\src\\en-chunker.bin");
			ChunkerModel cModel = new ChunkerModel(is);
			ShallowParser.instance = new ShallowParser(model, cModel);
		}
		return ShallowParser.instance;
	}
	
	/**
	 * 构造函数
	 * @param model
	 * @param cModel
	 */
	public ShallowParser(POSModel model, ChunkerModel cModel){
		ShallowParser.model = model;
		ShallowParser.cModel = cModel;
		
	}
	
	 /** A shallow Parser, chunk a sentence and return a map for the phrase
	  *  labels of words <wordsIndex, phraseLabel>
	 *   Notice: There should be " " BEFORE and after ",", " ","(",")" etc.
	 * @param input The input sentence
	 * @param model The POSModel of the chunk
	 * @param cModel The ChunkerModel of the chunk
	 * @return  HashMap<Integer,String>
	 */
	 public HashMap<Integer,String> chunk(String input) throws IOException { 	
			PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
			POSTaggerME tagger = new POSTaggerME(model);
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					new StringReader(input));			
			perfMon.start();
			
			String line;
			String whitespaceTokenizerLine[] = null; 
			String[] tags = null;
			
			while ((line = lineStream.read()) != null) {
				whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
						.tokenize(line);
				tags = tagger.tag(whitespaceTokenizerLine);	 
				POSSample posTags = new POSSample(whitespaceTokenizerLine, tags);
				
				String origin=posTags.toString();
				Pattern p = Pattern.compile("[.,\"\\?!:']");// 增加对应的标点  
			    Matcher m = p.matcher(origin);  
			    String s = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
				//System.out.println(s);
				String[] temp=s.split(" ");
				HashMap<String,String> hmap = new HashMap<String,String>();
				for(String entry: temp)
				{
					if(!entry.contentEquals("._."))
					{
						System.out.print(entry);
						int index=entry.indexOf((int)'_');
						String str=entry.substring(index+1);
						String word=entry.substring(0,index);
						hmap.put(word, str);
						System.out.println(": "+word+"  "+hmap.get(word));	
					}						
				}
				perfMon.incrementCounter();
			}
			
			ChunkerME chunkerME = new ChunkerME(cModel);
			String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
			
			HashMap<Integer,String> phraseLablesMap = new HashMap<Integer, String>();
			Integer wordCount = 1;
			Integer phLableCount = 0;
			for (String phLable : result){
				if(phLable.equals("O")) phLable += "-Punctuation"; //The phLable of the last word is OP
				if(phLable.split("-")[0].equals("B")) phLableCount++;
				phLable = phLable.split("-")[1] + phLableCount;
				System.out.println(wordCount + ":" + phLable);
				phraseLablesMap.put(wordCount, phLable);
				wordCount++;
			}
			return phraseLablesMap;
		}
	 
	 /** Just for testing
		 * @param tdl Typed Dependency List
		 * @return WDTreeNode root of WDTree
		 */
	 public static void main(String[] args) throws IOException {
		 //Notice: There should be " " BEFORE and after ",", " ","(",")" etc.
		 String input = "japan won at argentina 1 to 0 . ";
		 System.out.println(input);
		 ShallowParser swParser = ShallowParser.getInstance();
		 System.out.println("-------------------------------------------------");
		 //swParser.chunk(input);
		 swParser.getMap(input);
	 }
	 
	 
	 /**************************************
	  *  I design a method to get HashMap of property of the English sentence!
	 * @throws IOException 
	  *  
	  *  
	  **************************************/
	 
	 public HashMap<String,String> getMap(String input) throws IOException
	 {
		 //HashMap<String,String> hashmap = new HashMap<String,String>();
		 PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
			POSTaggerME tagger = new POSTaggerME(model);
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					new StringReader(input));			
			perfMon.start();
			
			String line;
			String whitespaceTokenizerLine[] = null; 
			String[] tags = null;
			
			HashMap<String,String> hmap = new HashMap<String,String>();
			
			while ((line = lineStream.read()) != null) {
				whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
						.tokenize(line);
				tags = tagger.tag(whitespaceTokenizerLine);	 
				POSSample posTags = new POSSample(whitespaceTokenizerLine, tags);
				
				String origin=posTags.toString();
				Pattern p = Pattern.compile("[.,\"\\?!:';]");// 增加对应的标点  
			    Matcher m = p.matcher(origin);  
			    String s = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号  
				String[] temp=s.split(" ");
				for(String entry: temp)
				{
					if(!entry.contentEquals("._."))
					{
						int index=entry.indexOf((int)'_');
						String str=entry.substring(index+1);
						String word=entry.substring(0,index);
						hmap.put(word, str);	
					}						
				}
				perfMon.incrementCounter();
			}
			return hmap;
	 }
}
