package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.DateFilter;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.TransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.DashboardService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.ImageUtil;
import com.synrgybootcamp.project.web.model.response.WebDashboardResponse;
import com.synrgybootcamp.project.web.model.response.sub.SubCardInfoResponse;
import com.synrgybootcamp.project.web.model.response.sub.SubRecentTransactionResponse;
import com.synrgybootcamp.project.web.model.response.sub.SubWebGraphResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

@Service
public class DashboardServiceImpl implements DashboardService {

  @Autowired
  private UserInformation userInformation;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PocketRepository pocketRepository;

  @Override
  public WebDashboardResponse webDashboard(DateFilter dateFilter) {
    User loggedInUser = userRepository.findById(userInformation.getUserID())
        .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

    Integer totalBalance = getTotalBalance(loggedInUser.getId());

    SubWebGraphResponse graph = generateGraphData(dateFilter, loggedInUser);
    SubRecentTransactionResponse mostRecentTransaction = generateMostRecenTransactionData(loggedInUser);

    Integer lastFourDigit = Optional.of(loggedInUser).map(User::getCardNumber)
        .map(num -> num.substring(num.length() - 5, num.length() - 1))
        .map(Integer::parseInt)
        .orElse(1234);
    Calendar expiredAt = Calendar.getInstance();
    expiredAt.setTime(loggedInUser.getExpiryDate());

    SubCardInfoResponse card = SubCardInfoResponse
        .builder()
        .balance(totalBalance)
        .cardHolderName(loggedInUser.getFullName())
        .lastFourDigit(lastFourDigit)
        .expiredAt(expiredAt.get(Calendar.MONTH) + "/" + expiredAt.get(Calendar.YEAR))
        .build();

    return WebDashboardResponse.builder()
        .cardInformation(card)
        .currentBalance(totalBalance)
        .graph(graph)
        .recentTransaction(mostRecentTransaction)
        .build();
  }

  private Integer getTotalBalance(String id) {
    Pocket pocket = pocketRepository.findPrimaryPocket(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket utama tidak ditemukan"));

    return pocket.getBalance();
  }

  private SubWebGraphResponse generateGraphData(DateFilter dateFilter, User user) {
    List<Transaction> transactions;

    if (dateFilter.equals(DateFilter.ALL)) {
      transactions = transactionRepository.findByUser(user);
    } else {
      Map<String, Date> startEndDate = getStartEndDate(dateFilter);
      transactions = transactionRepository.findByUserAndDateBetween(user, startEndDate.get("start"), startEndDate.get("end"));
    }

    Map<TransactionType, Integer> aggregatedTransactionData = transactions.stream()
        .collect(Collectors.groupingBy(Transaction::getType, summingInt(Transaction::getTotalAmount)));

    Integer total = aggregatedTransactionData.values().stream().reduce(0, Integer::sum);

    return SubWebGraphResponse
        .builder()
        .data(new ArrayList<>(aggregatedTransactionData.values()))
        .label(generateGraphLabel(aggregatedTransactionData, total))
        .total(total)
        .build();
  }

  private Map<String, Date> getStartEndDate(DateFilter dateFilter) {
    Map<String, Date> startEndDate = new HashMap<>();
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();

    switch (dateFilter) {
      case DAY:
        start.set(start.get(Calendar.YEAR),start.get(Calendar.MONTH),start.get(Calendar.DATE),0,0,0);
        end.set(end.get(Calendar.YEAR),end.get(Calendar.MONTH),end.get(Calendar.DATE),23,59,59);
        break;
      case WEEK:
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        end.setFirstDayOfWeek(Calendar.MONDAY);
        end.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        end.add(Calendar.DAY_OF_WEEK, 7);
        end.set(Calendar.HOUR, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        break;
      case MONTH:
        start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(start.getTime());
        System.out.println(end.getTime());
        break;
      case YEAR:
        start.set(Calendar.DAY_OF_YEAR, 1);
        end.set(Calendar.DAY_OF_YEAR, end.getActualMaximum(Calendar.DAY_OF_YEAR));
        break;
    }

    startEndDate.put("start", start.getTime());
    startEndDate.put("end", end.getTime());

    return startEndDate;
  }

  private List<String> generateGraphLabel(Map<TransactionType, Integer> aggregatedTransactionData, Integer total) {
    return aggregatedTransactionData.keySet().stream().map(key -> {
      double percentage = aggregatedTransactionData.get(key).doubleValue() / total.doubleValue() * 100;
      return Math.round(percentage) + "% " + key.getTextDisplay();
    }).collect(Collectors.toList());
  }

  private SubRecentTransactionResponse generateMostRecenTransactionData(User loggedInUser) {
    return transactionRepository.findFirstByUserOrderByDateDesc(loggedInUser).map(data -> SubRecentTransactionResponse.builder()
        .name(data.getType().getTextDisplay())
        .logo(ImageUtil.getImageFromTransactionType(data.getType()))
        .date(data.getDate())
        .totalAmount(data.getTotalAmount())
        .build()).orElse(null);
  }
}
