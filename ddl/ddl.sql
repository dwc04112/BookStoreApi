CREATE TABLE `book`
(
    `bid`            bigint(20) NOT NULL COMMENT '책id',
    `mid`            bigint(20) NOT NULL COMMENT 'member id',
    `author`         varchar(200) NOT NULL COMMENT '저자',
    `subject`        varchar(300) NOT NULL COMMENT '책제목',
    `page`           int(20) not null comment '책 페이지',
    `price`          int(11) NOT NULL COMMENT '가격',
    `published_date` date         NOT NULL COMMENT '출판일',
    `tag`            varchar(200) NOT NULL COMMENT '태그',
    `detail_tag`     varchar(200) NOT NULL COMMENT '디테일 태그',
    `keyword`        varchar(300) COMMENT '키워드',
    `isDel`          enum('Y','N') not null default 'N',
    PRIMARY KEY (`bid`)
)

CREATE TABLE `member`
(
      `mid` bigint(20) NOT null  auto_increment COMMENT'고객id',
      `email` varchar(300) DEFAULT null COMMENT '이메일',
      `password` varchar(3000) DEFAULT null COMMENT '비밀번호',
      `nickName` varchar(400) default null COMMENT '닉네임',
      `fullName` varchar(400) default null COMMENT '이름',
      PRIMARY KEY (`mid`)
)

CREATE TABLE `keywords` (
    `mid` bigint(20) NOT NULL COMMENT '고객id'
)


ALTER TABLE keywords
    ADD CONSTRAINT FK_member_TO_keywords -- 고객 -> 키워드
        FOREIGN KEY (
                     mid -- 고객id
            )
            REFERENCES member ( -- 고객
                               mid -- 고객id
                );

ALTER TABLE book
    ADD CONSTRAINT FK_member_TO_book -- 고객 -> 책
        FOREIGN KEY (
                     mid -- 고객id
            )
            REFERENCES member ( -- 고객
                               mid -- 고객id
                );



create table token(
      tokenId bigint(20) not null auto_increment,
      token varchar(500) not null,
      expirationTime time not null,
      primary key (tokenId)
)