package com.bookstore.bookstoreapi.payments.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Getter
@Table(name = "payments")
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payments {

    @Id
    @Column(name = "paymentId")
    private String paymentId;          //1. imp_uid

    @Column(unique = true)
    private long orderId;            //2. order id = merchant_uid
    private String payMethod;        //3. 결제수단
    private String pgProvide;        //4. 결제승인된 pg
    private int paidAt;              //5. 결제시간
    private String payStatus;        //6. 결제상태
    private int payAmount;           //7. 결제금액
    private int cancelAmount;
    private String buyerName;        //8. 주문자 이름
    private String bankName;         //9. 가상계좌 은행명
    private String bankHorder;       //10. 가상계좌 예금주

    public void updateCancel(String payStatus, int cancelAmount){
        this.payStatus = payStatus;
        this.cancelAmount = cancelAmount;
    }

    public Payments(String paymentId,
                  Long orderId,
                  String payMethod,
                  String pgProvide,
                  int paidAt,
                  String payStatus,
                  int payAmount,
                  int cancelAmount,
                  String buyerName,
                  String bankName,
                  String bankHorder
    )
    {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.payMethod = payMethod;
        this.pgProvide = pgProvide;
        this.paidAt = paidAt;
        this.payStatus = payStatus;
        this.payAmount = payAmount;
        this.cancelAmount = cancelAmount;
        this.buyerName = buyerName;
        this.bankName = bankName;
        this.bankHorder = bankHorder;
    }
}
