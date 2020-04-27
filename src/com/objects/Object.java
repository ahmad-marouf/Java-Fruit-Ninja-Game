package com.objects;

import com.controllers.ENUM;
import com.controllers.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Object implements GameObject {

   private int points;
   private ENUM objectType;
   private int xLocation;
   private int yLocation;
   private int maxHeight;
   private int initialVelocity;
   private int fallingVelocity;
   private boolean sliced;
   private boolean movedOffScreen;
   private BufferedImage[] bufferedImages;

   
   public Object(ENUM objectType, int points) {
      this.points = points;
      this.objectType = objectType;
      this.xLocation = 0;
      this.yLocation = 0;
      this.maxHeight = 30;
      this.initialVelocity = -5;
      this.fallingVelocity = 5;
      this.sliced = false;
      this.movedOffScreen = false;
      this.bufferedImages = new BufferedImage[2];

      try {
         bufferedImages[0] = ImageIO.read(new File("Images\\" + objectType + ".png"));
         bufferedImages[1] = ImageIO.read(new File("Images\\Sliced" + objectType + ".png"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Override
   public ENUM getObjectType() { return objectType; }

   @Override
   public int getXlocation() { return xLocation; }

   @Override
   public int getYlocation() { return yLocation; }

   @Override
   public int getMaxHeight() { return maxHeight; }

   @Override
   public int getInitialVelocity() { return initialVelocity; }

   @Override
   public int getFallingVelocity() { return fallingVelocity; }

   @Override
   public Boolean isSliced() { return sliced; }

   @Override
   public Boolean hasMovedOffScreen() { return movedOffScreen; }

   @Override
   public void slice() {
      sliced = true;

      // get score from gui
      int score = 0;
      score+= points;

      // override in bombs to subtract life or end game

   }

   @Override
   public void move(double time) {
      double timeOfMaxHeight = maxHeight / initialVelocity;
      if(time < timeOfMaxHeight)
         yLocation+= initialVelocity * time;
      else if(time > timeOfMaxHeight)
         yLocation+= fallingVelocity * time;
   }

   @Override
   public BufferedImage[] getBufferedImages() { return bufferedImages; }

}
