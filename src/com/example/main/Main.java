package com.example.main;

import com.example.rsa.MeuRSA;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        MeuRSA meuRSA = new MeuRSA();
        meuRSA.descriptografar(meuRSA.criptografar(getMensagemDeEntrada()));
        System.out.println(meuRSA);
    }

    public static String getMensagemDeEntrada() {
        Scanner mensagem = new Scanner(System.in);
        System.out.printf("Informe a mensagem para criptografar:\n");
        return mensagem.nextLine();
    }
}