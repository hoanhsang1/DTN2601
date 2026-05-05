package Exercises;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class ExerciseFive {
    public static void question1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào 3 số nguyên:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        System.out.println("3 số nguyên vừa nhập: " + a + ", " + b + ", " + c);
    }

    public static void question2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào 2 số thực:");
        float a = scanner.nextFloat();
        float b = scanner.nextFloat();
        System.out.println("2 số thực vừa nhập: " + a + ", " + b);
    }

    public static void question3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào họ và tên:");
        String name = scanner.nextLine();
        System.out.println("Họ và tên vừa nhập: " + name);
    }

    public static void question4() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào ngày sinh nhật (yyyy-MM-dd):");
        String dateString = scanner.next();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            System.out.println("Ngày sinh nhật: " + date);
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ.");
        }
    }

    public static Account question5() {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account();
        System.out.println("Nhập ID:");
        account.accountID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nhập username:");
        account.username = scanner.nextLine();
        System.out.println("Nhập fullName:");
        account.fullName = scanner.nextLine();
        System.out.println("Nhập email:");
        account.email = scanner.nextLine();
        
        System.out.println("Nhập Position (1: DEV, 2: TEST, 3: SCRUM_MASTER, 4: PM):");
        int pos = scanner.nextInt();
        Position position = new Position();
        position.positionID = pos;
        switch (pos) {
            case 1: position.positionName = PositionName.DEV; break;
            case 2: position.positionName = PositionName.TEST; break;
            case 3: position.positionName = PositionName.SCRUM_MASTER; break;
            case 4: position.positionName = PositionName.PM; break;
            default: position.positionName = PositionName.DEV; break;
        }
        account.position = position;
        return account;
    }

    public static Department question6() {
        Scanner scanner = new Scanner(System.in);
        Department department = new Department();
        System.out.println("Nhập ID:");
        department.departmentID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nhập tên phòng ban:");
        department.departmentName = scanner.nextLine();
        return department;
    }

    public static void question7() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Nhập vào một số chẵn:");
            int a = scanner.nextInt();
            if (a % 2 == 0) {
                System.out.println("Bạn vừa nhập số chẵn: " + a);
                break;
            } else {
                System.out.println("Đây không phải là số chẵn, vui lòng nhập lại.");
            }
        }
    }

    public static void question8_10_11(Account[] accounts, Group[] groups) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Mời bạn nhập vào chức năng muốn sử dụng:");
            System.out.println("1: Tạo account");
            System.out.println("2: Tạo department");
            System.out.println("3: Thêm group vào account");
            System.out.println("4: Thêm account vào 1 nhóm ngẫu nhiên");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    question5();
                    break;
                case 2:
                    question6();
                    break;
                case 3:
                    question9(accounts, groups);
                    break;
                case 4:
                    question11(accounts, groups);
                    break;
                default:
                    System.out.println("Mời bạn nhập lại");
                    continue;
            }
            
            System.out.println("Bạn có muốn thực hiện chức năng khác không? (Có/Không)");
            String cont = scanner.next();
            if (cont.equalsIgnoreCase("Không")) {
                return;
            }
        }
    }

    public static void question9(Account[] accounts, Group[] groups) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Danh sách username:");
        for (Account acc : accounts) {
            System.out.println("- " + acc.username);
        }
        System.out.println("Nhập vào username:");
        String username = scanner.next();
        
        System.out.println("Danh sách group:");
        for (Group group : groups) {
            System.out.println("- " + group.groupName);
        }
        System.out.println("Nhập vào tên group:");
        String groupName = scanner.next();
        
        Account selectedAcc = null;
        for (Account acc : accounts) {
            if (acc.username.equals(username)) {
                selectedAcc = acc;
                break;
            }
        }
        
        Group selectedGroup = null;
        for (Group group : groups) {
            if (group.groupName.equals(groupName)) {
                selectedGroup = group;
                break;
            }
        }
        
        if (selectedAcc != null && selectedGroup != null) {
            System.out.println("Đã thêm account " + selectedAcc.username + " vào group " + selectedGroup.groupName);
        } else {
            System.out.println("Không tìm thấy account hoặc group");
        }
    }

    public static void question11(Account[] accounts, Group[] groups) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Danh sách username:");
        for (Account acc : accounts) {
            System.out.println("- " + acc.username);
        }
        System.out.println("Nhập vào username:");
        String username = scanner.next();
        
        Account selectedAcc = null;
        for (Account acc : accounts) {
            if (acc.username.equals(username)) {
                selectedAcc = acc;
                break;
            }
        }
        
        if (selectedAcc != null) {
            Random random = new Random();
            int index = random.nextInt(groups.length);
            Group randomGroup = groups[index];
            System.out.println("Đã thêm account " + selectedAcc.username + " vào group ngẫu nhiên: " + randomGroup.groupName);
        } else {
            System.out.println("Không tìm thấy account");
        }
    }
}
