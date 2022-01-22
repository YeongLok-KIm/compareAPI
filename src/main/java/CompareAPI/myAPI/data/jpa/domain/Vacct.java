package CompareAPI.myAPI.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
public class Vacct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//pg사 TID
	@Column(nullable = false)
	private String tid;
	
	//주문아이디
	@Column(nullable = false)
    private String orderNo;
    
	//입금일자
	@Column(nullable = false)
    private String depositDt;

	//pg사 구분
	@Column(nullable = false)
	private String pgCd;
	
	//입금취소일
    private String cancelDt;
    
    //입금금액
    private long depositAmt;

    //입금가상계좌 은행코드
	private String bankCd;

	//가상계좌번호
	private String acctNo;

	//입금자명
	private String depositNm;

	//구매자명
	private String buyer;
	
	//대사결과
	private String compareStatus;
	
	//수정시각
	private LocalDateTime updateDttm;

    public Vacct() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }
    public Vacct(String orderNo,
				 String tid,
				 String pgCd,
				 String depositDt,
                 String cancelDt,
                 long depositAmt,
                 String bankCd,
                 String acctNo,
                 String depositNm,
                 String buyer,
                 String compareStatus 

	) {
        this.orderNo = orderNo;
		this.tid = tid;
		this.pgCd = pgCd;
		this.depositDt = depositDt;
		this.depositAmt = depositAmt;
		this.bankCd = bankCd;
		this.acctNo = acctNo;
		this.depositNm = depositNm;
		this.buyer = buyer;
		this.compareStatus = compareStatus;
    }

	@PreUpdate
	public void setPrePersist() {
		this.updateDttm = LocalDateTime.now();
	}


}