
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ACCT extends Department {
    final double REQUIREDCREDITS=65.0;
    final double PARTIALLYREQUIREDCREDITS=6.0;
    final double PARTIALLYREQUIREDCREDITSA=3.0;
    final double PARTIALLYREQUIREDCREDITSB=3.0;
    final double TOTALCREDITS=142.0;
    final double SELECTIVENEED=TOTALCREDITS-PARTIALLYREQUIREDCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;

    //儲存系上規定之群修
    private ArrayList<Course> deptPartiallyRequired1=new ArrayList<Course>();
    private ArrayList<Course> deptPartiallyRequired2=new ArrayList<Course>();
    private ArrayList<Course> partiallyRequired1=new ArrayList<Course>();
    private ArrayList<Course> partiallyRequired2=new ArrayList<Course>();

    double required=0;
    //儲存群修修習的總學分數
    double partiallyRequiredCredits1=0;
    double partiallyRequiredCredits2=0;
    double selective=0;

    public void requiredJudgement(ArrayList<Course> courses){
        System.out.println("\n<必修課程>");

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
            System.out.println("required credits pass");
        }else{
            requirements.set(2, false);
            System.out.println("Following course are needed: ");
            for (Course deptCourse: deptRequired){
                System.out.print(deptCourse.getName()+" ");
            }
            System.out.printf("total required credits:%.2f\n",required);
        }
    }

    public void selectiveJudgement(ArrayList<Course> courses){
        System.out.println("<選修課程>");
        for(Course course:courses){
            selective+=course.getCredits();
        }
        if(selective>=SELECTIVENEED){
            requirements.set(3,true);
            System.out.println("selective credits pass");
        }else{
            requirements.set(3,false);
            System.out.printf("total selective credits:%.2f\n",selective);
        }
    }

    //傳入系所規定的群修課程
    public void addPartiallyRequired() {

        try {
            String[] courseInfo;
            double credit;

            //群A
            File file = new File("../必群修csv/會計系_群修A.csv");
            Scanner readFile = new Scanner(file);

            while (readFile.hasNext()) {
                courseInfo = readFile.next().split(",");
                credit = Double.parseDouble(courseInfo[1]);
                Course course = new Course(courseInfo[0],credit);
                deptPartiallyRequired1.add(course);
            }
            readFile.close();

            //群B
            file = new File("../必群修csv/會計系_群修B.csv");
            readFile = new Scanner(file);

            while (readFile.hasNext()) {
                courseInfo = readFile.next().split(",");
                credit = Double.parseDouble(courseInfo[1]);
                Course course = new Course(courseInfo[0],credit);
                deptPartiallyRequired2.add(course);
            }
            readFile.close();

        }catch(FileNotFoundException e){
            System.out.print("File Not Found");
        }
    }

    public void partiallyRequiredJudgement(ArrayList<Course> courses){
        System.out.println("<群修課程>");
        //將使用者修過的群修分別存入進群A/群B
        for (Course course: courses){
            if (course.getSubcategory().equals("A")){
                partiallyRequired1.add(course);
            } else if (course.getCategory().equals("B")){
                partiallyRequired2.add(course);
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

        //判斷使用者群修的學分修習狀況
        if (partiallyRequiredCredits1 >=3 && partiallyRequiredCredits2 >=3){
            System.out.println("partiallyRequired credits pass");
        }
        if (partiallyRequiredCredits1 < 3){
            requirements.set(4, false);
            System.out.printf(String.format("群修A還缺 %.1f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequiredCredits1));

        }
        if (partiallyRequiredCredits2 < 3){
            requirements.set(5, false);
            System.out.printf(String.format("群修B還缺 %.1f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequiredCredits2));
        }
    }

    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }

    public void PERequirement(){
        super.PERequirement();
    }

    public void summarize(){

        requiredJudgement(requireds);
        selectiveJudgement(selectives);
        partiallyRequiredJudgement(partiallyRequireds);
        PERequirement();
        generalRequirement(generalCourses);

        int counter=0;
        super.summarize();
        if(counter==6){
            System.out.println("meet all requirements needed for graduation");
        }else{
            System.out.println("-".repeat(60));
            for(boolean passed :requirements){
                if(!passed){
                    System.out.println("距離畢業門檻尚缺以下學分：");
                    switch(requirements.indexOf(false)){
                        case 0:
                            System.out.printf("%.2f general course credits are needed\n",GENERALCREDITSNEEDED-generalCredits);
                        case 1:
                            System.out.printf("%.2f PE courses are needed\n",PECREDITSNEEDED-PE.size());
                        case 2:
                            System.out.printf("%.2f required credits are needed\n",REQUIREDCREDITS-required);
                        case 3:
                            System.out.printf("%.2f selective credits are needed\n",SELECTIVENEED-selective);
                        case 4:
                            System.out.printf("%.2f partially required credits-A are needed\n",PARTIALLYREQUIREDCREDITSA-partiallyRequiredCredits1);
                        case 5:
                            System.out.printf("%.2f partially required credits-B are needed\n",PARTIALLYREQUIREDCREDITSB-partiallyRequiredCredits2);
                    }
                    break;
                }
            }
        }
    }
}