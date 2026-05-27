# BankCustomerRegistry - Database Customer Management System

## 📌 O projekcie
BankCustomerRegistry to lekka aplikacja backendowa napisana w czystej Javie, służąca do bezpiecznego zarządzania danymi klientów banku w relacyjnej bazie danych PostgreSQL. Projekt kładzie szczególny nacisk na optymalne zarządzanie zasobami bazy danych (eliminacja wycieków pamięci) oraz najwyższe standardy bezpieczeństwa kodu SQL.

## 🚀 Główne Funkcjonalności
*   **Bezpieczne Zarządzanie Zasobami:** Wykorzystanie mechanizmu *try-with-resources* do automatycznego zamykania połączeń (`Connection`, `PreparedStatement`, `ResultSet`), co zapobiega wyciekom pamięci.
*   **Ochrona przed SQL Injection:** Całkowite wyeliminowanie podatności dzięki stosowaniu wyłącznie sparametryzowanych zapytań `PreparedStatement`.
*   **Zarządzanie Transakcjami:** Obsługa transakcyjności (`commit`/`rollback`) gwarantująca spójność i integralność danych bankowych.
*   **Operacje na danych:**
    *   Rejestracja nowych klientów wraz z weryfikacją unikalności numeru PESEL.
    *   Nadawanie oraz aktualizacja unikalnych, 26-cyfrowych numerów kont bankowych.
    *   Zaawansowane wyszukiwanie po nazwisku (z użyciem operatora `LIKE`) oraz precyzyjne wyszukiwanie po numerze PESEL.

## 🛠 Technologia
*   **Język:** Java 26
*   **Baza danych:** PostgreSQL
*   **Interfejs bazy danych:** JDBC (Java Database Connectivity)
*   **Architektura:** Warstwowa, oparta o zasady Clean Code oraz Single Responsibility Principle.

## 📁 Struktura Projektu
*   `Customer.java` – Klasa typu rekord reprezentująca encję klienta w systemie.
*   `DatabaseConnectionManager.java` – Odpowiedzialna za konfigurację parametrów bazy danych oraz bezpieczne dostarczanie obiektów połączeń.
*   `CustomerService.java` – Warstwa biznesowa i dostępu do danych, zawierająca czyste zapytania SQL, logikę JDBC, walidację danych (np. długość PESEL, format konta).
*   `Main.java` – Interfejs wiersza poleceń (CLI) sterujący aplikacją i pobierający dane od użytkownika.

## 📋 Struktura i Inicjalizacja Bazy Tabela (SQL)
Przed uruchomieniem aplikacji należy utworzyć w bazie danych PostgreSQL tabelę za pomocą poniższego skryptu:
```sql
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    pesel CHAR(11) UNIQUE NOT NULL,
    account_number VARCHAR(26) UNIQUE
);

-- Indeksy optymalizujące proces wyszukiwania klientów
CREATE INDEX idx_customers_last_name ON customers(last_name);
CREATE INDEX idx_customers_pesel ON customers(pesel);
```

## ⚙️ Konfiguracja i Uruchomienie
1. Klonowanie i przygotowanie bazy
   Upewnij się, że masz uruchomioną bazę PostgreSQL z utworzoną tabelą według schematu z sekcji powyżej.
2. Konfiguracja połączenia
   Stwórz plik config.properties, skopiuj calość z config.properties.example i podstaw swoje dane:
```properties
db.url=jdbc:postgresql://localhost:5432/nazwa_bazy
db.username=nazwa_uzytkownika
db.password=twoje_haslo
```

## 💻 Przykład działania (Interfejs CLI)
Aplikacja po uruchomieniu wita użytkownika tekstowym menu sterującym:
```text
=== BANK CUSTOMER REGISTRY ===
1. Dodaj nowego klienta
2. Przypisz numer konta
3. Wyszukaj po nazwisku
4. Wyszukaj po PESEL
5. Wyjście

Wybierz opcję: 1

[DODAWANIE] Podaj imię: Jan
[DODAWANIE] Podaj nazwisko: Kowalski
[DODAWANIE] Podaj PESEL: 92051412345

[SUKCES] Klient Jan Kowalski został pomyślnie dodany do bazy danych.
[JDBC LOG] Zapytanie wykonane w 14ms. Połączenie bezpiecznie zamknięte.
```
### Przykład wyszukiwania:
```text
Wybierz opcję: 3
[WYSZUKIWANIE] Podaj nazwisko (lub fragment): Kowal%

Wyniki wyszukiwania:
--------------------------------------------------------------------------------
ID   | PESEL       | Nazwisko i Imię     | Numer Konta
--------------------------------------------------------------------------------
1    | 92051412345 | Kowalski Jan        | BRAK RACHUNKU
--------------------------------------------------------------------------------
[JDBC LOG] Pobrano rekordów: 1. Zasoby ResultSet oraz PreparedStatement zamknięte.
```