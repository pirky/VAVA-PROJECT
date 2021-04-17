package project.model.users;

import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;

import java.util.ArrayList;
import java.util.List;

public class Organizer extends User{


    public Organizer(String userName, String password) {
        super(userName, password);
    }

    public Object clone(){
        return new Organizer(this.getUserName(), this.getPassword());
    }

    private List<Event> events;
    

    public List<Event> getEvents() {
        List<Event> returnList = new ArrayList<>();
        for(Event event: events){
            returnList.add((Event) event.clone());
        }
        return returnList;
    }

    public void setEvents(List<Event> events) {
        List<Event> list = new ArrayList<>();
        for(Event event: events){
            list.add((Event) event.clone());
        }
        this.events = list;
    }

    public void addEvent(BookDiscussion bookDiscussion){
        events.add((Event) bookDiscussion.clone());
    }

    public void addEvent(BookExchange bookExchange){
        events.add((Event) bookExchange.clone());
    }


}
