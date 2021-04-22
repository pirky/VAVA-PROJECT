package project.controller.readerControllers.EventsCalendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import project.controller.Main;
import java.io.IOException;
import java.time.YearMonth;
import java.util.*;

public class EventsController {
    private YearMonth currYearMonth;
    @FXML private Pane pane;
    @FXML private Label yearMonthLabel;
    @FXML private Label monday;
    @FXML private Label tuesday;
    @FXML private Label wednesday;
    @FXML private Label thursday;
    @FXML private Label friday;
    @FXML private Label saturday;
    @FXML private Label sunday;

    @FXML
    public void initialize(){
        currYearMonth = YearMonth.from(Main.booksDatabase.getDate());
        updateCalendar();
    }

    public void languageEN(){
        Main.currLanguage = "US";
        yearMonthLabel.setText(currYearMonth.getMonth().toString() + " " + currYearMonth.getYear());
        Locale enLocale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.readerView", enLocale);
        changeSigns(bundle);
    }

    public void languageSK(){
        Main.currLanguage = "SK";
        List<String> monthsSK = Arrays.asList("JANUÁR", "FEBRUÁR", "MAREC", "APRÍL", "MÁJ", "JÚN", "JÚL", "AUGUST", "SEPTEMBER", "OKTÓBER", "NOVEMBER", "DECEMBER");
        yearMonthLabel.setText(monthsSK.get(currYearMonth.getMonthValue() - 1) + " " + currYearMonth.getYear());
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.readerView", skLocale);
        changeSigns(bundle);
    }

    public void changeSigns(ResourceBundle bundle){
        monday.setText(bundle.getString("monday"));
        tuesday.setText(bundle.getString("tuesday"));
        wednesday.setText(bundle.getString("wednesday"));
        thursday.setText(bundle.getString("thursday"));
        friday.setText(bundle.getString("friday"));
        saturday.setText(bundle.getString("saturday"));
        sunday.setText(bundle.getString("sunday"));
    }

    public void updateCalendar(){
        if(Main.currLanguage.equals("SK")) languageSK();
        else languageEN();
        pane.getChildren().clear();
        pane.getChildren().add(new CalendarController(currYearMonth).getView());
    }

    public void previousMonth(){
        currYearMonth = currYearMonth.minusMonths(1);
        updateCalendar();
    }

    public void nextMonth(){
        currYearMonth = currYearMonth.plusMonths(1);
        updateCalendar();
    }

    public void setCurrYearMonth(YearMonth currYearMonth) {
        this.currYearMonth = currYearMonth;
    }

    public void showMenu() throws IOException {
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.readerView", skLocale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
