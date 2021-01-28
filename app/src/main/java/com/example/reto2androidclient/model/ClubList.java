package com.example.reto2androidclient.model;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
@Root(name="clubs")
public class ClubList {
    @ElementList(entry="club", inline=true)
    private List<Club> clubs;

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
