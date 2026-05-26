import java.util.ArrayList;

public class CustomerRepository {
    private final ArrayList<Customer> customers;
    public CustomerRepository() throws Exception {
        this.customers = new CustomerService().getValidatedCustomers();
    }
    public ArrayList<Customer> getCustomersData(){
        return  this.customers;
    }
}
