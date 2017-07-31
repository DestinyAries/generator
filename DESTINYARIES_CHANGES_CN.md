# 微改动描述
此改动是在 [MyBatis Generator (MBG)](https://github.com/mybatis/generator) 源代码基础上进行的扩展，且仅支持Mybatis3，不支持ibatis2。小女子能力有限，若有不足望请谅解并指出，谢谢。

# 功能
1. 添加 suppressDefaultComments 属性，去除默认生成的 Mybatis 注释，但可生成数据库表字段 comment 的注释 
2. xml 的生成变动
* ByMap_Where_Clause: 添加动态 sql 标签，用于条件查询的参数匹配
* ByMap_Set_Clause: 添加动态 sql 标签，用于赋值给指定的字段
* selectByMap: 添加动态 select 标签，用于条件查询
* updateByMap: 添加动态 update 标签，用于条件更新

3. XXXMapper.java 的生成变动
* 添加方法: List<XXXEntity> selectByMap(Map<String, Object> params);
* 添加方法: int updateByMap(Map<String, Object> params);

# 用法 - MAVEN
1. 打包成 jar 包(mybatis-generator-core-1.3.6-SNAPSHOT.jar)
2. 将 jar 包放到 MAVEN 的 Local Repository 的对应路径下 (如: .m2/repository/org/mybatis/generator/mybatis-generator-core/1.3.6-SNAPSHOT/)
3. 在所需生成代码的项目的 pom.xml 中配置即可

# 例子
1. suppressDefaultComments 属性
```
<commentGenerator>
    <!--  关闭默认生成的注释  -->
    <property name="suppressDefaultComments" value="true" />
    <!-- 生成的注释中包含数据库中的表的字段备注 -->
    <property name="addRemarkComments" value="true" />
</commentGenerator>
```

2. 上文功能所述的 xml 和 XXXMapper 的生成变动，在当前版本是强制生成的，不受配置影响。生成结果如下：
* xml (默认生成的是 equal 匹配表达式，可根据需要添加其他表达式)

```
<sql id="ByMap_Where_Clause">
    <where>
      ...
      <if test="eqName != null">
        AND name = #{eqName,jdbcType=VARCHAR}
      </if>
      ...
    </where>
</sql>

<sql id="ByMap_Set_Clause">
    <set>
      ...
      <if test="name != null">
        name = #{name,jdbcType=VARCHAR},
      </if>
      ...
    </set>
</sql>

<select id="selectByMap" parameterType="java.util.Map" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from user_info
    <if test="_parameter != null">
      <include refid="ByMap_Where_Clause" />
      <if test="orderByClause != null">
        order by ${orderByClause}
      </if>
    </if>
</select>

<update id="updateByMap" parameterType="java.util.Map">
    update user_info
    <if test="_parameter != null">
      <include refid="ByMap_Set_Clause" />
      <include refid="ByMap_Where_Clause" />
    </if>
</update>
```
* XXXMapper

```
List<XXXEntity> selectByMap(Map<String, Object> params);

int updateByMap(Map<String, Object> params);
```
