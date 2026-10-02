package br.edu.ifpb.apisinan.exceptions;

public class NotificacaoNaoEncontradaException extends RuntimeException {

    public NotificacaoNaoEncontradaException(Long id) {
        super("Não existe notificação com id " + id + ".");
    }
}