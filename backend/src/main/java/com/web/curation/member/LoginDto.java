package com.web.curation.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String login_id;
    private String password;
}
