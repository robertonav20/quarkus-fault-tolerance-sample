package org.acme.resource;

import io.micrometer.core.annotation.Counted;
import org.acme.resource.fallback.GreetingFallback;
import org.eclipse.microprofile.faulttolerance.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;

@Path("/")
public class GreetingResource {

    private static final Logger logger = LoggerFactory.getLogger(GreetingResource.class);

    @GET
    @Path("/fallback")
    @Produces(MediaType.TEXT_PLAIN)
    @Bulkhead(value = 1, waitingTaskQueue = 1)
    @Timeout(value = 1, unit = ChronoUnit.SECONDS)
    @Fallback(GreetingFallback.class)
    @Counted(value = "fallback-api", description = "Number of REST call received")
    public String fallback() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/timeout")
    @Produces(MediaType.TEXT_PLAIN)
    @Timeout(value = 1, unit = ChronoUnit.SECONDS)
    @Counted(value = "timeout-api", description = "Number of REST call received")
    public String timeout() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/bulkhead")
    @Produces(MediaType.TEXT_PLAIN)
    @Bulkhead(value = 1, waitingTaskQueue = 1)
    @Counted(value = "bulkhead-api", description = "Number of REST call received")
    public String bulkhead() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/circuit-breaker")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker(requestVolumeThreshold = 1)
    @Counted(value = "circuit-breaker-api", description = "Number of REST call received")
    public String circuitBreaker() {
        exception("Circuit breaker exception");
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/retry")
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(maxRetries = 1, delay = 1000, retryOn = RuntimeException.class)
    @Counted(value = "retry-api", description = "Number of REST call received")
    public String retry() {
        exception("Retry breaker exception");
        return "Hello from RESTEasy Reactive";
    }

    private void exception(String message) {
        logger.info("Raise an exception with message [{}]", message);
        throw new RuntimeException(message);
    }
}