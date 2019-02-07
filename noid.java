import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class noid extends PApplet {


boolean gameStart = false;
Ball b = new Ball(70);
Stick s = new Stick();

int cols = 11;
int rows = 4;
int score =0;
int hp = 100;


Block[][] blocks; 

public void setup() {
  background(0);
  colorMode(HSB, 255);
  
  blocks = new Block[cols][rows];
  for (int i = 0; i < cols; i++) {
    for (int j = 1; j < rows; j++) {
      int z = PApplet.parseInt(random(255));
      blocks[i][j] = new Block(i * 50+50, j * 30, 40, 20, z);
    }
  }
}


public void draw() {
  background (0xffFFFFFF);
  textSize(20);
  fill(0, 102, 153);
  text("Score:"+score, 10, 20); 
  fill(120, 100, 100);
  text("HP:"+ hp, 550, 20); 

  if (score >= 330) {
    textSize(72);
    fill(random(255), 102, 153);
    text("НИХУЯ СЕБЕ ", width/2-200, height/2);
    gameStart=false;
  }

  if (hp <= 0) {
        text ("НУ ТЫ И ДАУН",  width/2-200, height/2);
    text ("для продолжения игры надо задонатить",  width/2-200, height/2+70);
  }



  println(dist(s.stickX+s.stickLen/2, 0, b.ballx, 0));

  s.update();
  s.show();

  for (int i = 0; i < cols; i++) {
    for (int j = 1; j < rows; j++) {
      blocks[i][j].hit();
      blocks[i][j].show();
      fill(100);
    }
  }

  b.update();
  b.edgeDetect();
  b.show();

  if (gameStart == true) {
    b.moveBall();
  }
}

public void mousePressed() {

  if (hp > 0) {
    if (random (0, 1)<0.5f) {
      b.ballSpeedX = -5;
    } else {     
      b.ballSpeedX= 5;
    }
    gameStart = true;
  } 
}
class Ball { //<>//

  float ballpx, ballpy;
  float ballx = 0;
  float bally = 0;
  float ballSpeedX;
  float ballSpeedY = -10; 
  float ballDir = 0;
  float hue;
  int bright;
  int sat;

  Ball (int ballcolor) {
    hue = ballcolor;
  }



  public void update() {
    if (gameStart == false) {
      ballx = pmouseX;
      bally = height-60;
    }
  }

  public void moveBall() {
    ballpx = ballx;
    ballpy = bally;
    ballx = ballx+ballSpeedX;
    bally = bally+ballSpeedY;
  }

  public void show() {
    if (score == 0) {
      bright = 255;
      sat = 0;
    } else {
      bright = 150;
      sat = 255;
    }

    fill(hue, sat, bright);
    ellipse(ballx, bally, 10, 10);
  }


  public void edgeDetect() {
    if (ballx > width || ballx < 0) {
      ballSpeedX = ballSpeedX * -1;
    }

    if (bally < 0) {
      ballSpeedY = ballSpeedY * -1;
    }

    if (bally >= height) {
      resetBall();
      background(0xffFF0000);
      hp = hp-20;
      ballDir = random(-10, 10);
    }

    if (bally >= s.stickY-5 && ballSpeedY > 0 && bally < height-50) {
      if (ballx > s.stickX && ballx < s.stickX+100) {
        reflectY();
        ballSpeedX = map(dist(s.stickX, 0, ballx, 0), 0, 100, -10, 10);

        if (ballSpeedX > 10) {
          ballSpeedX = 10;
        }
      }
    }
  }


  public void reflectX() {
    b.ballSpeedX = ballSpeedX * -1;
  }

  public void reflectY() {
    b.ballSpeedY = ballSpeedY * -1;
  }

  public void resetBall() {
    gameStart  = false;
    ballSpeedY = 10;
    ballSpeedX = 0;
  }
}
class Block {
  float x, y;   // x,y location
  float w, h;   // width and height
  float hue;
  boolean exist=true;


  Block (float tempX, float tempY, float tempW, float tempH, float blockcolor) {
    x = tempX;
    y = tempY;
    w = tempW;
    h = tempH;
    hue = blockcolor;
  }


  public void hit() {
    //check X movment bounce
    if (b.ballx + b.ballSpeedX > x     && 
      b.ballx + b.ballSpeedX < x + w && 
      b.bally > y && 
      b.bally < y + h &&
      exist == true) {
      exist=false;
      b.reflectX();
      score = score +10;
      b.hue = hue;
    }
    if (b.ballx> x && 
      b.ballx< x + w && 
      b.bally + b.ballSpeedY > y && 
      b.bally + b.ballSpeedY < y + h &&
      exist == true) {
      exist=false;
      b.reflectY();
      score = score +10;
    }
  }

  public void show() {
    if (exist) {
      fill(hue, 190, 230);
      rect(x, y, w, h);
    }
  }
}
class Stick {
  float stickX = 0;
  float stickY = 0;
  float stickLen = 100;
  float hue;



  //Stick (int stickcolor) {
  //  hue = stickcolor;
  //}



  public void update() {
    stickX = mouseX-50;
    stickY = height-55;
  }

  public void show() {
 hue = map ((mouseX), 0, width, 0, 255);
    fill(hue, 200, 80);
    rect (stickX, stickY, stickLen, 10, 10);
  }
}
  public void settings() {  size (640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "noid" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
