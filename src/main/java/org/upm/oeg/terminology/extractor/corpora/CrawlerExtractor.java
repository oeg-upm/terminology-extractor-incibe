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
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author pcalleja
 */
public class CrawlerExtractor extends Extractor {
    
    
    final static Logger logger = Logger.getLogger(CrawlerExtractor.class); 
    
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

  
  
    
       public String connectMongoDatabase() throws UnknownHostException {

       
           MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
           
        
           DB database = mongoClient.getDB("mydatabase");
           DBCollection collection = database.getCollection("incibe");

           DBCursor cursor = collection.find();
           while (cursor.hasNext()) {
               DBObject obj = cursor.next();
               String text=(String) obj.get("startingpage");
               
               ObjectId id=(ObjectId) obj.get("_id");
               
               
                
                
                if(text==null)continue;
                createFile(id.toString(), this.getOutputDir().getAbsolutePath(), text);
           }

        return null;

    }

    public static void main(String[] args) throws UnknownHostException {

       
        String CorpusPath = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\CRAWLER\\Corpus\\" ;
        CrawlerExtractor craw = new CrawlerExtractor();
        craw.setOutputDir(new File(CorpusPath));

        craw.connectMongoDatabase();
    }


    
}
