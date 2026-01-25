package org.eotpremnice.xml.writer;

import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.ObjectFactory;
import org.eotpremnice.mapper.UblNamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlFileWriter {

    public static void write(DespatchAdviceType advice, String xmlLocation, String xmlName) throws Exception {

        JAXBContext context = JAXBContext.newInstance("oasis.names.specification.ubl.schema.xsd.despatchadvice_2");
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new UblNamespacePrefixMapper());

        // 4. Write file
        Path out = Paths.get(xmlLocation,
                xmlName,
                ".xml"
        );
        Files.createDirectories(out.getParent());

        ObjectFactory factory = new ObjectFactory();
        JAXBElement<DespatchAdviceType> root =
                factory.createDespatchAdvice(advice);

        marshaller.marshal(root, out.toFile());

    }
}
