package controllers

interface IController {
    suspend fun process()
}