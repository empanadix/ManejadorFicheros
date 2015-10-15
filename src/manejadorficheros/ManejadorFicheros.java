/*
    FILE STRUCTURE
 *  private String nombre;
    private String descripcion;
    private String version;
    private Boolean gratis;
    private Double precio;
    private String requisitos;


    METHODS ALLOWED:
        Load-Save plain text /with Strings and List<Software>
                  Binary file /with Software and List<Software>
                  DOM XML //with List<Software>
        Save ALL  save in plain text,binary at same time/ with textArea, TODO XML
                  load just plain text
 */
package manejadorficheros;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
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
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author David Caamaño Aldemunde
 */
public class ManejadorFicheros extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
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
    

    /*
    public void saveAllFile(TextArea t){
        List<String> myList = new ArrayList<>();
        String volcado = t.getText();
        String[] lineas = volcado.split("\n");

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
        
        //Ahora el binario
        try(DataOutputStream dos=new DataOutputStream(new FileOutputStream("datos.bin",true));){
            
            for (int i = 0; i < myList.size()-4; i+=5) {
                dos.writeUTF(myList.get(i));
                dos.writeUTF(myList.get(i+1));
                dos.writeUTF(myList.get(i+2));
                dos.writeDouble(Double.parseDouble(myList.get(i+3)));
                dos.writeUTF(myList.get(i+4));
 
            }

        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error Desconocido.");
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
    public String loadMainFile(){

        String f ="";
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt")))
        {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                f+=sCurrentLine+"\n";
            }


        } catch (IOException e) {
            e.printStackTrace();
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
            
        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error Desconocido.");
        }
    }
    public String loadMainFileBin(){
        String f = "";
 
        try(DataInputStream dis=new DataInputStream(new FileInputStream("datos.bin"));){
            
            for (;;) {
                f+=dis.readUTF();
                f+=dis.readUTF();
                f+=dis.readUTF();
                f+=dis.readDouble();
                f+=dis.readUTF();
            }
        }catch(EOFException e){  //Al hacer un for(;;) saltaria esta exepcion
            System.out.println("Fin del fichero.");
        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error Desconocido.");
        }
        return f;
    }
    */
    //////////////////////PLAIN SAVE LOAD LIST<SOFTWARE>//////////////////////////
    public void saveTXT(List<Software> lSw,File act){
            try
            {
                FileWriter fw = new FileWriter(act,true);

                for (int i = 0; i < lSw.size(); i++) {
                    fw.write(lSw.get(i).getNombre());
                    fw.write(lSw.get(i).getDescripcion());
                    fw.write(lSw.get(i).getVersion());
                    fw.write(String.valueOf(lSw.get(i).getPrecio()));
                    fw.write(lSw.get(i).getRequisitos());
                }
                fw.close();

            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
    }
    
    public List<Software> loadTXT(File act){
        List<Software> toReturn = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(act)))
        {
            String actName = "";
            String actDescription = "";
            String actVersion = "";
            Double actPrice = 0.0;
            String actRequirements = "";   
            do
            {
                actName = br.readLine();
                actDescription = br.readLine();
                actVersion = br.readLine();
                actPrice = Double.valueOf(br.readLine());
                actRequirements = br.readLine();
                
                if (actName != null){
                    toReturn.add(new Software(actName,actDescription,
                            actVersion,actPrice,actRequirements));
                }
                
            }while(actName != null && actDescription != null && actVersion != null
                    && actPrice != null && actRequirements!= null);
 

        } catch (IOException e) {
            e.printStackTrace();
        } 
        return toReturn;
    }
    
    //////////////////////BIN SAVE LOAD LIST<SOFTWARE>//////////////////////////
    public void saveBIN(List<Software> lSw,File act){
        try(DataOutputStream dos=new DataOutputStream(new FileOutputStream(act,true));){
            for (int i = 0; i < lSw.size(); i++) {
                dos.writeUTF(lSw.get(i).getNombre());
                dos.writeUTF(lSw.get(i).getDescripcion());
                dos.writeUTF(lSw.get(i).getVersion());
                dos.writeDouble(lSw.get(i).getPrecio());
                dos.writeUTF(lSw.get(i).getRequisitos());
            }
                        
        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error Desconocido.");
        }
    }
    
    public List<Software> loadBIN(File act){
        List<Software> toReturn = new ArrayList<>();
        try(DataInputStream dis=new DataInputStream(new FileInputStream(act));){

            for (;;) {
                toReturn.add(new Software(dis.readUTF(),dis.readUTF(),
                        dis.readUTF(),dis.readDouble(),dis.readUTF()));
            }
        }catch(EOFException e){  //Al hacer un for(;;) saltaria esta exepcion
            System.out.println("Fin del fichero.");
        }catch(IOException e){
            System.out.println("Error E/S");
        }catch(Exception e){
            System.out.println("Error Desconocido.");
        }
        return toReturn;
    }
    
    //////////////////////DOM SAVE LOAD LIST<SOFTWARE>//////////////////////////
    public List<Software> loadDOM(File act){
        List<Software> toReturn = new ArrayList<>();
        
               Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(act);

            Element doc = dom.getDocumentElement();
            NodeList nl;
            nl = doc.getElementsByTagName("nombre");
            for (int i = 0; i < nl.getLength(); i++) {
                List<String> rolev = new ArrayList<String>();
                String nombre="";
                String descripcion = "";
                String version = "";
                Double precio = 0.0;
                String requisitos = "";
                
                
            nombre = getTextValue(nombre, doc, "nombre",i);
            if (nombre != null) {
                if (!nombre.isEmpty())
                    rolev.add(nombre);
            }
            descripcion = getTextValue(descripcion, doc, "descripcion",i);
            if (descripcion != null) {
                if (!descripcion.isEmpty())
                    rolev.add(descripcion);
            }
            version = getTextValue(version, doc, "version",i);
            if (version != null) {
                if (!version.isEmpty())
                    rolev.add(version);
            }
            String precio2 = getTextValue(String.valueOf(precio), doc, "precio",i);
            if ( precio2 != null) {
                if (!precio2.isEmpty())
                    rolev.add(precio2);
            }
            requisitos = getTextValue(requisitos, doc, "requisitos",i);
            if ( requisitos != null) {
                if (!requisitos.isEmpty())
                    rolev.add(requisitos);
            }
                        
            toReturn.add(new Software(rolev.get(0),rolev.get(1),rolev.get(1),
                                    Double.valueOf(rolev.get(3)),rolev.get(4)));
            
        }

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return toReturn;
    }
        
    private String getTextValue(String def, Element doc, String tag,int i) {
        String value = def;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(i).hasChildNodes()) {
            value = nl.item(i).getFirstChild().getNodeValue();
        }
        return value;
    }
    public void saveDOM(List<Software> lSw,File act){

        try {
            DocumentBuilderFactory fábricaCreadorDocumento = DocumentBuilderFactory.newInstance();
            DocumentBuilder creadorDocumento = fábricaCreadorDocumento.newDocumentBuilder();
            //Crear un nuevo documento XML
            Document documento = creadorDocumento.newDocument();

            //Crear el nodo raíz y colgarlo del documento
            Element elementoRaiz = documento.createElement("softwares");
            documento.appendChild(elementoRaiz);
 
            
            for (int i = 0; i < lSw.size(); i++) {  
                Element elementoSotware = documento.createElement("software");
                elementoRaiz.appendChild(elementoSotware);
                
                Element elementoNombre = documento.createElement("nombre"); 
                elementoSotware.appendChild(elementoNombre);  
                Text textoNombre = documento.createTextNode(lSw.get(i).getNombre());
                elementoNombre.appendChild(textoNombre); 
                
                Element elementoDescripcion = documento.createElement("descripcion");
                elementoSotware.appendChild(elementoDescripcion);
                Text textoDescripcion = documento.createTextNode(lSw.get(i).getDescripcion());
                elementoDescripcion.appendChild(textoDescripcion); 
                
                Element elementoVersion = documento.createElement("version");         
                elementoSotware.appendChild(elementoVersion);
                Text textoVersion = documento.createTextNode(lSw.get(i).getVersion());
                elementoVersion.appendChild(textoVersion); 
                
                Element elementoPrecio = documento.createElement("precio");
                elementoSotware.appendChild(elementoPrecio);
                Text textoPrecio = documento.createTextNode(String.valueOf(lSw.get(i).getPrecio()));
                elementoPrecio.appendChild(textoPrecio); 
                
                Element elementoRequisitos = documento.createElement("requisitos");
                elementoSotware.appendChild(elementoRequisitos);
                Text textoRequisitos = documento.createTextNode(lSw.get(i).getRequisitos());
                elementoRequisitos.appendChild(textoRequisitos); 
            }

            //Generar el tranformador para obtener el documento XML en un fichero
            TransformerFactory fábricaTransformador = TransformerFactory.newInstance();
            Transformer transformador = fábricaTransformador.newTransformer();
            //Insertar saltos de línea al final de cada línea
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            //Añadir 3 espacios delante, en función del nivel de cada nodo
            transformador.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "3");
            Source origen = new DOMSource(documento);
            Result destino = new StreamResult(act);
            transformador.transform(origen, destino);

        } catch (ParserConfigurationException ex) {
            System.out.println("ERROR: No se ha podido crear el generador de documentos XML\n"+ex.getMessage());
            ex.printStackTrace();
        } catch (TransformerConfigurationException ex) {
            System.out.println("ERROR: No se ha podido crear el transformador del documento XML\n"+ex.getMessage());
            ex.printStackTrace();
        } catch (TransformerException ex) {
            System.out.println("ERROR: No se ha podido crear la salida del documento XML\n"+ex.getMessage());
            ex.printStackTrace();
        }
    }
}
