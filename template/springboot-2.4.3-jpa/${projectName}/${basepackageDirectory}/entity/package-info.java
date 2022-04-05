import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.onemysoft.wms.entity.WmsStockRoom;

/*
 * @Min 验证 Number 和 String 对象是否大等于指定的值
 * 
 * @Max 验证 Number 和 String 对象是否小等于指定的值
 * 
 * @Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内
 * 
 * @Length(min=, max=) 验证字符串长度是否在给定的范围之内
 * 
 */

/*
 * @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
 * 
 * @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
 */

//空检查
//@Null			验证对象是否为null
//@NotNull		验证对象是否不为null, 无法查检长度为0的字符串
//@NotBlank		检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格.
//@NotEmpty		检查约束元素是否为NULL或者是EMPTY. 

//Booelan检查
//@AssertTrue		验证 Boolean 对象是否为 true  
//@AssertFalse	验证 Boolean 对象是否为 false  

//长度检查
//@Size(min=, max=)		验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  
//@Length(min=, max=)		验证注解的元素值长度在min和max区间内

//日期检查
//@Past		验证 Date 和 Calendar 对象是否在当前时间之前  
//@Future		验证 Date 和 Calendar 对象是否在当前时间之后  
//@Pattern	验证 String 对象是否符合正则表达式的规则

//数值检查，建议使用在Stirng,Integer类型，不建议使用在int类型上，因为表单值为“”时无法转换为int，但可以转换为Stirng为"",Integer为null
//@Min			验证 Number 和 String 对象是否大等于指定的值  
//@Max			验证 Number 和 String 对象是否小等于指定的值  
//@DecimalMax		被标注的值必须不大于约束中指定的最大值. 这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示.小数存在精度
//@DecimalMin		被标注的值必须不小于约束中指定的最小值. 这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示.小数存在精度
//@Digits			验证 Number 和 String 的构成是否合法  
//@Digits(integer=,fraction=)		验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度。

//@Range(min=, max=)	验证注解的元素值在最小值和最大值之间
//@Range(min=10000,max=50000,message="range.bean.wage")
//private BigDecimal wage;

//@Valid 递归的对关联对象进行校验, 如果关联对象是个集合或者数组,那么对其中的元素进行递归校验,如果是一个map,则对其中的值部分进行校验.(是否进行递归验证)
//@CreditCardNumber信用卡验证
//@Email  验证是否是邮件地址，如果为null,不进行验证，算通过验证。
//@ScriptAssert(lang= ,script=, alias=)
//@URL(protocol=,host=, port=,regexp=, flags=)

//在需要校验的bean上加上分组注解：
//
//@NotBlank(groups = {Update.class}, message = "ID不能为空")
//private String id;
//
//@NotBlank(groups = {Insert.class, Update.class}, message = "名称不能为空")
//@Size(groups = {Insert.class, Update.class}, max = 32, message = "名称最大长度为32")
//private String name;

//@PostMapping("/insert")
//public int insert(@RequestBody @Validated({Insert.class}) HospitalRequest request) {
//    return hospitalService.insert(request);
//}
//
//@PostMapping("/update")
//public int update(@RequestBody @Validated({Update.class}) HospitalRequest request) {
//    return hospitalService.update(request);
//}

// 嵌套验证
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class HospitalRequest {
//    @Valid //嵌套验证必须用@Valid
//    @NotBlank(groups = {Insert.class, Update.class}, message = "departmentList不能为空")
//    @Size(groups = {Insert.class, Update.class}, min = 1, message = "至少要有一个属性")
//    private List<Department> departmentList;
//}



/***************************
 * ValidatorUtils
 **************************
 */
//在做更新时,校验如下:
//ValidatorUtils.validateEntity(gatherForm, Update.class);
//在做新增时,校验如下
//ValidatorUtils.validateEntity(gatherForm, Add.class);




/***************************
 * CascadeType
 **************************
 */


//CascadeType.REMOVE
//Cascade remove operation，级联删除操作。
//删除当前实体时，与它有映射关系的实体也会跟着被删除。
//CascadeType.MERGE
//Cascade merge operation，级联更新（合并）操作。
//当Student中的数据改变，会相应地更新Course中的数据。
//CascadeType.DETACH
//Cascade detach operation，级联脱管/游离操作。
//如果你要删除一个实体，但是它有外键无法删除，你就需要这个级联权限了。它会撤销所有相关的外键关联。
//CascadeType.REFRESH
//Cascade refresh operation，级联刷新操作。
//假设场景 有一个订单,订单里面关联了许多商品,这个订单可以被很多人操作,那么这个时候A对此订单和关联的商品进行了修改,与此同时,B也进行了相同的操作,但是B先一步比A保存了数据,那么当A保存数据的时候,就需要先刷新订单信息及关联的商品信息后,再将订单及商品保存。(来自良心会痛的评论)
//CascadeType.ALL
//Cascade all operations，清晰明确，拥有以上所有级联操作权限。





//@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
//@JoinColumn(name = _WMS_STOCK_ROOM_ID_COLUMN, referencedColumnName = WmsStockRoom._ID_COLUMN, insertable = false, updatable = false)