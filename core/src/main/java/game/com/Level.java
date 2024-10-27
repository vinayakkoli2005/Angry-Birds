package game.com;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Level implements Screen {
    protected ImageButton resumeButton, restartButton, save_exitButton;

    private Texture resumeTexture;
    private Texture retryTexture;
    private Texture saveExitTexture;

    protected void createPauseMenu(Stage stage) {
        resumeTexture = new Texture("images/RESUME.png");
        retryTexture = new Texture("images/RETRY.png");
        saveExitTexture = new Texture("images/save.png");

        resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        restartButton = new ImageButton(new TextureRegionDrawable(retryTexture));
        save_exitButton = new ImageButton(new TextureRegionDrawable(saveExitTexture));

        float buttonWidth = 200f;
        float buttonHeight = 80f;
        float centerX = (stage.getViewport().getWorldWidth() - buttonWidth) / 2;
        float centerY = stage.getViewport().getWorldHeight() / 2;

        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(centerX, centerY + 100);

        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(centerX, centerY);

        save_exitButton.setSize(buttonWidth, buttonHeight);
        save_exitButton.setPosition(centerX, centerY - 100);


        resumeButton.setVisible(false);
        restartButton.setVisible(false);
        save_exitButton.setVisible(false);

        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(save_exitButton);
    }

    protected void togglePauseMenu(boolean show) {
        resumeButton.setVisible(show);
        restartButton.setVisible(show);
        save_exitButton.setVisible(show);
    }

    protected void disposePauseMenu() {
        resumeTexture.dispose();
        retryTexture.dispose();
        saveExitTexture.dispose();
    }
}
