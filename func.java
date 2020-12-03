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

class Func extends Thread{
    public String n_func;
    public Integer arg;
    public Integer i;
    public ArrayList<String> nom_f;
    public ArrayList<String> funciones;
    public ArrayList<Thread> array_thr;
    public String resultado;

    public Func (String n_func, ArrayList<String> nom_f, ArrayList<String> funciones, Integer arg, ArrayList<Thread> array_thr, Integer i){
        this.arg = arg;
        this.funciones = funciones;
        this.n_func = n_func;
        this.nom_f = nom_f;
        this.array_thr = array_thr;
        this.i = i;
    }

    public void run(){
        String func_e = funciones.get(i);
        String[] div = func_e.split("[\\*\\/\\+\\-]");
        ScriptEngineManager engincito = new ScriptEngineManager();
        ScriptEngine engine = engincito.getEngineByName("JavaScript");
        
        for(int i = 0; i<div.length-1; i++){
            if(div[i].contains("(x)")){
                String[] div2 = div[i].split("\\(x\\)");
                for(int k=0; k<nom_f.size()-1; k++){
                    if(div2[0].equals(nom_f.get(k))){
                        Func hebra = new Func(div2[0],nom_f,funciones,arg,array_thr,k);
                        hebra.start();

                    }
                }
            }
            /*
            try{
                String[] div2 = div[i].split("\\(");
                for(int k = 0; k<nom_f.size()-1; k++){
                    if(div2[0].equals(nom_f.get(k))){
                        Func hebra = new Func(div2[0], nom_f, funciones, cantidad, array_thr,k);
                        hebra.start();
                        hebra.join();
                        System.out.println(hebra.resultado);
                    }
                }

            }catch (InterruptedException ex) {
            Logger.getLogger(Func.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        }
        


        
        Integer resultado = 0;
     }
}

public class func {
    public static void main(String[] args) throws Exception, ScriptException {

        int cantidad;
        ArrayList<Thread> array_thr = new ArrayList<Thread>();
        ArrayList<String> nom_f = new ArrayList<String>();
        ArrayList<String> funciones = new ArrayList<String>();

        String dir = new File("funciones.txt").getAbsolutePath();
        File fichero = new File(dir);
        Scanner sc = new Scanner(fichero);

        //Guardar la cantidad de funciones.
        String line = sc.nextLine();
        cantidad = Integer.valueOf(line);
        System.out.println("Funciones ingresadas: ");

        while(sc.hasNextLine()){
            line = sc.nextLine();
            System.out.println(line);
            String[] parts = line.split("=");
            nom_f.add((parts[0].split("\\("))[0]);
            funciones.add(parts[1]);
            
        }

        System.out.println(funciones.get(0));
        
        Scanner input = new Scanner (System.in);
        String a = "";

        while(!a.equals("exit")){
            String eleccion = "ERRORR";
            System.out.println("Escriba la operacion a realizar:");
            System.out.println("exit para salir.");
            a = input.nextLine();
            if(a.equals("exit")){
                continue;
            }
            else{
                String[] n_f = a.split("\\(");
                String[] rest = n_f[1].split("\\)");
                String n_func = n_f[0];
                Integer arg = Integer.valueOf(rest[0]);
                System.out.println(arg);

                for(int i = 0; i<nom_f.size()-1; i++){
                    if(n_func.equals(nom_f.get(i))){
                        Func hebra = new Func(n_func, nom_f, funciones, arg, array_thr,i);
                        hebra.start();
                        hebra.join();
                        System.out.println(hebra.resultado);
                    }
                }
            }
        }


        sc.close();
    }
}