package com.britesnow.xpsfyao.oauth;

import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import com.britesnow.snow.web.binding.ApplicationProperties;
import com.britesnow.xpsfyao.oauth.api.SalesForceApi;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OAuthServiceHelper {
    private final Map          appconfig;

    @Inject
    public OAuthServiceHelper(@ApplicationProperties Map appConfig) {
        this.appconfig = appConfig;
    }

    public OAuthService getOauthService(ServiceType serviceType) {
        OAuthService oauthService = null;
        if (serviceType == ServiceType.SalesForce) {
            oauthService = this.getSalesForceOAuthService();
        } 
        return oauthService;
    }

    private OAuthService getSalesForceOAuthService() {
        String prefix = "salesforce";
        String clientId = (String) appconfig.get(prefix + ".apiKey");
        String secret = (String) appconfig.get(prefix + ".apiSecret");
        String callback = (String) appconfig.get(prefix + ".callbackUrl");
        ServiceBuilder builder = new ServiceBuilder().provider(SalesForceApi.class).apiKey(clientId).apiSecret(secret).callback(callback);
        return builder.build();
    }

}
