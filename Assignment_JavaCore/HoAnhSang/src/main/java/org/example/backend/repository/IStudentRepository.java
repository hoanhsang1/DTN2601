package org.example.backend.repository;

import org.example.entity.Student;

import java.util.List;

// Interface định nghĩa các thao tác dữ liệu (CRUD) cho sinh viên
public interface IStudentRepository {

    // Kiểm tra đăng nhập tài khoản
    boolean login(String email, String password);

    // Lấy toàn bộ danh sách sinh viên (JOIN với Major)
    List<Student> findAll();

    // Kiểm tra email sinh viên đã tồn tại chưa
    boolean existsByEmail(String email);

    // Thêm mới sinh viên vào DB
    boolean insert(Student student);

    // Cập nhật chuyên ngành cho sinh viên theo studentId và majorId
    boolean updateMajor(int studentId, int majorId);

    // Xóa sinh viên theo ID
    boolean deleteById(int studentId);

    // Tìm danh sách sinh viên theo tên chuyên ngành
    List<Student> findByMajorName(String majorName);
}
