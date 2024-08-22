import java.util.Scanner;
import java.io.Serializable;

/**
 * A classe Torneio representa um torneio de jogos de dados. Ela gerencia os
 * jogadores,
 * o processo de apostas e as rodadas dos jogos. Existem duas variações de jogos
 * disponíveis:
 * Jogo de Azar e Jogo do Porquinho.
 */

public class Torneio implements Serializable {
    private static final long serialVersionUID = 1L;
    private Jogador[] jogadores; // Array para armazenar os jogadores
    private static int qtdJogadores; // Quantidade de jogadores no torneio
    private int qtdRodadas; // Quantidade de rodadas realizadas no torneio

    /**
     * Construtor padrão que inicializa o torneio com capacidade para 10 jogadores.
     */
    public Torneio() {
        jogadores = new Jogador[10];
        qtdJogadores = 0;
        qtdRodadas = 0;
    }

    /**
     * Inclui um novo jogador no torneio.
     * 
     * @param humOuMaq Se o jogador é humano (false) ou máquina (true).
     * @param nome     Nome do jogador a ser incluído.
     */
    void incluirJogador(Boolean humOuMaq, String nome) {
        int k = 1;
        for (int i = 0; i < jogadores.length && k == 1; i++) {
            if (jogadores[i] != null && jogadores[i].getId().equals(nome)) {
                System.out.println("Jogador com esse nome ja existe... Tente novamente!");
                k = 0;
            }
        }

        if (k == 1) {
            int i = 0;
            for (i = 0; i < jogadores.length; i++) {
                if (jogadores[i] == null) {
                    break;
                }
            }
            jogadores[i] = new Jogador(nome, humOuMaq);
            Torneio.qtdJogadores++;

        }    

    }

    /**
     * Remove um jogador do torneio.
     * 
     * @param nome Nome do jogador a ser removido.
     */
    void RemoverJogador(String nome) {
        int i;
        int j = percorrerArrayJogadores(nome);

        if (j != -1) {
            String name = jogadores[j].getId();
            // Remover o jogador e reorganizar o array
            for (i = j; i < jogadores.length - 1; i++) {
                jogadores[i] = jogadores[i + 1];
            }
            jogadores[jogadores.length - 1] = null; // último elemento é agora nulo
            Torneio.qtdJogadores--;

            System.out.println("\nJogador " + name + " removido com sucesso!\n");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    /**
     * Percorre o array de jogadores para encontrar o índice de um jogador pelo
     * nome.
     * 
     * @param nome Nome do jogador a ser encontrado.
     * @return O índice do jogador no array ou -1 se não for encontrado.
     */
    public int percorrerArrayJogadores(String nome) {
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && jogadores[i].getId().equals(nome)) {
                return i;
            }
        }
        return -1; // não encontrado
    }

    /**
     * Inicia o torneio de jogos de dados com a escolha de um dos jogos disponíveis.
     * 
     * @param escolha A escolha do jogo: 1 para Jogo de Azar, 2 para Jogo do
     *                Porquinho.
     * @return O jogador vencedor do torneio.
     */
    public Jogador iniciarTorneio(int escolha) {
        JogoDados jogoD = new JogoDados();

        if (escolha == 1) {
            jogadores[0].mostrarRegraJogoAzar();
        } else {
            jogadores[0].mostrarRegraJogoPorquinho();
        }
        do {
            qtdRodadas++;
            for (int i = 0; i < jogadores.length; i++) {
                if (jogadores[i] != null ) {
                    jogadores[i].apostar();
                }
            }

            if (escolha == 1) { // Jogo de Azar

                System.out.println("Iniciando o Jogo de Azar...");
                boolean algumVencedor = false;
                for (int i = 0; i < jogadores.length; i++) {
                    if (jogadores[i] != null) {

                        System.out.println("Jogador " + jogadores[i].getId() + " com aposta " + jogadores[i].getAposta()
                                + " está jogando...");

                        // int resultado = jogoDados.aplicarRegraJogoAzar();
                        // Aplica a regra do jogo Azar e determina se o jogador ganhou ou perdeu.
                        int resultado = jogadores[i].getJogoD().aplicarRegraJogoAzar();

                        if (resultado == 1) {
                            System.out.println("Jogador " + jogadores[i].getId() + " ganhou!");
                            jogadores[i].setGanhador(true);
                            algumVencedor = true;
                        } else if (resultado == -1) {
                            System.out.println("Jogador " + jogadores[i].getId() + " perdeu!");
                            jogadores[i].setGanhador(false);

                            jogadores[i].setSaldo(jogadores[i].getSaldo() - jogadores[i].getAposta());
                        }
                    }
                }
                if (!algumVencedor) {
                    System.out.println("\nTodos os jogadores perderam. Não há vencedores nesta rodada.\n");
                    // como nao tem vencedor temmos que desfazer o débito da aposta
                    for (int i = 0; i < jogadores.length; i++) {
                        if (jogadores[i] != null) {
                            jogadores[i].setSaldo(jogadores[i].getSaldo() + jogadores[i].getAposta());
                        }
                    }
                    continue; // Pular para a próxima rodada
                }
                double maior = maiorValorAposta();

                if (getQtdJogadoresGanhou() > 1) {
                    int qtdGanhouMaiorAposta = qtdJogadoresApostaramMaiorValor(maior);

                    for (int i = 0; i < jogadores.length; i++) {
                        if (jogadores[i] != null && jogadores[i].getGanhador() == true
                                && jogadores[i].getAposta() == maior) {
                            jogadores[i]
                                    .setSaldo(jogadores[i].getSaldo() + (somaApostas(maior) / qtdGanhouMaiorAposta));
                        }else if (jogadores[i] != null && jogadores[i].getGanhador() == true
                                && jogadores[i].getAposta() != maior){
                            jogadores[i].setSaldo(jogadores[i].getSaldo() - jogadores[i].getAposta());
                        }

                    }
                } else if (getQtdJogadoresGanhou() == 1) {
                    for (int i = 0; i < jogadores.length; i++) {
                        if (jogadores[i] != null && jogadores[i].getGanhador() == true && jogadores[i].getAposta()==maior) {
                            jogadores[i].setSaldo(jogadores[i].getSaldo() + somaApostas(maior));
                        }
                    }
                }

            } else if (escolha == 2) { // Jogo do Porquinho
                System.out.println("Iniciando o Jogo do Porquinho...");
                int pontos, qtd;

                for (int i = 0; i < jogadores.length; i++) {
                    if (jogadores[i] != null) {
                        // Cada jogador participa do jogo do porquinho
                        System.out.println("Jogador " + jogadores[i].getId() + " com aposta " + jogadores[i].getAposta()
                                + " está jogando...");

                        pontos = 0;
                        qtd = 0;
                        do {
                            pontos = pontos + jogadores[i].getJogoD().aplicarRegraJogoPorquinho();
                            System.out.println("Pontos totais: " + pontos);
                            qtd++;
                        } while (pontos < 300);

                        jogadores[i].setQtdLancamentos(qtd);

                        System.out.println("Jogador " + jogadores[i].getId() + " obteve " + pontos + " pontos com "
                                + jogadores[i].getQtdLancamentos() + " lançamentos\n");
                    }
                }

                int menor = verificaMenorNroLancamentos();
                int qtdMenor = qtdJogadoresMenorNroLancamentos(menor);
                double maior = maiorValorAposta();

                // Verifica se mais de um jogador ganhou
                if (qtdMenor > 1) {
                    int qtdGanhouMaiorAposta = qtdJogadoresApostaramMaiorValor(maior);

                    for (int i = 0; i < jogadores.length; i++) {
                        if (jogadores[i] != null && (jogadores[i].getGanhador() == true)
                                && (jogadores[i].getAposta() == maior)) {
                            jogadores[i]
                                    .setSaldo(jogadores[i].getSaldo() + (somaApostas(maior) / qtdGanhouMaiorAposta));
                        } else if (jogadores[i] != null) {
                            // Débito da aposta para perdedores
                            jogadores[i].setSaldo(jogadores[i].getSaldo() - jogadores[i].getAposta());

                        }

                    }
                } else if (qtdMenor == 1) { // so tem um ganhador
                    for (int i = 0; i < jogadores.length; i++) {
                        if (jogadores[i] != null && jogadores[i].getQtdLancamentos() == menor) {
                            jogadores[i].setSaldo(jogadores[i].getSaldo() + somaApostas(maior));
                        } else if (jogadores[i] != null) {
                            // Débito da aposta para perdedores
                            jogadores[i].setSaldo(jogadores[i].getSaldo() - jogadores[i].getAposta());
                        }
                    }
                }

            }

            relatorioParcial();
            removerJogadorSaldoZero();
            mostraSaldoJogadores();
            // System.out.println("qtd: " + getQtdJogadores());
        } while (qtdJogadores > 1);

        System.out.println("Jogador " + jogadores[0].getId() + " venceu!\n");
        return jogadores[0];
    }

    /**
     * Mostra o placar final de todos os jogadores no torneio.
     */
    void mostrarPlacarfinal() {
        System.out.println("Placar final: ");
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null) {
                System.out.println(jogadores[i].toString());
            }
        }
        System.out.println("\n");

    }

    /**
     * Atualiza a quantidade de jogadores do torneio.
     */
    public void atualizarQtdJogadores() {
        int contador = 0;
        for (Jogador jogador : jogadores) {
            if (jogador != null) {
                contador++;
            }
        }
        qtdJogadores = contador;
    }


    /**
     * Veerifica o maior valor de aposta feito pelos jogadores.
     *
     * @return O maior valor de aposta.
     */
    public double maiorValorAposta() {
        double maior = 0;

        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && (jogadores[i].getGanhador() == true) && (jogadores[i].getAposta() > maior)) {
                maior = jogadores[i].getAposta();
            }
        }
        return maior;
    }

    /**
     * Verifica quantos jogadores apotaram o maior valor de aposta.
     *
     * @param maior O maior valor de aposta.
     * @return A quantidade de jogadores que apotaram o maior valor de aposta.
     */
    public int qtdJogadoresApostaramMaiorValor(double maior) {
        int j = 0;
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && (jogadores[i].getGanhador() == true) && (jogadores[i].getAposta() == maior)) {
                j++;
            }
        }
        return j;
    }

    /**
     * Calcula a soma das apostas de todos os jogadores que apostaram o maior valor
     * de aposta.
     *
     * @param maior O maior valor de aposta.
     * @return A soma das apostas de todos os jogadores que apostaram o maior valor
     *         de aposta.
     */
    public double somaApostas(double maior) {
        double soma = 0;

        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && (jogadores[i].getGanhador() == false || jogadores[i].getAposta() != maior)) {
                soma = soma + jogadores[i].getAposta();
            }
        }
        return soma;
    }

    /**
     * Remove os jogadores que não possuem saldo.
     */
    public void removerJogadorSaldoZero() {
        for (int i = jogadores.length - 1; i >= 0; i--) {
            if (jogadores[i] != null && jogadores[i].getSaldo() < 10) {
                System.out.println("\nJogador " + jogadores[i].getId()
                        + " removido por saldo insuficiente (<10), saldo é: " + jogadores[i].getSaldo() + "\n");
                RemoverJogador(jogadores[i].getId());
            }
        }
    }

    /**
     * Mostra o saldo de todos os jogadores.
     */
    public void mostraSaldoJogadores() {
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null) {
                System.out.println("\nJogador " + jogadores[i].getId() + " saldo: " + jogadores[i].getSaldo());
            }
        }
        System.out.println("\n");
    }

    /**
     * Verifica o menor número de lançamentos feitos pelos jogadores
     */
    public int verificaMenorNroLancamentos() {
        int menor = 1000;
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && jogadores[i].getQtdLancamentos() < menor) {
                menor = jogadores[i].getQtdLancamentos();
            }
        }
        return menor;
    }

    /**
     * Verifica quantos jogadores apostaram o menor número de lançamentos.
     *
     * @param menor O menor número de lançamentos.
     * @return A quantidade de jogadores que apostaram o menor número de
     *         lançamentos.
     */
    public int qtdJogadoresMenorNroLancamentos(int menor) {
        int j = 0;
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && (jogadores[i].getQtdLancamentos() == menor)) {
                j++;
                jogadores[i].setGanhador(true);
            } else if (jogadores[i] != null) {
                jogadores[i].setGanhador(false);
            }
        }
        return j;
    }

    /**
     * Mostra o relatório parcial de todos os jogadores no torneio.
     */
    public void relatorioParcial() {
        System.out.println("\nRelatorio Parcial: ");
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null) {
                if (jogadores[i].getGanhador() == true) {
                    System.out.println("Jogador " + jogadores[i].getId() + " ganhou.");
                } else {
                    System.out.println("Jogador " + jogadores[i].getId() + " perdeu.");
                }
            }

        }
    }

    /**
     * Reinicia o torneio, limpando a lista de jogadores e zerando a quantidade de
     * jogadores e rodadas.
     */
    public void comecarNovo() {
        for (int i = 0; i < jogadores.length; i++) {
            if(jogadores[i]!=null){
                jogadores[i].setId("");
                jogadores[i].setSaldo(100);
                jogadores[i].setTipo(false);
                jogadores[i].setAposta(0.0);
                jogadores[i].setJogoD(null);
                jogadores[i].setGanhador(false);
                jogadores[i].setQtdLancamentos(0);
            }
            
        }

        setQtdJogadores(0);
        setQtdRodadas(0);
    }

    // Getters e Setters

    public int getQtdJogadores() {
        return qtdJogadores;
    }

    public void setQtdJogadores(int qtdJogadores) {
        this.qtdJogadores = qtdJogadores;
    }

    public int getQtdRodadas() {
        return qtdRodadas;
    }

    public void setQtdRodadas(int qtdRodadas) {
        this.qtdRodadas = qtdRodadas;
    }

    public Jogador getJogadorGanhador() {
        if (jogadores[0] != null) {
            return jogadores[0];
        }
        return null;
    }

    public int getQtdJogadoresGanhou() {
        int j = 0;

        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null && jogadores[i].getGanhador() == true) {
                j++;
            }
        }
        return j;
    }

    /**
     * Retorna uma representação em string do torneio.
     *
     * @return Uma string de todos os jogadores.
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null) {
                str += jogadores[i].toString();
            }
        }
        return str;
    }
}
