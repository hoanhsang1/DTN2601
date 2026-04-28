import Entity.Account;

import java.util.List;
import java.util.Scanner;

public class ExerciseOne {
    public void QuestionOne(List<Account> accounts) {
        float salary1 = accounts.get(0).getSalary();
        float salary2 = accounts.get(1).getSalary();

        int roundedSalary1 = Math.round(salary1);
        int roundedSalary2 = Math.round(salary2);

        System.out.println("Lương Account 1: " + salary1 + "$ -> Làm tròn: " + roundedSalary1 + "$");
        System.out.println("Lương Account 2: " + salary2 + "$ -> Làm tròn: " + roundedSalary2 + "$");
    }
    public void QuestionTwo() {
        int num = (int)(Math.random() * 100000);
        System.out.printf("%05d\n", num);
    }

    public void QuestionThree(int num) {
        int num2 = num%100;
        System.out.println(num2);
    }

    public float QuestionFour() {
        Scanner input = new Scanner(System.in);
        System.out.println("Nhập giá trị của số a");
        int a = input.nextInt();
        System.out.println("Nhập giá trị của số b");
        int b = input.nextInt();
        if (b == 0) {
            System.out.println("Không thể chia cho 0!");
            return 0;
        }
        return a/b;
    }
}
