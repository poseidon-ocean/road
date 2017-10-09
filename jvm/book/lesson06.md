# 类文件结构
* 代码编译的结果从本地机器码转变为字节码，是存储格式发展的一小步，却是编程语言发展的一大步

## 无关性的基石
* 与平台无关：不同虚拟机和平台统一使用程序存储格式--字节码(ByteCode)
* 语言无关性：Java语言规范与Java虚拟机规范的拆分，保证了其它语言运行于JVM

## Class类文件的结构
* 任何一个Class文件都对应着唯一的类和接口的定义信息，而类或接口并不一定都得定义在文件里（可类加载器直接生成）
* Class文件是一组以8位字节为基础单位的二进制流
	* 各个数据项目严格按照顺序紧凑地排列在Class文件之中，中间没有添加任何分隔符
	* 整个Class文件中存储的内容全部是程序运行的必要数据，没有空隙存在
	* 当遇到占用8位字节以上的空间数据项时，会按照高位在前的方式分割成若干个8位字节进行存储
* Java虚拟机规范规定：Class文件格式采用一种类似于C语言结构体的伪结构来存储数据
	* 无符号数：基本的数据类型
		* 以u1、u2、u4、u8来分别代表1个字节、2个字节、4个字节和8个字节的无符号数
		* 可以描述数字、索引引用、数量值或者按照UTF-8编码构成字符串值
	* 表：由多个无符号数或其它表作为数据项构成的复合数据类型
		* 习惯以"_info"结尾
		* 描述有层次关系的复合结构的数据，整个Class文件本质上就是一张表
		* 具体可以看 《Java虚拟机规范》
* 无论是无符号数还是表，当需要描述同一类型但数量不定的多个数据时，经常会出现一个前置的容量计数器加若干个连续的数据项的形式，这时称这一系列连续的某一类型的数据为某一类型的集合
```
ClassFile { 
    u4 magic; 
    u2 minor_version; 
    u2 major_version; 
    u2 constant_pool_count; 
    cp_info constant_pool[constant_pool_count-1]; 
    u2 access_flags; 
    u2 this_class; 
    u2 super_class; 
    u2 interfaces_count; 
    u2 interfaces[interfaces_count]; 
    u2 fields_count; field_info fields[fields_count]; 
    u2 methods_count; method_info methods[methods_count]; 
    u2 attributes_count; attribute_info attributes[attributes_count]; 
}
```
* Class结构由于没有分隔符，它的顺序、数量、数据存储的字节序等都被严格限定，都不允许改变

### 魔数与Class文件的版本
* 每个Class文件的头4个字节称为魔数(Magic Number)：
	* 唯一的作用是确定文件是否为一个能被虚拟机接受的Class文件
	* 很多文件存储标准都使用魔数来进行身份识别，如图片格式gif、jpeg等文件头中都存有魔数
	* Class文件的魔数值：0xCAFEBEBE
* 紧接魔数的4个字节是Class文件的版本号
	* 第5和第6个字节是次版本号(Minor Version)
	* 第7和第8个字节是主版本号(Major Version)
* WinHex是一款16进制编辑器，可以用来查看Class文件

### 常量池
* 紧接着主次版本号之后的是常量池入口
	* Class文件之中的资源仓库
	* Class文件结构中其它项目关联最多的数据类型
	* 占用Class文件空间最大的数据项之一
	* 还是Class文件中第一个出现的表类型数据项
* 由于常量池中常量的数量是不固定的
	* 所以在常量池的入口需要放置一项u2类型的数据，代计数值(constant_pool_count)
	* 这个计算量是从1开始而不是0开始，如果值是21，表示有20项，1-20
	* 第0项的设计：满足后面某些指向常量池的索引值的数据在特定情况下需要表达“不引用任何一个常量池项”
* 两大常量类：
	* 字面量(Literal)
		* 比较接近Java语言层面的常量，如文本字符串、声明final的常量值
	* 符号引用(Symbolic Reference)
		* 编译原理方面的概念，如下：
		* 类和接口的全限定名(Fully Qualified Name)
		* 字段的名称和描述符(Descriptor)
		* 方法的名称和描述符
* 常量池中每一个常量都是一个表：具体见常量池的项目类型
```
常量								项目		类型		描述
								tag		u1		值为1
CONSTANT_Utf8_info				length	u2		UTF-8编码的字符串占用的字节数
								bytes	u1		长度为length的UTF-8编码的字符串
								tag		u1		值为3
CONSTANT_Integer_info			bytes	u4		按照高位在前存储的int值，整型字面量
								tag		u1		值为4			
CONSTANT_Float_info				bytes	u4		按照高位在前存储的float值，浮点型字面量
								tag		u1		值为5
CONSTANT_Long_info				bytes	u8		按照高位在前存储的long值，长整型字面量				
								tag		u1		值为6	
CONSTANT_Double_info			bytes	u8		按照高位在前存储的double值，双精度字面量				
								tag		u1		值为7	
CONSTANT_Class_info				index	u2		指向全限定名常量项的索引，类或接口的符号引用	
								tag		u1		值为8	
CONSTANT_String_info			index	u2		指向字符串字面量的索引			
								tag		u1		值为9	
CONSTANT_Fieldref_info			index	u2		指向声明字段的类或接口描述符CONSTANT_Class_info的索引项	
								index	u2		指向字段名称及类型描述符CONSTANT_NameAndType_info的索引项	
								tag		u1		值为10	
CONSTANT_Methodref_info			index	u2		指向声明方法的类描述符CONSTANT_Class_info的索引项
								index	u2		指向方法名称及类型描述符CONSTANT_NameAndType_info的索引项
								tag		u1		值为11
CONSTANT_InrerfaceMethodref_info index	u2		指向声明方法的接口描述符CONSTANT_Class_info的索引项
								index	u2		指向方法名称及类型描述符CONSTANT_NameAndType_info的索引项				
								tag		u1		值为12
CONSTANT_NameAndType_info		index	u2		指向字段或方法名称常量项目的索引
								index	u2		指向该字段或方法描述符常量项的索引
CONSTANT_MethodHandle_info		15				表示方法句柄
CONSTANT_MethodType_info		16				标识方法类型
CONSTANT_InvokeDynamic_info		18				表示一个动态方法调用点
```
	* tag是标志位，用于区分常量类型
	* name_index是一个索引值，指向常量池中一个CONSTANT_Utf8_info类型常量
	* length值说明这个UTF-8编码的字符串长度是多少字节
		* ‘\u001’ - ‘\u007f’之间的字符(1-127的ASCII码)的缩略编码使用一个字节表示
		* ‘\u0080’ - ‘\u07ff’之间使用两个字节表示
		* ‘\u0800’ - ‘\uffff’之间使用三个字节表示
	* 由于Class文件中方法、字段等都需要引用CONSTANT_Utf8_info型常量，所以Java方法、字段名的最大长度是u2(65535)
		* Java程序中如果定义超过64KB英文字符的变量或方法名是无法编译的
	* 分析Class文件字节码内容：javap -verbose TestClass

### 访问标志













