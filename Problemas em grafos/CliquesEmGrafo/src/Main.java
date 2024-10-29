import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(10);
        grafo.preencheGrafo();
        grafo.exibeGrafo();

        grafo.ordenarVerticesPorGrau();

        long startTime = System.nanoTime();
        ArrayList<Integer> coberturaOtima = grafo.coberturaVerticesOtima();
        long endTime = System.nanoTime();

        System.out.println("Tempo de Execução: " + (endTime - startTime) / 1_000_000 + " milissegundos");

        long startTime2 = System.nanoTime();
        ArrayList<Integer> coberturaGulosa = grafo.coberturaVerticesGulosa();
        long endTime2 = System.nanoTime();

        System.out.println("Tempo de Execução: " + (endTime2 - startTime2) / 1_000_000 + " milissegundos");

        long startTime3 = System.nanoTime();
        grafo.encontrarMaiorClique();
        long endTime3 = System.nanoTime();

        System.out.println("Tempo de Execução: " + (endTime3 - startTime3) / 1_000_000 + " milissegundos");
    }
}

