import Entity.Question;

public class ExerciseThree {
    public void QuestionOne() {
        Integer salary = 5000;

        float f = salary.floatValue();

        System.out.printf("Salary: %.2f\n", f);
    }

    public void QuestionTwo() {
        String str = "1234567";

        int num = Integer.parseInt(str);

        System.out.println("Int: " + num);
    }

    public void QuestionThree() {
        Integer num = 1234567;

        int primitive = num; // unboxing

        System.out.println("Primitive int: " + primitive);
    }


}
