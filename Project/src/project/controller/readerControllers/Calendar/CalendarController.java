package project.controller.readerControllers.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import project.controller.Main;
import project.controller.readerControllers.EventEnrollController;
import project.model.events.Event;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
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

            ListView<Event> listView = getListView(calendarDate, vBoxNode, yearMonth);
            vBoxNode.getChildren().addAll(label, listView);
            calendarDate = calendarDate.plusDays(1);
        }
    }

    private ListView<Event> getListView(LocalDate calendarDate, VBoxNode vBoxNode, YearMonth yearMonth){
        ObservableList<Event> currEvents = getCurrEvents(calendarDate);
        ListView<Event> listView = new ListView<>();
        listView.setMaxHeight(vBoxNode.getPrefHeight() - 25);
        listView.setMaxWidth(186);
        listView.setStyle("-fx-font-size:14.0;");
        listView.setCellFactory(param -> new ListCell<Event>(){
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event==null) {
                    setGraphic(null);
                    setText(null);
                }else{
                    for(Reader reader: event.getParticipants()){
                        if(reader.toString().equals(Main.currUser.toString())){
                            setStyle("-fx-background-color: #bdffb3");
                            break;
                        }
                    }
                    for(Reader reader: event.getVolunteers()){
                        if(reader.toString().equals(Main.currUser.toString())){
                            setStyle("-fx-background-color: #bdffb3");
                            break;
                        }
                    }
                    setMinWidth(param.getWidth() - 19);
                    setMaxWidth(param.getWidth() - 19);
                    setPrefWidth(param.getWidth() - 19);
                    setWrapText(true);
                    setText(event.getName());
                }
            }
        });
        listView.setItems(currEvents);
        if(!currEvents.isEmpty()){
            listView.setOnMouseReleased(e -> {
                try {
                    showEvent(listView, yearMonth);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        return listView;
    }

    private void showEvent(ListView<Event> listView, YearMonth yearMonth) throws IOException {
        Event event = listView.getSelectionModel().getSelectedItem();
        if(event == null){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/readerViews/EventEnrollView.fxml"));
        Parent root = loader.load();
        Main.mainStage.setResizable(false);
        EventEnrollController eventEnrollController = loader.getController();
        eventEnrollController.setYearMonth(yearMonth);
        eventEnrollController.setEvent(event);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
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
