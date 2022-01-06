# Tratativa de Exceções em Java

## Checked Exception



Checked Exceptions DEVEM ser evitados e tratados pelo desenvolvedor para o programa funcionar

Representam condições inválidas em áreas fora do controle imediato do programa (problemas de  banco de dados, falhas de rede, arquivos ausentes).

Java verifica este tipo de exceções em tempo de compilação. Algumas checked excepetions  comuns em Java são IOException , SQLException  e ParseException .

## Caso de Estudo 

Caso de Estudo : Construido a partir do Curso  DiO "Tratamento de Exceção", da Prof. Camila 

Objetivo do Programa : Imprimir um arquivo no console (Input / output )

Texto : foi criado um arquivo em formato texto plano -> Cem-anos-de-Solidão.txt

```
Nota: Detalhes sobre tratamento de input / output ver curso da Dio "Entrada e Saida de Arquivos em Java", com a Camila. não esta no escopo deste trabalho detalhar este tema .  
```



### Codigo Inicial 

O codigo abaixo , esta construido para permitir a impressão de um arquivo . Veja os comentários no proprio código para entender sua estrutura . 

```
import java.io.*;

public class CheckedException {

    public static void main(String[] args) {
    
    		// criar string com o nome do arquivo a imprimir 
        String nomeDoArquivo = "Cem-anos-de-Solidão.txt";
        
        // chama o método que ira imprimir
        // o quel esta desenvolviso logo abaixo 
        imprimirArquivoNoConsole(nomeDoArquivo);

				 // independente de dar certo ou não. O programa deve continuar 
        System.out.println("\nApesar da exception ou não , o programa continua");

    }

		// Método para impressao de um arquivo ------------------------------
    public static void imprimirArquivoNoConsole( String nomeDoArquivo) {
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

```



### Indicação de  Exceções 

A IDE indica onde há exceções a serem tratadas ( checked excepciona) ,a partir dos metodos java utilizados , pois foi identificado que há pontos em que poderemos ter problema , por exemplo uma falta de aquivo . Assim , não devemos ficar parados e esperar o problema , e sim nos adiantarmos e tratamos ele . 

Na imagem abaixo temos uma destas indicações da IDE . NO caso "Filereader(), com a exceção FuleNotFoundException

![Screen Shot 2022-01-06 at 11.24.46](https://tva1.sinaimg.cn/large/008i3skNgy1gy4bjfkb2qj30cb0b8ab5.jpg)



```
Dica : Ande pelo código na IDE e abra os outros alertas apresentados . 
```

Se mesmo  com estes alertas , o código for rodado , o programa não será compilado( Java verifica este tipo de exceções em tempo de compilação) e a receberá as seguintes mensagens, mostando onde esta as exceção a serem resolvida  . 

![Screen Shot 2022-01-06 at 11.36.27](https://tva1.sinaimg.cn/large/008i3skNgy1gy4bviltzcj30jh05pt9l.jpg)





### Lançar Exception  - Lançar a Exception no método main 

Lançar uma exceção, por exemplo *throws FileNotFoundException*, é enviar a obrigatoriedade de tratamento ao metodo chamador do metodo onde a exceção pode ocorrer . 

No caso de estudo : 

- A exceção pode ocorrer no método *imprimirArquivoNoConsole()*, pois é ele que trata do arquivo a ser apresentado. 
- E o método main , chama  *imprimirArquivoNoConsole(nomeDoArquivo);*

- Assim metodo imprimirArquivoNoConsole() ao lançar a exceção , transfere ao método  "main" a obrigatoriedade de tratar a exceção . 
  

a. Lançar a exceção de FileReader no metodo imprimir . Click na opção Add exception to method signature ( ver imagem )

![Screen Shot 2022-01-06 at 11.41.44](https://tva1.sinaimg.cn/large/008i3skNgy1gy4c0vsfm0j30bf02jdfw.jpg)

neste momento o metodo imprimir lança a exceção FileNotFoundException. 

```
public static void imprimirArquivoNoConsole( String nomeDoArquivo) throws FileNotFoundException {
```



Após isto,  no metodo Main , onde chamamos o metodo imprimir passamos a ter um alerta 
![Screen Shot 2022-01-06 at 11.44.34](https://tva1.sinaimg.cn/large/008i3skNgy1gy4c494yqtj30f003ojro.jpg)



b. Lançar a exceção de readLine no metodo imprimir . Click na opção Add exception to method signature ( ver imagem )

![Screen Shot 2022-01-06 at 11.49.26](https://tva1.sinaimg.cn/large/008i3skNgy1gy4c8yy6jgj30d002ewei.jpg)



neste momento o metodo imprimir lança a exceção IOException.

```
public static void imprimirArquivoNoConsole( String nomeDoArquivo) throws IOException {
```

O lançamento FileNotFoundException, desaparece , pois dentro da hierarquica de exceções , ele esta abaixo ( é filha ) de IOException . Ou seja lançando o IOException, a FileNotFoundException será tambem lançada .

Alem disso , os ouros alertas que estavão tambem ligados a IO foram resolvidos. 



c. Lançar a exceção no metodo main . Click na opção Add exception to method signature ( ver imagem )

![Screen Shot 2022-01-06 at 11.58.50](https://tva1.sinaimg.cn/large/008i3skNgy1gy4cji94jij30bh02ljri.jpg)

neste momento o metodo main  lança a exceção IOException.

```
public static void main(String[] args) throws IOException {
```



Os alertas acabam , e se rodar o programa , e o  arquivo existir , sera apresentado no console . 

Caso o arquivo solicitado não exista será apresentada  a "pilha de exceção" indicando oque aconteceu ( para pilha  de exceção reveja o artigo Unchecked Exception)





### Tratativa de Exception  I 

a. vamos retirar o *throws IOException* do metodo main , pois agora será  tratada a exception ou inves de só lança-la. 

```
public static void main(String[] args) {
```



veja que a IDE voltou a reclamar da necessidade de adr atenção a Exception. 

![Screen Shot 2022-01-06 at 16.37.19](https://tva1.sinaimg.cn/large/008i3skNgy1gy4kkvzcrij30fl02amx9.jpg)



b. ir em "more actions" , e escolher a opção Surround with try /catch ( ou pode digita na mão conforme codigo abaixo )

o que era : 

```
imprimirArquivoNoConsole(nomeDoArquivo);
```

fica : 

```
try {
    imprimirArquivoNoConsole(nomeDoArquivo);
} catch (IOException e) {
    e.printStackTrace();
}
```



c. Passar uma mensagem mais  amigavel para o usuário

```
try {
    imprimirArquivoNoConsole(nomeDoArquivo);
} catch (IOException e) {
    // colocar mensagem para o usuário
    JOptionPane.showMessageDialog(null, "Revisar nome do arquivo que deseja imprimir");
} finally {
    System.out.println("\nChegou no finally");
}
```

```
Nota: até aqui só tratamos a exceção de arquivo não encontrado .  
```



### Tratativa de Exception II  

Outras exceções pode ocorrer , pois o nome do arquivo pode estar certo , mas vir apresentar algum problema de leitura ou outra situação que impeça dele ser lido ou apresentado. 



a. alterar o codigo  para tratar especificamente a exceção de falta de arquivo ( FileNotFoundException ) , e tratar as outras exceções ( IOException). 

```
try {
    imprimirArquivoNoConsole(nomeDoArquivo);
} catch( FileNotFoundException e) {
    // colocar mensagem sobre falta de arquivo
    JOptionPane.showMessageDialog(null, "Revisar nome do arquivo que deseja imprimir");
} catch (IOException e) {
    // colocar mensagem para outros tipos de exceções . 
    JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado. 
                                         Entre em contato com o suporte");
                                                 
    // imprimir a pilha de exceção pra ajudar a identificar a causa da exception
    e.printStackTrace();
    
} finally {
    System.out.println("\nChegou no finally");
}
```

```
Nota : A sequencia de catchs deve obdecer a hierarquia inversa entre a exceptivo. Primeiros as filhas , depois o pai. 

catch com -> FileNotFoundException ( filha )  deve vir primeiro que o catch com -> IOException ( classe pai ) .É questão de filtrar da mais específica , para a mais genérica.

Dica : tente inverter e veja o que acontece.  
```

### Código Final   

Código postado aqui sem os comentários  para ficar mais limpo de verificar a estrutura . 



```
import javax.swing.*;
import java.io.*;

public class CheckedException {

    public static void main(String[] args) {

        String nomeDoArquivo = "Cem-anos-de-Solidão.txt";

        try {
            imprimirArquivoNoConsole(nomeDoArquivo);
        } catch( FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Revisar nome do arquivo que deseja imprimir");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado. Entre em contato com o suporte");
            e.printStackTrace();

        } finally {
            System.out.println("\nChegou no finally");
        }

        System.out.println("\nApesar da exception ou não , o programa continua");
    }

    // Método para impressao de um arquivo ------------------------------
    public static void imprimirArquivoNoConsole( String nomeDoArquivo) throws IOException {

        File file = new File(nomeDoArquivo);

        BufferedReader br = new BufferedReader(new FileReader(file.getName()));
        String line = br.readLine();
        BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out));

        do {
            bw.write(line);
            bw.newLine();
            line=br.readLine();
        } while ( line != null );

        bw.flush();
        br.close();
    }
}
```



Para o trabalho completo de tratativa de exceções ver : https://github.com/Fabiojpl1907/TratativaExcecoesemJava