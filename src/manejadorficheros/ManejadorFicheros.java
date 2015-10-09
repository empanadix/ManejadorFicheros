/*
    FILE STRUCTURE
 *  private String nombre;
    private String descripcion;
    private String version;
    private Boolean gratis;
    private Double precio;
    private String requisitos;
 */
package manejadorficheros;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author David Caamaño Aldemunde
 */
public class ManejadorFicheros extends Application {
    
    private Stage primaryStage;
    List<Software> softwareList;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;        
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void saveAllFile(TextArea t){
        List<String> myList = new ArrayList<>();
        String volcado = t.getText();
        String[] lineas = volcado.split("\n");

        // new ArrayList<Element>(Arrays.asList(array))
        for (int i = 0; i < lineas.length; i++) {
            myList.add(lineas[i]);
        }
        
        //REESCRIBE EL FICHERO ENTERO, OJO   
        //Primero el .txt
        try
        {
            FileWriter fw = new FileWriter("data.txt",false);
            for (int i = 0; i < myList.size(); i++) {
                fw.write(myList.get(i)+"\n");
            }
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
        
        //Ahora el binario PRUEBA
        try(DataOutputStream dos=new DataOutputStream(new FileOutputStream("datos.bin",false));){
            
            for (int i = 0; i < myList.size()-4; i+=5) {
                dos.writeUTF(myList.get(i));
                dos.writeUTF(myList.get(i+1));
                dos.writeUTF(myList.get(i+2));
                Double number = Double.parseDouble(myList.get(i+3));
                dos.writeDouble(number);
                dos.writeUTF(myList.get(i+4));
            }
            dos.close();

        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(NumberFormatException e){
            System.out.println("Error guardando fichero: formato de número incorrecto");
            System.out.println(e.toString());
        }catch(Exception e){
            System.out.println("Error desconocido en saveAllFile");
            System.out.println(e.toString());
        }
    }
    public void saveMainFile(Software s){
        try
        {
            FileWriter fw = new FileWriter("data.txt",true);
            fw.write(s.getNombre()+"\n");
            fw.write(s.getDescripcion()+"\n");
            fw.write(s.getVersion()+"\n");
            fw.write(s.getPrecio()+"\n");
            fw.write(s.getRequisitos()+"\n");
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public String loadMainFile() 
            throws Exception,NullPointerException,IOException,NumberFormatException{
        chooseFileToOpen(); // Lanza excepción: se la pasamos a FXMLDocumentController
        String f ="";
        for (Software softwareItem : softwareList) {
            f+=softwareItem.getNombre()+"\n";
            f+=softwareItem.getDescripcion()+"\n";
            f+=softwareItem.getVersion()+"\n";
            f+=softwareItem.getPrecio()+"\n";
            f+=softwareItem.getRequisitos()+"\n";
        }
        return f;
    }
    
    public void saveMainFileBin(Software s){  
        //Escribir a binario
        try(DataOutputStream dos=new DataOutputStream(new FileOutputStream("datos.bin",true));){
            dos.writeUTF(s.getNombre());
            dos.writeUTF(s.getDescripcion());
            dos.writeUTF(s.getVersion());
            dos.writeDouble(s.getPrecio());
            dos.writeUTF(s.getRequisitos());
            
            dos.close();
        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error desconocido en saveMainFileBin.");
            System.out.println(e.toString());
        }
    }
    public String loadMainFileBin() 
            throws Exception,NullPointerException,IOException,NumberFormatException{
        chooseFileToOpen(); // Lanza excepción: se la pasamos a FXMLDocumentController
        String f ="";
        for (Software softwareItem : softwareList) {
            f+=softwareItem.getNombre()+"\n";
            f+=softwareItem.getDescripcion()+"\n";
            f+=softwareItem.getVersion()+"\n";
            f+=softwareItem.getPrecio()+"\n";
            f+=softwareItem.getRequisitos()+"\n";
        }
        return f;
    }
    
    private void chooseFileToOpen() 
            throws Exception,NullPointerException,IOException,NumberFormatException{
        FileChooser fileChooser = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDirectory);

        FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("FILES: (*.txt)(*.bin)", "*.txt", "*.bin");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showOpenDialog(primaryStage);
            
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        switch(extension){
            case ".txt":
                loadMainSoftware(file);
                break;
            case ".bin":
                loadMainSoftwareBin(file);
                break;
            default:
                System.out.println("Extensión de archivo desconocida");
                break;
        }        
    }
    private File chooseFileToSave() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(primaryStage);
    }
    
    private void loadMainSoftware(File file)
            throws Exception,NullPointerException,IOException,NumberFormatException{
        final int fieldsAmount = 5;
        String sCurrentLine="";
        
        // Al abrir el fichero de esta forma no es necesario cerrarlo
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            Software s=null;
            softwareList = new ArrayList<>();
            
            for (int i = fieldsAmount; (sCurrentLine = br.readLine()) != null; i++) {
                switch (i % fieldsAmount) {
                    case 0:
                        s = new Software();
                        s.setNombre(sCurrentLine);
                        break;
                    case 1:
                        if(s!=null) s.setDescripcion(sCurrentLine);
                        break;
                    case 2:
                        if(s!=null) s.setVersion(sCurrentLine);
                        break;
                    case 3:
                        if(s!=null) s.setPrecio(Double.parseDouble(sCurrentLine));
                        break;
                    case 4:
                        if(s!=null) { 
                            s.setRequisitos(sCurrentLine);
                            softwareList.add(s);
                        }
                        break;
                }
            }
            br.close();
        } // Al tener throws no es necesario el catch        
    }
    private void loadMainSoftwareBin(File file){
        String f = "";
 
        try(DataInputStream dis=new DataInputStream(new FileInputStream(file));){
            Software s = new Software();
            softwareList = new ArrayList<>();
        
            for (;;) {
                s = new Software(dis.readUTF());
                s.setDescripcion(dis.readUTF());
                s.setVersion(dis.readUTF());
                s.setPrecio(dis.readDouble());
                s.setRequisitos(dis.readUTF());
                softwareList.add(s);
            }
        } catch(EOFException e){  // Al hacer un for(;;) saltaria esta exepción
            System.out.println("Fin del fichero.");
        } catch(IOException e){
            System.out.println("Error E/S");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
