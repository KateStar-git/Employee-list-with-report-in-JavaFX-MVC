package controller;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Person;
import model.RoomNumbers;

public class MainWindowController {
	private Main main;
	private Stage primaryStage;
	private File personsFile;
	private ObservableList<Person> personsList = FXCollections.observableArrayList();
//why ObservableList?:If you want to show this list in for example tableView or other view 
//then you should use Observable collection
// which contains listeners act and other components necessery for doing interaction with view.

	@FXML private TableView<Person> tableView;
	@FXML private TableColumn<Person, String> firstNameColumn;
	@FXML private TableColumn<Person, String> lastNameColumn;
	@FXML private TableColumn<Person, String> roomColumn;
	@FXML private TextField firstNameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private TextField roomTextField;
	@FXML private TextField workStartHourTextField;
	@FXML private TextField workEndHourTextField;
	@FXML private Button readButton;
	@FXML private Button saveButton;
	@FXML private Button addButton;
	@FXML private Button reportButton;

	public void setMain(Main main) {
		this.main = main;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void ReadPersonList(File file) {
		primaryStage.setTitle(file.getName());
		personsList.clear();
		firstNameTextField.clear();
		lastNameTextField.clear();
		workStartHourTextField.clear();
		workEndHourTextField.clear();
		roomTextField.clear();
		// tableView.refresh();
		tableView.setItems(personsList);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			scanner.useDelimiter(" ");//ogranicznik
			// Check if there is another line of input
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				Optional<Person> person = parseLineToPerson(str);
				if (person.isPresent()) {
					personsList.add(person.get());
				}
			}
			scanner.close();

		} catch (IOException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		personsFile = file;
	}

	private Optional<Person> parseLineToPerson(String line) {
		if (line.matches(".*\\s.*\\s.*")) {// any number of whitespace characters, dot, any number of whitespace characters
			//s-dowolny znak białej spacji, tabulacji
			//d - jakakolwiek cyfra [0-9]
			//.*oznacza dowolny znak (poza znakiem nowej linii) powtórzony 0 lub więcej razy(tak jak +),
			Matcher matcher = Pattern.compile("(.*)\\s(.*)\\s(.*)\\s(.*)\\s(.*)").matcher(line);
			if (matcher.find()) {
				Person p = new Person(matcher.group(1), matcher.group(2), matcher.group(3),
						Short.valueOf(matcher.group(4)), Short.valueOf(matcher.group(5)));
				return Optional.of(p);
			}
		}
		return Optional.ofNullable(null);
	}

	private Person findPerson(String firstName, String lastName) {
		for (Person p : personsList) {
			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
				return p;
			}
		}
		return null;
	}

	private void activateAllFields() {
		personsList.clear();

		firstNameTextField.clear();
		firstNameTextField.setEditable(true);
		firstNameTextField.setStyle("-fx-background-color: white;");

		lastNameTextField.clear();
		lastNameTextField.setEditable(true);
		lastNameTextField.setStyle("-fx-background-color: white;");

		workStartHourTextField.clear();
		workStartHourTextField.setEditable(true);
		workStartHourTextField.setStyle("-fx-background-color: white;");

		workEndHourTextField.clear();
		workEndHourTextField.setEditable(true);
		workEndHourTextField.setStyle("-fx-background-color: white;");

		roomTextField.clear();
		roomTextField.setEditable(true);
		roomTextField.setStyle("-fx-background-color: white;");

		saveButton.setDisable(false);
		addButton.setDisable(false);
		reportButton.setDisable(false);
	}

	private void deactivateAllFields() {
		personsList.clear();

		firstNameTextField.clear();
		firstNameTextField.setEditable(false);
		firstNameTextField.setStyle("-fx-background-color: #B8D0E2;");

		lastNameTextField.clear();
		lastNameTextField.setEditable(false);
		lastNameTextField.setStyle("-fx-background-color:  #B8D0E2;");

		workStartHourTextField.clear();
		workStartHourTextField.setEditable(false);
		workStartHourTextField.setStyle("-fx-background-color: #B8D0E2;");

		workEndHourTextField.clear();
		workEndHourTextField.setEditable(false);
		workEndHourTextField.setStyle("-fx-background-color:  #B8D0E2;");

		roomTextField.clear();
		roomTextField.setEditable(false);
		roomTextField.setStyle("-fx-background-color:  #B8D0E2;");

		saveButton.setDisable(true);
		addButton.setDisable(true);
		reportButton.setDisable(true);
	}

	@FXML
	public void initialize() {
		roomTextField.setPromptText(" Numery od " + RoomNumbers.MIN_ROOM_NUMBER + " do " + RoomNumbers.MAX_ROOM_NUMBER);
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("room"));

		deactivateAllFields();
		tableView.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			firstNameTextField.setText(newVal.getFirstName());
			lastNameTextField.setText(newVal.getLastName());
			roomTextField.setText(newVal.getRoom());
			Person person = findPerson(newVal.getFirstName(), newVal.getLastName());
			if (person != null) {
				workStartHourTextField.setText(String.valueOf(person.getWorkStartHour()));
				workEndHourTextField.setText(String.valueOf(person.getWorkEndHour()));
			}
		});

		personsList.addListener(new ListChangeListener<Person>() {//listener(on Observable List) is notified when items are changed.

			@Override
			public void onChanged(Change<? extends Person> arg0) {
				tableView.setItems(personsList);
				// tableView.refresh();
			}
		});
	}

	@FXML
	public void readFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files only", "*.txt"));
		File selectedTxtFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedTxtFile != null) {
			activateAllFields();
			ReadPersonList(selectedTxtFile);
			tableView.setItems(personsList);
		}
	}

	@FXML
	public void saveFile() {
		// personsFile
		saveFile(personsList, personsFile);
	}

	@FXML
	public void reportFile() {
		File selectedTxtFile = null;
		try {
			if (personsList == null || personsList.size() == 0) {
				showAlert("No data entered", "Empty list can't be saved and reported.");
				return;
			}
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files only", "*.txt"));
			selectedTxtFile = fileChooser.showSaveDialog(primaryStage);
			if (selectedTxtFile != null) {
				List<Person> personSortedList = new ArrayList<Person>();
				// Collections.copy(personSortedList, personList);
				personSortedList.addAll(personsList);
				Collections.sort(personSortedList);
				saveFile(personSortedList, selectedTxtFile);
			}
		} catch (Exception e) {
			showAlert("Result:",
					"Folder: " + selectedTxtFile.getAbsolutePath() + "\n not save (" + e.getMessage() + ")");
			e.printStackTrace();
		}
	}

	private void saveFile(List listToSave, File file) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(file);
			for (int i = 0; i < listToSave.size(); i++) {
				printWriter.println(listToSave.get(i));
			}
			printWriter.flush();
			showInfo("Result:", "Folder: " + file.getAbsolutePath() + "\n saved");
		} catch (FileNotFoundException e) {
			showAlert("Result", "Folder: " + file.getAbsolutePath() + "\n not saved (" + e.getMessage() + ")");
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

	}

	private void checkNumberFormat(TextField field) {
		String fieldText = field.getText();
		if (!fieldText.matches("\\d*")) { //d - jakakolwiek cyfra [0-9]
			field.setText(fieldText.replaceAll("[^\\d]", "")); // jakakolwiek cyfra z poza zakresu [0-9]
		}
	}

	@FXML
	public void checkRoomFormat() {
		checkNumberFormat(roomTextField);
	}

	@FXML
	public void checkHourFromFormat() {
		checkNumberFormat(workStartHourTextField);
	}

	@FXML
	public void checkHourToFormat() {
		checkNumberFormat(workEndHourTextField);
	}

	@FXML
	public void addNewPerson() {
		boolean proceed = true;
		if (findPerson(firstNameTextField.getText(), lastNameTextField.getText()) != null) {
			showAlert("Repeated", "Such employee already exist on the list.");
			return;
		}

		if (!RoomNumbers.verifyRoomNumber(roomTextField.getText())) {
			proceed = false;
			showAlert("Wrong data entered", "Wrong room number.");
		}

		if (!verifyWorkingHours(workStartHourTextField.getText(), workEndHourTextField.getText())) {
			proceed = false;
			showAlert("Wrong data entered", "Wrong working hours.");
		}

		if (proceed) {
			personsList.add(new Person(firstNameTextField.getText(), lastNameTextField.getText(),
					roomTextField.getText(), 1, 2));
		}
	}

	private boolean verifyWorkingHours(String from, String to) {
		String hourRegexpStr = "[0-1]?[0-9]|2[0-3]";// ? - element przed ? jest opcjonalny
		if (!from.matches("\\d*") || !to.matches("\\d*")) {//d - jakakolwiek cyfra [0-9]
			return false;
		}
		if (!from.matches(hourRegexpStr) || !to.matches(hourRegexpStr)) {
			return false;
		}
		int startHour = Integer.valueOf(from);
		int endHour = Integer.valueOf(to);
		if (startHour >= endHour) {
			return false;
		}
		return true;
	}

	private void showAlert(String AlertTitle, String alertText) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(AlertTitle);
		errorAlert.setContentText(alertText);
		errorAlert.showAndWait();
	}

	private void showInfo(String infoTitle, String infoText) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setHeaderText(infoTitle);
		infoAlert.setContentText(infoText);
		infoAlert.showAndWait();
	}

}
