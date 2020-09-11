package microservices.hr.domain.candidate;

import microservices.hr.core.Repository;
import microservices.hr.domain.candidate.exception.CandidateNotFound;

public interface CandidateRepository extends Repository<Candidate, CandidateNotFound> {

}
