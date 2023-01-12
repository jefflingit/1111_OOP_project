import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MAB extends Department{
    final double REQUIREDCREDITS=47.0;
    final double PARTIALLYREQUIREDCREDITS=12.0;
    final double PARTIALLYREQUIREDCREDITSA=3.0;
    final double PARTIALLYREQUIREDCREDITSB=3.0;
    final double PARTIALLYREQUIREDCREDITSC=3.0;
    final double PARTIALLYREQUIREDCREDITSF=3.0;
    final double TOTALCREDITS=128.0;
    final double SELECTIVENEED=TOTALCREDITS-PARTIALLYREQUIREDCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;

    //儲存系上規定之群修
    private ArrayList<Course> deptPartiallyRequired1=new ArrayList<Course>();
    private ArrayList<Course> deptPartiallyRequired2=new ArrayList<Course>();
    private ArrayList<Course> deptPartiallyRequired3=new ArrayList<Course>();
    private ArrayList<Course> deptPartiallyRequired4=new ArrayList<Course>();
    //儲存使用者修過的群
    private ArrayList<Course> partiallyRequired1=new ArrayList<Course>();
    private ArrayList<Course> partiallyRequired2=new ArrayList<Course>();
    private ArrayList<Course> partiallyRequired3=new ArrayList<Course>();
    private ArrayList<Course> partiallyRequired4=new ArrayList<Course>();


    double required=0;
    //儲存群修修習的總學分數
    double partiallyRequiredCredits1=0;
    double partiallyRequiredCredits2=0;
    double partiallyRequiredCredits3=0;
    double partiallyRequiredCredits4=0;
    double selective=0;

    public void requiredJudgement(ArrayList<Course> courses){
        //儲存重複課程的index
        ArrayList<Integer> storeIndex = new ArrayList<Integer>();
        for(Course userCourse:courses){
            String userCourseName = userCourse.getName();
            for (Course deptCourse: deptRequired){
                String deptCourseName = deptCourse.getName();
                if (deptCourseName.equals(userCourseName)){
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
    public void addRequire(String fileName)throws IOException{
        super.addRequire(fileName,this.deptRequired);
    }
    public void addPartiallyRequired() throws IOException {
        super.addRequire("deptRequired_CSV/MAB_partiallyRequiredA.csv",this.deptPartiallyRequired1);
        super.addRequire("deptRequired_CSV/MAB_partiallyRequiredB.csv",this.deptPartiallyRequired2);
        super.addRequire("deptRequired_CSV/MAB_partiallyRequiredC.csv",this.deptPartiallyRequired3);
        super.addRequire("deptRequired_CSV/MAB_partiallyRequiredF.csv",this.deptPartiallyRequired4);
    }

    public void partiallyRequiredJudgement(ArrayList<Course> courses){

        //將使用者修過的群修分別存入進群ABCF
        for (Course course: courses){
            if (course.getSubcategory().equals("A")){
                partiallyRequired1.add(course);
            } else if (course.getCategory().equals("B")){
                partiallyRequired2.add(course);
            } else if (course.getCategory().equals("C")){
                partiallyRequired3.add(course);
            } else if (course.getCategory().equals("F")){
                partiallyRequired4.add(course);
            }
        }

        //計算群A使用者修習的課程數
        for (Course userCourse: partiallyRequired1){
            for (Course deptCourse: deptPartiallyRequired1){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits1+=deptCourse.getCredits();
            }
        }

        //計算群B使用者修習的學分數
        for (Course userCourse: partiallyRequired2){
            for (Course deptCourse: deptPartiallyRequired2){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits2+=deptCourse.getCredits();
            }
        }

        //計算群C使用者修習的學分數
        for (Course userCourse: partiallyRequired3){
            for (Course deptCourse: deptPartiallyRequired3){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits3+=deptCourse.getCredits();
            }
        }

        //計算群F使用者修習的學分數
        for (Course userCourse: partiallyRequired4){
            for (Course deptCourse: deptPartiallyRequired4){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits4+=deptCourse.getCredits();
            }
        }

        //判斷使用者群修的學分修習狀況
        if (partiallyRequiredCredits1 >=3 && partiallyRequiredCredits3 >=3 && partiallyRequiredCredits3 >=3 && partiallyRequiredCredits4 >=3){
            System.out.println("群修學分門檻已通過！");
        }
        if (partiallyRequiredCredits1 < 3){
            requirements.set(4, true);
            System.out.println("尚未修群A");
        }
        if (partiallyRequiredCredits2 < 3){
            requirements.set(5, true);
            System.out.println("尚未修群B");
        }
        if (partiallyRequiredCredits3 < 3){
            requirements.set(6, true);
            System.out.println("尚未修群C");
        }
        if (partiallyRequiredCredits4 < 3){
            requirements.set(7, true);
            System.out.println("尚未修群F");
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