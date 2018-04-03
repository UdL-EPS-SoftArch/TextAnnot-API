package cat.udl.eps.entsoftarch.textannot.service;

import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

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
                    if (bauthor) {
                        System.out.println("Author Name : " + new String(ch, start, length));
                        bauthor = false;
                    }
                    if (btitle) {
                        System.out.println("Title : " + new String(ch, start, length));
                        btitle = false;
                    }
                    if (bgenre) {
                        System.out.println("Genre : " + new String(ch, start, length));
                        bgenre = false;
                    }
                    if (bprice) {
                        System.out.println("Price : " + new String(ch, start, length));
                        bprice = false;
                    }
                    if (bdate) {
                        System.out.println("Date : " + new String(ch, start, length));
                        bdate = false;
                    }
                    if (bdescription) {
                        System.out.println("Description : " + new String(ch, start, length));
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
