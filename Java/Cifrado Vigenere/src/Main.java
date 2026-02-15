import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opt,position;
        boolean operacion = true;
        String message = "",key = "";
        char Caracter;
        BigVigenere vine = new BigVigenere();
        System.out.println("Bienvenido al Cifrado de Vinegere! \n");
        while(operacion){
            System.out.printf("%nOpciones Disponibles: %n 0.Salir del sistema: %n 1.Ingresar Clave de Cifrado: %n 2.Ingresar Mensaje a Cifrar: %n 3. Desencriptar ultimo mensaje: %n 4. Volver a Encriptar el Mensaje Guardado: %n 5.Encontrar un Caracter especifico segun la posicion indicada de forma optima: %n 6.Encontrar un Caracter especifico segun la posicion indicada de manera iterada: %n");
            opt = sc.nextInt();
            sc.nextLine();

            switch(opt){
                case 0:
                    operacion = false;
                    break;
                case 1:
                    System.out.printf("%nIngrese llave para el cifrado de Vigenere: ");
                    key = sc.nextLine();
                    vine = new BigVigenere(key);
                    break;
                case 2:
                    if (vine.getKeyLength() == 0) {
                        System.out.println("Primero debes ingresar una clave.");
                        break;
                    }else{
                        System.out.printf("%nIngrese mensaje para el cifrado de Vigenere:");
                        message = sc.nextLine();
                        long inicio = System.nanoTime();
                        message = vine.Encrypt(message);
                        long fin = System.nanoTime();
                        long tiempoEjecucion = fin - inicio;
                        System.out.printf("%nEl mensaje Encriptado es: %n%s%n",message);
                        System.out.printf("Tiempo de ejecución: (%.6f milisegundos)%n", tiempoEjecucion / 1_000_000.0);
                        break;
                    }
                case 3:
                    if (message.isEmpty()) {
                        System.out.println("No hay mensaje para desencriptar.");
                        break;
                    }else{
                        System.out.printf("%nEl mensaje Desencriptado es: %n%s%n",vine.Decrypt(message));
                        break;
                    }

                case 4:
                    if (vine.getKeyLength() == 0) {
                        System.out.println("Error: No se ha ingresado una clave aún.");
                        break;
                    }

                    else{
                        System.out.printf("%nIngrese el Mensaje Encriptado:");
                        message = sc.nextLine();
                        if (message.trim().isEmpty()) {
                            System.out.println("Error: El mensaje no puede estar vacío.");
                            break;
                        }
                        message = vine.Decrypt(message);
                        System.out.printf("%nIngrese la Nueva llave para el cifrado de Vigenere: ");
                        key = sc.nextLine();
                        vine = new BigVigenere(key);
                        message = vine.Encrypt(message);
                        System.out.printf("%nEl Mensaje Encriptado con la Nueva llave es: %s%n",message);
                        break;
                    }
                case 5:
                    System.out.printf("%nIngrese la posicion del Caracter: %n");
                    position = sc.nextInt();
                    sc.nextLine();
                    if(position<0 || position>63){
                        System.out.printf("%n Error: La posicion indicada es mayor al alcance de la matriz. %n");
                        break;
                    }
                    Caracter = vine.optimalSearch(position);
                    System.out.printf("%nEl Caracter que se encuentra en la posicion: %s es: %c %n",position,Caracter);
                    break;
                case 6:
                    System.out.printf("%nIngrese la posicion del Caracter: %n");
                    position = sc.nextInt();
                    sc.nextLine();
                    if(position < 0 || position >63){
                        System.out.printf("%n Error: La posicion indicada es mayor al alcance de la matriz. %n");
                        break;
                    }
                    Caracter = vine.Search(position);
                    System.out.printf("%nEl Caracter que se encuentra en la posicion: %s es: %c %n",position,Caracter);
                    break;
                default:
                    System.out.printf("%nOpcion no valida. %n");
                    break;
            }

        }
        sc.close();
    }
}


class BigVigenere {
    private static int[] Key;
    protected static char[][] alphabet = {
            {'0', '1', '2',
                    '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l','m', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z'},

            {'0', '1', '2',
                    '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l','m', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z'}

    };

    public int getKeyLength() {
        return Key.length;
    }

    public BigVigenere() {
        Key = new int[0];
    }

    public BigVigenere(String key) {
        Key = new int[key.length()];
        for(int i = 0; i < key.length(); i++) {
            int aux = getCharIndex(key.charAt(i));
            if (aux == -1) {
                aux = 0;
            }
            Key[i] = aux;
        }
    }
    public String Encrypt(String message) {
        int ml = message.length();
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < ml; i++) {
            char currentChar = message.charAt(i);
            int keyIndex = Key[i % Key.length];

            int charIndex = getCharIndex(currentChar);

            if (charIndex == -1) {
                encryptedMessage.append(currentChar);
            } else {
                int newIndex = (charIndex + keyIndex) % alphabet[0].length;
                encryptedMessage.append(alphabet[0][newIndex]);
            }
        }
        return encryptedMessage.toString();
    }

    public String Decrypt(String message){
        int ml = message.length();
        StringBuilder DecryptedMessage = new StringBuilder();
        for (int i = 0; i < ml; i++) {
            char currentChar = message.charAt(i);
            int keyIndex = Key[i % Key.length];

            int charIndex = getCharIndex(currentChar);

            if (charIndex == -1) {
                DecryptedMessage.append(currentChar);
            } else {
                int newIndex = (charIndex - keyIndex + alphabet[1].length) % alphabet[1].length;
                DecryptedMessage.append(alphabet[1][newIndex]);
            }
        }
        return DecryptedMessage.toString();
    }
    public int getCharIndex(char c) {
        for (int i = 0; i < alphabet[0].length; i++) {
            if (alphabet[0][i] == c) {
                return i;
            }
        }
        return -1;
    }

    public char Search(int position){
        char character = ' ';
        for(int i=0;i<=position;i++){
            character = alphabet[0][i];
        }
        return character;
    }

    public char optimalSearch(int position){
        char character;
        character = alphabet[0][position];
        return character;
    }
}
