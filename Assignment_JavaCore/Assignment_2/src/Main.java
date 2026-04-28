import Entity.*;
import Enum.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Tạo dữ liệu mẫu cho các Entity
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 1. Tạo Department (ít nhất 4 giá trị)
            List<Department> departments = new ArrayList<>();
            departments.add(new Department(1, "Phòng Kỹ thuật"));
            departments.add(new Department(2, "Phòng Kinh doanh"));
            departments.add(new Department(3, "Phòng Nhân sự"));
            departments.add(new Department(4, "Phòng Marketing"));
            departments.add(new Department(5, "Phòng Tài chính"));

            // 2. Tạo Position (ít nhất 4 giá trị)
            List<Position> positions = new ArrayList<>();
            positions.add(new Position(1, PositionName.DEV));
            positions.add(new Position(2, PositionName.TEST));
            positions.add(new Position(3, PositionName.SCRUM_MASTER));
            positions.add(new Position(4, PositionName.PM));

            // 3. Tạo Account (ít nhất 4 giá trị)
            List<Account> accounts = new ArrayList<>();
            accounts.add(new Account(1, "user1@company.com", "user1", "Nguyễn Văn A",
                    departments.get(0), positions.get(0)));
            accounts.add(new Account(2, "user2@company.com", "user2", "Trần Thị B",
                    departments.get(1), positions.get(1)));
            accounts.add(new Account(3, "user3@company.com", "user3", "Lê Văn C",
                    departments.get(2), positions.get(2)));
            accounts.add(new Account(4, "user4@company.com", "user4", "Phạm Thị D",
                    departments.get(3), positions.get(3)));
            accounts.add(new Account(5, "user5@company.com", "user5", "Hoàng Văn E",
                    departments.get(4), positions.get(0)));
            accounts.add(new Account(6, "user6@company.com", "user6", "Hoàng Văn F",
                    null, positions.get(0)));

            // 4. Tạo CategoryQuestion (ít nhất 4 giá trị)
            List<CategoryQuestion> categories = new ArrayList<>();
            categories.add(new CategoryQuestion(1, CategoryName.JAVA));
            categories.add(new CategoryQuestion(2, CategoryName.DOT_NET));
            categories.add(new CategoryQuestion(3, CategoryName.SQL));
            categories.add(new CategoryQuestion(4, CategoryName.POSTMAN));
            categories.add(new CategoryQuestion(5, CategoryName.RUBY));

            // 5. Tạo TypeQuestion (ít nhất 4 giá trị - chỉ có 2 enum nên tạo nhiều instance)
            List<TypeQuestion> typeQuestions = new ArrayList<>();
            typeQuestions.add(new TypeQuestion(1, TypeName.ESSAY));
            typeQuestions.add(new TypeQuestion(2, TypeName.MULTIPLE_CHOICE));
            typeQuestions.add(new TypeQuestion(3, TypeName.ESSAY));
            typeQuestions.add(new TypeQuestion(4, TypeName.MULTIPLE_CHOICE));

            // 6. Tạo Question (ít nhất 4 giá trị)
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

            // 7. Tạo Answer (ít nhất 4 giá trị)
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

            // 8. Tạo Group (ít nhất 4 giá trị)
            List<Group> groups = new ArrayList<>();
            groups.add(new Group(1, "Nhóm Java", accounts.get(0), sdf.parse("2024-01-01")));
            groups.add(new Group(2, "Nhóm .NET", accounts.get(1), sdf.parse("2024-01-02")));
            groups.add(new Group(3, "Nhóm SQL", accounts.get(2), sdf.parse("2024-01-03")));
            groups.add(new Group(4, "Nhóm Testing", accounts.get(3), sdf.parse("2024-01-04")));
            groups.add(new Group(5, "Nhóm DevOps", accounts.get(4), sdf.parse("2024-01-05")));

            // 9. Tạo GroupAccount (ít nhất 4 giá trị)
            List<GroupAccount> groupAccounts = new ArrayList<>();
            groupAccounts.add(new GroupAccount(groups.get(0), accounts.get(0), sdf.parse("2024-01-10")));
            groupAccounts.add(new GroupAccount(groups.get(0), accounts.get(1), sdf.parse("2024-01-11")));
            groupAccounts.add(new GroupAccount(groups.get(1), accounts.get(2), sdf.parse("2024-01-12")));
            groupAccounts.add(new GroupAccount(groups.get(2), accounts.get(3), sdf.parse("2024-01-13")));
            groupAccounts.add(new GroupAccount(groups.get(3), accounts.get(4), sdf.parse("2024-01-14")));

            // 10. Tạo Exam (ít nhất 4 giá trị)
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

            // 11. Tạo ExamQuestion (ít nhất 4 giá trị)
            List<ExamQuestion> examQuestions = new ArrayList<>();
            examQuestions.add(new ExamQuestion(exams.get(0), questions.get(0)));
            examQuestions.add(new ExamQuestion(exams.get(0), questions.get(1)));
            examQuestions.add(new ExamQuestion(exams.get(1), questions.get(2)));
            examQuestions.add(new ExamQuestion(exams.get(2), questions.get(3)));
            examQuestions.add(new ExamQuestion(exams.get(3), questions.get(4)));

            ExerciseOne exerciseOne = new ExerciseOne();
            exerciseOne.questionOne(accounts.get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}