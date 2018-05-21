package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cat.udl.eps.entsoftarch.textannot.service.XMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RepositoryEventHandler
public class XmlSampleEventHandler {

    final Logger logger = LoggerFactory.getLogger(XmlSample.class);

    @Autowired
    XmlSampleRepository xmlSampleRepository;

    @Autowired
    XMLService xmlService;

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
    public void handleXmlSamplePostCreate(XmlSample XmlSample){
        logger.info("After creating: {}", XmlSample.toString());
        xmlSampleRepository.save(XmlSample);
        xmlService.XMLParser(XmlSample);
    }

    @HandleAfterSave
    @Transactional
    public void handleXmlSamplePostSave(XmlSample XmlSample){
        logger.info("After updating: {}", XmlSample.toString());
        xmlSampleRepository.save(XmlSample);
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
