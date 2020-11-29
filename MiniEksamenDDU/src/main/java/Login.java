import processing.core.PApplet;

public class Login {
    PApplet p;
    String loginText = "";
    float xPos;
    float yPos;
    int id;

    Login(PApplet p, float a, float b, int c) {
        this.p = p;
        xPos = a;
        yPos = b;
        id = c;
    }

    void addUser(char c) {
        if (p.keyPressed && p.key == p.BACKSPACE && loginText.length() != 0) {
            loginText = loginText.substring(0, loginText.length() - 1);
        } else if (p.key == p.BACKSPACE || p.keyCode == 16 || p.key == p.TAB || p.key == p.ENTER || p.keyCode == 20 || p.keyCode == 18) {
        } else {
            loginText = loginText + c;
        }
    }

    void display() {
        p.fill(0, 255, 255);
        p.rect(xPos-10, yPos, p.width / 8, p.height / 16);
        p.textSize(15);
        p.fill(0);
        p.text(loginText, xPos, yPos + 30);
    }
}



