package CompareAPI.myAPI.data.jpa.controller;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import CompareAPI.myAPI.data.jpa.domain.Card;
import CompareAPI.myAPI.data.jpa.domain.Orders;
import CompareAPI.myAPI.data.jpa.domain.Vacct;
import CompareAPI.myAPI.data.jpa.entity.CompareVO;
import CompareAPI.myAPI.data.jpa.service.CardCompareService;
import CompareAPI.myAPI.data.jpa.service.OrdersService;
import CompareAPI.myAPI.data.jpa.service.VacctCompareService;
import CompareAPI.myAPI.data.jpa.util.DateUtil;

@RestController
@RequestMapping("/v1")
public class CompareController {
	
	public static final Logger logger = LoggerFactory.getLogger(CompareAPI.myAPI.data.jpa.controller.CompareController.class);
	
	@Autowired
	OrdersService ordersService;

	@Autowired
	CardCompareService cardCompareService;
	
	@Autowired
	VacctCompareService vacctCompareService;

	// 신용카드 일대사
	@RequestMapping(value = "/day-compare/card", method = RequestMethod.POST)
    public ResponseEntity<?> CardDayCompare(@RequestBody CompareVO vo) {
		
		try{
			
			logger.info("신용카드 일대사 시작[{}]", vo.getPgCd());
			logger.info("실행일 : {}", vo.getProcessDt());
			String compareDt;

			if(DateUtil.dateCheck(vo.getProcessDt())==false) {
				return new ResponseEntity(new CustomErrorType("인자(실행일)가 올바르지 않습니다."), HttpStatus.BAD_REQUEST);
			}else {

				//실행일의 전일자를 대사한다.
				compareDt = DateUtil.addDate(vo.getProcessDt(), -1);
				
				logger.info("대사일 : {}", compareDt);
				
				//대사일에 승인내역과 취소내역을 따로 처리 할 수도 있고 같이 처리 할 수도 있다.
				List<Card> cardApproveList = this.cardCompareService.getApprovedCard(vo.getPgCd(), compareDt, compareDt);
				List<Card> cardCancelList = this.cardCompareService.getCanceledCard(vo.getPgCd(), compareDt, compareDt);
				List<Card> cardList = new ArrayList<Card>();
				cardList.addAll(cardApproveList);
				cardList.addAll(cardCancelList);
				
				logger.info("cardList : {}", cardList.size());
				long totalApprovedAmt = 0L;
				long totalCanceledAmt = 0L;
				long totalCnt = cardList.size();
				long matchCnt = 0L;
				long notMatchCnt = 0L;
				long noOrderDataCnt = 0L;
				
				
				for(Card card : cardList) {
					
					if("APPROVED".equals(card.getApproveStatus())) {
						totalApprovedAmt += card.getApproveAmt();
					}else if("CANCELED".equals(card.getApproveStatus())) {
						totalCanceledAmt += card.getApproveAmt();
					}
					
					Orders order = this.ordersService.getOrderByTid(card.getTid());
					//PG대사 내역에는 있으나 주문내역에는 없는 경우
					if(order == null) {
						noOrderDataCnt++;
						logger.info("[NO ORDER DATA]tid:orderNo:orderAmt : {}", card.getTid()+":"+card.getOrderNo()+":"+card.getApproveAmt());
						
					}else {
						
						if(card.getApproveAmt() != order.getOrderAmt()) { // tid에 대한 금액이 상이한 경우
							noOrderDataCnt++;
							//대사 테이블에 기록
							card.setCompareStatus("NOT_MATCHED_AMOUNT");
							this.cardCompareService.updateCard(card);
							logger.info("[NOT MATCH DATA] : {}", card);
						}else if(card.getApproveStatus().equals(order.getOrderStatus()) == false) { // tid에 대한 결제상태가 상이한 경우
							notMatchCnt++; // 같은 날 승인성공과 승인취소가 동시에 데이터가 내려온 다면 건수는 둘다 카운트
							card.setCompareStatus("NOT_MATCHED_STATUS");
							this.cardCompareService.updateCard(card);
							logger.info("[NOT MATCH DATA]: {}", card);
						}else {
							matchCnt++;
							card.setCompareStatus("MATCHED");
							this.cardCompareService.updateCard(card);
						}
					}
					
				}
		    	
				JSONObject responseObj = new JSONObject();
				responseObj.put("TOTAL_PG_COUNT", totalCnt);
				responseObj.put("TOTAL_PG_APPROVED_AMOUNT", totalApprovedAmt);
				responseObj.put("TOTAL_PG_CANCELED_AMOUNT", totalCanceledAmt);
				responseObj.put("MATHCHED", matchCnt);
				responseObj.put("NOT_MATHCHED", notMatchCnt);
				responseObj.put("NO_ORDER_DATA", noOrderDataCnt);
			    logger.info("jobj : {}", responseObj.toJSONString());
				
		    	
		    	// 응답 header 생성 
		    	HttpHeaders responseHeaders = new HttpHeaders();
		    	responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		    	
		    	// 응답처리
		    	return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
			}
			
			
			 
		}catch(Exception e){
			logger.warn("error",e);
			return new ResponseEntity(new CustomErrorType("관리자에 문의하세요"),HttpStatus.CONFLICT);
		}
    }
	
	// 신용카드 월대사
	@RequestMapping(value = "/month-compare/card", method = RequestMethod.POST)
	public ResponseEntity<?> CardCompare(@RequestBody CompareVO vo) {
			
		try{
			
			logger.info("신용카드 월대사 시작[{}]", vo.getPgCd());
			logger.info("실행일 : {}", vo.getProcessDt());
			String compareDt;
			
			if(DateUtil.dateCheck(vo.getProcessDt())==false) {
				return new ResponseEntity(new CustomErrorType("인자(실행일)가 올바르지 않습니다."), HttpStatus.BAD_REQUEST);
			}else {

				//실행일의 전월일자를 대사한다.
				compareDt = DateUtil.addMonth(vo.getProcessDt(), -1);
					
				logger.info("대사일 : {}", compareDt);
				
				//전월의 01~31일까지의 데이터를 가져온다.
				List<Card> cardApproveList = this.cardCompareService.getApprovedCard(vo.getPgCd(), compareDt.substring(0, 6)+"01", compareDt.substring(0, 6)+"31");
				List<Card> cardCancelList = this.cardCompareService.getCanceledCard(vo.getPgCd(), compareDt.substring(0, 6)+"01", compareDt.substring(0, 6)+"31");
				List<Card> cardList = new ArrayList<Card>();
				cardList.addAll(cardApproveList);
				cardList.addAll(cardCancelList);
				
				
				logger.info("cardList : {}", cardList.size());
				long totalApprovedAmt = 0L;
				long totalCanceledAmt = 0L;
				long totalCnt = cardList.size();
				long matchCnt = 0L;
				long notMatchCnt = 0L;
				long noOrderDataCnt = 0L;
					
					
				for(Card card : cardList) {
						
					if("APPROVED".equals(card.getApproveStatus())) {
						totalApprovedAmt += card.getApproveAmt();
					}else if("CANCELED".equals(card.getApproveStatus())) {
						totalCanceledAmt += card.getApproveAmt();
					}
						
					Orders order = this.ordersService.getOrderByTid(card.getTid());
					//PG대사 내역에는 있으나 주문내역에는 없는 경우
					if(order == null) {
						noOrderDataCnt++;
						logger.info("[NO ORDER DATA]tid:orderNo:orderAmt : {}", card.getTid()+":"+card.getOrderNo()+":"+card.getApproveAmt());
							
					}else {
						
						if(card.getApproveAmt() != order.getOrderAmt()) { // tid에 대한 금액이 상이한 경우
							noOrderDataCnt++;
							card.setCompareStatus("NOT_MATCHED_AMOUNT");
							this.cardCompareService.updateCard(card);
							logger.info("[NOT MATCH DATA] : {}", card);
						}else if(card.getApproveStatus().equals(order.getOrderStatus()) == false) { // tid에 대한 결제상태가 상이한 경우
							notMatchCnt++; // 같은 날 승인성공과 승인취소가 동시에 데이터가 내려온 다면 건수는 둘다 카운트
							card.setCompareStatus("NOT_MATCHED_STATUS");
							this.cardCompareService.updateCard(card);
							logger.info("[NOT MATCH DATA]: {}", card);
						}else {
							matchCnt++;
							card.setCompareStatus("MATCHED");
							this.cardCompareService.updateCard(card);
						}
					}
						
				}
			    	
				JSONObject responseObj = new JSONObject();
				responseObj.put("TOTAL_PG_COUNT", totalCnt);
				responseObj.put("TOTAL_PG_APPROVED_AMOUNT", totalApprovedAmt);
				responseObj.put("TOTAL_PG_CANCELED_AMOUNT", totalCanceledAmt);
				responseObj.put("MATHCHED", matchCnt);
				responseObj.put("NOT_MATHCHED", notMatchCnt);
				responseObj.put("NO_ORDER_DATA", noOrderDataCnt);
				logger.info("jobj : {}", responseObj.toJSONString());
					
			    	
			    // 응답 header 생성 
			    HttpHeaders responseHeaders = new HttpHeaders();
			    responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
			    	
			    // 응답처리
			    return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
			}
				
				
				 
		}catch(Exception e){
			logger.warn("error",e);
			return new ResponseEntity(new CustomErrorType("관리자에 문의하세요"),HttpStatus.CONFLICT);
		}
	}


	// 가상계좌 일대사
	@RequestMapping(value = "/day-compare/vacct", method = RequestMethod.POST)
    public ResponseEntity<?> VacctDayCompare(@RequestBody CompareVO vo) {
		
		try{
			
			logger.info("가상계좌 일대사 시작[{}]", vo.getPgCd());
			logger.info("실행일 : {}", vo.getProcessDt());
			String compareDt;

			if(DateUtil.dateCheck(vo.getProcessDt())==false) {
				return new ResponseEntity(new CustomErrorType("인자(실행일)가 올바르지 않습니다."), HttpStatus.BAD_REQUEST);
			}else {

				//실행일의 전일자를 대사한다.
				compareDt = DateUtil.addDate(vo.getProcessDt(), -1);
				
				logger.info("대사일 : {}", compareDt);
				
				List<Vacct> vacctList = this.vacctCompareService.getVacct(vo.getPgCd(), compareDt, compareDt);
				
				
				logger.info("vacctList : {}", vacctList.size());
				long totalDepositAmt = 0L;
				long totalCanceledAmt = 0L;
				long totalCnt = vacctList.size();
				long matchCnt = 0L;
				long notMatchCnt = 0L;
				long calceledCnt = 0L;
				long noOrderDataCnt = 0L;
				
				
				for(Vacct vacct : vacctList) {
					
					totalDepositAmt += vacct.getDepositAmt();
					// 가상계좌는 당일에 입금취소가 발생할 수 있다.
					if(vacct.getCancelDt() != null && vacct.getCancelDt().length()==8) {
						totalCanceledAmt += vacct.getDepositAmt();
					}
					
					Orders order = this.ordersService.getOrderByTid(vacct.getTid());
					//PG대사 내역에는 있으나 주문내역에는 없는 경우
					if(order == null) {
						noOrderDataCnt++;
						logger.info("[NO ORDER DATA]tid:orderNo:orderAmt : {}", vacct.getTid()+":"+vacct.getOrderNo()+":"+vacct.getDepositAmt());
						
					}else {
						
						if(vacct.getDepositAmt() != order.getOrderAmt()) { // tid에 대한 금액이 상이한 경우
							noOrderDataCnt++;
							vacct.setCompareStatus("NOT_MATCHED_AMOUNT");
							this.vacctCompareService.updateVacct(vacct);
							logger.info("[NOT MATCH DATA] : {}", vacct);
						}else if(vacct.getCancelDt() != null && vacct.getCancelDt().length()==8) { // 취소가 발생한 경우
							calceledCnt++; // 입금취소가 발생 주문건이 주문내역에 잘 반영되었는지 확인한다.
							logger.info("[CANCELD DATA]: {}", vacct);
						}else {
							matchCnt++;
							vacct.setCompareStatus("MATCHED");
							this.vacctCompareService.updateVacct(vacct);
						}
					}
					
				}
		    	
				JSONObject responseObj = new JSONObject();
				responseObj.put("TOTAL_PG_COUNT", totalCnt);
				responseObj.put("TOTAL_PG_APPROVED_AMOUNT", totalDepositAmt);
				responseObj.put("TOTAL_PG_CANCELED_AMOUNT", totalCanceledAmt);
				responseObj.put("MATHCHED", matchCnt);
				responseObj.put("NOT_MATHCHED", notMatchCnt);
				responseObj.put("NO_ORDER_DATA", noOrderDataCnt);
				responseObj.put("CALCELED_DATA", calceledCnt);
			    logger.info("jobj : {}", responseObj.toJSONString());
				
		    	
		    	// 응답 header 생성 
		    	HttpHeaders responseHeaders = new HttpHeaders();
		    	responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		    	
		    	// 응답처리
		    	return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
			}
			
			
			 
		}catch(Exception e){
			logger.warn("error",e);
			return new ResponseEntity(new CustomErrorType("관리자에 문의하세요"),HttpStatus.CONFLICT);
		}
    }
	


	// 가상계좌 월대사
	@RequestMapping(value = "/month-compare/vacct", method = RequestMethod.POST)
    public ResponseEntity<?> VacctMonthCompare(@RequestBody CompareVO vo) {
		
		try{
			
			logger.info("가상계좌 월대사 시작[{}]", vo.getPgCd());
			logger.info("실행일 : {}", vo.getProcessDt());
			String compareDt;

			if(DateUtil.dateCheck(vo.getProcessDt())==false) {
				return new ResponseEntity(new CustomErrorType("인자(실행일)가 올바르지 않습니다."), HttpStatus.BAD_REQUEST);
			}else {

				//실행일의 전월일자를 대사한다.
				compareDt = DateUtil.addMonth(vo.getProcessDt(), -1);
				
				logger.info("대사일 : {}", compareDt);
				
				List<Vacct> vacctList = this.vacctCompareService.getVacct(vo.getPgCd(), compareDt.substring(0, 6)+"01", compareDt.substring(0, 6)+"31");
				
				
				logger.info("vacctList : {}", vacctList.size());
				long totalDepositAmt = 0L;
				long totalCanceledAmt = 0L;
				long totalCnt = vacctList.size();
				long matchCnt = 0L;
				long notMatchCnt = 0L;
				long calceledCnt = 0L;
				long noOrderDataCnt = 0L;
				
				
				for(Vacct vacct : vacctList) {
					
					totalDepositAmt += vacct.getDepositAmt();
					// 가상계좌는 당일에 입금취소가 발생할 수 있다.
					if(vacct.getCancelDt() != null && vacct.getCancelDt().length()==8) {
						totalCanceledAmt += vacct.getDepositAmt();
					}
					
					Orders order = this.ordersService.getOrderByTid(vacct.getTid());
					//PG대사 내역에는 있으나 주문내역에는 없는 경우
					if(order == null) {
						noOrderDataCnt++;
						logger.info("[NO ORDER DATA]tid:orderNo:orderAmt : {}", vacct.getTid()+":"+vacct.getOrderNo()+":"+vacct.getDepositAmt());
						
					}else {
						
						if(vacct.getDepositAmt() != order.getOrderAmt()) { // tid에 대한 금액이 상이한 경우
							noOrderDataCnt++;
							vacct.setCompareStatus("NOT_MATCHED_AMOUNT");
							this.vacctCompareService.updateVacct(vacct);
							logger.info("[NOT MATCH DATA] : {}", vacct);
						}else if(vacct.getCancelDt() != null && vacct.getCancelDt().length()==8) { // 취소가 발생한 경우
							calceledCnt++; // 입금취소가 발생 주문건이 주문내역에 잘 반영되었는지 확인한다.
							logger.info("[CANCELD DATA]: {}", vacct);
							
						}else {
							matchCnt++;
							vacct.setCompareStatus("MATCHED");
							this.vacctCompareService.updateVacct(vacct);
						}
					}
					
				}
		    	
				JSONObject responseObj = new JSONObject();
				responseObj.put("TOTAL_PG_COUNT", totalCnt);
				responseObj.put("TOTAL_PG_APPROVED_AMOUNT", totalDepositAmt);
				responseObj.put("TOTAL_PG_CANCELED_AMOUNT", totalCanceledAmt);
				responseObj.put("MATHCHED", matchCnt);
				responseObj.put("NOT_MATHCHED", notMatchCnt);
				responseObj.put("NO_ORDER_DATA", noOrderDataCnt);
				responseObj.put("CALCELED_DATA", calceledCnt);
			    logger.info("jobj : {}", responseObj.toJSONString());
				
		    	
		    	// 응답 header 생성 
		    	HttpHeaders responseHeaders = new HttpHeaders();
		    	responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		    	
		    	// 응답처리
		    	return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
			}
			
			
			 
		}catch(Exception e){
			logger.warn("error",e);
			return new ResponseEntity(new CustomErrorType("관리자에 문의하세요"),HttpStatus.CONFLICT);
		}
    }
	


}
