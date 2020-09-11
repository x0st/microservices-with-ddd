package microservices.hr.domain.candidate.query;

import java.util.UUID;

final public class Candidate {
    public UUID id;
    public String firstName;
    public String lastName;
    public Boolean hired;
    public Boolean invited;

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hired=" + hired +
                ", invited=" + invited +
                '}';
    }
}
