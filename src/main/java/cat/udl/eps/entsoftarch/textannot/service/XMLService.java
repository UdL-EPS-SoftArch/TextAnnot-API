package cat.udl.eps.entsoftarch.textannot.service;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

@Service
public class XMLService {

    @Autowired
    private MetadataFieldRepository metadataFieldRepository;
    @Autowired
    private MetadataValueRepository metadataValueRepository;

    public void XMLParser(XmlSample xmlSample) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean bauthor = false;
                boolean btitle = false;
                boolean bgenre = false;
                boolean bprice = false;
                boolean bdate = false;
                boolean bdescription = false;

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    System.out.println("Start Element :" + qName);

                    if (qName.equalsIgnoreCase("author")) {
                        bauthor = true;
                    }
                    if (qName.equalsIgnoreCase("title")) {
                        btitle = true;
                    }
                    if (qName.equalsIgnoreCase("genre")) {
                        bgenre = true;
                    }
                    if (qName.equalsIgnoreCase("price")) {
                        bprice = true;
                    }
                    if (qName.equalsIgnoreCase("date")) {
                        bdate = true;
                    }
                    if (qName.equalsIgnoreCase("description")) {
                        bdescription = true;
                    }
                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    System.out.println("End Element :" + qName);
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    ArrayList<MetadataValue> metadataValueList;
                    if (bauthor) {
                        System.out.println("Author Name : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("author", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        bauthor = false;
                    }
                    if (btitle) {
                        System.out.println("Title : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("title", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        btitle = false;
                    }
                    if (bgenre) {
                        System.out.println("Genre : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("genre", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        bgenre = false;
                    }
                    if (bprice) {
                        System.out.println("Price : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("price", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        bprice = false;
                    }
                    if (bdate) {
                        System.out.println("Date : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("date", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        bdate = false;
                    }
                    if (bdescription) {
                        System.out.println("Description : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("description", "string");
                        metadataField = metadataFieldRepository.save(metadataField);
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValue.setValued(metadataField);
                        metadataValue.setForA(xmlSample);
                        metadataValueRepository.save(metadataValue);
                        bdescription = false;
                    }
                }
            };

            saxParser.parse(new InputSource(new StringReader(xmlSample.getContent())), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
