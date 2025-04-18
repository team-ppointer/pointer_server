package com.moplus.moplus_server.client.homefeed.dto.response;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
import com.moplus.moplus_server.client.submit.domain.ProgressStatus;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record HomeFeedResponse(
        List<DailyProgressResponse> dailyProgresses,
        List<ProblemSetHomeFeedResponse> problemSets
) {
    private static final Logger log = LoggerFactory.getLogger(HomeFeedResponse.class);

    public static HomeFeedResponse of(
            List<DailyProgressResponse> dailyProgresses,
            List<ProblemSetHomeFeedResponse> problemSets
    ) {
        return new HomeFeedResponse(dailyProgresses, problemSets);
    }

    public record DailyProgressResponse(
            LocalDate date,
            ProgressStatus progressStatus
    ) {
        public static DailyProgressResponse of(LocalDate date, ProgressStatus progressStatus) {
            return new DailyProgressResponse(date, progressStatus);
        }
    }

    public record ProblemSetHomeFeedResponse(
            LocalDate date,
            Long publishId,
            String title,
            Long submitCount,
            ProblemHomeFeedResponse problemHomeFeedResponse
    ) {
        public static ProblemSetHomeFeedResponse of(LocalDate date, Long publishId,
                                                    ProblemSetGetResponse problemSetGetResponse,
                                                    Long submitCount) {
            ProblemSummaryResponse problemSummaryResponse = null;
            try {
                problemSummaryResponse = problemSetGetResponse.problemSummaries().get(0);
            } catch (IndexOutOfBoundsException e) {
                log.atError().log("id " + publishId + "번 발행에 속한 세트에 문항이 존재하지 않습니다. ");
                throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND,
                        "id " + publishId + "번 발행에 속한 세트에 문항이 존재하지 않습니다. ");
            }
            return new ProblemSetHomeFeedResponse(
                    date,
                    publishId,
                    problemSetGetResponse.title(),
                    submitCount,
                    ProblemHomeFeedResponse.of(problemSummaryResponse)
            );
        }

        public static ProblemSetHomeFeedResponse of(LocalDate date) {
            return new ProblemSetHomeFeedResponse(
                    date,
                    null,
                    null,
                    null,
                    null
            );
        }
    }

    public record ProblemHomeFeedResponse(
            Long problemId,
            String mainProblemImageUrl
    ) {
        public static ProblemHomeFeedResponse of(ProblemSummaryResponse problemSummaryResponse) {
            return new ProblemHomeFeedResponse(
                    problemSummaryResponse.getProblemId(),
                    problemSummaryResponse.getMainProblemImageUrl()
            );
        }
    }
} 