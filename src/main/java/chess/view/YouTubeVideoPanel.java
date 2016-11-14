package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class YouTubeVideoPanel extends JPanel {

	protected static WebView webview;

	public YouTubeVideoPanel() {
		this.setLayout(new BorderLayout());
		
		//Adding the video to the dialog box
		JFXPanel web = new JFXPanel();
		web.setPreferredSize(new Dimension(800, 450));
		this.add(web, BorderLayout.CENTER);
		Platform.runLater(() -> {
			webview = new WebView();
			web.setScene(new Scene(webview));
			webview.getEngine().load("http://www.dailymotion.com/embed/video/xsdji?autoPlay=1");
		});	
	}
	
	public void exit() {
		Platform.runLater(() -> {
			webview.getEngine().getLoadWorker().cancel();
		});	
	}
}