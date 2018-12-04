/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.experiments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pablo
 */
public class Comparator {
    
    
    
    public static void main(String[] args) throws Exception {

        Set<String> set1 = createSet(new File("C:\\Users\\Pablo\\Desktop\\test\\ttfidf1-1.json"));
        Set<String> set2 = createSet(new File("C:\\Users\\Pablo\\Desktop\\test\\ttfidf2-2.json"));

        System.out.println("news");
        for (String s : set2) {
            if (!set1.contains(s)) {
                System.out.println(s);

            }

        }

    }

    
    public void compareCvalue(){
    
        
    
    
    }
    
    public static HashSet createSet(File f) throws UnsupportedEncodingException, FileNotFoundException, IOException{
     
        HashSet set=new HashSet();
          BufferedReader Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "UTF8"));
        
        String line="";
        StringBuilder bf=new StringBuilder();
        
        while( (line=Buffer.readLine()) != null ){
            
            String term= line.split(",")[0];
            term= term.replace("{\"string\":", "");
            term= term.replaceAll("\"", "");
            
           // System.out.println(term);
            
            set.add(term);
        }
        return set;
    
    
    }
    
}
