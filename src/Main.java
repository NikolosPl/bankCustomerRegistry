void main() throws Exception{
    TableGenerator tableGenerator = new TableGenerator();
    CustomerService customers = new CustomerService();
    customers.validateCustomers();
    ArrayList<Customer> customersData = customers.getValidatedCustomers();
    tableGenerator.generateTable(customersData);
}