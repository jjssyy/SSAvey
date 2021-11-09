package com.web.curation.mySurvey;

import com.web.curation.survey.State;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MySurveyDto {

    private String sid;
    private String title;
    private String explain;
    private String writer;
    private boolean is_anony;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private State state;
}
