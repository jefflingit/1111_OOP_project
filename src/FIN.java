import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FIN extends Department{
    /**
     * The required credits that are needed
     */
    final double REQUIREDCREDITS=57.0;
    /**
     * The required credits that are needed
     */
    final double PARTIALLYREQUIREDCREDITS=3.0;
    /**
     * Total credits that are needed to graduate
     */
    final double TOTALCREDITS=128.0;
    /**
     * The selectives credits that are needed
     */
    final double SELECTIVENEED=TOTALCREDITS-PARTIALLYREQUIREDCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;

    //儲存系上規定之群修
    /**
     * The ArrayList to store the courses of the department partially required of category A that are needed
     */
    private ArrayList<Course> deptPartiallyRequired=new ArrayList<Course>();
    /**
     * The required credits that user has take
     */
    double required=0;
    /**
     * The partially required credits that user has taken
     */
    double partiallyRequired=0;
    /**
     * The selective credits that user has taken
     */
    double selective=0;


    /**
     * Use the super method to add requiredCredits threshold information to IB.
     * @param fileName
     * @throws IOException
     */
    public void addRequire(String fileName)throws IOException{
        super.addRequire(fileName,this.deptRequired);
    }

    /**
     * Use the super method to add partiallyRequiredCredits threshold information to IB.
     * @throws IOException
     */
    public void addPartiallyRequired() throws IOException {
        super.addRequire("FIN_partiallyRequired.csv",this.deptPartiallyRequired);
    }

    /**
     * Test the "required" courses and credits between student took and threshold (build ArrayList<Integer>, if the student has taken that class, remove the index of the class) ,and print out the credits information of required needs.
     * @param courses
     */
    public void requiredJudgement(ArrayList<Course> courses){

        //判斷使用者修習的必修課程跟系上的必修課程匹配的學分數
        //建立arrayList儲存有匹配的index
        ArrayList<Integer>storeIndex = new ArrayList<Integer>();
        for (Course userCourse: courses){
            for (Course deptCourse: deptRequired){
                if (userCourse.getName().equals(deptCourse.getName())){
                    required+=deptCourse.getCredits();
                    storeIndex.add(deptRequired.indexOf(deptCourse));
                }
            }
        }

        for (int index: storeIndex){
            deptRequired.remove(index);
        }

        if(required==REQUIREDCREDITS){
            requirements.set(2, true);
            System.out.println("必修學分門檻已通過！");
        }else{
            requirements.set(2, false);
            System.out.printf("應修 %.2f 學分\n",REQUIREDCREDITS);
            System.out.printf("已修 %.2f 學分\n",required);
            System.out.printf("尚缺 %.2f 學分\n",REQUIREDCREDITS-required);
            System.out.println("需要補齊的必修課程： ");
            for (Course deptCourse: deptRequired){
                System.out.print(deptCourse.getName()+" ");
            }
        }
    }

    /**
     * Test whether the student meet the threshold of selective credits and print out the credits information of selective needs.
     * @param courses
     */
    public void selectiveJudgement(ArrayList<Course> courses){
        for(Course course:courses){
            selective+=course.getCredits();
        }

        if(selective>=SELECTIVENEED){
            requirements.set(3,true);
            System.out.println("選修學分門檻已通過！");
        }else{
            requirements.set(3,false);
            System.out.printf("應修 %.2f 學分\n",SELECTIVENEED);
            System.out.printf("尚缺 %.2f 學分\n",SELECTIVENEED-selective);
        }
    }
    //傳入系所規定的群修課程
    /**
     * Store course-taken information to the ArrayList and test whether the student meet the threshold of PartiallyRequired credits and print out the credits information of partiallyRequired needs.
     * @param courses
     */
    public void partiallyRequiredJudgement(ArrayList<Course> courses){

        ArrayList<Integer>storeIndex = new ArrayList<Integer>();
        for(Course userCourse:courses){
            for (Course deptCourse: deptPartiallyRequired){
                if (userCourse.getName().equals(deptCourse.getName())){
                    partiallyRequired+=deptCourse.getCredits();
                    storeIndex.add(deptPartiallyRequired.indexOf(deptCourse));
                }
            }
        }

        for (int index: storeIndex){
            deptPartiallyRequired.remove(index);
        }

        if (partiallyRequired>=9){
            requirements.set(4, true);
            System.out.println("partiallyRequired credits pass");
        } else {
            requirements.set(4, false);
            System.out.printf("已修 %.2f 學分\n",partiallyRequired);
            System.out.printf(String.format("尚缺 %.2f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequired));
            System.out.printf(String.format("請挑以下其中 %.0f 堂課修習： \n",(PARTIALLYREQUIREDCREDITS-partiallyRequired)/3));
            for (Course deptCourse: deptPartiallyRequired){
                System.out.print(deptCourse.getName()+" ");
            }

        }
        System.out.printf("\n");
    }
    @Override
    /**
     * Override the super generalRequirement method of class Department.
     * @Override
     */
    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }
    @Override
    /**
     * Override the super PERequirement method of class Department.
     * @Override
     */
    public void PERequirement(){
        super.PERequirement();
    }
    @Override
    /**
     * Override the super summarize method of class Department.
     * @Override
     */
    public void summarize(){

        System.out.println("-".repeat(100));
        System.out.println("<必修課程>");
        System.out.println();
        requiredJudgement(requireds);
        System.out.println();
        System.out.println("-".repeat(100));

        System.out.println("<選修課程>");
        System.out.println();
        selectiveJudgement(selectives);
        System.out.println();
        System.out.println("-".repeat(100));

        System.out.println("<群修課程>");
        System.out.println();
        partiallyRequiredJudgement(partiallyRequireds);
        System.out.println();
        System.out.println();
        System.out.println("-".repeat(100));

        PERequirement();
        generalRequirement(generalCourses);
        System.out.println();

        super.graduationResult();
    }
}