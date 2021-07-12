package com.synrgybootcamp.project.util;

import com.synrgybootcamp.project.enums.TransactionType;

public class ImageUtil {

  public static String getImageFromTransactionType(TransactionType type) {
    String image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
    switch (type) {
      case EWALLET:
        image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
        break;
      case TRANSFER:
        image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
        break;
      case PAYMENT_MOBILE:
        image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
        break;
      case PAYMENT_MERCHANT:
        image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
        break;
      case PAYMENT_CREDIT_CARD:
        image = "https://www.freepnglogos.com/uploads/logo-bca-png/bank-bca-logo-bca-aroma-incense-website-2.png";
        break;
      default:
        break;
    }

    return image;
  }
}
