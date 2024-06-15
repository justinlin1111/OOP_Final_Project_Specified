package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BackgroundImageStretch extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image img = new Image(getClass().getResourceAsStream("gui/loginScreenBackground.png"));

        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(true); // Preserve the aspect ratio
        imageView.setManaged(false); // Allow the ImageView to resize freely

        Pane root = new Pane();
        root.getChildren().add(imageView);

        // Bind the dimensions of the ImageView to the dimensions of the Pane
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            double newHeight = newWidth / img.getWidth() * img.getHeight();
            if (newHeight > root.getHeight()) {
                newHeight = root.getHeight();
                newWidth = newHeight / img.getHeight() * img.getWidth();
            }
            imageView.setFitWidth(newWidth);
            imageView.setFitHeight(newHeight);
            imageView.setLayoutX((root.getWidth() - newWidth) / 2);
            imageView.setLayoutY((root.getHeight() - newHeight) / 2);
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            double newWidth = newHeight / img.getHeight() * img.getWidth();
            if (newWidth > root.getWidth()) {
                newWidth = root.getWidth();
                newHeight = newWidth / img.getWidth() * img.getHeight();
            }
            imageView.setFitWidth(newWidth);
            imageView.setFitHeight(newHeight);
            imageView.setLayoutX((root.getWidth() - newWidth) / 2);
            imageView.setLayoutY((root.getHeight() - newHeight) / 2);
        });

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
}
