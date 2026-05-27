import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CustomerService {
    private final TableGenerator generator;
    public CustomerService() {
        this.generator = new TableGenerator();
    }

    public void addClient(String firstName, String lastName, String pesel) throws Exception {
        if(!pesel.matches("\\d{11}")){
            System.out.println("PESEL musi składać sie z 11 cyfr");
            return;
        }
        long start = System.nanoTime();
        try(
                Connection conn = new DataBaseConnectionManager().connect();
                PreparedStatement insertInto = conn.prepareStatement("INSERT INTO customers(last_name, first_name, pesel) VALUES(?, ?, ?)")
                ){
            insertInto.setString(1, lastName);
            insertInto.setString(2, firstName);
            insertInto.setString(3, pesel);
            insertInto.executeUpdate();
        } catch (PSQLException e){
            switch (e.getSQLState()){
                case "23505" -> System.out.println("[BŁĄD] Klient z takim PESEL już istnieje.");
                case "23502" -> System.out.println("[BŁĄD] Brakuje wymaganego pola.");
                case "08001", "08006" -> System.out.println("[BŁĄD] Problem z połączeniem.");
                default -> System.out.println("[BŁĄD] " + e.getMessage());
            }
            return;
        }
        long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("[SUKCES] Klient " + firstName + " " + lastName + " został pomyślnie dodany do bazy danych.");
        System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
    }
    public void addClient(String firstName, String lastName, String pesel, String accountNumber) throws Exception {
        if(!pesel.matches("\\d{11}")){
            System.out.println("[BŁĄD] PESEL musi składać sie z 11 cyfr");
            return;
        }
        if(!pesel.matches("\\d{26}")){
            System.out.println("[BŁĄD] Numer konta musi składać sie z 26 cyfr");
        }
        long start = System.nanoTime();
        try(
                Connection conn = new DataBaseConnectionManager().connect();
                PreparedStatement insertInto = conn.prepareStatement("INSERT INTO customers(last_name, first_name, pesel, account_number) VALUES(?, ?, ?, ?)")
                ){
            insertInto.setString(1, lastName);
            insertInto.setString(2, firstName);
            insertInto.setString(3, pesel);
            insertInto.setString(4, accountNumber);
            insertInto.executeUpdate();
        } catch (PSQLException e){
            switch (e.getSQLState()){
                case "23505" -> System.out.println("[BŁĄD] Klient z takim PESEL już istnieje.");
                case "23502" -> System.out.println("[BŁĄD] Brakuje wymaganego pola.");
                case "08001", "08006" -> System.out.println("[BŁĄD] Problem z połączeniem.");
                default -> System.out.println("[BŁĄD] " + e.getMessage());
            }
            return;
        }
        long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("[SUKCES] Klient " + firstName + " " + lastName + " został pomyślnie dodany do bazy danych.");
        System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
    }

    public void addNumberAccount(String pesel, String accountNumber) throws Exception {
        if(!accountNumber.matches("\\d{26}")){
            System.out.println("Numer konta musi składać sie z 26 cyfr");
            return;
        }
        if(!pesel.matches("\\d{11}")){
            System.out.println("Pesel musi składać sie z 11 cyfr");
            return;
        }
        long start = System.nanoTime();
        try(
                Connection conn = new DataBaseConnectionManager().connect();
                PreparedStatement check = conn.prepareStatement("SELECT * FROM customers WHERE pesel = ?");
                PreparedStatement insertAccountNumber = conn.prepareStatement("UPDATE customers SET account_number = ? WHERE pesel = ?")
                ){
            check.setString(1, pesel);
            try(ResultSet rs = check.executeQuery()){
                if(!rs.next()) {
                    System.out.println("[BŁĄD] W bazie nie ma klienta z takim peselem.");
                    return;
                }
            }
            insertAccountNumber.setString(1, accountNumber);
            insertAccountNumber.setString(2, pesel);
            insertAccountNumber.executeUpdate();
        } catch (PSQLException _){
            System.out.println("[BŁĄD] Nie udało sie przypisać numeru konta do klienta o peselu: " + pesel);
            return;
        }
        long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("[SUKCES] Poprawnie dodano numer konta do klienta o peselu: " + pesel);
        System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
    }

    public void searchByLastName(String lastName) throws Exception{
        ArrayList<Customer> customers = new ArrayList<>();
        long start = System.nanoTime();
        try(
                Connection conn = new DataBaseConnectionManager().connect();
                PreparedStatement selectLastName = conn.prepareStatement("SELECT * FROM customers WHERE last_name LIKE ?")
                ){
            selectLastName.setString(1, "%" + lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase() + "%");
            try(ResultSet resultSet = selectLastName.executeQuery()){
                while(resultSet.next()){
                    customers.add(new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("pesel"),
                        resultSet.getString("account_number"),
                        null
                    ));
                }
            }
            if(customers.isEmpty()){
                System.out.println("\n[BŁĄD] Nie ma klienta o takim nazwisku.\n");
                return;
            }
        }
        generator.generateTable(customers);
        System.out.println();
        long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
    }
    public void searchByPesel(String pesel) throws Exception{
        ArrayList<Customer> customers = new ArrayList<>();
        long start = System.nanoTime();
        try(
                Connection conn = new DataBaseConnectionManager().connect();
                PreparedStatement selectPesel = conn.prepareStatement("SELECT * FROM customers WHERE pesel = ?")
                ){
            selectPesel.setString(1, pesel);
            try(ResultSet resultSet = selectPesel.executeQuery()) {
                while (resultSet.next()) {
                    customers.add(new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("pesel"),
                            resultSet.getString("account_number"),
                            null
                    ));
                }
            }
            if(customers.isEmpty()){
                System.out.println("\n[BŁĄD] Nie ma klienta o takim peselu.\n");
                return;
            }
        }
        generator.generateTable(customers);
        System.out.println();
        long czasWykonania = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("[JDBC LOG] Zapytanie wykonane w " + czasWykonania + "ms. Połączenie bezpiecznie zamknięte.");
    }
}
