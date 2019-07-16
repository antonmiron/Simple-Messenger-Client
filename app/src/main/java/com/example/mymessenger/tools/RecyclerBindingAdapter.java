package com.example.mymessenger.tools;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerBindingAdapter<T>
        extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder> {
    private int holderLayout, variableId;
    private List<T> items;
    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;
    private int maxItemCount;

    public RecyclerBindingAdapter(int holderLayout, int variableId, List<T> items) {
        this(holderLayout,variableId,items,-1);
    }
    public RecyclerBindingAdapter(int holderLayout, int variableId, List<T> items,int maxItemCount) {
        this.holderLayout = holderLayout;
        this.variableId = variableId;
        this.items = items;
        this.maxItemCount=maxItemCount;
    }

    /**Создаем Holder**/
    @Override
    public RecyclerBindingAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(holderLayout, parent, false);
        return new BindingHolder(v);
    }

    /**Добавляем item в holder**/
    @Override
    public void onBindViewHolder(RecyclerBindingAdapter.BindingHolder holder, final int position) {
        final T item = items.get(position);
        holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemLongClickListener!=null) {
                    onItemLongClickListener.onItemLongClick(position, item);
                    return true;
                }
                return false;
            }
        });
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, item);
            }
        });
        holder.getBinding().setVariable(variableId, item);
    }

    /**Список всех элементов**/
    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        if(maxItemCount>0 && items.size()>maxItemCount)
            return maxItemCount;
        else
            return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item);
    }
    public interface OnItemLongClickListener<T>{
        void onItemLongClick(int position, T item);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
