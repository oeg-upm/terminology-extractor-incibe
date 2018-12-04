# Terminology Extractor Incibe


Terminology extractor based on JATE 2.0 for Cibersecurity corpora

## To install

### First Step  (trying to fix):

Clone or download JATE: https://github.com/ziqizhang/jate

Compile

> mvn clean

> mvn install

Copy jate/target/jate-2.0-beta.11-jar-with-dependencies.jar to  terminology-extractor/solr-5.3.0/contrib/jate/lib/jate-2.0-beta.11-jar-with-dependencies.jar



### Second step


Clean and install with mvn this repo

> mvn clean install


## To Init

To start Solr server use: 
> start_solr_server

## To close

To stop Solr server use (remember to do):
> stop_solr_server

## To populate Solr database

To add files in Solr Database

> java -cp target/terminology-extractor-1.0-jar-with-dependencies.jar org.upm.oeg.terminology.extractor.database.Populator -c <corpus_dir> -i <core_name>

> java -cp target/terminology-extractor-1.0-jar-with-dependencies.jar org.upm.oeg.terminology.extractor.database.Populator -d D:\NextCloudCiber\FTP\ExtractorTerminologico\TerminologyExtractorCorpus\TED\Corpus\es -i TEDen


## To clean Solr database

To add files in Solr Database

> java -cp target/terminology-extractor-1.0-jar-with-dependencies.jar org.upm.oeg.terminology.extractor.database.Deleter -i <core_name>

> java -cp target/terminology-extractor-1.0-jar-with-dependencies.jar org.upm.oeg.terminology.extractor.database.Deleter -i TEDen



## To retrieve terminology 

To retrieve a json file with terminology using CValue


> curl -X POST http://localhost:8983/solr/<core_name>/cvalue

> curl -X POST http://localhost:8983/solr/TEDes/cvalue


To retrieve a json file with terminology using TTF-IDF

> curl -X POST http://localhost:8983/solr/<core_name>/ttfidf

> curl -X POST http://localhost:8983/solr/TEDes/ttfidf


Also can be queried from the web interface

>  http://localhost:8983/solr/#/<core_name>/query

and put /cvalue or /ttfidf in the Request-Handler



# Acknowledges
This work contains the library JATE 2.0  and an extension to work with Spanish documents

Zhang, Z., Gao, J., Ciravegna, F. 2016. JATE 2.0: Java Automatic Term Extraction with Apache Solr. In The Proceedings of the 10th Language Resources and Evaluation Conference, May 2016, Portorož, Slovenia


This work contains the latest Spanish POS models for OpenNLP via: https://github.com/utcompling/OpenNLP-Models

