package com.objects;

import com.controllers.ENUM;
import com.controllers.GameObject;
import com.gui.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
   private int timeCreated;

   
   public Object(ENUM objectType, int points) {
      Game game = Game.getInstance();
      Random random = new Random();

      this.points = points;
      this.objectType = objectType;
      this.xLocation = random.nextInt((int) game.getGameScene().getWidth() - 50);
      this.yLocation = (int) game.getGameScene().getHeight();
      this.maxHeight = (int) (game.getGameScene().getHeight() - 60);
      this.initialVelocity = game.getDifficulty().getInitialVelocity();
      this.fallingVelocity = game.getDifficulty().getFinalVelocity();
      this.sliced = false;
      this.movedOffScreen = false;
      this.bufferedImages = new BufferedImage[2];
      this.timeCreated = game.getTimeFrames();

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
      Game game = Game.getInstance();
      game.setScore(game.getScore() + points);
      // override in bombs to subtract life or end game

   }

   @Override
   public void move(double time) {
      double timeOfMaxHeight = maxHeight/initialVelocity;
      double deltaTime = time - timeCreated;
      if(deltaTime <= timeOfMaxHeight)
         yLocation = (maxHeight + 60) - (int) (initialVelocity * deltaTime);
      else if(deltaTime > timeOfMaxHeight)
         yLocation = (60 - maxHeight) - (int) (fallingVelocity * deltaTime) ;
   }

   @Override
   public BufferedImage[] getBufferedImages() { return bufferedImages; }

}
