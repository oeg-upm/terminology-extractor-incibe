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
/**
 *
 * @author pcalleja
 */
public class CordisH2020Extractor extends Extractor{
    
    
    public static void main (String [] args){
    
    
        File f = new File("C:\\Users\\pcalleja\\Desktop\\Cordisdump");
        goThroughXMLfolder(f);
        
    
    }
    
    
    
    public static void goThroughXMLfolder(File folder){
    
    
        
        for(File f: folder.listFiles()){
        
            /*if(f.isDirectory()){
                goThroughXMLfolder(f);
            }*/
            
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                processFile(f);
            }
            
        }

    }
    
    public static void processFile(File xmlFile){
    
            System.out.println(xmlFile.getName());
            SAXBuilder builder = new SAXBuilder();
            String Name= xmlFile.getName();

	  try {

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
                System.out.println(rootNode.getName());
                
                
                
                
                System.out.println(rootNode.getChildren().size());
                
               String Text="";
            

            List list2 = rootNode.getChildren();
                       
                   for(int j=0;j < list2.size();j++){
                            Element node2 = (Element) list2.get(j);
                                if(node2.getName().equals("objective")){
                                    Text= node2.getValue();
                                }
                             
                            
                   }
                            
                          
                 
             
               createCompleteFileWithText( Name+".txt", "C:\\Users\\pcalleja\\Desktop\\Cordis",  Text);
             
	  } catch (Exception io) {
              io.printStackTrace();
              System.out.println("fallo");
	  }
	
          System.out.println("llego");
    
    }
    
    
    
    
    
    /*
     
    private File file;
    private Writer Writer;
    
    
    
    public void createFile(String Dir, String FileName) throws UnsupportedEncodingException, FileNotFoundException {

        this.file = new File(Dir + File.separator + FileName);
        Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.file), "UTF8"));
    }
    
    public void createFile(String FilePath) throws UnsupportedEncodingException, FileNotFoundException {
    
        this.file = new File(FilePath);
        Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.file), "UTF8"));
    
    }
    
    public void appendText(String text) throws IOException{
    
         Writer.append(text);
    }
    
    public void appendLine(String text) throws IOException{
    
        Writer.append(text).append("\r\n");
    }
    
    public void closeFile() throws IOException{
            Writer.flush();
            Writer.close();
    }
*/
    
    
    
    
    
    
    
    
}
