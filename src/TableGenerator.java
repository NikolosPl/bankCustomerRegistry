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
            wName = Math.max(wName, (c.last_name() + ", " + c.first_name()).length());
            wAccount = Math.max(wAccount, c.accountNumber() != null ? c.accountNumber().length() : "BRAK".length());
        }
        String format = "%-" + wID + "s | %-" + wPesel + "s | %-" + wName + "s | %-" + wAccount + "s%n";
        String sep = "-".repeat(wID + wPesel + wName + wAccount + 9); // 3 separatory x 3 znaki (" | ")
        System.out.printf(format, "ID", "PESEL", "Nazwisko Imie", "Numer konta");
        System.out.println(sep);
        for (Customer c : customers){
            System.out.printf(format,
                    c.id(),
                    c.pesel(),
                    c.last_name() + " " + c.first_name(),
                    c.accountNumber() != null ? c.accountNumber() : "BRAK"
            );
        }
    }
    public void generateRejectedTable(List<Customer> customers){
        int wBlad = "BŁĄD".length();
        int wID = "ID".length();
        int wPesel = "PESEL".length();
        int wName = "Nazwisko Imie".length();
        int wAccount = "Numer konta".length();

        for(Customer c : customers){
            wBlad = Math.max(wBlad, c.errorInfo().length());
            wID = Math.max(wID, String.valueOf(c.id()).length());
            wPesel = Math.max(wPesel, c.pesel().length());
            wName = Math.max(wName, (c.last_name() + ", " + c.first_name()).length());
            wAccount = Math.max(wAccount, c.accountNumber() != null ? c.accountNumber().length() : "BRAK".length());
        }
        String format = "%-" + wBlad + "s | %-" + wID + "s | %-" + wPesel + "s | %-" + wName + "s | %-" + wAccount + "s%n";
        String sep = "-".repeat(wBlad + wID + wPesel + wName + wAccount + 12); // 4 separatory x 3 znaki (" | ")
        System.out.printf(format, "BŁĄD" , "ID", "PESEL", "Nazwisko Imie", "Numer konta");
        System.out.println(sep);
        for (Customer c : customers){
            System.out.printf(format,
                    c.errorInfo() != null ? c.errorInfo() : "",
                    c.id(),
                    c.pesel(),
                    c.last_name() + " " + c.first_name(),
                    c.accountNumber() != null ? c.accountNumber() : "BRAK"
            );
        }
    }
}
