package net.krvtz.resources;

import org.owasp.encoder.Encode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tag-enc")
@Produces({MediaType.TEXT_HTML})
@Consumes({MediaType.MULTIPART_FORM_DATA})
public class TagEncoderResource {
    static final String MODULE = "TagEncoderResource";

    @GET
    public Response handle(@QueryParam("input") String input) {
        String response = "<html><head><title>" + MODULE + "</title></head>" +
                "<body><h1>" + MODULE + "</h1><div>" +
                Encode.forHtml(input) +
                "</div>" +
                "<form><input name=input></form>" +
                "</body></html>";
        // The X-Xss-Protection header is disabled to prevent the XSS vector being blocked by the
        // browser's built-in xSS auditor
        return Response.ok(response).encoding("utf-8").header("X-Xss-Protection", "0").build();
    }

}
