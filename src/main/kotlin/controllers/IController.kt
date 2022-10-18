package controllers

/**
 * Interfaz para los controladores
 */
interface IController<T> {
    /**
     * Inicializa el controlador
     */
    suspend fun process(): T
}