package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dataType.Gram2;
import dataType.Gram3;
import dataType.Gram4;
import dataType.Gram5;

public class merge {
	
	/**
	 * ªÒ»°—µ¡∑ºØ
	 * @param args
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException {
		File file = new File("C:\\Users\\yangxuan\\workspace\\parse\\prep.txt");	
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String [] string={"above","about","across","after","against","along","amidst","among","around","as","at","before","behind","beneath","below","beside","besides","between","beyond","but","buy","concerning","despite","down","during","except","excepting","for","from","in","inside","into","like","near","of","off","on","onto","outof","outside","over","past","round","since","than","through","till","to","toward","under","until","up","upon","with","within","without"};
		BufferedWriter writer =new BufferedWriter(new FileWriter(new File("C:\\Users\\yangxuan\\workspace\\parse\\test")));
		int count=0;
		try
		{
		 String  str=null;
		 boolean flag=false;
		 while((str = reader.readLine())!=null)
		 {
			 flag=false;
			 String [] array=str.split(" ");
			 for(int j=0;j<array.length;j++)
			 {
				 if(flag==true) break;
				 for(int i=0;i<56;i++)
				 {
				    if(array[j].equals(string[i])==true)
				    {
				    	 writer.write(str);
				    	 flag=true;
				    	 writer.newLine();
				    	 count++;
				    	 break;
				    }
				 }
			 }
			 if(count==500) break;
		 }
		 reader.close();
		 writer.close();
	    }
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("acccccccccccccccccc");
	}
}
