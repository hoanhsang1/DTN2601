package com.vti.entity;

import Enum.CategoryName;

public class CategoryQuestion {
    private int categoryID;
    private CategoryName categoryName;

    public CategoryQuestion() {
    }

    public CategoryQuestion(int categoryID, CategoryName categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
}
