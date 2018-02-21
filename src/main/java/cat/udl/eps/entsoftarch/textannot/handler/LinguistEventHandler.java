package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import cat.udl.eps.entsoftarch.textannot.repository.LinguistRepository;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class LinguistEventHandler {
    final Logger logger = LoggerFactory.getLogger(Linguist.class);

    @Autowired
    LinguistRepository linguistRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleLinguistPreCreate(Linguist linguist) {
        logger.info("Before creating: {}", linguist.toString());
    }

    @HandleBeforeSave
    @Transactional
    public void handleLinguistPreSave(Linguist linguist){
        logger.info("Before updating: {}", linguist.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handleLinguistPreDelete(Linguist linguist){
        logger.info("Before deleting: {}", linguist.toString());
    }

    @HandleBeforeLinkSave
    public void handleLinguistPreLinkSave(Linguist linguist, Object o) {
        logger.info("Before linking: {} to {}", linguist.toString(), o.toString());
    }

    @HandleAfterCreate
    @Transactional
    public void handleLinguistPostCreate(Linguist linguist){
        logger.info("After creating: {}", linguist.toString());
        linguist.encodePassword();
        linguistRepository.save(linguist);
    }

    @HandleAfterSave
    @Transactional
    public void handleLinguistPostSave(Linguist linguist){
        logger.info("After updating: {}", linguist.toString());
        linguist.encodePassword();
        linguistRepository.save(linguist);
    }

    @HandleAfterDelete
    @Transactional
    public void handleLinguistPostDelete(Linguist linguist){
        logger.info("After deleting: {}", linguist.toString());
    }

    @HandleAfterLinkSave
    public void handleLinguistPostLinkSave(Linguist linguist, Object o) {
        logger.info("After linking: {} to {}", linguist.toString(), o.toString());
    }
}
