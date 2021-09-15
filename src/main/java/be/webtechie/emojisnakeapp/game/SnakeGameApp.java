package be.webtechie.emojisnakeapp.game;

import be.webtechie.emojisnakeapp.component.SnakeHeadComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static be.webtechie.emojisnakeapp.game.SnakeGameFactory.EntityType.*;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class SnakeGameApp extends GameApplication {

    private static final int CELL_SIZE = 32;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 17;

    /**
     * Reference to the factory which will defines how all the types must be created.
     */
    private final SnakeGameFactory snakeGameFactory = new SnakeGameFactory();

    /**
     * Player object we are going to use to provide to the factory so it can start a bullet from the player center.
     */
    private Entity player;

    /**
     * General game settings. For now only the title is set, but a longer list of options is available.
     *
     * @param settings The settings of the game which can be further extended here.
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(CELL_SIZE * GRID_WIDTH);
        settings.setHeight(CELL_SIZE * GRID_HEIGHT);
        settings.setTitle("Emoji Snake Game");
        settings.setTicksPerSecond(10);
    }

    /**
     * General game variables. Used to hold the points and lives.
     *
     * @param vars The variables of the game which can be further extended here.
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("lives", 5);
    }

    @Override
    protected void initUI() {
        Text scoreLabel = getUIFactoryService().newText("Score", Color.BLACK, 22);
        Text scoreValue = getUIFactoryService().newText("", Color.BLACK, 22);
        Text livesLabel = getUIFactoryService().newText("Lives", Color.BLACK, 22);
        Text livesValue = getUIFactoryService().newText("", Color.BLACK, 22);

        scoreLabel.setTranslateX(20);
        scoreLabel.setTranslateY(20);

        scoreValue.setTranslateX(90);
        scoreValue.setTranslateY(20);

        livesLabel.setTranslateX(getAppWidth() - 100);
        livesLabel.setTranslateY(20);

        livesValue.setTranslateX(getAppWidth() - 30);
        livesValue.setTranslateY(20);

        scoreValue.textProperty().bind(getWorldProperties().intProperty("score").asString());
        livesValue.textProperty().bind(getWorldProperties().intProperty("lives").asString());

        var dpad = getInput().createVirtualDpadView();
        dpad.setTranslateX(25);
        dpad.setTranslateY(getAppHeight() - 280);
        // Only for desktop testing
        //dpad.setScaleX(0.5);
        //dpad.setScaleY(0.5);

        getGameScene().addUINodes(scoreLabel, scoreValue, livesLabel, livesValue, dpad);
    }

    /**
     * Input configuration, here you configure all the input events like key presses, mouse clicks, etc.
     */
    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("UP") {
            @Override
            protected void onActionBegin() {
                player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                        SnakeHeadComponent::up);
            }
        }, KeyCode.UP, VirtualButton.UP);


        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onActionBegin() {
                player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                        SnakeHeadComponent::down);
            }
        }, KeyCode.DOWN, VirtualButton.DOWN);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onActionBegin() {
                player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                        SnakeHeadComponent::left);
            }
        }, KeyCode.LEFT, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onActionBegin() {
                player.getComponentOptional(SnakeHeadComponent.class).ifPresent(
                        SnakeHeadComponent::right);
            }
        }, KeyCode.RIGHT, VirtualButton.RIGHT);

        onKeyDown(KeyCode.F, () -> {
            player.getComponent(SnakeHeadComponent.class).grow();
        });

        onKeyDown(KeyCode.G, () -> {
            player.getComponent(SnakeHeadComponent.class).log();
        });
    }

    /**
     * Initialization of the game by providing the {@link EntityFactory}.
     */
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(this.snakeGameFactory);

        // Add the player
        this.player = spawn("snakeHead", 0, 0);

        spawnFood();
        spawnTrap();
    }

    @Override
    protected void onUpdate(double tpf) {
        var food = getGameWorld().getSingleton(FOOD);
        var trap = getGameWorld().getSingleton(TRAP);

        if (player.getX() == food.getX() && player.getY() == food.getY()) {
            food.removeFromWorld();
            trap.removeFromWorld();
            player.getComponent(SnakeHeadComponent.class).grow();
            spawnFood();
            spawnTrap();
        }

        if (player.getX() == trap.getX() && player.getY() == trap.getY()) {
            food.removeFromWorld();
            trap.removeFromWorld();
            player.getComponent(SnakeHeadComponent.class).die();
            spawnFood();
            spawnTrap();
        }
    }

    public void spawnFood() {
        var pos = getFreePosition();
        spawn("food", pos.getX(), pos.getY());
    }

    public void spawnTrap() {
        var pos = getFreePosition();
        spawn("trap", pos.getX(), pos.getY());
    }

    /**
     * Find an X/Y position which is not use by any of the snake elements or food or trap
     * @return
     */
    private Point2D getFreePosition() {
        var posX = 0;
        var posY = 0;

        var isBad = true;

        while (isBad) {
            posX = FXGLMath.random(0, GRID_WIDTH - 1) * CELL_SIZE;
            posY = FXGLMath.random(0, GRID_HEIGHT - 1) * CELL_SIZE;

            int pX = posX;
            int pY = posY;

            isBad = byType(SNAKE_HEAD, SNAKE_BODY, FOOD, TRAP)
                    .stream()
                    .anyMatch(e -> (int) e.getX() == pX && (int) e.getY() == pY);
        }

        return new Point2D(posX, posY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
