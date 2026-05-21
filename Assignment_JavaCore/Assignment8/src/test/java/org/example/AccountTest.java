package org.example;

import java.util.Collections;
import java.util.List;

import org.example.backend.controller.AccountController;
import org.example.backend.controller.DepartmentController;
import org.example.backend.controller.PositionController;
import org.example.entity.Account;
import org.example.entity.Department;
import org.example.entity.Position;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test cho AccountController + Validation.
 */
public class AccountTest {

    private static AccountController    accountController;
    private static DepartmentController departmentController;
    private static PositionController   positionController;
    private static int tempDeptId;
    private static int tempPosId;

    @BeforeClass
    public static void setup() {
        accountController    = new AccountController();
        departmentController = new DepartmentController();
        positionController   = new PositionController();

        List<Department> depts = departmentController.findAll();
        assertFalse("DB phải có ít nhất 1 department", depts.isEmpty());
        tempDeptId = depts.get(0).getId();

        List<Position> positions = positionController.findAll();
        assertFalse("DB phải có ít nhất 1 position", positions.isEmpty());
        tempPosId = positions.get(0).getId();

        System.out.println("[SETUP] deptId=" + tempDeptId + ", posId=" + tempPosId);
    }

    // helper tạo account test và trả về id
    private int createTemp(String suffix) {
        String err = accountController.create(
                "usr_" + suffix, "Test " + suffix, "test_" + suffix + "@x.com",
                tempDeptId, tempPosId);
        assertNull("Tạo account tạm phải thành công: " + err, err);
        List<Account> list = accountController.findByName("Test " + suffix);
        assertFalse("Phải tìm thấy account vừa tạo", list.isEmpty());
        return list.get(list.size() - 1).getId();
    }

    // ================================================================ FIND ALL
    @Test
    public void test01_FindAll_ShouldReturnList() {
        System.out.println("\n[TEST] FIND ALL accounts");
        List<Account> list = accountController.findAll();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " account");
    }

    // ================================================================ FIND BY ID
    @Test
    public void test02_FindById_ShouldReturnCorrect() {
        System.out.println("\n[TEST] FIND BY ID");
        int id = createTemp("FndId");
        Account acc = accountController.findById(id);
        assertNotNull(acc);
        assertEquals(id, acc.getId());
        printTable(Collections.singletonList(acc));
        System.out.println("  => PASS: id=" + id);
        accountController.delete(id);
    }

    // ================================================================ FIND BY NAME
    @Test
    public void test03_FindByName_ShouldReturnResults() {
        System.out.println("\n[TEST] FIND BY NAME");
        int id = createTemp("FndNm");
        List<Account> list = accountController.findByName("FndNm");
        assertFalse("Phải tìm thấy ít nhất 1 kết quả", list.isEmpty());
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " kết quả");
        accountController.delete(id);
    }

    // ================================================================ CREATE - validation
    @Test
    public void test04_Create_EmptyUsername_ShouldFail() {
        System.out.println("\n[TEST] CREATE - username rỗng");
        String err = accountController.create("", "Full Name", "a@b.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi username rỗng", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test05_Create_EmptyFullName_ShouldFail() {
        System.out.println("\n[TEST] CREATE - fullName rỗng");
        String err = accountController.create("usr_test5", "", "a@b.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi fullName rỗng", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test06_Create_InvalidEmail_ShouldFail() {
        System.out.println("\n[TEST] CREATE - email không có @");
        String err = accountController.create("usr_test6", "Full Name", "invalidemail", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi email không có @", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test07_Create_EmailAtStart_ShouldFail() {
        System.out.println("\n[TEST] CREATE - email bắt đầu bằng @");
        String err = accountController.create("usr_test7", "Full Name", "@domain.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi @ ở đầu", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test08_Create_DuplicateUsername_ShouldFail() {
        System.out.println("\n[TEST] CREATE - username đã tồn tại");
        int id = createTemp("DupUsr");
        String err = accountController.create("usr_DupUsr", "Other Name", "other@x.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi username trùng", err);
        System.out.println("  => PASS: " + err);
        accountController.delete(id);
    }

    @Test
    public void test09_Create_DuplicateEmail_ShouldFail() {
        System.out.println("\n[TEST] CREATE - email đã tồn tại");
        int id = createTemp("DupEml");
        String err = accountController.create("usr_other", "Other Name", "test_DupEml@x.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi email trùng", err);
        System.out.println("  => PASS: " + err);
        accountController.delete(id);
    }

    @Test
    public void test10_Create_DeptNotExist_ShouldFail() {
        System.out.println("\n[TEST] CREATE - departmentId không tồn tại");
        String err = accountController.create("usr_test10", "Full Name", "t10@x.com", 99999, tempPosId);
        assertNotNull("Phải lỗi khi departmentId không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test11_Create_PosNotExist_ShouldFail() {
        System.out.println("\n[TEST] CREATE - positionId không tồn tại");
        String err = accountController.create("usr_test11", "Full Name", "t11@x.com", tempDeptId, 99999);
        assertNotNull("Phải lỗi khi positionId không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test12_Create_Valid_ShouldSucceed() {
        System.out.println("\n[TEST] CREATE - hợp lệ");
        String err = accountController.create("usr_valid12", "Valid User", "valid12@x.com", tempDeptId, tempPosId);
        assertNull("Không được có lỗi khi dữ liệu hợp lệ", err);
        System.out.println("  => PASS: tạo thành công");
        List<Account> list = accountController.findByName("Valid User");
        if (!list.isEmpty()) accountController.delete(list.get(list.size() - 1).getId());
    }

    // ================================================================ UPDATE - validation
    @Test
    public void test13_Update_IdZero_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id=0");
        String err = accountController.update(0, "u", "n", "e@x.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test14_Update_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id không tồn tại");
        String err = accountController.update(99999, "u", "n", "e@x.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test15_Update_DuplicateUsername_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - username trùng account khác");
        int id1 = createTemp("UpdUsr1");
        int id2 = createTemp("UpdUsr2");
        // update id2 với username của id1
        String err = accountController.update(id2, "usr_UpdUsr1", "Name2", "upd2@x.com", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi username trùng account khác", err);
        System.out.println("  => PASS: " + err);
        accountController.delete(id1);
        accountController.delete(id2);
    }

    @Test
    public void test16_Update_InvalidEmail_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - email không hợp lệ");
        int id = createTemp("UpdEmail");
        String err = accountController.update(id, "usr_UpdEmail", "Name", "bademail", tempDeptId, tempPosId);
        assertNotNull("Phải lỗi khi email không hợp lệ", err);
        System.out.println("  => PASS: " + err);
        accountController.delete(id);
    }

    @Test
    public void test17_Update_Valid_ShouldSucceed() {
        System.out.println("\n[TEST] UPDATE - hợp lệ");
        int id = createTemp("UpdValid");
        String err = accountController.update(id, "usr_UpdValid", "Updated Name", "upd_valid@x.com", tempDeptId, tempPosId);
        assertNull("Không được có lỗi khi dữ liệu hợp lệ", err);
        Account updated = accountController.findById(id);
        assertNotNull(updated);
        assertEquals("usr_UpdValid", updated.getUsername());
        assertEquals("Updated Name", updated.getFullName());
        printTable(Collections.singletonList(updated));
        System.out.println("  => PASS: cập nhật id=" + id);
        accountController.delete(id);
    }

    // ================================================================ DELETE - validation
    @Test
    public void test18_Delete_IdZero_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id=0");
        String err = accountController.delete(0);
        assertNotNull("Phải lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test19_Delete_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id không tồn tại");
        String err = accountController.delete(99999);
        assertNotNull("Phải lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test20_Delete_Valid_ShouldSucceed() {
        System.out.println("\n[TEST] DELETE - hợp lệ");
        int id = createTemp("DelValid");
        String err = accountController.delete(id);
        assertNull("Không được có lỗi khi xóa hợp lệ", err);
        assertNull("Sau khi xóa findById phải null", accountController.findById(id));
        System.out.println("  => PASS: xóa id=" + id);
    }

    @Test
    public void test21_ImportAccountFromCSV() throws Exception {
        System.out.println("\n[TEST] IMPORT ACCOUNT FROM CSV");
        java.io.File csvFile = new java.io.File("target/test_import_acc.csv");
        try (java.io.PrintWriter pw = new java.io.PrintWriter(csvFile)) {
            pw.println("username,fullname,email,department_id,position_id");
            pw.println("acc_csv_1,Account CSV 1,acc_csv_1@test.com," + tempDeptId + "," + tempPosId);
            pw.println("acc_csv_1,Account CSV 1 Dup,acc_csv_1_dup@test.com," + tempDeptId + "," + tempPosId);
            pw.println("acc_csv_2,Account CSV 2,invalid_email," + tempDeptId + "," + tempPosId);
            pw.println("acc_csv_3,Account CSV 3,acc_csv_3@test.com,99999," + tempPosId);
        }

        String result = accountController.importAccountFromCSV(csvFile.getAbsolutePath());
        System.out.println("Result: " + result);
        org.junit.Assert.assertTrue(result.contains("Import hoàn tất") || result.contains("Import thành công"));

        java.io.File errFile = new java.io.File("target/test_import_acc_error.csv");
        org.junit.Assert.assertTrue("Error CSV should be created", errFile.exists());

        List<Account> list = accountController.findByName("Account CSV 1");
        org.junit.Assert.assertFalse(list.isEmpty());

        for (Account a : list) {
            accountController.delete(a.getId());
        }
        csvFile.delete();
        errFile.delete();
        System.out.println("  => PASS: account csv import tested successfully.");
    }

    // ================================================================ helper
    private void printTable(List<Account> list) {
        String border = "+-------+------------------+--------------------+----------------------------+--------------------+----------------+";
        System.out.println(border);
        System.out.printf("| %-5s | %-16s | %-18s | %-26s | %-18s | %-14s |%n",
                "ID", "Username", "Họ tên", "Email", "Phòng ban", "Chức vụ");
        System.out.println(border);
        if (list.isEmpty()) {
            System.out.printf("| %-5s | %-16s | %-18s | %-26s | %-18s | %-14s |%n",
                    "", "Không tìm thấy", "", "", "", "");
        } else {
            for (Account a : list) {
                System.out.printf("| %-5s | %-16s | %-18s | %-26s | %-18s | %-14s |%n",
                        a.getId(), a.getUsername(), a.getFullName(),
                        a.getEmail(), a.getDepartment().getName(), a.getPosition().getName());
            }
        }
        System.out.println(border);
    }
}
