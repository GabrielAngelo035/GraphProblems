import java.util.*;

public class Grafo {
    private int[][] grafo;
    private ArrayList<Integer> melhorCobertura;

    public Grafo(int x) {
        grafo = new int[x][x];
    }

    public void preencheGrafo() {
        Random random = new Random();
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j] = random.nextInt(2);
            }
        }
    }

    public void exibeGrafo() {
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                System.out.print(grafo[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<Integer> encontrarMaiorClique() {
        ArrayList<Integer> maiorClique = new ArrayList<>();
        int n = grafo.length;

        // Tentando todos os subconjuntos possíveis de vértices
        for (int i = 1; i < (1 << n); i++) {
            ArrayList<Integer> subconjunto = new ArrayList<>();

            // Criando subconjunto de vértices baseado em bits do número i
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subconjunto.add(j);
                }
            }

            // Verificando se o subconjunto é um clique
            int[] vertices = subconjunto.stream().mapToInt(Integer::intValue).toArray();
            if (eClique(vertices) && subconjunto.size() > maiorClique.size()) {
                maiorClique = new ArrayList<>(subconjunto);
            }
        }

        return maiorClique;
    }

    public boolean eClique(int[] S) {
        for (int i = 0; i < S.length; i++) {
            for (int j = i + 1; j < S.length; j++) {
                if (grafo[S[i]][S[j]] != 1)
                    return false;
            }
        }
        return true;
    }

    public void ordenarVerticesPorGrau() {
        int n = grafo.length;
        int[] graus = new int[n];

        // Calcula o grau de cada vértice
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graus[i] += grafo[i][j];
            }
        }

        // Cria uma lista de pares (vértice, grau)
        List<int[]> verticesGrau = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            verticesGrau.add(new int[]{i, graus[i]});
        }

        // Ordena a lista pelo grau em ordem crescente
        verticesGrau.sort(Comparator.comparingInt(a -> a[1]));

        // Exibe os vértices em ordem crescente de grau
        System.out.println("Vértices em ordem crescente de grau:");
        for (int[] verticeGrau : verticesGrau) {
            System.out.println("Vértice: " + verticeGrau[0] + ", Grau: " + verticeGrau[1]);
        }
    }

    public static void exibeMaiorClique(String[] args) {
        Grafo grafo = new Grafo(5);
        grafo.preencheGrafo();
        grafo.exibeGrafo();

        List<Integer> maiorClique = grafo.encontrarMaiorClique();
        System.out.println("Maior Clique: " + maiorClique);
    }

    public ArrayList<Integer> coberturaVerticesGulosa() {
        ArrayList<Integer> C = new ArrayList<>();
        boolean[] cobertos = new boolean[grafo.length];
        int n = grafo.length;
        int arestasCobertas = 0;

        while (true) {
            int maxGrau = -1;
            int verticeEscolhido = -1;

            for (int i = 0; i < n; i++) {
                if (!cobertos[i]) {
                    int grauAtual = 0;
                    for (int j = 0; j < n; j++) {
                        if (grafo[i][j] == 1 && !cobertos[j]) {
                            grauAtual++;
                        }
                    }

                    if (grauAtual > maxGrau) {
                        maxGrau = grauAtual;
                        verticeEscolhido = i;
                    }
                }
            }

            if (verticeEscolhido == -1) break;

            C.add(verticeEscolhido);

            for (int j = 0; j < n; j++) {
                if (grafo[verticeEscolhido][j] == 1) {
                    if (!cobertos[j]) {
                        arestasCobertas++;
                    }
                    cobertos[j] = true;
                }
            }
            cobertos[verticeEscolhido] = true;
        }

        System.out.println("Cobertura por Vértices Gulosa: " + C);
        System.out.println("Arestas Cobertas: " + arestasCobertas);
        return C;
    }


    public ArrayList<Integer> coberturaVerticesOtima() {
        int n = grafo.length;
        ArrayList<Integer> melhorCobertura = null;
        int maxArestasCobertas = 0;

        for (int i = 1; i < (1 << n); i++) {
            Set<Integer> coberturaAtual = new HashSet<>();
            int arestasCobertas = 0;

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    coberturaAtual.add(j);
                }
            }

            if (cobreTodasArestas(coberturaAtual)) {
                for (int v : coberturaAtual) {
                    for (int k = 0; k < n; k++) {
                        if (grafo[v][k] == 1) {
                            arestasCobertas++;
                        }
                    }
                }

                if (melhorCobertura == null || coberturaAtual.size() < melhorCobertura.size()) {
                    melhorCobertura = new ArrayList<>(coberturaAtual);
                    maxArestasCobertas = arestasCobertas / 2;  // Dividido por 2, pois cada aresta foi contada duas vezes
                }
            }
        }

        System.out.println("Cobertura por Vértices Ótima: " + melhorCobertura);
        System.out.println("Arestas Cobertas: " + maxArestasCobertas);
        return melhorCobertura;
    }


    private boolean cobreTodasArestas(Set<Integer> cobertura) {
            for (int i = 0; i < grafo.length; i++) {
                for (int j = i + 1; j < grafo.length; j++) {
                    if (grafo[i][j] == 1) {
                        // Se a aresta (i, j) não for coberta
                        if (!cobertura.contains(i) && !cobertura.contains(j)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
