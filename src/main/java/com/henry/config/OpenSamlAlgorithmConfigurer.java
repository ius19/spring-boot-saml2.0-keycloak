package com.henry.config;

import org.opensaml.Configuration;
import org.opensaml.xml.security.BasicSecurityConfiguration;
import org.opensaml.xml.signature.SignatureConstants;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Extra safeguard: at context refresh, make sure global OpenSAML defaults
 * use RSA-SHA256 and SHA-256 digest for references.
 */
@Component
public class OpenSamlAlgorithmConfigurer implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            BasicSecurityConfiguration config =
                    (BasicSecurityConfiguration) Configuration.getGlobalSecurityConfiguration();
            System.out.println(">>> OpenSamlAlgorithmConfigurer applying RSA-SHA256 / SHA256");
            config.registerSignatureAlgorithmURI("RSA", SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
            config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA256);
        } catch (Throwable t) {
            System.err.println("OpenSamlAlgorithmConfigurer: failed to apply algorithms: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
