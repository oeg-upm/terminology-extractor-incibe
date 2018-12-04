/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author pcalleja
 */
public class Extractor {
    
    
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
