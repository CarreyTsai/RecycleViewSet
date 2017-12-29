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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * 多类型adapter 使用时需要注册类型到delegate
 * 有一对一注册方式。一对多注册方式
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "MultiTypeAdapter";
    @NonNull
    private List<?> mItems;
    @NonNull
    private TypeToDelegateManager mTypeToDelegateManager;


    public MultiTypeAdapter() {
        this(Collections.emptyList());
    }


    public MultiTypeAdapter(@NonNull List<?> items) {
        this(items, new DefaultTypeToDelegateManager());
    }


    public MultiTypeAdapter(@NonNull List<?> items, int initialCapacity) {
        this(items, new DefaultTypeToDelegateManager(initialCapacity));
    }


    public MultiTypeAdapter(@NonNull List<?> items, @NonNull TypeToDelegateManager manager) {
        this.mItems = items;
        this.mTypeToDelegateManager = manager;
    }


    /**
     * 注册 类型字节码 到 delegate
     *
     * @param clazz    数据类型字节码
     * @param delegate
     * @param <T>      为具体数据类型
     */
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewDelegate<T, ?> delegate) {
        checkAndRemoveAllTypesIfNeeded(clazz);
        register(clazz, delegate, new DefaultTypeOffset<T>());
    }

    <T> void register(@NonNull Class<? extends T> clazz,
                      @NonNull ItemViewDelegate<T, ?> delegate,
                      @NonNull TypeOffset<T> typeOffset) {
        mTypeToDelegateManager.register(clazz, delegate, typeOffset);
        delegate.adapter = this;
    }


    /**
     * 一对多注册 使用方式
     * {@link MultiTypeAdapter#register(Class)}.{@link TypeToDelegateMapping#into(ItemViewDelegate[])}.
     * {@link TypeToDelegateMapping#withMapping(TypeOffset)}
     * <p>
     * MultiTypeAdapter.register(class).into(...ItemViewDelegate).withMapping(TypeOffset)
     * MultiTypeAdapter.register(class).into(...ItemViewDelegate).withClassMapping(ClassOffset)
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    @CheckResult
    public <T> DelegateRegister<T> register(@NonNull Class<? extends T> clazz) {
        checkAndRemoveAllTypesIfNeeded(clazz);
        return new TypeToDelegateMapping<T>(this, clazz);
    }

    @NonNull
    public List<?> getItems() {
        return mItems;
    }


    public void setItems(@NonNull List<?> items) {
        this.mItems = items;
    }

    @NonNull
    public TypeToDelegateManager getTypeToDelegateManager() {
        return mTypeToDelegateManager;
    }


    /**
     * 如果是使用自定义的 管理类。需要在注册类型之前使用
     *
     * @param manager
     */
    public void setTypeToDelegateManager(@NonNull TypeToDelegateManager manager) {
        this.mTypeToDelegateManager = manager;
    }

    /**
     * 获取itemView 的类型。
     *
     * @param position
     * @return
     */
    @Override
    public final int getItemViewType(int position) {
        Object item = mItems.get(position);
        return indexInTypesOf(position, item);
    }


    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewDelegate<?, ?> delegate = mTypeToDelegateManager.getItemViewDelegate(viewType);
        return delegate.onCreateViewHolder(inflater, parent);
    }


    @Override
    @Deprecated
    public final void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        Object item = mItems.get(position);
        ItemViewDelegate delegate = mTypeToDelegateManager.getItemViewDelegate(holder.getItemViewType());
        delegate.onBindViewHolder(holder, item, payloads);
    }


    @Override
    public final int getItemCount() {
        return mItems.size();
    }


    @Override
    @SuppressWarnings("unchecked")
    public final long getItemId(int position) {
        Object item = mItems.get(position);
        int itemViewType = getItemViewType(position);
        ItemViewDelegate delegate = mTypeToDelegateManager.getItemViewDelegate(itemViewType);
        return delegate.getItemId(item);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewRecycled(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewRecycled(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        return getRawBinderByViewHolder(holder).onFailedToRecycleView(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewAttachedToWindow(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewDetachedFromWindow(holder);
    }


    private @NonNull
    ItemViewDelegate getRawBinderByViewHolder(@NonNull ViewHolder holder) {
        return mTypeToDelegateManager.getItemViewDelegate(holder.getItemViewType());
    }


    int indexInTypesOf(int position, @NonNull Object item) throws DelegateNotRegisterException {
        int index = mTypeToDelegateManager.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked")
            TypeOffset<Object> typeOffset = (TypeOffset<Object>) mTypeToDelegateManager.getTypeOffset(index);
            return index + typeOffset.index(position, item);
        }
        throw new DelegateNotRegisterException(item.getClass());
    }

    /**
     * 取消class 注册
     *
     * @param clazz
     */
    private void checkAndRemoveAllTypesIfNeeded(@NonNull Class<?> clazz) {
        if (mTypeToDelegateManager.unregister(clazz)) {
            Log.w(TAG, "You have registered the " + clazz.getSimpleName() + " type. " +
                    "It will override the original binder(s).");
        }
    }
}
