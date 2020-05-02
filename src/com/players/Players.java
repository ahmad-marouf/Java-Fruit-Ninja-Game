package com.players;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class Players {

    @XmlElement(name = "player")
    List<String> playerList;

    public List<String> getPlayerList() {
        return playerList;
    }

    public void load() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Players players = (Players) unmarshaller.unmarshal(new File("players.xml"));
        if (players.getPlayerList() == null)
            this.playerList = new ArrayList<>();
        else
            playerList = players.getPlayerList();
    }
    public void save (Players players) throws JAXBException {
        JAXBContext jaxbContext= JAXBContext.newInstance(Players.class);
        Marshaller marshaller=jaxbContext.createMarshaller();
        marshaller.marshal(players,new File("players.xml"));
    }

    public void add (String player) throws JAXBException {
        playerList.add(player);
        save(this);
    }
}
