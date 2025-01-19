package de.doubleslash;

import io.quarkus.runtime.util.StringUtil;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class LanguageRequestFilter {

    private final Set<String> ALLOWED_LANGUAGES = Set.of("en", "de");

    @ServerRequestFilter(preMatching = true)
    public Optional<Response> filter(@Context UriInfo uriInfo) {
        String market = uriInfo.getQueryParameters().getFirst("lang");
        if (StringUtil.isNullOrEmpty(market) || !ALLOWED_LANGUAGES.contains(market.toLowerCase())) {
            URI uri = uriInfo.getRequestUriBuilder()
                    .replaceQueryParam("lang", "en")
                    .build();
            return Optional.of(Response.temporaryRedirect(uri).build());
        }
        return Optional.empty();
    }
}
