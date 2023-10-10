package com.halfwind;

import com.halfwind.core.ExecutionService;
import com.halfwind.core.PatientServiceImpl;
import com.halfwind.health.TemplateHealthCheck;
import com.halfwind.resources.HelloWorldResource;
import com.halfwind.resources.PatientResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class helloWorldDWApplication extends Application<helloWorldDWConfiguration> {

    public static void main(final String[] args) throws Exception {
        new helloWorldDWApplication().run(args);
    }

    @Override
    public String getName() {
        return "helloWorldDW";
    }

    @Override
    public void initialize(final Bootstrap<helloWorldDWConfiguration> bootstrap) {
        // TODO: application initialization

    }

    @Override
    public void run(final helloWorldDWConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);

        PatientServiceImpl globalPatientSvcImpl = new PatientServiceImpl();
        PatientResource patientResource = new PatientResource(globalPatientSvcImpl);

        environment.jersey().register(patientResource);

        TemplateHealthCheck templateHealthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("Formatting Health Check", templateHealthCheck);

        ScheduledExecutorService scheduledExecutorService = environment.lifecycle()
                .scheduledExecutorService("helloWorld-executor")
                .threads(2)
                .build();

        scheduledExecutorService.scheduleWithFixedDelay(new ExecutionService(globalPatientSvcImpl), 8, 5, TimeUnit.SECONDS);


    }

}
