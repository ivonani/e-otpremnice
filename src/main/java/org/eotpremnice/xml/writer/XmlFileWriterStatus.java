package org.eotpremnice.xml.writer;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_2.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.applicationresponse_2.ObjectFactory;
import org.eotpremnice.mapper.UblNamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlFileWriterStatus {

    public static Path write(ApplicationResponseType response, String xmlLocation, String xmlName) throws Exception {

        JAXBContext context = JAXBContext.newInstance("oasis.names.specification.ubl.schema.xsd.applicationresponse_2");
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new UblNamespacePrefixMapper());

        String xmlFullName = xmlName + ".xml";
        Path out = Paths.get(xmlLocation,
                xmlFullName
        );
        Files.createDirectories(out.getParent());

        ObjectFactory factory = new ObjectFactory();
        JAXBElement<ApplicationResponseType> root =
                factory.createApplicationResponse(response);

        marshaller.marshal(root, out.toFile());

        return out;
    }
}