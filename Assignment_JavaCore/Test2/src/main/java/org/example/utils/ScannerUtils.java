package org.example.utils;

import org.example.common.*;
import java.util.Objects;
import java.util.Scanner;

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
                System.err.println("ID phải lớn hơn 0! Nhập lại:");
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
            if (email == null || email.trim().isEmpty() || !email.matches(org.example.common.StringCommon.EMAIL_REGEX)) {// a@b
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
                System.out.println("Họ tên chỉ chứa chữ và khoảng trắng, không chứa số hay ký tự đặc biệt! Nhập lại:");
            }
        }
    }

    public static String inputPassword() {
        while (true) {
            String password = inputString();
            if (password.length() < 6 || password.length() > 12) {
                System.out.println("Mật khẩu phải từ 6 đến 12 ký tự! Nhập lại:");
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
                System.out.println("Mật khẩu phải chứa ít nhất 1 ký tự viết hoa! Nhập lại:");
                continue;
            }
            return password;
        }
    }

    public static org.example.enums.ProSkill inputProSkill() {
        System.out.println("Nhập Kỹ năng chuyên môn (DEV, TEST, JAVA, SQL):");
        while (true) {
            String skillStr = inputString().toUpperCase().trim();
            try {
                return org.example.enums.ProSkill.valueOf(skillStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Kỹ năng không hợp lệ (chọn DEV, TEST, JAVA, SQL). Nhập lại:");
            }
        }
    }

    public static int inputExpInYear() {
        System.out.println("Nhập số năm kinh nghiệm (0-100):");
        while (true) {
            int exp = inputInt();
            if (exp >= 0 && exp <= 100) {
                return exp;
            } else {
                System.out.println("Số năm kinh nghiệm không hợp lệ (0-100). Nhập lại:");
            }
        }
    }
}
