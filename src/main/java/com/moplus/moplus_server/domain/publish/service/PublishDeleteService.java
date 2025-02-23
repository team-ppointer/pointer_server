package com.moplus.moplus_server.domain.publish.service;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishDeleteService {

    private final PublishRepository publishRepository;

    @Transactional
    public void deletePublish(Long publishId) {
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        publish.validateDeletable();

        publishRepository.delete(publish);
    }
}
