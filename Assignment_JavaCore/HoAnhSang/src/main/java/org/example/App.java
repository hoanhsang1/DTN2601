package org.example;

import org.example.frontend.Program;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Điểm khởi chạy chính của chương trình Quản lý Sinh viên.
 */
public class App {
    public static void main(String[] args) {
        // Thiết lập mã hóa UTF-8 cho console
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Không hỗ trợ mã hóa UTF-8");
        }

        // Khởi tạo và chạy giao diện chương trình
        Program program = new Program();
        program.start();
    }
}
