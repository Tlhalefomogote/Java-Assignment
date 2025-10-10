public class ChequeAccount extends Account{
    private String employerName;
    private String jobTitle;
    private double monthlyIncome;
    private employerAddress;
    private String employerPhoneNumber;
    private String employerEmail;

    public ChequeAccount(String accountNumber, double balance, customer owner, String accountType, String status,
                         String creationDate, String lastAccessedDate, double interestRate, String currency,
                         String branchLocation, String employerName, String jobTitle, double monthlyIncome,
                         String employerAddress, String employerPhoneNumber, String employerEmail) {
        super(accountNumber, balance, owner, accountType, status, creationDate, lastAccessedDate, interestRate,
                currency, branchLocation);
        this.employerName = employerName;
        this.jobTitle = jobTitle;
        this.monthlyIncome = monthlyIncome;
        this.employerAddress = employerAddress;
        this.employerPhoneNumber = employerPhoneNumber;
        this.employerEmail = employerEmail;

        @ Override
        public void withdraw(double amount) {
            if (amount > 0 && amount <= getBalance()) {
                double newBalance = getBalance() - amount;
                System.out.println("Withdrew BWP: " + amount=+ "from Cheque Account");
            } else {
                System.out.println("Insufficient funds or invalid amount.");
                System.out.println("Withdrawal failed.");

            
                }
        } 

        public String getEmployerDetails() {
            return "Employer Name: " + employerName + ", Job Title: " + jobTitle + ", Monthly Income: " + monthlyIncome +
                    ", Employer Address: " + employerAddress + ", Employer Phone: " + employerPhoneNumber +
                    ", Employer Email: " + employerEmail;
        }
    }

