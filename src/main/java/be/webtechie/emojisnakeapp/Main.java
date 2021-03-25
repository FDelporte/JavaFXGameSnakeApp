package be.webtechie.emojisnakeapp;

import be.webtechie.emojisnakeapp.game.SnakeGameApp;
import com.almasb.fxgl.app.GameApplication;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.magnetometer.MagnetometerReading;
import com.gluonhq.attach.magnetometer.MagnetometerService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Dimension2D;

public class Main extends MobileApplication {

    private int screenWidth = 1920;
    private int screenHeight = 1080;

    @Override
    public void init() {
        // https://docs.gluonhq.com/attach/javadoc/latest/com.gluonhq.attach.display/com/gluonhq/attach/display/DisplayService.html
        DisplayService.create().ifPresent(service -> {
            Dimension2D resolution = service.getScreenResolution();
            screenWidth = (int) resolution.getWidth();
            screenHeight = (int) resolution.getHeight();
            System.out.print("Screen resolution: " + screenWidth + "*" + screenHeight);
        });

        // https://docs.gluonhq.com/attach/javadoc/latest/com.gluonhq.attach.magnetometer/com/gluonhq/attach/magnetometer/MagnetometerService.html
        MagnetometerService.create().ifPresent(service -> {
            service.start();
            MagnetometerReading reading = service.getReading();
            System.out.printf("Magnetic field: %.4f, %.4f, %.4f. Magnitude: %.4f",
                    reading.getX(), reading.getY(), reading.getZ(), reading.getMagnitude());
        });

        addViewFactory(HOME_VIEW, () -> {
            // TODO: pass width and height to app to configure play screen
            // var fxglRoot = GameApplication.embeddedLaunch(new SnakeGameApp(screenWidth, screenHeight));
            var fxglRoot = GameApplication.embeddedLaunch(new SnakeGameApp());
            fxglRoot.widthProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() < 700) {
                    // fxglRoot.setRenderWidth(newValue.doubleValue());
                }
            });

            View view = new View(fxglRoot) {
                @Override
                protected void updateAppBar(AppBar appBar) {
                    appBar.setVisible(false);
                }
            };

            return view;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
