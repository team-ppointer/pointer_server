package com.moplus.moplus_server.domain.DetailResultApplication.service;

import com.moplus.moplus_server.domain.DetailResultApplication.dto.request.DetailResultApplicationPostRequest;
import com.moplus.moplus_server.domain.DetailResultApplication.respository.DetailResultApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailResultApplicationService {

    private final DetailResultApplicationRepository detailResultApplicationRepository;

    @Transactional
    public void saveApplication(DetailResultApplicationPostRequest request) {
        detailResultApplicationRepository.save(request.toEntity());
    }
}
