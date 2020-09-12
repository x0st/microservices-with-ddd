package microservices.reception.domain.employee.query;

import java.util.List;

public interface EmployeesQuery {
    List<Employee> records();

    EmployeesQuery withFilters(Filters filters);

    final public static class Filters {
        private String firstName;
        private String lastName;

        public Filters() {
            this.firstName = null;
            this.lastName = null;
        }

        public Filters withFirstName(String value) {
            this.firstName = value;
            return this;
        }

        public Filters withLastName(String value) {
            this.lastName = value;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Boolean hasFirstName() {
            return null != firstName;
        }

        public Boolean hasLastName() {
            return null != lastName;
        }
    }
}
