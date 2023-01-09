import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        // TODO Auto-generated method stub

        String filename = "使用者已修課程_IB2.csv";

        try (Scanner input = new Scanner(new File(filename))){


            String[] deptScanInput = input.nextLine().split(" ");
            String[] deptScanInput2 = deptScanInput[1].split(",");
            String deptInput = deptScanInput2[0];
            Department myDept = deptSelector(deptInput);

            while (input.hasNextLine()) {
                try{
                    String[] userInfo = input.next().split(",");
                    String name = userInfo[0];
                    double credits = Double.parseDouble(userInfo[1]);
                    String category = userInfo[2];
                    String subcategory = userInfo[3];
                    if (subcategory.equals("none")) {
                        Course newCourse = new Course(name, credits, category);
                        myDept.addCourse(newCourse);
                    } else {
                        Course newCourse = new Course(name, credits, category, subcategory);
                        myDept.addCourse(newCourse);
                    }


                }catch (NoSuchElementException noe){
                    System.out.println("error");
                }

            }

            myDept.summarize();
            input.close();
        }catch(IOException ex){
            System.out.println("I/O error");
    }
}

    public static Department deptSelector(String dept){
        switch(dept){
            case "BA":
                Department ba=new BA();
                ba.addRequire("../必群修csv/企管系_必修.csv");
                //ba無群修學分
                return ba;
            case "IB":
                Department ib=new IB();
                //呼叫Department裡面的method，把系上要求的必修課程以csv的方式匯入
                ib.addRequire("../必群修csv/國貿系_必修.csv");
                IB ib2 = (IB)ib;
                //呼叫ACCT裡面的method，把系上要求的群修課程匯入
                ib2.addPartiallyRequired();
                return ib;
            case "ACCT":
                Department acct=new ACCT();
                acct.addRequire("../必群修csv/會計系_必修.csv");
                ACCT acct2 = (ACCT)acct;
                //呼叫ACCT裡面的method，把系上要求的群修課程匯入
                acct2.addPartiallyRequired();
                return acct;
            case "FIN":
                Department fin=new FIN();
                fin.addRequire("../必群修csv/財管系_必修.csv");
                FIN fin2 = (FIN)fin;
                fin2.addPartiallyRequired();
                return fin;
            case "STAT":
                Department stat=new STAT();
                stat.addRequire("../必群修csv/統計系_必修.csv");
                STAT stat2 = (STAT)stat;
                stat2.addPartiallyRequired();
                return stat;
            case "MIS":
                Department mis=new MIS();
                mis.addRequire("../必群修csv/資管系_必修.csv");
                MIS mis2 = (MIS)mis;
                mis2.addPartiallyRequired();
                return mis;
            case "MAB":
                Department mab=new MAB();
                mab.addRequire("../必群修csv/金融系_必修.csv");
                MAB mab2 = (MAB)mab;
                mab2.addPartiallyRequired();
                return mab;
            case "RMI":
                Department rmi=new RMI();
                rmi.addRequire("../必群修csv/風管系_必修.csv");
                RMI rmi2 = (RMI)rmi;
                rmi2.addPartiallyRequired();
                return rmi;
            default:
                Department myDept=new Department();
                return myDept;
        }
    }
}
