package com.britesnow.xpsfyao.dao;

import com.britesnow.xpsfyao.entity.SocialIdEntity;
import com.britesnow.xpsfyao.oauth.ServiceType;


public class SocialIdEntityDao extends BaseHibernateDao<SocialIdEntity> {

    public SocialIdEntity getSocialdentity(Long user_id,ServiceType service) {
        SocialIdEntity socialdentity = (SocialIdEntity) daoHelper.findFirst("from SocialIdEntity u where u.service = ? and u.user_id = ? ", service,user_id);
        return socialdentity;
    }
}
