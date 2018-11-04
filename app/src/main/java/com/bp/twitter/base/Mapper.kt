package com.bp.twitter.base

interface Mapper<out V, in D> {

    fun mapToView(type: D): V

}