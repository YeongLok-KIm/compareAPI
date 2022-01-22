package CompareAPI.myAPI.data.jpa.domain;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//PG사 TID
	@Column(nullable = false)
	private String tid;
	
	//주문아이디
    @Column(nullable = false)
    private String orderNo;
    
    //pg사 구분
	@Column(nullable = false)
	private String pgCd;
	
	//승인일
	@Column(nullable = false)
    private String approveDt;

	//취소일
    private String cancelDt;
    
    //승인금액
    private long approveAmt;

    //카드사코드
	private String cardCd;

	//승인번호
	private String approveNo;

	//할부기간(00 : 무이자)
	private String installment;

	//승인상태
	private String approveStatus;

	//대사결과
	private String compareStatus;

	//수정시각
    private LocalDateTime updateDttm;

    public Card() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }
    
    public Card(String orderNo,
				String tid,
				String pgCd,
				String approveDt,
				String cancelDt,
				long approveAmt,
				String cardCd,
				String approveNo,
				String installment,
				String approveStatus,
				String compareStatus

	) {
        this.orderNo = orderNo;
		this.tid = tid;
		this.pgCd = pgCd;
		this.approveDt = approveDt;
		this.cancelDt = cancelDt;
		this.approveAmt = approveAmt;
		this.cardCd = cardCd;
		this.approveNo = approveNo;
		this.installment = installment;
		this.approveStatus = approveStatus;
		this.compareStatus = compareStatus;
    }

	@PreUpdate
	public void setPrePersist() {
		this.updateDttm = LocalDateTime.now();
	}
	

}