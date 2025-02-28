package vn.hoidanit.laptopshop.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mật khẩu không được để trống.").addConstraintViolation();
            return false;
        }
        
        StringBuilder errorMessage = new StringBuilder();
        
        if (value.length() < 8) {
            errorMessage.append("Mật khẩu phải có ít nhất 8 ký tự. ");
        }
        if (!value.matches(".*\\d.*")) {
            errorMessage.append("Mật khẩu phải chứa ít nhất một chữ số. ");
        }
        if (!value.matches(".*[a-z].*")) {
            errorMessage.append("Mật khẩu phải chứa ít nhất một chữ cái thường. ");
        }
        if (!value.matches(".*[A-Z].*")) {
            errorMessage.append("Mật khẩu phải chứa ít nhất một chữ cái in hoa. ");
        }
        if (!value.matches(".*[@#$%^&+=!*()].*")) {
            errorMessage.append("Mật khẩu phải chứa ít nhất một ký tự đặc biệt (@#$%^&+=!*()). ");
        }

        if (!errorMessage.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage.toString().trim()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
