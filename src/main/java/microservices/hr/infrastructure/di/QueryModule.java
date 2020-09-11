package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import microservices.hr.domain.candidate.query.CandidatesQuery;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.query.candidate.CandidateMapper;
import microservices.hr.infrastructure.query.candidate.CandidatesMySQLQuery;

final public class QueryModule extends AbstractModule {
    @Provides
    static CandidatesQuery provideCandidatesQuery(MySQLClient mySQLClient) {
        return new CandidatesMySQLQuery(mySQLClient, new CandidateMapper());
    }
}
