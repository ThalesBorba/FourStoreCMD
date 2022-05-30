package br.com.foursys.fourcamp.fourstore.communication;

import java.util.Scanner;

import br.com.foursys.fourcamp.fourstore.controller.ProductController;
import br.com.foursys.fourcamp.fourstore.enums.MenuEnum;
import br.com.foursys.fourcamp.fourstore.exception.InvalidInputException;
import br.com.foursys.fourcamp.fourstore.exception.InvalidSellValueException;
import br.com.foursys.fourcamp.fourstore.exception.ProductNotFoundException;
import br.com.foursys.fourcamp.fourstore.exception.StockInsufficientException;
import br.com.foursys.fourcamp.fourstore.utils.RunTime;

public class MainMenu {

	static Scanner sc = new Scanner(System.in);

	public MainMenu() throws InvalidSellValueException, ProductNotFoundException, StockInsufficientException, InvalidInputException { menu();}
	/*	{
			String mockUser = "Admin";
			String mockPassword = "********";

			while (true) {
				System.out.println("### FAÇA SEU LOGIN ###");
				System.out.println("----------------------");
				System.out.print("Digite seu login: ");
				String user = sc.nextLine();
				System.out.print("Digite sua senha: ");
				String password = sc.nextLine();

				if ((!user.equals(mockUser) || (!password.equals(mockPassword)))) {
					System.out.println("Acesso negado!!!");
				} else { 
					menu();
					break;
				}
			}
		}
	}*/

	public void menu() throws InvalidSellValueException, ProductNotFoundException, StockInsufficientException, InvalidInputException {
		boolean validator = true;
		boolean initialStock = false;		
		Scanner input = new Scanner(System.in);
		String option = " ";
		while (validator) {
			System.out.println("\n## MENU PRINCIPAL ##");
			System.out.println("|------------------|");
			for (MenuEnum navigation : MenuEnum.values()) {
				System.out.println("  " + navigation.getOption() + " - " + navigation.getDescription());
			}
			System.out.println("|------------------|");
			System.out.print("Opção: ");
			option = input.nextLine();
			System.out.println();
			
			switch (option) {
				case "0" -> {
					System.out.println("### ENCERRANDO SISTEMA... ATÉ A PROXIMA ###");
					System.exit(0);
				}
				case "1" -> transactionMenu();
				case "2" -> initialStock = stockMenu(initialStock);
				default -> {
					System.err.println("Digite uma opção válida");
					RunTime.ThreadDelay();
				} 
			}
		}
		
	}

	private boolean stockMenu(boolean initialStock) throws InvalidSellValueException, ProductNotFoundException, InvalidInputException {
		Integer option = null;
		boolean validate = false;
		
		while (!validate) {
			System.out.println();
			System.out.println("########  MENU ESTOQUE ########");
			System.out.println("+------------------------------+");
			System.out.println("| 1 - CADASTRAR PRODUTO        |");
			System.out.println("| 2 - BUSCAR POR SKU           |");
			System.out.println("| 3 - LISTAR TODO ESTOQUE      |");
			System.out.println("| 4 - ATUALIZAR PRODUTO        |");
			System.out.println("| 5 - VOLTAR AO MENU ANTERIOR  |");
			System.out.println("| 6 - INICIALIZAR ESTOQUE      |");
			System.out.println("+------------------------------+");
			System.out.println("| 7 - SAIR                     |");
			System.out.println("+------------------------------+");
			System.out.print("\nOpção: ");

			try {
				option = Integer.parseInt(sc.nextLine());
				switch (option) {
					case 1 -> StockMenuCommunication.createProduct();					
					case 2 -> StockMenuCommunication.searchForSku();			
					case 3 -> StockMenuCommunication.listAllStock();				
					case 4 -> stockMenuUpdate();
					case 5 -> {break;}
					case 6 -> {
						if(!initialStock) {
							initialStock();
							System.out.println("\nEstoque inicial criado!");
							return true;
						}
					}
					case 7 -> {
						System.out.println("### ENCERRANDO SISTEMA... ATÉ A PROXIMA ###");
						System.exit(0);
						break;
					}
					case default -> throw new InvalidInputException(option);		
				}
				validate = true;
			} catch (InvalidInputException e) {

			} catch (NumberFormatException e) {
				throw new InvalidInputException(option);
			}

			finally {
				continue;
			}

		}
		return true;
	}

	private void transactionMenu() throws ProductNotFoundException, StockInsufficientException {
		Integer option = null;
		boolean validate = false;

		while (!validate) {
			System.out.println();
			System.out.println("########  MENU VENDAS ########");
			System.out.println("+------------------------------+");
			System.out.println("| 1 - REALIZAR VENDAS          |");
			System.out.println("| 2 - HISTÓRICO DE VENDAS      |");
			System.out.println("| 3 - VOLTAR AO MENU ANTERIOR  |");
			System.out.println("+------------------------------+");
			System.out.println("| 4 - SAIR                     |");
			System.out.println("+------------------------------+");
			System.out.print("\nOpção: ");

			try {
				option = Integer.parseInt(sc.nextLine());
				switch (option) {
					case 1 -> TransactionCommunication.purchase();
					case 2 -> TransactionCommunication.listTransactions();					
					case 3 -> {break;}
					case 4 -> {
						System.out.println("### ENCERRANDO SISTEMA... AT� A PROXIMA ###");
						System.exit(0);
					}
					case default -> throw new InvalidInputException(option);					
					}

				validate = true;
			} catch (InvalidInputException e) {

			} finally {
				continue;
			}
		}

	}
	
	private void stockMenuUpdate() throws InvalidSellValueException, ProductNotFoundException, InvalidInputException {
		Integer option = null;
		boolean validate = false;

		while (!validate) {
			System.out.println();
			System.out.println("########  ATUALIZAR ESTOQUE ########");
			System.out.println("+------------------------------+");
			System.out.println("| 1 - ATUALIZAR QUANTIDADE     |");
			System.out.println("| 2 - ATUALIZAR PREÇO          |");
			System.out.println("| 3 - EXCLUIR PRODUTO          |");
			System.out.println("| 4 - VOLTAR AO MENU ANTERIOR  |");
			System.out.println("+------------------------------+");
			System.out.println("| 5 - SAIR                     |");
			System.out.println("+------------------------------+");
			System.out.print("\nOpção: ");

			try {
				option = Integer.parseInt(sc.nextLine());
				
				switch (option) {
					case 1 -> StockMenuCommunication.updateProductQuantity();					
					case 2 -> StockMenuCommunication.updateProductPrice();						
					case 3 -> StockMenuCommunication.deleteProduct();
					case 4 -> stockMenu(validate);
					case 5 -> System.exit(0);
					case default -> throw new InvalidInputException(option);	
					
				}

				validate = true;
			} catch (NumberFormatException e) {
				throw new InvalidInputException(option);
			} catch (InvalidInputException e){

			} finally {
				continue;
			}

		}

	}
	
	public void initialStock() throws InvalidSellValueException {
		ProductController controller = new ProductController();
	controller.insertProduct("OBT3711415123655", "Camiseta Teste", 10, 10.00, 20.00);
	controller.insertProduct("AVN4312425223956", "Meia Teste", 35, 2.50, 10.00);
	controller.insertProduct("RVP4511415223354", "Shorts Teste", 55, 38.50, 80.00);
	controller.insertProduct("OLP3211425223452", "Bermuda Teste", 55, 20.50, 62.00);
	}
}


