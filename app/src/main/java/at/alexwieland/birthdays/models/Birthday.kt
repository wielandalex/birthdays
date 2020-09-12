package at.alexwieland.birthdays.models

import java.time.LocalDate

data class Birthday(
    var id: Int = -1,
    var name: String = "",
    var date: LocalDate = LocalDate.of(1900, 1, 1)
) {}