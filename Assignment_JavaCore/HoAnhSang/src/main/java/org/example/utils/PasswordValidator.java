package org.example.utils;

/**
 * Kiểm tra tính hợp lệ của mật khẩu theo 6 điều kiện.
 */
public class PasswordValidator {

    // Kiểm tra toàn bộ 6 điều kiện, in thông báo lỗi nếu vi phạm
    public boolean isValid(String password) {
        if (!hasValidLength(password)) {
            System.out.println("  [FAIL] Mật khẩu phải có độ dài từ 8 đến 20 ký tự.");
            return false;
        }
        if (!hasUppercase(password)) {
            System.out.println("  [FAIL] Mật khẩu phải chứa ít nhất 1 chữ cái in hoa (A-Z).");
            return false;
        }
        if (!hasLowercase(password)) {
            System.out.println("  [FAIL] Mật khẩu phải chứa ít nhất 1 chữ cái thường (a-z).");
            return false;
        }
        if (!hasDigit(password)) {
            System.out.println("  [FAIL] Mật khẩu phải chứa ít nhất 1 chữ số (0-9).");
            return false;
        }
        if (!hasSpecialChar(password)) {
            System.out.println("  [FAIL] Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt trong: @#$%^&+!");
            return false;
        }
        if (hasWhitespace(password)) {
            System.out.println("  [FAIL] Mật khẩu không được chứa khoảng trắng.");
            return false;
        }
        return true;
    }

    // Kiểm tra độ dài từ 8 đến 20 ký tự
    private boolean hasValidLength(String password) {
        return password.length() >= 8 && password.length() <= 20;
    }

    // Kiểm tra có ít nhất 1 chữ hoa
    private boolean hasUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra có ít nhất 1 chữ thường
    private boolean hasLowercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra có ít nhất 1 chữ số
    private boolean hasDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra có ít nhất 1 ký tự đặc biệt trong @#$%^&+!
    private boolean hasSpecialChar(String password) {
        String specialChars = "@#$%^&+!";
        for (char c : password.toCharArray()) {
            if (specialChars.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra có khoảng trắng không (true = có khoảng trắng = không hợp lệ)
    private boolean hasWhitespace(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }
}
