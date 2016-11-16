package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.BorderPane;

public class YouTubeVideoPanel extends JPanel {

	protected static MediaPlayer player;

	public YouTubeVideoPanel() {
		this.setLayout(new BorderLayout());

		//Adding the video to the dialog box
		Platform.setImplicitExit(false);
		JFXPanel web = new JFXPanel();

		String url = this.getClass().getResource("/rickroll.mp4").toExternalForm();
		player = new MediaPlayer(new Media(url));
		player.setAutoPlay(true);

		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView();
		mediaView.setFitHeight(450);	//Sets video height
		mediaView.setMediaPlayer(player);
		BorderPane pane = new BorderPane();
    pane.setCenter(mediaView);
		Scene scene = new Scene(pane, Color.BLACK);
		web.setScene(scene);

		this.add(web, BorderLayout.CENTER);
	}

	public void exit() {
		if(player != null) {
			player.stop();
			player.dispose();
		}
	}
}
