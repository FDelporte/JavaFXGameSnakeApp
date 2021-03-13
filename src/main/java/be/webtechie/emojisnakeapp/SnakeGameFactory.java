package be.webtechie.emojisnakeapp;

import be.webtechie.emojisnakeapp.component.SnakeHeadComponent;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;

/**
 * The factory which defines how each entity looks like
 */
public class SnakeGameFactory implements EntityFactory {

    /**
     * Types of objects we are going to use in our game.
     */
    public enum EntityType {
        SNAKE_HEAD, SNAKE_BODY
    }

    // TODO: safe actions plugin
    private List<String> textureNames = List.of(
            "angry.png",
            "cool.png",
            "crying.png",
            "dead.png",
            "emoji.png",
            "greed.png",
            "happy.png",
            "hypnotized.png",
            "in-love.png",
            "laughing.png",
            "pressure.png",
            "smile.png",
            "wink.png"
    );

    @Spawns("snakeHead")
    public Entity newSnakeHead(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SNAKE_HEAD)
                .viewWithBBox(texture(FXGLMath.random(textureNames).get(), 32, 32))
                .collidable()
                .with(new AutoRotationComponent())
                .with(new SnakeHeadComponent())
                .build();
    }

    @Spawns("snakeBody")
    public Entity newSnakeBody(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SNAKE_BODY)
                .viewWithBBox(texture(FXGLMath.random(textureNames).get(), 32, 32))
                .collidable()
                .with(new AutoRotationComponent())
                .build();
    }
}
