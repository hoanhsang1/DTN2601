package org.example.frontend;

import org.example.backend.controller.MajorController;
import org.example.backend.controller.StudentController;
import org.example.entity.Major;
import org.example.entity.Student;
import org.example.utils.ScannerUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Lớp giao diện chương trình, xử lý hiển thị menu và nhận input từ người dùng
public class Program {

    private final StudentController studentController = new StudentController();
    private final MajorController majorController     = new MajorController();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // Khởi động chương trình
    public void start() {
        loginMenu();
    }

    // Hiển thị menu đăng nhập / thoát
    private void loginMenu() {
        while (true) {
            printMenuHeader("STUDENT MANAGEMENT SYSTEM");
            System.out.printf("| %-2s. %-45s |%n", "1", "Đăng nhập");
            System.out.printf("| %-2s. %-45s |%n", "2", "Thoát chương trình");
            printMenuFooter();

            System.out.print("Lựa chọn của bạn: ");
            int choice = ScannerUtils.inputIntRange(1, 2);

            switch (choice) {
                case 1: login(); break;
                case 2:
                    System.out.println("Tạm biệt! Hẹn gặp lại.");
                    return;
            }
        }
    }

    // Thực hiện đăng nhập, vào menu chính nếu thành công
    private void login() {
        printMenuHeader("ĐĂNG NHẬP");
        System.out.print("Nhập Email:     ");
        String email = ScannerUtils.inputEmail();
        System.out.print("Nhập Mật Khẩu: ");
        String password = ScannerUtils.inputString();

        if (studentController.login(email, password)) {
            System.out.println(">> Đăng nhập thành công!");
            mainMenu();
        } else {
            System.out.println(">> Thông tin đăng nhập không đúng. Vui lòng thử lại.");
        }
    }

    // Hiển thị menu chính sau khi đăng nhập
    private void mainMenu() {
        while (true) {
            printMenuHeader("MỜI BẠN CHỌN CHỨC NĂNG");
            System.out.printf("| %-2s. %-45s |%n", "1", "Xem danh sách thông tin sinh viên");
            System.out.printf("| %-2s. %-45s |%n", "2", "Thêm sinh viên mới");
            System.out.printf("| %-2s. %-45s |%n", "3", "Cập nhật chuyên ngành cho sinh viên");
            System.out.printf("| %-2s. %-45s |%n", "4", "Xóa sinh viên theo ID");
            System.out.printf("| %-2s. %-45s |%n", "5", "Tìm kiếm sinh viên theo chuyên ngành");
            System.out.printf("| %-2s. %-45s |%n", "6", "Kiểm tra Password");
            System.out.printf("| %-2s. %-45s |%n", "7", "Đăng xuất");
            printMenuFooter();

            System.out.print("Lựa chọn của bạn: ");
            int choice = ScannerUtils.inputIntRange(1, 7);

            switch (choice) {
                case 1: showAllStudents();    break;
                case 2: addNewStudent();      break;
                case 3: updateStudentMajor(); break;
                case 4: deleteStudent();      break;
                case 5: searchByMajorName();  break;
                case 6: checkPassword();      break;
                case 7:
                    System.out.println(">> Đã đăng xuất.");
                    return;
            }
        }
    }

    // Hiển thị toàn bộ danh sách sinh viên dạng bảng
    private void showAllStudents() {
        List<Student> students = studentController.getAllStudents();
        if (students.isEmpty()) {
            System.out.println(">> Chưa có sinh viên nào trong hệ thống.");
            return;
        }
        System.out.println("\n===== DANH SÁCH SINH VIÊN =====");
        printStudentTable(students);
    }

    // Nhập thông tin và thêm mới sinh viên
    private void addNewStudent() {
        printMenuHeader("THÊM SINH VIÊN MỚI");

        System.out.print("Họ và tên:              ");
        String fullName = ScannerUtils.inputFullName();

        System.out.print("Email:                  ");
        String email = ScannerUtils.inputEmail();

        // Nhập ngày sinh, yêu cầu định dạng dd/MM/yyyy và lặp lại nếu sai
        Date birthday = inputDate();

        // Hiển thị danh sách chuyên ngành trước khi nhập ID
        printMajorList();
        System.out.println("Nhập ID chuyên ngành: ");

        // Nhập ID chuyên ngành, kiểm tra tồn tại trong danh sách
        int majorId = inputValidMajorId();

        Major major = new Major(majorId, "");
        Student student = new Student(0, fullName, email, birthday, major);

        if (studentController.addStudent(student)) {
            System.out.println(">> Thêm sinh viên thành công!");
        } else {
            System.out.println(">> Thêm sinh viên thất bại.");
        }
    }

    // Cập nhật chuyên ngành cho sinh viên theo studentId và majorId mới
    private void updateStudentMajor() {
        printMenuHeader("CẬP NHẬT CHUYÊN NGÀNH SINH VIÊN");

        System.out.print("Nhập ID sinh viên: ");
        int studentId = ScannerUtils.inputID();

        // Hiển thị danh sách chuyên ngành trước khi nhập ID
        printMajorList();
        // Nhập ID chuyên ngành mới, kiểm tra tồn tại trong danh sách
        int majorId = inputValidMajorId();

        if (studentController.updateStudentMajor(studentId, majorId)) {
            System.out.println(">> Cập nhật chuyên ngành thành công!");
        } else {
            System.out.println(">> Không tìm thấy sinh viên hoặc chuyên ngành với ID đã nhập.");
        }
    }

    // Xóa sinh viên theo ID
    private void deleteStudent() {
        printMenuHeader("XÓA SINH VIÊN");

        System.out.print("Nhập ID sinh viên cần xóa: ");
        int studentId = ScannerUtils.inputID();

        if (studentController.deleteStudent(studentId)) {
            System.out.println(">> Xóa sinh viên ID=" + studentId + " thành công!");
        } else {
            System.out.println(">> Không tìm thấy sinh viên với ID=" + studentId + ".");
        }
    }

    // Tìm và hiển thị sinh viên theo tên chuyên ngành
    private void searchByMajorName() {
        printMenuHeader("TÌM KIẾM SINH VIÊN THEO CHUYÊN NGÀNH");
        printMajorList();
        System.out.print("Nhập tên chuyên ngành: ");
        String majorName = ScannerUtils.inputString();

        List<Student> students = studentController.searchByMajorName(majorName);
        if (students.isEmpty()) {
            System.out.println(">> Không tìm thấy sinh viên thuộc chuyên ngành \"" + majorName + "\".");
        } else {
            System.out.println("\n===== KẾT QUẢ TÌM KIẾM: \"" + majorName + "\" =====");
            printStudentTable(students);
        }
    }

    // Nhập mật khẩu và kiểm tra tính hợp lệ
    private void checkPassword() {
        printMenuHeader("KIỂM TRA PASSWORD");
        System.out.println("Yêu cầu: 8-20 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt (@#$%^&+!), không có khoảng trắng.");
        System.out.println("----------------------------------------");
        System.out.print("Nhập mật khẩu cần kiểm tra: ");
        String password = ScannerUtils.inputString();

        if (studentController.validatePassword(password)) {
            System.out.println(">> [PASS] Mật khẩu hợp lệ!");
        } else {
            System.out.println(">> [FAIL] Mật khẩu không hợp lệ.");
        }
    }

    // Nhập ID chuyên ngành, kiểm tra tồn tại trong danh sách
    private int inputValidMajorId() {
        List<Major> majors = majorController.getAllMajors();
        while (true) {
            int id = ScannerUtils.inputID();
            for (Major m : majors) {
                if (m.getMajorId() == id) {
                    return id;
                }
            }
            System.out.println("  (ID chuyên ngành không tồn tại, vui lòng nhập lại)");
        }
    }

    // Hiển thị danh sách các chuyên ngành
    private void printMajorList() {
        List<Major> majors = majorController.getAllMajors();
        System.out.println("----- DANH SÁCH CHUYÊN NGÀNH HỆ THỐNG -----");
        for (Major m : majors) {
            System.out.printf("  ID: %-2d | Tên: %s%n", m.getMajorId(), m.getMajorName());
        }
        System.out.println("-------------------------------------------");
    }

    // In tiêu đề menu dạng khung
    private void printMenuHeader(String title) {
        System.out.println();
        System.out.printf("+---------------------------------------------------+%n");
        System.out.printf("|%-51s|%n", " " + title);
        System.out.printf("+---------------------------------------------------+%n");
    }

    // In đường kẻ dưới menu
    private void printMenuFooter() {
        System.out.printf("+---------------------------------------------------+%n");
    }

    // In danh sách sinh viên dạng bảng: ID | Họ tên | Email | Ngày sinh | Chuyên ngành
    private void printStudentTable(List<Student> students) {
        System.out.printf("%-6s | %-20s | %-25s | %-12s | %-20s%n",
                "ID", "Họ Tên", "Email", "Ngày Sinh", "Chuyên Ngành");
        System.out.println("-".repeat(95));

        for (Student s : students) {
            String birthday  = (s.getStudentBirthday() != null) ? DATE_FORMAT.format(s.getStudentBirthday()) : "N/A";
            String majorName = (s.getMajor() != null) ? s.getMajor().getMajorName() : "Chưa có";

            System.out.printf("%-6d | %-20s | %-25s | %-12s | %-20s%n",
                    s.getStudentId(),
                    s.getStudentFullName(),
                    s.getStudentEmail(),
                    birthday,
                    majorName);
        }

        System.out.println("-".repeat(95));
        System.out.println("Tổng số: " + students.size() + " sinh viên.");
    }

    // Nhập ngày sinh, yêu cầu định dạng dd/MM/yyyy và lặp lại nếu sai
    private Date inputDate() {
        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        return ScannerUtils.inputDateOfBirth();
    }
}