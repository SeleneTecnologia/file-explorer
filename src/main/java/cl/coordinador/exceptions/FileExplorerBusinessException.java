package cl.coordinador.exceptions;

public class FileExplorerBusinessException extends FileExplorerException {
    public FileExplorerBusinessException(FileExplorerExceptionCodes code) {
        super(code);
    }
}
