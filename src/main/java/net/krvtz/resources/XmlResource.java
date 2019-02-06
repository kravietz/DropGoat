package net.krvtz.resources;

import com.google.common.html.HtmlEscapers;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Path("/xml-parser")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class XmlResource {

    private static final String MODULE = "XmlResource";
    private static final String FEATURE_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";

    @GET
    public Response handle(@QueryParam("input") String input) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // disable built-in DocumentBuilderFactory safeguards
        // NEVER do this in production code
        dbf.setFeature(FEATURE_GENERAL_ENTITIES, true);
        dbf.setFeature(FEATURE_PARAMETER_ENTITIES, true);
        dbf.setXIncludeAware(true);
        dbf.setExpandEntityReferences(true);

        System.out.println("input=" + input);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;
        try {
            doc = db.parse(new InputSource(new StringReader(input)));
        } catch (SAXException e) {
            e.printStackTrace();
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Return pretty print xml string
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));

        System.out.println("parsed=" + stringWriter.toString());

        String response = stringWriter.toString();

        return Response.ok(response).type(MediaType.TEXT_XML).encoding("utf-8").build();
    }
}
