package com.Fruits;

import com.GameControllers.ENUM;
import com.GameControllers.GameObject;

import java.awt.image.BufferedImage;

public abstract class Object implements GameObject {
   protected int points;
   protected ENUM type;
   protected int xLocation;
   protected int yLocation;
   protected int Height;
   protected int V1;
   protected int V2;
   protected boolean Sliced;
   protected boolean MovedOffScreen;
   protected BufferedImage[] bufferedImages;
   public void setType(ENUM type){this.type=type;}
   public void setSliced(boolean sliced){this.Sliced=sliced;}
   @Override
   public ENUM getObjectType() {
      return type;
   }

   @Override
   public int getXlocation() {
      return xLocation;
   }

   @Override
   public int getYlocation() {
      return yLocation;
   }

   @Override
   public int getMaxHeight() {
      return Height;
   }

   @Override
   public int getInitialVelocity() {
      return V1;
   }

   @Override
   public int getFallingVelocity() {
      return V2;
   }

   @Override
   public Boolean isSliced() {
      return Sliced;
   }

   @Override
   public Boolean hasMovedOffScreen() {
      return MovedOffScreen;
   }

   @Override
   public void slice() {

   }

   @Override
   public void move(double time) {

   }

   @Override
   public BufferedImage[] getBufferedImages() {
      return new BufferedImage[0];
   }
}
