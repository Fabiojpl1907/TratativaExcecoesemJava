package fj.tratarexcecao;

import javax.swing.*;

// objetivo dividir 2 numeros inteiros

public class UncheckedException {
    public static void main(String[] args) {

    boolean continueLoop = true ;

     do {
         // criar uma caixa de dialogo para efetuar os inputs
         // A classe JOptionPane nos proporciona uma série de métodos estáticos
         // que ao serem invocados criam caixas de diálogos simples e objetivas.
         // Para usar JOptionPane temos sempre que importar o pacote javax.
         // showInputDialog retorna uma string
         String a = JOptionPane.showInputDialog("Numerador : ");
         String b = JOptionPane.showInputDialog("Denominador : ");

         // A função parseInt() analisa um argumento string e retorna um inteiro na base especificada.
         // base ->  Integer
         try {
             int resultado = dividir( Integer.parseInt(a), Integer.parseInt(b));
             System.out.println("Resultado : " + resultado);

             // se entrada é  valida, calcula o resulado
             // e sai do loop
             continueLoop = false ;

         } catch (NumberFormatException e) {
             // pedir para imprimir a pilha de exceção
             // e.printStackTrace();

             JOptionPane.showMessageDialog(null,
                     "Entrada inválida, informe um numero inteiro. "+ e.getMessage());

         } catch (ArithmeticException e) {
             // pedir para imprimir a pilha de exceção
             // e.printStackTrace();

             JOptionPane.showMessageDialog(null,
                     "Entrada inválida, impossivel dividir por 0 (zero). "+ e.getMessage());
         }
         // o que fazer apos capturar  a exceção .
         // finally É opcional
         // mas se indicado será mandatoriamente executado apos a captura da exceção .
         finally {
             System.out.println("Chegou no finally");
         }

     } while(continueLoop) ;

        // dar continuidade ao código apos capturar a exceção
        System.out.println("O codigo continua...");
    }


    private static int dividir(int a, int b ) {
        return a/b;
    }

}