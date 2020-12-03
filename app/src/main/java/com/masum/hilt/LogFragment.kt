package com.masum.hilt

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogFragment :Fragment() {
    @Inject lateinit var localDb:LocalDataResource
    @Inject lateinit var dateFormatter: DateFormatter
}