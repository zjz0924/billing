package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

public class Record extends JpaEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	// 名称
	private String name;
	// 到期时间
	private Date expireDate;
	// 价钱
	private Double price;
	// 比例
	private Integer scale;
	// 份额（占比例）
	private Double extract1;
    // 份额（剩余）
	private Double extract2;
	// 是否已结算（0：否，1：是）
	private Integer isCutoff;
	// 结算日期
	private Date cutoffDate;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 备注
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public Double getExtract1() {
		return extract1;
	}

	public void setExtract1(Double extract1) {
		this.extract1 = extract1;
	}

	public Double getExtract2() {
		return extract2;
	}

	public void setExtract2(Double extract2) {
		this.extract2 = extract2;
	}

	public Integer getIsCutoff() {
		return isCutoff;
	}

	public void setIsCutoff(Integer isCutoff) {
		this.isCutoff = isCutoff;
	}

	public Date getCutoffDate() {
		return cutoffDate;
	}

	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
}