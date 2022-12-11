package pl.polsl.controller;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import pl.polsl.model.InstanceNotFoundException;
import pl.polsl.model.KeyViolationException;
import pl.polsl.model.Model;
import pl.polsl.model.NameViolationException;
import pl.polsl.otherClasses.Person;
import pl.polsl.otherClasses.Position;
import pl.polsl.otherClasses.Priority;
import pl.polsl.otherClasses.Task;

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
    @FXML
    private AnchorPane appPanel;
    
    private Dialog<String> errorPopup, infoPopup;
    private ObservableList<Person> peopleData;   
    private ObservableList<Task> taskData;
    private final Model model;
    private String draggedAssigneeSignum;
    @FXML
    private Button assignButton;
    @FXML
    private Button unassignButton;


    
    public MainMenuController(Model model) {
        this.model = model;
        peopleData = FXCollections.observableArrayList(this.model.getTeam());
        taskData = FXCollections.observableArrayList(this.model.getTasks());
        peopleData.addListener(new ListChangeListener<Person>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Person> change) {
                while (change.next()) {                 
                    for (var remitem : change.getRemoved()) {
                        try {
                            model.removePersonBySignum(remitem.getSignum());
                        } catch (InstanceNotFoundException ex) {
                            errorPopup.setHeaderText("Unexpected error occured");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
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
                            errorPopup.setHeaderText("Invalid input data");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
                        }
                    }
                    for (var getitem : change.getList()) {
                        try {
                            model.getPersonBySignum(getitem.getSignum());
                        } catch (InstanceNotFoundException ex) {
                            errorPopup.setHeaderText("Unexpected error occured");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
                        }
                    }
                }
            }
        });
        
        taskData.addListener(new ListChangeListener<Task>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> change) {
                while (change.next()) {
                    for (var remitem : change.getRemoved()) {
                        try {
                            model.removeTaskById(remitem.getTaskID());
                        } catch (InstanceNotFoundException ex) {
                            errorPopup.setHeaderText("Unexpected error occured");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
                        }
                    } 
                    for (var additem : change.getAddedSubList()) {
                        try {
                            model.addNewTaskToBoard(additem);
                            IDTextfield.clear();
                            descriptionTextarea.clear();
                            datePicker.setValue(null);
                        } catch (KeyViolationException ex) {
                            taskData.remove(additem);
                            errorPopup.setHeaderText("Invalid input data");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
                        }
                    }                
                    for (var getitem : change.getList()) {
                        try {
                            model.getTaskById(getitem.getTaskID());
                        } catch (InstanceNotFoundException ex) {
                            errorPopup.setHeaderText("Unexpected error occured");
                            errorPopup.setContentText(ex.getMessage());
                            errorPopup.show();
                        }
                    }
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
        errorPopup = new Dialog<>();
        errorPopup.getDialogPane().getButtonTypes().add(confirmButton);
        errorPopup.setTitle("ERROR");
        errorPopup.setResizable(false);
        Image warnLabel = new Image("https://freesvg.org/img/warningSmall.png");
        ImageView warnLabelView = new ImageView(warnLabel);
        warnLabelView.setFitWidth(30);
        warnLabelView.setFitHeight(30);
        errorPopup.setGraphic(warnLabelView);
        
        infoPopup = new Dialog<>();
        infoPopup.getDialogPane().getButtonTypes().add(confirmButton);
        infoPopup.setTitle("INFO");
        infoPopup.setResizable(false);
        Image infoLabel = new Image("https://freesvg.org/img/1527172379.png");
        ImageView infoLabelView = new ImageView(infoLabel);
        infoLabelView.setFitWidth(30);
        infoLabelView.setFitHeight(30);
        infoPopup.setGraphic(infoLabelView);
 
        positionChoiceBox.getItems().addAll(Position.DEVELOPER,
            Position.MANAGER, Position.PRODUCT_OWNER);
        
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
    }

    @FXML
    private void addPersonToTable(ActionEvent event) {
        if (positionChoiceBox.getValue() == null ||
            signumTextfield.getText().equals("") ||
            surnameTextfield.getText().equals("") ||
            nameTextfield.getText().equals("")) {
            errorPopup.setHeaderText("Empty fields");
            errorPopup.setContentText("Please fill out all "
            + "empty fields.");
            errorPopup.show();
            return;
        }
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
            errorPopup.setHeaderText("Empty fields");
            errorPopup.setContentText("Please fill out all "
            + "empty fields.");
            errorPopup.show();
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
        if (event.getClickCount() == 2) {
            int index = taskTableView.getSelectionModel().getSelectedIndex();
            if(index != -1) {
                Task task = taskData.get(index);
                infoPopup.setHeaderText("Task info:");
                String taskInfo = "Id: " + task.getTaskID() + 
                    "\nPriority: " + task.getPriority() + 
                    "\nDate: " + task.getDate() + 
                    "\nDescription:\n\t" + task.getDescription() + 
                    "\nAssignee: " + (task.getAssigneeSignum().equals("")
                    ? "Not assigned" : task.getAssigneeSignum());
                infoPopup.setContentText(taskInfo);
                infoPopup.show();
            }
            taskTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void personClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = personTableView.getSelectionModel().getSelectedIndex();
            if(index != -1) {
                Person person = peopleData.get(index);
                infoPopup.setHeaderText("Person info:");
                String personInfo = "Signum: " + person.getSignum() + 
                    "\nName: " + person.getName() + 
                    "\nSurname: " + person.getSurname() + 
                    "\nPosition: " + person.getPosition();
                infoPopup.setContentText(personInfo);
                infoPopup.show();
            }
            personTableView.getSelectionModel().clearSelection(); 
        }
    } 

    @FXML
    private void assignPersonToTask(ActionEvent event) {
        int personIndex = personTableView.getSelectionModel().getSelectedIndex();
        int taskIndex = taskTableView.getSelectionModel().getSelectedIndex();
        if (personIndex == -1) {
            errorPopup.setHeaderText("No person to assign");
            errorPopup.setContentText("Please mark person in the table first.");
            errorPopup.show();
            return;
        }
        if (taskIndex == -1) {
            errorPopup.setHeaderText("No task to be assigned to");
            errorPopup.setContentText("Please mark task in the table first.");
            errorPopup.show();
            return;
        }
        Task task = taskData.get(taskIndex);
        task.setAssigneeSignum(peopleData
                .get(personIndex).getSignum());
        taskData.set(taskIndex, task);
        infoPopup.setHeaderText("Success");
        infoPopup.setContentText("Person identified by signum " 
            + peopleData.get(personIndex).getSignum()
            + " assigned to task " + task.getTaskID() + ".");
        infoPopup.show();
        personTableView.getSelectionModel().clearSelection();
        taskTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void unassignTask(ActionEvent event) {
        int taskIndex = taskTableView.getSelectionModel().getSelectedIndex();
        if (taskIndex == -1) {
            errorPopup.setHeaderText("No task to be assigned to");
            errorPopup.setContentText("Please mark task in the table first.");
            errorPopup.show();
            return;
        }
        Task task = taskData.get(taskIndex);
        task.setAssigneeSignum("");
        taskData.set(taskIndex, task);
        infoPopup.setHeaderText("Success");
        infoPopup.setContentText("Task unassigned successfully!");
        infoPopup.show();
        taskTableView.getSelectionModel().clearSelection();
    }
}