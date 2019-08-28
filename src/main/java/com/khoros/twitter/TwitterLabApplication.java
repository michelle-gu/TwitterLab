package com.khoros.twitter;

import com.khoros.twitter.resources.TwitterLabResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TwitterLabApplication extends Application<TwitterLabConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabApplication.class);

    public static void main(String[] args) throws Exception {
        new TwitterLabApplication().run(args);
    }

    @Override
    public String getName() {
        return "twitter-lab";
    }

    @Override
    public void initialize(Bootstrap<TwitterLabConfiguration> bootstrap) {
        // nothing to do here yet
    }

    @Override
    public void run(TwitterLabConfiguration configuration,
                    Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        LOGGER.info("Running TwitterLab application.");
        LOGGER.debug("Registering twitter lab resource with twitter instance.");
        TwitterLabComponent component = DaggerTwitterLabComponent.builder()
                .twitterLabModule(new TwitterLabModule(configuration)).build();

        final TwitterLabResource twitterLabResource = component.buildTwitterLabResource();
        environment.jersey().register(twitterLabResource);
    }

}
