package br.com.foursys.fourcamp.fourstore.exception;

public class InvalidSellValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidSellValueException() {
		System.err.println("Lucro menor que 25%. Operação inválida!\n");
	
}
}
