package com.kjuns.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-8-26
 * @file SortListUtil.java
 * @package com.kjuns.util
 * @project kjuns
 * @version 2.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SortListUtil {

	public static final String DESC = "desc";
	public static final String ASC = "asc";

	/**
	 * 对list中的元素按升序排列.
	 * 
	 * @param list
	 *            排序集合
	 * @param field
	 *            排序字段
	 * @return
	 */
	public static List<?> sort(List<?> list, final String field) {
		return sort(list, field, null);
	}

	/**
	 * 对list中的元素进行排序.
	 * 
	 * @param list
	 *            排序集合
	 * @param field
	 *            排序字段
	 * @param sort
	 *            排序方式: SortList.DESC(降序) SortList.ASC(升序).
	 * @return
	 */
	public static List<?> sort(List<?> list, final String field, final String sort) {
		Collections.sort(list, new Comparator() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Field f = a.getClass().getDeclaredField(field);
					f.setAccessible(true);
					Class<?> type = f.getType();

					if (type == int.class) {
						ret = ((Integer) f.getInt(a)).compareTo((Integer) f.getInt(b));
					} else if (type == double.class) {
						ret = ((Double) f.getDouble(a)).compareTo((Double) f.getDouble(b));
					} else if (type == long.class) {
						ret = ((Long) f.getLong(a)).compareTo((Long) f.getLong(b));
					} else if (type == float.class) {
						ret = ((Float) f.getFloat(a)).compareTo((Float) f.getFloat(b));
					} else if (type == Date.class) {
						ret = ((Date) f.get(a)).compareTo((Date) f.get(b));
					} else if (isImplementsOf(type, Comparable.class)) {
						ret = ((Comparable) f.get(a)).compareTo((Comparable) f.get(b));
					} else {
						ret = String.valueOf(f.get(a)).compareTo(String.valueOf(f.get(b)));
					}

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (sort != null && sort.toLowerCase().equals(DESC)) {
					return -ret;
				} else {
					return ret;
				}

			}
		});
		return list;
	}

	/**
	 * 对list中的元素按fields和sorts进行排序,
	 * fields[i]指定排序字段,sorts[i]指定排序方式.如果sorts[i]为空则默认按升序排列.
	 * 
	 * @param list
	 * @param fields
	 * @param sorts
	 * @return
	 */
	public static List<?> sort(List<?> list, String[] fields, String[] sorts) {
		if (fields != null && fields.length > 0) {
			for (int i = fields.length - 1; i >= 0; i--) {
				final String field = fields[i];
				String tmpSort = ASC;
				if (sorts != null && sorts.length > i && sorts[i] != null) {
					tmpSort = sorts[i];
				}
				final String sort = tmpSort;
				Collections.sort(list, new Comparator() {
					public int compare(Object a, Object b) {
						int ret = 0;
						try {
							Field f = a.getClass().getDeclaredField(field);
							f.setAccessible(true);
							Class<?> type = f.getType();
							if (type == int.class) {
								ret = ((Integer) f.getInt(a)).compareTo((Integer) f.getInt(b));
							} else if (type == double.class) {
								ret = ((Double) f.getDouble(a)).compareTo((Double) f.getDouble(b));
							} else if (type == long.class) {
								ret = ((Long) f.getLong(a)).compareTo((Long) f.getLong(b));
							} else if (type == float.class) {
								ret = ((Float) f.getFloat(a)).compareTo((Float) f.getFloat(b));
							} else if (type == Date.class) {
								ret = ((Date) f.get(a)).compareTo((Date) f.get(b));
							} else if (isImplementsOf(type, Comparable.class)) {
								ret = ((Comparable) f.get(a)).compareTo((Comparable) f.get(b));
							} else {
								ret = String.valueOf(f.get(a)).compareTo(String.valueOf(f.get(b)));
							}

						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}

						if (sort != null && sort.toLowerCase().equals(DESC)) {
							return -ret;
						} else {
							return ret;
						}
					}
				});
			}
		}
		return list;
	}

	/**
	 * 默认按正序排列
	 * 
	 * @param list
	 * @param method
	 * @return
	 */
	public static List<?> sortByMethod(List<?> list, final String method) {
		return sortByMethod(list, method, null);
	}

	public static List<?> sortByMethod(List<?> list, final String method, final String sort) {
		Object [] obj = null;
		Collections.sort(list, new Comparator() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Method m = a.getClass().getMethod(method);
					m.setAccessible(true);
					Class<?> type = m.getReturnType();
					if (type == int.class) {
						ret = ((Integer) m.invoke(a, obj)).compareTo((Integer) m.invoke(b, obj));
					} else if (type == double.class) {
						ret = ((Double) m.invoke(a, obj)).compareTo((Double) m.invoke(b, obj));
					} else if (type == long.class) {
						ret = ((Long) m.invoke(a, obj)).compareTo((Long) m.invoke(b, obj));
					} else if (type == float.class) {
						ret = ((Float) m.invoke(a, obj)).compareTo((Float) m.invoke(b, obj));
					} else if (type == Date.class) {
						ret = ((Date) m.invoke(a, obj)).compareTo((Date) m.invoke(b, obj));
					} else if (isImplementsOf(type, Comparable.class)) {
						ret = ((Comparable) m.invoke(a, obj)).compareTo((Comparable) m.invoke(b, obj));
					} else {
						ret = String.valueOf(m.invoke(a, obj)).compareTo(String.valueOf(m.invoke(b, obj)));
					}

					if (isImplementsOf(type, Comparable.class)) {
						ret = ((Comparable) m.invoke(a, obj)).compareTo((Comparable) m.invoke(b, obj));
					} else {
						ret = String.valueOf(m.invoke(a, obj)).compareTo(String.valueOf(m.invoke(b, obj)));
					}

				} catch (NoSuchMethodException ne) {
					System.out.println(ne);
				} catch (IllegalAccessException ie) {
					System.out.println(ie);
				} catch (InvocationTargetException it) {
					System.out.println(it);
				}

				if (sort != null && sort.toLowerCase().equals(DESC)) {
					return -ret;
				} else {
					return ret;
				}
			}
		});
		return list;
	}

	public static List<?> sortByMethod(List<?> list, final String methods[], final String sorts[]) {
		Object [] obj = null;
		if (methods != null && methods.length > 0) {
			for (int i = methods.length - 1; i >= 0; i--) {
				final String method = methods[i];
				String tmpSort = ASC;
				if (sorts != null && sorts.length > i && sorts[i] != null) {
					tmpSort = sorts[i];
				}
				final String sort = tmpSort;
				Collections.sort(list, new Comparator() {
					public int compare(Object a, Object b) {
						int ret = 0;
						try {
							Method m = a.getClass().getMethod(method);
							m.setAccessible(true);
							Class<?> type = m.getReturnType();
							if (type == int.class) {
								ret = ((Integer) m.invoke(a, new Object[]{obj})).compareTo((Integer) m.invoke(b, obj));
							} else if (type == double.class) {
								ret = ((Double) m.invoke(a, obj)).compareTo((Double) m.invoke(b, obj));
							} else if (type == long.class) {
								ret = ((Long) m.invoke(a, obj)).compareTo((Long) m.invoke(b, obj));
							} else if (type == float.class) {
								ret = ((Float) m.invoke(a, obj)).compareTo((Float) m.invoke(b, obj));
							} else if (type == Date.class) {
								ret = ((Date) m.invoke(a, obj)).compareTo((Date) m.invoke(b, obj));
							} else if (isImplementsOf(type, Comparable.class)) {
								ret = ((Comparable) m.invoke(a, obj)).compareTo((Comparable) m.invoke(b, obj));
							} else {
								ret = String.valueOf(m.invoke(a, obj)).compareTo(String.valueOf(m.invoke(b, obj)));
							}

						} catch (NoSuchMethodException ne) {
							System.out.println(ne);
						} catch (IllegalAccessException ie) {
							System.out.println(ie);
						} catch (InvocationTargetException it) {
							System.out.println(it);
						}

						if (sort != null && sort.toLowerCase().equals(DESC)) {
							return -ret;
						} else {
							return ret;
						}
					}
				});
			}
		}
		return list;
	}

	/**
	 * 判断对象实现的所有接口中是否包含szInterface
	 * 
	 * @param clazz
	 * @param szInterface
	 * @return
	 */
	public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {
		boolean flag = false;
		Class<?>[] face = clazz.getInterfaces();
		for (Class<?> c : face) {
			if (c == szInterface) {
				flag = true;
			} else {
				flag = isImplementsOf(c, szInterface);
			}
		}
		if (!flag && null != clazz.getSuperclass()) {
			return isImplementsOf(clazz.getSuperclass(), szInterface);
		}
		return flag;
	}
	
	  public static void main(String[] args) throws Exception {  
//	      // 按age正序排序,注意结果排完后是1,2,3,11. 不是1,11,2,3(如果是String类型正序排序是这样)  
//		  SortListUtil.sort(list, "age", null);  
//		  // 按id倒序  
//	      SortListUtil.sort(list, "id", SortListUtil.DESC);  
//	      // 先按name正序排序,再按id正序排序  
//	      SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {});  
//	      // 先按name正序排序,再按id倒序排序  
//	      SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {  
//	                SortListUtil.ASC, SortListUtil.DESC });  
//	      // 按birthday排序  
//	      SortListUtil.sort(list, "birthday");  
//	      // sortByMethod  
//	      SortListUtil.sortByMethod(list, "getId", null);  
	   }  

}
