package com.jvxb.demo.sbDemo.livable.modules.api.enums;

public enum ApiRequestStatusEnum {
	
	OK("0", "OK"),
	NO_THIRD_ID("1", "No ThirdId in Request"),
	UNKNOW_THIRD_ID("2", "Unknow ThirdId"),
	NO_MEMBER("3", "Member Expire"),
	MEMBER_EXPIRE("4", "Member Expire"),
	REQUEST_MANY("5", "Request too many times"),
	UNKNOW_ERROR("6", "未知错误");
	
	private String status;
    private String desc;
    
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	private ApiRequestStatusEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static ApiRequestStatusEnum getReqestEnum(String code) {
		ApiRequestStatusEnum requestStatus = ApiRequestStatusEnum.UNKNOW_ERROR;
		if(code == null) {
			return requestStatus;
		}
        for (ApiRequestStatusEnum requestCode : ApiRequestStatusEnum.values()) {
            if (requestCode.getStatus().equals(code)) {
                return requestCode;
            }
        }
        return requestStatus;
    }

    public static String getDescByStatus(String code) {
        return getReqestEnum(code).desc;
    }
	
}
