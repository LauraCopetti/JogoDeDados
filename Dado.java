import java.util.Random;
import java.io.Serializable;

/**
 * A classe Dado representa um dado de seis faces que pode ser rolado para gerar um valor aleatório entre 1 a 6. Esta classe é utilizada para simular lançamentos de dados nos jogos de Azar e do Porquinho.
*/
public class Dado implements Serializable{
    private static final long serialVersionUID = 1L;
    private int faceSuperior;
    
    /**
     * Construtor da classe Dado. Inicializa o valor da face superior do dado com 1.
    */
    public Dado() {
        faceSuperior = 1;
    }

    /**
     * Rola o dado, gerando  um valor entre 1 e 6 e atualiza a face superior do dado com o valor gerado.
    */
    public void roll() { 
        Random r = new Random();
        faceSuperior = r.nextInt(6) + 1;
    }

    /**
     * Método Getter que retorna o valor da face superior do dado
    */
    public int getFaceSuperior() { 
        return faceSuperior;
    }

    /**
     * Método toString que retorna uma representação em string da face superior do dado.
     * @return uma string representando a face superior do dado.
    */
    @Override
    public String toString() {
        return Integer.toString(faceSuperior);
    }
}
