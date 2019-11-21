
package fullpkg;

import java.io.IOException;
import java.net.UnknownHostException;

public class ExecutaCliente {
	public static void main(String[] args) throws UnknownHostException,	IOException {
		new Cliente("127.0.0.1", 3335).executa();
	}
}