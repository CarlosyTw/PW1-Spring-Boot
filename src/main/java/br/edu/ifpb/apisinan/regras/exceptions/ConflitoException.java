package br.edu.ifpb.apisinan.exceptions;

/** Conflito com o estado atual dos dados (ex.: número de notificação já cadastrado). */
public class ConflitoException extends RuntimeException {

    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}
