package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.enums.DateFilter;
import com.synrgybootcamp.project.web.model.response.WebDashboardResponse;

public interface DashboardService {
  WebDashboardResponse webDashboard(DateFilter dateFilter);
}
