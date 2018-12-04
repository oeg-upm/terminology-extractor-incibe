/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.terminology.extractor.corpora;

import java.io.File;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author pcalleja
 */
public class TEDExtractor extends Extractor{
    
    
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

        String text = parseXMLFile(Language, xmlFile);
        if (text == null) {
            return;
        }

        createCompleteFileWithText(xmlFile.getName(), this.getOutputDir().getAbsolutePath(), text);

    }
    
       public String parseXMLFile(String Language, File xmlFile) {

        String res = "";
        SAXBuilder builder = new SAXBuilder();
        //File xmlFile = new File("c:\\file.xml");

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

                    Element descr = contr.getChild("SHORT_DESCR", ns);

                    List<Element> elemList2 = descr.getChildren();
                    for (Element elem2 : elemList2) {

                        res = res.concat(elem2.getText() + " ");
                    }

                    return res;

                }

            }

        } catch (Exception io) {
            // bad namespaces
        }
        return null;

    }

    public static void main(String[] args) {

        String lang = "ES";
        String path = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\TED\\Data";
        String path2 = "D:\\NextCloudCiber\\FTP\\ExtractorTerminologico\\TerminologyExtractorCorpus\\TED\\Corpus\\" + lang.toLowerCase();
        TEDExtractor ted = new TEDExtractor();
        ted.setDataSource(new File(path));
        ted.setOutputDir(new File(path2));

        ted.extractCorpus(lang, ted.getDataSource());

    }


    
}
