package com.hellofresh.chiragtest.network


class LoginValidatorStatus(val status: Status, val message: String?) {
    companion object {

        fun  success(): LoginValidatorStatus
        {
            return LoginValidatorStatus(status = Status.SUCCESS,message = "")
        }

        fun error(t: String?): LoginValidatorStatus
        {
            return LoginValidatorStatus(Status.ERROR, t)
        }
    }

    enum class Status
    {
        SUCCESS ,ERROR
    }
}