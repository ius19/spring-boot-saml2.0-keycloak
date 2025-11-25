package com.henry.config;

import org.opensaml.Configuration;
import org.opensaml.xml.security.BasicSecurityConfiguration;
import org.opensaml.xml.signature.SignatureConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.stereotype.Component;

/**
 * Ensure OpenSAML uses RSA-SHA256 and SHA256 for digest when signing.
 */
@Component
public class CustomSamlBootstrap extends SAMLBootstrap {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(">>> CustomSamlBootstrap LOADED - forcing RSA-SHA256 / SHA256 <<<");
        super.postProcessBeanFactory(beanFactory);

        try {
            BasicSecurityConfiguration config =
                    (BasicSecurityConfiguration) Configuration.getGlobalSecurityConfiguration();

            config.registerSignatureAlgorithmURI("RSA", SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
            config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA256);

        } catch (Throwable t) {
            System.err.println("CustomSamlBootstrap: failed to apply RSA-SHA256 config: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
