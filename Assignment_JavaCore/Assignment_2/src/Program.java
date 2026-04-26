// Program.java

import Entity.Account;
import Entity.Department;
import Entity.Position;

import java.util.Date;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        // Entity.Department
        Department d1 = new Department();
        d1.departmentID = 1;
        d1.departmentName = "Sale";

        Department d2 = new Department();
        d2.departmentID = 2;
        d2.departmentName = "Marketing";

        Department d3 = new Department();
        d3.departmentID = 3;
        d3.departmentName = "IT";

        // Entity.Position
        Position p1 = new Position();
        p1.positionID = 1;
        p1.positionName = PositionName.DEV;

        Position p2 = new Position();
        p2.positionID = 2;
        p2.positionName = PositionName.TEST;

        Position p3 = new Position();
        p3.positionID = 3;
        p3.positionName = PositionName.PM;

        // Entity.Account
        Account a1 = new Account();
        a1.accountID = 1;
        a1.email = "a1@gmail.com";
        a1.username = "user1";
        a1.fullName = "User One";
        a1.department = d1;
        a1.position = p1;
        a1.createDate = new Date();

        Account a2 = new Account();
        a2.accountID = 2;
        a2.email = "a2@gmail.com";
        a2.username = "user2";
        a2.fullName = "User Two";
        a2.department = d2;
        a2.position = p2;
        a2.createDate = new Date();

        Account a3 = new Account();
        a3.accountID = 3;
        a3.email = "a3@gmail.com";
        a3.username = "user3";
        a3.fullName = "User Three";
        a3.department = d3;
        a3.position = p3;
        a3.createDate = new Date();
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("Exercise 1: Flow Control");
            System.out.println("Exercise 2: System out printf");
            System.out.println("Exercise 3: Date Format");
            System.out.println("Exercise 4: Random Number");
            System.out.println("Exercise 5: Input from console");
            System.out.println("Exercise 6: Method");
            System.out.println("Exercise 7: Exit");
            choice = sc.nextInt();
            if (!choice) {

            }
        }
    }
}