package cat.udl.eps.entsoftarch.textannot.config;

import cat.udl.eps.entsoftarch.textannot.domain.Admin;
import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataFieldRepository;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {
    @Autowired Environment environment;
    @Autowired MetadataTemplateRepository metadataTemplateRepository;
    @Autowired MetadataFieldRepository metadataFieldRepository;

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
    }

    @PostConstruct
    @Profile("!test")
    public void init() {
        if(!environment.acceptsProfiles("Test")) {
            MetadataTemplate template = new MetadataTemplate();
            template.setName("default");
            metadataTemplateRepository.save(template);

            String[][] defaultTemplateFields = {
                {"datos_generales", "número_muestra", "String"},
                {"datos_generales", "código_informante", "String"},
                {"datos_generales", "transliteración", "String"},
                {"datos_generales", "revisión_primera", "String"},
                {"datos_generales", "revisión_segunda", "String"},
                {"datos_generales", "etiquetado", "String"},
                {"datos_muestra", "fecha_recogida", "String"},
                {"datos_muestra", "palabras", "String"},
                {"datos_muestra", "género_discursivo", "String"},
                {"datos_muestra", "observaciones", "String"},
                {"datos_informante", "nombre", "String"},
                {"datos_informante", "sexo", "String"},
                {"datos_informante", "nivel_referencia", "String"},
                {"datos_informante", "curso", "String"},
                {"datos_informante", "universidad", "String"},
                {"datos_informante", "nivel_CET", "String"},
                {"datos_informante", "estancia_España", "String"},
                {"datos_informante", "observaciones", "String"},
            };

            for (String[] fieldData : defaultTemplateFields) {
                MetadataField field = new MetadataField();
                field.setCategory(fieldData[0]);
                field.setName(fieldData[1]);
                field.setType(fieldData[2]);
                field.setDefinedAt(template);
                metadataFieldRepository.save(field);
            }
        }
    }
}