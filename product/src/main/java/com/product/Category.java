package com.product;

public class Category {
	
	private int category_id;
    private String category;
    private String tag;
    private int status=1;
    private static int idCounter=0;

    public Category(String category, String tag){
        this.category_id=++idCounter;
        this.category=category;
        this.tag=tag;
    }

    public int getCategoryId(){
        return this.category_id;
    }

    public String getCategory(){
        return this.category;
    }

    public String getTag(){
        return this.tag;
    }

    public int getStatus(){
        return this.status;
    }

    public void changeStatus(){
        if(this.getStatus()==1)
            this.setStatus(0);
        else
            this.setStatus(1);
    }

    public void setStatus(int i){
      this.status=i;
    }

    @Override
    public String toString(){
        return "{"+this.getCategoryId()+","+this.getCategory()+","+this.getTag()+","+this.getStatus()+"}";
    }

}
