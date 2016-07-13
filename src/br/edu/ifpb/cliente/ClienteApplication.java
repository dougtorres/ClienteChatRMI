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
            op = keyboard.nextLine();
            String mensagem;
            
                switch (op.toLowerCase()) {
                    case "bye":
                        s.sair(c);
                        System.exit(0);
                        break;
                    case "send -all":
                        System.out.println("Digite a mensagem: ");
                        mensagem = keyboard.nextLine();
                        s.sendAll(c, mensagem);
                        break;
                    case "send -user":
                        System.out.println("Digite o nome do usuario: ");
                        String nomeUser = keyboard.nextLine();
                        System.out.println("Digite a mensagem: ");
                        mensagem = keyboard.nextLine();
                        s.sendUser(c, nomeUser.toUpperCase(), mensagem);
                        break;
                    case "list":
                        s.list(c);
                        break;
                    case "rename":
                        System.out.println("Digite o novo nome: ");
                        mensagem = keyboard.nextLine();
                        s.rename(c, mensagem.toUpperCase());
                        break;
                    case "help":
                        System.out.println("Send -all - Envia mensagem para todos");
                        System.out.println("Send -user - Envia mensagem para um usuario");
                        System.out.println("list - Lista todos os usuarios na sala");
                        System.out.println("rename - Renomeia o seu nome");
                        System.out.println("bye - sair");
                        break;
                    default:
                        System.out.println("Comando invalido!");
                        break;
                }
            }while(op.toLowerCase() != "bye");
            

        } catch (NotBoundException ex) {
            Logger.getLogger(ClienteApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClienteApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
