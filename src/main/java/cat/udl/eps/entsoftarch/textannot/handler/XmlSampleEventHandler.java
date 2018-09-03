package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cat.udl.eps.entsoftarch.textannot.service.XMLIngestionService;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import org.xml.sax.SAXException;

@Component
@RepositoryEventHandler
public class XmlSampleEventHandler {

    final Logger logger = LoggerFactory.getLogger(XmlSample.class);

    @Autowired
    XmlSampleRepository xmlSampleRepository;

    @Autowired
    XMLIngestionService xmlService;

    @HandleBeforeCreate
    @Transactional
    public void handleXmlSamplePreCreate(XmlSample XmlSample) {
        logger.info("Before creating: {}", XmlSample.toString());
    }

    @HandleBeforeSave
    @Transactional
    public void handleXmlSamplePreSave(XmlSample XmlSample){
        logger.info("Before updating: {}", XmlSample.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handleXmlSamplePreDelete(XmlSample XmlSample){
        logger.info("Before deleting: {}", XmlSample.toString());
    }

    @HandleBeforeLinkSave
    public void handleXmlSamplePreLinkSave(XmlSample XmlSample, Object o) {
        logger.info("Before linking: {} to {}", XmlSample.toString(), o.toString());
    }

    @HandleAfterCreate
    @Transactional
    public void handleXmlSamplePostCreate(XmlSample xmlSample)
        throws IOException, SAXException, ParserConfigurationException {
        logger.info("After creating: {}", xmlSample.toString());
        xmlService.ingest(xmlSample);
    }

    @HandleAfterSave
    @Transactional
    public void handleXmlSamplePostSave(XmlSample XmlSample){
        logger.info("After updating: {}", XmlSample.toString());
    }

    @HandleAfterDelete
    @Transactional
    public void handleXmlSamplePostDelete(XmlSample XmlSample){
        logger.info("After deleting: {}", XmlSample.toString());
    }

    @HandleAfterLinkSave
    public void handleXmlSamplePostLinkSave(XmlSample XmlSample, Object o) {
        logger.info("After linking: {} to {}", XmlSample.toString(), o.toString());
    }

}
