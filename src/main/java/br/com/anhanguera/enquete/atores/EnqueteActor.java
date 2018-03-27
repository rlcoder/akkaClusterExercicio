package br.com.anhanguera.enquete.atores;

import java.io.Serializable;

import akka.actor.AbstractLoggingActor;

public class EnqueteActor extends AbstractLoggingActor {

	//Trata todas as mensagens enviadas para este ator
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CadastrarEnquete.class, this::cadastrarEnquete)
				.build();
	}
	
	private void cadastrarEnquete(CadastrarEnquete envelope) {
		//logica de negocio para cadastrar uma enquete
	}
	
	public static class CadastrarEnquete 
		implements Serializable{
		
	}

}
