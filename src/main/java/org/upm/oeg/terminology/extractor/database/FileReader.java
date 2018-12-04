/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author pcalleja
 */
public class FileReader {
    
    
    private File File;
    private BufferedReader Buffer;
    
    public void openFile(String Path, String FileName) throws UnsupportedEncodingException, FileNotFoundException {

        this.File = new File(Path + File.separator + FileName);

        Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.File), "UTF8"));
    }
    
    
    
    
    public static String readContent(File f) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        

        BufferedReader Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "UTF8"));
        
        String line="";
        StringBuilder bf=new StringBuilder();
        
        while( (line=Buffer.readLine()) != null ){
            
            bf.append(line);
        }
        return bf.toString();
    }
    
    
    public void openFile(String FilePath) throws UnsupportedEncodingException, FileNotFoundException {

        this.File = new File( FilePath);

        Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.File), "UTF8"));
    }

    public String readLine() throws IOException {

        String resp = Buffer.readLine();
        if (resp == null) {
            Buffer.close();
            return null;

        }
        return resp;

    }

    public void close() throws IOException {

        Buffer.close();
    }

}
