package com.jvxb.demo.sbDemo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jvxb.demo.sbDemo.livable.utils.CommonFileUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGenerate {
	
	public static List<String> tableInfoList = new ArrayList<String>();
	
	/** 数据库表名称 */
	private static final String TABLE_NAME = "test_gen";
	/** 表对应的类名名称 */
	private static final String ENTITY_NAME = "TestGen";
	/** 类所在的模块名，即modules下的一级文件夹 */
	private static final String MODULE_NAME = "tg";
	/** 生成代码所在的位置。如果需要，可以设置直接生成到项目中。 */
	private static final String TARGET_DIR = "D:\\codeGen\\sbDemo";
	
	public static void main(String[] args) {
		System.err.println("-----------------------------------------------");
		beginGenerate(); 
		System.err.println("-----------------------------------------------");
	}

	private static void beginGenerate() {


		// 代码生成位置
		String fileDir = TARGET_DIR + File.separator + MODULE_NAME + File.separator;
		try {
			// 生成基本的文件夹: 模块，模块下的controller,service,mapper，及存放html文件的文件夹。
			generateBasePackage(fileDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 是否生成在同一文件夹下中 */
		boolean SAME_PACKAGE = false;
		
		tableInfoList = getTableInfoList(TABLE_NAME);
		
		// 根据路径全名生成 XxxController.java
		String controllerClassName = ENTITY_NAME + "Controller.java";
		String controllerFileCompleteName = fileDir + (SAME_PACKAGE ? "" : ("controller" + File.separator))
				+ controllerClassName;
		System.err.println("--->开始生成代码：" + controllerFileCompleteName);
		CommonFileUtil.writeFile(controllerFileCompleteName, getControllerContent());

		// 根据路径全名生成 XxxService.java
		String serviceClassName = "I" + ENTITY_NAME + "Service.java";
		String serviceFileCompleteName = fileDir + (SAME_PACKAGE ? "" : ("service" + File.separator))
				+ serviceClassName;
		System.err.println("--->开始生成代码：" + serviceFileCompleteName);
		CommonFileUtil.writeFile(serviceFileCompleteName, getServiceContent());

		// 根据路径全名生成 XxxServiceImpl.java
		String implClassName = ENTITY_NAME + "ServiceImpl.java";
		String serviceImplFileCompleteName = fileDir
				+ (SAME_PACKAGE ? "" : ("service" + File.separator + "impl" + File.separator)) + implClassName;
		System.err.println("--->开始生成代码：" + serviceImplFileCompleteName);
		CommonFileUtil.writeFile(serviceImplFileCompleteName, getServiceImplContent());

		// 根据路径全名生成 XxxMapper.java
		String mapperClassName = ENTITY_NAME + "Mapper.java";
		String mapperFileCompleteName = fileDir + (SAME_PACKAGE ? "" : ("mapper" + File.separator)) + mapperClassName;
		System.err.println("--->开始生成代码：" + mapperFileCompleteName);
		CommonFileUtil.writeFile(mapperFileCompleteName, getMapperContent());

		// 根据路径全名生成 XxxMapperProvider.java
		String mapperProClassName = ENTITY_NAME + "MapperProvider.java";
		String mapperProFileCompleteName = fileDir + (SAME_PACKAGE ? "" : ("mapper" + File.separator))
				+ mapperProClassName;
		System.err.println("--->开始生成代码：" + mapperProFileCompleteName);
		CommonFileUtil.writeFile(mapperProFileCompleteName, getMapperProviderContent(tableInfoList));

		// 根据路径全名生成 xxx_form.html
		String listHtmlName = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1) + "_list.html";
		String listHtmlCompleteName = fileDir + MODULE_NAME + File.separator + listHtmlName;
		System.err.println("--->开始生成代码：" + listHtmlCompleteName);
		CommonFileUtil.writeFile(listHtmlCompleteName, getListHtmlContent(tableInfoList));

		// 根据路径全名生成 xxx_form.html
		String formHtmlName = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1) + "_form.html";
		String formHtmlCompleteName = fileDir + MODULE_NAME + File.separator + formHtmlName;
		System.err.println("--->开始生成代码：" + formHtmlCompleteName);
		CommonFileUtil.writeFile(formHtmlCompleteName, getFormHtmlContent(tableInfoList));

		System.err.println("==========================================");
		System.err.println("================生成完毕==================");
		System.err.println("==========================================");
		// 生成后打开目标文件夹
		CommonFileUtil.openDir(TARGET_DIR);
	
	}
	
	public static String getShowCreateTable(String tableName) {
		String dbName = "test_db";
	    String username = "root";
	    String password = "123456";
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/"+dbName+"?useUnicode=true&characterEncoding=utf8&useSSL=false";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	        String sql = "show create table "+ tableName;
	        PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                if ((i == 2)) {
	                    return rs.getString(i);
	                }
	             }
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
	private static String getFormHtmlContent(List<String> tableInfoList) {
		String entityNameLower = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1);
		String content = "<!DOCTYPE html>\r\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\"\r\n" + 
				"	xmlns:th=\"http://www.thymeleaf.org\"\r\n" + 
				"	xmlns:layout=\"http://www.ultraq.net.nz/thymeleaf/layout\">\r\n" + 
				"<head th:include=\"frame/header :: commonHeader\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<br>\r\n" + 
				"	<div class=\"layui-fluid\">\r\n" + 
				"		<form class=\"layui-form\" id=\""+ entityNameLower +"Form\" th:action=\"@{/admin/"+ entityNameLower +"/save}\" autocomplete=\"off\" >\r\n" + 
				"			<input type=\"text\" hidden=\"hidden\" id=\"" + tableInfoList.get(tableInfoList.size()-1) + "\" name=\"" + tableInfoList.get(tableInfoList.size()-1) +"\" value=\"\" />\r\n" + 
				"			\r\n" + 
				"			<div class=\"layui-form-item\">\r\n" + 
				"				<label class=\"layui-form-label\"><span style=\"color: red\"> * </span>字符列模版:</label>\r\n" + 
				"				<div class=\"layui-input-block\">\r\n" + 
				"					<input type=\"text\" id=\"columnName\" name=\"varcharModel\" maxlength=\"16\" \r\n" + 
				"						placeholder=\"（做模板用）\" class=\"layui-input\">\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"			<!--  \r\n" + 
				"			<div class=\"layui-form-item\">\r\n" + 
				"				<label class=\"layui-form-label\">日期列模版:</label>\r\n" + 
				"				<div class=\"layui-input-inline\">\r\n" + 
				"					<input type=\"text\" name=\"dateModel\" class=\"layui-input layDate\" dateFormat=\"yyyy-MM-dd\" placeholder=\"（做模板用）\">\r\n" + 
				"				</div>\r\n" + 
				"				<label class=\"layui-form-label\">数字列模版:</label>\r\n" + 
				"				<div class=\"layui-input-inline\">\r\n" + 
				"					<input type=\"number\" name=\"numberModel\" class=\"layui-input\" placeholder=\"（做模板用）\"  \r\n" + 
				"						oninput=\"if(Math.abs(value) > 9999.99) value=9999.99 ; if(value.length > 8) value=value.slice(0,8)\">\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" +
				"			<div class=\"layui-form-item\">\r\n" + 
				"				<label class=\"layui-form-label\">单选框模版:</label>\r\n" + 
				"				<div class=\"layui-input-inline\">\r\n" + 
				"					<input type=\"radio\" name=\"radioModel\" value=\"1\" title=\"是\" checked=\"\">\r\n" + 
				"					<input type=\"radio\" name=\"radioModel\" value=\"0\" title=\"否\" checked=\"\">\r\n" + 
				"				</div>\r\n" + 
				"				<label class=\"layui-form-label\">复选框模版:</label>\r\n" + 
				"				<div class=\"layui-input-inline\" style=\"width: 208px;\">\r\n" + 
				"					<input type=\"checkbox\" name=\"cb[one]\" lay-skin=\"primary\" title=\"吃喝\">\r\n" + 
				"					<input type=\"checkbox\" name=\"cb[two]\" lay-skin=\"primary\" title=\"玩乐\">\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" +
				"			<div class=\"layui-form-item\">\r\n" + 
				"				<label class=\"layui-form-label\">文本域模版:</label>\r\n" + 
				"				<div class=\"layui-input-block\">\r\n" + 
				"					<textarea name=\"textareaModel\" maxlength=\"16\" class=\"layui-textarea\" placeholder=\"（做模板用）\"></textarea>\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"			--> \r\n";
				for (int i = 1; i < tableInfoList.size() - 1; i++) {
					content += "			<div class=\"layui-form-item\">\r\n";
					content += "				<label class=\"layui-form-label\">列名"+(i)+":</label>\r\n";
					content += "				<div class=\"layui-input-block\">\r\n";
					content += "					<input type=\"text\" id=\"" + tableInfoList.get(i) + "\" name=\"" + tableInfoList.get(i) + "\"\r\n";
					content += "						placeholder=\"请输入列名\" class=\"layui-input\" >\r\n";
					content += "				</div>\r\n";
					content += "			</div>\r\n";
				}
				content += "		</form>\r\n" + 
				"		\r\n" + 
				"		<hr>\r\n" + 
				"		<div class=\"layui-form-item\" style=\"text-align: center;\">\r\n" + 
				"			<button type=\"button\" id=\"saveBtn\" class=\"layui-btn\" onclick=\"save()\">保存</button>\r\n" + 
				"			<button type=\"button\" class=\"layui-btn layui-btn-primary\"\r\n" + 
				"				onclick=\"parent.layer.closeAll()\">关闭</button>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"	\r\n" + 
				"	<!-- 功能相关js -->\r\n" + 
				"	<script type=\"text/javascript\" th:inline=\"javascript\">\r\n" + 
				"	\r\n" + 
				"	function initData() {\r\n" + 
				"		var ctxPath = [[${#httpServletRequest.getContextPath()}]];\r\n" + 
				"		//根据模式和url获取数据加载表单\r\n" + 
				"		var mode = [[${mode}]];  \r\n" + 
				"       var id = [[${id}]];\r\n" + 
				"       //先渲染表单，后加载\n" + 
				"		layui.use('form', function(){\n" + 
				"		  //主要是渲染radio,checkBox和select\n" + 
				"		  var form = layui.form;\n" + 
				"		  form.render();\n" + 
				"		});\r\n" + 
				"		var loadUrl = ctxPath + \"/admin/"+ entityNameLower +"/get/\"+id;\r\n" + 
				"		loadForm('#"+ entityNameLower +"Form', mode, loadUrl);\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	//加载Form表单数据\r\n" + 
				"	function loadForm(formId, mode, loadUrl){\r\n" + 
				"		$('#columnName').focus();\r\n" + 
				"		//一般加载Form表单\r\n" + 
				"		commonLoadForm(formId, mode, loadUrl);\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	//保存数据\r\n" + 
				"	function save(){\r\n" + 
				"		//验证数据有效性\r\n" + 
				"		if(!commonCheckValid()){\r\n" + 
				"			return;\r\n" + 
				" 		}\r\n" + 
				"		if(!checkValid()){\r\n" + 
				"			return;\r\n" + 
				" 		}\r\n" + 
				"		//按钮不可用3s，防止重复提交\r\n" + 
				"		disabledThreeSecond('#saveBtn');\r\n" + 
				"		//一般提交Form：1.弹出加载框，2.提交成功？(关闭所有弹窗，刷新页面，提示保存成功 ) : (关闭加载，提示失败)，\r\n" + 
				"		commonSubmitForm(\"#"+ entityNameLower +"Form\");\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	//保存时,验证数据有效性\r\n" + 
				"	function checkValid(){\r\n" + 
				"		if($(\"#columnName\").val().trim() == ''){\r\n" + 
				"			commonTips(\"#columnName\", \"无效数据\");\r\n" + 
				"			return false;\r\n" + 
				"		}\r\n" + 
				"	\r\n" + 
				"		return true;\r\n" + 
				"	}\r\n" + 
				"	</script>\r\n" + 
				"	\r\n" + 
				"	<!-- 初始化相关js -->\r\n" + 
				"	<script type=\"text/javascript\" th:inline=\"javascript\">\r\n" + 
				"	//初始化数据\r\n" + 
				"	initData();\r\n" + 
				"	</script>\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"";

		return content;
	}

	private static String getListHtmlContent(List<String> tableInfoList) {
		String entityNameLower = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1);
		String content = "<!DOCTYPE html>\r\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\"\r\n"
				+ "	xmlns:th=\"http://www.thymeleaf.org\"\r\n"
				+ "	xmlns:layout=\"http://www.ultraq.net.nz/thymeleaf/layout\">\r\n"
				+ "<head th:include=\"frame/header :: commonHeader\">\r\n" 
				+ "</head>\r\n" 
				+ "\r\n" 
				+ "<body>\r\n"
				+ "	<form id=\"searchForm\">\r\n"
				+ "		<div class=\"layui-form-item\" style=\"margin-bottom: 0px;\">\r\n"
				+ "			<label class=\"layui-form-label\" style=\"width: 130px\">查询列名： </label>\r\n"
				+ "			<div class=\"layui-input-inline\">\r\n"
				+ "				<input type=\"text\" name=\"columnName\" id=\"columnName\" class=\"layui-input\" autocomplete=\"off\">\r\n"
				+ "			</div>\r\n"
				+ "			<button type=\"button\" class=\"layui-btn\" onclick=\"search()\"><i class=\"layui-icon layui-icon-search\"></i>查询</button>\r\n"
				+ "			<button type=\"button\" class=\"layui-btn\" onclick=\"$('#searchForm')[0].reset()\"><i class=\"layui-icon layui-icon-refresh-1\"></i>重置</button>\r\n"
				+ "		</div>\r\n" 
				+ "	</form>\r\n" 
				+ "	<hr style=\"margin:3px 0px 3px 0px; \">\r\n"
				+ "	<button type=\"button\" class=\"layui-btn\" onclick=\"add()\"><i class=\"layui-icon layui-icon-add-1\"></i>新增</button>\r\n"
				+ "	<button type=\"button\" class=\"layui-btn\" onclick=\"search()\"><i class=\"layui-icon layui-icon-refresh\"></i>刷新</button>\r\n"
				+ "	\r\n" 
				+ "	<table class=\"layui-hide\" id=\"" + entityNameLower + "Table\" lay-filter=\"" + entityNameLower + "Table\"></table>\r\n" 
				+ "	\r\n"
				+ "	<script type=\"text/javascript\" th:inline=\"javascript\">\r\n"
				+ "	var ctxPath = [[${#httpServletRequest.getContextPath()}]];\r\n"
				+ "	layui.use('table', function(){\r\n" 
				+ "	  var table = layui.table;\r\n" 
				+ "	  table.render({\r\n"
				+ "	    elem: '#" + entityNameLower + "Table'\r\n" 
				+ "	    ,url: ctxPath + '/admin/" + entityNameLower + "/pageData'\r\n" 
				+ "	    ,cols: [ [\r\n" 
				+ "	       {type:\"numbers\",width : 70, title:\"序号\"}\r\n";
				for (int i = 1; i < tableInfoList.size() - 1; i++) {
					//主键列不需添加
					if(tableInfoList.get(i).equals(tableInfoList.get(tableInfoList.size()-1))) {
						continue;
					}
					content += "	      ,{field:'" + tableInfoList.get(i) + "', width:160, title: '列名'}\r\n";
				}
				content +=
				"	      ,{field:'create_time', width:180, title: '创建时间', sort: true, templet: \"<div>{{layui.util.toDateString(d.create_time, 'yyyy-MM-dd HH:mm:ss')}}</div>\"}\r\n"
				+ "	      ,{fixed : 'right',title : '操作',width : 250,align : 'center',toolbar : '#operationBar'}\r\n"
				+ "	    ] ]\r\n" 
				+ "		  ,page: true //是否显示分页\r\n" 
				+ "		  ,limits: [10, 20, 50, 100]\r\n"
				+ "		  ,limit: 10 //每页默认显示的数量\r\n" 
				+ "	  });\r\n" 
				+ "	  \r\n" 
				+ "	  table.on('tool(" + entityNameLower + "Table)', function(obj) {\r\n" 
				+ "			var data = obj.data; //获得当前行数据\r\n"
				+ "			var layEvent = obj.event; //获得 lay-event 对应的值\r\n"
				+ "			if (layEvent === 'view') {\r\n" + "				view(data.id);\r\n"
				+ "			} else if (layEvent === 'edit') {\r\n" + "				edit(data.id);\r\n"
				+ "			} else if (layEvent === 'delete') {\r\n" + "				del(data.id);\r\n"
				+ "			}\r\n" 
				+ "		});\r\n" 
				+ "	  \r\n" 
				+ "	});\r\n" 
				+ "	</script>\r\n" 
				+ "\r\n"
				+ "	<script type=\"text/html\" id=\"operationBar\">\r\n"
				+ "    	<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"view\">查看</button>\r\n"
				+ "    	<button type=\"button\" class=\"layui-btn layui-btn-xs\" lay-event=\"edit\">编辑</button>\r\n"
				+ "    	<button type=\"button\" class=\"layui-btn layui-btn-danger layui-btn-xs\"  lay-event=\"delete\">删除</button>\r\n"
				+ "	</script>\r\n" 
				+ "	\r\n" 
				+ "	<script th:inline=\"javascript\">\r\n" 
				+ "	function add(){\r\n"
				+ "		var title = \"新增\";\r\n" 
				+ "		var openurl = ctxPath + \"/admin/" + entityNameLower + "/form?mode=add\";\r\n" 
				+ "		commonOpen(title, openurl, \"800px\", \"550px\");\r\n" 
				+ "	}\r\n"
				+ "	\r\n" 
				+ "	function view(id) {\r\n" 
				+ "		var title = \"查看\";\r\n"
				+ "		var openurl = ctxPath + \"/admin/" + entityNameLower + "/form?mode=view&id=\"+id;\r\n"
				+ "		commonOpen(title, openurl, \"800px\", \"550px\");\r\n" 
				+ "	}\r\n" 
				+ "	\r\n"
				+ "	function edit(id) {\r\n" 
				+ "		var title = \"编辑\";\r\n"
				+ "		var openurl = ctxPath + \"/admin/" + entityNameLower + "/form?mode=edit&id=\" + id;\r\n"
				+ "		commonOpen(title, openurl, \"800px\", \"550px\");\r\n" 
				+ "	}\r\n" 
				+ "	\r\n"
				+ "	function del(id) {\r\n" 
				+ "		parent.layer.confirm(\"删除后数据不可恢复，确定需要删除吗？\", {\r\n"
				+ "            btn: [\"确定\", \"取消\"]\r\n" 
				+ "        }, function (){\r\n"
				+ "        	var delUrl = ctxPath + \"/admin/" + entityNameLower + "/delete/\"+id;\r\n"
				+ "        	commonDelete(delUrl);\r\n" 
				+ "        })\r\n" 
				+ "	}\r\n" 
				+ "	\r\n"
				+ "	function search(){\r\n" 
				+ "		var columnName = $('#columnName').val();\r\n"
				+ "		layui.table.reload('" + entityNameLower + "Table',{\r\n" 
				+ "			where : {\r\n"
				+ "			pageSize : 10,\r\n" 
				+ "			currentPage : 1,\r\n"
				+ "			columnName : columnName\r\n" 
				+ "			}\r\n" 
				+ "		})\r\n" 
				+ "	}\r\n"
				+ "	</script>\r\n" 
				+ "	\r\n" 
				+ "</html>";
		return content;
	}

	// 生成基本的controller,service,mapper包
	private static void generateBasePackage(String fileDir) throws Exception {
		CommonFileUtil.genDir(fileDir);
		CommonFileUtil.genDir(fileDir + "controller");
		CommonFileUtil.genDirs(fileDir + "service" + File.separator + "impl");
		CommonFileUtil.genDir(fileDir + "mapper");
		CommonFileUtil.genDir(fileDir + MODULE_NAME);
	}

	/**
	 * mapperProvider.java的内容
	 */
	private static String getMapperProviderContent(List<String> tableInfoList) {
		String tableName = TABLE_NAME;
		String columnStr = tableInfoList.subList(1, tableInfoList.size()-1).toString();
		columnStr = columnStr.substring(1, columnStr.length()-1);
		
		String content = "package com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".mapper;\r\n" + "\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.utils.PageData;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;\r\n"
				+ "\r\n" 
				+ "public class " + ENTITY_NAME + "MapperProvider {\r\n" 
				+ "\r\n" 
				+ "    private String column = \"" + columnStr +"\";\r\n" 
				+ "\r\n" 
				+ "    public String getTablePageData(String columnName) {\r\n"
				+ "        String sql = \"select \" + column + \" from " + tableName + " where 1 = 1 \";\r\n"
				+ "        if (columnName != null && columnName.length() > 0) {\r\n"
				+ "            sql += \"and "+ tableInfoList.get(2) +" like concat ('%', #{columnName}, '%')  \";\r\n" 
				+ "        }\r\n"
				+ "        return sql;\r\n" 
				+ "    }\r\n" 
				+ "\r\n" 
				+ "    public String insertOrUpdate(PageData pd) {\r\n"
				+ "        String sql = \"\";\r\n" 
				+ "        pd.turnEmptyValueToNull();\r\n"
				+ "        if (pd.get(\"" + tableInfoList.get(tableInfoList.size()-1) + "\") == null) {\r\n" 
				+ "            sql = insert(pd);\r\n" 
				+ "        } else {\n" 
				+ "            sql = update(pd);\n" 
				+ "        }\n" 
				+ "        return sql;\r\n" 
				+ "    }\r\n" 
				+ "\r\n" 
				+ "    public String insert(PageData pd) {\r\n" 
				+ "        String sql = \"\";\n"
				+ "        StringBuilder sqlBuilder = new StringBuilder();\r\n"
				+ "        sqlBuilder.append(\"insert into "+ TABLE_NAME +" (\");\r\n"
				+ "        sqlBuilder.append(column);\r\n"
				+ "        sqlBuilder.append(\") values (\");\r\n" ;
				for (int i = 1; i < tableInfoList.size() - 1; i++) {
					if( i != tableInfoList.size() - 2) {
						content += "        sqlBuilder.append(\" #{" + tableInfoList.get(i) + "}, \");\r\n";
					} else {
						content += "        sqlBuilder.append(\" #{" + tableInfoList.get(i) + "} \");\r\n";
					}
				}
				content += "        sqlBuilder.append(\")\");\r\n" + 
						"        sql = sqlBuilder.toString();\r\n" + 
						"        return sql;\r\n" + 
						"    }\r\n" 
				+ "\r\n" 
				+ "    public String update(PageData pd) {\r\n" 
				+ "        String sql = \"\";\r\n" 
				+ "        StringBuilder sqlBuilder = new StringBuilder();\r\n" 
				+ "        sqlBuilder.append(\"update "+ TABLE_NAME +" set \");\r\n" ;
				
				for (int i = 1; i < tableInfoList.size() - 1; i++) {
					content += "        if(pd.get(\"" + tableInfoList.get(i) + "\") != null) {\r\n";
					content += "			sqlBuilder.append(\" " + tableInfoList.get(i) + " = #{"+ tableInfoList.get(i) +"},\");\r\n";
					content += "		}\r\n";
				}
				
				content += "        sql = CommonUtil.trimLastDot(sqlBuilder.toString());\r\n" 
						+ "        sql = sql + \" where " + tableInfoList.get(tableInfoList.size()-1) + " =  #{"+ tableInfoList.get(tableInfoList.size()-1) +"}\";\r\n" 
						+ "        return sql;\r\n" 
						+ "    }\r\n" 
				+ "\r\n" 
				+ "    public String updateAll(PageData pd) {\r\n" 
				+ "        String sql = \"\";\r\n" 
				+ "        StringBuilder sqlBuilder = new StringBuilder();\r\n" 
				+ "        sqlBuilder.append(\"update "+ TABLE_NAME +" set \");\r\n" ;
				
				for (int i = 1; i < tableInfoList.size() - 1; i++) {
					content += "		sqlBuilder.append(\" " + tableInfoList.get(i) + " = #{"+ tableInfoList.get(i) +"},\");\r\n";
				}
				content += "        sql = CommonUtil.trimLastDot(sqlBuilder.toString());\r\n" 
						+ "        sql = sql + \" where " + tableInfoList.get(tableInfoList.size()-1) + " = #{"+ tableInfoList.get(tableInfoList.size()-1) +"}\";\r\n" 
						+ "        return sql;\r\n" 
						+ "    }\r\n" 
						+ "\r\n" 
				+ "	public String delete(PageData pd) {\r\n"
				+ "		String sql = \"delete from " + tableName + " where " + tableInfoList.get(tableInfoList.size()-1) + " = #{" + tableInfoList.get(tableInfoList.size()-1) + "}\";\r\n"
				+ "		return sql;\r\n" 
				+ "	}\r\n" 
				+ "\r\n" 
				+ "	public String get(PageData pd) {\r\n"
				+ "		String sql = \"select * from " + tableName + " where " + tableInfoList.get(tableInfoList.size()-1) + " = #{" + tableInfoList.get(tableInfoList.size()-1) + "}\";\r\n" 
				+ "		return sql;\r\n" + "	}\r\n" 
				+ "\r\n"
				+ "	public String getAll(PageData pd) {\r\n" 
				+ "		String sql = \"select * from " + tableName + "\";\r\n" 
				+ "		return sql;\r\n" 
				+ "	}\r\n" 
				+ "\r\n" 
				+ "}\r\n" 
				+ "";
		return content;
	}

	private static List<String> getTableInfoList(String tableName) {
		List<String> tableInfoList = new ArrayList<>();
		String tableInfo = getShowCreateTable(tableName);
		Pattern pattern = Pattern.compile("`\\w+`");		//可加参数 Pattern.CASE_INSENSITIVE 忽略大小写
		Matcher matcher = pattern.matcher(tableInfo);
		// 检查所有的结果
		while (matcher.find()) {
			tableInfoList.add(matcher.group().substring(1, matcher.group().length()-1));
		}
		return tableInfoList;
	}


	/**
	 * mapper.java的内容
	 */
	private static String getMapperContent() {
		String content = "package com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".mapper;\r\n" 
				+ "\r\n"
				+ "import java.util.List;\r\n" 
				+ "\r\n" 
				+ "import org.apache.ibatis.annotations.Mapper;\r\n"
				+ "import org.apache.ibatis.annotations.Param;\r\n"
				+ "import org.apache.ibatis.annotations.InsertProvider;\r\n"
				+ "import org.apache.ibatis.annotations.DeleteProvider;\r\n"
				+ "import org.apache.ibatis.annotations.UpdateProvider;\r\n"
				+ "import org.apache.ibatis.annotations.SelectProvider;\r\n" 
				+ "\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.modules.base.mapper.BaseMapper;\r\n" 
				+ "import com.jvxb.demo.sbDemo.livable.utils.PageData;\r\n" 
				+ "\r\n" 
				+ "@Mapper\r\n"
				+ "public interface " + ENTITY_NAME + "Mapper extends BaseMapper {\r\n" 
				+ "\r\n" 
				+ "	@SelectProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"getTablePageData\")\r\n"
				+ "	List<PageData> getTablePageData(@Param(\"columnName\") String columnName);\r\n" 
				+ "\r\n"
				+ "	@InsertProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"insertOrUpdate\")\r\n"
				+ "	void insertOrUpdate(PageData pd);\r\n" 
				+ "\r\n" 
				+ "	@UpdateProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"updateAll\")\r\n" 
				+ "	void updateAll(PageData pd);\r\n"
				+ "\r\n" 
				+ "	@DeleteProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"delete\")\r\n" 
				+ "	void delete(PageData pd);\r\n" 
				+ "	\r\n"
				+ "	@SelectProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"get\")\r\n"
				+ "	PageData get(PageData pd);\r\n" 
				+ "\r\n" 
				+ "	@SelectProvider(type=" + ENTITY_NAME + "MapperProvider.class, method=\"getAll\")\r\n" 
				+ "	List<PageData> getAll(PageData pd);\r\n"
				+ "\r\n" 
				+ "}\r\n" 
				+ "";
		return content;
	}

	/**
	 * serviceImpl.java的内容
	 */
	private static String getServiceImplContent() {
		String entityNameLower = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1);
		String mapperVarName = entityNameLower + "Mapper";
		String content = "package com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".service.impl;\r\n" + "\r\n"
				+ "import java.util.List;\r\n" + "\r\n"
				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+ "import org.springframework.stereotype.Service;\r\n" + "\r\n"
				+ "import com.alibaba.fastjson.JSONObject;\r\n" + "import com.github.pagehelper.PageInfo;\r\n"
				+ "import com.github.pagehelper.PageHelper;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.utils.LayuiPageUtil;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".mapper." + ENTITY_NAME + "Mapper;\r\n" 
				+ "import com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".service.I" + ENTITY_NAME + "Service;\r\n" 
				+ "import com.jvxb.demo.sbDemo.livable.utils.PageData;\r\n" 
				+ "\r\n"
				+ "@Service\r\n" 
				+ "public class " + ENTITY_NAME + "ServiceImpl implements I" + ENTITY_NAME + "Service {\r\n" 
				+ "\r\n" 
				+ "	@Autowired\r\n" 
				+ "	private " + ENTITY_NAME + "Mapper " + mapperVarName + ";\r\n" 
				+ "	\r\n" 
				+ "	@Override\r\n"
				+ "	public JSONObject getTablePageData(Integer page, Integer limit, PageData pd) {\r\n"
				+ "		// TODO Auto-generated method stub\r\n" 
				+ "		PageHelper.startPage(page, limit);\r\n"
				+ "		String columnName = pd.getString(\"columnName\");\r\n" 
				+ "		List<PageData> list = " + mapperVarName + ".getTablePageData(columnName);\r\n"
				+ "		PageInfo<PageData> pageInfo = new PageInfo<>(list);\r\n"
				+ "		JSONObject pageJson = LayuiPageUtil.getLayuiPage(pageInfo);\r\n" 
				+ "		return pageJson;"
				+ "	}\r\n" 
				+ "\r\n" 
				+ "	@Override\r\n" 
				+ "	public void insertOrUpdate(PageData pd) {\r\n"
				+ "		// TODO Auto-generated method stub\r\n" 
				+ "		" + mapperVarName + ".insertOrUpdate(pd);\r\n"
				+ "	}\r\n" 
				+ "\r\n" 
				+ "	@Override\r\n" 
				+ "	public void delete(PageData pd) {\r\n"
				+ "		// TODO Auto-generated method stub\r\n" 
				+ "		" + mapperVarName + ".delete(pd);\r\n"
				+ "	}\r\n" 
				+ "\r\n" 
				+ "	@Override\r\n" 
				+ "	public PageData get(PageData pd) {\r\n" 
				+ "		return " + mapperVarName + ".get(pd);\r\n" 
				+ "	}\r\n" + "\r\n" 
				+ "	@Override\r\n"
				+ "	public void updateAll(PageData pd) {\r\n" 
				+ "		// TODO Auto-generated method stub\r\n"
				+ "		" + mapperVarName + ".updateAll(pd);\r\n" 
				+ "	}\r\n" 
				+ "\r\n" 
				+ "	@Override\r\n"
				+ "	public List<PageData> getAll(PageData pd) {\r\n" 
				+ "		// TODO Auto-generated method stub\r\n"
				+ "		return " + mapperVarName + ".getAll(pd);\r\n" 
				+ "	}\r\n" 
				+ "\r\n" 
				+ "}\r\n" 
				+ "";
		return content;
	}

	/**
	 * service.java的内容
	 */
	private static String getServiceContent() {
		String content = "package com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".service;\r\n" + "\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.modules.base.service.IBaseService;\r\n" + "\r\n"
				+ "public interface I" + ENTITY_NAME + "Service extends IBaseService{\r\n" + "\r\n" + "}\r\n" + "";
		return content;
	}

	/**
	 * controller.java的内容
	 */
	private static String getControllerContent() {
		String entityNameLower = ENTITY_NAME.substring(0, 1).toLowerCase() + ENTITY_NAME.substring(1);
		String serviceVarName = entityNameLower + "Service";
		String content = "package com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".controller;\r\n" + "\r\n"
				+ "import java.util.Date;\r\n"
				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n" + "\r\n"
				+ "import org.springframework.stereotype.Controller;\r\n"
				+ "import org.springframework.ui.ModelMap;\r\n"
				+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.ResponseBody;\r\n" + "\r\n"
				+ "import com.alibaba.fastjson.JSONObject;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.modules." + MODULE_NAME + ".service.I" + ENTITY_NAME
				+ "Service;\r\n" + "import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.utils.PageData;\r\n"
				+ "import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;\r\n" + "\r\n" + "@Controller\r\n"
				+ "public class " + ENTITY_NAME + "Controller extends BaseController {\r\n" + "\r\n"
				+ "	@Autowired\r\n" + "	I" + ENTITY_NAME + "Service " + serviceVarName + ";\r\n" + "\r\n" + "	/**\r\n"
				+ "	 * 前往列表页\r\n" + "	 */\r\n" + "	@RequestMapping(\"/admin/" + entityNameLower + "\")\r\n"
				+ "	public Object " + entityNameLower + "(ModelMap model) {\r\n" + "		return \"" + MODULE_NAME
				+ "/" + entityNameLower + "_list\";\r\n" + "	}\r\n" + "\r\n" + "	/**\r\n" + "	 * 获取列表页数据\r\n"
				+ "	 */\r\n" + "	@RequestMapping(\"/admin/" + entityNameLower + "/pageData\")\r\n"
				+ "	@ResponseBody\r\n" + "	public Object " + entityNameLower
				+ "PageData(Integer page, Integer limit) {\r\n" + "		PageData pd = this.getPageData();\r\n"
				+ "		JSONObject pageData = " + serviceVarName + ".getTablePageData(page, limit, pd);\r\n"
				+ "		return pageData;\r\n" + "	}\r\n" + "\r\n" + "	/**\r\n" + "	 * 前往表单页\r\n" + "	 */\r\n"
				+ "	@RequestMapping(\"/admin/" + entityNameLower + "/form\")\r\n" + "	public Object "
				+ entityNameLower + "Form(ModelMap model) {\r\n" + "		PageData pd = this.getPageData();\r\n"
				+ "		model.addAttribute(\"mode\", pd.get(\"mode\"));\r\n"
				+ "		model.addAttribute(\"id\", pd.get(\"id\"));\r\n" + "		return \"" + MODULE_NAME + "/"
				+ entityNameLower + "_form\";\r\n" + "	}\r\n" + "\r\n" + "	/**\r\n" + "	 * 保存数据\r\n" + "	 */\r\n"
				+ "	@RequestMapping(\"/admin/" + entityNameLower + "/save\")\r\n" + "	@ResponseBody\r\n"
				+ "	public Object "+ entityNameLower +"Save() {\r\n" 
				+ "		PageData pd = this.getPageData();\r\n" 
				+ "		Date now = new Date();\r\n" + 
				"		if (CommonUtil.isNullOrEmpty(pd.get(\"id\"))){\r\n" + 
				"			pd.put(\"create_by\", getSessionUser().getId());\r\n" + 
				"			pd.put(\"create_time\", now);\r\n" + 
				"		} else {\r\n" + 
				"			pd.put(\"update_by\", getSessionUser().getId());\r\n" + 
				"			pd.put(\"update_time\", now);\r\n" + 
				"		}\r\n" 
				+ "		"+ serviceVarName + ".insertOrUpdate(pd);\r\n" 
				+ "		return ResponseMessage.ok();\r\n" 
				+ "	}\r\n"
				+ "	\r\n" 
				+ "	/**\r\n"
				+ "	 * 查看数据\r\n" 
				+ "	 */\r\n" 
				+ "	@RequestMapping(\"/admin/"
				+ entityNameLower + "/get/{id}\")\r\n" + "	@ResponseBody\r\n" + "	public Object " + entityNameLower
				+ "Get(@PathVariable Integer id) {\r\n" + "		if (CommonUtil.notNum(id)) {\r\n"
				+ "			return ResponseMessage.error(\"无效数据\");\r\n" + "		}\r\n" + "		// 获取相关数据\r\n"
				+ "		PageData pd = new PageData();\r\n" + "		pd.put(\"id\", id);\r\n"
				+ "		PageData data = " + serviceVarName + ".get(pd);\r\n"
				+ "		return ResponseMessage.ok(data);\r\n" + "	}\r\n" + "	\r\n" + "	/**\r\n" + "	 * 删除数据\r\n"
				+ "	 */\r\n" + "	@RequestMapping(\"/admin/" + entityNameLower + "/delete/{id}\")\r\n"
				+ "	@ResponseBody\r\n" + "	public Object " + entityNameLower + "Delete(@PathVariable Integer id) {\r\n"
				+ "		if(CommonUtil.notNum(id)) {\r\n" + "			return ResponseMessage.error(\"无效数据\");\r\n"
				+ "		}\r\n" + "		PageData pd = new PageData(\"id\", id);\r\n" + "		" + serviceVarName
				+ ".delete(pd);\r\n" + "		return ResponseMessage.ok();\r\n" + "	}\r\n" + "	\r\n" + "\r\n"
				+ "}\r\n" + "";
		return content;
	}


}
