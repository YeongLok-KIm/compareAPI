package CompareAPI.myAPI.data.jpa.service;

import java.util.List;

import CompareAPI.myAPI.data.jpa.domain.Orders;

public interface OrdersService {
	
	List<Orders> getOrders(String orderMethod, String pgCd, String createDt);
	Orders getOrderByTid(String tid);

}
