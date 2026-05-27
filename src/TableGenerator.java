import java.util.List;

public class TableGenerator {
    public void generateTable(List<Customer> customers){
        int wID = "ID".length();
        int wPesel = "PESEL".length();
        int wName = "Nazwisko Imie".length();
        int wAccount = "Numer konta".length();

        for(Customer c : customers){
            wID = Math.max(wID, String.valueOf(c.id()).length());
            wPesel = Math.max(wPesel, c.pesel().length());
            wName = Math.max(wName, (c.lastName() + ", " + c.firstName()).length());
            wAccount = Math.max(wAccount, c.accountNumber() != null ? c.accountNumber().length() : "BRAK RACHUNKU".length());
        }
        String format = "%-" + wID + "s | %-" + wPesel + "s | %-" + wName + "s | %-" + wAccount + "s%n";
        String sep = "-".repeat(wID + wPesel + wName + wAccount + 9); // 3 separatory x 3 znaki (" | ")
        System.out.println(sep);
        System.out.printf(format, "ID", "PESEL", "Nazwisko Imie", "Numer konta");
        System.out.println(sep);
        for (Customer c : customers){
            System.out.printf(format,
                    c.id(),
                    c.pesel(),
                    c.lastName() + " " + c.firstName(),
                    c.accountNumber() != null ? c.accountNumber() : "BRAK RACHUNKU"
            );
        }
        System.out.println(sep);
    }
}
