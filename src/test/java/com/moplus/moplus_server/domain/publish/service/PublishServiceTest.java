package com.moplus.moplus_server.domain.publish.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.domain.publish.dto.request.PublishPostRequest;
import com.moplus.moplus_server.domain.publish.dto.response.PublishMonthGetResponse;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("h2test")
@Sql({"/insert-problem.sql", "/insert-problem-set2.sql"})
@SpringBootTest
public class PublishServiceTest {

    @Autowired
    private PublishSaveService publishSaveService;

    @Autowired
    private PublishDeleteService publishDeleteService;

    @Autowired
    private PublishGetService publishGetService;

    @Autowired
    private PublishRepository publishRepository;

    private PublishPostRequest publishPostRequest;

    @BeforeEach
    void setUp() {
        // 발행 요청 데이터 생성
        publishPostRequest = new PublishPostRequest(
                LocalDate.now().plusDays(1), // 내일부터 발행 가능
                1L
        );
    }

    @Test
    void 발행_생성_테스트() {
        // when
        Long publishId = publishSaveService.createPublish(publishPostRequest);

        // then
        Publish savedPublish = publishRepository.findByIdElseThrow(publishId);

        assertThat(savedPublish).isNotNull();
        assertThat(savedPublish.getPublishedDate()).isEqualTo(publishPostRequest.publishedDate());
        assertThat(savedPublish.getProblemSetId()).isEqualTo(1L);
    }

    @Test
    void 발행_삭제_테스트() {
        // given
        Long publishId = publishSaveService.createPublish(publishPostRequest);

        // when
        publishDeleteService.deletePublish(publishId);

        // then
        assertThatThrownBy(() -> publishRepository.findByIdElseThrow(publishId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(ErrorCode.PUBLISH_NOT_FOUND.getMessage());
    }

    @Test
    void 월별_발행_조회_테스트() {
        // given
        publishSaveService.createPublish(new PublishPostRequest(
                LocalDate.of(2025, 3, 10),
                1L
        ));

        publishSaveService.createPublish(new PublishPostRequest(
                LocalDate.of(2025, 3, 15),
                1L
        ));

        // when
        List<PublishMonthGetResponse> publishList = publishGetService.getPublishMonth(2025, 3);

        // then
        assertThat(publishList).hasSize(2);
        assertThat(publishList.get(0).day()).isEqualTo(10);
        assertThat(publishList.get(0).problemSetInfo().title()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
        assertThat(publishList.get(1).day()).isEqualTo(15);
    }

    @Test
    void 유효하지_않은_월_입력시_예외_테스트() {
        // when & then
        assertThatThrownBy(() -> publishGetService.getPublishMonth(2025, 13))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.INVALID_MONTH_ERROR.getMessage());

        assertThatThrownBy(() -> publishGetService.getPublishMonth(2025, 0))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.INVALID_MONTH_ERROR.getMessage());
    }

    @Test
    void 오늘날짜_또는_과거날짜로_발행_시_예외_테스트() {
        // given
        LocalDate today = LocalDate.now();
        LocalDate pastDate = today.minusDays(1);

        // when & then (createPublish에서 예외 발생하도록)
        assertThatThrownBy(() -> publishSaveService.createPublish(new PublishPostRequest(today, 1L)))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.INVALID_DATE_ERROR.getMessage());

        assertThatThrownBy(() -> publishSaveService.createPublish(new PublishPostRequest(pastDate, 1L)))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.INVALID_DATE_ERROR.getMessage());
    }
}