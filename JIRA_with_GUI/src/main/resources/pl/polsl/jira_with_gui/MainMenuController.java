package pl.polsl.jira_with_gui;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import pl.polsl.model.InstanceNotFoundException;
import pl.polsl.model.KeyViolationException;
import pl.polsl.model.Model;
import pl.polsl.model.NameViolationException;
import pl.polsl.model.Person;
import pl.polsl.model.Position;
import pl.polsl.model.Priority;
import pl.polsl.model.Task;

/**
 */
public class MainMenuController {

    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField surnameTextfield;
    @FXML
    private TextField signumTextfield;
    @FXML
    private ChoiceBox positionChoiceBox;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn signumColumn;
    @FXML
    private TableColumn positionColumn;
    
    @FXML
    private Button addPersonButton;
    @FXML
    private TableView personTableView;
    @FXML
    private Button removePersonButton;
    @FXML
    private TableView taskTableView;
    @FXML
    private TableColumn IDColumn;
    @FXML
    private TableColumn priorityColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn assigneeColumn;
    @FXML
    private Button addTaskButton;
    @FXML
    private TextField IDTextfield;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button removeTaskButton;
    @FXML
    private ChoiceBox priorityChoiceBox;
    @FXML
    private TextArea descriptionTextarea;
    
    private Dialog<String> dialog;
    private ObservableList<Person> peopleData;   
    private ObservableList<Task> taskData;
    private final Model model;
    @FXML
    private AnchorPane Prio;

    
    public MainMenuController(Model model) {
        this.model = model;
        peopleData = FXCollections.observableArrayList(this.model.getTeam());
        taskData = FXCollections.observableArrayList(this.model.getTasks());
        peopleData.addListener(new ListChangeListener<Person>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Person> change) {
                while (change.next()) {
//                    if (change.wasPermutated()) {
//                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
//                            System.out.println("zamiana");
//                        }
//                    } else if (change.wasUpdated()) {
//                        System.out.println("uaktualnienie");
//                    } else {
                        for (var remitem : change.getRemoved()) {
                            try {
                                model.removePersonBySignum(remitem.getSignum());
                            } catch (InstanceNotFoundException ex) {
                                dialog.setHeaderText("Unexpected error occured");
                                dialog.setContentText(ex.getMessage());
                                dialog.show();
                            }
                        } 
                        for (var additem : change.getAddedSubList()) {
                            try {
                                model.addNewPersonToTeam(additem);
                                nameTextfield.clear();
                                signumTextfield.clear();
                                surnameTextfield.clear();
                            } catch (KeyViolationException | NameViolationException ex) {
                                peopleData.remove(additem);
                                dialog.setHeaderText("Invalid input data");
                                dialog.setContentText(ex.getMessage());
                                dialog.show();
                            }
                        }
//                    }
                }
            }
        });
        
        taskData.addListener(new ListChangeListener<Task>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> change) {
                while (change.next()) {
//                    if (change.wasPermutated()) {
//                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
//                            System.out.println("zamiana");
//                        }
//                    } else if (change.wasUpdated()) {
//                        System.out.println("uaktualnienie");
//                    } else {
                        for (var remitem : change.getRemoved()) {
                            try {
                                model.removeTaskById(remitem.getTaskID());
                            } catch (InstanceNotFoundException ex) {
                                dialog.setHeaderText("Unexpected error occured");
                                dialog.setContentText(ex.getMessage());
                                dialog.show();
                            }
                        } 
                        for (var additem : change.getAddedSubList()) {
                            try {
                                model.addNewTaskToBoard(additem);
                                IDTextfield.clear();
                                descriptionTextarea.clear();
                            } catch (KeyViolationException ex) {
                                taskData.remove(additem);
                                dialog.setHeaderText("Invalid input data");
                                dialog.setContentText(ex.getMessage());
                                dialog.show();
                            }
                        }
//                    }
                }
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        ButtonType confirmButton = new ButtonType("OK, I got it",
        ButtonData.OK_DONE);
        dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(confirmButton);
        dialog.setTitle("Error");
 
        positionChoiceBox.getItems().addAll(Position.DEVELOPER,
            Position.MANAGER, Position.PRODUCT_OWNER);
        positionChoiceBox.setValue(Position.DEVELOPER);
        
        priorityChoiceBox.getItems().addAll(Priority.HIGH,
            Priority.MEDIUM, Priority.LOW);
        
        personTableView.setItems(peopleData);
        signumColumn.setCellValueFactory(new PropertyValueFactory<>("signum"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        taskTableView.setItems(taskData);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("taskID"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assigneeSignum"));
        
//        tableView.setEditable(true);
//        
//        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());	
//
//        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Person, String> t) {
//                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
//            }
//        });
    }

    @FXML
    private void addPersonToTable(ActionEvent event) {
        Person newPerson = new Person(signumTextfield.getText(),
        nameTextfield.getText(), surnameTextfield.getText(),
        (Position) positionChoiceBox.getValue());
        peopleData.add(newPerson);
    }

    @FXML
    private void removePersonFromTable(ActionEvent event) {
        int index = personTableView.getSelectionModel().getSelectedIndex();
        if(index != -1) peopleData.remove(index);
    }

    @FXML
    private void addTaskToTable(ActionEvent event) {
        if (datePicker.getValue() == null || 
            priorityChoiceBox.getValue() == null ||
            IDTextfield.getText().equals("")) {
            dialog.setHeaderText("Empty textfields");
            dialog.setContentText("Please fill out all the"
            + "empty textfields.");
            dialog.show();
            return;
        }
        Task newTask = new Task(IDTextfield.getText(),
        (Priority) priorityChoiceBox.getValue(),
        datePicker.getValue(),
        descriptionTextarea.getText(), "");
        taskData.add(newTask);
    }

    @FXML
    private void removeTaskFromTable(ActionEvent event) {
        int index = taskTableView.getSelectionModel().getSelectedIndex();
        if(index != -1) taskData.remove(index);
    }

    @FXML
    private void taskClicked(MouseEvent event) {
        int index = taskTableView.getSelectionModel().getSelectedIndex();
        if(index != -1) {
            dialog.setHeaderText("Task info");
            dialog.setContentText(taskData.get(index).getDescription());
            dialog.show();
        }
    }
    
}
