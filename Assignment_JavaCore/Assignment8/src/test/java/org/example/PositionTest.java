package org.example;

import java.util.Collections;
import java.util.List;

import org.example.backend.controller.PositionController;
import org.example.entity.Position;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test cho PositionController + Validation.
 */
public class PositionTest {

    private static PositionController controller;

    @BeforeClass
    public static void setup() {
        controller = new PositionController();
    }

    // ================================================================ FIND ALL
    @Test
    public void test01_FindAll_ShouldReturnList() {
        System.out.println("\n[TEST] FIND ALL positions");
        List<Position> list = controller.findAll();
        assertNotNull(list);
        assertFalse("DB phải có ít nhất 1 position", list.isEmpty());
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " chức vụ");
    }

    // ================================================================ FIND BY ID
    @Test
    public void test02_FindById_ShouldReturnCorrect() {
        System.out.println("\n[TEST] FIND BY ID");
        List<Position> all = controller.findAll();
        int id = all.get(0).getId();
        Position pos = controller.findById(id);
        assertNotNull(pos);
        assertEquals(id, pos.getId());
        printTable(Collections.singletonList(pos));
        System.out.println("  => PASS: id=" + id);
    }

    // ================================================================ FIND BY NAME
    @Test
    public void test03_FindByName_ShouldReturnResults() {
        System.out.println("\n[TEST] FIND BY NAME 'DEV'");
        List<Position> list = controller.findByName("DEV");
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " kết quả");
    }

    // ================================================================ MOST / LEAST
    @Test
    public void test04_FindMostEmployees() {
        System.out.println("\n[TEST] FIND MOST EMPLOYEES position");
        List<Position> list = controller.findMostEmployees();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " chức vụ");
    }

    @Test
    public void test05_FindLeastEmployees() {
        System.out.println("\n[TEST] FIND LEAST EMPLOYEES position");
        List<Position> list = controller.findLeastEmployees();
        assertNotNull(list);
        printTable(list);
        System.out.println("  => PASS: " + list.size() + " chức vụ");
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
    public void test07_Create_InvalidEnum_ShouldFail() {
        System.out.println("\n[TEST] CREATE - tên không hợp lệ");
        String err = controller.create("INVALID_ROLE");
        assertNotNull("Phải trả về lỗi khi tên không hợp lệ", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test08_Create_DuplicateName_ShouldFail() {
        System.out.println("\n[TEST] CREATE - tên đã tồn tại (DEV)");
        String err = controller.create("DEV");
        assertNotNull("Phải trả về lỗi khi tên đã tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    // ================================================================ UPDATE - validation
    @Test
    public void test09_Update_IdZero_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id=0");
        String err = controller.update(0, "TEST");
        assertNotNull("Phải trả về lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test10_Update_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - id không tồn tại");
        String err = controller.update(99999, "TEST");
        assertNotNull("Phải trả về lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test11_Update_InvalidEnum_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - tên không hợp lệ");
        List<Position> all = controller.findAll();
        int id = all.get(0).getId();
        String err = controller.update(id, "MANAGER");
        assertNotNull("Phải trả về lỗi khi tên không hợp lệ", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test12_Update_DuplicateName_ShouldFail() {
        System.out.println("\n[TEST] UPDATE - tên đã tồn tại");
        List<Position> all = controller.findAll();
        assertTrue("Cần ít nhất 2 position", all.size() >= 2);
        int id = all.get(0).getId();
        String existingName = all.get(1).getName().name();
        String err = controller.update(id, existingName);
        assertNotNull("Phải trả về lỗi khi tên đã tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    // ================================================================ DELETE - validation
    @Test
    public void test13_Delete_IdZero_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id=0");
        String err = controller.delete(0);
        assertNotNull("Phải trả về lỗi khi id=0", err);
        System.out.println("  => PASS: " + err);
    }

    @Test
    public void test14_Delete_IdNotExist_ShouldFail() {
        System.out.println("\n[TEST] DELETE - id không tồn tại");
        String err = controller.delete(99999);
        assertNotNull("Phải trả về lỗi khi id không tồn tại", err);
        System.out.println("  => PASS: " + err);
    }

    // ================================================================ helper
    private void printTable(List<Position> list) {
        System.out.println("+-------+----------------+");
        System.out.printf("| %-5s | %-14s |%n", "ID", "Tên chức vụ");
        System.out.println("+-------+----------------+");
        if (list.isEmpty()) {
            System.out.printf("| %-5s | %-14s |%n", "", "Không có dữ liệu");
        } else {
            for (Position p : list) {
                System.out.printf("| %-5s | %-14s |%n", p.getId(), p.getName());
            }
        }
        System.out.println("+-------+----------------+");
    }
}
