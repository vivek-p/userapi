package com.skillset.userapi.config;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class PostgreSQL94CustomDialect extends PostgreSQL94Dialect {

    public PostgreSQL94CustomDialect() {
        super();
        this.registerHibernateType(1111, String.class.getName());
    }

}