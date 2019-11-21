
package fullpkg;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;

public class Servidor extends JFrame {
    
        private JLabel lblTitulo, lblSubTitulo, lblPasso1Status, lblPasso2Status;
        private JButton btnSair;
        private JTextArea txtLinhaDoTempo; 
        private String historico = "";
        Calendar data = Calendar.getInstance();
	private int porta;
	private List<Socket> clientes;

	public Servidor(int porta) {
                this.porta = porta;
                setTitle("SERVIDOR CHAT");
                setPreferredSize (new Dimension (600, 300));
                setLayout (null);
                CriarComponentes();
                AdicionarComponentes();
                ConfigurarPosicoes();
                pack();
                setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                setVisible (true);
                ConfigurarComponentes();
                ConfigurarEventos();
		this.clientes = new ArrayList<>();
	}

	public void executa() throws IOException  {
		try(ServerSocket servidor = new ServerSocket(this.porta)){
			
			while (true) {
                                int dia = data.get(Calendar.DATE); 
                                int mes = data.get(Calendar.MONTH); 
                                int ano = data.get(Calendar.YEAR); 
                                int hora = data.get(Calendar.HOUR_OF_DAY); 
                                int min = data.get(Calendar.MINUTE);
                                int seg = data.get(Calendar.SECOND);
				Socket cliente = servidor.accept();
                                this.historico += "("+dia+"/"+(mes+1)+"/"+ano+" - "+hora+":"+min+":"+seg+") Cliente " + cliente.getInetAddress().getHostAddress() + " Conectado!\n";
                                txtLinhaDoTempo.setText(historico);
	
				this.clientes.add(cliente);
	
				TratadorDeMensagemDoCliente tc = new TratadorDeMensagemDoCliente(cliente, this);
				new Thread(tc).start();
			}
		}
	}
        
        public void CriarComponentes(){
            lblTitulo = new JLabel("SERVIDOR (Configurações e Informações)");
            lblSubTitulo = new JLabel("Configurações do Servidor: ");
            lblPasso1Status = new JLabel("Porta Aberta: "+this.porta);
            lblPasso2Status = new JLabel("Status do Servidor: ATIVO");
            txtLinhaDoTempo = new JTextArea("");
            btnSair = new JButton("DESLIGAR SERVIDOR");
        }
        
        public void ConfigurarPosicoes(){
            lblTitulo.setBounds (180, 10, 240, 20);
            lblSubTitulo.setBounds(10, 40, 240, 20);
            lblPasso1Status.setBounds(10, 60, 200, 20);
            lblPasso2Status.setBounds(10, 80, 200, 20);
            txtLinhaDoTempo.setBounds(10, 110, 563, 135);
            btnSair.setBounds(390, 40, 180, 60);
        }
        
        public void AdicionarComponentes() {
            add (lblTitulo);
            add (lblSubTitulo);
            add (lblPasso1Status);
            add (lblPasso2Status);
            add (txtLinhaDoTempo);
            add (btnSair);
        }
        
        public void InformaSaida(String informacao){
            this.historico += informacao;
            txtLinhaDoTempo.setText(historico);
        }
        
        public void ConfigurarEventos(){
            ButtonHandler handler = new ButtonHandler();
            btnSair.addActionListener(handler);
        }
        

        public void ConfigurarComponentes(){
            txtLinhaDoTempo.setEditable(false);
        }
        
        public class ButtonHandler implements ActionListener {
            //TRATA EVENTO DO BOTÃO
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }
        
	public void distribuiMensagem(Socket clienteQueEnviou, String msg) {
		for (Socket cliente : this.clientes) {
			if(!cliente.equals(clienteQueEnviou)){
				try {
					PrintStream ps = new PrintStream(cliente.getOutputStream());
					ps.println("("+clienteQueEnviou.getLocalAddress()+")"+" Disse: "+msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

