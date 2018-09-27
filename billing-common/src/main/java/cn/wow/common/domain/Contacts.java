package cn.wow.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系人
 * @author zhen
 * 2018-08-26
 */
public class Contacts extends JpaEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    // 姓名
    private String name;
    // 微信
    private String wechat;
    // 支付宝
    private String alipay;
    // 电话
    private String phone;
    // 备注
    private String remark;
    // 是否删除
    private Integer isDelete;
    // 创建时间
    private Date createTime;

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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Serializable getPrimaryKey() {
		return this.id;
	}
    
}