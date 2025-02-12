module Test {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens business to javafx.graphics, javafx.fxml;
}
