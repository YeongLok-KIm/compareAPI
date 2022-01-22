package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import CompareAPI.myAPI.data.jpa.domain.Orders;


public interface OrdersRepository extends JpaRepository<Orders, String> {

	List<Orders> findByOrderMethodAndPgCdAndCreateDt(String orderMethod, String pgCd, String createDt);
	Orders findByTid(String tid);

}

