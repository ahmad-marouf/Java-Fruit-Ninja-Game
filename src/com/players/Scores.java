package com.players;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "scores")
@XmlAccessorType(XmlAccessType.FIELD)
public class Scores {

    @XmlElement(name = "score")
    private List<Score> scoresList;

   public int getHighScore() {
       if (!scoresList.isEmpty())
           return scoresList.get(0).getScore();
       else
           return 0;
   }


    public List<Score> getScoresList() {
       if (scoresList != null)
           Collections.sort(scoresList);
       return scoresList;
    }



    public void load() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Scores.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Scores scores = (Scores) unmarshaller.unmarshal(new File ("scores.xml"));
        if (scores.getScoresList() == null)
            this.scoresList = new ArrayList<>();
        else
            scoresList = scores.getScoresList();
    }
    public void save (Scores scores) throws JAXBException {
        JAXBContext jaxbContext= JAXBContext.newInstance(Scores.class);
        Marshaller marshaller=jaxbContext.createMarshaller();
        marshaller.marshal(scores,new File("scores.xml"));
    }

    public void add (Score score) throws JAXBException {
        if (scoresList.size()<10)
            scoresList.add(score);
        else if (score.getScore() > scoresList.get(scoresList.size()-1).getScore()) {
            scoresList.remove(scoresList.size()-1);
            scoresList.add(score);
        }

        Collections.sort(scoresList);
        save(this);
    }



}
