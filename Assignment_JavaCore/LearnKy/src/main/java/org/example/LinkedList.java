package org.example;

public class LinkedList {
    Node head;
    Node tail;

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public void insert(int data) {
        Node newNode = new Node(data);
    }


}

