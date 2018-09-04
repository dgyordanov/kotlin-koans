package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

    operator fun plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)

    operator fun plus(repeatedTimeInterval: RepeatedTimeInterval) = addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.times)

}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) {

    operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }

    operator fun iterator() = DateRangeIterator(this)

}

class DateRangeIterator(val dateRange: DateRange) : Iterator<MyDate> {

    private var current = dateRange.start

    override fun next(): MyDate {
        val next = current
        current = current.nextDay()
        return next
    }

    override fun hasNext() : Boolean = current <= dateRange.endInclusive

}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val times: Int)

operator fun TimeInterval.times(times: Int) = RepeatedTimeInterval(this, times)
