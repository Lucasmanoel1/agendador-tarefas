package com.lucasmanoel.agendadortarefas.infrastructure;

public class ResourceNotFoudException extends RuntimeException {
    public ResourceNotFoudException(String mensagem) {
        super(mensagem);
    }
    public ResourceNotFoudException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
