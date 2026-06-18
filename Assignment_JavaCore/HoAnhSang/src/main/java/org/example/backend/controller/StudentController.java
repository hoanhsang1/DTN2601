package org.example.backend.controller;

import org.example.backend.service.IStudentService;
import org.example.backend.service.impl.StudentServiceImpl;
import org.example.entity.Student;

import java.util.List;

// Điều phối yêu cầu từ tầng Frontend xuống tầng Service
public class StudentController {

    private final IStudentService studentService = new StudentServiceImpl();

    // Xử lý đăng nhập
    public boolean login(String email, String password) {
        return studentService.login(email, password);
    }

    // Lấy danh sách toàn bộ sinh viên
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Thêm mới sinh viên
    public boolean addStudent(Student student) {
        return studentService.addStudent(student);
    }

    // Cập nhật chuyên ngành cho sinh viên
    public boolean updateStudentMajor(int studentId, int majorId) {
        return studentService.updateStudentMajor(studentId, majorId);
    }

    // Xóa sinh viên theo ID
    public boolean deleteStudent(int studentId) {
        return studentService.deleteStudent(studentId);
    }

    // Tìm sinh viên theo tên chuyên ngành
    public List<Student> searchByMajorName(String majorName) {
        return studentService.searchByMajorName(majorName);
    }

    // Kiểm tra mật khẩu hợp lệ
    public boolean validatePassword(String password) {
        return studentService.validatePassword(password);
    }
}
