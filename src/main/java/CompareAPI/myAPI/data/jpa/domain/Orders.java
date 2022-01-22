package CompareAPI.myAPI.data.jpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Data
@DynamicUpdate
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//주문아이디
    @Column(nullable = false)
    private String orderNo;

    //주문결제방법
	@Column(nullable = false)
	private String orderMethod;

	//pg사 TID
	@Column(nullable = false)
	private String tid;
	
	//pg사 구분
	@Column(nullable = false)
	private String pgCd;

	//주문금액
	@Column(nullable = false)
    private Long orderAmt;

	//생성일
	@Column(nullable = false)
    private String createDt;
	
	//주문결제상태
    private String orderStatus;

    public Orders() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }
    public Orders(String orderNo,
				  String orderMethod,
				  String tid,
				  String pgCd,
				  Long orderAmt,
				  String createDt,
				  String orderStatus
	) {
        this.orderNo = orderNo;
		this.orderMethod = orderMethod;
		this.tid = tid;
		this.pgCd = pgCd;
		this.orderAmt = orderAmt;
		this.createDt = createDt;
		this.orderStatus = orderStatus;
    }


}