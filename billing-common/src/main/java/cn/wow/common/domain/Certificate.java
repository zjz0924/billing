package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Certificate extends JpaEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	// 证书名称
	private String name;
	// 证书过期时间
	private Date certExpireDate;
	// 描述文件过期时间
	private Date descExpireDate;
	// 证书
	private String certFile;
	// 证书名称
	private String certFileName;
	// 描述文件
	private String descFile;
	// 描述文件名称
	private String descFileName;
	// 创建时间
	private Date createTime;
	// 备注
	private String remark;
	// 是否已删除（0：否，1：是）
	private Integer isDelete;

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

	public Date getCertExpireDate() {
		return certExpireDate;
	}

	public void setCertExpireDate(Date certExpireDate) {
		this.certExpireDate = certExpireDate;
	}

	public Date getDescExpireDate() {
		return descExpireDate;
	}

	public void setDescExpireDate(Date descExpireDate) {
		this.descExpireDate = descExpireDate;
	}

	public String getCertFile() {
		return certFile;
	}

	public void setCertFile(String certFile) {
		this.certFile = certFile == null ? null : certFile.trim();
	}

	public String getDescFile() {
		return descFile;
	}

	public void setDescFile(String descFile) {
		this.descFile = descFile == null ? null : descFile.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCertFileName() {
		if (StringUtils.isNotBlank(this.getCertFile())) {
			return this.getCertFile().substring(this.getCertFile().lastIndexOf("/") + 1);
		}
		return "";
	}

	public void setCertFileName(String certFileName) {
		this.certFileName = certFileName;
	}

	public String getDescFileName() {
		if (StringUtils.isNotBlank(this.getDescFile())) {
			return this.getDescFile().substring(this.getDescFile().lastIndexOf("/") + 1);
		}
		return "";
	}

	public void setDescFileName(String descFileName) {
		this.descFileName = descFileName;
	}

	@Override
	public Serializable getPrimaryKey() {
		return id;
	}
}