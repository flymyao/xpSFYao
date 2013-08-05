package com.britesnow.xpsfyao.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.britesnow.xpsfyao.dao.SocialIdEntityDao;
import com.britesnow.xpsfyao.entity.SocialIdEntity;
import com.britesnow.xpsfyao.entity.User;
import com.britesnow.xpsfyao.oauth.ServiceType;
import com.britesnow.xpsfyao.service.SalesForceAuthService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OauthHandlers {
    @Inject
    private SalesForceAuthService salesForceAuthService;
    @Inject
    private SocialIdEntityDao   socialIdEntityDao;

    @WebGet("/authorize")
    public void authorize(@WebModel Map m,@WebParam("service") ServiceType service, RequestContext rc) throws IOException {
        String url = "";
       if(service == ServiceType.SalesForce){
            url = salesForceAuthService.getAuthorizationUrl();
        }        
        rc.getRes().sendRedirect(url);
    }

    @WebModelHandler(startsWith="/salesforce_callback")
    public void salesforceCallback(RequestContext rc, @WebUser User user,@WebParam("code") String code) {
        String[] tokens = salesForceAuthService.getAccessToken(code);
        SocialIdEntity s =   salesForceAuthService.getSocialIdEntity(user.getId());
        Pattern expirePattern = Pattern.compile("\"issued_at\":\\s*\"(\\S*?)\"");
        Matcher matcher = expirePattern.matcher(tokens[2]);
        String expire = null;
        if(matcher.find()){
            expire = matcher.group(1);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND,new Long(expire).intValue()/1000);
        Date tokenDate = cal.getTime();
        if (s==null) {
            s = new SocialIdEntity();
            s.setUser_id(user.getId());
            s.setToken(tokens[0]);
            s.setService(ServiceType.SalesForce);
            s.setTokenDate(tokenDate);
            socialIdEntityDao.save(s);
        }else{
            s.setToken(tokens[0]);
            s.setTokenDate(tokenDate);
            socialIdEntityDao.update(s);
        }
    }
}
