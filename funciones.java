import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Hebra extends Thread{
  public String numero;
  public String eleccion;
  public ArrayList<String> funciones;
  public ArrayList<Thread> funciones_lista;
  public ArrayList<String> posibles;
  public String resultado;

  //Funcion constructor del objeto Hebra.

  public Hebra (String eleccion, ArrayList<String> funciones, ArrayList<String> posibles, String numero, ArrayList<Thread> funciones_lista){
      this.numero = numero;
      this.funciones = funciones;
      this.eleccion = eleccion;
      this.posibles = posibles;
      this.funciones_lista = funciones_lista;
  }

  //Main del objeto.

  public void run(){

      String str = "";
      String pedido = (String) funciones.get(posibles.indexOf(eleccion));
      String[] dividir = pedido.split("[\\*\\+\\-\\/]");
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("js");

      //Se recorre el arreglo donde esta dividida la funcion que ingreso el usuario, se divide por los signos matematicos dentro.

      for(int i = 0; i < dividir.length; i++){
        Pattern pat = Pattern.compile("[a-w y-z]+");  //Se genera un patron donde se excluye el x, asi se puede identificar el nombre de las funciones.
        Matcher mat = pat.matcher(dividir[i]);

        if (mat.find()) {
          str = mat.group();
        }

        //Si se encuentra un nombre de funcion, entra al if.

        if(posibles.contains(str)){

          //Se genera una hebra para la nueva funcion y se inicia.

          Hebra hebra = new Hebra(str, funciones, posibles, numero, funciones_lista); 
          hebra.start(); 

          try {

            //Se hace un join y se recupera el valor obtenido en la resolucion de la hebra (resultado).

            hebra.join();

            //En pedido se guarda la funcion original, se van reemplazando los (x) con el resultado de la hebra y asi se va 
            //formando una expresion sin funciones en el medio, solo matematicas.

            pedido = pedido.replace(str + "(x)", hebra.resultado);
            str = "";

          } catch (InterruptedException ex) {
              Logger.getLogger(Hebra.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }

      //Con ScriptEngine se resuelve las operaciones, toda hebra que llega a este punto no tiene una funcion en la variable pedido,
      //por lo que se puede resolver.

      engine.put("x", numero);
      Object operation = null;

      try {
        operation = engine.eval(pedido);
      } catch (ScriptException ex) {
        Logger.getLogger(Hebra.class.getName()).log(Level.SEVERE, null, ex);
      }

      //Al final el valor es retornado en la variable resultado.

      resultado = Double.toString((double) operation);
  }
}

public class funciones {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, ScriptException {

       ArrayList<Thread> funciones_lista = new ArrayList<Thread>();
       ArrayList<String> posibles = new ArrayList<String>();
       ArrayList<String> funciones = new ArrayList<String>();

       //Se abre el archivo con las funciones, se lee y se guarda el contenido en los ArrayList posibles y funciones.
       //posibles contiene el nombre de las funciones, y funciones tiene el contenido de las funciones (lo que viene despues del igual).

       String dir = new File("funciones.txt").getAbsolutePath();  
       File fich = new File(dir);
       Scanner sc = new Scanner(fich);

       System.out.println("Funciones registradas: ");         
       String linea = sc.nextLine();

      while(sc.hasNextLine()){
        linea = sc.nextLine();
        System.out.println(linea);

        if(linea.length() > 0){                      
          String separar[] = linea.split("\\(.\\)=");	

          for (int i=0; i<separar.length-1; i++){
            posibles.add(separar[0]);
            funciones.add(separar[1]);
          }

        }
      }

      Scanner input = new Scanner (System.in);
      String accion = "";

      while(!accion.equals("salir")){

        String eleccion = "";
        System.out.println("Escriba su operacion:");
        System.out.println("Para terminar el programa, escriba salir.");
        accion = input.nextLine();

        if(accion.equals("salir")){
          continue;
        }

        else if(accion.length() < 3){
          System.out.println("Formato no valido, debe ser 'funcion(numero)' ");
        }

        else{

          //Se divide lo que ingresa el usuario, y se genera un patron para buscar el nombre de la funcion, al igual que en el 
          //objeto Hebra.

          String[] dividir2 = accion.split("\\(");
          String[] dividir = dividir2[1].split("\\)");
          Pattern pat = Pattern.compile("[a-w y-z]+");  
          Matcher mat = pat.matcher(accion);

          if(mat.find()){
          eleccion = mat.group();
          }

          if(!posibles.contains(eleccion)){
            System.out.println("La funcion no esta registrada\n");
          }

          //Si existe la funcion, genera un objeto Hebra que resuelve lo pedido.

          else{
            Hebra hebra = new Hebra(eleccion, funciones, posibles, dividir[0], funciones_lista);
            hebra.start();
            hebra.join();
            System.out.println("Su resultado final es: "+ hebra.resultado+"\n");
          }
      }
     }

     input.close();
     sc.close();

  }
}