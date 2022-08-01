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
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
}
