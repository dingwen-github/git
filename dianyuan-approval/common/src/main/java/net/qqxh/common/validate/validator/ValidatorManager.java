package net.qqxh.common.validate.validator;


import net.qqxh.common.validate.core.Rule;

public interface ValidatorManager {
    Validator getValidatorByType(String validateType);

    boolean match(String val, Rule rule);

    void putValidator(String name, Validator validator);
}
