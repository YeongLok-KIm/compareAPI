package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import CompareAPI.myAPI.data.jpa.domain.Card;

public interface CardCompareRepository extends CrudRepository<Card, String> {
	
	List<Card> findByPgCdAndApproveDtBetween(String pgCd, String compareStartDt, String compareEndDt);
	List<Card> findByPgCdAndCancelDtBetween(String pgCd, String compareStartDt, String compareEndDt);

}

