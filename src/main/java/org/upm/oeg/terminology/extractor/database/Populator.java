/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.database;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author Pablo
 */
public class Populator {
    
     public static void main(String[] args) throws Exception {

        // PARAMETERS
        Map<String, String> parameters = getParams(args);

        if (!checkParameters(parameters)) {
            printHelp();
            return;
        }

        if (parameters.containsKey("-c")) {

            populateCoreWithCorpus(parameters.get("-i"), parameters.get("-c"));

        } else {

            populateWithDocument(parameters.get("-i"), parameters.get("-d"));
        }

    }

      protected static Map<String, String> getParams(String[] args) {

        Map<String, String> params = new HashMap<>();

        if ((args.length == 0)) //|| (args.length%2!=0)
        {

            return null;

        }

        for (int i = 0; i < args.length; i++) {

            String param = args[i];

            if (param.equals("-help")) {
                return null;
            } else {

                String value = args[i + 1];
                i++;
                params.put(param, value);
            }

        }

        return params;
    }

      
     public static void populateCoreWithCorpus(String CoreName, String CorpusFolder) throws Exception {

        String core = CoreName;
        String serverUrl = "http://localhost:8983/solr/" + core;
      
        File corpusDir = new File(CorpusFolder);

        SolrClient solrClient = new HttpSolrClient.Builder(serverUrl).build();

        int a=0;
        for (File f : corpusDir.listFiles()) {

            System.out.println("Adding "+f.getName()+" "+" to "+core+"  --- "+a);
            addDocument(solrClient, f.getName(), FileReader.readContent(f));
            solrClient.commit();
            System.out.println("Added");
            a++;
        }

        

        solrClient.close();
    }
    
    public static void populateWithDocument(String CoreName, String Document) throws Exception {

        String core = CoreName;
        String serverUrl = "http://localhost:8983/solr/" + core;
      
        File fil = new File(Document);

        SolrClient solrClient = new HttpSolrClient.Builder(serverUrl).build();

        System.out.println("Adding " + fil.getName() + " " + " to " + core);
        addDocument(solrClient, fil.getName(), FileReader.readContent(fil));
        solrClient.commit();
        System.out.println("Added");

        solrClient.close();
    }
      

    
    
    
    public static void addDocument(SolrClient solrClient,String id, String text) throws Exception{
     SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        document.addField("text", text );
        //System.out.println("solr document");

        
        solrClient.add(document);
        //System.out.println("added");
    
        
    }
    
    
     protected static boolean checkParameters(Map<String, String> params) {
        
        if (params==null){return false;} 
         
        boolean corpus=false;
        if (!params.containsKey("-i")){
            
            
            return false;
        }
        if (!params.containsKey("-c")){
        
            if(!params.containsKey("-d")){
                return false;
        
            } 
            
        }
        return true;
    }
     
     
      protected static void printHelp() {
        StringBuilder sb = new StringBuilder("Usage:\n");
       // sb.append("java -cp '[CLASSPATH]' ").append(App.class.getName()).append(" ")
      //  sb.append("[OPTIONS] [SOLR_HOME_PATH] [SOLR_CORE_NAME] ").append("\n\n");
     
        sb.append("Example: java -jar target/hner-icij-1.0-jar-with-dependencies.jar -d corpus -v \n\n");
        sb.append("[OPTIONS]:\n")
         .append("\t\t -f \t\tA Path of the file to be processed \n")
         .append("\t\t -d \t\tA Path of directory of the files to be processed \n");
        
              
        System.out.println(sb);
    }

}
