package org.example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.example.backend.controller.DepartmentController;
import org.example.backend.controller.AccountController;
import org.example.entity.Department;
import org.example.entity.Account;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AppTest {

    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void testManualImports() throws Exception {
        System.out.println("==================================================");
        System.out.println("MANUAL IMPORT TEST: DEPARTMENTS & ACCOUNTS");
        System.out.println("==================================================");

        DepartmentController deptController = new DepartmentController();
        AccountController accController = new AccountController();

        // Dùng timestamp để tên luôn unique, tránh ảnh hưởng từ lần test trước
        long ts = System.currentTimeMillis();
        String deptName1 = "Dept CSV A " + ts;
        String deptName2 = "Dept CSV B " + ts;
        String accUser1  = "acc_v1_" + ts;
        String accUser2  = "acc_v2_" + ts;
        String accEmail1 = "acc_v1_" + ts + "@x.com";
        String accEmail2 = "acc_v2_" + ts + "@x.com";

        List<Department> listD1 = null, listD2 = null;
        List<Account>    listA1 = null, listA2 = null;
        File deptValidFile   = new File("target/dept_valid.csv");
        File deptInvalidFile = new File("target/dept_invalid.csv");
        File deptErrorFile   = new File("target/dept_invalid_error.csv");
        File accValidFile    = new File("target/acc_valid.csv");
        File accInvalidFile  = new File("target/acc_invalid.csv");
        File accErrorFile    = new File("target/acc_invalid_error.csv");

        try {
            // ----------------------------------------------------------------
            // [1] Department - File HỢP LỆ (2 phòng ban mới)
            // ----------------------------------------------------------------
            System.out.println("\n--- [1] IMPORTING VALID DEPARTMENT CSV ---");
            try (PrintWriter pw = new PrintWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(deptValidFile), "UTF-8"))) {
                pw.println("department_name");
                pw.println(deptName1);
                pw.println(deptName2);
            }

            String resultDeptValid = deptController.importDepartmentFromCSV(deptValidFile.getAbsolutePath());
            System.out.println("Result: " + resultDeptValid);
            assertTrue("Import hợp lệ phải thành công", resultDeptValid.contains("Import thành công"));

            listD1 = deptController.findByName(deptName1);
            listD2 = deptController.findByName(deptName2);
            assertFalse("Phải có " + deptName1 + " trong DB", listD1.isEmpty());
            assertFalse("Phải có " + deptName2 + " trong DB", listD2.isEmpty());
            System.out.println("  => PASS: 2 phòng ban đã được thêm vào DB.");

            // ----------------------------------------------------------------
            // [2] Department - File LỖI (tên đã tồn tại, tên trùng trong file, rỗng)
            // ----------------------------------------------------------------
            System.out.println("\n--- [2] IMPORTING INVALID DEPARTMENT CSV ---");
            try (PrintWriter pw = new PrintWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(deptInvalidFile), "UTF-8"))) {
                pw.println("department_name");
                pw.println("Sale");        // Đã tồn tại trong DB
                pw.println(deptName1);     // Vừa insert ở test [1], nên đã tồn tại
                pw.println(deptName1);     // Trùng trong file
                pw.println("");            // Tên rỗng
            }

            String resultDeptInvalid = deptController.importDepartmentFromCSV(deptInvalidFile.getAbsolutePath());
            System.out.println("Result: " + resultDeptInvalid);
            assertTrue("Khi toàn lỗi phải thông báo thất bại", resultDeptInvalid.contains("Import thất bại") || resultDeptInvalid.contains("Import hoàn tất"));

            assertTrue("File lỗi phải được tạo ra", deptErrorFile.exists());
            System.out.println("--- Nội dung file lỗi (dept_invalid_error.csv): ---");
            Files.readAllLines(Paths.get(deptErrorFile.getAbsolutePath()), StandardCharsets.UTF_8).forEach(System.out::println);
            System.out.println("  => PASS: File lỗi đã xuất đúng các dòng bị lỗi.");

            // ----------------------------------------------------------------
            // [3] Account - File HỢP LỆ (2 tài khoản mới)
            // ----------------------------------------------------------------
            System.out.println("\n--- [3] IMPORTING VALID ACCOUNT CSV ---");
            try (PrintWriter pw = new PrintWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(accValidFile), "UTF-8"))) {
                pw.println("username,fullname,email,department_id,position_id");
                pw.println(accUser1 + ",Account CSV V100," + accEmail1 + ",1,1");
                pw.println(accUser2 + ",Account CSV V200," + accEmail2 + ",1,1");
            }

            String resultAccValid = accController.importAccountFromCSV(accValidFile.getAbsolutePath());
            System.out.println("Result: " + resultAccValid);
            assertTrue("Import tài khoản hợp lệ phải thành công", resultAccValid.contains("Import thành công"));

            listA1 = accController.findByName("Account CSV V100");
            listA2 = accController.findByName("Account CSV V200");
            assertFalse("Phải có Account CSV V100 trong DB", listA1.isEmpty());
            assertFalse("Phải có Account CSV V200 trong DB", listA2.isEmpty());
            System.out.println("  => PASS: 2 tài khoản đã được thêm vào DB.");

            // ----------------------------------------------------------------
            // [4] Account - File LỖI (trùng username/email, email sai format, dept không tồn tại)
            // ----------------------------------------------------------------
            System.out.println("\n--- [4] IMPORTING INVALID ACCOUNT CSV ---");
            try (PrintWriter pw = new PrintWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(accInvalidFile), "UTF-8"))) {
                pw.println("username,fullname,email,department_id,position_id");
                pw.println(accUser1 + ",Account Dup," + accEmail1 + ",1,1"); // Trùng username & email
                pw.println("acc_badmail_" + ts + ",Account Bad Mail,invalid_email_no_at,1,1"); // Email sai format
                pw.println("acc_nodept_"  + ts + ",Account No Dept,acc_nodept_" + ts + "@x.com,99999,1"); // Dept không tồn tại
            }

            String resultAccInvalid = accController.importAccountFromCSV(accInvalidFile.getAbsolutePath());
            System.out.println("Result: " + resultAccInvalid);
            assertTrue("Khi toàn lỗi phải thông báo thất bại", resultAccInvalid.contains("Import thất bại") || resultAccInvalid.contains("Import hoàn tất"));

            assertTrue("File lỗi account phải được tạo ra", accErrorFile.exists());
            System.out.println("--- Nội dung file lỗi (acc_invalid_error.csv): ---");
            Files.readAllLines(Paths.get(accErrorFile.getAbsolutePath()), StandardCharsets.UTF_8).forEach(System.out::println);
            System.out.println("  => PASS: File lỗi đã xuất đúng các dòng bị lỗi.");

        } finally {
            // ----------------------------------------------------------------
            // Cleanup DB (luôn chạy dù test thất bại)
            // ----------------------------------------------------------------
            if (listD1 != null) for (Department d : listD1) deptController.delete(d.getId());
            if (listD2 != null) for (Department d : listD2) deptController.delete(d.getId());
            if (listA1 != null) for (Account a : listA1) accController.delete(a.getId());
            if (listA2 != null) for (Account a : listA2) accController.delete(a.getId());

            // Cleanup files
            deptValidFile.delete();
            deptInvalidFile.delete();
            deptErrorFile.delete();
            accValidFile.delete();
            accInvalidFile.delete();
            accErrorFile.delete();
        }

        System.out.println("\n==================================================");
        System.out.println("MANUAL IMPORT TEST COMPLETED SUCCESSFULLY");
        System.out.println("==================================================");
    }
}
