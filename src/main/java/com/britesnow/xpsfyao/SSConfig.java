package com.britesnow.xpsfyao;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.britesnow.snow.web.auth.AuthRequest;
import com.britesnow.snow.web.binding.EntityClasses;
import com.britesnow.snow.web.db.hibernate.HibernateDaoHelper;
import com.britesnow.snow.web.db.hibernate.HibernateDaoHelperImpl;
import com.britesnow.xpsfyao.entity.BaseEntity;
import com.britesnow.xpsfyao.web.SSAuthRequest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;


public class SSConfig extends AbstractModule {
    private static Logger log = LoggerFactory.getLogger(SSConfig.class);

    @Override
    protected void configure() {

        // Default bind for the HibernateDaoHelper.
        bind(HibernateDaoHelper.class).to(HibernateDaoHelperImpl.class);
      bind(AuthRequest.class).to(SSAuthRequest.class);
    }

    // Used by the Hibernate support to inject the entity class
    @Provides
    @Singleton
    @Inject
    @EntityClasses
    public Class[] provideEntityClasses() {
        Set<Class<?>> entitySet;
        try {
            entitySet = new ClassesInPackageScanner().findAnnotatedClasses(BaseEntity.class.getPackage().getName(), javax.persistence.Entity.class);
            Class[] entityClasses = new Class[entitySet.size()];
            entitySet.toArray(entityClasses);
            return entityClasses;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Cannot get all the enity class: " + e.getMessage());
        }

    }

}
