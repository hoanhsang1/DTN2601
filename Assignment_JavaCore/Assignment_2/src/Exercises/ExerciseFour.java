package Exercises;

import java.time.LocalDate;
import java.util.Random;

public class ExerciseFour {
    public static void question1() {
        Random random = new Random();
        int a = random.nextInt();
        System.out.println("Random integer: " + a);
    }

    public static void question2() {
        Random random = new Random();
        float f = random.nextFloat();
        System.out.println("Random float: " + f);
    }

    public static void question3() {
        String[] students = {"Nguyen Van A", "Tran Van B", "Le Thi C", "Pham Van D"};
        Random random = new Random();
        int index = random.nextInt(students.length);
        System.out.println("Random student: " + students[index]);
    }

    public static void question4() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1995, 7, 24).toEpochDay();
        int maxDay = (int) LocalDate.of(1995, 12, 20).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        System.out.println("Random date between 24-07-1995 and 20-12-1995: " + randomDate);
    }

    public static void question5() {
        Random random = new Random();
        int now = (int) LocalDate.now().toEpochDay();
        int randomDay = now - random.nextInt(365);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        System.out.println("Random date in the last year: " + randomDate);
    }

    public static void question6() {
        Random random = new Random();
        int now = (int) LocalDate.now().toEpochDay();
        int randomDay = random.nextInt(now);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        System.out.println("Random date in the past: " + randomDate);
    }

    public static void question7() {
        Random random = new Random();
        int a = random.nextInt(900) + 100;
        System.out.println("Random 3-digit number: " + a);
    }
}
