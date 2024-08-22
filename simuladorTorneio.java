import java.util.Scanner;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe simuladorTorneio - Simulador para gerenciar torneios de jogos de dados.
 * Essa classe permite incluir e remover jogadores, iniciar torneios, exibir o placar,
 * e salvar/carregar o estado do torneio em arquivos.
 */
public class simuladorTorneio implements Serializable {

    private static Torneio t = new Torneio(); // Instância do torneio que será gerenciada

    /**
     * Método principal que inicializa o simulador de torneios.
     * 
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Bem vindo ao simulador de torneios!");

        // Chama o menu principal, passando a instância do torneio
        Menu(t);
    }

    /**
     * Método que exibe o menu principal e gerencia as opções selecionadas pelo usuário.
     * 
     * @param t Instância do torneio que será gerenciada pelo menu.
     */
    public static void Menu(Torneio t) {
        Scanner scan = new Scanner(System.in);
        int escolha = -1; // Variável para armazenar a escolha do usuário
        String opcao = "s"; // Opção para continuar ou sair do loop
        Jogador ganhou = new Jogador(); // Objeto para armazenar o jogador vencedor
        int rodadas = 0; // Contador de rodadas do torneio

        do {
            do {
                do {
                    escolha = -1; // Reinicia a escolha para o loop
                    System.out.println("Escolha a opção desejada: \n");
                    System.out.println(
                            "1- Incluir jogador\n2- Remover jogador\n3- Iniciar torneio\n4- Placar Torneio de Apostas\n5- Gravar torneio em um arquivo\n6- Abrir torneio de um arquivo\n7- Sair\n");

                    // Verifica se a entrada é um número inteiro
                    if (scan.hasNextInt()) {
                        escolha = scan.nextInt();
                        scan.nextLine(); // Consumir o newline após o número
                    } else {
                        System.out.println("Entrada inválida! Por favor, insira um número.");
                        scan.nextLine(); // Consumir a entrada incorreta para evitar loop infinito
                    }

                    // Verifica se a escolha está dentro do intervalo válido
                    if (escolha < 1 || escolha > 7) {
                        System.out.println("Opção inválida!\nTente novamente!");
                    }

                } while (escolha < 1 || escolha > 7); // Continua pedindo até uma escolha válida

                switch (escolha) {
                    case 1:
                        // Caso o número máximo de jogadores (10) já tenha sido atingido
                        if (t.getQtdJogadores() == 10) {
                            System.out.println("Você já atingiu a capacidade máxima de jogadores!");
                        } else {
                            // Adiciona um novo jogador
                            System.out.println("Informe seu nome: ");
                            String nome = scan.nextLine().trim(); // .trim() remove espaços vazios
                            int verificacao = t.percorrerArrayJogadores(nome);

                            // Verifica se o nome não está vazio
                            if (nome.isEmpty()) {
                                System.out.println("O nome nao pode estar vazio!");
                            } else {
                                // Verifica se já existe um jogador com o mesmo nome
                                if (verificacao != -1) {
                                    System.out.println("Já existe um jogador com esse nome!");
                                    System.out.println("Dois jogadores não podem ter o mesmo nome!");
                                } else {
                                    int tipo = -1;
                                    do {
                                        // Pede o tipo de jogador (humano ou máquina)
                                        System.out.println("Informe se é humano ou máquina: \n");
                                        System.out.println("0- Humano\n1- Máquina");

                                        if (scan.hasNextInt()) {
                                            tipo = scan.nextInt();
                                            scan.nextLine(); // Consumir o newline após o número
                                        } else {
                                            System.out.println("Entrada inválida! Por favor, insira um número.");
                                            scan.nextLine(); // Consumir a entrada incorreta para evitar loop infinito
                                        }

                                        // Verifica se a escolha está dentro do intervalo válido
                                        if (tipo != 0 && tipo != 1) {
                                            System.out.println("Opção inválida!\nTente novamente!");
                                        }

                                    } while (tipo != 0 && tipo != 1);

                                    t.incluirJogador(tipo == 1, nome); // Adiciona o jogador ao torneio
                                    System.out.println("Jogador incluido com sucesso!\n");
                                }
                            }
                        }
                        break;

                    case 2:
                        // Remove um jogador
                        if (t.getQtdJogadores() != 0) {
                            System.out.println("Informe o nome do jogador que deseja remover: ");
                            String nomeRemover = scan.nextLine();
                            System.out.println("Confirme que esse é o nome que deseja remover, escreva novamente: ");
                            String nomeRemoverConfirma = scan.nextLine();

                            boolean valor = nomeRemover.equals(nomeRemoverConfirma);

                            if (!valor) {
                                System.out.println("Nomes não conferem, tente novamente!");
                            } else {
                                t.RemoverJogador(nomeRemover); // Remove o jogador pelo nome
                            }

                        } else {
                            System.out.println("Não há jogadores para remover!\n");
                        }
                        break;
                    case 3:
                        // Inicia o torneio se houver jogadores suficientes
                        if (t.getQtdJogadores() < 2) {
                            System.out.println("Para iniciar o torneio, é necessário ter no mínimo 2 jogadores!");
                            escolha = -1; // para que o loop continue rodando
                        } else {
                            do {
                                System.out.println("Qual jogo deseja jogar? \n");
                                System.out.println("1-Jogo de Azar\n2-Jogo do Porquinho\n");
                                escolha = scan.nextInt();
                                scan.nextLine(); // Consome a nova linha restante após nextInt()

                                // Verifica se a escolha do jogo é válida
                                if (escolha != 1 && escolha != 2) {
                                    System.out.println("Opção inválida!\nTente novamente!");
                                }
                            } while (escolha != 1 && escolha != 2);

                            t.iniciarTorneio(escolha); // Inicia o torneio com o jogo selecionado

                            ganhou = t.getJogadorGanhador(); // Obtém o ganhador do torneio
                            rodadas = t.getQtdRodadas(); // Obtém o número de rodadas jogadas

                            do {
                                // Pergunta se o usuário deseja salvar o resultado do torneio
                                System.out.println("Deseja salvar o resultado do torneio no arquivo? (s/n)");
                                opcao = scan.nextLine().trim();

                                if (opcao.equalsIgnoreCase("s")) {
                                    gravarTorneioArquivo(t); // Salva o torneio em arquivo
                                    System.out.println("Resultado salvo com sucesso.");
                                    break;
                                } else if (opcao.equalsIgnoreCase("n")) {
                                    System.out.println("Resultado não salvo.");
                                    break;
                                } else{
                                    System.out.println("Opção inválida. Por favor, insira 's' para sim ou 'n' para não.");
                                }
                            } while (true);

                            t.comecarNovo(); // Reinicia o torneio, removendo todos os jogadores
                            System.out.println("Fim do torneio!\nTodos os jogadores foram removidos\n");
                        }
                        break;
                    case 4:
                        // Exibe o placar do torneio
                        if (rodadas != 0) {
                            System.out.println("\nA quantidade de rodadas foi: " + rodadas);
                            System.out.println("O jogador ganhador foi: " + ganhou.getId() + "\n");
                        } else {
                            System.out.println("Nenhum torneio foi iniciado ainda!");
                        }
                        break;

                    case 5:
                        // Salva o torneio em um arquivo
                        if(t.getJogadorGanhador()!=null){
                            gravarTorneioArquivo(t);
                        }else{
                            System.out.println("Não há jogadores nessa partida ainda");
                        }
                        break;
                    case 6:
                        if(t.getJogadorGanhador()!=null){
                            // Carrega o torneio a partir de um arquivo
                            t = lerTorneioArquivo(); // Atualiza a instância `t` com o torneio carregado
                            t.RemoverJogador(t.getJogadorGanhador().getId()); //remove o jogador que ganhou ultima rodada, pois quando
                            //chama a funcao de ler o torneio, o jogador é adicionado de volta
                        }else{
                            System.out.println("Não há jogadores nessa partida ainda");
                        }
                        break;
                    case 7:
                        // Sai do programa
                        System.out.println("Obrigado por utilizar o simulador de torneios!");
                        break;

                }
            } while (t.getQtdJogadores() <= 10 && escolha != 7); // Loop continua enquanto a opção de sair não for selecionada

        } while (opcao.equalsIgnoreCase("s") && escolha != 7); // Verifica se o usuário deseja continuar
    }

    /**
     * Método para carregar o torneio a partir de um arquivo.
     * 
     * @return Retorna a instância do torneio carregado.
     */
    public static Torneio lerTorneioArquivo() {
        File arquivo = new File("Torneio.dat"); // Nome do arquivo onde o torneio é salvo
        Torneio torneioCarregado = null; // Instância para armazenar o torneio carregado
        Scanner scan = new Scanner(System.in);

        try {
            FileInputStream fin = new FileInputStream(arquivo);
            ObjectInputStream oin = new ObjectInputStream(fin);

            torneioCarregado = (Torneio) oin.readObject(); // Carrega o torneio do arquivo
            oin.close();
            fin.close();

            torneioCarregado.atualizarQtdJogadores(); // Atualiza a quantidade de jogadores após o carregamento

            System.out.println("Torneio carregado com sucesso!");
            System.out.println("Deseja imprimir o torneio? (s/n)");
            String opcao = scan.nextLine();
            if (opcao.equalsIgnoreCase("s")) {
                System.out.println(torneioCarregado.toString()); // Imprime o estado do torneio carregado
            }

        } catch (Exception ex) {
            System.err.println("Erro ao carregar torneio: " + ex.toString()); // Tratamento de erros
        }
        


        return torneioCarregado; // Retorna o torneio carregado
    }

    /**
     * Método para salvar o estado atual do torneio em um arquivo.
     * 
     * @param t Instância do torneio que será salva.
     */
    public static void gravarTorneioArquivo(Torneio t) {
        File arquivo = new File("Torneio.dat"); // Nome do arquivo onde o torneio será salvo

        try {

            if (arquivo.exists()) {
                arquivo.delete(); // Deleta o arquivo existente antes de salvar o novo
                System.out.println("\nArquivo anterior deletado\n");
            }
            
            FileOutputStream fout = new FileOutputStream(arquivo);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(t); // Salva o objeto do torneio no arquivo
            oos.flush();
            oos.close();
            fout.close();
            System.out.println("Torneio salvo com sucesso!");
            
        } catch (Exception ex) {
            System.err.println("Erro ao salvar torneio: " + ex.toString()); // Tratamento de erros
        }
    }
    

}
