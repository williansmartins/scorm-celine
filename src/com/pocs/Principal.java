package com.pocs;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader;
import br.univali.celine.scorm.model.cam.ContentPackageReaderFactory;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Organization;

public class Principal {
	
//	private static final String INPUT_ZIP_FILE = "C:\\SCORM2004.4.MBCE.1.0.CP.zip";
	private static final String INPUT_ZIP_FILE = "C:\\scorm-excel.zip";
    private static final String OUTPUT_FOLDER = "C:\\outputzip";
    
	public static void main(String[] args) throws Exception {
		//UNZIP
		File zipFile = new File(INPUT_ZIP_FILE);
		File directory = new File(OUTPUT_FOLDER);
		
		//DELETE FILES
		FileUtils.deleteDirectory(directory);
		
		new Unziper().extractZipFile(zipFile, directory);
		
		//READXML
		//ScormBase scormbase = new ReadXMLFile().readXML();
		
		ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader("c:\\outputzip\\imsmanifest.xml");
		ContentPackage cp = cpr.read("c:\\outputzip\\imsmanifest.xml");
		Organization org = cp.getOrganizations().getDefaultOrganization();
		Iterator<Item> items = org.getChildren();
		
		ScormBase scormbase = new ScormBase(); 
		scormbase.titles = new ArrayList<String>();
		
		while (items.hasNext()) {
		  Item item = items.next();
		  Iterator<Item> subItems = item.getChildren();
			
			while (subItems.hasNext()) {
				String s = subItems.next().getTitle();
//				System.out.println("   " + subItems.next().getTitle());
				scormbase.titles.add( s );
			}
//			System.out.println(">>>" + item.getTitle());
		  scormbase.titles.add(item.getTitle());
		}
		
		//GENERATEHTML
		new GenerateHTML().GenerateHtml(scormbase);
	}
}
