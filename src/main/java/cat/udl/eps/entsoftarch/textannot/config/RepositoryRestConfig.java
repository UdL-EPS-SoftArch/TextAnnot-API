package cat.udl.eps.entsoftarch.textannot.config;

import cat.udl.eps.entsoftarch.textannot.domain.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Admin.class);
        config.exposeIdsFor(MetadataValue.class);
        config.exposeIdsFor(MetadataField.class);
        config.exposeIdsFor(MetadataTemplate.class);
        config.exposeIdsFor(XmlSample.class);
        config.exposeIdsFor(Sample.class);
        config.exposeIdsFor(Linguist.class);
        config.exposeIdsFor(Tag.class);
        config.exposeIdsFor(TagHierarchy.class);
    }

    @PostConstruct
    public void init() {
    }

}
