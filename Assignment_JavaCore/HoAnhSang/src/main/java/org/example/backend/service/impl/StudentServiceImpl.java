package org.example.backend.service.impl;

import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.impl.StudentRepositoryImpl;
import org.example.backend.service.IStudentService;
import org.example.entity.Student;
import org.example.utils.PasswordValidator;

import java.util.List;

// Hiện thực IStudentService, xử lý nghiệp vụ và gọi xuống Repository
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository = new StudentRepositoryImpl();
    private final PasswordValidator passwordValidator = new PasswordValidator();

    // Xác thực đăng nhập
    @Override
    public boolean login(String email, String password) {
        return studentRepository.login(email, password);
    }

    // Lấy toàn bộ danh sách sinh viên
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Thêm sinh viên mới, kiểm tra email trùng trước khi thêm
    @Override
    public boolean addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getStudentEmail())) {
            System.out.println(">> Email \"" + student.getStudentEmail() + "\" đã tồn tại trong hệ thống!");
            return false;
        }
        return studentRepository.insert(student);
    }

    // Cập nhật chuyên ngành cho sinh viên theo studentId và majorId
    @Override
    public boolean updateStudentMajor(int studentId, int majorId) {
        return studentRepository.updateMajor(studentId, majorId);
    }

    // Xóa sinh viên theo ID
    @Override
    public boolean deleteStudent(int studentId) {
        return studentRepository.deleteById(studentId);
    }

    // Tìm sinh viên theo tên chuyên ngành
    @Override
    public List<Student> searchByMajorName(String majorName) {
        return studentRepository.findByMajorName(majorName);
    }

    // Kiểm tra mật khẩu hợp lệ, ủy thác cho PasswordValidator
    @Override
    public boolean validatePassword(String password) {
        return passwordValidator.isValid(password);
    }
}
