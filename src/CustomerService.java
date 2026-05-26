import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

public class CustomerService {
    private final ArrayList<Customer> validatedCustomers;
    private final ArrayList<Customer> rejectedCustomers;
    private final DataBaseConnectionManager dataBaseConnectionManager;
    public CustomerService() throws Exception {
        this.validatedCustomers = new ArrayList<>();
        this.rejectedCustomers = new ArrayList<>();
        this.dataBaseConnectionManager = new DataBaseConnectionManager();
        this.validateCustomers();
    }
    public void validateCustomers() throws Exception {
    try (Connection conn = this.dataBaseConnectionManager.connect();
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

            if(customer.pesel().length() != 11){
                customer = new Customer(id, firstName, lastName, pesel, accountNumber,"[BŁĄD] PESEL musi składać się z 11 cyfr");
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

            if(accountNumbers.contains(customer.accountNumber()) && customer.accountNumber() != null){
                customer = new Customer(id, firstName, lastName, pesel, accountNumber,"[BŁĄD] Klient o podanym numerze konta już istnieje");
                this.rejectedCustomers.add(customer);
                continue;
            } else{
                accountNumbers.add(customer.accountNumber());
            }

            this.validatedCustomers.add(customer);
        }
        }
    }
    public ArrayList<Customer> getValidatedCustomers() throws Exception {
        return  this.validatedCustomers;
    }
    public ArrayList<Customer> getRejectedCustomers() throws Exception {
        return  this.rejectedCustomers;
    }
}
