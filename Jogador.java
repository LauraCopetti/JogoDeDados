import java.util.Scanner;
import java.io.Serializable;

/**
 * A classe Jogador representa um jogador em um torneio de jogos de dados.
 * O jogador pode ser do tipo humano ou máquina, e possui um saldo inicial de
 * 100 moedas. O saldo e a aposta do jogador são gerenciados por métodos
 * específicos.
 */

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private boolean tipo; // 0-humano, 1-maquina
    private double aposta, saldo = 100;
    private JogoDados jogoD;
    private boolean ganhador;
    private int qtdLancamentos;

    /**
     * Construtor padrão da classe Jogador. Inicializa o jogo de dados e define o
     * ganhador como falso e a quantidade de lançamentos como 0.
     */

    public Jogador() {
        this.jogoD = new JogoDados();
        this.ganhador = false;
        this.qtdLancamentos = 0;
    }

    /**
     * Construtor principal da classe Jogador.
     * 
     * @param id   O identificador do jogador.
     * @param tipo O tipo do jogador (false para humano, true para máquina).
     */

    public Jogador(String id, boolean tipo) {
        this.id = id;
        this.tipo = tipo;
        this.jogoD = new JogoDados();
        this.ganhador = false;
        this.qtdLancamentos = 0;
    }

    /**
     * Construtor adicional da classe Jogador com saldo e aposta.
     * 
     * @param id     O identificador do jogador.
     * @param saldo  O saldo inicial do jogador.
     * @param tipo   O tipo do jogador (false para humano, true para máquina).
     * @param aposta A aposta inicial do jogador.
     */

    public Jogador(String id, double saldo, boolean tipo, double aposta) {
        this.id = id;
        this.saldo = saldo;
        this.tipo = tipo;
        this.aposta = aposta;
        this.jogoD = new JogoDados();
        this.ganhador = false;
        this.qtdLancamentos = 0;
    }

    /**
     * Método para jogar os dados do jogo Azar.
     * Este método chama o método de rolar dados da classe JogoDados.
     */

    public void jogarDados() {
        System.out.println("Jogador " + this.id + " jogando os dados...");
        jogoD.rolarDados();
    }

    /**
     * Exibe as regras do Jogo do Porquinho.
     */

    public void mostrarRegraJogoPorquinho() {
        jogoD.mostrarRegraJogoPorquinho();
    }

    /**
     * Exibe as regras do Jogo de Azar.
     */

    public void mostrarRegraJogoAzar() {
        jogoD.mostrarRegraJogoAzar();
    }

    /**
     * Permite ao jogador apostar. Solicita ao usuário o valor da aposta e realiza
     * validações.
     */

    public void apostar() {
        Scanner scan = new Scanner(System.in);
        Double apostaJogador = null;
        
        if(this.tipo == true){
            this.aposta = saldo / 5.0;
        }else{
            do {
                System.out.println("Jogador(a) " + this.id + " seu saldo : " + this.saldo + ", informe sua aposta: ");

                // Verifica se a próxima entrada é um número válido
                if (scan.hasNextDouble()) {
                    apostaJogador = scan.nextDouble();
    
                    // 10 moedas é a aposta mínima
                    if (apostaJogador <= this.saldo && apostaJogador >= 10) {
                        this.aposta = apostaJogador;
                    } else if (apostaJogador > this.saldo) {
                        System.out.println("Você não tem saldo suficiente para essa aposta!\n");
                        apostaJogador = null; // Resetar para forçar o loop a continuar
                    } else {
                        System.out.println("Aposta inválida! A aposta deve ser pelo menos 10 moedas.\n");
                        apostaJogador = null;
                    }
                } else {
                    System.out.println("Entrada inválida! Por favor, insira um número.");
                    scan.nextLine(); // Consumir a entrada inválida para evitar loop infinito
                }
            
            } while (apostaJogador == null || apostaJogador > this.saldo);
    
            System.out.println("Aposta de R$ " + this.aposta + " foi registrada para o jogador " + this.id + "\n");
        }  
    }

    @Override
    public String toString() {
        String tipoJogador = this.tipo ? "Máquina" : "Humano";
        return "Nome: " + this.id + ", Tipo: " + tipoJogador + ", Saldo: R$ " + this.saldo + "\n";

    }

    // Getters e Setters

    public double getAposta() {
        return aposta;
    }

    public JogoDados getJogoD() {
        return jogoD;
    }

    public boolean getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getQtdLancamentos() {
        return qtdLancamentos;
    }

    public boolean getGanhador() {
        return ganhador;
    }


    public void setId(String id){
        this.id= id;
    }
    
    public void setQtdLancamentos(int qtdLancamentos) {
        this.qtdLancamentos = qtdLancamentos;
    }

    public void setAposta(double aposta) {
        this.aposta = aposta;
    }

    public void setJogoD(JogoDados jogoD) {
        this.jogoD = jogoD;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setGanhador(boolean ganhador) {
        this.ganhador = ganhador;
    }

}