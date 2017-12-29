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

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;


class TypeToDelegateMapping<T> implements DelegateRegister<T>, OneToManyMapping<T> {
    @NonNull
    private final MultiTypeAdapter adapter;
    @NonNull
    private final Class<? extends T> clazz;
    private ItemViewDelegate<T, ?>[] mDelegates;


    TypeToDelegateMapping(@NonNull MultiTypeAdapter adapter, @NonNull Class<? extends T> clazz) {
        this.clazz = clazz;
        this.adapter = adapter;
    }

    /**
     * 包裹delegates 然后使用withMapping做偏移处理
     *
     * @param delegates delegates
     * @return
     */
    @NonNull
    @Override
    @CheckResult
    @SafeVarargs
    public final OneToManyMapping<T> into(@NonNull ItemViewDelegate<T, ?>... delegates) {
        this.mDelegates = delegates;
        return this;
    }


    /**
     * 注册到manager
     * @param typeOffset  int类型映射
     */
    @Override
    public void withMapping(@NonNull TypeOffset<T> typeOffset) {
        doRegister(typeOffset);
    }


    /**
     * 注册到manager
     * @param classOffset  字节码映射
     */
    @Override
    public void withClassMapping(@NonNull ClassOffset<T> classOffset) {
        doRegister(ClassTypeOffsetWrapper.wrap(classOffset, mDelegates));
    }


    /**
     * 注册到manager
     * @param typeOffset
     */

    private void doRegister(@NonNull TypeOffset<T> typeOffset) {
        for (ItemViewDelegate<T, ?> binder : mDelegates) {
            adapter.register(clazz, binder, typeOffset);
        }
    }
}
