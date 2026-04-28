import Entity.Account;
import Entity.Group;

import java.util.Objects;

public class ExerciseOne {
    public void questionOne(Account account) {
        if (Objects.isNull(account.getDepartment())) {
            System.out.println("Nhân viên này chưa có phòng ban");
        }else {
            System.out.println("Phòng ban của nhân viên này là "+account.getDepartment().getDepartmentName());
        }

    }
    public void questionTwo(Account account) {
        if (Objects.isNull(account.get)) {
            System.out.println("Nhân viên này chưa có phòng ban");
        }else {
            System.out.println("Phòng ban của nhân viên này là "+account.getDepartment().getDepartmentName());
        }
    }
}
