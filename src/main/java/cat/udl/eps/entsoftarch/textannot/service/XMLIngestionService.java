package cat.udl.eps.entsoftarch.textannot.service;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataValueRepository;
import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Service
public class XMLIngestionService {
    final Logger logger = LoggerFactory.getLogger(XMLIngestionService.class);

    @Autowired private XmlSampleRepository xmlSampleRepository;
    @Autowired private MetadataFieldRepository metadataFieldRepository;
    @Autowired private MetadataValueRepository metadataValueRepository;

    public void ingest(XmlSample xmlSample)
        throws SAXException, ParserConfigurationException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(
            new InputSource(new StringReader(xmlSample.getContent())),
            new XmlSampleHandler(xmlSample));
    }

    class XmlSampleHandler extends DefaultHandler {

        private final XmlSample xmlSample;
        private String currentSubfield = "";
        private String currentField = "";
        private List<MetadataValue> metadataValues = new ArrayList();

        public XmlSampleHandler(XmlSample xmlSample) {
            this.xmlSample = xmlSample;
        }

        @Override
        public void startElement(String uri, String localName,String qName, Attributes attributes)
            throws SAXException {
            logger.info("Starts XML element: {}", qName);
            this.currentField = this.currentSubfield;
            this.currentSubfield = qName;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            logger.info("Ends XML element: {}", qName);
            this.currentSubfield = this.currentField;
            this.currentField = "";
        }

        @Override
        public void characters(char ch[], int start, int length) {
            String value = new String(ch, start, length).trim();
            logger.info("Content for XML element \"{} > {}\": {}", currentField, currentSubfield, value);

            if (value.isEmpty()) return;
            if (currentSubfield.equals("texto")) {
                xmlSample.setText(value.trim());
                return;
            } else if (currentField.equals("texto"))  {
                if (xmlSample.getText() == null || xmlSample.getText().equals("")) {
                    xmlSample.setText(value.trim());
                } else {
                    xmlSample.setText(xmlSample.getText() + "\n" + value.trim());
                }
                return;
            }
            MetadataTemplate template = xmlSample.getDescribedBy();
            Assert.notNull(template, "The XMLSample lacks an associated MetadataTemplate");
            MetadataField metadataField =
                metadataFieldRepository.findByCategoryAndName(currentField, currentSubfield);
            Assert.notNull(metadataField, "The metadata field \"" + currentField + ">" +
                currentSubfield + "\" is not defined in template " + template.getName());
            MetadataValue metadataValue = new MetadataValue(value);
            metadataValue.setValues(metadataField);
            metadataValue.setForA(xmlSample);
            metadataValues.add(metadataValue);
        }

        @Override
        public void endDocument() throws SAXException {
            xmlSampleRepository.save(this.xmlSample);
            metadataValues.forEach(metadataValue -> metadataValueRepository.save(metadataValue));
        }
    }
}
