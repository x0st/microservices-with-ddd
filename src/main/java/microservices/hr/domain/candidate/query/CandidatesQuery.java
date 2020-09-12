package microservices.hr.domain.candidate.query;

import java.util.List;

public interface CandidatesQuery {
    List<Candidate> records();

    CandidatesQuery withFilters(Filters filters);

    final public static class Filters {
        private Boolean hired;
        private Boolean invited;
        private String firstName;
        private String lastName;

        public Filters() {
            this.hired = null;
            this.invited = null;
            this.firstName = null;
            this.lastName = null;
        }

        public Filters withHired(Boolean hired) {
            this.hired = hired;
            return this;
        }

        public Filters withInvited(Boolean invited) {
            this.invited = invited;
            return this;
        }

        public Filters withFirstName(String value) {
            this.firstName = value;
            return this;
        }

        public Filters withLastName(String value) {
            this.lastName = value;
            return this;
        }
    }
}
