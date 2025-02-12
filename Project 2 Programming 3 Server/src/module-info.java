module Test {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	
	opens business to javafx.graphics, javafx.fxml;
}
