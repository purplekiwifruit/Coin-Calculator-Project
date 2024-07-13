package com.example;

import com.example.resources.CoinResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

public class CoinCalculatorApplication extends Application<CoinCalculatorConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CoinCalculatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void initialize(final Bootstrap<CoinCalculatorConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CoinCalculatorConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        CoinResource resource = new CoinResource();
        environment.jersey().register(resource);


        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
    }

}
