package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import CompareAPI.myAPI.data.jpa.domain.Vacct;

public interface VacctCompareRepository extends CrudRepository<Vacct, String> {
	
	List<Vacct> findByPgCdAndDepositDtBetween(String pgCd, String compareStartDt, String compareEndDt);

}

