package com.example;

import java.io.*;
import java.net.*;

public class ClaintStr {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    public Socket connetti(){
        System.out.println("2 CLIENT partito in esecuzione ...");
        try{
            //per l'input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            //creo un socket
            miosocket = new Socket(nomeServer, portaServer);
            //miosocket = new Socket(IntAddress.getLocalHost(), 6789);
            //associo due oggetti al socket per effettuare la scrittura e la lettura
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        }
        catch(UnknownHostException e){
            System.err.println("Host sconosciuto");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunica(){
        for(;;) //ciclo infinito: termina con FINE
        try{
            System.out.println("4 ... utente, inserisci la stringa da trasmettere al server:");
            stringaUtente = tastiera.readLine();
            //la spedisco al server
            System.out.println("5 ... invio la stringa al server e attendo ...");
            outVersoServer.writeBytes(stringaUtente + '\n');
            //leggo la risposta del server
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("7 ... risposta dal server " + '\n' + stringaRicevutaDalServer);
            if(stringaUtente.eqals("FINE")){
                System.out.println("8 CLIENT: termina elaborazione e chiude connessione");
                miosocket.close(); //chiudo la connessione
                break;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }
}