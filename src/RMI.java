import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class RMI extends Department{
    final double REQUIREDCREDITS=65.0;
    final double PARTIALLYREQUIREDCREDITS=3.0;
    final double TOTALCREDITS=128.0;
    final double SELECTIVENEED=TOTALCREDITS-PARTIALLYREQUIREDCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;

    //儲存系上規定之群修
    private ArrayList<Course> deptPartiallyRequired=new ArrayList<Course>();
    double required=0;
    double partiallyRequired=0;
    double selective=0;

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
            System.out.println("required credits pass");
        }else{
            requirements.set(2, false);
            System.out.printf("total required credits:%.2f\n",required);
            System.out.println("Following course are needed: ");
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
            File file = new File("../必群修csv/風管系_群修.csv");
            Scanner readFile = new Scanner(file);

            //讀csv檔，並把科系要求的課程加進群修的arrayList
            while (readFile.hasNext()) {
                courseInfo = readFile.next().split(",");
                credit = Double.parseDouble(courseInfo[1]);
                Course course = new Course(courseInfo[0],credit);
                deptPartiallyRequired.add(course);
            }
            readFile.close();
            //印出系上規定群修裡面的所有課程
            //for (Course course: deptPartiallyRequired) {
            //System.out.print(course.getName()+" "+course.getCredits());
            //System.out.println();
            //}
        }catch(FileNotFoundException e){
            System.out.print("File Not Found");
        }
    }


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
            System.out.printf(String.format("群修還缺 %.1f 學分",PARTIALLYREQUIREDCREDITS-partiallyRequired));
            System.out.printf(String.format("請挑下面其中%.1f堂課修 \n",(PARTIALLYREQUIREDCREDITS-partiallyRequired)/3));
            for (Course deptCourse: deptPartiallyRequired){
                System.out.print(deptCourse.getName()+" ");
            }
        }
        System.out.printf("\n");
    }

    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }

    public void PERequirement(){
        super.PERequirement();
    }

    public void summarize(){

        PERequirement();
        generalRequirement(generalCourses);
        requiredJudgement(requireds);
        selectiveJudgement(selectives);
        partiallyRequiredJudgement(partiallyRequireds);

        int counter=0;
        super.summarize();
        if(counter==6){
            System.out.println("meet all the requirement needed for graduation");
        }else{
            System.out.println("-".repeat(60));
            for(boolean passed :requirements){
                if(!passed){
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
                            System.out.printf("%.2f partially required credits are needed\n",PARTIALLYREQUIREDCREDITS-partiallyRequired);
                    }
                    break;
                }
            }
        }
    }

}
