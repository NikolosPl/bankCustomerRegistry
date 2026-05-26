public class Main {
    public static void main(String[] args) throws Exception{
        CustomerRepository customerRepo = new CustomerRepository();
        System.out.println("[ID] | [PESEL] | [Nazwisko, Imie] | [Numer konta]");
        for (Customer customer : customerRepo.getCustomerData()){
            System.out.println(customer.id() + " | " + customer.pesel() + " | " + customer.last_name() + " " + customer.first_name() + " | " + customer.accountNumber());
        }
        System.out.println("----------------------------------------------------------------------------------------");
        for (Customer customer : customerRepo.getRejectedCustomerData()){
            System.out.println(customer.errorInfo() + "||" + customer.id() + " | " + customer.pesel() + " | " + customer.last_name() + " " + customer.first_name() + " | " + customer.accountNumber());
        }

    }
}