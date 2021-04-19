package project.controller.readerControllers.Calendar;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VBoxNode extends VBox {
    private LocalDate date;

    public VBoxNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> System.out.println("This pane's date is: " + date));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
