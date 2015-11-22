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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Fecha última modificación: 23/10/2015
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
 * Version: 1.03
 *      -Renombrar mf a fileHandler
 *      -menuArchivoGuardar está desactivado por defecto, al abrir un archivo se activa
 *      -se ha simplificado el código de handleMenuAyudaAcercaDe()
 *      -se ha simplificado el código de initPanels() y renombrado a initPanes()
 *      -implements ChangeListener para los TextField
 *      -Se han añadido métodos privados:
 *          initPaneSoftware()--> Guardar.setDisable(true), setPromptText() y addListener(ChangeListener)
 *          setDisableSoftwareControls() activa/desactiva todos los controles relacionados con paneSoftware 
 *          setDisableSoftwarePrimaryControls() activa/desactiva ciertos controles relacionados con paneSoftware
 *      - paneSofware ya funciona correctamente
 * Version: 1.10
 *      -Añadidos comentarios especiales para Netbeans para facilitar la organización del código
 *      -exitManejadorFicheros() para poder reutilizar el método
 *      -Resetear mySoftwareCounter en openSoftware()
 *      -Reorganización: Ahora la parte que afecta a la vista está en los handlers y 
 *       la parte de la lógica en una única función por cada handler
 *      -newSoftware() se ha eliminado porque ya no es necesario
 *      -Captura de excepciones mejorada
 *      -TODO: 
 *          utilizar las opciones del menú configuración
 *          probar que Guardar como funciona correctamente cuando esté disponible
 *          añadir opción en el menú para utilizar el paneMostrar de la primera versión
 *          ¿mostrar contadorSofware?
 * 
 * 
 * @author Adil Casamayor Silvar
 */
public class FXMLDocumentController implements Initializable, ChangeListener  {
    
    // <editor-fold defaultstate="collapsed" desc="VARIABLES DECLARATION">
    Window primaryStage = null;
    private List<Software> mySoftware = null;
    private int mySoftwareCounter = 0;
    private String fileName = "";  // Se utilizará para la opción guardar
    private ManejadorFicheros fileHandler = null;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="@FXML CONTROLS DECLARATION">
    @FXML
    private MenuItem menuArchivoNuevo;
    @FXML
    private MenuItem menuArchivoAbrir;
    @FXML
    private MenuItem menuArchivoGuardar;
    @FXML
    private MenuItem menuAyudaAcercaDe;
    
    @FXML
    private RadioMenuItem menuConfiguracionLeerDOM;
    @FXML
    private RadioMenuItem menuConfiguracionLeerSAX;
    
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="IMPLEMENTED METHODS">
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        
        buttonSoftwareCancelar.setDisable(false);
        if (    
                !textSoftwareNombre.getText().isEmpty() && 
                !textSoftwareDescripcion.getText().isEmpty()&& 
                !textSoftwareVersion.getText().isEmpty()&& 
                !textSoftwarePrecio.getText().isEmpty()&& 
                !textSoftwareRequisitos.getText().isEmpty() 
            ) {
            menuArchivoGuardar.setDisable(false);
            buttonSoftwareGuardar.setDisable(false);
        }else
        {
            menuArchivoGuardar.setDisable(true);
            buttonSoftwareGuardar.setDisable(true);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileHandler = new ManejadorFicheros();
        
        initMenu();
        initPanes();
        showPane(paneInicio);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="INITIALIZE METHODS">
    private void initMenu(){
        menuArchivoGuardar.setDisable(true);
        ToggleGroup toggleGroup = new ToggleGroup();
        menuConfiguracionLeerDOM.setToggleGroup(toggleGroup);
        menuConfiguracionLeerSAX.setToggleGroup(toggleGroup);
        menuConfiguracionLeerDOM.setSelected(true);
    }
    
    private void initPanes(){
        initPaneSoftware();        
    }
    
    private void initPaneSoftware(){           
        buttonSoftwareAnterior.setDisable(true);        
        buttonSoftwareSiguiente.setDisable(true);     
        buttonSoftwareCancelar.setDisable(true);
        buttonSoftwareNuevo.setDisable(true);
        buttonSoftwareGuardar.setDisable(true);
            
        textSoftwareNombre.setPromptText("Nombre");
        textSoftwareDescripcion.setPromptText("Descripción");
        textSoftwareVersion.setPromptText("Versión");
        textSoftwarePrecio.setPromptText("Precio");
        textSoftwareRequisitos.setPromptText("Requisitos");
        
        textSoftwareNombre.textProperty().addListener(this);
        textSoftwareDescripcion.textProperty().addListener(this);
        textSoftwareVersion.textProperty().addListener(this);
        textSoftwarePrecio.textProperty().addListener(this);
        textSoftwareRequisitos.textProperty().addListener(this);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="@FXML HANDLERS">
    // MENU ARCHIVO HANDLERS
    @FXML
    private void handleMenuArchivoNuevo(ActionEvent event){
        clearPaneSoftware();
        setDisableSoftwareControls(true);
        buttonSoftwareCancelar.setDisable(false);
        showPane(paneSoftware);
    }
    
    @FXML
    private void handleMenuArchivoAbrir(ActionEvent event){
        if (openSoftware()) {
            updatePaneSoftware(mySoftware.get(0));
            setDisableSoftwareControls(true);
            setDisableSoftwarePrimaryControls(false);
            showPane(paneSoftware);
        }
    }
    
    @FXML
    private void handleMenuArchivoGuardar(ActionEvent event){
        //saveSoftwareAs()
        if (saveSoftware()) {
            setDisableSoftwareControls(true);
            setDisableSoftwarePrimaryControls(false);
        }        
    }
    
    @FXML
    private void handleMenuArchivoGuardarComo(ActionEvent event){
        if (saveSoftwareAs()) {
            setDisableSoftwareControls(true);
            setDisableSoftwarePrimaryControls(false);            
        }
    }
    
    @FXML
    private void handleMenuArchivoSalir(ActionEvent event){
        if (!exitManejadorFicheros()) {
            //TODO:
        }
    }
    
    // MENU CONFIGURACION HANDLERS
    
    // MENU AYUDA HANDLERS
    @FXML
    private void handleMenuAyudaAcercaDe(ActionEvent event){
        //clearPaneSoftware();
        showPane(paneAcercaDe);
    }
    
    // PANE SOFTWARE HANDLERS
    @FXML
    private void handleButtonSoftwareCancelar(ActionEvent event){
        if (mySoftware==null || mySoftware.isEmpty()) {
            clearPaneSoftware();
            setDisableSoftwareControls(true);
            showPane(paneInicio);
        } else {
            updatePaneSoftware(mySoftware.get(mySoftwareCounter));
            setDisableSoftwareControls(true); // Es necesario después de update
            setDisableSoftwarePrimaryControls(false);
        }
        
        //showNonImplementedAlert("handleButtonSoftwareCancelar");
    }
    
    @FXML
    private void handleButtonSoftwareGuardar(ActionEvent event){
        if (saveSoftware()) {
            setDisableSoftwareControls(true);
            setDisableSoftwarePrimaryControls(false);
        }
    }
    
    @FXML
    private void handleButtonSoftwareAnterior(ActionEvent event){
        backwardSoftware();
        updatePaneSoftware(mySoftware.get(mySoftwareCounter));       
        setDisableSoftwareControls(true);
        setDisableSoftwarePrimaryControls(false);
    }
    
    @FXML
    private void handleButtonSoftwareSiguiente(ActionEvent event){
        forwardSoftware();
        updatePaneSoftware(mySoftware.get(mySoftwareCounter));        
        setDisableSoftwareControls(true);
        setDisableSoftwarePrimaryControls(false);
    }
    
    @FXML
    private void handleButtonSoftwareNuevo(ActionEvent event){
        clearPaneSoftware();
        setDisableSoftwareControls(true);        
        buttonSoftwareAnterior.setDisable(false);
        buttonSoftwareSiguiente.setDisable(false);
        buttonSoftwareCancelar.setDisable(false);
        showPane(paneSoftware);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="HANDLERS MAIN PRIVATE METHODS">    
    private boolean openSoftware(){
        try {
            // Cargar archivo
            mySoftware = openFile();
            
            // Rellenar panel con el primer objeto Software sí se cargó el archivo
            if (mySoftware.isEmpty()) {
                throw new IndexOutOfBoundsException("El archivo está vacio o no se pudo leer");
            }
            mySoftwareCounter=0;
            // Cambia el comportamiento del botón Guardar del menú Archivo            
            /*
            menuArchivoGuardar.setOnAction((ActionEvent e) -> {
                //TODO:
            });*/
            return true;
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
        return false;
    }
    
    private boolean saveSoftware(){
        if (fileName.equals("")) {
            throw new NullPointerException("No se ha especificado un nombre de archivo");
        }
        try {
            File file = new File(fileName);
            if (file==null) {
                throw new NullPointerException("Ruta o nombre de archivo no válido");
            }
            chooseMethodAndSave(file);
            return true;
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
        return false;
    }
    
    private boolean saveSoftwareAs(){
        try {
            File file = chooseFileToSave();
            if (file==null) {
                throw new NullPointerException("Se ha cancelado la elección de fichero");
            }
            chooseMethodAndSave(file);
            return true;
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
        return false;
    }
    
    private void backwardSoftware(){
        if (mySoftwareCounter > 0) {
            mySoftwareCounter--;
        } else {
            mySoftwareCounter=mySoftware.size()-1;
        }
    }
    
    private void forwardSoftware(){
        if (mySoftwareCounter < mySoftware.size()-1) {
            mySoftwareCounter++;
        } else {
            mySoftwareCounter=0;
        }        
    }
    
    private boolean exitManejadorFicheros() {
        boolean confirmDialog = true; //TODO: sustituir por diálogo de confirmación
        if (confirmDialog) {
            Platform.exit();
            System.exit(0);
        }
        return false;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="VIEW PRIVATE METHODS">
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
    
    private void setDisableSoftwareControls(boolean disable){
        menuArchivoGuardar.setDisable(disable);
        buttonSoftwareGuardar.setDisable(disable);        
        buttonSoftwareCancelar.setDisable(disable);        
        buttonSoftwareAnterior.setDisable(disable);
        buttonSoftwareSiguiente.setDisable(disable);
        buttonSoftwareNuevo.setDisable(disable);
    }
    
    private void setDisableSoftwarePrimaryControls(boolean disable){        
        buttonSoftwareAnterior.setDisable(disable);
        buttonSoftwareSiguiente.setDisable(disable);
        buttonSoftwareNuevo.setDisable(disable);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODS">
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
    
    private List<Software> openFile() throws Exception, NullPointerException {
        // throws Exception,NullPointerException,IOException,NumberFormatException,IllegalArgumentException
        File file = chooseFileToOpen();
        if (file==null) {
            throw new NullPointerException("Se ha cancelado la elección de fichero");
        }
        
        String previusFileName = fileName;
        fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        switch(extension){
            case ".txt":
                return fileHandler.loadTXT(file);
            case ".bin":
                return fileHandler.loadBIN(file);
            case ".xml":
                if (menuConfiguracionLeerDOM.isSelected()) {
                    return fileHandler.loadDOM(file);
                } else if (menuConfiguracionLeerSAX.isSelected()) {
                    return fileHandler.loadSAX(file);
                } else {
                    throw new NullPointerException("Método de lectura XML desconocido");
                }
            default:
                fileName = previusFileName;  // Deshacemos los cambios
                throw new NullPointerException("Extensión de archivo desconocida");
        }
    }

    private void chooseMethodAndSave(File file) throws Exception, NullPointerException, IndexOutOfBoundsException{
        if (file==null) {
            throw new NullPointerException("Ruta o nombre de archivo no válido");
        }
        if (mySoftware==null) {
            throw new NullPointerException("No hay datos para guardar");
        }
        if (mySoftware.isEmpty()) {
            throw new IndexOutOfBoundsException("No hay datos para guardar");
        }
        String previusFileName = fileName;
        fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        switch(extension){
            case ".txt":
                fileHandler.saveTXT(mySoftware, file);
                break;
            case ".bin":
                fileHandler.saveBIN(mySoftware, file);
                break;
            case ".xml":
                fileHandler.saveDOM(mySoftware, file);
                break;
            default:
                fileName = previusFileName;  // Deshacemos los cambios
                throw new NullPointerException("Extensión de archivo desconocida");
        }
    }
    
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DEPRECATED METHODS">
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
    // </editor-fold>
    
}
