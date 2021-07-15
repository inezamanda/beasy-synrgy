package com.synrgybootcamp.project.util;

import com.synrgybootcamp.project.enums.TransactionType;

public class ImageUtil {

  public static String getImageFromTransactionType(TransactionType type) {
    String image = "";
    switch (type) {
      case EWALLET:
        image = "https://res.cloudinary.com/dh9nmeyfy/image/upload/v1626356436/e-wallet_yzbj6v.svg";
      break;
      case TRANSFER:
        image = "https://res.cloudinary.com/dh9nmeyfy/image/upload/v1626356432/transfer_nlktyf.svg";
        break;
      case PAYMENT_MOBILE:
        image = "https://res.cloudinary.com/dh9nmeyfy/image/upload/v1626356586/mobile_amgr8o.svg";
        break;
      case PAYMENT_MERCHANT:
        image = "https://res.cloudinary.com/dh9nmeyfy/image/upload/v1626356420/merchant_ywl1an.svg";
        break;
      case PAYMENT_CREDIT_CARD:
        image = "https://res.cloudinary.com/dh9nmeyfy/image/upload/v1626356499/credit_card_pxcz5o.svg";
        break;
      default:
        break;
    }

    return image;
  }
}
