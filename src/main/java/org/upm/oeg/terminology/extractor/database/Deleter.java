/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.database;

import java.util.HashMap;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 *
 * @author Pablo
 */
public class Deleter {
    
    
      public static void main(String[] args) throws Exception {

        // PARAMETERS
        Map<String, String> parameters = getParams(args);

        if (!checkParameters(parameters)) {
            printHelp();
            return;
        }

        cleanCore(parameters.get("-i"));

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

    public static void cleanCore(String CoreName) throws Exception {

        String core = CoreName;
        String serverUrl = "http://localhost:8983/solr/" + core;

        SolrClient solrClient = new HttpSolrClient.Builder(serverUrl).build();
        solrClient.deleteByQuery("*:*");
        solrClient.commit();

    }

    protected static boolean checkParameters(Map<String, String> params) {

        if (params == null) {
            return false;
        }

        boolean corpus = false;
        return params.containsKey("-i");
    }

    protected static void printHelp() {
        StringBuilder sb = new StringBuilder("Usage:\n");
        // sb.append("java -cp '[CLASSPATH]' ").append(App.class.getName()).append(" ")
        //  sb.append("[OPTIONS] [SOLR_HOME_PATH] [SOLR_CORE_NAME] ").append("\n\n");

        sb.append("Example: java -cp target/hner-icij-1.0-jar-with-dependencies.jar org.upm.oeg.terminology.extractor  -i SpanishCore \n\n");
        sb.append("[OPTIONS]:\n")
                .append("\t\t -i\t\tA Core Instance of Solr \n");

        System.out.println(sb);
    }

}
