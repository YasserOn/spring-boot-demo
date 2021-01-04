package com.xkcoding.mongodb.core;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

import java.util.Optional;

public class SoftDeleteMongoRepositoryFactory extends MongoRepositoryFactory {
    private final MongoOperations mongoOperations;

    public SoftDeleteMongoRepositoryFactory(MongoOperations mongoOperations) {
        super(mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key,
                                                                   QueryMethodEvaluationContextProvider evaluationContextProvider) {

        return super.getQueryLookupStrategy(key, evaluationContextProvider).map(this::createSoftDeleteQueryLookupStrategy);
    }

    private SoftDeleteMongoQueryLookupStrategy createSoftDeleteQueryLookupStrategy(QueryLookupStrategy strategy) {
        return new SoftDeleteMongoQueryLookupStrategy(strategy, mongoOperations);
    }
}
