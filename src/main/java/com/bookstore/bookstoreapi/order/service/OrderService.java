package com.bookstore.bookstoreapi.order.service;

import com.bookstore.bookstoreapi.bookjpa.dto.BookOrderDTO;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.order.model.OrderItemRepository;
import com.bookstore.bookstoreapi.order.model.OrdersRepository;
import com.bookstore.bookstoreapi.order.dto.SetOrderDTO;
import com.bookstore.bookstoreapi.order.model.Orders;
import com.bookstore.bookstoreapi.order.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    final BookRepository bookRepository;
    final OrdersRepository ordersRepository;
    final OrderItemRepository orderItemRepository;
    final MemberRepository memberRepository;

    //SecurityContextHolder에 저장된 사용자Email을 통해 사용자 Mid 불러오기
    private Long getMemberIdByEmail() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }

    //orders 관련
    //주문 생성
    // transactional 의 default = error & runtime e
    @Transactional
    public ApiResponse<Long> setCartList(SetOrderDTO setOrderDTO) {
        long newOrderId = newMerchantId(setOrderDTO);

        Orders postData = Orders.builder()
                .orderId(newOrderId)
                .mid(getMemberIdByEmail())
                .postcode(setOrderDTO.getPostcode())
                .addr(setOrderDTO.getAddr())
                .detailAddr(setOrderDTO.getDetailAddr())
                .phoneNum(setOrderDTO.getPhoneNum())
                .orderDate(LocalDate.now())
                .orderTime(LocalTime.now())
                .orderState("결제대기중")
                .deliverCost(0)
                .build();
        ordersRepository.save(postData);
        setCartListItem(setOrderDTO.getBookOrder(),newOrderId);
        return new ApiResponse<>(true,"주문번호 생성 성공", newOrderId);
    }

    // order id 만들기
    public Long newMerchantId(SetOrderDTO setOrderDTO){
        return Long.parseLong(nowDate6()+generateAuth5());   // 6+5 =11
    }
    public String nowDate6(){
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("yyMMdd");
        return formatter.format(now);
    }
    //난수 생성
    public static int generateAuth5() {
        return ThreadLocalRandom.current().nextInt(10000, 100000);
    }
    // order id 만들기 끝

    // 1-2 주문상품 관련
    // 주문 아이템 등록
    public void setCartListItem(List<BookOrderDTO> BookOrder, long newOrderId) {

        for (BookOrderDTO bookOrderDTO : BookOrder) {
            Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bookOrderDTO.getBid(), "N");
            Book data = bookData.orElseThrow(() -> new RuntimeException("해당 책 정보가 없습니다"));

            int result = orderItemRepository.addToOrderItem(
                    getNewOrderItemId(orderItemRepository),
                    newOrderId,
                    data.getBid(),
                    bookOrderDTO.getBookCount(),
                    data.getBookSalePrice(),
                    data.getBookTitle(),
                    data.getBookThumb()
            );
            if (result < 1) {
                throw new RuntimeException("주문상품을 등록하는데 실패했습니다");
            }
        }
    }
    // 주문상품 id 생성
    private Long getNewOrderItemId(OrderItemRepository orderItemRepository) {
        long result;
        OrderItem orderItemOfMaxId = orderItemRepository.findTopByOrderByOrderItemIdDesc();
        if (orderItemOfMaxId == null) {
            result = 1;
            log.debug("no Order Item data, maxId is 1");
        } else {
            result = orderItemOfMaxId.getOrderItemId() + 1;
        }
        log.debug("new order item Id=" + result);
        return result;
    }
    // 주문상품 관련 끝
    // order 등록관련 끝


    // order list 출력
    public List<Orders> getOrderList() {
        //default 2years
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(2);

        //고정
        LocalTime endTime = LocalTime.of(23,59,59);
        LocalTime startTime = LocalTime.of(0,0,0);
        return ordersRepository.findOrdersByMidAndOrderDateBetweenAndOrderTimeBetweenOrderByOrderDateDescOrderTimeDesc(getMemberIdByEmail(), startDate,endDate, startTime,endTime);
    }


    // *** by payments ****
    // by payments 전체 가격 받아오기
    public int getTotalAmount(Long merchant_uid) {
        return orderItemRepository.totalAmount(merchant_uid);
    }

    // by payments 상태 갱신
    public boolean updateState(long merchant_uid, String status) {
        switch (status) {
            case "paid":
                status = "결제완료";
                break;
            case "ready":
                status = "결제대기";
                break;
            case "cancel":
                status = "결제취소";
                break;
            default:
                status = "결제실패";
                break;
        }

        try {
            Optional<Orders> orderData = ordersRepository.findOrdersByOrderId(merchant_uid);
            Orders data = orderData.orElseThrow(() -> new RuntimeException("no data : find order by order_id"));
            data.updateOrderState(status);
            ordersRepository.save(data);
            return true;
        }catch (Exception e){
            log.debug("Exception (updateState) : "+ e);
            return false;
        }
    }
}
