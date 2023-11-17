package com.taohansen.dslearn.services.validation;

import com.taohansen.dslearn.dto.GameDTO;
import com.taohansen.dslearn.services.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class YearValidator implements ConstraintValidator<YearValid, GameDTO> {
    @Override
    public void initialize(YearValid ann) {
    }

    @Override
    public boolean isValid(GameDTO gameDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (gameDTO.getYear() < 1950) {
            list.add(new FieldMessage("year", "Ano muito antigo"));
        }
        if (gameDTO.getYear() > 2050) {
            list.add(new FieldMessage("year", "Ano muito distante"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
