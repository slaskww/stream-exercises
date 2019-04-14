package pl.klolo.workshops.domain;

import pl.klolo.workshops.mock.HoldingMockGenerator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

         List<Holding> holdings;
         HoldingMockGenerator holdingMockGenerator = new HoldingMockGenerator();
        holdings = holdingMockGenerator.generate();

        for (Holding holding: holdings){
            for (Company company: holding.getCompanies()){
                for (User user: company.getUsers()){
                    for (Account account:user.getAccounts()){
                        System.out.println("account.getAmount() =" + account.getAmount());
                        System.out.println("account.getCurrency() =" + account.getCurrency());
                        System.out.println("account.getNumber() =" + account.getNumber());
                        System.out.println("account.toString() =" + account.toString());
                        System.out.println("account.getType() =" + account.getType());
                        System.out.println();
                        System.out.println();

                    }
                }


            }
        }



    }
}
