import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Reserva[] lista = new Reserva[6];
		Reserva[] listaDeEspera = new Reserva[18];

		int indexList = 0;
		int indexEsp = 0;

		Scanner teclado = new Scanner(System.in);
		String line;
		do {
			System.out.print("> ");
			line = teclado.nextLine();
			switch (line.toLowerCase()) {
			case "help":
				printMenu();
				break;
			case "1":
				if (indexList >= lista.length) {
					System.out
							.println("Número máximo de reservas atingido! O sr(a) vai ser colocado na lista de espera");
					listaDeEspera[indexEsp] = reservarCliente();
					if (indexEsp >= listaDeEspera.length) {
						System.out.println("Lista de espera cheia. Por favor consulte outro dia.");
						break;
					}
					indexEsp++;
					break;
				}
				lista[indexList] = reservarCliente();
				indexList++;
				break;
			case "2":
				System.out.println("Favor insira o CPF ou CNPJ a ser buscado: ");
				String aux = teclado.nextLine();
				int aux2 = pesquisarLista(lista, aux);

				if (pesquisarLista(lista, aux) == -2) {
					if (pesquisarListaEsp(listaDeEspera, aux) == -1) {
						System.out.println("CPF / CNPJ não encontrado no sistema.");
						break;
					} else {
						System.out.println("Cliente não possui reserva. Está lista de espera.");
						break;
					}
				} else {
					System.out.println("Localizado na lista de reservas.");
					break;
				}
			case "3":
				System.out.println("Imprimindo reservas");
				if (indexList == 0) {
					System.out.println("Não há reservas.");
					break;
				}
				for (int i = 0; i < lista.length; i++) {
					System.out.println(lista[i]);
				}
				break;
			case "4":
				System.out.println("Imprimindo lista de espera");
				if (indexEsp == 0) {
					System.out.println("Lista de espera vazia.");
					break;
				}
				for (int i = 0; i < lista.length; i++) {
					System.out.println(listaDeEspera[i] + " Posicao na lista de espera = " + (i + 1) + "°");
				}
				break;
			case "5":
				System.out.println("Favor insira o CPF ou CNPJ a ser buscado:");
				aux = teclado.nextLine();
				if (pesquisarLista(lista, aux) == -2) {
					if (pesquisarListaEsp(listaDeEspera, aux) == -1) {
						System.out.println("CPF não consta no sistema!");
						break;
					} else {
						System.out.println("Retirado da lista de espera");
						indexEsp--;
						listaDeEspera[pesquisarListaEsp(listaDeEspera, aux)] = null;
						for (int i = 0; i < listaDeEspera.length; i++) {
							if (listaDeEspera[i] == null) {
								for (int j = i + 1; j < listaDeEspera.length; j++) {
									listaDeEspera[j - 1] = listaDeEspera[j];
								}
								listaDeEspera[listaDeEspera.length - 1] = null;
								break;
							}
						}
					}
				} else {
					System.out.println("Reserva excluida com sucesso.");
					indexList--;
					lista[pesquisarLista(lista, aux)] = null;
					for (int i = 0; i < lista.length; i++) {
						if (lista[i] == null) {
							for (int j = i + 1; j < lista.length; j++) {
								lista[j - 1] = lista[j];
							}
							lista[lista.length - 1] = null;
							break;
						}
					}
				}
				break;
			case "6":
				break;
			default:
				System.out.println("Opcao invalida");
			}

		} while (!line.equals("6"));

	}

	public static int pesquisarLista(Reserva[] cliente, String aux) {
		for (int i = 0; i < cliente.length; i++) {
			if (cliente[i] == null) {
				return -2;
			} else if (cliente[i].getCliente() instanceof PessoaFisica) {
				PessoaFisica pf = (PessoaFisica) cliente[i].getCliente();
				if (pf.getCpf().equals(aux)) {
					return i;
				}
			} else if (cliente[i].getCliente() instanceof PessoaJuridica) {
				PessoaJuridica pj = (PessoaJuridica) cliente[i].getCliente();
				if (pj.getCnpj().equals(aux)) {
					return i;
				}
			}
		}
		return -2;
	}

	public static int pesquisarListaEsp(Reserva[] cliente, String aux) {
		for (int i = 0; i < cliente.length; i++) {
			if (cliente[i] == null) {
				return -1;
			} else if (cliente[i].getCliente() instanceof PessoaFisica) {
				PessoaFisica pf = (PessoaFisica) cliente[i].getCliente();
				if (pf.getCpf().equals(aux)) {
					return i;
				}
			} else if (cliente[i].getCliente() instanceof PessoaJuridica) {
				PessoaJuridica pj = (PessoaJuridica) cliente[i].getCliente();
				if (pj.getCnpj().equals(aux)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static Reserva reservarCliente() {
		Scanner teclado = new Scanner(System.in);
		Reserva cliente = null;
		String tipoEmpregado = inputTipoCliente();
		switch (tipoEmpregado) {
		case "f":
			cliente = cadastrarF();
			break;
		case "j":
			cliente = cadastrarJ();
			break;
		}

		return cliente;
	}

	private static String inputTipoCliente() {
		String tipoCliente = null;
		while (tipoCliente == null) {
			System.out.print("Pessoa Física (f) ou Jurídica (j): ");
			tipoCliente = new Scanner(System.in).nextLine();
			if (!"f".equals(tipoCliente) && !"j".equals(tipoCliente)) {
				tipoCliente = null;
				System.out.println("Opcao invalida, aceitamos apenas f ou j");
			}
		}
		return tipoCliente;
	}

	private static String inputTipoPagamento() {
		String tipoPag = null;
		while (tipoPag == null) {
			System.out.print("Pagamento à vista (v) ou parcelado (p): ");
			tipoPag = new Scanner(System.in).nextLine();
			if (!"v".equals(tipoPag) && !"p".equals(tipoPag)) {
				tipoPag = null;
				System.out.println("Opcao invalida, aceitamos apenas v ou p");
			}
		}
		return tipoPag;
	}

	private static Reserva cadastrarF() {
		PessoaFisica cliente = new PessoaFisica();
		Reserva reserva = new Reserva();
		Scanner teclado = new Scanner(System.in);
		String tipoPag = inputTipoPagamento();

		System.out.println("Nome:");
		cliente.setNome(teclado.nextLine());

		System.out.println("CPF:");
		cliente.setCpf(teclado.nextLine());

		if (tipoPag.equalsIgnoreCase("v")) {
			reserva.setPagamentoAVista(true);
		} else {
			reserva.setPagamentoAVista(false);
		}

		reserva.setCliente(cliente);

		return reserva;
	}

	private static Reserva cadastrarJ() {
		PessoaJuridica cliente = new PessoaJuridica();
		Reserva reserva = new Reserva();
		Scanner teclado = new Scanner(System.in);
		String tipoPag = inputTipoPagamento();

		System.out.println("Nome:");
		cliente.setNome(teclado.nextLine());

		System.out.println("CPF:");
		cliente.setCnpj(teclado.nextLine());

		if (tipoPag.equalsIgnoreCase("v")) {
			reserva.setPagamentoAVista(true);
		} else {
			reserva.setPagamentoAVista(false);
		}

		reserva.setCliente(cliente);

		return reserva;
	}

	public static void printMenu() {
		System.out.println("--- Menu ---");
		System.out.println("1. Reservar mesa");
		System.out.println("2. Pesquisar reserva");
		System.out.println("3. Imprimir reservas");
		System.out.println("4. Imprimir lista de espera");
		System.out.println("5. Cancelar reserva");
		System.out.println("6. Finalizar");
	}

}
