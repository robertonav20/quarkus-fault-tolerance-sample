package org.acme.resource.fallback;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

public class GreetingFallback implements FallbackHandler<String> {
    @Override
    public String handle(ExecutionContext executionContext) {
        return "Fallback error message";
    }
}
