/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPkg;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author GDO
 */
public class MyMain extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    Scene myScene;
    BorderPane mainBorder;
    MenuBar mainBar;
    Menu fileMenu,editMenu,helpMenu;
    MenuItem itNewFile,itOpenFile,itSaveFile,itExitFile,
            itUndo,itCut,itCopy,itPast,itDelete,itSelectAll,
            itAbout;
    SeparatorMenuItem  spFile,spEdit1,spEdit2;
    Image imgNewFile,imgOpenFile,imgSaveFile;
    ImageView viewNewFile,viewOpenFile,viewSaveFile;
    
    TextArea txtArea;
    Alert a ;
    
    FileChooser fileChooser;
    File selectedFile;
    FileReader dataIn;
    FileWriter dataOut;
    int stateOpenNewFile=0;
    @Override
    public void init(){
        //Main Elements
        mainBorder  = new BorderPane();
        mainBar     = new MenuBar();
        txtArea     = new TextArea();
        
        //Menus
        fileMenu    = new Menu("File");
        editMenu    = new Menu("Edit");
        helpMenu    = new Menu("help");
        
        //File Menu Items
        imgNewFile  = new Image(getClass().getResourceAsStream("newIcon.png"));
        viewNewFile = new ImageView(imgNewFile);
        itNewFile   = new MenuItem("New",viewNewFile);
        
        imgOpenFile = new Image(getClass().getResourceAsStream("openIcon.png"));
        viewOpenFile= new ImageView(imgOpenFile);
        itOpenFile  = new MenuItem("Open",viewOpenFile);
        
        imgSaveFile = new Image(getClass().getResourceAsStream("saveIcon.png"));
        viewSaveFile= new ImageView(imgSaveFile);
        itSaveFile  = new MenuItem("Save",viewSaveFile);
        
        itExitFile  = new MenuItem("Exit");
        spFile      = new SeparatorMenuItem();
        
        itNewFile.setAccelerator(KeyCombination.keyCombination("ctrl+n"));
        itOpenFile.setAccelerator(KeyCombination.keyCombination("ctrl+o"));
        itSaveFile.setAccelerator(KeyCombination.keyCombination("ctrl+s"));
        itExitFile.setAccelerator(KeyCombination.keyCombination("ctrl+t"));
        fileMenu.getItems().addAll(itNewFile,itOpenFile,itSaveFile,spFile,itExitFile);
        
        //Edit Menu Items
        itUndo      = new MenuItem("Undo");
        itCut       = new MenuItem("Cut");
        itCopy      = new MenuItem("Copy");
        itPast      = new MenuItem("Past");
        itDelete    = new MenuItem("Delete");
        itSelectAll = new MenuItem("Select All");
        spEdit1     = new SeparatorMenuItem();
        spEdit2     = new SeparatorMenuItem();
        
        itUndo.setAccelerator(KeyCombination.keyCombination("ctrl+z"));
        itCut.setAccelerator(KeyCombination.keyCombination("ctrl+x"));
        itCopy.setAccelerator(KeyCombination.keyCombination("ctrl+c"));
        itPast.setAccelerator(KeyCombination.keyCombination("ctrl+v"));
        itDelete.setAccelerator(KeyCombination.keyCombination("ctrl+b"));
        itSelectAll.setAccelerator(KeyCombination.keyCombination("ctrl+a"));
        editMenu.getItems().addAll(itUndo,spEdit1,itCut,itCopy,itPast,itDelete,spEdit2,itSelectAll);
        
        //Help Menu Items
        itAbout     = new MenuItem("About Notepad");
        
        itAbout.setAccelerator(KeyCombination.keyCombination("ctrl+i"));
        helpMenu.getItems().addAll(itAbout);
    }
    @Override
    public void start(Stage stage) throws Exception {
        a = new Alert(AlertType.NONE);
        
        //File Menu items Events
        itNewFile.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(!txtArea.getText().trim().equals("")){
                    stateOpenNewFile=1;
                    a.setAlertType(AlertType.CONFIRMATION);
                    a.setHeaderText("Save current file before open other file?");
                    a.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            fileChooser = new FileChooser();
                            fileChooser.setTitle("Save");
                            fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
                            selectedFile = fileChooser.showSaveDialog(stage);
                            saveFile();
                        }
                        else{
                            txtArea.clear();
                        }
                    });
                }
            }
        });
        itOpenFile.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(!txtArea.getText().trim().equals("")){
                    a.setAlertType(AlertType.CONFIRMATION);
                    a.setHeaderText("Save current file before open other file?");
                    a.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            fileChooser = new FileChooser();
                            fileChooser.setTitle("Save");
                            fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
                            selectedFile = fileChooser.showSaveDialog(stage);
                            saveFile();
                        }
                    });
                }
                fileChooser = new FileChooser();
                fileChooser.setTitle("Open Text File");
                fileChooser.getExtensionFilters().add(
                        new ExtensionFilter("Text Files", "*.txt"));
                selectedFile = fileChooser.showOpenDialog(stage);
                openFile();
            }
        });
        itSaveFile.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
                selectedFile = fileChooser.showSaveDialog(stage);
                saveFile();
            }
        });
        itExitFile.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                a.setAlertType(AlertType.CONFIRMATION);
                a.setHeaderText("Close My Notepad App");
                a.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        System.exit(0);
                    }
                });
            }
        });
        
        //Edit Menu items Events
        itUndo.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                txtArea.undo();
            }
        });
        itCut.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                txtArea.cut();
            }
        });
        itCopy.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                txtArea.copy();
            }
        });
        itPast.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                txtArea.paste();
            }
        });
        itDelete.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(txtArea.getSelectedText()==""){
                    txtArea.deleteText(txtArea.getSelection());
                }
                else{
                    txtArea.deletePreviousChar();
                }
            }
        });
        itSelectAll.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                txtArea.selectAll();
            }
        });
        
        //About Menu items Events
        itAbout.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                a.setAlertType(AlertType.INFORMATION);
                a.setHeaderText("Created by: Mohamed Rashed\nIOT Track\nJava Tasks");
                a.show();
            }
        });
        
        //Main
        mainBar.getMenus().addAll(fileMenu,editMenu,helpMenu);
        mainBorder.setTop(mainBar);
        mainBorder.setCenter(txtArea);
        
        myScene = new Scene(mainBorder,600,600);
        stage.setTitle("My Notepad");
        stage.setScene(myScene);
        stage.show();
        
        
    }
    int fileLength;
    char[] charData;
    public void openFile(){
        Runnable runnOpenFile = new Runnable() {
            @Override
            public void run() {
                try {
                    runOpenFile();
                } catch (IOException ex) {
                    Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread myThread = new Thread(runnOpenFile);
        myThread.setDaemon(true);
        myThread.start();
    }
    public void runOpenFile() throws IOException{
        if (selectedFile != null) {
            System.out.println(selectedFile);
            txtArea.clear();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                    dataIn = new FileReader(selectedFile);
                            for(int i=0; i<selectedFile.length();i++){
                                fileLength = (int)selectedFile.length();
                                charData = new char[fileLength];
                                txtArea.appendText(String.valueOf((char)dataIn.read()));
                            }
                            dataIn.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                        Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
        }
    }
    
    public void saveFile(){
        Runnable runnOpenFile = new Runnable() {
            @Override
            public void run() {
                try {
                    runSaveFile();
                } catch (IOException ex) {
                    Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread myThread = new Thread(runnOpenFile);
        myThread.setDaemon(true);
        myThread.start();
    }
    public void runSaveFile() throws IOException{
        if (selectedFile != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        dataOut = new FileWriter(selectedFile);
                        dataOut.write(txtArea.getText());
                        dataOut.close();
                        if(stateOpenNewFile == 1){
                            txtArea.clear();
                            stateOpenNewFile = 0;
                        }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                        Logger.getLogger(MyMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }
}
