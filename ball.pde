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



  void update() {
    if (gameStart == false) {
      ballx = pmouseX;
      bally = height-60;
    }
  }

  void moveBall() {
    ballpx = ballx;
    ballpy = bally;
    ballx = ballx+ballSpeedX;
    bally = bally+ballSpeedY;
  }

  void show() {
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


  void edgeDetect() {
    if (ballx > width || ballx < 0) {
      ballSpeedX = ballSpeedX * -1;
    }

    if (bally < 0) {
      ballSpeedY = ballSpeedY * -1;
    }

    if (bally >= height) {
      resetBall();
      background(#FF0000);
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


  void reflectX() {
    b.ballSpeedX = ballSpeedX * -1;
  }

  void reflectY() {
    b.ballSpeedY = ballSpeedY * -1;
  }

  void resetBall() {
    gameStart  = false;
    ballSpeedY = 10;
    ballSpeedX = 0;
  }
}
