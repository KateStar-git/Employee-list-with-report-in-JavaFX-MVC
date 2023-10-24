module InterfaceHomeWork3 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens controller to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
}
