package com.britesnow.xpsfyao.hook;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.hook.On;
import com.britesnow.snow.web.hook.ReqPhase;
import com.britesnow.snow.web.hook.annotation.WebRequestHook;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.xpsfyao.entity.User;
import com.britesnow.xpsfyao.exception.JsonAuthException;
import com.google.inject.Singleton;

@Singleton
public class SSAuthHook {
    @WebRequestHook(phase = ReqPhase.AUTH, on = On.AFTER)
    public void checkAuth(@WebUser User user, RequestContext rc) {
        String resource =  rc.getReq().getRequestURI();
        if (resource.endsWith(".json") || resource.endsWith(".do") && !resource.endsWith("/login.do")) {
            if (user == null) {
                throw new JsonAuthException();
            }
        }
    }
}
