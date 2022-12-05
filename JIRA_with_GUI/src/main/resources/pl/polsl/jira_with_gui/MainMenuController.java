/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl.jira_with_gui;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.polsl.model.InstanceNotFoundException;
import pl.polsl.model.KeyViolationException;
import pl.polsl.model.Model;
import pl.polsl.model.NameViolationException;
import pl.polsl.model.Person;
import pl.polsl.model.Position;

/**
 */
public class MainMenuController {

    @FXML
    private Button addToTableButton;
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField surnameTextfield;
    @FXML
    private TextField signumTextfield;
    @FXML
    private ChoiceBox positionChoiceBox;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn signumColumn;
    @FXML
    private TableColumn positionColumn;
        
    private ObservableList<Person> data;    
    private final Model model;

    
    public MainMenuController(Model model) {
        this.model = model;
        data = FXCollections.observableArrayList(model.getTeam());
        data.addListener(new ListChangeListener<Person>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Person> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            System.out.println("zamiana");
                        }
                    } else if (change.wasUpdated()) {
                        System.out.println("uaktualnienie");
                    } else {
                        for (var remitem : change.getRemoved()) {
                            model.getTeam().remove(remitem);
                        } 
                        for (var additem : change.getAddedSubList()) {
                            try {
                                model.addNewPersonToTeam(additem);
                            } catch (KeyViolationException | NameViolationException ex) {
                                data.remove(additem);
                                System.out.println("Caught");
                            }
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
        
        positionChoiceBox.getItems().addAll(Position.DEVELOPER,
            Position.MANAGER, Position.PRODUCT_OWNER);
        positionChoiceBox.setValue(Position.DEVELOPER);
        
        tableView.setItems(data);
        signumColumn.setCellValueFactory(new PropertyValueFactory<>("signum"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        tableView.setEditable(true);
        
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());	

        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            }
        });
    }    

    @FXML
    private void addToTable(ActionEvent event) {
        Person newPerson = new Person(signumTextfield.getText(),
        nameTextfield.getText(), surnameTextfield.getText(),
        (Position) positionChoiceBox.getValue());
        data.add(newPerson);
        nameTextfield.clear();
        signumTextfield.clear();
        surnameTextfield.clear();
    }
    
}
