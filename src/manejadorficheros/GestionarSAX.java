/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Adil Casamayor Silvar
 */
public class GestionarSAX {

    ManejadorSAX sh;
    SAXParser parser;
    File ficheroXML;

    public int abrir_XML_SAX(File fichero) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //Se crea un objeto SAXParser para interpretar el documento XML.
            parser = factory.newSAXParser();
            //Se crea un instancia del manejador que será el que recorra el documento XML secuencialmente
            sh = new ManejadorSAX();
            ficheroXML = fichero;
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }        
    }

    public String recorrerSAXyMostrar() {
        /*
         Se da la salida al parser para que comience a manejar el documento XML 
         que se le pasa como parámetro con el manejador que también se le pasa. 
         Esto recorrera secuencialmente el documento XML y cuando detecte un 
         comienzo o fin de elemento o un texto entonces lo tratará 
         (según la implementación hecha del manejador)
         */
        try {
            //parser.reset();
            sh.cadena_resultado = "";
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        }
    }
    
    public List<Software> obtenerSAX() {
        try {
            //parser.reset();
            sh.cadena_resultado = "";
            parser.parse(ficheroXML, sh);
            return sh.softwareList;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ManejadorSAX extends DefaultHandler {

    int ultimoelemento;
    
    String cadena_resultado;
    Software mySoftware;
    List<Software> softwareList;

    public ManejadorSAX() {
        cadena_resultado = "";
        ultimoelemento = 0;
        mySoftware = new Software();
        softwareList = new ArrayList<>();
    }

    //A continuación se sobrecargan los eventos de la clase DafaultHandler para 
    //recuperar el documento XML.
    //En la implementación de estos eventos se indica qué se hace cuando se 
    //encuentre el comienzo de un elemento(startElement),el final de un elemento 
    //(endElement)y caracteres texto (characters)
    //Este handler detecta comienzo de un elemento, final y cadenas string (texto). 
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                
        switch (qName) {
            case "softwares":
                ultimoelemento = 0;
                break;
            case "software":
                ultimoelemento = 1;
                break;
            case "nombre":
                ultimoelemento = 2;
                break;
            case "descripcion":
                ultimoelemento = 3;
                break;
            case "version":
                ultimoelemento = 4;
                break;
            case "precio":
                ultimoelemento = 5;
                break;
            case "requisitos":
                ultimoelemento = 6;
                break; 
            default:
                // Asignar valor 0 a cualquier otro elemento
                // Evita que se concatenen otros datos en el método characters()
                ultimoelemento = 0;
                break;
        }
    }
    
    //Cuando en este ejemplo se detecta el final de un elemento <libro>, se pone 
    //un línea discontinua en la salida.
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Reiniciar valor por defecto de ultimoelemento
        // Evita que se concatenen otros datos en el método characters()
        ultimoelemento = 0;
        
        switch (qName) {
                case "softwares":

                break;
            case "software":
                cadena_resultado = "";
                softwareList.add(mySoftware);
                mySoftware = new Software();
                break;
            case "nombre":
                mySoftware.setNombre(cadena_resultado);
                cadena_resultado = "";
                break;
            case "descripcion":
                mySoftware.setDescripcion(cadena_resultado);
                cadena_resultado = "";
                break;
            case "version":
                mySoftware.setVersion(cadena_resultado);
                cadena_resultado = "";
                break;
            case "precio":
                Double price = Double.parseDouble(cadena_resultado);
                mySoftware.setPrecio(price);
                cadena_resultado = "";
                break;
            case "requisitos":
                mySoftware.setRequisitos(cadena_resultado);
                cadena_resultado = "";
                break;
            default:
                break;
        }
    }

    //Cuando se detecta una cadena de texto posterior a uno de los elementos entonces guarda
    //ese texto en la variable correspondiente.
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {        
        switch (ultimoelemento) {
            case 2:case 3:case 4:case 5:case 6:
                for (int i = start; i < length + start; i++) {
                    cadena_resultado += ch[i];
                }
                break;
            default:
                break;
        }
    }
}
