package cn.wow.common.vo;

import java.io.Serializable;

/**
 *  金额统计
 * @author zhen
 * 2018-08-28
 */
public class PriceItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 名称
	private String name;
	
	private Double total1 = 0d;
	
	private Double total2 = 0d;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotal1() {
		return total1;
	}

	public void setTotal1(Double total1) {
		this.total1 = total1;
	}

	public Double getTotal2() {
		return total2;
	}

	public void setTotal2(Double total2) {
		this.total2 = total2;
	}
	
}
