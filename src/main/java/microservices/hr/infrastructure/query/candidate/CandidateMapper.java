package microservices.hr.infrastructure.query.candidate;

import microservices.hr.domain.candidate.query.Candidate;
import microservices.hr.infrastructure.database.mysql.ResultSet;

final public class CandidateMapper {
    public Candidate candidate(ResultSet resultSet) {
        Candidate candidate;

        candidate = new Candidate();
        candidate.id = resultSet.getUUID("candidate.id");
        candidate.firstName = resultSet.getString("candidate.firstName");
        candidate.lastName = resultSet.getString("candidate.lastName");
        candidate.invited = resultSet.getBoolean("candidate.invited");
        candidate.hired = resultSet.getBoolean("candidate.hired");

        return candidate;
    }
}
