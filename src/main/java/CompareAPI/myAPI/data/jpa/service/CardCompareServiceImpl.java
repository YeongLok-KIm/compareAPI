package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import CompareAPI.myAPI.data.jpa.domain.Card;


@Service
public class CardCompareServiceImpl implements CardCompareService {
	
	private final CardCompareRepository cardRepository;

	@Autowired
	public CardCompareServiceImpl(CardCompareRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

		
	@Override
	@Transactional
	public Card updateCard(Card card) {
		// TODO Auto-generated method stub
		return this.cardRepository.save(card);
	}


	@Override
	public List<Card> getApprovedCard(String pgCd, String compareStartDt, String compareEndDt) {
		// TODO Auto-generated method stub
		Assert.hasLength(pgCd, "pgCd must not be empty");
		Assert.hasLength(compareStartDt, "compareStarDt must not be empty");
		Assert.hasLength(compareEndDt,  "compareEndDt must not be empty");
		return this.cardRepository.findByPgCdAndApproveDtBetween(pgCd, compareStartDt, compareEndDt);
	}


	@Override
	public List<Card> getCanceledCard(String pgCd, String compareStartDt, String compareEndDt) {
		// TODO Auto-generated method stub
		Assert.hasLength(pgCd, "pgCd must not be empty");
		Assert.hasLength(compareStartDt, "compareStarDt must not be empty");
		Assert.hasLength(compareEndDt,  "compareEndDt must not be empty");
		return this.cardRepository.findByPgCdAndCancelDtBetween(pgCd, compareStartDt, compareEndDt);
	}


}
