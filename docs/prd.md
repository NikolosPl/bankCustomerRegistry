# Dokumentacja Projektu: Baza Danych Klientów Banku (BankCustomerRegistry)

## 1. Cel projektu
Stworzenie prostej, wydajnej aplikacji backendowej łączącej się z bazą danych PostgreSQL za pomocą interfejsu JDBC. System ma umożliwiać bezpieczne zarządzanie danymi klientów banku, przypisywanie im unikalnych numerów kont oraz zaawansowane wyszukiwanie, ze szczególnym uwzględnieniem czystości kodu SQL i optymalnego zarządzania zasobami połączenia w celu eliminacji wycieków pamięci.

## 2. Format danych i Schemat bazy (Input/Database)
Aplikacja operuje na relacyjnej strukturze danych w PostgreSQL. Każdy rekord klienta w bazie musi być zdefiniowany według następującego schematu:

**Tabela `customers`:**
* `id` (SERIAL / BIGSERIAL) – Główny klucz unikalny, generowany automatycznie.
* `first_name` (VARCHAR(50)) – Imię klienta (pole wymagane).
* `last_name` (VARCHAR(100)) – Nazwisko klienta (pole wymagane, indeksowane).
* `pesel` (CHAR(11)) – Numer PESEL (pole wymagane, unikalne, indeksowane).
* `account_number` (VARCHAR(26)) – Standardowy numer konta bankowego (pole unikalne).

**Przykład poprawnego rekordu:**
`Jan;Kowalski;92051412345;10102030405060708090102030`

---

## 3. Wymagane Funkcjonalności (Logika Biznesowa)

### A. Zarządzanie Połączeniami i SQL (Bezpieczeństwo Techniczne)
* **Zamykanie Zasobów:** Bezwzględne stosowanie konstrukcji *try-with-resources* dla obiektów `Connection`, `PreparedStatement` oraz `ResultSet` w celu zapobiegania wyciekom pamięci i blokowaniu wątków bazy danych.
* **Ochrona przed SQL Injection:** Całkowity zakaz konkatenacji ciągów znaków w zapytaniach SQL. Każde zapytanie musi wykorzystywać sparametryzowane szablony `PreparedStatement`.
* **Zarządzanie Transakcjami:** Obsługa transakcyjności (`commit`/`rollback`) przy operacjach zapisu, aby zapewnić integralność danych.

### B. Operacje na Danych (Logika Aplikacji)
* **Dodawanie Klienta:** Rejestracja nowego klienta z walidacją unikalności numeru PESEL przed wykonaniem zapisu.
* **Przypisywanie Numeru Konta:** Możliwość nadania lub aktualizacji unikalnego, 26-cyfrowego numeru konta dla istniejącego klienta.
* **Wyszukiwanie po Nazwisku:** Wyszukiwanie klientów pasujących do frazy przy użyciu operatora `LIKE` (np. `Kowal%`) lub dokładnego dopasowania.
* **Wyszukiwanie po PESEL:** Szybkie, jednoznaczne wyszukiwanie klienta na podstawie dokładnego 11-cyfrowego numeru PESEL.

---

## 4. Wynik Działania (Output)
Program komunikuje się z użytkownikiem za pomocą interfejsu konsolowego (CLI) i zwraca następujące komunikaty:

1. **Statusy Operacji:** Potwierdzenia sukcesu lub czytelne komunikaty o błędach (np. `[BŁĄD] Klient o podanym PESEL już istnieje w bazie danych`).
2. **Prezentacja Wyników:** Lista wyszukanych klientów sformatowana w postaci czytelnej tabeli tekstowej:
   `[ID] | [PESEL] | [Nazwisko Imię] | [Numer Konta]`
3. **Logi Wydajnościowe:** Informacja o czasie wykonania zapytania oraz statusie zamknięcia połączenia JDBC.

---

## 5. Architektura Plików (Struktura projektu)
Podział na klasy zgodnie z architekturą warstwową i zasadą Single Responsibility:

* **`Customer`** – Klasa typu rekord reprezentująca encję klienta w systemie.
* **`DatabaseConnectionManager`** – Klasa odpowiedzialna wyłącznie za inicjalizację, konfigurację (sterownik, URL, login, hasło) oraz bezpieczne dostarczanie obiektów `Connection`.
* **`CustomerService`** – Warstwa biznesowa i dostępu do danych, zawierająca czyste zapytania SQL, logikę JDBC, walidację danych (np. długość PESEL, format konta).
* **`Main`** – Menu konsolowe sterujące aplikacją, pobierające dane od użytkownika i wywołujące odpowiednie usługi.

---

## 6. Rozszerzenia (Opcjonalne / Dla ambitnych)
* **Connection Pooling:** Wdrożenie biblioteki HikariCP do zarządzania pulą połączeń zamiast tworzenia nowego połączenia dla każdej operacji.
* **Baza In-Memory do Testów:** Konfiguracja bazy H2 w trybie kompatybilności z PostgreSQL do automatycznych testów jednostkowych i integracyjnych repozytorium.