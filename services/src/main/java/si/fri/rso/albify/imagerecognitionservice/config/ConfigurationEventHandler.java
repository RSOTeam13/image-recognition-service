package si.fri.rso.albify.imagerecognitionservice.config;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

@ApplicationScoped
public class ConfigurationEventHandler {

    private static final Logger log = Logger.getLogger(ConfigurationEventHandler.class.getName());

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        String watchedKey = "enable-tagging";

        ConfigurationUtil.getInstance().subscribe(watchedKey, (String key, String value) -> {
            if (watchedKey.equals(key)) {

                if ("true".equals(value.toLowerCase())) {
                    log.info("Tagging enabled.");
                } else {
                    log.info("Tagging disabled.");
                }

            }

        });
    }

}