package com.example.demo.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    EXPIRED_USER_JWT(false,2004,"만료된 JWT입니다."),
    REISSUE_TOKEN(false, 2005, "토큰이 만료되었습니다. 다시 로그인해주세요."),
    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    PASSWORD_CANNOT_BE_NULL(false, 2011, "비밀번호를 입력해주세요."),
    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_NONE_EXISTS_EMAIL(false,2018,"등록되지 않은 이메일입니다."),

    // [POST] /boards
    POST_BOARDS_EMPTY_TITLE(false, 2019, "제목은 두 글자 이상으로 작성해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"비밀번호가 틀렸습니다."),
    FAILED_TO_LOGOUT(false, 3015, "로그아웃에 실패하였습니다"),
    MEMBER_NOT_FOUND(false, 3016, "등록된 이메일이 아닙니다."),
    INCORRECT_PASSWORD(false, 3017, "비밀번호가 틀렸습니다"),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정에 실패하였습니다"),

    //[PATCH] /users/{boardIdx}
    MODIFY_FAIL_BOARD_TITLE(false, 4015, "게시글 제목 수정에 실패하였습니다"),

    DELETE_FAIL_USER(false, 4016, "멤버 삭제에 실패하였습니다."),
    CANNOT_DELETE(false, 5004, "작성된 게시글이 존재하여 멤버를 삭제하는데 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    /**
     *   5000 : Board 관련 오류
      */
    NONE_EXIST_BOARD(false, 5000, "요청하신 게시글은 존재하지 않습니다."),
    INVALID_BOARD_ID(false, 5001, "유효하지 않은 입력입니다."),
    EXCEEDED_TITLE_LIMIT(false, 5002, "제목이 글자수 제한을 초과하였습니다."),
    EXCEEDED_CONTENT_LIMIT(false, 5003, "본문이 글자수 제한을 초과하였습니다."),
    BOARD_NOT_FOUND(false, 5004, "요청하신 제목의 게시글은 존재하지 않습니다."),
    SAME_TITLE_ERROR(false, 5005, "동일한 게시글이 2개 이상 존재해 수정을 진행할 수 없습니다."),

    /**
     *   6000 : 회원등록 관련 오류
     */
    NICKNAME_ERROR(false, 5000, "이미 존재하는 닉네임입니다."),
    KAKAO_ERROR(false, 5001, "카카오 로그아웃에 실패했습니다."),
    INTRODUCE_ERROR(false, 5002, "한줄 소개는 0~30자이어야 합니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
