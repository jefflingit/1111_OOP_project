import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Department {
    protected ArrayList<Course> generalCourses=new ArrayList<Course>();
    protected ArrayList<Course> PE=new ArrayList<Course>();
    //儲存使用者已修的必修課程
    protected ArrayList<Course> requireds=new ArrayList<Course>();
    //儲存系所要求的必修課程
    protected ArrayList<Course> deptRequired = new ArrayList<Course>();

    protected ArrayList<Course> selectives=new ArrayList<Course>();
    //儲存使用者已修的群修課程
    protected ArrayList<Course> partiallyRequireds=new ArrayList<Course>();
    protected ArrayList<Boolean>requirements=new ArrayList<Boolean>();

    double generalCredits=0;
    final double GENERALCREDITSNEEDED=28;
    final double PECREDITSNEEDED=4;
    boolean graduationRequirement=false;
    {
        for(int i=0;i<6;i++){
            requirements.add(false);
        }
    }

    public void requiredJudgement(){

    }

    public void selectiveJudgement(){

    }

    //用傳入csv檔案的方式設定該科系的必修課程

    public void addRequire(String fileName) {
        File file = new File(fileName);

        try {
            String[] courseInfo;
            double credit;
            Scanner readFile = new Scanner(file);

            //讀csv檔，並把科系要求的課程加進deptRequired
            while (readFile.hasNext()) {
                courseInfo = readFile.next().split(",");
                credit = Double.parseDouble(courseInfo[1]);
                Course course = new Course(courseInfo[0],credit);
                deptRequired.add(course);
            }
            readFile.close();
            //印出deptRequired裡面的所有課程
            //for (Course course: deptRequired) {
            //System.out.print(course.getName()+" "+course.getCredits());
            //System.out.println();
            //}

        }catch(FileNotFoundException e){
            System.out.print("File Not Found");
        }
    }

    public void addCourse(Course course){
        switch(course.getCategory()){
            case "通識":
                generalCourses.add(course);
                break;
            case "體育":
                PE.add(course);
                break;
            case "必修":
                requireds.add(course);
                break;
            case "選修":
                selectives.add(course);
                break;
            case "群修":
                partiallyRequireds.add(course);
                break;
        }

    }

    public void PERequirement() {
        System.out.println("<通識及體育課程>");
        if (PE.size() >= 4) {
            requirements.set(1, true);
        } else {
            requirements.set(1, false);
        }
    }

    public void generalRequirement(ArrayList<Course> generalCourses){
        double humanity=0;
        double society=0;
        double nature=0;
        double chinese= 0;
        double english=0;
        double total=0;
        ArrayList<Boolean>core=new ArrayList<Boolean>();
        ArrayList<Boolean>limitMarks=new ArrayList<Boolean>();
        for(int i=0;i<5;i++){
            limitMarks.add(false);
        }
        for(int i=0;i<3;i++){
            core.add(false);
        }
        boolean coreMark=false;
        boolean limitMark=false;
        boolean passMark=false;


        for(Course course:generalCourses){
            if(course.getSubcategory().contains("中文")){
                chinese = generalLimitRequirement(course,chinese, 3, 6);
                limitMark=generalLimitRequirement(course,3,6);
                limitMarks.set(3,limitMark);
            }else if(course.getSubcategory().contains("英文")){
                english = generalLimitRequirement(course, english, 6, 6);
                limitMark=generalLimitRequirement(course,6,6);
                limitMarks.set(4,limitMark);
            }else if(course.getSubcategory().contains("人文")){
                if(course.getSubcategory().contains("核通")){
                    core.set(0, true);
                    humanity=generalLimitRequirement(course,humanity,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(0,limitMark);
                }else{
                    humanity=generalLimitRequirement(course,humanity,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(0, limitMark);
                }
            }else if(course.getSubcategory().contains("社會")){
                if(course.getSubcategory().contains("核通")){
                    core.set(1, true);
                    society=generalLimitRequirement(course,society,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(1,limitMark);
                }else{
                    society=generalLimitRequirement(course,society,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(1, limitMark);
                }

            }else if(course.getSubcategory().contains("自然")){
                if(course.getSubcategory().contains("核通")){
                    core.set(2, true);
                    nature=generalLimitRequirement(course,nature,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(2,limitMark);
                }else{
                    nature=generalLimitRequirement(course,nature,3,9);
                    limitMark=generalLimitRequirement(course,3,9);
                    limitMarks.set(2, limitMark);
                }

            }
            generalCredits=humanity+society+nature+chinese+english;
        }

        int coreNum=0;
        if(generalCredits>28){

            for(boolean mark:core){
                if(!mark){
                    passMark=false;
                    requirements.set(1,passMark);
                    for(boolean num:core){
                        if(!num){
                            coreNum++;
                        }
                    }
                    passMark=false;
                    requirements.set(1,passMark);
                    System.out.printf("%2d core general course needed\n",coreNum);
                }else{
                    passMark=true;
                    requirements.set(1,passMark);
                }
            }
            passMark=false;
            requirements.set(1,passMark);
            System.out.printf("total general course credits: %.2f\n",generalCredits);

        }else{
            passMark=false;
            requirements.set(1,passMark);
            System.out.printf("total general course credits: %.2f\n",generalCredits);
        }
        if (!passMark & coreNum>=3){
            System.out.println("general course credits pass");
        }

        //System.out.printf("%.2f general course credits needed\n",GENERALCREDITSNEEDED-generalCredits);
    }



    public boolean generalLimitRequirement(Course course,double min,double max){
        if(course.getCredits()<min){
            return false;
        }else if(course.getCredits()>max){
            System.out.printf("exceed %-4s higher limit\n",course.getSubcategory());
            return true;
        }else{
            return true;
        }


    }
    public double generalLimitRequirement(Course course,double credits,double min,double max){
        if(course.getCredits()<min){
            credits+=course.getCredits();
            return credits;
        }else if(course.getCredits()>max){
            System.out.printf("exceed %-4s higher limit\n",course.getSubcategory());
            credits=max;
            return credits;
        }else{
            credits+=course.getCredits();
            return credits;
        }


    }

    public void summarize(){
        int counter=0;
        for(boolean pass:requirements){
            if(!pass){
                counter++;
                break;
            }else{
                graduationRequirement=true;
                counter++;
            }
            counter++;
        }
    }
}
