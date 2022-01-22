package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import CompareAPI.myAPI.data.jpa.domain.Card;

public interface CardCompareService  {
	
	List<Card> getApprovedCard(String pgCd, String compareStartDt, String compareEndDt);
	List<Card> getCanceledCard(String pgCd, String compareStartDt, String compareEndDt);
	Card updateCard(Card card);

}
