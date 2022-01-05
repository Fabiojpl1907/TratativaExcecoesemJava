# Tratativa de Exceções em Java

## Unchecked Exception



Exceção não verificada ( Unchecked Exception ) em Java são aquelas exceções cujo tratamento é não é verificada durante o tempo de compilação.  Essas exceções ocorrem devido à "má programação".

O programa não dará um erro de compilação, pois estruturalmente o codigo esta corretamente escrito segundo as regras java, porem o mesmo traz um erro de lógica / regra de negócio "escondido" que poderá causar um problema em tempo de execução . Sera desenvolvido um caso de estudo detalhado abaixo . 

Unchecked Exceptions que PODEM ser evitadas se forem tratados e analisados pelo desenvolvedor. São identificadas em tempo de execução ( Runtime ).

## Caso de Estudo 

Caso de Estudo : Construido a partir do Curso  DiO "Tratamento de Exceção", da Prof. Camila 

Objetivo do Programa : receber e dividir 2 numeros apresentando o  resultado 

### Codigo Inicial 

No codigo abaixo , é esperado fornecer um numero inteiro, porem não é validada a entrada, o que pode causar um exceção na hora de rodar o programa, caso seja fornecido algo diferente de um inteiro ( double, float , string, etc ) 

```
import javax.swing.*;

public class UncheckedException {
    public static void main(String[] args) {
    
        String a = JOptionPane.showInputDialog("Numerador : ");
        String b = JOptionPane.showInputDialog("Denominador : ");

       int resultado = dividir( Integer.parseInt(a), Integer.parseInt(b));
       System.out.println("Resultado : " + resultado);

    } 

    private static int dividir(int a, int b ) {
        return a/b;
    }

}
```



```
Notas   // A classe JOptionPane nos proporciona uma série de métodos estáticos
        // que ao serem invocados criam caixas de diálogos simples e objetivas.
        // Para usar JOptionPane temos sempre que importar o pacote javax.
        // showInputDialog retorna uma string
        
				// A função parseInt() analisa um argumento string e retorna um inteiro na base especificada, no caso Integer
```

### Pilha de Exceção 

Ao rodarmos o programa e fornecermos um entrada não esperada, o progarama "dá pau" e  é gerada  uma "Pilha de exceção" . 

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "Texto"
at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
at java.base/java.lang.Integer.parseInt(Integer.java:652)
at java.base/java.lang.Integer.parseInt(Integer.java:770)
at fj.tratarexcecao.UncheckedException.main(UncheckedException.java:20)
```

Vamos aprender a intepreta-la . 

Regra 1 , ***leia de baixo para cima*** . Que é a sequencia de eventos ocorridos na exceção 

**a.** A exceção ocorreu no pacote: fj.tratarexcecao classe: UncheckedExceptio.main linha: 20

click sobre UncheckedException.java:20, e voce será levado a linha do codigo

```
at fj.tratarexcecao.UncheckedException.main(UncheckedException.java:20)
```



**b.**  As 3 linhas abaixo mostram o trajeto percorrido pelo compilador

atravez dos pacote java indicados em cada linha .

click nos links:  Integer.java:770, Integer.java:652 ou NumberFormatException.java:65

e sera levado ao codigo das classes do java

```
at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
at java.base/java.lang.Integer.parseInt(Integer.java:652)
at java.base/java.lang.Integer.parseInt(Integer.java:770)
```



**c.**  Mostra a exception e o que esta errado
	Exception -> NumberFormatException
    o que ocorreu de inesperado : For input string: "1.1'

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "Texto"
```



### Tratativa  I - Bloco Try / Catch

Para que isto não ocorra precisamos tratar o trecho onde pode ocorrer a exceção . 

Usando **try / cath** para envolver o trecho , capturar e trata a exceção . Veja o pedaço do código abaixo .

As declarações **try**... **catch** marcam um bloco de declarações para testar (**try**), e especifica uma resposta, caso uma exceção seja lançada.

    import javax.swing.*;
    
    public class UncheckedException {
        public static void main(String[] args) {
        
            String a = JOptionPane.showInputDialog("Numerador : ");
            String b = JOptionPane.showInputDialog("Denominador : ");
    
            try {
                int resultado = dividir( Integer.parseInt(a), Integer.parseInt(b));
                System.out.println("Resultado : " + resultado);
            } catch (NumberFormatException e) {
                // pedir para imprimir a pilha de exceção
                e.printStackTrace();
            } finally {            
                System.out.println("Chegou no finally");
            }
    
    				System.out.println("Código continua");
        } 
    
        private static int dividir(int a, int b ) {
            return a/b;
        }
    
    }


​        

```
Nota : Ao fecharmos com Try , a propria IDE sugere colocar o catch e identifica o tipo de exceção que pode ocorrer . 
```

![Screen Shot 2022-01-05 at 16.31.54](https://tva1.sinaimg.cn/large/008i3skNgy1gy3essg0mlj309403qgln.jpg)



```
Finally : o que fazer apos capturar  a exceção .  É opcional, mas se indicado será mandatoriamente executado após a captura da exceção . 
```

Neste caso, ao rodar o programa , será apresentada a pilha de exceção , mas o programa continua. 



### Tratativa   II - Mensagem ao usuário

Melhorar a tratativa de exceção , alterando o Catch para apresentar uma mensagem ao usuário . 

```
import javax.swing.*;

// objetivo dividir 2 numeros inteiros

public class UncheckedException {
    public static void main(String[] args) {
        String a = JOptionPane.showInputDialog("Numerador : ");
        String b = JOptionPane.showInputDialog("Denominador : ");
        
        try {
            int resultado = dividir( Integer.parseInt(a), Integer.parseInt(b));
            System.out.println("Resultado : " + resultado);
            
        } catch (NumberFormatException e) {
            
            // vamos mostrar uma mensagem ao usuario
            JOptionPane.showMessageDialog(null,
                    "Entrada inválida, informe um numero inteiro. "+ e.getMessage());

        } finally {   // dar continuidade no código após imprimir pilha
            System.out.println("Chegou no finally");
        }
        System.out.println("O codigo continua...");
    }
    
    private static int dividir(int a, int b ) {
        return a/b;
 }
```

```
Notas :

// o retorno "e" é um objeto
// e.getMessage() aqui apresentado para efeito de estudo
// não é normalmente mostrado ao usuario devido o seu formato de apresentação
```



### Tratativa III  - E se for fornecido denominador = 0 ( zero )

Lembrando : *Regra de negócio* da operação de divisão : proibido dividir por 0 ( zero ) 

O tentar dividir por zero é mostra a exceção : *ArithmeticException* ( tente e veja a pilha de exceção apresentada ).

Como trata isto ? 

Inserir um novo **catch** que captura este  tipo de exceção .Veja o trecho de  codigo a seguir 

```
...
try {
    int resultado = dividir( Integer.parseInt(a), Integer.parseInt(b));
    System.out.println("Resultado : " + resultado);
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

finally {   // dar continuidade no código após imprimir pilha
    System.out.println("Chegou no finally");
}

...
```



### Tratativa IV -  Tornar codigo mais funcional  

Como *regra de "negócio "* deveria ser possível fornecer  um novo par de numeros caso a entrada de dados tenha sido invalida. 

Para tal , pode ser utilizado uma estrutura de loop, que se repetira se a entrada for inválida



```
import javax.swing.*;

// objetivo dividir 2 numeros inteiros

public class UncheckedException {
    public static void main(String[] args) {

    boolean continueLoop = true ;

     do {

         String a = JOptionPane.showInputDialog("Numerador : ");
         String b = JOptionPane.showInputDialog("Denominador : ");

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
```



### Dicas de estudos  

- Que outras exceções podem aparecer ? 
- Analise os vários metodo disponiveis para   **e.** 

![Screen Shot 2022-01-05 at 17.43.29](https://tva1.sinaimg.cn/large/008i3skNgy1gy3guwck7ej30ex092mxw.jpg)

- Manter  **e.printStackTrace()**; ativo  durante a criação do código é uma boa prática que ajuda a identificar e corrigir os erros de codificação. Só lembre de desativar/ comentar antes de liberar o código para produção. 