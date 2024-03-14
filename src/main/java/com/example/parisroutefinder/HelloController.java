package com.example.parisroutefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class HelloController {
    @FXML
    private Label welcomeText;
    FileInputStream input;

    {
        try {
            input = new FileInputStream("src/main/resources/Image/gimp.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Image parisMap = new Image(input);
    @FXML
    public ImageView imageView;

    public void initialize(){
        imageView.setImage(parisMap);
    }

}