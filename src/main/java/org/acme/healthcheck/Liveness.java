package org.acme.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@org.eclipse.microprofile.health.Liveness
public class Liveness implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("fault-tolerance-liveness").up().build();
    }
}
