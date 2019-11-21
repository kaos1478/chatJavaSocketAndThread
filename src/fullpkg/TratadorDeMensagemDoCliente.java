
package fullpkg;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

class TratadorDeMensagemDoCliente implements Runnable {

	private Socket cliente;
	private Servidor servidor;
        private String msg;
        Calendar data = Calendar.getInstance();

	public TratadorDeMensagemDoCliente(Socket cliente, Servidor servidor) {
		this.cliente = cliente;
		this.servidor = servidor;
	}

	public void run() {
		try(Scanner s = new Scanner(this.cliente.getInputStream())) {
			while (s.hasNextLine()) {
				servidor.distribuiMensagem(this.cliente, s.nextLine());
			}
                        int dia = data.get(Calendar.DATE); 
                        int mes = data.get(Calendar.MONTH); 
                        int ano = data.get(Calendar.YEAR); 
                        int hora = data.get(Calendar.HOUR_OF_DAY); 
                        int min = data.get(Calendar.MINUTE);
                        int seg = data.get(Calendar.SECOND);
                        this.msg = this.cliente.getLocalAddress()+ "";
                        servidor.InformaSaida("("+dia+"/"+(mes+1)+"/"+ano+" - "+hora+":"+min+":"+seg+") Cliente " + this.msg.replace("/", "") + " Desconectou! \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}