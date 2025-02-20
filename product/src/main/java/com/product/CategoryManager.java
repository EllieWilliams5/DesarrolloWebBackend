package com.product;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

	   
    private static List<Category> categories= new ArrayList<>();
    public CategoryManager(){}

    public void createCategory(Category cat){
        if(!checkDouble(cat.getCategory(), cat.getTag()))
            categories.add(cat);
        else
            System.out.println("Error, El tag o la categoria ya existen");
    }
    
    private static boolean checkDouble(String category, String tag){
        for(Category cat : categories){
            if(cat.getCategory().equalsIgnoreCase(category)||cat.getTag().equalsIgnoreCase(tag))
                return true;
        }
        return false;
}

    public List<Category> getCategories(){
       List<Category> st =new ArrayList<>();

       for(Category cat : categories){
            if(cat.getStatus()==1)
                	st.add(cat);
            }
       return st; 
    }

    public void deleteCategory(int id){
        for(Category cat : categories){
            if(cat.getCategoryId()==id)
                cat.changeStatus();
        }
    }

    public static void main(String[] args){
        CategoryManager cm =new CategoryManager();
        cm.getCategories();
        //Podría ser mejor que solo se manden las cadenas y que CategoryManager cree los objetos category
        cm.createCategory(new Category("Games","gms"));
        cm.createCategory(new Category("Games","gms"));
        cm.getCategories();
        cm.createCategory(new Category("Consoles","Cns"));
        cm.createCategory(new Category("Cards","crd"));
        cm.createCategory(new Category("afdd","asfadf"));
        cm.getCategories();
        cm.deleteCategory(4);
        cm.getCategories();
        cm.createCategory(new Category("lol","lol"));
        cm.getCategories();
    }
    
}
