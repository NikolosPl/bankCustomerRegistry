void main() throws Exception{
    List<Customer> customers = new CustomerRepository().getCustomerData();
    TableGenerator tableGenerator = new TableGenerator();
    tableGenerator.generateTable(customers);
}