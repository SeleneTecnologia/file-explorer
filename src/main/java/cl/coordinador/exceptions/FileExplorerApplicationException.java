package cl.coordinador.exceptions;

public class FileExplorerApplicationException extends FileExplorerException {
    public FileExplorerApplicationException(FileExplorerExceptionCodes code) {
        super(code);
    }
}
