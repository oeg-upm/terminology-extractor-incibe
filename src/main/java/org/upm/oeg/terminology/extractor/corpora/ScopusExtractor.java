/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;


import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.BufferedWriter;
import java.io.File;
import org.apache.log4j.Logger;
import static org.upm.oeg.terminology.extractor.corpora.CoppaExtractor.logger;

/**
 *
 * @author pcalleja
 */
public class ScopusExtractor extends Extractor{
    
    
    final static Logger logger = Logger.getLogger(ScopusExtractor.class); 
    
    public static void main (String [] args) {
    
    
        String DataPath = "";
        String CorpusPath = "";
        
        createCorpus(DataPath,CorpusPath);
        
    
    }
    
    
    
    public static void createCorpus(String DataPath, String CorpusPath){
    
        File folder = new File("C:\\Users\\pcalleja\\Desktop\\ScopusDump");
        for(File f: folder.listFiles()){
        
            
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                String text = parseFile(f);
                if(text!=null){
                 createFile(f.getName()+".txt", CorpusPath, text);
                 }
            }
            
        }

    }
    
    public static String parseFile(File xmlFile){
    
            SAXBuilder builder = new SAXBuilder();
           
            String Name= xmlFile.getName();

	       try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            Element HeadElement2 = (Element) rootNode.getChildren().get(1);
            Element HeadElement = HeadElement2.getChild("item").getChild("bibrecord").getChild("head");

            List list = HeadElement.getChild("enhancement").getChild("classificationgroup").getChildren("classifications");

            boolean pass = false;
            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                if (node.getAttribute("type").getValue().equals("ASJC")) {

                    List elemClassi = node.getChildren("classification");

                    for (int j = 0; j < elemClassi.size(); j++) {
                        Element node2 = (Element) elemClassi.get(j);
                        String code = node2.getValue();
                        if (code.contains("1705")) {
                            pass = true;
                        }

                        if (code.contains("1706")) {
                            pass = true;
                        }

                    }
                }

            }
                
                
                
                if(pass==false){
                    return null;
                }
               List listA = HeadElement.getChild("abstracts").getChildren("abstract");
               String Text = (((Element) listA.get(0)).getValue());
               return Text;
                
               
	  } catch (Exception io) {
              logger.error("Failed parsing "+xmlFile.getName());
             
	  }
          return null;
    
    }
    
    
     
   
    
    
    
    
    
}
