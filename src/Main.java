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
        switch (new Scanner(System.in).nextByte()){
            case 1 -> {
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
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
                System.out.println("Wychodzenie z aplikacji...");
                return;
            }
            default -> System.out.println("Nie ma takiej opcji!");
        }
    }
}