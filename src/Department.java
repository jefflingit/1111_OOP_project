import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Department {
    public static final String UTF8_BOM = "\uFEFF";
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

    public void addRequire(String fileName,ArrayList<Course> courses) throws IOException{
        FileInputStream fis = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF8"));
        String line;
        boolean firstLine = true;
        String[] courseInfo;
        double credit;
        while((line=br.readLine())!=null)
        {
            if(firstLine) line = removeUTF8BOM(line);
            courseInfo = line.split(",");
            credit = Double.parseDouble(courseInfo[1]);
            Course course = new Course(courseInfo[0], credit);
            courses.add(course);
        }
        br.close();
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

    double humanity=0;
    double society=0;
    double nature=0;
    double chinese= 0;
    double english=0;
    double total=0;
    int coreNum=0;

    public void generalRequirement(ArrayList<Course> generalCourses){
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

        if(generalCredits>28){

            for(boolean mark:core){
                if(!mark){
                    passMark=false;
                    requirements.set(1,passMark);
                    for(boolean num:core){
                        if(!num){
                            coreNum = coreNum++;
                        }
                    }
                    passMark=false;
                    requirements.set(1,passMark);
                }else{
                    passMark=true;
                    requirements.set(1,passMark);
                }
            }
            passMark=false;
            requirements.set(1,passMark);
            System.out.printf("\n已修 %.2f 學分\n",generalCredits);
            System.out.printf("尚缺 %.2f 學分\n",28-generalCredits);

        }else{
            passMark=false;
            requirements.set(1,passMark);
            System.out.printf("\n已修 %.2f 學分\n",generalCredits);
            System.out.printf("尚缺 %.2f 學分\n",28-generalCredits);
        }
        if (!passMark & coreNum>=3){
            System.out.println("\n通識學分門檻已通過！");
        }

        //System.out.printf("%.2f general course credits needed\n",GENERALCREDITSNEEDED-generalCredits);
    }



    public boolean generalLimitRequirement(Course course,double min,double max){
        if(course.getCredits()<min){
            return false;
        }else if(course.getCredits()>max){
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

    public void graduationResult() {
        int counter=0;
        if(counter==6){
            System.out.println("-".repeat(100));
            System.out.println("/n恭喜您已完成所有畢業所需學分 畢業快樂！");
        }else{
            for(boolean passed :requirements){
                System.out.println("-".repeat(100));
                System.out.println("<通識及體育課程細項>");
                if(!passed){
                    switch(requirements.indexOf(false)){
                        case 0:
                            System.out.printf("\n體育已修 "+PE.size()+" 門");
                            if (PE.size() >= 4) {
                                System.out.println("\n已通過體育課修習標準");
                            }else {
                                System.out.printf("\n應再補齊 %.0f 門\n",PECREDITSNEEDED-PE.size());
                            }
                        case 1:
                            System.out.printf("\n國文通識（3~6)\n");
                            System.out.printf("已修 %.2f 學分\n",chinese);
                            if (chinese < 3) {
                                System.out.printf("低於下限 尚須 %.2f 學分\n", 3-chinese);
                            }else if(chinese >= 6) {
                                System.out.printf("已達上限 再繼續修也不算學分喔～\n");
                            }else {
                                System.out.printf("已過下限 還能修 %.2f 學分\n", 6-chinese);
                            }

                        case 2:
                            System.out.printf("\n外文通識（4~6)\n");
                            System.out.printf("已修 %.2f 學分\n", english);
                            if (english < 3) {
                                System.out.printf("低於下限 尚須 %.2f 學分\n", 4-english);
                            }else if(english >= 6) {
                                System.out.printf("已達上限 再繼續修也不算學分喔～\n");
                            }else {
                                System.out.printf("已過下限 還能修 %.2f 學分\n", 6-english);
                            }

                        case 3:
                            System.out.printf("\n社會通識（3~9)\n");
                            System.out.printf("已修 %.2f 學分\n",society);
                            if (society < 3) {
                                System.out.printf("低於下限 尚須 %.2f 學分\n", 3-society);
                            }else if(society >= 9) {
                                System.out.printf("已達上限 再繼續修也不算學分喔～\n");
                            }else {
                                System.out.printf("已過下限 還能修 %.2f 學分\n", 9-society);
                            }
                        case 4:
                            System.out.printf("\n人文通識（3~9)\n");
                            System.out.printf("已修 %.2f 學分\n",humanity);
                            if (humanity < 3) {
                                System.out.printf("低於下限 尚須 %.2f 學分\n", 3-humanity);
                            }else if(humanity >= 9) {
                                System.out.printf("已達上限 再繼續修也不算學分喔～\n");
                            }else {
                                System.out.printf("已過下限 還能修 %.2f 學分\n", 9-humanity);
                            }
                        case 5:
                            System.out.printf("\n自然通識（4~9)\n");
                            System.out.printf("已修 %.2f 學分\n",nature);
                            if (nature < 3) {
                                System.out.printf("低於下限 尚須 %.2f 學分\n", 4-nature);
                            }else if(nature >= 9) {
                                System.out.printf("已達上限 再繼續修也不算學分喔～\n");
                            }else {
                                System.out.printf("已過下限 還能修 %.2f 學分\n", 9-nature);
                            }
                        case 6:
                            if (coreNum == 2) {
                                System.out.println("\n核心通識已通過標準\n");
                            }else {
                                System.out.printf("\n尚缺 "+ (3-coreNum) +" 門核心通識\n");
                            }
                    }
                    break;
                }
            }
        }
    }
    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1); // 如果 String 是以 BOM 開頭, 則省略字串最前面的第一個 字元.
        }
        return s;
    }

}