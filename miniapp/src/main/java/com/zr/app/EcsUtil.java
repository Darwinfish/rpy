package com.zr.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenwf10476
 * @time 2014-9-15 下午2:02:51
 */
public class EcsUtil {
	private static final Log logger = LogFactory.getLog(EcsUtil.class);
	public static String formatDate(Date date,String pattern){
		SimpleDateFormat  dateFormat = new SimpleDateFormat (pattern);
		return dateFormat.format(date);
	}
	public static String today(){
		Date date = new Date();
		return formatDate(date,"yyyyMMdd");
	}
	public static String currentTime(){
		Date date = new Date();
		return formatDate(date,"yyyyMMddHHmmss");
	}
	
	public static void responseText(String content,OutputStream os,String charset) throws UnsupportedEncodingException, IOException{
		os.write(content.getBytes(charset));
		os.flush();
	}
	
	
	public static <T>  String bean2Json(T bean) throws IOException{
		StringWriter sw = new StringWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(sw);
		jsonGenerator.writeObject(bean);
		jsonGenerator.flush();
		jsonGenerator.close();
		return sw.toString();
		
	}
	public static String Object2Json(Object o) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(o);
	}
	
	/**
	 * json字符串转为java对象
	 * @param json
	 * @param t
	 * @return
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Bean(String json,Class<T> t) throws JsonMappingException, IOException{
		T bean=null ;
		ObjectMapper objectMapper = new ObjectMapper();
		bean = objectMapper.readValue(json, t);
		return bean;
	}
	

    
    public static String formatBalance(double balance,String format){
    	DecimalFormat df = new DecimalFormat(format);
    	return df.format(balance);
    }
    
    /**
     * 获取byte数组，字符集不存在则使用GBK
     * @param str
     * @param charset
     * @return
     */
    public static byte[] getBytes(String str,String charset){
    	try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			try {
				return str.getBytes("GBK");
			} catch (UnsupportedEncodingException e1) {
			}
		}
		return null;
    }
    
    /**
	 * 读取支付宝请求数据
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 *             fengming20131024
	 */
	public static String readLine(ServletInputStream in, String charset)
			throws IOException {
		byte[] buf = new byte[8 * 1024];
		StringBuffer sbuf = new StringBuffer();
		int result;
		do {
			result = in.read(buf, 0, buf.length); // does +=
			if (result != -1) {
				sbuf.append(new String(buf, 0, result, charset));
			}
		} while (result != -1); // loop only if the buffer was filled

		if (sbuf.length() == 0) {
			return ""; // nothing read, must be at the end of stream
		}
		return sbuf.toString();
	}
	
	public static String nvl(Object v){
		if (v == null){
			return "";
		}else {
			return (String)v;
		}
	}

	
	public static boolean isEmpty(String value){
		return value==null||value.trim().length()==0;
	}
	public static boolean isNotEmpty(String value){
		return !isEmpty(value);
	}
	
	/**
	 * @param o
	 * @param extension
	 */
	public static void dealExtension(Object o,String extension){
		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(o);
		String key;
		String values;
		Map<String,String> exMap = new HashMap<String,String>();
		exMap.put("userId", "bankidno");
		exMap.put("investorName", "nameinbank#customname");
		exMap.put("certType", "identitytype");
		exMap.put("certificateNo", "identityno");
		for (String exts:extension.split("@@")){
			if (exts.contains("=")){
				String[] kv = exts.split("=");
				key = exMap.get(kv[0]);
				if (key != null){
					values = kv[1];
					for (String value:values.split("#")){
						if (beanWrapper.isWritableProperty(key)){
							beanWrapper.setPropertyValue(key, value);
						}
					}
				}
			}
		}
	}
	
	
	public static Map<String,String>  getParaMap(HttpServletRequest request){
		return (Map<String, String>) request.getAttribute("paraMap");
	}
	
	public static boolean isTime(String time){
		return time!=null&&time.length()==14;
	}
	

	
	/**
	 * @param extension
	 */
	public static void orderDealExtension(Map<String,Object> map,String extension){
		String key;
		String values;
		Map<String,String> exMap = new HashMap<String,String>();
		exMap.put("userId", "partnerUserId");
		exMap.put("investorName", "custName");
		exMap.put("certType", "identifyType");
		exMap.put("certificateNo", "identifyNo");
		for (String exts:extension.split("@@")){
			if (exts.contains("=")){
				String[] kv = exts.split("=");
				key = exMap.get(kv[0]);
				if (key != null){
					values = kv[1];
					for (String value:values.split("#")){
						map.put(key, value);
					}
				}
			}
		}
	}

	
	/**
	 * 判断参数类型是否为某个类型
	 * @param parameter 参数
	 * @param classes 类型
	 * @return 是否符合
	 */
	public static boolean isAssignable(MethodParameter parameter,Class<?>...classes ){
		for (Class<?> cls:classes){
			if (ClassUtils.isAssignable(cls, parameter.getParameterType())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 去掉百分號，即數字*100，保留兩位小數
	 * @param num
     * @return
     */
	public static String abandonPercent(String num){
		DecimalFormat numFormat = new DecimalFormat("0.00");
		return numFormat.format(new BigDecimal(num).multiply(new BigDecimal(100)));
	}
}
