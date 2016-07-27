/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.cliente;

import br.edu.ifpb.model.Usuario;
import br.edu.ifpb.model.UsuarioIF;
import br.edu.ifpb.server.ServidorIF;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Douglas Torres
 */
public class ClienteApplication {

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);
            ServidorIF s = (ServidorIF) Naming.lookup("ServidorRemoto");
            Usuario c;
            while (true) {
                System.out.println("Digite seu nome: ");
                String nome = keyboard.nextLine();
                c = new Usuario(nome.toUpperCase());
                if (s.addUsuario(c) != null) {
                    break;
                }
            }
            String op;
            do{
            System.out.print("");
            op = keyboard.nextLine().toLowerCase();
            String mensagem;
            if(op.equals("help")){
                op = "10";
            }
                switch (op) {
                    case "0":
                        s.sair(c);
                        System.exit(0);
                        break;
                    case "1":
                        System.out.println("Digite a mensagem: ");
                        mensagem = keyboard.nextLine();
                        s.sendAll(c, mensagem);
                        break;
                    case "2":
                        System.out.println("Digite o nome do usuario: ");
                        String nomeUser = keyboard.nextLine();
                        System.out.println("Digite a mensagem: ");
                        mensagem = keyboard.nextLine();
                        s.sendUser(c, nomeUser.toUpperCase(), mensagem);
                        break;
                    case "3":
                        s.list(c);
                        break;
                    case "4":
                        System.out.println("Digite o novo nome: ");
                        mensagem = keyboard.nextLine();
                        s.rename(c, mensagem.toUpperCase());
                        break;
                    case "10":
                        System.out.println("1 - Envia mensagem para todos");
                        System.out.println("2 - Envia mensagem para um usuario");
                        System.out.println("3 - Lista todos os usuarios na sala");
                        System.out.println("4 - Renomeia o seu nome");
                        System.out.println("0 - sair");
                        break;
                    default:
                        System.out.println("Comando invalido! Digite HELP para ajuda");
                        break;
                }
            }while(!op.equals("0"));
            

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ClienteApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
