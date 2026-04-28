import Entity.Department;
import java.util.*;

public class ExerciseFive {

    // dữ liệu mẫu
    public static Department[] init() {
        return new Department[] {
                new Department(1, "Accounting"),
                new Department(2, "Boss of director"),
                new Department(3, "Marketing"),
                new Department(4, "Sale"),
                new Department(5, "Waiting room")
        };
    }

    public static void QuestionOne(Department[] d) {
        System.out.println("ID: " + d[0].getDepartmentID());
        System.out.println("Name: " + d[0].getDepartmentName());
    }

    public static void QuestionTwo(Department[] d) {
        for (Department dep : d) {
            System.out.println("ID: " + dep.getDepartmentID()
                    + ", Name: " + dep.getDepartmentName());
        }
    }

    public static void QuestionThree(Department[] d) {
        System.out.println(d[0].getDepartmentID());
    }

    public static void QuestionFour(Department[] d) {
        System.out.println(d[0].getDepartmentName().equals("Phòng A"));
    }

    public static void QuestionFive(Department[] d) {
        boolean equal = d[0].getDepartmentName()
                .equals(d[1].getDepartmentName());
        System.out.println(equal);
    }

    public static void QuestionSix() {
        Department[] d = init();

        Arrays.sort(d, new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                return o1.getDepartmentName().compareTo(o2.getDepartmentName());
            }
        });

        for (Department dep : d) {
            System.out.println(dep.getDepartmentName());
        }
    }

    public static void QuestionSeven() {
        Department[] d = init();

        Arrays.sort(d, new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                return o1.getDepartmentName().compareTo(o2.getDepartmentName());
            }
        });

        for (Department dep : d) {
            System.out.println(dep.getDepartmentName());
        }
    }
}