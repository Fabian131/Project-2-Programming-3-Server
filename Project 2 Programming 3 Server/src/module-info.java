module Test {
	requires javafx.controls;
	requires java.sql;
	
	opens business to javafx.graphics, javafx.fxml;
}
