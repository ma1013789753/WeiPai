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
 * 时间戳转换时间
 */
export function statusFilter(status) {
  if (status == 0) {
    return "正常";
  }else return "禁用";
}
