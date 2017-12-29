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

import java.util.ArrayList;
import java.util.List;

/**
 * 类型到delegate映射管理类。 具有类型  代理类 和偏移管理。 具有获取类型 获取代理 获取偏移等操作
 */
public class DefaultTypeToDelegateManager implements TypeToDelegateManager {

    /**
     * 类型 class 容器
     */

    @NonNull
    private final List<Class<?>> mClasses;
    /**
     * delegate 容器
     */
    @NonNull
    private final List<ItemViewDelegate<?, ?>> mDelegates;
    /**
     * 类型和delegate 偏移量容器
     */
    @NonNull
    private final List<TypeOffset<?>> mTypeOffsets;


    public DefaultTypeToDelegateManager() {
        this.mClasses = new ArrayList<>();
        this.mDelegates = new ArrayList<>();
        this.mTypeOffsets = new ArrayList<>();
    }


    public DefaultTypeToDelegateManager(int initialCapacity) {
        this.mClasses = new ArrayList<>(initialCapacity);
        this.mDelegates = new ArrayList<>(initialCapacity);
        this.mTypeOffsets = new ArrayList<>(initialCapacity);
    }


    /**
     * 这个方法可能导致 class delegate offset 容器大小不一致
     *
     * @param classes
     * @param delegates
     * @param typeOffsets
     */
    private DefaultTypeToDelegateManager(@NonNull List<Class<?>> classes,
                                         @NonNull List<ItemViewDelegate<?, ?>> delegates,
                                         @NonNull List<TypeOffset<?>> typeOffsets) {
        this.mClasses = classes;
        this.mDelegates = delegates;
        this.mTypeOffsets = typeOffsets;
    }

    /**
     * 注册类型到delegate
     *
     * @param clazz      类型class
     * @param delegate   itemView delegate
     * @param typeOffset 类型和 delegate 之间偏移量 默认是0
     * @param <T>        数据类型
     */
    @Override
    public <T> void register(
            @NonNull Class<? extends T> clazz,
            @NonNull ItemViewDelegate<T, ?> delegate,
            @NonNull TypeOffset<T> typeOffset) {
        mClasses.add(clazz);
        mDelegates.add(delegate);
        mTypeOffsets.add(typeOffset);
    }

    /**
     * 取消注册
     *
     * @param clazz 类型class
     * @return true 返回是否取消注册成功
     */
    @Override
    public boolean unregister(@NonNull Class<?> clazz) {
        boolean removed = false;
        while (true) {
            int index = mClasses.indexOf(clazz);
            if (index != NO_INDEX) {
                mClasses.remove(index);
                mDelegates.remove(index);
                mTypeOffsets.remove(index);
                removed = true;
            } else {
                break;
            }
        }
        return removed;
    }


    @Override
    public int size() {
        return mClasses.size();
    }


    /**
     * 获取该类型class 对应的位置。 如果类型没有注册返回-1
     *
     * @param clazz 类型class
     * @return 获取容器总第一次出现 类型class 位置 如果类型没有注册，返回-1 {@link #NO_INDEX}
     */
    @Override
    public int firstIndexOf(@NonNull final Class<?> clazz) {
        int index = mClasses.indexOf(clazz);
        if (index != NO_INDEX) {
            return index;
        }
        for (int i = 0; i < mClasses.size(); i++) {
            if (mClasses.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return NO_INDEX;
    }


    @Override
    public @NonNull
    Class<?> getClass(int index) {
        return mClasses.get(index);
    }


    @Override
    public @NonNull
    ItemViewDelegate<?, ?> getItemViewDelegate(int index) {
        return mDelegates.get(index);
    }


    @Override
    public @NonNull
    TypeOffset<?> getTypeOffset(int index) {
        return mTypeOffsets.get(index);
    }
}
