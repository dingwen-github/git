package net.qqxh.common.validate.model.manager;

import net.qqxh.common.validate.exception.ModelObtainFailException;
import net.qqxh.common.validate.model.ModelManger;
import net.qqxh.common.validate.model.ValidateModel;
import net.qqxh.common.validate.model.impl.MvcDeliverModel;
import net.qqxh.common.validate.model.impl.MvcReturnModel;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.ArrayList;
import java.util.List;

public class DefaultModelManger implements ModelManger {
    private List<ValidateModel> validateModelList = new ArrayList<>();

    public DefaultModelManger() {
        validateModelList.add(new MvcDeliverModel());

        validateModelList.add(new MvcReturnModel());
    }

    @Override
    public ValidateModel obtainModel(String model, ProceedingJoinPoint joinPoint) throws ModelObtainFailException {
        if (StringUtils.isNotEmpty(model)) {
            for (ValidateModel validateModel : validateModelList) {
                if (validateModel.supportsModel(model)) {
                    return validateModel;
                }
            }
        } else {
            for (ValidateModel validateModel : validateModelList) {
                if (validateModel.supportsModel(joinPoint)) {
                    return validateModel;
                }
            }
        }
        throw new ModelObtainFailException("Pattern matching failed, please try to specify manually");
    }

    @Override
    public void addValidateModel(ValidateModel validateModel) {
        validateModelList.add(validateModel);
    }
}
