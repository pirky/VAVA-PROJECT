package project.controller.readerControllers.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import project.controller.Main;
import project.model.events.Event;
import project.model.users.Organizer;
import project.model.users.User;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


public class CalendarController {
    private final List<Event> events;
    private final ArrayList<VBoxNode> allCalendarDays = new ArrayList<>(42);
    private final VBox view;

    public CalendarController(YearMonth yearMonth) {
        events = new ArrayList<>();
        setEvents(yearMonth);
        GridPane calendar = new GridPane();
        calendar.setPrefSize(1350, 680);
        calendar.setGridLinesVisible(true);

        int calendarLen = getRows(yearMonth);
        for (int i = 0; i < calendarLen; i++) {
            for (int j = 0; j < 7; j++) {
                VBoxNode vBoxNode = new VBoxNode();

                vBoxNode.setPrefSize(200, (int) Math.round(680 / (double) calendarLen));
                vBoxNode.setAlignment(Pos.TOP_CENTER);
                calendar.add(vBoxNode,j,i);
                allCalendarDays.add(vBoxNode);
            }
        }

        Text[] dayNames = new Text[]{ new Text("Pondelok"), new Text("Utorok"), new Text("Streda"),
                                        new Text("Štvrtok"), new Text("Piatok"), new Text("Sobota"),
                                        new Text("Nedeľa") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(1350);
        int col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            AnchorPane.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        populateCalendar(yearMonth);
        view = new VBox(dayLabels, calendar);
    }

    public void populateCalendar(YearMonth yearMonth) {
        // go back to nearest Monday
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }

        for (VBoxNode vBoxNode : allCalendarDays) {
            if (vBoxNode.getChildren().size() != 0) {
                vBoxNode.getChildren().remove(0);
            }
            Label label = new Label(" " + calendarDate.getDayOfMonth());
            label.setMinSize(190, 5);
            label.setStyle("-fx-font-size:14.0;");
            label.setTextAlignment(TextAlignment.LEFT);
            vBoxNode.setDate(calendarDate);

            if(calendarDate.getMonthValue() != yearMonth.getMonthValue()){
                vBoxNode.setStyle("-fx-background-color: #ffb8b8;");
                calendarDate = calendarDate.plusDays(1);
                vBoxNode.setAlignment(Pos.TOP_LEFT);
                vBoxNode.getChildren().add(label);
                continue;
            }

            ListView<Event> listView = getListView(calendarDate, vBoxNode);
            vBoxNode.getChildren().addAll(label, listView);
            calendarDate = calendarDate.plusDays(1);
        }
    }

    private ListView<Event> getListView(LocalDate calendarDate, VBoxNode vBoxNode){
        ObservableList<Event> currEvents = getCurrEvents(calendarDate);
        ListView<Event> listView = new ListView<>();
        listView.setMaxHeight(vBoxNode.getPrefHeight() - 25);
        listView.setMaxWidth(186);
        listView.setStyle("-fx-font-size:14.0;");
        listView.setCellFactory(param -> new ListCell<Event>(){
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null);
                }else{
                    setMinWidth(param.getWidth() - 19);
                    setMaxWidth(param.getWidth() - 19);
                    setPrefWidth(param.getWidth() - 19);
                    setWrapText(true);
                    setText(item.getName());
                }
            }
        });
        listView.setItems(currEvents);
        ////// listView on click action pridat
        return listView;
    }

    private ObservableList<Event> getCurrEvents(LocalDate currDate){
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        for(Event event: events){
            if(event.getReservation().getDateFrom().compareTo(currDate) <= 0 && event.getReservation().getDateTo().compareTo(currDate) >=0){
                eventList.add(event);
            }
        }
        return eventList;
    }

    private void setEvents(YearMonth yearMonth){
        events.clear();
        for(User user: Main.userDatabase.getUserDatabase()){
            if(!(user instanceof Organizer)){
                continue;
            }
            Organizer organizer = (Organizer) user;
            for(Event event: organizer.getEvents()){
                if(event.getReservation().getDateFrom().getMonthValue() == yearMonth.getMonthValue()){
                    events.add(event);
                }
            }
        }
    }

    private int getRows(YearMonth yearMonth){
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        int sizeOfMonth = yearMonth.lengthOfMonth();
        if(((calendarDate.getDayOfWeek().toString().equals("SATURDAY") || calendarDate.getDayOfWeek().toString().equals("SUNDAY"))
                && sizeOfMonth == 31) || calendarDate.getDayOfWeek().toString().equals("SUNDAY") && sizeOfMonth==30) {
            return 6;
        }
        else if((calendarDate.getDayOfWeek().toString().equals("MONDAY") && sizeOfMonth==28)) {
            return 4;
        }
        else{
            return 5;
        }
    }

    public VBox getView() {
        return view;
    }
}
