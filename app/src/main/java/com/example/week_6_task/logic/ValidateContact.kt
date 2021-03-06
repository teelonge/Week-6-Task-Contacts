package com.example.week_6_task.logic

object ValidateContact {
    /*
    Validates user's input when creating or updating a contact to ensure input entered is not empty and al checks for some other conditions
    */

    fun validateContactInput(name : String, phoneNumber : String, email : String) : Boolean
    {
        return when{
                (name.isNotEmpty() &&
                        (phoneNumber.isNotEmpty() && phoneNumber.all { it.isDigit() }) &&
                        (email.any { it.isDigit() } || email.isNotEmpty() && email.filter { it != '@' && it != '.' }.all { it.isLowerCase() })
                        && email.contains('@') && email.endsWith(".com")) && email.first().isLetter()   ->
            {
                true
            }

            else ->{
                false
            }
        }
    }
}