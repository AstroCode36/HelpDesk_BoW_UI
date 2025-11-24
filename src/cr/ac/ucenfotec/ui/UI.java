package cr.ac.ucenfotec.ui;

import java.io.*;

public class UI {
    private BufferedReader in;
    private PrintStream out;

    public UI() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = System.out;
    }

    public String leerTexto() throws IOException {
        return in.readLine();
    }

    public void imprimirMensaje(String msj) {
        out.println(msj);
    }

    public int leerOpcion() throws IOException {
        try {
            return Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}