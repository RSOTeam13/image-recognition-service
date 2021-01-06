package si.fri.rso.albify.imagerecognitionservice.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-config")
public class RestProperties {

    @ConfigValue(watch = true)
    private Boolean enableTagging;

    @ConfigValue(watch = true)
    private Boolean badHealth;

    public Boolean getEnableTagging() {
        return enableTagging;
    }

    public void setEnableTagging(final Boolean enableTagging) {
        this.enableTagging = enableTagging;
    }

    public Boolean getBadHealth() {
        return badHealth;
    }

    public void setBadHealth(final Boolean badHealth) {
        this.badHealth = badHealth;
    }
}