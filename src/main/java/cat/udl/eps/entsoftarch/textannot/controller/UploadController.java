package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.XmlSample;
import cat.udl.eps.entsoftarch.textannot.repository.MetadataTemplateRepository;
import cat.udl.eps.entsoftarch.textannot.repository.XmlSampleRepository;
import cat.udl.eps.entsoftarch.textannot.service.XMLIngestionService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

@RequestMapping("/upload")
@BasePathAwareController
public class UploadController {

  @Autowired XmlSampleRepository xmlSampleRepository;
  @Autowired MetadataTemplateRepository metadataTemplateRepository;
  @Autowired XMLIngestionService xmlService;

  @PostMapping("/xmlsample")
  @ResponseBody
  public void uploadXML(MultipartHttpServletRequest request)
          throws Exception {
    String templateUri = request.getParameter("metadataTemplate");
    String templateName = templateUri.substring(templateUri.lastIndexOf('/') + 1);
    Optional<MetadataTemplate> metadataTemplateOptional = metadataTemplateRepository.findByName(templateName);

    Iterator<String> fileNames = request.getFileNames();
    MultipartFile xmlFile = request.getFile(fileNames.next());
    XmlSample xmlSample = new XmlSample();

    ByteArrayOutputStream result = new ByteArrayOutputStream();
    InputStream input = xmlFile.getInputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = input.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    xmlSample.setContent(result.toString("UTF-8"));
    xmlSample.setDescribedBy(metadataTemplateOptional.orElseThrow(() -> new Exception("metadataTemplate Optional not found")));
    xmlService.ingest(xmlSample);
  }
}
