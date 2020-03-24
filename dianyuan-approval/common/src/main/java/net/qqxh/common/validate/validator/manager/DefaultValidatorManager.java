package net.qqxh.common.validate.validator.manager;

import net.qqxh.common.validate.core.Rule;
import net.qqxh.common.validate.enums.ValidatorType;
import net.qqxh.common.validate.validator.Validator;
import net.qqxh.common.validate.validator.ValidatorManager;
import net.qqxh.common.validate.validator.impl.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultValidatorManager implements ValidatorManager {
    private Map<String, Validator> validators = new LinkedHashMap<>();

    public DefaultValidatorManager() {
        validators.put(ValidatorType.REQUIRED, new RequireValidator());
        validators.put(ValidatorType.DATE, new DateValidator());
        validators.put(ValidatorType.INT, new IntValidator());
        validators.put(ValidatorType.DOUBLE, new DoubleValidator());
        validators.put(ValidatorType.LONG, new LongValidator());
        validators.put(ValidatorType.REGEX, new RegexValidator());
        validators.put(ValidatorType.IN, new InValidator());
        validators.put(ValidatorType.LIKE, new LikeValidator());
    }

    @Override
    public Validator getValidatorByType(String validateType) {
        Validator validator = validators.get(validateType);
        if (validator != null) {
            return validator;
        }
        return new DefaultValidator();
    }

    @Override
    public boolean match(String val, Rule rule) {
        Validator validator = getValidatorByType(rule.getValidateType());
        if (validator != null) {
            return validator.match(val, rule.getValidateRule());
        }
        return true;
    }

    @Override
    public void putValidator(String name, Validator validator) {
        validators.put(name, validator);
    }
}
