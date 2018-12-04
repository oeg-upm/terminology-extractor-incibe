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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 *
 * @author pcalleja
 */
public class ScopusExtractor extends Extractor{
    
    
    public static void main (String [] args) {
    
    
        File f = new File("C:\\Users\\pcalleja\\Desktop\\ScopusDump");
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
             //System.out.println("proceso");
            SAXBuilder builder = new SAXBuilder();
             //File xmlFile = new File("c:\\file.xml");
            String Name= xmlFile.getName();

	  try {

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
           //     System.out.println(rootNode.getName());
                
                
                
                
                
                
                
                 Element HeadElement2=  (Element)rootNode.getChildren().get(1);
                Element HeadElement=  HeadElement2.getChild("item").getChild("bibrecord").getChild("head");
                 
                List lista = HeadElement.getChildren();
                for(int j=0;j < lista.size();j++){
                            Element node2 = (Element) lista.get(j);
                         //  System.out.println(node2.getName());
                       }
                
                
               
                
                //Title=  TitleElement.getChildText("title");

            
                
                //Element classification= HeadElement.getChild("Enhacement").get
               
		List list = HeadElement.getChild("enhancement").getChild("classificationgroup").getChildren("classifications");
                

                boolean pass=false;
		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);

                   // System.out.println(node.getName());
                   if(node.getAttribute("type").getValue().equals("ASJC")){
                       
                    
                       List list2 = node.getChildren("classification");
                       
                       for(int j=0;j < list2.size();j++){
                          // System.out.println("entro");
                            Element node2 = (Element) list2.get(j);
                            String code=node2.getValue();
                            if(code.contains("1705")){
                                pass=true;
                            }
                            
                           if(code.contains("1706")){
                                pass=true;
                            }
                            
                           //System.out.println(node2.getText());
                           //System.out.println(node2.getValue());
                       }
                   }
                   

		}
                
                
                
                if(pass==false){
                    return;
                }
                List listA = HeadElement.getChild("abstracts").getChildren("abstract");
                
              
               String Text = (((Element) listA.get(0)).getValue());
               createCompleteFileWithText( Name+".txt", "C:\\Users\\pcalleja\\Desktop\\ScopusTIC",  Text);
                /*
                Element TextEle=  rootNode.getChild("text").getChild("body").getChild("div").getChild("p"); 
                Text = TextEle.getChildText("s");
                
                 boolean print =false;
                
                
                
                
               
                if(print){
                    System.out.println("Title : " + Title);
                
                    System.out.println("Text : " + Text);
                
              
                    System.out.println("ID : " + ID);
                 
                    System.out.println("IPC : " + IPC);
                    
                    System.out.println("----------------------------------------------------");
                    //createCompleteFileWithText( ID, "C:/Users/pcalleja/Desktop/jate/Corpus/Coppa",  Text);
                  
                }
                
                */
                
               
	  } catch (Exception io) {
              
             
	  }
	
    
    }
    
    
     
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
    
    
    public static File createCompleteFileWithText(String FileName, String Dir, String Text) {

        File file = null;
        try {

            file = new File(Dir + File.separator + FileName);

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            out.append(Text);

            out.flush();
            out.close();

        } catch (IOException e) {
            //logger.error(e);

        }

        return file;

    }
    
    
    
    
    
}
