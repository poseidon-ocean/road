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
* 常量池结束之后，紧接着的两个字节代表访问标志(access_flags)
	* 用于识别一些类或接口层次的访问信息，如下
		* 这个Class是类还是接口
		* 是否定义为public类型
		* 是否定义为abstract类型
		* 如果是类的话，是否被声明为final 
```
标志名			标志值		标志含义					针对的对像
ACC_PUBLIC		0x0001		public类型				所有类型
ACC_FINAL		0x0010		final类型				类
ACC_SUPER		0x0020		使用新的invokespecial语义	类和接口
ACC_INTERFACE	0x0200		接口类型					接口
ACC_ABSTRACT	0x0400		抽象类型					类和接口
ACC_SYNTHETIC	0x1000		该类不由用户代码生成			所有类型
ACC_ANNOTATION 	0x2000		注解类型					注解
ACC_ENUM  		0x4000		枚举类型					枚举
```
	* access_flags中一共有16个标志位可以使用，当前只定义了8个，没有使用到的标志位要求一律为0
	
### 类索引、父类索引与接口索引集合
* 类索引(this_class)和父类索引(super_class)都是一个u2类型的数据
* 接口索引集合(interfaces)是一组u2类型的数据集合
* Class文件由这三个数据来确定这个类的继承关系

### 字段表集合
* 字段表(field_info)用于描述接口或类中声明的变量
* 字段(field)含类级变量以及实例级变量，不含局部变量
* 描述的信息：
	* 字段的作用域(public、private、protected)
	* 是实例变量还是类变量(static)
	* 可变性(final)
	* 并发可见性(volatile)
	* 是否可被序列化(transient)
	* 字段数据类型
	* 字段名称

### 方法表集合
* 与字段表几乎一致
* 重载方法，要求必须拥有一个与原方法不同的特征签名
* 特征签名是一个方法中各个参数在常量池中的字段符号引用的集合

### 属性表集合
* 在Class文件、字段表、方法表都可以携带自己的属性表
* 每个属性：
	* 名称从常量池引用一个CONSTANT_Utf8_info类型常量表示
	*　属性值的结构自定义，只需一个u4长度属性说明所占用的位数
* Code属性
	* Java程序方法体中的代码经过Javac编译器处理后，最终变字节码指令存储在Code属性内
	* 接口或抽象类中的方法就不存在Code属性
```
类型				名称						数量
u2				attribute_name_index	1
u4				attribute_length		1
u2				max_stack				1
u2				max_locals				1
u4				code_length				1
u1				code					code_length
u2				exception_table_length	1
exception_info	exception_table			exception_length
u2				attributes_count		1
attribute_info	attributes				attributes_count
```
	* attribute_name_index是一项指向CONSTANT_Utf8_info类常量的索引，常量值固定为“Code”，代表属性的名称
	* attribute_length指示了属性值的长度
	* 由于属性名称索引与属性长度一共为6字节，所以属性值的长度固定为整个属性表长度减去6个字节
	* max_stack代表了操作数栈(Operand Stacks)深度的最大值
		* 在方法执行的任何时刻，操作数都不会超过成功深度
		* 虚拟机运行的时候需要根据这个值来分配栈帧(Stack Frame)中的操作数栈深度
	* max_locals代表了局部变量表所需的存储空间
		* 单位是Slot，不超过32位数据类型用一个Slot来存放，64位(long、double)需要两个
		* 方法参数、异常参数、局部变量都需要局部变量表来存放
	* code_length和code用来存储Java源程序编译后生成的字节码指令
		* code_length代表字节码长度
		* code用于存储字节码指令的一系列字节流
		* 只使用了u2长度，超过这个限制，Javac编译器会拒绝编译，所有不用写超长的方法
	* Code属性是Class文件中最重要的一个属性
		* Java程序信息分：代码(Code，方法体内的Java代码)和元数据(Metadata：类、字段、方法定义和其它信息)
		* Code属性用于描述代码，其它所有数据项目都用于描述元数据
		* 能够直接阅读字节码是工作中分析Java代码语义问题的必要工具和基本技能
		* 编码与指令之间的对应关系，查看“虚拟机字节码指令表” ：https://segmentfault.com/a/1190000008722128
		* 翻译指令"2A B7 00 0A B1"
			* 读入2A，查表得0x2A对应指令aload_0，这个指令含义是将第0个Slot中为reference类型的本地变量推送到操作数栈顶
			* 读入B7，查表得0xB7对应指令invokespecial，指令含义：调用超类构建方法、实例初始化方法、私有方法
			* 读入00、0A，这是invokespecial的参数，查常量池得0x000A对应的常量为实例构造器"<init>"方法的符号引用
			* 读入B1，查表得0xB1对应指令为return，含义是返回此方法，并且返回值为void
		* 使用javap -verbose AjaxResult.class 输出堆栈大小、各方法的 locals 及 args 数,以及class文件的编译版本
* Exception属性：列举出方法中可能抛出的受检查异常
* LineNumberTable属性：用于描述Java源码行号与字节码行号之间的对应关系
* LocalVariableTable属性：描述栈帧中局部变量表中的变量与Java源码中定义的变量之间的关系
* SourceFile属性：记录生成这个Class文件的源码文件名称
* ConstantValue属性：通知虚拟机自动为静态变量赋值( static修饰的变量)		
* InnerClasses属性：记录内部类与宿主类之间的关联
* Deprecated及Synthetic属性：标志类型布尔值属性
	* Deprecated属性用于表示某个类、字段或者方法，已经被程序作者定义为不再推荐使用
	* Synthetic属性代表此字段或方法并不是由Java源码直接产生的，而是由编译器自行添加的
* StackMapTable属性：变长属性，被类型检查验证器使用
* Signature属性：记录泛型签名信息
* BootstrapMethods属性：用于保存invokedynamic指令引用的引导方法限定符
		
## 字节码指令简介
* Java虚拟机的指令由一个字节长度的、代表着某种特定操作含义的数字(称为操作码 Opcode)以及跟随其后的零至多个代表此操作所需参数(称为操作数，Operands)而构成
* 字节码与数据类型
	* 大部分与数据类型有关的字节码指令，它们的操作码助记符都要特殊的字符来表明专门为哪种数据类型服务
		* i 代表int类型的数据操作
		* l 代表long类型的数据操作
		* s 代表short
		* b 代表boolean
		* c 代表char
		* f 代表float
		* d 代表double
		* a 代表reference
* 加载和存储指令
	* 用于将数据在栈帧中的局部变量表和操作数栈之间来回传输
	* 将一个局部变量加载到操作栈：
		* iload、iload_<n>、lload、lload_<n>、fload、fload_<n>、
		* dload、dload_<n>、aload、aload_<n>
	* 将一个数值从操作数栈存储到局部变量表：
		* bipush、sipush、ldc、ldc_w、ldc2_w、aconst_null、
		* dstore、dstore_<n>、astore、astore_<n> 
	* 将一个常量加载到操作数栈：
		* iload、iload_<n>、lload、lload_<n>、fload、fload_<n>、
		* iconst_m1、iconst_<i>、lconst_<l>、fconst_<f>、dconst_<d>
	* 扩充局部变量表的访问索引指令：wide
* 运算指令
	* 运算或算术指令用于对两个操作数栈上的值进行某种特定运算，并把结果重新存入到栈顶
	* 加法指令：iadd、ladd、fadd、dadd
	* 减法指令：isub、lsub、fsub、dsub
	* 乘法指令：imul、lmul、fmul、dmul
	* 除法指令：idiv、ldiv、fdiv、ddiv
	* 求余指令：irem、lrem、frem、drem
	* 取反指令：ineg、lneg、fneg、dneg
	* 位移指令：ishl、ishr、iushr、lshl、lshr、lushr
	* 按位或指令：ior、lor
	* 按位与指令：iand、land
	* 按位异或指令：ixor、lxor
	* 局部变量自增指令：iinc
	* 比较指令：dcmpg、dcmpl、fcmpg、fcmpl、lcmp
* 类型转换指令
	* 可以将两种不同的数值类型进行相互转换
	* java虚拟机直接支持数值类型的宽化类型转换(小范围转大范围)
	* 处理窄化类型转换时，必须显式地使用转换指令来完成
		* 指令：i2b、i2c、i2s、l2s、f2i、f2l、d2i、d2f
		* 如果浮点值是NaN，那转换结果就是int或long类型的0
		* 如果浮点值不是无穷大，使用IEEE 754的向零舍入模式取整，获得整数值v
			* 如果v在目标类型T的表示范围内，就转换为v
			* 否则根据v的符号，转换为T所能表示的最大或最小正数
		* Java虚拟机规范明确规定数值类型的窄化转换指令永远不可能导致虚拟机抛出运行时异常
* 对象创建与访问指令
	* 创建类实例的指令：new
	* 创建数组的指令：newarray、anewarray、multianewarray
	* 访问类字段和实例字段的指令：getstatic、 putstatic、getfield、putfield
	* 把一个数组元素加载到操作数栈的指令：baload、 caload、 saload、 iaload、 faload、daload、 aaload
	* 将一个操作数栈的值存储到数组元素中的指令：bastore、 castore、 sastore、 iastore、 fastore、 dastore、 aastore
	* 取数组长度的指令：arraylength
	* 检查类实例的指令：instanceof、checkcast
* 操作数栈管理指令
	* 将操作数栈的栈顶一个或两个元素出栈：pop、pop2
	* 复制栈顶的一个或两个数值并将复制值或双份的复制值重新压入栈顶：dup、dup2、dup_x1、dup2_x1、dup_x2、dup2_x2
	* 将栈最顶端的两个数值互换：swap
* 控制转移指令
	* 分支指令：ifeq、iflt、ifle、ifne、ifgt、ifge、ifnull、ifnotnull、if_icmpeq、if_icmplt、
	* if_icmpgt、if_icmple、if_icmpge、if_acmpeq、if_acmpne
	* 复合条件分支指令：tableswitch、lookupswitch
	* 无条件分支：goto、goto_w、jsr、jsr_w、ret
* 方法调用和返回指令
	* invokeinterface指令用于调用接口方法
	* invokespecial指令用于调用一些特殊处理的实例方法：实例初始化方法、私有方法、父类方法
	* invokestatic指令用于调用类方法
	* invokedynamic指令用于在运行时动态解析出调用点限定符所引用的方法，并执行该方法
	* 方法调用指令与数据类型无关，而方法返回指令是根据返回值的类型区分的
* 异常处理指令
	* athrow指令
	* 处理异常不是由字节指令来实现，而是采用异常表来完成的
* 同步指令
	* 方法级的同步是隐式的，即无须通过字节码指令来控制
	* 虚拟机可以从方法常量池的方法表结构中的ACC_SYNCHRONIZED访问标志得知一个方法是否声明为同步方法

## 公有设计和私有实现
* Java虚拟机规范描绘了Java虚拟机应有的共同程序存储格式：Class文件格式以及字节码指令集
* 虚拟机实现：
	* 将输入的Java虚拟机代码在加载或执行时翻译成另外一种虚拟机的指令集
	* 将输入的Java虚拟机代码在加载或执行时翻译成宿主机CPU的本地指令集（JIT代码生成技术）



> 有不虞只誉，有求全之毁 - 孟子·离娄上
无功受禄，转来不虞之誉；劳苦功高，反有求全之毁

