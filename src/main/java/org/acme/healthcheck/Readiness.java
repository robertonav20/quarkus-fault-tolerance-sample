package org.acme.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@org.eclipse.microprofile.health.Readiness
public class Readiness implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("fault-tolerance-readiness").up().build();
    }
}
