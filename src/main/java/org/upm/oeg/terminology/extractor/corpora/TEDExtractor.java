/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author pcalleja
 */
public class TEDExtractor extends Extractor{
    
    
    final static Logger logger = Logger.getLogger(TEDExtractor.class); 
    
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

  
    
    public void createCorpus(String Language, File Directory) {

        for (File f : Directory.listFiles()) {

            if (f.isDirectory()) {
                createCorpus(Language, f);
            }

            if ((f.isFile()) && (f.getName().endsWith(".xml"))) {

                parseFile(Language, f);
            }

        }

    }

    public void parseFile(String Language, File xmlFile) {

        String text = parseXMLFile(Language, xmlFile);
        if (text == null) {
            return;
        }

        createFile(xmlFile.getName(), this.getOutputDir().getAbsolutePath(), text);

    }
    
       public String parseXMLFile(String Language, File xmlFile) {

        String res = "";
        SAXBuilder builder = new SAXBuilder();
        
        HashSet CPVSet =new HashSet();
        CPVSet.add("79417000");
        CPVSet.add("79700000");
        CPVSet.add("80330000");
        CPVSet.add("80550000");
        CPVSet.add("80600000");
     


        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            //   http://publications.europa.eu/resource/schema/ted/R2.0.9/publication
            Namespace ns = rootNode.getNamespace();

            Element Form = rootNode.getChild("FORM_SECTION", ns);

            List<Element> elemList = Form.getChildren();
            for (Element elem : elemList) {

                String lang = elem.getAttributeValue("LG");
                if (lang == null) {
                    continue;
                }

                if (lang.equals(Language)) {

                    Element contr = elem.getChild("OBJECT_CONTRACT", ns);
                    Element cpv = contr.getChild("CPV_MAIN", ns);
                    Element cpvcode = cpv.getChild("CPV_CODE",ns);
                    
                    String attcpv= cpvcode.getAttributeValue("CODE");

                    if(!CPVSet.contains(attcpv)){
                        continue;  
                    }
                    

                    
                    Element descr = contr.getChild("SHORT_DESCR", ns);

                    List<Element> elemDesc = descr.getChildren();
                    for (Element elemdes : elemDesc) {

                        res = res.concat(elemdes.getText() + " ");
                    }

                    return res;

                }

            }

        } catch (Exception io) {
                logger.error("Failed parsing "+xmlFile.getName());
        }
        return null;

    }

    public static void main(String[] args) {

        String lang =  "ES";
        String path =  "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\TED\\Data";
        String path2 = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\TED\\Corpus\\" + lang.toLowerCase();
        TEDExtractor ted = new TEDExtractor();
        ted.setDataSource(new File(path));
        ted.setOutputDir(new File(path2));

        ted.createCorpus(lang, ted.getDataSource());

    }


    
}
