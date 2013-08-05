package com.britesnow.xpsfyao.service;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.britesnow.xpsfyao.dao.SocialIdEntityDao;
import com.britesnow.xpsfyao.entity.SocialIdEntity;
import com.britesnow.xpsfyao.oauth.OAuthServiceHelper;
import com.britesnow.xpsfyao.oauth.ServiceType;
import com.google.inject.Inject;

public class SalesForceAuthService implements AuthService {
    @Inject
    private SocialIdEntityDao socialIdEntityDao;

    private ServiceType       serivce     = ServiceType.SalesForce;
    private Token             EMPTY_TOKEN = null;
    private OAuthService      oAuthService;

    @Inject
    public SalesForceAuthService(OAuthServiceHelper oauthServiceHelper) {
        oAuthService = oauthServiceHelper.getOauthService(ServiceType.SalesForce);
    }

    @Override
    public SocialIdEntity getSocialIdEntity(Long userId) {
        return socialIdEntityDao.getSocialdentity(userId, serivce);
    }

    public String getAuthorizationUrl() {
        String authorizationUrl = oAuthService.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("authorizationUrl==="+authorizationUrl);
        return authorizationUrl;
    }

    public String[] getAccessToken(String code) {
        Verifier verifier = new Verifier(code);
        Token accessToken = oAuthService.getAccessToken(EMPTY_TOKEN, verifier);
        return new String[] { accessToken.getToken(), accessToken.getSecret(), accessToken.getRawResponse() };
    }

}
