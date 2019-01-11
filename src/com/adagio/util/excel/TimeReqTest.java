//package com.adagio.util.excel;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.haizol.swquote.biz.professional.utils.ComputeUtils;
//
//
//public class TimeReqTest {
//
//	/*
//	private Long id;//id
//	private Long pricingId;//核价id
//	private Date createTime;//创建时间
//	private String craftwork;//工艺
//	private String procedure;//工序名称
//	@ColumnDes(columnName = "B")
//	private Double machineType;//机床类型
//	
//	private String material;//材料
//	
//	@ColumnDes(columnName = "E")
//	private Double length;//长度
//	
//	@ColumnDes(columnName = "F")
//	private Double dia;//直径
//	
//	@ColumnDes(columnName = "G")
//	private Double num=4.0;//刀尖数量
//	
//	@ColumnDes(columnName = "H")
//	private Double cuttingSpeed;//切削速度
//	
//	@ColumnDes(columnName = "I")
//	private Double feedPer;//每刀尖进给量
//	
//	@ColumnDes(columnName = "J",columnFormula="#H*1000/(#F*3.14)-6500>=0?6500.0:#H*1000/(#F*3.14)",formulaParm={"H","F"})
//	private Double spindleSpeed;//主轴转速
//	
//	@ColumnDes(columnName = "K",columnFormula="#G*#I*#J",formulaParm={"G","I","J"})
//	private Double feedRate;//进给率
//	
//	//@ColumnDes(columnName = "L",columnFormula="(#X+#F)*T(java.lang.Math).ceil(#Z/#Y)",formulaParm={"F","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="#X+#M",formulaParm={"M","X"})
//	//@ColumnDes(columnName = "L",columnFormula="(3.14+1)*(#X-#F)*T(java.lang.Math).ceil(#Z/#W)",formulaParm={"F","X","W","Z"})
//	@ColumnDes(columnName = "L",columnFormula="(3.14*(#Y+#F)+#X-#Y)*T(java.lang.Math).ceil(#Z/#W)",formulaParm={"F","W","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="T(java.lang.Math).sqrt(3.14*3.14*(#Z+#F-#W)*(#Z+#F-#W)+#W*#W)*T(java.lang.Math).ceil((#X-#Y)/#W)",formulaParm={"F","W","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="(T(java.lang.Math).sqrt(3.14*3.14*(#Z-#F)*(#Z-#F)+#W*#W)+#Z-#F)*T(java.lang.Math).ceil((#X-#Y)/#W)",formulaParm={"F","W","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="(#Z+#X)*T(java.lang.Math).ceil(#Y/#W)",formulaParm={"F","W","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="3.14*(#X+#Y)/2*T(java.lang.Math).ceil((#X-#Y)/(2*#F))*T(java.lang.Math).ceil(#Z/3)+#Z",formulaParm={"F","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="(#X*#X*2*T(java.lang.Math).acos((#X-#Z)/#X)/2-(#X-#Z)*#X*T(java.lang.Math).sin(T(java.lang.Math).acos((#X-#Z)/#X)))*#K*#W/100",formulaParm={"W","K","X","Y","Z"})
//	//@ColumnDes(columnName = "L",columnFormula="#Y/(2*T(java.lang.Math).sqrt(#X*#X-(#X-#W/1000)*(#X-#W/1000)))",formulaParm={"W","X","Y"})
//	private Double cuttingLength;//特征长度
//	//
//	@ColumnDes(columnName = "M")
//	private Double reserveLength=5.0;//退刀距离，安全距离
//	
//	@ColumnDes(columnName = "N")
//	private Double numOf;//加工数量
//	
//	@ColumnDes(columnName = "O",columnFormula="(#L+#M)*#N*60/#K",formulaParm={"L","M","N","K"})
//	private Double cuttingTime;//切削时间
//	
//	@ColumnDes(columnName = "P")
//	private Double ATCTime=3.0;//换刀时间
//	
//	@ColumnDes(columnName = "Q")
//	private Double otherTime=3.0;
//	
//	@ColumnDes(columnName = "R",columnFormula="#O+#P+#Q",formulaParm={"O","P","Q"})
//	private Double totalTime;
//	
//	@ColumnDes(columnName = "S",columnFormula="#B*#R/3600",formulaParm={"B","R"})
//	private Double totalPrice;//总时间
//	@ColumnDes(columnName = "X")
//	private Double parameterX;//长度
//	@ColumnDes(columnName = "Y")
//	private Double parameterY;//宽度
//	@ColumnDes(columnName = "Z")
//	private Double parameterZ;//高度或者深度
//	@ColumnDes(columnName = "W")
//	private Double parameterB;
//	private String remark;//备注*/
//
//	private Long id;//id
//	private Long pricingId;//核价id
//	private Date createTime;//创建时间
//	private String craftwork;//工艺
//	private String procedure;//工序名称
//	@ColumnDes(columnName = "B")
//	private Double machineType;//机床类型
//	
//	private String material;//材料
//	
//	@ColumnDes(columnName = "E")
//	private Double length;//长度
//	
//	@ColumnDes(columnName = "F")
//	private Double dia;//直径
//	
//	@ColumnDes(columnName = "G")
//	private Double num=4.0;//刀尖数量
//	
//	@ColumnDes(columnName = "H")
//	private Double cuttingSpeed;//切削速度
//	
//	@ColumnDes(columnName = "I")
//	private Double feedPer;//每刀尖进给量
//	
//	@ColumnDes(columnName = "J",columnFormula="#H*1000/(#F*3.14)-6500>=0?6500.0:#H*1000/(#F*3.14)",formulaParm={"H","F"})
//	private Double spindleSpeed;//主轴转速
//	
//	@ColumnDes(columnName = "K",columnFormula="#G*#I*#J",formulaParm={"G","I","J"})
//	private Double feedRate;//进给率
//	
//	@ColumnDes(columnName = "L",columnFormula="(3.14*(#Y+#F)+#X-#Y)*T(java.lang.Math).ceil(#Z/#W)",formulaParm={"F","W","X","Y","Z"})
//	private Double cuttingLength;//特征长度
//	//(3.14*(#Y+#F)+#X-#Y)*T(java.lang.Math).ceil(#Z/#W)
//	@ColumnDes(columnName = "M")
//	private Double reserveLength=5.0;//退刀距离，安全距离
//	
//	@ColumnDes(columnName = "N")
//	private Double numOf;//加工数量
//	
//	@ColumnDes(columnName = "O",columnFormula="(#L+#M)*#N*60/#K",formulaParm={"L","M","N","K"})
//	private Double cuttingTime;//切削时间
//	
//	@ColumnDes(columnName = "P")
//	private Double ATCTime=3.0;//换刀时间
//	
//	@ColumnDes(columnName = "Q")
//	private Double otherTime=3.0;
//	
//	@ColumnDes(columnName = "R",columnFormula="#O+#P+#Q",formulaParm={"O","P","Q"})
//	private Double totalTime;
//	
//	@ColumnDes(columnName = "S",columnFormula="#B*#R/3600",formulaParm={"B","R"})
//	private Double totalPrice;//总时间
//	@ColumnDes(columnName = "X")
//	private Double parameterX;//长度
//	@ColumnDes(columnName = "Y")
//	private Double parameterY;//宽度
//	@ColumnDes(columnName = "Z")
//	private Double parameterZ;//高度或者深度
//	@ColumnDes(columnName = "W")
//	private Double parameterB;
//	private String remark;//备注
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public Long getPricingId() {
//		return pricingId;
//	}
//	public void setPricingId(Long pricingId) {
//		this.pricingId = pricingId;
//	}
//	public Date getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//	public String getCraftwork() {
//		return craftwork;
//	}
//	public void setCraftwork(String craftwork) {
//		this.craftwork = craftwork;
//	}
//	public String getProcedure() {
//		return procedure;
//	}
//	public void setProcedure(String procedure) {
//		this.procedure = procedure;
//	}
//	public Double getMachineType() {
//		return machineType;
//	}
//	public void setMachineType(Double machineType) {
//		this.machineType = machineType;
//	}
//	public String getMaterial() {
//		return material;
//	}
//	public void setMaterial(String material) {
//		this.material = material;
//	}
//	public Double getLength() {
//		return length;
//	}
//	public void setLength(Double length) {
//		this.length = length;
//	}
//	public Double getDia() {
//		return dia;
//	}
//	public void setDia(Double dia) {
//		this.dia = dia;
//	}
//	public Double getNum() {
//		return num;
//	}
//	public void setNum(Double num) {
//		this.num = num;
//	}
//	public Double getCuttingSpeed() {
//		return cuttingSpeed;
//	}
//	public void setCuttingSpeed(Double cuttingSpeed) {
//		this.cuttingSpeed = cuttingSpeed;
//	}
//	public Double getFeedPer() {
//		return feedPer;
//	}
//	public void setFeedPer(Double feedPer) {
//		this.feedPer = feedPer;
//	}
//	public Double getSpindleSpeed() {
//		return spindleSpeed;
//	}
//	public void setSpindleSpeed(Double spindleSpeed) {
//		this.spindleSpeed = spindleSpeed;
//	}
//	public Double getFeedRate() {
//		return feedRate;
//	}
//	public void setFeedRate(Double feedRate) {
//		this.feedRate = feedRate;
//	}
//	public Double getCuttingLength() {
//		return cuttingLength;
//	}
//	public void setCuttingLength(Double cuttingLength) {
//		this.cuttingLength = cuttingLength;
//	}
//	public Double getReserveLength() {
//		return reserveLength;
//	}
//	public void setReserveLength(Double reserveLength) {
//		this.reserveLength = reserveLength;
//	}
//	public Double getNumOf() {
//		return numOf;
//	}
//	public void setNumOf(Double numOf) {
//		this.numOf = numOf;
//	}
//	public Double getCuttingTime() {
//		return cuttingTime;
//	}
//	public void setCuttingTime(Double cuttingTime) {
//		this.cuttingTime = cuttingTime;
//	}
//	public Double getATCTime() {
//		return ATCTime;
//	}
//	public void setATCTime(Double aTCTime) {
//		ATCTime = aTCTime;
//	}
//	public Double getOtherTime() {
//		return otherTime;
//	}
//	public void setOtherTime(Double otherTime) {
//		this.otherTime = otherTime;
//	}
//	public Double getTotalTime() {
//		return totalTime;
//	}
//	public void setTotalTime(Double totalTime) {
//		this.totalTime = totalTime;
//	}
//	public Double getTotalPrice() {
//		return totalPrice;
//	}
//	public void setTotalPrice(Double totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//	
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	public Double getParameterX() {
//		return parameterX;
//	}
//	public void setParameterX(Double parameterX) {
//		this.parameterX = parameterX;
//	}
//	public Double getParameterY() {
//		return parameterY;
//	}
//	public void setParameterY(Double parameterY) {
//		this.parameterY = parameterY;
//	}
//	public Double getParameterZ() {
//		return parameterZ;
//	}
//	public void setParameterZ(Double parameterZ) {
//		this.parameterZ = parameterZ;
//	}
//	public Double getParameterB() {
//		return parameterB;
//	}
//	public void setParameterB(Double parameterB) {
//		this.parameterB = parameterB;
//	}
//	
//	public static void main(String[] args) throws JsonProcessingException {
//		TimeReqTest test = new TimeReqTest();
//
//		test.setDia(20.0);
//		
//		test.setNum(4.0);
//		test.setCuttingSpeed(60.0);
//		test.setFeedPer(0.05);
//		
//		test.setParameterX(40.0);
//		test.setParameterY(30.0);
//		test.setParameterZ(10.0);
//		test.setParameterB(6.0);
//		test.setReserveLength(5.0);
//		test.setNumOf(1.0);
//		test.setATCTime(3.0);
//		test.setOtherTime(3.0);
//		try {
//			CalculaUtil.calculate(test);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(ComputeUtils.formatDoubleVal(test.getTotalTime(),2, BigDecimal.ROUND_HALF_UP));
//		System.out.println(test.getCuttingLength());
//		ObjectMapper mapper = new ObjectMapper();
//		System.out.println(mapper.writeValueAsString(test));
//	}
//}
