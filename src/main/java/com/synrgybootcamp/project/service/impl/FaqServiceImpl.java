package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Faq;
import com.synrgybootcamp.project.repository.FaqRepository;
import com.synrgybootcamp.project.service.FaqService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.FaqRequest;
import com.synrgybootcamp.project.web.model.response.FaqResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqServiceImpl implements FaqService {
    @Autowired
    private FaqRepository faqRepository;

    @Override
    public List<FaqResponse> getAllFaqs() {
        List<Faq> faqs = faqRepository.findAll();
        return faqs.stream()
                .map(faq -> FaqResponse
                        .builder()
                        .question(faq.getQuestion())
                        .answer(faq.getAnswer())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public FaqResponse getFaqByID(String id) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "FAQ Not Found"));
        return FaqResponse
                .builder()
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }

    @Override
    public FaqResponse addNewFaq(FaqRequest faqRequest) {
        Faq faq = faqRepository.save(
                Faq
                        .builder()
                        .question(faqRequest.getQuestion())
                        .answer(faqRequest.getAnswer())
                        .build()
        );
        return FaqResponse
                .builder()
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }

    @Override
    public FaqResponse editFaq(String id, FaqRequest faqRequest) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "FAQ Not Found"));
        faq.setQuestion(faqRequest.getQuestion());
        faq.setAnswer(faqRequest.getAnswer());
        faqRepository.save(faq);
        return FaqResponse
                .builder()
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }

    @Override
    public FaqResponse deleteFaq(String id) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "FAQ Not Found"));
        faqRepository.delete(faq);
        return FaqResponse
                .builder()
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }
}
