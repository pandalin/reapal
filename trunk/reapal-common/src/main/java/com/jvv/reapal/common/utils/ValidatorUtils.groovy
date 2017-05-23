package com.jvv.reapal.common.utils

import com.jvv.reapal.facade.req.AbstractReq

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

/**
 * Created by IntelliJ IDEA
 * <p>〈参数校验〉 </p>
 * 〈参数校验〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 17:37
 * @version 1.0
 */
class ValidatorUtils {

    static void check(AbstractReq req, Class<?>... groups) {
        Set<ConstraintViolation<AbstractReq>> constraintViolations = ValidateUtils.INSTANCE.getValidator()
                .validate(req,groups)
        validate(constraintViolations)
    }

    static <T> void validate(Set<ConstraintViolation<T>> constraintViolations) {
        IllegalArgumentException exception = null
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            StringBuilder infoBuilder = new StringBuilder()
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                infoBuilder.append(constraintViolation.getPropertyPath().toString()).append("=:")
                        .append(constraintViolation.getMessage()).append(";")
            }
            exception = new IllegalArgumentException(infoBuilder.toString())
        }
        if (exception != null) {
            throw exception
        }
    }

}

enum ValidateUtils {
    INSTANCE{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()

        @Override
        Validator getValidator() {
            return factory.getValidator()
        }
    }

    abstract Validator getValidator()
}
