package project.controller.readerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import project.controller.Main;
import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

public class EventEnrollController {
    private YearMonth yearMonth;
    private Event event;
    @FXML
    private Label typeLabel;
    @FXML
    private Label hostLabel;
    @FXML
    private TextArea titleArea;
    @FXML
    private TextArea noteArea;
    @FXML
    private TextField hostField;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Button volunteerBtn;
    @FXML
    private Button participantBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label participantLabel;

    @FXML
    public void initialize(){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate dateCurr, boolean empty) {
                        super.updateItem(dateCurr, empty);
                        setDisable(true);
                    }
                };
            }
        };
        datePickerFrom.setDayCellFactory(dayCellFactory);
        datePickerTo.setDayCellFactory(dayCellFactory);
    }

    public void cancelParticipation() throws IOException {
        event.removeParticipant((Reader) Main.currUser);
        event.removeVolunteer((Reader) Main.currUser);
        enroll(3);
    }

    public void enrollParticipant() throws IOException {
        event.addParticipant((Reader) Main.currUser);
        enroll(1);
    }

    public void enrollVolunteer() throws IOException {
        event.addVolunteer((Reader) Main.currUser);
        enroll(2);
    }

    private void enroll(int option) throws IOException {
        for(User user: Main.userDatabase.getUserDatabase()){
            if(!(user instanceof Organizer)){
                continue;
            }
            Organizer organizer = (Organizer) user;
            for(Event temp: organizer.getEvents()){
                if (temp.toString().equals(event.toString())){
                    organizer.removeEvent(event);
                    if(event instanceof BookDiscussion){
                        organizer.addEvent((BookDiscussion) event);
                    }
                    else{
                        organizer.addEvent((BookExchange) event);
                    }
                    Main.userDatabase.removeUser(organizer);
                    Main.userDatabase.addUser(organizer);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("project/images/logo.jpg"));
                    if(option == 1){
                        alert.setTitle("Úspešná Registrácia");
                        alert.setHeaderText("Ste úspešne zaregistrovaný ako účastník");
                    }
                    else if(option == 2){
                        alert.setTitle("Úspešná Registrácia");
                        alert.setHeaderText("Ste úspešne zaregistrovaný ako dobrovoľník");
                    }
                    else if(option == 3){
                        alert.setTitle("Úspešné odhlásenie sa");
                        alert.setHeaderText("Ste úspešne odhlásený z podujatia");
                    }
                    alert.setContentText(null);
                    alert.showAndWait();
                    showCalendar();
                    return;
                }
            }
        }
    }

    public void setEvent(Event event) {
        this.event = event;
        if(event instanceof BookDiscussion){
            typeLabel.setText("Beseda");
            hostLabel.setVisible(true);
            hostField.setVisible(true);
            hostField.setText(((BookDiscussion) event).getHost());
        }
        else{
            typeLabel.setText("Burza");
            hostLabel.setVisible(false);
            hostField.setVisible(false);
        }
        titleArea.setText(event.getName());
        noteArea.setText(event.getNote());
        datePickerFrom.setValue(event.getReservation().getDateFrom());
        datePickerTo.setValue(event.getReservation().getDateTo());
        if(findParticipation()){
            participantBtn.setVisible(false);
            volunteerBtn.setVisible(false);
            cancelBtn.setVisible(true);
            participantLabel.setVisible(true);
        }
        else{
            participantBtn.setVisible(true);
            volunteerBtn.setVisible(true);
            cancelBtn.setVisible(false);
            participantLabel.setVisible(false);
        }
    }

    private boolean findParticipation(){
        for(Reader reader: event.getParticipants()){
            if(reader.toString().equals(Main.currUser.toString())){
                participantLabel.setText("Účastník");
                return true;
            }
        }

        for(Reader reader: event.getVolunteers()){
            if(reader.toString().equals(Main.currUser.toString())){
                participantLabel.setText("Dobrovoľník");
                return true;
            }
        }
        return false;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void showCalendar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/readerViews/EventsView.fxml"));
        Parent root = loader.load();
        Main.mainStage.setResizable(false);
        EventsController eventsController = loader.getController();
        eventsController.setCurrYearMonth(yearMonth);
        eventsController.updateCalendar();
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
