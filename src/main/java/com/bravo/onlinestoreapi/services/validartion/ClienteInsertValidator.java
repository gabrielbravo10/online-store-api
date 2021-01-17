package com.bravo.onlinestoreapi.services.validartion;

import com.bravo.onlinestoreapi.controllers.exception.FieldMessage;
import com.bravo.onlinestoreapi.dtos.ClienteNewDto;
import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.entities.enums.TipoCliente;
import com.bravo.onlinestoreapi.repositories.ClienteRepository;
import com.bravo.onlinestoreapi.services.validartion.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert clienteInsert) {
    }

    @Override
    public boolean isValid(ClienteNewDto clienteNewDto, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();


        if (clienteNewDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod())
                && !BR.isValidCPF(clienteNewDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF Invalido"));
        }

        if (clienteNewDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod())
                && !BR.isValidCNPJ(clienteNewDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Invalido"));
        }

        Cliente client = clienteRepository.findByEmail(clienteNewDto.getEmail());
        if (client != null) {
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
