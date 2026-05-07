package com.vti.entity;

public class VietnamesePhone extends Phone {

    @Override
    public void insertContact(String name, String phone) {

        for (Contact c : contacts) {

            if (c.getName().equalsIgnoreCase(name)) {

                if (!c.getPhone().contains(phone)) {
                    c.setPhone(c.getPhone() + ", " + phone);
                }

                return;
            }
        }

        contacts.add(new Contact(name, phone));
    }

    @Override
    public void removeContact(String name) {

        boolean removed = false;

        for (int i = 0; i < contacts.size(); i++) {

            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                removed = true;
                i--;
            }
        }

        if (removed) {
            System.out.println("Đã xóa");
        } else {
            System.out.println("Không tìm thấy");
        }
    }

    @Override
    public void updateContact(String name, String newPhone) {

        boolean found = false;

        for (Contact c : contacts) {

            if (c.getName().equalsIgnoreCase(name)) {
                c.setPhone(newPhone);
                found = true;
            }
        }

        if (found) {
            System.out.println("Đã cập nhật");
        } else {
            System.out.println("Không tìm thấy");
        }
    }

    @Override
    public void searchContact(String name) {

        boolean found = false;

        for (Contact c : contacts) {

            if (c.getName().equalsIgnoreCase(name)) {
                System.out.println(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy");
        }
    }
}