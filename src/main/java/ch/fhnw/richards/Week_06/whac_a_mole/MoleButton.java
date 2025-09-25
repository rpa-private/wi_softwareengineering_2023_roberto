package ch.fhnw.richards.Week_06.whac_a_mole;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MoleButton extends Button implements Runnable, EventHandler<ActionEvent> {
    final static Image moleIcon = new Image(MoleButton.class.getResourceAsStream("mole.gif"));
    final static Image emptyIcon = new Image(MoleButton.class.getResourceAsStream("empty.gif"));

    private final Whacamole mainProgram;
    private final ImageView moleImage = new ImageView(moleIcon);
    private final ImageView emptyImage = new ImageView(emptyIcon);

    public MoleButton(Whacamole mainProgram) {
        super();
        this.mainProgram = mainProgram;
        this.setGraphic(emptyImage);
        this.setDisable(true);
        this.setOnAction(this);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void handle(ActionEvent event) {
        mainProgram.whack();
    }

    @Override
    public void run() {
        while (true) {
            this.setDisable(Math.random() < 0.7);
            Platform.runLater(() -> {
                if (this.isDisabled()) {
                    this.setGraphic(emptyImage);
                } else {
                    this.setGraphic(moleImage);
                }
            });
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
