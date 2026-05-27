import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class CustomerService {
    private final ArrayList<Customer> validatedCustomers;
    private final ArrayList<Customer> rejectedCustomers;
    public CustomerService() {
        this.validatedCustomers = new ArrayList<>();
        this.rejectedCustomers = new ArrayList<>();
    }
    public void validateCustomers() throws Exception {
    try (Connection conn = new DataBaseConnectionManager().connect();
         PreparedStatement ps = conn.prepareStatement("SELECT id, first_name, last_name, pesel, account_number FROM customers;");
         ResultSet rs = ps.executeQuery()){
        HashSet<String> pesels = new HashSet<>();
        HashSet<String> accountNumbers = new HashSet<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String pesel = rs.getString("pesel");
            String accountNumber = rs.getString("account_number");

            Customer customer = new Customer(id, firstName, lastName, pesel, accountNumber,null);

            if(!customer.pesel().matches("\\d{11}")){
                customer = new Customer(id, firstName, lastName, pesel, accountNumber,"[BŁĄD] PESEL musi składać się tylko i wyłącznie z 11 cyfr");
                this.rejectedCustomers.add(customer);
                continue;
            }
            if(pesels.contains(customer.pesel())){
                customer = new Customer(id, firstName, lastName, pesel, accountNumber,"[BŁĄD] Klient o podanym PESEL już istnieje w bazie danych");
                this.rejectedCustomers.add(customer);
                continue;
            } else{
                pesels.add(customer.pesel());
            }

            if(customer.accountNumber() != null && accountNumbers.contains(customer.accountNumber())){
                customer = new Customer(id, firstName, lastName, pesel, accountNumber,"[BŁĄD] Klient o podanym numerze konta już istnieje");
                this.rejectedCustomers.add(customer);
                continue;
            } else if(customer.accountNumber() != null){
                accountNumbers.add(customer.accountNumber());
            }

            this.validatedCustomers.add(customer);
        }
        }
    }
    public ArrayList<Customer> getValidatedCustomers() {
        return  this.validatedCustomers;
    }
    public ArrayList<Customer> getRejectedCustomers() {
        return  this.rejectedCustomers;
    }

    public void addClient(String firstName, String lastName, String pesel, String accountNumber) throws Exception {
        if(!pesel.matches("\\d{11}")){
            System.out.println("PESEL musi skladac sie z 11 cyfr");
            return;
        }
        if(!accountNumber.matches("\\d{26}")){
            System.out.println("Numer konta musi skladac sie z 26 cyfr");
        }
        try (Connection conn = new DataBaseConnectionManager().connect();
             PreparedStatement insertInto = conn.prepareStatement("EXPLAIN (ANALYZE, FORMAT JSON) INSERT INTO customers(last_name, first_name, pesel, account_number) VALUES(?, ?, ?, ?)")) {
            insertInto.setString(1, lastName);
            insertInto.setString(2, firstName);
            insertInto.setString(3, pesel);
            insertInto.setString(4, accountNumber);
            insertInto.executeUpdate();
            System.out.println("[SUKCES] Klient " + firstName + " " + lastName + " został pomyślnie dodany do bazy danych.");
            System.out.println("[JDBC LOG] Zapytanie wykonane w " + insertInto.executeQuery().getString(1) + "ms. Połączenie bezpiecznie zamknięte.");
        } catch (PSQLException _){

        }


    }
    public void addClient(String firstName, String lastName, String pesel) throws Exception {
        if(!pesel.matches("\\d{11}")){
            System.out.println("PESEL musi skladac sie z 11 cyfr");
            return;
        }
        try (Connection conn = new DataBaseConnectionManager().connect();
             PreparedStatement insertInto = conn.prepareStatement("EXPLAIN (ANALYZE , FORMAT JSON ) INSERT INTO customers(last_name, first_name, pesel) VALUES(?, ?, ?)")) {
            insertInto.setString(1, lastName);
            insertInto.setString(2, firstName);
            insertInto.setString(3, pesel);
            insertInto.executeUpdate();
            System.out.println("[SUKCES] Klient " + firstName + " " + lastName + " został pomyślnie dodany do bazy danych.");
            System.out.println("[JDBC LOG] Zapytanie wykonane w " + insertInto.executeQuery().getString(1) + "ms. Połączenie bezpiecznie zamknięte.");
        } catch (PSQLException _){
            System.out.println("[BŁĄD] Klient nie został dodany.");
        }


    }

    public void addNumberAccount(String pesel, String accountNumber) throws Exception {
        if(!accountNumber.matches("\\d{26}")){
            System.out.println("Numer konta musi skladac sie z 26 cyfr");
            return;
        }
        try(Connection conn = new DataBaseConnectionManager().connect()){
            long start = System.nanoTime();
            PreparedStatement insertAccountNumber = conn.prepareStatement("UPDATE customers SET account_number = ? WHERE pesel = ?");
            insertAccountNumber.setString(1, accountNumber);
            insertAccountNumber.setString(2, pesel);
            long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            System.out.println("Poprawnie dodano numer konta do uzytkownika o peselu: " + pesel);
            System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
        } catch (PSQLException _){
            System.out.println("[BŁĄD] Nie udało sie przypisac numeru konta do uzytkownika o peselu: " + pesel);
        }
    }
}
