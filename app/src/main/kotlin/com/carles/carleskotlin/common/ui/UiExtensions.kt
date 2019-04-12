package com.carles.carleskotlin.common.ui

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.carles.carleskotlin.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Throwable.getMessageId() : Int = R.string.error_server_response

