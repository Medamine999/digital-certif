import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 4000;

        // Charger le truststore du client
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("clientTruststore.jks"), "passserver123".toCharArray());

        // Initialiser le TrustManagerFactory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(trustStore);

        // Initialiser le SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, port);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("Hello from client!");
        System.out.println("Received from server: " + in.readLine());

        socket.close();
    }
}
