package org.example;

import java.util.Collections;
import java.util.List;

import org.example.backend.controller.DepartmentController;
import org.example.entity.Department;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test cho DepartmentController + Validation.
 */
public class DepartmentTest {

    private static DepartmentController controller;

    @BeforeClass
    public static void setup() {
        controller = new DepartmentController();
        // dọn dữ liệu test cũ nếu còn sót
        for (Department d : controller.findByName("TEST_")) {
            controller.delete(d.getId());
        }
    }

    // ================================================================ FIND ALL
    @Test
    public void test01_FindAll_ShouldReturnList() {
        System.out.println("\n[TEST] FIND ALL departments");
        List<Department> list = controller.findAll();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " phòng ban");
    }

    // ================================================================ FIND BY ID
    @Test
    public void test02_FindById_ShouldReturnCorrect() {
        System.out.println("\n[TEST] FIND BY ID");
        List<Department> all = controller.findAll();
        assertFalse("DB phải có ít nhất 1 department", all.isEmpty());
        int id = all.get(0).getId();
        Department dep = controller.findById(id);
        assertNotNull(dep);
        assertEquals(id, dep.getId());
        printTable(Collections.singletonList(dep));
        System.out.println("  => PASS: id=" + id);
    }

    // ================================================================ FIND BY NAME
    @Test
    public void test03_FindByName_ShouldReturnResults() {
        System.out.println("\n[TEST] FIND BY NAME 'Sale'");
        List<Department> list = controller.findByName("Sale");
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " kết quả");
    }

    // ================================================================ MOST / LEAST EMPLOYEES
    @Test
    public void test04_FindMostEmployees() {
        System.out.println("\n[TEST] FIND MOST EMPLOYEES");
        List<Department> list = controller.findMostEmployees();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " phòng ban");
    }

    @Test
    public void test05_FindLeastEmployees() {
        System.out.println("\n[TEST] FIND LEAST EMPLOYEES");
        List<Department> list = controller.findLeastEmployees();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " phòng ban");
    }

    // ================================================================ CREATE - validation
    @Test
    public void test06_Create_EmptyName_ShouldFail() {
        System.out.println("\n[TEST] CREATE - tên rỗng");
        String err = controller.create("");
        assertNotNull("Phải trả về lỗi khi tên rỗng", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test07_Create_NullName_ShouldFail() {
        System.out.println("\n[TEST] CREATE - tên null");
        String err = controller.create(null);
        assertNotNull("Phải trả về lỗi khi tên null", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test08_Create_DuplicateName_ShouldFail() {
        System.out.println("\n[TEST] CREATE - tên đã tồn tại (Sale)");
        String err = controller.create("Sale");
        assertNotNull("Phải trả về lỗi khi tên đã tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test09_Create_ValidName_ShouldSucceed() {
        System.out.println("\n[TEST] CREATE - hợp lệ");
        String err = controller.create("TEST_DEPT_NEW");
        assertNull("Không được có lỗi khi tên hợp lệ", err);
        System.out.println("  => PASS: tạo thành công");
        // dọn
        for (Department d : controller.findByName("TEST_DEPT_NEW")) {
            controller.delete(d.getId());
        }
    }

    // ================================================================ UPDATE - validation
    @Test
    public void test10_Update_IdZero_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id=0");
        String err = controller.update(0, "SomeName");
        assertNotNull("Phải trả về lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test11_Update_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id không tồn tại");
        String err = controller.update(99999, "SomeName");
        assertNotNull("Phải trả về lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test12_Update_EmptyName_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - tên rỗng");
        List<Department> all = controller.findAll();
        int id = all.get(0).getId();
        String err = controller.update(id, "");
        assertNotNull("Phải trả về lỗi khi tên rỗng", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test13_Update_DuplicateName_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - tên trùng với phòng ban khác");
        List<Department> all = controller.findAll();
        // lấy 2 phòng ban khác nhau
        assertTrue("Cần ít nhất 2 phòng ban", all.size() >= 2);
        int id = all.get(0).getId();
        String otherName = all.get(1).getName();
        String err = controller.update(id, otherName);
        assertNotNull("Phải trả về lỗi khi tên trùng phòng ban khác", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test14_Update_SameName_ShouldSucceed() {
        System.out.println("\n[TEST] UPDATE - giữ nguyên tên (không đổi)");
        List<Department> all = controller.findAll();
        Department dep = all.get(0);
        // update với chính tên của nó -> phải thành công
        String err = controller.update(dep.getId(), dep.getName());
        assertNull("Giữ nguyên tên phải thành công", err);
        System.out.println("  => PASS: giữ nguyên tên id=" + dep.getId());
    }

    // ================================================================ DELETE - validation
    @Test
    public void test15_Delete_IdZero_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id=0");
        String err = controller.delete(0);
        assertNotNull("Phải trả về lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test16_Delete_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id không tồn tại");
        String err = controller.delete(99999);
        assertNotNull("Phải trả về lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test17_Delete_Valid_ShouldSucceed() {
        System.out.println("\n[TEST] DELETE - hợp lệ");
        controller.create("TEST_DELETE_ME");
        List<Department> found = controller.findByName("TEST_DELETE_ME");
        assertFalse("Phải tạo được bản ghi", found.isEmpty());
        int id = found.get(found.size() - 1).getId();
        String err = controller.delete(id);
        assertNull("Xóa hợp lệ không được có lỗi", err);
        assertNull("Sau khi xóa findById phải null", controller.findById(id));
        System.out.println("  => PASS: xóa id=" + id);
    }

    // ================================================================ helper
    private void printTable(List<Department> list) {
        System.out.println("+-------+------------------------+");
        System.out.printf("| %-5s | %-22s |%n", "ID", "Tên phòng ban");
        System.out.println("+-------+------------------------+");
        if (list.isEmpty()) {
            System.out.printf("| %-5s | %-22s |%n", "", "Không có dữ liệu");
        } else {
            for (Department d : list) {
                System.out.printf("| %-5s | %-22s |%n", d.getId(), d.getName());
            }
        }
        System.out.println("+-------+------------------------+");
    }
}
