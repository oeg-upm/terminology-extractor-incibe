/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.File;
import java.net.UnknownHostException;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author pcalleja
 */
public class CrawlerExtractor extends Extractor {
    
    
    private File DataSource;
    private File OutputDir;

    public File getDataSource() {
        return DataSource;
    }

    public void setDataSource(File DataSource) {
        this.DataSource = DataSource;
    }

    public File getOutputDir() {
        return OutputDir;
    }

    public void setOutputDir(File OutputDir) {
        this.OutputDir = OutputDir;
    }

  
    
    public void extractCorpus(String Language, File Directory) {

        for (File f : Directory.listFiles()) {

            if (f.isDirectory()) {
                extractCorpus(Language, f);
            }

            if ((f.isFile()) && (f.getName().endsWith(".xml"))) {

                processFile(Language, f);
            }

        }

    }

    public void processFile(String Language, File xmlFile) {

      
    }
    
       public String parseXMLFile() throws UnknownHostException {

       
           MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
           
           //MongoClient mongoClient = new MongoClient();
           DB database = mongoClient.getDB("mydatabase");
           DBCollection collection = database.getCollection("incibe");
           
          // DBObject query = new BasicDBObject("_id", "*");
          //      DBCursor cursor = collection.find(query);
           // DBObject jo = cursor.one();
          
           
           DBCursor cursor = collection.find();
           while (cursor.hasNext()) {
               DBObject obj = cursor.next();
               String text=(String) obj.get("startingpage");
               
               ObjectId id=(ObjectId) obj.get("_id");
               
                //do your thing
                
                
                if(text==null)continue;
                createCompleteFileWithText(id.toString(), this.getOutputDir().getAbsolutePath(), text);
           }

        return null;

    }

    public static void main(String[] args) throws UnknownHostException {

        //String lang = "ES";
        //String path = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\TED\\Data";
        String path2 = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\CRAWLER\\Corpus\\" ;
        CrawlerExtractor ted = new CrawlerExtractor();
        ted.setOutputDir(new File(path2));

        //ted.extractCorpus(lang, ted.getDataSource());
        ted.parseXMLFile();
    }


    
}
