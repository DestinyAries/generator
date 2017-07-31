/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

/**
 * 
 * @author linwanrong
 * 
 */
public class SelectByMapMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    public SelectByMapMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        Class<List> listClass = List.class;
        importedTypes.add(new FullyQualifiedJavaType(listClass.getName()));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(listClass.getSimpleName());
        returnType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        method.setReturnType(returnType);

        method.setName(introspectedTable.getSelectByMapStatementId());

        Class<Map> mapClass = Map.class;
        importedTypes.add(new FullyQualifiedJavaType(mapClass.getName()));
        String parameterName = "params";
        Parameter parameter = new Parameter(
                new FullyQualifiedJavaType(
                        mapClass.getSimpleName() + "<String, Object>"), parameterName);
        method.addParameter(parameter); //$NON-NLS-1$

        addMapperAnnotations(interfaze, method);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(
                method, interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
