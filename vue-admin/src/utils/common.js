export function isOk(row, column, cellValue){
    if(cellValue==0){
        return '否';
    }
    return '是'
}

export function isReward(row, column, cellValue){
    if(cellValue==0){
        return '积分';
    }
    return '现金'
}




//0未接单1已接单,2待审核，3已完成，4审核失败
export function isProcess(row, column, cellValue){
    if(cellValue==0){
        return '未接单';
    }
    if(cellValue==1){
        return '进行中';
    }
    if(cellValue==2){
        return '待审核';
    }    
    if(cellValue==3){
        return '已完成';
    }
    if(cellValue==4){
        return '审核失败';
    }
}




export function isFrom(row, column, cellValue){
    if(cellValue==1){
        return '微博';
    }
    return '公众号'
};

export function isPass(row, column, cellValue){
    if(cellValue==0){
        return '进行中';
    }
    if(cellValue==1){
        return '审核中';
    }
    if(cellValue==2){
        return '审核失败';
    }
    if(cellValue==3){
        return '结束';
    }
    if(cellValue==4){
        return '取消';
    }
}

export function ApproveState(row, column, cellValue){
    if(cellValue==0){
        return '审核中';
    }
    if(cellValue==1){
        return '审核通过';
    }
    if(cellValue==2){
        return '审核失败';
    }
    if(cellValue==3){
        return '无需审核';
    }
}

export function getRealTime(row, column, cellValue){
    var crtTime = new Date(cellValue*1000);
    return dateFtt("yyyy-MM-dd hh:mm",crtTime);    
}

function dateFtt(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 
