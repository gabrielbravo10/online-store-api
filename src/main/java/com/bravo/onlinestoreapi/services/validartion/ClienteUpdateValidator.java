package com.bravo.onlinestoreapi.services.validartion;

import com.bravo.onlinestoreapi.controllers.exception.FieldMessage;
import com.bravo.onlinestoreapi.dtos.ClienteDto;
import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDto> {


    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate clienteUpdate) {
    }

    @Override
    public boolean isValid(ClienteDto clienteDto, ConstraintValidatorContext constraintValidatorContext) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) httpServletRequest
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente client = clienteRepository.findByEmail(clienteDto.getEmail());

        if (client != null && !client.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email ja existente"));
        }

        for (FieldMessage fieldMessage : list) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
