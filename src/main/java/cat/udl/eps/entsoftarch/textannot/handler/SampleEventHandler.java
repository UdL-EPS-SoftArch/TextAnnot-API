package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.repository.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RepositoryEventHandler
public class SampleEventHandler {
    final Logger logger = LoggerFactory.getLogger(Sample.class);
    
    @Autowired
    SampleRepository sampleRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleSamplePreCreate(Sample Sample) {
        logger.info("Before creating: {}", Sample.toString());
    }

    @HandleBeforeSave
    @Transactional
    public void handleSamplePreSave(Sample Sample){
        logger.info("Before updating: {}", Sample.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handleSamplePreDelete(Sample Sample){
        logger.info("Before deleting: {}", Sample.toString());
    }

    @HandleBeforeLinkSave
    public void handleSamplePreLinkSave(Sample Sample, Object o) {
        logger.info("Before linking: {} to {}", Sample, o);
    }

    @HandleAfterCreate
    @Transactional
    public void handleSamplePostCreate(Sample Sample){
        logger.info("After creating: {}", Sample.toString());
        sampleRepository.save(Sample);
    }

    @HandleAfterSave
    @Transactional
    public void handleSamplePostSave(Sample Sample){
        logger.info("After updating: {}", Sample.toString());
        sampleRepository.save(Sample);
    }

    @HandleAfterDelete
    @Transactional
    public void handleSamplePostDelete(Sample Sample) {
        logger.info("After deleting: {}", Sample.toString());
    }

    @HandleAfterLinkSave
    public void handleSamplePostLinkSave(Sample Sample, Object o) {
        logger.info("After linking: {} to {}", Sample, o);
    }
}

    

