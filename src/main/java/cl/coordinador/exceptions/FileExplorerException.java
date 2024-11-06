package cl.coordinador.exceptions;

public class FileExplorerException extends RuntimeException {

    private final FileExplorerExceptionCodes code;

    public FileExplorerException(FileExplorerExceptionCodes code) {
        super(code.getMessage());
        this.code = code;
    }

    public FileExplorerExceptionCodes getCode() {
        return code;
    }


    public FileExplorerException(String message, FileExplorerExceptionCodes code) {
        super(message);
        this.code = code;
    }

}