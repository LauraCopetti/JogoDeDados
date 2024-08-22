import java.util.Scanner;
import java.io.Serializable;

/**
 * A classe JogoDados representa um jogo de dados que pode ser jogado de duas formas:
 * - Jogo de Azar
 * - Jogo do Porquinho
 * 
 * Utiliza dois dados para determinar o resultado dos jogos.
 */
public class JogoDados implements Serializable {
    private static final long serialVersionUID = 1L;
    private Dado[] dados; // Array para armazenar os dois dados utilizados no jogo
    private final int QTDDADOS = 2; // Utiliza dois dados para o jogo, pois ambos os jogos utilizam essa quantidade de dados

    /**
     * Construtor da classe JogoDados. Inicializa os dados do jogo.
     */
    public JogoDados() {
        this.dados = new Dado[this.QTDDADOS];
        for (int i = 0; i < this.dados.length; i++) {
            dados[i] = new Dado();
        }
    }

    /**
     * Rola os dados, chamando o método roll() de cada dado.
     */
    public void rolarDados() {
        System.out.println("Rolando os dados...\n");
        for (int i = 0; i < this.dados.length; i++) {
            dados[i].roll();
        }
    }


    /**
     * Aplica as regras do Jogo de Azar e retorna o resultado da jogada.
     * 
     * @return 1 se o jogador ganhar, -1 se o jogador perder.
     */
    public int aplicarRegraJogoAzar() {
        Scanner scan = new Scanner(System.in);
        int somaInicial, somaAtual;

        // Primeiro lançamento
        rolarDados();
        somaInicial = calcularSomaDados();
        System.out.println("Primeiro lançamento: " + dados[0].getFaceSuperior() + " e " + dados[1].getFaceSuperior()
                + " = " + somaInicial);

        if (somaInicial == 7 || somaInicial == 11) {
            System.out.println("Você ganhou com a soma " + somaInicial + "!\n");
            return 1;
        } else if (somaInicial == 2 || somaInicial == 3 || somaInicial == 12) {
            System.out.println("Você perdeu com a soma " + somaInicial + "!\n");
            return -1;
        } else {
            System.out.println("Número a ser buscado: " + somaInicial);
            do {
                System.out.println("\nLançando os dados novamente...\n");
                rolarDados();
                somaAtual = calcularSomaDados();
                System.out.println("Novo lançamento: " + dados[0].getFaceSuperior() + " e " + dados[1].getFaceSuperior()
                        + " = " + somaAtual);

                if (somaAtual == somaInicial) {
                    System.out.println("Você ganhou com a soma " + somaAtual + "!");
                    return 1;
                } else if (somaAtual == 2 || somaAtual==3 || somaAtual==12) {
                    System.out.println("Você perdeu com a soma " + somaAtual + "!");
                    return -1;
                }
            } while (somaAtual != somaInicial);
        }
        return -1; // Não deve ser alcançado
    }

    /**
     * Mostra as regras do Jogo de Azar.
     */
    public void mostrarRegraJogoAzar() {
        System.out.println("Regras do Jogo de Azar:");
        System.out.println("- O jogador lança dois dados.");
        System.out.println("- Se a soma das faces dos dados for 7 ou 11, o jogador ganha.");
        System.out.println("- Se a soma das faces dos dados for 2, 3 ou 12, o jogador perde.");
        System.out.println("- Se a soma obtida no primeiro lançamento não for 7, 11, 2, 3 ou 12, ");
        System.out.println("  essa soma se torna o valor a ser buscado nos lançamentos subsequentes.");
        System.out.println("- O jogador ganha se conseguir novamente atingir a soma obtida no primeiro lançamento.\n\n");
    }

    /**
     * Mostra as regras do Jogo do Porquinho.
     */
    public void mostrarRegraJogoPorquinho() {
        System.out.println("Regras do Jogo do Porquinho:");
        System.out.println("- O jogador lança os dois dados sucessivamente.");
        System.out.println("- A cada lançamento, multiplicam-se os valores dos dois dados até se atingir a pontuação de 300.");
        System.out.println("- Dobles (duplas de dados iguais) são sempre duplicados.");
        System.out.println("  Além da multiplicação, o valor obtido é dobrado.");
        System.out.println("- Exceção: Doble de 1 é contado como 30 pontos.");
        System.out.println("- O jogador que atingir 300 pontos com o menor número de lançamentos ganha a rodada.\n\n");
    }

    /**
     * Aplica as regras do Jogo do Porquinho e retorna os pontos obtidos no lançamento.
     * 
     * @return Pontos obtidos no lançamento.
     */
    public int aplicarRegraJogoPorquinho() {
        int pontos = 0;

        // Rola os dados
        this.rolarDados();

        // Obtem os valores dos dois dados
        int dado1 = dados[0].getFaceSuperior();
        int dado2 = dados[1].getFaceSuperior();

        System.out.println("Dado 1: "+ dado1 +"\nDado 2: "+ dado2);

        // Verifica se os dados são iguais (dúplex)
        if (dado1 == dado2) {
            if (dado1 == 1) {
                // Se for duplo de 1, pontua 30 pontos
                pontos = 30;
            } else {
                // Para outros duplos, multiplica os valores e dobra o resultado
                pontos = 2 * (dado1 * dado2);
            }
        } else {
            // Para dados diferentes, multiplica os valores
            pontos = dado1 * dado2;
        }

        System.out.println("Pontos obtidos nesse lançamento: " + pontos + "\n");
        
        return pontos;
    }

    /**
     * Calcula a soma das faces dos dados.
     * 
     * @return Soma das faces dos dados.
     */
    private int calcularSomaDados() {
        return dados[0].getFaceSuperior() + dados[1].getFaceSuperior();
    }

    /**
     * Retorna uma representação textual das faces dos dados.
     *
     * @return Uma string que representa as faces dos dados.
    */
    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder("Faces dos dados: ");
        for (Dado dado : this.dados) {
            resultado.append(dado.toString()).append(" ");
        }
        return resultado.toString();
    }
}
