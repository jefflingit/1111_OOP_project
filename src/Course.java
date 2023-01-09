public class Course {
    private String name ="";
    private double credits=0.0;
    private String category="";
    private String subcateogry="";

    public Course(String name, double credits){
        this.name=name;
        this.credits=credits;
    }

    public Course(String name, double credits,String category,String subcategory){
        this.name=name;
        this.credits=credits;
        this.category=category;
        this.subcateogry=subcategory;
    }

    public Course(String name, double credits,String category){
        this.name=name;
        this.credits=credits;
        this.category=category;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public String getSubcategory(){
        return this.subcateogry;
    }

    public void setSubcategory(String subcategory){
        this.subcateogry=subcategory;
    }

    public double getCredits(){
        return this.credits;
    }

    public void setCredits(double credits){
        this.credits=credits;
    }
}
