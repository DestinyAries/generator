# Description
This change is expanded according to [MyBatis Generator (MBG)](https://github.com/mybatis/generator) and applies only to Mybatis3. My ability is limited, if the code is defective, please forgive me and point the bugs out, thank you.

# Functions
1. Add the suppressDefaultComments property to remove the default generated MyBatis annotation, but can generate a comment for the database table field comment.
2. Xml generation changes
* ByMap_Where_Clause: Add a dynamic sql tag that is used to match the parameters of the conditional query.
* ByMap_Set_Clause: Add a dynamic sql tag that is used to assign to the specified field.
* selectByMap: Add a dynamic select tag that is used to select by a Map<String, Object> param.
* updateByMap: Add a dynamic update tag that is used to update by a Map<String, Object> param.

3. XXXMapper.java generation changes
* Add a selectByMap function: List<XXXEntity> selectByMap(Map<String, Object> params);
* Add a updateByMap unction: int updateByMap(Map<String, Object> params);

# Example
1. suppressDefaultComments
```
<commentGenerator>
    <property name="suppressDefaultComments" value="true" />
    <property name="addRemarkComments" value="true" />
</commentGenerator>
```

2. The above functions described in the xml and XXXMapper generation changes in the current version are mandatory and are not affected by configuration. The resulting results are as follows.
* xml (The default is an equal match expression, you can add other expressions as needed)

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
