
boolean gameStart = false;
Ball b = new Ball(70);
Stick s = new Stick();

int cols = 11;
int rows = 4;
int score =0;
int hp = 100;


Block[][] blocks; 

void setup() {
  background(0);
  colorMode(HSB, 255);
  size (640, 480);
  blocks = new Block[cols][rows];
  for (int i = 0; i < cols; i++) {
    for (int j = 1; j < rows; j++) {
      int z = int(random(255));
      blocks[i][j] = new Block(i * 50+50, j * 30, 40, 20, z);
    }
  }
}


void draw() {
  background (#FFFFFF);
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

void mousePressed() {

  if (hp > 0) {
    if (random (0, 1)<0.5) {
      b.ballSpeedX = -5;
    } else {     
      b.ballSpeedX= 5;
    }
    gameStart = true;
  } 
}
