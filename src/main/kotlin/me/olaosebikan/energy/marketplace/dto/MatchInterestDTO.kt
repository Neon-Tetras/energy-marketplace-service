package me.olaosebikan.energy.marketplace.dto

data class MatchInterestDTO (
    var interestId : Long,
    var sellerId : String,
    var certificateIds: Array<Long>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatchInterestDTO

        if (interestId != other.interestId) return false
        if (sellerId != other.sellerId) return false
        if (!certificateIds.contentEquals(other.certificateIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = interestId.hashCode()
        result = 31 * result + sellerId.hashCode()
        result = 31 * result + certificateIds.contentHashCode()
        return result
    }
}