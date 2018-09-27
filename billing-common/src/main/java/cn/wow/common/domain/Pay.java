package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 支出
 * @author zhen
 * 2018-08-28
 */
public class Pay extends JpaEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 支付日期
	private Date payDate;
    // 备注
    private String remark;
    // 金额
    private Double price;
    // 创建时间
    private Date createTime;
    // 是否删除
    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
}