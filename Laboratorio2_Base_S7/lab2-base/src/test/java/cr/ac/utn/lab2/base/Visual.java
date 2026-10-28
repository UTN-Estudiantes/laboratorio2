package cr.ac.utn.lab2.base;

/**
 * Pausa SOLO para que se pueda ver lo que hace el navegador (igual que en la Semana 5).
 * No es una buena practica de espera. Se controla con -Dritmo=milisegundos (0 = sin pausas).
 */
public final class Visual {
    private Visual() {}

    public static void pausa() {
        int ms = Integer.getInteger("ritmo", 700);
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
