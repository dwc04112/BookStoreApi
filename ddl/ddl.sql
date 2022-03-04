CREATE TABLE `book` (
    `bid` bigint(20) NOT NULL COMMENT '책id',
    `mid` bigint(20) NOT NULL COMMENT 'member id',
    `bTitle` varchar(300) NOT NULL COMMENT '책제목',
    `bSubTitle` varchar(200) NOT NULL COMMENT '부제목',
    `bAuthor` varchar(200) NOT NULL COMMENT '저자',
    `bTranslator` varchar(200) COMMENT '번역가',
    `bContent` varchar(4000) not null COMMENT '책 소개',
    `bIndex` varchar(4000) COMMENT '목차',
    `bPreview` varchar(4000) COMMENT '서평',
    `bPage` int(20) not null comment '책 페이지',
    `bIsbn` varchar(1000) not null comment 'isbn',
    `bPrice` int(11)  NOT NULL COMMENT '가격',
    `bSalePrice` int(11)  NOT NULL COMMENT '판매 가격',
    `bSize` varchar(200)  NOT NULL COMMENT '크기/무게',
    `bThumb` varchar(200)  NOT NULL COMMENT '썸네일',
    `bPublisher` varchar(200) NOT NULL COMMENT '출판사',
    `bPublishedDate` date NOT NULL COMMENT '출판일',
    `bTag` varchar(200) NOT NULL COMMENT '태그',
    `bDetailTag` varchar(200) NOT NULL COMMENT '디테일 태그',
    `bKeyword` varchar(1000) COMMENT '키워드',
    `isDel` enum('Y','N') not null default 'N',
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