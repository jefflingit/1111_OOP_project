public class Course {
    /**
     * The name of the course.
     */
    private String name ="";
    /**
     * The credits of the course.
     */
    private double credits=0.0;
    /**
     * The category of the course.
     */
    private String category="";
    /**
     * The subcategory of the course.
     */
    private String subcateogry="";


    /**
     * instantiate the object of Course with a given name and credits.
     * @param name
     * @param credits
     */
    public Course(String name, double credits){
        this.name=name;
        this.credits=credits;
    }

    /**
     * instantiate the object of Course with a given name, credits, category and subcategory.
     * @param name
     * @param credits
     * @param category
     * @param subcategory
     */
    public Course(String name, double credits,String category,String subcategory){
        this.name=name;
        this.credits=credits;
        this.category=category;
        this.subcateogry=subcategory;
    }

    /**
     * instantiate the object of Course with a given name, credits and category.
     * @param name
     * @param credits
     * @param category
     */
    public Course(String name, double credits,String category){
        this.name=name;
        this.credits=credits;
        this.category=category;
    }

    /**
     * 4 getters and setters for name, category, subcategory, credits.
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     *
     */
    public String getCategory(){
        return this.category;
    }

    /**
     *
     */
    public void setCategory(String category){
        this.category=category;
    }

    /**
     *
     */
    public String getSubcategory(){
        return this.subcateogry;
    }

    /**
     *
     */
    public void setSubcategory(String subcategory){
        this.subcateogry=subcategory;
    }

    /**
     *
     */
    public double getCredits(){
        return this.credits;
    }

    /**
     *
     */
    public void setCredits(double credits){
        this.credits=credits;
    }
}