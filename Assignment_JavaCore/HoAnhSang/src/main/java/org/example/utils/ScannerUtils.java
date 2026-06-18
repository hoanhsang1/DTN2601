package org.example.utils;

import org.example.common.StringCommon;

import java.util.Objects;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;


// hỗ trợ nhập dữ liệu và check du lieu nhap tu ban phim
public class ScannerUtils {
    private static Scanner sc = new Scanner(System.in);

    public static int inputInt() {
        while (true) {
            try {
                // Nhập vào 1 chuỗi ký tự
                // Integer.parseInt ==> convert từ String sang Interger
                // TH1: Nếu nhập vào chuỗi là số nguyên ==> convert thành công
                // TH2: Nếu nhập vào chuỗi là ko số nguyên ==> có exception ==> Nhập lại
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Nhập lại...");
            }
        }
    }

    public static int inputIntRange(int min, int max) {
        while (true) {
            try {
                // Nhập vào 1 chuỗi ký tự
                // Integer.parseInt ==> convert từ String sang Interger
                // TH1: Nếu nhập vào chuỗi là số nguyên ==> convert thành công
                // TH2: Nếu nhập vào chuỗi là ko số nguyên ==> có exception ==> Nhập lại
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Nhập lại...");

            } catch (Exception e) {
                System.out.println("Nhập lại...");
            }
        }
    }

    public static int inputID() {
        while (true) {
            int number = ScannerUtils.inputInt();
            if (number > 0) {
                return number;
            } else {
                System.err.println("ID phải lớn hơn 0! \nNhập lại:");
            }
        }
    }

    public static String inputString() {
        while (true) {
            String string = sc.nextLine();
            if (Objects.nonNull(string) && !string.trim().isEmpty()) {
                return string;
            } else {
                System.err.println("Nhập lại:");
            }
        }
    }

    // kiem tra dinh dang email xem co hop le ko
    public static String inputEmail() {
        while (true) {

            String email = sc.nextLine();// equals(); so sanh gtri,   == so sánh địa chỉ ,  biểu thức chính quy, matches(): so sánh  theo quy tắc
            if (email == null || email.trim().isEmpty() || !email.matches(StringCommon.EMAIL_REGEX)) {// a@b
                System.out.print("Nhập lại: ");
            } else {
                return email;
            }
        }
    }

    public static String inputPhoneNumber() {

        while (true) {

            boolean isNumber = true;
            String number = ScannerUtils.inputString();

            // bắt đầu bằng 0
            if (number.charAt(0) != '0') {
                isNumber = false;
            }

            // độ dài 9-12
            if (number.length() > 12 || number.length() < 9) {
                isNumber = false;
            }

            // kiểm tra toàn bộ ký tự là số
            for (int i = 0; i < number.length(); i++) {

                if (!Character.isDigit(number.charAt(i))) {
                    isNumber = false;
                    break;
                }
            }

            // kiểm tra cuối cùng
            if (isNumber) {
                return number;
            }

            System.out.print("Nhập lại: ");
        }
    }

    public static String inputFullName() {
        while (true) {
            String fullName = inputString();
            if (fullName.matches("^[\\p{L}\\s]+$")) {
                return fullName.trim();
            } else {
                System.out.println("Họ tên chỉ chứa chữ và khoảng trắng, không chứa số hay ký tự đặc biệt! \nNhập lại:");
            }
        }
    }

    public static String inputPassword() {
        while (true) {
            String password = inputString();
            if (password.length() < 6 || password.length() > 12) {
                System.out.println("Mật khẩu phải từ 6 đến 12 ký tự! \nNhập lại:");
                continue;
            }
            boolean hasUppercase = false;
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUppercase = true;
                    break;
                }
            }
            if (!hasUppercase) {
                System.out.println("Mật khẩu phải chứa ít nhất 1 ký tự viết hoa! \nNhập lại:");
                continue;
            }
            return password;
        }
    }

    public static Date parseAndValidateDateOfBirth(String input) throws ParseException, IllegalArgumentException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date birthday = dateFormat.parse(input);

        Date now = new Date();
        if (birthday.after(now)) {
            throw new IllegalArgumentException("Ngày sinh không được là ngày trong tương lai!");
        }

        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthday);
        Calendar todayCal = Calendar.getInstance();

        int age = todayCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        if (todayCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        if (age < 18) {
            throw new IllegalArgumentException("Sinh viên phải từ 18 tuổi trở lên!");
        }

        return birthday;
    }

    public static Date inputDateOfBirth() {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return parseAndValidateDateOfBirth(input);
            } catch (ParseException e) {
                System.out.println("Ngày sinh không đúng định dạng dd/MM/yyyy hoặc không tồn tại! \nNhập lại:");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Nhập lại:");
            } catch (Exception e) {
                System.out.println("Ngày sinh không hợp lệ! \nNhập lại:");
            }
        }
    }

}
