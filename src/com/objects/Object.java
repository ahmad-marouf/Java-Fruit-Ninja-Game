package com.objects;

import com.controllers.ENUM;
import com.controllers.EnumAdapter;
import com.controllers.GameObject;
import com.gui.Game;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@XmlRootElement(name = "object")
@XmlAccessorType(XmlAccessType.NONE)
public class Object implements GameObject {

   private ENUM objectType;
   @XmlElement(name = "xLocation")
   private int xLocation;
   @XmlElement(name = "yLocation")
   private int yLocation;
   @XmlElement(name = "maxHeight")
   private int maxHeight;
   @XmlElement(name = "initialVelocity")
   private int initialVelocity;
   @XmlElement(name = "fallingVelocity")
   private int fallingVelocity;
   @XmlElement(name = "sliced")
   private boolean sliced;
   @XmlElement(name = "movedOffScreen")
   private boolean movedOffScreen;
//   @XmlElement(name = "bufferedImages")
   private BufferedImage[] bufferedImages;
   @XmlElement(name = "timeCreated")
   private int timeCreated;

   public Object(){
      this.bufferedImages = new BufferedImage[2];
      try {
         bufferedImages[0] = ImageIO.read(new File("Images\\" + getObjectType() + ".png"));
         bufferedImages[1] = ImageIO.read(new File("Images\\Sliced" + getObjectType() + ".png"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   public Object(ENUM objectType) {
      Game game = Game.getInstance();
      Random random = new Random();

      this.objectType = objectType;
      this.xLocation = random.nextInt((int) game.getGameScene().getWidth() - 50);
      this.yLocation = (int) game.getGameScene().getHeight();
      this.maxHeight = (int) (game.getGameScene().getHeight() - 60);
      this.initialVelocity = game.getDifficulty().getInitialVelocity();
      this.fallingVelocity = game.getDifficulty().getFinalVelocity();
      this.sliced = false;
      this.movedOffScreen = false;
      this.bufferedImages = new BufferedImage[2];
      this.timeCreated = game.getGameState().getTimeFrames();

      try {
         bufferedImages[0] = ImageIO.read(new File("Images\\" + objectType + ".png"));
         bufferedImages[1] = ImageIO.read(new File("Images\\Sliced" + objectType + ".png"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @XmlElement(name = "objectType")
   @XmlJavaTypeAdapter(EnumAdapter.class)
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
   }

   @Override
   public void move(double time) {
      double timeOfMaxHeight = maxHeight/initialVelocity;
      double deltaTime = time - timeCreated;
      if(deltaTime <= timeOfMaxHeight)
         yLocation = (maxHeight + 60) - (int) (initialVelocity * deltaTime);
      else if(deltaTime > timeOfMaxHeight)
         yLocation = (60 - maxHeight) - (int) (fallingVelocity * deltaTime) ;
      if (yLocation > (maxHeight+60))
         movedOffScreen = true;
   }

   @Override
   public BufferedImage[] getBufferedImages() { return bufferedImages; }

}
