import Entity.Account;
import Entity.CategoryQuestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExerciseTwo {
    public void QuestionOne(int number) {
        List<Account> Accounts = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            Account acc = new Account();
            acc.setEmail("Email " + (i + 1));
            acc.setUsername("User name " + (i + 1));
            acc.setFullName("Full name " + (i + 1));
            acc.setCreateDate(new Date());

            Accounts.add(acc);
        }

        for (Account acc : Accounts) {
            System.out.println("ID: " + acc.getAccountID());
            System.out.println("Email: " + acc.getEmail());
            System.out.println("Username: " + acc.getUsername());
            System.out.println("FullName: " + acc.getFullName());
            System.out.println("CreateDate: " + acc.getCreateDate());
            System.out.println("Salary: " + acc.getSalary());
            System.out.println("----------------------");
        }
    }
}
