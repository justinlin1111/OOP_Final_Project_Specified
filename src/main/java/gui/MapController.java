package gui;

import System.*;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import System.*;

public class MapController implements Initializable {
    public ImageView imageView;
    public Rectangle rect;
    public Button logoutButton;
    public AnchorPane anchorPane;
    public HBox hBox1;
    public Button rentBike;
    public Button returnBike;
    public HBox hBox2;
    @FXML
    private ChoiceBox<String> locationChoice;
    @FXML
    VBox mapSpace;
    public MapView map;
    @FXML
    private Text costInfo;
    public String selecting;
    public String phoneNumber;

//    private final MapPoint ntu = new MapPoint(25.0173453, 121.5371769);
//    private final MapPoint ntuMainGate = new MapPoint(25.017144107866756, 121.5340181272698);

    public ArrayList<Mplayer> mapLayers = new ArrayList<>();
    public ArrayList<Mplayer> toRemove = new ArrayList<>();

    public ArrayList<Double> latList = new ArrayList<Double>();
    public ArrayList<Double> lonList = new ArrayList<Double>();
    public ArrayList<String> idList = new ArrayList<String>();

    private Point2D pressPoint;
    public void flyTo(MapPoint pt, double time){
        map.flyTo(0, pt, time);
    }
    public void mapPointsInit(){
        //add markers
        for (int i = 0; i < latList.size(); i++){
            mapLayers.add(new Mplayer(map, new MapPoint(latList.get(i), lonList.get(i)), idList.get(i)));
        }
        //load markers
        for (Mplayer mapLayer : mapLayers) {
            map.addLayer(mapLayer);
            toRemove.add(mapLayer);
        }

    }
    public void refreshLayers(){
        for (Mplayer mapLayer : toRemove){
            map.removeLayer(mapLayer);
        }
        toRemove.clear();
        for (int i = 0; i < latList.size(); i++){
            mapLayers.add(new Mplayer(map, new MapPoint(latList.get(i), lonList.get(i)), idList.get(i)));
        }
        //load markers
        for (Mplayer mapLayer : mapLayers) {
            map.addLayer(mapLayer);
            toRemove.add(mapLayer);
        }
    }


    public void addMap() {
        System.out.println("hi");
        map = new MapView();
        map.setPrefSize(800, 600);
        map.setZoom(15);
        mapPointsInit();
        map.flyTo(0, new MapPoint(latList.get(0), lonList.get(0)), 0.1);



        mapSpace.getChildren().addAll(map);


        map.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            pressPoint = new Point2D(event.getSceneX(), event.getSceneY());
        });

        map.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            Point2D releasePoint = new Point2D(event.getSceneX(), event.getSceneY());
            if (!(pressPoint != null && !pressPoint.equals(releasePoint))) {
                for (Mplayer mapLayer : mapLayers){
                    mapLayer.hideTooltip();
                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Station station : Main.allStation) {
            latList.add(station.getPositionLat());
            lonList.add(station.getPositionLon());
            idList.add(station.getStationName());
        }

        locationChoice.getItems().addAll(idList);
        locationChoice.setOnAction(this::getLocation);
    }
    public void getLocation(ActionEvent event){
        String select = locationChoice.getValue();
        selecting = select;
        int index = idList.indexOf(select);
        flyTo(new MapPoint(latList.get(index), lonList.get(index)), 0.5);
    }

    private class Mplayer extends MapLayer {
        private final Node marker;
        private final Tooltip tooltip;
        private String line2;
        private String line3;
        private final MapView map;
        private final MapPoint mapPoint;

        private String markerId;
        public String getMarkerId(){
            return this.markerId;
        }
        public MapPoint getMapPoint(){
            System.out.println(this.mapPoint.getLatitude());
            System.out.println(this.mapPoint.getLongitude());
            return new MapPoint(this.mapPoint.getLatitude(), this.mapPoint.getLongitude());
        }

        public Mplayer(MapView map, MapPoint mapPoint, String tooltipText) {
//            System.out.println(getMarkerId() + "123");
            Iterator iterator = new Iterator();
            this.map = map;
            this.mapPoint = mapPoint;
            this.markerId = tooltipText;
            this.line2 = String.valueOf(iterator.getMaintainer().nameGetStation(Main.allStation, getMarkerId()).getBikesNumber());
            this.line3 = String.valueOf(iterator.getMaintainer().nameGetStation(Main.allStation, getMarkerId()).getBikesCapacity()-iterator.getMaintainer().nameGetStation(Main.allStation, getMarkerId()).getBikesNumber());
            this.tooltip = new Tooltip(tooltipText + "\n" + line2 + "\n" + line3);
            this.marker = createMarkerShape();

            Tooltip.install(marker, tooltip);
            getChildren().add(marker);
            setupMarkerClickHandler();


            map.setOnScroll(event -> updateTooltipPosition());
            map.setOnMouseReleased(event -> updateTooltipPosition());
        }

        protected void layoutLayer() {
            Point2D point = getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
            marker.setTranslateX(point.getX() - marker.getBoundsInLocal().getWidth() / 2);
            marker.setTranslateY(point.getY() - marker.getBoundsInLocal().getHeight());
            updateTooltipPosition();
        }

        private Node createMarkerShape() {
            SVGPath svgPath = new SVGPath();
            svgPath.setContent("M12 2C8.1 2 5 5.1 5 9c0 5.2 7 13 7 13s7-7.8 7-13c0-3.9-3.1-7-7-7zm0 9.5c-1.4 0-2.5-1.1-2.5-2.5S10.6 6.5 12 6.5 14.5 7.6 14.5 9 13.4 11.5 12 11.5z");
            svgPath.setFill(Color.RED);
            svgPath.setScaleX(1.5);
            svgPath.setScaleY(1.5);
            return svgPath;
        }

        private void setupMarkerClickHandler() {
            marker.setOnMouseClicked((MouseEvent event) -> {
                Bounds bounds = marker.localToScreen(marker.getBoundsInLocal());
                tooltip.show(marker, bounds.getMinX(), bounds.getMinY() - tooltip.getHeight());
                updateTooltipPosition();
                event.consume(); // Consume the event to prevent it from propagating to the map click listener
            });
        }

        private void updateTooltipPosition() {
            if (tooltip.isShowing()) {
                Bounds bounds = marker.localToScreen(marker.getBoundsInLocal());
                tooltip.setAnchorX(bounds.getMinX());
                tooltip.setAnchorY(bounds.getMinY() - tooltip.getHeight());
            }
        }

        public void hideTooltip() {
            tooltip.hide();
        }
    }
    @FXML
    public void logoutHandle(ActionEvent event){
        FadeTransition fade = new FadeTransition();
        fade.setNode(anchorPane);
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
    @FXML
    public void rentBike(ActionEvent event){
        System.out.println(phoneNumber);
        System.out.println(Main.customerList);
        Iterator iterator = new Iterator();
        iterator.getCustomerSystem().topUpEasyCard(10000000, "111", Main.allEasycard);
        iterator.getCustomerSystem().RentBike(
                iterator.getMaintainer().nameGetStation(Main.allStation, selecting).getStationUID(),
                iterator.getCustomerSystem().phoneGetCustomer(phoneNumber, Main.customerList).getEasycard().get(0).getNumber(),
                Main.allEasycard,
                Main.allStation,
                Main.allRentalRecordForEasycard,
                Main.allRentalRecordForCustomer);
        refreshLayers();
    }
    @FXML
    public void returnBike(ActionEvent event){
        System.out.println(phoneNumber);
        Iterator iterator = new Iterator();
        iterator.getCustomerSystem().ReturnBike(
                iterator.getMaintainer().nameGetStation(Main.allStation, selecting).getStationUID(),
                iterator.getCustomerSystem().phoneGetCustomer(phoneNumber, Main.customerList).getusingEasycard().getNumber(),
                Main.allEasycard,
                Main.allStation,
                Main.allRentalRecordForEasycard,
                Main.allRentalRecordForCustomer);
        costInfo.setText(""); //set cost
        costInfo.setVisible(true);
        refreshLayers();
    }
}