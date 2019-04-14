package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * ${table.comment!} Mapper 接口
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    MyPage<${entity}> selectPage(@Param("param") MyPage page);
}
</#if>
