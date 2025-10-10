public abstract class account {
    private String accountNumber;
    private double balance;
    private customer owner;
    private String accountType;
    private String status; // e.g., "active", "closed"
    private String creationDate;
    private String lastAccessedDate;
    private double interestRate; // For interest-bearing accounts
    private String currency; // e.g., "USD", "EUR"
    private String branchLocation;

    public account(String accountNumber, double balance, customer owner, String accountType, String status,
                   String creationDate, String lastAccessedDate, double interestRate, String currency,
                   String branchLocation) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
        this.accountType = accountType;
        this.status = status;
        this.creationDate = creationDate;
        this.lastAccessedDate = lastAccessedDate;
        this.interestRate = interestRate;
        this.currency = currency;
        this.branchLocation = branchLocation;

        void deposit;(double amount) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Deposited BWP: " + amount);
            } else {
                System.out.println("Deposit amount must be positive.");

                public abstract void withdraw(double amount);

                public double getBalance() {
                    return balance;

                    public String getDetails() {
                        return accountNumber+", "+balance+", "+owner+", "+accountType+", "+status+", "+creationDate+", "+
                                lastAccessedDate+", "+interestRate+", "+currency+", "+branchLocation;
                    }
}
