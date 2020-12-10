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
    public Integer x;
    public ArrayList<Integer> array;
    public Integer resultado;
    public Integer flag;

    public Hebra (Integer x, ArrayList<Integer> array){
        this.x = x;
        this.array = array;
    }

    public void run(){
        flag = 0;
        resultado = 0;

        Integer n = array.size();
        while( n != 1){
            Integer med = n/2;
            if(x == array.get(med) || x==array.get(med-1)){
                //***************************************************/
                System.out.println("El elemento esta en el arreglo");
                resultado = 1;
                flag = 1;
                break;
            }
            else if(x <= array.get(med-1)){
                ArrayList<Integer> left = new ArrayList<Integer>();
                for(int i = 0; i<med; i++){
                    left.add(array.get(i));
                }
                Hebra leftH = new Hebra(x,left);
                leftH.start();
                try{
                    leftH.join();
                    if(leftH.resultado == 1){
                        flag = 1;
                        break;
                    }
                }catch (InterruptedException ex) {
                    Logger.getLogger(Hebra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(x >= array.get(med-1)){
                ArrayList<Integer> right = new ArrayList<Integer>();
                for(int i = med; i<n; i++){
                    right.add(array.get(i));
                }
                Hebra rightH = new Hebra(x,right);
                rightH.start();
                try{
                    rightH.join();
                    if(rightH.resultado == 1){
                        flag = 1;
                        break;
                    }
                }catch (InterruptedException ex) {
                    Logger.getLogger(Hebra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            n = med;
        }
    }
}

public class algo {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, ScriptException{
        Integer x = 4;
        ArrayList<Integer> array = new ArrayList<Integer>();
        for(int i = 0; i<5; i++){
            array.add(i);
        }
        System.out.println(array);
        Hebra hebra = new Hebra(x,array);
        hebra.start();
        hebra.join();
        if(hebra.flag == 0){
            System.out.println("El elemento no esta en el arreglo");
        }
    }
}
