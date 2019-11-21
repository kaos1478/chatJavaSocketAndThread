
package fullpkg;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente extends JFrame {

	private String host;
        private String historico = "";
	private int porta;
        private JButton btnEnviar;
        public static JTextArea txtLinhaDoTempo;
        private JTextArea txtDigitador;
        PrintStream saida;
        Socket cliente;
        RecebedorDeMensagemDoServidor r;
        

	public Cliente(String host, int porta) throws IOException {
                setTitle("CLIENTE CHAT");
                setPreferredSize (new Dimension (600, 600));
                setLayout (null);
                CriarComponentes();
                AdicionarComponentes(); 
                ConfigurarPosicoes(); 
                ConfigurarComponentes();
                ConfigurarEventos();
                
                pack(); 
                setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                setVisible (true);
                txtDigitador.addKeyListener(new java.awt.event.KeyListener(){ 
                    
                    public void keyTyped(KeyEvent K) {
                        
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                       if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            saida.println(txtDigitador.getText());
                            AddTexto("Voce Disse: "+txtDigitador.getText());
                            txtDigitador.setText("");
                        };
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            txtDigitador.setText("");
                        }
                    }
                });
		this.host = host;
		this.porta = porta;
                this.cliente = new Socket(this.host, this.porta);
                this.saida = new PrintStream(this.cliente.getOutputStream());
                this.r = new RecebedorDeMensagemDoServidor(this.cliente.getInputStream());
                //addKeyListener(this);
	}

        
        public class ButtonHandler implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                saida.println(txtDigitador.getText());
                AddTexto("Voce Disse: "+txtDigitador.getText());
                txtDigitador.setText("");
            }
        }
        
        /*KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                                   
                    saida.println(txtDigitador.getText());
                    AddTexto("Voce Disse: "+txtDigitador.getText());
                    
                    System.out.println("OKOKOKOKOK");
            }
        };*/
        
        public static void AddTexto(String texto) {
            txtLinhaDoTempo.setText(txtLinhaDoTempo.getText() + "\n" +  texto);
        }
        
        public void executa()  throws UnknownHostException, IOException {
                        txtLinhaDoTempo.setText("BEM VINDO!");
                        Scanner teclado = new Scanner(System.in); 
			new Thread(this.r).start();
	}
        
        public void CriarComponentes(){
            txtLinhaDoTempo = new JTextArea("");
            txtDigitador = new JTextArea("");
            btnEnviar = new JButton("ENVIAR");
        }
        
        public void ConfigurarPosicoes(){
            txtLinhaDoTempo.setBounds (10, 10, 563, 450);
            txtDigitador.setBounds (10, 470, 450, 75);
            btnEnviar.setBounds (470, 470, 100, 75);
        }
        
        public void AdicionarComponentes() {
            add (txtLinhaDoTempo);
            add (txtDigitador);
            add (btnEnviar);
            
        }
        
        public void ConfigurarEventos(){
            ButtonHandler handler = new ButtonHandler();
            btnEnviar.addActionListener(handler);
        }
        

        public void ConfigurarComponentes(){
            txtLinhaDoTempo.setEditable(false);
        }

}



