package jsf;

import java.util.ArrayList;

/**
 *
 * @author Ronan
 */
public class Primo {
    
    
    public static void main(String[] args) {
        ArrayList<Integer> primos = new ArrayList<>();
        // {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}
        primos.add(2);
        primos.add(3);
        primos.add(5);
        primos.add(7);
        primos.add(11);
        primos.add(13);
        primos.add(17);
        primos.add(19);
        primos.add(23);
        primos.add(29);
        primos.add(31);
        primos.add(37);
        
        int numero = 41;
        int cont = 0;
        boolean isPrimo;
        
        while (numero <= 5002) {
            isPrimo = true;
            
            for (int primo : primos) {
                if ((numero % primo) == 0) {
                    isPrimo = false;
                    break;
                }
            }
            
            if (isPrimo) {
                if (numero > 41) {
                    System.out.print(" - "); // só pra bonito
                }
                
                if (cont == 20) {
                    System.out.println("");
                    cont = 0;
                }
                
                cont ++;
                System.out.print(numero + "");
                primos.add(numero);
            }
            
            numero ++;
        }
        
        System.out.println("\n");
    }
}
