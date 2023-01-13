import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IB extends Department {
    /**
     * The required credits that are needed
     */
    final double REQUIREDCREDITS=48.0;
    /**
     * The partially required credits that are needed
     */
    final double PARTIALLYREQUIREDCREDITS=9.0;
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
     * Use the super method to add requiredCredits threshold information to MAB.
     * @param fileName
     * @throws IOException
     */
    public void addRequire(String fileName)throws IOException {
        super.addRequire(fileName,this.deptRequired);
    }
    /**
     *Use the super method to add partiallyRequiredCredits threshold information to ACCT.
     * @throws IOException
     */
    public void addPartiallyRequired() throws IOException {
        super.addRequire("deptRequired_CSV/IB_partiallyRequired.csv",this.deptPartiallyRequired);
    }



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
            System.out.println();
        }
    }

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
            System.out.println("群修學分門檻已通過！");
        } else {
            requirements.set(4, false);
            System.out.printf("已修 %.2f 學分\n",partiallyRequired);
            System.out.printf(String.format("尚缺 %.2f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequired));
            System.out.printf(String.format("請挑以下其中 %.0f 堂課修習： \n",(PARTIALLYREQUIREDCREDITS-partiallyRequired)/3));
            for (Course deptCourse: deptPartiallyRequired){
                System.out.print(deptCourse.getName()+" ");
            }
        }
    }
    @Override
    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }
    @Override
    public void PERequirement(){
        super.PERequirement();
    }
    @Override
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