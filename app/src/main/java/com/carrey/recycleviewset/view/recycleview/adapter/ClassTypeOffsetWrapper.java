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

import java.util.Arrays;

final class ClassTypeOffsetWrapper<T> implements TypeOffset<T> {

    @NonNull
    private final ClassOffset<T> mClassOffset;
    @NonNull
    private ItemViewDelegate<T, ?>[] mDelegates;


    /**
     * 类型偏移量 和对应的 delegate之间关系
     *
     * @param classOffset
     * @param delegates
     */

    private ClassTypeOffsetWrapper(@NonNull ClassOffset<T> classOffset,
                                   @NonNull ItemViewDelegate<T, ?>[] delegates) {
        this.mClassOffset = classOffset;
        this.mDelegates = delegates;
    }

    /**
     * 把偏移量和 delegates 包裹进来处理索引关系 {@link #index}来 满足
     * {@link TypeToDelegateMapping#doRegister(TypeOffset)} 入参要求
     *
     * @param classOffset
     * @param delegates
     * @param <T>
     * @return
     */
    @NonNull
    static <T> ClassTypeOffsetWrapper<T> wrap(@NonNull ClassOffset<T> classOffset,
                                              @NonNull ItemViewDelegate<T, ?>[] delegates) {
        return new ClassTypeOffsetWrapper<T>(classOffset, delegates);
    }

    /**
     * 数据类型 和delegate 的对应关系。
     * 一对一的情况 使用 {@link DefaultTypeOffset#index}
     * 针对一对多情况 一种数据类型注册多种delegate 需要设置 delegate 的索引
     * 使用{@link TypeToDelegateMapping#withMapping(TypeOffset)}来设置索引
     *
     * @param position 数据的位置
     * @param t        数据源
     * @return 数据类型对应的 delegate 的索引
     */
    @Override
    public int index(int position, @NonNull T t) {
        Class<?> delegate = mClassOffset.index(position, t);
        for (int i = 0; i < mDelegates.length; i++) {
            if (mDelegates[i].getClass().equals(delegate)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(
                String.format("%s 不在已注册的delegates (%s) 内.",
                        delegate.getName(), Arrays.toString(mDelegates))
        );
    }
}
