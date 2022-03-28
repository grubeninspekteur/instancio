/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instancio.model;

import org.instancio.internal.model.CollectionNode;
import org.instancio.internal.model.Node;
import org.instancio.pojo.collections.lists.ListListString;
import org.instancio.testsupport.fixtures.Types;
import org.instancio.testsupport.templates.ModelTestTemplate;
import org.instancio.testsupport.utils.CollectionUtils;

import java.util.List;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;

class ListListStringModelTest extends ModelTestTemplate<ListListString> {

    @Override
    protected void verify(Node rootNode) {
        assertNode(rootNode)
                .hasKlass(ListListString.class)
                .hasChildrenOfSize(1);

        // List<List<String>>
        final CollectionNode outerListNode = assertNode(CollectionUtils.getOnlyElement(rootNode.getChildren()))
                .hasNoChildren()
                .hasFieldName("nested")
                .hasGenericType(Types.LIST_LIST_STRING.get())
                .getAs(CollectionNode.class);

        // List<String>
        final CollectionNode outerListElementNode = (CollectionNode) outerListNode.getElementNode();

        assertNode(outerListElementNode)
                .hasKlass(List.class)
                .hasNullField()
                .hasGenericType(Types.LIST_STRING.get())
                .hasNoChildren()
                .getAs(CollectionNode.class);

        // String
        assertNode(outerListElementNode.getElementNode())
                .hasKlass(String.class)
                .hasNullField()
                .hasNoChildren();
    }
}