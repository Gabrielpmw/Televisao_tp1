package br.unitins.tp1.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationError error = new ValidationError("400", "Erro de validacao");


        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fullFieldName = violation.getPropertyPath().toString();
            int index = fullFieldName.lastIndexOf(".");
            String fieldName = fullFieldName.substring(index + 1);
            String message = violation.getMessage();

            error.addFieldErro(fieldName, message);

            System.out.println(fullFieldName);
        }

        return Response.status(400).entity(error).build();
    }

}
