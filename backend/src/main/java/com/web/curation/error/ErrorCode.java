package com.web.curation.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD REQUEST :
    서버가 클라이언트 오류(예: 잘못된 요청 구문, 유효하지 않은 요청 메시지 프레이밍, 또는 변조된 요청 라우팅)를 감지해
    요청을 처리할 수 없거나, 하지 않음 */
    MATTERMOST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "비밀번호를 입력하세요."),

    /* 401 UNAUTHORIZED : 해당 리소스에 유효한 인증 자격 증명이 없음 */
    MATTERMOST_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"이메일 또는 사용자명이나 비밀번호를 잘못 입력하셨습니다."),

    /* 403 FORBIDDEN : 서버에 요청이 전달되었지만 권한 때문에 거절됨 (요청이 서버에 의해 거부됨) */
    MATTERMOST_FORBIDDEN(HttpStatus.FORBIDDEN,"요청이 거부되었습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 설문조사를 찾을 수 없습니다."),
    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 템플릿을 찾을 수 없습니다."),
    SURVEYANSWER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 설문에 대한 응답을 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 중복된 데이터 존재 */
    ALREADY_SURVEYED(HttpStatus.CONFLICT,"이미 설문조사를 완료한 사용자입니다."),
    NEED_EMAIL(HttpStatus.CONFLICT, "이메일이 필요합니다"),

    /* 501 NOT IMPLEMENTED : 클라이언트 요청에 대한 서버의 응답 수행 기능이 없음
    (ex] 서버가 지원하지 않는 새로운 Method를 사용하여 요청 - GET2, POST2...) */
    MATTERMOST_NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED,"잘못된 요청 메소드입니다."),


    ;
    private final HttpStatus httpStatus;
    private final String detail;
}
