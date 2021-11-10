package com.web.curation.compare;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompareSurvey {
    private String sid;
    private String title;
    private String explain;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
