/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorficheros;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Fecha última modificación: 21/10/2015
 * Version: 1.02
 *      -Renovar la clase:
 *          Se han renombrado todos los controles y los manejadores
 *      -Se ha añadido el manejador handleMenuArchivoGuardarComo
 *      -Se han añadido métodos privados:
 *           initPanels() configuración inicial de la visibilidad de los paneles
 *           initMenu() configuración inicial de los items de los menús
 *           showNonImplementedAlert() lanza una alerta para los métodos no implementados
 *           showExceptionAlert() lanza una alerta, sirve para mostrar excepciones
 *           updatePaneSoftware() Actualiza los TextFiel del paneSoftware con los datos de mySoftware(mySoftwareCounter)
 *           clearPaneSoftware() método auxiliar para newSoftware(), vacia los TextFiel del paneSoftware
 *           hideAllPane() método auxiliar para showPane(), oculta todos los paneles 
 *           showPane() Muestra un panel y oculta el resto
 *           newSoftware() Muestra el paneSoftware con los campos vacios para introducir datos
 *           getFileChooser() método auxiliar para chooseFileToOpen() y chooseFileToSave()
 *           chooseFileToOpen() método auxiliar para openFile()
 *           chooseFileToSave() método auxiliar para saveSoftwareAs()
 *           openFile() método auxiliar para openSoftware()
 *           openSoftware() Obtiene los datos para la lista mySoftware mediante un OpenDialog
 *           chooseMethodAndSave() método auxiliar para saveSoftware() y saveSoftwareAs()
 *           saveSoftware() Guarda los datos de la lista mySoftware en el archivo actual
 *           saveSoftwareAs() Guarda los datos de la lista mySoftware mediante un SaveDialog
 *           showSoftwareByConsole() Muestra la lista con formato por consola
 *      -Se muestra el paneInicio al iniciar la aplicación
 *      -menuArchivo y menuAyuda ya funcionan
 *      -paneSofware: anterior, siguiente, nuevo y guardar ya funcionan
 * 
 * @author Adil Casamayor Silvar
 */
public class FXMLDocumentController implements Initializable {
    
    Window primaryStage = null;
    private List<Software> mySoftware = null;
    private int mySoftwareCounter = 0;
    private String fileName = "";  // Se utilizará para la opción guardar
    private ManejadorFicheros mf;
    
    @FXML
    private MenuItem menuArchivoNuevo;
    @FXML
    private MenuItem menuArchivoAbrir;
    @FXML
    private MenuItem menuArchivoGuardar;
    @FXML
    private MenuItem menuAyudaAcercaDe;
    
    @FXML
    private Pane paneInicio;
    @FXML
    private Pane paneAcercaDe;
    
    @FXML
    private Pane paneMostrar;
    @FXML
    private TextArea textAreaMostrar;
    @FXML
    private Button buttonMostarGuardar;
    @FXML
    private Button buttonMostrarCancelar;
    @FXML
    private Button buttonMostrarCargar;
    
    @FXML
    private Pane paneSoftware;
    @FXML
    private TextField textSoftwareNombre;
    @FXML
    private TextField textSoftwareDescripcion;
    @FXML
    private TextField textSoftwareVersion;
    @FXML
    private TextField textSoftwarePrecio;
    @FXML
    private TextField textSoftwareRequisitos;
    @FXML
    private Button buttonSoftwareCancelar;
    @FXML
    private Button buttonSoftwareGuardar;
    @FXML
    private Button buttonSoftwareAnterior;
    @FXML
    private Button buttonSoftwareSiguiente;
    @FXML
    private Button buttonSoftwareNuevo;
    
    
    // INITIALIZE METHODS
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mf = new ManejadorFicheros();
        
        initPanels();
        initMenu();
    }
    
    private void initPanels(){
        paneMostrar.setVisible(false);
        paneSoftware.setVisible(false);
        paneInicio.setVisible(true);
        paneAcercaDe.setVisible(false);
    }
    
    private void initMenu(){
        //TODO:
    }
       
    // MENU ARCHIVO HANDLERS
    @FXML
    private void handleMenuArchivoNuevo(ActionEvent event){
        // TODO: activar/desactivar controles y sombreados
        newSoftware();
    }
    
    @FXML
    private void handleMenuArchivoAbrir(ActionEvent event){
        openSoftware();
    }
    
    @FXML
    private void handleMenuArchivoGuardar(ActionEvent event){
        saveSoftware();
    }
    
    @FXML
    private void handleMenuArchivoGuardarComo(ActionEvent event){
        saveSoftwareAs();
    }
    
    @FXML
    private void handleMenuArchivoSalir(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }
    
    // MENU CONFIGURACION HANDLERS
    
    // MENU AYUDA HANDLERS
    @FXML
    private void handleMenuAyudaAcercaDe(ActionEvent event){
        paneMostrar.setVisible(false);
        paneSoftware.setVisible(false);
        paneInicio.setVisible(false);
        paneAcercaDe.setVisible(true);
    }
    
    // PANE SOFTWARE HANDLERS
    @FXML
    private void handleButtonSoftwareCancelar(ActionEvent event){
        // TODO:
        showNonImplementedAlert("handleButtonSoftwareCancelar");
    }
    
    @FXML
    private void handleButtonSoftwareGuardar(ActionEvent event){
        saveSoftware();
    }
    
    @FXML
    private void handleButtonSoftwareAnterior(ActionEvent event){
        if (mySoftwareCounter > 0) {
            mySoftwareCounter--;
        } else {
            mySoftwareCounter=mySoftware.size()-1;
        }
        updatePaneSoftware(mySoftware.get(mySoftwareCounter));
    }
    
    @FXML
    private void handleButtonSoftwareSiguiente(ActionEvent event){
        if (mySoftwareCounter < mySoftware.size()-1) {
            mySoftwareCounter++;
        } else {
            mySoftwareCounter=0;
        }
        updatePaneSoftware(mySoftware.get(mySoftwareCounter));
    }
    
    @FXML
    private void handleButtonSoftwareNuevo(ActionEvent event){
        newSoftware();
    }
    
    
    // PRIVATE METHODS
    private void showNonImplementedAlert(String method){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("FXMLDocumentController");
        alert.setHeaderText("Not implemented, yet");
        alert.setContentText("Method: "+method);

        alert.showAndWait();
        //throw new UnsupportedOperationException("Not implemented, yet");
    }
    
    private void showExceptionAlert(String exceptionName,String description){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("FXMLDocumentController");
        alert.setHeaderText(exceptionName);
        alert.setContentText(description);

        alert.showAndWait();
    }
    
    private void updatePaneSoftware(Software newSoftware){
        textSoftwareNombre.setText(newSoftware.getNombre());
        textSoftwareDescripcion.setText(newSoftware.getDescripcion());
        textSoftwareVersion.setText(newSoftware.getVersion());
        textSoftwarePrecio.setText(newSoftware.getPrecio().toString());
        textSoftwareRequisitos.setText(newSoftware.getRequisitos());
    }
    
    private void clearPaneSoftware(){
        textSoftwareNombre.setText("");
        textSoftwareDescripcion.setText("");
        textSoftwareVersion.setText("");
        textSoftwarePrecio.setText("");
        textSoftwareRequisitos.setText("");
    }
    
    private void hideAllPane(){
        paneMostrar.setVisible(false);
        paneSoftware.setVisible(false);
        paneInicio.setVisible(false);
        paneAcercaDe.setVisible(false);
    }
    
    private void showPane(Pane pane){
        hideAllPane();
        pane.setVisible(true);
    }
    
    private void newSoftware(){
        clearPaneSoftware();
        showPane(paneSoftware);
    }
    
    private FileChooser getFileChooser(){
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                                    "FILES: (*.txt)(*.bin)(*.xml)", 
                                                    "*.txt", "*.bin", "*.xml");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(workingDirectory);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }
    
    private File chooseFileToOpen(){
        return getFileChooser().showOpenDialog(primaryStage);
    }

    private File chooseFileToSave(){
        return getFileChooser().showSaveDialog(primaryStage);
    }
    
    // throws Exception,NullPointerException,IOException,NumberFormatException,IllegalArgumentException
    private List<Software> openFile() throws Exception, NullPointerException {
        File file = chooseFileToOpen();
        if (file==null) {
            throw new NullPointerException("Se ha cancelado la elección de fichero");
        }
        
        String previusFileName = fileName;
        fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        switch(extension){
            case ".txt":
                return mf.loadTXT(file);
            case ".bin":
                return mf.loadBIN(file);
            case ".xml":
                return mf.loadDOM(file);
            default:
                fileName = previusFileName;  // Deshacemos los cambios
                System.out.println("Extensión de archivo desconocida");
                throw new NullPointerException("Extensión de archivo desconocida");
        }
    }
    
    private void openSoftware(){
        try {
            // Cargar archivo
            mySoftware = openFile();
            
            // Rellenar panel con el primer objeto Software sí se cargó el archivo
            if (mySoftware.isEmpty()) {
                throw new IndexOutOfBoundsException("El archivo está vacio o no se pudo leer");
            }
            updatePaneSoftware(mySoftware.get(0));
            
            // Mostrar panel sí se cargó el archivo
            showPane(paneSoftware);
            
            // Cambia el comportamiento del botón Guardar del menú Archivo
            /*
            menuArchivoGuardar.setOnAction((ActionEvent e) -> {
                //TODO:
            });*/
        } catch (IOException ex) {
            // "Error accediendo al archivo"
            showExceptionAlert("IOException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            showExceptionAlert("NullPointerException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IndexOutOfBoundsException ex) {
            showExceptionAlert("IndexOutOfBoundsException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);        
        } catch (NumberFormatException ex) {
            // Hereda de IllegalArgumentException
            // "Error de formato en un campo de tipo numérico"
            showExceptionAlert("NumberFormatException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            // Sirve para las validaciones de los campos de texto
            // "Error de formato en un campo de tipo texto"
            showExceptionAlert("IllegalArgumentException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            // "Error desconocido"
            showExceptionAlert("Exception", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void chooseMethodAndSave(File file){
        String previusFileName = fileName;
        fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        switch(extension){
            case ".txt":
                mf.saveTXT(mySoftware, file);
                break;
            case ".bin":
                mf.saveBIN(mySoftware, file);
                break;
            case ".xml":
                mf.saveDOM(mySoftware, file);
                break;
            default:
                fileName = previusFileName;  // Deshacemos los cambios
                System.out.println("chooseMethodAndSave: Extensión de archivo desconocida");
                //throw new Exception("Extensión de archivo desconocida");
                break;
        }
    }
    
    private void saveSoftware(){
        try {
            if (fileName.equals("")) {
                throw new NullPointerException("No se ha especificado un nombre de archivo");
            }
            File file = new File(fileName);
            if (file==null) {
                throw new NullPointerException("La ruta o el nombre del archivo no es válido");
            }
            chooseMethodAndSave(file);
        } catch (NullPointerException ex) {
            showExceptionAlert("NullPointerException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            // "Error desconocido"
            showExceptionAlert("Exception", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveSoftwareAs(){
        try {
            File file = chooseFileToSave();
            if (file==null) {
                throw new NullPointerException("Se ha cancelado la elección de fichero");
            }
            chooseMethodAndSave(file);
        } catch (NullPointerException ex) {
            showExceptionAlert("NullPointerException", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            // "Error desconocido"
            showExceptionAlert("Exception", ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showSoftwareByConsole(){
        for (Software software : mySoftware) {
            System.out.println("Nombre: " + software.getNombre());
            System.out.println("Descripcion: " + software.getDescripcion());
            System.out.println("Version: " + software.getVersion());
            System.out.println("Precio: " + software.getPrecio());
            System.out.println("Requisitos: " + software.getRequisitos());
            System.out.println();
        }
    }
    
    // HANDLERS
    // TODO: modificar o borrar estos handlers
    @FXML
    private void addFileContent(ActionEvent event){
        try {
            //textAreaMostrar.setText(mf.loadMainFile());
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
        showPane(paneSoftware);
        // Cambia el comportamiento del botón Guardar del menú Archivo
        menuArchivoGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveNewSW(e);
            }
        });
    }
    
    @FXML
    private void showMostrar(ActionEvent event){
        showPane(paneMostrar);        
        // Cambia el comportamiento del botón Guardar del menú Archivo
        menuArchivoGuardar.setOnAction((ActionEvent e) -> {
            saveAllText(e);
        });
    }
    
    @FXML
    private void saveNewSW(ActionEvent event){
        
        try{
            
            Software newSW = new Software(textSoftwareNombre.getText(),textSoftwareDescripcion.getText(),
                textSoftwareVersion.getText(),Double.parseDouble(textSoftwarePrecio.getText()),textSoftwareRequisitos.getText());
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
        textAreaMostrar.clear();
    }
}
