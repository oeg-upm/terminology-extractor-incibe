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
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.jdom.Namespace;
import static org.upm.oeg.terminology.extractor.corpora.CoppaExtractor.logger;
/**
 *
 * @author pcalleja
 */
public class CordisFP7Extractor extends Extractor{
    
    
    final static String filter="SEC";
    
    final static Logger logger = Logger.getLogger(CordisFP7Extractor.class); 
    
    public static void main (String [] args){
    
    
        String pathDataDir="D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\CORDIS\\Data\\cordis-fp7";
        String pathCorpusDir="D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\CORDIS\\Corpus";
        createCorpus(pathDataDir,pathCorpusDir);
        
    
    }
    
    
    
    public static void createCorpus(String pathDataDir, String pathCorpusDir){
    
        File folder= new File (pathDataDir);
        
        for(File f: folder.listFiles()){
        
           
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                 String text= parseFile(f);
                 if(text!=null){
                 createFile(f.getName()+".txt", pathCorpusDir, text);
                 }
            }
            
        }

    }
    
    public static String parseFile(File xmlFile){
       
      
        SAXBuilder builder = new SAXBuilder();
        


        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            Namespace ns = rootNode.getNamespace();

            Element Form = rootNode.getChild("relations",ns).getChild("associations",ns);
            
            String abstracttext= rootNode.getChild("objective",ns).getValue();
            
            List<Element> elemList = Form.getChildren("programme",ns);
            for (Element elem : elemList) {

                String type = elem.getAttributeValue("type");
                
                
                if (type.equals("relatedSubProgramme")) {

                    Element contr = elem.getChild("relations",ns).getChild("categories",ns);
                    
                    List<Element> elemList2 = contr.getChildren("category",ns);
                    for(Element elem2 : elemList2){
                    
                        Element cat= elem2.getChild("code",ns);
                        
                        String code=cat.getValue();
                       
                        if(code.equals(filter)){
                            return abstracttext;
                            
                        }
                    
                    }
                    
                 

                    return null;

                }

            }

        } catch (Exception io) {
         
           logger.error("Failed parsing "+xmlFile.getName());
        }
        return null;
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
