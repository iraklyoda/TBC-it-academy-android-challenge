package com.example.challenge.data.mapper.base


import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

