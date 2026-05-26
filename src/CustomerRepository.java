import java.util.ArrayList;

public class CustomerRepository {
    private final CustomerService service;
    public CustomerRepository() throws Exception {
        this.service = new CustomerService();
    }
    public ArrayList<Customer> getCustomerData() throws Exception {
        return this.service.getValidatedCustomers();
    }
    public ArrayList<Customer> getRejectedCustomerData() throws Exception{
        return this.service.getRejectedCustomers();
    }
}
