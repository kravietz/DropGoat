package net.krvtz;

// proper

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import net.krvtz.resources.*;

public class DropGoatApplication extends Application<DropGoatConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropGoatApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<DropGoatConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new ViewBundle());
    }

    public void run(DropGoatConfiguration dropGoatConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new TagXssResource());
        environment.jersey().register(new AttrXssResource());
        environment.jersey().register(new JsXssResource());
        environment.jersey().register(new XmlResource());

        environment.jersey().register(new TagEncoderResource());
        environment.jersey().register(new AttrEncoderResource());
        environment.jersey().register(new TagEscapeResource());
    }
}
