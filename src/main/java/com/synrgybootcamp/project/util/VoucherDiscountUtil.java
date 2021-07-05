package com.synrgybootcamp.project.util;

import java.util.UUID;

public class VoucherDiscountUtil {
  public static String generateVoucher() {
    String uuid = UUID.randomUUID().toString();
    return "BEASY-" + uuid;
  }
}
