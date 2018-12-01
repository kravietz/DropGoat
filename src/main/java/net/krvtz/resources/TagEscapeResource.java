package net.krvtz.resources;

import net.krvtz.views.EscapeView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/tag-escape")
@Produces({MediaType.TEXT_HTML})
@Consumes({MediaType.MULTIPART_FORM_DATA})
public class TagEscapeResource {

    @GET
    // Freemarker templates are handled through Views in DropWizard
    public EscapeView handle(@QueryParam("input") String input) {
        return new EscapeView(input);
    }
}

