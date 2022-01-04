package com.black.icon.animated.extensions;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J8\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u00172\u0019\u0010\u0018\u001a\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019\u00a2\u0006\u0002\b\u001bH\u0016\u00a2\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\u001aH\u0007J(\u0010\u001e\u001a\u00028\u00002\u0019\u0010\u001f\u001a\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019\u00a2\u0006\u0002\b\u001bH\u0016\u00a2\u0006\u0002\u0010 R\u001e\u0010\u0006\u001a\u0004\u0018\u00018\u0000X\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006!"}, d2 = {"Lcom/black/icon/animated/extensions/ViewBindingHolderImpl;", "T", "Landroidx/viewbinding/ViewBinding;", "Lcom/black/icon/animated/extensions/ViewBindingHolder;", "Landroidx/lifecycle/LifecycleObserver;", "()V", "binding", "getBinding", "()Landroidx/viewbinding/ViewBinding;", "setBinding", "(Landroidx/viewbinding/ViewBinding;)V", "Landroidx/viewbinding/ViewBinding;", "fragmentName", "", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "getLifecycle", "()Landroidx/lifecycle/Lifecycle;", "setLifecycle", "(Landroidx/lifecycle/Lifecycle;)V", "initBinding", "Landroid/view/View;", "fragment", "Landroidx/fragment/app/Fragment;", "onBound", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Landroidx/viewbinding/ViewBinding;Landroidx/fragment/app/Fragment;Lkotlin/jvm/functions/Function1;)Landroid/view/View;", "onDestroyView", "requireBinding", "block", "(Lkotlin/jvm/functions/Function1;)Landroidx/viewbinding/ViewBinding;", "app_debug"})
public final class ViewBindingHolderImpl<T extends androidx.viewbinding.ViewBinding> implements com.black.icon.animated.extensions.ViewBindingHolder<T>, androidx.lifecycle.LifecycleObserver {
    @org.jetbrains.annotations.Nullable()
    private T binding;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.Lifecycle lifecycle;
    private java.lang.String fragmentName;
    
    public ViewBindingHolderImpl() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public T getBinding() {
        return null;
    }
    
    public void setBinding(@org.jetbrains.annotations.Nullable()
    T p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.lifecycle.Lifecycle getLifecycle() {
        return null;
    }
    
    public final void setLifecycle(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.Lifecycle p0) {
    }
    
    /**
     * To not leak memory we nullify the binding when the view is destroyed.
     */
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_DESTROY)
    public final void onDestroyView() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public T requireBinding(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> block) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.View initBinding(@org.jetbrains.annotations.NotNull()
    T binding, @org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onBound) {
        return null;
    }
}