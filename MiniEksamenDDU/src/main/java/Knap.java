import processing.core.PApplet;
import processing.core.PVector;

public abstract class Knap {
    //variabler
    float positionX, positionY, sizeX, sizeY;
    float mouseX, mouseY;
    String text;
    boolean klikket = false;


    PApplet p;

    Knap(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text ) {
        p = papp;
        positionX = posX;
        positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = text;

    }

    void klik() {
        if (p.mousePressed &&
                mouseX > positionX &&
                mouseX < positionX + sizeX &&
                mouseY > positionY &&
                mouseY < positionY + sizeY) {
        }
    }

    void setTekst(String tekst) {
        p.fill(41, 61, 82);
        p.textSize(20);
        p.text(tekst, positionX +(sizeX/16), positionY + (sizeY/2));

    }

    void tegnKnap(PVector color) {
        p.stroke(1, 46, 74, 100);
        if(klikket){
            p.fill(color.x,color.y,color.z);
        }else{
            p.fill(color.x-20,color.y-20,color.z-20);
        }

        p.rect(positionX, positionY, sizeX, sizeY);
        setTekst(text);
    }

    boolean erKlikket() {
        return klikket;
    }

    abstract void registrerKlik(float mouseX, float mouseY);
}

