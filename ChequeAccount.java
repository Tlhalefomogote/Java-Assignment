package model;

public class ChequeAccount extends model.Account implements model.Depositable, model.Withdrawable {
    private model.Company employer;

    public ChequeAccount(String accNo, model.Customer c, String branch, double opening, model.Company employer) {
        super(accNo, c, branch, opening);
        if (employer == null)
            throw new IllegalArgumentException("Employer required for ChequeAccount");
        this.employer = employer;
    }

    public model.Company getEmployer() { return employer; }
}
