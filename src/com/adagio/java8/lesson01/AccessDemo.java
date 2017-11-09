package com.adagio.java8.lesson01;

/**
 * ���ʾֲ���Ӧ���ⲿ����ľֲ�final�������Լ���Ա�����;�̬����
 *
 */
public class AccessDemo {

	public static void main(String[] args) {
		//���ʾֲ�����
//		final int num = 1;
//		Converter<Integer, String> stringConverter =
//		        (from) -> String.valueOf(from + num);
//
//		System.out.println(stringConverter.convert(2));     // 3
		
		//����num������Ҫһ����final������Ĵ�����Ȼ�ǺϷ���
//		int num = 1;
//		Converter<Integer, String> stringConverter =
//		        (from) -> String.valueOf(from + num);
//
//		stringConverter.convert(2);     // 3
		
		//num�ڱ����ʱ����ʽ�ص���final����������
		int num = 1;
		Converter<Integer, String> stringConverter =
				(from) -> String.valueOf(from + num);
//		num = 3;		//��������
		System.out.println(stringConverter.convert(5));
				
		Lambda4 l = new Lambda4();
		l.testScopes();
		
		
		//����Ĭ�Ͻӿڷ���
		//Ĭ�Ϸ����޷���lambda���ʽ�ڲ������ʣ�����Ĵ������޷�ͨ������
//		Formula formula = (a) -> sqrt( a * 100);
	}
	
}

//���ʳ�Ա�����;�̬����
class Lambda4 {
    static int outerStaticNum;
    int outerNum;

    void testScopes() {
    	//��lambda���ʽ���ڲ��ܻ�ȡ���Գ�Ա������̬�����Ķ�дȨ
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
        
        System.out.println(stringConverter1.convert(6) + "==" + stringConverter2.convert(7));
    }
}
