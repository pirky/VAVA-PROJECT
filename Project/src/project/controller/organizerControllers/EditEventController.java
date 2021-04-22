package project.controller.organizerControllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import project.controller.Main;
import project.model.Rooms.LibraryRoom;
import project.model.Rooms.RoomReservation;
import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;
import project.model.users.Organizer;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventController {
    ObservableList<LibraryRoom> allRooms = FXCollections.observableArrayList();
    ObservableList<Event> eventsObservable = FXCollections.observableArrayList();
    @FXML private ListView<ImageView> listView;
    @FXML private ComboBox<LibraryRoom> comboBox;
    @FXML private TextArea hostArea;
    @FXML private TextArea nameArea;
    @FXML private TextArea noteArea;
    @FXML private Label hostLabel;
    @FXML private Label infoLabel;
    @FXML private Button btn;
    @FXML ComboBox<Event> events;private Event globalEvent;
    @FXML private Text capacity;
    @FXML Text typeEvent;

    public void comboClicked(){
        comboBox.setDisable(false);
        nameArea.setDisable(false);
        noteArea.setDisable(false);
        hostLabel.setVisible(true);
        hostArea.setVisible(true);

        Organizer organizer = (Organizer) Main.currUser;

        List<BookDiscussion> bookDiscusionList = new java.util.ArrayList<>(Collections.emptyList());
        List<BookExchange> bookExchangeList = new java.util.ArrayList<>(Collections.emptyList());
        List<Event> eventsList = organizer.getEvents();

        for(Event event : eventsList){
            if (event instanceof BookDiscussion){
                bookDiscusionList.add((BookDiscussion) event);
            }
            else if (event instanceof BookExchange){
                bookExchangeList.add((BookExchange) event);

            }
        }

        if (events.getSelectionModel().getSelectedItem() instanceof BookDiscussion){
            hostLabel.setVisible(true);
            hostArea.setVisible(true);
            globalEvent = events.getSelectionModel().getSelectedItem();

            for (BookDiscussion bookDisc : bookDiscusionList) {
                if(bookDisc.getName().equals(events.getSelectionModel().getSelectedItem().getName())){
                    globalEvent = bookDisc;
                    nameArea.setText(bookDisc.getName());
                    noteArea.setText(bookDisc.getNote());
                    hostArea.setText(bookDisc.getHost());
                    comboBox.getSelectionModel().select(bookDisc.getReservation().getRoomId());
                    typeEvent.setText("diskusia");
                    for(LibraryRoom room : Main.roomsDatabase.getRooms())
                        if(room.getId() == bookDisc.getReservation().getRoomId()){
                            capacity.setText(String.valueOf(room.getCapacity()));
                        }
                }
            }

        }
        else if (events.getSelectionModel().getSelectedItem() instanceof BookExchange){
            hostLabel.setVisible(false);
            hostArea.setVisible(false);
            globalEvent = events.getSelectionModel().getSelectedItem();

            for (BookExchange bookExch : bookExchangeList) {
                if(bookExch.getName().equals(events.getSelectionModel().getSelectedItem().getName())){
                    nameArea.setText(bookExch.getName());
                    noteArea.setText(bookExch.getNote());
                    comboBox.getSelectionModel().select(bookExch.getReservation().getRoomId());
                    typeEvent.setText("burza");
                    for(LibraryRoom room : Main.roomsDatabase.getRooms())
                        if(room.getId() == bookExch.getReservation().getRoomId()){
                            capacity.setText(String.valueOf(room.getCapacity()));
                        }
                }
            }
        }
    }

    public void onClicked(){
        capacity.setText(String.valueOf(comboBox.getSelectionModel().getSelectedItem().getCapacity()));
    }

    @FXML
    public void initialize(){
        Organizer organizer = (Organizer) Main.currUser;
        eventsObservable.addAll(organizer.getEvents());
        events.setItems(eventsObservable);
        infoLabel.setVisible(false);
        hostLabel.setVisible(false);
        hostArea.setVisible(false);
        comboBox.setDisable(true);
        nameArea.setDisable(true);
        noteArea.setDisable(true);
        btn.setDisable(false);
        allRooms.addAll(Main.roomsDatabase.getRooms());
        comboBox.setItems(allRooms);
    }

    public void createEvent() {
        LibraryRoom room = comboBox.getValue();
        String name = nameArea.getText();
        String host;

        if (typeEvent.getText().equals("diskusia")) {
            host = hostArea.getText();
        } else {
            host = "nothing";
        }
        String note = noteArea.getText();
        if (testRequired(name, host, note)) {
            return;
        }

        Main.userDatabase.removeUser(Main.currUser);
        Organizer organizer = (Organizer) Main.currUser;

        if (typeEvent.getText().equals("burza")) {
            BookExchange bookExchange = new BookExchange(name, note, new RoomReservation(globalEvent.getReservation().getDateFrom(), globalEvent.getReservation().getDateTo(), room.getId()), (Organizer) Main.currUser);
            organizer.removeEvent(globalEvent);
            globalEvent = bookExchange;
            organizer.addEvent(bookExchange);
        } else if (typeEvent.getText().equals("diskusia")) {
            BookDiscussion bookDiscussion = new BookDiscussion(name, note, new RoomReservation(globalEvent.getReservation().getDateFrom(), globalEvent.getReservation().getDateTo(), room.getId()), (Organizer) Main.currUser, host);
            organizer.removeEvent(globalEvent);
            globalEvent = bookDiscussion;
            organizer.addEvent(bookDiscussion);
        }

        Main.userDatabase.addUser(organizer);
        Main.currUser = organizer;
        events.getItems().clear();
        eventsObservable.addAll(organizer.getEvents());
        events.setItems(eventsObservable);
        deleteFields();
    }

    private boolean testRequired(String name, String host, String note){
        if(name.isEmpty() || host.isEmpty() || note.isEmpty()){
            infoLabel.setText("Vyplň všetky polia");
            LOG.log(Level.INFO, "User did not enter all required fields");
            infoLabel.setVisible(true);
            return true;
        }

        infoLabel.setVisible(false);
        return false;
    }

    private void deleteFields(){
        comboBox.getSelectionModel().clearSelection();
        nameArea.clear();
        hostArea.clear();
        noteArea.clear();
        listView.getItems().clear();
        listView.refresh();
        hostLabel.setVisible(false);
        hostArea.setVisible(false);
        comboBox.setDisable(true);
        nameArea.setDisable(true);
        noteArea.setDisable(true);
    }

    public void deleteEvent(){
        Organizer organizer = (Organizer) Main.currUser;
        organizer.removeEvent(globalEvent);
        events.getItems().clear();
        eventsObservable.addAll(organizer.getEvents());
        events.setItems(eventsObservable);
        deleteFields();


    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    private static final Logger LOG = Logger.getLogger(EditEventController.class.getName());

}
