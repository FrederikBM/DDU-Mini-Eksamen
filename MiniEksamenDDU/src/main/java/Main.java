import processing.core.PApplet;
import processing.core.PVector;
import processing.data.StringList;

import java.sql.*;

public class Main extends PApplet {
    Login inputUser = new Login(this, 560, 270, 1);
    Login inputPassword = new Login(this, 560, 330, 2);

    AlmindeligKnap btnQuiz = new AlmindeligKnap(this, 530, 250, 110, 65, "Quiz");
    AlmindeligKnap btnShowGrades = new AlmindeligKnap(this, 530, 350, 110, 65, "Karakterer");
    AlmindeligKnap btnBack = new AlmindeligKnap(this, 530, 450, 170, 75,"Tilbage");
    AlmindeligKnap btnReturnLogin = new AlmindeligKnap(this, 50,50,170,110,"Tilbage");
    PVector farveElevKnap = new PVector(0, 255, 255);

    Test test1 = new Test(this, "Hvad er 1/10 i %?", "1%", "5%", "10%", "100%", 3);
    Test test2 = new Test(this, "4/10+5/10 = ?/?", "9/20", "5/20", "8/10", "9/10", 4);
    Test test3 = new Test(this, "Hvad er 2/7 i decimaltal?", "0,2", "0,34", "0,299", "0,286", 4);
    Test test4 = new Test(this, "Hvad er 1‰ i brøk?", "1/100", "1/1000", "2/100", "10/1000", 2);
    int box = 1;
    double pageID = 0;
    int currentGrade = 0;
    String grade = "";
    String student = "";
    StringList students;
    StringList studentsGrades;

    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    private Connection connection = null;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public Main() {
        try {
            connection = DriverManager.getConnection(databaseURL);
            println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public void settings() {
        size(1280, 720);
    }

    @Override
    public void setup() {
        students = new StringList();
        studentsGrades = new StringList();
        singleConnectLogin();
        background(255);
    }

    @Override
    public void draw() {

        if (pageID == 0) {
            connectDatabaseLogin();
            textSize(20);
            text("Login",560, 250);
            inputUser.display();
            inputPassword.display();
            clearPage(0);
        } else if (pageID == 1) {
            tegnReturnToLogin();
            quiz();
            grade();
            clearPage(1);
        } else if (pageID == 2) {
            test1.display();
            test1.checkAns(mouseX, mouseY);
        } else if (pageID == 2.1) {
            test2.display();
            test2.checkAns(mouseX, mouseY);
        } else if (pageID == 2.2) {
            test3.display();
            test3.checkAns(mouseX, mouseY);
        } else if (pageID == 2.3) {
            test4.display();
            test4.checkAns(mouseX, mouseY);
        } else if (pageID == 2.4){
            testResult();
        } else if(pageID==3){
            gradePage();
        } else if(pageID==4){
            tegnReturnToLogin();
            allStudentGrades();
        }

    }

    @Override
    public void mouseReleased() {
        quizPressed();
        gradePressed();
        nextQuestion1();
        nextQuestion2();
        nextQuestion3();
        nextQuestion4();
        back();
        returnToLogin();
    }

    @Override
    public void keyPressed() {
        if (key == TAB && box == 1) {
            box++;
        } else if (key == TAB && box == 2) {
            box--;
        }
        if (box == 1) {
            inputUser.addUser(key);
            inputUser.display();
        }
        if (box == 2) {
            inputPassword.addUser(key);
            inputPassword.display();
        }
    }

    void clearPage(double currentPageID) {
        if (pageID != currentPageID) {
            clear();
            background(255);
        }
    }

    void calculateGrade(){
        if(currentGrade==0){
            grade="-3";
        } else if(currentGrade==1){
            grade="02";
        } else if(currentGrade==2){
            grade="7";
        } else if(currentGrade==3){
            grade="10";
        } else if(currentGrade==4){
            grade="12";
        }
    }

    public  void update() {
        try {
//First SQL UPDATE Query to update record.
            String query1 =  "Update Logins Set Karakter='"+grade+"' Where Bruger = '"+student+"'";

            Connection con = DriverManager.getConnection(databaseURL);
            Statement s = con.createStatement();

//Executing first SQL UPDATE query using executeUpdate()  method of Statement object.
            int count = s.executeUpdate(query1);
            System.out.println("Number of rows updated by executing query1 =  " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//main() method ends

    void singleConnectLogin() {
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet login = s.executeQuery("SELECT [Bruger], [Kode], [Laerer], [ID], [Karakter] FROM [Logins]");

            while (login.next()) {
                String rsUsername = login.getString(1);
                Boolean rsTeacher = login.getBoolean(3);

                if (rsTeacher == false) {
                    students.append(rsUsername);
                    studentsGrades.append(login.getString(5));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void connectDatabaseLogin() {
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet login = s.executeQuery("SELECT [Bruger], [Kode], [Laerer], [ID], [Karakter] FROM [Logins]");
            ResultSet studentList = s.executeQuery("SELECT [Bruger], [Karakter] FROM Logins WHERE Laerer=false");

            while (login.next()) {
                String rsUsername = login.getString(1);
                String rsPassword = login.getString(2);
                Boolean rsTeacher = login.getBoolean(3);

                if (inputUser.loginText.equals(rsUsername) && inputPassword.loginText.equals(rsPassword) && rsTeacher == true && keyPressed && key == ENTER) {
                    println("fuck Jonas");
                    pageID=4;

                }  if (inputUser.loginText.equals(rsUsername) && inputPassword.loginText.equals(rsPassword) && rsTeacher == false && keyPressed && key == ENTER) {
                    grade = login.getString(5);
                    student = login.getString(1);
                    pageID = 1;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void quiz() {
        btnQuiz.tegnKnap(farveElevKnap);
        btnQuiz.registrerKlik(mouseX, mouseY);
    }

    void quizPressed(){
        if (btnQuiz.klikket == true && pageID==1) {
            pageID = 2;
            currentGrade=0;
            println(pageID);
            btnQuiz.registrerRelease();
            clearPage(1);
        }
    }

    void grade() {
        btnShowGrades.tegnKnap(farveElevKnap);
        btnShowGrades.registrerKlik(mouseX, mouseY);
    }

    void gradePressed(){
        if (btnShowGrades.klikket == true && pageID==1) {
            pageID = 3;
            println(pageID);
            btnShowGrades.registrerRelease();
            clearPage(1);
        }
    }

    void gradePage(){
        background(0,255,255);
        textSize(50);
        text("karakterer",560,100);
        textSize(20);
        text("Resultat af senste quiz: " + currentGrade + "/4",300,225);
        text("Din karakter er: " + grade, 300, 350);
        btnBack.tegnKnap(farveElevKnap);
        btnBack.registrerKlik(mouseX,mouseY);
    }

    void nextQuestion1() {
        if (test1.answered && test1.correctAns) {
            pageID = 2.1;
            currentGrade++;
            test1.registrerRelease();
            clearPage(2);
        } else if (test1.answered) {
            pageID = 2.1;
            test1.registrerRelease();
            clearPage(2);
        }
    }
    void nextQuestion2() {

        if (test2.answered && test2.correctAns) {
            pageID = 2.2;
            currentGrade++;
            test2.registrerRelease();
            clearPage(2.1);
        } else if (test2.answered) {
            pageID = 2.2;
            test2.registrerRelease();
            clearPage(2.1);
        }
    }
    void nextQuestion3() {
        if (test3.answered && test3.correctAns) {
            pageID = 2.3;
            currentGrade++;
            test3.registrerRelease();
            clearPage(2.2);
        } else if (test3.answered) {
            pageID = 2.3;
            test3.registrerRelease();
            clearPage(2.2);
        }
    }
    void nextQuestion4(){
        if (test4.answered && test4.correctAns) {
            pageID = 2.4;
            currentGrade++;
            test4.registrerRelease();
            clearPage(2.3);
            calculateGrade();
            update();
        } else if (test4.answered) {
            pageID = 2.4;
            test4.registrerRelease();
            clearPage(2.3);
            calculateGrade();
            update();
        }
    }

    void testResult(){
        textSize(50);
        text("Du fik " + currentGrade + "/4 rigtige", 430, 270);
        textSize(25);
        btnBack.tegnKnap(farveElevKnap);
        btnBack.registrerKlik(mouseX,mouseY);
    }

    void back(){
        if(btnBack.klikket==true&&pageID==2.4||btnBack.klikket==true&&pageID==3){
            pageID=1;
            btnBack.registrerRelease();
            clearPage(2.4);
            clearPage(3);
        }
    }

    void allStudentGrades(){
        for(int i = 0; i < students.size();i++){
            String s = students.get(i);
            String g = studentsGrades.get(i);
            textSize(20);
            text(s,300, 100+(i*50));
            text(g,700, 100+(i*50));
        }
    }

    void tegnReturnToLogin(){
        btnReturnLogin.tegnKnap(farveElevKnap);
        btnReturnLogin.registrerKlik(mouseX,mouseY);
    }

    void returnToLogin(){
        if(btnReturnLogin.klikket==true&&pageID==1||btnReturnLogin.klikket==true&&pageID==4){
            pageID=0;
            btnReturnLogin.registrerRelease();
            clearPage(1);
            clearPage(4);
        }
    }
}




