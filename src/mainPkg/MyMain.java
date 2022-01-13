/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPkg;

import javafx.application.Application;
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
                    a.setAlertType(AlertType.CONFIRMATION);
                    a.setHeaderText("Create New File Will Remove All Data If You Don't Save");
                    a.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            txtArea.clear();
                        }
                    });
                }
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
    
}
