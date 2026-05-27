void main() throws Exception{
    Scanner scanner = new Scanner(System.in);
    CustomerService service = new CustomerService();
    System.out.println("=== BANK CUSTOMER REGISTRY ===");
    while(true){
        System.out.println("1. Dodaj nowego klienta");
        System.out.println("2. Dodaj nowego klienta (z numerem konta)");
        System.out.println("3. Przypisz numer konta");
        System.out.println("4. Wyszukaj po nazwisku");
        System.out.println("5. Wyszukaj po PESEL");
        System.out.println("6. Wyjście\n");
        System.out.print("Wybierz opcje: ");
        switch (scanner.nextLine()){
            case "1" -> {
                System.out.print("\n[DODAWANIE] Podaj imię: ");
                String imie = scanner.nextLine();
                System.out.print("[DODAWANIE] Podaj nazwisko: ");
                String nazwisko = scanner.nextLine();
                System.out.print("[DODAWANIE] Podaj PESEL: ");
                String pesel = scanner.nextLine();
                System.out.println();
                service.addClient(imie,nazwisko,pesel);
                System.out.println();
            }
            case "2" -> {
                System.out.print("\n[DODAWANIE] Podaj imię: ");
                String imie = scanner.nextLine();
                System.out.print("\n[DODAWANIE] Podaj nazwisko: ");
                String nazwisko = scanner.nextLine();
                System.out.print("\n[DODAWANIE] Podaj PESEL: ");
                String pesel = scanner.nextLine();
                System.out.print("\n[DODAWANIE] Podaj numer konta: ");
                String numerKonta = scanner.nextLine();
                System.out.println();
                service.addClient(imie,nazwisko,pesel,numerKonta);
                System.out.println();
            }
            case "3" -> {
                System.out.print("\n[DODAWANIE] Podaj pesel: ");
                String pesel = scanner.nextLine();
                System.out.print("[DODAWANIE] Podaj numer konta: ");
                String numerKonta = scanner.nextLine();
                System.out.println();
                service.addNumberAccount(pesel, numerKonta);
                System.out.println();
            }
            case "4" -> {
                System.out.print("\n[WYSZUKIWANIE] Podaj nazwisko (lub fragment): ");
                String lastName = scanner.nextLine();
                System.out.println("\nWyniki wyszukiwania: ");
                service.searchByLastName(lastName);
            }
            case "5" -> {
                System.out.print("\n[WYSZUKIWANIE] Podaj pesel: ");
                String pesel = scanner.nextLine();
                System.out.println("\nWyniki wyszukiwania: ");
                service.searchByPesel(pesel);
            }
            case "6" -> {
                System.out.println("\nWychodzenie z aplikacji...");
                return;
            }
            default -> System.out.println("\nNie ma takiej opcji!\n");
        }
    }
}