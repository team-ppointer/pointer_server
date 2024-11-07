package com.moplus.moplus_server.domain.practiceTest.service.admin;

import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.practiceTest.dto.admin.request.RatingTableRequest;
import com.moplus.moplus_server.domain.practiceTest.repository.RatingTableRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingTableAdminService {

    private final RatingTableRepository ratingTableRepository;

    public List<RatingTable> findRatingTableByPracticeId(Long practiceId) {
        return ratingTableRepository.findAllByPracticeTestId(practiceId);
    }

    public void createRatingTable(RatingTableRequest request, Long practiceId) {
        request.setPracticeId(practiceId);
        ratingTableRepository.save(request.toEntity());
    }

    public void updateOrSaveRatingTable(Long practiceTestId, List<RatingTableRequest> request) {
        List<RatingTable> ratingTables = ratingTableRepository.findAllByPracticeTestId(practiceTestId);
        if (ratingTables.isEmpty())
            request.forEach(ratingTableRequest -> createRatingTable(ratingTableRequest, practiceTestId));
        else{
            for (int i = 0; i < ratingTables.size(); i++) {
                RatingTable ratingTable = ratingTables.get(i);
                ratingTable.updateByRatingTableRequest(request.get(i));
                ratingTableRepository.save(ratingTable);
            }
        }

    }


}
