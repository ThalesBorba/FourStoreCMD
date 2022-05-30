package br.com.foursys.fourcamp.fourstore.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.foursys.fourcamp.fourstore.controller.ProductController;
import br.com.foursys.fourcamp.fourstore.controller.TransactionController;
import br.com.foursys.fourcamp.fourstore.enums.PaymentMethodEnum;
import br.com.foursys.fourcamp.fourstore.exception.ProductNotFoundException;
import br.com.foursys.fourcamp.fourstore.exception.StockInsufficientException;
import br.com.foursys.fourcamp.fourstore.model.Stock;
import br.com.foursys.fourcamp.fourstore.utils.RunTime;

public class TransactionCommunication {
	static Scanner sc = new Scanner(System.in);
	static ProductController productController = new ProductController();
	static TransactionController transactionController = new TransactionController();

	public static void purchase() throws ProductNotFoundException, StockInsufficientException {
		List<Stock> cart = new ArrayList<Stock>();
		String option = "s";

		while (option.equalsIgnoreCase("S")) {
			System.out.print("Digite o SKU do produto: ");
			String sku = sc.nextLine();
			Integer quantity = 0;
			boolean validate = false;

			while (!validate) {
				try {
					System.out.print("Digite a quantidade que deseja comprar: ");
					quantity = Integer.parseInt(sc.nextLine());
					validate = true;
				} catch (Exception e) {
					System.out.println("Quantidade inv�lida!");
				}

				try {
					Stock stock = productController.validatePrePurchase(sku, quantity);
					if (stock != null) {
						cart.add(stock);
						validate = true;
					} else {
						System.out.println("Quantidade insuficiente ou produto n�o encontrado!");
					}

				} catch (Exception e) {
					System.out.println("Quantidade insuficiente ou produto n�o encontrado!");
				}
			}

			System.out.print("Deseja continuar? S/N ");
			option = sc.nextLine();
		}

		Double totalPrice = 0.0;

		for (Stock stock : cart) {
			System.out.println(stock.toString());
			totalPrice += stock.getProduct().getSellPrice() * stock.getQuantity();
		}

		if (totalPrice == 0.0) {
			return;
		}

		System.out.println("Pre�o total: " + totalPrice);

		System.out.print("\nDigite o nome do cliente: ");
		String name = sc.nextLine();

		boolean validate = false;
		String cpf = "";

		while (!validate) {
			System.out.print("\nDeseja inserir o cpf? S/N ");
			char choice = sc.nextLine().charAt(0);

			if (choice == 's' || choice == 'S') {
				while (!validate) {
					System.out.print("Digite o CPF: ");
					cpf = sc.nextLine();
					if (cpf.length() == 11) {
						validate = true;
					} else {
						System.err.println("O cpf deve ter 11 n�meros!");
						RunTime.ThreadDelay();
					}
				}
			} else if (choice == 'n' || choice == 'N') {
				validate = true;
			} else {
				System.out.println("Op��o inv�lida!");

			}
		}

		for (PaymentMethodEnum payment : PaymentMethodEnum.values()) {
			System.out.println(payment.getPaymentMethodId() + "  " + payment.getPaymentMethod());
		}

		Integer payment = 0;
		String paymentData = null;

		validate = false;

		while (!validate) {
			try {
				System.out.print("Escolha um m�todo pelo d�gito: ");
				payment = Integer.parseInt(sc.nextLine());

				paymentData = checkPayment(payment);
				if (paymentData == null || paymentData.equals("")) {
					paymentData = PaymentMethodEnum.getByPaymentMethodId(payment).getPaymentMethod();
				}
				validate = true;
			} catch (Exception e) {
				System.out.println("Op��o inv�lida!");
				validate = false;
			}
		}

		if (cpf.equals("")) {
			System.out.println(transactionController.purchase(cart, name, payment, paymentData));
		} else {
			System.out.println(transactionController.purchase(cart, name, cpf, payment, paymentData));
		}
	}

	public static void listTransactions() {
		System.out.println(transactionController.listAll());
	}

	public static String checkPayment(Integer option) {
		String data = null;
		String temp;
		Integer cardNumber;
		boolean validator = false;

		switch (option) {
		case 3:
			while (!validator) {
				try {
					System.out.print("Digite o numero do cart�o");
					data = "Cr�dito\n" + "N�mero do cart�o: ";
					temp = sc.nextLine();

					if (temp.length() == 12 && temp.matches("[0-9]+")) {
						data += temp + "\nC�d. de Seguran�a: ";

						System.out.print("Digite o c�digo de seguran�a: ");
						temp = sc.nextLine();

						if (temp.length() != 3) {
							System.err.println("N�mero do c�digo de seguran�a inv�lido! Precisa ter 3 digitos");
						} else {
							cardNumber = Integer.parseInt(temp);
							data += cardNumber + "\nVencimento: ";

							System.out.print("Digite a data de validade: MM:AA");
							temp = sc.nextLine();

							if (temp.substring(2, 3).equals("/") && temp.length() == 5) {
								String[] date = temp.split("/");
								Integer month = Integer.parseInt(date[0]);
								Integer year = Integer.parseInt(date[1]);

								if (month > 0 && month < 13 && year > 21) {
									data += temp;
									validator = true;
									return data;
								}
							}
						}
					} else {
						System.err.println("N�mero do cart�o inv�lido! Precisa ter 12 digitos");
					}
				} catch (Exception e) {
					System.err.println("Entrada inv�lida");
				}
			}
			break;
		case 4:
			while (!validator) {
				try {
					System.out.print("Digite o numero do cart�o");
					data = "D�bito\n" + "N�mero do cart�o: ";
					temp = sc.nextLine();

					if (temp.length() == 12 && temp.matches("[0-9]+")) {
						data += temp + "\nC�d. de Seguran�a: ";

						System.out.print("Digite o c�digo de seguran�a: ");
						temp = sc.nextLine();

						if (temp.length() != 3) {
							System.err.println("N�mero do c�digo de seguran�a inv�lido! Precisa ter 3 digitos");
						} else {
							cardNumber = Integer.parseInt(temp);
							data += cardNumber + "\nVencimento: ";

							System.out.print("Digite a data de validade: MM:AA");
							temp = sc.nextLine();

							if (temp.substring(2, 3).equals("/") && temp.length() == 5) {
								String[] date = temp.split("/");
								Integer month = Integer.parseInt(date[0]);
								Integer year = Integer.parseInt(date[1]);

								if (month > 0 && month < 13 && year > 21) {
									data += temp;
									validator = true;
									return data;
								}
							}
						}
					} else {
						System.err.println("N�mero do cart�o inv�lido! Precisa ter 12 digitos");
					}
				} catch (Exception e) {
					System.err.println("Entrada inv�lida");
				}
			}
			break;
		case 5:

			while (!validator) {
				System.out.print("Digite sua chave pix: ");
				temp = sc.nextLine();

				if (temp != "") {
					data = "Pix\nChave: " + temp;
					validator = true;
					return data;
				} else {
					System.err.println("Chave Inv�lida");
				}
			}
			break;
		case default:
			return data;
		}
		return data;
	}
}
