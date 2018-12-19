package com.jvxb.demo.sbDemo.livable.modules.userinfo.mapper;

import java.util.List;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public class UserInfoMapperProvider {
	
    private String column = "id, username, real_name, password, phone, idcard, birthday, age, gender, hobby, locate_province,"
    		+ " locate_city, locate_area, address, remark, head_url, photo_url, create_by, create_time, update_by, update_time";

	public String getTablePageData(String phone, String idcard) {
		String sql = "SELECT * FROM live_user_info where 1 = 1 ";
		if (CommonUtil.notNullOrEmpty(phone)) {
			sql += "AND phone LIKE CONCAT ('%', #{phone}, '%')  ";
		}
		if (CommonUtil.notNullOrEmpty(idcard)) {
			sql += "AND idcard LIKE CONCAT ('%', #{idcard}, '%')  ";
		}
		sql += " ORDER BY create_time DESC ";
		return sql;
	}
	
	public String insertOrUpdate(PageData pd) {
        String sql = "";
        pd.turnEmptyValueToNull();
        if (pd.get("id") == null) {
            sql = insert(pd);
        } else {
            sql = update(pd);
        }
        return sql;
    }

	public String insert(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into live_user_info (");
        sqlBuilder.append(column);
        sqlBuilder.append(") values (");
        sqlBuilder.append(" #{id}, ");
        sqlBuilder.append(" #{username}, ");
        sqlBuilder.append(" #{real_name}, ");
        sqlBuilder.append(" #{password}, ");
        sqlBuilder.append(" #{phone}, ");
        sqlBuilder.append(" #{idcard}, ");
        sqlBuilder.append(" #{birthday}, ");
        sqlBuilder.append(" #{age}, ");
        sqlBuilder.append(" #{gender}, ");
        sqlBuilder.append(" #{hobby}, ");
        sqlBuilder.append(" #{locate_province}, ");
        sqlBuilder.append(" #{locate_city}, ");
        sqlBuilder.append(" #{locate_area}, ");
        sqlBuilder.append(" #{address}, ");
        sqlBuilder.append(" #{remark}, ");
        sqlBuilder.append(" #{head_url}, ");
        sqlBuilder.append(" #{photo_url}, ");
        sqlBuilder.append(" #{create_by}, ");
        sqlBuilder.append(" #{create_time}, ");
        sqlBuilder.append(" #{update_by}, ");
        sqlBuilder.append(" #{update_time} ");
        sqlBuilder.append(")");
        sql = sqlBuilder.toString();
        return sql;
    }

    public String update(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update live_user_info set ");
        if(pd.get("id") != null) {
			sqlBuilder.append(" id = #{id},");
		}
        if(pd.get("username") != null) {
			sqlBuilder.append(" username = #{username},");
		}
        if(pd.get("real_name") != null) {
			sqlBuilder.append(" real_name = #{real_name},");
		}
        if(pd.get("password") != null) {
			sqlBuilder.append(" password = #{password},");
		}
        if(pd.get("phone") != null) {
			sqlBuilder.append(" phone = #{phone},");
		}
        if(pd.get("idcard") != null) {
			sqlBuilder.append(" idcard = #{idcard},");
		}
        if(pd.get("birthday") != null) {
			sqlBuilder.append(" birthday = #{birthday},");
		}
        if(pd.get("age") != null) {
			sqlBuilder.append(" age = #{age},");
		}
        if(pd.get("gender") != null) {
			sqlBuilder.append(" gender = #{gender},");
		}
        if(pd.get("hobby") != null) {
			sqlBuilder.append(" hobby = #{hobby},");
		}
        if(pd.get("locate_province") != null) {
			sqlBuilder.append(" locate_province = #{locate_province},");
		}
        if(pd.get("locate_city") != null) {
			sqlBuilder.append(" locate_city = #{locate_city},");
		}
        if(pd.get("locate_area") != null) {
			sqlBuilder.append(" locate_area = #{locate_area},");
		}
        if(pd.get("address") != null) {
			sqlBuilder.append(" address = #{address},");
		}
        if(pd.get("remark") != null) {
			sqlBuilder.append(" remark = #{remark},");
		}
        if(pd.get("photo_url") != null) {
        	sqlBuilder.append(" photo_url = #{photo_url},");
        }
        if(pd.get("head_url") != null) {
        	sqlBuilder.append(" head_url = #{head_url},");
        }
        if(pd.get("create_by") != null) {
			sqlBuilder.append(" create_by = #{create_by},");
		}
        if(pd.get("create_time") != null) {
			sqlBuilder.append(" create_time = #{create_time},");
		}
        if(pd.get("update_by") != null) {
			sqlBuilder.append(" update_by = #{update_by},");
		}
        if(pd.get("update_time") != null) {
			sqlBuilder.append(" update_time = #{update_time},");
		}
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where id =  #{id}";
        return sql;
    }

    public String updateAll(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update live_user_info set ");
		sqlBuilder.append(" id = #{id},");
		sqlBuilder.append(" username = #{username},");
		sqlBuilder.append(" real_name = #{real_name},");
		sqlBuilder.append(" password = #{password},");
		sqlBuilder.append(" phone = #{phone},");
		sqlBuilder.append(" idcard = #{idcard},");
		sqlBuilder.append(" birthday = #{birthday},");
		sqlBuilder.append(" age = #{age},");
		sqlBuilder.append(" gender = #{gender},");
		sqlBuilder.append(" hobby = #{hobby},");
		sqlBuilder.append(" locate_province = #{locate_province},");
		sqlBuilder.append(" locate_city = #{locate_city},");
		sqlBuilder.append(" locate_area = #{locate_area},");
		sqlBuilder.append(" address = #{address},");
		sqlBuilder.append(" remark = #{remark},");
    	sqlBuilder.append(" head_url = #{head_url},");
    	sqlBuilder.append(" photo_url = #{photo_url},");
		sqlBuilder.append(" create_by = #{create_by},");
		sqlBuilder.append(" create_time = #{create_time},");
		sqlBuilder.append(" update_by = #{update_by},");
		sqlBuilder.append(" update_time = #{update_time},");
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where id = #{id}";
        return sql;
    }

	public String delete(PageData pd) {
		String sql = "delete from live_user_info where id = #{id}";
		return sql;
	}

	public String get(PageData pd) {
		String sql = "select "+"id, username, real_name, password, phone, idcard, DATE_FORMAT(birthday, '%Y-%m-%d') birthday, age, gender, hobby, locate_province,"
	    		+ " locate_city, locate_area, address, remark, head_url, photo_url, create_by, create_time, update_by, update_time"+" from live_user_info where id = #{id}";
		return sql;
	}

	public String getAll(PageData pd) {
		String sql = "select * from live_user_info";
		return sql;
	}
	
	public String insertBatch(List<PageData> listPd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into live_user_info (username, password) values");
        for(PageData pd: listPd){
			if(pd !=null && pd.get("var0") !=null){
				String username = pd.getString("var0");
				String password = "123456";
				sqlBuilder.append("('" + username +"','"+password+"'),");
			}
		}
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        return sql;
    }

}
