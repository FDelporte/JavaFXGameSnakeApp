package be.webtechie.emojisnakeapp;

import be.webtechie.emojisnakeapp.game.SnakeGameApp;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.logging.Logger;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.magnetometer.MagnetometerReading;
import com.gluonhq.attach.magnetometer.MagnetometerService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.application.Application;
import javafx.geometry.Dimension2D;

public class Main extends MobileApplication {

    private static final Logger log = Logger.get(Main.class);

    private int screenWidth = 1920;
    private int screenHeight = 1080;

    @Override
    public void init() {
        // https://docs.gluonhq.com/attach/javadoc/latest/com.gluonhq.attach.display/com/gluonhq/attach/display/DisplayService.html
        DisplayService.create().ifPresent(service -> {
            Dimension2D resolution = service.getScreenResolution();
            screenWidth = (int) resolution.getWidth();
            screenHeight = (int) resolution.getHeight();

            log.info("Screen resolution: " + screenWidth + "*" + screenHeight);
        });

        // https://docs.gluonhq.com/attach/javadoc/latest/com.gluonhq.attach.magnetometer/com/gluonhq/attach/magnetometer/MagnetometerService.html
        MagnetometerService.create().ifPresent(service -> {
            service.start();
            MagnetometerReading reading = service.getReading();

            log.infof("Magnetic field: %.4f, %.4f, %.4f. Magnitude: %.4f",
                    reading.getX(), reading.getY(), reading.getZ(), reading.getMagnitude());
        });

        addViewFactory(HOME_VIEW, () -> {
            // TODO: pass width and height to app to configure play screen
            // var fxglRoot = GameApplication.embeddedLaunch(new SnakeGameApp(screenWidth, screenHeight));
            var fxglPane = GameApplication.embeddedLaunch(new SnakeGameApp());
            fxglPane.setRenderWidth(screenWidth);
            fxglPane.setRenderHeight(screenHeight);

            View view = new View(fxglPane) {
                @Override
                protected void updateAppBar(AppBar appBar) {
                    appBar.setVisible(false);
                }
            };

            view.setPrefSize(screenWidth, screenHeight);

            return view;
        });
    }

    @Override
    public void stop() throws Exception {
        FXGL.getGameController().exit();
    }

    public static class Launcher {
        public static void main(String[] args) {
            Main.start(args);
        }
    }

    public static void start(String[] args) {
        launch(args);
    }
}
