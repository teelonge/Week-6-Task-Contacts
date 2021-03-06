package com.example.week_6_task



import com.example.week_6_task.logic.ValidateContact.validateContactInput
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test


class ContactsValidationTest {

    private val names = arrayOf("","Tolulope Longe")
    private val emails = arrayOf("","tolulopelonge@gmail.com","tolulope","toluLOpe@gmail.com","tolulopeadebayo@gmail.cmo")
    private val phoneNumbers = arrayOf("","08090539526","0809yt7A45@")

    @Test
    fun validateName_givenAnEmptyString_returnFalse(){
        val result = validateContactInput(names[0],phoneNumbers[1],emails[1])
        assertFalse(result)
    }

    @Test
    fun validatePhone_givenAnEmptyString_returnFalse(){
        val result = validateContactInput(names[1],phoneNumbers[0],emails[1])
        assertFalse(result)
    }

    @Test
    fun validatePhone_ContainsElementAsidesDigit_returnFalse(){
        val result = validateContactInput(names[1],phoneNumbers[2],emails[4])
        assertFalse(result)
    }

    @Test
    fun validateEmail_givenAnEmptyString_returnFalse(){
        val result = validateContactInput(names[1],phoneNumbers[1],emails[0])
        assertFalse(result)
    }

    @Test
    fun validateEmail_doesntEndWithDotCom_returnFalse(){
        val result = validateContactInput(names[1],phoneNumbers[1],emails[4])
        assertFalse(result)
    }

    @Test
    fun validateEmail_containsCapitalLetter_returnFalse(){
        val result = validateContactInput(names[1],phoneNumbers[1],emails[3])
        assertFalse(result)
    }

    @Test
    fun validateContact_givenCorrectFieldInputs_returnTrue(){
        val result = validateContactInput(names[1],phoneNumbers[1],emails[1])
        assertTrue(result)
    }


}