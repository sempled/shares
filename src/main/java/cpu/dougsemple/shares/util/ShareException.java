package cpu.dougsemple.shares.util;

public class ShareException extends Exception {

    public ShareException() {
        super("Error in shares application");
    }

    public ShareException(String msg) {
        super(msg);
    }
}
