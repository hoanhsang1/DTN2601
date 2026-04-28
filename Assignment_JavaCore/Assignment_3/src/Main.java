import Entity.*;
import Enum.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // ==================== KHỞI TẠO DỮ LIỆU ====================
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 1. Tạo Department
            List<Department> departments = new ArrayList<>();
            departments.add(new Department(1, "Phòng Kỹ thuật"));
            departments.add(new Department(2, "Phòng Kinh doanh"));
            departments.add(new Department(3, "Phòng Nhân sự"));
            departments.add(new Department(4, "Phòng Marketing"));
            departments.add(new Department(5, "Phòng Tài chính"));

            // 2. Tạo Position
            List<Position> positions = new ArrayList<>();
            positions.add(new Position(1, PositionName.DEV));
            positions.add(new Position(2, PositionName.TEST));
            positions.add(new Position(3, PositionName.SCRUM_MASTER));
            positions.add(new Position(4, PositionName.PM));

            // 3. Tạo Account (có salary)
            List<Account> accounts = new ArrayList<>();
            accounts.add(new Account(1, "user1@company.com", "user1", "Nguyễn Văn A",
                    departments.get(0), positions.get(0), 5240.5f));
            accounts.add(new Account(2, "user2@company.com", "user2", "Trần Thị B",
                    departments.get(1), positions.get(1), 10970.055f));
            accounts.add(new Account(3, "user3@company.com", "user3", "Lê Văn C",
                    departments.get(2), positions.get(2), 10000));
            accounts.add(new Account(4, "user4@company.com", "user4", "Phạm Thị D",
                    departments.get(3), positions.get(3), 9999));
            accounts.add(new Account(5, "user5@company.com", "user5", "Hoàng Văn E",
                    departments.get(4), positions.get(0), 3000));
            accounts.add(new Account(6, "user6@company.com", "user6", "Hoàng Văn F",
                    null, positions.get(0), 8000));

            // 4. Tạo CategoryQuestion
            List<CategoryQuestion> categories = new ArrayList<>();
            categories.add(new CategoryQuestion(1, CategoryName.JAVA));
            categories.add(new CategoryQuestion(2, CategoryName.DOT_NET));
            categories.add(new CategoryQuestion(3, CategoryName.SQL));
            categories.add(new CategoryQuestion(4, CategoryName.POSTMAN));
            categories.add(new CategoryQuestion(5, CategoryName.RUBY));

            // 5. Tạo TypeQuestion
            List<TypeQuestion> typeQuestions = new ArrayList<>();
            typeQuestions.add(new TypeQuestion(1, TypeName.ESSAY));
            typeQuestions.add(new TypeQuestion(2, TypeName.MULTIPLE_CHOICE));
            typeQuestions.add(new TypeQuestion(3, TypeName.ESSAY));
            typeQuestions.add(new TypeQuestion(4, TypeName.MULTIPLE_CHOICE));

            // 6. Tạo Question
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(1, "Java là gì?", categories.get(0),
                    typeQuestions.get(0), accounts.get(0), sdf.parse("2024-01-01")));
            questions.add(new Question(2, "SQL là viết tắt của từ gì?", categories.get(2),
                    typeQuestions.get(1), accounts.get(1), sdf.parse("2024-01-02")));
            questions.add(new Question(3, ".NET Framework là gì?", categories.get(1),
                    typeQuestions.get(0), accounts.get(2), sdf.parse("2024-01-03")));
            questions.add(new Question(4, "Postman dùng để làm gì?", categories.get(3),
                    typeQuestions.get(1), accounts.get(3), sdf.parse("2024-01-04")));
            questions.add(new Question(5, "Ruby on Rails là gì?", categories.get(4),
                    typeQuestions.get(0), accounts.get(4), sdf.parse("2024-01-05")));

            // 7. Tạo Answer
            List<Answer> answers = new ArrayList<>();
            answers.add(new Answer(1, "Java là ngôn ngữ lập trình hướng đối tượng",
                    questions.get(0), true));
            answers.add(new Answer(2, "SQL là Structured Query Language",
                    questions.get(1), true));
            answers.add(new Answer(3, ".NET là framework của Microsoft",
                    questions.get(2), true));
            answers.add(new Answer(4, "Postman dùng để kiểm thử API",
                    questions.get(3), true));
            answers.add(new Answer(5, "Ruby on Rails là framework web",
                    questions.get(4), true));

            // 8. Tạo Group
            List<Group> groups = new ArrayList<>();
            groups.add(new Group(1, "Nhóm Java", accounts.get(0), sdf.parse("2024-01-01")));
            groups.add(new Group(2, "Nhóm .NET", accounts.get(1), sdf.parse("2024-01-02")));
            groups.add(new Group(3, "Nhóm SQL", accounts.get(2), sdf.parse("2024-01-03")));
            groups.add(new Group(4, "Nhóm Testing", accounts.get(3), sdf.parse("2024-01-04")));
            groups.add(new Group(5, "Nhóm DevOps", accounts.get(4), sdf.parse("2024-01-05")));

            // 9. Tạo GroupAccount
            List<GroupAccount> groupAccounts = new ArrayList<>();
            groupAccounts.add(new GroupAccount(groups.get(0), accounts.get(0), sdf.parse("2024-01-10")));
            groupAccounts.add(new GroupAccount(groups.get(0), accounts.get(1), sdf.parse("2024-01-11")));
            groupAccounts.add(new GroupAccount(groups.get(1), accounts.get(2), sdf.parse("2024-01-12")));
            groupAccounts.add(new GroupAccount(groups.get(2), accounts.get(3), sdf.parse("2024-01-13")));
            groupAccounts.add(new GroupAccount(groups.get(3), accounts.get(4), sdf.parse("2024-01-14")));

            // 10. Tạo Exam
            List<Exam> exams = new ArrayList<>();
            exams.add(new Exam(1, "EX001", "Kiểm tra Java cơ bản", categories.get(0),
                    60, accounts.get(0), sdf.parse("2024-02-01")));
            exams.add(new Exam(2, "EX002", "Kiểm tra SQL nâng cao", categories.get(2),
                    90, accounts.get(1), sdf.parse("2024-02-02")));
            exams.add(new Exam(3, "EX003", "Kiểm tra .NET Core", categories.get(1),
                    75, accounts.get(2), sdf.parse("2024-02-03")));
            exams.add(new Exam(4, "EX004", "Kiểm tra Postman", categories.get(3),
                    45, accounts.get(3), sdf.parse("2024-02-04")));
            exams.add(new Exam(5, "EX005", "Kiểm tra Ruby", categories.get(4),
                    60, accounts.get(4), sdf.parse("2024-02-05")));

            // 11. Tạo ExamQuestion
            List<ExamQuestion> examQuestions = new ArrayList<>();
            examQuestions.add(new ExamQuestion(exams.get(0), questions.get(0)));
            examQuestions.add(new ExamQuestion(exams.get(0), questions.get(1)));
            examQuestions.add(new ExamQuestion(exams.get(1), questions.get(2)));
            examQuestions.add(new ExamQuestion(exams.get(2), questions.get(3)));
            examQuestions.add(new ExamQuestion(exams.get(3), questions.get(4)));

            // ==================== TEST CÁC EXERCISE ====================

            System.out.println("================== EXERCISE 1 ==================");
            ExerciseOne ex1 = new ExerciseOne();

            // Question 1: Làm tròn lương
            System.out.println("\n--- Question 1: Làm tròn lương ---");
            ex1.QuestionOne(accounts);

            // Question 2: Số ngẫu nhiên 5 chữ số
            System.out.println("\n--- Question 2: Số ngẫu nhiên 5 chữ số ---");
            ex1.QuestionTwo();

            // Question 3: Lấy 2 số cuối
            System.out.println("\n--- Question 3: Lấy 2 số cuối của số 12345 ---");
            ex1.QuestionThree(12345);

            // Question 4: Tính thương (comment vì cần nhập từ bàn phím)
            System.out.println("\n--- Question 4: Tính thương (bỏ qua vì cần nhập tay) ---");
            // float result = ex1.QuestionFour();
            // System.out.println("Thương: " + result);

            System.out.println("\n================== EXERCISE 2 ==================");
            ExerciseTwo ex2 = new ExerciseTwo();

            // Question 1: Tạo 5 account mới
            System.out.println("\n--- Question 1: Tạo 5 account mới ---");
            ex2.QuestionOne(5);

            System.out.println("\n================== EXERCISE 3 ==================");
            ExerciseThree ex3 = new ExerciseThree();

            // Question 1: Boxing & Unboxing
            System.out.println("\n--- Question 1: Integer to Float ---");
            ex3.QuestionOne();

            // Question 2: String to Int
            System.out.println("\n--- Question 2: String to Int ---");
            ex3.QuestionTwo();

            // Question 3: Integer to int
            System.out.println("\n--- Question 3: Integer to int ---");
            ex3.QuestionThree();

            System.out.println("\n================== EXERCISE 4 ==================");
            ExerciseFour ex4 = new ExerciseFour();


            System.out.println("\n--- Question 1: Đếm số từ ---");
            System.out.print("Nhập xâu kí tự: ");
            ex4.QuestionOne();

            System.out.println("\n--- Question 2: Nối chuỗi ---");
            System.out.print("Nhập xâu s1: ");
            ex4.QuestionTwo();

            System.out.println("\n--- Question 3: Viết hoa chữ cái đầu ---");
            System.out.print("Nhập tên: ");
            ex4.QuestionThree();


            System.out.println("\n--- Question 4: In từng ký tự ---");
            System.out.print("Nhập tên: ");
            ex4.QuestionFour();


            System.out.println("\n--- Question 5: Nhập họ và tên ---");
            System.out.print("Nhập họ: ");
            ex4.QuestionFive();


            System.out.println("\n--- Question 6: Tách họ, tên đệm, tên ---");
            System.out.print("Nhập họ và tên đầy đủ: ");
            ex4.QuestionSix();


            System.out.println("\n--- Question 7: Chuẩn hóa chuỗi ---");
            System.out.print("Nhập họ và tên đầy đủ: ");
            ex4.QuestionSeven();


            System.out.println("\n--- Question 8: Group chứa 'Java' ---");
            ex4.QuestionEight();


            System.out.println("\n--- Question 9: Group 'Java' ---");
            ex4.QuestionNine();


            System.out.println("\n--- Question 10: Kiểm tra đảo ngược ---");
            System.out.print("Nhập chuỗi thứ nhất: ");
            System.out.print("Nhập chuỗi thứ hai: ");
            ex4.QuestionTen();


            System.out.println("\n--- Question 11: Đếm ký tự 'a' ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionEleven();


            System.out.println("\n--- Question 12: Đảo ngược chuỗi ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionTwelve();


            System.out.println("\n--- Question 13: Kiểm tra chuỗi không chứa số ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionThirteen();


            System.out.println("\n--- Question 14: Thay thế ký tự ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionFourteen();


            System.out.println("\n--- Question 15: Đảo ngược chuỗi theo từ ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionFifteen();


            System.out.println("\n--- Question 16: Chia chuỗi ---");
            System.out.print("Nhập chuỗi: ");
            ex4.QuestionSixteen();

            System.out.println("\n================== EXERCISE 5 ==================");

            // Tạo department array cho Exercise 5
            Department[] deptArray = {
                    new Department(1, "Accounting"),
                    new Department(2, "Boss of director"),
                    new Department(3, "Marketing"),
                    new Department(4, "Sale"),
                    new Department(5, "Waiting room")
            };

            // Question 1: In thông tin phòng ban thứ 1
            System.out.println("\n--- Question 1: Thông tin phòng ban thứ 1 ---");
            ExerciseFive.QuestionOne(deptArray);

            // Question 2: In tất cả phòng ban
            System.out.println("\n--- Question 2: Tất cả phòng ban ---");
            ExerciseFive.QuestionTwo(deptArray);

            // Question 3: In địa chỉ phòng ban thứ 1
            System.out.println("\n--- Question 3: Địa chỉ phòng ban thứ 1 ---");
            ExerciseFive.QuestionThree(deptArray);

            // Question 4: Kiểm tra tên phòng ban thứ 1
            System.out.println("\n--- Question 4: Kiểm tra tên 'Phòng A' ---");
            ExerciseFive.QuestionFour(deptArray);

            // Question 5: So sánh 2 phòng ban
            System.out.println("\n--- Question 5: So sánh phòng ban 1 và 2 ---");
            ExerciseFive.QuestionFive(deptArray);

            // Question 6 & 7: Sắp xếp phòng ban theo tên
            System.out.println("\n--- Question 6 & 7: Sắp xếp phòng ban theo tên ---");
            ExerciseFive.QuestionSix();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}