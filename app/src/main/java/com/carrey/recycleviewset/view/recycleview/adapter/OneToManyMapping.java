/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carrey.recycleviewset.view.recycleview.adapter;

import android.support.annotation.NonNull;


public interface OneToManyMapping<T> {


    /**
     * int 型数据类型偏移量  主要做为viewType
     *
     * @param typeOffset
     */
    void withMapping(@NonNull TypeOffset<T> typeOffset);

    /**
     * 根据数据源索引 或者数据类型 返回对应的delegate 字节码
     *
     * @param classOffset
     */

    void withClassMapping(@NonNull ClassOffset<T> classOffset);
}
