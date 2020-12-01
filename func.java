import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

class Func extends Thread{
    public String n_func;
    public Integer cantidad;
    public ArrayList nom_f;
    public ArrayList funciones;
    public ArrayList array_thr;
    public String resultado;

    public Func (String n_func, ArrayList nom_f, ArrayList funciones, Integer cantidad, ArrayList array_thr){
        this.cantidad = cantidad;
        this.funciones = funciones;
        this.n_func = n_func;
        this.nom_f = nom_f;
        this.array_thr = array_thr;
    }

    public void run(){
        System.out.println("MyThread running");
     }
}

public class func {
    public static void main(String[] args) throws Exception {

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
                String arg = rest[0];

                for(int i = 0; i<nom_f.size()-1; i++){
                    if(n_func.equals(nom_f.get(i))){
                        Func hebra = new Func(n_func, nom_f, funciones, cantidad, array_thr);
                        hebra.start();
                        hebra.join();
                    }
                }
            }
        }


        sc.close();
    }
}