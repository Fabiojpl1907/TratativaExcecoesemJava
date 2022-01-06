package fj.tratarexcecao;

import javax.swing.*;
import java.io.*;

public class CheckedException {

    public static void main(String[] args) {

        // criar string com o nome do arquivo a imprimir
        String nomeDoArquivo = "Cem-anos-de-Solidão.txt";

        // chama o método que ira imprimir
        // o quel esta desenvolviso logo abaixo
        try {
            imprimirArquivoNoConsole(nomeDoArquivo);
        } catch( FileNotFoundException e) {
            // colocar mensagem sobre falta de arquivo
            JOptionPane.showMessageDialog(null, "Revisar nome do arquivo que deseja imprimir");
        } catch (IOException e) {
            // colocar mensagem para outros tipos de exceções .
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado. Entre em contato com o suporte");

            // imprimir a pilha de exceção pra ajudar a identificar a causa da exception
            e.printStackTrace();

        } finally {
            System.out.println("\nChegou no finally");
        }

        // independente de dar certo ou não. O programa deve continuar
        System.out.println("\nApesar da exception ou não , o programa continua");

    }

    // Método para impressao de um arquivo ------------------------------
    public static void imprimirArquivoNoConsole( String nomeDoArquivo) throws IOException {
        // criar um objeto do tipo arquivo
        File file = new File(nomeDoArquivo);

        // colocar conteudo do arquivo no buffer
        BufferedReader br = new BufferedReader(new FileReader(file.getName()));
        // ler linha a linha do que esta no buffer
        String line = br.readLine();

        // abrir buffer para sair arquivo no console
        BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out));

        // laço que vai ler cada linha existente
        // no buffer reader e imprimir no console
        // laco termina quando linha = null - não tem mais linha a ser lida no documento
        do {
            bw.write(line);
            bw.newLine();
            line=br.readLine();
        } while ( line != null );

        // limpar buffer
        bw.flush();
        // fechar arquivo
        br.close();
    }
}

