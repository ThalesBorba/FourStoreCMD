package br.com.foursys.fourcamp.fourstore.exception;

public class CustomerNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(Long id) {
        System.err.println("Nenhum cliente encontrado com o cpf " + id);
    }

}
