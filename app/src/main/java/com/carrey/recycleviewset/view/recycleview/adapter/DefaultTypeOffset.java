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

/**
 * 数据类型  和delegate之间的偏移量
 *
 * @param <T>
 */
final class DefaultTypeOffset<T> implements TypeOffset<T> {

    /**
     * 默认一对一的偏移量 0
     *
     * @param position The position in items
     * @param t        Your item data
     * @return
     */
    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}
