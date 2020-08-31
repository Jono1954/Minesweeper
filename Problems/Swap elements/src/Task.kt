// The array numbers is already exists. Write only exchange actions here.

    val temp = numbers.first()
    numbers[0] = numbers.last()
    numbers[numbers.lastIndex] = temp
