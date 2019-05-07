package com.example.teleg.programm.serverSide.News;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel", strict = false)
public class Channel implements Serializable {
    @ElementList(inline = true, name = "item")
    private List<ItemNews> newsList;

    public List<ItemNews> getNewsList() {
        return newsList;
    }

    public Channel() {

    }

    public Channel(List<ItemNews> newsList){
        this.newsList = newsList;
    }
}
