package br.unitins.tp1.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends Error {
    
    public static class FieldError {
        private String fieldName;
        private String message;

        public FieldError(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getMessage() {
            return message;
        }
    }

    private List<FieldError> erros = null;

    public ValidationError(String code, String message) {
        super(code, message);
    }

    public List<FieldError> getErros() {
        return erros;
    }

    public void addFieldErro(String fieldName, String message) {
        if (erros == null)
            erros = new ArrayList<>();
        erros.add(new FieldError(fieldName, message));
    }
}