package com.black.icon.animated.extensions;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J8\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\n2\u0019\u0010\u000b\u001a\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r\u0018\u00010\f\u00a2\u0006\u0002\b\u000eH&\u00a2\u0006\u0002\u0010\u000fJ*\u0010\u0010\u001a\u00028\u00002\u001b\b\u0002\u0010\u0011\u001a\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r\u0018\u00010\f\u00a2\u0006\u0002\b\u000eH&\u00a2\u0006\u0002\u0010\u0012R\u0014\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/black/icon/animated/extensions/ViewBindingHolder;", "T", "Landroidx/viewbinding/ViewBinding;", "", "binding", "getBinding", "()Landroidx/viewbinding/ViewBinding;", "initBinding", "Landroid/view/View;", "fragment", "Landroidx/fragment/app/Fragment;", "onBound", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Landroidx/viewbinding/ViewBinding;Landroidx/fragment/app/Fragment;Lkotlin/jvm/functions/Function1;)Landroid/view/View;", "requireBinding", "block", "(Lkotlin/jvm/functions/Function1;)Landroidx/viewbinding/ViewBinding;", "app_debug"})
public abstract interface ViewBindingHolder<T extends androidx.viewbinding.ViewBinding> {
    
    @org.jetbrains.annotations.Nullable()
    public abstract T getBinding();
    
    /**
     * Saves the binding for cleanup on onDestroy, calls the specified function [onBound] with `this` value
     * as its receiver and returns the bound view root.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract android.view.View initBinding(@org.jetbrains.annotations.NotNull()
    T binding, @org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onBound);
    
    /**
     * Calls the specified [block] with the binding as `this` value and returns the binding. As a consequence, this method
     * can be used with a code block lambda in [block] or to initialize a variable with the return type.
     *
     * @throws IllegalStateException if not currently holding a ViewBinding (when called outside of an active fragment's lifecycle)
     */
    @org.jetbrains.annotations.NotNull()
    public abstract T requireBinding(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> block);
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 3)
    public final class DefaultImpls {
    }
}