package repositories

interface IResiduosContenedoresRepository {
    fun getContenedoresByTipoByDistrito(): List<String>
    fun getMediaContenedoresByDistrito(): List<String>
    fun getContenedoresByDistrito(): List<String>
    fun getContenedoresByToneladasByTipoBasuraByDistrito(): List<String>
    fun getAvgToneladasByDistrito(): List<String>
    fun getToneladasByDistrito(): List<String>
    fun getMaxByBasuraByDistrito(): List<String>
    fun getMinByBasuraByDistrito(): List<String>
    fun getAvgByBasuraByDistrito(): List<String>
    fun getDesviacionByBasuraByDistrito(): List<String>
    fun getRecogidoByAno(): List<String>
    fun getRecogidoByDistrito(): List<String>
}