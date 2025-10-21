package model;

// Simple test class for console demo
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("Tlhalefo Bank");

        Customer tlhalefo = bank.createCustomer("Tlhalefo", "Mogote", "Ramotswa");
        Company bdf = new Company("Botswana Defence Force", "Gaborone HQ");

        bank.openSavings(tlhalefo, "Main Branch", 1000);
        bank.openInvestment(tlhalefo, "Main Branch", 1500);
        bank.openCheque(tlhalefo, "Main Branch", 500, bdf);

        // perform some actions
        tlhalefo.getAccounts().get(0).deposit(200);
        tlhalefo.getAccounts().get(1).withdraw(100);
        tlhalefo.getAccounts().get(2).deposit(300);

        bank.runMonthlyProcesses();

        System.out.println("\n--- Account Summary ---");
        for (Account a : tlhalefo.getAccounts())
            System.out.println(a);
    }
}
