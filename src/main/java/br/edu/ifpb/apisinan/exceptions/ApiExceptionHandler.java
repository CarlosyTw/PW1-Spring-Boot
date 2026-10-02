package br.edu.ifpb.apisinan.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Converte exceções em respostas Problem Details (RFC 9457, application/problem+json).
 * Nenhum stack trace, SQL ou mensagem interna é devolvido ao cliente.
 */
@RestControllerAdvice
class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(NotificacaoNaoEncontradaException.class)
    ProblemDetail naoEncontrada(NotificacaoNaoEncontradaException ex) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problema.setTitle("Notificação não encontrada");
        problema.setDetail(ex.getMessage());
        return problema;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail dadosInvalidos(MethodArgumentNotValidException ex) {
        return invalido(ex.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    ProblemDetail parametrosInvalidos(BindException ex) {
        return invalido(ex.getBindingResult());
    }

    @ExceptionHandler(RegraNegocioException.class)
    ProblemDetail regraNegocio(RegraNegocioException ex) {
        return invalido(ex.getErros());
    }

    @ExceptionHandler({ConflitoException.class, DataIntegrityViolationException.class})
    ProblemDetail conflito(RuntimeException ex) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problema.setTitle("Conflito de dados");
        problema.setDetail(ex instanceof ConflitoException
                ? ex.getMessage()
                : "A operação viola uma restrição de integridade (por exemplo, número de notificação repetido).");
        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ProblemDetail corpoIlegivel(HttpMessageNotReadableException ex) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Corpo da requisição ilegível");
        problema.setDetail("O corpo não pôde ser lido. Confira se o JSON é válido, se as datas estão "
                + "no formato AAAA-MM-DD e se os campos de opções usam valores permitidos.");
        return problema;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail tipoIncompativel(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Parâmetro inválido");
        problema.setDetail("O valor informado para '" + ex.getName() + "' não é válido.");
        return problema;
    }

    /**
     * Última rede de segurança. Exceções do próprio Spring MVC (405, 415, 404 de rota etc.)
     * já carregam um ProblemDetail e são repassadas com o status correto; o resto vira 500
     * genérico, e o detalhe técnico fica apenas no log do servidor.
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ProblemDetail> inesperada(Exception ex) {
        if (ex instanceof ErrorResponse resposta) {
            return ResponseEntity.status(resposta.getStatusCode())
                    .headers(resposta.getHeaders())
                    .body(resposta.getBody());
        }
        log.error("Falha inesperada", ex);
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problema.setTitle("Erro interno");
        problema.setDetail("Ocorreu um erro inesperado. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problema);
    }

    private ProblemDetail invalido(BindingResult resultado) {
        Map<String, String> erros = new LinkedHashMap<>();
        resultado.getFieldErrors().forEach(
                erro -> erros.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        return invalido(erros);
    }

    private ProblemDetail invalido(Map<String, String> erros) {
        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Dados inválidos");
        problema.setDetail("Um ou mais campos não atendem às regras de preenchimento da ficha.");
        problema.setProperty("errors", erros);
        return problema;
    }
}
