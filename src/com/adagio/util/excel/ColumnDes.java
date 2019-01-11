package com.adagio.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * ClassName: ColumnDes 
 * @Description: 注解标记excel中的列名与格式
 * @author Vincentzheng
 * @date 2018年3月26日  下午8:38:55
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ColumnDes {
	/**列名*/
	String columnName();
	
	/**列为表达式时的 公式**/
	String columnFormula() default "";
	
	/**表达式内涉及到的参数**/
	String[] formulaParm() default {};
}
