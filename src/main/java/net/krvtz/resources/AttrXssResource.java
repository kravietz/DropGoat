package net.krvtz.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/attr-xss")
@Produces({MediaType.TEXT_HTML})
@Consumes({MediaType.MULTIPART_FORM_DATA})
public class AttrXssResource {

    static final String MODULE = "AttrXssResource";

    @GET
    public Response handle(@QueryParam("input") String input) {
        // TODO: switch to https://github.com/spullara/mustache.java

        String response = "<html><head><title>" + MODULE + "</title></head>" +
                "<body><h1>" + MODULE + "</h1><div class=\"" + input + "\">div contents</div>" +
                "<form><input name=input></form>" +
                "</body></html>";
        // The X-Xss-Protection header is disabled to prevent the XSS vector being blocked by the
        // browser's built-in xSS auditor
        return Response.ok(response).encoding("utf-8").header("X-Xss-Protection", "0").build();
    }
}
