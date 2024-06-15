package gui;
import System.*;

import com.gluonhq.maps.MapPoint;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.security.spec.ECField;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button maintain;
    @FXML
    public Text loginInfo;
    @FXML
    public Label phoneNumberLabel;
    @FXML
    public TextField phoneNumberInput;
    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView imageView;
    public static FXMLLoader mapLoader;
    public static Parent mapRoot;
    public static Scene mapScene;
    private Stage stage;
    public static MapController mapC;
    public void setMapInit(FXMLLoader f, Parent p, MapController c){
        LoginController.mapLoader = f;
        LoginController.mapRoot = p;
        LoginController.mapC = c;
        LoginController.mapScene = new Scene(mapRoot);
    }
    @FXML
    public void handleLogin(ActionEvent event){
        try {
            Iterator iterator = new Iterator();
            boolean isLogIn = iterator.getCustomerSystem().login(phoneNumberInput.getText(), passwordField.getText(), Main.customerList);
            if (isLogIn) {
                FadeTransition fade = new FadeTransition();
                fade.setNode(stackPane);
                fade.setDuration(Duration.millis(500));
                fade.setCycleCount(1);
                fade.setInterpolator(Interpolator.LINEAR);
                fade.setFromValue(1);
                fade.setToValue(0);
                fade.setOnFinished(ev -> {
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(mapScene);
                    stage.setResizable(false);
                    stage.setTitle("Map Screen");
                    stage.show();
                    mapC.map.flyTo(0, new MapPoint(mapC.latList.get(0), mapC.lonList.get(0)), 0.1);
                    mapC.anchorPane.setOpacity(0);
                    mapC.phoneNumber = phoneNumberInput.getText();

                    FadeTransition mpfade = new FadeTransition();
                    mpfade.setNode(mapC.anchorPane);
                    mpfade.setDuration(Duration.millis(500));
                    mpfade.setCycleCount(1);
                    mpfade.setInterpolator(Interpolator.LINEAR);
                    mpfade.setFromValue(0);
                    mpfade.setToValue(1);
                    mpfade.play();
                });
                fade.play();
            }else{
                loginInfo.setText("登入失敗");  //depend on what is happening
                loginInfo.setVisible(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void handleRegister(ActionEvent event) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(stackPane);
        fade.setDuration(Duration.millis(500));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(ev -> {
            setRegisterScreen(event);
        });
        fade.play();
    }
    public void setRegisterScreen(ActionEvent event){
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/gui/RegisterScreen.fxml"));
            Parent registerRoot = registerLoader.load();
            Scene registerScene = new Scene(registerRoot);
            stage.setScene(registerScene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginInfo.setVisible(false);
        stackPane.setOpacity(0);
        FadeTransition fade = new FadeTransition();
        fade.setNode(stackPane);
        fade.setDuration(Duration.millis(500));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}