void main() throws Exception {
    CustomerRepository customerRepo = new CustomerRepository();
    ArrayList<Customer> customers = customerRepo.getCustomersData();
    System.out.println("[ID] | [PESEL] | [Nazwisko, Imie] | [Numer konta]");
    for (Customer customer : customers){
        System.out.println(customer.id() + " | " + customer.pesel() + " | " + customer.last_name() + " " + customer.first_name() + " | " + customer.accountNumber());
    }
}