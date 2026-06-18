package org.example;

import junit.framework.TestCase;
import org.example.backend.controller.StudentController;
import org.example.entity.Major;
import org.example.entity.Student;
import org.example.utils.ScannerUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class StudentSystemTest extends TestCase {

    private StudentController studentController;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studentController = new StudentController();
    }

    /**
     * 1. Test chức năng Đăng nhập (Login)
     */
    public void testLogin() {
        // Đăng nhập thành công với tài khoản Admin hợp lệ
        boolean success = studentController.login("admin@vti.edu.vn", "$2P9eTa8");
        assertTrue("Đăng nhập với email admin@vti.edu.vn và pass $2P9eTa8 phải thành công", success);

        // Đăng nhập thất bại với tài khoản sai mật khẩu
        boolean failPassword = studentController.login("admin@vti.edu.vn", "sai_mat_khau");
        assertFalse("Đăng nhập sai mật khẩu phải thất bại", failPassword);

        // Đăng nhập thất bại với tài khoản sai email
        boolean failEmail = studentController.login("khongtontai@vti.edu.vn", "$2P9eTa8");
        assertFalse("Đăng nhập sai email phải thất bại", failEmail);
    }

    /**
     * 2. Test logic validate ngày sinh độc lập (Đúng định dạng, tương lai, dưới 18 tuổi, hợp lệ)
     */
    public void testDateOfBirthValidation() {
        // Case A: Định dạng ngày sinh hợp lệ và từ 18 tuổi trở lên (Ví dụ: 15/05/1990)
        try {
            Date date = ScannerUtils.parseAndValidateDateOfBirth("15/05/1990");
            assertNotNull("Ngày sinh hợp lệ không được trả về null", date);
        } catch (Exception e) {
            fail("Ngày sinh 15/05/1990 hợp lệ nhưng lại ném ngoại lệ: " + e.getMessage());
        }

        // Case B: Ngày sinh trong tương lai (Ví dụ: 01/01/2030)
        try {
            ScannerUtils.parseAndValidateDateOfBirth("01/01/2030");
            fail("Ngày sinh trong tương lai 01/01/2030 phải ném ra ngoại lệ");
        } catch (IllegalArgumentException e) {
            assertTrue("Thông báo lỗi phải đề cập ngày tương lai", e.getMessage().contains("tương lai"));
        } catch (Exception e) {
            fail("Ngoại lệ ném ra phải là IllegalArgumentException cho ngày sinh tương lai");
        }

        // Case C: Ngày sinh dưới 18 tuổi (Ví dụ: 01/01/2015)
        try {
            ScannerUtils.parseAndValidateDateOfBirth("01/01/2015");
            fail("Ngày sinh dưới 18 tuổi 01/01/2015 phải ném ra ngoại lệ");
        } catch (IllegalArgumentException e) {
            assertTrue("Thông báo lỗi phải đề cập tuổi từ 18 trở lên", e.getMessage().contains("18 tuổi"));
        } catch (Exception e) {
            fail("Ngoại lệ ném ra phải là IllegalArgumentException cho người dưới 18 tuổi");
        }

        // Case D: Định dạng ngày sai (Ví dụ: abc)
        try {
            ScannerUtils.parseAndValidateDateOfBirth("abc");
            fail("Định dạng ngày 'abc' không hợp lệ phải ném ra ngoại lệ");
        } catch (ParseException e) {
            // Đúng kỳ vọng: ném ra ParseException
        } catch (Exception e) {
            fail("Định dạng ngày 'abc' không hợp lệ phải ném ra ParseException");
        }

        // Case E: Ngày không tồn tại trên thực tế (Ví dụ: 31/02/2000)
        try {
            ScannerUtils.parseAndValidateDateOfBirth("31/02/2000");
            fail("Ngày không tồn tại 31/02/2000 phải ném ra ngoại lệ");
        } catch (ParseException e) {
            // Đúng kỳ vọng: ném ra ParseException
        } catch (Exception e) {
            fail("Ngày không tồn tại 31/02/2000 phải ném ra ParseException");
        }
    }

    /**
     * 3. Test lấy toàn bộ danh sách sinh viên
     */
    public void testGetAllStudents() {
        List<Student> list = studentController.getAllStudents();
        assertNotNull("Danh sách sinh viên trả về không được null", list);
        assertFalse("Danh sách sinh viên mẫu trong DB không được trống", list.isEmpty());
    }

    /**
     * 4. Test chức năng Thêm sinh viên mới (thành công / trùng email)
     */
    public void testAddStudent() {
        // Chuẩn bị dữ liệu sinh viên mới có email duy nhất
        String uniqueEmail = "test_sv_" + System.currentTimeMillis() + "@vti.edu.vn";
        Date birthday = null;
        try {
            birthday = ScannerUtils.parseAndValidateDateOfBirth("15/05/2000");
        } catch (Exception ignored) {}

        Major major = new Major(1, "Công nghệ thông tin");
        Student newStudent = new Student(0, "Nguyễn Văn Test", uniqueEmail, birthday, major);

        // Thêm thành công
        boolean added = studentController.addStudent(newStudent);
        assertTrue("Thêm sinh viên mới với email chưa tồn tại phải thành công", added);

        // Thêm thất bại do trùng email
        Student duplicateStudent = new Student(0, "Trần Văn Trùng", uniqueEmail, birthday, major);
        boolean addedDuplicate = studentController.addStudent(duplicateStudent);
        assertFalse("Thêm sinh viên trùng email phải thất bại", addedDuplicate);
    }

    /**
     * 5. Test Cập nhật chuyên ngành của sinh viên
     */
    public void testUpdateStudentMajor() {
        // Lấy danh sách hiện có
        List<Student> list = studentController.getAllStudents();
        assertFalse("Phải có ít nhất 1 sinh viên trong DB để test update", list.isEmpty());
        int firstStudentId = list.get(0).getStudentId();

        // Cập nhật chuyên ngành sang ID 2 (Quản trị kinh doanh)
        boolean success = studentController.updateStudentMajor(firstStudentId, 2);
        assertTrue("Cập nhật chuyên ngành cho sinh viên hiện tại phải thành công", success);

        // Cập nhật cho sinh viên không tồn tại (-1)
        boolean fail = studentController.updateStudentMajor(-1, 2);
        assertFalse("Cập nhật chuyên ngành cho sinh viên ID=-1 phải thất bại", fail);
    }

    /**
     * 6. Test Xóa sinh viên
     */
    public void testDeleteStudent() {
        // Tạo sinh viên để xóa
        String emailForDelete = "delete_sv_" + System.currentTimeMillis() + "@vti.edu.vn";
        Date birthday = null;
        try {
            birthday = ScannerUtils.parseAndValidateDateOfBirth("15/05/2000");
        } catch (Exception ignored) {}

        Major major = new Major(1, "Công nghệ thông tin");
        Student tempStudent = new Student(0, "Sinh Viên Xóa", emailForDelete, birthday, major);

        studentController.addStudent(tempStudent);

        // Lấy lại ID của sinh viên vừa tạo
        List<Student> list = studentController.getAllStudents();
        int targetId = -1;
        for (Student s : list) {
            if (s.getStudentEmail().equals(emailForDelete)) {
                targetId = s.getStudentId();
                break;
            }
        }

        assertTrue("Tìm thấy ID của sinh viên tạm vừa tạo", targetId != -1);

        // Xóa thành công
        boolean deleted = studentController.deleteStudent(targetId);
        assertTrue("Xóa sinh viên vừa tạo phải thành công", deleted);

        // Xóa thất bại cho ID không tồn tại
        boolean deleteFail = studentController.deleteStudent(-1);
        assertFalse("Xóa sinh viên ID=-1 phải thất bại", deleteFail);
    }

    /**
     * 7. Test Tìm kiếm sinh viên theo tên chuyên ngành
     */
    public void testSearchByMajorName() {
        // Tìm kiếm chuyên ngành có dữ liệu (Ví dụ: "Công nghệ thông tin")
        List<Student> results = studentController.searchByMajorName("Công nghệ thông tin");
        assertNotNull("Danh sách tìm kiếm không được null", results);
        
        // Tìm kiếm với tên chuyên ngành không tồn tại
        List<Student> emptyResults = studentController.searchByMajorName("Không tồn tại chuyên ngành này");
        assertTrue("Tìm kiếm chuyên ngành không tồn tại phải trả về danh sách trống", emptyResults.isEmpty());
    }

    /**
     * 8. Test Kiểm tra độ mạnh mật khẩu (Password Validation)
     */
    public void testValidatePassword() {
        // Thỏa mãn điều kiện: 8-20 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt (@#$%^&+!), không có khoảng trắng
        assertTrue("Mật khẩu Admin@123 phải hợp lệ", studentController.validatePassword("Admin@123"));
        assertTrue("Mật khẩu $2P9eTa8 phải hợp lệ", studentController.validatePassword("$2P9eTa8"));
        assertTrue("Mật khẩu Pass123! phải hợp lệ", studentController.validatePassword("Pass123!"));

        // Không thỏa mãn độ dài (ngắn hơn 8 ký tự)
        assertFalse("Mật khẩu ngắn hơn 8 ký tự phải không hợp lệ", studentController.validatePassword("Ab1!"));

        // Không thỏa mãn (thiếu chữ viết hoa)
        assertFalse("Mật khẩu thiếu chữ viết hoa phải không hợp lệ", studentController.validatePassword("admin@123"));

        // Không thỏa mãn (thiếu số)
        assertFalse("Mật khẩu thiếu số phải không hợp lệ", studentController.validatePassword("Admin@@@@"));

        // Không thỏa mãn (chứa khoảng trắng)
        assertFalse("Mật khẩu chứa khoảng trắng phải không hợp lệ", studentController.validatePassword("Admin 123!"));
    }
}
