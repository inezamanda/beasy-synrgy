package com.synrgybootcamp.project.util;

import com.synrgybootcamp.project.enums.MobileOperator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PhoneNumberChecker {
    private final List<String> TELKOMSEL = Arrays.asList("0811", "0812", "0813", "0821", "0822", "0823", "0852", "0851");
    private final List<String> INDOSAT = Arrays.asList("0855", "0856", "0857", "0858", "0814", "0815", "0816");
    private final List<String> XL = Arrays.asList("0817", "0818", "0819", "0859", "0877", "0878");
    private final List<String> AXIS = Arrays.asList("0838", "0831", "0832", "0833");
    private final List<String> TRI = Arrays.asList("0896", "0897", "0898", "0899");
    private final List<String> SMARTFREN = Arrays.asList("0881", "0882", "0883", "0884", "0885", "0886", "0887", "0888");

    public MobileOperator numberChecker(String phoneNumber) {
        String number = phoneNumber.substring(0,4);
        if(number.substring(0, 3).equals("+62")){
            number = number.replaceFirst("\\+62", "0");
        }
        if (TELKOMSEL.contains(number)) {
            return MobileOperator.TELKOMSEL;
        } else if (INDOSAT.contains(number)) {
            return MobileOperator.INDOSAT;
        } else if (XL.contains(number)) {
            return MobileOperator.XL;
        } else if (AXIS.contains(number)) {
            return MobileOperator.AXIS;
        } else if (TRI.contains(number)) {
            return MobileOperator.TRI;
        } else if (SMARTFREN.contains(number)) {
            return MobileOperator.SMARTFREN;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Nomor yang anda masukkan salah");
        }
    }
}
