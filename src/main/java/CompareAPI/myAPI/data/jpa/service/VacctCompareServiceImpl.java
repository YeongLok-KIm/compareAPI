package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import CompareAPI.myAPI.data.jpa.domain.Vacct;


@Service
public class VacctCompareServiceImpl implements VacctCompareService {
	
	private final VacctCompareRepository vacctRepository;

	@Autowired
	public VacctCompareServiceImpl(VacctCompareRepository vacctRepository) {
		this.vacctRepository = vacctRepository;
	}

		
	@Override
	@Transactional
	public Vacct updateVacct(Vacct vacct) {
		// TODO Auto-generated method stub
		return this.vacctRepository.save(vacct);
	}


	@Override
	public List<Vacct> getVacct(String pgCd, String compareStartDt, String compareEndDt) {
		// TODO Auto-generated method stub
		Assert.hasLength(pgCd, "pgCd must not be empty");
		Assert.hasLength(compareStartDt, "compareStarDt must not be empty");
		Assert.hasLength(compareEndDt,  "compareEndDt must not be empty");
		return this.vacctRepository.findByPgCdAndDepositDtBetween(pgCd, compareStartDt, compareEndDt);
	}

}
