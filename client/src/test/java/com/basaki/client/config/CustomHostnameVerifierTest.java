package com.basaki.client.config;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * {@code CustomHostnameVerifierTest} represents unit test for
 * {@code CustomHostnameVerifier}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/21/18
 */
public class CustomHostnameVerifierTest {

    @Mock
    private X509Certificate certificate;

    @Mock
    private SSLSession session;

    @InjectMocks
    private CustomHostnameVerifier verifier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerify() throws SSLPeerUnverifiedException {
        X500Principal principal = new X500Principal(
                "CN=MyServer,OU=MyServerOU,O=test,L=Pdx,ST=OR,C=US");
        when(certificate.getSubjectX500Principal()).thenReturn(principal);

        Certificate[] certs = {certificate};
        when(session.getPeerCertificates()).thenReturn(certs);

        verifier.verify("localhost", session);
    }

    @Test
    public void testVerifyWithSSLException() throws SSLPeerUnverifiedException {
        when(session.getPeerCertificates()).thenThrow(
                new SSLPeerUnverifiedException("Just a test!"));

        verifier.verify("localhost", session);
    }

    @Test
    public void testVerifyInvalidDn() throws SSLPeerUnverifiedException {
        X500Principal principal = new X500Principal(
                "OU=MyServerOU,O=test,L=Pdx,ST=OR,C=US");
        when(certificate.getSubjectX500Principal()).thenReturn(principal);

        Certificate[] certs = {certificate};
        when(session.getPeerCertificates()).thenReturn(certs);

        verifier.verify("localhost", session);
    }
}
