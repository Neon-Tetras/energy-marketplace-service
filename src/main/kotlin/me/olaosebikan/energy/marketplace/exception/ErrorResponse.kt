package me.olaosebikan.energy.marketplace.exception

class ErrorResponse(
    val responseCode : String,
    val responseDescription : String
) {
    val responseStatus : String = "failed";
}