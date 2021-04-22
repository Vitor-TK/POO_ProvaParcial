
public class Reserva implements Pagamento {

	private Cliente cliente;
	private boolean pagamentoAVista;

	@Override
	public double calcularPagamento() {
		if (isPagamentoAVista() == true) {
			return 3200 * 10 / 100;
		}
		return 3200;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isPagamentoAVista() {
		return pagamentoAVista;
	}

	public void setPagamentoAVista(boolean pagamentoAVista) {
		this.pagamentoAVista = pagamentoAVista;
	}

	@Override
	public String toString() {
		if (cliente instanceof PessoaFisica) {
			if (isPagamentoAVista() == true) {
				return "{" + "Tipo = pessoa física // Nome = " + cliente.getNome() + " // Pagamento = à vista}";
			}
			return "{" + "Tipo = pessoa física // Nome = " + cliente.getNome() + " // Pagamento = parcelado}";
		}
		else if (isPagamentoAVista() == true) {
			return "{" + "Tipo = pessoa jurídica // Nome = " + cliente.getNome() + " // Pagamento = à vista}";
		} 
		return "{" + "Tipo = pessoa jurídica // Nome = " + cliente.getNome() + " // Pagamento = parcelado}";
	}
}
