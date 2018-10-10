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
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_XML)
public class XmlResource {

    private static final String MODULE = "XmlResource";
    private static final String FEATURE_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    private static final String DEMO = "<?xml version=\"1.0\" encoding=\"ascii\"?><!DOCTYPE foo [<!ELEMENT foo ANY >]><foo>demo</foo>";

    private static final String EXPLOIT = "<?xml version=\"1.0\" encoding=\"ascii\"?>" +
            "<!DOCTYPE foo [\n" +
            "  <!ELEMENT foo ANY>\n" +
            "  <!ENTITY bar SYSTEM\n" +
            "  \"file:///etc/fstab\">;\n" +
            "]>\n" +
            "<foo>\n" +
            "  &bar;\n" +
            "</foo>";

    @GET
    public Response handle(@QueryParam("input") String input) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // disable built-in DocumentBuilderFactory safeguards
        // NEVER do this in production code
        dbf.setFeature(FEATURE_GENERAL_ENTITIES, true);
        dbf.setFeature(FEATURE_PARAMETER_ENTITIES, true);
        dbf.setXIncludeAware(true);
        dbf.setExpandEntityReferences(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(input)));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Return pretty print xml string
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));

        System.out.println("DOC=" + stringWriter.toString());

        String response = "<html><head><title>" + MODULE + "</title></head>" +
                "<body><h1>" + MODULE + "</h1><div><pre>" +
                HtmlEscapers.htmlEscaper().escape(stringWriter.toString())
                + "</pre></div>" +
                "<div><form><input name=input></form></div>" +

                "<div><a href=\"?input=" + URLEncoder.encode(EXPLOIT, "UTF-8") + "\">exploit</a></div>" +
                "<div><a href=\"?input=" + URLEncoder.encode(DEMO, "UTF-8") + "\">demo</a></div>" +

                "</body></html>";
        return Response.ok(response).encoding("utf-8").build();
    }
}
