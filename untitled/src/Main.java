import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static File file1 = new File("C:\\Users\\Francisco Oliveira\\Desktop\\ISEP\\APROG\\PROGRAMA TRABALHO\\untitled\\input1.txt");
    static final int[][] DISTANCIAS = {
            {0, 50, 60, 130, 300, 200},
            {50, 0, 130, 70, 250, 140},
            {60, 130, 0, 180, 370, 250},
            {130, 70, 180, 0, 200, 90},
            {300, 250, 370, 200, 0, 130},
            {200, 140, 250, 90, 130, 0}
    };
    static final String[] CIDADES = {"Porto", "Aveiro", "Braga", "Coimbra", "Lisboa", "Fátima"};

    public static void main(String[] args)
            throws FileNotFoundException {
        Scanner ler2 = new Scanner(file1);
        String cabecalho = ler2.nextLine();
        int numAutocarros, numDias;
        numAutocarros = verificaValorInicias(ler2);
        numDias = verificaValorInicias(ler2);
        int[][] autocarros = new int[numAutocarros][numDias];
        preencherMatriz(autocarros,ler2);
        mostrarPlaneamento(autocarros);
        int[][] distanciasMatriz = new int[numAutocarros][numDias];
        preencherDistanciasMatriz(autocarros,distanciasMatriz);
        mostrarPlaneamento(distanciasMatriz);//       //NAO ESTA COMO DEVIA! NUMEROS TEM QUE ESTAR CENTRADOS
        int[] distanciasTotais = new int[numAutocarros];
        preencherDistanciasTotais(distanciasTotais, distanciasMatriz);
        mostrarDistanciasTotais(distanciasTotais);
        mostrarMaximoKmDia(distanciasMatriz);
        mostrarPermanecemMaisQueUmDia(autocarros);   /////VER CODIGO TEM UM AVISO
        verificarDiaOndeTodosAutocarros(autocarros);
        mostrarPercentagemDosAutocarros(distanciasTotais);
        int autocarro = ler2.nextInt(); // verificar estes valores//
        int dia = ler2.nextInt();
        autocarroMaisProximo(dia,autocarro, autocarros);
        ler2.close();
    }
    private static void autocarroMaisProximo(int dia, int autocarro, int[][]autocarros) {
        int distanciaMinima = 371;
        int guardaAutocarro = -1;
        int numeroCidade = autocarros[autocarro][dia];
        int numeroCidade2;
        String cidade1 = CIDADES[numeroCidade];
        String cidade2 = "";
        for (int i = 0; i < autocarros.length; i++) {
            if (i!=autocarro){
                numeroCidade2 = autocarros[i][dia];
            if (DISTANCIAS[numeroCidade2][numeroCidade] <= distanciaMinima) {
                guardaAutocarro = i;
                cidade2 = CIDADES[numeroCidade2];
                distanciaMinima = DISTANCIAS[numeroCidade2][numeroCidade];
            }
        }
        }
        System.out.printf("No dia <%d>, <Bus%d> estará em <%s>. Bus<%d> é o mais próximo. Está em <%s> a <%d km>%n%n", dia, autocarro, cidade1, guardaAutocarro, cidade2, distanciaMinima);
    }



    private static void mostrarMaximoKmDia(int[][] distanciasMatriz) {
        int kmMax = 0;
        int soma;
        int guarda = 0;
        for(int i = 0; i < distanciasMatriz[0].length; i++){
            soma = 0;
            for(int j = 0; j < distanciasMatriz.length; j++){
                soma += distanciasMatriz[j][i];
            }
            if(soma>kmMax){
                kmMax=soma;
                guarda = i;
            }
        }
        System.out.printf("máximo de kms num dia: (%d km), dias: [%d]%n%n", kmMax, guarda);
    }

    private static void mostrarPercentagemDosAutocarros(int[] distanciasTotais) {
        String ponto = "*";
        String pontosFinais;
        int somaTotal=0;
        for(int i = 0; i < distanciasTotais.length; i++){
            somaTotal += distanciasTotais[i];
        }
        double per;
        for(int i = 0; i < distanciasTotais.length; i++){
            pontosFinais = "";
            per =  ((double)distanciasTotais[i]/somaTotal) * 100;
            int contagemPontos = ((int)per)/10;
            while(contagemPontos!=0){
                pontosFinais += ponto;
                contagemPontos--;
            }
            System.out.printf("Bus%d (%.2f%%) : %s%n", i, per, pontosFinais);
        }
        System.out.printf("%n");
    }

    private static void verificarDiaOndeTodosAutocarros(int[][] autocarros) {
        boolean valor = false;
        for(int i = 0; i < autocarros[0].length; i++){
            int guarda = 0;
            for(int j = 1; j < autocarros.length; j++){
                if(autocarros[j][i] == autocarros[j-1][i]){
                    valor = true;
                    guarda = j;
                }else{
                    valor = false;
                }
            }
            if(valor){
                String cidade = CIDADES[i];
                System.out.printf("No dia <%d>, todos os autocarros estarão em <%s>%n", guarda, cidade);
            }
        }
        System.out.printf("%n");
    }


    private static void mostrarPermanecemMaisQueUmDia(int[][] autocarros) {
        boolean valor;
        System.out.print("Autocarros que permanecem mais de 1 dia consecutivo na mesma cidade:");
        for(int i = 0; i < autocarros.length; i++) {
            valor = false;
            for (int j = 0; j < autocarros[0].length - 1; j++) {
                    if(autocarros[i][j] == autocarros[i][j+1]){
                        valor = true;
                        //Se a setora deixar pode se dar brake
                        break;
                    }
                }
            if (valor){
                System.out.printf(" Bus%d", i);
            }
        }
        System.out.printf("%n%n");
    }

    private static void mostrarDistanciasTotais(int[] distanciasTotais) {
        int soma=0;
        for(int i = 0; i < distanciasTotais.length; i++) {
            System.out.printf("Bus%d : %d km%n", i, distanciasTotais[i]);
            soma+=distanciasTotais[i];
        }
        System.out.printf("%n");
        System.out.printf("Total de km a percorrer pela frota = %d km%n", soma);
        System.out.printf("%n");
    }

    private static void preencherDistanciasTotais(int[] distanciasTotais, int [][] distanciasMatriz) {
        int soma ;
        for(int i = 0; i < distanciasMatriz.length; i++) {
            soma = 0;
            for (int j = 1; j < distanciasMatriz[0].length; j++) {
                soma += distanciasMatriz[i][j];
            }
            distanciasTotais[i]=soma;
        }
    }

    private static void preencherDistanciasMatriz(int[][] autocarros, int[][] distanciasMatriz) {
        for(int i = 0; i < autocarros.length; i++){
            for(int j = 1; j < autocarros[0].length; j++){
                distanciasMatriz [i][j] = DISTANCIAS[autocarros[i][j]][autocarros[i][j-1]];
            }
        }
    }


    private static void mostrarPlaneamento(int[][] matriz) {
        for(int i = 0; i < matriz.length; i++){
            System.out.printf("Bus%d :", i);
            for(int j = 0; j < matriz[0].length; j++){
                System.out.printf("    %d", matriz[i][j]);
            }
            System.out.printf("%n");
        }
        System.out.printf("%n");
    }

    private static void preencherMatriz(int[][] autocarros, Scanner ler2) {
        for(int i = 0; i < autocarros.length; i++){
            for(int j = 0; j < autocarros[0].length; j++){
                autocarros[i][j]= ler2.nextInt();
            }
        }
    }

    private static int verificaValorInicias(Scanner ler2) {
        int num;
        do{
            num= ler2.nextInt();
        }while(num<=0);
        return num;
    }

}