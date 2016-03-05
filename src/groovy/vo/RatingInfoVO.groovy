package vo

/**
 * Created by vishnu on 24/2/16.
 */
class RatingInfoVO {
    Integer totalVotes
    Integer averageScore
    Integer totalScore

    @Override
    String toString()
    {
        return "${totalVotes}, ${averageScore}, ${totalScore}"
    }
}
