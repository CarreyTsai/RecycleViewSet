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

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * 类型到delegate映射管理类。 具有类型  代理类 和偏移管理。 具有获取类型 获取代理 获取偏移等操作
 */

public interface TypeToDelegateManager {

    /**
     * 类型class 没有注册到容器
     */
    int NO_INDEX = -1;

    /**
     * 注册类型到delegate
     *
     * @param clazz      类型class
     * @param delegate   itemView delegate
     * @param typeOffset 类型和 delegate 之间偏移量 默认是0
     * @param <T>        数据类型
     */
    <T> void register(
            @NonNull Class<? extends T> clazz,
            @NonNull ItemViewDelegate<T, ?> delegate,
            @NonNull TypeOffset<T> typeOffset);

    /**
     * 取消注册
     *
     * @param clazz 类型class
     * @return true 返回是否取消注册成功
     */
    boolean unregister(@NonNull Class<?> clazz);

    /**
     * 类型和delegate 和offset 容器大小。需要一一对应
     *
     * @return 容器大小
     */
    int size();

    /**
     * 获取该类型class 对应的位置。 如果类型没有注册返回-1
     *
     * @param clazz 类型class
     * @return 获取容器总第一次出现 类型class 位置 如果类型没有注册，返回-1 {@link #NO_INDEX}
     */
    int firstIndexOf(@NonNull Class<?> clazz);

    /**
     * 通过索引获取类型
     *
     * @param index 类型class 索引
     * @return 通过索引获取的类型class
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @NonNull
    Class<?> getClass(@IntRange int index);

    /**
     * 通过索引获取 delegate
     *
     * @param index 类型class 索引
     * @return 通过索引获取的类型delegate
     * @throws IndexOutOfBoundsException
     */
    @NonNull
    ItemViewDelegate<?, ?> getItemViewDelegate(@IntRange int index);

    /**
     * 通过索引获取的偏移量 一种类型对应多种delegate的情况
     *
     * @param index 类型class 索引
     * @return 通过索引获取的类型class 和delegate 之间的偏移量（viewType）
     * @throws IndexOutOfBoundsException
     */
    @NonNull
    TypeOffset<?> getTypeOffset(@IntRange int index);
}
