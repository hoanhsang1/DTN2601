package Exercises;

public class ExerciseSix {
    public static void question1() {
        for (int i = 2; i < 10; i += 2) {
            System.out.println(i);
        }
    }

    public static void question2(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
        }
    }

    public static void question3() {
        for (int i = 1; i < 10; i++) {
            System.out.println(i);
        }
    }
}
