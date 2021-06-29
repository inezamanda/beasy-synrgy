package com.synrgybootcamp.project.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class CreditCardBillUtil {
    private List<Integer> listBill = Arrays.asList(2500000, 3100000, 5200000, 12305000, 9600000, 7400000);
    private List<String> listBank = Arrays.asList("BCA", "Mandiri", "CIMB Niaga", "Danamon", "BNI", "Beasy");
    private List<String> listCardType = Arrays.asList("Mastercard Gold", "Mastercard Silver", "Mastercard Diamond", "Mastercard Platinum", "Mastercard Black", "Mastercard Corporate");
    private Random rand = new Random();

    public Integer getRandomBill() {
        Integer randomBill = listBill.get(rand.nextInt(listBill.size()));
        return randomBill;
    }

    public String getRandomBank() {
        String randomBank = listBank.get(rand.nextInt(listBill.size()));
        return randomBank;
    }

    public String getRandomCardType() {
        String randomCardType = listCardType.get(rand.nextInt(listBill.size()));
        return randomCardType;
    }
}
