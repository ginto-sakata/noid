class Stick {
  float stickX = 0;
  float stickY = 0;
  float stickLen = 100;
  float hue;



  //Stick (int stickcolor) {
  //  hue = stickcolor;
  //}



  void update() {
    stickX = mouseX-50;
    stickY = height-55;
  }

  void show() {
 hue = map ((mouseX), 0, width, 0, 255);
    fill(hue, 200, 80);
    rect (stickX, stickY, stickLen, 10, 10);
  }
}
