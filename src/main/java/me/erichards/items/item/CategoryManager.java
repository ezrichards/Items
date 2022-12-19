package me.erichards.items.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Made by Ethan Richards
 * March 23, 2021
 */
public class CategoryManager {

    public List<Category> categories = new ArrayList<>();

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void clearCategories() {
        categories.clear();
    }

    public Category getCategoryByName(String name) {
        for(Category category : categories) {
            if(category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }
}
