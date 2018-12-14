/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import java.io.BufferedWriter;
import java.io.File;
import org.apache.log4j.Logger;
import static org.upm.oeg.terminology.extractor.corpora.CoppaExtractor.logger;
/**
 *
 * @author pcalleja
 */
public class CordisH2020Extractor extends Extractor{
    
    
    final static Logger logger = Logger.getLogger(CordisH2020Extractor.class); 
    
    public static void main (String [] args){
    
    
        String DataPath="";
        String CorpusPath="";
        createCorpus(DataPath,CorpusPath);
        
    
    }
    
    
    
    public static void createCorpus(String DataPath, String CopusPath){
    
    File folder = new File(DataPath);
        
        for(File f: folder.listFiles()){
        
           
            
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                String text= processFile(f);
                if(text!=null){
                 createFile(f.getName()+".txt", CopusPath, text);
                 }
            }
            
        }

    }
    
    public static String processFile(File xmlFile){

        System.out.println(xmlFile.getName());
        SAXBuilder builder = new SAXBuilder();

        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            String Text = "";

            List list2 = rootNode.getChildren();

            for (int j = 0; j < list2.size(); j++) {
                Element node2 = (Element) list2.get(j);
                if (node2.getName().equals("objective")) {
                    Text = node2.getValue();
                }

            }

            return Text;

        } catch (Exception io) {
            logger.error("Failed parsing " + xmlFile.getName());
        }

        return null;

    }
    
    
    
   
    
    
    
    
    
    
    
    
}
