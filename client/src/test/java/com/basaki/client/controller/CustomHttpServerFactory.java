package com.basaki.client.controller;

import com.github.tomakehurst.wiremock.common.JettySettings;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.HttpServer;
import com.github.tomakehurst.wiremock.http.HttpServerFactory;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.jetty9.JettyHttpServer;
import wiremock.org.eclipse.jetty.io.NetworkTrafficListener;
import wiremock.org.eclipse.jetty.server.ConnectionFactory;
import wiremock.org.eclipse.jetty.server.ServerConnector;
import wiremock.org.eclipse.jetty.server.SslConnectionFactory;

/**
 * {@code CustomHttpServerFactory} is a custom HTTP factory to set
 * key store password which was set as key manager password in
 * {@code JettyHttpServer}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/22/18
 */
public class CustomHttpServerFactory implements HttpServerFactory {

    @Override
    public HttpServer buildHttpServer(Options options,
            AdminRequestHandler adminRequestHandler,
            StubRequestHandler stubRequestHandler) {
        return new JettyHttpServer(
                options,
                adminRequestHandler,
                stubRequestHandler
        ) {
            @Override
            protected ServerConnector createServerConnector(String bindAddress,
                    JettySettings jettySettings, int port,
                    NetworkTrafficListener listener,
                    ConnectionFactory... connectionFactories) {
                if (connectionFactories[0] instanceof SslConnectionFactory) {
                    SslConnectionFactory sslConnectionFactory =
                            (SslConnectionFactory) connectionFactories[0];
                    sslConnectionFactory.getSslContextFactory().setKeyStorePassword(
                            options.httpsSettings().keyStorePassword());
                    connectionFactories =
                            new ConnectionFactory[]{sslConnectionFactory, connectionFactories[1]};
                }

                return super.createServerConnector(bindAddress, jettySettings,
                        port, listener, connectionFactories);
            }
        };
    }
}
