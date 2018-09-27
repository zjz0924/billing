package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class App extends JpaEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9025913208134841750L;

	private Long id;
	// 名称
	private String name;
	// 客户ID
	private Long contactsId;
	
	private Contacts contacts;
	
	// 生效日期
	private Date effectiveDate;
	// 过期日期
	private Date expireDate;
	// 备注
	private String remark;
	// 未签名文件
	private String unsignFile;
	// 未签名文件名称
	private String unsignFileName;
	// 签名文件
	private String signFile;
	// 已签名文件名称
	private String signFileName;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 是否删除，0：否，1：是
	private Integer isDelete;
	// 证书ID
	private Long certId;
	
	private Certificate certificate;

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

	public Long getContactsId() {
		return contactsId;
	}

	public void setContactsId(Long contactsId) {
		this.contactsId = contactsId;
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

	public String getUnsignFile() {
		return unsignFile;
	}

	public void setUnsignFile(String unsignFile) {
		this.unsignFile = unsignFile;
	}

	public String getUnsignFileName() {
		if(StringUtils.isBlank(this.unsignFileName)) {
			if (StringUtils.isNotBlank(this.getUnsignFile())) {
				this.unsignFileName = this.getUnsignFile().substring(this.getUnsignFile().lastIndexOf("/") + 1);
			}
		}
		return this.unsignFileName;
	}

	public void setUnsignFileName(String unsignFileName) {
		this.unsignFileName = unsignFileName;
	}

	public String getSignFile() {
		return signFile;
	}

	public void setSignFile(String signFile) {
		this.signFile = signFile;
	}

	public String getSignFileName() {
		if(StringUtils.isBlank(this.signFileName)) {
			if (StringUtils.isNotBlank(this.getSignFile())) {
				this.signFileName = this.getSignFile().substring(this.getSignFile().lastIndexOf("/") + 1);
			}
		}
		return this.signFileName;
	}

	public void setSignFileName(String signFileName) {
		this.signFileName = signFileName;
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

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public Long getCertId() {
		return certId;
	}

	public void setCertId(Long certId) {
		this.certId = certId;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	@JsonIgnore
	public Serializable getPrimaryKey() {
		return this.id;
	}
}