package com.baseconfig.pillar.databinding.recycler;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Binding holder for databinding layouts.
 * Example usage:
 * <pre>{@code
 *  BindingHolder<? extends ViewDataBinding> getHolder( ... ){
 *      MyLayoutBinding binding = DataBindingUtil.inflate(..., R.layout.my_layout, ..., false);
 *      return new BindingHolder<>(binding); //you can inline this no problem.
 *  }
 *
 *  void applyBinding(BindingHolder<? extends ViewDataBinding> holder){
 *      MyLayoutBinding binding = holder.getBinding();
 *      binding.textView.setText("Hello there General Kenobi")
 *  }
 * }</pre>
 */

public class BindingHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final V binding;

    public BindingHolder(V viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.binding = viewDataBinding;
    }

    public V getBinding() {
        return binding;
    }
}
