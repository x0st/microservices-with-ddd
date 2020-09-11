package microservices.bookkeeping.domain.employee;

final class Employee {
    private String firstName;
    private String lastName;
    private Integer bankAccount;

    public Employee(String firstName, String lastName, Integer bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
    }

    public void setBankAccount(Integer bankAccount) {
        this.bankAccount = bankAccount;
    }
}
