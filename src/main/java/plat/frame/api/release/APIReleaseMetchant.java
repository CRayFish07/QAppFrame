package plat.frame.api.release;

import java.util.List;

import plat.frame.api.APIRelease;
import plat.frame.api.APIReleaseInfo;

/**
 * 商户版接口发布.
 * @author chenzhuo
 *
 */
public class APIReleaseMetchant extends APIRelease
{
	//此URL对格式有严格要求,必须两个一起即:接口简述+地址URL
	private String[] urls = {
			"--说明--",
			"将XXX.api改为XXX.mto即为App调用接口.",
			"商户版登录",
			"/mns0/login/MerchantLogin/loginv0.api",
			"查询客户信息",
			"/mtr0/login/MerchantLogin/pullInfoV0.api",
			"找回登录前客户信息查询",
			"/mns0/login/MertPassword/qMertInfo.api",
			"找回登录密码",
			"/mns0/login/MertPassword/resetPwd.api",
			"修改登录密码",
			"/mtr0/login/MertPassword/updatePwd.api",
			"添加员工",
			"mtr0/baseManager/StaffManager/addStaffInfo.api",
			"修改员工",
			"mtr0/baseManager/StaffManager/updateStaffInfo.api",
			"查询员工信息",
			"mtr0/baseManager/StaffManager/queryStaffInfo.api",
			"添加部门",
			"mtr0/baseManager/DeptmentManager/addDept.api",
			"修改部门",
			"mtr0/baseManager/DeptmentManager/updateDeptInfo.api",
			"查询部门列表信息",
			"mtr0/baseManager/DeptmentManager/queryDeptInfo.api",
			"优惠券查询及试算",
			"mtr0/baseManager/DiscountCouponManager/queryDiscountCouponAndCount.api",
			"使用优惠券",
			"mtr0/baseManager/DiscountCouponManager/useDiscountCoupon.api",
			/*	"优惠券模板生成",
			"mtr0/baseManager/DiscountCouponManager/createDiscountCouponModel.api",
			"优惠券模板查询",
			"mtr0/baseManager/DiscountCouponManager/queryDiscountCouponModel.api",
			"优惠券领取",
			"mtr0/baseManager/DiscountCouponManager/getDiscountCoupon.api",*/
			"生成订单号",
			"mtr0/baseManager/OrderManager/createOrderNum.api",
			"生成订单",
			"mtr0/baseManager/OrderManager/createOrder.api",
			"订单取消",
			"mtr0/baseManager/OrderManager/cancelOrder.api",
			"订单支付",
			"mtr0/baseManager/OrderManager/orderPay.api",
			"订单查询",
			"mtr0/baseManager/OrderManager/queryOrderInfo.api"
	};
	
	public List<APIReleaseInfo> queryAPIs()
	{
		return queryAllAPIs(urls);
	}
}
