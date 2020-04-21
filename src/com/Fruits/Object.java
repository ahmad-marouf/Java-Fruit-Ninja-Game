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
   protected int Vi;
   protected int Vf;
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
      return Vi;
   }

   @Override
   public int getFallingVelocity() {
      return Vf;
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
   double TimeOfMaxHeight =Height/Vi;
   if(time<TimeOfMaxHeight)
      yLocation+=Vi*time;
   else if(time>TimeOfMaxHeight)
      yLocation+=Vf*time;
   }

   @Override
   public BufferedImage[] getBufferedImages() {
      return new BufferedImage[0];0
   }

}
