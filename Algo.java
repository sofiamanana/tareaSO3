import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

class Hebra extends Thread{
    public Integer x;
    public ArrayList<Integer> array;
    public Integer resultado;
    public static Integer flag;

    public Hebra (Integer x, ArrayList<Integer> array){
        this.x = x;
        this.array = array;
    }

    /*
    Nombre: run
    Parametros: ninguno
    Retorno: funcion void
    Descripcion: Separa el array  en 2 sin contar el medio,si el medio calza con el numero buscado se detiene el while
    si es menor se crea una hebra izquierda con el arreglo de los menores que el del medio y si es mayor se crea una hebra derecha con arreglos mayores que los del medio
    y asi consecutivamente
    */
    
    public void run(){
        flag = 0;
        resultado = 0;
        Integer n = array.size();
        while( n != 1 && flag!=1){
            Integer med = n/2;
            if(x == array.get(med)){
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
                        return;
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
                        return;
                    }
                }catch (InterruptedException ex) {
                    Logger.getLogger(Hebra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            n = med;
        }
    }
}

public class Algo {
    public static Integer flag;
    
    /*
    Nombre: binarySearch
    Parametros: int arr[] que es el arreglo, int l que es la pos de la izq, int r pos de la derecha, int x numero a buscar
    Retorno: mid si encuentra y -1 si no
    Descripcion: Analiza el numero medio del arreglo, si calza con el buscado lo retorna y si no considera la la mitad izquiera si es menor o la mitad
    derecha si es mayor, esto hasta que no queden mas numeros en el arreglo.
    */
    
    int binarySearch(int arr[], int l, int r, int x) 
    { 
        if (r >= l) { 
            int mid = l + (r - l) / 2; 
            if (arr[mid] == x) 
                return mid; 
            if (arr[mid] > x) 
                return binarySearch(arr, l, mid - 1, x); 
            return binarySearch(arr, mid + 1, r, x); 
        } 
        return -1; 
    } 
    
    //los arreglos tienen que estar ordenados
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, ScriptException{
    
        System.out.println("Busqueda binaria con threads:");
        long TInicio1, TFin1, tiempo1; 
        TInicio1 = System.currentTimeMillis();  
        flag =0;
        Integer y = 1;
        ArrayList<Integer> array = new ArrayList<Integer>();
        
        for(int i = 0; i<11; i++){//creamos el arreglo
            array.add(i);
        }
        
        Hebra hebra = new Hebra(y,array);
        hebra.start();
        hebra.join();
        TFin1 = System.currentTimeMillis(); 
        tiempo1 = TFin1 - TInicio1;
        System.out.println("Tiempo de ejecución en milisegundos: " + tiempo1);

        //////////////////////////////////////////////////////////////////////////

        System.out.println("Busqueda binaria sin threads:");
        long TInicio, TFin, tiempo; 
        TInicio = System.currentTimeMillis();  
        Algo ob = new Algo(); 
        int arr[] = {0,1,2,3,4,5,6,7,8,9,10,11};//creamos el arreglo
        int n = arr.length; 
        int x = 1; 
        int result = ob.binarySearch(arr, 0, n - 1, x); 
        
        if (result != -1){
            System.out.println("El elemento esta en el arreglo"); 
        }
            
        TFin = System.currentTimeMillis(); 
        tiempo = TFin - TInicio;
        System.out.println("Tiempo de ejecución en milisegundos: " + tiempo);
    }
}
