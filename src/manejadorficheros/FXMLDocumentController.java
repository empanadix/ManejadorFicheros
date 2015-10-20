/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorficheros;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 *
 * @author David Caamaño Aldemunde, Bruno del Greco
 */
public class FXMLDocumentController implements Initializable {
    
    Window primaryStage = null;

    @FXML
    private TextArea ta;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btCagar;
    @FXML
    private ManejadorFicheros mf;
    @FXML
    private MenuItem miNuevo;
    @FXML
    private MenuItem miMostrar;
    @FXML
    private MenuItem miGuardar;
    @FXML
    private Pane panelMostrar;
    @FXML
    private Pane panelNuevo;
    @FXML
    private Pane panelInicio;
    @FXML
    private Pane panelAcercaDe;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField version;
    @FXML
    private TextField precio;
    @FXML
    private TextField requisitos;
    @FXML
    private Button btGuardar2;
    @FXML
    private Button btCancelar2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mf = new ManejadorFicheros();
    }   
    
    @FXML
    private void addFileContent(ActionEvent event){
        try {
            //ta.setText(mf.loadMainFile());
        } catch (NullPointerException ex) {
            // TODO: Avisar al usuario mediante el interfaz gráfico
            System.out.println("Se ha cancelado la elección de fichero: " + ex.toString());
        } catch (NumberFormatException ex) {
            // TODO: Avisar al usuario mediante el interfaz gráfico
            System.out.println("No se ha cargado el archivo. Formato de número incorrecto:" + ex.toString());
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void showNewSW(ActionEvent event){
        panelMostrar.setVisible(false);
        panelNuevo.setVisible(true);
        panelInicio.setVisible(false);
        panelAcercaDe.setVisible(false);
        // Cambia el comportamiento del boton Guardar del menú Archivo
        miGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveNewSW(e);
            }
        });
    }
    
    @FXML
    private void showMostrar(ActionEvent event){
        panelMostrar.setVisible(true);
        panelNuevo.setVisible(false);
        panelInicio.setVisible(false);
        panelAcercaDe.setVisible(false);
        // Cambia el comportamiento del boton Guardar del menú Archivo
        miGuardar.setOnAction((ActionEvent e) -> {
            saveAllText(e);
        });
    }
    
    @FXML
    private void showAcercaDe(ActionEvent event){
        panelMostrar.setVisible(false);
        panelNuevo.setVisible(false);
        panelInicio.setVisible(false);
        panelAcercaDe.setVisible(true);
    }
    
    @FXML
    private void saveNewSW(ActionEvent event){
        
        try{
            
            Software newSW = new Software(nombre.getText(),descripcion.getText(),
                version.getText(),Double.parseDouble(precio.getText()),requisitos.getText());
            //mf.saveMainFile(newSW);
            //mf.saveMainFileBin(newSW);
        }catch(Exception e) {
            System.out.println("Error al crear el objeto software");
        }

    }
    
    @FXML
    private void saveAllText(ActionEvent event){
        //mf.saveAllFile(ta);
        //mf.save
    }
    
        @FXML
    private void clearTextArea(ActionEvent event){
        ta.clear();
    }
    
            @FXML
    private void exit(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }
    
}
