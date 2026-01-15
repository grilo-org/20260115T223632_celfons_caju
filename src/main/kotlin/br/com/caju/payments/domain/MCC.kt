package br.com.caju.payments.domain

enum class MCC {
    FOOD, MEAL, CASH;

    companion object {
        fun from(mcc: String): MCC =
            when (mcc) {
                "5411", "5412" -> FOOD
                "5811", "5812" -> MEAL
                else -> CASH
            }
    }
}
