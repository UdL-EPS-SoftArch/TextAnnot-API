package cat.udl.eps.entsoftarch.textannot.handler;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.exception.TagDeleteException;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
@RepositoryEventHandler
@Component
public class TagEventHandler {
    final Logger logger = LoggerFactory.getLogger(Tag.class);

    @Autowired
    TagRepository tagRepository;

    @HandleBeforeDelete
    @Transactional
    public void handletagPreDelete(Tag tag) throws TagDeleteException{

       List<Tag> sibilingsTags =  tagRepository.findByParent(tag);

       if (!sibilingsTags.isEmpty()){

           throw new TagDeleteException();

       }

    }




}
