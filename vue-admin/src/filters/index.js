import moment from 'moment'

/**
 * 数字保留小数点后两位
 */
export function formatDecimal(number) {
  return Number(number).toFixed(2)
}

/**
 * 时间戳转换时间
 */
export function formatDate(date) {
  if (date == undefined) {
    return "";
  }
  return moment(parseInt(date)*1000).format("YYYY-MM-DD HH:mm:ss");
}

/**
 * 用户状态转换
 */
export function statusFilter(status) {
  if (status == 0) {
    return "正常";
  }else return "禁用";
}

/**
 * 用户账号状态转换
 */
export function accStatusFilter(status) {
  switch (status) {
    case 0:
      return "等待审核";
    case 1:
      return "审核通过";
    case 2:
      return "审核失败";
    case 3:
      return "已删除";
    case -1:
      return "账号禁用中";
    case -2:
      return "永久禁用";
  }
}

/**
 * 提现状态
 */
export function cashStatusFilter(status) {
  switch (status) {
    case 0:
      return "等待审核";
    case 1:
      return "支付完成";
    default:
      return "拒绝";
  }
}

/**
 * 收入分类转换
 */
export function incomeTypeFilter(status) {
  switch (status) {
    case 'admin_change':
      return "后台充值";
    case 'order_pay':
      return "下单支付预存款";
    case 'order_freeze':
      return "下单冻结预存款";
    case 'order_cancel':
      return "取消订单解冻预存款";
    case 'order_comb_pay':
      return "下单支付被冻结的预存款";
    case 'recharge':
      return "充值";
    case 'cash_apply':
      return "申请提现冻结预存款";
    case 'cash_pay':
      return "提现成功";
    case 'cash_del':
      return "取消提现申请";
    case 'refund':
      return "取消退还";
    case 'task_in':
      return "任务收入";
    case 'task_income':
      return "任务收入";
    case 'task_in_check':
      return "收入待审";
    case 'task_income_check':
      return "收入待审";
    case 'check_false':
      return "审核失败";
    case 'sign':
      return "签到";
    case 'task_outcome':
      return '任务支出'
  }
}

/**
 * 支付状态
 */
export function payStatusFilter(status) {
  if (status == 0) {
    return "未支付";
  }else return "已支付";
}


/**
 * 支付方式
 */
export function payTypeFilter(status) {
  switch (status) {
    case '1':
      return "支付宝";
    case '2':
      return "微信";
    case '3':
      return "钱包";
  }
}
