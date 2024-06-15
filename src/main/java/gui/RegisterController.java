package gui;

import System.*;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public HBox hBox8;
    public Text registerInfo;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TextField phoneNumberInput;
    @FXML
    private Label accountLabel21;
    @FXML
    private TextField idInput;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailInput;
    @FXML
    private Label easycardLabel;
    @FXML
    private TextField easycardInput;
    @FXML
    private HBox hBox5;
    @FXML
    private HBox hBox6;
    @FXML
    private HBox hBox7;

    @FXML
    private HBox hBox1;

    @FXML
    private HBox hBox2;

    @FXML
    private HBox hBox3;

    @FXML
    private HBox hBox4;

    @FXML
    private ImageView imageView;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput1;

    @FXML
    private PasswordField passwordInput2;

    @FXML
    private Label passwordLabel1;

    @FXML
    private Label passwordLabel2;

    @FXML
    private Rectangle rect;

    @FXML
    private Button registerButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;
    private Stage stage;

    @FXML
    void handleRegister(ActionEvent event) {
        Easycard easycard = new Easycard(easycardInput.getText());
        Main.allEasycard.add(easycard);
        Iterator Iterator = new Iterator();
        Iterator.getCustomerSystem().register(
                phoneNumberInput.getText(),
                passwordInput1.getText(),
                passwordInput2.getText(),
                idInput.getText(),
                emailInput.getText(),
                easycardInput.getText(),
                Main.allEasycard,
                Main.PhoneNumbeofCustomer,
                Main.customerList,
                Main.idNumberofCustomer);

        if (true){
            FadeTransition fade = new FadeTransition();
            fade.setNode(stackPane);
            fade.setDuration(Duration.millis(500));
            fade.setCycleCount(1);
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(ev -> {
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(LoginController.mapScene);
                //mapC.flyTo(, 0);
                stage.setResizable(false);
                stage.setTitle("Map Screen");
                stage.show();
                LoginController.mapC.anchorPane.setOpacity(0);
                LoginController.mapC.phoneNumber = phoneNumberInput.getText();

                FadeTransition mpfade = new FadeTransition();
                mpfade.setNode(LoginController.mapC.anchorPane);
                mpfade.setDuration(Duration.millis(500));
                mpfade.setCycleCount(1);
                mpfade.setInterpolator(Interpolator.LINEAR);
                mpfade.setFromValue(0);
                mpfade.setToValue(1);
                mpfade.play();
            });
            fade.play();
        }else{
            registerInfo.setText(""); //depend on what is happening
            registerInfo.setVisible(true);
        }
    }

    @FXML
    void handleSwitchLogin(ActionEvent event) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(stackPane);
        fade.setDuration(Duration.millis(500));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(ev -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
                Parent loginRoot = loginLoader.load();
                LoginController loginC = loginLoader.getController();
                loginC.loginInfo.setVisible(false);
                Scene registerScene = new Scene(loginRoot);
                registerScene.addEventFilter(KeyEvent.KEY_PRESSED, eve -> {
                    if (eve.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    }
                });
                stage.setScene(registerScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        fade.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerInfo.setVisible(false);
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