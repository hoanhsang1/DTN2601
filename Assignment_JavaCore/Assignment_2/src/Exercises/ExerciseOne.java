package Exercises;

import Entity.Account;
import Entity.Group;
import Entity.GroupAccount;

import java.util.List;
import java.util.Objects;

public class ExerciseOne {
    public void questionOne(Account account) {
        if (Objects.isNull(account.getDepartment())) {
            System.out.println("Nhân viên này chưa có phòng ban");
        }else {
            System.out.println("Phòng ban của nhân viên này là "+account.getDepartment().getDepartmentName());
        }

    }

    private int countGroupAccount(Account account, List<GroupAccount> groupAccount) {
        int count = 0;
        for (GroupAccount acc : groupAccount) {
            count += acc.equalsAcc(account) ? 1 : 0;
        }
        return count;
    }

    public void questionTwo(Account account, List<GroupAccount> groupAccount) {
        int count = countGroupAccount(account, groupAccount);
        if (count == 0) {
            System.out.println("Nhân viên này chưa có group");
        }else if(count <=2){
            System.out.println("Group của nhân viên này là Java Fresher, C# Fresher");
        }else if(count == 3){
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

    public QuestionThree(Account acc) {

    }


}
