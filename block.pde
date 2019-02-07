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


  void hit() {
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

  void show() {
    if (exist) {
      fill(hue, 190, 230);
      rect(x, y, w, h);
    }
  }
}
