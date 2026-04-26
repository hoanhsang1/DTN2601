import Entity.Exam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ExerciseThree {
    public static void question1(Exam exam) {
        Locale locale = new Locale("vi", "VN");
        DateFormat dateformat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateformat.format(exam.createDate);
        System.out.println("Entity.Exam ID: " + exam.examID);
        System.out.println("Code: " + exam.code);
        System.out.println("Title: " + exam.title);
        System.out.println("Create Date: " + date);
    }

    public static void question2(Exam exam) {
        String pattern = "yyyy-MM-dd-HH-mm-ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(exam.createDate);
        System.out.println("Entity.Exam ID: " + exam.examID);
        System.out.println("Code: " + exam.code);
        System.out.println("Title: " + exam.title);
        System.out.println("Create Date: " + date);
    }

    public static void question3(Exam exam) {
        String pattern = "yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(exam.createDate);
        System.out.println("Create Year: " + date);
    }

    public static void question4(Exam exam) {
        String pattern = "MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(exam.createDate);
        System.out.println("Create Month-Year: " + date);
    }

    public static void question5(Exam exam) {
        String pattern = "MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(exam.createDate);
        System.out.println("Create MM-DD: " + date);
    }
}
