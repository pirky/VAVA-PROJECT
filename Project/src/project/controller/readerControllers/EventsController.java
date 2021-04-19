package project.controller.readerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import project.controller.Main;
import project.controller.readerControllers.Calendar.CalendarController;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Objects;

public class EventsController {
    private YearMonth currYearMonth;
    @FXML
    private Pane pane;
    @FXML
    private Label yearMonthLabel;

    @FXML
    public void initialize(){
        currYearMonth = YearMonth.from(Main.booksDatabase.getDate());
        yearMonthLabel.setText(currYearMonth.getMonth().toString() + " " + currYearMonth.getYear());
        updateCalendar();
    }

    private void updateCalendar(){
        pane.getChildren().clear();
        pane.getChildren().add(new CalendarController(currYearMonth).getView());
    }

    public void previousMonth(){
        currYearMonth = currYearMonth.minusMonths(1);
        yearMonthLabel.setText(currYearMonth.getMonth().toString() + " " + currYearMonth.getYear());
        updateCalendar();
    }

    public void nextMonth(){
        currYearMonth = currYearMonth.plusMonths(1);
        yearMonthLabel.setText(currYearMonth.getMonth().toString() + " " + currYearMonth.getYear());
        updateCalendar();
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
