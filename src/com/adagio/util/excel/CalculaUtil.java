//package com.adagio.util.excel;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//
//
///**
// * 
// * ClassName: ExcelCalculaUtil 
// * @Description: excel公式计算通用类，配合ColumbDes注解使用
// * @author Vincentzheng
// * @date 2018年3月27日  上午10:32:32
// *
// */
//public class CalculaUtil {
//
//	
//	/**
//	 * 
//	 * 计算对象中的参数值
//	 * @param obj 需要计算的 ColumbDes注解字段对象
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException   
//	 * @author Vincentzheng
//	 * @date 2018年3月27日 上午10:38:57
//	 */
//	public static void calculate(Object obj) throws IllegalArgumentException, IllegalAccessException{
//		Class<?> clz = obj.getClass();
//		Field[] fields = clz.getDeclaredFields();
//		EvaluationContext ctx = new StandardEvaluationContext();
//		ExpressionParser parser=new SpelExpressionParser();
//		
//		/***表达式字段集合*/
//		List<Field> flist = new LinkedList<Field>();
//		HashMap<String, Field> fMap = new HashMap<String, Field>();
//		/**初始化操作**/
//		for (Field field : fields) {
//			field.setAccessible(true);
//			ColumnDes columnDes = field.getAnnotation(ColumnDes.class);
//			if(columnDes !=null){
//				fMap.put(columnDes.columnName(), field);
//				if(columnDes.columnFormula().length()>0){
//					/**清空字段为表达式的值，以重新计算*/
//					field.set(obj, null);
//					flist.add(field);
//				}else {
//					/**非表达式的字段赋值 ctx*/
//					 ctx.setVariable(columnDes.columnName(), field.get(obj));
//				}
//			}
//		}
//	
//		/**表达式的字段赋值 ctx**/
//		for(Field field : flist){
//			calFormula(obj,field,fMap,ctx,parser);
//		}
//	}
//	
//
//	
//	/***
//	 * 
//	 * 递归调用计算表达式值
//	 * @param obj 要操作的对象
//	 * @param field 要赋值的字段
//	 * @param fMap 列名与fieldmap
//	 * @param ctx  表达式语言上下文
//	 * @param parser  表达式解析器 
//	 * @author Vincentzheng
//	 * @throws IllegalAccessException 
//	 * @throws IllegalArgumentException 
//	 * @date 2018年3月27日 上午9:41:48
//	 */
//	private static Object calFormula(Object obj,Field field,HashMap<String, Field> fMap,
//			EvaluationContext ctx,ExpressionParser parser) throws IllegalArgumentException, IllegalAccessException{
//		Object fvalue = field.get(obj);
//		if(fvalue !=  null){
//			return fvalue;
//		}else {
//			ColumnDes columnDes = field.getAnnotation(ColumnDes.class);
//			/**表达式所需变量计算&赋值**/
//			String columnFormula = columnDes.columnFormula();
//			if (columnFormula.length() > 0) {
//				String[] formulaParm = columnDes.formulaParm();
//				for (String parm : formulaParm) {
//					/**检查所需变量字段值**/
//					Field fm = fMap.get(parm);
//					//Object fmvalue = fm.get(obj);
//					Object fmvalue = calFormula(obj,fm,fMap,ctx,parser);
//					/**字段值为空有2种情况
//					 * 1 字段为表达式还未计算
//					 * 2 字段不是表达式但未赋值（这种情况应异常抛出）**/
//					if(fmvalue == null){
//						throw new IllegalArgumentException(obj.getClass().getName()+" 字段 "+fm.getName()+" 未赋值");
//					}
//				}
//				Object calvalue = parser.parseExpression(columnFormula).getValue(ctx);
//				field.set(obj, calvalue);
//				ctx.setVariable(columnDes.columnName(), calvalue);
//				return calvalue;
//			}
//		}
//		return null;
//	}
//}
