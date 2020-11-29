import processing.core.PApplet;

public class Test {
    PApplet p;
    String question, ans1, ans2, ans3, ans4;
    int correctAnsID;
    boolean correctAns;
    boolean answered;
    int ans1PosX = 463, ans1PosY = 340;
    int ans2PosX = 783, ans2PosY = 340;
    int ans3PosX = 463, ans3PosY = 520;
    int ans4PosX = 783, ans4PosY= 520;

    Test(PApplet Papp, String ask, String ans1, String ans2, String ans3, String ans4, int correctAnsID){
        p = Papp;
        question = ask;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
        this.correctAnsID=correctAnsID;
    }

    void display(){

        p.textSize(45);
        p.text(question,500, 170);
        p.textSize(25);
        p.text(ans1,ans1PosX,ans1PosY);
        p.text(ans2,ans2PosX,ans2PosY);
        p.text(ans3,ans3PosX,ans3PosY);
        p.text(ans4,ans4PosX,ans4PosY);
    }

    void checkAns(int mouseX, int mouseY){
        if(p.mousePressed&&correctAnsID==1&&
                mouseX > ans1PosX &&
                mouseX < ans1PosX + 100 &&
                mouseY > ans1PosY - 50 &&
                mouseY < ans1PosY ){
            correctAns=true;
            answered=true;
        } else if(p.mousePressed&&correctAnsID==2&&
                mouseX > ans2PosX &&
                mouseX < ans2PosX + 100 &&
                mouseY > ans2PosY - 50 &&
                mouseY < ans2PosY){
            correctAns=true;
            answered=true;
        } else if(p.mousePressed&&correctAnsID==3&&
                mouseX > ans3PosX &&
                mouseX < ans3PosX + 100 &&
                mouseY > ans3PosY - 50&&
                mouseY < ans3PosY ){
            correctAns=true;
            answered=true;
        } else if(p.mousePressed&&correctAnsID==4&&
                mouseX > ans4PosX &&
                mouseX < ans4PosX + 100 &&
                mouseY > ans4PosY - 50&&
                mouseY < ans4PosY ){
            correctAns=true;
            answered=true;
        } else if(
                p.mousePressed&&
                        mouseX > ans1PosX &&
                        mouseX < ans1PosX + 100 &&
                        mouseY > ans1PosY - 50 &&
                        mouseY < ans1PosY ||
                p.mousePressed&&
                        mouseX > ans2PosX &&
                        mouseX < ans2PosX + 100 &&
                        mouseY > ans2PosY - 50 &&
                        mouseY < ans2PosY||
                p.mousePressed&&
                        mouseX > ans3PosX &&
                        mouseX < ans3PosX + 100 &&
                        mouseY > ans3PosY - 50&&
                        mouseY < ans3PosY ||
                p.mousePressed&&
                        mouseX > ans4PosX &&
                        mouseX < ans4PosX + 100 &&
                        mouseY > ans4PosY - 50&&
                        mouseY < ans4PosY ){
            correctAns=false;
            answered=true;
        }
    }
    void registrerRelease(){
        answered=false;
    }
}
