import com.vti.entity.Account;
import com.vti.entity.Group;
import com.vti.entity.Position;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("====== Question 2 ======");
        // a) Không có parameters
        Account acc1 = new Account();
        System.out.println("Account 1 (No params) created.");

        // b) Có các parameter là id, Email, Username, FirstName, LastName
        Account acc2 = new Account(1, "email2@gmail.com", "user2", "Nguyen", "Van A");
        System.out.println("Account 2 created: " + acc2.getFullName());

        // c) Có các parameter là id, Email, Username, FirstName, LastName và Position
        Position pos1 = new Position();
        Account acc3 = new Account(2, "email3@gmail.com", "user3", "Tran", "Van B", pos1);
        System.out.println("Account 3 created: " + acc3.getFullName() + ", Create Date: " + acc3.getCreateDate());

        // d) Có các parameter là id, Email, Username, FirstName, LastName, Position, CreateDate
        Date date = new Date(System.currentTimeMillis() - 100000);
        Account acc4 = new Account(3, "email4@gmail.com", "user4", "Le", "Van C", pos1, date);
        System.out.println("Account 4 created: " + acc4.getFullName() + ", Create Date: " + acc4.getCreateDate());

        System.out.println("\n====== Question 3 ======");
        // a) Không có parameters
        Group grp1 = new Group();
        System.out.println("Group 1 (No params) created.");

        // b) Có các parameter là GroupName, Creator, array Account[] accounts, CreateDate
        Account[] accounts = {acc2, acc3, acc4};
        Group grp2 = new Group("Group 2", acc1, accounts, new Date());
        System.out.println("Group 2 created with " + grp2.getAccounts().length + " accounts.");

        // c) Có các parameter là GroupName, Creator, array String[] usernames , CreateDate
        String[] usernames = {"userA", "userB", "userC"};
        Group grp3 = new Group("Group 3", acc1, usernames, new Date());
        System.out.println("Group 3 created. Account usernames:");
        for (Account acc : grp3.getAccounts()) {
            System.out.println("- " + acc.getUsername());
        }
    }
}