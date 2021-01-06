package si.fri.rso.albify.imagerecognitionservice.services.beans;

import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.albify.imagerecognitionservice.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class TogglableHealthCheckBean implements HealthCheck {
    @Inject
    private RestProperties properties;
    public HealthCheckResponse call() {
        if (properties.getBadHealth()) {
            return HealthCheckResponse.down(TogglableHealthCheckBean.class.getSimpleName());
        }
        return HealthCheckResponse.up(TogglableHealthCheckBean.class.getSimpleName());
    }

}