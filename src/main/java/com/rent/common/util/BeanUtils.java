package com.rent.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaoyao
 * @version V1.0 自定义
 * @ClassName: BeanUtils
 * @date 2020-6-12 下午14:40:50
 */

@Slf4j
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * 非同类型转换器，默认没有，即不同类型不相互转换，不相互拷贝
     */
    private static List<DiffTypeConvertor> convertorsList = new LinkedList<DiffTypeConvertor>();

    /**
     * @param diffTypeConvertor
     */
    public final static void addConvertor(DiffTypeConvertor diffTypeConvertor) {
        convertorsList.add(diffTypeConvertor);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        return convertBean(bean, true);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map<br>
     * 如果选择转换空属性，null 属性的值将变为空字符串""
     *
     * @param bean          要转化的JavaBean 对象
     * @param isNullConvert 是否转换空属性 ,true 会将null 属性put（FieldName,""）
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBean(Object bean, boolean isNullConvert)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {

        Set<Class<?>> nullSet = new HashSet<Class<?>>();
        return convertBean(bean, nullSet, isNullConvert);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean            要转化的JavaBean 对象
     * @param exclusiveRegexp 不转换例外的属性名的正则表达匹配式
     * @param isNullConvert   是否转换空属性
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBean(Object bean, String exclusiveRegexp, boolean isNullConvert)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (bean == null) {
            return null;
        }
        if (bean instanceof Map<?, ?>) {
            return (Map<String, Object>) bean;
        }
        Pattern pattern = Pattern.compile(exclusiveRegexp);

        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Matcher matcher = pattern.matcher(propertyName);
            if (!matcher.matches()) {// 不匹配
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        if (isNullConvert) {// 如果转换空属性
                            returnMap.put(propertyName, "");
                        }
                    }
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map（默认转换空属性）
     *
     * @param bean
     * @param startWith 筛选需要转换的属性的属性名的前缀（并转换后去掉该前缀）
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> convertBeanBy(Object bean, String startWith)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        return convertBeanBy(bean, "^" + startWith + ".*", true, startWith);
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean                   要转化的JavaBean 对象
     * @param regexp                 筛选需要转换的属性的属性名的正则表达匹配式
     * @param isNullConvert          是否转换空属性
     * @param replaceWithBlankRegexp 在转换过程中，将属性名正则替换为空字符 (只替换首次匹配结果)
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBeanBy(Object bean, String regexp, boolean isNullConvert,
                                                    String replaceWithBlankRegexp)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (bean == null) {
            return null;
        }
        if (bean instanceof Map<?, ?>) {
            return (Map<String, Object>) bean;
        }
        Pattern pattern = Pattern.compile(regexp);

        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Matcher matcher = pattern.matcher(propertyName);
            if (matcher.matches()) {// 匹配
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (!Objects.equals(null, replaceWithBlankRegexp) && !replaceWithBlankRegexp.equals(
                            "")) {//转换工程修改属性key值
                        propertyName = propertyName.replaceFirst(replaceWithBlankRegexp, "");
                    }
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        if (isNullConvert) {// 如果转换空属性
                            returnMap.put(propertyName, "");
                        }
                    }
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean          要转化的JavaBean 对象
     * @param isNullConvert 是否转换空属性
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBean(Object bean, Set<Class<?>> exclusiveFieldtypesSet,
                                                  boolean isNullConvert) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (bean instanceof Map<?, ?>) {
            return (Map<String, Object>) bean;
        }
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class<?> propertyType = descriptor.getPropertyType();

            if (!propertyName.equals("class")) {
                if (!exclusiveFieldtypesSet.contains(propertyType)) {// 如果该属性类型不包含在排除类型中则转换
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        if (isNullConvert) {
                            returnMap.put(propertyName, "");
                        }
                    }
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static <T> T convertMap(Class<T> type, Map<String, Object> map)
            throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        T obj = type.newInstance(); // 创建 JavaBean 对象
        convertMap(obj, map);
        return obj;
    }

    /**
     * 将map中的元素 按键复制到bean对应的属性
     *
     * @param destObj 目标bean
     * @param map     源map
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException void
     * @Title convertMap
     * @Description TODO
     */
    public static void convertMap(Object destObj, Map<String, Object> map)
            throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(destObj.getClass()); // 获取类属性

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class<?> propertyType = descriptor.getPropertyType();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = null;
                try {
                    value = map.get(propertyName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (value != null && value.getClass() == propertyType) {//类型不同不处理
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(destObj, args);
                } else {//如果类型不同，看看有没有配置不同类型的转换器
                    Object temvalue = descriptor.getReadMethod().invoke(destObj, new Object[0]);
                    for (DiffTypeConvertor convertor : convertorsList) {
                        if (convertor.ifMatch(propertyName, propertyType)) {//如果多个转换器满足，执行到最后一个
                            try {
                                temvalue = convertor.convert(propertyType, value);
                            } catch (Exception e) {
                                throw new RuntimeException("propertyType converter error", e);
                            }

                        }
                    }
                    Object[] args = new Object[1];
                    args[0] = temvalue;
                    descriptor.getWriteMethod().invoke(destObj, args);
                }
            }
        }
    }

    /**
     * 会将数据源bean中的所有可以拷贝到目标bean中的属性拷贝过去（即属性名和属性类型一致）</br>
     * <p style="color:red;">
     * 确切的说对于不是基本类型的属性这里不是拷贝，而是两个bean共用这些属性（属性会引用到同一个实例），
     * 所以当你改变源bean中属性的值是会影响到目标bean的
     * ，不过你可以不用担心源bean的回收，会影响到目标bean的属性，因为这些属性还有新的主人，所以如果你使用这个方法仅仅是因为想丢掉一堆的get
     * set 逻辑，那么你可以 在执行完这个方法的时候，将源bean置空，防止对它的误操作而出现莫名的错误
     *
     * @param dest 目标bean
     * @param orig 数据源bean
     * @return void
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws IntrospectionException
     * @Title copyProperties
     * @Description TODO
     */
    public static void copyProperties(Object dest, Object orig)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        copyProperties(dest, orig, true);

    }

    /**
     * 将源bean中的非空（null）可拷贝字段全部浅拷贝到目标bean中</br></br>
     * 会将数据源bean中的所有可以拷贝到目标bean中的属性拷贝过去（即属性名和属性类型一致）</br>
     * <p style="color:red;">
     * 确切的说对于不是基本类型的属性这里不是拷贝，而是两个bean共用这些属性（属性会引用到同一个实例），
     * 所以当你改变源bean中属性的值是会影响到目标bean的
     * ，不过你可以不用担心源bean的回收，会影响到目标bean的属性，因为这些属性还有新的主人，所以如果你使用这个方法仅仅是因为想丢掉一堆的get
     * set 逻辑，那么你可以 在执行完这个方法的时候，将源bean置空，防止对它的误操作而出现莫名的错误
     *
     * @param dest            目标bean
     * @param orig            数据源bean
     * @param ifcopyNullValue 是否拷贝空值(false的情况下,数据源bean中的null值属性即使满足拷贝要求也不会被拷贝到目标bean中)
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException void
     * @Title copyProperties
     * @Description TODO
     */
    public static void copyProperties(Object dest, Object orig, boolean ifcopyNullValue)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bo specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bo specified");
        }

        try {
            BeanInfo destbeanInfo = Introspector.getBeanInfo(dest.getClass());
            PropertyDescriptor[] destpropertyDescriptors = destbeanInfo.getPropertyDescriptors();
            for (int i = 0; i < destpropertyDescriptors.length; i++) {
                PropertyDescriptor destdescriptor = destpropertyDescriptors[i];
                String destpropertyName = destdescriptor.getName();
                if (!destpropertyName.equals("class")) {
                    Map<String, Integer> indexMap = getPropertyIndexByDesc(orig, destdescriptor);
                    int propertyIndex = indexMap.get("index");
                    if (indexMap.get("flag") == 1 || indexMap.get("flag") == -2) {// 在源bean中找到了这个属性 ，或者找到同名不同类型属性
                        PropertyDescriptor origdescriptor = Introspector.getBeanInfo(orig.getClass())
                                .getPropertyDescriptors()[propertyIndex];
                        Method getMethod = origdescriptor.getReadMethod();
                        if (getMethod != null) {
                            Object value = getMethod.invoke(orig, new Object[0]);
                            if (indexMap.get("flag") == -2) {//如果类型不同，看看有没有配置类型转换器
                                Object temvalue = destdescriptor.getReadMethod().invoke(dest, new Object[0]);
                                for (DiffTypeConvertor convertor : convertorsList) {
                                    if (convertor.ifMatch(destpropertyName,
                                            destdescriptor.getPropertyType())) {//如果多个转换器满足，执行到最后一个
                                        try {
                                            temvalue = convertor.convert(destdescriptor.getPropertyType(), value);
                                        } catch (Exception e) {
                                            throw new RuntimeException("propertyType converter error", e);
                                        }
                                    }
                                }
                                value = temvalue;
                            }
                            Object[] args = new Object[1];
                            args[0] = value;
                            Method setMethod = destdescriptor.getWriteMethod();
                            if (setMethod != null) {
                                if (ifcopyNullValue == false) {
                                    if (value != null) {
                                        setMethod.invoke(dest, args);
                                    }
                                } else {
                                    setMethod.invoke(dest, args);
                                }
                            } else {
                                throw new RuntimeException(
                                        "can't find the property " + destpropertyName + "'s 'setmethod'  in " + dest
                                                .getClass());
                            }
                        } else {
                            throw new RuntimeException(
                                    "can't find the property " + destpropertyName + "'s 'getmethod'  in " + dest
                                            .getClass());
                        }
                    } //-1 不处理
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将一个集合按照其内部一部分属性值作为字典整理为字典集合
     * <p style="color:red">如果根据规则有多个对象可以得到相同的字典，那么这个字典只存储第一个对象
     *
     * @param collection
     * @param keyNames   对象作为字典用的属性属性名
     * @return
     */
    public static <T> Map<String, T> buildDictMap(Collection<T> collection, String... keyNames) {
        Map<String, T> map = new HashMap<String, T>();
        int i = 0;
        for (T item : collection) {
            Map<String, Object> temMap = null;
            try {
                temMap = convertBean(item);
            } catch (Exception e) {
                throw new IllegalArgumentException("无法提取集合中对象属性，collection[" + i + "]=" + item.toString(), e);
            }
            String key = "";
            for (String string : keyNames) {
                if (StringUtils.isNotBlank(string)) {
                    key += temMap.get(string);
                }
            }
            if (map.get(key) != null) {//如果有多个对象可以根据规则得到相同的字典，那么只存储第一个对象
                map.put(key, item);
            }
            i++;
        }
        return map;
    }

    /**
     * 如果找到同名同类型属性map.flag==1 map.index=数组下标
     * 同名map.flag==-2 map.index=数组下标
     * 没找到map.flag==-1 map.index=-1
     *
     * @param bean
     * @param descriptor
     * @return
     * @throws IntrospectionException
     */
    private static Map<String, Integer> getPropertyIndexByDesc(Object bean, PropertyDescriptor descriptor)
            throws IntrospectionException {
        Map<String, Integer> dataMap = new HashMap<String, Integer>(2);
        dataMap.put("flag", -1);
        int index = -1;
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor temdescriptor = propertyDescriptors[i];
            String tempropertyName = temdescriptor.getName();
            Class<?> temppropertyType = temdescriptor.getPropertyType();
            if (!tempropertyName.equals("class") && tempropertyName.equals(descriptor.getName())) {
                index = i;
                if (temppropertyType == descriptor.getPropertyType()) {
                    dataMap.put("flag", 1);
                } else {
                    dataMap.put("flag", -2);
                }
                break;//因为属性名不可能重复，所以一旦匹配，后面不可能再次匹配到
            }
        }
        dataMap.put("index", index);
        return dataMap;
    }


    /**
     * 金额字段为null时设置成zreo
     *
     * @param target
     */
    public static void moneyNull2Zreo(Object target) {
        if (target == null) {
            return;
        }
        try {
            BeanInfo sourceBeanBeanInfo = Introspector.getBeanInfo(target.getClass());
            //获取所有的参数
            PropertyDescriptor[] sourceProArr = sourceBeanBeanInfo.getPropertyDescriptors();
            //获取所有的参数
            PropertyDescriptor[] sources = sourceBeanBeanInfo.getPropertyDescriptors();
            Map<String, Object> valueMap = new LinkedHashMap<String, Object>();
            for (PropertyDescriptor pro : sourceProArr) {
                String name = pro.getName();
                Method readMethod = pro.getReadMethod();
                if (logger.isDebugEnabled() && readMethod == null) {
                    logger.debug("属性:" + name + "无读函数");
                    continue;
                }
                Object invoke = readMethod.invoke(target, null);
                valueMap.put(name, invoke);
            }
            for (PropertyDescriptor pro : sources) {
                String name = pro.getName();
                Object value = valueMap.get(pro.getName());
                Class<?> propertyType = pro.getPropertyType();
                if (propertyType != BigDecimal.class) {
                    continue;
                }
                if (value != null) {
                    continue;
                }
                Method method = pro.getWriteMethod();
                if (method != null) {
                    Class<?>[] parType = method.getParameterTypes();
                    if (parType != null) {
                        if (parType.length > 1) {
                            if (logger.isDebugEnabled()) {
                                logger.debug(
                                        "properties[{}]Parameter of WriteMethod expected 1 Parameter,but actually is[{}] ",
                                        name, parType.length);
                                continue;
                            }
                        }
                        method.invoke(target, BigDecimal.ZERO);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("属性初始化异常", e);
        }
    }

    /**
     * bean的发属性复制，只复制类型相同的属性。
     *
     * @param sourceBean 将sourceBean中的属性复制到targetBean
     * @param targetBean
     * @return
     * @throws Exception
     */
    public static void beanCopyWithoutNull(Object targetBean, Object sourceBean, String... ignores) {
        try {
            BeanInfo sourceBeanBeanInfo = Introspector.getBeanInfo(sourceBean.getClass());
            //获取所有的参数
            PropertyDescriptor[] sourceProArr = sourceBeanBeanInfo.getPropertyDescriptors();
            Map<String, Object> valueMap = new LinkedHashMap<String, Object>();
            for (PropertyDescriptor pro : sourceProArr) {
                String name = pro.getName();
                if (isIgnore(name, ignores)) {
                    continue;
                }
                Method readMethod = pro.getReadMethod();
                if (logger.isDebugEnabled() && readMethod == null) {
                    logger.debug("属性:" + name + "无读函数");
                    continue;
                }
                Object invoke = readMethod.invoke(sourceBean, null);
                valueMap.put(name, invoke);
            }

            BeanInfo targetBeanInfo = Introspector.getBeanInfo(targetBean.getClass());
            //获取所有的参数
            PropertyDescriptor[] proArr = targetBeanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pro : proArr) {
                String name = pro.getName();
                if (isIgnore(name, ignores)) {
                    continue;
                }
                Object value = valueMap.get(pro.getName());
                if (value == null || pro.getName().equalsIgnoreCase("class")) {
                    continue;
                }
                Method method = pro.getWriteMethod();
                if (method != null) {
                    Class<?>[] parType = method.getParameterTypes();
                    if (parType != null) {
                        if (parType.length > 1) {
                            if (logger.isDebugEnabled()) {
                                logger.debug(
                                        "properties[{}]Parameter of WriteMethod expected 1 Parameter,but actually is[{}] ",
                                        name, parType.length);
                                continue;
                            }
                        }
                        for (Class cc : parType) {
                            //如果属性是数组，直接将参数值[数组类型]赋值给此参数.
                            boolean array = cc.isArray();
                            if (array) {
                                method.invoke(targetBean, new Object[]{value});
                            } else {
                                method.invoke(targetBean, value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    private static boolean isIgnore(String name, String[] ignores) {
        if (StringUtils.isBlank(name) || ignores == null || ignores.length < 1) {
            return false;
        }
        for (String ignore : ignores) {
            if (name.equals(ignore)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjectsV1(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                try {
                    bean =  transferMap2Bean(clazz,map);
                } catch (Exception e) {
                    log.info("解析异常");
                }
                list.add(bean);
            }
        }
        return list;
    }


    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
//        BeanMap beanMap = BeanMap.create(bean);
//        beanMap.putAll(map);
        try {
            //添加这一行代码，解决date 类型为空  报错
            ConvertUtils.register(new DateConverter(null), Date.class);
            //这一行，可以解决 integer值为空时不自动赋值为0；
            ConvertUtils.register(new IntegerConverter(null), Integer.class);
            ConvertUtils.register(new LongConverter(null),Long.class);
            BeanUtils.populate(bean, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
        System.out.println(bean.toString());
        return bean;
    }


    public static  <T>   T transferMap2Bean(Class<T> clazz, Map<String, Object> map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //获取类属性
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        T obj = clazz.newInstance();
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;
                Field privateField = getPrivateField(propertyName, clazz);
                if (privateField == null) {
                } privateField.setAccessible(true);
                String type = privateField.getGenericType().toString();
                if ("class java.lang.String".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, value);
                    }

                } else if ("class java.lang.Boolean".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, Boolean.parseBoolean(String.valueOf(value)));
                    }
                } else if ("class java.lang.Long".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, Long.parseLong(String.valueOf(value)));
                    }

                } else if ("class java.lang.Integer".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, Integer.parseInt(String.valueOf(value)));
                    }

                } else if ("class java.lang.Double".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, Double.parseDouble(String.valueOf(value)));
                    }

                } else if ("class java.lang.Float".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, Float.parseFloat(String.valueOf(value)));
                    }

                } else if ("class java.math.BigDecimal".equals(type)) {
                    if (value == null) {
                        privateField.set(obj, null);
                    } else {
                        privateField.set(obj, new BigDecimal(String.valueOf(value)));
                    }

                }//可继续追加类型
            }
        }
        return obj;
    }


    /*拿到反射父类私有属性*/
    private static Field getPrivateField(String name, Class cls) {
        Field declaredField = null;
        try {
            declaredField = cls.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {

            if (cls.getSuperclass() == null) {
                return declaredField;
            } else {
                declaredField = getPrivateField(name, cls.getSuperclass());
            }
        }  return declaredField;
    }







    /**
     * 非同类型，转化器
     *
     * @author xuwh10815
     * @version $Id: BeanUtils.java, v 1.0 2017年4月13日 下午3:12:52 xuwh10815 Exp $
     */
    public interface DiffTypeConvertor {

        /**
         * 是否匹配，经过该转换器
         *
         * @param propertyName
         * @param propertyType
         * @return
         */
        public boolean ifMatch(final String propertyName, final Class<?> propertyType);

        /**
         * 转换器
         *
         * @param propertyType 需要转换的新属性类型
         * @param value        原始属性对象
         * @return 新类型属性对象
         * @throws NoSuchMethodException
         * @throws SecurityException
         */
        public <T> T convert(final Class<T> propertyType, final Object value) throws Exception;
    }
}
