CREATE TABLE `book` (
                        `bid` bigint(20) NOT NULL COMMENT '책id',
                        `mid` bigint(20) NOT NULL COMMENT 'member id',
                        `bookTitle` varchar(300) NOT NULL COMMENT '책제목',
                        `bookSubTitle` varchar(200) DEFAULT NULL COMMENT '부제목',
                        `bookAuthor` varchar(200) NOT NULL COMMENT '저자',
                        `bookTranslator` varchar(200) DEFAULT NULL COMMENT '번역가',
                        `bookContent` varchar(4000) NOT NULL COMMENT '책 소개',
                        `bookIndex` varchar(4000) DEFAULT NULL COMMENT '목차',
                        `bookPreview` varchar(4000) DEFAULT NULL COMMENT '서평',
                        `bookPage` int(20) NOT NULL COMMENT '책 페이지',
                        `bookIsbn` varchar(300) NOT NULL COMMENT 'isbn',
                        `bookPrice` int(11) NOT NULL COMMENT '가격',
                        `bookSalePrice` int(11) NOT NULL COMMENT '판매 가격',
                        `bookSize` varchar(200) NOT NULL COMMENT '크기/무게',
                        `bookThumb` varchar(200) NOT NULL COMMENT '썸네일',
                        `bookPublisher` varchar(200) NOT NULL COMMENT '출판사',
                        `bookPublishedDate` date NOT NULL COMMENT '출판일',
                        `bookTag` varchar(200) NOT NULL COMMENT '태그',
                        `bookKeyword` varchar(1000) DEFAULT NULL COMMENT '키워드',
                        `isDel` enum('Y','N') NOT NULL DEFAULT 'N',
                        PRIMARY KEY (`bid`),
                        KEY `FK_member_TO_book` (`mid`),
                        CONSTRAINT `FK_member_TO_book` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`)
)



CREATE TABLE `cart` (
                        `cartId` bigint(20) NOT NULL COMMENT 'wish id',
                        `mid` bigint(20) NOT NULL COMMENT '고객id',
                        `bid` bigint(20) NOT NULL COMMENT 'book id',
                        `bookCount` int(11) NOT NULL COMMENT '책 수량',
                        PRIMARY KEY (`cartId`),
                        KEY `FK_book_TO_cart` (`bid`),
                        CONSTRAINT `FK_book_TO_cart` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`)
)

CREATE TABLE `comment` (
                           `cid` bigint(20) NOT NULL COMMENT 'comment id',
                           `bid` bigint(20) NOT NULL COMMENT '책id',
                           `mid` bigint(20) NOT NULL COMMENT 'member id',
                           `ratings` int(11) NOT NULL COMMENT '평가',
                           `nickName` varchar(400) NOT NULL COMMENT '작성자 닉네임',
                           `profile` varchar(800) DEFAULT NULL,
                           `content` varchar(2000) NOT NULL COMMENT '댓글내용',
                           `popularity` int(11) NOT NULL DEFAULT 0 COMMENT '평가',
                           `commentDate` date NOT NULL COMMENT '작성일',
                           `commentTime` time NOT NULL COMMENT '작성시각',
                           `isDel` enum('Y','N') NOT NULL DEFAULT 'N',
                           PRIMARY KEY (`cid`),
                           KEY `FK_Book_TO_Comment` (`bid`),
                           KEY `FK_Member_TO_Comment` (`mid`),
                           CONSTRAINT `FK_Book_TO_Comment` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`),
                           CONSTRAINT `FK_Member_TO_Comment` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`)
)

CREATE TABLE `keywords` (
                            `mid` bigint(20) NOT NULL COMMENT '고객id',
                            KEY `FK_member_TO_keywords` (`mid`),
                            CONSTRAINT `FK_member_TO_keywords` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`)
)

CREATE TABLE `member` (
                          `mid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '고객id',
                          `email` varchar(300) NOT NULL COMMENT '이메일',
                          `password` varchar(3000) NOT NULL COMMENT '비밀번호',
                          `nickName` varchar(200) DEFAULT NULL COMMENT '닉네임',
                          `fullName` varchar(200) NOT NULL COMMENT '이름',
                          `phoneNum` varchar(20) NOT NULL,
                          `profilePicture` varchar(1000) DEFAULT NULL,
                          `userRule` enum('user','admin') NOT NULL,
                          `createDate` date NOT NULL,
                          `isDel` enum('Y','N') NOT NULL DEFAULT 'N',
                          PRIMARY KEY (`mid`)
)

CREATE TABLE `orders` (
                          `orderId` bigint(20) NOT NULL COMMENT 'wish id',
                          `mid` bigint(20) NOT NULL COMMENT '고객id',
                          `buyerName` varchar(200) NOT NULL,
                          `postcode` varchar(10) NOT NULL COMMENT '구매자 우편번호',
                          `addr` varchar(400) NOT NULL COMMENT '구매자 주소',
                          `detailAddr` varchar(400) NOT NULL COMMENT '구매자 상세주소',
                          `phoneNum` varchar(20) NOT NULL COMMENT '구매자 번호',
                          `orderDate` date NOT NULL COMMENT '주문 날짜',
                          `orderTime` time NOT NULL COMMENT '주문 시간',
                          `orderState` varchar(400) NOT NULL COMMENT '주문 상태',
                          `deliverCost` int(11) NOT NULL COMMENT '배송비',
                          `isDel` enum('Y','N') NOT NULL DEFAULT 'N',
                          PRIMARY KEY (`orderId`)
)

CREATE TABLE `orderitem` (
                             `orderItemId` bigint(20) NOT NULL COMMENT 'order item id',
                             `orderId` bigint(20) NOT NULL COMMENT 'order id',
                             `bid` bigint(20) NOT NULL COMMENT 'book id',
                             `bookCount` int(11) NOT NULL COMMENT '책 수량',
                             `bookSalePrice` int(11) NOT NULL COMMENT '판매 가격',
                             `bookTitle` varchar(300) NOT NULL,
                             `bookThumb` varchar(200) NOT NULL,
                             PRIMARY KEY (`orderItemId`),
                             KEY `FK_orders_TO_orderitem` (`orderId`),
                             CONSTRAINT `FK_orders_TO_orderitem` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
)

CREATE TABLE `payments` (
                            `paymentId` varchar(200) NOT NULL COMMENT 'imp_uid ',
                            `orderId` bigint(20) NOT NULL COMMENT 'order id = merchant_uid',
                            `payMethod` varchar(200) NOT NULL COMMENT '결제수단',
                            `pgProvide` varchar(200) NOT NULL COMMENT '결제승인된 pg',
                            `paidAt` int(11) NOT NULL COMMENT '결제 시간',
                            `payStatus` varchar(200) NOT NULL COMMENT '결제 상태',
                            `payAmount` int(11) NOT NULL COMMENT '결제 금액',
                            `cancelAmount` int(11) NOT NULL DEFAULT 0,
                            `buyerName` varchar(200) NOT NULL COMMENT '주문자 이름',
                            `bankName` varchar(200) DEFAULT NULL COMMENT '가상계좌 은행명',
                            `bankHorder` varchar(200) DEFAULT NULL COMMENT '가상계좌 예금주',
                            PRIMARY KEY (`paymentId`)
)

CREATE TABLE `wishlist` (
                            `wid` bigint(20) NOT NULL COMMENT 'wish id',
                            `mid` bigint(20) NOT NULL COMMENT '고객id',
                            `titleNum` int(11) DEFAULT NULL,
                            `wishlistTitle` varchar(200) NOT NULL DEFAULT '나의 보관함' COMMENT '위시리스트 이름',
                            `bid` bigint(20) NOT NULL COMMENT 'book id',
                            `bookTitle` varchar(300) NOT NULL COMMENT '책제목',
                            `bookAuthor` varchar(200) NOT NULL COMMENT '저자',
                            `bookSalePrice` int(11) NOT NULL COMMENT '가격',
                            `bookThumb` varchar(200) NOT NULL COMMENT '썸네일',
                            PRIMARY KEY (`wid`),
                            KEY `FK_member_TO_wishlist` (`mid`),
                            KEY `FK_book_TO_wishlist` (`bid`),
                            CONSTRAINT `FK_book_TO_wishlist` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`),
                            CONSTRAINT `FK_member_TO_wishlist` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`)
)

CREATE TABLE `addressbook` (
                               `addrId` bigint(20) NOT NULL COMMENT '주소id ',
                               `mid` bigint(20) NOT NULL COMMENT 'mid',
                               `addrName` varchar(500) NOT NULL,
                               `postcode` varchar(10) NOT NULL COMMENT '우편번호',
                               `addr` varchar(400) NOT NULL COMMENT ' 주소',
                               `detailAddr` varchar(400) NOT NULL COMMENT ' 상세주소',
                               `phoneNum` varchar(400) NOT NULL,
                               `isDel` enum('Y','N') NOT NULL DEFAULT 'N',
                               PRIMARY KEY (`addrId`)
)

CREATE TABLE `token` (
                         `tokenId` bigint(20) NOT NULL AUTO_INCREMENT,
                         `token` varchar(500) NOT NULL,
                         `expirationTime` time NOT NULL,
                         PRIMARY KEY (`tokenId`)
)