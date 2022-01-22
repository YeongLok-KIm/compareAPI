package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import CompareAPI.myAPI.data.jpa.domain.Vacct;

public interface VacctCompareService  {
	
	List<Vacct> getVacct(String pgCd, String compareStartDt, String compareEndDt);
	Vacct updateVacct(Vacct vact);

}
