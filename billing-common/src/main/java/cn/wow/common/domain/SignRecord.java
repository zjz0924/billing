package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 签名记录
 * @author zhen
 * 2018-08-26
 */
public class SignRecord extends JpaEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    // 类型： 1-新增，2-续费，3-补签
    private Integer type;
    // 套餐ID
    private Long comboId;
    
    private Combo combo;
    // 价钱
    private Double price;
    // 生效时间
    private Date effectiveDate;
    // 过期时间
    private Date expireDate;
    // appId
    private Long appId;
    
    private App app;
    
    // 创建时间
    private Date createTime;
    
    // 证书ID
    private Long certId;
    
    private Certificate certificate;
    
    // 支付类型（微信、支付宝、银行卡）
    private String payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
	public Long getComboId() {
		return comboId;
	}

	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
}