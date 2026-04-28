import java.util.*;

public class ExerciseFour {

    Scanner sc = new Scanner(System.in);


    public void QuestionOne() {
        String s = sc.nextLine();
        String[] words = s.trim().split("\\s+");
        System.out.println(words.length);
    }


    public void QuestionTwo() {
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        System.out.println(sb.toString());
    }


    public void QuestionThree() {
        String name = sc.nextLine();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        System.out.println(name);
    }


    public void QuestionFour() {
        String name = sc.nextLine();
        for (int i = 0; i < name.length(); i++) {
            System.out.println("Ký tự thứ " + (i + 1) + " là: " + name.charAt(i));
        }
    }


    public void QuestionFive() {
        String ho = sc.nextLine();
        String ten = sc.nextLine();
        System.out.println(ho + " " + ten);
    }


    public void QuestionSix() {
        String full = sc.nextLine().trim();
        String[] parts = full.split("\\s+");

        System.out.println("Họ là: " + parts[0]);
        System.out.println("Tên là: " + parts[parts.length - 1]);

        String dem = "";
        for (int i = 1; i < parts.length - 1; i++) {
            dem += parts[i] + " ";
        }
        System.out.println("Tên đệm là: " + dem.trim());
    }


    public void QuestionSeven() {
        String input = sc.nextLine().trim().toLowerCase();
        String[] parts = input.split("\\s+");

        String result = "";
        for (String w : parts) {
            result += w.substring(0, 1).toUpperCase() + w.substring(1) + " ";
        }
        System.out.println(result.trim());
    }


    public void QuestionEight() {
        String[] groups = {"Java", "PHP", "Java Advanced", "C++"};
        for (String g : groups) {
            if (g.contains("Java")) {
                System.out.println(g);
            }
        }
    }


    public void QuestionNine() {
        String[] groups = {"Java", "PHP", "Java Advanced", "C++"};
        for (String g : groups) {
            if (g.equals("Java")) {
                System.out.println(g);
            }
        }
    }


    public void QuestionTen() {
        String a = sc.nextLine();
        String b = sc.nextLine();

        String rev = new StringBuilder(a).reverse().toString();
        System.out.println(rev.equals(b) ? "OK" : "KO");
    }


    public void QuestionEleven() {
        String str = sc.nextLine();
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == 'a') count++;
        }
        System.out.println(count);
    }


    public void QuestionTwelve() {
        String str = sc.nextLine();
        String rev = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            rev += str.charAt(i);
        }
        System.out.println(rev);
    }


    public static void QuestionThirteen() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        int count = 0;

        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }

        System.out.println(count);
    }


    public void QuestionFourteen() {
        String str = sc.nextLine();
        System.out.println(str.replace('e', '*'));
    }


    public void QuestionFifteen() {
        String str = sc.nextLine().trim();
        String[] words = str.split("\\s+");

        for (int i = words.length - 1; i >= 0; i--) {
            System.out.print(words[i] + " ");
        }
        System.out.println();
    }


    public void QuestionSixteen() {
        String str = sc.nextLine();
        int n = sc.nextInt();

        if (str.length() % n != 0) {
            System.out.println("KO");
        } else {
            for (int i = 0; i < str.length(); i += n) {
                System.out.println(str.substring(i, i + n));
            }
        }
    }
}