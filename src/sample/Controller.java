package sample;
import org.apache.log4j.Logger;
import sample.models.*;
import sample.utils.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class);

    @FXML
    private TextField textFieldBus;
    @FXML
    private ComboBox comboBoxBusStop;
    @FXML
    private TextField textFieldBusStop;
    @FXML
    private ComboBox comboBoxType;
    @FXML
    private DatePicker datePickerDate;

    @FXML
    private TextField textFieldFrom;
    @FXML
    private TextField textFieldDestination;
    @FXML
    private TextArea textAreaTimetable;
    @FXML
    private TextField textFieldBusStopTimetable;

    @FXML
    private Button buttonSave;

    private Bus currentBus;
    private BusStop currentBusStop;

    @FXML
    public void initialize() {
        logger.info("Initialization...");

    }

    public void clearInterface() {
        logger.info("Setting default values...");
        textFieldBus.setText(null);
        textFieldBusStop.setText(null);
        textFieldFrom.setText(null);
        textFieldDestination.setText(null);
        textAreaTimetable.setText(null);
        comboBoxBusStop.getItems().clear();

        currentBus = null;
        currentBusStop = null;
    }

    public void setBus(Bus bus) {
        currentBus = bus;

        // Bus Name
        textFieldBus.setText(bus.getName());

        // Bus Stop
        comboBoxBusStop.getItems().clear();
        comboBoxBusStop.getItems().addAll(bus.getBusStops().stream().map((x) -> {
            return x.getName();
        }).collect(Collectors.toList()));

        // Date
        datePickerDate.setValue(LocalDate.now());

        // Setting from & destination fields
        textFieldFrom.setText(bus.getFrom());
        textFieldDestination.setText(bus.getDestination());
    }

    public boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    public void updateBusStopList() {
        if (currentBus != null) {
            comboBoxBusStop.getItems().clear();
            comboBoxBusStop.getItems().addAll(currentBus.getBusStops().stream().map((x) -> {
                return x.getName();
            }).collect(Collectors.toList()));
            logger.info("ComboBoxBusStop has been updated");
        }
    }

    public void updateBusStopList(String name) {
        updateBusStopList();
        comboBoxBusStop.setValue(name);
    }

    public void onButtonStopAdd() {
        if (currentBus != null) {
            String name = textFieldBusStop.getText();
            if (!isNullOrEmpty(name)) {
                BusStop busStop = new BusStop(name);
                currentBus.getBusStops().add(busStop);
                logger.info("New bus stop added: " + name);
            }
            updateBusStopList(name);
        }
    }

    public void onButtonStopRemove() {
        if (currentBus != null) {
            String name = textFieldBusStop.getText();
            if (!isNullOrEmpty(name)) {
                BusStop busStop = currentBus.getBusStops().stream().filter((x) -> x.getName().equals(name)).findFirst().get();
                if (busStop != null) {
                    currentBus.getBusStops().remove(busStop);
                }
                logger.info("Bus stop removed: " + name);
            }
            updateBusStopList();
        }
    }

    public void onUpdateTimetableClick() {
        if (currentBusStop != null) {
            logger.info("Updating timetable");
            String name = textFieldBusStopTimetable.getText();
            currentBusStop.setName(name);

            try {
                String[] timetableData = textAreaTimetable.getText().split(" ");
                ArrayList<Double> timetable = new ArrayList<Double>(Arrays.asList(timetableData).stream().map((x) -> {
                    return Double.parseDouble(x);
                }).collect(Collectors.toList()));

                DayOfWeek day = datePickerDate.getValue().getDayOfWeek();
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    currentBusStop.setWeekendTimetable(timetable);
                } else {
                    currentBusStop.setWorkdayTimetable(timetable);
                }
            } catch (Exception ex) {
                logger.error("Exception in onUpdateTimetableClick:", ex);
            }

            updateBusStopList(name);
        }
    }

    public void onTextFieldBusStopChanged() {
        if (currentBusStop != null) {
            String name = textFieldBusStop.getText();
            currentBusStop.setName(name);
            logger.info("CurrentBusStop name has been updated: " + name);
        }
    }

    public void onDatePickerDateChanged() {
        if (currentBusStop != null) {

            ArrayList<Double> timetableData;
            DayOfWeek day = datePickerDate.getValue().getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                timetableData = currentBusStop.getWeekendTimetable();
            } else {
                timetableData = currentBusStop.getWorkdayTimetable();
            }

            if(timetableData == null)
                return;

            String text = String.join(" ", timetableData.stream().map((x) -> {
                return x.toString();
            }).collect(Collectors.toList()));

            textAreaTimetable.setText(text);
            logger.info("DatePicker has been changed");
        }
    }

    public void onComboBoxBusStopChanged() {
        String name = (comboBoxBusStop.getValue() != null) ? comboBoxBusStop.getValue().toString() : null;
        if (name == null)
            return;

        currentBusStop = currentBus.getBusStops().stream().filter(x -> x.getName().equals(name)).findFirst().get();
        textFieldBusStop.setText(name);
        textFieldBusStopTimetable.setText(name);
        onDatePickerDateChanged();
        logger.info("Bus stop changed: " + currentBusStop.getName());
    }

    public void onNewClick() {
        Bus bus = new Bus("Bus", "From", "Destination");
        bus.getBusStops().add(new BusStop("BusStop"));

        clearInterface();
        setBus(bus);
    }

    public void onOpenClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getStage());
        if (file != null) {
            Bus bus = XML.deserializeFromXML(file.getAbsolutePath());
            setBus(bus);
        }
    }

    public void onSaveClick() {
        if (currentBus != null) {
            currentBus.setName(textFieldBus.getText());
            currentBus.setFrom(textFieldFrom.getText());
            currentBus.setDestination(textFieldDestination.getText());
        }
    }

    public void onExportClick() {
        logger.info("Exporting: " + currentBus.getName());
        XML.serializeToXML(currentBus, currentBus.getName());
    }

    public void onQuitClick() {
        logger.info("QuitMenuItem. Closing the application...");
        System.exit(0);
    }
}
