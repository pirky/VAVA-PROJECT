package project.controller.readerControllers.EventsCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import project.controller.Main;
import project.model.events.BookDiscussion;
import project.model.events.Event;
import project.model.events.Message;
import java.io.IOException;
import java.time.YearMonth;


public class DiscussionController {
    ObservableList<Message> messages = FXCollections.observableArrayList();
    private YearMonth yearMonth;
    private Event event;
    @FXML
    private ListView<Message> listView;

    public void setEvent(Event event) {
        this.event = event;
//        messages.addAll(((BookDiscussion) event).getMessages());
        messages.add(new Message("serus", "userName"));
        messages.add(new Message("serus", "pele"));
        messages.add(new Message("debilko", "jozo"));
        listView.setItems(messages);

    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void showEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/readerViews/EventsCalendar/EventEnrollView.fxml"));
        Parent root = loader.load();
        Main.mainStage.setResizable(false);
        EventEnrollController eventEnrollController = loader.getController();
        eventEnrollController.setYearMonth(yearMonth);
        eventEnrollController.setEvent(event);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
