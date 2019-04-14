package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * ${table.comment!} 服务类
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

     MyPage<${entity}> selectPage(MyPage page);
}
</#if>
