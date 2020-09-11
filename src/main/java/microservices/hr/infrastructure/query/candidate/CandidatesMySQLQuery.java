package microservices.hr.infrastructure.query.candidate;

import java.util.LinkedList;
import java.util.List;

import microservices.hr.domain.candidate.query.Candidate;
import microservices.hr.domain.candidate.query.CandidatesQuery;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.database.mysql.ResultSet;

final public class CandidatesMySQLQuery implements CandidatesQuery {
    private final MySQLClient mySQLClient;
    private final CandidateMapper candidateMapper;
    private Filters filters;

    public CandidatesMySQLQuery(MySQLClient mySQLClient, CandidateMapper candidateMapper) {
        this.mySQLClient = mySQLClient;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public List<Candidate> records() {
        List<Candidate> records = new LinkedList<>();

        ResultSet resultSet = mySQLClient
                .connection()
                .selectQuery()
                .from("candidates AS candidate")
                .select(
                        "candidate.id AS `candidate.id`",
                        "candidate.firstName AS `candidate.firstName`",
                        "candidate.lastName AS `candidate.lastName`",
                        "candidate.invited AS `candidate.invited`",
                        "candidate.hired AS `candidate.hired`"
                )
                .toPreparedStatement()
                .withResult();

        while (resultSet.next()) {
            records.add(this.candidateMapper.candidate(resultSet));
        }

        return records;
    }

    @Override
    public CandidatesQuery withFilters(Filters filters) {
        this.filters = filters;
        return this;
    }
}
