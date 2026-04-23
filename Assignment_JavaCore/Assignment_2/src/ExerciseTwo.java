import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseTwo {
    public static void question1() {
        int i = 5;
        System.out.printf("%d%n", i);
    }

    public static void question2() {
        int i = 100000000;
        System.out.printf(Locale.US, "%,d%n", i);
    }

    public static void question3() {
        float f = 5.567098f;
        System.out.printf("%.4f%n", f);
    }

    public static void question4() {
        String name = "Nguyễn Văn A";
        System.out.printf("Tên tôi là \"%s\" và tôi đang độc thân.%n", name);
    }

    public static void question5() {
        String pattern = "dd/MM/yyyy HH'h':mm'p':ss's'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
    }

    public static void question6(Account[] accounts) {
        System.out.printf("%-25s | %-20s | %-15s%n", "Email", "FullName", "Department");
        System.out.println("-----------------------------------------------------------------------");
        for (Account acc : accounts) {
            String deptName = acc.department != null ? acc.department.departmentName : "Unknown";
            System.out.printf("%-25s | %-20s | %-15s%n", acc.email, acc.fullName, deptName);
        }
    }
}
