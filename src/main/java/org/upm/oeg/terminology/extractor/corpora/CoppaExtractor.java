/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.BufferedWriter;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author pcalleja
 */
public class CoppaExtractor extends Extractor{
    
    
    final static Logger logger = Logger.getLogger(CoppaExtractor.class); 
    
    public static void main (String [] args){
    
    
        String pathDataDir="";
        String pathCorpusDir="";
        createCorpus(pathDataDir,pathCorpusDir);
        
        
    
    }
    
    
    
    public static void createCorpus(String PathData, String PathOutput){
    
        File folder= new File(PathData);
        
        for(File f: folder.listFiles()){
        
            if(f.isDirectory()){
                createCorpus(f.getAbsolutePath(),PathOutput);
            }
            
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                
                 String text= parseFile(f);
                 if(text!=null){
                 createFile(f.getName()+".txt", PathOutput, text);
                 }
                
            }
            
        }

    }
    
    public static String parseFile(File xmlFile){
    
            SAXBuilder builder = new SAXBuilder();

	  try {

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
                
                String Title,IPC="",ID="",Text="";
                
                Element TitleElement=  rootNode.getChild("teiHeader").getChild("fileDesc").getChild("titleStmt");
                
                Title=  TitleElement.getChildText("title");

                Element Notes=  rootNode.getChild("teiHeader").getChild("notesStmt");
                
               
		List list = Notes.getChildren("note");

		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);

                   if(node.getAttribute("type").getValue().equals("ID")){
                    ID= node.getText();
                   }
                   if(node.getAttribute("type").getValue().equals("IC")){
                   IPC = node.getText();
                   }

		}
                Element TextEle=  rootNode.getChild("text").getChild("body").getChild("div").getChild("p"); 
                Text = TextEle.getChildText("s");
                
             
                
                if(IPC.contains("G06F 21")){
                    return Text;
                }
                
                
               
              
                
                
                
               
	  } catch (Exception io) {
		logger.error("Failed parsing "+xmlFile.getName());
	  }
          return null;
    
    }
    
    
    
    
    
}
