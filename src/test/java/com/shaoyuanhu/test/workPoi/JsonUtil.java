package com.shaoyuanhu.test.workPoi;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by chenww3 on 2015/6/14.
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper OM = new ObjectMapper();
    static{
    	// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性  
    	OM.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);  
    	OM.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    
    public static JavaType assignList(Class<? extends Collection> collection, Class<? extends Object> object) {
        return OM.getTypeFactory().constructParametricType(collection, object);
    }


    public static <T> ArrayList<T> readValuesAsArrayList(String key, Class<T> object) {
        ArrayList<T> list = null;
        try {
            list = OM.readValue(key, assignList(ArrayList.class, object));
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }


    public static String toJson(Object obj){
    	if(obj == null){
    		return "";
    	}
        try {
            return OM.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
    * @Description: 重载个性化时间
    * @author yuzj7@lenovo.com  
    * @date 2015年8月9日 下午4:19:18
    * @param sdf
    * @param json
    * @param clazz
    * @return
     */
    public static <T> T fromJson(SimpleDateFormat sdf,String json, Class<T> clazz){
        try {
        	OM.setDateFormat(sdf);
            T t =  OM.readValue(json, clazz);
            OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return t;
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return OM.readValue(json, clazz);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
