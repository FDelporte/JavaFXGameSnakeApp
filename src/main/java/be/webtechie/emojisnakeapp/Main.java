package be.webtechie.emojisnakeapp;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.control.Label;

public class Main extends MobileApplication {

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, () -> {
            FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SEARCH.text,
                    e -> System.out.println("Search"));
            //new SnakeGameApp()
            View view = new View(new Label("Test")) {
                @Override
                protected void updateAppBar(AppBar appBar) {
                    appBar.setTitleText("Emoji Snake");
                }
            };

            fab.showOn(view);

            return view;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
