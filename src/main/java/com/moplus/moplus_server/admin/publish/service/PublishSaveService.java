package com.moplus.moplus_server.admin.publish.service;

import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.admin.publish.dto.request.PublishPostRequest;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishSaveService {

    private final ProblemSetRepository problemSetRepository;
    private final PublishRepository publishRepository;

    @Transactional
    public Long createPublish(PublishPostRequest request) {
        problemSetRepository.validatePublishableProblemSet(request.problemSetId());
        Publish publish = request.toEntity();
        // 발행날짜 유효성 검사
        publish.validatePublishedDate();
        return publishRepository.save(publish).getId();
    }
}
