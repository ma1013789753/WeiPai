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
