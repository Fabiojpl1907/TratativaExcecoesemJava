# Tratativa de Exceções em Java

## Exception Personalizada

É a criação  de uma rotina personalizada de tratamento de exceção pelo próprio desenvolvedor. 

A classe criada deverá extender a classe Exception do Java. 

## Caso de Estudo 

Caso de Estudo : Construido a partir do Curso  DiO "Tratamento de Exceção", da Prof. Camila 

Objetivo do Programa : Imprimir um arquivo no console (Input / output )

Texto : foi criado um arquivo em formato texto plano -> Cem-anos-de-Solidão.txt ( ver no repositório inficado ao final do artigo )

```
Nota: Detalhes sobre tratamento de input / output ver curso da Dio "Entrada e Saida de Arquivos em Java", com a Camila. não esta no escopo deste trabalho detalhar este tema .  
```



### Codigo Inicial 

O codigo abaixo , esta construído para permitir a impressão de um arquivo . 

Nele temos : 

- Tratamento da exceção dentro do imprimirArquivoNoConsole(), sem lançar para o metodo chamador

- Feito desacoplamento das funções ler arquivo e apresentar arquivo , o que é uma boa prática.

  - lerArquivo() -> le o arquivo

  - imprimirArquivoNoConsole() -> apresenta o arquivo

- Devido ao desacoplamento , a exceção de arquivo não encontrado será depois tratada dentro do lerArquivo(), pois é este metodo que faz a pesquisa e leitura do arquivo 

```
import javax.swing.*;
import java.io.*;

public class ExceptionPersonalizada {

        public static void main(String[] args) {
            String nomeDoArquivo = JOptionPane.showInputDialog("Nome do arquivo 
                                                                a ser exibido: ");

            imprimirArquivoNoConsole(nomeDoArquivo);
            System.out.println("\nCom exception ou não, 
                               o programa continua...");
        }

        public static void imprimirArquivoNoConsole(String nomeDoArquivo) {

            try {
                BufferedReader br = lerArquivo(nomeDoArquivo);
                String line = br.readLine();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
                do {
                    bw.write(line);
                    bw.newLine();
                    line = br.readLine();
                } while (line != null);
                bw.flush();
                br.close();
            }catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ocorreu um erro não esperado, 
                        por favor, fale com o suporte." + ex.getMessage());
                        
                ex.printStackTrace();
            }
        }

        public static BufferedReader lerArquivo(String nomeDoArquivo){
            File file = new File(nomeDoArquivo);
            return new BufferedReader(new FileReader(nomeDoArquivo));
        }
}
```



### Tratar Excecão - File not Found  

a. Criar bloco try/catch no metodo lerarquivo() 

```
public static BufferedReader lerArquivo(String nomeDoArquivo) {

    File file = new File(nomeDoArquivo);
    try {
        return new BufferedReader(new FileReader(nomeDoArquivo));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
```

b. Criar uma exception personalizada

Lembrando que Exception é uma classe java, posso criar uma classe própria extendendo exception . 

para ficar tudo dentro do mesmo arquivo , vamos criar a apos a classe  ExceptionPersonalizada1.

Como boa prática no nome desta classe , colocamos a palavra exception. 

```
class ImpossivelAberturaDeArquivoException extends Exception {
	
		// atributos
    private String nomeDoArquivo;
    private String diretorio;

    // construtor 
    public ImpossivelAberturaDeArquivoException(String nomeDoArquivo, String diretorio) {
    
        super("O arquivo " + nomeDoArquivo + " não foi encontrado no diretório " 
              + diretorio);
        this.nomeDoArquivo = nomeDoArquivo;
        this.diretorio = diretorio;      
    }
    
    // como estamos extendendo a classe Excpetion
    // podemos, por exemplo sobreescrever os metodos da classe Exception
    @Override
    public String toString() {
        return "ImpossivelAberturaDeArquivoException{" +
                "nomeDoArquivo='" + nomeDoArquivo + '\'' +
                ", diretorio='" + diretorio + '\'' +
                '}';
    }
        
}   
```



c. Alterar o bloco try/catch para lançar a exception personalizada 

```
nota : Os metodos getName() e getPath() são da classe File 
```

```
File file = new File(nomeDoArquivo);
try {
    return new BufferedReader(new FileReader(nomeDoArquivo));
} catch (FileNotFoundException e) {
    throw new ImpossivelAberturaDeArquivoException(file.getName(), file.getPath());
}
```



d. Ao alterar o corpo do Try/catch sera apresentado um alerta , pois precisamos  fazer o metodo lerarquivo() lançar o tratamento para a exception personalizada . 

asim temos

```
public static BufferedReader lerArquivo(String nomeDoArquivo) throws ImpossivelAberturaDeArquivoException {

    File file = new File(nomeDoArquivo);
    try {
        return new BufferedReader(new FileReader(nomeDoArquivo));
    } catch (FileNotFoundException e) {
        throw new ImpossivelAberturaDeArquivoException(file.getName(), file.getPath());
    }
}
```



e. proximo passo é ajustar o metodo imprimirArquivoNoConsole(), para tambem tratar com a exception personalizada . 

Como a exception personalizada é filha da classe Exception Java , o tratamento dela precisa vir antes de IOException. 

```
public static void imprimirArquivoNoConsole(String nomeDoArquivo) {

    try {
        BufferedReader br = lerArquivo(nomeDoArquivo);
        String line = br.readLine();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        do {
            bw.write(line);
            bw.newLine();
            line = br.readLine();
        } while (line != null);
        bw.flush();
        br.close();
    } catch (ImpossivelAberturaDeArquivoException e) {
         // vai apresentar a mensagem construida na exception personalizada
        JOptionPane.showMessageDialog(null,e.getMessage());
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null,
                "Ocorreu um erro não esperado, por favor, fale com o suporte." + ex.getMessage());
        ex.printStackTrace();
    }
}
```



### Código Final   

O nome do arquivo sera passado por uma caixa de input.

```
import javax.swing.*;
import java.io.*;

public class ExceptionPersonalizada {

        public static void main(String[] args) {
            String nomeDoArquivo = JOptionPane.showInputDialog("Nome do arquivo 
                                                                   a ser exibido: ");

            imprimirArquivoNoConsole(nomeDoArquivo);
            System.out.println("\nCom exception ou não, o programa continua...");
        }

        public static void imprimirArquivoNoConsole(String nomeDoArquivo) {

            try {
                BufferedReader br = lerArquivo(nomeDoArquivo);
                String line = br.readLine();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
                do {
                    bw.write(line);
                    bw.newLine();
                    line = br.readLine();
                } while (line != null);
                bw.flush();
                br.close();
            } catch (ImpossivelAberturaDeArquivoException e) {
                // vai apresentar a mensagem construida na exception personalizada
                JOptionPane.showMessageDialog(null,e.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ocorreu um erro não esperado, por favor, fale com o suporte." 
                               + ex.getMessage());
                ex.printStackTrace();
            }
        }

        public static BufferedReader lerArquivo(String nomeDoArquivo) throws ImpossivelAberturaDeArquivoException {

            File file = new File(nomeDoArquivo);
            try {
                return new BufferedReader(new FileReader(nomeDoArquivo));
            } catch (FileNotFoundException e) {
                throw new ImpossivelAberturaDeArquivoException(file.getName(),
                        file.getPath());
            }
        }
    }

    class ImpossivelAberturaDeArquivoException extends Exception {

        private String nomeDoArquivo;
        private String diretorio;

        public ImpossivelAberturaDeArquivoException(String nomeDoArquivo, String diretorio) {
            super("O arquivo " + nomeDoArquivo + " não foi encontrado no diretório " 
                    + diretorio);
            this.nomeDoArquivo = nomeDoArquivo;
            this.diretorio = diretorio;
        }

    @Override
    public String toString() {
        return "ImpossivelAberturaDeArquivoException{" +
                "nomeDoArquivo='" + nomeDoArquivo + '\'' +
                ", diretorio='" + diretorio + '\'' +
                '}';
    }

}
```



Para o trabalho completo de tratativa de exceções ver : https://github.com/Fabiojpl1907/TratativaExcecoesemJava