package com.ohgiraffers.problem;

public class MenuAndCategory {

    private Menu menu;
    private Category category;

    public MenuAndCategory(){}

    public MenuAndCategory(Menu menu, Category category) {
        this.menu = menu;
        this.category = category;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "MenuAndCategory{" +
                "menu=" + menu +
                ", category=" + category +
                '}';
    }
}
