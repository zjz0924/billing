package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class App extends JpaEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9025913208134841750L;

	private Long id;
	// 名称
	private String name;
	// 生效日期
	private Date effectiveDate;
	// 过期日期
	private Date expireDate;
	// 备注
	private String remark;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 是否删除，0：否，1：是
	private Integer isDelete;
	// 是否已结算，0：否，1：是
	private Integer isCut;

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
		this.name = name;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsCut() {
		return isCut;
	}

	public void setIsCut(Integer isCut) {
		this.isCut = isCut;
	}

	@JsonIgnore
	public Serializable getPrimaryKey() {
		return this.id;
	}
}