package com.tongu.search.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tongu.search.exception.RbacException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Bean操作工具类
 */
public class BeanUtil {

	/**
	 * 日期转换
	 */
	public static class DateConverter implements Converter {
		@Override
		public Object convert(Class arg0, Object arg1) {
			// String p = (String) arg1;
			if (arg1 instanceof String) {
				String p = (String) arg1;
				if (p == null || p.trim().length() == 0) {
					return null;
				}
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return df.parse(p.trim());
				} catch (Exception e) {
					try {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						return df.parse(p.trim());
					} catch (ParseException ex) {
						return null;
					}
				}
			}
			/**
			 * long 转换 Date java.util.Date
			 */
			else if (arg1 instanceof Long) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis((Long) arg1);
				return c.getTime();
			}
			return null;
		}
	}

	/**
	 * Map转为Class
	 * @param map
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T toBean(Map<String, Object> map, Class<T> type){
		T obj;
		try {
			obj = type.newInstance();
			ConvertUtils.register(new DateConverter(), Date.class);
			BeanUtilsBean.getInstance().populate(obj, map);
		} catch (InstantiationException e) {
			throw new RbacException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new RbacException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RbacException(e.getMessage());
		}
		return obj;
	}

	/**
	 * Class转为map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj) {
		try {
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(obj.getClass());
			Map<String, Object> map = Maps.newHashMap();
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Object value = propertyDescriptor.getReadMethod().invoke(obj);
				if(value != null) {
					map.put(propertyDescriptor.getName(), value);
				}
			}
			return map;
		} catch (IllegalAccessException e) {
			throw new RbacException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RbacException(e.getMessage());
		}
	}

	/**
	 * 拷贝属性
	 * @param src
	 * @param dest
	 */
	public static void copyProperties(Object src, Object dest){
		try {
			ConvertUtils.register(new DateConverter(), Date.class);
			BeanUtilsBean.getInstance().copyProperties(dest, src);
			
		} catch (IllegalAccessException e) {
			throw new RbacException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RbacException(e.getMessage());
		}
	}

	/**
	 * 获取属性值为空的属性值名称
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) {
	        	emptyNames.add(pd.getName());
			}
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

	/**
	 * 拷贝属性（忽略空值属性）
	 * @param src
	 * @param target
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	/**
	 * 拷贝属性
	 * @param src
	 * @param dest
	 * @param properties 仅拷贝指定的属性
	 */
	public static void copyProperties(Object src, Object dest, String... properties) {
		List<String> propertiesList = properties != null ? Arrays.asList(properties) : null;
		Set<String> ignoreSet = Sets.newHashSet();
		if(!CollectionUtils.isEmpty(propertiesList)) {
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(src.getClass());
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if(!propertiesList.contains(propertyDescriptor.getName())) {
					ignoreSet.add(propertyDescriptor.getName());
				}
			}
		}
		String[] ignoreProperties = new String[ignoreSet.size()];
		BeanUtils.copyProperties(src, dest, ignoreSet.toArray(ignoreProperties));
	}

	/**
	 * 把指定属性由""改为null
	 * @param src
	 * @param trimNullProperties
	 */
	public static void trimNullProperties(Object src, String... trimNullProperties) {
		List<String> trimNullList = trimNullProperties != null ? Arrays.asList(trimNullProperties) : null;
		if(CollectionUtils.isEmpty(trimNullList)) {
			return;
		}
		try {
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(src.getClass());
			for(String property : trimNullList) {
				for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					if(propertyDescriptor.getName().equals(property)) {
						Object value = propertyDescriptor.getReadMethod().invoke(src);
						propertyDescriptor.getWriteMethod().invoke(src, StringUtils.trimToNull((String)value));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给对象某个属性赋值
	 * @param src
	 * @param property
	 * @param value
	 * @param <T>
	 */
	public static <T> void setProperties(Object src, String property, T value) {
		try {
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(src.getClass());
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if(propertyDescriptor.getName().equals(property)) {
					propertyDescriptor.getWriteMethod().invoke(src, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
