package com.controllers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "scores")
@XmlAccessorType(XmlAccessType.FIELD)
public class Scores {
    private int[] scoresList=new int[10];
    private static Scores instance;

    public static Scores getInstance() {
        if (instance==null)
            instance = new Scores();
        return instance;
    }

   public int getHighScore() { return scoresList[0]; }


    public int[] getScoresList() {
        Arrays.sort(scoresList);
        return scoresList;
    }



    public void load() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Scores.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Scores scores = (Scores) unmarshaller.unmarshal(new File ("scores.xml"));
    }
    public void save () throws JAXBException {
        JAXBContext jaxbContext= JAXBContext.newInstance(Scores.class);
        Marshaller marshaller=jaxbContext.createMarshaller();
        marshaller.marshal(scoresList,new File("scores.xml"));
    }
    public void add(int score) throws JAXBException {
        if(score>scoresList[9]) {
            scoresList[9]=score;
            Arrays.sort(scoresList);
        }
        List<Integer> scoresList=Arrays.asList();
        if(scoresList.size()<10)
        {
            scoresList.add(score);
        }

    }



}
