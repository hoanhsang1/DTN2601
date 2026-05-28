package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
//        Menu menu = new Menu();
//        menu.run();
        Locale locale = new Locale("vi");
        ResourceBundle rb = ResourceBundle.getBundle("message_vi");
        System.out.println(rb.getString("hello"));

    }
}