package pl.klolo.workshops.logic;

import pl.klolo.workshops.domain.Currency;
import pl.klolo.workshops.domain.*;
import pl.klolo.workshops.mock.HoldingMockGenerator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * 1 Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma.
     */
    long getHoldingsWhereAreCompanies() {
        int numberOfHolldings = 0;
        for (Holding holding : holdings) {
            if (holding.getCompanies().size() > 0 && nonNull(holding.getCompanies())) {
                numberOfHolldings++;
            }
        }
        return numberOfHolldings;
    }

    /**
     * 2 Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma. Napisz to za pomocą strumieni.
     */
    long getHoldingsWhereAreCompaniesAsStream() {
        return holdings.stream()
                .filter(holding -> nonNull(holding.getCompanies()) && holding.getCompanies().size() > 0)
                .count();
    }

    /**
     * 3 Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy.
     */
    List<String> getHoldingNames() {

        List<String> holdingsNames = new ArrayList<>();
        for (Holding holding : holdings) {
            holdingsNames.add(holding.getName().toLowerCase());
        }
        return holdingsNames;

    }

    /**
     * 4 Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy. Napisz to za pomocą strumieni.
     */
    List<String> getHoldingNamesAsStream() {
        return holdings.stream()
                .map(holding -> holding.getName().toLowerCase())
                .collect(Collectors.toList());
    }

    /**
     * 5 Zwraca nazwy wszystkich holdingów posortowane i sklejone w jeden string. String ma postać: (Coca-Cola, Nestle, Pepsico)
     */
    String getHoldingNamesAsString() {

        StringBuilder namesAsString = new StringBuilder();
        List<String> names = new ArrayList<>();

        for (Holding holding : holdings) {
            names.add(holding.getName());
        }
        Collections.sort(names);

        namesAsString.append('(');
        for (String name : names) {
            namesAsString.append(name);
            namesAsString.append(", ");
        }
        namesAsString.deleteCharAt(namesAsString.toString().length() - 1);
        namesAsString.deleteCharAt(namesAsString.toString().length() - 1);
        namesAsString.append(')');

        return namesAsString.toString();
    }

    /**
     * 6 Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico). Napisz to za pomocą strumieni.
     */
    String getHoldingNamesAsStringAsStream() {
        return holdings.stream()
                .map(holding -> holding.getName())
                .sorted()
                .collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * 7 Zwraca liczbę firm we wszystkich holdingach.
     */
    long getCompaniesAmount() {

        long numberOfCompanies = 0;

        for (Holding holding : holdings) {
            numberOfCompanies += holding.getCompanies().size();
        }

        return numberOfCompanies;
    }

    /**
     * 8 Zwraca liczbę firm we wszystkich holdingach. Napisz to za pomocą strumieni.
     */
    long getCompaniesAmountAsStream() {

        return holdings.stream()
                .map(holding -> holding.getCompanies().size())
                .mapToLong(value -> (long) value).sum();

    }

    /**
     * 9 Zwraca liczbę wszystkich pracowników we wszystkich firmach.
     */
    long getAllUserAmount() {

        long allEmployees = 0;

        for (Holding holding : holdings) {

            for (Company company : holding.getCompanies()) {

                allEmployees += company.getUsers().size();
            }
        }
        return allEmployees;
    }

    /**
     * 10 Zwraca liczbę wszystkich pracowników we wszystkich firmach. Napisz to za pomocą strumieni.
     */
    long getAllUserAmountAsStream() {
        return holdings.stream()
                .flatMap(holding -> holding.getCompanies().stream())
                .map(company -> company.getUsers().size())
                .mapToLong(value -> (long) value).sum();
    }

    /**
     * 11 Zwraca listę wszystkich nazw firm w formie listy.
     */
    List<String> getAllCompaniesNames() {

        List<String> companiesNames = new ArrayList<>();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                companiesNames.add(company.getName());
            }
        }
        return companiesNames;
    }

    /**
     * 12 Zwraca listę wszystkich nazw firm w formie listy. Tworzenie strumienia firm umieść w osobnej metodzie którą później będziesz wykorzystywać. Napisz to za
     * pomocą strumieni.
     */
    List<String> getAllCompaniesNamesAsStream() {
        return holdings.stream()
                .flatMap(holding -> holding.getCompanies().stream().map(company -> company.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 13 Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList.
     */
    LinkedList<String> getAllCompaniesNamesAsLinkedList() {
        LinkedList<String> companiesNames = new LinkedList<>();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                companiesNames.add(company.getName());
            }
        }
        return companiesNames;
    }

    /**
     * 14 Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList. Obiektów nie przepisujemy po zakończeniu działania strumienia. Napisz to za
     * pomocą strumieni.
     */
    LinkedList<String> getAllCompaniesNamesAsLinkedListAsStream() {
        return holdings.stream()
                .flatMap(holding -> holding.getCompanies().stream().map(company -> company.getName()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * 15 Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+"
     */
    String getAllCompaniesNamesAsString() {

        StringBuilder companiesNames = new StringBuilder();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                companiesNames.append(company.getName());
                companiesNames.append('+');
            }
        }
        companiesNames.deleteCharAt(companiesNames.toString().length() - 1);
        return companiesNames.toString();
    }

    /**
     * 16 Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+" Napisz to za pomocą strumieni.
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
     * 17 Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+". Używamy collect i StringBuilder. Napisz to za pomocą
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
                .collect(Collector.of(StringBuilder::new, (StringBuilder, str) -> {
                    if (StringBuilder.toString().equals("")) {
                        StringBuilder.append(str);
                    } else {
                        StringBuilder.append("+").append(str);
                    }
                }, StringBuilder::append, StringBuilder::toString));

    }

    /**
     * 18 Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach.
     */
    long getAllUserAccountsAmount() {

        long accountAmount = 0;

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    accountAmount += user.getAccounts().size();
                }
            }
        }
        return accountAmount;
    }

    /**
     * 19 Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach. Napisz to za pomocą strumieni.
     */
    long getAllUserAccountsAmountAsStream() {
        return holdings.stream()
                .flatMap(holding -> holding.getCompanies().stream().flatMap(company -> company.getUsers().stream().map(user -> user.getAccounts().size())))
                .mapToLong(value -> (long) value).sum();
    }

    /**
     * 20 Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane.
     */
    String getAllCurrencies() {

        Set<String> allCurrencies = new TreeSet<>();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    for (Account account : user.getAccounts()) {
                        allCurrencies.add(account.getCurrency().name()); //zwraca nazwe enuma
                    }
                }
            }
        }
        return allCurrencies.toString().replace("[", "").replace("]", "");
    }

    /**
     * 21 Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane. Napisz to za pomocą strumieni.
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
     * 22 Metoda zwraca analogiczne dane jak getAllCurrencies, jednak na utworzonym zbiorze nie uruchamiaj metody stream, tylko skorzystaj z  Stream.generate.
     * Wspólny kod wynieś do osobnej metody.
     *
     * @see #getAllCurrencies()
     */
    String getAllCurrenciesUsingGenerate() {
        return "";
        //Stream.generate();
    }

    /**
     * 23 Zwraca liczbę kobiet we wszystkich firmach.
     */
    long getWomanAmount() {
        long womenAmount = 0;

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    if (user.getSex().name() == "WOMAN") {
                        womenAmount++;
                    }
                }
            }
        }
        return womenAmount;
    }

    /**
     * 24 Zwraca liczbę kobiet we wszystkich firmach. Powtarzający się fragment kodu tworzący strumień uzytkowników umieść w osobnej metodzie. Predicate określający
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
     * 25 Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Ustaw precyzje na 3 miejsca po przecinku.
     */
    BigDecimal getAccountAmountInPLN(final Account account) {

        return account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate));


    }


    /**
     * 26 Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Napisz to za pomocą strumieni.
     */
    BigDecimal getAccountAmountInPLNAsStream(final Account account) {
        return Stream.of(account)
                .map(account1 -> account1.getAmount().multiply(BigDecimal.valueOf(account1.getCurrency().rate)))
                .reduce(new BigDecimal("0"), BigDecimal::add).setScale(3, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 27 Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją.
     */
    BigDecimal getTotalCashInPLN(final List<Account> accounts) {
        BigDecimal totalCashInPLN = new BigDecimal("0");

        for (Account account : accounts) {
            totalCashInPLN = totalCashInPLN.add(account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate)));
        }
        return totalCashInPLN;
    }

    /**
     * 28 Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją. Napisz to za pomocą strumieni.
     */
    BigDecimal getTotalCashInPLNAsStream(final List<Account> accounts) {
        return accounts.stream()
                .map(accounts1 -> accounts1.getAmount().multiply(BigDecimal.valueOf(accounts1.getCurrency().rate)))
                .reduce(new BigDecimal("0"), BigDecimal::add);
    }

    /**
     * 29 Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek.
     */
    Set<String> getUsersForPredicate(final Predicate<User> userPredicate) {

        Set<String> users = new HashSet<>();
        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    if (userPredicate.test(user)) {
                        users.add(user.getFirstName());
                    }
                }
            }
        }
        return users;
    }

    /**
     * 30 Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek. Napisz to za pomocą strumieni.
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
     * 31 Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy.
     */
    List<String> getOldWoman(final int age) {
        List<String> users = new ArrayList<>();
        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    if (user.getAge() > age) {
                        System.out.println(user.getFirstName());
                        if (user.getSex() == Sex.WOMAN) {
                            users.add(user.getFirstName());
                        }
                    }
                }
            }
        }
        return users;
    }

    /**
     * 32 Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy. Napisz
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
     * 33 Dla każdej firmy uruchamia przekazaną metodę.
     */
    void executeForEachCompany(final Consumer<Company> consumer) throws IllegalArgumentException {

        holdings.stream()
                .flatMap(holding -> holding.getCompanies().stream())
                .forEach(consumer);
    }

    /**
     * 34 Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach.
     */
    Optional<User> getRichestWoman() {


        return null;
    }

    /**
     * 35 Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach. Napisz to za pomocą strumieni.
     */
    Optional<User> getRichestWomanAsStream() {


        Optional<User> richestWoman = holdings.stream().flatMap(holding -> holding.getCompanies()
                .stream()
                .flatMap(company -> company.getUsers()
                        .stream()
                        .filter(user -> user.getSex() == Sex.WOMAN)))
                .max(Comparator.comparing(user -> user.getAccounts()
                        .stream()
                        .map(account -> account.getAmount().multiply(BigDecimal.valueOf(account.getCurrency().rate)))
                        .reduce(BigDecimal::add)
                        .get()));

        return richestWoman;
    }

    /**
     * 36 Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia.
     */
    Set<String> getFirstNCompany(final int n) {

        Set<String> companies = new HashSet<>();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                if (companies.size() != n) {
                    companies.add(company.getName());
                }
            }
        }

        return companies;
    }

    /**
     * 37 Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia. Napisz to za pomocą strumieni.
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
     * 38 Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
     * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return.
     */
    AccountType getMostPopularAccountType() {

        Map<AccountType, Long> accountTypeCounter = new TreeMap<>();
        accountTypeCounter.put(AccountType.ROR2, 0L);
        accountTypeCounter.put(AccountType.RO1, 0L);
        accountTypeCounter.put(AccountType.ROR1, 0L);
        accountTypeCounter.put(AccountType.RO2, 0L);
        accountTypeCounter.put(AccountType.LO1, 0L);
        accountTypeCounter.put(AccountType.LO2, 0L);


        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    for (Account account : user.getAccounts()) {
                        accountTypeCounter.computeIfPresent(account.getType(), (k, v) -> v + 1);
                    }
                }
            }

            return ((TreeMap<AccountType, Long>) accountTypeCounter).firstEntry().getKey();
        }

        throw new IllegalStateException();
    }

    /**
     * 39 Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
     * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return. Napisz to za pomocą strumieni.
     */
    AccountType getMostPopularAccountTypeAsStream() {

        Map<AccountType, Long> accounts =
                getAccoutStream()
                        .map(Account::getType)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // return accounts.entrySet().stream().max((s, f) -> f.getValue().intValue()).get().getKey();
        return accounts.entrySet()
                .stream()
                .sorted(Collections.reverseOrder((k, v) -> v.getValue().intValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(IllegalStateException::new);

//            accounts.forEach((accountType, aLong) -> {
//              System.out.printf("%s : %d%n", accountType.toString(), aLong);
//            });


    }

    /**
     * 40 Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException.
     */
    User getUser(final Predicate<User> predicate) {

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                for (User user : company.getUsers()) {
                    if (predicate.test(user)) {
                        return user;
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * 41 Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException. Napisz to
     * za pomocą strumieni.
     */
    User getUserAsStream(final Predicate<User> predicate) {
        return getUserStream()
                .filter(user -> predicate.test(user))
                .findFirst().orElseThrow(() -> new IllegalArgumentException());


    }

    /**
     * 42 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników.
     */
    Map<String, List<User>> getUserPerCompany() {

        Map<String, List<User>> usersPerCompany = new HashMap<>();

        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                usersPerCompany.put(company.getName(), company.getUsers());
            }
        }

        return usersPerCompany;
    }

    /**
     * 43 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników. Napisz to za pomocą strumieni.
     */
    Map<String, List<User>> getUserPerCompanyAsStream() {

        return holdings.stream()
                .flatMap(holding -> holding
                        .getCompanies()
                        .stream())
                .collect(Collectors.toMap(Company::getName, Company::getUsers));

    }

    /**
     * 44 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
     * Możesz skorzystać z metody entrySet.
     */
    Map<String, List<String>> getUserPerCompanyAsString() {

        Map<String, List<String>> usersPerCompany = new HashMap<>();


        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                ArrayList listOfNamesAsStrings = new ArrayList();

                for (User user : company.getUsers()) {
                    listOfNamesAsStrings.add(user.getFirstName() + " " + user.getLastName());
                }
                usersPerCompany.put(company.getName(), listOfNamesAsStrings);
            }
        }

        return usersPerCompany;
    }

    /**
     * 45 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
     * Możesz skorzystać z metody entrySet. Napisz to za pomocą strumieni.
     */
    Map<String, List<String>> getUserPerCompanyAsStringAsStream() {

        BiFunction<String, String, String> joinFirstNameAndLastName = (f, s) -> f + " " + s;

        return getCompanyStream()
                .collect(Collectors
                        .toMap(Company::getName, c -> c.getUsers()
                                .stream()
                                //  .map(u -> joinFirstNameAndLastName.apply(u.getFirstName(), u.getLastName()))
                                .map(u -> u.getFirstName() + " " + u.getLastName())
                                .collect(Collectors.toList())));
    }

    /**
     * 46 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej
     * funkcji.
     */
    <T> Map<String, List<T>> getUserPerCompany(final Function<User, T> converter) {

        Map<String, List<T>> usersPerCompany = new HashMap<>();


        for (Holding holding : holdings) {
            for (Company company : holding.getCompanies()) {
                ArrayList listOfNamesAfterConversion = new ArrayList();

                for (User user : company.getUsers()) {
                    listOfNamesAfterConversion.add(converter.apply(user));
                }
                usersPerCompany.put(company.getName(), listOfNamesAfterConversion);
            }
        }

        return usersPerCompany;

    }

    /**
     * 47 Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej funkcji.
     * Napisz to za pomocą strumieni.
     */
    <T> Map<String, List<T>> getUserPerCompanyAsStream(final Function<User, T> converter) {
        return holdings.stream()
                .flatMap(holding -> holding
                        .getCompanies()
                        .stream())
                .collect(Collectors.toMap(Company::getName, company -> company.getUsers()
                        .stream()
                        .map(user -> converter.apply(user))
                        .collect(Collectors.toList())));
    }

    /**
     * 48 Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
     * jest natomiast zbiór nazwisk tych osób.
     */
    Map<Boolean, Set<String>> getUserBySex() {
        Map<Boolean, Set<String>> usersBySex = new HashMap<>();
        Set<String> women = new HashSet<>();
        Set<String> men = new HashSet<>();

        for (Holding holding: holdings){
            for (Company company: holding.getCompanies()){
                for (User user: company.getUsers()){
                    if (user.getSex() == Sex.WOMAN){
                        women.add(user.getLastName());
                    } else if (user.getSex() == Sex.MAN){
                        men.add(user.getLastName());
                    }
                }
            }
        }

        usersBySex.put(true, men);
        usersBySex.put(false, women);

        return usersBySex;

    }

    /**
     * 49 Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
     * jest natomiast zbiór nazwisk tych osób. Napisz to za pomocą strumieni.
     */
    Map<Boolean, Set<String>> getUserBySexAsStream() {

        Predicate<User> isManOrWOman = user -> user.getSex() == Sex.MAN || user.getSex() == Sex.WOMAN ;


             return    getUserStream()
                .filter(isManOrWOman::test)
                .collect(Collectors.partitioningBy(user -> user.getSex() == Sex.MAN, Collectors.mapping(user -> user.getLastName(), Collectors.toSet())));


    }

    /**
     * 50 Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek.
     */
    Map<String, Account> createAccountsMap() {
        return null;
    }

    /**
     * 51 Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek. Napisz to za pomocą strumieni.
     */
    Map<String, Account> createAccountsMapAsStream() {
        return null;
    }

    /**
     * 52 Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń.
     */
    String getUserNames() {
        return null;
    }

    /**
     * 53 Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń. Napisz to za pomocą strumieni.
     */
    String getUserNamesAsStream() {
        return null;
    }

    /**
     * 54 zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10.
     */
    Set<User> getUsers() {
        return null;
    }

    /**
     * 55 zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10. Napisz to za pomocą strumieni.
     */
    Set<User> getUsersAsStream() {
        return null;
    }

    /**
     * 56 Zwraca użytkownika, który spełnia podany warunek.
     */
    Optional<User> findUser(final Predicate<User> userPredicate) {
        return null;
    }

    /**
     * 57 Zwraca użytkownika, który spełnia podany warunek. Napisz to za pomocą strumieni.
     */
    Optional<User> findUserAsStream(final Predicate<User> userPredicate) {
        return null;
    }

    /**
     * 58 Dla podanego użytkownika zwraca informacje o tym ile ma lat w formie: IMIE NAZWISKO ma lat X. Jeżeli użytkownik nie istnieje to zwraca text: Brak
     * użytkownika.
     * <p>
     * Uwaga: W prawdziwym kodzie nie przekazuj Optionali jako parametrów. Napisz to za pomocą strumieni.
     */
    String getAdultantStatusAsStream(final Optional<User> user) {
        return null;
    }

    /**
     * 59 Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
     * Pasibrzuch, Adam Wojcik
     */
    void showAllUser() {
        throw new IllegalArgumentException("not implemented yet");
    }

    /**
     * 60 Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
     * Pasibrzuch, Adam Wojcik. Napisz to za pomocą strumieni.
     */
    void showAllUserAsStream() {
        throw new IllegalArgumentException("not implemented yet");
    }

    /**
     * 61 Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki.
     */
    Map<AccountType, BigDecimal> getMoneyOnAccounts() {
        return null;
    }

    /**
     * 62 Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki. Napisz to za pomocą
     * strumieni. Ustaw precyzje na 0.
     */
    Map<AccountType, BigDecimal> getMoneyOnAccountsAsStream() {
        return null;
    }

    /**
     * 63 Zwraca sumę kwadratów wieków wszystkich użytkowników.
     */
    int getAgeSquaresSum() {
        return -1;
    }

    /**
     * 64 Zwraca sumę kwadratów wieków wszystkich użytkowników. Napisz to za pomocą strumieni.
     */
    int getAgeSquaresSumAsStream() {
        return -1;
    }

    /**
     * 65 Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
     * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody).
     */
    List<User> getRandomUsers(final int n) {
        return null;
    }

    /**
     * 66 Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
     * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody). Napisz to za pomocą strumieni.
     */
    List<User> getRandomUsersAsStream(final int n) {
        return null;
    }

    /**
     * 67 Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
     * na rachunku danego typu przeliczona na złotkówki.
     */
    Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMap() {
        return null;
    }

    /**
     * 68 Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
     * na rachunku danego typu przeliczona na złotkówki.  Napisz to za pomocą strumieni.
     */
    Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMapAsStream() {
        return null;
    }

    /**
     * 69 Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
     * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach.
     */

    Map<Permit, List<User>> getUsersByTheyPermitsSorted() {
        return null;
    }

    /**
     * 70 Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
     * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach. Napisz to za pomoca strumieni.
     */

    Map<Permit, List<User>> getUsersByTheyPermitsSortedAsStream() {
        return null;
    }

    /**
     * 71 Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
     * List<Users>
     */
    Map<Boolean, List<User>> divideUsersByPredicate(final Predicate<User> predicate) {
        return null;
    }

    /**
     * 72 Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
     * List<Users>. Wykonaj zadanie za pomoca strumieni.
     */
    Map<Boolean, List<User>> divideUsersByPredicateAsStream(final Predicate<User> predicate) {
        return null;
    }

    /**
     * 73 Zwraca strumień wszystkich firm.
     */
    private Stream<Company> getCompanyStream() {
        return holdings.stream()
                .flatMap(holding -> holding
                        .getCompanies()
                        .stream());
    }

    /**
     * 74 Zwraca zbiór walut w jakich są rachunki.
     */
    private Set<Currency> getCurenciesSet() {
        return null;
    }

    /**
     * 75 Tworzy strumień rachunków.
     */
    private Stream<Account> getAccoutStream() {
        return holdings.stream().flatMap(holding -> holding.getCompanies()
                .stream()
                .flatMap(company -> company.getUsers()
                        .stream()
                        .flatMap(user -> user.getAccounts()
                                .stream())));
    }

    /**
     * 76 Tworzy strumień użytkowników.
     */
    private Stream<User> getUserStream() {

        return holdings.stream()
                .flatMap(holding -> holding.getCompanies()
                        .stream()
                        .flatMap(company -> company.getUsers()
                                .stream()));
    }

}