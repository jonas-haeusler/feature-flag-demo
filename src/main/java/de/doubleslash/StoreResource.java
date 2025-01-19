package de.doubleslash;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class StoreResource {

    @Inject
    Template store;

    @Inject
    FeatureConfiguration featureConfiguration;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance store(@QueryParam("lang") String language) {
        return store.data("lang", language.toLowerCase())
                .data("featuredItemsSection", featureConfiguration.featureItemsSectionEnabled())
            .data("freeShipping", featureConfiguration.freeShippingEnabled(language));
    }
}
