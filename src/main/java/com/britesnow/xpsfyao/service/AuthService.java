package com.britesnow.xpsfyao.service;

import com.britesnow.xpsfyao.entity.SocialIdEntity;


public interface AuthService {
    
    SocialIdEntity getSocialIdEntity(Long userId);
}
