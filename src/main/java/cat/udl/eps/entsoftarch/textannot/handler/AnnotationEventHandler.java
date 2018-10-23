package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import cat.udl.eps.entsoftarch.textannot.repository.AnnotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RepositoryEventHandler
public class AnnotationEventHandler {
    final Logger logger = LoggerFactory.getLogger(Annotation.class);

    @Autowired
    AnnotationRepository annotationRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleAnnotationPreCreate(Annotation annotation) {
        logger.info("Before creating: {}", annotation.toString());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", auth.getAuthorities());

        annotation.setLinguist((Linguist) auth.getPrincipal());
        annotationRepository.save(annotation);
    }


}
