package app.auth.domain

interface PatternValidator {
    fun matches(value : String) : Boolean
}