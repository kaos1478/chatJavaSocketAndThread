
package fullpkg;

import java.io.IOException;

public class ExecutaServidor {

	public static void main(String[] args) 
			throws IOException {
		new Servidor(3335).executa();
	}
}