package de.doubleslash;

import dev.openfeature.contrib.providers.flagd.Config;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.ImmutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.Value;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class FeatureConfiguration {

    private final Client client;

    public FeatureConfiguration() {
        FeatureProvider featureProvider;
        if (ConfigUtils.isProfileActive("prod")) {
            featureProvider = new FlagdProvider();
        } else {
            featureProvider = new FlagdProvider(FlagdOptions.builder()
                    .resolverType(Config.Resolver.IN_PROCESS)
                    .offlineFlagSourcePath(getClass()
                            .getClassLoader()
                            .getResource("local-flags.json")
                            .getPath())
                    .build());
        }
        OpenFeatureAPI.getInstance().setProviderAndWait(featureProvider);

        client = OpenFeatureAPI.getInstance().getClient();
    }

    public boolean featureItemsSectionEnabled() {
        return client.getBooleanValue("feature-items-section", false);
    }

    public boolean freeShippingEnabled(String language) {
        Map<String, Value> context = Map.of("lang", new Value(language));
        ImmutableContext evaluationContext = new ImmutableContext(context);

        return client.getBooleanValue("free-shipping", false, evaluationContext);
    }
}
