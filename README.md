# helloWorldDW

Sample Dropwizard application with both synchronous and async endpoints.

# Resources

    GET     /helloworld (com.halfwind.resources.HelloWorldResource)
    POST    /patientservice/patient/v3 (com.halfwind.resources.PatientResource)
    GET     /patientservice/patient/{id} (com.halfwind.resources.PatientResource)
    GET     /patientservice/patients (com.halfwind.resources.PatientResource)
    POST    /patientservice/patients (com.halfwind.resources.PatientResource)
    POST    /patientservice/patients/v2 (com.halfwind.resources.PatientResource)

# Async endpoint :
    Lets you submit requests, that would be processed as a batch and you get a response,later.

    POST    /patientservice/patients/v2 (com.halfwind.resources.PatientResource)

How to start the helloWorldDW application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/example-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
