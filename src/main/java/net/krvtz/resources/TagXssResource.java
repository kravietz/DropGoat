package net.krvtz.resources;

import net.krvtz.views.HtmlView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/tag-xss")
@Produces({MediaType.TEXT_HTML})
@Consumes({MediaType.MULTIPART_FORM_DATA})
public class TagXssResource {
    @GET
    public HtmlView handle(@QueryParam("input") String input) {
        return new HtmlView("/TagXss.ftl.html", input);
    }

}
