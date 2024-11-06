package cl.coordinador.exceptions;

public enum FileExplorerExceptionCodes {

    FILE_EXPLORER_GENERAL_ERROR("Error no controlado en el sistema.");

    FileExplorerExceptionCodes(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}
