package pl.klolo.workshops.logic;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.klolo.workshops.domain.*;
import pl.klolo.workshops.domain.Currency;
import pl.klolo.workshops.mock.HoldingMockGenerator;

import static java.util.Objects.nonNull;

class WorkShop {

  /**
   * Lista holdingów wczytana z mocka.
   */
  private final List<Holding> holdings;

  // Predykat określający czy użytkownik jest kobietą
  private final Predicate<User> isWoman = null;

  WorkShop() {
    final HoldingMockGenerator holdingMockGenerator = new HoldingMockGenerator();
    holdings = holdingMockGenerator.generate();
  }

  /**
   * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma.
   */
  long getHoldingsWhereAreCompanies() {
     int numberOfHolldings = 0;
    for (Holding holding: holdings) {
      if(holding.getCompanies().size() > 0 && nonNull(holding.getCompanies())){
        numberOfHolldings++;
      }
    }
  return numberOfHolldings;
  }

  /**
   * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma. Napisz to za pomocą strumieni.
   */
  long getHoldingsWhereAreCompaniesAsStream() {
    return holdings.stream()
            .filter(holding -> nonNull(holding.getCompanies()) && holding.getCompanies().size() > 0)
            .count();
  }

  /**
   * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy.
   */
  List<String> getHoldingNames() {

    List<String> holdingsNames= new ArrayList<>();
    for (Holding holding: holdings) {
      holdingsNames.add(holding.getName().toLowerCase());
    }
    return holdingsNames;

  }

  /**
   * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy. Napisz to za pomocą strumieni.
   */
  List<String> getHoldingNamesAsStream() {
    return holdings.stream()
            .map(holding -> holding.getName().toLowerCase())
            .collect(Collectors.toList());
  }

  /**
   * Zwraca nazwy wszystkich holdingów posortowane i sklejone w jeden string. String ma postać: (Coca-Cola, Nestle, Pepsico)
   */
  String getHoldingNamesAsString() {

    StringBuilder namesAsString = new StringBuilder();
    List<String> names = new ArrayList<>();

    for (Holding holding:  holdings) {
      names.add(holding.getName());
    }
    Collections.sort(names);

    namesAsString.append('(');
    for (String name:  names) {
      namesAsString.append(name);
      namesAsString.append(", ");
    }
    namesAsString.deleteCharAt(namesAsString.toString().length()-1);
    namesAsString.deleteCharAt(namesAsString.toString().length()-1);
    namesAsString.append(')');

    return namesAsString.toString();
  }

  /**
   * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico). Napisz to za pomocą strumieni.
   */
  String getHoldingNamesAsStringAsStream() {
    return holdings.stream()
            .map(holding -> holding.getName())
            .sorted()
            .collect(Collectors.joining(", ", "(", ")"));
  }

  /**
   * Zwraca liczbę firm we wszystkich holdingach.
   */
  long getCompaniesAmount() {

long numberOfCompanies = 0;

    for (Holding holding:  holdings) {
     numberOfCompanies +=  holding.getCompanies().size();
    }

    return numberOfCompanies;
  }

  /**
   * Zwraca liczbę firm we wszystkich holdingach. Napisz to za pomocą strumieni.
   */
  long getCompaniesAmountAsStream() {

    return holdings.stream()
            .map(holding -> holding.getCompanies().size())
            .mapToLong(value -> (long) value).sum();

  }

  /**
   * Zwraca liczbę wszystkich pracowników we wszystkich firmach.
   */
  long getAllUserAmount() {

    long allEmployees = 0;

    for (Holding holding: holdings) {

      for (Company company :holding.getCompanies()){

       allEmployees += company.getUsers().size();
      }
    }
    return allEmployees;
  }

  /**
   * Zwraca liczbę wszystkich pracowników we wszystkich firmach. Napisz to za pomocą strumieni.
   */
  long getAllUserAmountAsStream() {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies().stream())
            .map(company -> company.getUsers().size())
            .mapToLong(value -> (long) value).sum();
  }

  /**
   * Zwraca listę wszystkich nazw firm w formie listy.
   */
  List<String> getAllCompaniesNames() {

    List<String> companiesNames = new ArrayList<>();

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        companiesNames.add(company.getName());
      }
    }
    return companiesNames;
  }

  /**
   * Zwraca listę wszystkich nazw firm w formie listy. Tworzenie strumienia firm umieść w osobnej metodzie którą później będziesz wykorzystywać. Napisz to za
   * pomocą strumieni.
   */
  List<String> getAllCompaniesNamesAsStream() {
    return holdings.stream()
                    .flatMap(holding -> holding.getCompanies().stream().map(company -> company.getName()))
                    .collect(Collectors.toList());
  }

  /**
   * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList.
   */
  LinkedList<String> getAllCompaniesNamesAsLinkedList() {
    LinkedList<String> companiesNames = new LinkedList<>();

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        companiesNames.add(company.getName());
      }
    }
    return companiesNames;
  }

  /**
   * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList. Obiektów nie przepisujemy po zakończeniu działania strumienia. Napisz to za
   * pomocą strumieni.
   */
  LinkedList<String> getAllCompaniesNamesAsLinkedListAsStream() {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies().stream().map(company -> company.getName()))
            .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+"
   */
  String getAllCompaniesNamesAsString() {

    StringBuilder companiesNames = new StringBuilder();

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        companiesNames.append(company.getName());
        companiesNames.append('+');
      }
    }
    companiesNames.deleteCharAt(companiesNames.toString().length()-1);
    return companiesNames.toString();
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+" Napisz to za pomocą strumieni.
   */
  String getAllCompaniesNamesAsStringAsStream() {
    return holdings.stream()
            .flatMap(holding -> holding
                    .getCompanies()
                    .stream()
                    .map(company -> company.getName()))
            .collect(Collectors.joining("+"));
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+". Używamy collect i StringBuilder. Napisz to za pomocą
   * strumieni.
   * <p>
   * UWAGA: Zadanie z gwiazdką. Nie używamy zmiennych.
   */
  String getAllCompaniesNamesAsStringUsingStringBuilder() {
    return holdings.stream()
            .flatMap(holding -> holding
                    .getCompanies()
                    .stream()
                    .map(company -> company.getName()))
            .collect(Collector.of(StringBuilder::new, (StringBuilder, str) -> {if(StringBuilder.toString().equals("")){  StringBuilder.append(str);} else{ StringBuilder.append("+").append(str);}}, StringBuilder::append, StringBuilder::toString));

  }
  /**
   * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach.
   */
  long getAllUserAccountsAmount() {

    long accountAmount = 0;

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        for (User user: company.getUsers()) {
         accountAmount += user.getAccounts().size();
        }
      }
    }
    return accountAmount;
  }

  /**
   * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach. Napisz to za pomocą strumieni.
   */
  long getAllUserAccountsAmountAsStream() {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies().stream().flatMap(company -> company.getUsers().stream().map(user -> user.getAccounts().size())))
            .mapToLong(value -> (long) value).sum();
  }

  /**
   * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane.
   */
  String getAllCurrencies() {

Set<String> allCurrencies = new TreeSet<>();

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        for (User user: company.getUsers()) {
          for (Account account: user.getAccounts()){
           allCurrencies.add(account.getCurrency().name()); //zwraca nazwe enuma
          }
        }
      }
    }
   return allCurrencies.toString().replace("[", "").replace("]", "");
  }

  /**
   * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane. Napisz to za pomocą strumieni.
   */
  String getAllCurrenciesAsStream() {
    return holdings.stream()
            .flatMap(holding -> holding
                    .getCompanies()
                    .stream()
                    .flatMap(company -> company.getUsers()
                            .stream()
                            .flatMap(user -> user.getAccounts()
                                    .stream()
                                    .map(account -> account.getCurrency().name()))))
            .distinct()
            .sorted()
            .collect(Collectors.joining(", "));

  }

  /**
   * Metoda zwraca analogiczne dane jak getAllCurrencies, jednak na utworzonym zbiorze nie uruchamiaj metody stream, tylko skorzystaj z  Stream.generate.
   * Wspólny kod wynieś do osobnej metody.
   *
   * @see #getAllCurrencies()
   */
  String getAllCurrenciesUsingGenerate() {
    return "";
            //Stream.generate();
  }

  /**
   * Zwraca liczbę kobiet we wszystkich firmach.
   */
  long getWomanAmount() {
    long  womenAmount =  0;

    for (Holding holding: holdings) {
      for (Company company: holding.getCompanies()) {
        for (User user: company.getUsers()) {
          if(user.getSex().name() == "WOMAN"){
            womenAmount++;
          }
        }
      }
    }
    return womenAmount;
  }

  /**
   * Zwraca liczbę kobiet we wszystkich firmach. Powtarzający się fragment kodu tworzący strumień uzytkowników umieść w osobnej metodzie. Predicate określający
   * czy mamy doczynienia z kobietą Inech będzie polem statycznym w klasie. Napisz to za pomocą strumieni.
   */
  long getWomanAmountAsStream() {
    String woman = "WOMAN";
    return holdings.stream()
            .flatMap(holding -> holding
                    .getCompanies()
                    .stream()
                    .flatMap(company -> company.getUsers()
                            .stream()
                            .filter(user -> user.getSex() == Sex.WOMAN)))
                    .count();
  }

//  List<User> getUsers() {
//
//    return holdings.stream()
//            .flatMap(holding -> holding.getCompanies().stream().flatMap(company -> company.getUsers().addAll()))//.collect(Collectors.toList());
//  }

  /**
   * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Ustaw precyzje na 3 miejsca po przecinku.
   */
  BigDecimal getAccountAmountInPLN(final Account account) {

    return account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate));


  }


  /**
   * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Napisz to za pomocą strumieni.
   */
  BigDecimal getAccountAmountInPLNAsStream(final Account account) {
    return Stream.of(account)
            .map(account1 -> account1.getAmount().multiply(BigDecimal.valueOf(account1.getCurrency().rate)))
            .reduce(new BigDecimal("0"), BigDecimal::add).setScale(3, BigDecimal.ROUND_HALF_DOWN);
  }

  /**
   * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją.
   */
  BigDecimal getTotalCashInPLN(final List<Account> accounts) {
    BigDecimal totalCashInPLN = new BigDecimal("0");

    for (Account account:accounts){
      totalCashInPLN = totalCashInPLN.add(account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate)));
    }
    return totalCashInPLN;
  }

  /**
   * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją. Napisz to za pomocą strumieni.
   */
  BigDecimal getTotalCashInPLNAsStream(final List<Account> accounts) {
    return accounts.stream()
            .map(accounts1 -> accounts1.getAmount().multiply(BigDecimal.valueOf(accounts1.getCurrency().rate)))
            .reduce(new BigDecimal("0"), BigDecimal::add);
  }

  /**
   * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek.
   */
  Set<String> getUsersForPredicate(final Predicate<User> userPredicate) {

    Set<String> users = new HashSet<>();
    for (Holding holding  :holdings){
      for (Company company  :holding.getCompanies()){
        for (User user: company.getUsers()){
          if(userPredicate.test(user)){
            users.add(user.getFirstName());
          }
        }
      }
    }
    return users;
  }

  /**
   * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek. Napisz to za pomocą strumieni.
   */
  Set<String> getUsersForPredicateAsStream(final Predicate<User> userPredicate) {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies()
                    .stream()
                    .flatMap(company -> company.getUsers()
                            .stream()
                            .filter(user -> userPredicate.test(user))
                            .map(user -> user.getFirstName())))
            .collect(Collectors.toSet());
  }

  /**
   * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy.
   */
  List<String> getOldWoman(final int age) {
    List<String> users = new ArrayList<>();
    for (Holding holding  :holdings){
      for (Company company  :holding.getCompanies()){
        for (User user: company.getUsers()){
          if(user.getAge() > age){
            System.out.println(user.getFirstName());
            if(user.getSex() == Sex.WOMAN){
            users.add(user.getFirstName());
            }
          }
        }
      }
    }
    return users;
  }

  /**
   * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy. Napisz
   * to za pomocą strumieni.
   */
  List<String> getOldWomanAsStream(final int age) {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies()
            .stream()
                    .flatMap(company -> company.getUsers()
                            .stream()
                            .filter(user -> (user.getAge() > age && user.getSex() == Sex.WOMAN))
                            .map(user -> user.getFirstName())))
            .collect(Collectors.toList());
  }

  /**
   * Dla każdej firmy uruchamia przekazaną metodę.
   */
  void executeForEachCompany(final Consumer<Company> consumer) throws IllegalArgumentException {

    holdings.stream()
            .flatMap(holding -> holding.getCompanies().stream())
            .forEach(consumer);
  }

  /**
   * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach.
   */
  Optional<User> getRichestWoman() {
//  BigDecimal currentMax = new BigDecimal("0");
//
//  Optional<User> woman = null;
//          String richestWoman = null;
//
//  for (Holding holding: holdings){
//    for (Company company: holding.getCompanies()){
//      for(User user: company.getUsers()){
//
//        BigDecimal amount = new BigDecimal("0");
//
//        for (Account account: user.getAccounts()){
//          amount = amount.add(account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate)));
//        }
//      if(currentMax.compareTo(amount) == -1){
//      currentMax = amount;
//      richestWoman = user.getFirstName() + " "   +  user.getLastName();}
//      }
//    }
//  }


return null;
  }

  /**
   * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach. Napisz to za pomocą strumieni.
   */
  Optional<User> getRichestWomanAsStream() {

    return holdings.stream().flatMap(holding -> holding.getCompanies()
            .stream()
            .flatMap(company -> company.getUsers()
                    .stream()
                    .filter(user -> user.getSex() == Sex.WOMAN)))
            .flatMap(user -> user.getAccounts()
                    .stream()
                    .reduce((account, account2) -> account.getAmount().add(account2.getAmount())));



      }

  /**
   * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia.
   */
  Set<String> getFirstNCompany(final int n) {

    Set<String> companies = new HashSet<>();

    for (Holding holding: holdings){
     for (Company company: holding.getCompanies()){
       if(companies.size() != n){
         companies.add(company.getName());
       }
     }
    }

    return companies;
  }

  /**
   * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia. Napisz to za pomocą strumieni.
   */
  Set<String> getFirstNCompanyAsStream(final int n) {
    return holdings.stream()
            .flatMap(holding -> holding.getCompanies()
                    .stream()
                    .map(company -> company.getName()))
            .limit(n)
            .collect(Collectors.toSet());
  }

  /**
   * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
   * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return.
   */
  AccountType getMostPopularAccountType() {
   // return AccountType.ROR1;

/*    Set<AccountType> types  = new HashSet<>();
    for(Holding holding: holdings){
      for(Company company: holding.getCompanies()){
        for (User user:company.getUsers()){
          for(Account account : user.getAccounts()){
          types.add(account.getType());
          }
        }
      }
    }


    }*/
  }

  /**
   * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
   * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return. Napisz to za pomocą strumieni.
   */
  AccountType getMostPopularAccountTypeAsStream() {
    return null;
  }

  /**
   * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException.
   */
  User getUser(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException. Napisz to
   * za pomocą strumieni.
   */
  User getUserAsStream(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników.
   */
  Map<String, List<User>> getUserPerCompany() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników. Napisz to za pomocą strumieni.
   */
  Map<String, List<User>> getUserPerCompanyAsStream() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
   * Możesz skorzystać z metody entrySet.
   */
  Map<String, List<String>> getUserPerCompanyAsString() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
   * Możesz skorzystać z metody entrySet. Napisz to za pomocą strumieni.
   */
  Map<String, List<String>> getUserPerCompanyAsStringAsStream() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej
   * funkcji.
   */
  <T> Map<String, List<T>> getUserPerCompany(final Function<User, T> converter) {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej funkcji.
   * Napisz to za pomocą strumieni.
   */
  <T> Map<String, List<T>> getUserPerCompanyAsStream(final Function<User, T> converter) {
    return null;
  }

  /**
   * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
   * jest natomiast zbiór nazwisk tych osób.
   */
  Map<Boolean, Set<String>> getUserBySex() {
    return null;
  }

  /**
   * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
   * jest natomiast zbiór nazwisk tych osób. Napisz to za pomocą strumieni.
   */
  Map<Boolean, Set<String>> getUserBySexAsStream() {
    return null;
  }

  /**
   * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek.
   */
  Map<String, Account> createAccountsMap() {
    return null;
  }

  /**
   * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek. Napisz to za pomocą strumieni.
   */
  Map<String, Account> createAccountsMapAsStream() {
    return null;
  }

  /**
   * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń.
   */
  String getUserNames() {
    return null;
  }

  /**
   * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń. Napisz to za pomocą strumieni.
   */
  String getUserNamesAsStream() {
    return null;
  }

  /**
   * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10.
   */
  Set<User> getUsers() {
    return null;
  }

  /**
   * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10. Napisz to za pomocą strumieni.
   */
  Set<User> getUsersAsStream() {
    return null;
  }

  /**
   * Zwraca użytkownika, który spełnia podany warunek.
   */
  Optional<User> findUser(final Predicate<User> userPredicate) {
    return null;
  }

  /**
   * Zwraca użytkownika, który spełnia podany warunek. Napisz to za pomocą strumieni.
   */
  Optional<User> findUserAsStream(final Predicate<User> userPredicate) {
    return null;
  }

  /**
   * Dla podanego użytkownika zwraca informacje o tym ile ma lat w formie: IMIE NAZWISKO ma lat X. Jeżeli użytkownik nie istnieje to zwraca text: Brak
   * użytkownika.
   * <p>
   * Uwaga: W prawdziwym kodzie nie przekazuj Optionali jako parametrów. Napisz to za pomocą strumieni.
   */
  String getAdultantStatusAsStream(final Optional<User> user) {
    return null;
  }

  /**
   * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
   * Pasibrzuch, Adam Wojcik
   */
  void showAllUser() {
    throw new IllegalArgumentException("not implemented yet");
  }

  /**
   * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
   * Pasibrzuch, Adam Wojcik. Napisz to za pomocą strumieni.
   */
  void showAllUserAsStream() {
    throw new IllegalArgumentException("not implemented yet");
  }

  /**
   * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki.
   */
  Map<AccountType, BigDecimal> getMoneyOnAccounts() {
    return null;
  }

  /**
   * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki. Napisz to za pomocą
   * strumieni. Ustaw precyzje na 0.
   */
  Map<AccountType, BigDecimal> getMoneyOnAccountsAsStream() {
    return null;
  }

  /**
   * Zwraca sumę kwadratów wieków wszystkich użytkowników.
   */
  int getAgeSquaresSum() {
    return -1;
  }

  /**
   * Zwraca sumę kwadratów wieków wszystkich użytkowników. Napisz to za pomocą strumieni.
   */
  int getAgeSquaresSumAsStream() {
    return -1;
  }

  /**
   * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
   * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody).
   */
  List<User> getRandomUsers(final int n) {
    return null;
  }

  /**
   * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
   * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody). Napisz to za pomocą strumieni.
   */
  List<User> getRandomUsersAsStream(final int n) {
    return null;
  }

  /**
   * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
   * na rachunku danego typu przeliczona na złotkówki.
   */
  Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMap() {
    return null;
  }

  /**
   * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
   * na rachunku danego typu przeliczona na złotkówki.  Napisz to za pomocą strumieni.
   */
  Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMapAsStream() {
    return null;
  }

  /**
   * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
   * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach.
   */

  Map<Permit, List<User>> getUsersByTheyPermitsSorted() {
    return null;
  }

  /**
   * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
   * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach. Napisz to za pomoca strumieni.
   */

  Map<Permit, List<User>> getUsersByTheyPermitsSortedAsStream() {
    return null;
  }

  /**
   * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
   * List<Users>
   */
  Map<Boolean, List<User>> divideUsersByPredicate(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
   * List<Users>. Wykonaj zadanie za pomoca strumieni.
   */
  Map<Boolean, List<User>> divideUsersByPredicateAsStream(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Zwraca strumień wszystkich firm.
   */
  private Stream<Company> getCompanyStream() {
    return null;
  }

  /**
   * Zwraca zbiór walut w jakich są rachunki.
   */
  private Set<Currency> getCurenciesSet() {
    return null;
  }

  /**
   * Tworzy strumień rachunków.
   */
  private Stream<Account> getAccoutStream() {
    return null;
  }

  /**
   * Tworzy strumień użytkowników.
   */
  private Stream<User> getUserStream() {
    return null;
  }

}