package view;


import controller.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Guest;
import model.GuestDao;

public class MainWindow {

	private BorderPane bpRoot;
	private Scene scene;
	private Stage primaryStage;
	
	private GridPane gpWindowElements;
	
	private TextField  tfName;
	private TextField  tfSurname;
	private TextField  tfRoomNumber;
	private TextField  tfID;		
	
	public MainWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.bpRoot = new BorderPane();
		scene = new Scene(this.bpRoot,800,440);
		//CSS for some styling
		scene.getStylesheets().add(getClass().getResource("../view/style.css").toExternalForm());
		
		this.primaryStage=primaryStage;
		
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("Hotel Registration Window");
		this.primaryStage.setResizable(false);
		this.primaryStage.centerOnScreen();
		this.primaryStage.show();
		
		//MainWindow layout elements
		
		Label lblName = new Label ("Name: ");
		Label lblSurname = new Label ("Surname: ");
		Label lblRoomNumber = new Label ("Room Number: ");
		Label lblID = new Label ("ID: ");
		
		tfName = new TextField();
		tfSurname = new TextField();
		tfRoomNumber = new TextField();
		tfID = new TextField();
		
		Button btnRegister = new Button("Register");
		btnRegister.setMinWidth(120);
		Button btnShowBkdRms = new Button("Show Booked Rooms");
		btnShowBkdRms.setMinWidth(120);
		Button btnShowRmHistory = new Button("Show Room History");
		btnShowRmHistory.setMinWidth(120);
		Button btnCancelRegistration = new Button("Cancel Registration");
		btnCancelRegistration.setMinWidth(120);
		Button btnShowAllRooms = new Button("Show All Rooms");
		btnShowAllRooms.setMinWidth(120);
		
		GridPane gpButtons = new GridPane();
		gpButtons.setHgap(10);
		gpButtons.add(btnRegister, 0, 0);
		gpButtons.add(btnCancelRegistration, 1, 0);	
		gpButtons.add(btnShowRmHistory, 2, 0);
		gpButtons.add(btnShowBkdRms, 3, 0);
		gpButtons.add(btnShowAllRooms, 4, 0);
		
		gpWindowElements = new GridPane();
		
		gpWindowElements.add(lblName, 0, 0);
		gpWindowElements.add(tfName, 1, 0);
		gpWindowElements.add(lblSurname, 0, 1);
		gpWindowElements.add(tfSurname, 1, 1);
		gpWindowElements.add(lblID, 0, 2);
		gpWindowElements.add(tfID, 1,2);
		gpWindowElements.add(lblRoomNumber, 0, 3);
		gpWindowElements.add(tfRoomNumber, 1, 3);
		gpWindowElements.add(gpButtons, 0, 2);
		gpWindowElements.setPadding(new Insets(10,10,10,50));
		gpWindowElements.setVgap(10);
		gpWindowElements.setHgap(10);
		
		TableView tblGuestTable = new TableView();
		tblGuestTable.setEditable(true);
		TableColumn colName = new TableColumn("name");
		colName.setMinWidth(20);
		TableColumn colSurname = new TableColumn("surname");
		colSurname.setMinWidth(100);
		TableColumn colRoomNumber = new TableColumn("room");
		colRoomNumber.setMinWidth(100);
		TableColumn colIsBooked = new TableColumn("isbooked");
		colRoomNumber.setMinWidth(100);
		TableColumn colId = new TableColumn("id");
		colId.setMinWidth(20);
		
		tblGuestTable.getColumns().addAll(colId,colName,colSurname,colRoomNumber,colIsBooked);
		tblGuestTable.setMaxHeight(400);
		 
		colName.setCellValueFactory(new PropertyValueFactory<Guest,String>("name"));
		colSurname.setCellValueFactory(new PropertyValueFactory<Guest,String>("surname"));
		colRoomNumber.setCellValueFactory(new PropertyValueFactory<Guest,Integer>("room"));
		colIsBooked.setCellValueFactory(new PropertyValueFactory<Guest,Boolean>("isbooked"));
		colId.setCellValueFactory(new PropertyValueFactory<Guest,Boolean>("id"));//INT
		
		ObservableList<Guest> guestInfo = FXCollections.observableArrayList();
		GuestDao guestDao = new GuestDao();
		guestDao.showGuests(guestInfo);	 
		tblGuestTable.setItems(guestInfo);
		
		lblName.setId("lbltext");
		lblSurname .setId("lbltext");
		lblRoomNumber .setId("lbltext");
		lblID.setId("lbltext");
		 
		bpRoot.setId("bpRootBackground");
		bpRoot.setPadding(new Insets(10,10,10,10));
		bpRoot.setLeft(gpWindowElements);
		bpRoot.setRight(tblGuestTable);
		bpRoot.setBottom(gpButtons);
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(guestValidation("Registration"))
					//Checking if there is a guest object in DB that has room that is wanted with value isbooked=true
				{
					if(guestDao.testforBookedRoom(Integer.parseInt(tfRoomNumber.getText().toString())) !=null){ 
						//if guest is found testforBookedRoom returns guestInfo and message is shown to the user
						showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "Room number Error!", "Room is already booked!");	
				} else {  //Lets to add new entry    
				Guest guest = new Guest(
						tfName.getText().toString(),
						tfSurname.getText().toString(),
						Integer.parseInt(tfRoomNumber.getText().toString()),
						true);
						guestDao.addGuest(guest);
						//Clean and add new data to the table view
						tblGuestTable.getItems().clear();
						guestDao.showGuests(guestInfo);
						tblGuestTable.setItems(guestInfo);
						}
				}
			}
	});
		
		btnShowBkdRms.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){	
				tblGuestTable.getItems().clear();
				//Adding Guest that matches isbooked = true;
				tblGuestTable.setItems(guestDao.findEmptyRoom(true));	
				}	
	});
		
		btnShowAllRooms.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){	
				tblGuestTable.getItems().clear();
				guestDao.showGuests(guestInfo);
				tblGuestTable.setItems(guestInfo);	
				}	
	});
		
		btnCancelRegistration.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(guestValidation("cancelRegistration"))
				{
				Guest guestToCancel = new Guest(
						tfName.getText().toString(),
						tfSurname.getText().toString(),
						tfRoomNumber.getLength(),
						false,
						Integer.parseInt(tfID.getText())
						);
						guestDao.cancelRegistration(guestToCancel);	
						tblGuestTable.getItems().clear();			
						guestDao.showGuests(guestInfo);
				}
			}				
	});
		btnShowRmHistory.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(guestValidation("showHistory"))
				{
				tblGuestTable.getItems().clear();
				tblGuestTable.setItems(guestDao.showRoomHistory(Integer.parseInt(tfRoomNumber.getText().toString())));
				}	
			}
		
		});
	}
	
	private boolean guestValidation(String actionToValidate) {
		switch(actionToValidate) {
		case "Registration":
			if(!Validation.isValidNameToRegister(tfName.getText().toString())) {
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "Name Error!", "Please enter guest name only using lowercase or upercase letters, name can't be longer than 20 symbols.");
			return false;
			} else if(!Validation.isValidSurnameToRegister(tfSurname.getText().toString())) {
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "Name Error!", "Please enter guest surname only using lowercase or upercase letters, name can't be longer than 20 symbols.");
			return false;
			} else if(!Validation.isValidRoom(tfRoomNumber.getText().toString())){//Making sure that only rooms 1 to 5 could be added as assignment requires
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "Room number Error!", "Please enter rooms from 1 to 5");
				return false;
			} else if (tfID.getText().toString().length() !=0){//Checking if ID field is empty
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "ID Error!", "ID is only used to cancel registration, leave field empty");
				return false;
			}
			else return true;
			
		case "cancelRegistration":
			
			if(!Validation.isValidID(tfID.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "ID Error!", "Bad ID format. Please enter only numbers");
				return false;
			}
			else return true;
			
		case "showHistory":
			
			if (!Validation.isValidRoom(tfRoomNumber.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpWindowElements.getScene().getWindow(), "Room number Error!", "Please enter room number from 1 to 5");
				return false;
			}
			else return true;
		 }
		
		return true;
	}
	
	private void showAlert(Alert.AlertType alerType, Window owner, String title, String message){
		Alert alert = new Alert(alerType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
		}
	
}
