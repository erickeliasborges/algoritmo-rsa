package com.example.rsa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MeuRSA {

    private int chavePublica;
    private int chavePrivada;
    private Integer seq = 0;
    int n;
    private String mensagem = "";
    private String mensagemEmCodigo = "";
    private String mensagemCriptografada = "";
    private String mensagemDescriptografada = "";

    public MeuRSA() {
        calcularChavePrivadaEPublica();
    }

    public double[] criptografar(String mensagemACriptografar) {
        this.mensagem = mensagemACriptografar;
        int[] mensagemEmCodigo = mensagemToCodigo(mensagem);
        double[] mensagemCriptografada = new double[mensagemEmCodigo.length];
        seq = 0;
        for (int value : mensagemEmCodigo) {
            mensagemCriptografada[seq] = ((Math.pow(value, this.chavePublica)) % n);
            this.mensagemCriptografada += (this.mensagemCriptografada.isEmpty() ? "" : ", ") + mensagemCriptografada[seq];
            seq++;
        }
        return mensagemCriptografada;
    }

//    Para poder criptografar a mensagem utilizei o código numérico de cada caracter
    private int[] mensagemToCodigo(String mensagem) {
        int[] mensagemEmCodigo = new int[mensagem.length()];
        seq = 0;
        for (char character : mensagem.toCharArray()) {
            mensagemEmCodigo[seq] += character;
            this.mensagemEmCodigo += (this.mensagemEmCodigo.isEmpty() ? "" : ", ") + mensagemEmCodigo[seq];
            seq++;
        }

        return mensagemEmCodigo;
    }

    public String descriptografar(double[] mensagemCriptografada) {
        List<BigInteger> mensagemDescriptografada = new ArrayList<>();
        seq = 0;
        for (double value : mensagemCriptografada) {
            BigInteger valorEmBigDecimal = BigDecimal.valueOf(value).toBigInteger();
            BigInteger result = (valorEmBigDecimal.pow(this.chavePrivada)).mod(BigInteger.valueOf(n));
            mensagemDescriptografada.add(result);
            seq++;
        }

        for (BigInteger bigInteger : mensagemDescriptografada) {
            this.mensagemDescriptografada += Character.toString(bigInteger.intValue());
        }
        return this.mensagemDescriptografada;
    }

    public void calcularChavePrivadaEPublica() {
        int p, q, r, d = 0, e, i;

//        Passo 1. Escolha dois números primos grandes diferentes 𝑝 e 𝑞.
        p = 661;
        q = 683;

//        Passo 2. Calcule 𝑛 onde 𝑛 = 𝑝𝑞.
        n = p * q;

//        Passo 3. Calcule o Euler totient (uma função phi) ϕ(𝑛) = (𝑝−1)(𝑞−1).
        r = (p - 1) * (q - 1);

//        Passo 4. Escolha um inteiro 𝑒 tal que 1 < 𝑒 < ϕ(𝑛) e 𝑒 é co-primo a ϕ(𝑛).
        for (e = 2; e < r; e++) {
            // O valor de e refere-se a chave pública
            if (mdc(e, r) == 1) {
                break;
            }
        }

//        Passo 5. Calcule 𝑑 tal que a relação de = 1 + 𝑥𝜙(𝑛) seja verdadeira para algum inteiro 𝑥. Lembrando que 𝑑 também é um inteiro.
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * r);
//            O valor de d refere-se a chave privada
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        this.chavePrivada = d;
        this.chavePublica = e;
    }

    private int mdc(int e, int r) {
        if (e == 0)
            return r;
        else
            return mdc(r % e, e);
    }

    public int getChavePublica() {
        return this.chavePublica;
    }

    public int getChavePrivada() {
        return this.chavePrivada;
    }

    public String toString() {
        return
                "\n------------------Chave privada-----------------------\n" +
                this.chavePrivada + "\n" +
                "\n------------------Chave pública-----------------------\n" +
                this.chavePublica + "\n" +
                "\n------------------Valor de n--------------------------\n" +
                this.n + "\n" +
                "\n------------------Mensagem----------------------------\n" +
                this.mensagem + "\n" +
                "\n------------------Mensagem em código (vírgula somente para visualizar melhor, não faz parte da chave)------------------\n" +
                this.mensagemEmCodigo + "\n" +
                "\n------------------Mensagem criptografada (vírgula somente para visualizar melhor, não faz parte da chave)--------------\n" +
                this.mensagemCriptografada + "\n" +
                "\n------------------Mensagem descriptografada-----------\n" +
                this.mensagemDescriptografada;
    }

}
