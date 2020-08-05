package com.kero.health.core.dao.utils;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class ModifiedPostgres10Dialect extends PostgreSQL10Dialect {

    public ModifiedPostgres10Dialect() {
        super();
        
        registerFunction("dateoverlaps", new StandardSQLFunction("overlaps", StandardBasicTypes.BOOLEAN));
    }
}
