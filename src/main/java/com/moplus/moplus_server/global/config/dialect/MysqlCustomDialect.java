package com.moplus.moplus_server.global.config.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.type.StandardBasicTypes;

public class MysqlCustomDialect extends MySQL8Dialect {

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        functionContributions.getFunctionRegistry().registerPattern(
                "match",
                "match (?1) against (?2 in boolean mode)",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.DOUBLE)
        );
    }
} 