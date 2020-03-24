package net.qqxh.common.validate.model;

import net.qqxh.common.validate.exception.ModelObtainFailException;
import org.aspectj.lang.ProceedingJoinPoint;

public interface ModelManger {
    ValidateModel obtainModel(String model, ProceedingJoinPoint joinPoint) throws ModelObtainFailException;

    void addValidateModel(ValidateModel validateModel);
}
