/*
 * Copyright 2016 Lukáš Petrovický
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.triceo.robozonky;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

import org.slf4j.Logger;

/**
 * Decorates the request with User-Agent and adds some simple request logging.
 *
 * If ever a filter is needed for JAX-RS communication, this class should serve as the base class for that filter.
 */
public abstract class CommonFilter implements ClientRequestFilter, ClientResponseFilter {

    protected abstract Logger getLogger();

    private static final String VERSION = CommonFilter.class.getPackage().getImplementationVersion();
    private static final String URL = "http://triceo.github.io/robozonky/";

    @Override
    public void filter(final ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().putSingle("User-Agent", "RoboZonky " + CommonFilter.VERSION +
                " (" + CommonFilter.URL + ")");
        this.getLogger().trace("Will '{}' to '{}'.", clientRequestContext.getMethod(), clientRequestContext.getUri());
    }

    @Override
    public void filter(final ClientRequestContext clientRequestContext,
                       final ClientResponseContext clientResponseContext) throws IOException {
        this.getLogger().debug("Operation '{}' to '{}' finished with HTTP {}.", clientRequestContext.getMethod(),
                clientRequestContext.getUri(), clientResponseContext.getStatus());
    }


}
