import Entity.Account;
import Entity.Department;
import Entity.Group;

import java.util.Objects;

public class ExerciseOne {
    public static void question1(Account a2) {
        if (Objects.isNull(a2.department)) {
            System.out.println("Nhân viên này chưa có phòng ban");
        } else {
            System.out.println("Phòng ban của nhân viên này là " + a2.department.departmentName);
        }
    }

    public static void question2(Account a2) {
        if (a2.groups == null || a2.groups.length == 0) {
            System.out.println("Nhân viên này chưa có group");
        } else if (a2.groups.length == 1 || a2.groups.length == 2) {
            System.out.println("Entity.Group của nhân viên này là Java Fresher, C# Fresher");
        } else if (a2.groups.length == 3) {
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

    public static void question3(Account a2) {
        System.out.println(a2.department == null ? "Nhân viên này chưa có phòng ban" : "Phòng ban của nhân viên này là " + a2.department.departmentName);
    }

    public static void question4(Account a1) {
        System.out.println(a1.position.positionName == PositionName.DEV ? "Đây là Developer" : "Người này không phải là Developer");
    }

    public static void question5(Group g1) {
        if (g1.accounts == null) {
            System.out.println("Nhóm không có thành viên");
            return;
        }
        int count = g1.accounts.length;
        switch (count) {
            case 1:
                System.out.println("Nhóm có một thành viên");
                break;
            case 2:
                System.out.println("Nhóm có hai thành viên");
                break;
            case 3:
                System.out.println("Nhóm có ba thành viên");
                break;
            default:
                System.out.println("Nhóm có nhiều thành viên");
                break;
        }
    }

    public static void question6(Account a2) {
        int count = a2.groups == null ? 0 : a2.groups.length;
        switch (count) {
            case 0:
                System.out.println("Nhân viên này chưa có group");
                break;
            case 1:
            case 2:
                System.out.println("Entity.Group của nhân viên này là Java Fresher, C# Fresher");
                break;
            case 3:
                System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
                break;
            default:
                System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
                break;
        }
    }

    public static void question7(Account a1) {
        switch (a1.position.positionName) {
            case DEV:
                System.out.println("Đây là Developer");
                break;
            default:
                System.out.println("Người này không phải là Developer");
                break;
        }
    }

    public static void question8(Account[] accounts) {
        for (Account acc : accounts) {
            System.out.println("Email: " + acc.email + " - FullName: " + acc.fullName + " - Entity.Department: " + acc.department.departmentName);
        }
    }

    public static void question9(Department[] departments) {
        for (Department dept : departments) {
            System.out.println("Id: " + dept.departmentID + " - Name: " + dept.departmentName);
        }
    }

    public static void question10(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
        }
    }

    public static void question11(Department[] departments) {
        for (int i = 0; i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
        }
    }

    public static void question12(Department[] departments) {
        for (int i = 0; i < 2 && i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
        }
    }

    public static void question13(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            if (i == 1) continue;
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
        }
    }

    public static void question14(Account[] accounts) {
        for (Account acc : accounts) {
            if (acc.accountID < 4) {
                System.out.println("Email: " + acc.email + " - FullName: " + acc.fullName + " - Entity.Department: " + acc.department.departmentName);
            }
        }
    }

    public static void question15() {
        for (int i = 0; i <= 20; i += 2) {
            System.out.println(i);
        }
    }

    public static void question16_10(Account[] accounts) {
        int i = 0;
        while (i < accounts.length) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
            i++;
        }
    }

    public static void question16_11(Department[] departments) {
        int i = 0;
        while (i < departments.length) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
            i++;
        }
    }

    public static void question16_12(Department[] departments) {
        int i = 0;
        while (i < 2 && i < departments.length) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
            i++;
        }
    }

    public static void question16_13(Account[] accounts) {
        int i = 0;
        while (i < accounts.length) {
            if (i == 1) {
                i++;
                continue;
            }
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
            i++;
        }
    }

    public static void question16_14(Account[] accounts) {
        int i = 0;
        while (i < accounts.length) {
            if (accounts[i].accountID >= 4) {
                i++;
                continue;
            }
            System.out.println("Email: " + accounts[i].email + " - FullName: " + accounts[i].fullName + " - Entity.Department: " + accounts[i].department.departmentName);
            i++;
        }
    }

    public static void question16_15() {
        int i = 0;
        while (i <= 20) {
            System.out.println(i);
            i += 2;
        }
    }

    public static void question17_10(Account[] accounts) {
        if (accounts.length == 0) return;
        int i = 0;
        do {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
            i++;
        } while (i < accounts.length);
    }

    public static void question17_11(Department[] departments) {
        if (departments.length == 0) return;
        int i = 0;
        do {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
            i++;
        } while (i < departments.length);
    }

    public static void question17_12(Department[] departments) {
        if (departments.length == 0) return;
        int i = 0;
        do {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
            i++;
        } while (i < 2 && i < departments.length);
    }

    public static void question17_13(Account[] accounts) {
        if (accounts.length == 0) return;
        int i = 0;
        do {
            if (i == 1) {
                i++;
                continue;
            }
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("Full name: " + accounts[i].fullName);
            System.out.println("Phòng ban: " + accounts[i].department.departmentName);
            i++;
        } while (i < accounts.length);
    }

    public static void question17_14(Account[] accounts) {
        if (accounts.length == 0) return;
        int i = 0;
        do {
            if (accounts[i].accountID >= 4) {
                i++;
                continue;
            }
            System.out.println("Email: " + accounts[i].email + " - FullName: " + accounts[i].fullName + " - Entity.Department: " + accounts[i].department.departmentName);
            i++;
        } while (i < accounts.length);
    }

    public static void question17_15() {
        int i = 0;
        do {
            System.out.println(i);
            i += 2;
        } while (i <= 20);
    }
}
