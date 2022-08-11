package com.codrive.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity //이 클래스가 DB의 테이블을 의미한다는 어노테이션
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//identity는 mysql이나 mariadb를 의미하는 제너레이션 타입니다.
    private Integer id; //게시판 아이디 (번호)
    private String title; //게시판 제목
    private String content; //게시판 내용
    private String filename; //파일이름(저장할)
    private String filepath; //파일경로(저장한)
    private String user; //작성자
    private String day; //작성일자
    private String tag; //언어구분
}
