package com.vti.frontend;

import com.vti.entity.VietnamesePhone;
import com.vti.entity.Employee;
import com.vti.entity.Manager;
import com.vti.entity.Waiter;

public class Exercise6 {

    public static void main(String[] args) {

        VietnamesePhone phone = new VietnamesePhone();

        phone.insertContact("Sang", "0123");
        phone.insertContact("Nam", "0456");

        phone.searchContact("Sang");

        phone.updateContact("Sang", "0999");

        phone.searchContact("Sang");

        phone.removeContact("Nam");

        phone.searchContact("Nam");
        Employee e = new Employee("Sang", 2.5);
        Manager m = new Manager("Nam", 3.0);
        Waiter w = new Waiter("Huy", 1.8);

        e.displayInfor();
        m.displayInfor();
        w.displayInfor();
    }
}