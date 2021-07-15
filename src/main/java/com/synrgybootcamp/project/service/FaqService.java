package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.FaqRequest;
import com.synrgybootcamp.project.web.model.response.FaqResponse;

import java.util.List;

public interface FaqService {
    List<FaqResponse> getAllFaqs();
    FaqResponse getFaqByID(String id);
    FaqResponse addNewFaq(FaqRequest faqRequest);
    FaqResponse editFaq(String id, FaqRequest faqRequest);
    FaqResponse deleteFaq(String id);
}
