package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

public class Record extends JpaEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	// 到期时间
	private Date expireDate;
	// 套餐ID
	private Long comboId;
	private Combo combo;
	// 价钱
	private Double price;
	// 分成ID
	private Long scaleId;
	private Scale scale;
	// 份额（占比例）
	private Double extract1;
    // 份额（剩余）
	private Double extract2;
	// 结算日期
	private Date cutoffDate;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 备注
	private String remark;
	// 类型：1-新增，2-续费
	private Integer type;
	//
	private Long appId;
	
	private App app;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
	public Long getComboId() {
		return comboId;
	}

	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}

	public Long getScaleId() {
		return scaleId;
	}

	public void setScaleId(Long scaleId) {
		this.scaleId = scaleId;
	}
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
}