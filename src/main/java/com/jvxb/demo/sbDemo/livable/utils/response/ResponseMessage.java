package com.jvxb.demo.sbDemo.livable.utils.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * 响应工具
 * 
 * @author 抓娃小兵
 */
public class ResponseMessage<T> implements Serializable {

	private static final long serialVersionUID = -4772210798944513134L;
	/**
	 * 响应状态码
	 */
	protected Integer code;

	/**
	 * 响应消息，用于描述响应结果
	 */
	protected String message;

	/**
	 * 响应数据
	 */
	protected T data;

	/**
	 * 服务器时间戳
	 */
	private Long timestamp;

	public static <T> ResponseMessage<T> ok() {
		return ok(null);
	}

	public static <T> ResponseMessage<T> ok(T result) {
		return new ResponseMessage<T>().code(HttpStatus.OK.value()).putTimeStamp().result(result);
	}

	public static <T> ResponseMessage<T> error(String message) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}

	public static <T> ResponseMessage<T> error(int code, String message) {
		ResponseMessage<T> msg = new ResponseMessage<>();
		msg.setMessage(message);
		return msg.code(code).putTimeStamp();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public ResponseMessage<T> code(int code) {
		this.code = code;
		return this;
	}

	public ResponseMessage<T> result(T result) {
		this.data = result;
		return this;
	}

	private ResponseMessage<T> putTimeStamp() {
		this.timestamp = System.currentTimeMillis();
		return this;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
