import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static final String UTF8_BOM = "\uFEFF";
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String filename = "user_IB.csv";
        encodingCSV(filename);
    }
    public static void encodingCSV(String file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF8"));
        String line;
        boolean firstLine = true;
        String[] userInfo;
        String name = "";
        String categroy = "";
        String subcategory = "";
        double credits;
        String[] deptScanInput= br.readLine().split(" ");
        String[] deptScanInput2 = deptScanInput[1].split(",");
        String deptInput = deptScanInput2[0];
        Department myDept = deptSelector(deptInput);

        while((line=br.readLine())!=null)
        {
            if(firstLine) line = removeUTF8BOM(line);
            userInfo = line.split(",");
            name = userInfo[0];
            credits = Double.parseDouble(userInfo[1]);
            categroy = userInfo[2];
            subcategory = userInfo[3];
            if (subcategory.equals("none")) {
                Course newCourse = new Course(name, credits, categroy);
                myDept.addCourse(newCourse);
            }else{
                Course newCourse = new Course(name, credits, categroy, subcategory);
                myDept.addCourse(newCourse);
            }

        }
        myDept.summarize();
        br.close();
    }
    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1); // 如果 String 是以 BOM 開頭, 則省略字串最前面的第一個 字元.
        }
        return s;
    }


    public static Department deptSelector(String dept) throws IOException {
        switch (dept) {
            case "BA":
                System.out.println("系所 BA");
                Department ba = new BA();
                BA ba2 = (BA) ba;
                ba2.addRequire("deptRequired_CSV/BA_required.csv");
                return ba2;
            case "IB":
                System.out.println("系所 IB");
                Department ib = new IB();
                IB ib2 = (IB) ib;
                //呼叫Department裡面的method，把系上要求的required課程以csv的方式匯入
                ib2.addRequire("deptRequired_CSV/IB_required.csv");
                //呼叫ACCT裡面的method，把系上要求的partiallyRequired課程匯入
                ib2.addPartiallyRequired();
                return ib2;
            case "ACCT":
                System.out.println("系所 ACCT");
                Department acct = new ACCT();
                ACCT acct2 = (ACCT) acct;
                //呼叫ACCT裡面的method，把系上要求的partiallyRequired課程匯入
                acct2.addRequire("deptRequired_CSV/ACCT_required.csv");
                acct2.addPartiallyRequired();

                return acct2;
            case "FIN":
                System.out.println("系所 FIN");
                Department fin = new FIN();
                FIN fin2 = (FIN) fin;
                fin2.addRequire("deptRequired_CSV/FIN_required.csv");
                fin2.addPartiallyRequired();
                return fin;
            case "STAT":
                System.out.println("系所 STAT");
                Department stat = new STAT();
                STAT stat2 = (STAT) stat;
                stat2.addRequire("deptRequired_CSV/STAT_required.csv");
                stat2.addPartiallyRequired();
                return stat2;
            case "MIS":
                System.out.println("系所 MIS");
                Department mis = new MIS();
                MIS mis2 = (MIS) mis;
                mis2.addRequire("deptRequired_CSV/MIS_required.csv");
                mis2.addPartiallyRequired();
                return mis2;
            case "MAB":
                System.out.println("系所 MAB");
                Department mab = new MAB();
                MAB mab2 = (MAB) mab;
                mab2.addRequire("deptRequired_CSV/MAB_required.csv");
                mab2.addPartiallyRequired();
                return mab2;
            case "RMI":
                System.out.println("系所 RMI");
                Department rmi = new RMI();
                RMI rmi2 = (RMI) rmi;
                rmi2.addRequire("deptRequired_CSV/RMI_required.csv");
                rmi2.addPartiallyRequired();
                return rmi2;
            default:
                Department myDept = new Department();
                return myDept;
        }
    }
}
