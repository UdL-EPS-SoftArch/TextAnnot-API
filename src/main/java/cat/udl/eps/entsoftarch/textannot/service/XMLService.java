package cat.udl.eps.entsoftarch.textannot.service;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLService {

    public static void XMLParser(String content) {



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
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        System.out.println(metadataField.getValues());
                        System.out.println(metadataValue.getValue());
                        bauthor = false;
                    }
                    if (btitle) {
                        System.out.println("Title : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("title", "string");
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        btitle = false;
                    }
                    if (bgenre) {
                        System.out.println("Genre : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("genre", "string");
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        bgenre = false;
                    }
                    if (bprice) {
                        System.out.println("Price : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("price", "string");
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        bprice = false;
                    }
                    if (bdate) {
                        System.out.println("Date : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("date", "string");
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        bdate = false;
                    }
                    if (bdescription) {
                        System.out.println("Description : " + new String(ch, start, length));
                        MetadataField metadataField = new MetadataField("description", "string");
                        MetadataValue metadataValue = new MetadataValue(new String(ch, start, length));
                        metadataValueList = new ArrayList<MetadataValue>();
                        metadataValueList.add(metadataValue);
                        metadataField.setValues(metadataValueList);
                        bdescription = false;
                    }
                }
            };



            saxParser.parse(new InputSource(new StringReader(content)), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
