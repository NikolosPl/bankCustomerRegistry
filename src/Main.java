void main() throws Exception{
    Scanner scanner = new Scanner(System.in);
    CustomerService service = new CustomerService();
    System.out.println("=== BANK CUSTOMER REGISTRY ===");
    while(true){
        System.out.println("1. Dodaj nowego klienta");
        System.out.println("2. Przypisz numer konta");
        System.out.println("3. Wyszukaj po nazwisku");
        System.out.println("4. Wyszukaj po PESEL");
        System.out.println("5. Wyjście\n");
        System.out.print("Wybierz opcje: ");
        switch (scanner.next()){
            case "1" -> {
                System.out.print("\n[DODAWANIE] Podaj imie: ");
                String imie = scanner.next();
                System.out.print("[DODAWANIE] Podaj nazwisko: ");
                String nazwisko = scanner.next();
                System.out.print("[DODAWANIE] Podaj PESEL: ");
                String pesel = scanner.next();
                System.out.println();
                service.addClient(imie,nazwisko,pesel);
                System.out.println();
            }
            case "2" -> {
                System.out.print("\n[DODAWANIE] Podaj pesel: ");
                String pesel = scanner.next();
                System.out.print("[DODAWANIE] Podaj numer konta: ");
                String numerKonta = scanner.next();
                System.out.println();
                service.addNumberAccount(pesel, numerKonta);
                System.out.println();
            }
            case "3" -> {
                System.out.print("\n[WYSZUKIWANIE] Podaj nazwisko (lub fragment): ");
                String lastName = scanner.next();
                System.out.println("\nWyniki wyszukiwania: ");
                service.searchByLastName(lastName);
            }
            case "4" -> {
                System.out.print("\n[WYSZUKIWANIE] Podaj pesel (lub fragment): ");
                String pesel = scanner.next();
                System.out.println("\nWyniki wyszukiwania: ");
                service.searchByPesel(pesel);
            }
            case "5" -> {
                System.out.println("\nWychodzenie z aplikacji...");
                return;
            }
            default -> System.out.println("\nNie ma takiej opcji!\n");
        }
    }
}