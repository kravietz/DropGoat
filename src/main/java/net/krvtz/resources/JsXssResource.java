package net.krvtz.resources;

import net.krvtz.views.HtmlView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/js-xss")
@Produces({MediaType.TEXT_HTML})
@Consumes({MediaType.MULTIPART_FORM_DATA})
public class JsXssResource {
    @GET
    public HtmlView handle(@QueryParam("input") String input) {
        return new HtmlView("/JsXss.ftl.html", input);
    }
}
