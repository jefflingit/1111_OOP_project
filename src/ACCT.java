import java.io.IOException;
import java.util.ArrayList;

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

    public void addRequire(String fileName)throws IOException{
        super.addRequire(fileName,this.deptRequired);
    }

    //傳入系所規定的群修課程
    public void addPartiallyRequired() throws IOException {
        super.addRequire("deptRequired_CSV/ACCT_partiallyRequiredA.csv",this.deptPartiallyRequired1);
        super.addRequire("deptRequired_CSV/ACCT_partiallyRequiredB.csv",this.deptPartiallyRequired2);
    }

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

    public void partiallyRequiredJudgement(ArrayList<Course> courses){
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
            System.out.println("群修學分門檻已通過！");
        }
        if (partiallyRequiredCredits1 < 3){
            requirements.set(4, false);
            System.out.printf(String.format("群修A尚缺 %.2f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequiredCredits1));

        }
        if (partiallyRequiredCredits2 < 3){
            requirements.set(5, false);
            System.out.printf(String.format("群修B尚缺 %.2f 學分\n",PARTIALLYREQUIREDCREDITS-partiallyRequiredCredits2));
        }
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
    @Override
    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }
    @Override
    public void PERequirement(){
        super.PERequirement();
    }
}