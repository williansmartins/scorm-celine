package com.pocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GenerateHTML {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		GenerateHtml(null);
	}

	public static void GenerateHtml(ScormBase scormbase) throws FileNotFoundException,
			UnsupportedEncodingException, IOException {
		PrintWriter writer = new PrintWriter("C:\\outputzip\\index.html", "UTF-8");
		
		File cabecalho = new File("C:\\base\\cabecalho.html");
		FileInputStream fis = null;
		fis = new FileInputStream(cabecalho);
		
		int content;
		while ((content = fis.read()) != -1) {
			writer.print((char) content);
		}
		
		writer.println("");
		for (int i = 1; i < scormbase.titles.size(); i++) {
			writer.println("<li><a id='menu"+i+"'  data-previous-menu='menu"+(i-1)+"' data-next-menu='menu"+(i+1)+"' data-page='scenario.html?topico="+i+"' onclick='new APIContntScorm().goToPage("+i+", this)'  target='frameTarget'>" + scormbase.titles.get(i) + "</a></li>");
		}
		writer.println("	<input type='hidden' id='totalPage' value='"+ (scormbase.titles.size()-1) +"'/>");

		File rodape = new File("C:\\base\\rodape.html");
		fis = new FileInputStream(rodape);
		
		content = 0;
		while ((content = fis.read()) != -1) {
			writer.print((char) content);
		}
		
		writer.close();
		fis.close();
		
		
		File cssSrcFolder = new File("c:\\base\\css");
    	File cssDestFolder = new File("c:\\outputzip\\css");

    	File jsSrcFolder = new File("c:\\base\\js");
    	File jsDestFolder = new File("c:\\outputzip\\js");
 
    	//make sure source exists
    	if(!cssSrcFolder.exists()){
 
           System.out.println("Directory does not exist.");
           //just exit
           System.exit(0);
 
        }else{
 
           try{
        	copyFolder(cssSrcFolder,cssDestFolder);
           }catch(IOException e){
        	e.printStackTrace();
        	//error, just exit
                System.exit(0);
           }
        }
    	
    	//make sure source exists
    	if(!jsSrcFolder.exists()){
 
           System.out.println("Directory does not exist.");
           //just exit
           System.exit(0);
 
        }else{
 
           try{
        	copyFolder(jsSrcFolder,jsDestFolder);
           }catch(IOException e){
        	e.printStackTrace();
        	//error, just exit
                System.exit(0);
           }
        }    	
 
    	System.out.println("Done");
	}
	
	public static void copyFolder(File src, File dest)
	    	throws IOException{
	 
	    	if(src.isDirectory()){
	 
	    		//if directory not exists, create it
	    		if(!dest.exists()){
	    		   dest.mkdir();
	    		   System.out.println("Directory copied from " 
	                              + src + "  to " + dest);
	    		}
	 
	    		//list all the directory contents
	    		String files[] = src.list();
	 
	    		for (String file : files) {
	    		   //construct the src and dest file structure
	    		   File srcFile = new File(src, file);
	    		   File destFile = new File(dest, file);
	    		   //recursive copy
	    		   copyFolder(srcFile,destFile);
	    		}
	 
	    	}else{
	    		//if file, then copy it
	    		//Use bytes stream to support all file types
	    		InputStream in = new FileInputStream(src);
	    	        OutputStream out = new FileOutputStream(dest); 
	 
	    	        byte[] buffer = new byte[1024];
	 
	    	        int length;
	    	        //copy the file content in bytes 
	    	        while ((length = in.read(buffer)) > 0){
	    	    	   out.write(buffer, 0, length);
	    	        }
	 
	    	        in.close();
	    	        out.close();
	    	        System.out.println("File copied from " + src + " to " + dest);
	    	}
	    }
}
