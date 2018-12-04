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

/**
 *
 * @author pcalleja
 */
public class CoppaExtractor extends Extractor{
    
    
    public static void main (String [] args){
    
    
        File f = new File("C:\\Users\\pcalleja\\Desktop\\Todo");
        goThroughTeiFolder(f);
        
    
    }
    
    
    
    public static void goThroughTeiFolder(File folder){
    
    
        
        for(File f: folder.listFiles()){
        
            if(f.isDirectory()){
                goThroughTeiFolder(f);
            }
            
            if((f.isFile()) && (f.getName().endsWith(".xml"))){
                
                processFile(f);
            }
            
        }

    }
    
    public static void processFile(File xmlFile){
    
            SAXBuilder builder = new SAXBuilder();
             //File xmlFile = new File("c:\\file.xml");

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
                
                 boolean print =false;
                
                if(IPC.contains("G06F 21")){
                print =true;
                }
                
                
               
                if(print){
                    System.out.println("Title : " + Title);
                
                    System.out.println("Text : " + Text);
                
              
                    System.out.println("ID : " + ID);
                 
                    System.out.println("IPC : " + IPC);
                    
                    System.out.println("----------------------------------------------------");
                    createCompleteFileWithText( ID, "C:\\Users\\pcalleja\\Desktop\\nope",  Text);
                  
                }
                
                
                
               
	  } catch (Exception io) {
		System.out.println(io.getMessage());
	  }
	
    
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
    
    
    */
    
    
}
