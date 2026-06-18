package org.example.backend.service;

import org.example.entity.Student;

import java.util.List;

// Interface định nghĩa các nghiệp vụ liên quan đến sinh viên
public interface IStudentService {

    // Xác thực đăng nhập
    boolean login(String email, String password);

    // Lấy toàn bộ danh sách sinh viên
    List<Student> getAllStudents();

    // Thêm sinh viên mới (kiểm tra email trùng trước)
    boolean addStudent(Student student);

    // Cập nhật chuyên ngành cho sinh viên
    boolean updateStudentMajor(int studentId, int majorId);

    // Xóa sinh viên theo ID
    boolean deleteStudent(int studentId);

    // Tìm sinh viên theo tên chuyên ngành
    List<Student> searchByMajorName(String majorName);

    // Kiểm tra mật khẩu hợp lệ
    boolean validatePassword(String password);
}
