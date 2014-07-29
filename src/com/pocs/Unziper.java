package com.pocs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Unziper {
	
	private static final int TAMANHO_BUFFER = 2048;
	private static final String INPUT_ZIP_FILE = "C:\\SCORM2004.4.MBCE.1.0.CP.zip";
    private static final String OUTPUT_FOLDER = "C:\\outputzip";
    
	
	public static void main(String[] args) throws ZipException, IOException, ParserConfigurationException, SAXException {
		//descompactar o zip
		File zipFile = new File(INPUT_ZIP_FILE);
		File directory = new File(OUTPUT_FOLDER);
		new Unziper().extractZipFile(zipFile, directory);
		
	}

	public void extractZipFile( File zipFile, File directory) throws ZipException, IOException {  
		ZipFile zip = null;  
		File file = null;  
		InputStream is = null;  
		OutputStream os = null;  
		byte[] buffer = new byte[TAMANHO_BUFFER];  
		
		try {  
			
			//cria diretório informado, caso não exista  
			if( !directory.exists() ) {  
				directory.mkdirs();  
			} 
			
			if( !directory.exists() || !directory.isDirectory() ) {  
				throw new IOException("Informe um diretório válido");  
			}  
			
			zip = new ZipFile( zipFile );  
			Enumeration<?> e = zip.entries();  
			while( e.hasMoreElements() ) {  
				ZipEntry input = (ZipEntry) e.nextElement();  
				file = new File( directory, input.getName() );  
				//if directory dont exist, create the structure
				//and jump for next input
				if( input.isDirectory() && !file.exists() ) {  
					file.mkdirs();  
					continue;  
				}  
				// if the structure of directories dont exist, create
				if( !file.getParentFile().exists() ) {  
					file.getParentFile().mkdirs();  
				}  
				try {  
					//read the zip file and save in disc  
					is = zip.getInputStream( input );  
					os = new FileOutputStream( file );  
					int bytesLidos = 0;  
					
					if( is == null ) {  
						throw new ZipException("Erro ao ler a entrada do zip: " + input.getName());  
					}  
					while( (bytesLidos = is.read( buffer )) > 0 ) {  
						os.write( buffer, 0, bytesLidos );  
					}  
				} finally {  
					if( is != null ) {  
						try {  
							is.close();  
						} catch( Exception ex ) {}  
					}  
					if( os != null ) {  
						try {  
							os.close();  
						} catch( Exception ex ) {}  
					}  
				}  
			}  
		} finally {  
			if( zip != null ) {  
				try {  
					zip.close();  
				} catch( Exception e ) {}  
			}  
		}  
	}  

}
