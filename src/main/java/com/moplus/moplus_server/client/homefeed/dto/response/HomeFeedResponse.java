package com.moplus.moplus_server.client.homefeed.dto.response;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
import java.time.LocalDate;
import java.util.List;

public record HomeFeedResponse(
        List<DailyProgressResponse> dailyProgresses,
        List<ProblemSetHomeFeedResponse> problemSets
) {
    public static HomeFeedResponse of(
            List<DailyProgressResponse> dailyProgresses,
            List<ProblemSetHomeFeedResponse> problemSets
    ) {
        return new HomeFeedResponse(dailyProgresses, problemSets);
    }

    public record DailyProgressResponse(
            LocalDate date,
            double progressRate
    ) {
        public static DailyProgressResponse of(LocalDate date, double progressRate) {
            return new DailyProgressResponse(date, progressRate);
        }
    }

    public record ProblemSetHomeFeedResponse(
            LocalDate date,
            Long problemSetId,
            String title,
            Long submitCount,
            ProblemHomeFeedResponse problemHomeFeedResponse
    ) {
        public static ProblemSetHomeFeedResponse of(LocalDate date, ProblemSetGetResponse problemSetGetResponse,
                                                    Long submitCount) {
            return new ProblemSetHomeFeedResponse(
                    date,
                    problemSetGetResponse.id(),
                    problemSetGetResponse.title(),
                    submitCount,
                    ProblemHomeFeedResponse.of(problemSetGetResponse.problemSummaries().get(0))
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
                    problemSummaryResponse.problemId(),
                    problemSummaryResponse.mainProblemImageUrl()
            );
        }
    }
} 