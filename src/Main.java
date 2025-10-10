public class Main {
    public static <Customer> void main(String[] args) {
        Customer Tlhalefo = new Customer("Tlhalefo", "Mogote", "2025-05-15", "Gaborone", "71234567");
        ChequeAccount chequeAccount = new ChequeAccount("CA123", 1000.0, Tlhalefo, "Cheque", "Active", "2025-02-01", "2024-01-01", 0.05, "BWP", "Gaborone Branch",
                "Teacher", "71234567");
                SavingsAccount savings = new SavingsAccount ("SAV001","Gaborone");
                InvestmentAccount invest= new InvestmentAccount("INV001"."Gaborone );
                
                Tlhalefo.openAccount(chequeAccount);
                Tlhalefo.openAccount(savingsAccount);
                Thlalefo.openAccount(investmentAccount);
                
                savings.deposit(1000);
                invest.deposit(700);
                cheque.deposit(2000);

                savings.payInterest();
                invest.payInterest();
                cheque.withdraw(500);

                Tlhalefo.showAccountDetails();
                System.out.println("Customer: " + Tlhalefo.getFirstName() + " " + Tlhalefo.getLastName());
                showAccountDetails();
                System.out.println("Cheque Account Balance: BWP" + cheque.getBalance());
                System.out.println("Savings Account Balance: BWP" + savings.getBalance());
                System.out.println("Investment Account Balance: BWP" + invest.getBalance());
                
    }


}
