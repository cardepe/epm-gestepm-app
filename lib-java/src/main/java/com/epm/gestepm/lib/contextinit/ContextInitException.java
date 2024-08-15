package com.epm.gestepm.lib.contextinit;

public class ContextInitException extends RuntimeException {

    private static final String MSG = "Error creando contexto de la aplicacion";

    public ContextInitException(final String msg, final Exception ex) {
        super(String.join("-", MSG, msg), ex);
    }

}
